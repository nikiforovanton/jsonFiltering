package com.objectedge.service;

import com.objectedge.model.Filter;
import com.objectedge.processor.FilterProcessor;
import com.objectedge.repository.FilterRepository;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilterManager {

    private FilterRepository filterRepository;
    private Map<String, FilterProcessor> processorMap = new HashMap<>();

    public FilterManager(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    public Map<String, FilterProcessor> getProcessorMap() {
        return processorMap;
    }

    public void setProcessorMap(Map<String, FilterProcessor> processorMap) {
        this.processorMap = processorMap;
    }

    public JSONObject filter(JSONObject src, String... filterIds) {
        Objects.requireNonNull(filterIds);
        return filter(src, Arrays.asList(filterIds));
    }

    public JSONObject filter(JSONObject src, Collection<String> filterIds) {
        Objects.requireNonNull(src);
        Objects.requireNonNull(filterIds);

        filterIds = filterIds.stream().filter(Objects::nonNull).collect(Collectors.toList());

        List<Filter> filters = new ArrayList<>();
        for (String filterId : filterIds) {
            Filter filter = filterRepository.getById(filterId);

            if (filter != null) {
                filters.add(filter);
            } else {
                System.out.println("WARN\tnot found filter with id: " + filterId);
            }
        }

        if (filters.isEmpty()) {
            System.out.println("INFO\tno filters to apply");
            return src;
        }

        JSONObject dest = null;

        for (Filter filter : filters) {
            FilterProcessor processor = processorMap.get(filter.getType());

            if (processor != null) {
                // initializing destination object with first valid processor.
                if (dest == null) {
                    dest = processor.prepareDestinationObject(src, filter);
                }
                processor.process(src, dest, filter);
            } else {
                System.out.printf("WARN\tnot found processor for filter '%s' with type: %s. " +
                        "Check configuration of 'processorMap'\n", filter.getId(), filter.getType());
            }
        }
        return dest;
    }
}
