package com.facilvirtual.fvstoresdesk.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.facilvirtual.fvstoresdesk.model.Supplier;
import com.facilvirtual.fvstoresdesk.model.VatCondition;

public class AddNewSupplier extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("AddNewSupplier");
   protected String action = "";
   protected Supplier supplier;
   protected Text txtCompanyName;
   protected Text txtBusinessName;
   protected Text txtPhone;
   protected Text txtMobile;
   protected Text txtEmail;
   protected Text txtAddressStreet;
   protected Text txtAddressNumber;
   protected Text txtAddressExtra;
   protected Text txtCity;
   protected Text txtContactName;
   protected Text txtCuit;
   protected Text txtGrossIncomeNumber;
   protected Combo comboVatCondition;
   protected Text txtWebsite;
   protected Text txtObservations;
   protected Button btnDeleted;

   public AddNewSupplier(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      GridLayout gl_container = new GridLayout(2, false);
      gl_container.marginTop = 5;
      gl_container.marginRight = 10;
      gl_container.marginLeft = 10;
      container.setLayout(gl_container);
      Label lblRaznSocial = new Label(container, 0);
      lblRaznSocial.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblRaznSocial.setText("Razón Social:*");
      this.txtCompanyName = new Text(container, 2048);
      this.txtCompanyName.setTextLimit(60);
      this.txtCompanyName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblNombreComercial = new Label(container, 0);
      lblNombreComercial.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNombreComercial.setText("Nombre Comercial:");
      this.txtBusinessName = new Text(container, 2048);
      this.txtBusinessName.setTextLimit(60);
      this.txtBusinessName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblCuit = new Label(container, 0);
      lblCuit.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCuit.setText("CUIT:");
      this.txtCuit = new Text(container, 2048);
      this.txtCuit.setTextLimit(11);
      this.txtCuit.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblTelfono = new Label(container, 0);
      lblTelfono.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTelfono.setText("Teléfono:");
      this.txtPhone = new Text(container, 2048);
      this.txtPhone.setTextLimit(60);
      this.txtPhone.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblCelular = new Label(container, 0);
      lblCelular.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCelular.setText("Celular:");
      this.txtMobile = new Text(container, 2048);
      this.txtMobile.setTextLimit(60);
      this.txtMobile.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblEmail = new Label(container, 0);
      lblEmail.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblEmail.setText("E-mail:");
      this.txtEmail = new Text(container, 2048);
      this.txtEmail.setTextLimit(60);
      this.txtEmail.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblCalle = new Label(container, 0);
      lblCalle.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCalle.setText("Calle:");
      this.txtAddressStreet = new Text(container, 2048);
      this.txtAddressStreet.setTextLimit(60);
      this.txtAddressStreet.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblNmero = new Label(container, 0);
      lblNmero.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNmero.setText("Número:");
      this.txtAddressNumber = new Text(container, 2048);
      this.txtAddressNumber.setTextLimit(60);
      this.txtAddressNumber.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblInformacinDeDomicilio = new Label(container, 0);
      lblInformacinDeDomicilio.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblInformacinDeDomicilio.setText("Información de domicilio adicional:");
      this.txtAddressExtra = new Text(container, 2048);
      this.txtAddressExtra.setTextLimit(60);
      this.txtAddressExtra.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblLocalidad = new Label(container, 0);
      lblLocalidad.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblLocalidad.setText("Localidad:");
      this.txtCity = new Text(container, 2048);
      this.txtCity.setTextLimit(60);
      this.txtCity.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblPersonaDeContacto = new Label(container, 0);
      lblPersonaDeContacto.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPersonaDeContacto.setText("Persona de contacto:");
      this.txtContactName = new Text(container, 2048);
      this.txtContactName.setTextLimit(60);
      this.txtContactName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblIngresosBrutos = new Label(container, 0);
      lblIngresosBrutos.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblIngresosBrutos.setText("Ingresos Brutos:");
      this.txtGrossIncomeNumber = new Text(container, 2048);
      this.txtGrossIncomeNumber.setTextLimit(60);
      this.txtGrossIncomeNumber.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblCondicinIva = new Label(container, 0);
      lblCondicinIva.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCondicinIva.setText("Condición IVA: *");
      this.comboVatCondition = new Combo(container, 8);
      this.comboVatCondition.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      List<VatCondition> vatConditions = this.getOrderService().getAllVatConditionsForCompany();
      int selectedIdx = 0;

      for(Iterator var20 = vatConditions.iterator(); var20.hasNext(); ++selectedIdx) {
         VatCondition vc = (VatCondition)var20.next();
         this.comboVatCondition.add(vc.getName());
         if ("Responsable Inscripto".equalsIgnoreCase(vc.getName())) {
            this.comboVatCondition.select(selectedIdx);
         }
      }

      Label lblPginaWeb = new Label(container, 0);
      lblPginaWeb.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPginaWeb.setText("Página Web:");
      this.txtWebsite = new Text(container, 2048);
      this.txtWebsite.setTextLimit(60);
      this.txtWebsite.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblObservaciones = new Label(container, 0);
      lblObservaciones.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblObservaciones.setText("Observaciones:");
      this.txtObservations = new Text(container, 2626);
      this.txtObservations.setTextLimit(255);
      GridData gd_txtObservations = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtObservations.heightHint = 50;
      this.txtObservations.setLayoutData(gd_txtObservations);
      if (this.getSupplier() != null) {
         new Label(container, 0);
         this.btnDeleted = new Button(container, 32);
         this.btnDeleted.setText("Eliminado");
      }

      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            Date creationDate = new Date();
            Supplier supplier = new Supplier();
            supplier.setCreationDate(creationDate);
            supplier.setLastUpdatedDate(creationDate);
            supplier.setCompanyName(this.txtCompanyName.getText().trim());
            supplier.setBusinessName(this.txtBusinessName.getText().trim());
            supplier.setCuit(this.txtCuit.getText().trim());
            supplier.setPhone(this.txtPhone.getText().trim());
            supplier.setMobile(this.txtMobile.getText().trim());
            supplier.setEmail(this.txtEmail.getText().trim());
            supplier.setAddressStreet(this.txtAddressStreet.getText().trim());
            supplier.setAddressNumber(this.txtAddressNumber.getText().trim());
            supplier.setAddressExtra(this.txtAddressExtra.getText().trim());
            supplier.setCity(this.txtCity.getText().trim());
            supplier.setContactName(this.txtContactName.getText().trim());
            supplier.setGrossIncomeNumber(this.txtGrossIncomeNumber.getText().trim());
            String vatConditionName = this.comboVatCondition.getText();
            supplier.setVatCondition(this.getOrderService().getVatConditionByName(vatConditionName));
            supplier.setWebsite(this.txtWebsite.getText().trim());
            supplier.setObservations(this.txtObservations.getText().trim());
            supplier.setActive(true);
            this.getProductService().saveSupplier(supplier);
         } catch (Exception var4) {
            logger.error("Error al guardar proveedor");
            logger.error(var4.getMessage());
            //logger.error(var4);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtCompanyName.getText().trim())) {
         valid = false;
         this.alert("Ingresa la razón social");
      }

      if (valid) {
         Supplier supplier = this.getProductService().getSupplierByCompanyName(this.txtCompanyName.getText().trim());
         if (supplier != null) {
            valid = false;
            this.alert("Ya existe un proveedor con esa razón social");
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nuevo proveedor");
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
      return new Point(408, 510);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
   }
}
