package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import net.nikodem.nikodemocracy.ui.MainUI;

import javax.annotation.PreDestroy;

/**
 * @author Peter Nikodem
 */
public class AbstractView extends Panel implements View {

    private final VerticalLayout layout = new VerticalLayout();

    public AbstractView(){
        setContent(layout);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    protected void addComponent(Component c){
        layout.addComponent(c);
    }
}
