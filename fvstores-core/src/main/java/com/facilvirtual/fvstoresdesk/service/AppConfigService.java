package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.AppConfigDao;
import com.facilvirtual.fvstoresdesk.dao.PriceListDao;
import com.facilvirtual.fvstoresdesk.dao.ReceiptTypeDao;
import com.facilvirtual.fvstoresdesk.dao.SaleConditionDao;
import com.facilvirtual.fvstoresdesk.dao.WorkstationConfigDao;
import com.facilvirtual.fvstoresdesk.model.AfipConfig;
import com.facilvirtual.fvstoresdesk.model.AfipProp;
import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.CreditCard;
import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.DebitCard;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.model.ReceiptType;
import com.facilvirtual.fvstoresdesk.model.SaleCondition;
import com.facilvirtual.fvstoresdesk.model.Vat;
import com.facilvirtual.fvstoresdesk.model.VatCondition;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import com.facilvirtual.fvstoresdesk.util.AfipUtils;
import com.facilvirtual.fvstoresdesk.util.DiskUtils;
import com.facilvirtual.fvstoresdesk.util.FVFileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DependsOnDatabaseInitialization
@Service
@Transactional
public class AppConfigService {
   protected static Logger logger = LoggerFactory.getLogger(AppConfigService.class);
   @Autowired
   private AppConfigDao appConfigDao;
   @Autowired
   private WorkstationConfigDao workstationConfigDao;
   @Autowired
   private ReceiptTypeDao receiptTypeDao;
   @Autowired
   private SaleConditionDao saleConditionDao;
   @Autowired
   private PriceListDao priceListDao;
   @Autowired
   private AfipConfig afipConfig;

   public AppConfigService() {
   }

   public AfipConfig getAfipConfig() {
      return this.afipConfig;
   }

   public void setAfipConfig(AfipConfig afipConfig) {
      this.afipConfig = afipConfig;
   }

   public void saveAppConfig(AppConfig appConfig) {
      this.appConfigDao.saveAppConfig(appConfig);
   }

   public AppConfig getAppConfig() {
      return this.appConfigDao.getAppConfig(new Long(1L));
   }

   public AfipProp getAfipProp() {
      AfipProp afipProp = new AfipProp();
      Properties prop = new Properties();
      InputStream input = null;

      try {
         input = new FileInputStream("C:/facilvirtual/config/afip.properties");
         prop.load(input);
         afipProp.setAfipToken(prop.getProperty("afip.token"));
         afipProp.setAfipSign(prop.getProperty("afip.sign"));
      } catch (IOException var13) {
         var13.printStackTrace();
      } finally {
         if (input != null) {
            try {
               input.close();
            } catch (IOException var12) {
               logger.error(var12.getMessage());
               logger.error(var12.toString());
            }
         }

      }

      return afipProp;
   }

   public boolean createBackup(String filename) {
      return this.appConfigDao.createBackup(filename);
   }

   public void deactivateLicense() {
      logger.info("Desactivando licencia");
      WorkstationConfig workstationConfig = this.getCurrentWorkstationConfig();
      workstationConfig.setLicenseActivationCode("");
      workstationConfig.setLicenseExpirationDate((Date)null);
      this.saveWorkstationConfig(workstationConfig);
   }

   public void saveWorkstationConfig(WorkstationConfig workstationConfig) {
      this.workstationConfigDao.saveWorkstationConfig(workstationConfig);
   }

   public WorkstationConfig getCurrentWorkstationConfig() {
      return this.getWorkstationConfigByInstallationCode(this.getCurrentInstallationCode());
   }

   public String getCurrentInstallationCode() {
      String serialNumber = "";

      try {
         int maxLength = 10;
         serialNumber = DiskUtils.getSerialNumber("C");
         if (serialNumber.length() > maxLength) {
            serialNumber = serialNumber.substring(0, maxLength);
         } else if (serialNumber.length() < maxLength) {
            while(serialNumber.length() < maxLength) {
               serialNumber = "0" + serialNumber;
            }
         }

         if (serialNumber.length() == 0) {
            serialNumber = "0";
         }
      } catch (Exception var3) {
         serialNumber = "0";
      }

      return serialNumber.toUpperCase();
   }

