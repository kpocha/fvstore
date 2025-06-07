package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(
   name = "fvpos_supplier_on_account_op"
)
public class SupplierOnAccountOperation implements Serializable {
   private static final long serialVersionUID = 4207124593862636437L;
   @Id
   @Column(
      name = "operation_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "creation_date"
   )
   private Date creationDate;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "operation_date"
   )
   private Date operationDate;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "last_updated_date"
   )
   private Date lastUpdatedDate;
   @Column(
      name = "is_active",
      nullable = false
   )
   private boolean active = true;
   @Column(
      name = "type",
      nullable = false
   )
   private int type = 0;
   @Column(
      name = "description"
   )
   private String description = "";
   @Column(
      name = "observations"
   )
   private String observations = "";
   @Column(
      name = "amount",
      nullable = false
   )
   private double amount = 0.0;
   @Column(
      name = "is_system",
      nullable = false
   )
   private boolean system = false;
   @Column(
      name = "cash_number",
      nullable = false
   )
   private int cashNumber = 0;
   @ManyToOne
   @JoinColumn(
      name = "author_id"
   )
   private Employee author = null;
   @ManyToOne
   @JoinColumn(
      name = "purchase_id"
   )
   private Purchase purchase = null;
   @ManyToOne
   @JoinColumn(
      name = "supplier_id"
   )
   private Supplier supplier = null;

   public SupplierOnAccountOperation() {
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

   public Date getLastUpdatedDate() {
      return this.lastUpdatedDate;
   }

   public void setLastUpdatedDate(Date lastUpdatedDate) {
      this.lastUpdatedDate = lastUpdatedDate;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getObservations() {
      return this.observations;
   }

   public void setObservations(String observations) {
      this.observations = observations;
   }

   public Employee getAuthor() {
      return this.author;
   }

   public void setAuthor(Employee author) {
      this.author = author;
   }

   public Purchase getPurchase() {
      return this.purchase;
   }

   public void setPurchase(Purchase purchase) {
      this.purchase = purchase;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
   }

   public boolean isSystem() {
      return this.system;
   }

   public void setSystem(boolean system) {
      this.system = system;
   }

   public int getCashNumber() {
      return this.cashNumber;
   }

   public void setCashNumber(int cashNumber) {
      this.cashNumber = cashNumber;
   }

   public int getType() {
      return this.type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public double getAmount() {
      return this.amount;
   }

   public void setAmount(double amount) {
      this.amount = amount;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public void setCreditType() {
      this.setType(1);
   }

   public void setDebitType() {
      this.setType(2);
   }

   public void setBalanceType() {
      this.setType(3);
   }

   public boolean isCreditType() {
      return this.getType() == 1;
   }

   public boolean isDebitType() {
      return this.getType() == 2;
   }

   public boolean isBalanceType() {
      return this.getType() == 3;
   }

   public String getCreationDateToDisplay() {
      String str = "";
      if (this.getCreationDate() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
         str = formatter.format(this.getCreationDate());
      }

      return str;
   }

   public String getAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getAmount() != 0.0 ? str : "0,00";
   }

   public Date getOperationDate() {
      return this.operationDate;
   }

   public void setOperationDate(Date operationDate) {
      this.operationDate = operationDate;
   }

   public String getOperationDateToDisplay() {
      String str = "";
      if (this.getOperationDate() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
         str = formatter.format(this.getOperationDate());
      }

      return str;
   }
}
