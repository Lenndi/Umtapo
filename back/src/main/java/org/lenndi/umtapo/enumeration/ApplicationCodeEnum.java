package org.lenndi.umtapo.enumeration;

/**
 * Created by axel on 16/02/17.
 */
public enum ApplicationCodeEnum {
    /**
     * Document already borrowed application code enum.
     */
    DOCUMENT_ALREADY_BORROWED(1000),
    /**
     * Document already rendered application code enum.
     */
    DOCUMENT_ALREADY_RENDERED(1001),
    /**
     * Document cannot to be borrowed application code enum.
     */
    DOCUMENT_CANNOT_TO_BE_BORROWED(1002);


    private final int id;
    ApplicationCodeEnum(int id) {
        this.id = id;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return id;
    }
}
