package org.lenndi.umtapo.dao;

import org.lenndi.umtapo.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;

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
    Page<Item> findByRecordIdentifierSerialNumberContainingIgnoreCase(String contains, Pageable pageable);

    /**
     * Find by ids list.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the list
     */
    Page<Item> findByIdIn(Collection<Integer> id, Pageable pageable);

    /**
     * Find by nfc id item.
     *
     * @param nfcIf the nfc if
     * @return the item
     */
    Item findByNfcId(String nfcIf);
}
