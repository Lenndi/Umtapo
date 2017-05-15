package org.lenndi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lenndi.umtapo.entity.User;
import org.lenndi.umtapo.enumeration.ApplicationCodeEnum;
import org.lenndi.umtapo.exception.SsoIdEqualsPasswordException;
import org.lenndi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * User web service.
 * <p>
 * Created by axel on 29/11/16.
 */
@RestController
public class UserWebService {

    private static final Logger LOGGER = Logger.getLogger(UserWebService.class);


    private UserService userService;

    /**
     * Instantiates a new User web service.
     *
     * @param userService the user service
     */
    @Autowired
    public UserWebService(UserService userService) {
        Assert.notNull(userService);
        this.userService = userService;
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUser(@PathVariable Integer id) {
        User user = this.userService.findOne(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    /**
     * Gets user by SsoId.
     *
     * @param ssoId the ssoid
     * @return the user
     */
    @RequestMapping(value = "/users/ssoId", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUserBySsoId(@PathParam("ssoId") String ssoId) {
        User user = this.userService.findBySso(ssoId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    /**
     * Sets user.
     *
     * @param user the user
     * @return the user
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity setUser(@RequestBody User user) {

        User userResult = null;

        try {
            userResult = this.userService.save(user);
        } catch (final SsoIdEqualsPasswordException e) {
            LOGGER.error("SsoIdEqualsPasswordException exception : " + e);
            return new ResponseEntity(ApplicationCodeEnum.LOGIN_AND_PASSWORD_ARE_EQUALS, HttpStatus.CONFLICT);
        }
        return new ResponseEntity(userResult, HttpStatus.OK);
    }

    /**
     * Update user.
     *
     * @param user the user
     * @return the user
     */
    @RequestMapping(value = "/users", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateUser(@RequestBody User user) {

        User userResult = null;

        try {
            userResult = this.userService.save(user);
        } catch (final SsoIdEqualsPasswordException e) {
            LOGGER.error("SsoIdEqualsPasswordException exception : " + e);
            return new ResponseEntity(ApplicationCodeEnum.LOGIN_AND_PASSWORD_ARE_EQUALS, HttpStatus.CONFLICT);
        }
        return new ResponseEntity(userResult, HttpStatus.OK);
    }

    /**
     * Gets user service.
     *
     * @return the user service
     */
    public UserService getUserService() {
        return userService;
    }


    /**
     * Sets user service.
     *
     * @param userService the user service
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
