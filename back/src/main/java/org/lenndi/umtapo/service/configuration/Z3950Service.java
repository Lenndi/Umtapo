package org.lenndi.umtapo.service.configuration;

import org.lenndi.umtapo.entity.configuration.Z3950;

import java.util.Map;

/**
 * Z39.50 service.
 */
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
