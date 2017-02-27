package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Library Dao.
 */
public interface LibraryDao extends JpaRepository<Library, Integer> {

    /**
     * Find by borrower id library.
     *
     * @param borrowerId the borrower id
     * @return the library
     */
    Library findByBorrowersId(Integer borrowerId);
}
