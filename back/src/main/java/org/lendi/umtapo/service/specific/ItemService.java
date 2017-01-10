package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Item service.
 */
@Service
public interface ItemService extends GenericService<Item, Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    Item save(Item item);

    /**
     * Save dto item dto.
     *
     * @param itemDto the item dto
     * @return the item dto
     */
    ItemDto saveDto(ItemDto itemDto);

    /**
     * {@inheritDoc}
     */
    @Override
    Item findOne(Integer id);

    /**
     * Find one dto item dto.
     *
     * @param id the id
     * @return the item dto
     */
    ItemDto findOneDto(Integer id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<Item> findAll();

    /**
     * Find all dto list.
     *
     * @return the list
     */
    List<ItemDto> findAllDto();

    /**
     * {@inheritDoc}
     */
    @Override
    Boolean exists(Integer id);
}
