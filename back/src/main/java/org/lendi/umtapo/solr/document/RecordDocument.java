package org.lendi.umtapo.solr.document;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * Solr document representation of Record bean.
 */
@SolrDocument(solrCoreName = "record")
public class RecordDocument {

    @Id
    private String id;

    @Field
    private String mainTitle;

    @Field
    private String subTitle;

    @Field
    private List<String> alternateTitles;

    @Field
    private String uniformTitle;

    @Field
    private String name;

    @Field
    private String secondName;

    @Field
    private String date;

    @Field
    private String titles;

    @Field
    private List<String> terms;

    @Field
    private String dewey;

    @Field
    private String universal;

    @Field
    private String mainDescription;

    @Field
    private List<String> otherDescriptions;

    @Field
    private String mainPhysicalDescription;

    @Field
    private String secondaryPhysicalDescription;

    @Field
    private String format;

    @Field
    private String associatedMaterial;

    @Field
    private String publicationPlace;

    @Field
    private String editorName;

    @Field
    private String editorAddress;

    @Field
    private String manufacturerName;

    @Field
    private String manufacturerPlace;

    @Field
    private String manufacturerAddress;

    @Field
    private String publicationDate;

    @Field
    private String manufactureDate;

    @Field
    private List<String> types;

    @Field
    private String recordIdentifier;

    @Field
    private String serialNumber;

    @Field
    private String serialType;

    @Field
    private String barCode;

    @Field
    private String library;

    @Field
    private String url;

    @Field
    private String mainLanguage;

    @Field
    private String originalLanguage;

    @Field
    private List<String> subtitles;

    @Field
    private List<String> others;

    @Field
    private String generalNote;

    @Field
    private String recordOrigin;

    @Field
    private String transactionDate;

    @Field
    private boolean isModified;

