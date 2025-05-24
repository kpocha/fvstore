package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.CashOperation;
import com.facilvirtual.fvstoresdesk.model.Employee;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.events.TraverseEvent;
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

public class AddNewCashIncome extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("AddNewCashIncome");
   private String action = "";
   private Text txtDescription;
   private Text txtAmount;
   private Employee employee;

   public AddNewCashIncome(Shell parentShell) {
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
      lblTitle.setText("Nuevo ingreso a caja");
      Label lblConcepto = new Label(container, 0);
      FormData fd_lblConcepto = new FormData();
      fd_lblConcepto.top = new FormAttachment(lblTitle, 48);
      fd_lblConcepto.left = new FormAttachment(0, 44);
      lblConcepto.setLayoutData(fd_lblConcepto);
      lblConcepto.setText("Concepto:");
      this.txtDescription = new Text(container, 2048);
      this.txtDescription.setTextLimit(150);
      FormData fd_txtDescription = new FormData();
      fd_txtDescription.top = new FormAttachment(lblConcepto, -10, 128);
      fd_txtDescription.right = new FormAttachment(lblConcepto, 309, 131072);
      fd_txtDescription.left = new FormAttachment(lblConcepto, 6);
      this.txtDescription.setLayoutData(fd_txtDescription);
      Label lblImporte = new Label(container, 0);
      FormData fd_lblImporte = new FormData();
      fd_lblImporte.left = new FormAttachment(0, 43);
      fd_lblImporte.top = new FormAttachment(lblConcepto, 27);
      fd_lblImporte.right = new FormAttachment(lblConcepto, 0, 131072);
      lblImporte.setLayoutData(fd_lblImporte);
      lblImporte.setText("Importe: $");
      this.txtAmount = new Text(container, 2048);
      this.txtAmount.setTextLimit(30);
      fd_txtDescription.bottom = new FormAttachment(100, -134);
      FormData fd_txtAmount = new FormData();
      fd_txtAmount.top = new FormAttachment(this.txtDescription, 21);
      fd_txtAmount.right = new FormAttachment(lblImporte, 97, 131072);
      fd_txtAmount.left = new FormAttachment(lblImporte, 6);
      this.txtAmount.setLayoutData(fd_txtAmount);

       this.txtAmount.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent arg0) {
            if (arg0.detail == 4) { // 4 es el código para la tecla Enter
               processDialog();
            }
         }
      });
      
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            Date creationDate = new Date();
            CashOperation cashOperation = new CashOperation();
            cashOperation.setCreationDate(creationDate);
            cashOperation.setOperationDate(creationDate);
            cashOperation.setLastUpdatedDate(creationDate);
            cashOperation.setIncomeType();
            cashOperation.setDescription(this.txtDescription.getText().trim());
            cashOperation.setAmount(Double.valueOf(this.txtAmount.getText().trim().replaceAll(",", ".")));
            cashOperation.setAuthor(this.getEmployee());
            cashOperation.setCashNumber(this.getWorkstationConfig().getCashNumber());
            this.getCashService().saveCashOperation(cashOperation);
            this.getAppConfigService().incCashAmount(this.getWorkstationConfig(), cashOperation.getAmount());
         } catch (Exception var3) {
            logger.error("Error al guardar ingreso a caja");
            logger.error(var3.getMessage());
            //logger.error(var3);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtDescription.getText().trim())) {
         valid = false;
         this.alert("Ingresa el concepto");
      }

      if (valid && "".equals(this.txtAmount.getText().trim())) {
         valid = false;
         this.alert("Ingresa el importe");
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

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nuevo ingreso a caja");
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
