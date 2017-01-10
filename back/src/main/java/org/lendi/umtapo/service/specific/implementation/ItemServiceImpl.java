package org.lendi.umtapo.service.specific.implementation;

import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.mapper.ItemMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.GeoPage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by axel on 10/01/17.
 */
@Service
public class ItemServiceImpl extends AbstractGenericService<Item, Integer> implements ItemService {

    private final ItemMapper itemMapper;

    /**
     * Instantiates a new Item service.
     *
     * @param itemMapper the item mapper
     */
    @Autowired
    public ItemServiceImpl(ItemMapper itemMapper) {
        Assert.notNull(itemMapper);
        this.itemMapper = itemMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto saveDto(ItemDto itemDto) {
        Item item = this.itemMapper.mapItemDtoToItem(itemDto);
        item = this.save(item);

        return this.itemMapper.mapItemToItemDto(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto findOneDto(Integer id) {
        Item item = this.findOne(id);

        return itemMapper.mapItemToItemDto(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ItemDto> findAllDto() {
        List<Item> items = this.findAll();

        return this.mapItemsToItemDtos(items);
    }

    private Item mapItemDtoToItem(ItemDto itemDto) {
        return itemMapper.mapItemDtoToItem(itemDto);
    }

    private ItemDto mapItemToItemDto(Item item) {
        return itemMapper.mapItemToItemDto(item);
    }

    private List<Item> mapItemDtosToItems(List<ItemDto> itemDtos) {

        List<Item> items = new ArrayList<>();
        itemDtos.forEach(itemDto -> items.add(mapItemDtoToItem(itemDto)));

        return items;
    }

    private List<ItemDto> mapItemsToItemDtos(List<Item> items) {

        List<ItemDto> itemDtos = new ArrayList<>();
        items.forEach(item -> itemDtos.add(mapItemToItemDto(item)));

        return itemDtos;
    }
}
