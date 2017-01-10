package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.ZonedDateTime;


/**
 * Loan dto.
 * <p>
 * Created by axel on 29/11/16.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LoanDto {

 private Integer id;
 private ZonedDateTime date;
 private BorrowerDto borrower;
 private ItemDto item;

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
     * Gets date.
     *
     * @return the date
     */
    public ZonedDateTime getDate() {
  return date;
 }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(ZonedDateTime date) {
  this.date = date;
 }

    /**
     * Gets borrower.
     *
     * @return the borrower
     */
    public BorrowerDto getBorrower() {
        return borrower;
    }

    /**
     * Sets borrower.
     *
     * @param borrower the borrower
     */
    public void setBorrower(BorrowerDto borrower) {
        this.borrower = borrower;
    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public ItemDto getItem() {
        return item;
    }

    /**
     * Sets item.
     *
     * @param item the item
     */
    public void setItem(ItemDto item) {
        this.item = item;
    }
}
