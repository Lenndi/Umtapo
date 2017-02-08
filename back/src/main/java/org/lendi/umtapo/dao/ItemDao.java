package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.enumeration.Condition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * Find by name containing ignore case page.
     *
     * @param contains the contains
     * @param pageable the pageable
     * @return the page
     */
    Page<Item> findByRecordIdentifierBarCodeContainingIgnoreCase(String contains, Pageable pageable);

    /**
     * Find by name containing ignore case page.
     *
     * @param contains the contains
     * @param pageable the pageable
     * @return the page
     */
    Page<Item> findByRecordTitleMainTitleContainingIgnoreCase(String contains, Pageable pageable);
}
