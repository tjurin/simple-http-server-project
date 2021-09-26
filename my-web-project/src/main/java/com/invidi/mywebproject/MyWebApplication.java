package com.invidi.mywebproject;

import com.invidi.mywebproject.controllers.MyController;
import com.invidi.simplewebserver.main.SimpleWebServer;
import com.invidi.simplewebserver.main.WebServer;

public class MyWebApplication {

   public static void main(String[] args) {
      final WebServer ws = new SimpleWebServer();

      ws.getWebContext().addController(new MyController());
      ws.getWebContext().setStaticPath("/static");

      ws.start(8080);
   }
}
