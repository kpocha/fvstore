package com.facilvirtual.fvstoresdesk.model;

import com.facilvirtual.fvstoresdesk.util.FVMathUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Entity
@Table(
   name = "fvpos_product_price"
)
public class ProductPrice implements Serializable {
   protected static Logger logger = LoggerFactory.getLogger("ProductPrice");
   private static final long serialVersionUID = 8326994427956715466L;
   @Id
   @Column(
      name = "price_id"
   )
   @GeneratedValue
   private Long id;
   @ManyToOne
   @JoinColumn(
      name = "product_id"
   )
   private Product product;
   @ManyToOne
   @JoinColumn(
      name = "price_list_id"
   )
   private PriceList priceList;
   @Column(
      name = "cost_price",
      nullable = false
   )
   private Double costPrice = 0.0;
   @Column(
      name = "net_price",
      nullable = false
   )
   private Double netPrice = 0.0;
   @Column(
      name = "gross_margin",
      nullable = false
   )
   private Double grossMargin = 0.0;
   @Column(
      name = "previous_selling_price",
      nullable = false
   )
   private Double previousSellingPrice = 0.0;
   @Column(
      name = "selling_price",
      nullable = false
   )
   private Double sellingPrice = 0.0;
   @Column(
      name = "internal_tax",
      nullable = false
   )
   private Double internalTax = 0.0;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "previous_updated_price"
   )
   private Date previousUpdatedPrice;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "last_updated_price"
   )
   private Date lastUpdatedPrice;
   @ManyToOne
   @JoinColumn(
      name = "vat_id",
      nullable = false
   )
   private Vat vat;

   public ProductPrice() {
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }

   public Double getCostPrice() {
      return this.costPrice;
   }

   public void setCostPrice(Double costPrice) {
      this.costPrice = costPrice;
   }

   public Double getNetPrice() {
      return this.netPrice;
   }

   public void setNetPrice(Double netPrice) {
      this.netPrice = netPrice;
   }

   public Double getGrossMargin() {
      return this.grossMargin;
   }

   public void setGrossMargin(Double grossMargin) {
      this.grossMargin = grossMargin;
   }

   public Double getPreviousSellingPrice() {
      return this.previousSellingPrice;
   }

   public void setPreviousSellingPrice(Double previousSellingPrice) {
      this.previousSellingPrice = previousSellingPrice;
   }

   public Double getSellingPrice() {
      return this.sellingPrice;
   }

   public void setSellingPrice(Double sellingPrice) {
      this.sellingPrice = sellingPrice;
   }

   public Double getInternalTax() {
      return this.internalTax;
   }

   public void setInternalTax(Double internalTax) {
      this.internalTax = internalTax;
   }

   public Date getPreviousUpdatedPrice() {
      return this.previousUpdatedPrice;
   }

   public void setPreviousUpdatedPrice(Date previousUpdatedPrice) {
      this.previousUpdatedPrice = previousUpdatedPrice;
   }

   public Date getLastUpdatedPrice() {
      return this.lastUpdatedPrice;
   }

   public void setLastUpdatedPrice(Date lastUpdatedPrice) {
      this.lastUpdatedPrice = lastUpdatedPrice;
   }

   public Vat getVat() {
      return this.vat;
   }

   public void setVat(Vat vat) {
      this.vat = vat;
   }

   private String getBarCode() {
      return this.getProduct().getBarCode();
   }

   public void updateSellingPrice(double sellingPrice, Date editionDate) {
      try {
         if (sellingPrice >= 0.0) {
            this.setSellingPrice(FVMathUtils.roundValue(sellingPrice));
            this.setLastUpdatedPrice(editionDate);
            this.updateNetPrice();
            this.updateCostPrice();
         }
      } catch (Exception var5) {
         logger.error("Error actualizando precio de venta de producto con codigo: " + this.getBarCode());
      }

   }

   public void updateNetPrice() {
      try {
         if (this.getVat() != null && this.getVat().getValue() > 0.0) {
            double valueWithoutVat = this.getSellingPrice() / (1.0 + this.getVat().getValue() / 100.0);
            double internalTaxAmount = valueWithoutVat * (this.getInternalTax() / 100.0);
            double value = valueWithoutVat - internalTaxAmount;
            this.setNetPrice(FVMathUtils.roundValue(value));
         } else if (this.getVat() != null && this.getVat().getValue() == 0.0) {
            this.setNetPrice(this.getCostPrice());
         }
      } catch (Exception var7) {
         logger.error("Error actualizando precio neto de producto con codigo: " + this.getBarCode());
      }

   }

   public void updateCostPrice() {
      try {
         if (this.getGrossMargin() > 0.0) {
            double costPrice = this.getNetPrice() / (1.0 + this.getGrossMargin() / 100.0);
            this.setCostPrice(FVMathUtils.roundValue(costPrice));
         } else {
            this.setCostPrice(FVMathUtils.roundValue(this.getNetPrice()));
         }
      } catch (Exception var3) {
         logger.error("Error actualizando precio de costo de producto con codigo: " + this.getBarCode());
      }

   }

   public String getCostPriceToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String priceStr = String.valueOf(formatter.format(this.getCostPrice()));
      priceStr = priceStr.replaceAll("\\.", ",");
      return this.getCostPrice() != 0.0 ? priceStr : "0,00";
   }

   public String getVatToDisplay() {
      if (this.getVat() != null) {
         DecimalFormat formatter = new DecimalFormat("0.00");
         String str = String.valueOf(formatter.format(this.getVat().getValue()));
         str = str.replaceAll("\\.", ",");
         return this.getVat().getValue() != 0.0 ? str : "0,00";
      } else {
         return "0,00";
      }
   }

   public String getGrossMarginToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getGrossMargin()));
      str = str.replaceAll("\\.", ",");
      return this.getGrossMargin() != 0.0 ? str : "0,00";
   }

   public String getSellingPriceToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String priceStr = String.valueOf(formatter.format(this.getSellingPrice()));
      priceStr = priceStr.replaceAll("\\.", ",");
      return this.getSellingPrice() != 0.0 ? priceStr : "0,00";
   }

   public String getInternalTaxToDisplay() {
      if (this.getInternalTax() != null) {
         DecimalFormat formatter = new DecimalFormat("0.00");
         String str = String.valueOf(formatter.format(this.getInternalTax()));
         str = str.replaceAll("\\.", ",");
         return this.getInternalTax() != 0.0 ? str : "0,00";
      } else {
         return "0,00";
      }
   }

   public String getLastUpdatedPriceToDisplay() {
      String str = "";
      if (this.getLastUpdatedPrice() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
         str = formatter.format(this.getLastUpdatedPrice());
      }

      return str;
   }

   public String getNetPriceToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String priceStr = String.valueOf(formatter.format(this.getNetPrice()));
      priceStr = priceStr.replaceAll("\\.", ",");
      return this.getNetPrice() != 0.0 ? priceStr : "0,00";
   }

   public String getLastUpdatedPriceForLabel() {
      String dateStr = "";
      if (this.getLastUpdatedPrice() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
         dateStr = formatter.format(this.getLastUpdatedPrice());
      }

      return dateStr;
   }

   public void updateGrossMargin() {
      try {
         this.updateNetPrice();
         double diff = this.getNetPrice() - this.getCostPrice();
         double margin = 0.0;
         if (this.getCostPrice() != 0.0) {
            margin = diff / this.getCostPrice() * 100.0;
         }

         this.setGrossMargin(FVMathUtils.roundValue(margin));
      } catch (Exception var5) {
         logger.error("Error al actualizar el margen de ganancia del producto con código: " + this.getBarCode());
      }

   }

   public String getPricePerUnitToDisplay() {
      String str = "";

      try {
         if (this.getProduct().getQuantity() != 0.0 && !"".equalsIgnoreCase(this.getProduct().getQuantityUnit())) {
            switch (this.getProduct().getQuantityUnit().toUpperCase()) {
               case "CC":
                  str = this.getPricePerUnitForCc();
                  return str;
               case "GRS":
                  str = this.getPricePerUnitForGrs();
                  return str;
               case "KGS":
                  str = this.getPricePerUnitForKgs();
                  return str;
               case "LTS":
                  str = this.getPricePerUnitForLts();
                  return str;
               case "UNI":
                  str = this.getPricePerUnitForUni();
                  return str;
            }

            str = "";
         }
      } catch (Exception var3) {
         logger.error("Error calculando precio por unidad para el artículo con id: " + this.getId());
      }

      return str;
   }

   private String getPricePerUnitForCc() {
      String str = "";
      if (this.getSellingPrice() != 0.0 && this.getProduct().getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getProduct().getQuantity();
         value *= 1000.0;
         DecimalFormat formatter = new DecimalFormat("0.00");
         str = String.valueOf(formatter.format(value));
         str = str + "/L";
      } else {
         str = "0,00/L";
      }

      return str;
   }

   private String getPricePerUnitForGrs() {
      String str = "";
      if (this.getSellingPrice() != 0.0 && this.getProduct().getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getProduct().getQuantity();
         DecimalFormat formatter;
         if (this.getProduct().getQuantity() < 100.0) {
            value *= 100.0;
            formatter = new DecimalFormat("0.00");
            str = String.valueOf(formatter.format(value));
            str = str + "/100 Grs";
         } else {
            value *= 1000.0;
            formatter = new DecimalFormat("0.00");
            str = String.valueOf(formatter.format(value));
            str = str + "/Kg";
         }
      } else {
         str = "0,00/Kg";
      }

      return str;
   }

   private String getPricePerUnitForUni() {
      String str = "";
      if (this.getSellingPrice() != 0.0 && this.getProduct().getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getProduct().getQuantity();
         DecimalFormat formatter;
         if (this.getProduct().getQuantity() >= 25.0) {
            value *= 100.0;
            formatter = new DecimalFormat("0.00");
            str = String.valueOf(formatter.format(value));
            str = str + "/100 Uni";
         } else {
            formatter = new DecimalFormat("0.00");
            str = String.valueOf(formatter.format(value));
            str = str + "/Uni";
         }
      } else {
         str = "0,00/Unidad";
      }

      return str;
   }

   private String getPricePerUnitForKgs() {
      String str = "";
      if (this.getSellingPrice() != 0.0 && this.getProduct().getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getProduct().getQuantity();
         DecimalFormat formatter = new DecimalFormat("0.00");
         str = String.valueOf(formatter.format(value));
         str = str + "/Kg";
      } else {
         str = "0,00/Kg";
      }

      return str;
   }

   private String getPricePerUnitForLts() {
      String str = "";
      if (this.getSellingPrice() != 0.0 && this.getProduct().getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getProduct().getQuantity();
         DecimalFormat formatter = new DecimalFormat("0.00");
         str = String.valueOf(formatter.format(value));
         str = str + "/L";
      } else {
         str = "0,00/L";
      }

      return str;
   }

   public void updateCostPriceForVariationPercent(Double costVariation) {
      try {
         if (costVariation != 0.0) {
            double newCostPrice = this.getCostPrice() * (1.0 + costVariation / 100.0);
            this.setCostPrice(newCostPrice);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public void updateSellingPriceForCostChanged(Date editionDate) {
      try {
         double newSellingPrice = this.calculateSellingPrice();
         this.setSellingPrice(newSellingPrice);
         this.setLastUpdatedPrice(editionDate);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   protected Double calculateSellingPrice() {
      double newSellingPrice = 0.0;

      try {
         newSellingPrice = this.getCostPrice() + this.getCostPrice() * this.getGrossMargin() / 100.0;
         newSellingPrice += newSellingPrice * this.getVat().getValue() / 100.0;
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return newSellingPrice;
   }
}
