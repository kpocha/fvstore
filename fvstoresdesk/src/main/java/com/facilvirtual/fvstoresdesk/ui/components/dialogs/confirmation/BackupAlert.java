package com.facilvirtual.fvstoresdesk.ui.components.dialogs.confirmation;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class BackupAlert extends AbstractFVDialog {
   private static final Logger logger = LoggerFactory.getLogger(BackupAlert.class);
   private String action = "";

   public BackupAlert(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      logger.info("Iniciando BKP");
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
      lblTitle.setText("Crear copia de seguridad");
      Label lblModo = new Label(container, 0);
      lblModo.setText("Para crear una copia de seguridad del sistema, copia la carpeta");
      FormData fd_lblModo = new FormData();
      fd_lblModo.top = new FormAttachment(lblTitle, 34);
      fd_lblModo.left = new FormAttachment(0, 30);
      fd_lblModo.right = new FormAttachment(100, -47);
      lblModo.setLayoutData(fd_lblModo);
      Label lblParaCerrarLa = new Label(container, 0);
      FormData fd_lblParaCerrarLa = new FormData();
      fd_lblParaCerrarLa.top = new FormAttachment(lblModo, 18);
      fd_lblParaCerrarLa.left = new FormAttachment(0, 29);
      lblParaCerrarLa.setLayoutData(fd_lblParaCerrarLa);
      lblParaCerrarLa.setText("C:\\facilvirtual en un medio externo (cd, pendrive, disco externo, etc).");
      return container;
   }

   private void processDialog() {
      this.close();
   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }
   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Crear copia de seguridad");
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
      Button button = this.createButton(parent, 0, "Aceptar", false);
      button.setText("Aceptar");
   }
   @Override
   protected Point getInitialSize() {
      return new Point(435, 355);
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
