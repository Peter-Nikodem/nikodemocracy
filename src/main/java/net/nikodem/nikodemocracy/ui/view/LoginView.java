package net.nikodem.nikodemocracy.ui.view;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
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
    private Button registerButton = new Button("Register");
    private Button goToMainMenuButton = new Button("Go to main menu");
    private HorizontalLayout buttonGroup = new HorizontalLayout();

    @Override
    protected void setComponentLayout() {
        addComponent(nameTF);
        addComponent(passwordTF);
        buttonGroup.addComponent(loginButton);
        buttonGroup.addComponent(registerButton);
//        buttonGroup.addComponent(goToMainMenuButton);
        addComponent(buttonGroup);
    }

    @Override
    protected void setBehaviour() {
        nameTF.setRequired(true);
        nameTF.focus();
        passwordTF.setRequired(true);

        loginButton.addClickListener((event -> tryToLogin()));
        registerButton.addClickListener((event -> navigateTo(RegisterAccountView.getPath(viewRequestedBeforeAuthentication))));
    }

    @Override
    protected void setStyle(){
        buttonGroup.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        loginButton.setIcon(FontAwesome.SIGN_IN);
        registerButton.setIcon(FontAwesome.ARCHIVE);
        goToMainMenuButton.setIcon(FontAwesome.ARROW_LEFT);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
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
            if (nameTF.isEmpty()) {
                nameTF.setRequiredError("Please enter your username");
            }
            if (passwordTF.isEmpty()) {
                passwordTF.setRequiredError("Please enter your password");
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        viewRequestedBeforeAuthentication = viewChangeEvent.getParameters();
    }


}
