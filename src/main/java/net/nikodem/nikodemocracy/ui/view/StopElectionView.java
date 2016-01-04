package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import net.nikodem.nikodemocracy.authority.RegistrationAuthority;
import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.exception.ElectionNotFoundException;
import net.nikodem.nikodemocracy.service.ElectionService;
import net.nikodem.nikodemocracy.ui.component.ElectionInfoPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;

/**
 * @author Peter Nikodem
 */
@Secured("ROLE_ELECTION_ADMIN")
@SpringView(name = StopElectionView.NAME)
public class StopElectionView extends AbstractView {
    public static final String NAME = "stop";

    @Autowired
    private ElectionService electionService;

    @Autowired
    private RegistrationAuthority registrationAuthority;

    private final Button stopButton = new Button("Stop");

    private ElectionInfoPanel electionInfo;

    private Election election;


    public static String getPath(String shortUrl) {
        return NAME + "/" + shortUrl;
    }

    @Override
    protected void setComponentLayout() {
    }

    @Override
    protected void setBehaviour() {
        stopButton.addClickListener(event -> stopElection());
    }

    private void stopElection() {
        registrationAuthority.stopElection(election);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            election = electionService.findElectionByShortUrl(event.getParameters());
            electionInfo = new ElectionInfoPanel(election);
            addComponent(electionInfo);
            addComponent(stopButton);
        } catch (ElectionNotFoundException ex) {
            navigateTo(ElectionNotFoundView.NAME);
        }
    }
}
