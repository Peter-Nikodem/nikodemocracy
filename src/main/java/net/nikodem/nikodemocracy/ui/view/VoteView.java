package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import net.nikodem.nikodemocracy.authority.TabulationAuthority;
import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.dto.Vote;
import net.nikodem.nikodemocracy.model.exception.ElectionNotFoundException;
import net.nikodem.nikodemocracy.model.exception.VoterKeyAlreadyUsedException;
import net.nikodem.nikodemocracy.model.exception.VoterKeyNotFoundException;
import net.nikodem.nikodemocracy.service.ElectionService;
import net.nikodem.nikodemocracy.service.KeyGenerationService;
import net.nikodem.nikodemocracy.ui.component.VotingInfoPanel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Peter Nikodem
 */
@SpringView(name = VoteView.NAME)
public class VoteView extends AbstractView {

    public static final String NAME = "vote";
    private static final Integer KEY_LENGTH = 16;
    private final TabulationAuthority tabulationAuthority;

    private final ElectionService electionService;

    private final KeyGenerationService keyGenerationService;

    private VotingInfoPanel votingInfoPanel;
    private TextField voterKeyTf = new TextField("Voter key");
    private TextField voteKeyTf = new TextField("Vote key");
    private Button generateVoteKeyButton = new Button("Generate vote key");
    private Button voteButton = new Button("Vote");
    private VerticalLayout voteKeyLayout = new VerticalLayout();

    private Election election;

    @Autowired
    public VoteView(TabulationAuthority tabulationAuthority, ElectionService electionService, KeyGenerationService keyGenerationService) {
        this.tabulationAuthority = tabulationAuthority;
        this.electionService = electionService;
        this.keyGenerationService = keyGenerationService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            retrieveElectionFromUrlParameters(event);
            prepareComponents();
            placeComponents();
        } catch (ElectionNotFoundException ex) {
            navigateTo(ElectionNotFoundView.NAME);
        }
    }

    private void retrieveElectionFromUrlParameters(ViewChangeListener.ViewChangeEvent event) {
        election = electionService.findElectionByShortUrl(event.getParameters());
    }

    private void prepareComponents() {
        votingInfoPanel = new VotingInfoPanel(election);

        generateVoteKeyButton.addClickListener(e -> generateVoteKey());
        voteButton.addClickListener(e -> tryToVote());

        voteKeyTf.setReadOnly(true);

        voterKeyTf.addValidator(new StringLengthValidator("Invalid voter key length", KEY_LENGTH, KEY_LENGTH, false));
        voteKeyTf.addValidator(new StringLengthValidator("Please generate vote key", KEY_LENGTH, KEY_LENGTH, false));

        voteKeyLayout.setSpacing(true);
        voteKeyLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    }

    private void placeComponents() {
        voteKeyLayout.addComponent(voteKeyTf);
        voteKeyLayout.addComponent(generateVoteKeyButton);

        addComponent(votingInfoPanel);
        addComponent(voterKeyTf);
        addComponent(voteKeyLayout);
        addComponent(voteButton);
    }

    private void generateVoteKey() {
        voteKeyTf.setReadOnly(false);
        voteKeyTf.setValue(keyGenerationService.randomAlphanumericString());
        voteKeyTf.setReadOnly(true);
    }

    private void tryToVote() {
        try {
            validate();
            vote();
            displayVoteSuccessfulMessage();
        } catch (Validator.InvalidValueException ex) {
            displayInvalidInputMessage();
        } catch (VoterKeyNotFoundException ex2) {
            displayInvalidVoterKeyMessage();
        } catch (VoterKeyAlreadyUsedException ex3) {
            displayAlreadyUsedVoterKeyMessage();
        }
    }

    private void displayAlreadyUsedVoterKeyMessage() {
        Notification.show("This voter key has already been used!", Notification.Type.WARNING_MESSAGE);
    }

    private void displayInvalidVoterKeyMessage() {
        Notification.show("Invalid voter key! Please check the election invitation email again.",
                Notification.Type.WARNING_MESSAGE);
    }

    private void displayInvalidInputMessage() {
        Notification.show("Invalid input!", Notification.Type.WARNING_MESSAGE);
    }

    private void vote() {
        tabulationAuthority.receiveVote(getVote());
    }

    private Vote getVote() {
        return new Vote(election, voterKeyTf.getValue(), voteKeyTf.getValue(), votingInfoPanel.getChosenOption());
    }

    private void validate() {
        voteKeyTf.validate();
        voterKeyTf.validate();
        votingInfoPanel.validate();
    }

    private void displayVoteSuccessfulMessage() {
        Notification.show("Vote successful!\n " +
                        " We will send you the election results to your email.\n" +
                        "Please keep your vote key if you want to ensure your vote has been counted",
                Notification.Type.ASSISTIVE_NOTIFICATION);
    }


}
