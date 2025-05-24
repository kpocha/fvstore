// package com.facilvirtual.fvstoresdesk.persistence;

// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileReader;
// import java.io.IOException;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;import org.hsqldb.Server;
// import org.hsqldb.cmdline.SqlFile;
// import org.hsqldb.cmdline.SqlToolError;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// @Component("DatabaseUpdater")
// @Order(Integer.MIN_VALUE)
// public class DatabaseUpdater {
//    private static Logger logger = LoggerFactory.getLogger("DatabaseUpdater");
//    private Connection connection;
//    private Server server;

//    public DatabaseUpdater() {
//       this.init();
//    }

//    public void init() {
//       logger.info("Iniciando DatabaseUpdater...");
//       this.initializeServer();

//       try {
//          this.connection = DriverManager.getConnection("jdbc:hsqldb:file:C:/facilvirtual/data/fvposdb;ifexists=true", "adminFvPos", "fvPos2014");
//          if (!this.existsDataModel_1_3()) {
//             this.createDataModel_1_3();
//          }

//          if (!this.existsDataModel_1_7()) {
//             this.createDataModel_1_7();
//          }

//          if (!this.existsDataModel_1_8()) {
//             this.createDataModel_1_8();
//          }

//          if (!this.existsDataModel_1_9()) {
//             this.createDataModel_1_9();
//          }

//          if (!this.existsDataModel_1_10()) {
//             this.createDataModel_1_10();
//          }

//          if (!this.existsDataModel_11_0()) {
//             this.createDataModel_11_0();
//          }
//       } catch (Exception var2) {
//          logger.error(var2.getMessage());
//          //logger.error(var2);
//       }

//       this.stop();
//       logger.info("Finalizando DatabaseUpdater...");
//    }

//    public void stop() {
//       logger.info("Stopping HSQL server...");
//       if (this.server != null) {
//          this.server.stop();
//       }

//    }

//    public void destroy() {
//       logger.info("Finalizando DatabaseUpdater...");
//       this.server.stop();
//    }

//    private void initializeServer() {
//       this.server = new Server();
//       this.server.setAddress("localhost");
//       this.server.setDatabaseName(0, "fvposdb");
//       this.server.setDatabasePath(0, "file:C:/facilvirtual/data/fvposdb");
//       this.server.setPort(this.getServerPort());
//       this.server.setTrace(true);
//       this.server.start();
//    }

//    private int getServerPort() {
//       int port;
//       try {
//          BufferedReader br = new BufferedReader(new FileReader("C:/facilvirtual/config/fvpos.properties"));
//          String serverTypeLine = br.readLine();
//          String serverPortLine = br.readLine();
//          String[] serverPortLineArray = serverPortLine.split("=");
//          String serverPort = serverPortLineArray[1].trim();
//          logger.info("Puerto de DatabaseUpdater: " + serverPort);
//          port = Integer.valueOf(serverPort);
//       } catch (Exception var7) {
//          port = 9001;
//       }

//       return port;
//    }

//    private void createDataModel_1_3() throws IOException, SQLException, SqlToolError {
//       logger.info("Creating Data Model 1.3");
//       File file = new File("C:/facilvirtual/scripts/update_1_3_01.sql");
//       if (!file.isFile()) {
//          throw new IOException("SQL File not found in " + file.getAbsolutePath());
//       } else {
//          SqlFile sqlFile = new SqlFile(file);
//          sqlFile.setConnection(this.connection);
//          sqlFile.setContinueOnError(true);
//          sqlFile.execute();
//          file = new File("C:/facilvirtual/scripts/update_1_3_02.sql");
//          if (!file.isFile()) {
//             throw new IOException("SQL File not found in " + file.getAbsolutePath());
//          } else {
//             sqlFile = new SqlFile(file);
//             sqlFile.setConnection(this.connection);
//             sqlFile.setContinueOnError(true);
//             sqlFile.execute();
//             file = new File("C:/facilvirtual/scripts/update_1_3_03.sql");
//             if (!file.isFile()) {
//                throw new IOException("SQL File not found in " + file.getAbsolutePath());
//             } else {
//                sqlFile = new SqlFile(file);
//                sqlFile.setConnection(this.connection);
//                sqlFile.setContinueOnError(true);
//                sqlFile.execute();
//                logger.info("Successfully created data model 1.3");
//             }
//          }
//       }
//    }

//    private void createDataModel_1_7() throws IOException, SQLException, SqlToolError {
//       logger.info("Creating Data Model 1.7");
//       File file = new File("C:/facilvirtual/scripts/update_1_7_01.sql");
//       if (!file.isFile()) {
//          throw new IOException("SQL File not found in " + file.getAbsolutePath());
//       } else {
//          SqlFile sqlFile = new SqlFile(file);
//          sqlFile.setConnection(this.connection);
//          sqlFile.setContinueOnError(true);
//          sqlFile.execute();
//          logger.info("Successfully created data model 1.7");
//       }
//    }

//    private void createDataModel_1_8() throws IOException, SQLException, SqlToolError {
//       logger.info("Creating Data Model 1.8");
//       File file = new File("C:/facilvirtual/scripts/update_1_8_01.sql");
//       if (!file.isFile()) {
//          throw new IOException("SQL File not found in " + file.getAbsolutePath());
//       } else {
//          SqlFile sqlFile = new SqlFile(file);
//          sqlFile.setConnection(this.connection);
//          sqlFile.setContinueOnError(true);
//          sqlFile.execute();
//          file = new File("C:/facilvirtual/scripts/update_1_8_02.sql");
//          if (!file.isFile()) {
//             throw new IOException("SQL File not found in " + file.getAbsolutePath());
//          } else {
//             sqlFile = new SqlFile(file);
//             sqlFile.setConnection(this.connection);
//             sqlFile.setContinueOnError(true);
//             sqlFile.execute();
//             logger.info("Successfully created data model 1.8");
//          }
//       }
//    }

