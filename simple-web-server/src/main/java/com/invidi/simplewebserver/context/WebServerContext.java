package com.invidi.simplewebserver.context;

import java.util.List;

public interface WebServerContext {

    void addController(Object controller);
    Object getController(int n);
    List<Object> getControllers();
    void setStaticPath(String path);
    String getStaticPath();


}
