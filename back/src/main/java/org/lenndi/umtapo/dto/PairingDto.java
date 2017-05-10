package org.lenndi.umtapo.dto;


import org.lenndi.umtapo.enumeration.PairingType;

/**
 * Created by axel on 14/04/17.
 */
public class PairingDto {

    private String itemTag;
    private Integer borrowerId;
    private Integer itemId;
    private String cardTag;
    private String borrowerTag;
    private PairingType pairingType;

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

    /**
     * Gets card tag.
     *
     * @return the card tag
     */
    public String getCardTag() {
        return cardTag;
    }

    /**
     * Sets card tag.
     *
     * @param cardTag the card tag
     */
    public void setCardTag(String cardTag) {
        this.cardTag = cardTag;
    }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public String getItemTag() {
        return itemTag;
    }

    /**
     * Sets item id.
     *
     * @param itemTag the item id
     */
    public void setItemTag(String itemTag) {
        this.itemTag = itemTag;
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
     * Gets borrower tag.
     *
     * @return the borrower tag
     */
    public String getBorrowerTag() {
        return borrowerTag;
    }

    /**
     * Sets borrower tag.
     *
     * @param borrowerTag the borrower tag
     */
    public void setBorrowerTag(String borrowerTag) {
        this.borrowerTag = borrowerTag;
    }
}
