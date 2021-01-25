/*
 * This file is generated by jOOQ.
 */
package org.vaadin.erik.data.generated.jooq.information_schema.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaadin.erik.data.generated.jooq.information_schema.InformationSchema;
import org.vaadin.erik.data.generated.jooq.information_schema.tables.records.LocksRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Locks extends TableImpl<LocksRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>INFORMATION_SCHEMA.LOCKS</code>
     */
    public static final Locks LOCKS = new Locks();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LocksRecord> getRecordType() {
        return LocksRecord.class;
    }

    /**
     * The column <code>INFORMATION_SCHEMA.LOCKS.TABLE_SCHEMA</code>.
     */
    public final TableField<LocksRecord, String> TABLE_SCHEMA = createField(DSL.name("TABLE_SCHEMA"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.LOCKS.TABLE_NAME</code>.
     */
    public final TableField<LocksRecord, String> TABLE_NAME = createField(DSL.name("TABLE_NAME"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.LOCKS.SESSION_ID</code>.
     */
    public final TableField<LocksRecord, Integer> SESSION_ID = createField(DSL.name("SESSION_ID"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.LOCKS.LOCK_TYPE</code>.
     */
    public final TableField<LocksRecord, String> LOCK_TYPE = createField(DSL.name("LOCK_TYPE"), SQLDataType.VARCHAR(2147483647), this, "");

    private Locks(Name alias, Table<LocksRecord> aliased) {
        this(alias, aliased, null);
    }

    private Locks(Name alias, Table<LocksRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>INFORMATION_SCHEMA.LOCKS</code> table reference
     */
    public Locks(String alias) {
        this(DSL.name(alias), LOCKS);
    }

    /**
     * Create an aliased <code>INFORMATION_SCHEMA.LOCKS</code> table reference
     */
    public Locks(Name alias) {
        this(alias, LOCKS);
    }

    /**
     * Create a <code>INFORMATION_SCHEMA.LOCKS</code> table reference
     */
    public Locks() {
        this(DSL.name("LOCKS"), null);
    }

    public <O extends Record> Locks(Table<O> child, ForeignKey<O, LocksRecord> key) {
        super(child, key, LOCKS);
    }

    @Override
    public Schema getSchema() {
        return InformationSchema.INFORMATION_SCHEMA;
    }

    @Override
    public Locks as(String alias) {
        return new Locks(DSL.name(alias), this);
    }

    @Override
    public Locks as(Name alias) {
        return new Locks(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Locks rename(String name) {
        return new Locks(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Locks rename(Name name) {
        return new Locks(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, String, Integer, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
