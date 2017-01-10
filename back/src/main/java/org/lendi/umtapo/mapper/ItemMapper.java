package org.lendi.umtapo.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * Item generic mapper.
 *
 * Created by axel on 10/01/17.
 */
@Component
public class ItemMapper extends ConfigurableMapper {

    private static final MapperFacade MAPPER;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(ZonedDateTime.class));
        mapperFactory.classMap(Item.class, ItemDto.class).byDefault().register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    /**
     * Instantiates a new Item mapper.
     */
    public ItemMapper() {
    }

    /**
     * Map item to item dto.
     *
     * @param item the item
     * @return the item dto
     */
    public ItemDto mapItemToItemDto(Item item) {
        return MAPPER.map(item, ItemDto.class);
    }

    /**
     * Map item dto to item.
     *
     * @param itemDto the item dto
     * @return the item
     */
    public Item mapItemDtoToItem(ItemDto itemDto) {
        return MAPPER.map(itemDto, Item.class);
    }
}

