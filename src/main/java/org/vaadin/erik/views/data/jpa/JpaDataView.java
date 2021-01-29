package org.vaadin.erik.views.data.jpa;

import com.vaadin.flow.router.PageTitle;
import org.vaadin.erik.data.entity.Person;
import org.vaadin.erik.data.entity.Phone;
import org.vaadin.erik.views.data.AbstractDataView;

@PageTitle("Data: JPA")
public class JpaDataView extends AbstractDataView<Person, Phone> {

    public JpaDataView(JpaDataPresenter presenter) {
        super(presenter);
    }
}
