package net.nikodem.nikodemocracy.ui.component;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import net.nikodem.nikodemocracy.model.dto.Election;

import java.util.List;


/**
 * @author Peter Nikodem
 */
public class ElectionInfoPanel extends Panel {
    private final Table votersTable = new Table();
    private final Table answersTable = new Table();
    private final Label name;
    private final Label question;

    private final HorizontalLayout tablesLayout = new HorizontalLayout();
    private final HorizontalLayout mainLayout = new HorizontalLayout();
    private final VerticalLayout labelsLayout = new VerticalLayout();

    private final Election election;


    public ElectionInfoPanel(Election election) {
        this.election = election;

        votersTable.setContainerDataSource(createContainer("Invited voters",election.getEmailsOfEligibleVoters()));

        answersTable.setContainerDataSource(createContainer("Possible answers",election.getPossibleAnswers()));

        name = new Label("Election name: " + election.getName());
        question = new Label("Polled question: " + election.getQuestion());

        labelsLayout.addComponent(name);
        labelsLayout.addComponent(question);

        labelsLayout.setSpacing(true);

        tablesLayout.addComponent(answersTable);
        tablesLayout.addComponent(votersTable);
        labelsLayout.setSpacing(true);
        labelsLayout.setMargin(true);
        mainLayout.addComponent(labelsLayout);
        mainLayout.addComponent(tablesLayout);
        setContent(mainLayout);
    }

    private Container createContainer(String header,List<String> items){
        Container container = new IndexedContainer();
        addHeader(container,header);
        items.forEach(item -> addItem(item, header,container));
        return container;
    }

    private void addItem(String itemData, String header, Container container) {
        Item item = container.getItem(container.addItem());
        item.getItemProperty(header).setValue(itemData);
    }

    private void addHeader(Container container, String header) {
        container.addContainerProperty(header, String.class, null);
    }




}
