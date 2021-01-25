package org.vaadin.erik.data;

import java.time.LocalDate;

/**
 * Just to enable shared logic in the different data views.
 */
public interface IPerson {

    Integer getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPhone();

    LocalDate getDateOfBirth();

    String getOccupation();

    boolean isImportant();
}
