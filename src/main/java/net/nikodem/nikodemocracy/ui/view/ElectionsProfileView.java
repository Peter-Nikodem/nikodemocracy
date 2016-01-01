package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.security.access.annotation.Secured;

/**
 * @author Peter Nikodem
 */
@Secured("ROLE_ELECTION_ADMIN")
@SpringView(name = ElectionsProfileView.NAME)
public class ElectionsProfileView extends AbstractView {
    public static final String NAME = "elections";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
