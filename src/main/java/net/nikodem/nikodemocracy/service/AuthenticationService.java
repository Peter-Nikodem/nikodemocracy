package net.nikodem.nikodemocracy.service;

/**
 * @author Peter Nikodem
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public boolean loginSuccessfull(Authentication authenticationRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }
        catch (AuthenticationException ex){
            return false;
        }
    }
}
