package org.lenndi.umtapo.entity.configuration;

/**
 * Z39.50 short description.
 */
public class Z3950Description {
    private Integer id;
    private String name;

    /**
     * Instantiates a new Z 3950 description.
     *
     * @param id   the id
     * @param name the name
     */
    public Z3950Description(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
