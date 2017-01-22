package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Item Dao.
 */
public interface LoanDao extends JpaRepository<Loan, Integer> {

}
