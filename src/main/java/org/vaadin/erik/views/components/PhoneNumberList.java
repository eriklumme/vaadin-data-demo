package org.vaadin.erik.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

public class PhoneNumberList<PHONE> extends CustomField<List<PHONE>> implements HasStyle {

    private final VerticalLayout phoneNumberList = new VerticalLayout();
    private final HashMap<PHONE, Component> componentMap = new LinkedHashMap<>();
    private final Function<PHONE, String> phoneRenderer;

    public PhoneNumberList(String caption, Function<PHONE, String> phoneRenderer, Function<String, PHONE> instantiator) {
        this.phoneRenderer = phoneRenderer;

        getStyle().set("margin-top", "var(--lumo-space-m");
        setLabel(caption);

        TextField textField = new TextField();
        textField.setPlaceholder("Add phone number");

        Button addButton = new Button(VaadinIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_ICON);
        addButton.addClickListener(e -> {
            if (!textField.getValue().isEmpty()) {
                createRow(instantiator.apply(textField.getValue()));
                textField.clear();
                updateValue();
            }
        });

        phoneNumberList.setPadding(false);
        phoneNumberList.setSpacing(false);
        phoneNumberList.getStyle().set("padding-left", "var(--lumo-space-xs)");

        add(phoneNumberList, new HorizontalLayout(textField, addButton));
    }

    private void createRow(PHONE phone) {
        if (!componentMap.containsKey(phone)) {
            Button removeButton = new Button(VaadinIcon.CLOSE_SMALL.create());
            removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            removeButton.addClickListener(e -> {
                Component component = componentMap.get(phone);
                componentMap.remove(phone);
                phoneNumberList.remove(component);
                updateValue();
            });

            Div textDiv = new Div();
            textDiv.setWidthFull();
            textDiv.setText(phoneRenderer.apply(phone));
            HorizontalLayout component = new HorizontalLayout(textDiv, removeButton);
            component.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            component.setWidthFull();
            componentMap.put(phone, component);
            phoneNumberList.add(component);
        }
    }

    @Override
    protected List<PHONE> generateModelValue() {
        return new ArrayList<>(componentMap.keySet());
    }

    @Override
    protected void setPresentationValue(List<PHONE> phones) {
        try {
            componentMap.clear();
            phoneNumberList.removeAll();

            if (phones == null) {
                return;
            }

            for (PHONE phone: phones) {
                createRow(phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
