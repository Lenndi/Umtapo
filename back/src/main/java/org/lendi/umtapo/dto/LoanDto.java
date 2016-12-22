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
 private BorrowerDto borrowerDto;
 private ItemDto itemDto;

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
     * Gets borrower dto.
     *
     * @return the borrower dto
     */
    public BorrowerDto getBorrowerDto() {
  return borrowerDto;
 }

    /**
     * Sets borrower dto.
     *
     * @param borrowerDto the borrower dto
     */
    public void setBorrowerDto(BorrowerDto borrowerDto) {
  this.borrowerDto = borrowerDto;
 }

    /**
     * Gets item dto.
     *
     * @return the item dto
     */
    public ItemDto getItemDto() {
  return itemDto;
 }

    /**
     * Sets item dto.
     *
     * @param itemDto the item dto
     */
    public void setItemDto(ItemDto itemDto) {
  this.itemDto = itemDto;
 }
}
