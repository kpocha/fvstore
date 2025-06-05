package com.facilvirtual.fvstoresdesk.ui.screens.cash;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.model.ReceiptType;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import com.facilvirtual.fvstoresdesk.ui.list.CustomerOnAccountSummaryExcelGenerator;
import com.facilvirtual.fvstoresdesk.ui.list.ListOfPricesExcelGenerator;
import com.facilvirtual.fvstoresdesk.ui.list.ListOfRepositionExcelGenerator;
import com.facilvirtual.fvstoresdesk.ui.list.StockValuationExcelGenerator;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesByCategory;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesByCategoryGenerator;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesByDate;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesByDateGenerator;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesByPaymentMethods;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesByPaymentMethodsGenerator;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesByProduct;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesByProductGenerator;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesWithDetailByDate;
import com.facilvirtual.fvstoresdesk.ui.report.ReportSalesWithDetailByDateGenerator;
import com.facilvirtual.fvstoresdesk.ui.tool.CreateBarcodes;
import com.facilvirtual.fvstoresdesk.ui.tool.CreateBarcodesGenerator;
import com.facilvirtual.fvstoresdesk.ui.tool.CreateLabelsByCode;
import com.facilvirtual.fvstoresdesk.ui.tool.CreateLabelsByDate;
import com.facilvirtual.fvstoresdesk.ui.tool.CreateLabelsGenerator;
import com.facilvirtual.fvstoresdesk.ui.utils.FacturaElectronicaPrompt;
import com.facilvirtual.fvstoresdesk.ui.utils.ImportProductsFromExcelProcessor;
import com.facilvirtual.fvstoresdesk.ui.utils.PrintReceiptPrompt;
import com.facilvirtual.fvstoresdesk.util.FVDateUtils;
import com.facilvirtual.fvstoresdesk.util.FVMathUtils;
import com.facilvirtual.fvstoresdesk.util.FVMediaUtils;
import com.facilvirtual.fvstoresdesk.ui.screens.auth.Login;
import com.facilvirtual.fvstoresdesk.ui.screens.budgets.BudgetsManager;
import com.facilvirtual.fvstoresdesk.ui.screens.customers.CustomerOnAccountOpsManager;
import com.facilvirtual.fvstoresdesk.ui.screens.customers.CustomersManager;
import com.facilvirtual.fvstoresdesk.ui.screens.products.ImportProductsFromExcel;
import com.facilvirtual.fvstoresdesk.ui.screens.products.ProductCategoriesManager;
import com.facilvirtual.fvstoresdesk.ui.screens.products.ProductsManager;
import com.facilvirtual.fvstoresdesk.ui.screens.products.QuickReposition;
import com.facilvirtual.fvstoresdesk.ui.screens.purchases.PurchasesManager;
import com.facilvirtual.fvstoresdesk.ui.screens.sales.AddNewFacturaElectronicaAfip;
import com.facilvirtual.fvstoresdesk.ui.screens.sales.NotasDeCreditoManager;
import com.facilvirtual.fvstoresdesk.ui.screens.sales.PricesUpdater;
import com.facilvirtual.fvstoresdesk.ui.screens.sales.SalesManager;
import com.facilvirtual.fvstoresdesk.ui.screens.settings.PrintSettings;
import com.facilvirtual.fvstoresdesk.ui.screens.settings.Settings;
import com.facilvirtual.fvstoresdesk.ui.screens.suppliers.SupplierOnAccountOpsManager;
import com.facilvirtual.fvstoresdesk.ui.screens.suppliers.SuppliersManager;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.text.DecimalFormat;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.jface.dialogs.MessageDialog;

import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVApplicationWindow;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.AboutFacilVirtual;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.ReactivateLicense;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.confirmation.EditNetworkSettingsAlert;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.confirmation.FVConfirmDialog;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.PriceListPrompt;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister.Discount;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister.PaymentPrompt;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister.PricePrompt;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister.QtyPrompt;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister.SearchProduct;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister.Surcharge;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.Customer.ChangeCustomer;






public class CashRegister extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("CashRegister");
   protected Color themeBack;
   protected Color themeBack02;
   protected Color themeText;
   protected Color themeHeaderBack;
   protected Color themeRowOdd;
   protected Color themeRowEven;
   protected Color themeTableBack;
   protected Color themeTableText;
   protected Color themeInputReadOnlyBack;
   protected Color themeInputReadOnlyText;
   private AppConfig appConfig;
   private WorkstationConfig workstationConfig;
   private boolean fullScreenMode = false;
   private boolean hotkeysEnabled = true;
   private boolean managerMode = false;
   private boolean fromLogin = false;
   private Employee cashier;
   private Composite mainContainer;
   private Composite leftContainer;
   private Composite rightContainer;
   private Composite tableHeaderContainer;
   private Composite tableContainer;
   private Composite receiptInfoContainer;
   private Canvas canvasTotalDisplay;
   private Canvas canvasInvoiceType;
   private Label lblSubtotal;
   private Label lblDiscount;
   private Label lblTotal;
   private Label lblChange;
   private Text txtSubtotal;
   private Text txtDiscountDisplay;
   private Text txtTotal;
   private Text txtChange;
   private Text txtBarCode;
   private Text txtQty;
   private Combo comboPriceLists;
   private Table table;
   private Order currentOrder;
   private Action actionListOfPrices;
   private Action actionReportSalesByPM;
   private Action actionSalesManager;
   private Action action_7;
   private Text txtCustomerFullName;
   private Text txtVatCondition;
   private Text txtAddress;
   private Text txtCuit;
   private Text txtPhone;
   private Text txtCity;
   private Text txtDiscount;
   private Text txtVat1;
   private Text txtVat2;
   private Action actionCreateLabelsByDate;
   private Text txtA;
   private Label lblCustomerName;
   private Label lblCuit;
   private Action actionCreateBackup;
   private Action actionSettings;
   private Action actionImportProducts;
   private Action actionAbout;
   private Action actionReactivateLicense;
   private Action actionCloseSession;
   private Label btnCobrar;
   private Label btnNuevaVenta;
   private Label btnCheckPrice;
   private Action actionProductCategoriesManager;
   private Action actionSuppliersManager;
   private Action actionOpenCash;
   private Action actionCloseCash;
   private Action actionCashOperations;
   private Action actionAddNewCashIncome;
   private Action actionAddNewCashOutflow;
   private Action actionPurchasesManager;
   private MenuManager menuManagerFile;
   private MenuManager menuManagerProducts;
   private MenuManager menuManagerOrders;
   private MenuManager menuManagerPurchases;
   private MenuManager menuManagerCustomers;
   private MenuManager menuManagerSuppliers;
   private MenuManager menuManagerLists;
   private MenuManager menuManagerReports;
   private MenuManager menuManagerTools;
   private MenuManager menuManagerCash;
   private MenuManager menuManagerHelp;
   private Action actionCustomerOnAccount;
   private Action actionSupplierOnAccount;
   private TableColumn column_1;
   private TableColumn column_2;
   private Label label_1;
   private Label lblCdigo;
   private Label lblDescripcin;
   private Label lblCantidad_1;
   private Label lblUnitario;
   private Label lblImporte;
   private Label lblIVA;
   private Label lblImporteIva;
   private Label lblImporte_1;
   private TableColumn column_3;
   private TableColumn column_4;
   private TableColumn column_5;
   private TableColumn column_6;
   private TableColumn column_7;
   private TableColumn column_8;
   private TableColumn column_9;
   private Label lblNewLabel_3;
   private Composite grpDatosDelComprobante;
   private Composite composite_1;
   private Label lblTipoComprobante;
   private Combo comboReceiptTypes;
   private Label lblNmero;
   private Text txtReceiptNumber;
   private DateTime dateTimeSaleDate;
   private Label lblObs;
   private Text txtObservations;
   private Label lblDatosDelCliente;
   private Composite bottomContainer;
   private Label lblNewLabel_4;
   private Label lblCaja;
   private Text txtCashNumber;
   private Label lblCajero;
   private Text txtCashierName;
   private Label lblNewLabel_5;
   private Action actionListOfReposition;
   private Action actionBudgets;
   private Action actionPrintSettings;
   private Action actionReportSalesWithDetailByDate;
   private Action actionStockValuation;
   private Action actionReportSalesByCategory;
   private Action actionReportSalesByProduct;
   private Action actionCreateLabelsByCode;
   private Label lblFSeleccionar;
   private Action actionCreateBarcodes;
   private Action actionCustomerOnAccountSummary;
   private Action actionUpdatePrices;
   private Action actionQuickReposition;
   private Action actionNetworkSettings;
   private Action actionNotasDeCredito;
   private Action actionNotasDeDebito;
   private Action actionReportSalesByDate;

   @Override
   protected Control createContents(Composite parent) {
      try {
         logger.info("Iniciando CashRegister...");
         
         // Inicializar el tema
         this.initTheme();
         parent.setBackground(this.themeBack);
         
         Control control;
         if (this.getWorkstationConfig().isCashOpened()) {
            logger.info("Caja abierta, creando interfaz de caja...");
            control = this.createContentsCashOpened(parent);
         } else {
            logger.info("Caja cerrada, creando interfaz de caja cerrada...");
            control = this.createContentsCashClosed(parent);
            
            // Abrir caja automáticamente si corresponde
            if (this.isFromLogin() && 
                this.getWorkstationConfig().isOpenCashOnLogin() && 
                (this.getCashier() != null && 
                 (this.getCashier().isAdmin() || this.getCashier().isAllowOpenCash()))) {
                logger.info("Abriendo caja automáticamente...");
                this.openCash();
            }
         }

         // Configurar menús según permisos
         if (!this.isManagerMode()) {
            this.initDisabledMenus();
         }

         return control;
         
      } catch (Exception e) {
         logger.error("Error al crear la interfaz de CashRegister", e);
         MessageDialog.openError(getShell(), "Error", "Error al iniciar la caja registradora: " + e.getMessage());
         return null;
      }
   }
//aplicamos los colores del tema
   @Override
   protected void initTheme() {
      try {
         logger.info("Inicializando tema...");
         
         // Colores del tema
         this.themeBack = SWTResourceManager.getColor(233, 237, 234);  //Verde grisáceo muy claro
         this.themeBack02 = SWTResourceManager.getColor(246, 248, 247); //Blanco verdoso muy claro	
        // this.themeText = SWTResourceManager.getColor(62, 133, 37); //Verde medio
         //this.themeHeaderBack = SWTResourceManager.getColor(62, 133, 37); //Verde medio   
         this.themeText = SWTResourceManager.getColor(193, 63, 15); //Naranja fuerte CARPER
         this.themeHeaderBack = SWTResourceManager.getColor(193, 63, 15); //Naranja fuerte CARPER   
         this.themeTableBack = SWTResourceManager.getColor(255, 255, 255); //Blanco
         this.themeTableText = SWTResourceManager.getColor(21, 25, 36); //Azul oscuro casi negro
         this.themeRowOdd = SWTResourceManager.getColor(255, 255, 255); //Blanco
         this.themeRowEven = SWTResourceManager.getColor(246, 248, 247); //Blanco verdoso muy claro
         this.themeInputReadOnlyBack = SWTResourceManager.getColor(233, 237, 234); //Verde grisáceo muy claro
         this.themeInputReadOnlyText = SWTResourceManager.getColor(21, 25, 36); //Azul oscuro casi negro
         
      } catch (Exception e) {
         logger.error("Error al inicializar el tema", e);
         throw new RuntimeException("Error al inicializar el tema: " + e.getMessage());
      }
   }

   public CashRegister(boolean fullScreenMode) {
      super((Shell)null);
      
      this.setAppConfig(super.getAppConfig());
      this.setWorkstationConfig(super.getWorkstationConfig());
      this.createActions();
      this.setBlockOnOpen(true);
      this.setFullScreenMode(fullScreenMode);
      this.addMenuBar();
   }
   @Override
   protected Point getInitialSize() {
      org.eclipse.swt.graphics.Rectangle bounds = getShell().getDisplay().getPrimaryMonitor().getBounds();
      int width = (int)(bounds.width * 0.8);
      int height = (int)(bounds.height * 0.8);
      return new Point(width, height);
   }
   @Override
   protected boolean canHandleShellCloseEvent() {
      return false;
   }

   public boolean isManagerMode() {
      return this.managerMode;
   }

   public void setManagerMode(boolean managerMode) {
      this.managerMode = managerMode;
   }
   @Override
   public void initWindowTitle(Shell shell, WorkstationConfig workstationConfig) {
      String sessionInfo = "Modo Manager";
      if (!this.isManagerMode()) {
         sessionInfo = "Caja: " + this.getWorkstationConfig().getCashNumberToDisplay() + " - Cajero: " + WordUtils.capitalize(this.getCashier().getUsername());
      }

      if (workstationConfig.isTrialMode()) {
         Date today = new Date();
         int days = workstationConfig.getTrialExpirationDaysQty(today);
         this.initTitle(shell, "FácilVirtual - Versión de evaluación (vence en " + days + " días) - " + sessionInfo);
      } else {
         boolean expiredLicense = workstationConfig.isExpiredLicense();
         if (expiredLicense) {
            this.closeSession();
         } else {
            this.initTitle(shell, "FácilVirtual - " + sessionInfo);
         }
      }

   }
