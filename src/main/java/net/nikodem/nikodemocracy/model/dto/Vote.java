package net.nikodem.nikodemocracy.model.dto;

/**
 * @author Peter Nikodem
 */
public class Vote {

    private final Election election;
    private final String voterKey;
    private final String voteKey;
    private final String chosenAnswer;

    public Vote(Election election, String voterKey, String voteKey, String chosenAnswer) {
        this.election = election;
        this.voterKey = voterKey;
        this.voteKey = voteKey;
        this.chosenAnswer = chosenAnswer;
    }

    public Election getElection() {
        return election;
    }

    public String getVoterKey() {
        return voterKey;
    }

    public String getVoteKey() {
        return voteKey;
    }

    public String getChosenAnswer() {
        return chosenAnswer;
    }
}
