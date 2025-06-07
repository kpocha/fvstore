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
   name = "fvpos_budget"
)
public class Budget implements Serializable {
   protected static Logger logger = LoggerFactory.getLogger("Budget");
   private static final long serialVersionUID = 3910810390520371938L;
   @Id
   @Column(
      name = "budget_id"
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
      name = "budget_date",
      nullable = false
   )
   private Date budgetDate;
   @Column(
      name = "budget_number"
   )
   private int budgetNumber;
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
   @OneToMany(
      mappedBy = "budget",
      cascade = {CascadeType.ALL}
   )
   @OrderBy("lineNumber")
   private List<BudgetLine> budgetLines = new ArrayList();
   @ManyToOne
   @JoinColumn(
      name = "vat_condition_id",
      nullable = false
   )
   private VatCondition vatCondition;
   @ManyToOne
   @JoinColumn(
      name = "sale_condition_id",
      nullable = false
   )
   private SaleCondition saleCondition;
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
      name = "standard_vat_value",
      nullable = false
   )
   private double standardVatValue = 21.0;
   @Column(
      name = "reduced_vat_value",
      nullable = false
   )
   private double reducedVatValue = 10.5;

   public Budget() {
   }

   public int getBudgetNumber() {
      return this.budgetNumber;
   }

   public void setBudgetNumber(int budgetNumber) {
      this.budgetNumber = budgetNumber;
   }

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
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

   public Date getBudgetDate() {
      return this.budgetDate;
   }

   public void setBudgetDate(Date budgetDate) {
      this.budgetDate = budgetDate;
   }

   public List<BudgetLine> getBudgetLines() {
      return this.budgetLines;
   }

   public void setBudgetLines(List<BudgetLine> budgetLines) {
      this.budgetLines = budgetLines;
   }

   public VatCondition getVatCondition() {
      return this.vatCondition;
   }

   public void setVatCondition(VatCondition vatCondition) {
      this.vatCondition = vatCondition;
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

   public double getInnerTaxes() {
      return this.innerTaxes;
   }

   public void setInnerTaxes(double innerTaxes) {
      this.innerTaxes = innerTaxes;
   }

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }

   public int getItemsQty() {
      return this.itemsQty;
   }

   public void setItemsQty(int itemsQty) {
      this.itemsQty = itemsQty;
   }

   public void addBudgetLine(BudgetLine budgetLine) {
      this.budgetLines.add(budgetLine);
      ++this.itemsQty;
      this.subtotal += budgetLine.getSubtotal();
      this.total = this.calculateTotal();
   }

   public void removeBudgetLine(int lineNumber) {
      try {
         this.itemsQty = 0;
         this.subtotal = 0.0;
         this.total = 0.0;
         this.budgetLines.remove(lineNumber);

         for(Iterator var3 = this.budgetLines.iterator(); var3.hasNext(); this.total = this.calculateTotal()) {
            BudgetLine currentBudgetLine = (BudgetLine)var3.next();
            if (currentBudgetLine.getLineNumber() > lineNumber) {
               currentBudgetLine.setLineNumber(currentBudgetLine.getLineNumber() - 1);
            }

            ++this.itemsQty;
            this.subtotal += currentBudgetLine.getSubtotal();
         }
      } catch (Exception var4) {
         logger.error("Error al quitar artículo de presupuesto");
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

   public void clearBudget() {
      this.getBudgetLines().clear();
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

   public BudgetLine getBudgetLineByLineNumber(int idx) {
      return (BudgetLine)this.budgetLines.get(idx);
   }

   public BudgetLine getBudgetLineByBarCode(String barCode) {
      boolean found = false;
      BudgetLine budgetLine = null;
      Iterator<BudgetLine> it = this.getBudgetLines().iterator();

      while(!found && it.hasNext()) {
         BudgetLine currentBudgetLine = (BudgetLine)it.next();
         if (currentBudgetLine.getProduct() != null && barCode.equalsIgnoreCase(currentBudgetLine.getProduct().getBarCode())) {
            found = true;
            budgetLine = currentBudgetLine;
         }
      }

      return budgetLine;
   }

   public void updateTotal() {
      this.subtotal = 0.0;

      BudgetLine budgetLine;
      for(Iterator var2 = this.budgetLines.iterator(); var2.hasNext(); this.subtotal += budgetLine.getSubtotal()) {
         budgetLine = (BudgetLine)var2.next();
      }

      this.setTotal(this.calculateTotal());
   }

   public String getStatusToDisplay() {
      if ("PENDING".equalsIgnoreCase(this.getStatus())) {
         return "Pendiente";
      } else {
         return "CANCELLED".equalsIgnoreCase(this.getStatus()) ? "Anulado" : "Completo";
      }
   }

   public String getCreationDateToDisplay() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      String dateStr = formatter.format(this.getCreationDate());
      return dateStr;
   }

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
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

   public double getNetSubtotal() {
      double netSubtotal = 0.0;

      BudgetLine budgetLine;
      for(Iterator var4 = this.getBudgetLines().iterator(); var4.hasNext(); netSubtotal += budgetLine.getNetSubtotal()) {
         budgetLine = (BudgetLine)var4.next();
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
      Iterator var4 = this.getBudgetLines().iterator();

      while(var4.hasNext()) {
         BudgetLine budgetLine = (BudgetLine)var4.next();
         if (budgetLine.getVatValue() == this.getStandardVatValue()) {
            total += budgetLine.getVatTotal();
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
      Iterator var4 = this.getBudgetLines().iterator();

      while(var4.hasNext()) {
         BudgetLine budgetLine = (BudgetLine)var4.next();
         if (budgetLine.getVatValue() == this.getReducedVatValue()) {
            total += budgetLine.getVatTotal();
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

   public String getBudgetDateToDisplay() {
      String str = "";
      if (this.getCreationDate() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
         str = formatter.format(this.getBudgetDate());
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

   public String getBudgetNumberToPrint() {
      String str = "";
      if (this.getBudgetNumber() != 0) {
         str = String.valueOf(this.getBudgetNumber());
         if (!str.equals("")) {
            while(str.length() < 8) {
               str = "0" + str;
            }
         }
      }

      return str;
   }

   public String getBudgetDateToPrint() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      String dateStr = formatter.format(this.getBudgetDate());
      return dateStr;
   }

   public String getObservationsToDisplay() {
      return this.getObservations() != null ? this.getObservations() : "";
   }

   public String getVatConditionToDisplay() {
      String str = "";
      if (this.getVatCondition() != null) {
         str = this.getVatCondition().getName();
      }

      return str;
   }

   public String getCustomerNameToPrint() {
      String str = this.getCustomer().getFullName();
      if (str.contains("- Cliente Ocasional -")) {
         str = "";
      }

      return str;
   }

   public SaleCondition getSaleCondition() {
      return this.saleCondition;
   }

   public void setSaleCondition(SaleCondition saleCondition) {
      this.saleCondition = saleCondition;
   }

   public String getSaleConditionToDisplay() {
      String str = "";
      if (this.getSaleCondition() != null) {
         str = this.getSaleCondition().getName();
      }

      return str;
   }

   public String getSaleConditionToPrint() {
      String str = "";
      if (this.getSaleCondition() != null) {
         str = this.getSaleCondition().getAbbreviateName();
      }

      return str;
   }
}
