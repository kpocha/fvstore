package com.facilvirtual.fvstoresdesk.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.VatCondition;

public class EditCustomer extends AddNewCustomer {
   public EditCustomer(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      if (this.getCustomer().isBusinessCustomer()) {
         this.initializeBusinessFields();
      } else {
         this.initializeHomeFields();
      }

      this.initializeCommonFields();
      return container;
   }

   public void initializeBusinessFields() {
      this.txtCompanyName.setText(this.getCustomer().getCompanyName());
      this.txtBusinessName.setText(this.getCustomer().getBusinessName());
      this.txtContactName.setText(this.getCustomer().getContactName());
      this.txtCuit.setText(this.getCustomer().getCuit());
      this.txtGrossIncomeNumber.setText(this.getCustomer().getGrossIncomeNumber());
   }

   public void initializeHomeFields() {
      this.txtFirstName.setText(this.getCustomer().getFirstName());
      this.txtLastName.setText(this.getCustomer().getLastName());
      int idx = 0;
      String[] dTypes = new String[]{"", "CI", "DNI", "LC", "LE", "PAS"};
      String[] var6 = dTypes;
      int var5 = dTypes.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String dType = var6[var4];
         if (dType.equalsIgnoreCase(this.getCustomer().getDocumentType())) {
            this.comboDocumentType.select(idx);
         }

         ++idx;
      }

      this.txtDocumentNumber.setText(this.getCustomer().getDocumentNumber());
   }

   public void initializeCommonFields() {
      this.txtPhone.setText(this.getCustomer().getPhone());
      this.txtEmail.setText(this.getCustomer().getEmail());
      this.txtAddressStreet.setText(this.getCustomer().getAddressStreet());
      this.txtAddressNumber.setText(this.getCustomer().getAddressNumber());
      this.txtCity.setText(this.getCustomer().getCity());
      this.txtProvincia.setText(this.getCustomer().getProvince());
      List<VatCondition> vatConditions = this.getOrderService().getAllVatConditions();
      int vatConditionIdx = 0;

      for(Iterator var4 = vatConditions.iterator(); var4.hasNext(); ++vatConditionIdx) {
         VatCondition vc = (VatCondition)var4.next();
         if (this.getCustomer().getVatCondition().getId().equals(vc.getId())) {
            this.comboVatCondition.select(vatConditionIdx);
         }
      }

      this.txtObservations.setText(this.getCustomer().getObservations());
      this.btnAllowOnAccount.setSelection(this.getCustomer().isAllowOnAccountOperations());
      this.btnOnAccountLimited.setSelection(this.getCustomer().isOnAccountLimited());
      this.btnOnAccountIlimited.setSelection(!this.getCustomer().isOnAccountLimited());
      this.txtOnAccountLimit.setText(this.getCustomer().getOnAccountLimitToDisplay());
      this.txtOnAccountTotal.setText(this.getCustomer().getOnAccountTotalToDisplay());
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.editCustomer();
      } else {
         this.close();
      }

   }

   private void editCustomer() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            Date editionDate = new Date();
            this.customer.setLastUpdatedDate(editionDate);
            if (this.comboAccountType.getSelectionIndex() == 0) {
               this.customer.setAccountType("HOME");
               this.customer.setFirstName(this.txtFirstName.getText().trim());
               this.customer.setLastName(this.txtLastName.getText().trim());
               this.customer.setDocumentType(this.comboDocumentType.getText());
               this.customer.setDocumentNumber(this.txtDocumentNumber.getText().trim());
            } else {
               this.customer.setAccountType("BUSINESS");
               this.customer.setCompanyName(this.txtCompanyName.getText().trim());
               this.customer.setBusinessName(this.txtBusinessName.getText().trim());
               this.customer.setContactName(this.txtContactName.getText().trim());
               this.customer.setCuit(this.txtCuit.getText().trim());
               this.customer.setGrossIncomeNumber(this.txtGrossIncomeNumber.getText().trim());
            }

            this.customer.setPhone(this.txtPhone.getText().trim());
            this.customer.setEmail(this.txtEmail.getText().trim());
            this.customer.setAddressStreet(this.txtAddressStreet.getText().trim());
            this.customer.setAddressNumber(this.txtAddressNumber.getText().trim());
            this.customer.setCity(this.txtCity.getText().trim());
            this.customer.setProvince(this.txtProvincia.getText().trim());
            this.customer.setObservations(this.txtObservations.getText().trim());
            String vatConditionName = this.comboVatCondition.getText();
            this.customer.setVatCondition(this.getOrderService().getVatConditionByName(vatConditionName));
            this.customer.setAllowOnAccountOperations(this.btnAllowOnAccount.getSelection());
            this.customer.setOnAccountLimited(this.btnOnAccountLimited.getSelection());
            double onAccountLimit = 0.0;

            try {
               onAccountLimit = Double.parseDouble(this.txtOnAccountLimit.getText().trim().replaceAll(",", "."));
            } catch (Exception var6) {
            }

            this.customer.setOnAccountLimit(onAccountLimit);
         } catch (Exception var7) {
            logger.error("Error al modificar cliente");
            logger.error(var7.getMessage());
            //logger.error(var7);
         }

         this.close();
      }

   }
   @Override
   public boolean validateFields() {
      boolean valid = true;

      try {
         if (this.comboAccountType.getSelectionIndex() == 0) {
            if (this.txtFirstName.getText().equals("")) {
               this.alert("Ingresa el nombre");
               valid = false;
            }
         } else if (this.txtCompanyName.getText().trim().equals("")) {
            this.alert("Ingresa la razón social");
            valid = false;
         } else {
            Customer customer = this.getCustomerService().getCustomerByCompanyName(this.txtCompanyName.getText().trim());
            if (customer != null && !this.txtCompanyName.getText().trim().equalsIgnoreCase(this.getCustomer().getCompanyName())) {
               this.alert("Ya existe una empresa con esa razón social");
               valid = false;
            }
         }
      } catch (Exception var3) {
         logger.error("Error en la validación de modificar cliente");
         logger.error(var3.getMessage());
         //logger.error(var3);
      }

      return valid;
   }
}
