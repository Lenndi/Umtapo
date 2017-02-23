package org.lendi.umtapo.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.log4j.Logger;
import org.lendi.umtapo.dto.LoanDto;
import org.lendi.umtapo.entity.Loan;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Map;

/**
 * Loan generic mapper.
 * <p>
 * Created by axel on 10/01/17.
 */
@Component
public class LoanMapper extends ConfigurableMapper {

    private static final Logger LOGGER = Logger.getLogger(ItemMapper.class);

    private static final MapperFacade MAPPER;
    private static final MapperFacade MAPPER_PATCH;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(ZonedDateTime.class));
        mapperFactory.classMap(Loan.class, LoanDto.class).byDefault().register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Loan.class, JsonNode.class)
                .customize(new CustomMapper<Loan, JsonNode>() {
                    @Override
                    public void mapAtoB(Loan loan, JsonNode jsonNode, MappingContext mappingContext) {
                        for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields(); it.hasNext();) {
                            Map.Entry<String, JsonNode> elt = it.next();
                            for (final Field field : loan.getClass().getDeclaredFields()) {
                                field.setAccessible(true);
                                if (field.getName().equals(elt.getKey())) {
                                    Object value = jsonNode.get(elt.getKey());
                                    try {
                                        if (Integer.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().intValue();
                                        } else if (ZonedDateTime.class.isAssignableFrom(field.getType())) {
                                            value = ZonedDateTime.parse(elt.getValue().textValue());
                                        } else if (Boolean.class.isAssignableFrom(field.getType())) {
                                            value = elt.getValue().asBoolean();
                                        }
                                        field.set(loan, value);
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
     * Map item to item dto.
     *
     * @param loan     the loan
     * @param jsonNode the json node
     */
    public void mergeLoanAndJsonNode(Loan loan, JsonNode jsonNode) throws IllegalAccessException {
        MAPPER_PATCH.map(loan, jsonNode);
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

