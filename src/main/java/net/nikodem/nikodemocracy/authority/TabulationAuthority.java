package net.nikodem.nikodemocracy.authority;

import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.dto.Vote;
import net.nikodem.nikodemocracy.model.exception.ElectionNotFoundException;
import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import net.nikodem.nikodemocracy.service.ElectionService;
import net.nikodem.nikodemocracy.service.ResultsService;
import net.nikodem.nikodemocracy.service.VoteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Peter Nikodem
 */
@Service
public class TabulationAuthority {

    private VoteService voteService;

    private ResultsService resultsService;

    public void receiveElectionInformation(Election election, List<String> anonymizedVoterKeys) {
        createElectionPresenceTable(election, anonymizedVoterKeys);
    }

    private void createElectionPresenceTable(Election election, List<String> anonymizedVoterKeys) throws ElectionNotFoundException {
        voteService.createPresenceRecords(election, anonymizedVoterKeys);
    }

    public void receiveVote(Vote vote) {
        voteService.submitVote(vote);
    }

    public void publishResultsOfFinishedElection(Election election) {

    }
}
