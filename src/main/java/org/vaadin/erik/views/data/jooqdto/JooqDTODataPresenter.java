package org.vaadin.erik.views.data.jooqdto;

import com.vaadin.flow.data.provider.Query;
import org.jooq.DSLContext;
import org.jooq.exception.DataChangedException;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.vaadin.erik.data.dto.PersonDTO;
import org.vaadin.erik.data.dto.PhoneDTO;
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
public class JooqDTODataPresenter implements DataPresenter<PersonDTO, PhoneDTO> {

    private final DSLContext dslContext;

    public JooqDTODataPresenter(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Class<PersonDTO> getImplementationClass() {
        return PersonDTO.class;
    }

    @Override
    public Stream<PersonDTO> fetch(Query<PersonDTO, Void> query) {
        List<PersonDTO> persons = dslContext.select()
                .from(Person.PERSON)
                .orderBy(JooqUtils.toOrderFields(query))
                .offset(query.getOffset())
                .limit(query.getLimit())
                .fetchInto(PersonDTO.class);
        List<Integer> ids = persons.stream().map(PersonDTO::getId).collect(Collectors.toList());
        List<PhoneDTO> phones = dslContext.select()
                .from(Phone.PHONE)
                .where(Phone.PHONE.PERSON_ID.in(ids))
                .fetchInto(PhoneDTO.class);

        persons.forEach(person -> person.setPhones(
                phones.stream()
                        .filter(phone -> phone.getPersonId().equals(person.getId()))
                        .collect(Collectors.toList())));
        return persons.stream();
    }

    @Override
    public Optional<PersonDTO> reload(PersonDTO person) {
        Optional<PersonDTO> optionalPerson = dslContext.select()
                .from(Person.PERSON)
                .where(Person.PERSON.ID.eq(person.getId()))
                .fetchOptionalInto(PersonDTO.class);
        optionalPerson.ifPresent(personDTO -> {
            List<PhoneDTO> phones = dslContext.select()
                    .from(Phone.PHONE)
                    .where(Phone.PHONE.PERSON_ID.eq(personDTO.getId()))
                    .fetchInto(PhoneDTO.class);
            personDTO.setPhones(phones);
        });
        return optionalPerson;
    }

    @Override
    public void updateOrInsert(PersonDTO person) {
        if (person.getId() == null) {
            PersonRecord personRecord = dslContext.newRecord(Person.PERSON, person);
            personRecord.insert();
            person.setId(personRecord.getId());
        } else {
            try {
                dslContext.newRecord(Person.PERSON, person).update();
                List<Integer> phoneIds = person.getPhones().stream()
                        .map(PhoneDTO::getId).collect(Collectors.toList());
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
        person.getPhones().forEach(phone -> {
            if (phone.getId() != null) {
                return;
            }
            phone.setPersonId(person.getId());
            dslContext.newRecord(Phone.PHONE, phone).insert();
        });
    }

    @Override
    public PersonDTO instantiateEmpty() {
        return new PersonDTO();
    }

    @Override
    public boolean isImportant(PersonDTO person) {
        return person.isImportant();
    }

    @Override
    public List<PhoneDTO> getPhones(PersonDTO person) {
        return person.getPhones();
    }

    @Override
    public void setPhones(PersonDTO personDTO, List<PhoneDTO> phoneDTOS) {
        personDTO.setPhones(phoneDTOS);
    }

    @Override
    public PhoneDTO createPhone(String phone) {
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setPhone(phone);
        return phoneDTO;
    }

    @Override
    public String getPhoneString(PhoneDTO phoneDTO) {
        return phoneDTO.getPhone();
    }
}
