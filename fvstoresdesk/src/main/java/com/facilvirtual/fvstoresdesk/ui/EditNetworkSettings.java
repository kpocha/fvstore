package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Employee;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditNetworkSettings extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("EditNetworkSettings");
   private String action = "";
   private Text txtServerPort;
   private Employee employee;
   private Text txtIp;
   private Combo comboConnectionType;
   private Combo comboServerType;

   public EditNetworkSettings(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 0);
      lblTitle.setAlignment(16777216);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.top = new FormAttachment(0, 32);
      fd_lblTitle.right = new FormAttachment(100, -26);
      fd_lblTitle.left = new FormAttachment(0, 25);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Configuración de red");
      Label lblConcepto = new Label(container, 0);
      lblConcepto.setAlignment(131072);
      FormData fd_lblConcepto = new FormData();
      lblConcepto.setLayoutData(fd_lblConcepto);
      lblConcepto.setText("Tipo de servidor:");
      Label lblImporte = new Label(container, 0);
      fd_lblConcepto.left = new FormAttachment(lblImporte, 0, 16384);
      lblImporte.setAlignment(131072);
      FormData fd_lblImporte = new FormData();
      fd_lblImporte.left = new FormAttachment(0, 56);
      lblImporte.setLayoutData(fd_lblImporte);
      lblImporte.setText("Puerto:");
      this.txtServerPort = new Text(container, 2048);
      fd_lblImporte.right = new FormAttachment(this.txtServerPort, -14);
      this.txtServerPort.setTextLimit(30);
      FormData fd_txtServerPort = new FormData();
      fd_txtServerPort.right = new FormAttachment(100, -173);
      fd_txtServerPort.left = new FormAttachment(0, 163);
      this.txtServerPort.setLayoutData(fd_txtServerPort);
      Label lblModo = new Label(container, 0);
      lblModo.setAlignment(131072);
      fd_lblConcepto.top = new FormAttachment(lblModo, 27);
      lblModo.setText("Tipo de nodo:");
      FormData fd_lblModo = new FormData();
      fd_lblModo.left = new FormAttachment(0, 56);
      fd_lblModo.bottom = new FormAttachment(100, -173);
      lblModo.setLayoutData(fd_lblModo);
      this.comboConnectionType = new Combo(container, 8);
      fd_lblModo.right = new FormAttachment(100, -280);
      FormData fd_comboConnectionType = new FormData();
      fd_comboConnectionType.right = new FormAttachment(lblModo, 159, 131072);
      fd_comboConnectionType.left = new FormAttachment(lblModo, 14);
      this.comboConnectionType.setLayoutData(fd_comboConnectionType);
      this.comboConnectionType.add("Servidor");
      this.comboConnectionType.add("Terminal");
      this.comboServerType = new Combo(container, 8);
      fd_comboConnectionType.bottom = new FormAttachment(this.comboServerType, -19);
      fd_lblConcepto.right = new FormAttachment(this.comboServerType, -14);
      FormData fd_comboServerType = new FormData();
      fd_comboServerType.right = new FormAttachment(0, 308);
      fd_comboServerType.top = new FormAttachment(0, 129);
      fd_comboServerType.left = new FormAttachment(0, 163);
      this.comboServerType.setLayoutData(fd_comboServerType);
      this.comboServerType.add("Server");
      this.comboServerType.add("WebServer");
      Label lblUrl = new Label(container, 0);
      lblUrl.setAlignment(131072);
      fd_lblImporte.bottom = new FormAttachment(lblUrl, -26);
      FormData fd_lblUrl = new FormData();
      fd_lblUrl.left = new FormAttachment(0, 126);
      fd_lblUrl.top = new FormAttachment(0, 213);
      lblUrl.setLayoutData(fd_lblUrl);
      lblUrl.setText("IP:");
      this.txtIp = new Text(container, 2048);
      fd_lblUrl.right = new FormAttachment(100, -280);
      fd_txtServerPort.bottom = new FormAttachment(100, -88);
      this.txtIp.setTextLimit(60);
      FormData fd_txtIp = new FormData();
      fd_txtIp.right = new FormAttachment(lblUrl, 201, 131072);
      fd_txtIp.top = new FormAttachment(this.txtServerPort, 20);
      fd_txtIp.left = new FormAttachment(0, 163);
      this.txtIp.setLayoutData(fd_txtIp);
      //TODO: Arrelge¿ar
      //this.txtServerPort.addTraverseListener(new 1(this));
      this.initializeSettings();
      return container;
   }

   private void initializeSettings() {
      try {
         BufferedReader br = new BufferedReader(new FileReader("C:/facilvirtual/config/fvpos.properties"));

         try {
            String serverTypeLine = br.readLine();
            String[] serverTypeLineArray = serverTypeLine.split("=");
            String serverType = serverTypeLineArray[1].trim();
            String serverPortLine = br.readLine();
            String[] serverPortLineArray = serverPortLine.split("=");
            String serverPort = serverPortLineArray[1].trim();
            String jdbcUrlLine = br.readLine();
            String[] jdbcUrlLineArray = jdbcUrlLine.split("=");
            String jdbcUrl = jdbcUrlLineArray[1].trim();
            if (jdbcUrl.contains("file:")) {
               this.comboConnectionType.select(0);
               this.comboServerType.select(0);
               this.txtIp.setText("127.0.0.1");
               this.txtServerPort.setText("9001");
            } else {
               this.comboConnectionType.select(1);
               String ip = "";
               String[] serverIPLineArray;
               String[] serverIPLineArray2;
               if (jdbcUrl.contains("hsql:")) {
                  this.comboServerType.select(0);
                  serverIPLineArray = jdbcUrl.split("hsql://");
                  ip = serverIPLineArray[1].trim();
                  if (ip.contains(":")) {
                     serverIPLineArray2 = ip.split(":");
                     ip = serverIPLineArray2[0];
                  } else {
                     serverIPLineArray2 = ip.split("/");
                     ip = serverIPLineArray2[0];
                  }
               } else {
                  this.comboServerType.select(1);
                  serverIPLineArray = jdbcUrl.split("http://");
                  ip = serverIPLineArray[1].trim();
                  if (ip.contains(":")) {
                     serverIPLineArray2 = ip.split(":");
                     ip = serverIPLineArray2[0];
                  } else {
                     serverIPLineArray2 = ip.split("/");
                     ip = serverIPLineArray2[0];
                  }
               }

               this.txtIp.setText(ip);
               this.txtServerPort.setText(serverPort);
            }
         } catch (Exception var23) {
            var23.printStackTrace();
         } finally {
            try {
               br.close();
            } catch (Exception var22) {
               var22.printStackTrace();
            }

         }
      } catch (FileNotFoundException var25) {
         var25.printStackTrace();
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            logger.info("Se editó la configuración de red");
            String connType = this.comboConnectionType.getText().trim();
            String serverType = this.comboServerType.getText();
            String serverPort = this.txtServerPort.getText().trim();
            String ip = this.txtIp.getText().trim();
            String jdbcUrl = this.buildJdbcUrl(connType, serverType, serverPort, ip);
            logger.info("Tipo de conexión: " + connType);
            logger.info("Tipo de servidor: " + serverType);
            logger.info("Puerto: " + serverPort);
            logger.info("JDBC URL: " + jdbcUrl);

            try {
               File file = new File("C://facilvirtual/config/fvpos.properties");
               if (!file.exists()) {
                  file.createNewFile();
               }

               FileWriter fw = new FileWriter(file, false);
               BufferedWriter bw = new BufferedWriter(fw);
               PrintWriter pw = new PrintWriter(bw);
               pw.println("dataSource.serverType = " + serverType);
               pw.println("dataSource.serverPort = " + serverPort);
               pw.println("dataSource.jdbcUrl = " + jdbcUrl);
               pw.close();
            } catch (IOException var10) {
               logger.error("Error (IOException) al guardar configuración de red");
               logger.error(var10.getMessage());
              // //logger.error(var10);
            }
         } catch (Exception var11) {
            logger.error("Error al guardar configuración de red");
            logger.error(var11.getMessage());
           // //logger.error(var11);
         }

         this.close();
      }

   }

   private String buildJdbcUrl(String connType, String serverType, String serverPort, String ip) {
      String jdbcUrl = "";
      if (connType.equalsIgnoreCase("Servidor")) {
         jdbcUrl = "jdbc:hsqldb:file:C:/facilvirtual/data/fvposdb;ifexists=true";
      } else {
         if ("".equals(serverPort)) {
            serverPort = "9001";
         }

         if ("".equals(ip)) {
            serverPort = "127.0.0.1";
         }

         if (serverType.equalsIgnoreCase("Server")) {
            jdbcUrl = "jdbc:hsqldb:hsql://" + ip + ":" + serverPort + "/fvposdb;ifexists=true";
         } else {
            jdbcUrl = "jdbc:hsqldb:http://" + ip + ":" + serverPort + "/fvposdb;ifexists=true";
         }
      }

      return jdbcUrl;
   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Configuración de red");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Guardar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(435, 355);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public Employee getEmployee() {
      return this.employee;
   }

   public void setEmployee(Employee employee) {
      this.employee = employee;
   }
}