//Aca se crea el menu de la caja
   private void createActions() {
     this.actionNotasDeDebito = new Action ("Notas de débito"){
      @Override
      public void run() {
          CashRegister.this.openNotasDeCreditoManager();
         }
      };
//ARCHIVO
      this.actionCreateBackup = new Action("Crear copia de seguridad") {
         @Override
         public void run() {
            CashRegister.this.createReportSalesByProduct();
            //TODO: esto esta mal revisar ocmo hacer una copi de seguridad
         }
      };

      this.actionSettings = new Action("Configuración") {
         @Override
         public void run() {
            CashRegister.this.settings();
         }
      };
      this.actionPrintSettings = new Action("Configuración de impresión"){
               @Override
               public void run(){
                  CashRegister.this.printSettings();
               }
            };
            this.actionNetworkSettings = new Action("Configuración de red"){
               @Override
               public void run(){
                  CashRegister.this.editNetworkSettings();
               }
            };
            this.actionCloseSession = new Action("Cerrar sesión") {
               @Override
               public void run() {
                  CashRegister.this.closeSession();
               }
         };
//FIN ARCHIVO
//ARTICULOS
      this.actionUpdatePrices = new Action("Actualización masiva de precios"){
         @Override
         public void run(){
            CashRegister.this.updatePrices();
         }
      };
      this.actionQuickReposition = new Action("Reposición rápida de stock"){
         @Override
         public void run(){
            CashRegister.this.quickReposition();
         }
      };
      this.actionProductCategoriesManager = new Action("Administrador de rubros") {
         @Override
         public void run() {
             CashRegister.this.productCategoriesManager();
         }
     };
      this.actionImportProducts = new Action("Importar artículos desde Excel") {
         @Override
         public void run() {
            CashRegister.this.importProductsFromExcel();
         }
      };
//FIN ARTICULOS
//VENTAS
     this.actionSalesManager = new Action("Administrador de ventas") {
         @Override
         public void run() {
          CashRegister.this.openSalesManager();
         }
      };
      this.actionNotasDeCredito = new Action("Notas de crédito"){
         @Override
         public void run(){
            CashRegister.this.openNotasDeCreditoManager();
         }
      };
      this.actionNotasDeDebito = new Action("Notas de débito"){
         @Override
         public void run(){
            CashRegister.this.openNotasDeCreditoManager();
         }
      };
      this.actionBudgets = new Action("Presupuestos"){
         @Override
         public void run(){
            CashRegister.this.openBudgetsManager();
         }
      };
//FIN VENTAS
//COMPRAS
      this.actionPurchasesManager = new Action("Administrador de compras"){
         @Override
         public void run(){
            CashRegister.this.openPurchasesManager();
         }
      };
//FIN COMPRAS
//CLIENTES
      this.action_7 = new Action("Administrador de clientes") {
         @Override
         public void run() {
            CashRegister.this.customersManager();
         }
      };
      this.actionCustomerOnAccount = new Action("Cuentas corrientes de clientes"){
         @Override
         public void run(){
            CashRegister.this.openCustomerOnAccountManager();
         }
      };
      this.actionCustomerOnAccountSummary = new Action("Resumen de cuentas corrientes"){
         @Override
         public void run(){
            try {
               CustomerOnAccountSummaryExcelGenerator dialog = new CustomerOnAccountSummaryExcelGenerator(getShell());
               dialog.open();
            } catch (Exception e) {
               logger.error("Error al crear Resumen de ctas. ctes. de clientes");
               logger.error(e.getMessage(), e);
            }
         }
      };
//FIN CLIENTES
//PROVEEDORES
      this.actionSuppliersManager = new Action("Administrador de proveedores") {
         @Override
         public void run() {
            SuppliersManager suppliersManager = SuppliersManager.getInstance();
            suppliersManager.open();
         }
     };
      this.actionSupplierOnAccount = new Action("Cuentas corrientes de proveedores"){
         @Override
         public void run(){
            CashRegister.this.openSupplierOnAccountManager();
         }
      };
//FIN PROVEEDORES
//LISTADOS Y REPORTES
      this.actionListOfPrices = new Action("Lista de precios") {
         @Override
         public void run() {
            CashRegister.this.listOfPricesExcel();
         }
      };
      this.actionListOfReposition = new Action("Listado de reposición"){
         @Override
         public void run(){
            try {
               ListOfRepositionExcelGenerator dialog = new ListOfRepositionExcelGenerator(getShell());
               dialog.open();
            } catch (Exception error) {
               CashRegister.logger.error("Error al crear lista de reposición");
               CashRegister.logger.error(error.getMessage());
            }
         }
      };
      this.actionStockValuation = new Action("Stock valorizado"){
         @Override
         public void run(){
            try {
               StockValuationExcelGenerator dialog = new StockValuationExcelGenerator(getShell());
               dialog.open();
            } catch (Exception var2) {
               CashRegister.logger.error("Error al crear stock valorizado");
               CashRegister.logger.error(var2.getMessage());
            }
         }
      };
//FIN LISTADOS Y REPORTES
//INFORMES
      this.actionReportSalesByDate = new Action("Ventas por fecha"){
         @Override
         public void run(){
            CashRegister.this.createReportSalesByDate();
            }
      };
      this.actionReportSalesWithDetailByDate = new Action("Ventas con detalle por fecha"){
         @Override
         public void run(){
            CashRegister.this.createReportSalesWithDetailByDate();
         }
      };
      this.actionReportSalesByCategory = new Action("Ventas por rubros"){
         @Override
         public void run(){
            CashRegister.this.createReportSalesByCategory();
         }
      };
      this.actionReportSalesByProduct = new Action("Ventas por artículos"){
         @Override
         public void run(){
            CashRegister.this.createReportSalesByProduct();
         }
      };
      this.actionReportSalesByPM = new Action("Ventas por medios de pago") {
         @Override
         public void run() {
             CashRegister.this.createReportSalesByPaymentMethods();
         }
     };
//FIN INFORMES
//HERRAMIENTAS
      this.actionCreateLabelsByCode = new Action("Generar etiquetas por códigos"){
         @Override
         public void run(){
            CashRegister.this.createLabelsByCode();
         }
      };
      this.actionCreateBarcodes = new Action("Generar códigos de barra"){
         @Override
         public void run(){
            CashRegister.this.createBarcodes();
         }
      };
      this.actionCreateLabelsByDate = new Action("Generar etiquetas por fecha") {
         @Override
         public void run() {
             CashRegister.this.createLabelsByDate();
         }
     };
//FIN HERRAMIENTAS
//CAJA
      this.actionOpenCash = new Action("Abrir caja") {
         @Override
         public void run() {
            CashRegister.this.openCash();
         }
      };

      this.actionCloseCash = new Action("Cerrar caja") {
         @Override
         public void run() {
            CashRegister.this.closeCash();
         }
      };

      this.actionCashOperations = new Action("Movimientos de caja") {
         @Override
         public void run() {
            CashOperationsManager manager = CashOperationsManager.getInstance();
            manager.open();
         }
      };

      this.actionAddNewCashIncome = new Action("Nuevo ingreso") {
      @Override   
      public void run() {
            CashRegister.this.addNewCashIncome();
         }
      };

      this.actionAddNewCashOutflow = new Action("Nuevo egreso") {
      @Override
      public void run() {
            CashRegister.this.addNewCashOutflow();
         }
      };
//FIN CAJA
//AYUDA
      this.actionAbout = new Action("Acerca de FácilVirtual") {
         @Override
         public void run() {
            CashRegister.this.about();
         }
      };

      this.actionReactivateLicense = new Action("Reactivar") {
         @Override
         public void run() {
            CashRegister.this.reactivateLicense();
         }
      };
//FIN AYUDA
      
      
     
      
   }

   private void openBudgetsManager() {
      BudgetsManager window = BudgetsManager.getInstance();
      window.setCashier(this.getCashier());
      window.setCashRegister(this);
      window.open();
   }

   private void openSupplierOnAccountManager() {
      SupplierOnAccountOpsManager window = SupplierOnAccountOpsManager.getInstance();
      window.setEmployee(this.getCashier());
      window.setCashRegister(this);
      window.open();
   }

   private void openCustomerOnAccountManager() {
      CustomerOnAccountOpsManager window = CustomerOnAccountOpsManager.getInstance();
      window.setEmployee(this.getCashier());
      window.setCashRegister(this);
      window.open();
   }

   private void openSalesManager() {
      SalesManager salesManager = SalesManager.getInstance();
      salesManager.setCashier(this.getCashier());
      salesManager.setCashRegister(this);
      salesManager.open();
   }

   private void openNotasDeCreditoManager() {
      NotasDeCreditoManager manager = NotasDeCreditoManager.getInstance();
      manager.setCashier(this.getCashier());
      manager.setCashRegister(this);
      manager.open();
   }

   private void openPurchasesManager() {
      PurchasesManager purchasesManager = PurchasesManager.getInstance();
      purchasesManager.setCashier(this.getCashier());
      purchasesManager.setCashRegister(this);
      purchasesManager.open();
   }

   private void addNewCashIncome() {
      AddNewCashIncome dialog = new AddNewCashIncome(this.getShell());
      dialog.setEmployee(this.getCashier());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.updatedWorkstationConfig();
      }

   }

   private void addNewCashOutflow() {
      AddNewCashOutflow dialog = new AddNewCashOutflow(this.getShell());
      dialog.setEmployee(this.getCashier());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.updatedWorkstationConfig();
      }

   }

   private void customersManager() {
      CustomersManager customersManager = CustomersManager.getInstance();
      customersManager.setCashRegister(this);
      customersManager.open();
   }

   private void productCategoriesManager() {
      ProductCategoriesManager dialog = new ProductCategoriesManager(this.getShell());
      dialog.open();
   }

   private void settings() {
      this.saveCurrentOrderInfo();
      Settings dialog = new Settings(this.getShell());
      dialog.setCashRegister(this);
      dialog.open();
   }

   private void printSettings() {
      PrintSettings dialog = new PrintSettings(this.getShell());
      dialog.open();
   }

   private void about() {
      AboutFacilVirtual dialog = new AboutFacilVirtual(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.setParentWindow(this);
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.alert("Se desactivó la licencia de uso");
         this.updatedWorkstationConfig();
      }

   }

   private void reactivateLicense() {
      ReactivateLicense dialog = new ReactivateLicense(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.setParentWindow(this);
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.alert("La activación se realizó con éxito");
         this.updatedWorkstationConfig();
      }

   }

   private void closeSession() {
      logger.info("Cerrando sesión");
      FVMediaUtils.playSound("logout.wav");
      this.getShell().dispose();
      Login window = new Login();
      window.setBlockOnOpen(true);
      window.open();
      this.close();
   }

   private void quickReposition() {
      QuickReposition dialog = new QuickReposition(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.alert("El stock fue actualizado con éxito");
      }

   }

   private void updatePrices() {
      PricesUpdater dialog = new PricesUpdater(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.alert("Los precios fueron actualizados con éxito");
      }

   }

   private void importProductsFromExcel() {
        ImportProductsFromExcel dialog = new ImportProductsFromExcel(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            ImportProductsFromExcelProcessor processor = new ImportProductsFromExcelProcessor(this.getShell());
            processor.setBlockOnOpen(true);
            processor.setSettings(dialog);
            processor.open();
         } catch (Exception var3) {
         }
      }

   }

   public Order getCurrentOrder() {
      return this.currentOrder;
   }

   public void setCurrentOrder(Order currentOrder) {
      this.currentOrder = currentOrder;
   }

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   protected Control createContentsCashClosed(Composite parent) {
      logger.info("Iniciando Caja cerrada");
      if (this.isFullScreenMode()) {
         this.getMenuBarManager().dispose();
      }

      Composite container = new Composite(parent, 0);
      container.setBackground(this.themeBack);
      container.setLayout((Layout)null);
      Label lblLaCajaEst = new Label(container, 0);
      lblLaCajaEst.setBounds(10, 10, 157, 13);
      lblLaCajaEst.setText("La caja se encuentra cerrada.");
      lblLaCajaEst.setBackground(this.themeBack);
      this.setCurrentOrder((Order)null);
      return container;
   }

   protected Control createContentsCashOpened(Composite parent) {
      logger.info("Iniciando Caja abierta");
      if (this.isFullScreenMode()) {
         this.getMenuBarManager().dispose();
         this.getShell().setFullScreen(this.isFullScreenMode());
      }

      this.mainContainer = new Composite(parent, 0);
      this.mainContainer.setBackground(this.themeBack);
      GridLayout gridLayout = new GridLayout(2, false);
      gridLayout.marginHeight = 0;
      gridLayout.marginWidth = 0;
      gridLayout.horizontalSpacing = 15;
      this.mainContainer.setLayout(gridLayout);

      // Contenedor izquierdo - expandible
      this.leftContainer = new Composite(this.mainContainer, 0);
      this.leftContainer.setBackground(this.themeBack);
      GridLayout gl_leftContainer = new GridLayout(1, false);
      gl_leftContainer.marginTop = 10;
      gl_leftContainer.marginLeft = 15;
      gl_leftContainer.verticalSpacing = 0;
      gl_leftContainer.marginHeight = 0;
      gl_leftContainer.marginWidth = 0;
      this.leftContainer.setLayout(gl_leftContainer);
      GridData gd_leftContainer = new GridData(SWT.FILL, SWT.FILL, true, true);
      gd_leftContainer.widthHint = SWT.DEFAULT;
      this.leftContainer.setLayoutData(gd_leftContainer);

      // Contenedor derecho - ancho fijo pero altura expandible
      this.rightContainer = new Composite(this.mainContainer, 0);
      this.rightContainer.setBackground(this.themeBack);
      GridLayout gl_rightContainer = new GridLayout(1, false);
      gl_rightContainer.marginRight = 15;
      gl_rightContainer.marginHeight = 0;
      gl_rightContainer.marginWidth = 0;
      this.rightContainer.setLayout(gl_rightContainer);
      GridData gd_rightContainer = new GridData(SWT.END, SWT.FILL, false, true);
      gd_rightContainer.widthHint = 322;
      this.rightContainer.setLayoutData(gd_rightContainer);

      this.createLeftContents();
      this.createRightContents();
      this.createBottomContents();
      this.initTotalDisplayForOtherInvoice();
      this.initTableForOtherInvoice();
      this.configureHotkeys(parent);
      
      if (this.getCurrentOrder() == null) {
         this.initNewOrder();
      } else {
         this.initCurrentOrder();
      }

      return this.mainContainer;
   }

   private void createLeftContents() {
      // Contenedor superior
      Composite leftContentTop = new Composite(this.leftContainer, 0);
      leftContentTop.setBackground(this.themeBack);
      GridData gd_leftContentTop = new GridData(SWT.FILL, SWT.TOP, true, false);
      gd_leftContentTop.heightHint = 52;
      leftContentTop.setLayoutData(gd_leftContentTop);

      // Contenedor del encabezado de la tabla
      this.tableHeaderContainer = new Composite(this.leftContainer, 0);
      GridLayout gl_tableHeaderContainer = new GridLayout(9, false);
      gl_tableHeaderContainer.horizontalSpacing = 0;
      gl_tableHeaderContainer.verticalSpacing = 0;
      gl_tableHeaderContainer.marginHeight = 0;
      gl_tableHeaderContainer.marginWidth = 0;
      this.tableHeaderContainer.setLayout(gl_tableHeaderContainer);
      GridData gd_tableHeaderContainer = new GridData(SWT.FILL, SWT.TOP, true, false);
      gd_tableHeaderContainer.heightHint = 19;
      this.tableHeaderContainer.setLayoutData(gd_tableHeaderContainer);
      this.tableHeaderContainer.setBackground(this.themeBack);

      // Contenedor de la tabla - expandible
      this.tableContainer = new Composite(this.leftContainer, 0);
      this.tableContainer.setLayout(new FillLayout(SWT.VERTICAL));
      GridData gd_tableContainer = new GridData(SWT.FILL, SWT.FILL, true, true);
      this.tableContainer.setLayoutData(gd_tableContainer);
      this.tableContainer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

      // Contenedor inferior
      Composite leftContentBottom = new Composite(this.leftContainer, 0);
      leftContentBottom.setBackground(this.themeBack);
      GridLayout gl_leftContentBottom = new GridLayout(1, false);
      gl_leftContentBottom.marginHeight = 0;
      gl_leftContentBottom.marginWidth = 0;
      leftContentBottom.setLayout(gl_leftContentBottom);
      GridData gd_leftContentBottom = new GridData(SWT.FILL, SWT.BOTTOM, true, false);
      gd_leftContentBottom.heightHint = 50;
      leftContentBottom.setLayoutData(gd_leftContentBottom);

      Label lblNewLabel_1 = new Label(this.leftContainer, 0);
      GridData gd_lblNewLabel_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel_1.heightHint = 21;
      lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
      lblNewLabel_1.setText("");
      this.receiptInfoContainer = new Composite(this.leftContainer, 0);
      GridLayout gl_receiptInfoContainer = new GridLayout(1, false);
      gl_receiptInfoContainer.verticalSpacing = 0;
      gl_receiptInfoContainer.marginWidth = 0;
      this.receiptInfoContainer.setLayout(gl_receiptInfoContainer);
      GridData gd_receiptInfoContainer = new GridData(4, 128, false, false, 1, 1);
      gd_receiptInfoContainer.heightHint = 130;
      gd_receiptInfoContainer.widthHint = 250;
      this.receiptInfoContainer.setLayoutData(gd_receiptInfoContainer);
      this.receiptInfoContainer.setBackground(this.themeBack);
      Label lblDatosDelComprobante = new Label(this.receiptInfoContainer, 0);
      GridData gd_lblDatosDelComprobante = new GridData(16384, 16777216, true, false, 1, 1);
      gd_lblDatosDelComprobante.widthHint = 157;
      gd_lblDatosDelComprobante.heightHint = 19;
      lblDatosDelComprobante.setLayoutData(gd_lblDatosDelComprobante);
      lblDatosDelComprobante.setForeground(SWTResourceManager.getColor(1));
      lblDatosDelComprobante.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      lblDatosDelComprobante.setBackground(this.themeHeaderBack);
      lblDatosDelComprobante.setText(" Datos del comprobante");
      this.grpDatosDelComprobante = new Composite(this.receiptInfoContainer, 0);
      this.grpDatosDelComprobante.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.grpDatosDelComprobante.setBackground(SWTResourceManager.getColor(169, 169, 169));
      this.grpDatosDelComprobante.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      GridLayout gl_grpDatosDelComprobante = new GridLayout(1, false);
      gl_grpDatosDelComprobante.verticalSpacing = 0;
      gl_grpDatosDelComprobante.marginWidth = 1;
      gl_grpDatosDelComprobante.marginHeight = 1;
      this.grpDatosDelComprobante.setLayout(gl_grpDatosDelComprobante);
      this.composite_1 = new Composite(this.grpDatosDelComprobante, 0);
      this.composite_1.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.composite_1.setBackground(this.themeBack02);
      GridLayout gl_composite_1 = new GridLayout(8, false);
      gl_composite_1.marginHeight = 7;
      gl_composite_1.horizontalSpacing = 10;
      this.composite_1.setLayout(gl_composite_1);
      Label lblFecha = new Label(this.composite_1, 0);
      lblFecha.setBackground(this.themeBack02);
      lblFecha.setForeground(this.themeText);
      lblFecha.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      lblFecha.setText("Fecha");
      this.dateTimeSaleDate = new DateTime(this.composite_1, 2048);
      this.dateTimeSaleDate.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      GridData gd_dateTimeSaleDate = new GridData(4, 16777216, false, false, 1, 1);
      gd_dateTimeSaleDate.heightHint = 20;
      this.dateTimeSaleDate.setLayoutData(gd_dateTimeSaleDate);
      if (this.getCurrentOrder() != null && this.getCurrentOrder().getSaleDate() != null) {
         GregorianCalendar calendar = new GregorianCalendar();
         calendar.setTime(new Date(this.getCurrentOrder().getSaleDate().getTime()));
         this.dateTimeSaleDate.setDay(calendar.get(5));
         this.dateTimeSaleDate.setMonth(calendar.get(2));
         this.dateTimeSaleDate.setYear(calendar.get(1));
      }

      this.lblTipoComprobante = new Label(this.composite_1, 0);
      this.lblTipoComprobante.setBackground(this.themeBack02);
      this.lblTipoComprobante.setForeground(this.themeText);
      this.lblTipoComprobante.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblTipoComprobante.setText("Tipo");
      this.comboReceiptTypes = new Combo(this.composite_1, 8);
      this.comboReceiptTypes.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            updatedReceiptType();
         }
      });
      this.comboReceiptTypes.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      GridData gd_comboReceiptTypes = new GridData(16384, 16777216, false, false, 1, 1);
      gd_comboReceiptTypes.widthHint = 95;
      this.comboReceiptTypes.setLayoutData(gd_comboReceiptTypes);
      List<ReceiptType> receiptTypes = this.getAppConfigService().getActiveReceiptTypesForOrder();
      int selectedIdx = 0;

      for(Iterator var23 = receiptTypes.iterator(); var23.hasNext(); ++selectedIdx) {
         ReceiptType rt = (ReceiptType)var23.next();
         this.comboReceiptTypes.add(rt.getName());
         if (this.getCurrentOrder() != null && this.getCurrentOrder().getReceiptType().getName().equalsIgnoreCase(rt.getName())) {
            this.comboReceiptTypes.select(selectedIdx);
         }
      }

      this.lblNmero = new Label(this.composite_1, 0);
      this.lblNmero.setBackground(this.themeBack02);
      this.lblNmero.setForeground(this.themeText);
      this.lblNmero.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblNmero.setText("Nro.");
      this.txtReceiptNumber = new Text(this.composite_1, 2048);
      this.txtReceiptNumber.setTextLimit(30);
      this.txtReceiptNumber.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      GridData gd_txtReceiptNumber = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtReceiptNumber.heightHint = 18;
      gd_txtReceiptNumber.widthHint = 40;
      this.txtReceiptNumber.setLayoutData(gd_txtReceiptNumber);
      if (this.getCurrentOrder() != null && this.getCurrentOrder().getReceiptNumber() != null) {
         this.txtReceiptNumber.setText(String.valueOf(this.getCurrentOrder().getReceiptNumber()));
      } else {
         this.txtReceiptNumber.setText(String.valueOf(this.getAppConfig().getSaleStartNumber()));
      }

      new Label(this.composite_1, 0);
      new Label(this.composite_1, 0);
      this.lblObs = new Label(this.composite_1, 0);
      this.lblObs.setForeground(this.themeText);
      this.lblObs.setBackground(this.themeBack02);
      this.lblObs.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblObs.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblObs.setText("Observaciones");
      this.txtObservations = new Text(this.composite_1, 2626);
      this.txtObservations.setTextLimit(255);
      this.txtObservations.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      GridData gd_txtObservations = new GridData(4, 16777216, true, false, 7, 1);
      gd_txtObservations.heightHint = 38;
      this.txtObservations.setLayoutData(gd_txtObservations);
      if (this.getCurrentOrder() != null) {
         this.txtObservations.setText(String.valueOf(this.getCurrentOrder().getObservations()));
      }

      Composite customerInfoContainer = new Composite(this.leftContainer, 0);
      GridLayout gl_customerInfoContainer = new GridLayout(1, false);
      gl_customerInfoContainer.horizontalSpacing = 0;
      gl_customerInfoContainer.verticalSpacing = 0;
      gl_customerInfoContainer.marginHeight = 0;
      gl_customerInfoContainer.marginWidth = 0;
      customerInfoContainer.setLayout(gl_customerInfoContainer);
      GridData gd_customerInfoContainer = new GridData(4, 128, false, false, 1, 1);
      gd_customerInfoContainer.heightHint = 115;
      gd_customerInfoContainer.widthHint = 250;
      customerInfoContainer.setLayoutData(gd_customerInfoContainer);
      customerInfoContainer.setBackground(this.themeBack);
      this.lblDatosDelCliente = new Label(customerInfoContainer, 0);
      GridData gd_lblDatosDelCliente = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblDatosDelCliente.widthHint = 115;
      gd_lblDatosDelCliente.heightHint = 19;
      this.lblDatosDelCliente.setLayoutData(gd_lblDatosDelCliente);
      this.lblDatosDelCliente.setForeground(SWTResourceManager.getColor(1));
      this.lblDatosDelCliente.setBackground(this.themeHeaderBack);
      this.lblDatosDelCliente.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblDatosDelCliente.setText(" Datos del cliente");
      Composite grpCliente = new Composite(customerInfoContainer, 0);
      grpCliente.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      grpCliente.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      grpCliente.setBackground(SWTResourceManager.getColor(169, 169, 169));
      GridLayout gl_grpCliente = new GridLayout(1, false);
      gl_grpCliente.marginWidth = 1;
      gl_grpCliente.marginHeight = 1;
      grpCliente.setLayout(gl_grpCliente);
      Composite grpClienteTable = new Composite(grpCliente, 0);
      grpClienteTable.setBackground(this.themeBack02);
      GridLayout gl_leftContainer11 = new GridLayout(5, false);
      gl_leftContainer11.marginHeight = 7;
      gl_leftContainer11.horizontalSpacing = 10;
      grpClienteTable.setLayout(gl_leftContainer11);
      grpClienteTable.setLayoutData(new GridData(4, 1, true, true));
      this.lblCustomerName = new Label(grpClienteTable, 0);
      this.lblCustomerName.setForeground(this.themeText);
      this.lblCustomerName.setBackground(this.themeBack02);
      GridData gd_lblCustomerName = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblCustomerName.widthHint = 100;
      this.lblCustomerName.setLayoutData(gd_lblCustomerName);
      this.lblCustomerName.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblCustomerName.setText("Nombre");
      this.txtCustomerFullName = new Text(grpClienteTable, 8);
      this.txtCustomerFullName.setForeground(this.themeInputReadOnlyText);
      this.txtCustomerFullName.setBackground(this.themeInputReadOnlyBack);
      this.txtCustomerFullName.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.txtCustomerFullName.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      Label lblTelfono = new Label(grpClienteTable, 0);
      lblTelfono.setForeground(this.themeText);
      lblTelfono.setBackground(this.themeBack02);
      GridData gd_lblTelfono = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblTelfono.widthHint = 100;
      lblTelfono.setLayoutData(gd_lblTelfono);
      lblTelfono.setText("Teléfono/s");
      lblTelfono.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.txtPhone = new Text(grpClienteTable, 8);
      this.txtPhone.setForeground(this.themeInputReadOnlyText);
      this.txtPhone.setBackground(this.themeInputReadOnlyBack);
      this.txtPhone.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.txtPhone.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      Button btnChangeCustomer = new Button(grpClienteTable, 0);
      btnChangeCustomer.setBackground(this.themeBack);
      GridData gd_btnChangeCustomer = new GridData(4, 16777216, false, false, 1, 1);
      gd_btnChangeCustomer.widthHint = 130;
      gd_btnChangeCustomer.heightHint = 35;
      gd_btnChangeCustomer.verticalSpan = 3;
      btnChangeCustomer.setLayoutData(gd_btnChangeCustomer);
      btnChangeCustomer.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnChangeCustomer.setText("Cambiar cliente");
      btnChangeCustomer.addSelectionListener(new SelectionAdapter(){
         @Override
         public void widgetSelected(SelectionEvent e) {
            changeCustomer();
         }
      });
      Label lblDireccin = new Label(grpClienteTable, 0);
      lblDireccin.setForeground(this.themeText);
      lblDireccin.setBackground(this.themeBack02);
      lblDireccin.setText("Domicilio");
      lblDireccin.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.txtAddress = new Text(grpClienteTable, 8);
      this.txtAddress.setForeground(this.themeInputReadOnlyText);
      this.txtAddress.setBackground(this.themeInputReadOnlyBack);
      this.txtAddress.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      this.txtAddress.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      Label lblNewLabel = new Label(grpClienteTable, 0);
      lblNewLabel.setForeground(this.themeText);
      lblNewLabel.setBackground(this.themeBack02);
      lblNewLabel.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      lblNewLabel.setText("Condición IVA");
      this.txtVatCondition = new Text(grpClienteTable, 8);
      this.txtVatCondition.setForeground(this.themeInputReadOnlyText);
      this.txtVatCondition.setBackground(this.themeInputReadOnlyBack);
      this.txtVatCondition.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.txtVatCondition.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      Label lblLocalidad = new Label(grpClienteTable, 0);
      lblLocalidad.setForeground(this.themeText);
      lblLocalidad.setBackground(this.themeBack02);
      lblLocalidad.setText("Localidad");
      lblLocalidad.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.txtCity = new Text(grpClienteTable, 8);
      this.txtCity.setForeground(this.themeInputReadOnlyText);
      this.txtCity.setBackground(this.themeInputReadOnlyBack);
      this.txtCity.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      this.txtCity.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.lblCuit = new Label(grpClienteTable, 0);
      this.lblCuit.setForeground(this.themeText);
      this.lblCuit.setBackground(this.themeBack02);
      this.lblCuit.setText("CUIT");
      this.lblCuit.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.txtCuit = new Text(grpClienteTable, 8);
      this.txtCuit.setForeground(this.themeInputReadOnlyText);
      this.txtCuit.setBackground(this.themeInputReadOnlyBack);
      this.txtCuit.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      this.txtCuit.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.bottomContainer = new Composite(this.mainContainer, 0);
      this.bottomContainer.setBackground(this.themeHeaderBack);
      GridLayout gl_bottomContainer = new GridLayout(6, false);
      gl_bottomContainer.marginRight = 8;
      gl_bottomContainer.verticalSpacing = 2;
      this.bottomContainer.setLayout(gl_bottomContainer);
      GridData gd_bottomContainer = new GridData(4, 16777216, false, false, 2, 1);
      gd_bottomContainer.heightHint = 36;
      this.bottomContainer.setLayoutData(gd_bottomContainer);
      this.lblCaja = new Label(this.bottomContainer, 0);
      this.lblCaja.setForeground(SWTResourceManager.getColor(1));
      this.lblCaja.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.lblCaja.setBackground(this.themeHeaderBack);
      this.lblCaja.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblCaja.setText("   Caja");
      this.txtCashNumber = new Text(this.bottomContainer, 8);
      this.txtCashNumber.setForeground(this.themeHeaderBack);
      this.txtCashNumber.setBackground(this.themeBack02);
      this.txtCashNumber.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      GridData gd_txtCashNumber = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtCashNumber.widthHint = 18;
      this.txtCashNumber.setLayoutData(gd_txtCashNumber);
      this.txtCashNumber.setText("01");
      this.lblCajero = new Label(this.bottomContainer, 0);
      this.lblCajero.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.lblCajero.setForeground(SWTResourceManager.getColor(1));
      this.lblCajero.setBackground(this.themeHeaderBack);
      this.lblCajero.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblCajero.setText("  Cajero");
      this.txtCashierName = new Text(this.bottomContainer, 8);
      this.txtCashierName.setForeground(this.themeHeaderBack);
      this.txtCashierName.setBackground(this.themeBack02);
      this.txtCashierName.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      GridData gd_txtCashierName = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtCashierName.widthHint = 150;
      this.txtCashierName.setLayoutData(gd_txtCashierName);
      this.lblNewLabel_4 = new Label(this.bottomContainer, 0);
      this.lblNewLabel_4.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.lblNewLabel_4.setBackground(this.themeHeaderBack);
      Image imageF1 = new Image(Display.getCurrent(), this.getImagesDir() + "icon_fullscreen.gif");
      Label lblFullscreen = new Label(this.bottomContainer, 0);
      lblFullscreen.setLocation(0, 100);
      lblFullscreen.setSize(32, 24);
      lblFullscreen.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseUp(MouseEvent e) {
            fullscreen();
         }
      });
      lblFullscreen.setImage(imageF1);
      Image imageF2;
      if (!this.isFullScreenMode()) {
         lblFullscreen.setToolTipText("Pantalla completa");
         imageF2 = new Image(Display.getCurrent(), this.getImagesDir() + "icon_fullscreen.gif");
         lblFullscreen.setImage(imageF2);
      } else {
         lblFullscreen.setToolTipText("Salir de pantalla completa");
         imageF2 = new Image(Display.getCurrent(), this.getImagesDir() + "icon_fullscreen_restore.gif");
         lblFullscreen.setImage(imageF2);
      }

      this.canvasInvoiceType = new Canvas(leftContentTop, 2048);
      this.canvasInvoiceType.setBackground(SWTResourceManager.getColor(1));
      this.canvasInvoiceType.setBounds(590, 0, 27, 26);
      this.txtA = new Text(this.canvasInvoiceType, 0);
      this.txtA.setFont(SWTResourceManager.getFont("Arial", 14, 1));
      this.txtA.setText("A");
      this.txtA.setBounds(-1, 0, 24, 24);
      this.txtBarCode = new Text(leftContentTop, 2048);
      this.txtBarCode.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.txtBarCode.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent e) {
            if (e.detail == SWT.TRAVERSE_RETURN) {
               CashRegister.this.addProductToOrder(txtBarCode.getText().trim());
            }
         }
      });
      this.txtBarCode.setBounds(0, 19, 175, 26);
      Label lblCdigoDeBarras = new Label(leftContentTop, 0);
      lblCdigoDeBarras.setForeground(SWTResourceManager.getColor(255, 255, 255));
      lblCdigoDeBarras.setBackground(this.themeHeaderBack);
      lblCdigoDeBarras.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      lblCdigoDeBarras.setBounds(0, 0, 175, 19);
      lblCdigoDeBarras.setText(" Código de barras");
      this.txtQty = new Text(leftContentTop, 133120);
      this.txtQty.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent arg0) {
            txtQty.selectAll();
         }
      });
      this.txtQty.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseDown(MouseEvent e) {
            txtQty.selectAll();
         }

         @Override
         public void mouseUp(MouseEvent e) {
            txtQty.selectAll();
         }
      });
      this.txtQty.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.txtQty.setText("1");
      this.txtQty.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent arg0) {
            if (arg0.detail == SWT.TRAVERSE_RETURN) {
               txtBarCode.setFocus();
            }
         }
      });
      this.txtQty.setBounds(181, 19, 76, 26);
      Label lblCantidad = new Label(leftContentTop, 0);
      lblCantidad.setForeground(SWTResourceManager.getColor(255, 255, 255));
      lblCantidad.setBackground(this.themeHeaderBack);
      lblCantidad.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      lblCantidad.setBounds(181, 0, 76, 19);
      lblCantidad.setText(" Cantidad");
      Button btnBuscar = new Button(leftContentTop, 0);
      btnBuscar.setBackground(this.themeBack);
      btnBuscar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            searchProduct();
         }
      });
      btnBuscar.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnBuscar.setBounds(280, 11, 133, 35);
      btnBuscar.setText("Buscar artículo");
      btnBuscar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      Label lblLista = new Label(leftContentTop, 0);
      lblLista.setForeground(SWTResourceManager.getColor(62, 133, 37));
      lblLista.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      lblLista.setBounds(431, 25, 34, 15);
      lblLista.setText("Lista");
      this.comboPriceLists = new Combo(leftContentTop, 8);
      this.comboPriceLists.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.comboPriceLists.setBounds(465, 21, 114, 23);
      List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
      Iterator var52 = priceLists.iterator();

      while(var52.hasNext()) {
         PriceList pl = (PriceList)var52.next();
         this.comboPriceLists.add(pl.getName());
      }

      this.comboPriceLists.select(0);
      Composite composite = new Composite(leftContentBottom, 0);
      composite.setBackground(this.themeBack);
      composite.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      GridLayout gl_composite = new GridLayout(6, false);
      gl_composite.marginWidth = 0;
      composite.setLayout(gl_composite);
      Button btnQuitar = new Button(composite, 0);
      GridData gd_btnQuitar = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnQuitar.widthHint = 90;
      gd_btnQuitar.heightHint = 35;
      btnQuitar.setLayoutData(gd_btnQuitar);
      btnQuitar.setSize(47, 26);
      btnQuitar.setBackground(this.themeBack);
      btnQuitar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            removeProduct();
         }
      });
      btnQuitar.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnQuitar.setText("Quitar");
      Button btnDescuento = new Button(composite, 0);
      GridData gd_btnDescuento = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnDescuento.widthHint = 90;
      gd_btnDescuento.heightHint = 35;
      btnDescuento.setLayoutData(gd_btnDescuento);
      btnDescuento.setSize(74, 26);
      btnDescuento.setBackground(this.themeBack);
      btnDescuento.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            applyDiscount();
         }
      });
      btnDescuento.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDescuento.setText("Descuento");
      Button btnSurcharge = new Button(composite, 0);
      GridData gd_btnSurcharge = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnSurcharge.widthHint = 90;
      gd_btnSurcharge.heightHint = 35;
      btnSurcharge.setLayoutData(gd_btnSurcharge);
      btnSurcharge.setSize(60, 26);
      btnSurcharge.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            CashRegister.this.applySurcharge();
         }
      });
      btnSurcharge.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnSurcharge.setText("Recargo");
      this.lblNewLabel_5 = new Label(composite, 0);
      this.lblNewLabel_5.setLayoutData(new GridData(16384, 16777216, true, false, 1, 1));
      Button btnCambiarQty = new Button(composite, 0);
      GridData gd_btnCambiarQty = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnCambiarQty.widthHint = 120;
      gd_btnCambiarQty.heightHint = 35;
      btnCambiarQty.setLayoutData(gd_btnCambiarQty);
      btnCambiarQty.setSize(113, 26);
      btnCambiarQty.setBackground(this.themeBack);
      btnCambiarQty.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnCambiarQty.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            CashRegister.this.changeQty();
         }
      });
      btnCambiarQty.setText("Cambiar cantidad");
      Button btnCambiarPrecio = new Button(composite, 0);
      GridData gd_btnCambiarPrecio = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnCambiarPrecio.widthHint = 120;
      gd_btnCambiarPrecio.heightHint = 35;
      btnCambiarPrecio.setLayoutData(gd_btnCambiarPrecio);
      btnCambiarPrecio.setSize(99, 26);
      btnCambiarPrecio.setBackground(this.themeBack);
      btnCambiarPrecio.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            changePrice();
         }
      });
      btnCambiarPrecio.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnCambiarPrecio.setText("Cambiar precio");
   }

   private void applyDiscount() {
      if (this.getCashier() == null || !this.getCashier().isAdmin() && !this.getCashier().isAllowApplyDiscount()) {
         this.alert(this.getMsgPrivilegeRequired());
      } else {
         this.processApplyDiscount();
      }

   }

   private void processApplyDiscount() {
      Discount dialog = new Discount(this.getShell());
      dialog.setCurrentOrder(this.getCurrentOrder());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            dialog.setDiscountPercent(dialog.getDiscountPercent().replaceAll(",", "\\."));
            if (!"".equals(dialog.getDiscountPercent())) {
               this.getCurrentOrder().updateDiscountPercent(FVMathUtils.roundValue(Double.valueOf(dialog.getDiscountPercent())));
            } else {
               this.getCurrentOrder().updateDiscountPercent(0.0);
            }
         } catch (NumberFormatException var4) {
            this.getCurrentOrder().updateDiscountPercent(0.0);
         }

         try {
            dialog.setDiscountAmount(dialog.getDiscountAmount().replaceAll(",", "\\."));
            if (!"".equals(dialog.getDiscountAmount())) {
               this.getCurrentOrder().updateDiscountAmount(FVMathUtils.roundValue(Double.valueOf(dialog.getDiscountAmount())));
            } else {
               this.getCurrentOrder().updateDiscountAmount(0.0);
            }
         } catch (NumberFormatException var3) {
            this.getCurrentOrder().updateDiscountAmount(0.0);
         }

         this.refreshCashRegister();
      }

   }

   private void applySurcharge() {
      if (this.getCashier() == null || !this.getCashier().isAdmin() && !this.getCashier().isAllowApplySurcharge()) {
         this.alert(this.getMsgPrivilegeRequired());
      } else {
         this.processApplySurcharge();
      }

   }

   private void processApplySurcharge() {
      Surcharge dialog = new Surcharge(this.getShell());
      dialog.setCurrentOrder(this.getCurrentOrder());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.refreshCashRegister();
      }

   }

   private void createRightContents() {
      Composite rightContentTop = new Composite(this.rightContainer, 0);
      rightContentTop.setBackground(this.themeBack);
      rightContentTop.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      this.canvasTotalDisplay = new Canvas(rightContentTop, 0);
      this.canvasTotalDisplay.setBackground(SWTResourceManager.getColor(15));
      this.canvasTotalDisplay.setBounds(0, 0, 322, 114);
      this.btnCobrar = new Label(rightContentTop, 0);
      this.btnCobrar.setBackground(this.themeBack);
      this.btnCobrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_pay_click.gif");
                btnCobrar.setImage(image);
            }
        });
        this.btnCobrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
                Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_pay_over.gif");
                btnCobrar.setImage(image);
                checkout();
            }
        });
        this.btnCobrar.addMouseTrackListener(new MouseTrackAdapter() {
            @Override
            public void mouseEnter(MouseEvent e) {
                Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_pay_over.gif");
                btnCobrar.setImage(image);
            }

            @Override
            public void mouseExit(MouseEvent e) {
                Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_pay.gif");
                btnCobrar.setImage(image);
            }
        });
      Image image = new Image(Display.getCurrent(), this.getImagesDir() + "btn_pay.gif");
      this.btnCobrar.setImage(image);
      this.btnCobrar.setFont(SWTResourceManager.getFont("Arial", 14, 1));
      this.btnCobrar.setBounds(0, 113, 322, 55);
      this.btnNuevaVenta = new Label(rightContentTop, 0);
      this.btnNuevaVenta.setBackground(this.themeBack);
      this.btnNuevaVenta.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseDown(MouseEvent e) {
              Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_new_sale_click.gif");
              btnNuevaVenta.setImage(image);
              initNewOrder();
          }
      });
      this.btnNuevaVenta.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseUp(MouseEvent e) {
              Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_new_sale.gif");
              btnNuevaVenta.setImage(image);
          }
      });
      this.btnNuevaVenta.addMouseTrackListener(new MouseTrackAdapter() {
          @Override
          public void mouseEnter(MouseEvent e) {
              Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_new_sale_over.gif");
              btnNuevaVenta.setImage(image);
          }
      });
      Image image2 = new Image(Display.getCurrent(), this.getImagesDir() + "btn_new_sale.gif");
      this.btnNuevaVenta.setImage(image2);
      this.btnNuevaVenta.setFont(SWTResourceManager.getFont("Arial", 11, 0));
      this.btnNuevaVenta.setBounds(0, 174, 153, 45);
      this.btnCheckPrice = new Label(rightContentTop, 0);
      this.btnCheckPrice.setBackground(this.themeBack);
      this.btnCheckPrice.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseDown(MouseEvent e) {
              Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_check_price_click.gif");
              btnCheckPrice.setImage(image);
          }
      });
      this.btnCheckPrice.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseUp(MouseEvent e) {
              Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_check_price_over.gif");
              btnCheckPrice.setImage(image);
              checkPrice();
          }
      });
      this.btnCheckPrice.addMouseTrackListener(new MouseTrackAdapter() {
          @Override
          public void mouseEnter(MouseEvent e) {
              Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_check_price_over.gif");
              btnCheckPrice.setImage(image);
          }

          @Override
          public void mouseExit(MouseEvent e) {
              Image image = new Image(Display.getCurrent(), getImagesDir() + "btn_check_price.gif");
              btnCheckPrice.setImage(image);
          }
      });
      Image image3 = new Image(Display.getCurrent(), this.getImagesDir() + "btn_check_price.gif");
      this.btnCheckPrice.setImage(image3);
      this.btnCheckPrice.setFont(SWTResourceManager.getFont("Arial", 11, 0));
      this.btnCheckPrice.setBounds(169, 174, 153, 45);
      Button btnDept1 = new Button(rightContentTop, 0);
      btnDept1.setAlignment(16384);
      btnDept1.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDept1.setBounds(0, 251, 153, 35);
      btnDept1.setText(" Departamento 01");
      if (this.getWorkstationConfig().getCashDept1() != null) {
         btnDept1.setText(" 01-" + this.getWorkstationConfig().getCashDept1().getName());
         class Dept1SelectionListener extends SelectionAdapter {
            private final CashRegister cashRegister;
            
            Dept1SelectionListener(CashRegister cashRegister) {
               this.cashRegister = cashRegister;
            }
            
            @Override
            public void widgetSelected(SelectionEvent e) {
               cashRegister.addProductCategoryToOrder(cashRegister.getWorkstationConfig().getCashDept1().getName());
            }
         }
         btnDept1.addSelectionListener(new Dept1SelectionListener(this));
      }

      Button btnDept2 = new Button(rightContentTop, 0);
      btnDept2.setAlignment(16384);
      btnDept2.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDept2.setBounds(0, 293, 153, 35);
      btnDept2.setText(" Departamento 02");
      if (this.getWorkstationConfig().getCashDept2() != null) {
         btnDept2.setText(" 02-" + this.getWorkstationConfig().getCashDept2().getName());
         btnDept2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               addProductCategoryToOrder(getWorkstationConfig().getCashDept2().getName());
            }
         });
      }

      Button btnDept3 = new Button(rightContentTop, 0);
      btnDept3.setAlignment(16384);
      btnDept3.setBounds(0, 335, 153, 35);
      btnDept3.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDept3.setText(" Departamento 03");
      if (this.getWorkstationConfig().getCashDept3() != null) {
         btnDept3.setText(" 03-" + this.getWorkstationConfig().getCashDept3().getName());
         btnDept3.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               addProductCategoryToOrder(getWorkstationConfig().getCashDept3().getName());
            }
         });
      }

      Button btnDept4 = new Button(rightContentTop, 0);
      btnDept4.setAlignment(16384);
      btnDept4.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDept4.setBounds(0, 376, 153, 35);
      btnDept4.setText(" Departamento 04");
      if (this.getWorkstationConfig().getCashDept4() != null) {
         btnDept4.setText(" 04-" + this.getWorkstationConfig().getCashDept4().getName());
         btnDept4.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               addProductCategoryToOrder(getWorkstationConfig().getCashDept4().getName());
            }
         });
      }

      Button btnDept5 = new Button(rightContentTop, 0);
      btnDept5.setAlignment(16384);
      btnDept5.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDept5.setBounds(169, 251, 153, 35);
      btnDept5.setText(" Departamento 05");
      if (this.getWorkstationConfig().getCashDept5() != null) {
         btnDept5.setText(" 05-" + this.getWorkstationConfig().getCashDept5().getName());
         btnDept5.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               addProductCategoryToOrder(getWorkstationConfig().getCashDept5().getName());
            }
         });
      }

      Button btnDept6 = new Button(rightContentTop, 0);
      btnDept6.setAlignment(16384);
      btnDept6.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDept6.setBounds(169, 293, 153, 35);
      btnDept6.setText(" Departamento 06");
      if (this.getWorkstationConfig().getCashDept6() != null) {
         btnDept6.setText(" 06-" + this.getWorkstationConfig().getCashDept6().getName());
         btnDept6.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               addProductCategoryToOrder(getWorkstationConfig().getCashDept6().getName());
            }
         });
      }

      Button btnDept7 = new Button(rightContentTop, 0);
      btnDept7.setAlignment(16384);
      btnDept7.setBounds(169, 335, 153, 35);
      btnDept7.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDept7.setText(" Departamento 07");
      if (this.getWorkstationConfig().getCashDept7() != null) {
         btnDept7.setText(" 07-" + this.getWorkstationConfig().getCashDept7().getName());
         btnDept7.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               addProductCategoryToOrder(getWorkstationConfig().getCashDept7().getName());
            }
         });
      }

      Button btnDept8 = new Button(rightContentTop, 0);
      btnDept8.setAlignment(16384);
      btnDept8.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      btnDept8.setBounds(169, 376, 153, 36);
      btnDept8.setText(" Departamento 08");
      this.lblFSeleccionar = new Label(rightContentTop, 0);
      this.lblFSeleccionar.setAlignment(16777216);
      this.lblFSeleccionar.setBackground(this.themeBack);
      this.lblFSeleccionar.setBounds(0, 233, 312, 15);
      this.lblFSeleccionar.setText("F8 - SELECCIONAR DEPARTAMENTO");
      if (this.getWorkstationConfig().getCashDept8() != null) {
         btnDept8.setText(" 08-" + this.getWorkstationConfig().getCashDept8().getName());
         btnDept8.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               addProductCategoryToOrder(getWorkstationConfig().getCashDept8().getName());
            }
         });
      }

      Composite rightContentBottom = new Composite(this.rightContainer, 0);
      rightContentBottom.setLayoutData(new GridData(4, 1024, false, true, 1, 1));
      rightContentBottom.setBackground(SWTResourceManager.getColor(1));
      Image imageLogo = new Image(Display.getCurrent(), this.getImagesDir() + "logo_facilvirtual_cash_register.gif");
      Canvas canvasLogo = new Canvas(rightContentBottom, 0);
      canvasLogo.setBounds(0, 0, 322, 90);
      Label lblLogo = new Label(canvasLogo, 0);
      lblLogo.setBackground(this.themeBack);
      lblLogo.setBounds(0, 0, 322, 90);
      lblLogo.setText("");
      lblLogo.setImage(imageLogo);
      Label lblNewLabel_1 = new Label(this.rightContainer, 0);
      GridData gd_lblNewLabel_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel_1.heightHint = 17;
      lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
   }

   private void fullscreen() {
      if (this.isFullScreenMode()) {
         logger.info("Saliendo de modo Fullscreen");
      } else {
         logger.info("Ingresando a modo Fullscreen");
      }

      this.saveCurrentOrderInfo();
      this.close();
      CashRegister cash = new CashRegister(!this.isFullScreenMode());
      cash.setCurrentOrder(this.currentOrder);
      cash.setCashier(this.getCashier());
      cash.setManagerMode(this.isManagerMode());
      cash.open();
   }

   private void saveCurrentOrderInfo() {
      Order currentOrder = this.getCurrentOrder();
      if (currentOrder != null) {
         currentOrder.setSaleDate(this.buildDateFromInput(this.dateTimeSaleDate));

         try {
            Integer value = this.getIntegerValueFromText(this.txtReceiptNumber);
            if (value == null) {
               currentOrder.setReceiptNumber("");
            } else {
               currentOrder.setReceiptNumber(String.valueOf(value));
            }
         } catch (Exception var3) {
            currentOrder.setReceiptNumber("");
         }

         currentOrder.setObservations(this.txtObservations.getText());
      }

   }

   private void openCash() {
      if (this.getWorkstationConfig().isCashOpened()) {
         this.alert("La caja ya se encuentra abierta");
      } else {
         OpenCash openCash = new OpenCash(this.getShell());
         openCash.setEmployee(this.getCashier());
         openCash.open();
         if ("OK".equalsIgnoreCase(openCash.getAction())) {
            logger.info("Apertura de caja");
            Order currentOrder = this.getCurrentOrder();
            this.close();
            CashRegister cash = new CashRegister(this.isFullScreenMode());
            cash.setCurrentOrder(currentOrder);
            cash.setCashier(this.getCashier());
            cash.setManagerMode(this.isManagerMode());
            cash.open();
         }
      }

   }

   private void closeCash() {
      if (!this.getWorkstationConfig().isCashOpened()) {
         this.alert("La Caja ya se encuentra cerrada");
      } else {
         CloseCash closeCash = new CloseCash(this.getShell());
         closeCash.setEmployee(this.getCashier());
         closeCash.open();
         if ("OK".equalsIgnoreCase(closeCash.getAction())) {
            logger.info("Cerrando la Caja");
            Order currentOrder = this.getCurrentOrder();
            this.close();
            CashRegister cash = new CashRegister(this.isFullScreenMode());
            cash.setCurrentOrder(currentOrder);
            cash.setCashier(this.getCashier());
            cash.setManagerMode(this.isManagerMode());
            cash.open();
         }
      }

   }

   private void createBottomContents() {
      this.txtCashNumber.setText(this.getWorkstationConfig().getCashNumberToDisplay());
      if (this.getCashier() != null) {
         this.txtCashierName.setText(this.getCashier().getUsername());
      }

   }

   private void initTable() {
      if (this.getCurrentOrder().isInvoiceA()) {
         this.initTableForInvoiceA();
      } else {
         this.initTableForOtherInvoice();
      }

   }

   private void initTableForInvoiceA() {
      int descColX = this.getShell().getClientArea().width - 885;
      this.clearContainer(this.tableHeaderContainer);
      this.label_1 = new Label(this.tableHeaderContainer, 0);
      this.label_1.setAlignment(16777216);
      this.label_1.setBackground(this.themeHeaderBack);
      this.label_1.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.label_1.setForeground(SWTResourceManager.getColor(1));
      GridData gd_label_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_label_1.heightHint = 19;
      gd_label_1.widthHint = 30;
      this.label_1.setLayoutData(gd_label_1);
      this.label_1.setText("#");
      this.lblCdigo = new Label(this.tableHeaderContainer, 0);
      this.lblCdigo.setAlignment(16777216);
      this.lblCdigo.setForeground(SWTResourceManager.getColor(1));
      this.lblCdigo.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblCdigo.setBackground(this.themeHeaderBack);
      GridData gd_lblCdigo = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblCdigo.heightHint = 19;
      gd_lblCdigo.widthHint = 120;
      this.lblCdigo.setLayoutData(gd_lblCdigo);
      this.lblCdigo.setText("Código");
      this.lblDescripcin = new Label(this.tableHeaderContainer, 0);
      this.lblDescripcin.setAlignment(16777216);
      this.lblDescripcin.setForeground(SWTResourceManager.getColor(1));
      this.lblDescripcin.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblDescripcin.setBackground(this.themeHeaderBack);
      GridData gd_lblDescripcin = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblDescripcin.heightHint = 19;
      gd_lblDescripcin.widthHint = descColX;
      this.lblDescripcin.setLayoutData(gd_lblDescripcin);
      this.lblDescripcin.setText("Descripción");
      this.lblCantidad_1 = new Label(this.tableHeaderContainer, 0);
      this.lblCantidad_1.setAlignment(16777216);
      GridData gd_lblCantidad_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblCantidad_1.heightHint = 19;
      gd_lblCantidad_1.widthHint = 70;
      this.lblCantidad_1.setLayoutData(gd_lblCantidad_1);
      this.lblCantidad_1.setForeground(SWTResourceManager.getColor(1));
      this.lblCantidad_1.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblCantidad_1.setBackground(this.themeHeaderBack);
      this.lblCantidad_1.setText("Cantidad");
      this.lblUnitario = new Label(this.tableHeaderContainer, 0);
      this.lblUnitario.setAlignment(16777216);
      GridData gd_lblUnitario = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblUnitario.heightHint = 19;
      gd_lblUnitario.widthHint = 70;
      this.lblUnitario.setLayoutData(gd_lblUnitario);
      this.lblUnitario.setForeground(SWTResourceManager.getColor(1));
      this.lblUnitario.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblUnitario.setBackground(this.themeHeaderBack);
      this.lblUnitario.setText("Unitario");
      this.lblImporte = new Label(this.tableHeaderContainer, 0);
      this.lblImporte.setAlignment(16777216);
      GridData gd_lblImporte = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblImporte.heightHint = 19;
      gd_lblImporte.widthHint = 55;
      this.lblImporte.setLayoutData(gd_lblImporte);
      this.lblImporte.setForeground(SWTResourceManager.getColor(1));
      this.lblImporte.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblImporte.setBackground(this.themeHeaderBack);
      this.lblImporte.setText("IVA");
      this.lblImporteIva = new Label(this.tableHeaderContainer, 0);
      this.lblImporteIva.setAlignment(16777216);
      this.lblImporteIva.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      GridData gd_lblImporteIva = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblImporteIva.widthHint = 90;
      gd_lblImporteIva.heightHint = 19;
      this.lblImporteIva.setLayoutData(gd_lblImporteIva);
      this.lblImporteIva.setBackground(this.themeHeaderBack);
      this.lblImporteIva.setForeground(SWTResourceManager.getColor(1));
      this.lblImporteIva.setText("Importe IVA");
      this.lblImporte_1 = new Label(this.tableHeaderContainer, 0);
      this.lblImporte_1.setAlignment(16777216);
      this.lblImporte_1.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      GridData gd_lblImporte_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblImporte_1.widthHint = 70;
      gd_lblImporte_1.heightHint = 19;
      this.lblImporte_1.setLayoutData(gd_lblImporte_1);
      this.lblImporte_1.setForeground(SWTResourceManager.getColor(1));
      this.lblImporte_1.setBackground(this.themeHeaderBack);
      this.lblImporte_1.setText("Importe");
      Label lblNewLabel_2 = new Label(this.tableHeaderContainer, 0);
      GridData gd_lblNewLabel_2 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel_2.widthHint = 50;
      gd_lblNewLabel_2.heightHint = 19;
      lblNewLabel_2.setLayoutData(gd_lblNewLabel_2);
      lblNewLabel_2.setBackground(this.themeHeaderBack);
      this.tableHeaderContainer.layout();
      this.clearContainer(this.tableContainer);
      this.table = new Table(this.tableContainer, 67584);
      this.table.setBackground(this.themeTableBack);
      this.table.setForeground(this.themeTableText);
      this.table.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.table.setLayout(new FillLayout());
      TableColumn column = new TableColumn(this.table, 131072);
      column.setResizable(false);
      column.setText("#");
      column.setWidth(30);
      column.setAlignment(131072);
      this.column_3 = new TableColumn(this.table, 131072);
      this.column_3.setResizable(false);
      this.column_3.setText("Código");
      this.column_3.setWidth(120);
      this.column_4 = new TableColumn(this.table, 16384);
      this.column_4.setResizable(false);
      this.column_4.setText("Descripción");
      this.column_4.setWidth(descColX);
      this.column_5 = new TableColumn(this.table, 131072);
      this.column_5.setResizable(false);
      this.column_5.setText("Cantidad");
      this.column_5.setWidth(70);
      this.column_6 = new TableColumn(this.table, 131072);
      this.column_6.setResizable(false);
      this.column_6.setText("Unitario");
      this.column_6.setWidth(70);
      this.column_7 = new TableColumn(this.table, 131072);
      this.column_7.setResizable(false);
      this.column_7.setText("IVA");
      this.column_7.setWidth(55);
      this.column_8 = new TableColumn(this.table, 131072);
      this.column_8.setResizable(false);
      this.column_8.setText("Importe IVA");
      this.column_8.setWidth(90);
      this.column_9 = new TableColumn(this.table, 131072);
      this.column_9.setResizable(false);
      this.column_9.setText("Importe");
      this.column_9.setWidth(70);
   }

   private void initTableForOtherInvoice() {
      int descColX = this.getShell().getClientArea().width - 740;
      if (this.getAppConfig().isResponsableInscripto()) {
         descColX = this.getShell().getClientArea().width - 795;
      }

      this.clearContainer(this.tableHeaderContainer);
      this.label_1 = new Label(this.tableHeaderContainer, 0);
      this.label_1.setAlignment(16777216);
      this.label_1.setBackground(this.themeHeaderBack);
      this.label_1.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.label_1.setForeground(SWTResourceManager.getColor(1));
      GridData gd_label_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_label_1.heightHint = 19;
      gd_label_1.widthHint = 30;
      this.label_1.setLayoutData(gd_label_1);
      this.label_1.setText("#");
      this.lblCdigo = new Label(this.tableHeaderContainer, 0);
      this.lblCdigo.setAlignment(16777216);
      this.lblCdigo.setForeground(SWTResourceManager.getColor(1));
      this.lblCdigo.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblCdigo.setBackground(this.themeHeaderBack);
      GridData gd_lblCdigo = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblCdigo.heightHint = 19;
      gd_lblCdigo.widthHint = 120;
      this.lblCdigo.setLayoutData(gd_lblCdigo);
      this.lblCdigo.setText("Código");
      this.lblDescripcin = new Label(this.tableHeaderContainer, 0);
      this.lblDescripcin.setAlignment(16777216);
      this.lblDescripcin.setForeground(SWTResourceManager.getColor(1));
      this.lblDescripcin.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblDescripcin.setBackground(this.themeHeaderBack);
      GridData gd_lblDescripcin = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblDescripcin.heightHint = 19;
      gd_lblDescripcin.widthHint = descColX;
      this.lblDescripcin.setLayoutData(gd_lblDescripcin);
      this.lblDescripcin.setText("Descripción");
      this.lblCantidad_1 = new Label(this.tableHeaderContainer, 0);
      this.lblCantidad_1.setAlignment(16777216);
      GridData gd_lblCantidad_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblCantidad_1.heightHint = 19;
      gd_lblCantidad_1.widthHint = 70;
      this.lblCantidad_1.setLayoutData(gd_lblCantidad_1);
      this.lblCantidad_1.setForeground(SWTResourceManager.getColor(1));
      this.lblCantidad_1.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblCantidad_1.setBackground(this.themeHeaderBack);
      this.lblCantidad_1.setText("Cantidad");
      this.lblUnitario = new Label(this.tableHeaderContainer, 0);
      this.lblUnitario.setAlignment(16777216);
      GridData gd_lblUnitario = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblUnitario.heightHint = 19;
      gd_lblUnitario.widthHint = 70;
      this.lblUnitario.setLayoutData(gd_lblUnitario);
      this.lblUnitario.setForeground(SWTResourceManager.getColor(1));
      this.lblUnitario.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblUnitario.setBackground(this.themeHeaderBack);
      this.lblUnitario.setText("Unitario");
      GridData gd_lblImporte;
      if (this.getAppConfig().isResponsableInscripto()) {
         this.lblIVA = new Label(this.tableHeaderContainer, 0);
         this.lblIVA.setAlignment(16777216);
         gd_lblImporte = new GridData(16384, 16777216, false, false, 1, 1);
         gd_lblImporte.heightHint = 19;
         gd_lblImporte.widthHint = 55;
         this.lblIVA.setLayoutData(gd_lblImporte);
         this.lblIVA.setForeground(SWTResourceManager.getColor(1));
         this.lblIVA.setFont(SWTResourceManager.getFont("Arial", 10, 1));
         this.lblIVA.setBackground(this.themeHeaderBack);
         this.lblIVA.setText("IVA");
      }

      this.lblImporte = new Label(this.tableHeaderContainer, 0);
      this.lblImporte.setAlignment(16777216);
      gd_lblImporte = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblImporte.heightHint = 19;
      gd_lblImporte.widthHint = 70;
      this.lblImporte.setLayoutData(gd_lblImporte);
      this.lblImporte.setForeground(SWTResourceManager.getColor(1));
      this.lblImporte.setFont(SWTResourceManager.getFont("Arial", 10, 1));
      this.lblImporte.setBackground(this.themeHeaderBack);
      this.lblImporte.setText("Importe");
      this.lblNewLabel_3 = new Label(this.tableHeaderContainer, 0);
      this.lblNewLabel_3.setBackground(this.themeHeaderBack);
      GridData gd_lblNewLabel_3 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel_3.widthHint = 30;
      gd_lblNewLabel_3.heightHint = 19;
      this.lblNewLabel_3.setLayoutData(gd_lblNewLabel_3);
      new Label(this.tableHeaderContainer, 0);
      new Label(this.tableHeaderContainer, 0);
      this.tableHeaderContainer.layout();
      this.clearContainer(this.tableContainer);
      this.table = new Table(this.tableContainer, 67584);
      this.table.setBackground(this.themeTableBack);
      this.table.setForeground(this.themeTableText);
      this.table.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.table.setLayout(new FillLayout());
      TableColumn column = new TableColumn(this.table, 131072);
      column.setResizable(false);
      column.setText("#");
      column.setWidth(30);
      column.setAlignment(131072);
      this.column_1 = new TableColumn(this.table, 131072);
      this.column_1.setResizable(false);
      this.column_1.setText("Código");
      this.column_1.setWidth(120);
      this.column_2 = new TableColumn(this.table, 16384);
      this.column_2.setResizable(false);
      this.column_2.setText("Descripción");
      this.column_2.setWidth(descColX);
      column = new TableColumn(this.table, 131072);
      column.setResizable(false);
      column.setText("Cantidad");
      column.setWidth(70);
      column = new TableColumn(this.table, 131072);
      column.setResizable(false);
      column.setText("Unitario");
      column.setWidth(70);
      if (this.getAppConfig().isResponsableInscripto()) {
         column = new TableColumn(this.table, 131072);
         column.setResizable(false);
         column.setText("IVA");
         column.setWidth(55);
      }

      column = new TableColumn(this.table, 131072);
      column.setResizable(false);
      column.setText("Importe");
      column.setWidth(70);
   }

   private void initTotalDisplayForOtherInvoice() {
      this.clearContainer(this.canvasTotalDisplay);
      Composite composite = new Composite(this.canvasTotalDisplay, 0);
      composite.setBackground(SWTResourceManager.getColor(2));
      composite.setBounds(0, 0, 322, 114);
      this.lblSubtotal = new Label(composite, 0);
      this.lblSubtotal.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      this.lblSubtotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.lblSubtotal.setBackground(SWTResourceManager.getColor(2));
      this.lblSubtotal.setText("Subtotal");
      this.lblSubtotal.setBounds(3, 4, 94, 24);
      this.txtTotal = new Text(composite, 131072);
      this.txtTotal.setEditable(false);
      this.txtTotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.txtTotal.setFont(SWTResourceManager.getFont("Arial", 26, 0));
      this.txtTotal.setBackground(SWTResourceManager.getColor(2));
      this.txtTotal.setBounds(131, 72, 192, 37);
      this.txtTotal.setText("$ 0,00");
      this.txtDiscount = new Text(composite, 131072);
      this.txtDiscount.setEditable(false);
      this.txtDiscount.setText("$0,00");
      this.txtDiscount.setForeground(SWTResourceManager.getColor(15));
      this.txtDiscount.setFont(SWTResourceManager.getFont("Arial", 16, 0));
      this.txtDiscount.setBackground(SWTResourceManager.getColor(2));
      this.txtDiscount.setBounds(162, 27, 160, 24);
      this.txtSubtotal = new Text(composite, 131072);
      this.txtSubtotal.setEditable(false);
      this.txtSubtotal.setText("$0,00");
      this.txtSubtotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.txtSubtotal.setFont(SWTResourceManager.getFont("Arial", 16, 0));
      this.txtSubtotal.setBackground(SWTResourceManager.getColor(2));
      this.txtSubtotal.setBounds(124, 0, 198, 24);
      this.lblDiscount = new Label(composite, 0);
      this.lblDiscount.setText("Descuento/Recargo");
      this.lblDiscount.setForeground(SWTResourceManager.getColor(15));
      this.lblDiscount.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      this.lblDiscount.setBackground(SWTResourceManager.getColor(2));
      this.lblDiscount.setBounds(3, 30, 153, 18);
      this.lblTotal = new Label(composite, 0);
      this.lblTotal.setText("Total");
      this.lblTotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.lblTotal.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      this.lblTotal.setBackground(SWTResourceManager.getColor(2));
      this.lblTotal.setBounds(4, 87, 94, 24);
      this.txtDiscountDisplay = new Text(composite, 0);
      this.txtDiscountDisplay.setFont(SWTResourceManager.getFont("Arial", 10, 0));
      this.txtDiscountDisplay.setForeground(SWTResourceManager.getColor(1));
      this.txtDiscountDisplay.setEditable(false);
      this.txtDiscountDisplay.setBackground(SWTResourceManager.getColor(2));
      this.txtDiscountDisplay.setBounds(1, 50, 311, 20);
   }

   private void initTotalDisplayForInvoiceA() {
      this.clearContainer(this.canvasTotalDisplay);
      Composite composite = new Composite(this.canvasTotalDisplay, 0);
      composite.setBackground(SWTResourceManager.getColor(2));
      composite.setBounds(0, 0, 322, 114);
      this.lblSubtotal = new Label(composite, 0);
      this.lblSubtotal.setFont(SWTResourceManager.getFont("Arial", 11, 0));
      this.lblSubtotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.lblSubtotal.setBackground(SWTResourceManager.getColor(2));
      this.lblSubtotal.setText("Subtotal");
      this.lblSubtotal.setBounds(3, 4, 94, 17);
      this.txtTotal = new Text(composite, 131072);
      this.txtTotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.txtTotal.setFont(SWTResourceManager.getFont("Arial", 25, 1));
      this.txtTotal.setBackground(SWTResourceManager.getColor(2));
      this.txtTotal.setBounds(103, 74, 220, 36);
      this.txtTotal.setText("$0,00");
      this.txtSubtotal = new Text(composite, 131072);
      this.txtSubtotal.setText("$0,00");
      this.txtSubtotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.txtSubtotal.setFont(SWTResourceManager.getFont("Arial", 13, 1));
      this.txtSubtotal.setBackground(SWTResourceManager.getColor(2));
      this.txtSubtotal.setBounds(124, 4, 198, 17);
      this.lblDiscount = new Label(composite, 0);
      this.lblDiscount.setText("Otros Tributos");
      this.lblDiscount.setForeground(SWTResourceManager.getColor(15));
      this.lblDiscount.setFont(SWTResourceManager.getFont("Arial", 11, 0));
      this.lblDiscount.setBackground(SWTResourceManager.getColor(2));
      this.lblDiscount.setBounds(3, 55, 105, 17);
      this.lblTotal = new Label(composite, 0);
      this.lblTotal.setText("Total");
      this.lblTotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.lblTotal.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      this.lblTotal.setBackground(SWTResourceManager.getColor(2));
      this.lblTotal.setBounds(4, 87, 78, 24);
      this.txtDiscount = new Text(composite, 131072);
      this.txtDiscount.setText("$0,00");
      this.txtDiscount.setForeground(SWTResourceManager.getColor(15));
      this.txtDiscount.setFont(SWTResourceManager.getFont("Arial", 13, 1));
      this.txtDiscount.setBackground(SWTResourceManager.getColor(2));
      this.txtDiscount.setBounds(136, 55, 186, 17);
      Label lblIva = new Label(composite, 0);
      lblIva.setText("IVA 10,5%");
      lblIva.setForeground(SWTResourceManager.getColor(15));
      lblIva.setFont(SWTResourceManager.getFont("Arial", 11, 0));
      lblIva.setBackground(SWTResourceManager.getColor(2));
      lblIva.setBounds(4, 38, 105, 17);
      Label lblIva_1 = new Label(composite, 0);
      lblIva_1.setText("IVA 21%");
      lblIva_1.setForeground(SWTResourceManager.getColor(15));
      lblIva_1.setFont(SWTResourceManager.getFont("Arial", 11, 0));
      lblIva_1.setBackground(SWTResourceManager.getColor(2));
      lblIva_1.setBounds(4, 21, 105, 17);
      this.txtVat1 = new Text(composite, 131072);
      this.txtVat1.setText("$0,00");
      this.txtVat1.setForeground(SWTResourceManager.getColor(15));
      this.txtVat1.setFont(SWTResourceManager.getFont("Arial", 13, 1));
      this.txtVat1.setBackground(SWTResourceManager.getColor(2));
      this.txtVat1.setBounds(136, 21, 186, 17);
      this.txtVat2 = new Text(composite, 131072);
      this.txtVat2.setText("$0,00");
      this.txtVat2.setForeground(SWTResourceManager.getColor(15));
      this.txtVat2.setFont(SWTResourceManager.getFont("Arial", 13, 1));
      this.txtVat2.setBackground(SWTResourceManager.getColor(2));
      this.txtVat2.setBounds(124, 38, 198, 17);
      this.txtDiscountDisplay = new Text(composite, 0);
      this.txtDiscountDisplay.setBounds(80, 20, 145, 13);
      this.txtDiscountDisplay.setFont(SWTResourceManager.getFont("Arial", 8, 0));
      this.txtDiscountDisplay.setForeground(SWTResourceManager.getColor(1));
      this.txtDiscountDisplay.setEditable(false);
      this.txtDiscountDisplay.setBackground(SWTResourceManager.getColor(2));
   }

   private void initTotalDisplayForChange() {
      this.clearContainer(this.canvasTotalDisplay);
      Composite composite = new Composite(this.canvasTotalDisplay, 0);
      composite.setBackground(SWTResourceManager.getColor(2));
      composite.setBounds(0, 0, 322, 114);
      this.lblChange = new Label(composite, 0);
      this.lblChange.setText("Vuelto");
      this.lblChange.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.lblChange.setFont(SWTResourceManager.getFont("Arial", 22, 0));
      this.lblChange.setBackground(SWTResourceManager.getColor(2));
      this.lblChange.setBounds(31, 29, 114, 44);
      this.lblChange.setVisible(true);
      this.txtChange = new Text(composite, 131072);
      this.txtChange.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.txtChange.setFont(SWTResourceManager.getFont("Arial", 26, 0));
      this.txtChange.setBackground(SWTResourceManager.getColor(2));
      this.txtChange.setBounds(131, 72, 192, 37);
      this.txtChange.setText("$ 0,00");
   }

   private void initCurrentOrder() {
      this.txtBarCode.setFocus();
      if (this.getCurrentOrder().isInvoiceA()) {
         this.canvasInvoiceType.setVisible(true);
         this.initTotalDisplayForInvoiceA();
         this.initTableForInvoiceA();
         this.lblCustomerName.setText("Razón Social");
         this.lblCuit.setVisible(true);
         this.txtCuit.setVisible(true);
      } else {
         this.canvasInvoiceType.setVisible(false);
         this.initTotalDisplayForOtherInvoice();
         this.initTableForOtherInvoice();
         this.lblCustomerName.setText("Sr/a.");
         this.lblCuit.setVisible(false);
         this.txtCuit.setVisible(false);
      }

      this.refreshCashRegister();
      this.tableContainer.layout();
      this.updatedCustomer();
   }

   private void initNewOrder() {
      this.txtBarCode.setFocus();
      Order order = new Order();
      order.setPosNumber(this.getAppConfig().getCompanyPosNumber());
      order.setCompanyVatCondition(this.getAppConfig().getCompanyVatCondition());
      order.setCashier(this.getCashier());
      order.setCashNumber(this.getWorkstationConfig().getCashNumber());
      order.setReceiptType(this.getWorkstationConfig().getDefaultReceiptTypeForOrders());
      this.setCurrentOrder(order);
      GregorianCalendar calendar = new GregorianCalendar();
      Date today = new Date();
      calendar.setTime(today);
      this.dateTimeSaleDate.setDay(calendar.get(5));
      this.dateTimeSaleDate.setMonth(calendar.get(2));
      this.dateTimeSaleDate.setYear(calendar.get(1));
      List<ReceiptType> receiptTypes = this.getAppConfigService().getActiveReceiptTypesForOrder();
      int selectedIdx = 0;

      for(Iterator var7 = receiptTypes.iterator(); var7.hasNext(); ++selectedIdx) {
         ReceiptType rt = (ReceiptType)var7.next();
         if (this.getWorkstationConfig().getDefaultReceiptTypeForOrders().getName().equalsIgnoreCase(rt.getName())) {
            this.comboReceiptTypes.select(selectedIdx);
         }
      }

      this.txtReceiptNumber.setText(String.valueOf(this.getAppConfig().getSaleStartNumber()));
      this.txtObservations.setText("");
      Customer customer = this.getCustomerService().getCustomer(new Long(1L));
      if (customer != null) {
         this.getCurrentOrder().setCustomer(customer);
         this.updatedCustomer();
      }

      this.updatedReceiptType();
   }

   private void newSale() {
      this.initNewOrder();
      this.txtBarCode.setText("");
   }

   private void refreshCashRegister() {
      this.refreshTable();
      this.refreshTotalPane();
      this.txtBarCode.setFocus();
   }

   private void refreshCashRegisterAfterPayment(Order previousOrder) {
      this.initNewOrder();
      this.initTotalDisplayForChange();
      this.txtChange.setText("$ " + previousOrder.getChangeToDisplay());
   }

   public static void main(String[] args) {
      try {
         CashRegister window = new CashRegister(false);
         window.setBlockOnOpen(true);
         window.open();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }
   @Override
   protected void configureShell(Shell shell) {
      super.configureShell(shell);
      this.initWindowTitle(shell, this.getWorkstationConfig());
      shell.addListener(SWT.Activate, new Listener() {
         @Override
         public void handleEvent(Event event) {
            if (isOpenSession()) {
                txtBarCode.setFocus();
            }
         }
     });
      shell.setMaximized(true);
      shell.addListener(SWT.Close, new Listener() {
         @Override
         public void handleEvent(Event e) {
             try {
                CashRegister.this.setFullScreenMode(true);
                if (isOpenSession()) {
                    saveCurrentOrderInfo();
                }
             } catch (Exception ex) {
                 logger.error("Error al cerrar la ventana", ex);
             }
         }
     });
   }

   private void configureHotkeys(Composite parent) {
      this.getShell().getDisplay().addFilter(SWT.KeyDown, new Listener() {
          @Override
          public void handleEvent(Event e) {
              if (e.keyCode == SWT.F5 && isOpenSession() && isHotkeysEnabled()) {
                  newSale();
              }
          }
      });

      this.getShell().getDisplay().addFilter(SWT.KeyDown, new Listener() {
          @Override
          public void handleEvent(Event e) {
              if (e.keyCode == SWT.F6 && isOpenSession() && isHotkeysEnabled()) {
                  checkout();
              }
          }
      });

      this.getShell().getDisplay().addFilter(SWT.KeyDown, new Listener() {
          @Override
          public void handleEvent(Event e) {
              if (e.keyCode == SWT.F9 && isOpenSession() && isHotkeysEnabled()) {
                  checkPrice();
              }
          }
      });
  }

   private boolean isOpenSession() {
      if (txtBarCode == null || txtBarCode.isDisposed()) {
         return false;
      }
      return !txtBarCode.isEnabled();
   }

   private void exit() {
      if (FVConfirmDialog.openQuestion(this.getShell(), "Salir", "¿Quieres salir del programa?")) {
         logger.info("Cerrando aplicación");

         try {
            this.getAppConfigService().closeDatabaseConnection();
         } catch (Exception var3) {
            logger.error(var3.getMessage());
            logger.error(var3.toString());
         }

         try {
            FVMediaUtils.playSound("logout.wav");
            Thread.sleep(500L);
         } catch (InterruptedException var2) {
            logger.error("Error al salir de la aplicación");
            logger.error(var2.getMessage());
            ////logger.error(var2);
         }

         System.exit(0);
      }

   }
   @Override
   protected MenuManager createMenuManager() {
      MenuManager menuManager = new MenuManager("menu");
      this.menuManagerFile = new MenuManager("&Archivo");
      menuManager.add(this.menuManagerFile);
      Action exitAction = new Action("&Salir"){
         @Override
         public void run(){
            CashRegister.this.exit();
         }
      };
      this.menuManagerFile.add(this.actionCreateBackup);
      this.menuManagerFile.add(new Separator());
      this.menuManagerFile.add(this.actionSettings);
      this.menuManagerFile.add(new Separator());
      this.menuManagerFile.add(this.actionPrintSettings);
      this.menuManagerFile.add(new Separator());
      this.menuManagerFile.add(this.actionNetworkSettings);
      this.menuManagerFile.add(new Separator());
      this.menuManagerFile.add(this.actionCloseSession);
      this.menuManagerFile.add(exitAction);
      Action adminProductsAction = new Action("&Administrador de artículos"){
         @Override
         public void run(){
            ProductsManager dialog = ProductsManager.getInstance();
            dialog.setBlockOnOpen(true);
            dialog.open();
         }
      };
      this.menuManagerProducts = new MenuManager("Artículos");
      menuManager.add(this.menuManagerProducts);
      this.menuManagerProducts.add(adminProductsAction);
      this.menuManagerProducts.add(new Separator());
      this.menuManagerProducts.add(this.actionProductCategoriesManager);
      this.menuManagerProducts.add(new Separator());
      this.menuManagerProducts.add(this.actionQuickReposition);
      this.menuManagerProducts.add(new Separator());
      this.menuManagerProducts.add(this.actionImportProducts);
      this.menuManagerProducts.add(this.actionUpdatePrices);
      this.menuManagerOrders = new MenuManager("Ventas");
      menuManager.add(this.menuManagerOrders);
      this.menuManagerOrders.add(this.actionSalesManager);
      this.menuManagerOrders.add(new Separator());
      this.menuManagerOrders.add(this.actionNotasDeCredito);
      this.menuManagerOrders.add(new Separator());
      this.menuManagerOrders.add(this.actionBudgets);
      this.menuManagerPurchases = new MenuManager("Compras");
      menuManager.add(this.menuManagerPurchases);
      this.menuManagerPurchases.add(this.actionPurchasesManager);
      this.menuManagerCustomers = new MenuManager("Clientes");
      menuManager.add(this.menuManagerCustomers);
      this.menuManagerCustomers.add(this.action_7);
      this.menuManagerCustomers.add(new Separator());
      this.menuManagerCustomers.add(this.actionCustomerOnAccount);
      this.menuManagerCustomers.add(this.actionCustomerOnAccountSummary);
      this.menuManagerSuppliers = new MenuManager("Proveedores");
      menuManager.add(this.menuManagerSuppliers);
      this.menuManagerSuppliers.add(this.actionSuppliersManager);
      this.menuManagerSuppliers.add(new Separator());
      this.menuManagerSuppliers.add(this.actionSupplierOnAccount);
      this.menuManagerLists = new MenuManager("&Listados");
      menuManager.add(this.menuManagerLists);
      this.menuManagerLists.add(this.actionListOfPrices);
      this.menuManagerLists.add(new Separator());
      this.menuManagerLists.add(this.actionListOfReposition);
      this.menuManagerLists.add(new Separator());
      this.menuManagerLists.add(this.actionStockValuation);
      this.menuManagerReports = new MenuManager("&Informes");
      menuManager.add(this.menuManagerReports);
      this.menuManagerReports.add(this.actionReportSalesByDate);
      this.menuManagerReports.add(new Separator());
      this.menuManagerReports.add(this.actionReportSalesWithDetailByDate);
      this.menuManagerReports.add(this.actionReportSalesByCategory);
      this.menuManagerReports.add(this.actionReportSalesByProduct);
      this.menuManagerReports.add(this.actionReportSalesByPM);
      this.menuManagerTools = new MenuManager("Herramientas");
      menuManager.add(this.menuManagerTools);
      this.menuManagerTools.add(this.actionCreateLabelsByDate);
      this.menuManagerTools.add(this.actionCreateLabelsByCode);
      this.menuManagerTools.add(new Separator());
      this.menuManagerTools.add(this.actionCreateBarcodes);
      this.menuManagerCash = new MenuManager("Caja");
      menuManager.add(this.menuManagerCash);
      this.menuManagerCash.add(this.actionAddNewCashIncome);
      this.menuManagerCash.add(this.actionAddNewCashOutflow);
      this.menuManagerCash.add(new Separator());
      this.menuManagerCash.add(this.actionCashOperations);
      this.menuManagerCash.add(new Separator());
      this.menuManagerCash.add(this.actionOpenCash);
      this.menuManagerCash.add(this.actionCloseCash);
      this.menuManagerHelp = new MenuManager("Ayuda");
      menuManager.add(this.menuManagerHelp);
      this.menuManagerHelp.add(this.actionReactivateLicense);
      this.menuManagerHelp.add(new Separator());
      this.menuManagerHelp.add(this.actionAbout);
      return menuManager;
   }

   private void addProductToOrder(String barCode) {
      if (!"".equals(this.txtQty.getText()) && this.txtQty.getText().contains(",")) {
         this.txtQty.setText(this.txtQty.getText().replaceAll(",", "\\."));
      }

      WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
      if (workstationConfig.isModuleScaleActive() && this.getProductService().isScaleBarCode(barCode, workstationConfig.getScaleCode())) {
         this.addScaleProductToOrder(barCode);
      } else {
         this.addStandardProductToOrder(barCode);
      }

   }

   private void addScaleProductToOrder(String scaleBarCode) {
      WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
      Product product = this.getProductService().getProductByScaleBarCode(scaleBarCode, workstationConfig.getScaleProductCodeStart(), workstationConfig.getScaleProductCodeEnd());
      if (product != null) {
         if (product.isDiscontinued() && (!this.getWorkstationConfig().isTrialMode() || this.getWorkstationConfig().isTrialMode() && this.getWorkstationConfig().getTrialMaxProductsQty() > this.getProductService().getActiveProductsQty())) {
            product.setDiscontinued(false);
            product.setInWeb(true);
            this.getProductService().saveProduct(product);
         }

         PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
         ProductPrice productPrice = this.getProductService().retrieveOrCreateProductPriceForProduct(product, priceList);
         OrderLine orderLine = new OrderLine();
         orderLine.setOrder(this.getCurrentOrder());
         orderLine.setLineNumber(this.getCurrentOrder().getItemsQty() + 1);
         orderLine.setPrice(productPrice.getSellingPrice());
         orderLine.setVatValue(productPrice.getVat().getValue());
         orderLine.setProfitMargin(productPrice.getGrossMargin());
         orderLine.setInternalTax(productPrice.getInternalTax());
         orderLine.setProduct(product);
         orderLine.setInOffer(product.isInOffer());
         orderLine.setCostPrice(productPrice.getCostPrice());
         this.checkExpirationDate(orderLine);
         this.checkMinStock(orderLine);
         if (productPrice.getSellingPrice() == 0.0) {
            PricePrompt pp = new PricePrompt(this.getShell());
            pp.setTitle("Artículo sin precio (Lista: " + priceList.getName() + ")");
            pp.setCurrentOrder(this.getCurrentOrder());
            pp.setCurrentOrderLine(orderLine);
            pp.open();
            if ("OK".equalsIgnoreCase(pp.getAction())) {
               productPrice.setVat(pp.getVat());
               productPrice.updateSellingPrice(pp.getPrice(), new Date());
               orderLine.setCostPrice(productPrice.getCostPrice());
               orderLine.setVatValue(pp.getVat().getValue());
               if (pp.isUpdateProduct()) {
                  this.getProductService().saveProductPrice(productPrice);
               }

               orderLine.setPrice(pp.getPrice());
            }
         }

         if (orderLine.getPrice() > 0.0) {
            double orderLineQty;
            if (workstationConfig.isScaleLabelWeightType()) {
               orderLineQty = this.getProductService().getWeightFromScaleBarCode(scaleBarCode, workstationConfig.getScaleWeightStart(), workstationConfig.getScaleWeightEnd(), workstationConfig.getScaleWeightDecimalsStart(), workstationConfig.getScaleWeightDecimalsEnd());
               orderLine.setQty(orderLineQty);
            } else {
               orderLineQty = this.getProductService().getPriceFromScaleBarCode(scaleBarCode, workstationConfig.getScalePriceStart(), workstationConfig.getScalePriceEnd(), workstationConfig.getScalePriceDecimalsStart(), workstationConfig.getScalePriceDecimalsEnd());
              // double orderLineQty = orderLineQty / productPrice.getSellingPrice();
               orderLine.setQty(orderLineQty);
            }

            this.getCurrentOrder().addOrderLine(orderLine);
            this.getCurrentOrder().updateTotal();
            this.refreshCashRegister();
            this.cleanBarCodeAndQty();
         }
      } else {
         this.alert("No se encontró el artículo");
         this.cleanBarCodeAndQty();
      }

   }

   private void addStandardProductToOrder(String barCode) {
      Product product = this.getProductService().getProductByBarCode(barCode);
      if (product != null) {
         if (!product.isPerWeight() && this.txtQty.getText().contains(".")) {
            this.alert("El artículo no es fraccionable");
         } else {
            if (product.isDiscontinued() && (!this.getWorkstationConfig().isTrialMode() || this.getWorkstationConfig().isTrialMode() && this.getWorkstationConfig().getTrialMaxProductsQty() > this.getProductService().getActiveProductsQty())) {
               product.setDiscontinued(false);
               product.setInWeb(true);
               this.getProductService().saveProduct(product);
            }

            PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
            ProductPrice productPrice = this.getProductService().retrieveOrCreateProductPriceForProduct(product, priceList);
            boolean foundOrderLine = false;
            OrderLine orderLine = this.getCurrentOrder().getOrderLineByBarCodeAndPrice(product.getBarCode(), productPrice.getSellingPrice());
            if (orderLine == null) {
               orderLine = new OrderLine();
               orderLine.setOrder(this.getCurrentOrder());
               orderLine.setLineNumber(this.getCurrentOrder().getItemsQty() + 1);
               orderLine.setQty(Double.parseDouble(this.txtQty.getText()));
               orderLine.setProduct(product);
               orderLine.setInOffer(product.isInOffer());
            } else {
               orderLine.incQty(Double.parseDouble(this.txtQty.getText()));
               foundOrderLine = true;
            }

            this.checkExpirationDate(orderLine);
            this.checkMinStock(orderLine);
            if (productPrice.getSellingPrice() == 0.0) {
               PricePrompt pp = new PricePrompt(this.getShell());
               pp.setTitle("Artículo sin precio (Lista: " + priceList.getName() + ")");
               pp.setCurrentOrder(this.getCurrentOrder());
               pp.setCurrentOrderLine(orderLine);
               pp.open();
               if ("OK".equalsIgnoreCase(pp.getAction())) {
                  productPrice.setVat(pp.getVat());
                  productPrice.updateSellingPrice(pp.getPrice(), new Date());
                  orderLine.setCostPrice(productPrice.getCostPrice());
                  orderLine.setVatValue(pp.getVat().getValue());
                  if (pp.isUpdateProduct()) {
                     this.getProductService().saveProductPrice(productPrice);
                  }

                  orderLine.setPrice(pp.getPrice());
               }
            }

            if (!foundOrderLine) {
               orderLine.setPrice(productPrice.getSellingPrice());
               orderLine.setVatValue(productPrice.getVat().getValue());
               orderLine.setProfitMargin(productPrice.getGrossMargin());
               orderLine.setInternalTax(productPrice.getInternalTax());
               orderLine.setCostPrice(productPrice.getCostPrice());
            }

            if (orderLine.getPrice() > 0.0) {
               if (!foundOrderLine) {
                  this.getCurrentOrder().addOrderLine(orderLine);
               }

               this.getCurrentOrder().updateTotal();
               this.refreshCashRegister();
               this.cleanBarCodeAndQty();
            } else {
               this.txtBarCode.setText("");
            }
         }
      } else {
         this.alert("No se encontró el artículo");
         this.cleanBarCodeAndQty();
      }

   }

   private void checkMinStock(OrderLine orderLine) {
      try {
         if (orderLine.getProduct() != null && orderLine.getProduct().isStockControlEnabled() && orderLine.getProduct().getStock() - orderLine.getQty() <= orderLine.getProduct().getStockMin()) {
            this.alert("El producto alcanzará el stock mínimo luego de esta venta.");
         }
      } catch (Exception var3) {
         logger.error("Se produjo un error en Alerta de stock mínimo");
         logger.error(var3.getMessage());
         ////logger.error(var3);
      }

   }

   private void checkExpirationDate(OrderLine orderLine) {
      try {
         if (orderLine.getProduct() != null && orderLine.getProduct().isAlertExpActive() && orderLine.getProduct().getExpirationDate() != null) {
            int days = FVDateUtils.daysBetween(new Date(), orderLine.getProduct().getExpirationDate()) + 1;
            if (days <= 0) {
               this.alert("El producto está vencido ( Fecha de vencimiento: " + orderLine.getProduct().getExpirationDateToDisplay() + " )");
            } else if (days <= orderLine.getProduct().getAlertExpDays()) {
               this.alert("Faltan " + days + " días para la fecha de vencimiento ( " + orderLine.getProduct().getExpirationDateToDisplay() + " ) del producto.");
            }
         }
      } catch (Exception var3) {
         logger.error("Se produjo un error en Alerta de stock mínimo");
         logger.error(var3.getMessage());
        // //logger.error(var3);
      }

   }

   private void addProductCategoryToOrder(String categoryName) {
      ProductCategory category = this.getProductService().getProductCategoryByName(categoryName);
      if (category != null) {
         OrderLine orderLine = new OrderLine();
         orderLine.setOrder(this.getCurrentOrder());
         orderLine.setLineNumber(this.getCurrentOrder().getItemsQty() + 1);
         orderLine.setQty(1.0);
         orderLine.setCategory(category);
         PricePrompt pp = new PricePrompt(this.getShell());
         pp.setTitle(category.getName());
         pp.setCurrentOrder(this.getCurrentOrder());
         pp.setCurrentOrderLine(orderLine);
         pp.open();
         if ("OK".equalsIgnoreCase(pp.getAction())) {
            orderLine.setPrice(pp.getPrice());
            orderLine.setCostPrice(pp.getPrice());
            if (orderLine.getPrice() >= 0.0) {
               this.getCurrentOrder().addOrderLine(orderLine);
               if (orderLine.getCategory().getVat() != null) {
                  orderLine.setVatValue(orderLine.getCategory().getVat().getValue());
               }

               this.refreshCashRegister();
               this.cleanBarCodeAndQty();
            }
         }
      } else {
         this.cleanBarCodeAndQty();
      }

   }

   private void refreshTable() {
      if (this.getCurrentOrder().isInvoiceA()) {
         this.refreshTableForInvoiceA();
      } else {
         this.refreshTableForOtherInvoice();
      }

   }

   private void refreshTableForInvoiceA() {
      this.table.removeAll();
      Iterator var2 = this.getCurrentOrder().getOrderLines().iterator();

      while(var2.hasNext()) {
         OrderLine orderLine = (OrderLine)var2.next();
         this.addOrderLineToTableForInvoiceA(orderLine);
      }

      try {
         if (this.getCurrentOrder().getDiscountPercent() != 0.0 || this.getCurrentOrder().getDiscountAmount() != 0.0 || this.getCurrentOrder().getSurchargePercent() != 0.0 || this.getCurrentOrder().getSurchargeAmount() != 0.0) {
            int lineNumber = this.getCurrentOrder().getOrderLines().size() + 1;
            TableItem item = new TableItem(this.table, 0);
            item.setText(0, "");
            item.setText(1, "");
            item.setText(2, "Dto./Recargo");
            item.setText(3, "");
            item.setText(4, "");
            item.setText(5, "21%");
            item.setText(6, this.getCurrentOrder().getImpIVADtoRecargoToDisplay());
            item.setText(7, this.getCurrentOrder().getNetoDtoRecargoToDisplay());
            if (lineNumber % 2 == 0) {
               item.setBackground(this.themeRowEven);
            } else {
               item.setBackground(this.themeRowOdd);
            }
         }
      } catch (Exception var3) {
         logger.error("Error al mostrar Dto./Recargo en Factura A");
      }

   }

   private void refreshTableForOtherInvoice() {
      this.table.removeAll();
      Iterator var2 = this.getCurrentOrder().getOrderLines().iterator();

      while(var2.hasNext()) {
         OrderLine orderLine = (OrderLine)var2.next();
         this.addOrderLineToTableForOtherInvoice(orderLine);
      }

   }

   private void refreshTotalPane() {
      if (this.getCurrentOrder().isInvoiceA()) {
         this.refreshTotalPaneForInvoiceA();
      } else {
         this.refreshTotalPaneForOtherInvoice();
      }

   }

   private void refreshTotalPaneForInvoiceA() {
      this.initTotalDisplayForInvoiceA();
      this.txtSubtotal.setText("$ " + this.getCurrentOrder().getImpNetoToDisplay());
      this.txtDiscount.setText("$ 0,00");
      this.txtVat1.setText("$ " + this.getCurrentOrder().getImporteIVA21ToDisplay());
      this.txtVat2.setText("$ " + this.getCurrentOrder().getImporteIVA105ToDisplay());
      this.txtTotal.setText("$ " + this.getCurrentOrder().getTotalToDisplay2());
      this.updateDiscountSurchargeInfoForInvoiceA();
   }

   private void refreshTotalPaneForOtherInvoice() {
      this.initTotalDisplayForOtherInvoice();
      this.txtSubtotal.setText("$ " + this.getCurrentOrder().getSubtotalToDisplay());
      this.txtDiscount.setText("$ " + this.getCurrentOrder().getDiscountSurchargeToDisplay2());
      this.txtTotal.setText("$ " + this.getCurrentOrder().getTotalToDisplay2());
      this.updateDiscountSurchargeInfo();
   }

   private void updateDiscountSurchargeInfoForInvoiceA() {
      this.txtDiscountDisplay.setText("");
   }

   private void updateDiscountSurchargeInfo() {
      String info1 = "";
      if (this.getCurrentOrder().getDiscountPercent() != 0.0 && this.getCurrentOrder().getDiscountAmount() != 0.0) {
         info1 = "-" + this.getCurrentOrder().getDiscountPercentToDisplay() + "%  -$" + this.getCurrentOrder().getDiscountAmountToDisplay();
      } else if (this.getCurrentOrder().getDiscountPercent() != 0.0) {
         info1 = "-" + this.getCurrentOrder().getDiscountPercentToDisplay() + "%";
      } else if (this.getCurrentOrder().getDiscountAmount() != 0.0) {
         info1 = "-$" + this.getCurrentOrder().getDiscountAmountToDisplay();
      } else {
         info1 = "";
      }

      String info2 = "";
      if (this.getCurrentOrder().getSurchargePercent() != 0.0 && this.getCurrentOrder().getSurchargeAmount() != 0.0) {
         info2 = "+" + this.getCurrentOrder().getSurchargePercentToDisplay() + "%  +$" + this.getCurrentOrder().getSurchargeAmountToDisplay();
      } else if (this.getCurrentOrder().getSurchargePercent() != 0.0) {
         info2 = "+" + this.getCurrentOrder().getSurchargePercentToDisplay() + "%";
      } else if (this.getCurrentOrder().getSurchargeAmount() != 0.0) {
         info2 = "+$" + this.getCurrentOrder().getSurchargeAmountToDisplay();
      } else {
         info2 = "";
      }

      this.txtDiscountDisplay.setText(info1 + "  " + info2);
   }

   private void cleanBarCodeAndQty() {
      this.txtBarCode.setText("");
      this.txtQty.setText("1");
      this.txtBarCode.setFocus();
   }

   private void addOrderLineToTableForInvoiceA(OrderLine orderLine) {
      TableItem item = new TableItem(this.table, 0);
      item.setText(0, String.valueOf(orderLine.getLineNumber()));
      if (orderLine.getProduct() != null) {
         item.setText(1, orderLine.getProduct().getBarCode());
         item.setText(2, orderLine.getProduct().getDescription().toUpperCase());
      } else {
         item.setText(1, orderLine.getCategory().getCode());
         item.setText(2, orderLine.getCategory().getName().toUpperCase());
      }

      item.setText(3, orderLine.getQtyToDisplay());
      item.setText(4, orderLine.getNetPriceToDisplay());
      item.setText(5, orderLine.getVatValueToDisplay());
      item.setText(6, orderLine.getVatTotalToDisplay());
      item.setText(7, orderLine.getNetSubtotalToDisplay());
      if (orderLine.getLineNumber() % 2 == 0) {
         item.setBackground(this.themeRowEven);
      } else {
         item.setBackground(this.themeRowOdd);
      }

   }

   private void addOrderLineToTableForOtherInvoice(OrderLine orderLine) {
      TableItem item = new TableItem(this.table, 0);
      item.setText(0, String.valueOf(orderLine.getLineNumber()));
      if (orderLine.getProduct() != null) {
         item.setText(1, orderLine.getProduct().getBarCode());
         item.setText(2, orderLine.getProduct().getDescription().toUpperCase());
      } else {
         item.setText(1, orderLine.getCategory().getCode());
         item.setText(2, orderLine.getCategory().getName().toUpperCase());
      }

      item.setText(3, orderLine.getQtyToDisplay());
      item.setText(4, orderLine.getPriceToDisplay());
      int idx2 = 5;
      if (this.getAppConfig().isResponsableInscripto()) {
         item.setText(idx2, orderLine.getVatValueToDisplay());
         ++idx2;
      }

      item.setText(idx2, orderLine.getSubtotalToDisplay());
      if (orderLine.getLineNumber() % 2 == 0) {
         item.setBackground(this.themeRowEven);
      } else {
         item.setBackground(this.themeRowOdd);
      }

   }

   private void removeProduct() {
      int idx = this.table.getSelectionIndex();
      if (idx >= 0) {
         if ("".equals(this.table.getItem(idx).getText(1)) && "Dto./Recargo".equalsIgnoreCase(this.table.getItem(idx).getText(2))) {
            this.alert("Utiliza los botones Descuento o Recargo para quitarlo");
         } else {
            this.getCurrentOrder().removeOrderLine(idx);
            this.refreshCashRegister();
            this.refreshTotalPane();
         }
      } else {
         this.alert("Selecciona un artículo");
      }

   }

   private void changePrice() {
      if (this.getCashier() == null || !this.getCashier().isAdmin() && !this.getCashier().isAllowModifyPrice()) {
         this.alert(this.getMsgPrivilegeRequired());
      } else {
         this.processChangePrice();
      }

   }

   private void processChangePrice() {
      int idx = this.table.getSelectionIndex();
      if (idx >= 0) {
         OrderLine orderLine = this.getCurrentOrder().getOrderLineByLineNumber(idx);
         PricePrompt pp = new PricePrompt(this.getShell());
         pp.setTitle("Cambiar precio (Lista: " + this.getCurrentPriceList().getName() + ")");
         pp.setCurrentOrder(this.getCurrentOrder());
         pp.setCurrentOrderLine(orderLine);
         pp.open();
         if ("OK".equalsIgnoreCase(pp.getAction())) {
            Product product = orderLine.getProduct();
            if (product != null) {
               PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
               ProductPrice productPrice = this.getProductService().retrieveOrCreateProductPriceForProduct(product, priceList);
               productPrice.setVat(pp.getVat());
               productPrice.updateSellingPrice(pp.getPrice(), new Date());
               orderLine.setCostPrice(productPrice.getCostPrice());
               orderLine.setVatValue(pp.getVat().getValue());
               if (pp.isUpdateProduct()) {
                  this.getProductService().saveProductPrice(productPrice);
               }
            }

            orderLine.setPrice(pp.getPrice());
            this.getCurrentOrder().updateTotal();
            this.refreshCashRegister();
         }
      } else {
         this.alert("Selecciona un artículo");
      }

   }

   private void changeQty() {
      int idx = this.table.getSelectionIndex();
      if (idx >= 0) {
         OrderLine orderLine = this.getCurrentOrder().getOrderLineByLineNumber(idx);
         QtyPrompt pp = new QtyPrompt(this.getShell());
         pp.setCurrentOrder(this.getCurrentOrder());
         pp.setCurrentOrderLine(orderLine);
         pp.open();
         if ("OK".equalsIgnoreCase(pp.getAction())) {
            if (pp.getQty() == 0.0) {
               this.removeProduct();
            } else {
               orderLine.setQty(pp.getQty());
               this.getCurrentOrder().updateTotal();
               this.refreshCashRegister();
               this.checkMinStock(orderLine);
            }
         }
      } else {
         this.alert("Selecciona un artículo");
      }

   }

   private PriceList getCurrentPriceList() {
      return this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
   }

   private void searchProduct() {
      SearchProduct dialog = new SearchProduct(this.getShell());
      dialog.setPriceList(this.getCurrentPriceList());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.addProductToOrder(dialog.getProduct().getBarCode());
         this.refreshCashRegister();
      }

   }

   private void checkout() {
      if (this.getCashier() == null || !this.getCashier().isAdmin() && !this.getCashier().isAllowCreateOrder()) {
         this.alert(this.getMsgPrivilegeRequired());
      } else {
         this.processCheckout();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if (valid) {
         try {
            Date today = new Date();
            Date saleDate = this.buildDateFromInput(this.dateTimeSaleDate);
            if (!DateUtils.isSameDay(today, saleDate) && today.before(saleDate)) {
               valid = false;
               this.alert("La fecha de venta debe ser menor o igual a la fecha actual");
            }
         } catch (Exception var4) {
            valid = false;
            this.alert("La fecha de venta no es válida");
         }
      }

      if (valid) {
         Integer saleNumber = this.getIntegerValueFromText(this.txtReceiptNumber);
         if (saleNumber == null || saleNumber <= 0) {
            valid = false;
            this.alert("El nro. de comprobante no es válido");
         }
      }

      return valid;
   }

   private void processCheckout() {
      this.setHotkeysEnabled(false);
      boolean canSaveOrder = true;
      if (!this.validateFields()) {
         canSaveOrder = false;
      }

      if (canSaveOrder) {
         Date today = new Date();
         this.getCurrentOrder().setSaleDate((Date)null);

         try {
            Date saleDate = this.buildDateFromInput(this.dateTimeSaleDate);
            if (!DateUtils.isSameDay(today, saleDate) && today.after(saleDate)) {
               this.getCurrentOrder().setSaleDate(saleDate);
               this.getCurrentOrder().setCustomSaleDate(true);
            }
         } catch (Exception var8) {
         }

         this.getCurrentOrder().setReceiptNumber(this.txtReceiptNumber.getText().trim());
         this.getCurrentOrder().setObservations(this.txtObservations.getText().trim());
      }

      if (canSaveOrder && this.getWorkstationConfig().isTrialMode()) {
         int ordersQty = this.getOrderService().getTotalOrdersQty();
         if (ordersQty >= this.getWorkstationConfig().getTrialMaxOrdersQty()) {
            this.alert("Ha alcanzado el máximo de ventas permitidas en la versión de evaluación.");
            canSaveOrder = false;
         }
      }

      if (canSaveOrder && this.getCurrentOrder().getTotal() > 0.0) {
         PaymentPrompt pp = new PaymentPrompt(this.getShell());
         pp.setCurrentOrder(this.getCurrentOrder());
         pp.setAction("");
         pp.open();
         if ("OK".equalsIgnoreCase(pp.getAction())) {
            Order previousOrder = this.getCurrentOrder();

            try {
               Date creationDate = new Date();
               this.getCurrentOrder().setStatus("COMPLETED");
               this.getCurrentOrder().setCreationDate(creationDate);
               this.getCurrentOrder().setPriceList(this.getCurrentPriceList());
               this.getOrderService().saveOrder(this.getCurrentOrder());
               if (this.getCurrentOrder().getNetCashAmount() > 0.0) {
                  this.getCashService().saveNewCashOperationForOrder(this.getCurrentOrder());
                  if (this.getCashService().mustUpdateCashAmount(this.getWorkstationConfig(), this.getCurrentOrder().getSaleDate())) {
                     this.getAppConfigService().incCashAmount(this.getWorkstationConfig(), this.getCurrentOrder().getNetCashAmount());
                     this.updatedWorkstationConfig();
                  }
               }

               if (this.getCurrentOrder().getNetOnAccountAmount() > 0.0) {
                  this.getCustomerService().saveNewCustomerOnAccountOperationForOrder(this.getCurrentOrder());
               }

               this.getProductService().updateLastSaleDateForOrder(this.getCurrentOrder());
               this.getProductService().updateStockForOrder(this.getCurrentOrder());
               this.appConfig.setSaleStartNumber(this.appConfig.getSaleStartNumber() + 1);
               this.getAppConfigService().saveAppConfig(this.appConfig);
               this.getAppConfigService().backupDB("data2");
            } catch (Exception var7) {
               logger.error("Error al guardar la venta");
               logger.error("Message: " + var7.getMessage());
               logger.error("Exception: " + var7);
            }

            FVMediaUtils.playSound("checkout.wav");
            if (this.getWorkstationConfig().isOpenConfirmFacturaElectronica() && this.getAppConfig().isAfipEnabledFacturaElectronica()) {
               FacturaElectronicaPrompt fePrompt = new FacturaElectronicaPrompt(this.getShell());
               fePrompt.setAction("");
               fePrompt.setBlockOnOpen(true);
               fePrompt.open();
               if ("OK".equalsIgnoreCase(fePrompt.getAction())) {
                  this.openFacturaElectronicaDialog(this.getCurrentOrder());
               }
            }

            if (this.getWorkstationConfig().isOpenConfirmPrintOrder()) {
               PrintReceiptPrompt printReceiptPrompt = new PrintReceiptPrompt(this.getShell());
               printReceiptPrompt.setAction("");
               printReceiptPrompt.setBlockOnOpen(true);
               printReceiptPrompt.open();
               if ("OK".equalsIgnoreCase(printReceiptPrompt.getAction())) {
                  try {
                     this.printOrder(this.getCurrentOrder());
                  } catch (Exception var6) {
                     logger.error("Se produjo un error al imprimir el comprobante");
                     logger.error(var6.getMessage());
                     logger.error(var6.toString());
                  }
               }
            }

            this.initNewOrder();
            this.refreshCashRegisterAfterPayment(previousOrder);
         }
      }

      this.setHotkeysEnabled(true);
   }

   private void openFacturaElectronicaDialog(Order order) {
      if (!this.getAppConfig().isAfipEnabledFacturaElectronica()) {
         this.alert("La Factura Electrónica no está habilitada");
      } else if (!this.getWorkstationConfig().isValidCodFactElect()) {
         this.alert("El Código de Factura Electrónica no es válido");
      } else if (!this.getAppConfig().isMonotributo() && !this.getAppConfig().isResponsableInscripto()) {
         this.alert("Verifique la condición de IVA en el menú Archivo->Configuración->General");
      } else {
         AddNewFacturaElectronicaAfip dialog = new AddNewFacturaElectronicaAfip(this.getShell());
         dialog.setOrder(order);
         dialog.open();
      }

   }

   private void printOrder(Order order) {
      this.getOrderService().printOrder(order, this.getAppConfig(), this.getWorkstationConfig(), this.getShell());
   }

   private void createLabelsByDate() {
      CreateLabelsByDate dialog = new CreateLabelsByDate(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            CreateLabelsGenerator generatorDialog = new CreateLabelsGenerator(this.getShell());
            generatorDialog.setBlockOnOpen(true);
            generatorDialog.setPriceList(dialog.getPriceList());
            generatorDialog.setProducts(dialog.getProducts());
            generatorDialog.setStartDate(dialog.getStartDate());
            generatorDialog.setEndDate(dialog.getEndDate());
            generatorDialog.open();
         } catch (Exception var3) {
         }
      }

   }

   private void createLabelsByCode() {
      CreateLabelsByCode dialog = new CreateLabelsByCode(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            CreateLabelsGenerator generatorDialog = new CreateLabelsGenerator(this.getShell());
            generatorDialog.setBlockOnOpen(true);
            generatorDialog.setPriceList(dialog.getPriceList());
            generatorDialog.setProducts(dialog.getProducts());
            generatorDialog.setStartDate((Date)null);
            generatorDialog.setEndDate((Date)null);
            generatorDialog.open();
         } catch (Exception var3) {
         }
      }

   }

   private void createBarcodes() {
      CreateBarcodes dialog = new CreateBarcodes(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            CreateBarcodesGenerator generatorDialog = new CreateBarcodesGenerator(this.getShell());
            generatorDialog.setBlockOnOpen(true);
            generatorDialog.setProducts(dialog.getProducts());
            generatorDialog.open();
         } catch (Exception var3) {
         }
      }

   }
