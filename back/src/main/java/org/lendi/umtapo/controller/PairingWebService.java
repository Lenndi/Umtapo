package org.lendi.umtapo.controller;

import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.service.specific.UserService;
import org.lendi.umtapo.service.specific.implementation.PairingServiceImpl;
import org.restlet.resource.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by axel on 09/04/17.
 */
@RestController
public class PairingWebService {

    private final UserService userService;
    private final PairingServiceImpl pairingServiceImpl;

    /**
     * Instantiates a new Pairing web service.
     *
     * @param userService        the user service
     * @param pairingServiceImpl the pairing service
     */
    public PairingWebService(UserService userService, PairingServiceImpl pairingServiceImpl) {
        this.userService = userService;
        this.pairingServiceImpl = pairingServiceImpl;
    }

    /**
     * Set pairing user response entity.
     *
     * @return the response entity
     */
    @Post
    @RequestMapping("/pairing")
    public ResponseEntity setPairingUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findBySso(auth.getPrincipal().toString());
        this.pairingServiceImpl.setUserIdFuture(user.getId());

        return new ResponseEntity(user, HttpStatus.OK);
    }
}
