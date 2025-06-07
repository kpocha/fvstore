package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(
   name = "fvpos_budget_line"
)
public class BudgetLine implements Serializable {
   private static final long serialVersionUID = -923934857525866314L;
   @Id
   @Column(
      name = "budget_line_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Column(
      name = "line_number",
      nullable = false
   )
   private int lineNumber = 0;
   @Column(
      name = "qty",
      nullable = false
   )
   private double qty = 0.0;
   @Column(
      name = "price",
      nullable = false
   )
   private double price = 0.0;
   @Column(
      name = "vat_value",
      nullable = false
   )
   private double vatValue = 0.0;
   @ManyToOne
   @JoinColumn(
      name = "product_id"
   )
   private Product product = null;
   @ManyToOne
   @JoinColumn(
      name = "budget_id"
   )
   private Budget budget;
   @Column(
      name = "description"
   )
   private String description = "";

   public BudgetLine() {
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Budget getBudget() {
      return this.budget;
   }

   public void setBudget(Budget budget) {
      this.budget = budget;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public void setLineNumber(int lineNumber) {
      this.lineNumber = lineNumber;
   }

   public double getQty() {
      return this.qty;
   }

   public void setQty(double qty) {
      this.qty = qty;
   }

   public double getPrice() {
      return this.price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public double getSubtotal() {
      return this.roundValue(this.getQty() * this.getPrice());
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public double getVatValue() {
      return this.vatValue;
   }

   public void setVatValue(double vatValue) {
      this.vatValue = vatValue;
   }

   public double getNetSubtotal() {
      return this.roundValue(this.getNetPrice() * this.getQty());
   }

   public double getNetPrice() {
      return this.roundValue(this.getPrice() / (1.0 + this.getVatValue() / 100.0));
   }

   public String getPriceToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String priceStr = String.valueOf(formatter.format(this.getPrice()));
      priceStr = priceStr.replaceAll("\\.", ",");
      return this.getPrice() != 0.0 ? priceStr : "0,00";
   }

   public String getNetPriceToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String priceStr = String.valueOf(formatter.format(this.getNetPrice()));
      priceStr = priceStr.replaceAll("\\.", ",");
      return this.getNetPrice() != 0.0 ? priceStr : "0,00";
   }

   public String getSubtotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String subtotalStr = String.valueOf(formatter.format(this.getSubtotal()));
      subtotalStr = subtotalStr.replaceAll("\\.", ",");
      return this.getSubtotal() != 0.0 ? subtotalStr : "0,00";
   }

   public String getQtyToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0");
      if (this.getProduct() != null && this.getProduct().isPerWeight()) {
         formatter = new DecimalFormat("0.000");
      }

      String str = String.valueOf(formatter.format(this.getQty()));
      return str;
   }

   public void incQty(double incQty) {
      this.setQty(this.getQty() + incQty);
   }

   private double roundValue(double valueToRound) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(valueToRound));
      return Double.valueOf(str.replaceAll(",", "\\."));
   }

   public String getVatValueToDisplay() {
      if (this.getVatValue() == 21.0) {
         return "21%";
      } else {
         return this.getVatValue() == 10.5 ? "10,5%" : "0%";
      }
   }

   public String getNetSubtotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetSubtotal()));
      str = str.replaceAll("\\.", ",");
      return this.getNetSubtotal() != 0.0 ? str : "0,00";
   }

   public double getVatTotal() {
      return this.getSubtotal() - this.getNetSubtotal();
   }

   public String getDescriptionToPrint() {
      String desc = "";
      if (this.getProduct() != null) {
         desc = this.getProduct().getDescriptionForBudget();
      }

      return StringUtils.abbreviate(desc, 40).toUpperCase();
   }
}
