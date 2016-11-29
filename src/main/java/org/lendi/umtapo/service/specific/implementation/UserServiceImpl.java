package org.lendi.umtapo.service.specific.implementation;


import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.service.generic.implementation.GenericServiceImpl;
import org.lendi.umtapo.service.specific.UserService;
import org.springframework.stereotype.Service;


/**
 * Created by axel on 29/11/16.
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, Integer> implements UserService {

}
