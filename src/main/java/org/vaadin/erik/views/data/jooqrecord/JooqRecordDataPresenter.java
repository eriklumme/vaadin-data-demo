package org.vaadin.erik.views.data.jooqrecord;

import com.vaadin.flow.data.provider.Query;
import org.jooq.DSLContext;
import org.jooq.exception.DataChangedException;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.vaadin.erik.data.dto.PersonProxy;
import org.vaadin.erik.data.generated.jooq.public_.tables.Person;
import org.vaadin.erik.data.generated.jooq.public_.tables.Phone;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PersonRecord;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PhoneRecord;
import org.vaadin.erik.utils.JooqUtils;
import org.vaadin.erik.views.data.DataPresenter;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JooqRecordDataPresenter implements DataPresenter<PersonProxy, PhoneRecord> {

    private final DSLContext dslContext;

    public JooqRecordDataPresenter(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Class<PersonProxy> getImplementationClass() {
        return PersonProxy.class;
    }

    @Override
    public Stream<PersonProxy> fetch(Query<PersonProxy, Void> query) {
        List<PersonRecord> personRecords = dslContext
                .selectFrom(Person.PERSON)
                .orderBy(JooqUtils.toOrderFields(query))
                .offset(query.getOffset())
                .limit(query.getLimit())
                .fetch();

        List<Integer> ids = personRecords.stream().map(PersonRecord::getId).collect(Collectors.toList());
        List<PhoneRecord> phoneRecords = dslContext
                .selectFrom(Phone.PHONE)
                .where(Phone.PHONE.PERSON_ID.in(ids))
                .fetch();
        return personRecords.stream().map(person -> {
            PersonProxy personProxy = new PersonProxy(person);
            personProxy.setPhoneRecords(phoneRecords.stream()
                    .filter(phone -> phone.getPersonId().equals(person.getId()))
                    .collect(Collectors.toList()));
            return personProxy;
        });
    }

    @Override
    public Optional<PersonProxy> reload(PersonProxy person) {
        return dslContext.selectFrom(Person.PERSON)
                .where(Person.PERSON.ID.eq(person.getId()))
                .fetchOptional()
                .map(personRecord -> {
                    PersonProxy personProxy = new PersonProxy(personRecord);
                    List<PhoneRecord> phoneRecords = dslContext.selectFrom(Phone.PHONE)
                            .where(Phone.PHONE.PERSON_ID.eq(personRecord.getId()))
                            .fetchStream().collect(Collectors.toList());
                    personProxy.setPhoneRecords(phoneRecords);
                    return personProxy;
                });
    }

    @Override
    public void updateOrInsert(PersonProxy person) {
        if (person.getId() == null) {
            person.getPersonRecord().attach(dslContext.configuration());
            person.getPersonRecord().insert();
        } else {
            try {
                person.getPersonRecord().update();
                List<Integer> phoneIds = person.getPhoneRecords().stream()
                        .map(PhoneRecord::getId).collect(Collectors.toList());
                dslContext.deleteFrom(Phone.PHONE)
                        .where(DSL.and(
                            Phone.PHONE.PERSON_ID.eq(person.getId()),
                            Phone.PHONE.ID.notIn(phoneIds)
                        ))
                        .execute();
            } catch (DataChangedException e) {
                throw new OptimisticLockException(e);
            }
        }
        person.getPhoneRecords().forEach(phoneRecord -> {
            if (phoneRecord.getId() != null) {
                return;
            }
            phoneRecord.attach(dslContext.configuration());
            phoneRecord.setPersonId(person.getId());
            phoneRecord.insert();
        });
    }

    @Override
    public PersonProxy instantiateEmpty() {
        return new PersonProxy(new PersonRecord());
    }

    @Override
    public boolean isImportant(PersonProxy personProxy) {
        return personProxy.isImportant();
    }

    @Override
    public List<PhoneRecord> getPhones(PersonProxy personProxy) {
        return personProxy.getPhoneRecords();
    }

    @Override
    public void setPhones(PersonProxy personProxy, List<PhoneRecord> phoneRecords) {
        personProxy.setPhoneRecords(phoneRecords);
    }

    @Override
    public PhoneRecord createPhone(String phone) {
        PhoneRecord phoneRecord = new PhoneRecord();
        phoneRecord.setPhone(phone);
        return phoneRecord;
    }

    @Override
    public String getPhoneString(PhoneRecord phoneRecord) {
        return phoneRecord.getPhone();
    }
}
