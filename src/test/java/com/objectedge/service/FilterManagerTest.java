package com.objectedge.service;

import com.objectedge.processor.FilterExcludeProcessor;
import com.objectedge.processor.FilterIncludeProcessor;
import com.objectedge.repository.FilterRepository;
import com.objectedge.repository.json.JsonFilterRepository;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class FilterManagerTest {

    private FilterManager filterManager;
    private JSONObject src;

    @Before
    public void initGlobal() throws IOException {
        FilterRepository filterRepository = new JsonFilterRepository("./src/main/resources/json/filter.json");
        src = new JSONObject(new JSONTokener(new FileInputStream("./src/main/resources/json/test.json")));

        filterManager = new FilterManager(filterRepository);
        filterManager.getProcessorMap().put("include", new FilterIncludeProcessor());
        filterManager.getProcessorMap().put("exclude", new FilterExcludeProcessor());
    }

    @Test
    public void filter() throws Exception {
        JSONObject dest = filterManager.filter(src, "filter-1", "filter-2");

        assertNotNull(dest);
        assertNotNull(dest.opt("propertyString"));
        assertNotNull(dest.opt("propertyInt"));
        assertNotNull(dest.opt("propertyBoolean"));
        assertNull(dest.opt("propertyArrayObject"));
        assertNull(dest.opt("propertyArrayString"));

        JSONObject subObj = dest.optJSONObject("propertyObject");
        assertNotNull(subObj);
        assertNotNull(subObj.opt("propertyString"));
        assertNotNull(subObj.opt("propertyInt"));
        assertNotNull(subObj.opt("propertyBoolean"));
        assertNull(subObj.opt("propertyArrayString"));
    }
}