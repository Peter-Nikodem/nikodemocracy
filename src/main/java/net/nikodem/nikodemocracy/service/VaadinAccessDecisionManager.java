package net.nikodem.nikodemocracy.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Peter Nikodem
 */
@Component
public class VaadinAccessDecisionManager implements AccessDecisionManager {

    private AccessDecisionManager delegate;

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        try {
            if (objectHasNoAccessRestrictions(configAttributes)){
                return;
            }
            delegate.decide(authentication,o,configAttributes);
        } catch (AccessDeniedException ex){
            throw ex;
        } catch (InsufficientAuthenticationException ex){
            throw ex;
        }
    }

    private boolean objectHasNoAccessRestrictions(Collection<ConfigAttribute> configAttributes) {
        return configAttributes == null;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return delegate.supports(configAttribute);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return delegate.supports(aClass);
    }

    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager){
        this.delegate = accessDecisionManager;
    }
}
