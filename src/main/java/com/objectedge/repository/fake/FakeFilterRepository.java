package com.objectedge.repository.fake;

import com.objectedge.model.Filter;
import com.objectedge.repository.FilterRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FakeFilterRepository implements FilterRepository {
    public Filter getById(String id) {
        return filter(id);
    }

    public Collection<Filter> getAll() {
        return IntStream.rangeClosed(1, 10).mapToObj(t->filter(String.valueOf(t))).collect(Collectors.toSet());
    }

    public void save(Filter filter) {

    }

    private Filter filter(String id) {
        Filter filter = new Filter();
        filter.setId(id);
        filter.setProperties(new HashSet<>(Arrays.asList("one", "two", "three")));
        return filter;
    }

    private Filter filter() {
        return filter("filter-1");
    }
}
