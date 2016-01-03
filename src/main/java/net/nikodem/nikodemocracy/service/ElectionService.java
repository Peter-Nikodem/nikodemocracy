package net.nikodem.nikodemocracy.service;

import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.exception.ElectionNameAlreadyTakenException;
import net.nikodem.nikodemocracy.model.jpa.AnswerEntity;
import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import net.nikodem.nikodemocracy.model.jpa.VoterEntity;
import net.nikodem.nikodemocracy.repository.AnswerRepository;
import net.nikodem.nikodemocracy.repository.ElectionRepository;
import net.nikodem.nikodemocracy.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Responsible for saving and retrieving Elections.
 *
 * @author Peter Nikodem
 */
@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private VoterRepository voterRepository;


    public void createElection(Election election, Map<String, String> mailsToVoterIds) throws ElectionNameAlreadyTakenException {
        checkIfElectionWithTheSameNameAndAdminAlreadyDoesntExist(election);
        ElectionEntity savedElection = saveElection(election);
        saveAnswers(election.getPossibleAnswers(), savedElection);
        saveVoters(mailsToVoterIds, savedElection);
    }

    private void checkIfElectionWithTheSameNameAndAdminAlreadyDoesntExist(Election election) throws ElectionNameAlreadyTakenException {
        if (electionRepository.findByNameAndAdminUsername(
                election.getName(), election.getAdmin().getUsername()).isPresent()) {
            throw new ElectionNameAlreadyTakenException();
        }
    }

    private ElectionEntity saveElection(Election election) {
        return electionRepository.save(ElectionEntity.buildFromDto(election));
    }

    /*
     I don't know how to map a list element also using its index, had to use the standard approach.
     */
    private void saveAnswers(List<String> possibleAnswers, ElectionEntity election) {
        List<AnswerEntity> answerEntities = new ArrayList<>();
        for (int order = 0; order < possibleAnswers.size(); order++) {
            String answerText = possibleAnswers.get(order);
            answerEntities.add(new AnswerEntity(answerText, order, election));
        }
        answerRepository.save(answerEntities);
    }


    private void saveVoters(Map<String, String> mailsToVoterKeys, ElectionEntity savedElection) {
        List<VoterEntity> voterEntities = new ArrayList<>();
        mailsToVoterKeys.forEach(
                (mail, voterKey) -> voterEntities.add(new VoterEntity(mail, voterKey, savedElection)));
        voterRepository.save(voterEntities);
    }

    /*
     Fully functional, but uglier transformation. Think about possible improvements.
      */
    @Deprecated
    private void saveVoters2(Map<String, String> mailsToVoterIds, ElectionEntity savedElection) {
        List<VoterEntity> voterEntities = mailsToVoterIds.entrySet().stream().
                map(entry -> new VoterEntity(entry.getKey(), entry.getValue(), savedElection))
                .collect(Collectors.toList());
        voterRepository.save(voterEntities);
    }


}
