package net.nikodem.nikodemocracy.ui.view;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import net.nikodem.nikodemocracy.service.AuthenticationService;
import net.nikodem.nikodemocracy.ui.MainUI;
import net.nikodem.nikodemocracy.ui.event.NavigationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.annotation.PostConstruct;

/**
 * @author Peter Nikodem
 */
@SpringView(name = LoginView.NAME)
public class LoginView extends AbstractView {
    public static final String NAME = "login";

    public static String loginPathForRequestedView(String requestedViewName) {
        return NAME + "/" + requestedViewName;
    }

    @Autowired
    private AuthenticationService authenticationService;

    private String viewRequestedBeforeAuthentication;
    private TextField nameTF = new TextField("Username");
    private PasswordField passwordTF = new PasswordField("Password");
    private Button loginButton = new Button("Login");

    @Override
    protected void setComponentLayout() {
        addComponent(nameTF);
        addComponent(passwordTF);
        addComponent(loginButton);
    }

    @Override
    protected void setBehaviour() {
        nameTF.setRequired(true);
        nameTF.focus();
        passwordTF.setRequired(true);

        loginButton.addClickListener((event -> tryToLogin()));
    }

    @Override
    protected void setStyle(){
        loginButton.setIcon(FontAwesome.SIGN_IN);
    }

    /**
     * @refactor
     */
    private void tryToLogin(){
        if (nameTF.isValid() && passwordTF.isValid()) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(nameTF.getValue(), passwordTF.getValue());
            if (authenticationService.loginSuccessfull(authentication)) {
                navigateTo(viewRequestedBeforeAuthentication);
            } else {
                passwordTF.clear();
            }
        } else {
            System.out.println("not valid");
            if (nameTF.isEmpty()) {
                nameTF.setRequiredError("Please enter your username");
            }
            if (passwordTF.isEmpty()) {
                passwordTF.setRequiredError("Please enter your password");
            }
        }
    }

    private void navigateTo(String viewName) {
        EventBus eventbus = MainUI.getCurrent().getEventBus();
        eventbus.post(new NavigationEvent(this, viewName));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        viewRequestedBeforeAuthentication = viewChangeEvent.getParameters();
    }


}
