package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.entity.Subscription;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Subscription service.
 */
@Service
public interface SubscriptionService extends GenericService<Subscription, Integer> {

    @Override
    Subscription save(Subscription entity);

    @Override
    Subscription findOne(Integer integer);

    @Override
    List<Subscription> findAll();

    @Override
    void delete(Integer integer);

    /**
     * Find latest subscription by borrower id subscription.
     *
     * @param borrowerId the borrower id
     * @return the subscription
     */
    Subscription findLatestSubscriptionByBorrowerId(Integer borrowerId);

    @Override
    Boolean exists(Integer integer);
}
