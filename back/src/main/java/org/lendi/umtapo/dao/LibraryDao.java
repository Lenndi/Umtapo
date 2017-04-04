package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Library Dao.
 */
public interface LibraryDao extends JpaRepository<Library, Integer> {

    /**
     * Get partner libraries.
     * @return libraries
     */
    List<Library> findByExternalFalse();

    /**
     * Get external libraries.
     * @return libraries
     */
    List<Library> findByExternalTrue();
}
