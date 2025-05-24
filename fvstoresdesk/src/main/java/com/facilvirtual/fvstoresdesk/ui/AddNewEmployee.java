package com.facilvirtual.fvstoresdesk.ui;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.facilvirtual.fvstoresdesk.model.Employee;

public class AddNewEmployee extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("AddNewEmployee");
   protected String action = "";
   protected Employee employee;
   protected Button btnDeleted;
   protected TabFolder tabFolder;
   protected TabItem tbtmDatosDeUsuario;
   protected TabItem tbtmPermisos;
   protected Composite composite;
   protected Label lblNombreDeUsuario;
   protected Text txtUsername;
   protected Label lblNombre;
   protected Text txtFirstName;
   protected Label lblApellido;
   protected Label lblCargo;
   protected Text txtLastName;
   protected Text txtJobPosition;
   protected Label lblTipoDeCuenta;
   protected Composite composite_1;
   protected Label lblPassword;
   protected Label lblPassword2;
   protected Text txtPassword;
   protected Text txtPassword2;
   protected Composite composite_2;
   protected Button btnAllowLogin;
   protected Button btnAllowCreateOrder;
   protected Button btnAllowModifyPrice;
   protected Button btnAllowModuleProducts;
   protected Button btnAllowModuleOrders;
   protected Button btnAllowModulePurchases;
   protected Button btnAllowModuleSuppliers;
   protected Button btnAllowModuleCustomers;
   protected Button btnAllowModuleReports;
   protected Button btnAllowModuleLists;
   protected Button btnAllowModuleTools;
   protected Button btnAllowApplyDiscount;
   protected Button btnAllowOpenCash;
   protected Button btnAllowCreateIncome;
   protected Button btnAllowModuleCash;
   protected Button btnAdmin;
   protected Button btnLimited;
   protected Button allowLogin;
   protected Label lblNewLabel;
   protected Button btnAllowCloseCash;
   protected Button btnAllowCreateOutflow;
   protected Button btnAllowCreatePurchase;
   protected Button btnAllowApplySurcharge;
   private Label lblComisinPorVenta;
   protected Text txtCommissionPerSale;

   public AddNewEmployee(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout(new GridLayout(1, false));
      this.tabFolder = new TabFolder(container, 0);
      GridData gd_tabFolder = new GridData(4, 16777216, true, false, 1, 1);
      gd_tabFolder.widthHint = 402;
      gd_tabFolder.heightHint = 385;
      this.tabFolder.setLayoutData(gd_tabFolder);
      this.tbtmDatosDeUsuario = new TabItem(this.tabFolder, 0);
      this.tbtmDatosDeUsuario.setText("Datos de usuario");
      this.composite = new Composite(this.tabFolder, 0);
      this.tbtmDatosDeUsuario.setControl(this.composite);
      GridLayout gl_composite = new GridLayout(2, false);
      gl_composite.marginLeft = 5;
      gl_composite.marginRight = 5;
      gl_composite.marginTop = 5;
      this.composite.setLayout(gl_composite);
      this.lblNombreDeUsuario = new Label(this.composite, 0);
      this.lblNombreDeUsuario.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblNombreDeUsuario.setText("Nombre de usuario: *");
      this.txtUsername = new Text(this.composite, 2048);
      this.txtUsername.setTextLimit(30);
      this.txtUsername.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.lblPassword = new Label(this.composite, 0);
      this.lblPassword.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblPassword.setText("Contraseña: *");
      this.txtPassword = new Text(this.composite, 4196352);
      this.txtPassword.setTextLimit(30);
      this.txtPassword.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.lblPassword2 = new Label(this.composite, 0);
      this.lblPassword2.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblPassword2.setText("Contraseña (de nuevo): *");
      this.txtPassword2 = new Text(this.composite, 4196352);
      this.txtPassword2.setTextLimit(30);
      this.txtPassword2.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.lblTipoDeCuenta = new Label(this.composite, 0);
      this.lblTipoDeCuenta.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblTipoDeCuenta.setText("Tipo de cuenta:");
      this.composite_1 = new Composite(this.composite, 0);
      this.composite_1.setLayout(new GridLayout(2, false));
      this.btnAdmin = new Button(this.composite_1, 16);
      this.btnAdmin.setBounds(0, 0, 83, 16);
      this.btnAdmin.setText("Administrador");
      this.btnAdmin.setSelection(true);
      this.btnLimited = new Button(this.composite_1, 16);
      this.btnLimited.setText("Limitada");
      this.lblNombre = new Label(this.composite, 0);
      this.lblNombre.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblNombre.setText("Nombre:");
      this.txtFirstName = new Text(this.composite, 2048);
      this.txtFirstName.setTextLimit(30);
      this.txtFirstName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.lblApellido = new Label(this.composite, 0);
      this.lblApellido.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblApellido.setText("Apellido:");
      this.txtLastName = new Text(this.composite, 2048);
      this.txtLastName.setTextLimit(30);
      this.txtLastName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.lblCargo = new Label(this.composite, 0);
      this.lblCargo.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblCargo.setText("Cargo:");
      this.txtJobPosition = new Text(this.composite, 2048);
      this.txtJobPosition.setTextLimit(30);
      this.txtJobPosition.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.lblComisinPorVenta = new Label(this.composite, 0);
      this.lblComisinPorVenta.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblComisinPorVenta.setText("Comisión por venta (%):");
      this.txtCommissionPerSale = new Text(this.composite, 2048);
      this.txtCommissionPerSale.setTextLimit(30);
      this.txtCommissionPerSale.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.lblNewLabel = new Label(this.composite, 0);
      this.lblNewLabel.setText("");
      this.btnAllowLogin = new Button(this.composite, 32);
      this.btnAllowLogin.setText("Puede iniciar sesión");
      this.btnAllowLogin.setSelection(true);
      if (this.getEmployee() != null) {
         new Label(this.composite, 0);
         this.btnDeleted = new Button(this.composite, 32);
         this.btnDeleted.setText("Eliminado");
      }

      this.tbtmPermisos = new TabItem(this.tabFolder, 0);
      this.tbtmPermisos.setText("Permisos");
      this.composite_2 = new Composite(this.tabFolder, 0);
      this.tbtmPermisos.setControl(this.composite_2);
      GridLayout gl_composite_2 = new GridLayout(2, false);
      gl_composite_2.marginTop = 5;
      gl_composite_2.marginLeft = 5;
      this.composite_2.setLayout(gl_composite_2);
      this.btnAllowOpenCash = new Button(this.composite_2, 32);
      this.btnAllowOpenCash.setText("Puede abrir la caja");
      this.btnAllowModuleProducts = new Button(this.composite_2, 32);
      this.btnAllowModuleProducts.setText("Puede ingresar al módulo Artículos");
      this.btnAllowCloseCash = new Button(this.composite_2, 32);
      this.btnAllowCloseCash.setText("Puede cerrar la caja");
      this.btnAllowModuleOrders = new Button(this.composite_2, 32);
      this.btnAllowModuleOrders.setText("Puede ingresar al módulo Ventas");
      this.btnAllowCreateIncome = new Button(this.composite_2, 32);
      this.btnAllowCreateIncome.setText("Puede registrar ingresos a caja");
      this.btnAllowModulePurchases = new Button(this.composite_2, 32);
      this.btnAllowModulePurchases.setText("Puede ingresar al módulo Compras");
      this.btnAllowCreateOutflow = new Button(this.composite_2, 32);
      this.btnAllowCreateOutflow.setText("Puede registrar egresos de caja");
      this.btnAllowModuleCustomers = new Button(this.composite_2, 32);
      this.btnAllowModuleCustomers.setText("Puede ingresar al módulo Clientes");
      this.btnAllowCreateOrder = new Button(this.composite_2, 32);
      this.btnAllowCreateOrder.setText("Puede registrar ventas");
      this.btnAllowModuleSuppliers = new Button(this.composite_2, 32);
      this.btnAllowModuleSuppliers.setText("Puede ingresar al módulo Proveedores");
      this.btnAllowCreatePurchase = new Button(this.composite_2, 32);
      this.btnAllowCreatePurchase.setText("Puede registrar compras");
      this.btnAllowModuleLists = new Button(this.composite_2, 32);
      this.btnAllowModuleLists.setText("Puede ingresar al módulo Listados");
      this.btnAllowModifyPrice = new Button(this.composite_2, 32);
      this.btnAllowModifyPrice.setText("Puede modificar precios");
      this.btnAllowModuleReports = new Button(this.composite_2, 32);
      this.btnAllowModuleReports.setText("Puede ingresar al módulo Informes");
      this.btnAllowApplyDiscount = new Button(this.composite_2, 32);
      this.btnAllowApplyDiscount.setText("Puede aplicar descuentos");
      this.btnAllowModuleTools = new Button(this.composite_2, 32);
      this.btnAllowModuleTools.setText("Puede ingresar al módulo Herramientas");
      this.btnAllowApplySurcharge = new Button(this.composite_2, 32);
      this.btnAllowApplySurcharge.setText("Puede aplicar recargos");
      this.btnAllowModuleCash = new Button(this.composite_2, 32);
      this.btnAllowModuleCash.setText("Puede ingresar al módulo Caja");
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            Date creationDate = new Date();
            Employee employee = new Employee();
            employee.setCreationDate(creationDate);
            employee.setLastUpdatedDate(creationDate);
            employee.setUsername(this.txtUsername.getText().trim());
            employee.setPassword(this.txtPassword.getText().trim());
            employee.setAdmin(this.btnAdmin.getSelection());
            employee.setFirstName(this.txtFirstName.getText().trim());
            employee.setLastName(this.txtLastName.getText().trim());
            employee.setJobPosition(this.txtJobPosition.getText().trim());
            employee.setAllowLogin(this.btnAllowLogin.getSelection());
            Double commissionPerSale = this.getDoubleValueFromText(this.txtCommissionPerSale);
            if (commissionPerSale != null) {
               employee.setCommissionPerSale(commissionPerSale);
            }

            employee.setAllowOpenCash(this.btnAllowOpenCash.getSelection());
            employee.setAllowCloseCash(this.btnAllowCloseCash.getSelection());
            employee.setAllowCreateIncome(this.btnAllowCreateIncome.getSelection());
            employee.setAllowCreateOutflow(this.btnAllowCreateOutflow.getSelection());
            employee.setAllowCreateOrder(this.btnAllowCreateOrder.getSelection());
            employee.setAllowCreatePurchase(this.btnAllowCreatePurchase.getSelection());
            employee.setAllowModifyPrice(this.btnAllowModifyPrice.getSelection());
            employee.setAllowApplyDiscount(this.btnAllowApplyDiscount.getSelection());
            employee.setAllowApplySurcharge(this.btnAllowApplySurcharge.getSelection());
            employee.setAllowModuleProducts(this.btnAllowModuleProducts.getSelection());
            employee.setAllowModuleOrders(this.btnAllowModuleOrders.getSelection());
            employee.setAllowModulePurchases(this.btnAllowModulePurchases.getSelection());
            employee.setAllowModuleCustomers(this.btnAllowModuleCustomers.getSelection());
            employee.setAllowModuleSuppliers(this.btnAllowModuleSuppliers.getSelection());
            employee.setAllowModuleLists(this.btnAllowModuleLists.getSelection());
            employee.setAllowModuleReports(this.btnAllowModuleReports.getSelection());
            employee.setAllowModuleTools(this.btnAllowModuleTools.getSelection());
            employee.setAllowModuleCash(this.btnAllowModuleCash.getSelection());
            this.getAccountService().saveEmployee(employee);
         } catch (Exception var4) {
            logger.error("Error al guardar el usuario");
            logger.error(var4.getMessage());
            //logger.error(var4);
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
         if (employee != null) {
            valid = false;
            this.alert("Ya existe ese nombre de usuario");
         }
      }

      if (valid && "".equals(this.txtPassword.getText().trim())) {
         valid = false;
         this.alert("Ingresa la contraseña");
      }

      if (valid && "".equals(this.txtPassword2.getText().trim())) {
         valid = false;
         this.alert("Reingresa la contraseña para confirmarla");
      }

      if (valid && !this.txtPassword.getText().trim().equals(this.txtPassword2.getText().trim())) {
         valid = false;
         this.alert("Las contraseñas no coinciden");
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nuevo usuario");
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

   public Employee getEmployee() {
      return this.employee;
   }

   public void setEmployee(Employee employee) {
      this.employee = employee;
   }
}
