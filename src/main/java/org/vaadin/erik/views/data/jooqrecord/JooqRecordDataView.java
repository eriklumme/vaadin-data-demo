package org.vaadin.erik.views.data.jooqrecord;

import com.vaadin.flow.router.PageTitle;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PersonRecord;
import org.vaadin.erik.views.data.AbstractDataView;

@PageTitle("Data: Jooq Record")
public class JooqRecordDataView extends AbstractDataView<PersonRecord> {

    public JooqRecordDataView(JooqRecordDataPresenter presenter) {
        super(presenter);
    }
}
