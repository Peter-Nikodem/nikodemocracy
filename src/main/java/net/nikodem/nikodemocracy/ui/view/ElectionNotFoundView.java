package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import org.springframework.security.access.annotation.Secured;

/**
 * @author Peter Nikodem
 */
@SpringView(name = ElectionNotFoundView.NAME)
public class ElectionNotFoundView extends AbstractView {
    public static final String NAME = "not-found";

    @Override
    protected void setComponentLayout() {
        addComponent(new Label("Election not found."));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
