package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.CustomerOnAccountOperation;
import com.facilvirtual.fvstoresdesk.model.Employee;
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

public class AddNewCustomerOnAccountCredit extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("AddNewCustomerOnAccountCredit");
   private String action = "";
   private Text txtAmount;
   private Employee employee;
   private Customer customer;
   private CashRegister cashRegister;

   public AddNewCustomerOnAccountCredit(Shell parentShell) {
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
      lblTitle.setText("Nuevo pago de cliente");
      Label lblImporte = new Label(container, 0);
      FormData fd_lblImporte = new FormData();
      fd_lblImporte.top = new FormAttachment(lblTitle, 55);
      fd_lblImporte.left = new FormAttachment(0, 55);
      lblImporte.setLayoutData(fd_lblImporte);
      lblImporte.setText("Importe: $");
      this.txtAmount = new Text(container, 2048);
      fd_lblImporte.right = new FormAttachment(100, -340);
      this.txtAmount.setTextLimit(30);
      FormData fd_txtAmount = new FormData();
      fd_txtAmount.right = new FormAttachment(100, -221);
      fd_txtAmount.left = new FormAttachment(lblImporte, 6);
      fd_txtAmount.top = new FormAttachment(lblTitle, 52);
      this.txtAmount.setLayoutData(fd_txtAmount);
       this.txtAmount.addTraverseListener(new TraverseListener() {
      @Override
      public void keyTraversed(TraverseEvent e) {
         if (e.detail == SWT.TRAVERSE_RETURN) {
            processDialog(); // Este método reemplaza access$0
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
            CustomerOnAccountOperation operation = new CustomerOnAccountOperation();
            operation.setCreationDate(creationDate);
            operation.setOperationDate(creationDate);
            operation.setLastUpdatedDate(creationDate);
            operation.setCreditType();
            operation.setDescription("Pago");
            operation.setAmount(this.getDoubleValueFromText(this.txtAmount));
            operation.setAuthor(this.getEmployee());
            operation.setCashNumber(this.getWorkstationConfig().getCashNumber());
            operation.setSystem(false);
            operation.setCustomer(this.getCustomer());
            this.getCustomerService().saveCustomerOnAccountOperation(operation);
            Customer customer = operation.getCustomer();
            customer.setOnAccountTotal(customer.getOnAccountTotal() + operation.getAmount());
            this.getCustomerService().saveCustomer(customer);
            this.getCashService().saveNewCashOperationForCustomerOperation(operation);
            this.getAppConfigService().incCashAmount(this.getWorkstationConfig(), operation.getAmount());
            this.getCashRegister().updatedWorkstationConfig();
         } catch (Exception var4) {
            logger.error("Error al guardar pago de cliente");
            logger.error(var4.getMessage());
            //logger.error(var4);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtAmount.getText().trim())) {
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
      this.initTitle(newShell, "Nuevo pago de cliente");
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

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public CashRegister getCashRegister() {
      return this.cashRegister;
   }

   public void setCashRegister(CashRegister cashRegister) {
      this.cashRegister = cashRegister;
   }
}
