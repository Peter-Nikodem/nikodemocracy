package net.nikodem.nikodemocracy.service;

import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.dto.Results;
import net.nikodem.nikodemocracy.model.dto.VotesForAnswer;
import net.nikodem.nikodemocracy.model.exception.ElectionNotFoundException;
import net.nikodem.nikodemocracy.model.jpa.AnswerEntity;
import net.nikodem.nikodemocracy.repository.AnswerRepository;
import net.nikodem.nikodemocracy.repository.ElectionRepository;
import net.nikodem.nikodemocracy.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Nikodem
 */
@Service
public class ResultsService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AnswerRepository answerRepository;


    public Results collectResults(Election election) {
        List<VotesForAnswer> votesForAnswers = new ArrayList<>();
        List<AnswerEntity> answerEntities = electionRepository.findByNameAndAdminUsername(election.getName(), election.getAdmin().getUsername())
                .map(e -> answerRepository.findByElectionOrderByAnswerOrder(e))
                .orElseThrow(ElectionNotFoundException::new);
        for (AnswerEntity answer : answerEntities) {
            votesForAnswers.add(
                    new VotesForAnswer(
                            answer.getAnswerText(),
                            voteRepository.findVoteKeyByAnswerOrderByVoteKey(answer)));
        }
        return new Results(election, votesForAnswers);
    }
}
