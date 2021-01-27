package org.vaadin.erik.views.data;

import com.vaadin.flow.data.provider.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

public interface DataPresenter<T> {

    Class<T> getImplementationClass();

    @Transactional(readOnly = true)
    Stream<T> fetch(Query<T, Void> query);

    @Transactional(readOnly = true)
    Optional<T> reload(T person);

    @Transactional
    void updateOrInsert(T person);

    T instantiateEmpty();

    boolean isImportant(T person);
}
