package org.lendi.umtapo.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Loan;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * Loan generic mapper.
 * <p>
 * Created by axel on 10/01/17.
 */
@Component
public class LoanMapper extends ConfigurableMapper {

    private static final MapperFacade MAPPER;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(ZonedDateTime.class));
        mapperFactory.classMap(Loan.class, LoanDto.class).byDefault().register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    /**
     * Instantiates a new Loan mapper.
     */
    public LoanMapper() {
    }

    /**
     * Map loan to loan dto.
     *
     * @param loan the loan
     * @return the loan dto
     */
    public LoanDto mapLoanToLoanDto(Loan loan) {
        return MAPPER.map(loan, LoanDto.class);
    }

    /**
     * Map loan dto to loan.
     *
     * @param loanDto the loan dto
     * @return the loan
     */
    public Loan mapLoanDtoToLoan(LoanDto loanDto) {
        return MAPPER.map(loanDto, Loan.class);
    }
}

