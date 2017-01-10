package org.lendi.umtapo.dao;


import org.lendi.umtapo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Item Dao.
 * <p>
 * Created by axel on 29/11/16.
 */
public interface ItemDao extends JpaRepository<Item, Integer> {
}
