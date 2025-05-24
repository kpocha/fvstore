package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.VatCondition;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class AddNewCustomer extends AbstractFVDialog {
   protected static Logger logger = LoggerFactory.getLogger("AddNewCustomer");
   protected String action = "";
   protected Combo comboAccountType;
   protected Text txtCompanyName;
   protected Text txtBusinessName;
   protected Text txtContactName;
   protected Text txtCuit;
   protected Text txtGrossIncomeNumber;
   protected Text txtFirstName;
   protected Text txtLastName;
   protected Combo comboDocumentType;
   protected Text txtPhone;
   protected Text txtEmail;
   protected Text txtAddressStreet;
   protected Text txtAddressNumber;
   protected Text txtCity;
   protected Text txtProvincia;
   protected Text txtObservations;
   protected Combo comboVatCondition;
   protected Button btnAllowOnAccount;
   protected Button btnOnAccountLimited;
   protected Button btnOnAccountIlimited;
   protected Composite headerContainer;
   protected Composite formContainer;
   private int currentFormId;
   protected Customer customer;
   protected Text txtOnAccountLimit;
   protected Text txtOnAccountTotal;
   protected Text txtDocumentNumber;

   public AddNewCustomer(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FillLayout fl_container = new FillLayout(256);
      fl_container.marginWidth = 5;
      fl_container.marginHeight = 5;
      container.setLayout(fl_container);
      TabFolder tabFolder = new TabFolder(container, 0);
      TabItem tbtmGeneral = new TabItem(tabFolder, 0);
      tbtmGeneral.setText("General");
      Composite composite = new Composite(tabFolder, 0);
      composite.setLayout(new GridLayout(1, false));
      tbtmGeneral.setControl(composite);
      this.headerContainer = new Composite(composite, 0);
      GridLayout gl_headerContainer = new GridLayout(2, false);
      gl_headerContainer.marginHeight = 0;
      this.headerContainer.setLayout(gl_headerContainer);
      this.headerContainer.setLayoutData(new GridData(4, 1, true, false));
      this.createHeaderForm();
      this.formContainer = new Composite(composite, 0);
      GridLayout gl_formContainer = new GridLayout(2, false);
      gl_formContainer.marginHeight = 0;
      this.formContainer.setLayout(gl_formContainer);
      this.formContainer.setLayoutData(new GridData(4, 1, true, false));
      TabItem tbtmCuentaCorriente = new TabItem(tabFolder, 0);
      tbtmCuentaCorriente.setText("Cuenta corriente");
      Composite composite_1 = new Composite(tabFolder, 0);
      tbtmCuentaCorriente.setControl(composite_1);
      composite_1.setLayout(new GridLayout(2, false));
      Label lblEstado = new Label(composite_1, 0);
      lblEstado.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblEstado.setText("Estado:");
      this.btnAllowOnAccount = new Button(composite_1, 32);
      this.btnAllowOnAccount.setText("Habilitada");
      this.btnAllowOnAccount.setSelection(true);
      Label lblTipo = new Label(composite_1, 0);
      lblTipo.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTipo.setText("Tipo:");
      Composite composite_2 = new Composite(composite_1, 0);
      composite_2.setLayout(new GridLayout(2, false));
      this.btnOnAccountIlimited = new Button(composite_2, 16);
      this.btnOnAccountIlimited.setBounds(0, 0, 83, 16);
      this.btnOnAccountIlimited.setText("Ilimitada");
      this.btnOnAccountIlimited.setSelection(true);
      this.btnOnAccountLimited = new Button(composite_2, 16);
      this.btnOnAccountLimited.setBounds(0, 0, 83, 16);
      this.btnOnAccountLimited.setText("Limitada");
      Label lblLmiteDeCta = new Label(composite_1, 0);
      lblLmiteDeCta.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblLmiteDeCta.setText("Monto límite: $");
      this.txtOnAccountLimit = new Text(composite_1, 2048);
      this.txtOnAccountLimit.setTextLimit(30);
      GridData gd_txtOnAccountLimit = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtOnAccountLimit.widthHint = 100;
      this.txtOnAccountLimit.setLayoutData(gd_txtOnAccountLimit);
      Label lblSaldoActual = new Label(composite_1, 0);
      lblSaldoActual.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblSaldoActual.setText("Saldo actual: $");
      this.txtOnAccountTotal = new Text(composite_1, 2056);
      this.txtOnAccountTotal.setTextLimit(30);
      GridData gd_txtOnAccountTotal = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtOnAccountTotal.widthHint = 100;
      this.txtOnAccountTotal.setLayoutData(gd_txtOnAccountTotal);
      if (this.getCustomer() != null && !this.getCustomer().isHomeCustomer()) {
         this.createBusinessCustomerForm();
         this.initBusinessCustomerForm();
      } else {
         this.createHomeCustomerForm();
         this.initHomeCustomerForm();
      }

      return container;
   }

   private void createHeaderForm() {
      Label lblAccountType = new Label(this.headerContainer, 0);
      lblAccountType.setAlignment(131072);
      GridData gd_lblAccountType = new GridData(131072, 16777216, false, false, 1, 1);
      gd_lblAccountType.widthHint = 188;
      lblAccountType.setLayoutData(gd_lblAccountType);
      lblAccountType.setBounds(5, 9, 77, 13);
      lblAccountType.setText("Tipo de cuenta:");
      this.comboAccountType = new Combo(this.headerContainer, 8);
      GridData gd_comboAccountType = new GridData(100, 14);
      gd_comboAccountType.grabExcessHorizontalSpace = true;
      gd_comboAccountType.horizontalAlignment = 4;
      this.comboAccountType.setLayoutData(gd_comboAccountType);

      //this.comboAccountType.addSelectionListener(new 1(this));
      this.comboAccountType.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            changedAccountType();
         }
      });
      
      this.comboAccountType.add("Individuo");
      this.comboAccountType.add("Empresa");
   }

   private void createBusinessCustomerForm() {
      Label lblCompanyName = new Label(this.formContainer, 0);
      lblCompanyName.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCompanyName.setText("Razón social: *");
      this.txtCompanyName = new Text(this.formContainer, 2048);
      GridData gd_txtCompanyName = new GridData(200, 14);
      gd_txtCompanyName.horizontalAlignment = 4;
      this.txtCompanyName.setLayoutData(gd_txtCompanyName);
      this.txtCompanyName.setTextLimit(60);
      Label lblBusinessName = new Label(this.formContainer, 0);
      lblBusinessName.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblBusinessName.setText("Nombre comercial:");
      this.txtBusinessName = new Text(this.formContainer, 2048);
      this.txtBusinessName.setTextLimit(60);
      this.txtBusinessName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblCuit = new Label(this.formContainer, 0);
      lblCuit.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCuit.setText("CUIT:");
      this.txtCuit = new Text(this.formContainer, 2048);
      this.txtCuit.setTextLimit(11);
      this.txtCuit.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblPhone = new Label(this.formContainer, 0);
      lblPhone.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPhone.setText("Teléfono:");
      this.txtPhone = new Text(this.formContainer, 2048);
      this.txtPhone.setTextLimit(60);
      this.txtPhone.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblEmail = new Label(this.formContainer, 0);
      lblEmail.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblEmail.setText("E-mail:");
      this.txtEmail = new Text(this.formContainer, 2048);
      this.txtEmail.setTextLimit(60);
      this.txtEmail.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblAddressStreet = new Label(this.formContainer, 0);
      lblAddressStreet.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblAddressStreet.setText("Calle:");
      this.txtAddressStreet = new Text(this.formContainer, 2048);
      this.txtAddressStreet.setTextLimit(60);
      this.txtAddressStreet.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblAddressNumber = new Label(this.formContainer, 0);
      lblAddressNumber.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblAddressNumber.setText("Número:");
      this.txtAddressNumber = new Text(this.formContainer, 2048);
      this.txtAddressNumber.setTextLimit(60);
      this.txtAddressNumber.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblCity = new Label(this.formContainer, 0);
      lblCity.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCity.setText("Localidad:");
      this.txtCity = new Text(this.formContainer, 2048);
      this.txtCity.setTextLimit(60);
      this.txtCity.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblProvincia = new Label(this.formContainer, 0);
      lblProvincia.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblProvincia.setText("Provincia:");
      this.txtProvincia = new Text(this.formContainer, 2048);
      this.txtProvincia.setTextLimit(60);
      this.txtProvincia.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblContactName = new Label(this.formContainer, 0);
      lblContactName.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblContactName.setText("Persona de contacto:");
      this.txtContactName = new Text(this.formContainer, 2048);
      this.txtContactName.setTextLimit(60);
      this.txtContactName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblGrossIncomeNumber = new Label(this.formContainer, 0);
      lblGrossIncomeNumber.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblGrossIncomeNumber.setText("Ingresos Brutos:");
      this.txtGrossIncomeNumber = new Text(this.formContainer, 2048);
      this.txtGrossIncomeNumber.setTextLimit(60);
      this.txtGrossIncomeNumber.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblVatCondition = new Label(this.formContainer, 0);
      lblVatCondition.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblVatCondition.setText("Condición IVA: *");
      this.comboVatCondition = new Combo(this.formContainer, 8);
      this.comboVatCondition.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      List<VatCondition> vatConditions = this.getOrderService().getAllVatConditions();
      int selectedIdx = 0;

      for(Iterator var17 = vatConditions.iterator(); var17.hasNext(); ++selectedIdx) {
         VatCondition vc = (VatCondition)var17.next();
         this.comboVatCondition.add(vc.getName());
         if ("Responsable Inscripto".equalsIgnoreCase(vc.getName())) {
            this.comboVatCondition.select(selectedIdx);
         }
      }

      Label lblObservations = new Label(this.formContainer, 0);
      lblObservations.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblObservations.setText("Observaciones:");
      this.txtObservations = new Text(this.formContainer, 2626);
      this.txtObservations.setTextLimit(255);
      GridData gd_txtObservations = new GridData(1808);
      gd_txtObservations.grabExcessVerticalSpace = false;
      gd_txtObservations.verticalAlignment = 128;
      gd_txtObservations.heightHint = 40;
      this.txtObservations.setLayoutData(gd_txtObservations);
   }

   private void createHomeCustomerForm() {
      Label lblFirstName = new Label(this.formContainer, 0);
      lblFirstName.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblFirstName.setText("Nombre: *");
      this.txtFirstName = new Text(this.formContainer, 2048);
      this.txtFirstName.setTextLimit(60);
      GridData gd_txtFirstName = new GridData(200, 14);
      gd_txtFirstName.horizontalAlignment = 4;
      this.txtFirstName.setLayoutData(gd_txtFirstName);
      Label lblLastName = new Label(this.formContainer, 0);
      lblLastName.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblLastName.setText("Apellido:");
      this.txtLastName = new Text(this.formContainer, 2048);
      this.txtLastName.setTextLimit(60);
      this.txtLastName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblTipoDeDocumento = new Label(this.formContainer, 0);
      lblTipoDeDocumento.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTipoDeDocumento.setText("Tipo y nro. de documento:");
      Composite composite = new Composite(this.formContainer, 0);
      composite.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      GridLayout gl_composite = new GridLayout(2, false);
      gl_composite.marginHeight = 0;
      gl_composite.marginWidth = 0;
      composite.setLayout(gl_composite);
      this.comboDocumentType = new Combo(composite, 8);
      GridData gd_comboDocumentType = new GridData(16384, 16777216, false, false, 1, 1);
      gd_comboDocumentType.widthHint = 30;
      this.comboDocumentType.setLayoutData(gd_comboDocumentType);
      this.comboDocumentType.setBounds(0, 0, 93, 21);
      this.comboDocumentType.add("");
      this.comboDocumentType.select(0);
      this.comboDocumentType.add("CI");
      this.comboDocumentType.add("DNI");
      this.comboDocumentType.add("LC");
      this.comboDocumentType.add("LE");
      this.comboDocumentType.add("PAS");
      this.txtDocumentNumber = new Text(composite, 2048);
      this.txtDocumentNumber.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.txtDocumentNumber.setBounds(0, 0, 76, 19);
      Label lblPhone = new Label(this.formContainer, 0);
      lblPhone.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPhone.setText("Teléfono:");
      this.txtPhone = new Text(this.formContainer, 2048);
      this.txtPhone.setTextLimit(60);
      this.txtPhone.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblEmail = new Label(this.formContainer, 0);
      lblEmail.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblEmail.setText("E-mail:");
      this.txtEmail = new Text(this.formContainer, 2048);
      this.txtEmail.setTextLimit(60);
      this.txtEmail.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblAddressStreet = new Label(this.formContainer, 0);
      lblAddressStreet.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblAddressStreet.setText("Calle:");
      this.txtAddressStreet = new Text(this.formContainer, 2048);
      this.txtAddressStreet.setTextLimit(60);
      this.txtAddressStreet.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblAddressNumber = new Label(this.formContainer, 0);
      lblAddressNumber.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblAddressNumber.setText("Número:");
      this.txtAddressNumber = new Text(this.formContainer, 2048);
      this.txtAddressNumber.setTextLimit(60);
      this.txtAddressNumber.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblCity = new Label(this.formContainer, 0);
      lblCity.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCity.setText("Localidad:");
      this.txtCity = new Text(this.formContainer, 2048);
      this.txtCity.setTextLimit(60);
      this.txtCity.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblProvincia = new Label(this.formContainer, 0);
      lblProvincia.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblProvincia.setText("Provincia:");
      this.txtProvincia = new Text(this.formContainer, 2048);
      this.txtProvincia.setTextLimit(60);
      this.txtProvincia.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblVatCondition = new Label(this.formContainer, 0);
      lblVatCondition.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblVatCondition.setText("Condición IVA: *");
      this.comboVatCondition = new Combo(this.formContainer, 8);
      this.comboVatCondition.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      List<VatCondition> vatConditions = this.getOrderService().getAllVatConditions();
      int selectedIdx = 0;

      for(Iterator var18 = vatConditions.iterator(); var18.hasNext(); ++selectedIdx) {
         VatCondition vc = (VatCondition)var18.next();
         this.comboVatCondition.add(vc.getName());
         if ("Consumidor Final".equalsIgnoreCase(vc.getName())) {
            this.comboVatCondition.select(selectedIdx);
         }
      }

      Label lblObservations = new Label(this.formContainer, 0);
      lblObservations.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblObservations.setText("Observaciones:");
      this.txtObservations = new Text(this.formContainer, 2626);
      this.txtObservations.setTextLimit(255);
      GridData gd_txtObservations = new GridData(1808);
      gd_txtObservations.grabExcessVerticalSpace = false;
      gd_txtObservations.verticalAlignment = 128;
      gd_txtObservations.heightHint = 40;
      this.txtObservations.setLayoutData(gd_txtObservations);
   }

   private void initHomeCustomerForm() {
      this.comboAccountType.select(0);
      this.currentFormId = 0;
      this.txtFirstName.setFocus();
   }

   private void initBusinessCustomerForm() {
      this.comboAccountType.select(1);
      this.currentFormId = 1;
      this.txtCompanyName.setFocus();
   }

   private void changedAccountType() {
      int idx = this.comboAccountType.getSelectionIndex();
      Control control;
      int var3;
      int var4;
      Control[] var5;
      if (idx == 0 && this.currentFormId != 0) {
         var4 = (var5 = this.formContainer.getChildren()).length;

         for(var3 = 0; var3 < var4; ++var3) {
            control = var5[var3];
            control.dispose();
         }

         this.createHomeCustomerForm();
         this.comboAccountType.select(0);
         this.currentFormId = 0;
      } else if (idx == 1 && this.currentFormId != 1) {
         var4 = (var5 = this.formContainer.getChildren()).length;

         for(var3 = 0; var3 < var4; ++var3) {
            control = var5[var3];
            control.dispose();
         }

         this.createBusinessCustomerForm();
         this.comboAccountType.select(1);
         this.currentFormId = 1;
      }

      this.formContainer.layout();
   }

   private void addNewCustomer() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            Date creationDate = new Date();
            Customer customer = new Customer();
            customer.setCreationDate(creationDate);
            customer.setLastUpdatedDate(creationDate);
            if (this.comboAccountType.getSelectionIndex() == 0) {
               customer.setAccountType("HOME");
               customer.setFirstName(this.txtFirstName.getText().trim());
               customer.setLastName(this.txtLastName.getText().trim());
               customer.setDocumentType(this.comboDocumentType.getText());
               customer.setDocumentNumber(this.txtDocumentNumber.getText().trim());
            } else {
               customer.setAccountType("BUSINESS");
               customer.setCompanyName(this.txtCompanyName.getText().trim());
               customer.setBusinessName(this.txtBusinessName.getText().trim());
               customer.setContactName(this.txtContactName.getText().trim());
               customer.setCuit(this.txtCuit.getText().trim());
               customer.setGrossIncomeNumber(this.txtGrossIncomeNumber.getText().trim());
            }

            customer.setPhone(this.txtPhone.getText().trim());
            customer.setEmail(this.txtEmail.getText().trim());
            customer.setAddressStreet(this.txtAddressStreet.getText().trim());
            customer.setAddressNumber(this.txtAddressNumber.getText().trim());
            customer.setCity(this.txtCity.getText().trim());
            customer.setProvince(this.txtProvincia.getText().trim());
            customer.setObservations(this.txtObservations.getText().trim());
            String vatConditionName = this.comboVatCondition.getText();
            customer.setVatCondition(this.getOrderService().getVatConditionByName(vatConditionName));
            customer.setAllowOnAccountOperations(this.btnAllowOnAccount.getSelection());
            customer.setOnAccountLimited(this.btnOnAccountLimited.getSelection());
            double onAccountLimit = 0.0;

            try {
               onAccountLimit = Double.parseDouble(this.txtOnAccountLimit.getText().trim().replaceAll(",", "."));
            } catch (Exception var7) {
            }

            customer.setOnAccountLimit(onAccountLimit);
            this.setCustomer(customer);
         } catch (Exception var8) {
            logger.error("Error en alta de cliente");
            logger.error(var8.getMessage());
            //logger.error(var8);
         }

         this.close();
      }

   }

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
            if (customer != null) {
               this.alert("Ya existe una empresa con esa razón social");
               valid = false;
            }
         }
      } catch (Exception var3) {
         logger.error("Error en validación de alta de cliente");
         logger.error(var3.getMessage());
         //logger.error(var3);
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nuevo cliente");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.addNewCustomer();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Guardar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(460, 570);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
}
