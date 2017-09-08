package com.objectedge.service;

import com.objectedge.model.Filter;
import org.json.JSONObject;

public interface FilterProcessor {

    void process(JSONObject src, JSONObject dest, Filter filter);
}