// INFORMES METODOS
   private void createReportSalesByPaymentMethods() {
      ReportSalesByPaymentMethods dialog = new ReportSalesByPaymentMethods(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            ReportSalesByPaymentMethodsGenerator generatorDialog = new ReportSalesByPaymentMethodsGenerator(this.getShell());
            generatorDialog.setBlockOnOpen(true);
            generatorDialog.setOrders(dialog.getOrders());
            generatorDialog.setStartDate(dialog.getStartDate());
            generatorDialog.setEndDate(dialog.getEndDate());
            generatorDialog.open();
         } catch (Exception var3) {
         }
      }

   }

   private void createReportSalesByDate() {
      ReportSalesByDate dialog = new ReportSalesByDate(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            ReportSalesByDateGenerator generatorDialog = new ReportSalesByDateGenerator(this.getShell());
            generatorDialog.setBlockOnOpen(true);
            generatorDialog.setOrders(dialog.getOrders());
            generatorDialog.setStartDate(dialog.getStartDate());
            generatorDialog.setEndDate(dialog.getEndDate());
            generatorDialog.setConCAE(dialog.getConCAE());
            generatorDialog.open();
         } catch (Exception var3) {
         }
      }

   }

   private void createReportSalesWithDetailByDate() {
      ReportSalesWithDetailByDate dialog = new ReportSalesWithDetailByDate(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            ReportSalesWithDetailByDateGenerator generatorDialog = new ReportSalesWithDetailByDateGenerator(this.getShell());
            generatorDialog.setBlockOnOpen(true);
            generatorDialog.setOrders(dialog.getOrders());
            generatorDialog.setStartDate(dialog.getStartDate());
            generatorDialog.setEndDate(dialog.getEndDate());
            generatorDialog.open();
         } catch (Exception var3) {
         }
      }

   }

   private void createReportSalesByCategory() {
      ReportSalesByCategory dialog = new ReportSalesByCategory(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            ReportSalesByCategoryGenerator generatorDialog = new ReportSalesByCategoryGenerator(this.getShell());
            generatorDialog.setBlockOnOpen(true);
            generatorDialog.setOrders(dialog.getOrders());
            generatorDialog.setStartDate(dialog.getStartDate());
            generatorDialog.setEndDate(dialog.getEndDate());
            generatorDialog.open();
         } catch (Exception var3) {
         }
      }

   }

   private void createReportSalesByProduct() {
      ReportSalesByProduct dialog = new ReportSalesByProduct(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            ReportSalesByProductGenerator generatorDialog = new ReportSalesByProductGenerator(this.getShell());
            generatorDialog.setBlockOnOpen(true);
            generatorDialog.setOrders(dialog.getOrders());
            generatorDialog.setStartDate(dialog.getStartDate());
            generatorDialog.setEndDate(dialog.getEndDate());
            generatorDialog.open();
         } catch (Exception var3) {
         }
      }

   }
