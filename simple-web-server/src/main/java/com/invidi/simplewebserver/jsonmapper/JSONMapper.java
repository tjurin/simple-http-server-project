package com.invidi.simplewebserver.jsonmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JSONMapper {

    public static String mapToJSON(Map<String, Object> values){
        String s = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(values);
        } catch (JsonProcessingException e){
            System.err.println(e.getMessage());
        }
        return s;
    }
}
