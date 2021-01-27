package org.vaadin.erik.views.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import org.jooq.DSLContext;
import org.vaadin.erik.data.generated.jooq.public_.tables.Person;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PersonRecord;

import java.util.Optional;
import java.util.stream.Stream;

@PageTitle("Data: Jooq Record")
public class JooqRecordDataView extends AbstractDataView<PersonRecord> {

    private final DSLContext dslContext;

    public JooqRecordDataView(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    Class<PersonRecord> getImplementationClass() {
        return PersonRecord.class;
    }

    @Override
    Stream<PersonRecord> fetch(Query<PersonRecord, Void> query) {
        return dslContext.selectFrom(Person.PERSON)
                .offset(query.getOffset())
                .limit(query.getLimit())
                .fetchStream();
    }

    @Override
    Optional<PersonRecord> reload(PersonRecord person) {
        return dslContext.selectFrom(Person.PERSON)
                .where(Person.PERSON.ID.eq(person.getId()))
                .fetchOptional();
    }

    @Override
    void update(PersonRecord person) {
        person.update();
    }

    @Override
    PersonRecord instantiateEmpty() {
        return new PersonRecord();
    }

    @Override
    boolean isImportant(PersonRecord person) {
        return person.getImportant();
    }
}
