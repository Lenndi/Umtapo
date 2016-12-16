package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Borrower Dao.
 * <p>
 * Created by axel on 29/11/16.
 */
public interface BorrowerDao extends JpaRepository<Borrower, Integer> {
}
