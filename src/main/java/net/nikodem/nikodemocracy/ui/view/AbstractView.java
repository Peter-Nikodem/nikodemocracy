package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import net.nikodem.nikodemocracy.ui.MainUI;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Peter Nikodem
 */
public abstract class AbstractView extends Panel implements View {

    private final HorizontalLayout header = new HorizontalLayout();
    private final VerticalLayout mainLayout = new VerticalLayout();
    private final VerticalLayout layout = new VerticalLayout();
    private final Label mainLabel = new Label("<center><h1> Nikodemocracy </h1></center>", ContentMode.HTML);
    private final HorizontalLayout loginInfo = new HorizontalLayout();


    public AbstractView() {
        setSizeFull();
        buildHeader();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(header);
        layout.addComponent(loginInfo);
        layout.addComponent(mainLayout);
        setContent(layout);
    }

    @PostConstruct
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
    }

    protected void addComponent(Component component) {
        mainLayout.addComponent(component);
    }

    protected void registerWithEventbus() {
        MainUI.getCurrent().getEventBus().register(this);
    }

    @PreDestroy
    public void destroy() {
        MainUI.getCurrent().getEventBus().unregister(this);
    }


}
