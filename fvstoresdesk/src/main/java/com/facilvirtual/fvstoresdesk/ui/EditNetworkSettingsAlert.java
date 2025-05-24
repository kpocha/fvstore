package com.facilvirtual.fvstoresdesk.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditNetworkSettingsAlert extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("EditNetworkSettingsAlert");
   private String action = "";

   public EditNetworkSettingsAlert(Shell parentShell) {
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
      Label lblModo = new Label(container, 0);
      lblModo.setText("Para configurar la red, es necesario cerrar la sesión de usuario.");
      FormData fd_lblModo = new FormData();
      fd_lblModo.left = new FormAttachment(lblTitle, 11, 16384);
      fd_lblModo.right = new FormAttachment(100, -41);
      lblModo.setLayoutData(fd_lblModo);
      Label lblParaCerrarLa = new Label(container, 0);
      fd_lblModo.bottom = new FormAttachment(100, -169);
      FormData fd_lblParaCerrarLa = new FormData();
      fd_lblParaCerrarLa.top = new FormAttachment(lblModo, 24);
      fd_lblParaCerrarLa.left = new FormAttachment(lblModo, 0, 16384);
      lblParaCerrarLa.setLayoutData(fd_lblParaCerrarLa);
      lblParaCerrarLa.setText("Para cerrar la sesión ir al menú Archivo->Cerrar sesión.");
      return container;
   }

   private void processDialog() {
      this.close();
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
      Button button = this.createButton(parent, 0, "Aceptar", false);
      button.setText("Aceptar");
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
}
