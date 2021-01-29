package org.vaadin.erik.views.data.jpa;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.vaadin.erik.data.entity.Person;
import org.vaadin.erik.data.entity.Phone;
import org.vaadin.erik.views.data.DataPresenter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JpaDataPresenter implements DataPresenter<Person, Phone> {

    private final TransactionTemplate transactionTemplate;

    @PersistenceContext
    private EntityManager em;

    public JpaDataPresenter(PlatformTransactionManager platformTransactionManager) {
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public Class<Person> getImplementationClass() {
        return Person.class;
    }

    @Override
    public Stream<Person> fetch(Query<Person, Void> query) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        cq.select(root);
        cq.orderBy(query.getSortOrders().stream().map(sortOrder -> {
            Path<?> path = root.get(sortOrder.getSorted());
            return sortOrder.getDirection() == SortDirection.ASCENDING ?
                    cb.asc(path) : cb.desc(path);
        }).collect(Collectors.toList()));

        return em.createQuery(cq)
                .setFirstResult(query.getOffset())
                .setMaxResults(query.getLimit())
                .getResultList().stream();
    }

    @Override
    public Optional<Person> reload(Person person) {
        return Optional.ofNullable(em.find(Person.class, person.getId()));
    }

    @Override
    public void updateOrInsert(Person person){
        transactionTemplate.executeWithoutResult(status -> em.merge(person));
    }

    @Override
    public Person instantiateEmpty() {
        return new Person();
    }

    @Override
    public boolean isImportant(Person person) {
        return person.isImportant();
    }

    @Override
    public List<Phone> getPhones(Person person) {
        return person.getPhones();
    }

    @Override
    public void setPhones(Person person, List<Phone> phones) {
        person.setPhones(phones);
    }

    @Override
    public Phone createPhone(String phone) {
        Phone phoneEntity = new Phone();
        phoneEntity.setPhone(phone);
        return phoneEntity;
    }

    @Override
    public String getPhoneString(Phone phone) {
        return phone.getPhone();
    }
}
