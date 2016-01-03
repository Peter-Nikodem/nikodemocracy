package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import net.nikodem.nikodemocracy.authority.RegistrationAuthority;
import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.dto.ElectionBuilder;
import net.nikodem.nikodemocracy.model.exception.ElectionNameAlreadyTakenException;
import net.nikodem.nikodemocracy.ui.MainUI;
import net.nikodem.nikodemocracy.ui.component.ElasticPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

/**
 * @author Peter Nikodem
 */
@Secured("ROLE_ELECTION_ADMIN")
@SpringView(name = CreateElectionView.NAME)
public class CreateElectionView extends AbstractView {

    @Autowired
    RegistrationAuthority registrationAuthority;

    public static final String NAME = "create";

    private TextField electionName = new TextField("Election name");
    private TextField polledQuestion = new TextField("Polled question");

    private ElasticPanel electionOptions = new ElasticPanel("Election options", new StringLengthValidator("Invalid length", 1, 20, false));
    private ElasticPanel eligibleVoters = new ElasticPanel("Eligible voters",new EmailValidator("Not a valid email"));
    private HorizontalLayout helpLayout = new HorizontalLayout();
    private VerticalLayout verticalLayout = new VerticalLayout();


    private Button startButton = new Button("Start election");


    private void tryToStartElection() {
        try {
            validate();
            startElection();
            navigateTo(ManageElectionsView.NAME);
        } catch (Validator.InvalidValueException|IllegalStateException|ElectionNameAlreadyTakenException ex) {
            displayCausesOfInvalidity();
        }
    }
    private void validate() throws Validator.InvalidValueException,IllegalStateException {
        electionName.validate();
        polledQuestion.validate();
        electionOptions.validate();
        eligibleVoters.validate();
    }

    private void startElection() throws ElectionNameAlreadyTakenException {
        Election election = ElectionBuilder.election()
                .withName(electionName.getValue())
                .withQuestion(polledQuestion.getValue())
                .withPossibleAnswers(electionOptions.getOptions())
                .withEmailsOfEligibleVoters(eligibleVoters.getOptions())
                .withElectionAdmin(MainUI.getCurrentUser())
                .build();

        registrationAuthority.registerElection(election);
    }

    private void displayCausesOfInvalidity() {
        Notification.show("Whoops", Notification.Type.WARNING_MESSAGE);
    }


    @Override
    protected void setComponentLayout() {
        verticalLayout.addComponent(electionName);
        verticalLayout.addComponent(polledQuestion);
        verticalLayout.addComponent(electionOptions);
        helpLayout.addComponent(verticalLayout);
        helpLayout.addComponent(eligibleVoters);
        addComponent(helpLayout);
        addComponent(startButton);
    }

    @Override
    protected void setBehaviour() {
        addValidation();
        startButton.addClickListener(e -> tryToStartElection());
    }

    private void addValidation() {
        electionName.addValidator(new StringLengthValidator("Invalid length", 1, 40, false));
        polledQuestion.addValidator(new StringLengthValidator("Invalid length", 1, 40, false));
    }


    @Override
    protected void setStyle() {
        helpLayout.setMargin(true);
        helpLayout.setSpacing(true);
        verticalLayout.setSpacing(true);

        startButton.setIcon(FontAwesome.THUMBS_UP);
        startButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }


}
