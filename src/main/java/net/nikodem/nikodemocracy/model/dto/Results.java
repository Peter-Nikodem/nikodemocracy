package net.nikodem.nikodemocracy.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Peter Nikodem
 */
public class Results {
    private final List<VotesForAnswer> votesForAnswers;
    private final Election election;

    public Results(Election election, List<VotesForAnswer> votesForAnswers) {
        this.election = election;
        this.votesForAnswers = sortByNumberOfVotesAndMakeImmutable(votesForAnswers);
    }

    private List<VotesForAnswer> sortByNumberOfVotesAndMakeImmutable(List<VotesForAnswer> votesForAnswers) {
        List<VotesForAnswer> list = new ArrayList<>(votesForAnswers);
        Collections.sort(list);
        return Collections.unmodifiableList(list);
    }

    public List<VotesForAnswer> getVotesForAnswers() {
        return votesForAnswers;
    }

    public Election getElection() {
        return election;
    }
}
