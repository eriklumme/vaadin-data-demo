/*
 * This file is generated by jOOQ.
 */
package org.vaadin.erik.data.generated.jooq.public_.tables;


import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaadin.erik.data.generated.jooq.public_.Keys;
import org.vaadin.erik.data.generated.jooq.public_.Public;
import org.vaadin.erik.data.generated.jooq.public_.tables.records.PhoneRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Phone extends TableImpl<PhoneRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>PUBLIC.PHONE</code>
     */
    public static final Phone PHONE = new Phone();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PhoneRecord> getRecordType() {
        return PhoneRecord.class;
    }

    /**
     * The column <code>PUBLIC.PHONE.ID</code>.
     */
    public final TableField<PhoneRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>PUBLIC.PHONE.PHONE</code>.
     */
    public final TableField<PhoneRecord, String> PHONE_ = createField(DSL.name("PHONE"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>PUBLIC.PHONE.PERSON_ID</code>.
     */
    public final TableField<PhoneRecord, Integer> PERSON_ID = createField(DSL.name("PERSON_ID"), SQLDataType.INTEGER, this, "");

    private Phone(Name alias, Table<PhoneRecord> aliased) {
        this(alias, aliased, null);
    }

    private Phone(Name alias, Table<PhoneRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>PUBLIC.PHONE</code> table reference
     */
    public Phone(String alias) {
        this(DSL.name(alias), PHONE);
    }

    /**
     * Create an aliased <code>PUBLIC.PHONE</code> table reference
     */
    public Phone(Name alias) {
        this(alias, PHONE);
    }

    /**
     * Create a <code>PUBLIC.PHONE</code> table reference
     */
    public Phone() {
        this(DSL.name("PHONE"), null);
    }

    public <O extends Record> Phone(Table<O> child, ForeignKey<O, PhoneRecord> key) {
        super(child, key, PHONE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<PhoneRecord, Integer> getIdentity() {
        return (Identity<PhoneRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<PhoneRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_4;
    }

    @Override
    public List<UniqueKey<PhoneRecord>> getKeys() {
        return Arrays.<UniqueKey<PhoneRecord>>asList(Keys.CONSTRAINT_4);
    }

    @Override
    public List<ForeignKey<PhoneRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PhoneRecord, ?>>asList(Keys.FKKK6UIJ3J6WIKPNQLJ9DYMOBS9);
    }

    public Person person() {
        return new Person(this, Keys.FKKK6UIJ3J6WIKPNQLJ9DYMOBS9);
    }

    @Override
    public Phone as(String alias) {
        return new Phone(DSL.name(alias), this);
    }

    @Override
    public Phone as(Name alias) {
        return new Phone(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Phone rename(String name) {
        return new Phone(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Phone rename(Name name) {
        return new Phone(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
