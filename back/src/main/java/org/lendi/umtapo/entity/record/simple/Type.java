package org.lendi.umtapo.entity.record.simple;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Type entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ElementCollection
    private List<String> types;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
