package org.lendi.umtapo.service.specific.implementation;

import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.PairingDto;
import org.lendi.umtapo.enumeration.PairingType;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by axel on 14/04/17.
 */
@Service
public class PairingServiceImpl extends Thread {

    private static final Logger LOGGER = Logger.getLogger(PairingServiceImpl.class);


    private Integer id;
    private String tagId;
    private PairingType pairingType;
    @Value("${pairing.timeout}")
    private Integer timeout;

    private final BorrowerService borrowerService;

    /**
     * Instantiates a new Pairing service.
     *
     * @param borrowerService the borrower service
     */
    public PairingServiceImpl(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
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
    public synchronized void setTagId(String tagId) {
        this.tagId = tagId;

        if (readyToPair()) {
            try {
                Borrower borrower = this.borrowerService.findOne(this.id);
                borrower.setNfcId(this.tagId);
                this.borrowerService.save(borrower);
                this.setPropertiesNull();
                notify();
            } catch (final Exception e) {
                LOGGER.error("Error during setPairingDto : " + e);
            }
        }
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

    private void setPropertiesNull() {
        this.id = null;
        this.tagId = null;
        this.pairingType = null;
    }

    /**
     * Sets pairing type.
     *
     * @param pairingDto the pairing dto
     */
    public synchronized void setPairingDto(PairingDto pairingDto) {

        this.pairingType = pairingDto.getPairingType();
        this.id = pairingDto.getId();

        try {
            wait(timeout);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ready to pair boolean.
     *
     * @return the boolean
     */
    public Boolean readyToPair() {

        Boolean result = false;

        if (this.tagId != null && this.id != null && this.getPairingType() != null) {
            result = true;
        }
        return result;
    }
}
