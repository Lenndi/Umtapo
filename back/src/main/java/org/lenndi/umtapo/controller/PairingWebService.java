package org.lenndi.umtapo.controller;

import org.lenndi.umtapo.dto.PairingDto;
import org.lenndi.umtapo.enumeration.PairingType;
import org.lenndi.umtapo.service.specific.implementation.PairingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by axel on 14/04/17.
 */
@RestController
public class PairingWebService extends Thread {

    private final PairingServiceImpl pairingService;

    /**
     * Instantiates a new Pairing web service.
     *
     * @param pairingService the pairing service
     */
    public PairingWebService(PairingServiceImpl pairingService) {
        this.pairingService = pairingService;
    }

    /**
     * Sets pairing.
     *
     * @param pairingDto the pairing dto
     * @return the pairing
     */
    @RequestMapping(value = "/pairing/borrower", method = RequestMethod.POST, produces = {MediaType
            .APPLICATION_JSON_VALUE})
    public ResponseEntity pairingBorrower(@RequestBody PairingDto pairingDto) {

        if (pairingDto != null) {
            pairingDto.setPairingType(PairingType.BORROWER);
            pairingService.setPairingDto(pairingDto);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Sets pairing.
     *
     * @param pairingDto the pairing dto
     * @return the pairing
     */
    @RequestMapping(value = "/pairing/item", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity pairingItem(@RequestBody PairingDto pairingDto) {

        if (pairingDto != null) {
            pairingDto.setPairingType(PairingType.ITEM);
            pairingService.setPairingDto(pairingDto);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
