package org.lendi.umtapo.mapper.converter;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.ZonedDateTime;

/**
 * Convert olderReturn date from BorrowerDocument to lateness for BorrowerDto based on actual date.
 */
public class LatenessConverter extends CustomConverter<ZonedDateTime, Boolean> {

    @Override
    public Boolean convert(ZonedDateTime olderReturn, Type<? extends Boolean> destinationType) {
        return olderReturn.isBefore(ZonedDateTime.now());
    }
}
