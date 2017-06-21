package org.lendi.umtapo.dto;

import org.lendi.umtapo.enumeration.PairingType;

/**
 * Created by axel on 14/04/17.
 */
public class PairingDto {

    private Integer id;
    private String tagId;
    private PairingType pairingType;

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
     * Gets pairing type.
     *
     * @return the pairing type
     */
    public PairingType getPairingType() {
        return pairingType;
    }

    /**
     * Sets pairing type.
     *
     * @param pairingType the pairing type
     */
    public void setPairingType(PairingType pairingType) {
        this.pairingType = pairingType;
    }

    /**
     * Gets tag id.
     *
     * @return the tag id
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * Sets tag id.
     *
     * @param tagId the tag id
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
