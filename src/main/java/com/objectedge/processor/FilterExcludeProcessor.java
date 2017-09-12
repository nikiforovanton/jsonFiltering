package com.objectedge.processor;

import com.objectedge.model.Filter;
import org.json.JSONObject;

import java.util.Objects;

public class FilterExcludeProcessor implements FilterProcessor {

    private static final String DOT = ".";

    @Override
    public JSONObject prepareDestinationObject(JSONObject src, Filter filter) {
        return new JSONObject(src, JSONObject.getNames(src));
    }

    public void process(JSONObject src, JSONObject dest, Filter filter) {
        Objects.requireNonNull(dest);
        Objects.requireNonNull(filter);
        System.out.println("DEBUG\tapplying filter: " + filter.getId());

        for (String prop : filter.getProperties()) {
            processProperty(dest, prop);
        }
    }

    private void processProperty(JSONObject dest, String prop) {
        Objects.requireNonNull(prop);
        int dotIndex = prop.indexOf(DOT);

        if (dotIndex < 0) {
            dest.remove(prop);
        } else {
            String subProp = prop.substring(0, dotIndex);

            JSONObject subDest = dest.optJSONObject(subProp);
            if (subDest != null) {
                processProperty(subDest, prop.substring(dotIndex + 1));
            } else {
                System.out.println("DEBUG\tno such property: " + prop);
            }
        }
    }
}
