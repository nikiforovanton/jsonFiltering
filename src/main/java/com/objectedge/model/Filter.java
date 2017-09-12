package com.objectedge.model;

import java.util.Set;

public class Filter {

    private String id;
    private Set<String> properties;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getProperties() {
        return properties;
    }

    public void setProperties(Set<String> properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filter)) return false;

        Filter filter = (Filter) o;

        return id != null ? id.equals(filter.id) : filter.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Filter{");
        sb.append("id='").append(id).append('\'');
        sb.append(", properties=").append(properties);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
