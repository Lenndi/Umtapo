package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Item Dao.
 */
public interface ItemDao extends JpaRepository<Item, Integer> {

    @Query("select max(internalId) from Item")
    Integer findTopInternalId();
}
