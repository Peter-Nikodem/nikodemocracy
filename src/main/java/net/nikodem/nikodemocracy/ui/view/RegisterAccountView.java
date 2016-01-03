package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import net.nikodem.nikodemocracy.model.exception.UsernameAlreadyTakenException;
import net.nikodem.nikodemocracy.service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Peter Nikodem
 */
@SpringView(name = RegisterAccountView.NAME)
public class RegisterAccountView extends AbstractView {
    public static final String NAME = "register";

    private final AdminDetailsService userDetailsService;
    private final TextField usernameTF = new TextField("Username");
    private final TextField emailTF = new TextField("Email");
    private final PasswordField passwordTF = new PasswordField("Password");
    private final PasswordField repeatedPasswordTF = new PasswordField("Password again");
    private final Button registerButton = new Button("Register");
    private final Label infoLabel = new Label("Please enter your credentials");
    private String viewRequestedBeforeAuthentication;

    @Autowired
    public RegisterAccountView(AdminDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    void registerNewAccount() {
        if (inputIsValid()) {
            try {
                userDetailsService.registerNewUser(usernameTF.getValue(), emailTF.getValue(), passwordTF.getValue());
                Notification.show("New user account created!", Notification.Type.ASSISTIVE_NOTIFICATION);
                navigateTo(determineViewPath());
            } catch (UsernameAlreadyTakenException ex) {
                Notification.show("Username already taken", Notification.Type.WARNING_MESSAGE);
                usernameTF.setRequiredError("Please choose another username");
            }
        } else {
            Notification.show("Please correct your credentials", Notification.Type.WARNING_MESSAGE);
        }
    }

    private String determineViewPath() {
        return viewRequestedBeforeAuthentication.isEmpty()?MainView.NAME:LoginView.loginPathForRequestedView(viewRequestedBeforeAuthentication);
    }


    private boolean inputIsValid() {
        return usernameTF.isValid() && emailTF.isValid() && passwordTF.isValid() &&
                repeatedPasswordTF.getValue() != null && repeatedPasswordTF.getValue().equals(passwordTF.getValue());
    }


    @Override
    protected void setComponentLayout() {
        addComponent(infoLabel);
        addComponent(usernameTF);
        addComponent(emailTF);
        addComponent(passwordTF);
        addComponent(repeatedPasswordTF);
        addComponent(registerButton);
    }

    @Override
    protected void setBehaviour() {
        usernameTF.setRequired(true);
        emailTF.setRequired(true);
        passwordTF.setRequired(true);
        repeatedPasswordTF.setRequired(true);
        usernameTF.focus();
        usernameTF.addValidator(new StringLengthValidator("Username must be between 3 and 20 characters", 3, 20, false));
        emailTF.addValidator(new EmailValidator("Entered value must be a valid email address"));
        passwordTF.addValidator(new StringLengthValidator("Password must be between 7 and 30 characters", 7, 20, false));
        registerButton.addClickListener((event -> registerNewAccount()));
    }

    @Override
    protected void setStyle() {
        registerButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        registerButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        viewRequestedBeforeAuthentication = viewChangeEvent.getParameters();
    }


    public static String getPath(String requestedViewName) {
        return NAME + "/" + requestedViewName;
    }
}
