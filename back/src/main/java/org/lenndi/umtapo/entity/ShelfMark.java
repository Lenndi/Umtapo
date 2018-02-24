package org.lenndi.umtapo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Embeddable;

/**
 * Item shelfmark.
 */
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShelfMark {

    private String field1;
    private String field2;
    private String field3;
    private String field4;

    /**
     * Instantiates a new Shelf mark.
     */
    public ShelfMark() { }

    /**
     * Instantiates a new Shelf mark.
     *
     * @param field1 the field 1
     * @param field2 the field 2
     * @param field3 the field 3
     */
    public ShelfMark(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    /**
     * Gets field 1.
     *
     * @return the field 1
     */
    public String getField1() {
        return field1;
    }

    /**
     * Sets field 1.
     *
     * @param field1 the field 1
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }

    /**
     * Gets field 2.
     *
     * @return the field 2
     */
    public String getField2() {
        return field2;
    }

    /**
     * Sets field 2.
     *
     * @param field2 the field 2
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }

    /**
     * Gets field 3.
     *
     * @return the field 3
     */
    public String getField3() {
        return field3;
    }

    /**
     * Sets field 3.
     *
     * @param field3 the field 3
     */
    public void setField3(String field3) {
        this.field3 = field3;
    }

    /**
     * Gets field 4.
     *
     * @return the field 4
     */
    public String getField4() {
        return field4;
    }

    /**
     * Sets field 4.
     *
     * @param field4 the field 4
     */
    public void setField4(String field4) {
        this.field4 = field4;
    }
}
