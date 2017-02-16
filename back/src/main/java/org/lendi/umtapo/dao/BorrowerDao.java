package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Borrower Dao.
 * <p>
 * Created by axel on 29/11/16.
 */
@Repository
public interface BorrowerDao extends JpaRepository<Borrower, Integer> {
}
