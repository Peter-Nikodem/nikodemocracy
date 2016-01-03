package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Link;
import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.service.ElectionService;
import net.nikodem.nikodemocracy.ui.MainUI;
import net.nikodem.nikodemocracy.ui.component.ElectionStatusButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Peter Nikodem
 */
@Secured("ROLE_ELECTION_ADMIN")
@SpringView(name = ManageElectionsView.NAME)
public class ManageElectionsView extends AbstractView {
    public static final String NAME = "manage";

    @Autowired
    private ElectionService electionService;

    private Link createNewElectionLink = createLink("Create new election", CreateElectionView.NAME);
    private Link goToMainMenuLink = createLink("Go to main menu", MainView.NAME);

    private List<Button> electionButtons;

    @Override
    protected void setComponentLayout() {
        addComponent(createNewElectionLink);
        addComponent(goToMainMenuLink);
    }

    @PostConstruct
    private void addElectionButtons() {
        List<Election> usersElections = electionService.findUsersElections(MainUI.getCurrentUser().getUsername());
        for (Election election : usersElections) {
            ElectionStatusButton button = new ElectionStatusButton(election);
            button.addClickListener(event -> {
                ElectionStatusButton b = (ElectionStatusButton) event.getButton();
                navigateTo(StopElectionView.getPath(b.getShortUrl()));
            });
            addComponent(button);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
