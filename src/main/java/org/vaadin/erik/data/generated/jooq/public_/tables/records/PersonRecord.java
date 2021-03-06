/*
 * This file is generated by jOOQ.
 */
package org.vaadin.erik.data.generated.jooq.public_.tables.records;


import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaadin.erik.data.generated.jooq.public_.tables.Person;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PersonRecord extends UpdatableRecordImpl<PersonRecord> implements Record8<Integer, LocalDate, String, String, Boolean, String, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>PUBLIC.PERSON.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.PERSON.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>PUBLIC.PERSON.DATE_OF_BIRTH</code>.
     */
    public void setDateOfBirth(LocalDate value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.PERSON.DATE_OF_BIRTH</code>.
     */
    public LocalDate getDateOfBirth() {
        return (LocalDate) get(1);
    }

    /**
     * Setter for <code>PUBLIC.PERSON.EMAIL</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.PERSON.EMAIL</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>PUBLIC.PERSON.FIRST_NAME</code>.
     */
    public void setFirstName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.PERSON.FIRST_NAME</code>.
     */
    public String getFirstName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.PERSON.IMPORTANT</code>.
     */
    public void setImportant(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.PERSON.IMPORTANT</code>.
     */
    public Boolean getImportant() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>PUBLIC.PERSON.LAST_NAME</code>.
     */
    public void setLastName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.PERSON.LAST_NAME</code>.
     */
    public String getLastName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>PUBLIC.PERSON.OCCUPATION</code>.
     */
    public void setOccupation(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.PERSON.OCCUPATION</code>.
     */
    public String getOccupation() {
        return (String) get(6);
    }

    /**
     * Setter for <code>PUBLIC.PERSON.VERSION</code>.
     */
    public void setVersion(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.PERSON.VERSION</code>.
     */
    public Integer getVersion() {
        return (Integer) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, LocalDate, String, String, Boolean, String, String, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Integer, LocalDate, String, String, Boolean, String, String, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Person.PERSON.ID;
    }

    @Override
    public Field<LocalDate> field2() {
        return Person.PERSON.DATE_OF_BIRTH;
    }

    @Override
    public Field<String> field3() {
        return Person.PERSON.EMAIL;
    }

    @Override
    public Field<String> field4() {
        return Person.PERSON.FIRST_NAME;
    }

    @Override
    public Field<Boolean> field5() {
        return Person.PERSON.IMPORTANT;
    }

    @Override
    public Field<String> field6() {
        return Person.PERSON.LAST_NAME;
    }

    @Override
    public Field<String> field7() {
        return Person.PERSON.OCCUPATION;
    }

    @Override
    public Field<Integer> field8() {
        return Person.PERSON.VERSION;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public LocalDate component2() {
        return getDateOfBirth();
    }

    @Override
    public String component3() {
        return getEmail();
    }

    @Override
    public String component4() {
        return getFirstName();
    }

    @Override
    public Boolean component5() {
        return getImportant();
    }

    @Override
    public String component6() {
        return getLastName();
    }

    @Override
    public String component7() {
        return getOccupation();
    }

    @Override
    public Integer component8() {
        return getVersion();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public LocalDate value2() {
        return getDateOfBirth();
    }

    @Override
    public String value3() {
        return getEmail();
    }

    @Override
    public String value4() {
        return getFirstName();
    }

    @Override
    public Boolean value5() {
        return getImportant();
    }

    @Override
    public String value6() {
        return getLastName();
    }

    @Override
    public String value7() {
        return getOccupation();
    }

    @Override
    public Integer value8() {
        return getVersion();
    }

    @Override
    public PersonRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public PersonRecord value2(LocalDate value) {
        setDateOfBirth(value);
        return this;
    }

    @Override
    public PersonRecord value3(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public PersonRecord value4(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public PersonRecord value5(Boolean value) {
        setImportant(value);
        return this;
    }

    @Override
    public PersonRecord value6(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public PersonRecord value7(String value) {
        setOccupation(value);
        return this;
    }

    @Override
    public PersonRecord value8(Integer value) {
        setVersion(value);
        return this;
    }

    @Override
    public PersonRecord values(Integer value1, LocalDate value2, String value3, String value4, Boolean value5, String value6, String value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PersonRecord
     */
    public PersonRecord() {
        super(Person.PERSON);
    }

    /**
     * Create a detached, initialised PersonRecord
     */
    public PersonRecord(Integer id, LocalDate dateOfBirth, String email, String firstName, Boolean important, String lastName, String occupation, Integer version) {
        super(Person.PERSON);

        setId(id);
        setDateOfBirth(dateOfBirth);
        setEmail(email);
        setFirstName(firstName);
        setImportant(important);
        setLastName(lastName);
        setOccupation(occupation);
        setVersion(version);
    }
}
