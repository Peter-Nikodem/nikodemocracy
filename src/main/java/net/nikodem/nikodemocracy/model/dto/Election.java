package net.nikodem.nikodemocracy.model.dto;

import net.nikodem.nikodemocracy.service.KeyGenerationService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Peter Nikodem
 */
public class Election {

    @NotNull
    private final String name;

    @NotNull
    private final String question;

    @NotNull
    private final List<String> possibleAnswers;

    @NotNull
    private final List<String> emailsOfEligibleVoters;

    @NotNull
    private final Admin admin;

    private final String shortUrl;

    private Election(String name, String question, List<String> possibleAnswers, List<String> emailsOfEligibleVoters, Admin admin) {
        this.name = name;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.emailsOfEligibleVoters = emailsOfEligibleVoters;
        this.admin = admin;
        this.shortUrl = KeyGenerationService.generateElectionUrl(admin.getUsername(),name);
    }

    static Election buildFromBuilder(ElectionBuilder b) {
        return new Election(b.name, b.question, b.possibleAnswers, b.emailsOfEligibleVoters, b.electionAdmin);
    }

    public String getName() {
        return name;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public List<String> getEmailsOfEligibleVoters() {
        return emailsOfEligibleVoters;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public Admin getAdmin() {
        return admin;
    }
}
