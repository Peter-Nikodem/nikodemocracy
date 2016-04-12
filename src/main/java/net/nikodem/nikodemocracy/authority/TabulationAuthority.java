package net.nikodem.nikodemocracy.authority;

import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.dto.Results;
import net.nikodem.nikodemocracy.model.dto.ResultsEmail;
import net.nikodem.nikodemocracy.model.dto.Vote;
import net.nikodem.nikodemocracy.model.exception.ElectionNotFoundException;
import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import net.nikodem.nikodemocracy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Peter Nikodem
 */
@Service
public class TabulationAuthority {

    private VoteService voteService;

    private ResultsService resultsService;

    private MailingService mailingService;

    private PresenceService presenceService;

    public void receiveElectionInformation(Election election, List<String> anonymizedVoterKeys) {
        createElectionPresenceTable(election, anonymizedVoterKeys);
    }

    private void createElectionPresenceTable(Election election, List<String> anonymizedVoterKeys) throws ElectionNotFoundException {
        voteService.createPresenceRecords(election, anonymizedVoterKeys);
    }

    public void receiveVote(Vote vote) {
        voteService.submitVote(vote);
        if (everyoneHasVoted(vote.getElection())){
            publishResultsOfFinishedElection(vote.getElection());
        }
    }

    private boolean everyoneHasVoted(Election election) {
        return presenceService.everyoneHasVoted(election);
    }

    public void publishResultsOfFinishedElection(Election election) {
        Results electionResults = resultsService.collectResults(election);
        for (String emailTo : election.getEmailsOfEligibleVoters()) {
            mailingService.sendEmail(new ResultsEmail(emailTo, electionResults));
        }
    }

    private ResultsEmail prepareResultsEmail(Results electionResults) {
        return null;
    }


    @Autowired
    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }

    @Autowired
    public void setResultsService(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    @Autowired
    public void setMailingService(MailingService mailingService) {
        this.mailingService = mailingService;
    }

}
