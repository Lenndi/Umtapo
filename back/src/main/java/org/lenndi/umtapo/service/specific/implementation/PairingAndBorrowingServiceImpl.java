package org.lenndi.umtapo.service.specific.implementation;

import org.apache.log4j.Logger;
import org.lenndi.umtapo.dto.PairingDto;
import org.lenndi.umtapo.entity.Borrower;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.enumeration.PairingType;
import org.lenndi.umtapo.exception.CreateLoanException;
import org.lenndi.umtapo.exception.NotLoannableException;
import org.lenndi.umtapo.service.specific.BorrowerService;
import org.lenndi.umtapo.service.specific.ItemService;
import org.lenndi.umtapo.service.specific.LoanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Pairing service.
 */
@Service
public class PairingAndBorrowingServiceImpl extends Thread {

    private static final Logger LOGGER = Logger.getLogger(PairingAndBorrowingServiceImpl.class);


    private PairingDto pairingDto;
    @Value("${pairing.timeout}")
    private Integer timeout;
    private Boolean isNotify = false;

    private final BorrowerService borrowerService;

    private final ItemService itemService;

    private final LoanService loanService;

    /**
     * Instantiates a new Pairing service.
     *
     * @param borrowerService the borrower service
     * @param itemService     the item service
     * @param loanService     the loan service
     */
    public PairingAndBorrowingServiceImpl(BorrowerService borrowerService, ItemService itemService,
                                          LoanService loanService) {
        this.borrowerService = borrowerService;
        this.itemService = itemService;
        this.loanService = loanService;
    }

    /**
     * Sets tag id.
     *
     * @param tagId the tag id
     */
    @Transactional
    public synchronized void setTagId(String tagId) {

        if (this.pairingDto != null && this.pairingDto.getWaitPairing() != null && this.pairingDto.getWaitPairing()) {
            if (this.pairingDto.getPairingType() == PairingType.BORROWER) {
                Borrower borrower = this.borrowerService.findOne(this.pairingDto.getBorrowerId());
                borrower.setNfcId(tagId);
                this.borrowerService.save(borrower);
                this.pairingDto = null;
            } else if (this.pairingDto.getPairingType() == PairingType.ITEM) {
                Item item = this.itemService.findOne(this.pairingDto.getItemId());
                item.setNfcId(tagId);
                this.itemService.save(item);
                this.pairingDto = null;
            }
            this.isNotify = true;
            notify();
        } else {
            Item item = null;

            Borrower borrower = this.borrowerService.findByNfcId(tagId);
            if (borrower == null) {
                item = this.itemService.findByNfcId(tagId);
            } else {
                this.pairingDto = new PairingDto();
                this.pairingDto.setBorrowerId(borrower.getId());
                this.pairingDto.setPairingType(PairingType.BORROWER);
            }

            if (this.pairingDto != null && this.pairingDto.getPairingType() == PairingType.BORROWER) {
                if (item != null && item.getBorrowed()) {
                    this.loanService.backLoan(item);
                } else if (item != null && !item.getBorrowed()) {
                    try {
                        this.loanService.createLoan(item, this.pairingDto.getBorrowerId());
                    } catch (final CreateLoanException | NotLoannableException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Sets pairing dto.
     *
     * @param pairingDtoParam the pairing dto
     * @return the pairing dto
     */
    public synchronized Boolean setPairingDto(PairingDto pairingDtoParam) {

        this.pairingDto = pairingDtoParam;
        this.pairingDto.setWaitPairing(true);

        try {
            wait(timeout);
            this.pairingDto = null;
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        return this.isNotify;
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
