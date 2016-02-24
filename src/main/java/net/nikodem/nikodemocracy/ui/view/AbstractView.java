package net.nikodem.nikodemocracy.ui.view;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import net.nikodem.nikodemocracy.ui.MainUI;
import net.nikodem.nikodemocracy.ui.event.NavigationEvent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Peter Nikodem
 */
public abstract class AbstractView extends Panel implements View {

    private final HorizontalLayout header = new HorizontalLayout();
    private final VerticalLayout mainLayout = new VerticalLayout();
    private final VerticalLayout layout = new VerticalLayout();
    private final Label mainLabel = new Label("  Nikodemocracy " + FontAwesome.ENVELOPE_SQUARE.getHtml(), ContentMode.HTML);
    private final HorizontalLayout loginInfo = new HorizontalLayout();


    public AbstractView() {
        setSizeFull();
        buildHeader();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(header);
        layout.addComponent(loginInfo);
        layout.addComponent(mainLayout);

        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainLayout.setSpacing(true);
        mainLayout.setSpacing(true);
        setContent(layout);
    }

    protected void init(){
        setComponentLayout();
        setStyle();
        setBehaviour();
        registerWithEventbus();
    }

    protected void setComponentLayout(){}

    protected void setStyle() {}

    protected void setBehaviour() {}

    private void buildHeader() {
        header.addComponent(mainLabel);
        mainLabel.addStyleName(ValoTheme.LABEL_H1);
        header.setComponentAlignment(mainLabel,Alignment.MIDDLE_CENTER);
    }
    protected Link createLink(String register, String viewName) {
        Link link = new Link(register, new ExternalResource("#!" + viewName));
        link.addStyleName(ValoTheme.LINK_LARGE);
        return link;
    }

    protected void addComponent(Component component) {
        mainLayout.addComponent(component);
    }

    protected void navigateTo(String viewName) {
        EventBus eventbus = MainUI.getCurrent().getEventBus();
        eventbus.post(new NavigationEvent(this, viewName));
    }

    protected void registerWithEventbus() {
        MainUI.getCurrent().getEventBus().register(this);
    }

    @PreDestroy
    public void destroy() {
        MainUI.getCurrent().getEventBus().unregister(this);
    }


}
