package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Item Dao.
 */
public interface ItemDao extends JpaRepository<Item, Integer> {
}
