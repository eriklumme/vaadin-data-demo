package org.vaadin.erik.views.data;

import com.vaadin.flow.data.provider.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface DataPresenter<PERSON, PHONE> {

    Class<PERSON> getImplementationClass();

    @Transactional(readOnly = true)
    Stream<PERSON> fetch(Query<PERSON, Void> query);

    @Transactional(readOnly = true)
    Optional<PERSON> reload(PERSON person);

    @Transactional
    void updateOrInsert(PERSON person);

    PERSON instantiateEmpty();

    boolean isImportant(PERSON person);

    List<PHONE> getPhones(PERSON person);

    void setPhones(PERSON person, List<PHONE> phones);

    PHONE createPhone(String phone);

    String getPhoneString(PHONE phone);
}
