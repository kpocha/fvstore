package com.facilvirtual.fvstoresdesk.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.facilvirtual.fvstoresdesk.model.AfipConfig;
import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.CreditCard;
import com.facilvirtual.fvstoresdesk.model.DebitCard;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ReceiptType;
import com.facilvirtual.fvstoresdesk.model.VatCondition;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import com.facilvirtual.fvstoresdesk.service.AfipService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.util.FVFileUtils;
import com.facilvirtual.fvstoresdesk.util.FVImageUtils;

public class Settings extends AbstractFVDialog {
   protected static Logger logger = LoggerFactory.getLogger("Settings");
   private String action = "";
   private AppConfig appConfig;
   private WorkstationConfig workstationConfig;
   private Table tableCreditCards;
   private Table tableDebitCards;
   private Text txtCompanyName;
   private Text txtCompanyBusinessName;
   private Text txtCompanyCuit;
   private Text txtCompanyAddressStreet;
   private Text txtCompanyAddressNumber;
   private Text txtCompanyAddressOther;
   private Text txtCompanyPhone;
   private Text txtCompanyEmail;
   private Text txtCompanyPostalCode;
   private Text txtCompanyCity;
   private Text txtCompanyProvince;
   private Text txtCompanyGrossIncomeNumber;
   private Text txtCompanyPosNumber;
   private Text txtCompanyWebsite;
   private Text txtScaleCode;
   private Text txtScaleProductCodeStart;
   private Text txtScaleProductCodeEnd;
   private Text txtScaleWeightStart;
   private Text txtScaleWeightEnd;
   private Text txtScaleWeightDecimalsStart;
   private Text txtScaleWeightDecimalsEnd;
   private Text txtScalePriceStart;
   private Text txtScalePriceEnd;
   private Text txtScalePriceDecimalsStart;
   private Text txtScalePriceDecimalsEnd;
   private Text txtScaleChecksumDigit;
   private CLabel lblLogo;
   private boolean deleteLogo = false;
   private String tmpLogoFilename = "";
   private Button btnActiveScaleModule;
   private Combo comboScaleLabelType;
   private Text txtFiscalPrinterModel;
   private Text txtFiscalPrinterCopies;
   private DateTime datepickerCompanyStartActivities;
   private Combo comboCompanyVatCondition;
   private CashRegister cashRegister;
   private Button btnActivateFiscalPrinterModule;
   private Combo comboFiscalPrinter;
   private Combo comboFiscalPrinterPort;
   private Combo comboFiscalPrinterVelocity;
   private Table tableUsers;
   private Combo comboDept1;
   private Combo comboDept2;
   private Combo comboDept3;
   private Combo comboDept4;
   private Combo comboDept5;
   private Combo comboDept6;
   private Combo comboDept7;
   private Combo comboDept8;
   private Button btnEnabledFacturaElectronica;
   private Button btnEnabledFacturaA;
   private Button btnAfipStatus;
   private Button btnOpenCashOnLogin;
   private Button btnOpenConfirmPrintOrder;
   private Button btnOpenConfirmFacturaElectronica;
   private Button btnPrintCodeInTickets;
   private Text txtCashNumber;
   private Combo comboReceiptTypesForOrders;
   private Combo comboReceiptTypesForPurchases;
   private Text txtBudgetStartNumber;
   private Text txtSaleStartNumber;
   private Table tablePriceLists;
   private Text txtAfipPtoVta;
   private Text txtPtoVta;
   private Text txtCodigoFacturaElectronica;
   private Text txtAfipStatus;

