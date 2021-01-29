package org.vaadin.erik.views.data.jooqdto;

import com.vaadin.flow.router.PageTitle;
import org.vaadin.erik.data.dto.PersonDTO;
import org.vaadin.erik.data.dto.PhoneDTO;
import org.vaadin.erik.views.data.AbstractDataView;

@PageTitle("Data: Jooq DTO")
public class JooqDTODataView extends AbstractDataView<PersonDTO, PhoneDTO> {


    public JooqDTODataView(JooqDTODataPresenter presenter) {
        super(presenter);
    }

}