//FIN INFORMES METODOS

   private void changeCustomer() {
      ChangeCustomer dialog = new ChangeCustomer(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.getCurrentOrder().setCustomer(dialog.getCustomer());
         this.updatedCustomer();
      }

   }

   private void checkPrice() {
      this.setHotkeysEnabled(false);
      CheckPriceDialog dialog = new CheckPriceDialog(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         CheckPrice infoDialog = new CheckPrice(this.getShell());
         infoDialog.setProduct(dialog.getProduct());
         infoDialog.setBlockOnOpen(true);
         infoDialog.setPriceList(dialog.getPriceList());
         infoDialog.setTitle("Ver precio (Lista: " + dialog.getPriceList().getName() + ")");
         infoDialog.open();
      }

      this.txtBarCode.setFocus();
      this.setHotkeysEnabled(true);
   }

   private void cashCategoryPrompt() {
      this.setHotkeysEnabled(false);
      CashCategoryPrompt dialog = new CashCategoryPrompt(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         if (dialog.getCategoryNumber() == 1 && this.getWorkstationConfig().getCashDept1() != null) {
            this.addProductCategoryToOrder(this.getWorkstationConfig().getCashDept1().getName());
         } else if (dialog.getCategoryNumber() == 2 && this.getWorkstationConfig().getCashDept2() != null) {
            this.addProductCategoryToOrder(this.getWorkstationConfig().getCashDept2().getName());
         } else if (dialog.getCategoryNumber() == 3 && this.getWorkstationConfig().getCashDept3() != null) {
            this.addProductCategoryToOrder(this.getWorkstationConfig().getCashDept3().getName());
         } else if (dialog.getCategoryNumber() == 4 && this.getWorkstationConfig().getCashDept4() != null) {
            this.addProductCategoryToOrder(this.getWorkstationConfig().getCashDept4().getName());
         } else if (dialog.getCategoryNumber() == 5 && this.getWorkstationConfig().getCashDept5() != null) {
            this.addProductCategoryToOrder(this.getWorkstationConfig().getCashDept5().getName());
         } else if (dialog.getCategoryNumber() == 6 && this.getWorkstationConfig().getCashDept6() != null) {
            this.addProductCategoryToOrder(this.getWorkstationConfig().getCashDept6().getName());
         } else if (dialog.getCategoryNumber() == 7 && this.getWorkstationConfig().getCashDept7() != null) {
            this.addProductCategoryToOrder(this.getWorkstationConfig().getCashDept7().getName());
         } else if (dialog.getCategoryNumber() == 8 && this.getWorkstationConfig().getCashDept8() != null) {
            this.addProductCategoryToOrder(this.getWorkstationConfig().getCashDept8().getName());
         }
      }

      this.setHotkeysEnabled(true);
   }

   private void updatedCustomer() {
      this.getCurrentOrder().setVatCondition(this.getCurrentOrder().getCustomer().getVatCondition());
      Customer customer = this.getCurrentOrder().getCustomer();
      this.txtCustomerFullName.setText(customer.getFullName());
      this.txtAddress.setText(customer.getAddressForOrder(40));
      this.txtCuit.setText(customer.getCuitForOrder());
      this.txtPhone.setText(customer.getPhoneForOrder());
      this.txtCity.setText(customer.getCityForOrder());
      this.txtVatCondition.setText(customer.getVatCondition().getName());
      if (customer.isBusinessCustomer()) {
         this.lblCustomerName.setText("Razón Social");
         this.lblCuit.setVisible(true);
         this.txtCuit.setVisible(true);
      } else {
         this.lblCustomerName.setText("Sr/a.");
         this.lblCuit.setVisible(false);
         this.txtCuit.setVisible(false);
      }

   }

   public void updatedReceiptType() {
      String receiptTypeName = this.comboReceiptTypes.getText();
      this.getCurrentOrder().setReceiptType(this.getAppConfigService().getReceiptTypeByName(receiptTypeName));
      if (this.getCurrentOrder().isInvoiceA()) {
         this.canvasInvoiceType.setVisible(true);
         this.initTotalDisplayForInvoiceA();
         this.initTableForInvoiceA();
      } else {
         this.canvasInvoiceType.setVisible(false);
         this.initTotalDisplayForOtherInvoice();
         this.initTableForOtherInvoice();
      }

      this.refreshCashRegister();
      this.tableContainer.layout();
   }

   public boolean isFromLogin() {
      return this.fromLogin;
   }

   public void setFromLogin(boolean fromLogin) {
      this.fromLogin = fromLogin;
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

   public void refreshCustomer() {
      if (this.getCurrentOrder() != null) {
         Customer customer = this.getCustomerService().getCustomer(this.getCurrentOrder().getCustomer().getId());
         this.getCurrentOrder().setCustomer(customer);
         this.updatedCustomer();
      }

   }

   public void updatedWorkstationConfig() {
      this.setWorkstationConfig(super.getWorkstationConfig());
   }

   private void listOfPricesExcel() {
      PriceListPrompt dialog = new PriceListPrompt(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.setTitle("Generar lista de precios");
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            ListOfPricesExcelGenerator dialog2 = new ListOfPricesExcelGenerator(this.getShell());
            dialog2.setBlockOnOpen(true);
            dialog2.setPriceList(dialog.getPriceList());
            dialog2.open();
         } catch (Exception var3) {
         }
      }

   }

   private void editNetworkSettings() {
      EditNetworkSettingsAlert dialog = new EditNetworkSettingsAlert(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.open();
   }

   protected boolean isFullScreenMode() {
      return this.fullScreenMode;
   }

   protected void setFullScreenMode(boolean fullScreenMode) {
      this.fullScreenMode = fullScreenMode;
   }

   protected boolean isHotkeysEnabled() {
      return this.hotkeysEnabled;
   }

   protected void setHotkeysEnabled(boolean hotkeysEnabled) {
      this.hotkeysEnabled = hotkeysEnabled;
   }

   private void initDisabledMenus() {
      if (!this.getCashier().isAdmin()) {
         this.actionSettings.setEnabled(false);
         this.actionPrintSettings.setEnabled(false);
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModuleProducts()) {
         this.menuManagerProducts.dispose();
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModuleOrders()) {
         this.menuManagerOrders.dispose();
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModulePurchases()) {
         this.menuManagerPurchases.dispose();
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModuleCustomers()) {
         this.menuManagerCustomers.dispose();
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModuleSuppliers()) {
         this.menuManagerSuppliers.dispose();
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModuleLists()) {
         this.menuManagerLists.dispose();
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModuleReports()) {
         this.menuManagerReports.dispose();
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModuleTools()) {
         this.menuManagerTools.dispose();
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowCreateIncome()) {
         this.actionAddNewCashIncome.setEnabled(false);
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowCreateOutflow()) {
         this.actionAddNewCashOutflow.setEnabled(false);
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowModuleCash()) {
         this.actionCashOperations.setEnabled(false);
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowOpenCash()) {
         this.actionOpenCash.setEnabled(false);
      }

      if (!this.getCashier().isAdmin() && !this.getCashier().isAllowCloseCash()) {
         this.actionCloseCash.setEnabled(false);
      }

      if (!this.getWorkstationConfig().isCashOpened()) {
         this.actionAddNewCashIncome.setEnabled(false);
         this.actionAddNewCashOutflow.setEnabled(false);
         this.actionCloseCash.setEnabled(false);
      } else {
         this.actionOpenCash.setEnabled(false);
      }

      if (this.getWorkstationConfig().isTrialMode()) {
         this.actionCreateBackup.setEnabled(false);
      }
   }
}