package net.nikodem.nikodemocracy.authority;

import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.dto.InvitationEmail;
import net.nikodem.nikodemocracy.model.exception.ElectionNameAlreadyTakenException;
import net.nikodem.nikodemocracy.service.ElectionService;
import net.nikodem.nikodemocracy.service.KeyGenerationService;
import net.nikodem.nikodemocracy.service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Peter Nikodem
 */
@Service
public class RegistrationAuthority {


    private ElectionService electionService;
    private MailingService mailingService;
    private KeyGenerationService keyGenerationService;
    private TabulationAuthority tabulationAuthority;

    public void registerElection(Election election) {
        Map<String, String> mailsToVoterIds = generateVoterIds(election);
        saveElection(election, mailsToVoterIds);
        sendInvitationEmails(mailsToVoterIds, election);
        notifyTabulationAuthority(election, anonymize(mailsToVoterIds));
    }

    private Map<String, String> generateVoterIds(Election election) {
        return election.
                getEmailsOfEligibleVoters().
                stream().
                collect(Collectors.toMap(
                        Function.identity(),
                        this::generateRandomVoterKey
                ));
    }

    private String generateRandomVoterKey(String _) {
        return keyGenerationService.randomAlphanumericString();
    }

    private void saveElection(Election election, Map<String, String> mailsToVoterIds) throws ElectionNameAlreadyTakenException {
        electionService.createElection(election, mailsToVoterIds);
    }

    private void sendInvitationEmails(Map<String, String> emailsToVoterIds, Election election) {
        String electionUrl = generateFullUrl(election.getShortUrl());
        emailsToVoterIds.forEach((email, voterId) ->
                mailingService.sendEmail(
                        new InvitationEmail(email, voterId, election.getName(), electionUrl)));
    }

    private void notifyTabulationAuthority(Election election, List<String> anonymizedVoterKeys) {
        tabulationAuthority.receiveElectionInformation(election, anonymizedVoterKeys);
    }

    private List<String> anonymize(Map<String, String> mailsToVoterIds) {
        return mailsToVoterIds.values().stream().
                sorted().
                collect(Collectors.toList());
    }

    private String generateFullUrl(String shortUrl) {
        return "localhost:8080/election/" + shortUrl;
    }

    public void stopElection(Election election) {

    }

    @Autowired
    void setMailingService(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @Autowired
    void setKeyGenerationService(KeyGenerationService keyGenerationService) {
        this.keyGenerationService = keyGenerationService;
    }

    @Autowired
    void setElectionService(ElectionService electionService) {
        this.electionService = electionService;
    }

    @Autowired
    void setTabulationAuthority(TabulationAuthority tabulationAuthority) {
        this.tabulationAuthority = tabulationAuthority;
    }
}
