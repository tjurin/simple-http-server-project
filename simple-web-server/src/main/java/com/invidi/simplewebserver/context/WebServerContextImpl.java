package com.invidi.simplewebserver.context;

import java.util.ArrayList;
import java.util.List;

public class WebServerContextImpl implements WebServerContext {

    private List<Object> controllers = new ArrayList<>();
    private String path;

    @Override
    public void addController(Object controller) {
        this.controllers.add(controller);
    }

    @Override
    public Object getController(int n) {
        return null;
    }

    @Override
    public List<Object> getControllers() {
        return this.controllers;
    }

    @Override
    public void setStaticPath(String path) {
        this.path = path;
    }

    @Override
    public String getStaticPath() {
        return this.path;
    }
}
