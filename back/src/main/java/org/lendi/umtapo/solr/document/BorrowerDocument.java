package org.lendi.umtapo.solr.document;

import org.apache.solr.client.solrj.beans.Field;

import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * The type Borrower document.
 */
public class BorrowerDocument {

    public static final String DOCUMENT_TYPE = "borrower";

    @Field(value = "document_type")
    private String documentType;

    @Field
    private String id;

    @Field
    private String[] name;

    @Field
    private String comment;

    @Field
    private ZonedDateTime birthday;

    @Field
    private Integer quota;

    @Field
    private Boolean emailOptin;

    @Field(child = true)
    private AddressDocument address;

    public BorrowerDocument() {
        this.documentType = DOCUMENT_TYPE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Arrays.toString(name);
    }

    public void setName(String name) {
        this.name = new String[]{name};
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Boolean getEmailOptin() {
        return emailOptin;
    }

    public void setEmailOptin(Boolean emailOptin) {
        this.emailOptin = emailOptin;
    }

    public AddressDocument getAddress() {
        return address;
    }

    public void setAddress(AddressDocument address) {
        this.address = address;
    }

    public String getDocumentType() {
        return documentType;
    }
}
