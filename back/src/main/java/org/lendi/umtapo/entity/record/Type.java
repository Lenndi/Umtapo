package org.lendi.umtapo.entity.record;

import java.util.ArrayList;
import java.util.List;

/**
 * Type entity.
 */
public class Type {
    private List<String> types;

    public Type() {
        this.types = new ArrayList<>();
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void addType(String type) {
        this.types.add(type);
    }
}
