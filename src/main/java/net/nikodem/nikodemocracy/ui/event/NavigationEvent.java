package net.nikodem.nikodemocracy.ui.event;

import net.nikodem.nikodemocracy.ui.MainUI;

import java.util.EventObject;

/**
 * @author Peter Nikodem
 */
public class NavigationEvent extends EventObject {

    private String target;

    public NavigationEvent(Object source, String target) {
        super(source);
        this.target = target == null ? "" : target;
    }

    public String getTarget() {
        return target;
    }
}
