package org.lendi.umtapo.solr.document.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.beans.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Type entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Type {

    private static final String DOCUMENT_TYPE = "type";

    @Field(value = "document_type")
    private final String documentType;

    @Field
    private List<String> types;

    /**
     * Instantiates a new Type.
     */
    public Type() {
        this.documentType = DOCUMENT_TYPE;
        this.types = new ArrayList<>();
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
     * Add type.
     *
     * @param type the type
     */
    public void addType(String type) {
        this.types.add(type);
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
