package org.lendi.umtapo.service.specific.implementation;

import org.lendi.umtapo.dao.ItemDao;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.mapper.ItemMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Item service implementation.
 */
@Service
public class ItemServiceImpl extends AbstractGenericService<Item, Integer> implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemDao itemDao;

    /**
     * Instantiates a new Item service.
     *
     * @param itemMapper the item mapper
     * @param itemDao
     */
    @Autowired
    public ItemServiceImpl(ItemMapper itemMapper, ItemDao itemDao) {
        Assert.notNull(itemMapper, "Argument itemMapper cannot be null");
        Assert.notNull(itemDao, "Argument itemDao cannot be null");

        this.itemMapper = itemMapper;
        this.itemDao = itemDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto saveDto(ItemDto itemDto) {
        Integer previousInternalId = this.itemDao.findTopInternalId();

        Item item = this.itemMapper.mapItemDtoToItem(itemDto);
        if (item.getInternalId() == null) {
            item.setInternalId(previousInternalId + 1);
        }
        item = this.save(item);

        return this.itemMapper.mapItemToItemDto(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto findOneDto(Integer id) {
        Item item = this.findOne(id);

        return this.itemMapper.mapItemToItemDto(item);
    }

    @Override
    public List<ItemDto> findAllDto() {
        return mapLibrariesToLibrariesDTO(this.findAll());
    }

    private Item mapItemDtoToItem(ItemDto itemDto) {
        return this.itemMapper.mapItemDtoToItem(itemDto);
    }

    private ItemDto mapItemToItemDto(Item item) {
        return this.itemMapper.mapItemToItemDto(item);
    }

    private List<Item> mapLibrariesDtoToLibraries(List<ItemDto> librariesDto) {
        List<Item> libraries = new ArrayList<>();
        librariesDto.forEach(ItemDto -> libraries.add(mapItemDtoToItem(ItemDto)));

        return libraries;
    }

    private List<ItemDto> mapLibrariesToLibrariesDTO(List<Item> libraries) {
        List<ItemDto> librariesDto = new ArrayList<>();
        libraries.forEach(Item -> librariesDto.add(mapItemToItemDto(Item)));

        return librariesDto;
    }
}
