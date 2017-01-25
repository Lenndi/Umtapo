package org.lendi.umtapo.dao;

import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.enumeration.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

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

    @Modifying
    @Query("update Item i set i.condition = ?1 where i.id = ?2")
    Integer saveConditonById(Condition condition, Integer id);

    @Modifying
    @Query("update Item i set i.loanable = true where i.id = ?2")
    Integer saveIsLoanableById(Integer id);
}
