package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Borrower Dao.
 *
 * Created by axel on 29/11/16.
 */
public interface BorrowerDao extends JpaRepository<Borrower, Integer> {
}
