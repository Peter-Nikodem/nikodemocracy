package net.nikodem.nikodemocracy.ui;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import net.nikodem.nikodemocracy.service.VaadinAccessDecisionManager;
import net.nikodem.nikodemocracy.ui.view.ErrorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Peter Nikodem
 */
@SpringUI(path = "")
@Theme("valo")
@PreserveOnRefresh
public class MainUI extends UI {

    private final SpringViewProvider viewProvider;
    private final AuthenticationManager authenticationManager;
    private final VaadinAccessDecisionManager accessDecisionManager;

    public static MainUI getCurrent() {
        return (MainUI) UI.getCurrent();
    }

    @Autowired
    public MainUI(SpringViewProvider viewProvider, AuthenticationManager authenticationManager, VaadinAccessDecisionManager accessDecisionManager) {
        this.viewProvider = viewProvider;
        this.authenticationManager = authenticationManager;
        this.accessDecisionManager = accessDecisionManager;
    }

    @Override
    protected void init(VaadinRequest request) {
        initNavigator();
        checkAccessRestrictionForRequestedView();
        setTitleForTheView();
    }

    private void initNavigator() {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        setNavigator(navigator);
    }

    private void checkAccessRestrictionForRequestedView() {
        //what could possible go wrong here?
    }

    private void setTitleForTheView() {
        Page.getCurrent().setTitle("Nikodemocracy");
    }

    

}
