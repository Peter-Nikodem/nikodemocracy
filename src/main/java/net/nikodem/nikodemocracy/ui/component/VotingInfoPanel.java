package net.nikodem.nikodemocracy.ui.component;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import net.nikodem.nikodemocracy.model.dto.Election;

import java.util.List;

/**
 * @author Peter Nikodem
 */
public class VotingInfoPanel extends Panel {
    private final ComboBox options;
    private final Label name;
    private final Label question;

    public VotingInfoPanel(Election election){
        options = new ComboBox("Offered options");
        name = new Label("Election name: " + election.getName());
        question = new Label("Question: " + election.getQuestion());


        options.setNullSelectionAllowed(false);
        options.setInvalidAllowed(false);
        options.addItems(election.getPossibleAnswers());
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(name);
        layout.addComponent(question);
        layout.addComponent(options);
        setContent(layout);
    }

    public String getChosenOption(){
        return (String) options.getValue();
    }

    private Container createContainer(String header,List<String> items){
        Container container = new IndexedContainer();
        addHeader(container,header);
        items.forEach(item -> addItem(item, header,container));
        return container;
    }

    private void addItem(String itemData, String header, Container container) {
        Item item = container.getItem(container.addItem());
        System.out.println(itemData);
        item.getItemProperty(header).setValue(itemData);
    }

    private void addHeader(Container container, String header) {
        container.addContainerProperty(header, String.class, null);
    }

    public void validate() throws Validator.InvalidValueException {

    }
}
