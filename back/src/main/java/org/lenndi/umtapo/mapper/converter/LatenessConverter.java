package org.lenndi.umtapo.mapper.converter;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

import java.util.Date;

/**
 * Convert olderReturn date from BorrowerDocument to lateness for BorrowerDto based on actual date.
 */
public class LatenessConverter extends CustomConverter<Date, Boolean> {

    @Override
    public Boolean convert(Date olderReturn, Type<? extends Boolean> destinationType) {
        return olderReturn.before(new Date());
    }
}
