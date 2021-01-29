package org.vaadin.erik.views.data.jdbc;

import com.vaadin.flow.router.PageTitle;
import org.vaadin.erik.data.dto.PersonDTO;
import org.vaadin.erik.data.dto.PhoneDTO;
import org.vaadin.erik.views.data.AbstractDataView;

@PageTitle("Data: JDBC")
public class JdbcDataView extends AbstractDataView<PersonDTO, PhoneDTO> {

    public JdbcDataView(JdbcDataPresenter presenter) {
        super(presenter);
    }
}
