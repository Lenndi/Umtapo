package org.lenndi.umtapo.dao;

import org.lenndi.umtapo.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Item Dao.
 */
@Transactional
public interface LoanDao extends JpaRepository<Loan, Integer> {

    /**
     * Save conditon by id integer.
     *
     * @param date the date
     * @param id   the id
     * @return the integer
     */
    @Modifying
    @Query("update Loan l set l.end = ?1 where l.id = ?2")
    Integer setEndById(ZonedDateTime date, Integer id);

    /**
     * Find by borrower id and returned false list.
     *
     * @param borrower the borrower
     * @return the list
     */
    List<Loan> findByBorrowerIdAndReturnedFalse(Integer borrower);

    /**
     * Find first by borrower id and returned false order by end asc loan.
     *
     * @param borrowerId the borrower id
     * @return the loan
     */
    Loan findFirstByBorrowerIdAndReturnedFalseOrderByEndAsc(Integer borrowerId);
}
