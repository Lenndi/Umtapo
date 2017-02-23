package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * The interface Subscription dao.
 */
@Transactional
public interface SubscriptionDao extends JpaRepository<Subscription, Integer> {

    /**
     * Find first by borrower id order by start desc borrower.
     *
     * @param borrowerId the borrower id
     * @return the borrower
     */
    Subscription findFirstByBorrowerIdOrderByStartDesc(Integer borrowerId);
}
