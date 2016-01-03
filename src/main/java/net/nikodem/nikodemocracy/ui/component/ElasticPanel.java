package net.nikodem.nikodemocracy.ui.component;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Peter Nikodem
 */
public class ElasticPanel extends VerticalLayout {
    private final String name;
    private final List<TextField> objects = new ArrayList<>();
    private final Button addOptionButton = new Button();
    private Validator fieldValidator = value -> {};


    public ElasticPanel(String name) {
        this.name = name;
        addComponent(new Label(name));
        addComponent(addOptionButton);
        addOption();
        addOption();
        addOptionButton.addClickListener((event -> addOption()));
        addOptionButton.setIcon(FontAwesome.PLUS);
    }

    public ElasticPanel(String name, Validator validator) {
        this(name);
        fieldValidator = validator;
    }

    private void addOption() {
        HorizontalLayout row = new HorizontalLayout();
        TextField textField = new TextField();
        textField.addValidator(fieldValidator);
        objects.add(textField);
        Button button = new Button();
        button.setIcon(FontAwesome.MINUS);
        button.addClickListener((event -> {
            objects.remove(textField);
            removeComponent(row);
        }));
        row.addComponent(textField);
        row.addComponent(button);
        addComponent(row);
    }

    public List<String> getOptions() {
        return objects.stream().
                map(o -> o.getValue()).
                filter(s -> s != null && !s.isEmpty()).
                collect(Collectors.toList());
    }


    public void validate() throws Validator.InvalidValueException{
        objects.forEach(tf -> tf.validate());
        if (getOptions().isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
