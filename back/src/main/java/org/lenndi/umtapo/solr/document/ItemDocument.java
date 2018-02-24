package org.lenndi.umtapo.solr.document;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * The type Item document.
 */
@SolrDocument(solrCoreName = "item")
public class ItemDocument {

    @Id
    private String id;

    @Field
    private String internalId;

    @Field
    private String externalId;

    @Field
    private Boolean loanable;

    @Field
    private Boolean borrowed;

    @Field
    private String condition;

    @Field
    private String type;

    @Field
    private String mainTitle;

    @Field
    private String name;

    @Field
    private String secondName;

    @Field
    private String editorName;

    @Field
    private String publicationDate;

    @Field
    private String serialNumber;

    @Field
    private String libraryId;

    @Field
    private String externalLibraryId;

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
     * Gets internal id.
     *
     * @return the internal id
     */
    public String getInternalId() {
        return internalId;
    }

    /**
     * Sets internal id.
     *
     * @param internalId the internal id
     */
    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    /**
     * Gets external id.
     *
     * @return the external id
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets external id.
     *
     * @param externalId the external id
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * Gets loanable.
     *
     * @return the loanable
     */
    public Boolean getLoanable() {
        return loanable;
    }

    /**
     * Sets loanable.
     *
     * @param loanable the loanable
     */
    public void setLoanable(Boolean loanable) {
        this.loanable = loanable;
    }

    /**
     * Gets borrowed.
     *
     * @return the borrowed
     */
    public Boolean getBorrowed() {
        return borrowed;
    }

    /**
     * Sets borrowed.
     *
     * @param borrowed the borrowed
     */
    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    /**
     * Gets condition.
     *
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets condition.
     *
     * @param condition the condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Gets main title.
     *
     * @return the main title
     */
    public String getMainTitle() {
        return mainTitle;
    }

    /**
     * Sets main title.
     *
     * @param mainTitle the main title
     */
    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets second name.
     *
     * @return the second name
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * Sets second name.
     *
     * @param secondName the second name
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Gets editor name.
     *
     * @return the editor name
     */
    public String getEditorName() {
        return editorName;
    }

    /**
     * Sets editor name.
     *
     * @param editorName the editor name
     */
    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    /**
     * Gets publication date.
     *
     * @return the publication date
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets publication date.
     *
     * @param publicationDate the publication date
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Gets serial number.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets serial number.
     *
     * @param serialNumber the serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Gets library id.
     *
     * @return the library id
     */
    public String getLibraryId() {
        return libraryId;
    }

    /**
     * Sets library id.
     *
     * @param libraryId the library id
     */
    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    /**
     * Gets external library id.
     *
     * @return the external library id
     */
    public String getExternalLibraryId() {
        return externalLibraryId;
    }

    /**
     * Sets external library id.
     *
     * @param externalLibraryId the external library id
     */
    public void setExternalLibraryId(String externalLibraryId) {
        this.externalLibraryId = externalLibraryId;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }
}
