package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;


/**
 * User service.
 *
 * Created by axel on 29/11/16.
 */
@Service
public interface UserService extends GenericService<User,Integer> {

 User findBySso(String sso);
}