//    private void createDataModel_1_9() throws IOException, SQLException, SqlToolError {
//       logger.info("Creating Data Model 1.9");
//       File file = new File("C:/facilvirtual/scripts/update_1_9_01.sql");
//       if (!file.isFile()) {
//          throw new IOException("SQL File not found in " + file.getAbsolutePath());
//       } else {
//          SqlFile sqlFile = new SqlFile(file);
//          sqlFile.setConnection(this.connection);
//          sqlFile.setContinueOnError(true);
//          sqlFile.execute();
//          logger.info("Successfully created data model 1.9");
//       }
//    }

//    private void createDataModel_1_10() throws IOException, SQLException, SqlToolError {
//       logger.info("Creating Data Model 1.10");
//       File file = new File("C:/facilvirtual/scripts/update_1_10_01.sql");
//       if (!file.isFile()) {
//          throw new IOException("SQL File not found in " + file.getAbsolutePath());
//       } else {
//          SqlFile sqlFile = new SqlFile(file);
//          sqlFile.setConnection(this.connection);
//          sqlFile.setContinueOnError(true);
//          sqlFile.execute();
//          file = new File("C:/facilvirtual/scripts/update_1_10_02.sql");
//          if (!file.isFile()) {
//             throw new IOException("SQL File not found in " + file.getAbsolutePath());
//          } else {
//             sqlFile = new SqlFile(file);
//             sqlFile.setConnection(this.connection);
//             sqlFile.setContinueOnError(true);
//             sqlFile.execute();
//             logger.info("Successfully created data model 1.10");
//          }
//       }
//    }

//    private void createDataModel_11_0() throws IOException, SQLException, SqlToolError {
//       logger.info("Creating Data Model 11.0");
//       File file = new File("C:/facilvirtual/scripts/update_11_0_01.sql");
//       if (!file.isFile()) {
//          throw new IOException("SQL File not found in " + file.getAbsolutePath());
//       } else {
//          SqlFile sqlFile = new SqlFile(file);
//          sqlFile.setConnection(this.connection);
//          sqlFile.setContinueOnError(true);
//          sqlFile.execute();
//          file = new File("C:/facilvirtual/scripts/update_11_0_02.sql");
//          if (!file.isFile()) {
//             throw new IOException("SQL File not found in " + file.getAbsolutePath());
//          } else {
//             sqlFile = new SqlFile(file);
//             sqlFile.setConnection(this.connection);
//             sqlFile.setContinueOnError(true);
//             sqlFile.execute();
//             logger.info("Successfully created data model 11.0");
//          }
//       }
//    }

//    private boolean existsDataModel_1_3() {
//       try {
//          logger.info("Checking if data base model is created 1.3");
//          this.connection.createStatement().executeQuery("Select BUDGET_START_NUMBER from FVPOS_APP_CONFIG");
//          logger.info("Data model created 1.3");
//          return true;
//       } catch (SQLException var2) {
//          logger.info("Data model not created 1.3");
//          return false;
//       }
//    }

//    private boolean existsDataModel_1_7() {
//       try {
//          logger.info("Checking if data base model is created 1.7");
//          this.connection.createStatement().executeQuery("Select COST_PRICE from FVPOS_ORDER_LINE");
//          logger.info("Data model created 1.7");
//          return true;
//       } catch (SQLException var2) {
//          logger.info("Data model not created 1.7");
//          return false;
//       }
//    }

//    private boolean existsDataModel_1_8() {
//       try {
//          logger.info("Checking if data base model is created 1.8");
//          this.connection.createStatement().executeQuery("Select internal_tax from FVPOS_PRODUCT");
//          logger.info("Data model created 1.8");
//          return true;
//       } catch (SQLException var2) {
//          logger.info("Data model not created 1.8");
//          return false;
//       }
//    }

//    private boolean existsDataModel_1_9() {
//       try {
//          logger.info("Checking if data base model is created 1.9");
//          this.connection.createStatement().executeQuery("Select inner_taxes from FVPOS_PRODUCT");
//          logger.info("Data model not created 1.9");
//          return false;
//       } catch (SQLException var2) {
//          logger.info("Data model created 1.9");
//          return true;
//       }
//    }

//    private boolean existsDataModel_1_10() {
//       try {
//          logger.info("Checking if data base model is created 1.10");
//          this.connection.createStatement().executeQuery("Select * from FVPOS_PRICE_LIST");
//          logger.info("Data model created 1.10");
//          return true;
//       } catch (SQLException var2) {
//          logger.info("Data model not created 1.10");
//          return false;
//       }
//    }

//    private boolean existsDataModel_11_0() {
//       try {
//          logger.info("Checking if data base model is created 11.0");
//          this.connection.createStatement().executeQuery("Select * from FVPOS_NOTA_DE_CREDITO");
//          logger.info("Data model created 11.0");
//          return true;
//       } catch (SQLException var2) {
//          logger.info("Data model not created 11.0");
//          return false;
//       }
//    }
// }
