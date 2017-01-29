package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Borrower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Borrower Dao.
 * <p>
 * Created by axel on 29/11/16.
 */
@Repository
public interface BorrowerDao extends JpaRepository<Borrower, Integer> {

    /**
     * Find by name containing ignore case page.
     *
     * @param contains the contains
     * @param pageable the pageable
     * @return the page
     */
    Page<Borrower> findByNameContainingIgnoreCase(String contains, Pageable pageable);

}
