package com.mycompany.myapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parse(Class testClass, Class<T> parseResultClass, String fileName) throws Exception {
        InputStream in = testClass.getResourceAsStream("./" + fileName);
        InputStreamReader reader = new InputStreamReader(in, "UTF8");
        return objectMapper.readValue(reader, parseResultClass);
    }

}
