package com.objectedge.processor;

import com.objectedge.model.Filter;
import com.objectedge.repository.FilterRepository;
import com.objectedge.repository.json.JsonFilterRepository;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class FilterExcludeProcessorTest {

    private FilterProcessor filterProcessor;
    private FilterRepository filterRepository;
    private JSONObject src;

    @Before
    public void init() throws IOException {
        filterProcessor = new FilterExcludeProcessor();
        src = new JSONObject(new JSONTokener(new FileInputStream("./src/main/resources/json/test.json")));
        filterRepository = new JsonFilterRepository("./src/main/resources/json/filter.json");
    }

    @Test
    public void testDestinationObject() {
        JSONObject dest = filterProcessor.prepareDestinationObject(src, null);

        assertNotNull(dest);
        assertNotNull(dest.opt("propertyString"));
        assertNotNull(dest.opt("propertyInt"));
        assertNotNull(dest.opt("propertyBoolean"));
        assertNotNull(dest.opt("propertyArrayObject"));
        assertNotNull(dest.opt("propertyArrayString"));

        JSONObject subObj = dest.optJSONObject("propertyObject");
        assertNotNull(subObj);
        assertNotNull(subObj.opt("propertyString"));
        assertNotNull(subObj.opt("propertyInt"));
        assertNotNull(subObj.opt("propertyBoolean"));
        assertNotNull(subObj.opt("propertyArrayString"));
    }

    @Test
    public void process() throws Exception {
        Filter filter = filterRepository.getById("filter-1");

        JSONObject dest = src;
        filterProcessor.process(null, dest, filter);

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

    @Test
    public void processEmpty() throws Exception {
        Filter filter = filterRepository.getById("filter-1");

        JSONObject dest = new JSONObject();
        filterProcessor.process(null, dest, filter);

        assertNotNull(dest);
        assertNull(dest.opt("propertyString"));
        assertNull(dest.opt("propertyInt"));
        assertNull(dest.opt("propertyBoolean"));
        assertNull(dest.opt("propertyArrayObject"));
        assertNull(dest.opt("propertyArrayString"));
    }
}