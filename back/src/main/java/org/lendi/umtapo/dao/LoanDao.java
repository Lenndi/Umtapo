package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

/**
 * Item Dao.
 */
@Transactional
public interface LoanDao extends JpaRepository<Loan, Integer> {

    @Modifying
    @Query("update Loan l set l.end = ?1 where l.id = ?2")
    Integer saveConditonById(ZonedDateTime date, Integer id);

    @Modifying
    @Query("update Loan l set i.isReturn = true where i.id = ?2")
    Integer saveIsReturnIdById(Integer id);
}
