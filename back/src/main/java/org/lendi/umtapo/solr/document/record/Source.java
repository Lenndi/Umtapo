package org.lendi.umtapo.solr.document.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Source entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Source {

    private static final String DOCUMENT_TYPE = "source";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private String library;

    @Field
    private String url;

    /**
     * Instantiates a new Source.
     */
    public Source() {
        this.documentType = DOCUMENT_TYPE;
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
     * Gets document type.
     *
     * @return the document type
     */
    public String getDocumentType() {
        return documentType;
    }
}
