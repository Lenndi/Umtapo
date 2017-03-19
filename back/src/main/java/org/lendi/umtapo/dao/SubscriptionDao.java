package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

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

    /**
     * Retrieve by dates included in range list.
     *
     * @param start      the start
     * @param end        the end
     * @param borrowerId the borrower id
     * @return the list
     */
    @Query("SELECT s FROM Subscription s "
            + "WHERE (:start > s.start AND :start < s.end) OR (:end > s.start AND :end < s.end) "
            + "AND s.borrower.id = :borrowerId")
    List<Subscription> retrieveByDatesIncludedInRange(
            @Param("start") ZonedDateTime start,
            @Param("end") ZonedDateTime end,
            @Param("borrowerId") Integer borrowerId);
}
