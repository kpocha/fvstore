package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.CreditCardDao;
import com.facilvirtual.fvstoresdesk.dao.DebitCardDao;
import com.facilvirtual.fvstoresdesk.dao.NotaDeCreditoDao;
import com.facilvirtual.fvstoresdesk.dao.OrderDao;
import com.facilvirtual.fvstoresdesk.dao.OrderLineDao;
import com.facilvirtual.fvstoresdesk.dao.SaleConditionDao;
import com.facilvirtual.fvstoresdesk.dao.VatConditionDao;
import com.facilvirtual.fvstoresdesk.dao.VatDao;
import com.facilvirtual.fvstoresdesk.model.AfipConfig;
import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.CashOperation;
import com.facilvirtual.fvstoresdesk.model.CreditCard;
import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.CustomerOnAccountOperation;
import com.facilvirtual.fvstoresdesk.model.DebitCard;
import com.facilvirtual.fvstoresdesk.model.NotaDeCredito;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import com.facilvirtual.fvstoresdesk.model.SaleCondition;
import com.facilvirtual.fvstoresdesk.model.Vat;
import com.facilvirtual.fvstoresdesk.model.VatCondition;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
// import com.facilvirtual.fvstoresdesk.print.FacturaAPdfCreator; // Commented out - UI concern
// import com.facilvirtual.fvstoresdesk.print.FacturaBCPdfCreator; // Commented out - UI concern
// import com.facilvirtual.fvstoresdesk.print.NotaDeCreditoAPdfCreator; // Commented out - UI concern
// import com.facilvirtual.fvstoresdesk.print.NotaDeCreditoBCPdfCreator; // Commented out - UI concern
// import com.facilvirtual.fvstoresdesk.print.TicketACreator; // Commented out - UI concern
// import com.facilvirtual.fvstoresdesk.print.TicketBCCreator; // Commented out - UI concern
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.eclipse.swt.widgets.Shell; // Removed Shell import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
   protected static Logger logger = LoggerFactory.getLogger("OrderService");
   @Autowired
   private OrderDao orderDao;
   @Autowired
   private OrderLineDao orderLineDao;
   @Autowired
   private CreditCardDao creditCardDao;
   @Autowired
   private DebitCardDao debitCardDao;
   @Autowired
   private VatConditionDao vatConditionDao;
   @Autowired
   private SaleConditionDao saleConditionDao;
   @Autowired
   private VatDao vatDao;
   @Autowired
   private NotaDeCreditoDao notaDeCreditoDao;
   @Autowired
   private AfipConfig afipConfig;

   public OrderService() {
   }

   public void saveOrder(Order order) {
      this.orderDao.saveOrder(order);
   }

   public void saveNotaDeCredito(NotaDeCredito notaDeCredito) {
      this.notaDeCreditoDao.saveNotaDeCredito(notaDeCredito);
   }

   public List<Order> getAllOrders() {
      return this.orderDao.getAllOrders();
   }

   public void saveCreditCard(CreditCard creditCard) {
      this.creditCardDao.saveCreditCard(creditCard);
   }

   public void saveDebitCard(DebitCard debitCard) {
      this.debitCardDao.saveDebitCard(debitCard);
   }

   public void saveVatCondition(VatCondition vatCondition) {
      this.vatConditionDao.saveVatCondition(vatCondition);
   }

   public VatCondition getVatCondition(Long vatConditionId) {
      return this.vatConditionDao.getVatCondition(vatConditionId);
   }

   public List<VatCondition> getAllVatConditions() {
      return this.vatConditionDao.getAllVatConditions();
   }

   public List<VatCondition> getAllVatConditionsForCompany() {
      return this.vatConditionDao.getAllVatConditionsForCompany();
   }

   public VatCondition getVatConditionByName(String vatConditionName) {
      return this.vatConditionDao.getVatConditionByName(vatConditionName);
   }

   public void saveVat(Vat vat) {
      this.vatDao.saveVat(vat);
   }

   public Vat getVat(Long vatId) {
      return this.vatDao.getVat(vatId);
   }

   public Vat getVatByName(String vatName) {
      return this.vatDao.getVatByName(vatName);
   }

   public List<Vat> getAllVats() {
      return this.vatDao.getAllVats();
   }

   public List<CreditCard> getActiveCreditCards() {
      return this.creditCardDao.getActiveCreditCards();
   }

   public List<DebitCard> getActiveDebitCards() {
      return this.debitCardDao.getActiveDebitCards();
   }

   public CreditCard getCreditCardByName(String creditCardName) {
      return this.creditCardDao.getCreditCardByName(creditCardName);
   }

   public DebitCard getDebitCardByName(String debitCardName) {
      return this.debitCardDao.getDebitCardByName(debitCardName);
   }

   public double getTotalForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getTotal();
         }
      }

      return total;
   }

   public String getTotalToDisplayForOrders(List<Order> orders) {
      double total = this.getTotalForOrders(orders);
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(total));
      str = str.replaceAll("\\.", ",");
      return total != 0.0 ? str : "0,00";
   }

   public double getDiscountForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getDiscount();
         }
      }

      return total;
   }

   public String getDiscountToDisplayForOrders(List<Order> orders) {
      double total = this.getDiscountForOrders(orders);
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(total));
      str = str.replaceAll("\\.", ",");
      return total != 0.0 ? str : "0,00";
   }

   public double getSubtotalForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getSubtotal();
         }
      }

      return total;
   }

   public String getSubtotalToDisplayForOrders(List<Order> orders) {
      double total = this.getSubtotalForOrders(orders);
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(total));
      str = str.replaceAll("\\.", ",");
      return total != 0.0 ? str : "0,00";
   }

   public void cancelOrder(Long orderId) {
      Order order = this.getOrder(orderId);
      if (order != null && !order.isCancelled()) {
         logger.info("Cancelando venta - Nro. Trans.:" + orderId);
         order.setStatus("CANCELLED");
         this.saveOrder(order);
         CashOperation cashOperation = this.getCashService().getCashOperationByOrderId(order.getId());
         if (cashOperation != null) {
            cashOperation.setActive(false);
            this.getCashService().saveCashOperation(cashOperation);
            WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
            if (this.getCashService().mustUpdateCashAmount(workstationConfig, cashOperation.getOperationDate())) {
               this.getAppConfigService().decCashAmount(workstationConfig, order.getNetCashAmount());
            }
         }

         CustomerOnAccountOperation customerOnAccountOp = this.getCustomerService().getCustomerOnAccountOpByOrderId(order.getId());
         if (customerOnAccountOp != null) {
            customerOnAccountOp.setActive(false);
            this.getCustomerService().saveCustomerOnAccountOperation(customerOnAccountOp);
            Customer customer = customerOnAccountOp.getCustomer();
            customer.setOnAccountTotal(customer.getOnAccountTotal() + customerOnAccountOp.getAmount());
            this.getCustomerService().saveCustomer(customer);
         }

         this.getProductService().restoreLastSaleDateForOrder(order);
         this.getProductService().restoreStockForOrder(order);
      }

   }

   public void restoreOrder(Long orderId) {
      Order order = this.getOrder(orderId);
      if (order != null && order.isCancelled()) {
         logger.info("Restaurando venta - Nro. Trans.:" + orderId);
         order.setStatus("COMPLETED");
         this.saveOrder(order);
         CashOperation cashOperation = this.getCashService().getCashOperationByOrderId(order.getId());
         if (cashOperation != null) {
            cashOperation.setActive(true);
            this.getCashService().saveCashOperation(cashOperation);
            WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
            if (this.getCashService().mustUpdateCashAmount(workstationConfig, cashOperation.getOperationDate())) {
               this.getAppConfigService().incCashAmount(this.getAppConfigService().getCurrentWorkstationConfig(), order.getNetCashAmount());
            }
         }

         CustomerOnAccountOperation customerOnAccountOp = this.getCustomerService().getCustomerOnAccountOpByOrderId(order.getId());
         if (customerOnAccountOp != null) {
            customerOnAccountOp.setActive(true);
            this.getCustomerService().saveCustomerOnAccountOperation(customerOnAccountOp);
            Customer customer = customerOnAccountOp.getCustomer();
            customer.setOnAccountTotal(customer.getOnAccountTotal() - customerOnAccountOp.getAmount());
            this.getCustomerService().saveCustomer(customer);
         }

         this.getProductService().updateLastSaleDateForOrder(order);
         this.getProductService().updateStockForOrder(order);
      }

   }

   public ProductService getProductService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (ProductService)context.getBean("productService");
   }

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   public CashService getCashService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (CashService)context.getBean("cashService");
   }

   public AccountService getAccountService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AccountService)context.getBean("accountService");
   }

   public CustomerService getCustomerService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (CustomerService)context.getBean("customerService");
   }

   public AfipService getAfipService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AfipService)context.getBean("afipService");
   }

   public Order getOrder(Long orderID) {
      return this.orderDao.getOrder(orderID);
   }

   private String formatValueToDisplay(double value) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(value));
      str = str.replaceAll("\\.", ",");
      return value != 0.0 ? str : "0,00";
   }

   public double getNetCashTotalForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getNetCashAmount();
         }
      }

      return total;
   }

   public String getNetCashTotalToDisplayForOrders(List<Order> orders) {
      return this.formatValueToDisplay(this.getNetCashTotalForOrders(orders));
   }

   public double getNetCreditCardTotalForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getNetCreditCardAmount();
         }
      }

      return total;
   }

   public String getNetCreditCardTotalToDisplayForOrders(List<Order> orders) {
      return this.formatValueToDisplay(this.getNetCreditCardTotalForOrders(orders));
   }

   public double getNetOnAccountTotalForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getNetOnAccountAmount();
         }
      }

      return total;
   }

   public String getNetOnAccountTotalToDisplayForOrders(List<Order> orders) {
      return this.formatValueToDisplay(this.getNetOnAccountTotalForOrders(orders));
   }

   public double getNetDebitCardTotalForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getNetDebitCardAmount();
         }
      }

      return total;
   }

   public String getNetDebitCardTotalToDisplayForOrders(List<Order> orders) {
      return this.formatValueToDisplay(this.getNetDebitCardTotalForOrders(orders));
   }

   public double getNetCheckTotalForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getNetCheckAmount();
         }
      }

      return total;
   }

   public String getNetCheckTotalToDisplayForOrders(List<Order> orders) {
      return this.formatValueToDisplay(this.getNetCheckTotalForOrders(orders));
   }

   public double getNetTicketsTotalForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getNetTicketsAmount();
         }
      }

      return total;
   }

   public String getNetTicketsTotalToDisplayForOrders(List<Order> orders) {
      return this.formatValueToDisplay(this.getNetTicketsTotalForOrders(orders));
   }

   public List<Order> getAllOrdersForDateRange(Date startDate, Date endDate) {
      return this.orderDao.getAllOrdersForDateRange(startDate, endDate);
   }

   public List<CreditCard> getAllCreditCards() {
      return this.creditCardDao.getAllCreditCards();
   }

   public List<DebitCard> getAllDebitCards() {
      return this.debitCardDao.getAllDebitCards();
   }

   public double getNetCreditCardTotalForOrders(List<Order> orders, String creditCardName) {
      double total = 0.0;
      Iterator var6 = orders.iterator();

      while(var6.hasNext()) {
         Order currentOrder = (Order)var6.next();
         if (currentOrder.isCompleted() && currentOrder.getCreditCard() != null && currentOrder.getCreditCard().getName().equalsIgnoreCase(creditCardName)) {
            total += currentOrder.getNetCreditCardAmount();
         }
      }

      return total;
   }

   public String getNetCreditCardTotalToDisplayForOrders(List<Order> orders, String creditCardName) {
      return this.formatValueToDisplay(this.getNetCreditCardTotalForOrders(orders, creditCardName));
   }

   public double getNetDebitCardTotalForOrders(List<Order> orders, String debitCardName) {
      double total = 0.0;
      Iterator var6 = orders.iterator();

      while(var6.hasNext()) {
         Order currentOrder = (Order)var6.next();
         if (currentOrder.isCompleted() && currentOrder.getDebitCard() != null && currentOrder.getDebitCard().getName().equalsIgnoreCase(debitCardName)) {
            total += currentOrder.getNetDebitCardAmount();
         }
      }

      return total;
   }

   public String getNetDebitCardTotalToDisplayForOrders(List<Order> orders, String debitCardName) {
      return this.formatValueToDisplay(this.getNetDebitCardTotalForOrders(orders, debitCardName));
   }

   public int getTotalOrdersQty() {
      return this.orderDao.getTotalOrdersQty();
   }

   public List<OrderLine> getOrderLinesForOrder(Order order) {
      return this.orderLineDao.getOrderLinesForOrder(order);
   }

   public double getDiscountSurchargeForOrders(List<Order> orders) {
      double total = 0.0;
      Iterator var5 = orders.iterator();

      while(var5.hasNext()) {
         Order currentOrder = (Order)var5.next();
         if (currentOrder.isCompleted()) {
            total += currentOrder.getDiscountSurcharge();
         }
      }

      return total;
   }

   public String getDiscountSurchargeToDisplayForOrders(List<Order> orders) {
      double total = this.getDiscountSurchargeForOrders(orders);
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(total));
      str = str.replaceAll("\\.", ",");
      return total != 0.0 ? str : "0,00";
   }

   public List<SaleCondition> getAllSaleConditions() {
      return this.saleConditionDao.getAllSaleConditions();
   }

   public SaleCondition getSaleConditionByName(String saleConditionName) {
      return this.saleConditionDao.getSaleConditionByName(saleConditionName);
   }

   public void printOrder(Order order, AppConfig appConfig, WorkstationConfig workstationConfig /*, Shell shell Removed */) {
      // try {
      //    if (order.getReceiptType().getName().equalsIgnoreCase("Ticket Fiscal") || order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal") || order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura B") || order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura C") || order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura A") && !order.hasAfipCae()) {
      //       TicketBCCreator ticketCreator = new TicketBCCreator(order, appConfig, workstationConfig, shell);
      //       ticketCreator.print();
      //    } else if (order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura A") && order.hasAfipCae()) {
      //       TicketACreator ticketCreator = new TicketACreator(order, appConfig, workstationConfig, shell);
      //       ticketCreator.print();
      //    } else if (order.hasAfipCae() && order.isInvoiceA() && !order.isRemito()) {
      //       FacturaAPdfCreator orderPdfCreator = new FacturaAPdfCreator(order, shell);
      //       orderPdfCreator.createPdf();
      //    } else {
      //       FacturaBCPdfCreator orderPdfCreator = new FacturaBCPdfCreator(order, shell);
      //       orderPdfCreator.createPdf();
      //    }
      // } catch (Exception var6) {
      //    logger.error("Se produjo un error al imprimir el comprobante con id: " + order.getId());
      //    logger.error("Message: " + var6.getMessage());
      //    logger.error("Exception: " + var6);
      // }
      logger.warn("printOrder method in core OrderService is currently disabled pending refactor for UI separation.");
   }

   public void printNotaDeCredito(NotaDeCredito nota, AppConfig appConfig, WorkstationConfig workstationConfig /*, Shell shell Removed */) {
      // try {
      //    if (nota.hasAfipCae() && nota.getCbteTipo().getName().equalsIgnoreCase("Nota de Crédito A")) {
      //       NotaDeCreditoAPdfCreator notaPdfCreator = new NotaDeCreditoAPdfCreator(nota, shell);
      //       notaPdfCreator.createPdf();
      //    } else {
      //       NotaDeCreditoBCPdfCreator notaPdfCreator = new NotaDeCreditoBCPdfCreator(nota, shell);
      //       notaPdfCreator.createPdf();
      //    }
      // } catch (Exception var6) {
      //    logger.error("Se produjo un error al imprimir el comprobante con id: " + nota.getId());
      //    logger.error("Message: " + var6.getMessage());
      //    logger.error("Exception: " + var6);
      // }
      logger.warn("printNotaDeCredito method in core OrderService is currently disabled pending refactor for UI separation.");
   }

   public List<Order> getCompletedOrdersForDateRange(Date startDate, Date endDate) {
      return this.orderDao.getCompletedOrdersForDateRange(startDate, endDate);
   }

   public List<Order> getFiscalOrdersForDateRange(Date startDate, Date endDate) {
      return this.orderDao.getFiscalOrdersForDateRange(startDate, endDate);
   }

   public String createFacturaElectronicaAfip(Order order) {
      if (!order.hasAfipCae()) {
         Long ultimo = this.getAfipService().getCompUltimoAutorizadoAfip(order.getAfipCbteTipo());
         if (ultimo == -1L) {
            this.getAppConfigService().loginAfip();
            ultimo = this.getAfipService().getCompUltimoAutorizadoAfip(order.getAfipCbteTipo());
         }

         return ultimo != -1L ? this.getAfipService().generateCaeForOrder(order) : "No se pudo conectar con Afip. Por favor, revisa tu conexión a internet o intenta más tarde.";
      } else {
         return "La venta ya tiene un CAE asignado.";
      }
   }

   public Order getOrderByNumber(int afipPtoVta, int nroFactura) {
      return this.orderDao.getOrderByNumber(afipPtoVta, nroFactura);
   }

   public Vat getDefaultVat(AppConfig appConfig) {
      Vat vat;
      if ("Monotributo".equals(appConfig.getCompanyVatCondition().getName())) {
         vat = this.getVatByName("0%");
      } else {
         vat = this.getVatByName("21%");
      }

      return vat;
   }

   public List<NotaDeCredito> getAllNotasDeCreditoForDateRange(Date startDate, Date endDate) {
      return this.notaDeCreditoDao.getAllNotasDeCreditoForDateRange(startDate, endDate);
   }

   public int getNotasDeCreditoQty() {
      int total = 0;

      try {
         total = this.notaDeCreditoDao.getAllNotasDeCredito().size();
      } catch (Exception var3) {
      }

      return total;
   }

   public NotaDeCredito getNotaDeCredito(Long notaId) {
      return this.notaDeCreditoDao.getNotaDeCredito(notaId);
   }

   public String createNotaDeCreditoAfip(NotaDeCredito notaDeCredito) {
      if (!notaDeCredito.hasAfipCae()) {
         Long ultimo = this.getAfipService().getCompUltimoAutorizadoAfip(notaDeCredito.getAfipCbteTipo());
         if (ultimo == -1L) {
            this.getAppConfigService().loginAfip();
            ultimo = this.getAfipService().getCompUltimoAutorizadoAfip(notaDeCredito.getAfipCbteTipo());
         }

         return ultimo != -1L ? this.getAfipService().generateCaeForNotaDeCredito(notaDeCredito) : "No se pudo conectar con Afip. Por favor, revisa tu conexión a internet o intenta más tarde.";
      } else {
         return "La nota de crédito ya tiene un CAE asignado.";
      }
   }

   public String afipTest() {
      return this.afipConfig.getEndpointUrlDev();
   }
}
