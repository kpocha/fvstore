package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.ProductCategoryDao;
import com.facilvirtual.fvstoresdesk.dao.ProductDao;
import com.facilvirtual.fvstoresdesk.dao.ProductPriceDao;
import com.facilvirtual.fvstoresdesk.dao.SupplierDao;
import com.facilvirtual.fvstoresdesk.dao.SupplierForProductDao;
import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.model.PurchaseLine;
import com.facilvirtual.fvstoresdesk.model.Supplier;
import com.facilvirtual.fvstoresdesk.model.SupplierForProduct;
import com.facilvirtual.fvstoresdesk.model.Vat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {
   protected static Logger logger = LoggerFactory.getLogger("ProductService");
   @Autowired
   private ProductDao productDao;
   @Autowired
   private ProductCategoryDao productCategoryDao;
   @Autowired
   private SupplierDao supplierDao;
   @Autowired
   private SupplierForProductDao supplierForProductDao;
   @Autowired
   private ProductPriceDao productPriceDao;

   public ProductService() {
   }

   public List<Product> getAllProducts() {
      return this.productDao.getAllProducts();
   }

   public Product getProductByBarCode(String barCode) {
      return this.productDao.getProductByBarCode(barCode);
   }

   public void saveProductCategory(ProductCategory productCategory) {
      this.productCategoryDao.saveProductCategory(productCategory);
   }

   public void saveProduct(Product product) {
      this.productDao.saveProduct(product);
   }

   public List<Product> searchProducts(String query, boolean hideDiscontinued, int maxResults) {
      return this.productDao.searchProducts(query, hideDiscontinued, maxResults);
   }

   public List<Product> getAllProductsWithPrice() {
      return this.productDao.getAllProductsWithPrice();
   }

   public ProductCategory getProductCategory(Long id) {
      return this.productCategoryDao.getProductCategory(id);
   }

   public ProductCategory getProductCategoryByName(String categoryName) {
      return this.productCategoryDao.getProductCategoryByName(categoryName);
   }

   public List<ProductCategory> getAllProductCategories() {
      return this.productCategoryDao.getAllProductCategories();
   }

   public void deleteProductByBarCode(String barCode) {
      this.productDao.deleteProductByBarCode(barCode);
   }

   public List<Product> getProductsWithUpdatedPricesInDateRange(Date startDate, Date endDate, PriceList priceList) {
      return this.productDao.getProductsWithUpdatedPricesInDateRange(startDate, endDate, priceList);
   }

   public List<Product> getAllActiveProducts() {
      return this.productDao.getAllActiveProducts();
   }

   public boolean isScaleBarCode(String barCode, String scaleCode) {
      boolean cond1 = barCode.length() == 13;
      boolean cond2 = barCode.startsWith(scaleCode);
      return cond1 && cond2;
   }

   public Product getProductByScaleBarCode(String scaleBarCode, int productCodeStart, int productCodeEnd) {
      String productBarCode = this.getProductBarCodeFromScaleBarCode(scaleBarCode, productCodeStart, productCodeEnd);
      return this.getProductByBarCode(productBarCode);
   }

   private String getProductBarCodeFromScaleBarCode(String scaleBarCode, int productCodeStart, int productCodeEnd) {
      String barCode = scaleBarCode.substring(productCodeStart - 1, productCodeEnd);
      String cleanBarCode = this.removeLeadingZeros(barCode);
      return cleanBarCode;
   }

   public String removeLeadingZeros(String s) {
      return s.replaceFirst("^0+(?!$)", "");
   }

   public double getWeightFromScaleBarCode(String scaleBarCode, int scaleWeightStart, int scaleWeightEnd, int scaleWeightDecimalsStart, int scaleWeightDecimalsEnd) {
      try {
         String valueStr = scaleBarCode.substring(scaleWeightStart - 1, scaleWeightEnd);
         String decimalsStr = scaleBarCode.substring(scaleWeightDecimalsStart - 1, scaleWeightDecimalsEnd);
         return Double.parseDouble(valueStr + "." + decimalsStr);
      } catch (Exception var8) {
         return 0.0;
      }
   }

   public double getPriceFromScaleBarCode(String scaleBarCode, int scalePriceStart, int scalePriceEnd, int scalePriceDecimalsStart, int scalePriceDecimalsEnd) {
      try {
         String valueStr = scaleBarCode.substring(scalePriceStart - 1, scalePriceEnd);
         String decimalsStr = scaleBarCode.substring(scalePriceDecimalsStart - 1, scalePriceDecimalsEnd);
         return Double.parseDouble(valueStr + "." + decimalsStr);
      } catch (Exception var8) {
         return 0.0;
      }
   }

   public int getTotalProductsQty() {
      return this.productDao.getTotalProductsQty();
   }

   public int getActiveProductsQty() {
      return this.productDao.getActiveProductsQty();
   }

   public void updateLastSaleDateForOrder(Order order) {
      try {
         Iterator var3 = order.getOrderLines().iterator();

         while(var3.hasNext()) {
            OrderLine ol = (OrderLine)var3.next();
            Product p = ol.getProduct();
            if (p != null) {
               p.setPreviousSaleDate(p.getLastSaleDate());
               p.setLastSaleDate(order.getSaleDate());
               this.saveProduct(p);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al actualizar la fecha de ultima venta.");
         logger.error("Venta Id: " + order.getId());
         logger.error("Message: " + var5.getMessage());
      }

   }

   public void restoreLastSaleDateForOrder(Order order) {
      try {
         Iterator var3 = order.getOrderLines().iterator();

         while(var3.hasNext()) {
            OrderLine ol = (OrderLine)var3.next();
            Product p = ol.getProduct();
            if (p != null) {
               p.setLastSaleDate(p.getPreviousSaleDate());
               this.saveProduct(p);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al restaurar la fecha de ultima venta.");
         logger.error("Venta Id: " + order.getId());
         logger.error("Message: " + var5.getMessage());
      }

   }

   public List<ProductCategory> getActiveProductCategories() {
      return this.productCategoryDao.getActiveProductCategories();
   }

   public void updateStockForOrder(Order order) {
      try {
         Iterator var3 = order.getOrderLines().iterator();

         while(var3.hasNext()) {
            OrderLine ol = (OrderLine)var3.next();
            Product p = ol.getProduct();
            if (p != null && p.isStockControlEnabled()) {
               p.setStock(p.getStock() - ol.getQty());
               this.saveProduct(p);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al actualizar stock en venta.");
         logger.error("Venta Id: " + order.getId());
         logger.error("Message: " + var5.getMessage());
      }

   }

   public void restoreStockForOrder(Order order) {
      try {
         Iterator var3 = order.getOrderLines().iterator();

         while(var3.hasNext()) {
            OrderLine ol = (OrderLine)var3.next();
            Product p = ol.getProduct();
            if (p != null && p.isStockControlEnabled()) {
               p.setStock(p.getStock() + ol.getQty());
               this.saveProduct(p);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al restaurar stock en venta.");
         logger.error("Venta Id: " + order.getId());
         logger.error("Message: " + var5.getMessage());
      }

   }

   public void saveSupplier(Supplier supplier) {
      this.supplierDao.saveSupplier(supplier);
   }

   public Supplier getSupplierByCompanyName(String companyName) {
      return this.supplierDao.getSupplierByCompanyName(companyName);
   }

   public List<Supplier> searchSuppliers(String query, boolean hideDeletedSuppliers, int maxResults) {
      return this.supplierDao.searchSuppliers(query, hideDeletedSuppliers, maxResults);
   }

   public void deleteSupplier(Long supplierId) {
      this.supplierDao.deleteSupplier(supplierId);
   }

   public Supplier getSupplier(Long supplierId) {
      return this.supplierDao.getSupplier(supplierId);
   }

   public void saveSupplierForProduct(SupplierForProduct supplierForProduct) {
      this.supplierForProductDao.saveSupplierForProduct(supplierForProduct);
   }

   public SupplierForProduct getSupplierForProduct(Product product, int supplierNumber) {
      return this.supplierForProductDao.getSupplierForProduct(product, supplierNumber);
   }

   public String getProductPhotoUrl(String barCode) {
      String photoUrl = "";

      try {
         String url = "http://supers.facilvirtual.com.ar/api/stores/product/" + barCode + "/387219739821";
         URLConnection connection = (new URL(url)).openConnection();
         InputStream response = connection.getInputStream();
         String x = this.convertStreamToString(response);
         JSONObject jsonObj = new JSONObject(x);
         if (jsonObj.getBoolean("photoAvailable")) {
            photoUrl = "http://resources.facilvirtual.com.ar/stores/common/images/products/large/" + jsonObj.getString("photo");
         } else {
            photoUrl = "http://resources.facilvirtual.com.ar/stores/common/images/imageNotAvailable200x200.gif";
         }
      } catch (Exception var8) {
         photoUrl = "http://resources.facilvirtual.com.ar/stores/common/images/imageNotAvailable200x200.gif";
      }

      return photoUrl;
   }

   private String convertStreamToString(InputStream is) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      String line = null;

      try {
         while((line = reader.readLine()) != null) {
            sb.append(line);
         }
      } catch (IOException var14) {
         var14.printStackTrace();
      } finally {
         try {
            is.close();
         } catch (IOException var13) {
            var13.printStackTrace();
         }

      }

      return sb.toString();
   }

   public String getProductPhotoUrlOLD(String barCode) {
      String photo = "";
      String photoUrl = "";

      try {
         Class.forName(AppConfig.DBWEB_CONN_DRIVER);
         Connection conn = DriverManager.getConnection(AppConfig.DBWEB_CONN_URL, AppConfig.DBWEB_USER, AppConfig.DBWEB_PASSWORD);
         Statement stmt = conn.createStatement();
         stmt.execute("BEGIN");
         ResultSet rs = stmt.executeQuery("select photo from product where bar_code = '" + barCode + "'");
         if (rs != null) {
            rs.next();
            photo = rs.getString(1);
            photoUrl = "http://resources.facilvirtual.com.ar/stores/common/images/products/large/" + photo;
         }

         stmt.execute("END");
         stmt.close();
         conn.close();
      } catch (Exception var7) {
         logger.error("Error recuperando la foto del producto");
         logger.error(var7.getMessage());
         //logger.error(var7);
      }

      return photoUrl;
   }

   public void updateLastPurchaseDateForPurchase(Purchase purchase) {
      try {
         Iterator var3 = purchase.getPurchaseLines().iterator();

         while(var3.hasNext()) {
            PurchaseLine pl = (PurchaseLine)var3.next();
            Product p = pl.getProduct();
            if (p != null) {
               p.setPreviousPurchaseDate(p.getLastPurchaseDate());
               p.setLastPurchaseDate(purchase.getPurchaseDate());
               this.saveProduct(p);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al actualizar la fecha de ultima compra.");
         logger.error("Compra Id: " + purchase.getId());
         logger.error("Message: " + var5.getMessage());
      }

   }

   public void restoreLastPurchaseDateForPurchase(Purchase purchase) {
      try {
         Iterator var3 = purchase.getPurchaseLines().iterator();

         while(var3.hasNext()) {
            PurchaseLine pl = (PurchaseLine)var3.next();
            Product p = pl.getProduct();
            if (p != null) {
               p.setLastPurchaseDate(p.getPreviousPurchaseDate());
               this.saveProduct(p);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al restaurar la fecha de ultima compra.");
         logger.error("Compra Id: " + purchase.getId());
         logger.error("Message: " + var5.getMessage());
      }

   }

   public void updateStockForPurchase(Purchase purchase) {
      try {
         Iterator var3 = purchase.getPurchaseLines().iterator();

         while(var3.hasNext()) {
            PurchaseLine pl = (PurchaseLine)var3.next();
            Product p = pl.getProduct();
            if (p != null && p.isStockControlEnabled()) {
               p.setStock(p.getStock() + pl.getQty());
               this.saveProduct(p);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al actualizar stock de compra.");
         logger.error("Compra Id: " + purchase.getId());
         logger.error("Message: " + var5.getMessage());
      }

   }

   public void restoreStockForPurchase(Purchase purchase) {
      try {
         Iterator var3 = purchase.getPurchaseLines().iterator();

         while(var3.hasNext()) {
            PurchaseLine pl = (PurchaseLine)var3.next();
            Product p = pl.getProduct();
            if (p != null && p.isStockControlEnabled()) {
               p.setStock(p.getStock() - pl.getQty());
               this.saveProduct(p);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al restaurar stock de compra.");
         logger.error("Compra Id: " + purchase.getId());
         logger.error("Message: " + var5.getMessage());
      }

   }

   private double roundValue(double valueToRound) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(valueToRound));
      return Double.valueOf(str.replaceAll(",", "\\."));
   }

   public void updatePricesForPurchase(Purchase purchase) {
      try {
         Iterator var3 = purchase.getPurchaseLines().iterator();

         while(var3.hasNext()) {
            PurchaseLine pl = (PurchaseLine)var3.next();
            Product p = pl.getProduct();
            if (p != null) {
               PriceList priceList = this.getAppConfigService().getDefaultPriceList();
               ProductPrice productPrice = this.getProductService().getProductPriceForProduct(p, priceList);
               if (productPrice != null) {
                  if (purchase.isInvoiceA()) {
                     productPrice.setCostPrice(this.roundValue(pl.getNetPrice()));
                  } else {
                     productPrice.setCostPrice(this.roundValue(pl.getPrice()));
                  }

                  double sellingPrice = productPrice.getCostPrice() * (1.0 + productPrice.getVat().getValue() / 100.0);
                  sellingPrice *= 1.0 + productPrice.getGrossMargin() / 100.0;
                  productPrice.setPreviousSellingPrice(productPrice.getSellingPrice());
                  productPrice.setSellingPrice(this.roundValue(sellingPrice));
                  productPrice.updateNetPrice();
                  productPrice.setPreviousUpdatedPrice(productPrice.getLastUpdatedPrice());
                  productPrice.setLastUpdatedPrice(purchase.getCreationDate());
                  this.getProductService().saveProductPrice(productPrice);
               }
            }
         }
      } catch (Exception var9) {
         logger.error("Error al actualizar precio de compra.");
         logger.error("Compra Id: " + purchase.getId());
         logger.error("Message: " + var9.getMessage());
      }

   }

   public void restorePricesForPurchase(Purchase purchase) {
      try {
         Iterator var3 = purchase.getPurchaseLines().iterator();

         while(var3.hasNext()) {
            PurchaseLine pl = (PurchaseLine)var3.next();
            Product p = pl.getProduct();
            if (p != null) {
               p.setSellingPrice(p.getPreviousSellingPrice());
               double costPrice = p.getSellingPrice() / (1.0 + p.getGrossMargin() / 100.0);
               costPrice /= 1.0 + p.getVat().getValue() / 100.0;
               p.setCostPrice(this.roundValue(costPrice));
               p.updateNetPrice();
               this.saveProduct(p);
            }
         }
      } catch (Exception var7) {
         logger.error("Error al restaurar precio de compra.");
         logger.error("Compra Id: " + purchase.getId());
         logger.error("Message: " + var7.getMessage());
      }

   }

   public List<Product> getProductsForListOfReposition() {
      return this.productDao.getProductsForListOfReposition();
   }

   public OrderService getOrderService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (OrderService)context.getBean("orderService");
   }

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   public ProductService getProductService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (ProductService)context.getBean("productService");
   }

   public ProductCategory getOrCreateProductCategoryByName(String categoryName) {
      ProductCategory category = null;

      try {
         category = this.getProductCategoryByName(categoryName);
         if (category == null) {
            try {
               logger.info("Creando categoría: " + categoryName);
               Vat vat = this.getOrderService().getVat(new Long(1L));
               category = new ProductCategory();
               category.setName(categoryName);
               category.setActive(true);
               category.setVat(vat);
               this.saveProductCategory(category);
            } catch (Exception var4) {
               logger.error("Error al crear categoría: " + categoryName);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al recuperar o crear categoría: " + categoryName);
      }

      return category;
   }

   public List<Product> getProductsWithStockControlEnabled() {
      return this.productDao.getProductsWithStockControlEnabled();
   }

   public List<Product> getProductsByBarCodes(String[] barCodesArray) {
      List<Product> products = new ArrayList();
      String[] var6 = barCodesArray;
      int var5 = barCodesArray.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String barCode = var6[var4];
         Product aProduct = this.getProductByBarCode(barCode.trim());
         if (aProduct != null) {
            products.add(aProduct);
         }
      }

      return products;
   }

   public ProductPrice getProductPriceForProduct(Product product, PriceList priceList) {
      return this.productPriceDao.getProductPriceForProduct(product, priceList);
   }

   public ProductPrice retrieveOrCreateProductPriceForProduct(Product product, PriceList priceList) {
      ProductPrice productPrice = this.getProductPriceForProduct(product, priceList);
      if (productPrice == null) {
         Vat vat = this.getOrderService().getVat(new Long(1L));
         productPrice = new ProductPrice();
         productPrice.setProduct(product);
         productPrice.setPriceList(priceList);
         productPrice.setVat(vat);
      }

      return productPrice;
   }

   public void saveProductPrice(ProductPrice productPrice) {
      this.productPriceDao.saveProductPrice(productPrice);
   }

   public List<Product> getProductsByCategory(ProductCategory productCategory) {
      return this.productDao.getProductsByCategory(productCategory);
   }
}
