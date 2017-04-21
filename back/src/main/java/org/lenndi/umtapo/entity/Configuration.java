package org.lenndi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Configuration entity.
 * <p>
 * Created by axel on 29/11/16.
 */
@Entity
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String masterToken;

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
     * Gets master token.
     *
     * @return the master token
     */
    public String getMasterToken() {
        return masterToken;
    }


    /**
     * Sets master token.
     *
     * @param masterToken the master token
     */
    public void setMasterToken(String masterToken) {
        this.masterToken = masterToken;
    }
}
