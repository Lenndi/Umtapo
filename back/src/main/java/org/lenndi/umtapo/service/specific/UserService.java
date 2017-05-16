package org.lenndi.umtapo.service.specific;

import org.lenndi.umtapo.entity.User;
import org.lenndi.umtapo.exception.SsoIdEqualsPasswordException;
import org.springframework.stereotype.Service;


/**
 * User service.
 * <p>
 * Created by axel on 29/11/16.
 */
@Service
public interface UserService {


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
     * @param user the user
     * @return the user
     * @throws SsoIdEqualsPasswordException the sso id equals password exception
     */
    User save(User user) throws SsoIdEqualsPasswordException;

    /**
     * save.
     *
     * @param id the id
     * @return the user
     */
    User findOne(Integer id);
}
