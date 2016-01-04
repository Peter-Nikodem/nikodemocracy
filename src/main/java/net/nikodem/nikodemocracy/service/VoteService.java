package net.nikodem.nikodemocracy.service;

import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.dto.Vote;
import net.nikodem.nikodemocracy.model.exception.AnswerNotFoundException;
import net.nikodem.nikodemocracy.model.exception.ElectionNotFoundException;
import net.nikodem.nikodemocracy.model.exception.VoterKeyAlreadyUsedException;
import net.nikodem.nikodemocracy.model.exception.VoterKeyNotFoundException;
import net.nikodem.nikodemocracy.model.jpa.AnswerEntity;
import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import net.nikodem.nikodemocracy.model.jpa.PresenceEntity;
import net.nikodem.nikodemocracy.model.jpa.VoteEntity;
import net.nikodem.nikodemocracy.repository.AnswerRepository;
import net.nikodem.nikodemocracy.repository.ElectionRepository;
import net.nikodem.nikodemocracy.repository.PresenceRepository;
import net.nikodem.nikodemocracy.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Peter Nikodem
 */
@Service
public class VoteService {
    @Autowired
    ElectionRepository electionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    PresenceRepository presenceRepository;

    @Autowired
    VoteRepository voteRepository;


    @Transactional
    public void createPresenceRecords(Election election, List<String> anonymizedVoterKeys) throws ElectionNotFoundException {
        ElectionEntity electionEntity = findElectionEntity(election);
        List<PresenceEntity> presences = anonymizedVoterKeys.stream()
                .map(voterKey -> new PresenceEntity(voterKey, electionEntity))
                .collect(Collectors.toList());
        presenceRepository.save(presences);
    }

    @Transactional
    public void submitVote(Vote vote) {
        ElectionEntity electionEntity = findElectionEntity(vote.getElection());
        PresenceEntity presence = retrieveAndVerifyVotersPresence(vote,electionEntity);
        saveVote(vote,electionEntity);
        updatePresnce(presence);
    }

    private void updatePresnce(PresenceEntity presence) {
        presence.setHasVoted(true);
        presenceRepository.save(presence);
    }

    private void saveVote(Vote vote, ElectionEntity electionEntity) {
        AnswerEntity chosenAnswerEntity = findChosenAnswerEntity(vote.getChosenAnswer(), electionEntity);
        VoteEntity voteEntity = new VoteEntity(vote.getVoteKey(), chosenAnswerEntity);
        voteRepository.save(voteEntity);
    }

    private AnswerEntity findChosenAnswerEntity(String answer, ElectionEntity electionEntity) {
        return answerRepository.findByAnswerTextAndElection(answer,electionEntity)
                .orElseThrow(AnswerNotFoundException::new);
    }

    private PresenceEntity retrieveAndVerifyVotersPresence(Vote vote, ElectionEntity electionEntity)
            throws VoterKeyAlreadyUsedException, VoterKeyNotFoundException {
        PresenceEntity presence = presenceRepository.findByVoterKeyAndElection(vote.getVoterKey(), electionEntity)
                .orElseThrow(VoterKeyNotFoundException::new);
        if (presence.hasVoted()) {
            throw new VoterKeyAlreadyUsedException();
        }
        return presence;

    }

    private ElectionEntity findElectionEntity(Election election) {
        return electionRepository
                .findByNameAndAdminUsername(election.getName(), election.getAdmin().getUsername())
                .orElseThrow(ElectionNotFoundException::new);
    }
}
