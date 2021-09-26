package com.invidi.mywebproject.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.invidi.simplewebserver.annotations.Path;
import com.invidi.simplewebserver.annotations.QueryParam;
import com.invidi.simplewebserver.annotations.RestController;
import com.invidi.simplewebserver.model.RequestMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController("/api")
public class MyController {

   final static Map<String, String> data = new ConcurrentHashMap<>();

   @Path(value = "/data", method = RequestMethod.POST)
   public void saveKeyValuePair(@QueryParam("key") String key, @QueryParam("value") String value) {
      System.out.println("saveKeyValuePair invoked");
      data.put(key, value);
   }

   @Path(value = "/data", method = RequestMethod.GET)
   public Result getValueByKey(@QueryParam("key") String key) {
      System.out.println("getValueByKey invoked");
      String result = data.get(key);
      return new Result(result);
   }

   public static class Result {

      @JsonProperty
      String result;

      public Result(String result) {
         this.result = result;
      }

      public void test(){

      }
   }
}
