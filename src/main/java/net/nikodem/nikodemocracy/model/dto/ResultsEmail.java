package net.nikodem.nikodemocracy.model.dto;

/**
 * @author Peter Nikodem
 */
public class ResultsEmail implements Mailable {

    private final String to;
    private final Results electionResults;


    public ResultsEmail(String emailTo, Results electionResults) {
        this.to = emailTo;
        this.electionResults = electionResults;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getSubject() {
        return electionResults.getElection().getName() + "  results!";
    }

    @Override
    public String getText() {
        return electionResults.getVotesForAnswers().toString();
    }
}
