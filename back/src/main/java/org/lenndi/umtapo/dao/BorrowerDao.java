package org.lenndi.umtapo.dao;

import org.lenndi.umtapo.entity.Borrower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    /**
     * Find by nfc id borrower.
     *
     * @param tagId the nfc id
     * @return the borrower
     */
    Borrower findByTagIdIgnoreCase(String tagId);
}
