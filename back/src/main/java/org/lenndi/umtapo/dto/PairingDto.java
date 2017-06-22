package org.lenndi.umtapo.dto;


import org.lenndi.umtapo.enumeration.PairingType;

/**
 * Created by axel on 14/04/17.
 */
public class PairingDto {


    private Integer borrowerId;
    private Integer itemId;
    private PairingType pairingType;
    private Boolean waitPairing;

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
     * Gets wait pairing.
     *
     * @return the wait pairing
     */
    public Boolean getWaitPairing() {
        return waitPairing;
    }

    /**
     * Sets wait pairing.
     *
     * @param waitPairing the wait pairing
     */
    public void setWaitPairing(Boolean waitPairing) {
        this.waitPairing = waitPairing;
    }

    /**
     * Gets borrower id.
     *
     * @return the borrower id
     */
    public Integer getBorrowerId() {
        return borrowerId;
    }

    /**
     * Sets borrower id.
     *
     * @param borrowerId the borrower id
     */
    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
