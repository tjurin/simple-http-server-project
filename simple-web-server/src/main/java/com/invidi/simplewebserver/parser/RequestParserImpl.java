package com.invidi.simplewebserver.parser;

import com.invidi.simplewebserver.exception.NoPathException;
import com.invidi.simplewebserver.model.Request;
import com.invidi.simplewebserver.model.RequestLine;
import com.invidi.simplewebserver.model.RequestMethod;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestParserImpl implements RequestParser {

    @Override
    public Request mapRequest(List<String> lines) throws NoPathException {
        if (lines.isEmpty()) {
            throw new NoPathException("Invalid request - no paths to be mapped");
        }

        Map<String, String> headers = new HashMap<>();
        //parse request
        var RequestLine = parseRequestLine(lines.get(0));

        return new Request(RequestLine, headers);
    }

    private RequestLine parseRequestLine(String requestLine) {
        var req = parseRequestmethod(requestLine);
        var path = parsePath(requestLine);
        var query = parseQueryParams(requestLine);
        return new RequestLine.RequestLineBuilder(req)
                .path(path)
                .querys(query)
                .build();
    }

    private RequestMethod parseRequestmethod(String requestLine) {
        RequestMethod req;

        //parse request
        switch (requestLine.substring(0, requestLine.indexOf(" "))) {
            case "GET":
                req = RequestMethod.GET;
                break;
            case "POST":
                req = RequestMethod.POST;
                break;
            default:
                throw new UnsupportedOperationException("Request not implemented");
        }
        return req;
    }


    private String parsePath(String requestLine) {
        String path;
        if (requestLine.contains("?"))
            path = requestLine.substring(requestLine.indexOf(" ") + 1, requestLine.indexOf("?"));
        else
            path = requestLine.substring(requestLine.indexOf(" ") + 1, requestLine.lastIndexOf(" "));
        return path;
    }


    private List<String> parseQueryParams(String requestLine) {
        List<String> params = new ArrayList<>();
        if (requestLine.contains("?")) {
            var qparam = requestLine.substring(requestLine.lastIndexOf('?') + 1, requestLine.lastIndexOf(' '));
            Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");
            Matcher matcher = pat.matcher(qparam);
            while (matcher.find()) {
                params.add(matcher.group(2));
            }
        }
        return params;
    }

}
