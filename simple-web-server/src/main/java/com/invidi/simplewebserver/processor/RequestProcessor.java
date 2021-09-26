package com.invidi.simplewebserver.processor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.invidi.simplewebserver.context.WebServerContext;
import com.invidi.simplewebserver.jsonmapper.JSONMapper;
import com.invidi.simplewebserver.model.HttpStatus;
import com.invidi.simplewebserver.model.Request;
import com.invidi.simplewebserver.model.RequestLine;
import com.invidi.simplewebserver.response.ResponseService;
import com.invidi.simplewebserver.staticserver.StaticServer;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class RequestProcessor {

    private StaticServer server;
    private WebServerContext webServerContext;
    private AnnotationProcessor annotationProcessor;
    private OutputStream outputStream;

    public RequestProcessor(OutputStream outputStream, WebServerContext webServerContext) {
        this.outputStream = outputStream;
        this.server = new StaticServer(webServerContext.getStaticPath(), outputStream);
        this.webServerContext = webServerContext;
        this.annotationProcessor = new AnnotationProcessor(webServerContext);
    }

    public void process(Request request) {
        var path = request.getRequestLine().getPath();
        if (server.doesContainFile(path)) {
            server.serveContent(path);
        } else {
            var map = annotationProcessor.mapControllers();
            var data = findPathInControllers(request.getRequestLine(), map);
            if (data.isPresent()) {
               var result = invokeMethod(filterMethod(webServerContext.getControllers().get(0).getClass()
                                .getDeclaredMethods(),
                        data.get().getMethodName()), request.getRequestLine().getQuerys());
                ResponseService.sendData(outputStream, processMethodResult(result));
            }
        }
    }

    private Optional<AnnotationProcessor.HelperObject> findPathInControllers(RequestLine requestLine, HashMap<Integer, AnnotationProcessor.HelperObject> paths) {
        var matchingPath = paths.values().stream()
                .filter(pathObject -> pathObject.getPath().equals(requestLine.getPath()))
                .filter(pathObject -> pathObject.getRequestMethod().equals(requestLine.getRequestMethod())) //add filter here for params, based on that method name can be deduced
                .findFirst();

        return matchingPath.isPresent() ? matchingPath : Optional.empty();
    }

    //assuming we will not overload methods, otherwise parameters should also be checked
    private Optional<Method> filterMethod(Method[] methods, String methodName) {
        return Arrays.stream(methods).filter(method -> method.getName().equals(methodName)).findFirst();
    }

    private Object invokeMethod(Optional<Method> method, List<String> params) {
        Object result = null;
        try {
            if (method.isPresent()) {
                result =  method.get().invoke(webServerContext.getControllers().get(0), params.toArray());
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    private String processMethodResult(Object result){
        String s = "";
        try {
            if (result != null) {
                var fieldName = result.getClass().getDeclaredFields()[0].getName();
                Field field = result.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                var fieldValue = field.get(result);
                var map = new HashMap<String, Object>();
                map.put(fieldName, fieldValue);
                s = JSONMapper.mapToJSON(map);
            }
        } catch (IllegalAccessException | NoSuchFieldException e){
            System.err.println(e.getMessage());
        }
        return s;
    }
}