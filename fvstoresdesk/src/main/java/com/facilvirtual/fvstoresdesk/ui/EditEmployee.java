package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Employee;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EditEmployee extends AddNewEmployee {
   private static Logger logger = LoggerFactory.getLogger("EditEmployee");

   public EditEmployee(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      this.lblPassword.dispose();
      this.txtPassword.dispose();
      this.lblPassword2.dispose();
      this.txtPassword2.dispose();
      this.initEmployee();
      return container;
   }

   private void initEmployee() {
      try {
         this.txtUsername.setText(this.getEmployee().getUsername());
         if (this.getEmployee().isAdmin()) {
            this.btnAdmin.setSelection(true);
            this.btnLimited.setSelection(false);
         } else {
            this.btnAdmin.setSelection(false);
            this.btnLimited.setSelection(true);
         }

         this.txtFirstName.setText(this.getEmployee().getFirstName());
         this.txtLastName.setText(this.getEmployee().getLastName());
         this.txtJobPosition.setText(this.getEmployee().getJobPosition());
         this.txtCommissionPerSale.setText(this.getEmployee().getCommissionPerSaleToDisplay());
         this.btnAllowLogin.setSelection(this.getEmployee().isAllowLogin());
         this.btnDeleted.setSelection(!this.getEmployee().isActive());
         this.btnAllowOpenCash.setSelection(this.getEmployee().isAllowOpenCash());
         this.btnAllowCloseCash.setSelection(this.getEmployee().isAllowCloseCash());
         this.btnAllowCreateIncome.setSelection(this.getEmployee().isAllowCreateIncome());
         this.btnAllowCreateOutflow.setSelection(this.getEmployee().isAllowCreateOutflow());
         this.btnAllowCreateOrder.setSelection(this.getEmployee().isAllowCreateOrder());
         this.btnAllowCreatePurchase.setSelection(this.getEmployee().isAllowCreatePurchase());
         this.btnAllowModifyPrice.setSelection(this.getEmployee().isAllowModifyPrice());
         this.btnAllowApplyDiscount.setSelection(this.getEmployee().isAllowApplyDiscount());
         this.btnAllowApplySurcharge.setSelection(this.getEmployee().isAllowApplySurcharge());
         this.btnAllowModuleProducts.setSelection(this.getEmployee().isAllowModuleProducts());
         this.btnAllowModuleOrders.setSelection(this.getEmployee().isAllowModuleOrders());
         this.btnAllowModulePurchases.setSelection(this.getEmployee().isAllowModulePurchases());
         this.btnAllowModuleCustomers.setSelection(this.getEmployee().isAllowModuleCustomers());
         this.btnAllowModuleSuppliers.setSelection(this.getEmployee().isAllowModuleSuppliers());
         this.btnAllowModuleLists.setSelection(this.getEmployee().isAllowModuleLists());
         this.btnAllowModuleReports.setSelection(this.getEmployee().isAllowModuleReports());
         this.btnAllowModuleTools.setSelection(this.getEmployee().isAllowModuleTools());
         this.btnAllowModuleCash.setSelection(this.getEmployee().isAllowModuleCash());
      } catch (Exception var2) {
         logger.error("Error al inicializar usuario para editar");
         logger.error(var2.getMessage());
         ////logger.error(var2);
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            Date editionDate = new Date();
            this.employee.setLastUpdatedDate(editionDate);
            this.employee.setUsername(this.txtUsername.getText().trim());
            this.employee.setAdmin(this.btnAdmin.getSelection());
            this.employee.setFirstName(this.txtFirstName.getText().trim());
            this.employee.setLastName(this.txtLastName.getText().trim());
            this.employee.setJobPosition(this.txtJobPosition.getText().trim());
            this.employee.setAllowLogin(this.btnAllowLogin.getSelection());
            Double commissionPerSale = this.getDoubleValueFromText(this.txtCommissionPerSale);
            if (commissionPerSale != null) {
               this.employee.setCommissionPerSale(commissionPerSale);
            } else {
               this.employee.setCommissionPerSale(0.0);
            }

            this.employee.setAllowOpenCash(this.btnAllowOpenCash.getSelection());
            this.employee.setAllowCloseCash(this.btnAllowCloseCash.getSelection());
            this.employee.setAllowCreateIncome(this.btnAllowCreateIncome.getSelection());
            this.employee.setAllowCreateOutflow(this.btnAllowCreateOutflow.getSelection());
            this.employee.setAllowCreateOrder(this.btnAllowCreateOrder.getSelection());
            this.employee.setAllowCreatePurchase(this.btnAllowCreatePurchase.getSelection());
            this.employee.setAllowModifyPrice(this.btnAllowModifyPrice.getSelection());
            this.employee.setAllowApplyDiscount(this.btnAllowApplyDiscount.getSelection());
            this.employee.setAllowApplySurcharge(this.btnAllowApplySurcharge.getSelection());
            this.employee.setAllowModuleProducts(this.btnAllowModuleProducts.getSelection());
            this.employee.setAllowModuleOrders(this.btnAllowModuleOrders.getSelection());
            this.employee.setAllowModulePurchases(this.btnAllowModulePurchases.getSelection());
            this.employee.setAllowModuleCustomers(this.btnAllowModuleCustomers.getSelection());
            this.employee.setAllowModuleSuppliers(this.btnAllowModuleSuppliers.getSelection());
            this.employee.setAllowModuleLists(this.btnAllowModuleLists.getSelection());
            this.employee.setAllowModuleReports(this.btnAllowModuleReports.getSelection());
            this.employee.setAllowModuleTools(this.btnAllowModuleTools.getSelection());
            this.employee.setAllowModuleCash(this.btnAllowModuleCash.getSelection());
            this.employee.setActive(!this.btnDeleted.getSelection());
            this.getAccountService().saveEmployee(this.employee);
         } catch (Exception var3) {
            logger.error("Error al editar el usuario");
            logger.error(var3.getMessage());
          //  //logger.error(var3);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtUsername.getText().trim())) {
         valid = false;
         this.alert("Ingresa el nombre de usuario");
      }

      if (valid) {
         Employee employee = this.getAccountService().getEmployeeByUsername(this.txtUsername.getText().trim());
         if (employee != null && !employee.getUsername().trim().equalsIgnoreCase(this.txtUsername.getText().trim())) {
            valid = false;
            this.alert("Ya existe ese nombre de usuario");
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Modificar usuario");
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
      return new Point(465, 502);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }
}
