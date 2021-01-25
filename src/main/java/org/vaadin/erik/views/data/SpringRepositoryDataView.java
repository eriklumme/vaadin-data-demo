package org.vaadin.erik.views.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;
import org.vaadin.erik.data.entity.Person;
import org.vaadin.erik.data.service.PersonService;
import org.vaadin.erik.views.main.MainView;

import java.util.Optional;
import java.util.stream.Stream;

@PageTitle("Data: Spring Repository")
@RouteAlias(value = "", layout = MainView.class)
public class SpringRepositoryDataView extends AbstractDataView<Person> {

    private final PersonService personService;

    public SpringRepositoryDataView(PersonService personService) {
        this.personService = personService;
    }

    @Override
    Class<Person> getImplementationClass() {
        return Person.class;
    }

    @Override
    Stream<Person> fetch(Query<Person, Void> query) {
        return personService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream();
    }

    @Override
    Optional<Person> get(Integer id) {
        return personService.get(id);
    }

    @Override
    void update(Person person) {
        personService.update(person);
    }

    @Override
    Person instantiateEmpty() {
        return new Person();
    }
}
