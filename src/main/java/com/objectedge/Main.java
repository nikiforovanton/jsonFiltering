package com.objectedge;

import com.objectedge.service.FilterManager;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        FilterManager filterManager = new FilterManager();

        JSONObject json = filterManager.filter(jsonObject(), "filter-1", null, "filter-2", "filter-3");
        System.out.println(json);
    }

    private static JSONObject jsonObject() throws FileNotFoundException {
        return new JSONObject(new JSONTokener(new FileInputStream("./src/main/resources/test.json")));
    }
}
