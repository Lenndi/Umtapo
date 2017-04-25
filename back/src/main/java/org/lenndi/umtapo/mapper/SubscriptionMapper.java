package org.lenndi.umtapo.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lenndi.umtapo.dto.SubscriptionDto;
import org.lenndi.umtapo.entity.Subscription;
import org.lenndi.umtapo.mapper.converter.PriceConverter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * Subscription generic mapper.
 */
@Component
public class SubscriptionMapper extends ConfigurableMapper {

    private static final MapperFacade MAPPER;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        final ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new PassThroughConverter(ZonedDateTime.class));
        converterFactory.registerConverter("priceConverter", new PriceConverter());

        mapperFactory.classMap(Subscription.class, SubscriptionDto.class)
                .fieldMap("contribution", "contribution").converter("priceConverter").add()
                .byDefault()
                .register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    /**
     * Instantiates a new Subscription mapper.
     */
    public SubscriptionMapper() {
    }

    /**
     * Map subscription to subscription dto.
     *
     * @param subscription the subscription
     * @return the subscription dto
     */
    public SubscriptionDto mapSubscriptionToSubscriptionDto(Subscription subscription) {
        return MAPPER.map(subscription, SubscriptionDto.class);
    }

    /**
     * Map subscription dto to subscription.
     *
     * @param subscriptionDto the subscription dto
     * @return the subscription
     */
    public Subscription mapSubscriptionDtoToSubscription(SubscriptionDto subscriptionDto) {
        return MAPPER.map(subscriptionDto, Subscription.class);
    }
}

