package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Item service.
 */
@Service
public interface ItemService extends GenericService<Item, Integer> {

    /**
     * Persist a Item.
     *
     * @param library library
     * @return ItemDto
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
     * Find a Item by id.
     *
     * @param id library id
     * @return ItemDto
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
     * Find all Libraries.
     *
     * @return List<ItemDto>
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
     * Check if Item exist.
     *
     * @return true if exist.
     */
    @Override
    Boolean exists(Integer id);
}
