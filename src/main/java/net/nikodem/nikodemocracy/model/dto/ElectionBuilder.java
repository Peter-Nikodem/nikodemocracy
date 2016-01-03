package net.nikodem.nikodemocracy.model.dto;

import java.util.List;

/**
 * @author Peter Nikodem
 */
public class ElectionBuilder {
    String name;
    String question;
    List<String> possibleAnswers;
    List<String> emailsOfEligibleVoters;
    Admin electionAdmin;

    public static ElectionBuilder election() {
        return new ElectionBuilder();
    }


    public ElectionBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ElectionBuilder withQuestion(String polledQuestion) {
        this.question = polledQuestion;
        return this;
    }

    public ElectionBuilder withPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
        return this;
    }

    public ElectionBuilder withEmailsOfEligibleVoters(List<String> emailsOfEligibleVoters) {
        this.emailsOfEligibleVoters = emailsOfEligibleVoters;
        return this;
    }

    public ElectionBuilder withElectionAdmin(Admin electionAdmin) {
        this.electionAdmin = electionAdmin;
        return this;
    }

    public Election build() {
        return Election.buildFromBuilder(this);
    }


}
