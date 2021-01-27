package org.vaadin.erik.views.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import org.jooq.DSLContext;
import org.vaadin.erik.data.dto.PersonDTO;
import org.vaadin.erik.data.generated.jooq.public_.tables.Person;

import java.util.Optional;
import java.util.stream.Stream;

@PageTitle("Data: Jooq DTO")
public class JooqDTODataView extends AbstractDataView<PersonDTO> {

    private final DSLContext dslContext;

    public JooqDTODataView(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    Class<PersonDTO> getImplementationClass() {
        return PersonDTO.class;
    }

    @Override
    Stream<PersonDTO> fetch(Query<PersonDTO, Void> query) {
        return dslContext.select()
                .from(Person.PERSON)
                .offset(query.getOffset())
                .limit(query.getLimit())
                .fetchStreamInto(PersonDTO.class);
    }

    @Override
    Optional<PersonDTO> reload(PersonDTO person) {
        return dslContext.select()
                .from(Person.PERSON)
                .where(Person.PERSON.ID.eq(person.getId()))
                .fetchOptionalInto(PersonDTO.class);
    }

    @Override
    void update(PersonDTO person) {
        dslContext.newRecord(Person.PERSON, person).update();
    }

    @Override
    PersonDTO instantiateEmpty() {
        return new PersonDTO();
    }

    @Override
    boolean isImportant(PersonDTO person) {
        return person.isImportant();
    }
}
