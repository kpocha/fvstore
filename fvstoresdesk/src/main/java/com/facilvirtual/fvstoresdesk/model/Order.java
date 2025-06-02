package com.facilvirtual.fvstoresdesk.model;

import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.util.AfipUtils;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

@Entity
@Table(
   name = "fvpos_order"
)
public class Order implements Serializable {
   protected static Logger logger = LoggerFactory.getLogger("Order");
   private static final long serialVersionUID = -47478251218222L;
   @Id
   @Column(
      name = "order_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "creation_date",
      nullable = false
   )
   private Date creationDate;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "sale_date",
      nullable = false
   )
   private Date saleDate;
   @Column(
      name = "custom_sale_date",
      nullable = false
   )
   private boolean customSaleDate = false;
   @Column(
      name = "items_qty",
      nullable = false
   )
   private int itemsQty = 0;
   @Column(
      name = "subtotal",
      nullable = false
   )
   private double subtotal = 0.0;
   @Column(
      name = "inner_taxes",
      nullable = false
   )
   private double innerTaxes = 0.0;
   @Column(
      name = "discount_percent",
      nullable = false
   )
   private double discountPercent = 0.0;
   @Column(
      name = "discount_amount",
      nullable = false
   )
   private double discountAmount = 0.0;
   @Column(
      name = "surcharge_percent",
      nullable = false
   )
   private double surchargePercent = 0.0;
   @Column(
      name = "surcharge_amount",
      nullable = false
   )
   private double surchargeAmount = 0.0;
   @Column(
      name = "total",
      nullable = false
   )
   private double total = 0.0;
   @Column(
      name = "status",
      nullable = false
   )
   private String status = "PENDING";
   @ManyToOne
   @JoinColumn(
      name = "receipt_type_id"
   )
   private ReceiptType receiptType;
   @Column(
      name = "receipt_number"
   )
   private String receiptNumber;
   @Column(
      name = "delivery_note_number"
   )
   private int deliveryNoteNumber;
   @OneToMany(
      mappedBy = "order",
      cascade = {CascadeType.ALL}
   )
   @OrderBy("lineNumber")
   private List<OrderLine> orderLines = new ArrayList();
   @ManyToOne
   @JoinColumn(
      name = "vat_condition_id",
      nullable = false
   )
   private VatCondition vatCondition;
   @ManyToOne
   @JoinColumn(
      name = "company_vat_condition_id",
      nullable = false
   )
   private VatCondition companyVatCondition;
   @ManyToOne
   @JoinColumn(
      name = "customer_id",
      nullable = false
   )
   private Customer customer;
   @ManyToOne
   @JoinColumn(
      name = "cashier_id",
      nullable = false
   )
   private Employee cashier;
   @Column(
      name = "standard_vat_value",
      nullable = false
   )
   private double standardVatValue = 21.0;
   @Column(
      name = "reduced_vat_value",
      nullable = false
   )
   private double reducedVatValue = 10.5;
   @Column(
      name = "payment_cash_amt",
      nullable = false
   )
   private double cashAmount = 0.0;
   @Column(
      name = "payment_credit_card_amt",
      nullable = false
   )
   private double creditCardAmount = 0.0;
   @ManyToOne
   @JoinColumn(
      name = "credit_card_id"
   )
   private CreditCard creditCard;
   @Column(
      name = "payment_on_account_amt",
      nullable = false
   )
   private double onAccountAmount = 0.0;
   @Column(
      name = "payment_debit_card_amt",
      nullable = false
   )
   private double debitCardAmount = 0.0;
   @ManyToOne
   @JoinColumn(
      name = "debit_card_id"
   )
   private DebitCard debitCard;
   @Column(
      name = "payment_check_amt",
      nullable = false
   )
   private double checkAmount = 0.0;
   @Column(
      name = "payment_tickets_amt",
      nullable = false
   )
   private double ticketsAmount = 0.0;
   @Column(
      name = "payment_net_cash_amt",
      nullable = false
   )
   private double netCashAmount = 0.0;
   @Column(
      name = "payment_net_credit_card_amt",
      nullable = false
   )
   private double netCreditCardAmount = 0.0;
   @Column(
      name = "payment_net_on_account_amt",
      nullable = false
   )
   private double netOnAccountAmount = 0.0;
   @Column(
      name = "payment_net_debit_card_amt",
      nullable = false
   )
   private double netDebitCardAmount = 0.0;
   @Column(
      name = "payment_net_check_amt",
      nullable = false
   )
   private double netCheckAmount = 0.0;
   @Column(
      name = "payment_net_tickets_amt",
      nullable = false
   )
   private double netTicketsAmount = 0.0;
   @Column(
      name = "pos_number",
      nullable = false
   )
   private int posNumber = 0;
   @Column(
      name = "cash_number",
      nullable = false
   )
   private int cashNumber = 0;
   @Column(
      name = "observations"
   )
   private String observations = "";
   @ManyToOne
   @JoinColumn(
      name = "price_list_id",
      nullable = false
   )
   private PriceList priceList;
   @Column(
      name = "afip_pto_vta"
   )
   private int afipPtoVta = 0;
   @Column(
      name = "afip_cbte_tipo"
   )
   private int afipCbteTipo = 0;
   @Column(
      name = "afip_concepto"
   )
   private int afipConcepto = 0;
   @Column(
      name = "afip_doc_tipo"
   )
   private int afipDocTipo = 0;
   @Column(
      name = "afip_doc_nro"
   )
   private Long afipDocNro;
   @Column(
      name = "afip_cbte_desde"
   )
   private Long afipCbteDesde;
   @Column(
      name = "afip_cbte_hasta"
   )
   private Long afipCbteHasta;
   @Column(
      name = "afip_cbte_fch"
   )
   private String afipCbteFch = "";
   @Column(
      name = "afip_fch_serv_desde"
   )
   private String afipFchServDesde;
   @Column(
      name = "afip_fch_serv_hasta"
   )
   private String afipFchServHasta;
   @Column(
      name = "afip_fch_vto_pago"
   )
   private String afipFchVtoPago;
   @Column(
      name = "afip_cae"
   )
   private String afipCae;
   @Column(
      name = "afip_cae_fch_vto"
   )
   private String afipCaeFchVto;
   @Column(
      name = "afip_bar_code"
   )
   private String afipBarCode = "";

   public Order() {
   }

   public String getAfipBarCode() {
      return this.afipBarCode;
   }

   public void setAfipBarCode(String afipBarCode) {
      this.afipBarCode = afipBarCode;
   }

   public int getCashNumber() {
      return this.cashNumber;
   }

   public void setCashNumber(int cashNumber) {
      this.cashNumber = cashNumber;
   }

   public String getObservations() {
      return this.observations;
   }

   public void setObservations(String observations) {
      this.observations = observations;
   }

   public int getPosNumber() {
      return this.posNumber;
   }

   public void setPosNumber(int posNumber) {
      this.posNumber = posNumber;
   }

   public double getStandardVatValue() {
      return this.standardVatValue;
   }

   public void setStandardVatValue(double standardVatValue) {
      this.standardVatValue = standardVatValue;
   }

   public double getReducedVatValue() {
      return this.reducedVatValue;
   }

   public void setReducedVatValue(double reducedVatValue) {
      this.reducedVatValue = reducedVatValue;
   }

   public VatCondition getCompanyVatCondition() {
      return this.companyVatCondition;
   }

   public void setCompanyVatCondition(VatCondition companyVatCondition) {
      this.companyVatCondition = companyVatCondition;
   }

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Date getCreationDate() {
      return this.creationDate;
   }

   public void setCreationDate(Date creationDate) {
      this.creationDate = creationDate;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public double getSubtotal() {
      return this.subtotal;
   }

   public void setSubtotal(double subtotal) {
      this.subtotal = subtotal;
   }

   public String getReceiptNumber() {
      return this.receiptNumber;
   }

   public void setReceiptNumber(String receiptNumber) {
      this.receiptNumber = receiptNumber;
   }

   public void setDiscountPercent(double discountPercent) {
      this.discountPercent = discountPercent;
   }

   public void setDiscountAmount(double discountAmount) {
      this.discountAmount = discountAmount;
   }

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }

   public List<OrderLine> getOrderLines() {
      return this.orderLines;
   }

   public void setOrderLines(List<OrderLine> orderLines) {
      this.orderLines = orderLines;
   }

   public int getItemsQty() {
      return this.itemsQty;
   }

   public void setItemsQty(int itemsQty) {
      this.itemsQty = itemsQty;
   }

   public CreditCard getCreditCard() {
      return this.creditCard;
   }

   public void setCreditCard(CreditCard creditCard) {
      this.creditCard = creditCard;
   }

   public DebitCard getDebitCard() {
      return this.debitCard;
   }

   public void setDebitCard(DebitCard debitCard) {
      this.debitCard = debitCard;
   }

   public VatCondition getVatCondition() {
      return this.vatCondition;
   }

   public void setVatCondition(VatCondition vatCondition) {
      this.vatCondition = vatCondition;
   }

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public void addOrderLine(OrderLine orderLine) {
      this.orderLines.add(orderLine);
      ++this.itemsQty;
      this.subtotal += orderLine.getSubtotal();
      this.total = this.calculateTotal();
   }

   public void removeOrderLine(int lineNumber) {
      try {
         this.itemsQty = 0;
         this.subtotal = 0.0;
         this.total = 0.0;
         this.orderLines.remove(lineNumber);

         for(Iterator var3 = this.orderLines.iterator(); var3.hasNext(); this.total = this.calculateTotal()) {
            OrderLine currentOrderLine = (OrderLine)var3.next();
            if (currentOrderLine.getLineNumber() > lineNumber) {
               currentOrderLine.setLineNumber(currentOrderLine.getLineNumber() - 1);
            }

            ++this.itemsQty;
            this.subtotal += currentOrderLine.getSubtotal();
         }
      } catch (Exception var4) {
         logger.error("Error al quitar artículo de venta");
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

   }

   public double getDiscount() {
      double d1 = this.getSubtotal() * this.getDiscountPercent() / 100.0;
      double d2 = this.getDiscountAmount();
      return d1 + d2;
   }

   public double getSurcharge() {
      double s1 = this.getSubtotal() * this.getSurchargePercent() / 100.0;
      double s2 = this.getSurchargeAmount();
      return s1 + s2;
   }

   public double calculateTotal() {
      return this.subtotal - this.getDiscount() + this.getSurcharge();
   }

   public String getSubtotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String subtotalStr = String.valueOf(formatter.format(this.getSubtotal()));
      subtotalStr = subtotalStr.replaceAll("\\.", ",");
      return this.getSubtotal() != 0.0 ? subtotalStr : "0,00";
   }

   public String getSubtotalToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String subtotalStr = String.valueOf(formatter.format(this.getSubtotal()));
      return this.getSubtotal() != 0.0 ? subtotalStr : "0,00";
   }

   public String getDiscountPercentToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getDiscountPercent()));
      str = str.replaceAll("\\.", ",");
      return this.getDiscountPercent() != 0.0 ? str : "0,00";
   }

   public String getTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getTotal() != 0.0 ? str : "0,00";
   }

   public String getTotalToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getTotal()));
      return this.getTotal() != 0.0 ? str : "0,00";
   }

   public String getTotalToDisplay2() {
      return this.getTotal() < 0.0 ? "(" + this.getTotalToDisplay().replaceAll("-", "") + ")" : this.getTotalToDisplay();
   }

   public String getTotalToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getTotal()));
      str = str.replaceAll(",", "\\.");
      return this.getTotal() != 0.0 ? str : "0";
   }

   public void clearOrder() {
      this.getOrderLines().clear();
      this.setItemsQty(0);
      this.setSubtotal(0.0);
      this.setDiscountPercent(0.0);
      this.setDiscountAmount(0.0);
      this.setTotal(0.0);
      this.setCreationDate(new Date());
      this.setCashAmount(0.0);
      this.setCreditCardAmount(0.0);
      this.setOnAccountAmount(0.0);
      this.setDebitCardAmount(0.0);
      this.setCheckAmount(0.0);
      this.setTicketsAmount(0.0);
   }

   public double getDiscountPercent() {
      return this.discountPercent;
   }

   public double getDiscountAmount() {
      return this.discountAmount;
   }

   public String getDiscountAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getDiscountAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getDiscountAmount() != 0.0 ? str : "0,00";
   }

   public String getDiscountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getDiscount()));
      str = str.replaceAll("\\.", ",");
      return this.getDiscount() != 0.0 ? str : "0,00";
   }

   public String getDiscountToDisplay2() {
      if (this.getDiscount() != 0.0) {
         return this.getDiscount() > 0.0 ? "(" + this.getDiscountToDisplay() + ")" : this.getDiscountToDisplay();
      } else {
         return "0,00";
      }
   }

   public void updateDiscountPercent(Double discountPercent) {
      this.setDiscountPercent(discountPercent);
      this.setTotal(this.calculateTotal());
   }

   public void updateDiscountAmount(Double discountAmount) {
      this.setDiscountAmount(discountAmount);
      this.setTotal(this.calculateTotal());
   }

   public void updateSurchargePercent(Double surchargePercent) {
      this.setSurchargePercent(surchargePercent);
      this.setTotal(this.calculateTotal());
   }

   public void updateSurchargeAmount(Double surchargeAmount) {
      this.setSurchargeAmount(surchargeAmount);
      this.setTotal(this.calculateTotal());
   }

   public OrderLine getOrderLineByLineNumber(int idx) {
      return (OrderLine)this.orderLines.get(idx);
   }

   public OrderLine getOrderLineByBarCodeAndPrice(String barCode, double price) {
      boolean found = false;
      OrderLine orderLine = null;
      Iterator<OrderLine> it = this.getOrderLines().iterator();

      while(!found && it.hasNext()) {
         OrderLine currentOrderLine = (OrderLine)it.next();
         if (currentOrderLine.getProduct() != null && barCode.equalsIgnoreCase(currentOrderLine.getProduct().getBarCode()) && price == currentOrderLine.getPrice()) {
            found = true;
            orderLine = currentOrderLine;
         }
      }

      return orderLine;
   }

   public void updateTotal() {
      this.subtotal = 0.0;

      OrderLine orderLine;
      for(Iterator var2 = this.orderLines.iterator(); var2.hasNext(); this.subtotal += orderLine.getSubtotal()) {
         orderLine = (OrderLine)var2.next();
      }

      this.setTotal(this.calculateTotal());
   }

   public String getStatusToDisplay() {
      if ("PENDING".equalsIgnoreCase(this.getStatus())) {
         return "Pendiente";
      } else {
         return "CANCELLED".equalsIgnoreCase(this.getStatus()) ? "Anulada" : "Completada";
      }
   }

   public String getCreationDateToDisplay() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      String dateStr = formatter.format(this.getCreationDate());
      return dateStr;
   }

   public String getHourToDisplay() {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
      String dateStr = formatter.format(this.getCreationDate());
      return dateStr;
   }

   public double getPaymentTotal() {
      return this.getCashAmount() + this.getCreditCardAmount() + this.getOnAccountAmount() + this.getDebitCardAmount() + this.getCheckAmount() + this.getTicketsAmount();
   }

   public double getChange() {
      return this.roundValue(this.getPaymentTotal() - this.getTotal());
   }

   public String getChangeToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getChange()));
      str = str.replaceAll("\\.", ",");
      return this.getChange() != 0.0 ? str : "0,00";
   }

   private double roundValue(double valueToRound) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(valueToRound));
      return Double.valueOf(str.replaceAll(",", "\\."));
   }

   public double getNetSubtotalNoLazy() {
      double netSubtotal = 0.0;
      List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);

      OrderLine orderLine;
      for(Iterator var5 = orderLines.iterator(); var5.hasNext(); netSubtotal += orderLine.getNetSubtotal()) {
         orderLine = (OrderLine)var5.next();
      }

      return netSubtotal;
   }

   public double getNetSubtotal() {
      double netSubtotal = 0.0;

      OrderLine orderLine;
      for(Iterator var4 = this.orderLines.iterator(); var4.hasNext(); netSubtotal += orderLine.getNetSubtotal()) {
         orderLine = (OrderLine)var4.next();
      }

      return netSubtotal;
   }

   public double getImpNeto() {
      double impNeto = 0.0;

      OrderLine orderLine;
      try {
         for(Iterator var8 = this.orderLines.iterator(); var8.hasNext(); impNeto += orderLine.getImpNetoSubtotal()) {
            orderLine = (OrderLine)var8.next();
         }
      } catch (Exception var7) {
         List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);

        // OrderLine orderLine;
         for(Iterator var6 = orderLines.iterator(); var6.hasNext(); impNeto += orderLine.getImpNetoSubtotal()) {
            orderLine = (OrderLine)var6.next();
         }
      }

      impNeto += this.getNetoDtoRecargo();
      return impNeto;
   }

   public double getImpIVA() {
      return this.getTotal() - this.getImpNeto();
   }

   public String getNetSubtotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetSubtotal()));
      str = str.replaceAll("\\.", ",");
      return this.getNetSubtotal() != 0.0 ? str : "0,00";
   }

   public double getNetSurcharge() {
      double d1 = this.getNetSubtotal() * this.getSurchargePercent() / 100.0;
      double d2 = this.getSurchargeAmount();
      return d1 + d2;
   }

   public double getNetDiscount() {
      double d1 = this.getNetSubtotal() * this.getDiscountPercent() / 100.0;
      double d2 = this.getDiscountAmount();
      return d1 + d2;
   }

   public String getNetDiscountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetDiscount()));
      str = str.replaceAll("\\.", ",");
      return this.getNetDiscount() != 0.0 ? str : "0,00";
   }

   public String getNetDiscountToDisplay2() {
      if (this.getNetDiscount() != 0.0) {
         return this.getNetDiscount() > 0.0 ? "(" + this.getNetDiscountToDisplay() + ")" : this.getNetDiscountToDisplay();
      } else {
         return "0,00";
      }
   }

   public double getNetSubtotal2() {
      return this.getNetSubtotal() - this.getNetDiscount();
   }

   public double getSubtotalForInvoiceA() {
      return this.getNetSubtotal();
   }

   public double getSubtotalForInvoiceANoLazy() {
      return this.getNetSubtotalNoLazy();
   }

   public double getSubtotal2ForInvoiceANoLazy() {
      return this.getSubtotalForInvoiceANoLazy() + this.getInternalTaxTotalNoLazy();
   }

   public double getSubtotal2ForInvoiceA() {
      return this.getSubtotalForInvoiceA() + this.getInternalTaxTotal();
   }

   public double getInternalTaxTotalNoLazy() {
      double total = 0.0;
      List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);

      OrderLine orderLine;
      for(Iterator var5 = orderLines.iterator(); var5.hasNext(); total += orderLine.getInternalTaxTotal()) {
         orderLine = (OrderLine)var5.next();
      }

      return total;
   }

   public double getInternalTaxTotal() {
      double total = 0.0;

      OrderLine orderLine;
      for(Iterator var4 = this.orderLines.iterator(); var4.hasNext(); total += orderLine.getInternalTaxTotal()) {
         orderLine = (OrderLine)var4.next();
      }

      return total;
   }

   public OrderService getOrderService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (OrderService)context.getBean("orderService");
   }

   public String getNetSubtotal2ToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(this.getNetSubtotal2());
      str = str.replaceAll("\\.", ",");
      return this.getNetSubtotal2() != 0.0 ? str : "0,00";
   }

   public String getSubtotalForInvoiceAToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getSubtotalForInvoiceANoLazy()));
      str = str.replaceAll("\\.", ",");
      return this.getSubtotalForInvoiceANoLazy() != 0.0 ? str : "0,00";
   }

   public String getSubtotalForInvoiceAToDisplayLazy() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getSubtotalForInvoiceA()));
      str = str.replaceAll("\\.", ",");
      return this.getSubtotalForInvoiceA() != 0.0 ? str : "0,00";
   }

   public String getInternalTaxTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getInternalTaxTotalNoLazy()));
      str = str.replaceAll("\\.", ",");
      return this.getInternalTaxTotalNoLazy() != 0.0 ? str : "0,00";
   }

   public String getInternalTaxTotalToDisplayLazy() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getInternalTaxTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getInternalTaxTotal() != 0.0 ? str : "0,00";
   }

   public String getSubtotal2ForInvoiceAToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getSubtotal2ForInvoiceANoLazy()));
      str = str.replaceAll("\\.", ",");
      return this.getSubtotal2ForInvoiceANoLazy() != 0.0 ? str : "0,00";
   }

   public String getSubtotal2ForInvoiceAToDisplayLazy() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getSubtotal2ForInvoiceA()));
      str = str.replaceAll("\\.", ",");
      return this.getSubtotal2ForInvoiceA() != 0.0 ? str : "0,00";
   }

   public double getStandardVatTotalNoLazy() {
      double total = 0.0;
      List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);
      Iterator var5 = orderLines.iterator();

      while(var5.hasNext()) {
         OrderLine orderLine = (OrderLine)var5.next();
         if (orderLine.getVatValue() == this.getStandardVatValue()) {
            total += orderLine.getVatTotal();
         }
      }

      return total;
   }

   public double getStandardVatTotal() {
      double total = 0.0;
      Iterator var4 = this.orderLines.iterator();

      while(var4.hasNext()) {
         OrderLine orderLine = (OrderLine)var4.next();
         if (orderLine.getVatValue() == this.getStandardVatValue()) {
            total += orderLine.getVatTotal();
         }
      }

      return total;
   }

   public String getStandardVatTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getStandardVatTotalNoLazy()));
      str = str.replaceAll("\\.", ",");
      return this.getStandardVatTotalNoLazy() != 0.0 ? str : "0,00";
   }

   public String getStandardVatTotalToDisplayLazy() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getStandardVatTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getStandardVatTotal() != 0.0 ? str : "0,00";
   }

   public double getReducedVatTotalNoLazy() {
      double total = 0.0;
      List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);
      Iterator var5 = orderLines.iterator();

      while(var5.hasNext()) {
         OrderLine orderLine = (OrderLine)var5.next();
         if (orderLine.getVatValue() == this.getReducedVatValue()) {
            total += orderLine.getVatTotal();
         }
      }

      return total;
   }

   public double getReducedVatTotal() {
      double total = 0.0;
      Iterator var4 = this.orderLines.iterator();

      while(var4.hasNext()) {
         OrderLine orderLine = (OrderLine)var4.next();
         if (orderLine.getVatValue() == this.getReducedVatValue()) {
            total += orderLine.getVatTotal();
         }
      }

      return total;
   }

   public String getReducedVatTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getReducedVatTotalNoLazy()));
      str = str.replaceAll("\\.", ",");
      return this.getReducedVatTotalNoLazy() != 0.0 ? str : "0,00";
   }

   public String getReducedVatTotalToDisplayLazy() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getReducedVatTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getReducedVatTotal() != 0.0 ? str : "0,00";
   }

   public Date getSaleDate() {
      return this.saleDate;
   }

   public void setSaleDate(Date saleDate) {
      this.saleDate = saleDate;
   }

   public double getCashAmount() {
      return this.cashAmount;
   }

   public void setCashAmount(double cashAmount) {
      this.cashAmount = cashAmount;
   }

   public double getCreditCardAmount() {
      return this.creditCardAmount;
   }

   public void setCreditCardAmount(double creditCardAmount) {
      this.creditCardAmount = creditCardAmount;
   }

   public double getOnAccountAmount() {
      return this.onAccountAmount;
   }

   public void setOnAccountAmount(double onAccountAmount) {
      this.onAccountAmount = onAccountAmount;
   }

   public double getDebitCardAmount() {
      return this.debitCardAmount;
   }

   public void setDebitCardAmount(double debitCardAmount) {
      this.debitCardAmount = debitCardAmount;
   }

   public double getCheckAmount() {
      return this.checkAmount;
   }

   public void setCheckAmount(double checkAmount) {
      this.checkAmount = checkAmount;
   }

   public double getTicketsAmount() {
      return this.ticketsAmount;
   }

   public void setTicketsAmount(double ticketsAmount) {
      this.ticketsAmount = ticketsAmount;
   }

   public String getCashAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getCashAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getCashAmount() != 0.0 ? str : "0,00";
   }

   public String getCreditCardAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getCreditCardAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getCreditCardAmount() != 0.0 ? str : "0,00";
   }

   public String getCreditCardNameToDisplay() {
      return this.getCreditCard() != null ? this.getCreditCard().getName() : "";
   }

   public String getDebitCardNameToDisplay() {
      return this.getDebitCard() != null ? this.getDebitCard().getName() : "";
   }

   public String getOnAccountAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getOnAccountAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getOnAccountAmount() != 0.0 ? str : "0,00";
   }

   public String getDebitCardAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getDebitCardAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getDebitCardAmount() != 0.0 ? str : "0,00";
   }

   public String getCheckAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getCheckAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getCheckAmount() != 0.0 ? str : "0,00";
   }

   public String getTicketsAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getTicketsAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getTicketsAmount() != 0.0 ? str : "0,00";
   }

   public String getReceiptNumberToDisplay() {
      return this.getReceiptNumber() != null ? this.getReceiptNumber() : "";
   }

   public String getObservationsToDisplay() {
      return this.getObservations() != null ? this.getObservations() : "";
   }

   public String getSaleDateToDisplay() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      String dateStr = formatter.format(this.getSaleDate());
      return dateStr;
   }

   public String getSaleDateToReport() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      String dateStr = formatter.format(this.getSaleDate());
      return dateStr;
   }

   public String getSaleHourToDisplay() {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
      String dateStr = formatter.format(this.getSaleDate());
      return dateStr;
   }

   public boolean isCompleted() {
      return "COMPLETED".equalsIgnoreCase(this.getStatus());
   }

   public boolean isPending() {
      return "PENDING".equalsIgnoreCase(this.getStatus());
   }

   public boolean isCancelled() {
      return "CANCELLED".equalsIgnoreCase(this.getStatus());
   }

   private String formatValueToDisplay(double value) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(value));
      str = str.replaceAll("\\.", ",");
      return value != 0.0 ? str : "0,00";
   }

   public double getInnerTaxes() {
      return this.innerTaxes;
   }

   public void setInnerTaxes(double innerTaxes) {
      this.innerTaxes = innerTaxes;
   }

   public ReceiptType getReceiptType() {
      return this.receiptType;
   }

   public void setReceiptType(ReceiptType receiptType) {
      this.receiptType = receiptType;
   }

   public boolean isInvoiceA() {
      boolean isA = false;
      if (this.getReceiptType() != null) {
         isA = this.getReceiptType().getName().equalsIgnoreCase("Factura A") || this.getReceiptType().getName().equalsIgnoreCase("Ticket Factura A");
      }

      return isA;
   }

   public boolean isInvoiceB() {
      boolean isB = false;
      if (this.getReceiptType() != null) {
         isB = this.getReceiptType().getName().equalsIgnoreCase("Factura B") || this.getReceiptType().getName().equalsIgnoreCase("Ticket Factura B");
      }

      return isB;
   }

   public boolean isInvoiceC() {
      boolean isC = false;
      if (this.getReceiptType() != null) {
         isC = this.getReceiptType().getName().equalsIgnoreCase("Factura C") || this.getReceiptType().getName().equalsIgnoreCase("Ticket Factura C");
      }

      return isC;
   }

   public boolean isRemito() {
      boolean isRemito = false;
      if (this.getReceiptType() != null) {
         isRemito = this.getReceiptType().getName().equalsIgnoreCase("Remito");
      }

      return isRemito;
   }

   public double getNetCashAmount() {
      return this.netCashAmount;
   }

   public void setNetCashAmount(double netCashAmount) {
      this.netCashAmount = netCashAmount;
   }

   public double getNetCreditCardAmount() {
      return this.netCreditCardAmount;
   }

   public void setNetCreditCardAmount(double netCreditCardAmount) {
      this.netCreditCardAmount = netCreditCardAmount;
   }

   public double getNetOnAccountAmount() {
      return this.netOnAccountAmount;
   }

   public void setNetOnAccountAmount(double netOnAccountAmount) {
      this.netOnAccountAmount = netOnAccountAmount;
   }

   public double getNetDebitCardAmount() {
      return this.netDebitCardAmount;
   }

   public void setNetDebitCardAmount(double netDebitCardAmount) {
      this.netDebitCardAmount = netDebitCardAmount;
   }

   public double getNetCheckAmount() {
      return this.netCheckAmount;
   }

   public void setNetCheckAmount(double netCheckAmount) {
      this.netCheckAmount = netCheckAmount;
   }

   public double getNetTicketsAmount() {
      return this.netTicketsAmount;
   }

   public void setNetTicketsAmount(double netTicketsAmount) {
      this.netTicketsAmount = netTicketsAmount;
   }

   public String getNetCashAmountToDisplay() {
      return this.formatValueToDisplay(this.getNetCashAmount());
   }

   public String getNetCreditCardAmountToDisplay() {
      return this.formatValueToDisplay(this.getNetCreditCardAmount());
   }

   public String getNetOnAccountAmountToDisplay() {
      return this.formatValueToDisplay(this.getNetOnAccountAmount());
   }

   public String getNetDebitCardAmountToDisplay() {
      return this.formatValueToDisplay(this.getNetDebitCardAmount());
   }

   public String getNetCheckAmountToDisplay() {
      return this.formatValueToDisplay(this.getNetCheckAmount());
   }

   public String getNetTicketsAmountToDisplay() {
      return this.formatValueToDisplay(this.getNetTicketsAmount());
   }

   public double getTotalTendered() {
      return this.getCashAmount() + this.getCreditCardAmount() + this.getOnAccountAmount() + this.getDebitCardAmount() + this.getCheckAmount() + this.getTicketsAmount();
   }

   public double getNetTotalTendered() {
      return this.getNetCashAmount() + this.getNetCreditCardAmount() + this.getNetOnAccountAmount() + this.getNetDebitCardAmount() + this.getNetCheckAmount() + this.getNetTicketsAmount();
   }

   public double getToPay() {
      return this.getTotal() - this.getNetTotalTendered();
   }

   public double getSurchargePercent() {
      return this.surchargePercent;
   }

   public void setSurchargePercent(double surchargePercent) {
      this.surchargePercent = surchargePercent;
   }

   public double getSurchargeAmount() {
      return this.surchargeAmount;
   }

   public void setSurchargeAmount(double surchargeAmount) {
      this.surchargeAmount = surchargeAmount;
   }

   public String getSurchargeAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getSurchargeAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getSurchargeAmount() != 0.0 ? str : "0,00";
   }

   public String getSurchargeToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getSurcharge()));
      str = str.replaceAll("\\.", ",");
      return this.getSurcharge() != 0.0 ? str : "0,00";
   }

   public String getSurchargePercentToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getSurchargePercent()));
      str = str.replaceAll("\\.", ",");
      return this.getSurchargePercent() != 0.0 ? str : "0,00";
   }

   public String getDiscountSurchargeToDisplay2() {
      if (this.getDiscountSurcharge() != 0.0) {
         return this.getDiscountSurcharge() < 0.0 ? "(" + this.getDiscountSurchargeToDisplay().replaceAll("-", "") + ")" : this.getDiscountSurchargeToDisplay();
      } else {
         return "0,00";
      }
   }

   public double getDiscountSurcharge() {
      return this.getSurcharge() - this.getDiscount();
   }

   public String getDiscountSurchargeToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getDiscountSurcharge()));
      str = str.replaceAll("\\.", ",");
      return this.getDiscountSurcharge() != 0.0 ? str : "0,00";
   }

   public String getDiscountSurchargeToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getDiscountSurcharge()));
      return this.getDiscountSurcharge() != 0.0 ? str : "0,00";
   }

   public double getNetDiscountSurcharge() {
      return this.getNetSurcharge() - this.getNetDiscount();
   }

   public String getNetDiscountSurchargeToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetDiscountSurcharge()));
      str = str.replaceAll("\\.", ",");
      return this.getNetDiscountSurcharge() != 0.0 ? str : "0,00";
   }

   public String getNetDiscountSurchargeToDisplay2() {
      if (this.getNetDiscount() != 0.0) {
         return this.getNetDiscount() < 0.0 ? "(" + this.getNetDiscountSurchargeToDisplay().replaceAll("-", "") + ")" : this.getNetDiscountSurchargeToDisplay();
      } else {
         return "0,00";
      }
   }

   public boolean isCustomSaleDate() {
      return this.customSaleDate;
   }

   public void setCustomSaleDate(boolean customSaleDate) {
      this.customSaleDate = customSaleDate;
   }

   public String getPtoVtaNroToPrint(AppConfig appConfig) {
      String strPtoVta = "";

      try {
         strPtoVta = String.valueOf(appConfig.getPtoVta());
         if (this.hasAfipCae()) {
            strPtoVta = String.valueOf(this.getAfipPtoVta());
         }

         while(strPtoVta.length() < 4) {
            strPtoVta = "0" + strPtoVta;
         }
      } catch (Exception var4) {
      }

      return strPtoVta;
   }

   public String getNroTicketToPrint(AppConfig appConfig) {
      String strNumber = "";

      try {
         String str = "";
         if (this.hasAfipCae()) {
            str = String.valueOf(this.getAfipCbteDesde());
         } else if (this.getReceiptNumber() != null) {
            str = this.getReceiptNumber();
         }

         while(str.length() < 8) {
            str = "0" + str;
         }

         strNumber = str;
      } catch (Exception var4) {
      }

      return strNumber;
   }

   public String getReceiptNumberToPrint(AppConfig appConfig) {
      String strNumber = "";

      try {
         String strPtoVta = String.valueOf(appConfig.getPtoVta());
         if (this.hasAfipCae() && !this.isRemito()) {
            strPtoVta = String.valueOf(this.getAfipPtoVta());
         }

         while(strPtoVta.length() < 4) {
            strPtoVta = "0" + strPtoVta;
         }

         String str = "";
         if (this.hasAfipCae() && !this.isRemito()) {
            str = String.valueOf(this.getAfipCbteDesde());
         } else if (this.getReceiptNumber() != null) {
            str = this.getReceiptNumber();
         }

         while(str.length() < 8) {
            str = "0" + str;
         }

         strNumber = strPtoVta + "-" + str;
      } catch (Exception var5) {
      }

      return strNumber;
   }

   public String getNotFiscalReceiptNumber(AppConfig appConfig) {
      String strNumber = "";

      try {
         String strPtoVta;
         for(strPtoVta = String.valueOf(appConfig.getPtoVta()); strPtoVta.length() < 4; strPtoVta = "0" + strPtoVta) {
         }

         String str = "";
         if (this.getReceiptNumber() != null) {
            str = this.getReceiptNumber();
         }

         while(str.length() < 8) {
            str = "0" + str;
         }

         strNumber = strPtoVta + "-" + str;
      } catch (Exception var5) {
      }

      return strNumber;
   }

   public String getFiscalReceiptNumber(AppConfig appConfig) {
      String strNumber = "";

      try {
         String strPtoVta;
         for(strPtoVta = String.valueOf(this.getAfipPtoVta()); strPtoVta.length() < 4; strPtoVta = "0" + strPtoVta) {
         }

         String str;
         for(str = String.valueOf(this.getAfipCbteDesde()); str.length() < 8; str = "0" + str) {
         }

         strNumber = strPtoVta + "-" + str;
      } catch (Exception var5) {
      }

      return strNumber;
   }

   public String getSaleDateToPrint() {
      String dateStr = "";

      try {
         if (this.hasAfipCae()) {
            dateStr = this.getAfipCbteFchToPrint();
         } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            dateStr = formatter.format(this.getSaleDate());
         }
      } catch (Exception var3) {
      }

      return dateStr;
   }

   public String getAfipCbteFchToPrint() {
      String dateStr = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipCbteFch());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateStr = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateStr;
   }

   public String getSaleConditionToDisplay() {
      String str = "CONTADO";
      if (this.getNetOnAccountAmount() > 0.0) {
         str = "CTA. CTE.";
      }

      return str;
   }

   public String getCustomerNameToPrint(int i) {
      String str = "";

      try {
         str = this.getCustomer().getFullName();
         if (str.contains("- Cliente Ocasional -")) {
            str = "";
         }

         if (str.length() > i) {
            str = str.substring(0, i);
         }
      } catch (Exception var4) {
      }

      return str;
   }

   public String getCreationDateForTicket() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
      String dateStr = formatter.format(this.getCreationDate());
      return dateStr;
   }

   public String getCreationHourForTicket() {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
      String dateStr = formatter.format(this.getCreationDate());
      return dateStr;
   }

   public int getDeliveryNoteNumber() {
      return this.deliveryNoteNumber;
   }

   public void setDeliveryNoteNumber(int deliveryNoteNumber) {
      this.deliveryNoteNumber = deliveryNoteNumber;
   }

   public String getDeliveryNoteNumberToDisplay() {
      return this.getDeliveryNoteNumber() > 0 ? String.valueOf(this.getDeliveryNoteNumber()) : "";
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }

   public int getAfipCbteTipo() {
      return this.afipCbteTipo;
   }

   public void setAfipCbteTipo(int afipCbteTipo) {
      this.afipCbteTipo = afipCbteTipo;
   }

   public int getAfipConcepto() {
      return this.afipConcepto;
   }

   public void setAfipConcepto(int afipConcepto) {
      this.afipConcepto = afipConcepto;
   }

   public int getAfipDocTipo() {
      return this.afipDocTipo;
   }

   public void setAfipDocTipo(int afipDocTipo) {
      this.afipDocTipo = afipDocTipo;
   }

   public Long getAfipDocNro() {
      return this.afipDocNro;
   }

   public void setAfipDocNro(Long afipDocNro) {
      this.afipDocNro = afipDocNro;
   }

   public Long getAfipCbteDesde() {
      return this.afipCbteDesde;
   }

   public void setAfipCbteDesde(Long afipCbteDesde) {
      this.afipCbteDesde = afipCbteDesde;
   }

   public Long getAfipCbteHasta() {
      return this.afipCbteHasta;
   }

   public void setAfipCbteHasta(Long afipCbteHasta) {
      this.afipCbteHasta = afipCbteHasta;
   }

   public String getAfipCbteFch() {
      return this.afipCbteFch;
   }

   public void setAfipCbteFch(String afipCbteFch) {
      this.afipCbteFch = afipCbteFch;
   }

   public int getAfipCbteTipo2() {
      if (this.isInvoiceA()) {
         return 1;
      } else if (this.isInvoiceB()) {
         return 6;
      } else {
         return this.isInvoiceC() ? 11 : 0;
      }
   }

   public int getAfipDocTipo2() {
      if (this.isInvoiceA()) {
         return 80;
      } else {
         return this.isInvoiceB() && this.getTotal() > 1000.0 ? 96 : 99;
      }
   }

   public String getAfipFchServDesde() {
      return this.afipFchServDesde;
   }

   public void setAfipFchServDesde(String afipFchServDesde) {
      this.afipFchServDesde = afipFchServDesde;
   }

   public String getAfipFchServHasta() {
      return this.afipFchServHasta;
   }

   public void setAfipFchServHasta(String afipFchServHasta) {
      this.afipFchServHasta = afipFchServHasta;
   }

   public String getAfipFchVtoPago() {
      return this.afipFchVtoPago;
   }

   public void setAfipFchVtoPago(String afipFchVtoPago) {
      this.afipFchVtoPago = afipFchVtoPago;
   }

   public String getAfipCae() {
      return this.afipCae;
   }

   public void setAfipCae(String afipCae) {
      this.afipCae = afipCae;
   }

   public String getAfipCaeFchVto() {
      return this.afipCaeFchVto;
   }

   public void setAfipCaeFchVto(String afipCaeFchVto) {
      this.afipCaeFchVto = afipCaeFchVto;
   }

   public boolean hasAfipCae() {
      return this.getAfipCae() != null && !this.getAfipCae().equals("");
   }

   public String getAfipCbteTipoForBarCode() {
      return this.getAfipCbteTipo() < 10 ? "0" + this.getAfipCbteTipo() : String.valueOf(this.getAfipCbteTipo());
   }

   public String getAfipCaeFchVtoToDisplay() {
      String dateToDisplay = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipCaeFchVto());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateToDisplay = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateToDisplay;
   }

   public String getAfipFchServDesdeToPrint() {
      String dateToDisplay = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipFchServDesde());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateToDisplay = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateToDisplay;
   }

   public String getAfipFchServHastaToPrint() {
      String dateToDisplay = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipFchServHasta());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateToDisplay = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateToDisplay;
   }

   public String getAfipFchVtoPagoToPrint() {
      String dateToDisplay = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipFchVtoPago());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateToDisplay = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateToDisplay;
   }

   public int getAfipPtoVta() {
      return this.afipPtoVta;
   }

   public void setAfipPtoVta(int afipPtoVta) {
      this.afipPtoVta = afipPtoVta;
   }

   public void initAfipBarCode() {
      if (this.hasAfipCae()) {
         String barCode = "";
         barCode = barCode + this.getAppConfigService().getAppConfig().getCompanyCuit();
         barCode = barCode + this.getAfipCbteTipoForBarCode();
         barCode = barCode + this.getAppConfigService().getAppConfig().getAfipPtoVtaForBarCode();
         barCode = barCode + this.getAfipCae();
         barCode = barCode + this.getAfipCaeFchVto();
         int controlDigit = AfipUtils.createAfipControlDigit(barCode);
         barCode = barCode + controlDigit;
         this.setAfipBarCode(barCode);
      }

   }

   private AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   public String getImpTotalConcToAfip() {
      return "0";
   }

   public String getImpNetoToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImpNeto()));
      str = str.replaceAll(",", "\\.");
      return this.getImpNeto() != 0.0 ? str : "0";
   }

   public String getImpNetoToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImpNeto()));
      str = str.replaceAll("\\.", ",");
      return this.getImpNeto() != 0.0 ? str : "0,00";
   }

   public String getImpNetoToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getImpNeto()));
      return this.getImpNeto() != 0.0 ? str : "0,00";
   }

   public String getImpIVAToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImpIVA()));
      str = str.replaceAll(",", "\\.");
      return this.getImpIVA() != 0.0 ? str : "0";
   }

   public String getImporteIVA21ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImporteIVA21()));
      str = str.replaceAll(",", "\\.");
      return this.getImporteIVA21() != 0.0 ? str : "0";
   }

   public String getImporteIVA21ToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImporteIVA21()));
      str = str.replaceAll("\\.", ",");
      return this.getImporteIVA21() != 0.0 ? str : "0,00";
   }

   public String getImporteIVA21ToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getImporteIVA21()));
      return this.getImporteIVA21() != 0.0 ? str : "0,00";
   }

   public double getImporteIVA21() {
      double impIVA = 0.0;

      OrderLine orderLine;
      try {
         for(Iterator var8 = this.orderLines.iterator(); var8.hasNext(); impIVA += orderLine.getImporteIVA21Subtotal()) {
            orderLine = (OrderLine)var8.next();
         }
      } catch (Exception var7) {
         List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);

        // OrderLine orderLine;
         for(Iterator var6 = orderLines.iterator(); var6.hasNext(); impIVA += orderLine.getImporteIVA21Subtotal()) {
            orderLine = (OrderLine)var6.next();
         }
      }

      impIVA += this.getImpIVADtoRecargo();
      return impIVA;
   }

   public double getImpIVADtoRecargo() {
      double ivaDiscountSurcharge = 0.0;

      try {
         if (this.getDiscountSurcharge() != 0.0) {
            ivaDiscountSurcharge = this.getDiscountSurcharge() - this.getNetoDtoRecargo();
         }
      } catch (Exception var4) {
      }

      return ivaDiscountSurcharge;
   }

   public String getImpIVADtoRecargoToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImpIVADtoRecargo()));
      str = str.replaceAll("\\.", ",");
      return this.getImpIVADtoRecargo() != 0.0 ? str : "0,00";
   }

   public String getBaseImpIVA21ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA21()));
      str = str.replaceAll(",", "\\.");
      return this.getBaseImpIVA21() != 0.0 ? str : "0";
   }

   public double getBaseImpIVA21() {
      double baseImpIVA = 0.0;
      List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);

      OrderLine orderLine;
      for(Iterator var5 = orderLines.iterator(); var5.hasNext(); baseImpIVA += orderLine.getBaseImpIVA21Subtotal()) {
         orderLine = (OrderLine)var5.next();
      }

      baseImpIVA += this.getNetoDtoRecargo();
      return baseImpIVA;
   }

   public double getNetoDtoRecargo() {
      double netDiscountSurcharge = 0.0;

      try {
         if (this.getDiscountSurcharge() != 0.0) {
            netDiscountSurcharge = this.getDiscountSurcharge() / 1.21;
         }
      } catch (Exception var4) {
      }

      return netDiscountSurcharge;
   }

   public String getNetoDtoRecargoToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetoDtoRecargo()));
      str = str.replaceAll("\\.", ",");
      return this.getNetoDtoRecargo() != 0.0 ? str : "0,00";
   }

   public String getNetoDtoRecargoToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getNetoDtoRecargo()));
      return this.getNetoDtoRecargo() != 0.0 ? str : "0,00";
   }

   public double getImporteIVA105() {
      double impIVA = 0.0;

      OrderLine orderLine;
      try {
         for(Iterator var8 = this.orderLines.iterator(); var8.hasNext(); impIVA += orderLine.getImporteIVA105Subtotal()) {
            orderLine = (OrderLine)var8.next();
         }
      } catch (Exception var7) {
         List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);

        // OrderLine orderLine;
         for(Iterator var6 = orderLines.iterator(); var6.hasNext(); impIVA += orderLine.getImporteIVA105Subtotal()) {
            orderLine = (OrderLine)var6.next();
         }
      }

      return impIVA;
   }

   public String getBaseImpIVA105ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA105()));
      str = str.replaceAll(",", "\\.");
      return this.getBaseImpIVA105() != 0.0 ? str : "0";
   }

   public double getBaseImpIVA105() {
      double baseImpIVA = 0.0;
      List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this);

      OrderLine orderLine;
      for(Iterator var5 = orderLines.iterator(); var5.hasNext(); baseImpIVA += orderLine.getBaseImpIVA105Subtotal()) {
         orderLine = (OrderLine)var5.next();
      }

      return baseImpIVA;
   }

   public String getImporteIVA105ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImporteIVA105()));
      str = str.replaceAll(",", "\\.");
      return this.getImporteIVA105() != 0.0 ? str : "0";
   }

   public String getImporteIVA105ToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImporteIVA105()));
      str = str.replaceAll("\\.", ",");
      return this.getImporteIVA105() != 0.0 ? str : "0,00";
   }

   public String getImporteIVA105ToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getImporteIVA105()));
      return this.getImporteIVA105() != 0.0 ? str : "0,00";
   }

   public String getAfipCbteTipoToDisplay() {
      String cbte = "";

      try {
         switch (this.getAfipCbteTipo()) {
            case 1:
               cbte = "Factura A";
               break;
            case 6:
               cbte = "Factura B";
               break;
            case 11:
               cbte = "Factura C";
         }
      } catch (Exception var3) {
      }

      return cbte;
   }

   public double getBaseImpIVA0() {
      return this.roundValue(this.getImpNeto() - this.getBaseImpIVA105() - this.getBaseImpIVA21());
   }

   public String getBaseImpIVA0ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA0()));
      str = str.replaceAll(",", "\\.");
      return this.getBaseImpIVA0() != 0.0 ? str : "0";
   }

   public String getAfipDocNroToPrint() {
      String str = "";

      try {
         String docNroStr = String.valueOf(this.getAfipDocNro());
         if (docNroStr.length() == 11) {
            try {
               String p1 = docNroStr.substring(0, 2);
               String p2 = docNroStr.substring(2, 10);
               String p3 = docNroStr.substring(10, 11);
               str = p1 + "-" + p2 + "-" + p3;
            } catch (Exception var6) {
               str = docNroStr;
            }
         } else {
            str = docNroStr;
         }
      } catch (Exception var7) {
         str = "";
      }

      return str;
   }

   public String getReceiptTypeNameToDisplay() {
      String name = "";

      try {
         name = this.getReceiptType().getName();
      } catch (Exception var3) {
      }

      return name;
   }

   public String getBaseImpIVA0ToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA0()));
      str = str.replaceAll("\\.", ",");
      return this.getBaseImpIVA0() != 0.0 ? str : "0,00";
   }

   public String getBaseImpIVA105ToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA105()));
      str = str.replaceAll("\\.", ",");
      return this.getBaseImpIVA105() != 0.0 ? str : "0,00";
   }

   public String getBaseImpIVA21ToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA21()));
      str = str.replaceAll("\\.", ",");
      return this.getBaseImpIVA21() != 0.0 ? str : "0,00";
   }
}
