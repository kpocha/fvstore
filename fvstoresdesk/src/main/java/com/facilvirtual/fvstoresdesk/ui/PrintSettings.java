package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class PrintSettings extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("PrintSettings");
   private String action = "";
   private Composite compositeTickets;
   private Composite compositeFacturaC;
   private Label lblTextoEnPie;
   private Text txtFooterFactura1;
   private Text txtFooterFactura2;
   private Label lblTextoEnPie_1;
   private Text txtFooterTicket;

   public PrintSettings(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      GridLayout gl_container = new GridLayout(1, false);
      gl_container.marginHeight = 7;
      gl_container.marginWidth = 9;
      container.setLayout(gl_container);
      TabFolder tabFolder = new TabFolder(container, 0);
      GridData gd_tabFolder = new GridData(16384, 16777216, false, false, 1, 1);
      gd_tabFolder.heightHint = 300;
      gd_tabFolder.widthHint = 330;
      tabFolder.setLayoutData(gd_tabFolder);
      TabItem tbtmFacturaB = new TabItem(tabFolder, 0);
      tbtmFacturaB.setText("Tickets");
      this.compositeTickets = new Composite(tabFolder, 0);
      tbtmFacturaB.setControl(this.compositeTickets);
      this.compositeTickets.setLayout(new GridLayout(1, false));
      new Label(this.compositeTickets, 0);
      this.lblTextoEnPie_1 = new Label(this.compositeTickets, 0);
      this.lblTextoEnPie_1.setText("Texto inferior del ticket");
      this.txtFooterTicket = new Text(this.compositeTickets, 2048);
      this.txtFooterTicket.setTextLimit(120);
      GridData gd_txtFooterTicket = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtFooterTicket.widthHint = 270;
      this.txtFooterTicket.setLayoutData(gd_txtFooterTicket);
      this.txtFooterTicket.setText(this.getAppConfig().getTextFooterTicket());
      TabItem tbtmFacturas = new TabItem(tabFolder, 0);
      tbtmFacturas.setText("Facturas");
      this.compositeFacturaC = new Composite(tabFolder, 0);
      tbtmFacturas.setControl(this.compositeFacturaC);
      this.compositeFacturaC.setLayout(new GridLayout(1, false));
      new Label(this.compositeFacturaC, 0);
      this.lblTextoEnPie = new Label(this.compositeFacturaC, 0);
      this.lblTextoEnPie.setText("Texto en pie de página");
      this.txtFooterFactura1 = new Text(this.compositeFacturaC, 2048);
      this.txtFooterFactura1.setTextLimit(60);
      GridData gd_txtFooterFactura1 = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtFooterFactura1.widthHint = 270;
      this.txtFooterFactura1.setLayoutData(gd_txtFooterFactura1);
      this.txtFooterFactura2 = new Text(this.compositeFacturaC, 2048);
      this.txtFooterFactura2.setTextLimit(60);
      GridData gd_txtFooterFactura2 = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtFooterFactura2.widthHint = 270;
      this.txtFooterFactura2.setLayoutData(gd_txtFooterFactura2);
      this.txtFooterFactura1.setText(this.getAppConfig().getTextFooterFactura1());
      this.txtFooterFactura2.setText(this.getAppConfig().getTextFooterFactura2());
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            this.saveSettingsForTickets();
            this.saveSettingsForFacturas();
         } catch (Exception var2) {
            logger.error("Error al guardar configuración de impresión");
            logger.error(var2.getMessage());
           // //logger.error(var2);
         }

         this.close();
      }

   }

   private void saveSettingsForTickets() {
      AppConfig appConfig = this.getAppConfig();
      appConfig.setTextFooterTicket(this.txtFooterTicket.getText().trim());
      this.getAppConfigService().saveAppConfig(appConfig);
   }

   private void saveSettingsForFacturas() {
      AppConfig appConfig = this.getAppConfig();
      appConfig.setTextFooterFactura1(this.txtFooterFactura1.getText().trim());
      appConfig.setTextFooterFactura2(this.txtFooterFactura2.getText().trim());
      this.getAppConfigService().saveAppConfig(appConfig);
   }

   public boolean validateFields() {
      return this.validateFieldsForTickets() && this.validateFieldsForFacturas();
   }

   public boolean validateFieldsForTickets() {
      boolean valid = true;
      return valid;
   }

   public boolean validateFieldsForFacturas() {
      boolean valid = true;
      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Configuración de impresión");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(360, 430);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }
}
