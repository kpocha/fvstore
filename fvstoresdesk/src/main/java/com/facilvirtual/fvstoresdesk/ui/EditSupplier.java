package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Supplier;
import com.facilvirtual.fvstoresdesk.model.VatCondition;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EditSupplier extends AddNewSupplier {
   private static Logger logger = LoggerFactory.getLogger("EditSupplier");

   public EditSupplier(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      this.initSupplier();
      return container;
   }

   private void initSupplier() {
      try {
         this.txtCompanyName.setText(this.getSupplier().getCompanyName());
         this.txtBusinessName.setText(this.getSupplier().getBusinessName());
         this.txtCuit.setText(this.getSupplier().getCuit());
         this.txtPhone.setText(this.getSupplier().getPhone());
         this.txtMobile.setText(this.getSupplier().getMobile());
         this.txtEmail.setText(this.getSupplier().getEmail());
         this.txtAddressStreet.setText(this.getSupplier().getAddressStreet());
         this.txtAddressNumber.setText(this.getSupplier().getAddressNumber());
         this.txtAddressExtra.setText(this.getSupplier().getAddressExtra());
         this.txtCity.setText(this.getSupplier().getCity());
         this.txtContactName.setText(this.getSupplier().getContactName());
         this.txtGrossIncomeNumber.setText(this.getSupplier().getGrossIncomeNumber());
         List<VatCondition> vatConditions = this.getOrderService().getAllVatConditionsForCompany();
         int vatConditionIdx = 0;

         for(Iterator var4 = vatConditions.iterator(); var4.hasNext(); ++vatConditionIdx) {
            VatCondition vc = (VatCondition)var4.next();
            if (this.getSupplier().getVatCondition().getId().equals(vc.getId())) {
               this.comboVatCondition.select(vatConditionIdx);
            }
         }

         this.txtWebsite.setText(this.getSupplier().getWebsite());
         this.txtObservations.setText(this.getSupplier().getObservations());
         this.btnDeleted.setSelection(!this.getSupplier().isActive());
      } catch (Exception var5) {
         logger.error("Error al inicializar proveedor para editar");
         logger.error(var5.getMessage());
        // //logger.error(var5);
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            Date editionDate = new Date();
            this.supplier.setLastUpdatedDate(editionDate);
            this.supplier.setCompanyName(this.txtCompanyName.getText().trim());
            this.supplier.setBusinessName(this.txtBusinessName.getText().trim());
            this.supplier.setCuit(this.txtCuit.getText().trim());
            this.supplier.setPhone(this.txtPhone.getText().trim());
            this.supplier.setMobile(this.txtMobile.getText().trim());
            this.supplier.setEmail(this.txtEmail.getText().trim());
            this.supplier.setAddressStreet(this.txtAddressStreet.getText().trim());
            this.supplier.setAddressNumber(this.txtAddressNumber.getText().trim());
            this.supplier.setAddressExtra(this.txtAddressExtra.getText().trim());
            this.supplier.setCity(this.txtCity.getText().trim());
            this.supplier.setContactName(this.txtContactName.getText().trim());
            this.supplier.setGrossIncomeNumber(this.txtGrossIncomeNumber.getText().trim());
            String vatConditionName = this.comboVatCondition.getText();
            this.supplier.setVatCondition(this.getOrderService().getVatConditionByName(vatConditionName));
            this.supplier.setWebsite(this.txtWebsite.getText().trim());
            this.supplier.setObservations(this.txtObservations.getText().trim());
            this.supplier.setActive(!this.btnDeleted.getSelection());
            this.getProductService().saveSupplier(this.supplier);
         } catch (Exception var3) {
            logger.error("Error al modificar proveedor");
            logger.error(var3.getMessage());
            ////logger.error(var3);
         }

         this.close();
      }

   }
   @Override
   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtCompanyName.getText().trim())) {
         valid = false;
         this.alert("Ingresa la razón social");
      }

      if (valid) {
         Supplier supplier = this.getProductService().getSupplierByCompanyName(this.txtCompanyName.getText().trim());
         if (supplier != null && !supplier.getCompanyName().trim().equalsIgnoreCase(this.txtCompanyName.getText().trim())) {
            valid = false;
            this.alert("Ya existe un proveedor con esa razón social");
         }
      }

      return valid;
   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Modificar proveedor");
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
      this.createButton(parent, 0, "Guardar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(408, 510);
   }
}
