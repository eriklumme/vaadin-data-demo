package org.vaadin.erik.views.data.repository;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.vaadin.erik.data.entity.Person;
import org.vaadin.erik.data.service.PersonService;
import org.vaadin.erik.views.data.DataPresenter;

import javax.persistence.OptimisticLockException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RepositoryDataPresenter implements DataPresenter<Person> {

    private final PersonService personService;

    public RepositoryDataPresenter(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Class<Person> getImplementationClass() {
        return Person.class;
    }

    @Override
    public Stream<Person> fetch(Query<Person, Void> query) {
        return personService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream();
    }

    @Override
    public Optional<Person> reload(Person person) {
        return personService.get(person.getId());
    }

    @Override
    public void updateOrInsert(Person person) {
        try {
            personService.update(person);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticLockException(e);
        }

    }

    @Override
    public Person instantiateEmpty() {
        return new Person();
    }

    @Override
    public boolean isImportant(Person person) {
        return person.isImportant();
    }

}
