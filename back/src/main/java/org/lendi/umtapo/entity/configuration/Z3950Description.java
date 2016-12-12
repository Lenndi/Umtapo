package org.lendi.umtapo.entity.configuration;

/**
 * Z39.50 short description.
 */
public class Z3950Description {
    private Integer id;
    private String name;

    public Z3950Description(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