    /**
     * Instantiates a new Record document.
     */
    public RecordDocument() {
        this.alternateTitles = new ArrayList<>();
        this.terms = new ArrayList<>();
        this.otherDescriptions = new ArrayList<>();
        this.types = new ArrayList<>();
        this.subtitles = new ArrayList<>();
        this.others = new ArrayList<>();
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
     * Gets sub title.
     *
     * @return the sub title
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * Sets sub title.
     *
     * @param subTitle the sub title
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * Gets alternate titles.
     *
     * @return the alternate titles
     */
    public List<String> getAlternateTitles() {
        return alternateTitles;
    }

    /**
     * Sets alternate titles.
     *
     * @param alternateTitles the alternate titles
     */
    public void setAlternateTitles(List<String> alternateTitles) {
        this.alternateTitles = alternateTitles;
    }

    /**
     * Gets uniform title.
     *
     * @return the uniform title
     */
    public String getUniformTitle() {
        return uniformTitle;
    }

    /**
     * Sets uniform title.
     *
     * @param uniformTitle the uniform title
     */
    public void setUniformTitle(String uniformTitle) {
        this.uniformTitle = uniformTitle;
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
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets titles.
     *
     * @return the titles
     */
    public String getTitles() {
        return titles;
    }

    /**
     * Sets titles.
     *
     * @param titles the titles
     */
    public void setTitles(String titles) {
        this.titles = titles;
    }

    /**
     * Gets terms.
     *
     * @return the terms
     */
    public List<String> getTerms() {
        return terms;
    }

    /**
     * Sets terms.
     *
     * @param terms the terms
     */
    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    /**
     * Gets dewey.
     *
     * @return the dewey
     */
    public String getDewey() {
        return dewey;
    }

    /**
     * Sets dewey.
     *
     * @param dewey the dewey
     */
    public void setDewey(String dewey) {
        this.dewey = dewey;
    }

    /**
     * Gets universal.
     *
     * @return the universal
     */
    public String getUniversal() {
        return universal;
    }

    /**
     * Sets universal.
     *
     * @param universal the universal
     */
    public void setUniversal(String universal) {
        this.universal = universal;
    }

    /**
     * Gets main description.
     *
     * @return the main description
     */
    public String getMainDescription() {
        return mainDescription;
    }

    /**
     * Sets main description.
     *
     * @param mainDescription the main description
     */
    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    /**
     * Gets other descriptions.
     *
     * @return the other descriptions
     */
    public List<String> getOtherDescriptions() {
        return otherDescriptions;
    }

    /**
     * Sets other descriptions.
     *
     * @param otherDescriptions the other descriptions
     */
    public void setOtherDescriptions(List<String> otherDescriptions) {
        this.otherDescriptions = otherDescriptions;
    }

    /**
     * Gets main physical description.
     *
     * @return the main physical description
     */
    public String getMainPhysicalDescription() {
        return mainPhysicalDescription;
    }

    /**
     * Sets main physical description.
     *
     * @param mainPhysicalDescription the main physical description
     */
    public void setMainPhysicalDescription(String mainPhysicalDescription) {
        this.mainPhysicalDescription = mainPhysicalDescription;
    }

    /**
     * Gets secondary physical description.
     *
     * @return the secondary physical description
     */
    public String getSecondaryPhysicalDescription() {
        return secondaryPhysicalDescription;
    }

    /**
     * Sets secondary physical description.
     *
     * @param secondaryPhysicalDescription the secondary physical description
     */
    public void setSecondaryPhysicalDescription(String secondaryPhysicalDescription) {
        this.secondaryPhysicalDescription = secondaryPhysicalDescription;
    }

    /**
     * Gets format.
     *
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets format.
     *
     * @param format the format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Gets associated material.
     *
     * @return the associated material
     */
    public String getAssociatedMaterial() {
        return associatedMaterial;
    }

    /**
     * Sets associated material.
     *
     * @param associatedMaterial the associated material
     */
    public void setAssociatedMaterial(String associatedMaterial) {
        this.associatedMaterial = associatedMaterial;
    }

    /**
     * Gets publication place.
     *
     * @return the publication place
     */
    public String getPublicationPlace() {
        return publicationPlace;
    }

    /**
     * Sets publication place.
     *
     * @param publicationPlace the publication place
     */
    public void setPublicationPlace(String publicationPlace) {
        this.publicationPlace = publicationPlace;
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
     * Gets editor address.
     *
     * @return the editor address
     */
    public String getEditorAddress() {
        return editorAddress;
    }

    /**
     * Sets editor address.
     *
     * @param editorAddress the editor address
     */
    public void setEditorAddress(String editorAddress) {
        this.editorAddress = editorAddress;
    }

    /**
     * Gets manufacturer name.
     *
     * @return the manufacturer name
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * Sets manufacturer name.
     *
     * @param manufacturerName the manufacturer name
     */
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    /**
     * Gets manufacturer place.
     *
     * @return the manufacturer place
     */
    public String getManufacturerPlace() {
        return manufacturerPlace;
    }

    /**
     * Sets manufacturer place.
     *
     * @param manufacturerPlace the manufacturer place
     */
    public void setManufacturerPlace(String manufacturerPlace) {
        this.manufacturerPlace = manufacturerPlace;
    }

    /**
     * Gets manufacturer address.
     *
     * @return the manufacturer address
     */
    public String getManufacturerAddress() {
        return manufacturerAddress;
    }

    /**
     * Sets manufacturer address.
     *
     * @param manufacturerAddress the manufacturer address
     */
    public void setManufacturerAddress(String manufacturerAddress) {
        this.manufacturerAddress = manufacturerAddress;
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
     * Gets manufacture date.
     *
     * @return the manufacture date
     */
    public String getManufactureDate() {
        return manufactureDate;
    }

    /**
     * Sets manufacture date.
     *
     * @param manufactureDate the manufacture date
     */
    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    /**
     * Gets types.
     *
     * @return the types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * Sets types.
     *
     * @param types the types
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    /**
     * Gets record identifier.
     *
     * @return the record identifier
     */
    public String getRecordIdentifier() {
        return recordIdentifier;
    }

    /**
     * Sets record identifier.
     *
     * @param recordIdentifier the record identifier
     */
    public void setRecordIdentifier(String recordIdentifier) {
        this.recordIdentifier = recordIdentifier;
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
     * Gets serial type.
     *
     * @return the serial type
     */
    public String getSerialType() {
        return serialType;
    }

    /**
     * Sets serial type.
     *
     * @param serialType the serial type
     */
    public void setSerialType(String serialType) {
        this.serialType = serialType;
    }

    /**
     * Gets bar code.
     *
     * @return the bar code
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * Sets bar code.
     *
     * @param barCode the bar code
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * Gets library.
     *
     * @return the library
     */
    public String getLibrary() {
        return library;
    }

    /**
     * Sets library.
     *
     * @param library the library
     */
    public void setLibrary(String library) {
        this.library = library;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets main language.
     *
     * @return the main language
     */
    public String getMainLanguage() {
        return mainLanguage;
    }

    /**
     * Sets main language.
     *
     * @param mainLanguage the main language
     */
    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    /**
     * Gets original language.
     *
     * @return the original language
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * Sets original language.
     *
     * @param originalLanguage the original language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * Gets subtitles.
     *
     * @return the subtitles
     */
    public List<String> getSubtitles() {
        return subtitles;
    }

    /**
     * Sets subtitles.
     *
     * @param subtitles the subtitles
     */
    public void setSubtitles(List<String> subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Gets others.
     *
     * @return the others
     */
    public List<String> getOthers() {
        return others;
    }

    /**
     * Sets others.
     *
     * @param others the others
     */
    public void setOthers(List<String> others) {
        this.others = others;
    }

    /**
     * Gets general note.
     *
     * @return the general note
     */
    public String getGeneralNote() {
        return generalNote;
    }

    /**
     * Sets general note.
     *
     * @param generalNote the general note
     */
    public void setGeneralNote(String generalNote) {
        this.generalNote = generalNote;
    }

    /**
     * Gets record origin.
     *
     * @return the record origin
     */
    public String getRecordOrigin() {
        return recordOrigin;
    }

    /**
     * Sets record origin.
     *
     * @param recordOrigin the record origin
     */
    public void setRecordOrigin(String recordOrigin) {
        this.recordOrigin = recordOrigin;
    }

    /**
     * Gets transaction date.
     *
     * @return the transaction date
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets transaction date.
     *
     * @param transactionDate the transaction date
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets is modified.
     *
     * @return the is modified
     */
    public boolean getIsModified() {
        return isModified;
    }

    /**
     * Sets is modified.
     *
     * @param modified the modified
     */
    public void setIsModified(boolean modified) {
        isModified = modified;
    }
}
