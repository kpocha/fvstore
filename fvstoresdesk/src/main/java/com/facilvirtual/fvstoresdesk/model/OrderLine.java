package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(
   name = "fvpos_order_line"
)
public class OrderLine implements Serializable, Comparable<OrderLine> {
   private static final long serialVersionUID = -6076040657551314512L;
   @Id
   @Column(
      name = "order_line_id"
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
   @Column(
      name = "in_offer",
      nullable = false
   )
   private boolean inOffer = false;
   @ManyToOne
   @JoinColumn(
      name = "product_id"
   )
   private Product product = null;
   @ManyToOne
   @JoinColumn(
      name = "category_id"
   )
   private ProductCategory category = null;
   @ManyToOne
   @JoinColumn(
      name = "order_id"
   )
   private Order order;
   @Column(
      name = "description"
   )
   private String description = "";
   @Column(
      name = "cost_price",
      nullable = false
   )
   private Double costPrice = 0.0;
   @Column(
      name = "internal_tax",
      nullable = false
   )
   private Double internalTax = 0.0;
   @Column(
      name = "profit_margin",
      nullable = false
   )
   private Double profitMargin = 0.0;

   public OrderLine() {
   }

   public Double getCostPrice() {
      return this.costPrice;
   }

   public void setCostPrice(Double costPrice) {
      this.costPrice = costPrice;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Order getOrder() {
      return this.order;
   }

   public void setOrder(Order order) {
      this.order = order;
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

   public double getVatValue() {
      return this.vatValue;
   }

   public void setVatValue(double vatValue) {
      this.vatValue = vatValue;
   }

   public boolean isInOffer() {
      return this.inOffer;
   }

   public void setInOffer(boolean inOffer) {
      this.inOffer = inOffer;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public ProductCategory getCategory() {
      return this.category;
   }

   public void setCategory(ProductCategory category) {
      this.category = category;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getPriceToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String priceStr = String.valueOf(formatter.format(this.getPrice()));
      priceStr = priceStr.replaceAll("\\.", ",");
      return this.getPrice() != 0.0 ? priceStr : "0,00";
   }

   public String getPriceToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String priceStr = String.valueOf(formatter.format(this.getPrice()));
      return this.getPrice() != 0.0 ? priceStr : "0,00";
   }

   public String getSubtotalToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String subtotalStr = String.valueOf(formatter.format(this.getSubtotal()));
      return this.getSubtotal() != 0.0 ? subtotalStr : "0,00";
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

   public String getQtyToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###", simbolos);
      if (this.getProduct() != null && this.getProduct().isPerWeight()) {
         formatter = new DecimalFormat("###,###,###.000");
      }

      String str = String.valueOf(formatter.format(this.getQty()));
      return str;
   }

   public void incQty(double incQty) {
      this.setQty(this.getQty() + incQty);
   }

   public double getSubtotal() {
      return this.roundValue(this.getPrice() * this.getQty());
   }

   public double getCostSubtotal() {
      return this.roundValue(this.getCostPrice() * this.getQty());
   }

   public double getNetSubtotal() {
      return this.roundValue(this.getNetPrice() * this.getQty());
   }

   public double getImpNetoSubtotal() {
      return this.getImpNeto() * this.getQty();
   }

   public double getImpIVASubtotal() {
      return this.getImpIVA() * this.getQty();
   }

   public double getSubtotalWithoutVat() {
      return this.roundValue(this.getPriceWithoutVat() * this.getQty());
   }

   public double getPriceWithoutVat() {
      double valueWithoutVat = this.getPrice() - this.getVatAmount();
      return this.roundValue(valueWithoutVat);
   }

   public double getNetPrice() {
      double netValue = this.getPrice() / (1.0 + this.getVatValue() / 100.0);
      return netValue;
   }

   public double getImpNeto() {
      double netValue = this.getPrice() / (1.0 + this.getVatValue() / 100.0);
      return netValue;
   }

   public double getImpIVA() {
      double ivaValue = this.getPrice() - this.getImpNeto();
      return ivaValue;
   }

   public double getInternalTaxReal() {
      double value = 0.0;
      if (this.getInternalTax() > 0.0) {
         value = 100.0 * this.getInternalTax() / (100.0 - this.getInternalTax());
      }

      return value;
   }

   public double getInternalTaxAmount() {
      double internalTaxAmount = 0.0;
      if (this.getInternalTax() > 0.0) {
         internalTaxAmount = this.getNetPrice() * this.getInternalTaxReal() / 100.0;
      }

      return this.roundValue(internalTaxAmount);
   }

   private double roundValue(double valueToRound) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(valueToRound));
      return Double.valueOf(str.replaceAll(",", "\\."));
   }

