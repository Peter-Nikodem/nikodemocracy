package net.nikodem.nikodemocracy.service;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import net.nikodem.nikodemocracy.ui.MainUI;
import net.nikodem.nikodemocracy.ui.event.NavigationEvent;
import net.nikodem.nikodemocracy.ui.view.AccessDeniedView;
import net.nikodem.nikodemocracy.ui.view.LoginView;
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
            if (MainUI.isCurrentUserAnonymous()){
                navigateTo(LoginView.loginPathForRequestedView(requestedView()));
            } else {
                navigateTo(AccessDeniedView.NAME);
            }
        }
    }

    /**
     * In other words, access Vaadin Spring view has no @Secured annotation.
     */
    private boolean objectHasNoAccessRestrictions(Collection<ConfigAttribute> configAttributes) {
        return configAttributes == null;
    }

    private String requestedView() {
        return MainUI.getCurrent().getNavigator().getState();
    }

    private void navigateTo(String viewName){
        EventBus eventBus = MainUI.getCurrent().getEventBus();
        eventBus.post(new NavigationEvent(this,viewName));
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
