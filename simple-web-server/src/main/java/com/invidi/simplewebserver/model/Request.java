package com.invidi.simplewebserver.model;

import java.util.Map;

public class Request {

    private RequestLine requestLine;
    private Map<String, String> headers;

    public Request(RequestLine requestLine, Map<String, String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}