   public Settings(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      this.setAppConfig(super.getAppConfig());
      this.setWorkstationConfig(super.getWorkstationConfig());
      Composite container = (Composite)super.createDialogArea(parent);
      TabFolder tabFolder = new TabFolder(container, 0);
      GridData gd_tabFolder = new GridData(4, 16777216, true, false, 1, 1);
      gd_tabFolder.heightHint = 450;
      tabFolder.setLayoutData(gd_tabFolder);
      tabFolder.setBounds(10, 10, 474, 438);
      TabItem tbtmGeneral = new TabItem(tabFolder, 0);
      tbtmGeneral.setText("General");
      Composite composite = new Composite(tabFolder, 0);
      tbtmGeneral.setControl(composite);
      GridLayout gl_composite = new GridLayout(2, false);
      gl_composite.marginRight = 40;
      gl_composite.marginLeft = 20;
      gl_composite.marginTop = 5;
      composite.setLayout(gl_composite);
      Label lblRaznSocial = new Label(composite, 0);
      lblRaznSocial.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblRaznSocial.setText("Razón Social:");
      this.txtCompanyName = new Text(composite, 2048);
      this.txtCompanyName.setTextLimit(60);
      this.txtCompanyName.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblNombreComercial = new Label(composite, 0);
      lblNombreComercial.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNombreComercial.setText("Nombre comercial:");
      this.txtCompanyBusinessName = new Text(composite, 2048);
      this.txtCompanyBusinessName.setTextLimit(60);
      this.txtCompanyBusinessName.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblCuit = new Label(composite, 0);
      lblCuit.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCuit.setText("CUIT:");
      this.txtCompanyCuit = new Text(composite, 2048);
      this.txtCompanyCuit.setTextLimit(11);
      this.txtCompanyCuit.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblCalle = new Label(composite, 0);
      lblCalle.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCalle.setText("Calle:");
      this.txtCompanyAddressStreet = new Text(composite, 2048);
      this.txtCompanyAddressStreet.setTextLimit(60);
      this.txtCompanyAddressStreet.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblNmero = new Label(composite, 0);
      lblNmero.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNmero.setText("Número:");
      this.txtCompanyAddressNumber = new Text(composite, 2048);
      this.txtCompanyAddressNumber.setTextLimit(60);
      this.txtCompanyAddressNumber.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblInformacinAdicionalDe = new Label(composite, 0);
      lblInformacinAdicionalDe.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblInformacinAdicionalDe.setText("Información adicional de domicilio:");
      this.txtCompanyAddressOther = new Text(composite, 2048);
      this.txtCompanyAddressOther.setTextLimit(60);
      this.txtCompanyAddressOther.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblCdigoPostal = new Label(composite, 0);
      lblCdigoPostal.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCdigoPostal.setText("Código Postal:");
      this.txtCompanyPostalCode = new Text(composite, 2048);
      this.txtCompanyPostalCode.setTextLimit(30);
      this.txtCompanyPostalCode.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblCiudad = new Label(composite, 0);
      lblCiudad.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCiudad.setText("Ciudad:");
      this.txtCompanyCity = new Text(composite, 2048);
      this.txtCompanyCity.setTextLimit(60);
      this.txtCompanyCity.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblProvincia = new Label(composite, 0);
      lblProvincia.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblProvincia.setText("Provincia:");
      this.txtCompanyProvince = new Text(composite, 2048);
      this.txtCompanyProvince.setTextLimit(60);
      this.txtCompanyProvince.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblTelfono = new Label(composite, 0);
      lblTelfono.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTelfono.setText("Teléfono:");
      this.txtCompanyPhone = new Text(composite, 2048);
      this.txtCompanyPhone.setTextLimit(60);
      this.txtCompanyPhone.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblEmail = new Label(composite, 0);
      lblEmail.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblEmail.setText("E-mail:");
      this.txtCompanyEmail = new Text(composite, 2048);
      this.txtCompanyEmail.setTextLimit(90);
      this.txtCompanyEmail.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblIngresosBrutos = new Label(composite, 0);
      lblIngresosBrutos.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblIngresosBrutos.setText("Ingresos Brutos:");
      this.txtCompanyGrossIncomeNumber = new Text(composite, 2048);
      this.txtCompanyGrossIncomeNumber.setTextLimit(30);
      this.txtCompanyGrossIncomeNumber.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      Label lblInicioDeActividades = new Label(composite, 0);
      lblInicioDeActividades.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblInicioDeActividades.setText("Inicio de Actividades:");
      this.datepickerCompanyStartActivities = new DateTime(composite, 2048);
      Label lblNmeroDeSucursal = new Label(composite, 0);
      lblNmeroDeSucursal.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNmeroDeSucursal.setText("Condición IVA:");
      this.comboCompanyVatCondition = new Combo(composite, 8);
      this.comboCompanyVatCondition.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      List<VatCondition> vatConditions = this.getOrderService().getAllVatConditionsForCompany();
      int selectedIdx = 0;

      for(Iterator var25 = vatConditions.iterator(); var25.hasNext(); ++selectedIdx) {
         VatCondition vc = (VatCondition)var25.next();
         this.comboCompanyVatCondition.add(vc.getName());
         if ("Responsable Inscripto".equalsIgnoreCase(vc.getName())) {
            this.comboCompanyVatCondition.select(selectedIdx);
         }
      }

      Label lblNroSucursal = new Label(composite, 0);
      lblNroSucursal.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNroSucursal.setText("Nro. Sucursal:");
      this.txtCompanyPosNumber = new Text(composite, 2048);
      this.txtCompanyPosNumber.setTextLimit(30);
      this.txtCompanyPosNumber.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      this.txtCompanyPosNumber.setText("1");
      Label lblPginaWeb = new Label(composite, 0);
      lblPginaWeb.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPginaWeb.setText("Página Web:");
      this.txtCompanyWebsite = new Text(composite, 2048);
      this.txtCompanyWebsite.setTextLimit(90);
      this.txtCompanyWebsite.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      TabItem tbtmLogo = new TabItem(tabFolder, 0);
      tbtmLogo.setText("Logo");
      Composite composite_8 = new Composite(tabFolder, 0);
      tbtmLogo.setControl(composite_8);
      GridLayout gl_composite_8 = new GridLayout(1, false);
      gl_composite_8.verticalSpacing = 10;
      gl_composite_8.marginWidth = 7;
      gl_composite_8.marginTop = 7;
      gl_composite_8.marginHeight = 0;
      gl_composite_8.horizontalSpacing = 10;
      composite_8.setLayout(gl_composite_8);
      this.lblLogo = new CLabel(composite_8, 2048);
      GridData gd_lblLogo = new GridData(16777216, 16777216, false, false, 1, 1);
      gd_lblLogo.widthHint = 320;
      gd_lblLogo.heightHint = 120;
      this.lblLogo.setLayoutData(gd_lblLogo);
      this.lblLogo.setText("");
      this.lblLogo.setBackground(SWTResourceManager.getColor(1));
      this.lblLogo.setMargins(10, 10, 0, 0);
      Composite composite_9 = new Composite(composite_8, 0);
      GridLayout gl_composite_9 = new GridLayout(2, false);
      gl_composite_9.marginHeight = 0;
      gl_composite_9.marginBottom = 8;
      composite_9.setLayout(gl_composite_9);
      Button button_3 = new Button(composite_9, 0);
      button_3.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            updateLogo();
         }
      });
      GridData gd_button_3 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_button_3.widthHint = 90;
      button_3.setLayoutData(gd_button_3);
      button_3.setText("Modificar");
      Button button_4 = new Button(composite_9, 0);
      button_4.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            deleteLogo();
         }
      });
      GridData gd_button_4 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_button_4.widthHint = 90;
      gd_button_4.horizontalIndent = 5;
      button_4.setLayoutData(gd_button_4);
      button_4.setText("Eliminar");
      TabItem tbtmTarjetasDeCrdito = new TabItem(tabFolder, 0);
      tbtmTarjetasDeCrdito.setText("Tarjetas de crédito");
      this.createCreditCardsContents(tabFolder, tbtmTarjetasDeCrdito);
      TabItem tbtmTarjetasDeDbito = new TabItem(tabFolder, 0);
      tbtmTarjetasDeDbito.setText("Tarjetas de débito");
      this.createDebitCardsContents(tabFolder, tbtmTarjetasDeDbito);
      TabItem tbtmListasDePrecios;
      Composite composite_2;
      if (this.getAppConfig().isModuleFiscalAvailable()) {
         tbtmListasDePrecios = new TabItem(tabFolder, 0);
         tbtmListasDePrecios.setText("Impresora Fiscal");
         composite_2 = new Composite(tabFolder, 0);
         tbtmListasDePrecios.setControl(composite_2);
         GridLayout gl_composite_2 = new GridLayout(2, false);
         gl_composite_2.marginTop = 5;
         gl_composite_2.marginRight = 75;
         gl_composite_2.marginLeft = 75;
         composite_2.setLayout(gl_composite_2);
         new Label(composite_2, 0);
         this.btnActivateFiscalPrinterModule = new Button(composite_2, 32);
         this.btnActivateFiscalPrinterModule.setText("Activar módulo Impresora fiscal");
         Label lblMarca = new Label(composite_2, 0);
         lblMarca.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblMarca.setText("Marca:");
         this.comboFiscalPrinter = new Combo(composite_2, 8);
         this.comboFiscalPrinter.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         String[] printerBrands = this.getAppConfigService().getAllFiscalPrinterBrands();
         String[] var46 = printerBrands;
         int var45 = printerBrands.length;

         for(int var44 = 0; var44 < var45; ++var44) {
            String printerBrandName = var46[var44];
            this.comboFiscalPrinter.add(printerBrandName);
         }

         Label lblModelo = new Label(composite_2, 0);
         lblModelo.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblModelo.setText("Modelo:");
         this.txtFiscalPrinterModel = new Text(composite_2, 2048);
         this.txtFiscalPrinterModel.setTextLimit(30);
         this.txtFiscalPrinterModel.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         Label lblPuerto = new Label(composite_2, 0);
         lblPuerto.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblPuerto.setText("Puerto:");
         this.comboFiscalPrinterPort = new Combo(composite_2, 8);
         this.comboFiscalPrinterPort.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         String[] printerPorts = this.getAppConfigService().getAllFiscalPrinterPorts();
         String[] var49 = printerPorts;
         int printerVelocity = printerPorts.length;

         for(int var47 = 0; var47 < printerVelocity; ++var47) {
            String printerPort = var49[var47];
            this.comboFiscalPrinterPort.add(printerPort);
         }

         Label lblVelocidadDeComunicacin = new Label(composite_2, 0);
         lblVelocidadDeComunicacin.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblVelocidadDeComunicacin.setText("Velocidad:");
         this.comboFiscalPrinterVelocity = new Combo(composite_2, 8);
         this.comboFiscalPrinterVelocity.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         int[] printerVelocities = this.getAppConfigService().getAllFiscalPrinterVelocities();
         int[] var51 = printerVelocities;
         int var50 = printerVelocities.length;

         for(int var120 = 0; var120 < var50; ++var120) {
            printerVelocity = var51[var120];
            this.comboFiscalPrinterVelocity.add(String.valueOf(printerVelocity));
         }

         Label lblCopias = new Label(composite_2, 0);
         lblCopias.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblCopias.setText("Copias:");
         this.txtFiscalPrinterCopies = new Text(composite_2, 2048);
         this.txtFiscalPrinterCopies.setTextLimit(5);
         this.txtFiscalPrinterCopies.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         this.initFiscalPrinterModule();
      }

      tbtmListasDePrecios = new TabItem(tabFolder, 0);
      tbtmListasDePrecios.setText("Listas de precios");
      composite_2 = new Composite(tabFolder, 0);
      tbtmListasDePrecios.setControl(composite_2);
      composite_2.setLayout(new GridLayout(2, false));
      this.tablePriceLists = new Table(composite_2, 67584);
      this.tablePriceLists.setLinesVisible(true);
      this.tablePriceLists.setHeaderVisible(true);
      this.tablePriceLists.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      TableColumn tableColumn_2 = new TableColumn(this.tablePriceLists, 0);
      tableColumn_2.setWidth(35);
      tableColumn_2.setText("#");
      TableColumn tableColumn = new TableColumn(this.tablePriceLists, 0);
      tableColumn.setWidth(180);
      tableColumn.setText("Nombre");
      TableColumn tableColumn_1 = new TableColumn(this.tablePriceLists, 0);
      tableColumn_1.setWidth(145);
      tableColumn_1.setText("Estado");
      Composite composite_6 = new Composite(composite_2, 0);
      composite_6.setLayout(new GridLayout(1, false));
      Button button = new Button(composite_6, 0);
      button.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            editPriceList();
         }
      });
      button.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      button.setText("Modificar");
      Button button_1 = new Button(composite_6, 0);
      button_1.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            activatePriceList();
         }
      });
      button_1.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      button_1.setText("Activar");
      Button button_2 = new Button(composite_6, 0);
      button_2.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            deactivatePriceList();
         }
      });
      button_2.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      button_2.setText("Desactivar");
      TabItem tbtmUsuarios = new TabItem(tabFolder, 0);
      tbtmUsuarios.setText("Usuarios");
      Composite composite_1 = new Composite(tabFolder, 0);
      tbtmUsuarios.setControl(composite_1);
      composite_1.setLayout(new GridLayout(1, false));
      this.tableUsers = new Table(composite_1, 67584);
      this.tableUsers.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.tableUsers.setHeaderVisible(true);
      this.tableUsers.setLinesVisible(true);
      TableColumn tblclmnNombreDeUsuario = new TableColumn(this.tableUsers, 0);
      tblclmnNombreDeUsuario.setWidth(112);
      tblclmnNombreDeUsuario.setText("Usuario");
      TableColumn tblclmnNombre_1 = new TableColumn(this.tableUsers, 0);
      tblclmnNombre_1.setWidth(110);
      tblclmnNombre_1.setText("Nombre");
      TableColumn tblclmnApellido = new TableColumn(this.tableUsers, 0);
      tblclmnApellido.setWidth(110);
      tblclmnApellido.setText("Apellido");
      TableColumn tblclmnEstado_1 = new TableColumn(this.tableUsers, 0);
      tblclmnEstado_1.setWidth(110);
      tblclmnEstado_1.setText("Estado");
      //Composite composite_2 = new Composite(composite_1, 0);
      composite_2.setLayout(new GridLayout(4, false));
      Button btnNuevoUsuario = new Button(composite_2, 0);
      btnNuevoUsuario.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            addNewEmployee();
         }
      });
      GridData gd_btnNuevoUsuario = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnNuevoUsuario.widthHint = 90;
      btnNuevoUsuario.setLayoutData(gd_btnNuevoUsuario);
      btnNuevoUsuario.setBounds(0, 0, 68, 23);
      btnNuevoUsuario.setText("Nuevo usuario");
      Button btnNewButton = new Button(composite_2, 0);
      btnNewButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            editEmployee();
         }
      });
      GridData gd_btnNewButton = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnNewButton.widthHint = 90;
      btnNewButton.setLayoutData(gd_btnNewButton);
      btnNewButton.setText("Modificar");
      Button btnNewButton_1 = new Button(composite_2, 0);
      btnNewButton_1.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            deleteEmployee();
         }
      });
      GridData gd_btnNewButton_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnNewButton_1.widthHint = 90;
      btnNewButton_1.setLayoutData(gd_btnNewButton_1);
      btnNewButton_1.setText("Eliminar");
      Button btnCambiarContrasea = new Button(composite_2, 0);
      btnCambiarContrasea.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            changePassword();
         }
      });
      btnCambiarContrasea.setText("Cambiar contraseña");
      TabItem tbtmCaja = new TabItem(tabFolder, 0);
      tbtmCaja.setText("Caja");
      Composite composite_3 = new Composite(tabFolder, 0);
      tbtmCaja.setControl(composite_3);
      GridLayout gl_composite_3 = new GridLayout(4, true);
      gl_composite_3.marginTop = 5;
      gl_composite_3.marginRight = 5;
      gl_composite_3.marginLeft = 1;
      composite_3.setLayout(gl_composite_3);
      Label lblNroCaja = new Label(composite_3, 0);
      lblNroCaja.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNroCaja.setText("Nro. Caja");
      this.txtCashNumber = new Text(composite_3, 2048);
      GridData gd_txtCashNumber = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtCashNumber.widthHint = 100;
      this.txtCashNumber.setLayoutData(gd_txtCashNumber);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      Label lblBotones = new Label(composite_3, 0);
      lblBotones.setText("Botones");
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      Label lblDep = new Label(composite_3, 0);
      lblDep.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDep.setText("Departamento 1:");
      this.comboDept1 = new Combo(composite_3, 8);
      this.comboDept1.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblDep_4 = new Label(composite_3, 0);
      lblDep_4.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDep_4.setText("Departamento 5:");
      this.comboDept5 = new Combo(composite_3, 8);
      this.comboDept5.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblDep_1 = new Label(composite_3, 0);
      lblDep_1.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDep_1.setText("Departamento 2:");
      this.comboDept2 = new Combo(composite_3, 8);
      this.comboDept2.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblDep_5 = new Label(composite_3, 0);
      lblDep_5.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDep_5.setText("Departamento 6:");
      this.comboDept6 = new Combo(composite_3, 8);
      this.comboDept6.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblDep_2 = new Label(composite_3, 0);
      lblDep_2.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDep_2.setText("Departamento 3:");
      this.comboDept3 = new Combo(composite_3, 8);
      this.comboDept3.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblDepartamento = new Label(composite_3, 0);
      lblDepartamento.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDepartamento.setText("Departamento 7:");
      this.comboDept7 = new Combo(composite_3, 8);
      this.comboDept7.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblDep_3 = new Label(composite_3, 0);
      lblDep_3.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDep_3.setText("Departamento 4:");
      this.comboDept4 = new Combo(composite_3, 8);
      this.comboDept4.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblDepartamento_1 = new Label(composite_3, 0);
      lblDepartamento_1.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDepartamento_1.setText("Departamento 8:");
      this.comboDept8 = new Combo(composite_3, 8);
      this.comboDept8.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      Label lblCreaRubros = new Label(composite_3, 0);
      lblCreaRubros.setLayoutData(new GridData(16384, 16777216, false, false, 4, 1));
      lblCreaRubros.setText("Usa el Administrador de rubros para crearlos y asignarlos como Departamentos");
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      new Label(composite_3, 0);
      TabItem tbtmFacturaElectrnica;
      Composite composite_7;
      Label lblInformacinDeAfip;
      Label lblPuntoDeVenta;
      Label lblEstado;
      Label lblComprobantePredeterminadoPara;
      Label lblComprobantePredeterminadoPara_1;
      if (this.getAppConfig().isModuleScaleAvailable()) {
         tbtmFacturaElectrnica = new TabItem(tabFolder, 0);
         tbtmFacturaElectrnica.setText("Balanza");
         composite_7 = new Composite(tabFolder, 0);
         tbtmFacturaElectrnica.setControl(composite_7);
         GridLayout gl_composite_1 = new GridLayout(2, false);
         gl_composite_1.marginTop = 5;
         gl_composite_1.marginRight = 75;
         gl_composite_1.marginLeft = 75;
         composite_7.setLayout(gl_composite_1);
         new Label(composite_7, 0);
         this.btnActiveScaleModule = new Button(composite_7, 32);
         this.btnActiveScaleModule.setText("Activar módulo Balanza");
         Label lblTipoDeEtiqueta = new Label(composite_7, 0);
         lblTipoDeEtiqueta.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblTipoDeEtiqueta.setText("Tipo de etiqueta:");
         this.comboScaleLabelType = new Combo(composite_7, 8);
         this.comboScaleLabelType.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         this.comboScaleLabelType.add("PESO");
         this.comboScaleLabelType.add("PRECIO");
         lblInformacinDeAfip = new Label(composite_7, 0);
         lblInformacinDeAfip.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblInformacinDeAfip.setText("Código balanza:");
         this.txtScaleCode = new Text(composite_7, 2048);
         this.txtScaleCode.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         lblPuntoDeVenta = new Label(composite_7, 0);
         lblPuntoDeVenta.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblPuntoDeVenta.setText("Inicio código artículo:");
         this.txtScaleProductCodeStart = new Text(composite_7, 2048);
         this.txtScaleProductCodeStart.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         Label lblFinCdigoArticulo = new Label(composite_7, 0);
         lblFinCdigoArticulo.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblFinCdigoArticulo.setText("Fin código artículo:");
         this.txtScaleProductCodeEnd = new Text(composite_7, 2048);
         this.txtScaleProductCodeEnd.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         lblEstado = new Label(composite_7, 0);
         lblEstado.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblEstado.setText("Inicio dígito peso:");
         this.txtScaleWeightStart = new Text(composite_7, 2048);
         this.txtScaleWeightStart.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         Label lblFinDgitoPeso = new Label(composite_7, 0);
         lblFinDgitoPeso.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblFinDgitoPeso.setText("Fin dígito peso:");
         this.txtScaleWeightEnd = new Text(composite_7, 2048);
         this.txtScaleWeightEnd.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         Label lblInicioDecimalesPeso = new Label(composite_7, 0);
         lblInicioDecimalesPeso.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblInicioDecimalesPeso.setText("Inicio decimales peso:");
         this.txtScaleWeightDecimalsStart = new Text(composite_7, 2048);
         this.txtScaleWeightDecimalsStart.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         Label lblFinDecimalesPeso = new Label(composite_7, 0);
         lblFinDecimalesPeso.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblFinDecimalesPeso.setText("Fin decimales peso:");
         this.txtScaleWeightDecimalsEnd = new Text(composite_7, 2048);
         this.txtScaleWeightDecimalsEnd.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         Label lblInicioDgitoPrecio = new Label(composite_7, 0);
         lblInicioDgitoPrecio.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblInicioDgitoPrecio.setText("Inicio dígito precio:");
         this.txtScalePriceStart = new Text(composite_7, 2048);
         this.txtScalePriceStart.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         Label lblFinDgitoPrecio = new Label(composite_7, 0);
         lblFinDgitoPrecio.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblFinDgitoPrecio.setText("Fin dígito precio:");
         this.txtScalePriceEnd = new Text(composite_7, 2048);
         this.txtScalePriceEnd.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         lblComprobantePredeterminadoPara = new Label(composite_7, 0);
         lblComprobantePredeterminadoPara.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblComprobantePredeterminadoPara.setText("Inicio decimales precio:");
         this.txtScalePriceDecimalsStart = new Text(composite_7, 2048);
         this.txtScalePriceDecimalsStart.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         Label lblFinDecimalesPrecio = new Label(composite_7, 0);
         lblFinDecimalesPrecio.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblFinDecimalesPrecio.setText("Fin decimales precio:");
         this.txtScalePriceDecimalsEnd = new Text(composite_7, 2048);
         this.txtScalePriceDecimalsEnd.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         lblComprobantePredeterminadoPara_1 = new Label(composite_7, 0);
         lblComprobantePredeterminadoPara_1.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
         lblComprobantePredeterminadoPara_1.setText("Dígito de control:");
         this.txtScaleChecksumDigit = new Text(composite_7, 2048);
         this.txtScaleChecksumDigit.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
         this.initScaleModule();
      }

      tbtmFacturaElectrnica = new TabItem(tabFolder, 0);
      tbtmFacturaElectrnica.setText("Factura Electrónica");
      composite_7 = new Composite(tabFolder, 0);
      tbtmFacturaElectrnica.setControl(composite_7);
      composite_7.setLayout(new GridLayout(2, false));
      new Label(composite_7, 0);
      new Label(composite_7, 0);
      new Label(composite_7, 0);
      this.btnEnabledFacturaElectronica = new Button(composite_7, 32);
      this.btnEnabledFacturaElectronica.setText("Habilitar Factura Electrónica");
      Label lblCdigoFacturaElectrnica = new Label(composite_7, 0);
      lblCdigoFacturaElectrnica.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCdigoFacturaElectrnica.setText("Código de Factura Electrónica");
      this.txtCodigoFacturaElectronica = new Text(composite_7, 2048);
      this.txtCodigoFacturaElectronica.setTextLimit(6);
      GridData gd_txtCodigoFacturaElectronica = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtCodigoFacturaElectronica.widthHint = 50;
      this.txtCodigoFacturaElectronica.setLayoutData(gd_txtCodigoFacturaElectronica);
      new Label(composite_7, 0);
      new Label(composite_7, 0);
      new Label(composite_7, 0);
      lblInformacinDeAfip = new Label(composite_7, 0);
      lblInformacinDeAfip.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1));
      lblInformacinDeAfip.setText("Información de AFIP");
      new Label(composite_7, 0);
      new Label(composite_7, 0);
      lblPuntoDeVenta = new Label(composite_7, 0);
      lblPuntoDeVenta.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPuntoDeVenta.setText("Nro. Punto de Venta");
      this.txtAfipPtoVta = new Text(composite_7, 2048);
      this.txtAfipPtoVta.setTextLimit(10);
      GridData gd_txtAfipPtoVta = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtAfipPtoVta.widthHint = 30;
      this.txtAfipPtoVta.setLayoutData(gd_txtAfipPtoVta);
      new Label(composite_7, 0);
      this.btnEnabledFacturaA = new Button(composite_7, 32);
      this.btnEnabledFacturaA.setText("Habilitado para realizar Factura A");
      new Label(composite_7, 0);
      new Label(composite_7, 0);
      lblEstado = new Label(composite_7, 0);
      lblEstado.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblEstado.setText("Estado");
      Composite composite_10 = new Composite(composite_7, 0);
      composite_10.setLayout(new GridLayout(2, false));
      this.txtAfipStatus = new Text(composite_10, 2056);
      this.txtAfipStatus.setEnabled(false);
      GridData gd_txtAfipStatus = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtAfipStatus.widthHint = 146;
      this.txtAfipStatus.setLayoutData(gd_txtAfipStatus);
      this.txtAfipStatus.setBounds(0, 0, 76, 21);
      this.btnAfipStatus = new Button(composite_10, 0);
      this.btnAfipStatus.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            updateAfipStatus();
         }
      });
      this.btnAfipStatus.setBounds(0, 0, 75, 25);
      this.btnAfipStatus.setText("Actualizar");
      TabItem tbtmPreferencias = new TabItem(tabFolder, 0);
      tbtmPreferencias.setText("Preferencias");
      Composite composite_4 = new Composite(tabFolder, 0);
      tbtmPreferencias.setControl(composite_4);
      GridLayout gl_composite_4 = new GridLayout(2, false);
      gl_composite_4.marginTop = 5;
      gl_composite_4.marginLeft = 5;
      composite_4.setLayout(gl_composite_4);
      this.btnOpenCashOnLogin = new Button(composite_4, 32);
      this.btnOpenCashOnLogin.setText("Abrir la caja al iniciar sesión");
      new Label(composite_4, 0);
      this.btnOpenConfirmPrintOrder = new Button(composite_4, 32);
      this.btnOpenConfirmPrintOrder.setText("Consultar para imprimir comprobante al cerrar venta");
      new Label(composite_4, 0);
      this.btnOpenConfirmFacturaElectronica = new Button(composite_4, 32);
      this.btnOpenConfirmFacturaElectronica.setText("Consultar para hacer factura electrónica al cerrar venta");
      new Label(composite_4, 0);
      this.btnPrintCodeInTickets = new Button(composite_4, 32);
      this.btnPrintCodeInTickets.setText("Imprimir código en tickets");
      new Label(composite_4, 0);
      lblComprobantePredeterminadoPara = new Label(composite_4, 0);
      lblComprobantePredeterminadoPara.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblComprobantePredeterminadoPara.setText("Comprobante predeterminado para ventas");
      this.comboReceiptTypesForOrders = new Combo(composite_4, 8);
      this.comboReceiptTypesForOrders.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      List<ReceiptType> receiptTypes = this.getAppConfigService().getActiveReceiptTypesForOrder();
      selectedIdx = 0;

      for(Iterator var93 = receiptTypes.iterator(); var93.hasNext(); ++selectedIdx) {
         ReceiptType rt = (ReceiptType)var93.next();
         this.comboReceiptTypesForOrders.add(rt.getName());
         if (this.getWorkstationConfig().getDefaultReceiptTypeForOrders().getName().equalsIgnoreCase(rt.getName())) {
            this.comboReceiptTypesForOrders.select(selectedIdx);
         }
      }

      lblComprobantePredeterminadoPara_1 = new Label(composite_4, 0);
      lblComprobantePredeterminadoPara_1.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblComprobantePredeterminadoPara_1.setText("Comprobante predeterminado para compras");
      this.comboReceiptTypesForPurchases = new Combo(composite_4, 8);
      this.comboReceiptTypesForPurchases.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Label lblNroPuntoDe = new Label(composite_4, 0);
      lblNroPuntoDe.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNroPuntoDe.setText("Nro. punto de venta");
      this.txtPtoVta = new Text(composite_4, 2048);
      this.txtPtoVta.setText("1");
      GridData gd_txtPtoVta = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtPtoVta.widthHint = 50;
      this.txtPtoVta.setLayoutData(gd_txtPtoVta);
      Label lblNroPresupuestoInicial = new Label(composite_4, 0);
      lblNroPresupuestoInicial.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNroPresupuestoInicial.setText("Nro. presupuesto inicial");
      this.txtBudgetStartNumber = new Text(composite_4, 2048);
      GridData gd_txtBudgetStartNumber = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtBudgetStartNumber.widthHint = 50;
      this.txtBudgetStartNumber.setLayoutData(gd_txtBudgetStartNumber);
      Label lblNroComprobanteInicial = new Label(composite_4, 0);
      lblNroComprobanteInicial.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblNroComprobanteInicial.setText("Nro. comprobante de venta inicial");
      this.txtSaleStartNumber = new Text(composite_4, 2048);
      GridData gd_txtSaleStartNumber = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtSaleStartNumber.widthHint = 50;
      this.txtSaleStartNumber.setLayoutData(gd_txtSaleStartNumber);
      List<ReceiptType> receiptTypes2 = this.getAppConfigService().getActiveReceiptTypesForPurchase();
      selectedIdx = 0;

      for(Iterator var101 = receiptTypes2.iterator(); var101.hasNext(); ++selectedIdx) {
         ReceiptType rt = (ReceiptType)var101.next();
         this.comboReceiptTypesForPurchases.add(rt.getName());
         if (this.getWorkstationConfig().getDefaultReceiptTypeForPurchases().getName().equalsIgnoreCase(rt.getName())) {
            this.comboReceiptTypesForPurchases.select(selectedIdx);
         }
      }

      this.initGeneral();
      this.initLogo();
      this.initCreditCards();
      this.initDebitCards();
      this.initPriceLists();
      this.initUsers();
      this.initCash();
      this.initFacturaElectronica();
      this.initPreferences();
      return container;
   }

   private void updateAfipStatus() {
      this.appConfig.setCompanyCuit(this.txtCompanyCuit.getText().trim());
      String vatConditionName = this.comboCompanyVatCondition.getText();
      VatCondition companyVatCondition = this.getOrderService().getVatConditionByName(vatConditionName);
      this.appConfig.setCompanyVatCondition(companyVatCondition);
      if (this.getIntegerValueFromText(this.txtCodigoFacturaElectronica) != null) {
         this.workstationConfig.setCodFacturaElectronica(this.getIntegerValueFromText(this.txtCodigoFacturaElectronica));
      } else {
         this.workstationConfig.setCodFacturaElectronica(0);
      }

      this.workstationConfig.initValidCodFactElect(this.getAppConfigService().getCurrentInstallationCode());
      if (this.getIntegerValueFromText(this.txtAfipPtoVta) != null) {
         this.appConfig.setAfipPtoVta(this.getIntegerValueFromText(this.txtAfipPtoVta));
      } else {
         this.appConfig.setAfipPtoVta(0);
      }

      this.getAppConfigService().saveWorkstationConfig(this.workstationConfig);
      this.getAppConfigService().saveAppConfig(this.appConfig);
      this.btnAfipStatus.setEnabled(false);
      this.txtAfipStatus.setText("Cargando...");
      this.txtAfipStatus.update();
      logger.info("----------------------------------------");
      logger.info("Actualizando Estado de Factura Electrónica");
      logger.info("CUIT: " + this.appConfig.getCompanyCuit());
      logger.info("Cond. IVA: " + this.appConfig.getCompanyVatCondition().getNameToPrint());
      logger.info("Código Factura Electrónica: " + this.workstationConfig.getCodFacturaElectronicaToDisplay());
      logger.info("Pto. Vta.: " + this.appConfig.getAfipPtoVta());

      try {
         AfipConfig afipConfig = this.getAfipService().getAfipConfig();
         String p12file = afipConfig.getLoginKeystore();
         String signer = afipConfig.getLoginKeystoreSigner();
         String p12pass = afipConfig.getLoginKeystorePassword();
         if ("1".equals(afipConfig.getDevMode())) {
            logger.info("Modo: Desarrollo");
         } else {
            logger.info("Modo: Producción");
         }

         logger.info("Certificado: " + p12file);
         logger.info("Signer: " + signer);
         logger.info("Pass: " + p12pass);
      } catch (Exception var9) {
         logger.error(var9.getMessage());
         logger.error(var9.toString());
      }

      if (!this.getWorkstationConfig().isValidCodFactElect()) {
         this.alert("El Código de Factura Electrónica no es válido");
         this.txtAfipStatus.setText("No Disponible");
         this.btnAfipStatus.setEnabled(true);
         logger.info("Error: Código no válido");
      } else if (!this.getAppConfig().isMonotributo() && !this.getAppConfig().isResponsableInscripto()) {
         this.alert("Verifique la condición de IVA en el menú Archivo->Configuración->General");
         this.txtAfipStatus.setText("No Disponible");
         this.btnAfipStatus.setEnabled(true);
         logger.info("Error: Condición de IVA no válida");
      } else {
         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var8) {
         }

         try {
            String afipStatus = this.getAfipService().getAfipStatus(this.getAppConfig());
            this.txtAfipStatus.setText(afipStatus);
         } catch (Exception var7) {
            this.txtAfipStatus.setText("No Disponible");
         }

         this.btnAfipStatus.setEnabled(true);
      }

      logger.info("Estado: " + this.txtAfipStatus.getText());
      logger.info("----------------------------------------");
   }

   public AfipService getAfipService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AfipService)context.getBean("afipService");
   }

   private void initCash() {
      try {
         this.txtCashNumber.setText(String.valueOf(this.getWorkstationConfig().getCashNumber()));
         this.comboDept1.add("");
         this.comboDept1.select(0);
         List<ProductCategory> categories = this.getProductService().getActiveProductCategories();
         int selectedIdx = 0;
         Iterator var4 = categories.iterator();

         ProductCategory pc;
         while(var4.hasNext()) {
            pc = (ProductCategory)var4.next();
            if (!"Sin clasificar".equalsIgnoreCase(pc.getName())) {
               this.comboDept1.add(pc.getName());
               ++selectedIdx;
            }

            if (this.getWorkstationConfig().getCashDept1() != null && this.getWorkstationConfig().getCashDept1().getName().equals(pc.getName())) {
               this.comboDept1.select(selectedIdx);
            }
         }

         this.comboDept2.add("");
         this.comboDept2.select(0);
         selectedIdx = 0;
         var4 = categories.iterator();

         while(var4.hasNext()) {
            pc = (ProductCategory)var4.next();
            if (!"Sin clasificar".equalsIgnoreCase(pc.getName())) {
               this.comboDept2.add(pc.getName());
               ++selectedIdx;
            }

            if (this.getWorkstationConfig().getCashDept2() != null && this.getWorkstationConfig().getCashDept2().getName().equals(pc.getName())) {
               this.comboDept2.select(selectedIdx);
            }
         }

         this.comboDept3.add("");
         this.comboDept3.select(0);
         selectedIdx = 0;
         var4 = categories.iterator();

         while(var4.hasNext()) {
            pc = (ProductCategory)var4.next();
            if (!"Sin clasificar".equalsIgnoreCase(pc.getName())) {
               this.comboDept3.add(pc.getName());
               ++selectedIdx;
            }

            if (this.getWorkstationConfig().getCashDept3() != null && this.getWorkstationConfig().getCashDept3().getName().equals(pc.getName())) {
               this.comboDept3.select(selectedIdx);
            }
         }

         this.comboDept4.add("");
         this.comboDept4.select(0);
         selectedIdx = 0;
         var4 = categories.iterator();

         while(var4.hasNext()) {
            pc = (ProductCategory)var4.next();
            if (!"Sin clasificar".equalsIgnoreCase(pc.getName())) {
               this.comboDept4.add(pc.getName());
               ++selectedIdx;
            }

            if (this.getWorkstationConfig().getCashDept4() != null && this.getWorkstationConfig().getCashDept4().getName().equals(pc.getName())) {
               this.comboDept4.select(selectedIdx);
            }
         }

         this.comboDept5.add("");
         this.comboDept5.select(0);
         selectedIdx = 0;
         var4 = categories.iterator();

         while(var4.hasNext()) {
            pc = (ProductCategory)var4.next();
            if (!"Sin clasificar".equalsIgnoreCase(pc.getName())) {
               this.comboDept5.add(pc.getName());
               ++selectedIdx;
            }

            if (this.getWorkstationConfig().getCashDept5() != null && this.getWorkstationConfig().getCashDept5().getName().equals(pc.getName())) {
               this.comboDept5.select(selectedIdx);
            }
         }

         this.comboDept6.add("");
         this.comboDept6.select(0);
         selectedIdx = 0;
         var4 = categories.iterator();

         while(var4.hasNext()) {
            pc = (ProductCategory)var4.next();
            if (!"Sin clasificar".equalsIgnoreCase(pc.getName())) {
               this.comboDept6.add(pc.getName());
               ++selectedIdx;
            }

            if (this.getWorkstationConfig().getCashDept6() != null && this.getWorkstationConfig().getCashDept6().getName().equals(pc.getName())) {
               this.comboDept6.select(selectedIdx);
            }
         }

         this.comboDept7.add("");
         this.comboDept7.select(0);
         selectedIdx = 0;
         var4 = categories.iterator();

         while(var4.hasNext()) {
            pc = (ProductCategory)var4.next();
            if (!"Sin clasificar".equalsIgnoreCase(pc.getName())) {
               this.comboDept7.add(pc.getName());
               ++selectedIdx;
            }

            if (this.getWorkstationConfig().getCashDept7() != null && this.getWorkstationConfig().getCashDept7().getName().equals(pc.getName())) {
               this.comboDept7.select(selectedIdx);
            }
         }

         this.comboDept8.add("");
         this.comboDept8.select(0);
         selectedIdx = 0;
         var4 = categories.iterator();

         while(var4.hasNext()) {
            pc = (ProductCategory)var4.next();
            if (!"Sin clasificar".equalsIgnoreCase(pc.getName())) {
               this.comboDept8.add(pc.getName());
               ++selectedIdx;
            }

            if (this.getWorkstationConfig().getCashDept8() != null && this.getWorkstationConfig().getCashDept8().getName().equals(pc.getName())) {
               this.comboDept8.select(selectedIdx);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al inicializar config. de caja");
      }

   }

   private void initFacturaElectronica() {
      this.btnEnabledFacturaElectronica.setSelection(this.getAppConfig().isAfipEnabledFacturaElectronica());
      this.txtCodigoFacturaElectronica.setText(this.getWorkstationConfig().getCodFacturaElectronicaToDisplay());
      this.txtAfipPtoVta.setText(String.valueOf(this.getAppConfig().getAfipPtoVta()));
      this.btnEnabledFacturaA.setSelection(this.getAppConfig().isAfipEnabledFacturaA());
   }

   private void initPreferences() {
      this.btnOpenCashOnLogin.setSelection(this.getWorkstationConfig().isOpenCashOnLogin());
      this.btnOpenConfirmPrintOrder.setSelection(this.getWorkstationConfig().isOpenConfirmPrintOrder());
      this.btnOpenConfirmFacturaElectronica.setSelection(this.getWorkstationConfig().isOpenConfirmFacturaElectronica());
      this.btnPrintCodeInTickets.setSelection(this.getWorkstationConfig().isPrintCodeInTickets());
      this.txtBudgetStartNumber.setText(String.valueOf(this.getAppConfig().getBudgetStartNumber()));
      this.txtSaleStartNumber.setText(String.valueOf(this.getAppConfig().getSaleStartNumber()));
      this.txtPtoVta.setText(String.valueOf(this.getAppConfig().getPtoVta()));
   }

   private void addNewEmployee() {
      AddNewEmployee dialog = new AddNewEmployee(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         logger.info("Se guardó un usuario nuevo");
         this.initUsers();
      }

   }

   private void deleteEmployee() {
      int idx = this.tableUsers.getSelectionIndex();
      if (idx >= 0) {
         String username = this.tableUsers.getItem(idx).getText(0).trim();
         if (username.equalsIgnoreCase("Administrador")) {
            this.alert("No se puede eliminar el usuario Administrador");
         } else if (FVConfirmDialog.openQuestion(this.getShell(), "Eliminar usuario", "¿Quieres eliminar el usuario?")) {
            try {
               this.getAccountService().deleteEmployee(username);
               this.initUsers();
            } catch (Exception var4) {
            }
         }
      } else {
         this.alert("Selecciona un usuario");
      }

   }

   private void editEmployee() {
      try {
         int idx = this.tableUsers.getSelectionIndex();
         if (idx >= 0) {
            String username = this.tableUsers.getItem(idx).getText(0).trim();
            if (username.equalsIgnoreCase("Administrador")) {
               this.alert("No se puede modificar el usuario Administrador");
            } else {
               Employee employee = this.getAccountService().getEmployeeByUsername(username);
               EditEmployee dialog = new EditEmployee(this.getShell());
               dialog.setEmployee(employee);
               dialog.open();
               if ("OK".equalsIgnoreCase(dialog.getAction())) {
                  logger.info("Se editó el usuario con id: " + employee.getId());
                  this.initUsers();
               }
            }
         } else {
            this.alert("Selecciona un usuario");
         }
      } catch (Exception var5) {
      }

   }

   private void changePassword() {
      try {
         int idx = this.tableUsers.getSelectionIndex();
         if (idx >= 0) {
            String username = this.tableUsers.getItem(idx).getText(0).trim();
            Employee employee = this.getAccountService().getEmployeeByUsername(username);
            ChangePassword dialog = new ChangePassword(this.getShell());
            dialog.setEmployee(employee);
            dialog.setManagerMode(this.getCashRegister().isManagerMode());
            dialog.open();
         } else {
            this.alert("Selecciona un usuario");
         }
      } catch (Exception var5) {
      }

   }

   private void initUsers() {
      this.tableUsers.removeAll();
      List<Employee> employees = this.getAccountService().getAllEmployees();
      Iterator var3 = employees.iterator();

      while(var3.hasNext()) {
         Employee employee = (Employee)var3.next();
         TableItem tableItem = new TableItem(this.tableUsers, 0);
         tableItem.setText(0, String.valueOf(employee.getUsername()));
         tableItem.setText(1, String.valueOf(employee.getFirstName()));
         tableItem.setText(2, String.valueOf(employee.getLastName()));
         if (employee.isActive()) {
            tableItem.setText(3, "Activo");
         } else {
            tableItem.setText(3, "Eliminado");
         }
      }

   }

   private void initGeneral() {
      try {
         AppConfig appConfig = this.getAppConfigService().getAppConfig();
         WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
         this.txtCompanyName.setText(appConfig.getCompanyName());
         this.txtCompanyBusinessName.setText(appConfig.getCompanyBusinessName());
         this.txtCompanyCuit.setText(appConfig.getCompanyCuit());
         this.txtCompanyAddressStreet.setText(appConfig.getCompanyAddressStreet());
         this.txtCompanyAddressNumber.setText(appConfig.getCompanyAddressNumber());
         this.txtCompanyAddressOther.setText(appConfig.getCompanyAddressOther());
         this.txtCompanyPostalCode.setText(appConfig.getCompanyPostalCode());
         this.txtCompanyCity.setText(appConfig.getCompanyCity());
         this.txtCompanyProvince.setText(appConfig.getCompanyProvince());
         this.txtCompanyPhone.setText(appConfig.getCompanyPhone());
         this.txtCompanyEmail.setText(appConfig.getCompanyEmail());
         this.txtCompanyGrossIncomeNumber.setText(appConfig.getCompanyGrossIncomeNumber());
         if (appConfig.getCompanyStartActivitiesDate() == null) {
            if (workstationConfig.getInstallationDate() != null) {
               this.initDatepickerForDate(this.datepickerCompanyStartActivities, workstationConfig.getInstallationDate());
            }
         } else {
            this.initDatepickerForDate(this.datepickerCompanyStartActivities, appConfig.getCompanyStartActivitiesDate());
         }

         List<VatCondition> vatConditions = this.getOrderService().getAllVatConditionsForCompany();
         int vatConditionIdx = 0;

         for(Iterator var6 = vatConditions.iterator(); var6.hasNext(); ++vatConditionIdx) {
            VatCondition vc = (VatCondition)var6.next();
            if (appConfig.getCompanyVatCondition().getId().equals(vc.getId())) {
               this.comboCompanyVatCondition.select(vatConditionIdx);
            }
         }

         this.txtCompanyPosNumber.setText(String.valueOf(appConfig.getCompanyPosNumber()));
         this.txtCompanyWebsite.setText(appConfig.getCompanyWebsite());
      } catch (Exception var7) {
      }

   }

   private void initScaleModule() {
      try {
         this.btnActiveScaleModule.setSelection(this.getWorkstationConfig().isModuleScaleActive());
         if (this.getWorkstationConfig().isScaleLabelWeightType()) {
            this.comboScaleLabelType.select(0);
         } else {
            this.comboScaleLabelType.select(1);
         }

         this.txtScaleCode.setText(this.getWorkstationConfig().getScaleCode());
         this.txtScaleProductCodeStart.setText(String.valueOf(this.getWorkstationConfig().getScaleProductCodeStart()));
         this.txtScaleProductCodeEnd.setText(String.valueOf(this.getWorkstationConfig().getScaleProductCodeEnd()));
         this.txtScaleWeightStart.setText(String.valueOf(this.getWorkstationConfig().getScaleWeightStart()));
         this.txtScaleWeightEnd.setText(String.valueOf(this.getWorkstationConfig().getScaleWeightEnd()));
         this.txtScaleWeightDecimalsStart.setText(String.valueOf(this.getWorkstationConfig().getScaleWeightDecimalsStart()));
         this.txtScaleWeightDecimalsEnd.setText(String.valueOf(this.getWorkstationConfig().getScaleWeightDecimalsEnd()));
         this.txtScalePriceStart.setText(String.valueOf(this.getWorkstationConfig().getScalePriceStart()));
         this.txtScalePriceEnd.setText(String.valueOf(this.getWorkstationConfig().getScalePriceEnd()));
         this.txtScalePriceDecimalsStart.setText(String.valueOf(this.getWorkstationConfig().getScalePriceDecimalsStart()));
         this.txtScalePriceDecimalsEnd.setText(String.valueOf(this.getWorkstationConfig().getScalePriceDecimalsEnd()));
         this.txtScaleChecksumDigit.setText(String.valueOf(this.getWorkstationConfig().getScaleChecksumDigit()));
      } catch (Exception var2) {
      }

   }

   private void initFiscalPrinterModule() {
      try {
         this.btnActivateFiscalPrinterModule.setSelection(this.getWorkstationConfig().isModuleFiscalActive());
         String[] printerBrands = this.getAppConfigService().getAllFiscalPrinterBrands();
         int printerBrandIdx = 0;
         String[] var6 = printerBrands;
         int var5 = printerBrands.length;

         int printerPortIdx;
         for(printerPortIdx = 0; printerPortIdx < var5; ++printerPortIdx) {
            String printerBrandName = var6[printerPortIdx];
            if (this.getWorkstationConfig().getFiscalPrinterBrand().equalsIgnoreCase(printerBrandName)) {
               this.comboFiscalPrinter.select(printerBrandIdx);
            }

            ++printerBrandIdx;
         }

         this.txtFiscalPrinterModel.setText(this.getWorkstationConfig().getFiscalPrinterModel());
         String[] printerPorts = this.getAppConfigService().getAllFiscalPrinterPorts();
         printerPortIdx = 0;
         String[] var8 = printerPorts;
         int printerVelocity = printerPorts.length;

         int printerVelocityIdx;
         for(printerVelocityIdx = 0; printerVelocityIdx < printerVelocity; ++printerVelocityIdx) {
            String printerPort = var8[printerVelocityIdx];
            if (this.getWorkstationConfig().getFiscalPrinterPort().equalsIgnoreCase(printerPort)) {
               this.comboFiscalPrinterPort.select(printerPortIdx);
            }

            ++printerPortIdx;
         }

         int[] printerVelocities = this.getAppConfigService().getAllFiscalPrinterVelocities();
         printerVelocityIdx = 0;
         int[] var10 = printerVelocities;
         int var9 = printerVelocities.length;

         for(int var16 = 0; var16 < var9; ++var16) {
            printerVelocity = var10[var16];
            if (this.getWorkstationConfig().getFiscalPrinterVelocity() == printerVelocity) {
               this.comboFiscalPrinterVelocity.select(printerVelocityIdx);
            }

            ++printerVelocityIdx;
         }

         this.txtFiscalPrinterCopies.setText(String.valueOf(this.getWorkstationConfig().getFiscalPrinterCopies()));
      } catch (Exception var11) {
      }

   }

   private void createCreditCardsContents(TabFolder tabFolder, TabItem tabItem) {
      Composite composite = new Composite(tabFolder, 0);
      tabItem.setControl(composite);
      composite.setLayout(new GridLayout(2, false));
      this.tableCreditCards = new Table(composite, 67584);
      this.tableCreditCards.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.tableCreditCards.setHeaderVisible(true);
      this.tableCreditCards.setLinesVisible(true);
      TableColumn tblclmnNombre = new TableColumn(this.tableCreditCards, 0);
      tblclmnNombre.setWidth(200);
      tblclmnNombre.setText("Nombre");
      TableColumn tblclmnEstado = new TableColumn(this.tableCreditCards, 0);
      tblclmnEstado.setWidth(145);
      tblclmnEstado.setText("Estado");
      Composite composite_1 = new Composite(composite, 0);
      composite_1.setLayout(new GridLayout(1, false));
      Button btnAgregar = new Button(composite_1, 0);
      btnAgregar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            addNewCreditCard();
         }
      });
      btnAgregar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnAgregar.setSize(51, 23);
      btnAgregar.setText("Nueva tarjeta");
      Button btnActivar = new Button(composite_1, 0);
      btnActivar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            activateCreditCard();
         }
      });
      btnActivar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnActivar.setSize(46, 23);
      btnActivar.setText("Activar");
      Button btnDesactivar = new Button(composite_1, 0);
      btnDesactivar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            deactivateCreditCard();
         }
      });
      btnDesactivar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnDesactivar.setSize(63, 23);
      btnDesactivar.setText("Desactivar");
   }
