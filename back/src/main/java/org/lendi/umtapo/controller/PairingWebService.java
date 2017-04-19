package org.lendi.umtapo.controller;

import org.lendi.umtapo.dto.PairingDto;
import org.lendi.umtapo.service.specific.implementation.PairingServiceImpl;
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
public class PairingWebService extends Thread{

    private final PairingServiceImpl pairingService;

    public PairingWebService(PairingServiceImpl pairingService) {
        this.pairingService = pairingService;
    }

    @RequestMapping(value = "/pairing", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity setPairing(@RequestBody PairingDto pairingDto) {

        if (pairingDto != null) {
            if (pairingDto.getPairingType() == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            pairingService.setPairingDto(pairingDto);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
