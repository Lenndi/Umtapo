package org.lendi.umtapo.entity.configuration;

import java.util.Map;

/**
 * Z39.50 configuration file wrapper.
 */
public class Z3950Configuration {
    private Map<Integer, Z3950> providers;

    public Map<Integer, Z3950> getProviders() {
        return providers;
    }

    public void setProviders(Map<Integer, Z3950> providers) {
        this.providers = providers;
    }
}
