package org.lendi.umtapo.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Borrower mapper generic.
 *
 * Created by axel on 05/12/16.
 */
@Component
public class BorrowerMapper extends ConfigurableMapper {

    private static final MapperFacade DTO_MAPPER;
    private static final MapperFacade DOCUMENT_MAPPER;
    private static final MapperFacade DTO_DOCUMENT_MAPPER;
    private static final MapperFacade MAPPER_PATCH;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(ZonedDateTime.class));

        mapperFactory.classMap(Borrower.class, BorrowerDto.class).exclude("library").byDefault().register();
        DTO_MAPPER = mapperFactory.getMapperFacade();

        mapperFactory.classMap(Borrower.class, BorrowerDocument.class)
                .field("address.id", "address.addressId")
                .byDefault()
                .register();
        DOCUMENT_MAPPER = mapperFactory.getMapperFacade();

        mapperFactory.classMap(BorrowerDto.class, BorrowerDocument.class)
                .field("address.id", "address.addressId")
                .byDefault()
                .register();
        DTO_DOCUMENT_MAPPER = mapperFactory.getMapperFacade();
    }

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Borrower.class, JsonNode.class)
                .customize(new CustomMapper<Borrower, JsonNode>() {
                    @Override
                    public void mapAtoB(Borrower item, JsonNode jsonNode, MappingContext mappingContext) {
                        for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields(); it.hasNext(); ) {
                            Map.Entry<String, JsonNode> elt = it.next();
                            for (Field field : item.getClass().getDeclaredFields()) {
                                field.setAccessible(true);
                                if (field.getName().equals(elt.getKey())) {
                                    Object value = jsonNode.get(elt.getKey());
                                    try {
                                        if (Integer.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().intValue();
                                        } else if (Date.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().asText();
                                        } else if (boolean.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().asBoolean();
                                        }
                                        field.set(item, value);
                                    } catch (IllegalAccessException e) {
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
     * Instantiates a new Borrower mapper.
     */
    public BorrowerMapper() {
    }

    /**
     * Map borrower to borrower dto.
     *
     * @param borrower the borrower
     * @return the borrower dto
     */
    public BorrowerDto mapBorrowerToBorrowerDto(Borrower borrower) {
        return DTO_MAPPER.map(borrower, BorrowerDto.class);
    }

    /**
     * Map borrower dto to borrower.
     *
     * @param borrowerDto the borrower dto
     * @return the borrower
     */
    public Borrower mapBorrowerDtoToBorrower(BorrowerDto borrowerDto) {
        return DTO_MAPPER.map(borrowerDto, Borrower.class);
    }

    /**
     * Map item to item dto.
     *
     * @param borrower the borrower
     * @param jsonNode the json node
     */
    public void mergeItemAndJsonNode(Borrower borrower, JsonNode jsonNode) {
        MAPPER_PATCH.map(borrower, jsonNode);
    }

    /**
     * Map borrower to borrower document borrower document.
     *
     * @param borrower the borrower
     * @return the borrower document
     */
    public BorrowerDocument mapBorrowerToBorrowerDocument(Borrower borrower) {
        return DOCUMENT_MAPPER.map(borrower, BorrowerDocument.class);
    }

    /**
     * Map borrower documentto borrower borrower.
     *
     * @param borrowerDocument the borrower document
     * @return the borrower
     */
    public Borrower mapBorrowerDocumenttoBorrower(BorrowerDocument borrowerDocument) {
        return DOCUMENT_MAPPER.map(borrowerDocument, Borrower.class);
    }

    /**
     * Map borrower to borrower document borrower document.
     *
     * @param borrowerDto the borrower
     * @return the borrower document
     */
    public BorrowerDocument mapBorrowerDtoToBorrowerDocument(BorrowerDto borrowerDto) {
        return DTO_DOCUMENT_MAPPER.map(borrowerDto, BorrowerDocument.class);
    }

    /**
     * Map borrower documentto borrower borrower.
     *
     * @param borrowerDocument the borrower document
     * @return the borrower
     */
    public BorrowerDto mapBorrowerDocumenttoBorrowerDto(BorrowerDocument borrowerDocument) {
        return DTO_DOCUMENT_MAPPER.map(borrowerDocument, BorrowerDto.class);
    }
}
