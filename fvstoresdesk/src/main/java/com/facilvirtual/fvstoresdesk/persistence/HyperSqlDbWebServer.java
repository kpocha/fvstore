package com.facilvirtual.fvstoresdesk.persistence;

import java.io.IOException;
import java.util.Properties;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl;
import org.hsqldb.server.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

public class HyperSqlDbWebServer implements SmartLifecycle {
   private final Logger logger = LoggerFactory.getLogger(HyperSqlDbWebServer.class);
   private final HsqlProperties properties;
   private WebServer server;
   private boolean running = false;

   public HyperSqlDbWebServer(Properties props) {
      this.properties = new HsqlProperties(props);
   }
   @Override
   public boolean isRunning() {
      if (this.server != null) {
         this.server.checkRunning(this.running);
      }

      return this.running;
   }
   @Override
   public void start() {
      if (this.server == null) {
         this.logger.info("Starting HSQL webserver...");
         this.server = new WebServer();

         try {
            this.server.setProperties(this.properties);
            this.server.start();
            this.running = true;
         } catch (ServerAcl.AclFormatException var2) {
            this.logger.error("Error starting HSQL server.", var2);
         } catch (IOException var3) {
            this.logger.error("Error starting HSQL server.", var3);
         }
      }

   }
   @Override
   public void stop() {
      this.logger.info("Stopping HSQL server...");
      if (this.server != null) {
         this.server.stop();
         this.running = false;
      }

   }
   @Override
   public int getPhase() {
      return 0;
   }
   @Override
   public boolean isAutoStartup() {
      return true;
   }
   @Override
   public void stop(Runnable runnable) {
      this.stop();
      runnable.run();
   }
}
