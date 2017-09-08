package com.objectedge.service;

import com.objectedge.model.Filter;
import com.objectedge.repository.FilterRepository;
import com.objectedge.repository.json.JsonFilterRepository;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class FilterManager {

    private FilterRepository filterRepository;
    private FilterProcessor filterProcessor;

    // inject
    public FilterManager(FilterRepository filterRepository, FilterProcessor filterProcessor) {
        this.filterRepository = filterRepository;
        this.filterProcessor = filterProcessor;
    }

    public FilterManager() throws FileNotFoundException {
        filterRepository = new JsonFilterRepository("./src/main/resources/filter.json");
        filterProcessor = new FilterProcessorImpl();
    }

    public JSONObject filter(JSONObject src, String... filterIds) {
        Objects.requireNonNull(filterIds);
        return filter(src, Arrays.asList(filterIds));
    }

    public JSONObject filter(JSONObject src, Collection<String> filterIds) {
        Objects.requireNonNull(src);
        Objects.requireNonNull(filterIds);

        if (filterIds.isEmpty()) {
            System.out.println("INFO\tno filters to apply");
            return src;
        }

        JSONObject dest = new JSONObject();
        for (String filterId : filterIds) {
            Filter filter = filterRepository.getById(filterId);

            if (filter == null) {
                System.out.println("WARN\tnot found filter with id: " + filterId);
                continue;
            }

            filterProcessor.process(src, dest, filter);
        }
        return dest;
    }
}
