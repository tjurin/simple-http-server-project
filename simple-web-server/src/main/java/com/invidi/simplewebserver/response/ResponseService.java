package com.invidi.simplewebserver.response;

import com.invidi.simplewebserver.model.HttpStatus;

import java.io.OutputStream;
import java.io.PrintWriter;


public class ResponseService {

    public static void sendFile(OutputStream outputStream, String file, String path) {
        try (var pr = new PrintWriter(outputStream)) {
            var contentType = mapHeaders(path);
            pr.println("HTTP/1.1 " + HttpStatus.OK + " " + HttpStatus.OK.getReasonPhrase());
            pr.println("Server: SomeServer");
            pr.println(contentType);
            pr.println("Cache-Control: no-cache");
            pr.println("Content-Length: " + (file.length()));
            pr.println("Connection: Closed");
            pr.println();
            pr.println(file);
            pr.flush();
        } catch (Exception e) {

        }
    }

    public static void sendData(OutputStream outputStream, String result) {
        try (var pr = new PrintWriter(outputStream)) {
            pr.println("HTTP/1.1 " + HttpStatus.OK + " " + HttpStatus.OK.getReasonPhrase());
            pr.println("Server: SomeServer");
            pr.println("Content-type: text/plain");
            pr.println("Content-Length: " + (result.length()));
            pr.println("Connection: keep-alive");
            pr.println();
            pr.println(result);
            pr.flush();
        } catch (Exception e) {

        }
    }

    private static String mapHeaders(String file) {
        var fileType = file.substring(file.lastIndexOf(".") + 1);

        switch (fileType) {
            case "html":
                return "Content-Type: text/html; charset=iso-8859-1";
            case "js":
                return "Content-Type: application/javascript";
            default:
                return "unsupported";
        }
    }

}
