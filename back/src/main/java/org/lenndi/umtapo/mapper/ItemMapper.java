package org.lenndi.umtapo.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.log4j.Logger;
import org.lenndi.umtapo.dto.ItemDto;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.entity.Library;
import org.lenndi.umtapo.enumeration.Condition;
import org.lenndi.umtapo.enumeration.ItemType;
import org.lenndi.umtapo.mapper.converter.PriceConverter;
import org.lenndi.umtapo.solr.document.ItemDocument;
import org.lenndi.umtapo.solr.document.bean.record.Creator;
import org.lenndi.umtapo.solr.document.bean.record.Identifier;
import org.lenndi.umtapo.solr.document.bean.record.Publisher;
import org.lenndi.umtapo.solr.document.bean.record.Record;
import org.lenndi.umtapo.solr.document.bean.record.RecordDate;
import org.lenndi.umtapo.solr.document.bean.record.Title;
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

    private static final Logger LOGGER = Logger.getLogger(ItemMapper.class);

    private static final MapperFacade MAPPER;
    private static final MapperFacade MAPPER_PATCH;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        final ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new PassThroughConverter(ZonedDateTime.class));
        converterFactory.registerConverter("priceConverter", new PriceConverter());

        mapperFactory.classMap(Item.class, ItemDto.class)
                .fieldMap("purchasePrice", "purchasePrice").converter("priceConverter").add()
                .exclude("library")
                .fieldMap("library.id", "library.id").add()
                .fieldMap("library.name", "library.name").add()
                .byDefault()
                .register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
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
                                        } else if (Boolean.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().asBoolean();
                                        }
                                        field.set(item, value);
                                    } catch (final IllegalAccessException e) {
                                        LOGGER.error("Dynamic JsonPatch Failed" + e);
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

    /**
     * Map item to document item document.
     *
     * @param item the item
     * @return the item document
     */
    public ItemDocument mapItemToDocument(Item item) {
        Record record = item.getRecord();

        String externalLibraryId = item.getExternalLibrary() != null
                ? item.getExternalLibrary().getId().toString()
                : null;

        ItemDocument document = new ItemDocument();
        document.setId(item.getId().toString());
        document.setBorrowed(item.getBorrowed());
        document.setLoanable(item.getLoanable());
        document.setCondition(item.getCondition() != null ? item.getCondition().toString() : null);
        document.setType(item.getType() != null ? item.getType().toString() : "BOOK");
        document.setExternalLibraryId(externalLibraryId);
        document.setInternalId(item.getInternalId() != null ? item.getInternalId().toString() : null);
        document.setExternalId(item.getExternalId());
        document.setLibraryId(item.getLibrary() != null ? item.getLibrary().getId().toString() : null);
        document.setMainTitle(record.getTitle().getMainTitle());
        document.setName(record.getCreator().getName());
        document.setSecondName(record.getCreator().getSecondName());
        document.setEditorName(record.getPublisher().getEditorName());
        document.setPublicationDate(record.getDate().getPublicationDate());
        document.setSerialNumber(record.getIdentifier().getSerialNumber());

        return document;
    }

    /**
     * Map document to item item.
     *
     * @param itemDocument the item document
     * @return the item
     */
    public Item mapDocumentToItem(ItemDocument itemDocument) {
        Title title = new Title();
        title.setMainTitle(itemDocument.getMainTitle());

        Creator creator = new Creator();
        creator.setName(itemDocument.getName());
        creator.setSecondName(itemDocument.getSecondName());

        Publisher publisher = new Publisher();
        publisher.setEditorName(itemDocument.getEditorName());

        RecordDate recordDate = new RecordDate();
        recordDate.setPublicationDate(itemDocument.getPublicationDate());

        Identifier identifier = new Identifier();
        identifier.setSerialNumber(itemDocument.getSerialNumber());

        Record record = new Record();
        record.setTitle(title);
        record.setCreator(creator);
        record.setPublisher(publisher);
        record.setDate(recordDate);
        record.setIdentifier(identifier);

        Library externalLibrary;
        if (itemDocument.getExternalLibraryId() != null) {
            externalLibrary = new Library();
            externalLibrary.setId(Integer.parseInt(itemDocument.getExternalLibraryId()));
        } else {
            externalLibrary = null;
        }

        Library library;
        if (itemDocument.getLibraryId() != null) {
            library = new Library();
            library.setId(Integer.parseInt(itemDocument.getLibraryId()));
        } else {
            library = null;
        }

        Integer internalId = itemDocument.getExternalLibraryId() == null
                ? Integer.parseInt(itemDocument.getInternalId())
                : null;

        Item item = new Item();
        item.setId(Integer.parseInt(itemDocument.getId()));
        item.setBorrowed(itemDocument.getBorrowed());
        item.setLoanable(itemDocument.getLoanable());
        item.setCondition(itemDocument.getCondition() != null ? Condition.valueOf(itemDocument.getCondition()) : null);
        item.setType(itemDocument.getType() != null ? ItemType.valueOf(itemDocument.getType()) : null);
        item.setExternalLibrary(externalLibrary);
        item.setInternalId(internalId);
        item.setExternalId(itemDocument.getExternalId());
        item.setLibrary(library);
        item.setRecord(record);

        return item;
    }
}
