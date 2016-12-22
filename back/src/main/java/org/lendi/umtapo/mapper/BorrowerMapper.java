package org.lendi.umtapo.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.entity.Borrower;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * Borrower mapper générique
 * <p>
 * Created by axel on 05/12/16.
 */
@Component
public class BorrowerMapper extends ConfigurableMapper {

    private static final MapperFacade MAPPER;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(ZonedDateTime.class));
        mapperFactory.classMap(Borrower.class, BorrowerDto.class).exclude("library").byDefault().register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    /**
     * Instantiates a new Borrower mapper.
     */
    public BorrowerMapper() {
    }

    /**
     * Map borrower to borrower dto borrower dto.
     *
     * @param borrower the borrower
     * @return the borrower dto
     */
    public BorrowerDto mapBorrowerToBorrowerDto(Borrower borrower) {
        return MAPPER.map(borrower, BorrowerDto.class);
    }

    /**
     * Map borrower dto to borrower borrower.
     *
     * @param borrowerDto the borrower dto
     * @return the borrower
     */
    public Borrower mapBorrowerDtoToBorrower(BorrowerDto borrowerDto) {
        return MAPPER.map(borrowerDto, Borrower.class);
    }
}
