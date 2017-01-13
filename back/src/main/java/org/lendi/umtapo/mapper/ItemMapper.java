package org.lendi.umtapo.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.mapper.converter.LibraryConverter;
import org.lendi.umtapo.mapper.converter.PriceConverter;
import org.springframework.stereotype.Component;

/**
 * Item entity to Item DTO mapper.
 */
@Component
public class ItemMapper extends ConfigurableMapper {

    private static final MapperFacade MAPPER;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        final ConverterFactory converterFactory = mapperFactory.getConverterFactory();

        converterFactory.registerConverter("priceConverter", new PriceConverter());
        converterFactory.registerConverter("libraryConverter", new LibraryConverter());

        mapperFactory.classMap(Item.class, ItemDto.class)
                .fieldMap("purchasePrice", "purchasePrice").converter("priceConverter").add()
                .fieldMap("library", "library").converter("libraryConverter").add()
                .byDefault()
                .register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    /**
     * Instantiates a new Item mapper.
     */
    public ItemMapper() {
    }

    /**
     * Map item to item dto item dto.
     *
     * @param item the item
     * @return the item dto
     */
    public ItemDto mapItemToItemDto(Item item) {
        return MAPPER.map(item, ItemDto.class);
    }

    /**
     * Map item dto to item item.
     *
     * @param itemDto the item dto
     * @return the item
     */
    public Item mapItemDtoToItem(ItemDto itemDto) {
        return MAPPER.map(itemDto, Item.class);
    }
}
