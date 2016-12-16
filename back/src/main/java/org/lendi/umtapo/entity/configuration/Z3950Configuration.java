package org.lendi.umtapo.entity.configuration;

import java.util.Map;

/**
 * Z39.50 configuration file wrapper.
 */
public class Z3950Configuration {
    private Map<Integer, Z3950> providers;

    /**
     * Gets providers.
     *
     * @return the providers
     */
    public Map<Integer, Z3950> getProviders() {
        return providers;
    }

    /**
     * Sets providers.
     *
     * @param providers the providers
     */
    public void setProviders(Map<Integer, Z3950> providers) {
        this.providers = providers;
    }
}
