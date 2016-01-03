package net.nikodem.nikodemocracy.authority;

import net.nikodem.nikodemocracy.model.dto.Election;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Peter Nikodem
 */
@Service
public class TabulationAuthority {
    public void receiveElectionInformation(Election election, List<String> anonymizedVoterKeys) {

    }
}
