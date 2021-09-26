package com.invidi.simplewebserver.parser;

import com.invidi.simplewebserver.exception.NoPathException;
import com.invidi.simplewebserver.model.Request;
import com.invidi.simplewebserver.model.RequestMethod;

import java.util.List;

public interface RequestParser {

    Request mapRequest(List<String> lines) throws NoPathException;

}
