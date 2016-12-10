package org.lendi.umtapo.service.configuration;

import org.lendi.umtapo.entity.configuration.Z3950;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Z39.50 service.
 */
@Service
public interface Z3950Service {
    Map<Integer, Z3950> findAll();
    Z3950 find(int providerId);
}
