package com.invidi.simplewebserver.model;

import java.util.List;
import java.util.Map;

public class RequestLine {

    private final RequestMethod requestMethod;
    private final String path;
    private final List<String> querys;

    public RequestLine(RequestLineBuilder requestLineBuilder) {
        this.requestMethod = requestLineBuilder.requestMethod;
        this.path = requestLineBuilder.path;
        this.querys = requestLineBuilder.querys;
    }

    public static class RequestLineBuilder {
        private RequestMethod requestMethod;
        private String path;
        private List<String> querys;

        public RequestLineBuilder(RequestMethod requestMethod) {
            this.requestMethod = requestMethod;
        }

        public RequestLineBuilder path(String path) {
            this.path = path;
            return this;
        }

        public RequestLineBuilder querys(List<String> querys) {
            this.querys = querys;
            return this;
        }

        public RequestLine build() {
            RequestLine requestLine = new RequestLine(this);
            return requestLine;
        }
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }

    public List<String> getQuerys() {
        return querys;
    }
}
