package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Employee;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class ChangePassword extends AbstractFVDialog {
   private String action = "";
   private Text txtCurrentPassword;
   private Text txtNewPassword;
   private Text txtNewPassword2;
   private Employee employee;
   private boolean managerMode = false;

   public ChangePassword(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 16777216);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.top = new FormAttachment(0, 24);
      fd_lblTitle.left = new FormAttachment(0, 73);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Cambiar contraseña");
      Label lblNombreDelArchivo = new Label(container, 0);
      lblNombreDelArchivo.setText("Contraseña actual:");
      lblNombreDelArchivo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblNombreDelArchivo = new FormData();
      fd_lblNombreDelArchivo.right = new FormAttachment(100, -200);
      fd_lblNombreDelArchivo.left = new FormAttachment(0, 54);
      lblNombreDelArchivo.setLayoutData(fd_lblNombreDelArchivo);
      this.txtCurrentPassword = new Text(container, 4196352);
      this.txtCurrentPassword.setTextLimit(15);
      fd_lblTitle.right = new FormAttachment(this.txtCurrentPassword, 0, 131072);
      fd_lblNombreDelArchivo.bottom = new FormAttachment(100, -187);
      this.txtCurrentPassword.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtCurrentPassword = new FormData();
      fd_txtCurrentPassword.top = new FormAttachment(lblNombreDelArchivo, 6);
      fd_txtCurrentPassword.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      fd_txtCurrentPassword.right = new FormAttachment(100, -67);
      this.txtCurrentPassword.setLayoutData(fd_txtCurrentPassword);
      Label lblContraseaNueva = new Label(container, 0);
      lblContraseaNueva.setText("Contraseña nueva:");
      lblContraseaNueva.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblContraseaNueva = new FormData();
      fd_lblContraseaNueva.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      lblContraseaNueva.setLayoutData(fd_lblContraseaNueva);
      this.txtNewPassword = new Text(container, 4196352);
      this.txtNewPassword.setTextLimit(15);
      fd_lblContraseaNueva.bottom = new FormAttachment(100, -127);
      this.txtNewPassword.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtNewPassword = new FormData();
      fd_txtNewPassword.top = new FormAttachment(lblContraseaNueva, 6);
      fd_txtNewPassword.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      fd_txtNewPassword.right = new FormAttachment(100, -67);
      this.txtNewPassword.setLayoutData(fd_txtNewPassword);
      Label lblConfirmarContraseaNueva = new Label(container, 0);
      lblConfirmarContraseaNueva.setText("Confirmar contraseña nueva:");
      lblConfirmarContraseaNueva.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblConfirmarContraseaNueva = new FormData();
      fd_lblConfirmarContraseaNueva.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      lblConfirmarContraseaNueva.setLayoutData(fd_lblConfirmarContraseaNueva);
      this.txtNewPassword2 = new Text(container, 4196352);
      this.txtNewPassword2.setTextLimit(15);
      fd_lblConfirmarContraseaNueva.bottom = new FormAttachment(100, -64);
      this.txtNewPassword2.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtNewPassword2 = new FormData();
      fd_txtNewPassword2.top = new FormAttachment(lblConfirmarContraseaNueva, 6);
      fd_txtNewPassword2.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      fd_txtNewPassword2.right = new FormAttachment(100, -67);
      this.txtNewPassword2.setLayoutData(fd_txtNewPassword2);
      if (this.isManagerMode()) {
         this.initPassword();
      }

      return container;
   }

   private void initPassword() {
      this.txtCurrentPassword.setText(this.employee.getPassword());
      this.txtCurrentPassword.setToolTipText(this.employee.getPassword());
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            this.getEmployee().setPassword(this.txtNewPassword.getText().trim());
            this.getAccountService().saveEmployee(this.getEmployee());
         } catch (Exception var2) {
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtCurrentPassword.getText().trim())) {
         valid = false;
         this.alert("Ingresa la contraseña actual");
      }

      if (valid && !this.getEmployee().getPassword().equals(this.txtCurrentPassword.getText().trim())) {
         valid = false;
         this.alert("La contraseña actual es incorrecta");
      }

      if (valid && "".equals(this.txtNewPassword.getText().trim())) {
         valid = false;
         this.alert("Ingresa la contraseña nueva");
      }

      if (valid && "".equals(this.txtNewPassword2.getText().trim())) {
         valid = false;
         this.alert("Reingresa la contraseña nueva para confirmarla");
      }

      if (valid && !this.txtNewPassword.getText().trim().equals(this.txtNewPassword2.getText().trim())) {
         valid = false;
         this.alert("Las contraseñas no coinciden");
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Cambiar contraseña");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 0, "Aceptar", false);
      button.setText("Guardar");
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(403, 358);
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

   public boolean isManagerMode() {
      return this.managerMode;
   }

   public void setManagerMode(boolean managerMode) {
      this.managerMode = managerMode;
   }
}