//TODO: Arreglar
   private void createDebitCardsContents(TabFolder tabFolder, TabItem tabItem) {
      Composite composite = new Composite(tabFolder, 0);
      tabItem.setControl(composite);
      composite.setLayout(new GridLayout(2, false));
      this.tableDebitCards = new Table(composite, 67584);
      this.tableDebitCards.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.tableDebitCards.setHeaderVisible(true);
      this.tableDebitCards.setLinesVisible(true);
      TableColumn tblclmnNombre = new TableColumn(this.tableDebitCards, 0);
      tblclmnNombre.setWidth(200);
      tblclmnNombre.setText("Nombre");
      TableColumn tblclmnEstado = new TableColumn(this.tableDebitCards, 0);
      tblclmnEstado.setWidth(145);
      tblclmnEstado.setText("Estado");
      Composite composite_1 = new Composite(composite, 0);
      composite_1.setLayout(new GridLayout(1, false));
      Button btnAgregar = new Button(composite_1, 0);
      btnAgregar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            addNewDebitCard();
         }
      });
      btnAgregar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnAgregar.setSize(51, 23);
      btnAgregar.setText("Nueva tarjeta");
      Button btnActivar = new Button(composite_1, 0);
      btnActivar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            activateDebitCard();
         }
      });
      btnActivar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnActivar.setSize(46, 23);
      btnActivar.setText("Activar");
      Button btnDesactivar = new Button(composite_1, 0);
      btnDesactivar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            deactivateDebitCard();
         }
      });
      btnDesactivar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnDesactivar.setSize(63, 23);
      btnDesactivar.setText("Desactivar");
   }

   private void addNewCreditCard() {
      AddNewCreditCard dialog = new AddNewCreditCard(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.getOrderService().saveCreditCard(dialog.getCreditCard());
         this.initCreditCards();
      }

   }

   private void initCreditCards() {
      this.tableCreditCards.removeAll();
      List<CreditCard> creditCards = this.getOrderService().getAllCreditCards();
      Iterator var3 = creditCards.iterator();

      while(var3.hasNext()) {
         CreditCard creditCard = (CreditCard)var3.next();
         TableItem tableItem = new TableItem(this.tableCreditCards, 0);
         tableItem.setText(0, creditCard.getName());
         if (creditCard.isActive()) {
            tableItem.setText(1, "Activa");
         } else {
            tableItem.setText(1, "Inactiva");
         }
      }

   }

   private void activateCreditCard() {
      try {
         int idx = this.tableCreditCards.getSelectionIndex();
         if (idx >= 0) {
            String creditCardName = this.tableCreditCards.getItem(idx).getText(0).trim();
            CreditCard creditCard = this.getOrderService().getCreditCardByName(creditCardName);
            creditCard.setActive(true);
            this.getOrderService().saveCreditCard(creditCard);
            this.initCreditCards();
         } else {
            this.alert("Selecciona una tarjeta");
         }
      } catch (Exception var4) {
      }

   }

   private void deactivateCreditCard() {
      try {
         int idx = this.tableCreditCards.getSelectionIndex();
         if (idx >= 0) {
            String creditCardName = this.tableCreditCards.getItem(idx).getText(0).trim();
            CreditCard creditCard = this.getOrderService().getCreditCardByName(creditCardName);
            creditCard.setActive(false);
            this.getOrderService().saveCreditCard(creditCard);
            this.initCreditCards();
         } else {
            this.alert("Selecciona una tarjeta");
         }
      } catch (Exception var4) {
      }

   }

   private void initDebitCards() {
      this.tableDebitCards.removeAll();
      List<DebitCard> debitCards = this.getOrderService().getAllDebitCards();
      Iterator var3 = debitCards.iterator();

      while(var3.hasNext()) {
         DebitCard debitCard = (DebitCard)var3.next();
         TableItem tableItem = new TableItem(this.tableDebitCards, 0);
         tableItem.setText(0, debitCard.getName());
         if (debitCard.isActive()) {
            tableItem.setText(1, "Activa");
         } else {
            tableItem.setText(1, "Inactiva");
         }
      }

   }

   private void addNewDebitCard() {
      AddNewDebitCard dialog = new AddNewDebitCard(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.getOrderService().saveDebitCard(dialog.getDebitCard());
         this.initDebitCards();
      }

   }

   private void activateDebitCard() {
      try {
         int idx = this.tableDebitCards.getSelectionIndex();
         if (idx >= 0) {
            String debitCardName = this.tableDebitCards.getItem(idx).getText(0).trim();
            DebitCard debitCard = this.getOrderService().getDebitCardByName(debitCardName);
            debitCard.setActive(true);
            this.getOrderService().saveDebitCard(debitCard);
            this.initDebitCards();
         } else {
            this.alert("Selecciona una tarjeta");
         }
      } catch (Exception var4) {
      }

   }

   private void deactivateDebitCard() {
      try {
         int idx = this.tableDebitCards.getSelectionIndex();
         if (idx >= 0) {
            String debitCardName = this.tableDebitCards.getItem(idx).getText(0).trim();
            DebitCard debitCard = this.getOrderService().getDebitCardByName(debitCardName);
            debitCard.setActive(false);
            this.getOrderService().saveDebitCard(debitCard);
            this.initDebitCards();
         } else {
            this.alert("Selecciona una tarjeta");
         }
      } catch (Exception var4) {
      }

   }

   private void initPriceLists() {
      this.tablePriceLists.removeAll();
      List<PriceList> priceLists = this.getAppConfigService().getAllPriceLists();
      int number = 1;

      for(Iterator var4 = priceLists.iterator(); var4.hasNext(); ++number) {
         PriceList priceList = (PriceList)var4.next();
         TableItem tableItem = new TableItem(this.tablePriceLists, 0);
         tableItem.setText(0, String.valueOf(number));
         tableItem.setText(1, priceList.getName());
         if (priceList.isActive()) {
            tableItem.setText(2, "Activa");
         } else {
            tableItem.setText(2, "Inactiva");
         }
      }

   }

   private void editPriceList() {
      try {
         int idx = this.tablePriceLists.getSelectionIndex();
         if (idx >= 0) {
            String priceListName = this.tablePriceLists.getItem(idx).getText(1).trim();
            PriceList priceList = this.getAppConfigService().getPriceListByName(priceListName);
            EditPriceList dialog = new EditPriceList(this.getShell());
            dialog.setPriceList(priceList);
            dialog.open();
            if ("OK".equalsIgnoreCase(dialog.getAction())) {
               this.getAppConfigService().savePriceList(dialog.getPriceList());
               this.initPriceLists();
            }
         } else {
            this.alert("Selecciona una lista");
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   private void activatePriceList() {
      try {
         int idx = this.tablePriceLists.getSelectionIndex();
         if (idx >= 0) {
            String priceListName = this.tablePriceLists.getItem(idx).getText(1).trim();
            PriceList priceList = this.getAppConfigService().getPriceListByName(priceListName);
            priceList.setActive(true);
            this.getAppConfigService().savePriceList(priceList);
            this.initPriceLists();
         } else {
            this.alert("Selecciona una lista");
         }
      } catch (Exception var4) {
      }

   }

   private void deactivatePriceList() {
      try {
         int idx = this.tablePriceLists.getSelectionIndex();
         if (idx == 0) {
            this.alert("No se puede desactivar la lista predeterminada");
         } else if (idx >= 1) {
            String priceListName = this.tablePriceLists.getItem(idx).getText(1).trim();
            PriceList priceList = this.getAppConfigService().getPriceListByName(priceListName);
            priceList.setActive(false);
            this.getAppConfigService().savePriceList(priceList);
            this.initPriceLists();
         } else {
            this.alert("Selecciona una lista");
         }
      } catch (Exception var4) {
      }

   }

   private void saveGeneral() {
      try {
         AppConfig appConfig = this.getAppConfig();
         appConfig.setCompanyName(this.txtCompanyName.getText().trim());
         appConfig.setCompanyBusinessName(this.txtCompanyBusinessName.getText().trim());
         appConfig.setCompanyCuit(this.txtCompanyCuit.getText().trim());
         appConfig.setCompanyAddressStreet(this.txtCompanyAddressStreet.getText().trim());
         appConfig.setCompanyAddressNumber(this.txtCompanyAddressNumber.getText().trim());
         appConfig.setCompanyAddressOther(this.txtCompanyAddressOther.getText().trim());
         appConfig.setCompanyPostalCode(this.txtCompanyPostalCode.getText().trim());
         appConfig.setCompanyCity(this.txtCompanyCity.getText().trim());
         appConfig.setCompanyProvince(this.txtCompanyProvince.getText().trim());
         appConfig.setCompanyPhone(this.txtCompanyPhone.getText().trim());
         appConfig.setCompanyEmail(this.txtCompanyEmail.getText().trim());
         appConfig.setCompanyGrossIncomeNumber(this.txtCompanyGrossIncomeNumber.getText().trim());
         Date startActivitiesDate = this.buildDateFromInput(this.datepickerCompanyStartActivities);
         appConfig.setCompanyStartActivitiesDate(startActivitiesDate);
         String vatConditionName = this.comboCompanyVatCondition.getText();
         VatCondition companyVatCondition = this.getOrderService().getVatConditionByName(vatConditionName);
         appConfig.setCompanyVatCondition(companyVatCondition);
         if (this.getWorkstationConfig().isCashOpened()) {
            this.getCashRegister().getCurrentOrder().setCompanyVatCondition(companyVatCondition);
         }

         try {
            appConfig.setCompanyPosNumber(Integer.valueOf(this.txtCompanyPosNumber.getText().trim()));
         } catch (Exception var6) {
            appConfig.setCompanyPosNumber(1);
         }

         appConfig.setCompanyWebsite(this.txtCompanyWebsite.getText().trim());
         this.getAppConfigService().saveAppConfig(appConfig);
      } catch (Exception var7) {
         logger.error("Error al guardar Configuración General");
         logger.error("Message: " + var7.getMessage());
         logger.error("Exception: ", var7);
      }

   }

   private void saveLogo() {
      try {
         if (this.getDeleteLogo()) {
            this.appConfig.setCompanyLogo("");
            this.getAppConfigService().saveAppConfig(this.appConfig);
         } else {
            try {
               if (!"".equals(this.getTmpLogoFilename())) {
                  String baseIn = "C://facilvirtual//tmp//";
                  String fileIn = baseIn + this.getTmpLogoFilename();
                  String baseOut = "C://facilvirtual//images//";
                  String filenameOut = "logo." + FVFileUtils.getFileType(this.getTmpLogoFilename());
                  String fileOut = baseOut + filenameOut;
                  FVFileUtils.copyFile(fileIn, fileOut);
                  this.appConfig.setCompanyLogo(filenameOut);
                  this.getAppConfigService().saveAppConfig(this.appConfig);
                  this.deleteLogoTmpFile();
               }
            } catch (Exception var6) {
               logger.error("Se produjo un error eliminando archivo temporal: " + this.getTmpLogoFilename());
               logger.error(var6.getMessage());
               ////logger.error(var6);
            }
         }
      } catch (Exception var7) {
      }

   }

   private void saveScaleSettings() {
      try {
         WorkstationConfig workstationConfig = this.getWorkstationConfig();
         workstationConfig.setModuleScaleActive(this.btnActiveScaleModule.getSelection());
         if (this.comboScaleLabelType.getSelectionIndex() == 0) {
            workstationConfig.setScaleLabelType("WEIGHT");
         } else {
            workstationConfig.setScaleLabelType("PRICE");
         }

         workstationConfig.setScaleCode(this.txtScaleCode.getText());
         workstationConfig.setScaleProductCodeStart(Integer.valueOf(this.txtScaleProductCodeStart.getText().trim()));
         workstationConfig.setScaleProductCodeEnd(Integer.valueOf(this.txtScaleProductCodeEnd.getText().trim()));
         workstationConfig.setScaleWeightStart(Integer.valueOf(this.txtScaleWeightStart.getText().trim()));
         workstationConfig.setScaleWeightEnd(Integer.valueOf(this.txtScaleWeightEnd.getText().trim()));
         workstationConfig.setScaleWeightDecimalsStart(Integer.valueOf(this.txtScaleWeightDecimalsStart.getText().trim()));
         workstationConfig.setScaleWeightDecimalsEnd(Integer.valueOf(this.txtScaleWeightDecimalsEnd.getText().trim()));
         workstationConfig.setScalePriceStart(Integer.valueOf(this.txtScalePriceStart.getText().trim()));
         workstationConfig.setScalePriceEnd(Integer.valueOf(this.txtScalePriceEnd.getText().trim()));
         workstationConfig.setScalePriceDecimalsStart(Integer.valueOf(this.txtScalePriceDecimalsStart.getText().trim()));
         workstationConfig.setScalePriceDecimalsEnd(Integer.valueOf(this.txtScalePriceDecimalsEnd.getText().trim()));
         workstationConfig.setScaleChecksumDigit(Integer.valueOf(this.txtScaleChecksumDigit.getText().trim()));
         this.getAppConfigService().saveWorkstationConfig(workstationConfig);
      } catch (Exception var2) {
      }

   }

   private void saveFiscalPrinterSettings() {
      try {
         WorkstationConfig workstationConfig = this.getWorkstationConfig();
         workstationConfig.setModuleFiscalActive(this.btnActivateFiscalPrinterModule.getSelection());
         String brandName = this.comboFiscalPrinter.getItem(this.comboFiscalPrinter.getSelectionIndex());
         workstationConfig.setFiscalPrinterBrand(brandName);
         workstationConfig.setFiscalPrinterModel(this.txtFiscalPrinterModel.getText().trim());
         String port = this.comboFiscalPrinterPort.getItem(this.comboFiscalPrinterPort.getSelectionIndex());
         workstationConfig.setFiscalPrinterPort(port);
         String velocity = this.comboFiscalPrinterVelocity.getItem(this.comboFiscalPrinterVelocity.getSelectionIndex());
         workstationConfig.setFiscalPrinterVelocity(Integer.valueOf(velocity));
         String copies = this.txtFiscalPrinterCopies.getText().trim();
         workstationConfig.setFiscalPrinterCopies(Integer.valueOf(copies));
         this.getAppConfigService().saveWorkstationConfig(workstationConfig);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         this.saveGeneral();
         this.saveLogo();
         if (this.getAppConfig().isModuleScaleAvailable()) {
            this.saveScaleSettings();
         }

         if (this.getAppConfig().isModuleFiscalAvailable()) {
            this.saveFiscalPrinterSettings();
         }

         if (this.getWorkstationConfig().isCashOpened()) {
            this.getCashRegister().updatedReceiptType();
         }

         this.saveCash();
         this.saveFacturaElectronica();
         this.savePreferences();
         Order currentOrder = this.getCashRegister().getCurrentOrder();
         this.getCashRegister().close();
         CashRegister cash = new CashRegister(false);
         if (currentOrder != null) {
            currentOrder.setCashNumber(this.getWorkstationConfig().getCashNumber());
            cash.setCurrentOrder(currentOrder);
         }

         cash.setCashier(this.getCashRegister().getCashier());
         cash.setManagerMode(this.getCashRegister().isManagerMode());
         cash.open();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   private void saveCash() {
      try {
         WorkstationConfig workstationConfig = this.getWorkstationConfig();
         int cashNumber = -1;

         try {
            cashNumber = Integer.valueOf(this.txtCashNumber.getText().trim().replaceAll(",", "."));
         } catch (Exception var11) {
         }

         if (cashNumber >= 0) {
            workstationConfig.setCashNumber(cashNumber);
         }

         String categoryName1 = this.comboDept1.getText();
         if (!"".equals(categoryName1)) {
            workstationConfig.setCashDept1(this.getProductService().getProductCategoryByName(categoryName1));
         } else {
            workstationConfig.setCashDept1((ProductCategory)null);
         }

         String categoryName2 = this.comboDept2.getText();
         if (!"".equals(categoryName2)) {
            workstationConfig.setCashDept2(this.getProductService().getProductCategoryByName(categoryName2));
         } else {
            workstationConfig.setCashDept2((ProductCategory)null);
         }

         String categoryName3 = this.comboDept3.getText();
         if (!"".equals(categoryName3)) {
            workstationConfig.setCashDept3(this.getProductService().getProductCategoryByName(categoryName3));
         } else {
            workstationConfig.setCashDept3((ProductCategory)null);
         }

         String categoryName4 = this.comboDept4.getText();
         if (!"".equals(categoryName4)) {
            workstationConfig.setCashDept4(this.getProductService().getProductCategoryByName(categoryName4));
         } else {
            workstationConfig.setCashDept4((ProductCategory)null);
         }

         String categoryName5 = this.comboDept5.getText();
         if (!"".equals(categoryName5)) {
            workstationConfig.setCashDept5(this.getProductService().getProductCategoryByName(categoryName5));
         } else {
            workstationConfig.setCashDept5((ProductCategory)null);
         }

         String categoryName6 = this.comboDept6.getText();
         if (!"".equals(categoryName6)) {
            workstationConfig.setCashDept6(this.getProductService().getProductCategoryByName(categoryName6));
         } else {
            workstationConfig.setCashDept6((ProductCategory)null);
         }

         String categoryName7 = this.comboDept7.getText();
         if (!"".equals(categoryName7)) {
            workstationConfig.setCashDept7(this.getProductService().getProductCategoryByName(categoryName7));
         } else {
            workstationConfig.setCashDept7((ProductCategory)null);
         }

         String categoryName8 = this.comboDept8.getText();
         if (!"".equals(categoryName8)) {
            workstationConfig.setCashDept8(this.getProductService().getProductCategoryByName(categoryName8));
         } else {
            workstationConfig.setCashDept8((ProductCategory)null);
         }

         this.getAppConfigService().saveWorkstationConfig(workstationConfig);
      } catch (Exception var12) {
         logger.error("Error al guardar configuracion de caja");
         logger.error("Message: " + var12.getMessage());
         logger.error("Exception: ", var12);
      }

   }

   private void saveFacturaElectronica() {
      try {
         AppConfig appConfig = this.getAppConfig();
         WorkstationConfig workstationConfig = this.getWorkstationConfig();
         appConfig.setAfipEnabledFacturaElectronica(this.btnEnabledFacturaElectronica.getSelection());
         if (this.getIntegerValueFromText(this.txtCodigoFacturaElectronica) != null) {
            workstationConfig.setCodFacturaElectronica(this.getIntegerValueFromText(this.txtCodigoFacturaElectronica));
         } else {
            workstationConfig.setCodFacturaElectronica(0);
         }

         workstationConfig.initValidCodFactElect(this.getAppConfigService().getCurrentInstallationCode());
         if (this.getIntegerValueFromText(this.txtAfipPtoVta) != null) {
            appConfig.setAfipPtoVta(this.getIntegerValueFromText(this.txtAfipPtoVta));
         } else {
            appConfig.setAfipPtoVta(0);
         }

         appConfig.setAfipEnabledFacturaA(this.btnEnabledFacturaA.getSelection());
         this.getAppConfigService().saveAppConfig(appConfig);
         this.getAppConfigService().saveWorkstationConfig(workstationConfig);
      } catch (Exception var3) {
         logger.error("Error al guardar la configuración de Factura Electrónica");
         logger.error("Message: " + var3.getMessage());
         logger.error("Exception: ", var3);
      }

   }

   private void savePreferences() {
      try {
         AppConfig appConfig = this.getAppConfig();
         Integer budgetStartNumber = this.getIntegerValueFromText(this.txtBudgetStartNumber);
         if (budgetStartNumber != null) {
            appConfig.setBudgetStartNumber(budgetStartNumber);
         }

         Integer saleStartNumber = this.getIntegerValueFromText(this.txtSaleStartNumber);
         if (saleStartNumber != null) {
            appConfig.setSaleStartNumber(saleStartNumber);
         }

         try {
            appConfig.setPtoVta(Integer.valueOf(this.txtPtoVta.getText()));
         } catch (Exception var7) {
            appConfig.setPtoVta(1);
         }

         this.getAppConfigService().saveAppConfig(appConfig);
         WorkstationConfig workstationConfig = this.getWorkstationConfig();
         workstationConfig.setOpenCashOnLogin(this.btnOpenCashOnLogin.getSelection());
         workstationConfig.setOpenConfirmPrintOrder(this.btnOpenConfirmPrintOrder.getSelection());
         workstationConfig.setOpenConfirmFacturaElectronica(this.btnOpenConfirmFacturaElectronica.getSelection());
         workstationConfig.setPrintCodeInTickets(this.btnPrintCodeInTickets.getSelection());
         ReceiptType defaultReceiptTypeForOrders = this.getAppConfigService().getReceiptTypeByName(this.comboReceiptTypesForOrders.getText());
         workstationConfig.setDefaultReceiptTypeForOrders(defaultReceiptTypeForOrders);
         ReceiptType defaultReceiptTypeForPurchases = this.getAppConfigService().getReceiptTypeByName(this.comboReceiptTypesForPurchases.getText());
         workstationConfig.setDefaultReceiptTypeForPurchases(defaultReceiptTypeForPurchases);
         this.getAppConfigService().saveWorkstationConfig(workstationConfig);
      } catch (Exception var8) {
         logger.error("Error al guardar preferencias");
         logger.error("Message: " + var8.getMessage());
         logger.error("Exception: ", var8);
      }

   }

   protected void initLogo() {
      try {
         String fileName;
         Image origImage;
         Image scaledImage;
         GridData gd_lblLogo;
         if (this.getAppConfig().isCompanyLogoAvailable()) {
            fileName = "C://facilvirtual//images//" + this.getAppConfig().getCompanyLogo();
            origImage = new Image(Display.getCurrent(), fileName);
            scaledImage = FVImageUtils.scaleTo(origImage, 300, 100);
            this.lblLogo.setMargins(FVImageUtils.calculateLeftMargin(scaledImage, 320), FVImageUtils.calculateTopMargin(scaledImage, 120), 0, 0);
            this.lblLogo.setImage(scaledImage);
            gd_lblLogo = new GridData(16777216, 16777216, false, false, 1, 1);
            gd_lblLogo.horizontalIndent = 10;
            gd_lblLogo.widthHint = 320;
            gd_lblLogo.heightHint = 120;
            this.lblLogo.setLayoutData(gd_lblLogo);
         } else {
            fileName = "C://facilvirtual//images//su_logo.jpg";
            origImage = new Image(Display.getCurrent(), fileName);
            scaledImage = FVImageUtils.scaleTo(origImage, 300, 100);
            this.lblLogo.setMargins(FVImageUtils.calculateLeftMargin(scaledImage, 320), FVImageUtils.calculateTopMargin(scaledImage, 120), 0, 0);
            this.lblLogo.setImage(scaledImage);
            gd_lblLogo = new GridData(16777216, 16777216, false, false, 1, 1);
            gd_lblLogo.horizontalIndent = 10;
            gd_lblLogo.widthHint = 320;
            gd_lblLogo.heightHint = 120;
            this.lblLogo.setLayoutData(gd_lblLogo);
         }
      } catch (Exception var5) {
         logger.error("Error inicializando logo");
         logger.error(var5.getMessage());
         ////logger.error(var5);
      }

   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Configuración");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.deleteLogoTmpFile();
         this.close();
      }

   }

   protected void deleteLogoTmpFile() {
      try {
         if (!"".equals(this.getTmpLogoFilename())) {
            String baseOut = "C://facilvirtual//tmp//";
            String fileOut = baseOut + this.getTmpLogoFilename();
            FVFileUtils.deleteFile(fileOut);
         }
      } catch (Exception var3) {
         logger.error("Se produjo un error eliminando archivo temporal: " + this.getTmpLogoFilename());
         logger.error(var3.getMessage());
         ////logger.error(var3);
         var3.printStackTrace();
      }

   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 0, "Aceptar", false);
      //button.addSelectionListener(new 17(this));
      this.createButton(parent, 1, "Cancelar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(515, 580);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   protected CashRegister getCashRegister() {
      return this.cashRegister;
   }

   protected void setCashRegister(CashRegister cashRegister) {
      this.cashRegister = cashRegister;
   }
   @Override
   public AppConfig getAppConfig() {
      return this.appConfig;
   }

   public void setAppConfig(AppConfig appConfig) {
      this.appConfig = appConfig;
   }
   @Override
   public WorkstationConfig getWorkstationConfig() {
      return this.workstationConfig;
   }

   public void setWorkstationConfig(WorkstationConfig workstationConfig) {
      this.workstationConfig = workstationConfig;
   }

   private void deleteLogo() {
      try {
         String fileName = "C://facilvirtual//images//su_logo.jpg";
         Image origImage = new Image(Display.getCurrent(), fileName);
         Image scaledImage = FVImageUtils.scaleTo(origImage, 300, 100);
         this.lblLogo.setMargins(FVImageUtils.calculateLeftMargin(scaledImage, 320), FVImageUtils.calculateTopMargin(scaledImage, 120), 0, 0);
         this.lblLogo.setImage(scaledImage);
         this.setDeleteLogo(true);
      } catch (Exception var4) {
      }

   }

   public boolean isDeleteLogo() {
      return this.deleteLogo;
   }

   public boolean getDeleteLogo() {
      return this.deleteLogo;
   }

   public void setDeleteLogo(boolean deleteLogo) {
      this.deleteLogo = deleteLogo;
   }

   protected void updateLogo() {
      try {
         FileDialog dialog = new FileDialog(this.getShell(), 4096);
         dialog.setFilterNames(new String[]{"Archivos JPG (*.jpg)", "Archivos PNG (*.png)", "Archivos GIF (*.gif)"});
         dialog.setFilterExtensions(new String[]{"*.jpg", "*.png", "*.gif"});
         dialog.setFilterPath("C:\\");
         dialog.setFileName("");
         String localFilePath = dialog.open();
         if (localFilePath != null) {
            String baseOut = "C://facilvirtual//tmp//";
            String filenameOut = "logo." + FVFileUtils.getFileType(localFilePath);
            this.setTmpLogoFilename(filenameOut);
            String fileOut = baseOut + filenameOut;
            FVFileUtils.copyFile(localFilePath, fileOut);
            Image origImage = new Image(Display.getCurrent(), fileOut);
            Image scaledImage = FVImageUtils.scaleTo(origImage, 300, 100);
            this.lblLogo.setMargins(FVImageUtils.calculateLeftMargin(scaledImage, 320), FVImageUtils.calculateTopMargin(scaledImage, 120), 0, 0);
            this.lblLogo.setImage(scaledImage);
            this.setDeleteLogo(false);
         }
      } catch (Exception var8) {
      }

   }

   public String getTmpLogoFilename() {
      return this.tmpLogoFilename;
   }

   public void setTmpLogoFilename(String tmpLogoFilename) {
      this.tmpLogoFilename = tmpLogoFilename;
   }
}
