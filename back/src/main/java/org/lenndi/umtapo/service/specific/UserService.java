package org.lenndi.umtapo.service.specific;

import org.lenndi.umtapo.entity.User;
import org.lenndi.umtapo.service.generic.GenericService;
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

    /**
     * save.
     *
     * @param user
     * @return the user
     */
    @Override
    User save(User user);
}
