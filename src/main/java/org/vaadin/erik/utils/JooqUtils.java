package org.vaadin.erik.utils;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import org.jooq.OrderField;
import org.jooq.TableField;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PersonRecord;

import static org.vaadin.erik.data.generated.jooq.public_.tables.Person.PERSON;

public class JooqUtils {

    public static OrderField<?>[] toOrderFields(Query<?, Void> query) {
        return query.getSortOrders().stream().map(sortOrder -> {
            switch (sortOrder.getSorted()) {
                case "firstName":
                    return toOrderField(sortOrder, PERSON.FIRST_NAME);
                case "lastName":
                    return toOrderField(sortOrder, PERSON.LAST_NAME);
                case "email":
                    return toOrderField(sortOrder, PERSON.EMAIL);
                case "dateOfBirth":
                    return toOrderField(sortOrder, PERSON.DATE_OF_BIRTH);
                case "occupation":
                    return toOrderField(sortOrder, PERSON.OCCUPATION);
                default:
                    throw new IllegalArgumentException("Unknown property to sort by: " + sortOrder.getSorted());
            }
        }).toArray(OrderField[]::new);
    }

    private static OrderField<?> toOrderField(QuerySortOrder querySortOrder, TableField<PersonRecord, ?> field) {
        return querySortOrder.getDirection() == SortDirection.ASCENDING ?
                field.asc() : field.desc();
    }
}
