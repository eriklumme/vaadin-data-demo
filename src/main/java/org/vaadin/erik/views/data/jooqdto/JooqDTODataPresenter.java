package org.vaadin.erik.views.data.jooqdto;

import com.vaadin.flow.data.provider.Query;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.vaadin.erik.data.dto.PersonDTO;
import org.vaadin.erik.data.generated.jooq.public_.tables.Person;
import org.vaadin.erik.views.data.DataPresenter;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class JooqDTODataPresenter implements DataPresenter<PersonDTO> {

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
        return dslContext.select()
                .from(Person.PERSON)
                .offset(query.getOffset())
                .limit(query.getLimit())
                .fetchStreamInto(PersonDTO.class);
    }

    @Override
    public Optional<PersonDTO> reload(PersonDTO person) {
        return dslContext.select()
                .from(Person.PERSON)
                .where(Person.PERSON.ID.eq(person.getId()))
                .fetchOptionalInto(PersonDTO.class);
    }

    @Override
    public void updateOrInsert(PersonDTO person) {
        if (person.getId() == null) {
            dslContext.newRecord(Person.PERSON, person).insert();
        } else {
            dslContext.newRecord(Person.PERSON, person).update();
        }
    }

    @Override
    public PersonDTO instantiateEmpty() {
        return new PersonDTO();
    }

    @Override
    public boolean isImportant(PersonDTO person) {
        return person.isImportant();
    }

}
