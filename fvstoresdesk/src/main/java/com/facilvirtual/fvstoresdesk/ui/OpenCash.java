package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
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

public class OpenCash extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("OpenCash");
   private String action = "";
   private Text txtAmount;
   private Employee employee;

   public OpenCash(Shell parentShell) {
      super(parentShell);
   }
   @Override
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
      lblTitle.setText("Apertura de caja");
      Label lblImporte = new Label(container, 0);
      FormData fd_lblImporte = new FormData();
      fd_lblImporte.top = new FormAttachment(lblTitle, 48);
      fd_lblImporte.left = new FormAttachment(0, 72);
      lblImporte.setLayoutData(fd_lblImporte);
      lblImporte.setText("Caja inicial: $");
      this.txtAmount = new Text(container, 2048);
      fd_lblImporte.right = new FormAttachment(100, -316);
      this.txtAmount.setTextLimit(30);
      FormData fd_txtAmount = new FormData();
      fd_txtAmount.top = new FormAttachment(lblImporte, -3, 128);
      fd_txtAmount.left = new FormAttachment(lblImporte, 6);
      fd_txtAmount.right = new FormAttachment(100, -219);
      this.txtAmount.setLayoutData(fd_txtAmount);
      this.txtAmount.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent e) {
            if (e.detail == SWT.TRAVERSE_RETURN) {
               processDialog();
            }
         }
      });
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            Date creationDate = new Date();
            WorkstationConfig workstationConfig = this.getWorkstationConfig();
            workstationConfig.setCashOpened(true);
            this.getAppConfigService().saveWorkstationConfig(workstationConfig);
            this.getCashService().saveNewOpenCashOperation(creationDate, workstationConfig.getCashNumber(), this.getEmployee());
            double amount = Double.valueOf(this.txtAmount.getText().trim().replaceAll(",", "."));
            this.getCashService().saveNewInitialCash(creationDate, workstationConfig.getCashNumber(), amount, this.getEmployee());
            this.getAppConfigService().incCashAmount(this.getWorkstationConfig(), amount);
         } catch (Exception var5) {
            logger.error("Error en Apertura de caja");
            logger.error(var5.getMessage());
           // //logger.error(var5);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if (valid && "".equals(this.txtAmount.getText().trim())) {
         valid = false;
         this.alert("Ingresa el importe de la caja inicial");
      }

      double value;
      if (valid) {
         try {
            value = Double.valueOf(this.txtAmount.getText().trim().replaceAll(",", "."));
         } catch (Exception var4) {
            valid = false;
            this.alert("El importe ingresado no es válido");
         }
      }

      if (valid) {
         value = Double.valueOf(this.txtAmount.getText().trim().replaceAll(",", "."));
         if (value <= 0.0) {
            valid = false;
            this.alert("El importe debe ser mayor a 0");
         }
      }

      return valid;
   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Apertura de caja");
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
      this.createButton(parent, 0, "Abrir caja", false);
      this.createButton(parent, 1, "Cancelar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(462, 332);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
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
