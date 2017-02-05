package org.lendi.umtapo.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

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
