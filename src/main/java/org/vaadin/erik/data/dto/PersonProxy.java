package org.vaadin.erik.data.dto;

import org.vaadin.erik.data.generated.jooq.public_.tables.records.PersonRecord;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PhoneRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses for combining a jOOQ {@link PersonRecord} and a list of {@link PhoneRecord}.
 *
 * Would not necessarily be needed if it wasn't for trying to keep
 * the {@link org.vaadin.erik.views.data.AbstractDataView} reusable.
 */
public class PersonProxy {

    private final PersonRecord personRecord;
    private List<PhoneRecord> phoneRecords = new ArrayList<>();

    public PersonProxy(PersonRecord personRecord) {
        this.personRecord = personRecord;
    }

    public Integer getId() {
        return personRecord.getId();
    }

    public void setId(Integer id) {
        personRecord.setId(id);
    }

    public String getFirstName() {
        return personRecord.getFirstName();
    }

    public void setFirstName(String firstName) {
        personRecord.setFirstName(firstName);
    }

    public String getLastName() {
        return personRecord.getLastName();
    }

    public void setLastName(String lastName) {
        personRecord.setLastName(lastName);
    }

    public String getEmail() {
        return personRecord.getEmail();
    }

    public void setEmail(String email) {
        personRecord.setEmail(email);
    }

    public LocalDate getDateOfBirth() {
        return personRecord.getDateOfBirth();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        personRecord.setDateOfBirth(dateOfBirth);
    }

    public String getOccupation() {
        return personRecord.getOccupation();
    }

    public void setOccupation(String occupation) {
        personRecord.setOccupation(occupation);
    }

    public boolean isImportant() {
        return Boolean.TRUE.equals(personRecord.getImportant());
    }

    public void setImportant(boolean important) {
        personRecord.setImportant(important);
    }

    public Integer getVersion() {
        return personRecord.getVersion();
    }

    public void setVersion(Integer version) {
        personRecord.setVersion(version);
    }

    public List<PhoneRecord> getPhoneRecords() {
        return phoneRecords;
    }

    public void setPhoneRecords(List<PhoneRecord> phoneRecords) {
        if (phoneRecords != null) {
            this.phoneRecords = phoneRecords;
        }
    }

    public PersonRecord getPersonRecord() {
        return personRecord;
    }
}
