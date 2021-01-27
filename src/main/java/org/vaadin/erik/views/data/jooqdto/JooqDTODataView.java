package org.vaadin.erik.views.data.jooqdto;

import com.vaadin.flow.router.PageTitle;
import org.vaadin.erik.data.dto.PersonDTO;
import org.vaadin.erik.views.data.AbstractDataView;

@PageTitle("Data: Jooq DTO")
public class JooqDTODataView extends AbstractDataView<PersonDTO> {


    public JooqDTODataView(JooqDTODataPresenter presenter) {
        super(presenter);
    }

}
