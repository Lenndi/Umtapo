package org.lendi.umtapo.entity;

import java.util.List;

/**
 * Z39.50 configuration file wrapper.
 */
public class Z3950Configuration {
    private List<Z3950> providers;

    public List<Z3950> getProviders() {
        return providers;
    }

    public void setProviders(List<Z3950> providers) {
        this.providers = providers;
    }
}
