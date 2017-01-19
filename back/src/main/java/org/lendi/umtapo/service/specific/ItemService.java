package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface of Item service.
 */
@Service
public interface ItemService extends GenericService<Item, Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    Item save(Item library);

    /**
     * Persist a Item from a ItemDto.
     *
     * @param libraryDto the library dto
     * @return library dto
     */
    ItemDto saveDto(ItemDto libraryDto);

    /**
     * {@inheritDoc}
     */
    @Override
    Item findOne(Integer id);

    /**
     * Find a Item by id.
     *
     * @param id the id
     * @return ItemDto library dto
     */
    ItemDto findOneDto(Integer id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<Item> findAll();

    /**
     * Find all Libraries.
     *
     * @return list list
     */
    List<ItemDto> findAllDto();

    /**
     * {@inheritDoc}
     */
    @Override
    Boolean exists(Integer id);
}