   public String getNetPriceToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetPrice()));
      str = str.replaceAll("\\.", ",");
      return this.getNetPrice() != 0.0 ? str : "0,00";
   }

   public String getNetPriceToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getNetPrice()));
      return this.getNetPrice() != 0.0 ? str : "0,00";
   }

   public String getNetSubtotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getNetSubtotal()));
      str = str.replaceAll("\\.", ",");
      return this.getNetSubtotal() != 0.0 ? str : "0,00";
   }

   public String getNetSubtotalToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getNetSubtotal()));
      return this.getNetSubtotal() != 0.0 ? str : "0,00";
   }

   public double getVatTotal() {
      return this.getVatAmount() * this.getQty();
   }

   public String getVatValueToDisplay() {
      if (this.getVatValue() == 21.0) {
         return "21%";
      } else {
         return this.getVatValue() == 10.5 ? "10,5%" : "0%";
      }
   }

   public double getVatAmount() {
      double value = this.getNetPrice() * (this.getVatValue() / 100.0);
      return value;
   }

   public String getVatAmountToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getVatAmount()));
      str = str.replaceAll("\\.", ",");
      return this.getVatAmount() != 0.0 ? str : "0,00";
   }

   public String getVatTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getVatTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getVatTotal() != 0.0 ? str : "0,00";
   }

   public String getDescriptionToPrint(int size) {
      String desc = "";
      String newDesc = "";

      try {
         if (this.getProduct() != null) {
            desc = this.getProduct().getDescription();
         } else {
            desc = this.getCategory().getName();
         }

         try {
            newDesc = desc.substring(0, size).toUpperCase();
         } catch (Exception var5) {
            newDesc = desc.toUpperCase();
         }
      } catch (Exception var6) {
      }

      return newDesc;
   }

   public String getDescriptionLine1ToPrint(int i) {
      String desc = "";

      try {
         if (this.getProduct() != null) {
            desc = this.getProduct().getDescription();
         } else {
            desc = this.getCategory().getName();
         }

         if (desc.length() > i) {
            desc = desc.substring(0, i);
         }
      } catch (Exception var4) {
      }

      return desc;
   }

   public String getDescriptionLine2ToPrint(int i) {
      String desc = "";

      try {
         if (this.getProduct() != null) {
            desc = this.getProduct().getDescription();
         } else {
            desc = this.getCategory().getName();
         }

         if (desc.length() > i) {
            int j = i * 2;
            if (desc.length() > j) {
               desc = desc.substring(i, j);
            } else {
               desc = desc.substring(i);
            }
         } else {
            desc = "";
         }
      } catch (Exception var4) {
      }

      return desc;
   }

   public String getCodeToPrint(int i) {
      String codeStr = "";
      if (this.getProduct() != null) {
         codeStr = this.getProduct().getBarCode();
      } else {
         codeStr = this.getCategory().getCode();
      }

      if (codeStr.length() > i) {
         codeStr = codeStr.substring(0, i);
      }

      return codeStr;
   }

   public String getDescriptionWithCodeToPrint(int size) {
      String newDesc = "";

      try {
         String strBarCode;
         String description;
         if (this.getProduct() != null) {
            strBarCode = this.getProduct().getBarCode();
            if (strBarCode != null && strBarCode.length() > 6) {
               strBarCode = strBarCode.substring(strBarCode.length() - 6, strBarCode.length());
            }

            description = this.getProduct().getDescription();
            if (description != null && description.length() > size) {
               description = description.substring(0, size);
            }

            newDesc = strBarCode + " " + description;
         } else {
            strBarCode = "D" + this.getCategory().getNumberToDisplay();
            description = this.getCategory().getName();
            newDesc = strBarCode + " " + description;
            int totalSize = size + 6;
            if (newDesc != null && newDesc.length() > totalSize) {
               newDesc = newDesc.substring(0, totalSize);
            }
         }
      } catch (Exception var6) {
      }

      newDesc = newDesc.toUpperCase();
      return newDesc;
   }

   public String getQtyForTicket() {
      new DecimalFormat("0");
      DecimalFormat formatter = new DecimalFormat("0.000");
      String str = String.valueOf(formatter.format(this.getQty()));
      return str;
   }

   public String getQtyDescriptionForTicket() {
      return this.getQtyForTicket() + " x " + this.getPriceToDisplay();
   }

   public String getQtyDescriptionForTicketA() {
      return this.getQtyForTicket() + " x " + this.getNetPriceToDisplay();
   }

   public Double getProfitMargin() {
      return this.profitMargin;
   }

   public void setProfitMargin(Double profitMargin) {
      this.profitMargin = profitMargin;
   }

   public double calculateProfitMargin() {
      double profitMargin = 0.0;
      if (this.getCostPrice() != 0.0) {
         profitMargin = (this.getNetPrice() / this.getCostPrice() - 1.0) * 100.0;
      }

      return profitMargin;
   }

   public String getProfitMarginToDisplay() {
      double profitMargin = this.getProfitMargin();
      if (profitMargin == 0.0 && this.getInternalTax() == 0.0) {
         profitMargin = this.calculateProfitMargin();
      }

      new DecimalFormat("0");
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(profitMargin));
      return str;
   }

   public Double getProfit() {
      return this.getSubtotal() - this.getCostSubtotal();
   }

   public int compareTo(OrderLine o) {
      return 0;
   }

   public Double getInternalTax() {
      return this.internalTax;
   }

   public void setInternalTax(Double internalTax) {
      this.internalTax = internalTax;
   }

   public double getInternalTaxTotal() {
      return this.getInternalTaxAmount() * this.getQty();
   }

   public double getImporteIVA21Subtotal() {
      return this.getImporteIVA21() * this.getQty();
   }

   public double getImporteIVA21() {
      if (this.getVatValue() == 21.0) {
         double netPrice = this.getPrice() / (1.0 + this.getVatValue() / 100.0);
         return this.getPrice() - netPrice;
      } else {
         return 0.0;
      }
   }

   public double getBaseImpIVA21Subtotal() {
      return this.getBaseImpIVA21() * this.getQty();
   }

   public double getBaseImpIVA21() {
      if (this.getVatValue() == 21.0) {
         double netPrice = this.getPrice() / (1.0 + this.getVatValue() / 100.0);
         return netPrice;
      } else {
         return 0.0;
      }
   }

   public double getImporteIVA105Subtotal() {
      return this.getImporteIVA105() * this.getQty();
   }

   public double getImporteIVA105() {
      if (this.getVatValue() == 10.5) {
         double netPrice = this.getPrice() / (1.0 + this.getVatValue() / 100.0);
         return this.getPrice() - netPrice;
      } else {
         return 0.0;
      }
   }

   public double getBaseImpIVA105Subtotal() {
      return this.getBaseImpIVA105() * this.getQty();
   }

   public double getBaseImpIVA105() {
      if (this.getVatValue() == 10.5) {
         double netPrice = this.getPrice() / (1.0 + this.getVatValue() / 100.0);
         return netPrice;
      } else {
         return 0.0;
      }
   }

   public String getVatValueToPrint() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getVatValue()));
      str = str.replaceAll("\\.", ",");
      return this.getVatValue() != 0.0 ? str : "0,00";
   }
}
