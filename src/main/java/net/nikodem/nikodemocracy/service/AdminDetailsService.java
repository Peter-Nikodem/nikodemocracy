package net.nikodem.nikodemocracy.service;

import net.nikodem.nikodemocracy.model.dto.Admin;
import net.nikodem.nikodemocracy.model.exception.UsernameAlreadyTakenException;
import net.nikodem.nikodemocracy.model.jpa.AdminEntity;
import net.nikodem.nikodemocracy.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Peter Nikodem
 */
@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username).
                map(Admin::buildFromEntity).
                orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public void registerNewUser(String username, String email, String unencryptedPassword)
            throws UsernameAlreadyTakenException {
        if (adminRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyTakenException();
        }
        adminRepository.save(
                new AdminEntity(username, email, encryptPassword(unencryptedPassword)));

    }

    private String encryptPassword(String unencryptedPassword) {
        return passwordEncoder.encode(unencryptedPassword);
    }


}