   public String[] getAllFiscalPrinterBrands() {
      String[] printerBrands = new String[]{"EPSON", "HASAR"};
      return printerBrands;
   }

   public String[] getAllFiscalPrinterPorts() {
      String[] printerPorts = new String[]{"COM1", "COM2", "COM3", "COM4"};
      return printerPorts;
   }

   public int[] getAllFiscalPrinterVelocities() {
      int[] printerVelocities = new int[]{7200, 9600, 14400, 19200, 38400, 57600, 115200};
      return printerVelocities;
   }

   public void initializeApplication(String appName, String appModel, String appVersion) {
      logger.info("Inicio: Inicializando aplicación");
      logger.info("AppName: " + appName);
      logger.info("AppModel: " + appModel);
      logger.info("AppVersion: " + appVersion);
      this.loadCards();
      this.loadVatConditions();
      this.loadSaleConditions();
      this.loadReceiptTypes();
      this.loadCasualCustomer();
      this.loadAdministrator();
      this.loadAppConfig(appName, appModel, appVersion);
      this.loadWorkstationConfig(appModel);
      if (!"Kioscos".equalsIgnoreCase(appModel) && !"Supermercados".equalsIgnoreCase(appModel)) {
         this.loadVat();
         this.loadDefaultProductCategories();
         this.loadPriceLists();
      }

      if ("Kioscos".equalsIgnoreCase(appModel)) {
         this.initCashDeptForKioscos();
      }

      if ("Supermercados".equalsIgnoreCase(appModel)) {
         this.initCashDeptForSupermercados();
      }

      logger.info("Fin: Inicializando aplicación");
   }

