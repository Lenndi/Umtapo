package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Type entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Type {
    private List<String> types;

    /**
     * Instantiates a new Type.
     */
    public Type() {
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
}
