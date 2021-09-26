package com.invidi.simplewebserver.processor;

import com.invidi.simplewebserver.annotations.Path;
import com.invidi.simplewebserver.annotations.RestController;
import com.invidi.simplewebserver.context.WebServerContext;
import com.invidi.simplewebserver.context.WebServerContextImpl;
import com.invidi.simplewebserver.model.RequestLine;
import com.invidi.simplewebserver.model.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class AnnotationProcessor {

    private WebServerContext webServerContext;

    public AnnotationProcessor(WebServerContext webServerContext) {
        this.webServerContext = webServerContext;
    }

    public HashMap<Integer, HelperObject> mapControllers() {
        HashMap<Integer, HelperObject> paths = new HashMap<>();
        webServerContext.getControllers().forEach(controller -> {
            buildPathFromAnnotations(controller, paths);
        });
        return paths;
    }

    private void buildPathFromAnnotations(Object object, Map<Integer, HelperObject> map) {
        var rootPath = object.getClass().getAnnotation(RestController.class).value();
        Method[] methods = object.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            var methodPath = methods[i].getDeclaredAnnotation(Path.class).value();
            var requestmethod = methods[i].getDeclaredAnnotation(Path.class).method();

            map.put(i,
                    new HelperObject(rootPath + methodPath,
                            requestmethod, object.getClass().getName(),
                            methods[i].getName()
                    ));
        }
    }

    public class HelperObject {

        private String path;
        private RequestMethod requestMethod;
        private String className;
        private String methodName;

        public HelperObject(String path, RequestMethod requestMethod, String className, String methodName) {
            this.path = path;
            this.requestMethod = requestMethod;
            this.className = className;
            this.methodName = methodName;
        }

        public String getPath() {
            return path;
        }

        public RequestMethod getRequestMethod() {
            return requestMethod;
        }

        public String getClassName() {
            return className;
        }

        public String getMethodName() {
            return methodName;
        }
    }


}
