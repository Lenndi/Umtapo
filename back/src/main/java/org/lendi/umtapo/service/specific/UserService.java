package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;


/**
 * User service.
 * <p>
 * Created by axel on 29/11/16.
 */
@Service
public interface UserService extends GenericService<User, Integer> {

    /**
     * Find by sso user.
     *
     * @param sso the sso
     * @return the user
     */
    User findBySso(String sso);
}
