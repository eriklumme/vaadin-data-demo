package org.vaadin.erik.views.data.jooqrecord;

import com.vaadin.flow.data.provider.Query;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.vaadin.erik.data.generated.jooq.public_.tables.Person;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PersonRecord;
import org.vaadin.erik.views.data.DataPresenter;
import org.vaadin.erik.views.data.JooqUtils;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class JooqRecordDataPresenter implements DataPresenter<PersonRecord> {

    private final DSLContext dslContext;

    public JooqRecordDataPresenter(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Class<PersonRecord> getImplementationClass() {
        return PersonRecord.class;
    }

    @Override
    public Stream<PersonRecord> fetch(Query<PersonRecord, Void> query) {
        return dslContext.selectFrom(Person.PERSON)
                .orderBy(JooqUtils.toOrderFields(query))
                .offset(query.getOffset())
                .limit(query.getLimit())
                .fetchStream();
    }

    @Override
    public Optional<PersonRecord> reload(PersonRecord person) {
        return dslContext.selectFrom(Person.PERSON)
                .where(Person.PERSON.ID.eq(person.getId()))
                .fetchOptional();
    }

    @Override
    public void updateOrInsert(PersonRecord person) {
        if (person.getId() == null) {
            person.insert();
        } else {
            person.update();
        }
    }

    @Override
    public PersonRecord instantiateEmpty() {
        return new PersonRecord();
    }

    @Override
    public boolean isImportant(PersonRecord person) {
        return person.getImportant();
    }
}
