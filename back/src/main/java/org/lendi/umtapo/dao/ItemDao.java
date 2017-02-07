package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Item Dao.
 */
@Transactional
public interface ItemDao extends JpaRepository<Item, Integer> {

    /**
     * Find top internal id integer.
     *
     * @return the integer
     */
    @Query("select max(internalId) from Item")
    Integer findTopInternalId();

    /**
     * Find by internal id integer.
     *
     * @param internalId the internal id
     * @return the integer
     */
    Item findByInternalId(Integer internalId);
}
