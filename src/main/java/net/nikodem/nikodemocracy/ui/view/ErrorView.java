package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import javax.annotation.PostConstruct;

/**
 * @author Peter Nikodem
 */
@SpringView(name = ErrorView.NAME)
public class ErrorView extends AbstractView {

    public static final String NAME = "error";

    private ObjectProperty<String> errorMessage;

    public ErrorView() {
        errorMessage = new ObjectProperty<>("");
        Label errorMessageLabel = new Label(errorMessage, ContentMode.HTML);
        addComponent(errorMessageLabel);
        addComponent(new Link("Go back to the main page",new ExternalResource("#!" + MainView.NAME)));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        errorMessage.setValue("<h1> Page " + viewChangeEvent.getViewName() + " not found! </h1> ");
    }
}
