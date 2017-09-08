package com.objectedge.repository.json;

import com.objectedge.model.Filter;
import com.objectedge.repository.FilterRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class JsonFilterRepository implements FilterRepository {

    private Map<String, Set<String>> filters = new HashMap<>();

    public JsonFilterRepository(String pathToFile) throws FileNotFoundException {
        JSONArray filters = new JSONObject(new JSONTokener(new FileInputStream(pathToFile))).getJSONArray("filters");
        for (int index = 0; index < filters.length(); ++index) {
            JSONObject filter = filters.getJSONObject(index);
            this.filters.put(filter.getString("id"), new HashSet(filter.getJSONArray("properties").toList()));
        }
    }

    @Override
    public Filter getById(String id) {

        if (filters.containsKey(id)) {
            Filter filter = new Filter();
            filter.setId(id);
            filter.setProperties(filters.get(id));
            return filter;
        }
        return null;
    }

    @Override
    public Collection<Filter> getAll() {
        Set<Filter> filters = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : this.filters.entrySet()) {
            Filter filter = new Filter();
            filter.setProperties(entry.getValue());
            filter.setId(entry.getKey());
            filters.add(filter);
        }
        return filters;
    }

    @Override
    public void save(Filter filter) {

    }
}
