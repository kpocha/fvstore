package com.facilvirtual.fvstoresdesk.ui.components.dialogs;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVApplicationWindow;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.confirmation.FVConfirmDialog;

public class AboutFacilVirtual extends AbstractFVDialog {
   protected static Logger logger = LoggerFactory.getLogger("AboutFacilVirtual");
   private String action = "";
   private AbstractFVApplicationWindow parentWindow;
   private Text txtLicenseNumber;
   private Text txtSerialNumber;
   private Text txtExpirationDate;
   private Text txtVersion;
   private Text txtAppModel;

   public AboutFacilVirtual(Shell parentShell) {
      super(parentShell);
   }
   @Override 
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Button btnDeactivateLicense = new Button(container, 0);
      
    btnDeactivateLicense.addSelectionListener(new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            deactivateLicense();
        }
    });

      FormData fd_btnDeactivateLicense = new FormData();
      btnDeactivateLicense.setLayoutData(fd_btnDeactivateLicense);
      btnDeactivateLicense.setText("Desactivar licencia");
      Label lblSoporteTcnico = new Label(container, 0);
      FormData fd_lblSoporteTcnico = new FormData();
      fd_lblSoporteTcnico.left = new FormAttachment(btnDeactivateLicense, 0, 16384);
      lblSoporteTcnico.setLayoutData(fd_lblSoporteTcnico);
      lblSoporteTcnico.setText("Soporte técnico:");
      Label lblClientesfacilvirtualcom = new Label(container, 0);
      fd_lblSoporteTcnico.top = new FormAttachment(lblClientesfacilvirtualcom, 0, 128);
      lblClientesfacilvirtualcom.setFont(SWTResourceManager.getFont("Tahoma", 8, 1));
      FormData fd_lblClientesfacilvirtualcom = new FormData();
      lblClientesfacilvirtualcom.setLayoutData(fd_lblClientesfacilvirtualcom);
      lblClientesfacilvirtualcom.setText("clientes@facilvirtual.com");
      Label lblWeb = new Label(container, 0);
      FormData fd_lblWeb = new FormData();
      fd_lblWeb.left = new FormAttachment(btnDeactivateLicense, 0, 16384);
      lblWeb.setLayoutData(fd_lblWeb);
      lblWeb.setText("Sitio web:");
      Label lblWwwfacilvirtualcom = new Label(container, 0);
      fd_lblWeb.top = new FormAttachment(lblWwwfacilvirtualcom, 0, 128);
      fd_lblWeb.right = new FormAttachment(lblWwwfacilvirtualcom, -24);
      fd_lblClientesfacilvirtualcom.bottom = new FormAttachment(lblWwwfacilvirtualcom, -6);
      fd_lblClientesfacilvirtualcom.left = new FormAttachment(lblWwwfacilvirtualcom, 0, 16384);
      lblWwwfacilvirtualcom.setText("www.facilvirtual.com");
      lblWwwfacilvirtualcom.setFont(SWTResourceManager.getFont("Tahoma", 8, 1));
      FormData fd_lblWwwfacilvirtualcom = new FormData();
      fd_lblWwwfacilvirtualcom.top = new FormAttachment(0, 285);
      fd_lblWwwfacilvirtualcom.left = new FormAttachment(0, 160);
      lblWwwfacilvirtualcom.setLayoutData(fd_lblWwwfacilvirtualcom);
      Canvas canvas = new Canvas(container, 0);
      canvas.setBackground(SWTResourceManager.getColor(1));
      FormData fd_canvas = new FormData();
      fd_canvas.bottom = new FormAttachment(0, 57);
      fd_canvas.top = new FormAttachment(0);
      fd_canvas.left = new FormAttachment(0);
      fd_canvas.right = new FormAttachment(0, 472);
      canvas.setLayoutData(fd_canvas);
      Image logoimg = new Image(Display.getCurrent(), this.getImagesDir() + "logo_facilvirtual_about.png");
      Label lblNewLabel = new Label(canvas, 0);
      lblNewLabel.setBackground(SWTResourceManager.getColor(1));
      lblNewLabel.setBounds(51, 2, 187, 57);
      lblNewLabel.setText("");
      lblNewLabel.setImage(logoimg);
      Label lblNmeroDeLicencia = new Label(container, 0);
      fd_btnDeactivateLicense.left = new FormAttachment(lblNmeroDeLicencia, 0, 16384);
      FormData fd_lblNmeroDeLicencia = new FormData();
      fd_lblNmeroDeLicencia.top = new FormAttachment(canvas, 20);
      this.txtVersion = new Text(canvas, 8);
      this.txtVersion.setBackground(SWTResourceManager.getColor(1));
      this.txtVersion.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      this.txtVersion.setText("Versión 1.0");
      this.txtVersion.setBounds(247, 35, 208, 19);
      this.txtAppModel = new Text(canvas, 8);
      this.txtAppModel.setForeground(SWTResourceManager.getColor(17));
      this.txtAppModel.setText("Kioscos");
      this.txtAppModel.setFont(SWTResourceManager.getFont("Arial", 12, 1));
      this.txtAppModel.setBackground(SWTResourceManager.getColor(1));
      this.txtAppModel.setBounds(244, 10, 218, 19);
      fd_lblNmeroDeLicencia.left = new FormAttachment(0, 57);
      lblNmeroDeLicencia.setLayoutData(fd_lblNmeroDeLicencia);
      lblNmeroDeLicencia.setText("Código de instalación");
      this.txtLicenseNumber = new Text(container, 2048);
      this.txtLicenseNumber.setEditable(false);
      FormData fd_txtLicenseNumber = new FormData();
      fd_txtLicenseNumber.top = new FormAttachment(lblNmeroDeLicencia, 5);
      fd_txtLicenseNumber.right = new FormAttachment(lblNmeroDeLicencia, 317);
      fd_txtLicenseNumber.left = new FormAttachment(lblNmeroDeLicencia, 0, 16384);
      this.txtLicenseNumber.setLayoutData(fd_txtLicenseNumber);
      Label lblNmeroDeSerie = new Label(container, 0);
      FormData fd_lblNmeroDeSerie = new FormData();
      fd_lblNmeroDeSerie.top = new FormAttachment(this.txtLicenseNumber, 10);
      fd_lblNmeroDeSerie.left = new FormAttachment(lblNmeroDeLicencia, 0, 16384);
      lblNmeroDeSerie.setLayoutData(fd_lblNmeroDeSerie);
      lblNmeroDeSerie.setText("Código de activación");
      this.txtSerialNumber = new Text(container, 2048);
      this.txtSerialNumber.setEditable(false);
      FormData fd_txtSerialNumber = new FormData();
      fd_txtSerialNumber.top = new FormAttachment(lblNmeroDeSerie, 5);
      fd_txtSerialNumber.right = new FormAttachment(0, 374);
      fd_txtSerialNumber.left = new FormAttachment(0, 57);
      this.txtSerialNumber.setLayoutData(fd_txtSerialNumber);
      Label lblFechaDeCaducidad = new Label(container, 0);
      FormData fd_lblFechaDeCaducidad = new FormData();
      fd_lblFechaDeCaducidad.top = new FormAttachment(this.txtSerialNumber, 10);
      fd_lblFechaDeCaducidad.left = new FormAttachment(btnDeactivateLicense, 0, 16384);
      lblFechaDeCaducidad.setLayoutData(fd_lblFechaDeCaducidad);
      lblFechaDeCaducidad.setText("Fecha de caducidad");
      this.txtExpirationDate = new Text(container, 2048);
      this.txtExpirationDate.setEditable(false);
      fd_btnDeactivateLicense.top = new FormAttachment(this.txtExpirationDate, 6);
      FormData fd_txtExpirationDate = new FormData();
      fd_txtExpirationDate.top = new FormAttachment(lblFechaDeCaducidad, 5);
      fd_txtExpirationDate.right = new FormAttachment(0, 374);
      fd_txtExpirationDate.left = new FormAttachment(0, 57);
      this.txtExpirationDate.setLayoutData(fd_txtExpirationDate);
      this.initLicenseInfo();
      return container;
   }

   private void deactivateLicense() {
      if (FVConfirmDialog.openQuestion(this.getShell(), "Desactivar licencia", "Se desactivará la licencia de uso del programa.")) {
         this.setAction("OK");
         this.getAppConfigService().deactivateLicense();
         this.getParentWindow().initWindowTitle(this.getParentWindow().getShell(), this.getAppConfigService().getCurrentWorkstationConfig());
         this.close();
      }

   }

   private void initLicenseInfo() {
      AppConfig appConfig = this.getAppConfigService().getAppConfig();

      try {
         WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
         this.txtAppModel.setText(appConfig.getAppModel());
         this.txtVersion.setText("Versión: " + appConfig.getAppVersion());
         String installationCode = this.getAppConfigService().getCurrentInstallationCode();
         String prefix = this.getAppConfigService().getInstallationCodePrefix();
         this.txtLicenseNumber.setText(prefix + installationCode);
         if (workstationConfig.getLicenseActivationCode() != null) {
            this.txtSerialNumber.setText(workstationConfig.getLicenseActivationCode());
         }

         if (workstationConfig.getLicenseExpirationDate() != null) {
            this.txtExpirationDate.setText(workstationConfig.getAppLicenseExpirationDateToDisplay());
         }
      } catch (Exception var5) {
         logger.error("Error al inicializar datos de la licencia");
      }

   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Acerca de FácilVirtual");
   }
   @Override 
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.close();
      } else {
         this.close();
      }

   }
   @Override 
   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 1, "Cancelar", false);
      button.setText("Cerrar");
   }
   @Override 
   protected Point getInitialSize() {
      return new Point(478, 385);
   }
   @Override 
   public String getAction() {
      return this.action;
   }
   @Override 
   public void setAction(String action) {
      this.action = action;
   }

   protected AbstractFVApplicationWindow getParentWindow() {
      return this.parentWindow;
   }

   public void setParentWindow(AbstractFVApplicationWindow parentWindow) {
      this.parentWindow = parentWindow;
   }
}
