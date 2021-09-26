package com.invidi.simplewebserver.main;

import com.invidi.simplewebserver.processor.AnnotationProcessor;
import com.invidi.simplewebserver.context.WebServerContext;
import com.invidi.simplewebserver.context.WebServerContextImpl;
import com.invidi.simplewebserver.requests.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleWebServer implements WebServer {

    private final int NUMBER_OF_THREADS = 10;
    private ExecutorService requestPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private ServerSocket server;
    private WebServerContext webServerContext = new WebServerContextImpl();

    @Override
    public void start(int port) {
        try {
            server = new ServerSocket(port, 10);
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket client = server.accept();
                requestPool.execute(new ClientHandler(client.getInputStream(), client.getOutputStream(), webServerContext));
            }
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Override
    public void stop() {
        requestPool.shutdown();
    }

    @Override
    public WebServerContext getWebContext() {
        return webServerContext;
    }
}
