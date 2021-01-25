package org.vaadin.erik.views.data;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.vaadin.erik.data.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.stream.Stream;

@PageTitle("Data: JPA")
public class JPARepositoryView extends AbstractDataView<Person> {

    private final TransactionTemplate transactionTemplate;

    @PersistenceContext
    private EntityManager em;

    public JPARepositoryView(PlatformTransactionManager platformTransactionManager) {
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    Class<Person> getImplementationClass() {
        return Person.class;
    }

    @Override
    Stream<Person> fetch(Query<Person, Void> query) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        cq.select(root);
        return em.createQuery(cq)
                .setFirstResult(query.getOffset())
                .setMaxResults(query.getLimit())
                .getResultList().stream();
    }

    @Override
    Optional<Person> get(Integer id) {
        return Optional.ofNullable(em.find(Person.class, id));
    }

    @Override
    void update(Person person) {
        transactionTemplate.executeWithoutResult(status -> em.merge(person));
    }

    @Override
    Person instantiateEmpty() {
        return new Person();
    }
}
