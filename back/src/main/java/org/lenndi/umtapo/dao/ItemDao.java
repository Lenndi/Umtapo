package org.lenndi.umtapo.dao;

import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.entity.Library;
import org.lenndi.umtapo.entity.ShelfMark;
import org.lenndi.umtapo.enumeration.Condition;
import org.lenndi.umtapo.enumeration.ItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
     * Update item.
     *
     * @param condition       the condition
     * @param currency        the currency
     * @param externalLibrary the external library
     * @param loanable        the loanable
     * @param type            the type
     * @param shelfMark       the shelf mark
     * @param purchasePrice   the purchase price
     * @param id              the id
     * @return result code
     */
    @Modifying
    @Query("update Item item "
            + "set item.condition = ?1, item.currency = ?2, item.externalLibrary = ?3, item.loanable = ?4, "
            + "item.type = ?5, item.shelfmark = ?6, item.purchasePrice = ?7 "
            + "where item.id = ?8")
    Integer updateItem(
            Condition condition,
            String currency,
            Library externalLibrary,
            Boolean loanable,
            ItemType type,
            ShelfMark shelfMark,
            Integer purchasePrice,
            Integer id);

    /**
     * Find by nfc id item.
     *
     * @param nfcIf the nfc if
     * @return the item
     */
    Item findByNfcId(String nfcIf);
}
