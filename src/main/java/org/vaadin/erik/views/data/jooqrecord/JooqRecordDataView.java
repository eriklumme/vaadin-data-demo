package org.vaadin.erik.views.data.jooqrecord;

import com.vaadin.flow.router.PageTitle;
import org.vaadin.erik.data.dto.PersonProxy;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PhoneRecord;
import org.vaadin.erik.views.data.AbstractDataView;

@PageTitle("Data: Jooq Record")
public class JooqRecordDataView extends AbstractDataView<PersonProxy, PhoneRecord> {

    public JooqRecordDataView(JooqRecordDataPresenter presenter) {
        super(presenter);
    }
}
