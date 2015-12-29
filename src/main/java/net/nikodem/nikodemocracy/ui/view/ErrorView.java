package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

/**
 * @author Peter Nikodem
 */
@SpringView(name = ErrorView.NAME)
public class ErrorView extends AbstractView {

    public static final String NAME = "error";

    @PostConstruct
    private void init() {
        addComponent(new Label("Something went wrong, try monarchy instead."));
    }
}
