package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Link;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Peter Nikodem
 */
@SpringView(name = MainView.NAME)
public class MainView extends AbstractView{
    public static final String NAME = "main";
    private final Link createElectionLink = createLink("Create new election",CreateElectionView.NAME);
    private final Link manageElectionLink = createLink("Manage election", ManageElectionsView.NAME);
    private final Link registerAccountLink = createLink("Register new account",RegisterAccountView.NAME);



    @Override
    protected void setComponentLayout() {
        addComponent(createElectionLink);
        addComponent(manageElectionLink);
        addComponent(registerAccountLink);
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }


}
