package net.nikodem.nikodemocracy.model.dto;

/**
 * @author Peter Nikodem
 */
public interface Mailable {
    String getTo();
    String getSubject();
    String getText();
}
