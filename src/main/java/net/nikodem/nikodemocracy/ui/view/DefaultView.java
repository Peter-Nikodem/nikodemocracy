package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import net.nikodem.nikodemocracy.ui.MainUI;

/**
 * @author Peter Nikodem
 */
@SpringView(name = "")
public class DefaultView extends Navigator.EmptyView {

    @Override
    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent){
        MainUI.getCurrent().getNavigator().navigateTo(MainView.NAME);
    }
}
