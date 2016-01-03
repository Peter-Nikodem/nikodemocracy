package net.nikodem.nikodemocracy.ui.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import net.nikodem.nikodemocracy.model.dto.Election;

/**
 * @author Peter Nikodem
 */
public class ElectionStatusButton extends Button {

    private final String name;
    private final String shortUrl;
    private final boolean isFinished;

    public ElectionStatusButton(Election election) {
        super(election.getName());
        this.name = election.getName();
        this.shortUrl = election.getShortUrl();
        this.isFinished = election.isFinished();
        setStyle();
    }

    private void setStyle() {
        setIcon(isFinished? FontAwesome.STOP: FontAwesome.PLAY);
        setWidth("250px");
        setHeight("90px");
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
