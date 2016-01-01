package net.nikodem.nikodemocracy.service;

import net.nikodem.nikodemocracy.model.dto.CurrentUser;
import net.nikodem.nikodemocracy.model.exception.UsernameAlreadyTakenException;
import net.nikodem.nikodemocracy.model.jpa.ElectionAdmin;
import net.nikodem.nikodemocracy.repository.ElectionAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Peter Nikodem
 */
@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private final ElectionAdminRepository electionAdminRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public CurrentUserDetailsService(ElectionAdminRepository electionAdminRepository) {
        this.electionAdminRepository = electionAdminRepository;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return electionAdminRepository.findByUsername(username).
                map(CurrentUser::buildFromEntity).
                orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public void registerNewUser(String username, String email, String unencryptedPassword)
            throws UsernameAlreadyTakenException {
        if (electionAdminRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyTakenException();
        }
        electionAdminRepository.save(
                new ElectionAdmin(username, email, encryptPassword(unencryptedPassword)));

    }

    private String encryptPassword(String unencryptedPassword) {
        return passwordEncoder.encode(unencryptedPassword);
    }


}
