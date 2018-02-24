package org.lenndi.umtapo.mapper.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * Convert price from float to integer to convert a price in cents.
 * eg: 12.5 -> 1250
 * Or from integer to float to convert cents in price.
 * eg: 125 -> 1.25
 */
public class PriceConverter extends BidirectionalConverter<Float, Integer> {
    private static final int CENT = 100;

    @Override
    public Integer convertTo(Float price, Type<Integer> destinationType, MappingContext mappingContext) {
        return (int) (price * CENT);
    }

    @Override
    public Float convertFrom(Integer price, Type<Float> destinationType, MappingContext mappingContext) {
        return (float) price / CENT;
    }
}
