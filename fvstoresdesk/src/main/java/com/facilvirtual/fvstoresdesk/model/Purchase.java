package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.DecimalFormat;
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
@Entity
@Table(
   name = "fvpos_purchase"
)
public class Purchase implements Serializable {
   protected static Logger logger = LoggerFactory.getLogger("Purchase");
   private static final long serialVersionUID = -461674505883461178L;
   @Id
   @Column(
      name = "purchase_id"
   )
   @GeneratedValue
   private Long id;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "creation_date",
      nullable = false
   )
   private Date creationDate;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "purchase_date",
      nullable = false
   )
   private Date purchaseDate;
   @Column(
      name = "custom_purchase_date",
      nullable = false
   )
   private boolean customPurchaseDate = false;
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
   private String receiptNumber = "";
   @OneToMany(
      mappedBy = "purchase",
      cascade = {CascadeType.ALL}
   )
   @OrderBy("lineNumber")
   private List<PurchaseLine> purchaseLines = new ArrayList();
   @ManyToOne
   @JoinColumn(
      name = "vat_condition_id"
   )
   private VatCondition vatCondition;
   @ManyToOne
   @JoinColumn(
      name = "supplier_id"
   )
   private Supplier supplier;
   @ManyToOne
   @JoinColumn(
      name = "cashier_id",
      nullable = false
   )
   private Employee cashier;
   @Column(
      name = "payment_cash_amt",
      nullable = false
   )
   private double cashAmount = 0.0;
   @Column(
      name = "payment_card_amt",
      nullable = false
   )
   private double cardAmount = 0.0;
   @Column(
      name = "payment_on_account_amt",
      nullable = false
   )
   private double onAccountAmount = 0.0;
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
   @Column(
      name = "is_update_prices",
      nullable = false
   )
   private boolean updatePrices = true;
   @Column(
      name = "is_update_stock",
      nullable = false
   )
   private boolean updateStock = true;
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

   public Purchase() {
   }

   public boolean isUpdateStock() {
      return this.updateStock;
   }

   public void setUpdateStock(boolean updateStock) {
      this.updateStock = updateStock;
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

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }

   public List<PurchaseLine> getPurchaseLines() {
      return this.purchaseLines;
   }

   public void setPurchaseLines(List<PurchaseLine> purchaseLines) {
      this.purchaseLines = purchaseLines;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
   }

   public int getItemsQty() {
      return this.itemsQty;
   }

   public void setItemsQty(int itemsQty) {
      this.itemsQty = itemsQty;
   }

   public VatCondition getVatCondition() {
      return this.vatCondition;
   }

   public void setVatCondition(VatCondition vatCondition) {
      this.vatCondition = vatCondition;
   }

   public void addPurchaseLine(PurchaseLine purchaseLine) {
      this.purchaseLines.add(purchaseLine);
      ++this.itemsQty;
      this.subtotal += purchaseLine.getSubtotal();
      this.total = this.calculateTotal();
   }

   public void removePurchaseLine(int lineNumber) {
      try {
         this.itemsQty = 0;
         this.subtotal = 0.0;
         this.total = 0.0;
         this.purchaseLines.remove(lineNumber);

         for(Iterator var3 = this.purchaseLines.iterator(); var3.hasNext(); this.total = this.calculateTotal()) {
            PurchaseLine currentPurchaseLine = (PurchaseLine)var3.next();
            if (currentPurchaseLine.getLineNumber() > lineNumber) {
               currentPurchaseLine.setLineNumber(currentPurchaseLine.getLineNumber() - 1);
            }

            ++this.itemsQty;
            this.subtotal += currentPurchaseLine.getSubtotal();
         }
      } catch (Exception var4) {
         logger.error("Error al quitar artículo de compra");
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

   }

   public double calculateTotal() {
      return this.subtotal + this.getInnerTaxes();
   }

   public String getSubtotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String subtotalStr = String.valueOf(formatter.format(this.getSubtotal()));
      subtotalStr = subtotalStr.replaceAll("\\.", ",");
      return this.getSubtotal() != 0.0 ? subtotalStr : "0,00";
   }

   public String getTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getTotal() != 0.0 ? str : "0,00";
   }

   public String getTotalToDisplay2() {
      return this.getTotal() < 0.0 ? "(" + this.getTotalToDisplay().replaceAll("-", "") + ")" : this.getTotalToDisplay();
   }

   public void clearPurchase() {
      this.getPurchaseLines().clear();
      this.setItemsQty(0);
      this.setSubtotal(0.0);
      this.setInnerTaxes(0.0);
      this.setTotal(0.0);
      this.setCreationDate(new Date());
   }

   public String getInnerTaxesToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getInnerTaxes()));
      str = str.replaceAll("\\.", ",");
      return this.getInnerTaxes() != 0.0 ? str : "0,00";
   }

   public PurchaseLine getPurchaseLineByLineNumber(int idx) {
      return (PurchaseLine)this.purchaseLines.get(idx);
   }

   public PurchaseLine getPurchaseLineByBarCode(String barCode) {
      boolean found = false;
      PurchaseLine purchaseLine = null;
      Iterator<PurchaseLine> it = this.getPurchaseLines().iterator();

      while(!found && it.hasNext()) {
         PurchaseLine currentPurchaseLine = (PurchaseLine)it.next();
         if (currentPurchaseLine.getProduct() != null && barCode.equalsIgnoreCase(currentPurchaseLine.getProduct().getBarCode())) {
            found = true;
            purchaseLine = currentPurchaseLine;
         }
      }

      return purchaseLine;
   }

   public void updateTotal() {
      this.subtotal = 0.0;

      PurchaseLine purchaseLine;
      for(Iterator var2 = this.purchaseLines.iterator(); var2.hasNext(); this.subtotal += purchaseLine.getSubtotal()) {
         purchaseLine = (PurchaseLine)var2.next();
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

   public Date getPurchaseDate() {
      return this.purchaseDate;
   }

   public void setPurchaseDate(Date purchaseDate) {
      this.purchaseDate = purchaseDate;
   }

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   public double getCashAmount() {
      return this.cashAmount;
   }

   public void setCashAmount(double cashAmount) {
      this.cashAmount = cashAmount;
   }

   public double getCardAmount() {
      return this.cardAmount;
   }

   public void setCardAmount(double cardAmount) {
      this.cardAmount = cardAmount;
   }

   public double getOnAccountAmount() {
      return this.onAccountAmount;
   }

   public void setOnAccountAmount(double onAccountAmount) {
      this.onAccountAmount = onAccountAmount;
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

   public int getPosNumber() {
      return this.posNumber;
   }

   public void setPosNumber(int posNumber) {
      this.posNumber = posNumber;
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

   public boolean isUpdatePrices() {
      return this.updatePrices;
   }

   public void setUpdatePrices(boolean updatePrices) {
      this.updatePrices = updatePrices;
   }

   public double getNetSubtotal() {
      double netSubtotal = 0.0;

      PurchaseLine purchaseLine;
      for(Iterator var4 = this.getPurchaseLines().iterator(); var4.hasNext(); netSubtotal += purchaseLine.getNetSubtotal()) {
         purchaseLine = (PurchaseLine)var4.next();
      }

      return netSubtotal;
   }

   public String getNetSubtotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetSubtotal()));
      str = str.replaceAll("\\.", ",");
      return this.getNetSubtotal() != 0.0 ? str : "0,00";
   }

   public double getNetSubtotal2() {
      double netSubtotal2 = this.getNetSubtotal();
      netSubtotal2 += this.getInnerTaxes();
      return netSubtotal2;
   }

   public String getNetSubtotal2ToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetSubtotal2()));
      str = str.replaceAll("\\.", ",");
      return this.getNetSubtotal2() != 0.0 ? str : "0,00";
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

   public double getStandardVatTotal() {
      double total = 0.0;
      Iterator var4 = this.getPurchaseLines().iterator();

      while(var4.hasNext()) {
         PurchaseLine purchaseLine = (PurchaseLine)var4.next();
         if (purchaseLine.getVatValue() == this.getStandardVatValue()) {
            total += purchaseLine.getVatTotal();
         }
      }

      return total;
   }

   public String getStandardVatTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getStandardVatTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getStandardVatTotal() != 0.0 ? str : "0,00";
   }

   public double getReducedVatTotal() {
      double total = 0.0;
      Iterator var4 = this.getPurchaseLines().iterator();

      while(var4.hasNext()) {
         PurchaseLine purchaseLine = (PurchaseLine)var4.next();
         if (purchaseLine.getVatValue() == this.getReducedVatValue()) {
            total += purchaseLine.getVatTotal();
         }
      }

      return total;
   }

   public String getReducedVatTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getReducedVatTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getReducedVatTotal() != 0.0 ? str : "0,00";
   }

   public String getPurchaseDateToDisplay() {
      String str = "";
      if (this.getCreationDate() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
         str = formatter.format(this.getPurchaseDate());
      }

      return str;
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

   public String getCashAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getCashAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getCashAmount() != 0.0 ? str : "0,00";
   }

   public String getCardAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getCardAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getCardAmount() != 0.0 ? str : "0,00";
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

   public String getOnAccountAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getOnAccountAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getOnAccountAmount() != 0.0 ? str : "0,00";
   }

   public boolean isCustomPurchaseDate() {
      return this.customPurchaseDate;
   }

   public void setCustomPurchaseDate(boolean customPurchaseDate) {
      this.customPurchaseDate = customPurchaseDate;
   }

   public boolean isInvoiceA() {
      boolean isA = false;
      if (this.getReceiptType() != null) {
         isA = this.getReceiptType().getName().equalsIgnoreCase("Factura A") || this.getReceiptType().getName().equalsIgnoreCase("Ticket Factura A");
      }

      return isA;
   }
}
