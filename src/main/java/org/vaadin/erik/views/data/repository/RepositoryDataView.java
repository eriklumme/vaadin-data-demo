package org.vaadin.erik.views.data.repository;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import org.vaadin.erik.data.entity.Person;
import org.vaadin.erik.data.entity.Phone;
import org.vaadin.erik.views.data.AbstractDataView;
import org.vaadin.erik.views.main.MainView;

@PageTitle("Data: Spring Repository")
@RouteAlias(value = "", layout = MainView.class)
public class RepositoryDataView extends AbstractDataView<Person, Phone> {

    public RepositoryDataView(RepositoryDataPresenter presenter) {
        super(presenter);
    }
}
