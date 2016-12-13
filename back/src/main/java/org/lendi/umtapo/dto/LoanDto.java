package org.lendi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;

/**
 * Loan dto.
 *
 * Created by axel on 29/11/16.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LoanDto {

 private Integer id;
 private LocalDateTime date;
 private BorrowerDto borrowerDto;
 private ItemDto itemDto;

 public Integer getId() {
  return id;
 }

 public void setId(Integer id) {
  this.id = id;
 }

 public LocalDateTime getDate() {
  return date;
 }

 public void setDate(LocalDateTime date) {
  this.date = date;
 }

 public BorrowerDto getBorrowerDto() {
  return borrowerDto;
 }

 public void setBorrowerDto(BorrowerDto borrowerDto) {
  this.borrowerDto = borrowerDto;
 }

 public ItemDto getItemDto() {
  return itemDto;
 }

 public void setItemDto(ItemDto itemDto) {
  this.itemDto = itemDto;
 }
}
