package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class CloseCash extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("CloseCash");
   private String action = "";
   private Employee employee;

   public CloseCash(Shell parentShell) {
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
      lblTitle.setText("Cierre de caja");
      Label lblImporte = new Label(container, 0);
      FormData fd_lblImporte = new FormData();
      fd_lblImporte.top = new FormAttachment(lblTitle, 48);
      fd_lblImporte.left = new FormAttachment(0, 72);
      lblImporte.setLayoutData(fd_lblImporte);
      lblImporte.setText("Se cerrará la caja.");
      fd_lblImporte.right = new FormAttachment(100, -230);
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            Date creationDate = new Date();
            WorkstationConfig workstationConfig = this.getWorkstationConfig();
            workstationConfig.setCashOpened(false);
            this.getAppConfigService().saveWorkstationConfig(workstationConfig);
            this.getCashService().saveNewWithdrawCash(creationDate, workstationConfig.getCashNumber(), workstationConfig.getCashAmount(), this.getEmployee());
            this.getCashService().saveNewCloseCashOperation(creationDate, workstationConfig.getCashNumber(), this.getEmployee());
            this.getAppConfigService().resetCashAmount(this.getWorkstationConfig());
         } catch (Exception var3) {
            logger.error("Error en Cierre de caja");
            logger.error(var3.getMessage());
            ////logger.error(var3);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Cierre de caja");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Cerrar caja", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(462, 332);
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
