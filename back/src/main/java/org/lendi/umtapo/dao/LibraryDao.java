package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Library Dao.
 */
public interface LibraryDao extends JpaRepository<Library, Integer> {
}