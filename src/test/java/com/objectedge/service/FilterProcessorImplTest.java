package com.objectedge.service;

import com.objectedge.model.Filter;
import com.objectedge.repository.FilterRepository;
import com.objectedge.repository.json.JsonFilterRepository;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class FilterProcessorImplTest {

    private FilterProcessor filterProcessor;
    private FilterRepository filterRepository;
    private JSONObject src;
    private JSONObject dest;
    private Filter filter;

    @Before
    public void init() throws FileNotFoundException {
        filterProcessor = new FilterProcessorImpl();

        src = new JSONObject(new JSONTokener(new FileInputStream("./src/main/resources/test.json")));
        dest = new JSONObject();

        filterRepository = new JsonFilterRepository("./src/main/resources/filter.json");
        filter = filterRepository.getById("filter-1");
    }

    @Test
    public void process() throws Exception {
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
}