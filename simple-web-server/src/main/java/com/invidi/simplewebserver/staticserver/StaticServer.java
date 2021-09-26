package com.invidi.simplewebserver.staticserver;

import com.invidi.simplewebserver.model.HttpStatus;
import com.invidi.simplewebserver.response.ResponseService;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class StaticServer {

    private String staticPath;
    private OutputStream outputStream;

    private String resourcePath = "my-web-project/src/main/resources/";
    private List<String> indexPaths = Arrays.asList("", "/", "index.html", "/index.html");

    public StaticServer(String staticPath, OutputStream outputStream) {
        this.staticPath = staticPath;
        this.outputStream = outputStream;
    }

    public boolean doesContainFile(String path) {
        if (indexPaths.contains(path))
            return true;
        else if (isFileLocatedInStaticFolder(path))
            return true;
        return false;
    }

    public void serveContent(String path) {
        if (indexPaths.contains(path))
            ResponseService.sendFile(outputStream, readFile("/index.html"), "/index.html");
        else
            ResponseService.sendFile(outputStream, readFile(path), path);
    }

    private boolean isFileLocatedInStaticFolder(String path) {
        return Files.exists(Path.of(resourcePath + staticPath + path));
    }

    private String readFile(String path) {
        String file = "";
        try {
            file = Files.readString(Path.of(resourcePath + staticPath + path));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return file;
    }
}
