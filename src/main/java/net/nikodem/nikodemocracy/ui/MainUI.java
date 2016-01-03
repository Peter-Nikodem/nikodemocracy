package net.nikodem.nikodemocracy.ui;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import net.nikodem.nikodemocracy.model.dto.Admin;
import net.nikodem.nikodemocracy.service.VaadinAccessDecisionManager;
import net.nikodem.nikodemocracy.ui.event.NavigationEvent;
import net.nikodem.nikodemocracy.ui.view.AccessDeniedView;
import net.nikodem.nikodemocracy.ui.view.ErrorView;
import net.nikodem.nikodemocracy.ui.view.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.SimpleMethodInvocation;
import org.springframework.util.ReflectionUtils;

import java.util.Collection;

/**
 * Violates SRP. Split to more classes when it first becomes a problem.
 *
 * @author Peter Nikodem
 */
@SpringUI(path = "")
@Theme("valo")
@PreserveOnRefresh
public class MainUI extends UI {

    private final SpringViewProvider viewProvider;
    private final VaadinAccessDecisionManager vaadinAccessDecisionManager;
    private final EventBus eventBus;

    public static MainUI getCurrent() {
        return (MainUI) UI.getCurrent();
    }
    public static Admin getCurrentUser() {return getCurrent().getUser(); }
    public static boolean isCurrentUserAnonymous() {
        return MainUI.getCurrent().isUserAnonymous();
    }

    @Autowired
    public MainUI(SpringViewProvider viewProvider, VaadinAccessDecisionManager accessDecisionManager) {
        this.viewProvider = viewProvider;
        this.vaadinAccessDecisionManager = accessDecisionManager;
        eventBus = new EventBus("main");
        eventBus.register(this);
    }

    public Admin getUser() {
        return isUserAnonymous() ? null : ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public boolean isUserAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }

    // getUser() method

    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    protected void init(VaadinRequest request) {
        initErrorHandler();
        initNavigator();
        checkAccessRestrictionForRequestedView();
        setTitleForTheView();
    }

    private void initErrorHandler() {
        VaadinSession.getCurrent().setErrorHandler(new MainErrorHandler());
    }

    private void initNavigator() {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        setNavigator(navigator);
    }

    private void checkAccessRestrictionForRequestedView() {
        View targetView = viewProvider.getView(getNavigator().getState());
        if (targetView != null) {
            Collection<ConfigAttribute> attributes = retrieveViewsSecuredAnnotationAttributes(targetView);
            decideAccessPermission(targetView, attributes);
        }
    }

    private Collection<ConfigAttribute> retrieveViewsSecuredAnnotationAttributes(View targetView) {
        SecuredAnnotationSecurityMetadataSource metadataSource = new SecuredAnnotationSecurityMetadataSource();
        return metadataSource.getAttributes(
                new SimpleMethodInvocation(targetView,
                        ReflectionUtils.findMethod(View.class, "enter", ViewChangeListener.ViewChangeEvent.class)));
    }


    private void decideAccessPermission(View view, Collection<ConfigAttribute> attributes) {
        try {
            vaadinAccessDecisionManager.decide(SecurityContextHolder.getContext().getAuthentication(), view, attributes);
        } catch (AccessDeniedException adEx) {
            //Already handled internally by VaadinAccessDecisionManager. Catch enforced by method's signature.
        }
    }

    private void setTitleForTheView() {
        Page.getCurrent().setTitle("Nikodemocracy");
    }

    @Subscribe
    public void handleNavigation(NavigationEvent event) {
        getNavigator().navigateTo(event.getTarget());
    }


    private class MainErrorHandler implements ErrorHandler {

        @Override
        public void error(com.vaadin.server.ErrorEvent event) {
            if (accessWasDenied(event)){
                decideOnNavigation();
            }
        }

        private boolean accessWasDenied(com.vaadin.server.ErrorEvent event) {
            return event.getThrowable() instanceof AccessDeniedException ||
                    event.getThrowable().getCause() instanceof AccessDeniedException;
        }

        private void decideOnNavigation() {
            if (isCurrentUserAnonymous()) {
                if (!isAtLoginScreen()) {
                    navigateTo(LoginView.loginPathForRequestedView(getNavigator().getState()));
                }
            } else {
                navigateTo(AccessDeniedView.NAME);
            }
        }

        private boolean isAtLoginScreen() {
            return getNavigator().getState().startsWith(LoginView.NAME);
        }

        private void navigateTo(String viewName) {
            eventBus.post(new NavigationEvent(this, viewName));
        }

    }



}
