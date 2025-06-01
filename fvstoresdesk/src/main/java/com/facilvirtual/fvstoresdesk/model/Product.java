package com.facilvirtual.fvstoresdesk.model;

import com.facilvirtual.fvstoresdesk.util.FVMathUtils;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
   name = "fvpos_product"
)
public class Product implements Serializable, Comparable<Product> {
   protected static Logger logger = LoggerFactory.getLogger("Product");
   private static final long serialVersionUID = -3179544096422082533L;
   @Id
   @Column(
      name = "product_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "creation_date",
      nullable = false
   )
   private Date creationDate;
   @Column(
      name = "photo"
   )
   private String photo;
   @Column(
      name = "bar_code",
      unique = true,
      nullable = false
   )
   private String barCode;
   @Column(
      name = "product_description",
      nullable = false
   )
   private String description;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "last_updated_description",
      nullable = false
   )
   private Date lastUpdatedDescription;
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
   @Column(
      name = "selling_unit",
      nullable = false
   )
   private String sellingUnit = "UN";
   @Column(
      name = "is_discontinued",
      nullable = false
   )
   private boolean discontinued = true;
   @Column(
      name = "in_offer",
      nullable = false
   )
   private boolean inOffer = false;
   @Column(
      name = "in_web",
      nullable = false
   )
   private boolean inWeb = false;
   @Column(
      name = "offer_price",
      nullable = false
   )
   private Double offerPrice = 0.0;
   @Column(
      name = "offer_start_date"
   )
   private Date offerStartDate;
   @Column(
      name = "offer_end_date"
   )
   private Date offerEndDate;
   @ManyToOne
   @JoinColumn(
      name = "brand_id"
   )
   private Brand brand;
   @ManyToOne
   @JoinColumn(
      name = "category_id"
   )
   private ProductCategory category;
   @ManyToOne
   @JoinColumn(
      name = "subcategory1_id"
   )
   private ProductCategory subcategory1;
   @ManyToOne
   @JoinColumn(
      name = "subcategory2_id"
   )
   private ProductCategory subcategory2;
   @ManyToOne
   @JoinColumn(
      name = "supplier_id"
   )
   private Supplier supplier;
   @OneToMany(
      mappedBy = "supplier",
      cascade = {CascadeType.ALL}
   )
   @OrderBy("supplierNumber")
   private List<SupplierForProduct> suppliersForProduct = new ArrayList();
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "last_updated_category",
      nullable = false
   )
   private Date lastUpdatedCategory;
   @Column(
      name = "product_short_description"
   )
   private String shortDescription;
   @Column(
      name = "quantity",
      nullable = false
   )
   private Double quantity = 0.0;
   @Column(
      name = "quantity_unit"
   )
   private String quantityUnit;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "previous_sale_date"
   )
   private Date previousSaleDate;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "last_sale_date"
   )
   private Date lastSaleDate;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "previous_purchase_date"
   )
   private Date previousPurchaseDate;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "last_purchase_date"
   )
   private Date lastPurchaseDate;
   @Column(
      name = "stock",
      nullable = false
   )
   private Double stock = 0.0;
   @Column(
      name = "stock_min",
      nullable = false
   )
   private Double stockMin = 0.0;
   @Column(
      name = "stock_max",
      nullable = false
   )
   private Double stockMax = 0.0;
   @Column(
      name = "is_stock_control_enabled",
      nullable = false
   )
   private boolean stockControlEnabled = false;
   @OneToMany(
      mappedBy = "product"
   )
   private List<ProductPhoto> photos = new ArrayList();
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "expiration_date"
   )
   private Date expirationDate;
   @Column(
      name = "alert_exp_days"
   )
   private int alertExpDays = 15;
   @Column(
      name = "is_alert_exp_active",
      nullable = false
   )
   private boolean alertExpActive = false;

   public Product() {
   }

   public List<ProductPhoto> getPhotos() {
      return this.photos;
   }

   public void setPhotos(List<ProductPhoto> photos) {
      this.photos = photos;
   }

   public boolean isStockControlEnabled() {
      return this.stockControlEnabled;
   }

   public void setStockControlEnabled(boolean stockControlEnabled) {
      this.stockControlEnabled = stockControlEnabled;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
   }

   public Double getStock() {
      return this.stock;
   }

   public void setStock(Double stock) {
      this.stock = stock;
   }

   public Double getStockMin() {
      return this.stockMin;
   }

   public void setStockMin(Double stockMin) {
      this.stockMin = stockMin;
   }

   public Double getStockMax() {
      return this.stockMax;
   }

   public void setStockMax(Double stockMax) {
      this.stockMax = stockMax;
   }

   public Date getLastSaleDate() {
      return this.lastSaleDate;
   }

   public void setLastSaleDate(Date lastSaleDate) {
      this.lastSaleDate = lastSaleDate;
   }

   public String getShortDescription() {
      return this.shortDescription;
   }

   public void setShortDescription(String shortDescription) {
      this.shortDescription = shortDescription;
   }

   public Double getQuantity() {
      return this.quantity;
   }

   public void setQuantity(Double quantity) {
      this.quantity = quantity;
   }

   public String getQuantityUnit() {
      return this.quantityUnit;
   }

   public void setQuantityUnit(String quantityUnit) {
      this.quantityUnit = quantityUnit;
   }

   public Vat getVat() {
      return this.vat;
   }

   public void setVat(Vat vat) {
      this.vat = vat;
   }

   public Date getLastUpdatedDescription() {
      return this.lastUpdatedDescription;
   }

   public void setLastUpdatedDescription(Date lastUpdatedDescription) {
      this.lastUpdatedDescription = lastUpdatedDescription;
   }

   public Date getLastUpdatedCategory() {
      return this.lastUpdatedCategory;
   }

   public void setLastUpdatedCategory(Date lastUpdatedCategory) {
      this.lastUpdatedCategory = lastUpdatedCategory;
   }

   public ProductCategory getCategory() {
      return this.category;
   }

   public void setCategory(ProductCategory category) {
      this.category = category;
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

   public String getBarCode() {
      return this.barCode;
   }

   public void setBarCode(String barCode) {
      this.barCode = barCode;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getQuantityToDisplay() {
      String value = "";
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getQuantity()));
      str = str.replaceAll("\\.", ",");
      if (this.getQuantity() != 0.0) {
         value = str;
      } else {
         value = "";
      }

      if (value.endsWith("0")) {
         value = value.substring(0, value.length() - 1);
      }

      if (value.endsWith("0")) {
         value = value.substring(0, value.length() - 2);
      }

      return value;
   }

   public boolean isPerWeight() {
      return "KG".equalsIgnoreCase(this.getSellingUnit());
   }

   public boolean isInOffer() {
      return this.inOffer;
   }

   public void setInOffer(boolean inOffer) {
      this.inOffer = inOffer;
   }

   public Date getOfferStartDate() {
      return this.offerStartDate;
   }

   public void setOfferStartDate(Date offerStartDate) {
      this.offerStartDate = offerStartDate;
   }

   public Date getOfferEndDate() {
      return this.offerEndDate;
   }

   public void setOfferEndDate(Date offerEndDate) {
      this.offerEndDate = offerEndDate;
   }

   public Double getCostPrice() {
      return this.costPrice;
   }

   public void setCostPrice(Double costPrice) {
      this.costPrice = costPrice;
   }

   public Double getSellingPrice() {
      return this.sellingPrice;
   }

   public void setSellingPrice(Double sellingPrice) {
      this.sellingPrice = sellingPrice;
   }

   public String getSellingUnit() {
      return this.sellingUnit;
   }

   public void setSellingUnit(String sellingUnit) {
      this.sellingUnit = sellingUnit;
   }

   public Double getOfferPrice() {
      return this.offerPrice;
   }

   public void setOfferPrice(Double offerPrice) {
      this.offerPrice = offerPrice;
   }

   public boolean isDiscontinued() {
      return this.discontinued;
   }

   public void setDiscontinued(boolean discontinued) {
      this.discontinued = discontinued;
   }

   public Double getGrossMargin() {
      return this.grossMargin;
   }

   public void setGrossMargin(Double grossMargin) {
      this.grossMargin = grossMargin;
   }

   public Double getNetPrice() {
      return this.netPrice;
   }

   public void setNetPrice(Double netPrice) {
      this.netPrice = netPrice;
   }

   public void initCreationDate(Date creationDate) {
      this.creationDate = creationDate;
      this.lastUpdatedCategory = creationDate;
      this.lastUpdatedDescription = creationDate;
      this.lastUpdatedPrice = creationDate;
   }

   public boolean isInWeb() {
      return this.inWeb;
   }

   public void setInWeb(boolean inWeb) {
      this.inWeb = inWeb;
   }

   public int compareTo(Product o) {
      return this.getDescription().toUpperCase().compareTo(o.getDescription().toUpperCase());
   }

   public String getDescriptionForLabel() {
      String str = "";
      if (this.getShortDescription() != null && !"".equalsIgnoreCase(this.getShortDescription())) {
         str = this.getShortDescription();
      } else if (this.getDescription().length() > 24) {
         str = this.getDescription().substring(0, 24);
      } else {
         str = this.getDescription();
      }

      return str.toUpperCase();
   }

   public String getQuantityForLabel() {
      String str = this.getQuantityToDisplay() != null ? this.getQuantityToDisplay() : "";
      if ("1".equals(str) && "LTS".equalsIgnoreCase(this.getQuantityUnit())) {
         str = str + "  L";
      } else if ("1".equals(str) && "KGS".equalsIgnoreCase(this.getQuantityUnit())) {
         str = str + "  KG";
      } else {
         str = str + (this.getQuantityUnit() != null ? "  " + this.getQuantityUnit() : "");
      }

      return str.toUpperCase();
   }

   public String getLastSaleDateToDisplay() {
      String dateStr = "";
      if (this.getLastSaleDate() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
         dateStr = formatter.format(this.getLastSaleDate());
      }

      return dateStr;
   }

   public String getStockToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0");
      if (this.isPerWeight()) {
         formatter = new DecimalFormat("0.000");
      }

      String str = String.valueOf(formatter.format(this.getStock()));
      return str;
   }

   public String getStockMinToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0");
      if (this.isPerWeight()) {
         formatter = new DecimalFormat("0.000");
      }

      String str = String.valueOf(formatter.format(this.getStockMin()));
      return str;
   }

   public String getStockMaxToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0");
      if (this.isPerWeight()) {
         formatter = new DecimalFormat("0.000");
      }

      String str = String.valueOf(formatter.format(this.getStockMax()));
      return str;
   }

   public void addSupplierForProduct(SupplierForProduct supplierForProduct) {
      this.suppliersForProduct.add(supplierForProduct);
   }

   public ProductCategory getSubcategory1() {
      return this.subcategory1;
   }

   public void setSubcategory1(ProductCategory subcategory1) {
      this.subcategory1 = subcategory1;
   }

   public ProductCategory getSubcategory2() {
      return this.subcategory2;
   }

   public void setSubcategory2(ProductCategory subcategory2) {
      this.subcategory2 = subcategory2;
   }

   public List<SupplierForProduct> getSuppliersForProduct() {
      return this.suppliersForProduct;
   }

   public void setSuppliersForProduct(List<SupplierForProduct> suppliersForProduct) {
      this.suppliersForProduct = suppliersForProduct;
   }

   public String getPhoto() {
      return this.photo;
   }

   public void setPhoto(String photo) {
      this.photo = photo;
   }

   public Brand getBrand() {
      return this.brand;
   }

   public void setBrand(Brand brand) {
      this.brand = brand;
   }

   public Double getInternalTax() {
      return this.internalTax;
   }

   public void setInternalTax(Double internalTax) {
      this.internalTax = internalTax;
   }

   public Date getPreviousSaleDate() {
      return this.previousSaleDate;
   }

   public void setPreviousSaleDate(Date previousSaleDate) {
      this.previousSaleDate = previousSaleDate;
   }

   public Date getPreviousUpdatedPrice() {
      return this.previousUpdatedPrice;
   }

   public void setPreviousUpdatedPrice(Date previousUpdatedPrice) {
      this.previousUpdatedPrice = previousUpdatedPrice;
   }

   public Date getPreviousPurchaseDate() {
      return this.previousPurchaseDate;
   }

   public void setPreviousPurchaseDate(Date previousPurchaseDate) {
      this.previousPurchaseDate = previousPurchaseDate;
   }

   public Date getLastPurchaseDate() {
      return this.lastPurchaseDate;
   }

   public void setLastPurchaseDate(Date lastPurchaseDate) {
      this.lastPurchaseDate = lastPurchaseDate;
   }

   public String formatStockValuationToDisplay(Double stockValuation) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String priceStr = String.valueOf(formatter.format(stockValuation));
      priceStr = priceStr.replaceAll("\\.", ",");
      return stockValuation != 0.0 ? priceStr : "0,00";
   }

   public Date getExpirationDate() {
      return this.expirationDate;
   }

   public void setExpirationDate(Date expirationDate) {
      this.expirationDate = expirationDate;
   }

   public int getAlertExpDays() {
      return this.alertExpDays;
   }

   public void setAlertExpDays(int alertExpDays) {
      this.alertExpDays = alertExpDays;
   }

   public boolean isAlertExpActive() {
      return this.alertExpActive;
   }

   public boolean getAlertExpActive() {
      return this.alertExpActive;
   }

   public void setAlertExpActive(boolean alertExpActive) {
      this.alertExpActive = alertExpActive;
   }

   public String getExpirationDateToDisplay() {
      String dateStr = "";
      if (this.getExpirationDate() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
         dateStr = formatter.format(this.getExpirationDate());
      }

      return dateStr;
   }

   public Date getLastUpdatedPrice() {
      return this.lastUpdatedPrice;
   }

   public void setLastUpdatedPrice(Date lastUpdatedPrice) {
      this.lastUpdatedPrice = lastUpdatedPrice;
   }

   public Double getPreviousSellingPrice() {
      return this.previousSellingPrice;
   }

   public void setPreviousSellingPrice(Double previousSellingPrice) {
      this.previousSellingPrice = previousSellingPrice;
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
         if (this.getQuantity() != 0.0 && !"".equalsIgnoreCase(this.getQuantityUnit())) {
            switch (this.getQuantityUnit().toUpperCase()) {
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
      if (this.getSellingPrice() != 0.0 && this.getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getQuantity();
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
      if (this.getSellingPrice() != 0.0 && this.getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getQuantity();
         DecimalFormat formatter;
         if (this.getQuantity() < 100.0) {
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
      if (this.getSellingPrice() != 0.0 && this.getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getQuantity();
         DecimalFormat formatter;
         if (this.getQuantity() >= 25.0) {
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
      if (this.getSellingPrice() != 0.0 && this.getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getQuantity();
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
      if (this.getSellingPrice() != 0.0 && this.getQuantity() != 0.0) {
         double value = this.getSellingPrice() / this.getQuantity();
         DecimalFormat formatter = new DecimalFormat("0.00");
         str = String.valueOf(formatter.format(value));
         str = str + "/L";
      } else {
         str = "0,00/L";
      }

      return str;
   }

   public boolean havePhoto() {
      return this.getPhoto() != null && !"".equals(this.getPhoto());
   }

   public String getDescriptionForBudget() {
      return "(" + this.getBarCode() + ") " + this.getDescription();
   }

   public String toString() {
      return "Product{id: " + this.getId() + ", cód. " + this.getBarCode() + ", desc.: " + this.getDescription() + "}";
   }
}
