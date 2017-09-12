package com.objectedge.processor;

import com.objectedge.model.Filter;
import com.objectedge.processor.FilterIncludeProcessor;
import com.objectedge.processor.FilterProcessor;
import com.objectedge.repository.FilterRepository;
import com.objectedge.repository.json.JsonFilterRepository;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class FilterIncludeProcessorTest {

    private FilterProcessor filterProcessor;
    private FilterRepository filterRepository;
    private JSONObject src;

    @Before
    public void init() throws IOException {
        filterProcessor = new FilterIncludeProcessor();
        src = new JSONObject(new JSONTokener(new FileInputStream("./src/main/resources/json/test.json")));
        filterRepository = new JsonFilterRepository("./src/main/resources/json/filter.json");
    }

    @Test
    public void testDestinationObject() {
        JSONObject dest = filterProcessor.prepareDestinationObject(src, null);

        assertNotNull(dest);
        assertNull(dest.opt("propertyString"));
        assertNull(dest.opt("propertyInt"));
        assertNull(dest.opt("propertyBoolean"));
        assertNull(dest.opt("propertyArrayObject"));
        assertNull(dest.opt("propertyArrayString"));
    }

    @Test
    public void process() throws Exception {
        Filter filter = filterRepository.getById("filter-1");

        JSONObject dest = new JSONObject();
        filterProcessor.process(src, dest, filter);

        assertNotNull(dest);
        assertNull(dest.opt("propertyString"));
        assertNull(dest.opt("propertyInt"));
        assertNull(dest.opt("propertyBoolean"));
        assertNotNull(dest.opt("propertyArrayObject"));
        assertNotNull(dest.opt("propertyArrayString"));

        JSONObject subObj = dest.optJSONObject("propertyObject");
        assertNotNull(subObj);
        assertNull(subObj.opt("propertyString"));
        assertNull(subObj.opt("propertyInt"));
        assertNull(subObj.opt("propertyBoolean"));
        assertNotNull(subObj.opt("propertyArrayString"));
    }

    @Test
    public void processNotEmpty() throws Exception {
        Filter filter = filterRepository.getById("filter-1");

        JSONObject dest =  new JSONObject(src, JSONObject.getNames(src));
        filterProcessor.process(src, dest, filter);

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
}