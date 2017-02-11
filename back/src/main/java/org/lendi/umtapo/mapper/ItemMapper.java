package org.lendi.umtapo.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.enumeration.Condition;
import org.lendi.umtapo.mapper.converter.PriceConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * The type Item mapper.
 */
@Component
public class ItemMapper extends ConfigurableMapper {

    private static final MapperFacade MAPPER;
    private static final MapperFacade MAPPER_PATCH;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        final ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(ZonedDateTime.class));
        converterFactory.registerConverter("priceConverter", new PriceConverter());

        mapperFactory.classMap(Item.class, ItemDto.class)
                .fieldMap("purchasePrice", "purchasePrice").converter("priceConverter").add()
                .byDefault()
                .register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Item.class, JsonNode.class)
                .customize(new CustomMapper<Item, JsonNode>() {
                    @Override
                    public void mapAtoB(Item item, JsonNode jsonNode, MappingContext mappingContext) {
                        for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields(); it.hasNext();) {
                            Map.Entry<String, JsonNode> elt = it.next();
                            for (final Field field : item.getClass().getDeclaredFields()) {
                                field.setAccessible(true);
                                if (field.getName().equals(elt.getKey())) {
                                    Object value = jsonNode.get(elt.getKey());
                                    try {
                                        if (Integer.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().intValue();
                                        } else if (Date.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().asText();
                                        } else if (Condition.class.isAssignableFrom(field.getType())) {
                                            value = Condition.valueOf(jsonNode.get(field.getName()).textValue());
                                        } else if (boolean.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().asBoolean();
                                        }
                                        field.set(item, value);
                                    } catch (final IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                })
                .register();
        MAPPER_PATCH = mapperFactory.getMapperFacade();
    }

    /**
     * Instantiates a new Item mapper.
     */
    public ItemMapper() {
    }

    /**
     * Map item to item dto.
     *
     * @param item     the item
     * @param jsonNode the json node
     */
    public void mergeItemAndJsonNode(Item item, JsonNode jsonNode) {
        MAPPER_PATCH.map(item, jsonNode);
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

    /**
     * Map item to item dto item dto.
     *
     * @param item the item
     * @return the item dto
     */
    public ItemDto mapItemToItemDto(Item item) {
        return MAPPER.map(item, ItemDto.class);
    }

}
