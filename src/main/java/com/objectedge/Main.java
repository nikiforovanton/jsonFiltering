package com.objectedge;

import com.objectedge.service.FilterManager;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring/business-config.xml");

        FilterManager filterManager = (FilterManager) context.getBean("filterManager");

        JSONObject src = jsonObject();
        System.out.println(src);

        JSONObject json = filterManager.filter(src, "filter-1", null, "filter-2", "filter-3");
        System.out.println(json);
    }

    private static JSONObject jsonObject() throws FileNotFoundException {
        return new JSONObject(new JSONTokener(new FileInputStream("./src/main/resources/json/test.json")));
    }
}
