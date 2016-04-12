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
 * does this class do too much? SRP?
 *
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

    //@TODO this sucks, figure out a way how to remove the redundant parameter
    private String generateRandomVoterKey(String _) {
        return keyGenerationService.generateRandomAlphanumericString();
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
        return "http://localhost:8080/#!vote/" + shortUrl;
    }

    //TODO finishing election should be Tabulation authority's responsible
    public void stopElection(Election election) {
        markElectionAsFinished(election);
        informTabulationAuthorityToPublishResults(election);
    }

    private void markElectionAsFinished(Election election) {
        electionService.finishElection(election);
    }

    private void informTabulationAuthorityToPublishResults(Election election) {
        tabulationAuthority.publishResultsOfFinishedElection(election);
    }

    @Autowired
    void setMailingService(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @Autowired
    public void setKeyGenerationService(KeyGenerationService keyGenerationService) {
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
