/*
 * This file is generated by jOOQ.
 */
package org.vaadin.erik.data.generated.jooq.public_.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaadin.erik.data.generated.jooq.public_.tables.Phone;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PhoneRecord extends UpdatableRecordImpl<PhoneRecord> implements Record3<Integer, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>PUBLIC.PHONE.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.PHONE.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>PUBLIC.PHONE.PHONE</code>.
     */
    public void setPhone(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.PHONE.PHONE</code>.
     */
    public String getPhone() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.PHONE.PERSON_ID</code>.
     */
    public void setPersonId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.PHONE.PERSON_ID</code>.
     */
    public Integer getPersonId() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Phone.PHONE.ID;
    }

    @Override
    public Field<String> field2() {
        return Phone.PHONE.PHONE_;
    }

    @Override
    public Field<Integer> field3() {
        return Phone.PHONE.PERSON_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getPhone();
    }

    @Override
    public Integer component3() {
        return getPersonId();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getPhone();
    }

    @Override
    public Integer value3() {
        return getPersonId();
    }

    @Override
    public PhoneRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public PhoneRecord value2(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public PhoneRecord value3(Integer value) {
        setPersonId(value);
        return this;
    }

    @Override
    public PhoneRecord values(Integer value1, String value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PhoneRecord
     */
    public PhoneRecord() {
        super(Phone.PHONE);
    }

    /**
     * Create a detached, initialised PhoneRecord
     */
    public PhoneRecord(Integer id, String phone, Integer personId) {
        super(Phone.PHONE);

        setId(id);
        setPhone(phone);
        setPersonId(personId);
    }
}