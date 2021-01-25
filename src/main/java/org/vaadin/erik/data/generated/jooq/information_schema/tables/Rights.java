/*
 * This file is generated by jOOQ.
 */
package org.vaadin.erik.data.generated.jooq.information_schema.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaadin.erik.data.generated.jooq.information_schema.InformationSchema;
import org.vaadin.erik.data.generated.jooq.information_schema.tables.records.RightsRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Rights extends TableImpl<RightsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>INFORMATION_SCHEMA.RIGHTS</code>
     */
    public static final Rights RIGHTS = new Rights();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RightsRecord> getRecordType() {
        return RightsRecord.class;
    }

    /**
     * The column <code>INFORMATION_SCHEMA.RIGHTS.GRANTEE</code>.
     */
    public final TableField<RightsRecord, String> GRANTEE = createField(DSL.name("GRANTEE"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.RIGHTS.GRANTEETYPE</code>.
     */
    public final TableField<RightsRecord, String> GRANTEETYPE = createField(DSL.name("GRANTEETYPE"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.RIGHTS.GRANTEDROLE</code>.
     */
    public final TableField<RightsRecord, String> GRANTEDROLE = createField(DSL.name("GRANTEDROLE"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.RIGHTS.RIGHTS</code>.
     */
    public final TableField<RightsRecord, String> RIGHTS_ = createField(DSL.name("RIGHTS"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.RIGHTS.TABLE_SCHEMA</code>.
     */
    public final TableField<RightsRecord, String> TABLE_SCHEMA = createField(DSL.name("TABLE_SCHEMA"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.RIGHTS.TABLE_NAME</code>.
     */
    public final TableField<RightsRecord, String> TABLE_NAME = createField(DSL.name("TABLE_NAME"), SQLDataType.VARCHAR(2147483647), this, "");

    /**
     * The column <code>INFORMATION_SCHEMA.RIGHTS.ID</code>.
     */
    public final TableField<RightsRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER, this, "");

    private Rights(Name alias, Table<RightsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Rights(Name alias, Table<RightsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>INFORMATION_SCHEMA.RIGHTS</code> table reference
     */
    public Rights(String alias) {
        this(DSL.name(alias), RIGHTS);
    }

    /**
     * Create an aliased <code>INFORMATION_SCHEMA.RIGHTS</code> table reference
     */
    public Rights(Name alias) {
        this(alias, RIGHTS);
    }

    /**
     * Create a <code>INFORMATION_SCHEMA.RIGHTS</code> table reference
     */
    public Rights() {
        this(DSL.name("RIGHTS"), null);
    }

    public <O extends Record> Rights(Table<O> child, ForeignKey<O, RightsRecord> key) {
        super(child, key, RIGHTS);
    }

    @Override
    public Schema getSchema() {
        return InformationSchema.INFORMATION_SCHEMA;
    }

    @Override
    public Rights as(String alias) {
        return new Rights(DSL.name(alias), this);
    }

    @Override
    public Rights as(Name alias) {
        return new Rights(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Rights rename(String name) {
        return new Rights(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Rights rename(Name name) {
        return new Rights(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<String, String, String, String, String, String, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
