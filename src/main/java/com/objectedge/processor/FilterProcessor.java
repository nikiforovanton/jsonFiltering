package com.objectedge.processor;

import com.objectedge.model.Filter;
import org.json.JSONObject;

public interface FilterProcessor {

    JSONObject prepareDestinationObject(JSONObject src, Filter filter);
    void process(JSONObject src, JSONObject dest, Filter filter);
}
