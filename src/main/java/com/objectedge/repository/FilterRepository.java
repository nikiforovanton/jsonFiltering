package com.objectedge.repository;

import com.objectedge.model.Filter;

import java.util.Collection;

public interface FilterRepository {

    Filter getById(String id);
    Collection<Filter> getAll();
    void save(Filter filter);
}
