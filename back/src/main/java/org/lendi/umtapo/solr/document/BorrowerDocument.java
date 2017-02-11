package org.lendi.umtapo.solr.document;

import org.apache.solr.client.solrj.beans.Field;

import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * The type Borrower document.
 */
public class BorrowerDocument implements ParentDocument {

    private static final String DOCUMENT_TYPE = "borrower";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String id;

    @Field
    private String[] name;

    @Field
    private String comment;

    private ZonedDateTime birthday;

    @Field
    private Integer quota;

    @Field
    private Boolean emailOptin;

    @Field(child = true)
    private AddressDocument address;

    /**
     * Instantiates a new Borrower document.
     */
    public BorrowerDocument() {
        this.documentType = DOCUMENT_TYPE;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return Arrays.toString(name);
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = new String[]{name};
    }

    /**
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public ZonedDateTime getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets quota.
     *
     * @return the quota
     */
    public Integer getQuota() {
        return quota;
    }

    /**
     * Sets quota.
     *
     * @param quota the quota
     */
    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    /**
     * Gets email optin.
     *
     * @return the email optin
     */
    public Boolean getEmailOptin() {
        return emailOptin;
    }

    /**
     * Sets email optin.
     *
     * @param emailOptin the email optin
     */
    public void setEmailOptin(Boolean emailOptin) {
        this.emailOptin = emailOptin;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public AddressDocument getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(AddressDocument address) {
        this.address = address;
    }

    /**
     * Gets document type.
     *
     * @return the document type
     */
    public String getDocumentType() {
        return documentType;
    }
}
