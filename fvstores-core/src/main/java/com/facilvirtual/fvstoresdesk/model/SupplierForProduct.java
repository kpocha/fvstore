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
   name = "fvpos_supplier_for_product"
)
public class SupplierForProduct implements Serializable {
   private static final long serialVersionUID = -2808925193357025589L;
   @Id
   @Column(
      name = "supplier_for_product_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Column(
      name = "supplier_number",
      nullable = false
   )
   private int supplierNumber = 0;
   @Column(
      name = "cost_price",
      nullable = false
   )
   private double costPrice = 0.0;
   @Column(
      name = "default_supplier",
      nullable = false
   )
   private boolean defaultSupplier = false;
   @ManyToOne
   @JoinColumn(
      name = "supplier_id"
   )
   private Supplier supplier = null;
   @ManyToOne
   @JoinColumn(
      name = "product_id",
      nullable = false
   )
   private Product product = null;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "last_updated_date"
   )
   private Date lastUpdatedDate;

   public SupplierForProduct() {
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public int getSupplierNumber() {
      return this.supplierNumber;
   }

   public void setSupplierNumber(int supplierNumber) {
      this.supplierNumber = supplierNumber;
   }

   public double getCostPrice() {
      return this.costPrice;
   }

   public void setCostPrice(double costPrice) {
      this.costPrice = costPrice;
   }

   public boolean isDefaultSupplier() {
      return this.defaultSupplier;
   }

   public void setDefaultSupplier(boolean defaultSupplier) {
      this.defaultSupplier = defaultSupplier;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public Date getLastUpdatedDate() {
      return this.lastUpdatedDate;
   }

   public void setLastUpdatedDate(Date lastUpdatedDate) {
      this.lastUpdatedDate = lastUpdatedDate;
   }

   public String getCostPriceToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getCostPrice()));
      str = str.replaceAll("\\.", ",");
      return this.getCostPrice() != 0.0 ? str : "0,00";
   }

   public String getLastUpdatedDateToDisplay() {
      String dateStr = "";
      if (this.getLastUpdatedDate() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
         dateStr = formatter.format(this.getLastUpdatedDate());
      }

      return dateStr;
   }
}
