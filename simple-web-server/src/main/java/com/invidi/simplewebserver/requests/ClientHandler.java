package com.invidi.simplewebserver.requests;

import com.invidi.simplewebserver.context.WebServerContext;
import com.invidi.simplewebserver.exception.NoPathException;
import com.invidi.simplewebserver.processor.AnnotationProcessor;
import com.invidi.simplewebserver.parser.RequestParser;
import com.invidi.simplewebserver.parser.RequestParserImpl;
import com.invidi.simplewebserver.processor.RequestProcessor;

import java.io.*;
import java.util.ArrayList;


public class ClientHandler implements Runnable {
    private InputStream inputStream;
    private OutputStream outputStream;
    private RequestParser requestParser = new RequestParserImpl();
    private RequestProcessor requestProcessor;


    public ClientHandler(InputStream inputStream, OutputStream outputStream, WebServerContext webServerContext) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.requestProcessor = new RequestProcessor(outputStream, webServerContext);
    }

    @Override
    public void run() {
        try (var pr = new PrintWriter(outputStream);
             var reader = new BufferedReader(new InputStreamReader(this.inputStream))) {
            var lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null && (!line.isBlank()) || (!line.isEmpty())) {
                lines.add(line);
            }
            requestProcessor.process(requestParser.mapRequest(lines));
            pr.println("Received request");
            pr.flush();

        } catch (IOException | NoPathException | UnsupportedOperationException unsupportedOperationException) {
            System.err.println(unsupportedOperationException.getMessage());
        }

    }


}
