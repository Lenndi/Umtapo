package org.lenndi.umtapo.mapper.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Convert date from ZonedDateTime to Date.
 */
public class ZonedDateTimeConverter extends BidirectionalConverter<ZonedDateTime, Date> {
    @Override
    public Date convertTo(ZonedDateTime source, Type<Date> destinationType, MappingContext mappingContext) {
        return Date.from(source.toInstant());
    }

    @Override
    public ZonedDateTime convertFrom(Date source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
        return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
    }
}
