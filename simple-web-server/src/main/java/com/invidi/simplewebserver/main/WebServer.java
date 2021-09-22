package com.invidi.simplewebserver.main;

import com.invidi.simplewebserver.context.WebServerContext;

public interface WebServer {

    void start(int port);
    void stop();

    WebServerContext getWebContext();
}
