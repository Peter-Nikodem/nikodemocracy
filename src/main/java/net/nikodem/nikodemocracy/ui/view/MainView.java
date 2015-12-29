package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import sun.applet.Main;

import javax.annotation.PostConstruct;

/**
 * @author Peter Nikodem
 */
@SpringView(name = MainView.NAME)
public class MainView extends AbstractView{
    public static final String NAME = "main";

    @PostConstruct
    private void init(){
        addComponent(new Label("“What shall we say about those spectators, then, who can see a plurality of beautiful things, but not beauty itself, and who are incapable of following if someone else tries to lead them to it, and who can see many moral actions, but not morality itself, and so on? That they only ever entertain beliefs, and do not know any of the things they believe?” "));
    }

}
