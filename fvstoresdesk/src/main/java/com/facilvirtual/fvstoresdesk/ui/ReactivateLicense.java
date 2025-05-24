package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import com.facilvirtual.fvstoresdesk.util.CryptUtils;
import java.util.Date;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class ReactivateLicense extends AbstractFVDialog {
   private String action = "";
   private Text txtActivationCode;
   private Text txtInstallationCode;
   private AbstractFVApplicationWindow parentWindow;

   public ReactivateLicense(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 0);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.top = new FormAttachment(0, 31);
      fd_lblTitle.left = new FormAttachment(0, 97);
      fd_lblTitle.right = new FormAttachment(100, -108);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Reactivar licencia de uso");
      Label lblNombreDelArchivo = new Label(container, 0);
      lblNombreDelArchivo.setText("Código de activación:");
      lblNombreDelArchivo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblNombreDelArchivo = new FormData();
      fd_lblNombreDelArchivo.left = new FormAttachment(0, 63);
      fd_lblNombreDelArchivo.right = new FormAttachment(100, -187);
      lblNombreDelArchivo.setLayoutData(fd_lblNombreDelArchivo);
      this.txtActivationCode = new Text(container, 133120);
      this.txtActivationCode.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtActivationCode = new FormData();
      fd_txtActivationCode.left = new FormAttachment(0, 63);
      fd_txtActivationCode.right = new FormAttachment(100, -81);
      fd_txtActivationCode.bottom = new FormAttachment(100, -47);
      fd_txtActivationCode.top = new FormAttachment(lblNombreDelArchivo, 6);
      this.txtActivationCode.setLayoutData(fd_txtActivationCode);
      this.txtInstallationCode = new Text(container, 133120);
      this.txtInstallationCode.setEditable(false);
      fd_lblNombreDelArchivo.top = new FormAttachment(this.txtInstallationCode, 25);
      this.txtInstallationCode.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtInstallationCode = new FormData();
      fd_txtInstallationCode.bottom = new FormAttachment(100, -116);
      fd_txtInstallationCode.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      fd_txtInstallationCode.right = new FormAttachment(100, -81);
      this.txtInstallationCode.setLayoutData(fd_txtInstallationCode);
      Label lblCdigoDeInstalacin = new Label(container, 0);
      fd_txtInstallationCode.top = new FormAttachment(lblCdigoDeInstalacin, 6);
      lblCdigoDeInstalacin.setText("Código de instalación:");
      lblCdigoDeInstalacin.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblCdigoDeInstalacin = new FormData();
      fd_lblCdigoDeInstalacin.top = new FormAttachment(lblTitle, 33);
      fd_lblCdigoDeInstalacin.bottom = new FormAttachment(100, -147);
      fd_lblCdigoDeInstalacin.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      fd_lblCdigoDeInstalacin.right = new FormAttachment(100, -242);
      lblCdigoDeInstalacin.setLayoutData(fd_lblCdigoDeInstalacin);
      this.initInstallationCode();
      return container;
   }

   private void initInstallationCode() {
      String installationCode = this.getAppConfigService().getCurrentInstallationCode();
      String prefix = this.getAppConfigService().getInstallationCodePrefix();
      this.txtInstallationCode.setText(prefix + installationCode);
   }

   private void saveActivationCode() {
      if (this.validateFields()) {
         this.setAction("OK");
         String activationCode = this.txtActivationCode.getText().trim();
         activationCode = CryptUtils.formatKey(CryptUtils.unformatKey(activationCode));
         WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
         workstationConfig.setLicenseActivationCode(activationCode);
         Date appLicenseExpirationDate = workstationConfig.getExpirationDateFromActivationCode(activationCode);
         workstationConfig.setLicenseExpirationDate(appLicenseExpirationDate);
         this.getAppConfigService().saveWorkstationConfig(workstationConfig);
         this.getParentWindow().initWindowTitle(this.getParentWindow().getShell(), workstationConfig);
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      String activationCode = this.txtActivationCode.getText().trim();
      String installationCode = this.getAppConfigService().getCurrentInstallationCode();
      if ("".equals(activationCode)) {
         valid = false;
         this.alert("Ingresa el código de activación");
      }

      if (valid) {
         boolean validCode = this.getAppConfigService().getCurrentWorkstationConfig().isValidActivationCode(installationCode, activationCode);
         if (!validCode) {
            valid = false;
            this.alert("El código de activación ingresado no es válido");
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Reactivar licencia de uso");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.saveActivationCode();
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
      return new Point(455, 332);
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
