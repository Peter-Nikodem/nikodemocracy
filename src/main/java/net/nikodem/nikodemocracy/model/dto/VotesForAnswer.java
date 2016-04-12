package net.nikodem.nikodemocracy.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Peter Nikodem
 */
public class VotesForAnswer implements Comparable<VotesForAnswer> {

    private final String answer;
    private final List<String> voteKeys;
    private final int numberOfVotes;

    public VotesForAnswer(String answer, List<String> voteKeys) {
        this.answer = answer;
        this.voteKeys = sortAndMakeImmutable(voteKeys);
        this.numberOfVotes = voteKeys.size();
    }

    private List<String> sortAndMakeImmutable(List<String> voteKeys) {
        List<String> sortedVoteKeys = new ArrayList<>(voteKeys);
        Collections.sort(sortedVoteKeys);
        return Collections.unmodifiableList(sortedVoteKeys);
    }

    @Override
    public int compareTo(VotesForAnswer o) {
        return Integer.compare(numberOfVotes, o.numberOfVotes);
    }

    @Override
    public String toString(){
        return answer + " | " + numberOfVotes + " | " + voteKeys;
    }
}
