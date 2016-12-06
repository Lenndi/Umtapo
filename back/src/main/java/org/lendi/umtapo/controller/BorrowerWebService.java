package org.lendi.umtapo.controller;

import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by axel on 05/12/16.
 */
@RestController
public class BorrowerWebService {

 @Autowired
 private BorrowerService borrowerService;

 @RequestMapping(value = "/borrowers/{id}", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE})
 public ResponseEntity<BorrowerDto> getBorrower(@PathVariable Integer id){

  return new ResponseEntity<>(borrowerService.find(id), HttpStatus.OK);
 }

 @RequestMapping(value = "/borrowers", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE})
 public ResponseEntity<BorrowerDto> getBorrowers(){

  return new ResponseEntity<>(borrowerService.find(id), HttpStatus.OK);
 }

 @RequestMapping(value = "/borrowers", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
         produces = { MediaType.APPLICATION_JSON_VALUE})
 public ResponseEntity<BorrowerDto> setUser(@RequestBody BorrowerDto borrowerDto){

  return new ResponseEntity<>(borrowerService.save(borrowerDto), HttpStatus.OK);
 }
}
