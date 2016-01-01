package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import org.springframework.security.access.annotation.Secured;

/**
 * @author Peter Nikodem
 */
@Secured("ROLE_ELECTION_ADMIN")
@SpringView(name = CreateElectionView.NAME)
public class CreateElectionView extends AbstractView {
    public static final String NAME = "create";


    @Override
    protected void setComponentLayout() {
        addComponent(new Label("'Create new election' view"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }



}
