package com.objectedge.service;

import com.objectedge.model.Filter;
import org.json.JSONObject;

import java.util.Objects;

public class FilterProcessorImpl implements FilterProcessor {

    private static final String DOT = ".";

    public void process(JSONObject src, JSONObject dest, Filter filter) {
        Objects.requireNonNull(src);
        Objects.requireNonNull(dest);
        Objects.requireNonNull(filter);
        System.out.println("DEBUG\tapplying filter: " + filter.getId());

        for (String prop : filter.getProperties()) {
            processProperty(src, dest, prop);
        }
    }

    private void processProperty(JSONObject src, JSONObject dest, String prop) {
        Objects.requireNonNull(prop);
        int dotIndex = prop.indexOf(DOT);

        if (dotIndex < 0) {
            // TODO: how to apply multiple filters???
            //if (!dest.has(prop)) {
                Object propValue = src.opt(prop);
                if (propValue != null) {
                    dest.put(prop, propValue);
                } else {
                    System.out.println("WARN\tno such property: " + prop);
                }
            //}
        } else {
            String subProp = prop.substring(0, dotIndex);

            JSONObject subSrc = src.optJSONObject(subProp);
            if (subSrc != null) {
                if (!dest.has(subProp)) {
                    dest.put(subProp, new JSONObject());
                }
                JSONObject subDest = dest.getJSONObject(subProp);

                processProperty(subSrc, subDest, prop.substring(dotIndex + 1));
            } else {
                System.out.println("WARN\tno such property: " + prop);
            }
        }
    }
}
