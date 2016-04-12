package net.nikodem.nikodemocracy.service;

import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.repository.ElectionRepository;
import net.nikodem.nikodemocracy.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Peter Nikodem
 */
public class PresenceService {

    private PresenceRepository presenceRepository;
    private ElectionRepository electionRepository;

    public boolean everyoneHasVoted(Election election) {
        return presenceRepository.unvotedPresenceExists(election.getName(), election.getAdmin().getUsername());
    }

    @Autowired
    public void setPresenceRepository(PresenceRepository presenceRepository) {
        this.presenceRepository = presenceRepository;
    }
}
