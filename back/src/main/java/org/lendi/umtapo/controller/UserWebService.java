package org.lendi.umtapo.controller;

import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * User web service.
 *
 * Created by axel on 29/11/16.
 */
@RestController
public class UserWebService {

    private UserService userService;

    @Autowired
    public UserWebService(UserService userService) {
        Assert.notNull(userService);
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUser(@PathVariable Integer id) {
        User user = this.userService.findOne(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity setUser(@RequestBody User user) {

        return new ResponseEntity(userService.save(user), HttpStatus.OK);
    }

    public UserService getUserService() {
        return userService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
