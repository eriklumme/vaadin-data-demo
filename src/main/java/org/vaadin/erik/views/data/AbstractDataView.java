package org.vaadin.erik.views.data;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.erik.data.IPerson;
import org.vaadin.erik.views.main.MainView;

import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("FieldCanBeLocal")
@Route(layout = MainView.class)
@CssImport("./styles/views/data-view.css")
public abstract class AbstractDataView<T extends IPerson> extends Div {

    private final Grid<T> grid = new Grid<>(getImplementationClass(), false);

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<T> binder;

    private T person;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phone;
    private DatePicker dateOfBirth;
    private TextField occupation;
    private Checkbox important;

    public AbstractDataView() {
        setId("data-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("phone").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);
        grid.addColumn("occupation").setAutoWidth(true);
        TemplateRenderer<T> importantRenderer = TemplateRenderer.<T>of(
                "<iron-icon hidden='[[!item.important]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.important]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("important", IPerson::isImportant);
        grid.addColumn(importantRenderer).setHeader("Important").setAutoWidth(true);

        grid.setItems(this::fetch);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<T> personFromBackend = get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (personFromBackend.isPresent()) {
                    populateForm(personFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(getImplementationClass());

        // Bind fields. This where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.person == null) {
                    this.person = instantiateEmpty();
                }
                binder.writeBean(this.person);

                update(this.person);
                clearForm();
                refreshGrid();
                Notification.show("Person details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the person details.");
            }
        });

    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        firstName = new TextField("First Name");
        lastName = new TextField("Last Name");
        email = new TextField("Email");
        phone = new TextField("Phone");
        dateOfBirth = new DatePicker("Date Of Birth");
        occupation = new TextField("Occupation");
        important = new Checkbox("Important");
        important.getStyle().set("padding-top", "var(--lumo-space-m)");
        Component[] fields = new Component[]{firstName, lastName, email, phone, dateOfBirth, occupation, important};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(T value) {
        this.person = value;
        binder.readBean(this.person);
    }

    abstract Class<T> getImplementationClass();

    abstract Stream<T> fetch(Query<T, Void> query);

    abstract Optional<T> get(Integer id);

    abstract void update(T person);

    abstract T instantiateEmpty();
}