   public ProductService getProductService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (ProductService)context.getBean("productService");
   }

   public void initCashDeptForKioscos() {
      WorkstationConfig workstationConfig = this.getCurrentWorkstationConfig();
      workstationConfig.setCashDept1(this.getProductService().getProductCategoryByName("Almacén"));
      workstationConfig.setCashDept2(this.getProductService().getProductCategoryByName("Fiambrería"));
      workstationConfig.setCashDept3(this.getProductService().getProductCategoryByName("Carnicería"));
      workstationConfig.setCashDept4(this.getProductService().getProductCategoryByName("Verdulería"));
      workstationConfig.setCashDept5(this.getProductService().getProductCategoryByName("Bazar"));
      workstationConfig.setCashDept6(this.getProductService().getProductCategoryByName("Limpieza"));
      workstationConfig.setCashDept7(this.getProductService().getProductCategoryByName("Panadería"));
      workstationConfig.setCashDept8(this.getProductService().getProductCategoryByName("Varios"));
      this.saveWorkstationConfig(workstationConfig);
   }

   public void initCashDeptForSupermercados() {
      WorkstationConfig workstationConfig = this.getCurrentWorkstationConfig();
      workstationConfig.setCashDept1(this.getProductService().getProductCategoryByName("Almacén"));
      workstationConfig.setCashDept2(this.getProductService().getProductCategoryByName("Fiambrería"));
      workstationConfig.setCashDept3(this.getProductService().getProductCategoryByName("Carnicería"));
      workstationConfig.setCashDept4(this.getProductService().getProductCategoryByName("Verdulería"));
      workstationConfig.setCashDept5(this.getProductService().getProductCategoryByName("Bazar"));
      workstationConfig.setCashDept6(this.getProductService().getProductCategoryByName("Limpieza"));
      workstationConfig.setCashDept7(this.getProductService().getProductCategoryByName("Panadería"));
      workstationConfig.setCashDept8(this.getProductService().getProductCategoryByName("Varios"));
      this.saveWorkstationConfig(workstationConfig);
   }

   public void loadAppConfig(String appName, String appModel, String appVersion) {
      VatCondition vatCondition = this.getOrderService().getVatConditionByName("Monotributo");
      AppConfig appConfig = new AppConfig();
      appConfig.setCompanyVatCondition(vatCondition);
      appConfig.setAppName(appName);
      appConfig.setAppModel(appModel);
      appConfig.setAppVersion(appVersion);
      this.saveAppConfig(appConfig);
   }

   public void loadWorkstationConfig(String appModel) {
      logger.info("Inicio: Inicializando workstation");
      WorkstationConfig workstationConfig = new WorkstationConfig();
      workstationConfig.setCashNumber(this.getWorkstationsQty() + 1);
      workstationConfig.setServer(true);
      workstationConfig.setInstallationDate(new Date());
      workstationConfig.setInstallationCode(this.getCurrentInstallationCode());
      if (workstationConfig.getCashNumber() == 1) {
         workstationConfig.setServer(true);
      } else {
         workstationConfig.setServer(false);
      }

      ReceiptType rt1;
      if ("Punto de Venta".equalsIgnoreCase(appModel)) {
         rt1 = this.getReceiptTypeByName("Factura C");
      } else {
         rt1 = this.getReceiptTypeByName("Ticket Fiscal");
      }

      ReceiptType rt2 = this.getReceiptTypeByName("Factura A");
      workstationConfig.setDefaultReceiptTypeForOrders(rt1);
      workstationConfig.setDefaultReceiptTypeForPurchases(rt2);
      this.saveWorkstationConfig(workstationConfig);
      logger.info("Fin: Inicializando workstation");
   }

   private int getWorkstationsQty() {
      return this.workstationConfigDao.getWorkstationsQty();
   }

   public void loadDefaultProductCategories() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      Vat vat1 = this.getOrderService().getVat(new Long(1L));
      ProductService productService = (ProductService)context.getBean("productService");
      productService.saveProductCategory(new ProductCategory("Sin Clasificar", vat1));
   }

   public void loadProductCategoriesForKioscos() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      ProductService productService = (ProductService)context.getBean("productService");
      Vat vat1 = this.getOrderService().getVat(new Long(1L));
      Vat vat2 = this.getOrderService().getVat(new Long(2L));
      productService.saveProductCategory(new ProductCategory("Alimentos Congelados", vat1));
      productService.saveProductCategory(new ProductCategory("Almacén", vat1));
      productService.saveProductCategory(new ProductCategory("Automotor", vat1));
      productService.saveProductCategory(new ProductCategory("Bazar", vat1));
      productService.saveProductCategory(new ProductCategory("Bebidas con Alcohol", vat1));
      productService.saveProductCategory(new ProductCategory("Bebidas sin Alcohol", vat1));
      productService.saveProductCategory(new ProductCategory("Blanco y Mantelería", vat1));
      productService.saveProductCategory(new ProductCategory("Carnicería", vat1));
      productService.saveProductCategory(new ProductCategory("Comidas Elaboradas", vat1));
      productService.saveProductCategory(new ProductCategory("Electrodomésticos", vat1));
      productService.saveProductCategory(new ProductCategory("Farmacia", vat1));
      productService.saveProductCategory(new ProductCategory("Ferretería", vat1));
      productService.saveProductCategory(new ProductCategory("Fiambrería", vat1));
      productService.saveProductCategory(new ProductCategory("Galletitas y Golosinas", vat1));
      productService.saveProductCategory(new ProductCategory("Jardinería", vat1));
      productService.saveProductCategory(new ProductCategory("Juguetería", vat1));
      productService.saveProductCategory(new ProductCategory("Kiosco", vat1));
      productService.saveProductCategory(new ProductCategory("Librería", vat1));
      productService.saveProductCategory(new ProductCategory("Limpieza", vat1));
      productService.saveProductCategory(new ProductCategory("Lácteos", vat1));
      productService.saveProductCategory(new ProductCategory("Mamás y Bebés", vat1));
      productService.saveProductCategory(new ProductCategory("Mascotas", vat1));
      productService.saveProductCategory(new ProductCategory("Panadería", vat2));
      productService.saveProductCategory(new ProductCategory("Pastas y Pizzas", vat1));
      productService.saveProductCategory(new ProductCategory("Perfumería", vat1));
      productService.saveProductCategory(new ProductCategory("Pescados y Mariscos", vat1));
      productService.saveProductCategory(new ProductCategory("Otros Rubros", vat1));
      productService.saveProductCategory(new ProductCategory("Varios", vat1));
      productService.saveProductCategory(new ProductCategory("Verdulería", vat1));
   }

   public void loadProductCategoriesForSupermercados() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      ProductService productService = (ProductService)context.getBean("productService");
      Vat vat1 = this.getOrderService().getVat(new Long(1L));
      Vat vat2 = this.getOrderService().getVat(new Long(2L));
      productService.saveProductCategory(new ProductCategory("Alimentos Congelados", vat1));
      productService.saveProductCategory(new ProductCategory("Almacén", vat1));
      productService.saveProductCategory(new ProductCategory("Automotor", vat1));
      productService.saveProductCategory(new ProductCategory("Bazar", vat1));
      productService.saveProductCategory(new ProductCategory("Bebidas con Alcohol", vat1));
      productService.saveProductCategory(new ProductCategory("Bebidas sin Alcohol", vat1));
      productService.saveProductCategory(new ProductCategory("Blanco y Mantelería", vat1));
      productService.saveProductCategory(new ProductCategory("Carnicería", vat1));
      productService.saveProductCategory(new ProductCategory("Comidas Elaboradas", vat1));
      productService.saveProductCategory(new ProductCategory("Electrodomésticos", vat1));
      productService.saveProductCategory(new ProductCategory("Farmacia", vat1));
      productService.saveProductCategory(new ProductCategory("Ferretería", vat1));
      productService.saveProductCategory(new ProductCategory("Fiambrería", vat1));
      productService.saveProductCategory(new ProductCategory("Galletitas y Golosinas", vat1));
      productService.saveProductCategory(new ProductCategory("Jardinería", vat1));
      productService.saveProductCategory(new ProductCategory("Juguetería", vat1));
      productService.saveProductCategory(new ProductCategory("Kiosco", vat1));
      productService.saveProductCategory(new ProductCategory("Librería", vat1));
      productService.saveProductCategory(new ProductCategory("Limpieza", vat1));
      productService.saveProductCategory(new ProductCategory("Lácteos", vat1));
      productService.saveProductCategory(new ProductCategory("Mamás y Bebés", vat1));
      productService.saveProductCategory(new ProductCategory("Mascotas", vat1));
      productService.saveProductCategory(new ProductCategory("Panadería", vat2));
      productService.saveProductCategory(new ProductCategory("Pastas y Pizzas", vat1));
      productService.saveProductCategory(new ProductCategory("Perfumería", vat1));
      productService.saveProductCategory(new ProductCategory("Pescados y Mariscos", vat1));
      productService.saveProductCategory(new ProductCategory("Otros Rubros", vat1));
      productService.saveProductCategory(new ProductCategory("Varios", vat1));
      productService.saveProductCategory(new ProductCategory("Verdulería", vat1));
   }

   public void loadCards() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      OrderService orderService = (OrderService)context.getBean("orderService");
      orderService.saveDebitCard(new DebitCard("Visa Débito"));
      orderService.saveDebitCard(new DebitCard("Maestro"));
      orderService.saveDebitCard(new DebitCard("Cabal 24"));
      orderService.saveCreditCard(new CreditCard("Visa Crédito"));
      orderService.saveCreditCard(new CreditCard("MasterCard"));
      orderService.saveCreditCard(new CreditCard("American Express"));
      orderService.saveCreditCard(new CreditCard("Tarjeta Naranja"));
      orderService.saveCreditCard(new CreditCard("Tarjeta Shopping"));
      orderService.saveCreditCard(new CreditCard("Argencard"));
      orderService.saveCreditCard(new CreditCard("Cabal"));
      orderService.saveCreditCard(new CreditCard("Cencosud"));
      orderService.saveCreditCard(new CreditCard("Nativa Nación (MC)"));
      orderService.saveCreditCard(new CreditCard("Patagonia 365"));
      orderService.saveCreditCard(new CreditCard("Italcred"));
   }

   public void loadVatConditions() {
      OrderService orderService = this.getOrderService();
      orderService.saveVatCondition(new VatCondition("Consumidor Final", "CF", false, true));
      orderService.saveVatCondition(new VatCondition("Responsable Inscripto", "RI", true, true));
      orderService.saveVatCondition(new VatCondition("Monotributo", "M", true, true));
      orderService.saveVatCondition(new VatCondition("No Responsable", "NR", true, true));
      orderService.saveVatCondition(new VatCondition("Exento", "EXE", true, true));
   }

   public void loadSaleConditions() {
      this.saveSaleCondition(new SaleCondition("Contado", "Contado", true, true));
      this.saveSaleCondition(new SaleCondition("Cuenta Corriente", "Cta. Cte.", true, true));
   }

   public void saveSaleCondition(SaleCondition saleCondition) {
      this.saleConditionDao.saveSaleCondition(saleCondition);
   }

   private OrderService getOrderService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      OrderService orderService = (OrderService)context.getBean("orderService");
      return orderService;
   }

   public void loadVat() {
      OrderService orderService = this.getOrderService();
      orderService.saveVat(new Vat("21%", 21.0));
      orderService.saveVat(new Vat("10,5%", 10.5));
      orderService.saveVat(new Vat("0%", 0.0));
   }

   public void loadCasualCustomer() {
      Date creationDate = new Date();
      Customer customer = new Customer();
      customer.setCreationDate(creationDate);
      customer.setLastUpdatedDate(creationDate);
      customer.setAccountType("HOME");
      customer.setVatCondition(this.getOrderService().getVatConditionByName("Consumidor Final"));
      customer.setFirstName("Cliente");
      customer.setLastName("Ocasional");
      customer.setAllowOnAccountOperations(false);
      this.getCustomerService().saveCustomer(customer);
   }

   public AccountService getAccountService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AccountService)context.getBean("accountService");
   }

   public CustomerService getCustomerService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (CustomerService)context.getBean("customerService");
   }

   public void loadAdministrator() {
      AccountService accountService = this.getAccountService();
      Date creationDate = new Date();
      Employee employee01 = this.getAccountService().getEmployee(new Long(1L));
      if (employee01 == null) {
         employee01 = new Employee();
         employee01.setCreationDate(creationDate);
         employee01.setLastUpdatedDate(creationDate);
         employee01.setUsername("Administrador");
         employee01.setPassword("admin");
         employee01.setAdmin(true);
         employee01.setFirstName("");
         employee01.setLastName("");
         employee01.setJobPosition("");
         employee01.setShift1(true);
         employee01.setShift2(true);
         employee01.setShift3(true);
         employee01.setAllowLogin(true);
      }

      accountService.saveEmployee(employee01);
   }

   public void loadReceiptTypes() {
      ReceiptType receiptA = new ReceiptType("Factura A", true, true);
      this.saveReceiptType(receiptA);
      ReceiptType receiptB = new ReceiptType("Factura B", true, true);
      this.saveReceiptType(receiptB);
      ReceiptType receiptC = new ReceiptType("Factura C", true, true);
      this.saveReceiptType(receiptC);
      this.saveReceiptType(new ReceiptType("Recibo", true, true, false));
      this.saveReceiptType(new ReceiptType("Remito", true, true, false));
      this.saveReceiptType(new ReceiptType("Ticket Fiscal", true, true));
      this.saveReceiptType(new ReceiptType("Ticket No Fiscal", true, true));
      this.saveReceiptType(new ReceiptType("Ticket Factura A", true, true));
      this.saveReceiptType(new ReceiptType("Ticket Factura B", true, true));
      this.saveReceiptType(new ReceiptType("Ticket Factura C", true, true));
      this.saveReceiptType(new ReceiptType("Nota de Crédito A", false, false, true));
      this.saveReceiptType(new ReceiptType("Nota de Crédito B", false, false, true));
      this.saveReceiptType(new ReceiptType("Nota de Crédito C", false, false, true));
   }

   public void loadPriceLists() {
      this.savePriceList(new PriceList("Minorista", true));
      this.savePriceList(new PriceList("Mayorista", true));
      this.savePriceList(new PriceList("Lista 3", false));
      this.savePriceList(new PriceList("Lista 4", false));
      this.savePriceList(new PriceList("Lista 5", false));
      this.savePriceList(new PriceList("Lista 6", false));
      this.savePriceList(new PriceList("Lista 7", false));
      this.savePriceList(new PriceList("Lista 8", false));
      this.savePriceList(new PriceList("Lista 9", false));
      this.savePriceList(new PriceList("Lista 10", false));
   }

   public WorkstationConfig getWorkstationConfigByInstallationCode(String currentInstallationCode) {
      return this.workstationConfigDao.getWorkstationConfigByInstallationCode(currentInstallationCode);
   }

   public void incCashAmount(WorkstationConfig workstationConfig, double amount) {
      workstationConfig.setCashAmount(workstationConfig.getCashAmount() + amount);
      this.saveWorkstationConfig(workstationConfig);
   }

   public void decCashAmount(WorkstationConfig workstationConfig, double amount) {
      workstationConfig.setCashAmount(workstationConfig.getCashAmount() - amount);
      this.saveWorkstationConfig(workstationConfig);
   }

   public void resetCashAmount(WorkstationConfig workstationConfig) {
      workstationConfig.setCashAmount(0.0);
      this.saveWorkstationConfig(workstationConfig);
   }

   public List<WorkstationConfig> getActiveWorkstationConfigs() {
      return this.workstationConfigDao.getActiveWorkstationConfigs();
   }

   public void saveReceiptType(ReceiptType receiptType) {
      this.receiptTypeDao.saveReceipt(receiptType);
   }

   public List<ReceiptType> getAllReceiptTypesForPurchase() {
      return this.receiptTypeDao.getAllReceiptTypesForPurchase();
   }

   public List<ReceiptType> getActiveReceiptTypesForPurchase() {
      return this.receiptTypeDao.getActiveReceiptTypesForPurchase();
   }

   public ReceiptType getReceiptTypeByName(String receiptTypeName) {
      return this.receiptTypeDao.getReceiptTypeByName(receiptTypeName);
   }

   public List<ReceiptType> getAllReceiptTypesForOrder() {
      return this.receiptTypeDao.getAllReceiptTypesForOrder();
   }

   public List<ReceiptType> getActiveReceiptTypesForOrder() {
      return this.receiptTypeDao.getActiveReceiptTypesForOrder();
   }

   public List<ReceiptType> getActiveReceiptTypesForOrder(Order order) {
      List<ReceiptType> types = this.receiptTypeDao.getActiveReceiptTypesForOrder();

      try {
         if (order != null && order.hasAfipCae()) {
            types.clear();
            if (order.getAfipCbteTipo() == 1) {
               types.add(this.receiptTypeDao.getReceiptTypeByName("Factura A"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Remito"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket Factura A"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket Fiscal"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket No Fiscal"));
            }

            if (order.getAfipCbteTipo() == 6) {
               types.add(this.receiptTypeDao.getReceiptTypeByName("Factura B"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Remito"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket Factura B"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket Fiscal"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket No Fiscal"));
            }

            if (order.getAfipCbteTipo() == 11) {
               types.add(this.receiptTypeDao.getReceiptTypeByName("Factura C"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Remito"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket Factura C"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket Fiscal"));
               types.add(this.receiptTypeDao.getReceiptTypeByName("Ticket No Fiscal"));
            }
         }
      } catch (Exception var4) {
      }

      return types;
   }

   public void savePriceList(PriceList priceList) {
      this.priceListDao.savePriceList(priceList);
   }

   public List<PriceList> getActivePriceLists() {
      return this.priceListDao.getActivePriceLists();
   }

   public PriceList getPriceListByName(String priceListName) {
      return this.priceListDao.getPriceListByName(priceListName);
   }

   public PriceList getDefaultPriceList() {
      return this.priceListDao.getPriceList(new Long(1L));
   }

   public List<PriceList> getAllPriceLists() {
      return this.priceListDao.getAllPriceLists();
   }

   public String getInstallationCodePrefix() {
      return this.getAppConfig().getAppModel().substring(0, 1) + "11";
   }

   public void updateData_1_10() {
      logger.info("Actualizando datos para versión 1.10");

      try {
         this.loadPriceLists();
         List<Product> allProducts = this.getProductService().getAllProducts();
         PriceList defaultPriceList = this.getDefaultPriceList();
         int idx = 1;

         for(Iterator var5 = allProducts.iterator(); var5.hasNext(); ++idx) {
            Product product = (Product)var5.next();
            logger.info("Actualizando artículo: " + idx + " - " + product.getBarCode() + " - " + product.getDescription());
            ProductPrice productPrice = new ProductPrice();
            productPrice.setProduct(product);
            productPrice.setPriceList(defaultPriceList);
            productPrice.setCostPrice(product.getCostPrice());
            productPrice.setVat(product.getVat());
            productPrice.setGrossMargin(product.getGrossMargin());
            productPrice.setSellingPrice(product.getSellingPrice());
            productPrice.setInternalTax(product.getInternalTax());
            productPrice.updateNetPrice();
            productPrice.setLastUpdatedPrice(product.getLastUpdatedPrice());
            this.getProductService().saveProductPrice(productPrice);
         }
      } catch (Exception var7) {
         logger.error("Error en actualización de datos para versión 1.10");
      }

      logger.info("Fin de actualización de datos para versión 1.10");
   }

   public void updateData_11_0() {
      logger.info("Actualizando datos para versión 11.0");

      try {
         this.saveReceiptType(new ReceiptType("Nota de Crédito A", false, false, true));
         this.saveReceiptType(new ReceiptType("Nota de Crédito B", false, false, true));
         this.saveReceiptType(new ReceiptType("Nota de Crédito C", false, false, true));
         WorkstationConfig wsc = this.getCurrentWorkstationConfig();
         wsc.setOpenConfirmPrintOrder(true);
         this.saveWorkstationConfig(wsc);
      } catch (Exception var2) {
         logger.error("Error en actualización de datos para versión 11.0");
      }

      logger.info("Fin de actualización de datos para versión 11.0");
   }

   public boolean isUpdatedData_1_10() {
      PriceList priceList = this.getDefaultPriceList();
      return priceList != null;
   }

   public boolean isUpdatedData_11_0() {
      ReceiptType nota = this.getReceiptTypeByName("Nota de Crédito A");
      return nota != null;
   }

   public void loginAfip() {
      logger.info("Conectando con Afip");
      String loginTicketResponse = AfipUtils.login(this.getAfipConfig());
      logger.debug("Login ticket response: " + loginTicketResponse);

      try {
         Reader tokenReader = new StringReader(loginTicketResponse);
         Document tokenDoc = (new SAXReader(false)).read(tokenReader);
         String token = tokenDoc.valueOf("/loginTicketResponse/credentials/token");
         String sign = tokenDoc.valueOf("/loginTicketResponse/credentials/sign");
         logger.debug("Guardando token y sign de Afip");
         this.saveAfipProp(token, sign);
      } catch (Exception var6) {
      }

   }

   private void saveAfipProp(String token, String sign) {
      Properties prop = new Properties();
      OutputStream output = null;

      try {
         output = new FileOutputStream("C:/facilvirtual/config/afip.properties");
         prop.setProperty("afip.token", token);
         prop.setProperty("afip.sign", sign);
         prop.store(output, (String)null);
      } catch (IOException var14) {
         var14.printStackTrace();
      } finally {
         if (output != null) {
            try {
               output.close();
            } catch (IOException var13) {
               logger.error(var13.getMessage());
               logger.error(var13.toString());
            }
         }

      }

   }

   public void closeDatabaseConnection() {
      try {
         JdbcTemplate jdbcTemplate = new JdbcTemplate(this.getDataSource());
         jdbcTemplate.execute("SHUTDOWN");
      } catch (Exception var2) {
         logger.error(var2.getMessage());
         logger.error(var2.toString());
      }

   }

   private DataSource getDataSource() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (DataSource)context.getBean("dataSource");
   }

   public void backupDB(String dirDest) {
      logger.info("Creando copia de seguridad");

      try {
         File srcFolder = new File("C:\\facilvirtual\\data");
         File destFolder = new File("C:\\facilvirtualRestore\\" + dirDest);
         if (!srcFolder.exists()) {
            logger.error("El directorio no existe.");
         } else {
            if (!destFolder.exists()) {
               destFolder.mkdirs();
            }

            try {
               FVFileUtils.copyFolder(srcFolder, destFolder);
            } catch (IOException var5) {
               logger.error(var5.getMessage());
               logger.error(var5.toString());
            }
         }

         logger.info("Se creó la copia de seguridad con éxito (" + dirDest + ")");
      } catch (Exception var6) {
         logger.error("Se produjo un error al crear la copia de seguridad (" + dirDest + ")");
         logger.error(var6.getMessage());
         //logger.error(var6);
      }

   }
}
