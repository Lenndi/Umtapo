package org.lenndi.umtapo.service.specific.implementation;

import org.apache.log4j.Logger;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.enumeration.PairingType;
import org.lenndi.umtapo.dto.PairingDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.service.specific.BorrowerService;
import org.lenndi.umtapo.service.specific.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by axel on 14/04/17.
 */
@Service
public class PairingServiceImpl extends Thread {

    private static final Logger LOGGER = Logger.getLogger(PairingServiceImpl.class);


    private PairingDto pairingDto;
    @Value("${pairing.timeout}")
    private Integer timeout;

    private final BorrowerService borrowerService;

    private final ItemService itemService;

    /**
     * Instantiates a new Pairing service.
     *
     * @param borrowerService the borrower service
     * @param itemService     the item service
     */
    public PairingServiceImpl(BorrowerService borrowerService, ItemService itemService) {
        this.borrowerService = borrowerService;
        this.itemService = itemService;
    }

    /**
     * Sets tag id.
     *
     * @param tagId the tag id
     */
    public synchronized void setTagId(String tagId) {

        if (this.pairingDto.getPairingType() == PairingType.BORROWER) {
            try {
                Borrower borrower = this.borrowerService.findOne(this.pairingDto.getBorrowerId());
                borrower.setNfcId(tagId);
                this.borrowerService.save(borrower);
                this.pairingDto = null;
                notify();
            } catch (final Exception e) {
                LOGGER.error("Error during setPairingDto : " + e);
            }
        } else if (this.pairingDto.getPairingType() == PairingType.ITEM) {
            try {
                Item item = this.itemService.findOne(this.pairingDto.getItemId());
                item.setNfcId(tagId);
                this.itemService.save(item);
                this.pairingDto = null;
                notify();
            } catch (final Exception e) {
                LOGGER.error("Error during setPairingDto : " + e);
            }
        } else if (this.borrowerService.findByNfcId(tagId) != null) {
            this.pairingDto.setPairingType(PairingType.BORROWER);
        } else if (this.itemService.findByNfcId(tagId) != null) {
            this.pairingDto.setPairingType(PairingType.ITEM);
        }


    }

    /**
     * Sets pairing type.
     *
     * @param pairingDto the pairing dto
     */
    public synchronized void setPairingDto(PairingDto pairingDto) {

        this.pairingDto = pairingDto;

        try {
            wait(timeout);
            this.pairingDto = null;
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets logger.
     *
     * @return the logger
     */
    public static Logger getLOGGER() {
        return LOGGER;
    }

    /**
     * Gets pairing dto.
     *
     * @return the pairing dto
     */
    public PairingDto getPairingDto() {
        return pairingDto;
    }

    /**
     * Gets timeout.
     *
     * @return the timeout
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * Sets timeout.
     *
     * @param timeout the timeout
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    /**
     * Gets borrower service.
     *
     * @return the borrower service
     */
    public BorrowerService getBorrowerService() {
        return borrowerService;
    }

    /**
     * Gets item service.
     *
     * @return the item service
     */
    public ItemService getItemService() {
        return itemService;
    }
}
