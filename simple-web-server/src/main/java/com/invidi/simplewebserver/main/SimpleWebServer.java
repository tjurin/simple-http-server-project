package com.invidi.simplewebserver.main;

import com.invidi.simplewebserver.context.WebServerContext;

public class SimpleWebServer implements WebServer {

   @Override
   public void start(int port) {
      throw new UnsupportedOperationException("Please implement me :)");
   }

   @Override
   public void stop() {
      throw new UnsupportedOperationException("Please implement me :)");
   }

   @Override
   public WebServerContext getWebContext() {
      throw new UnsupportedOperationException("Please implement me :)");
   }
}
