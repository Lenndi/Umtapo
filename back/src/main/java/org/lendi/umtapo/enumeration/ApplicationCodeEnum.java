package org.lendi.umtapo.enumeration;

/**
 * Created by axel on 16/02/17.
 */
public enum ApplicationCodeEnum {
    DOCUMENT_ALREADY_BORROWED(1000),
    DOCUMENT_ALREADY_RENDERED(1001),
    DOCUMENT_CANNOT_TO_BE_BORROWED(1002);


    private final int id;
    ApplicationCodeEnum(int id) { this.id = id; }
    public int getValue() { return id; }
}
