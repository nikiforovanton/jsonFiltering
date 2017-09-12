package com.objectedge.repository.json;

import com.objectedge.model.Filter;
import com.objectedge.repository.FilterRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonFilterRepository implements FilterRepository {

    private static final String JSON_PROP_FILTERS = "filters";
    private static final String JSON_PROP_ID = "id";
    private static final String JSON_PROP_TYPE = "type";
    private static final String JSON_PROP_PROPERTIES = "properties";

    private Map<String, Filter> filters = new HashMap<>();

    public JsonFilterRepository(String pathToFile) throws IOException {

        try (FileInputStream fis = new FileInputStream(pathToFile)) {
            JSONArray filters = new JSONObject(new JSONTokener(fis)).optJSONArray(JSON_PROP_FILTERS);

            if (filters != null) {
                for (int index = 0; index < filters.length(); ++index) {
                    JSONObject json = filters.getJSONObject(index);

                    String id = json.getString(JSON_PROP_ID);
                    String type = json.getString(JSON_PROP_TYPE);
                    Set<String> props = new HashSet<>();

                    JSONArray jsonProps = json.getJSONArray(JSON_PROP_PROPERTIES);
                    for (int i = 0; i < jsonProps.length(); ++i) {
                        props.add(jsonProps.getString(i));
                    }

                    Filter filter = new Filter();
                    filter.setId(id);
                    filter.setType(type);
                    filter.setProperties(props);

                    this.filters.put(id, filter);
                }
            }
        }
    }

    @Override
    public Filter getById(String id) {
        return filters.get(id);
    }

    @Override
    public Collection<Filter> getAll() {
        return filters.values();
    }

    @Override
    public void save(Filter filter) {}
}
