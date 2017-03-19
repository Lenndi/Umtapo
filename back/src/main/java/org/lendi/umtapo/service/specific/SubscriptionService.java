package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.SubscriptionDto;
import org.lendi.umtapo.entity.Subscription;
import org.lendi.umtapo.exception.BadSubscriptionDateException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Subscription service.
 */
@Service
public interface SubscriptionService {

    /**
     * Save subscription.
     *
     * @param entity the entity
     * @return the subscription
     * @throws BadSubscriptionDateException the bad subscription date exception
     */
    Subscription save(Subscription entity) throws BadSubscriptionDateException;

    /**
     * Find one subscription.
     *
     * @param integer the integer
     * @return the subscription
     */
    Subscription findOne(Integer integer);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Subscription> findAll();

    /**
     * Delete.
     *
     * @param integer the integer
     */
    void delete(Integer integer);

    /**
     * Save SubscriptionDto
     *
     * @param subscriptionDto the subscription dto
     * @return a subscription dto
     * @throws BadSubscriptionDateException the bad subscription date exception
     */
    SubscriptionDto saveDto(SubscriptionDto subscriptionDto) throws BadSubscriptionDateException;

    /**
     * Exists boolean.
     *
     * @param integer the integer
     * @return the boolean
     */
    Boolean exists(Integer integer);
}
