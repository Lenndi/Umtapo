package org.lendi.umtapo.service.configuration;

import org.lendi.umtapo.entity.configuration.Z3950;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Z39.50 service.
 */
@Service
public interface Z3950Service {

    /**
     * Find all map.
     *
     * @return the map
     */
    Map<Integer, Z3950> findAll();

    /**
     * Find z 3950.
     *
     * @param providerId the provider id
     * @return the z 3950
     */
    Z3950 find(int providerId);
}
