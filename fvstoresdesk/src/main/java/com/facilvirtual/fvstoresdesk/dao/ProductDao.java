package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("ProductDao");

   public ProductDao() {
   }

   public List<Product> getAllProducts() {
      List<Product> products = this.getSession().createCriteria(Product.class).list();
      Collections.sort(products);
      return products;
   }

   public void saveProduct(Product product) {
      Session session = this.getSession();
      session.saveOrUpdate(product);
   }

   public Product getProductByBarCode(String barCode) {
      Product p = null;

      try {
         Criteria crit = this.getSession().createCriteria(Product.class);
         crit.add(Restrictions.eq("barCode", barCode).ignoreCase());
         crit.setMaxResults(1);
         p = (Product)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Error recuperando producto por código de barras: " + barCode);
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return p;
   }

   public List<Product> searchProducts(String query, boolean hideDiscontinued, int maxResults) {
      Session session = this.getSession();
      String queryStr = "from Product prod where (upper(prod.description) like '%" + query.toUpperCase() + "%' or " + "upper(prod.barCode) like '%" + query.toUpperCase() + "%')";
      if (hideDiscontinued) {
         queryStr = queryStr + " and is_discontinued = false";
      }

      Query q = session.createQuery(queryStr);
      q.setMaxResults(maxResults);
      List<Product> products = q.list();
      Collections.sort(products);
      return products;
   }

   public List<Product> getAllProductsWithPrice() {
      Session session = this.getSession();
      Query q = session.createQuery("from Product prod where prod.sellingPrice > 0");
      List<Product> products = q.list();
      return products;
   }

   public void deleteProductByBarCode(String barCode) {
      logger.info("Discontinuando artículo con Codigo de barras:" + barCode);
      Product product = this.getProductByBarCode(barCode);
      if (product != null) {
         product.setDiscontinued(true);
         this.saveProduct(product);
      } else {
         logger.warn("No se encontró el artículo para Discontinuar con Codigo de barras:" + barCode);
      }

   }

   public List<Product> getProductsWithUpdatedPricesInDateRange(Date startDate, Date endDate, PriceList priceList) {
      System.out.println("Recuperando artículos c/precios actualizados entre Inicio: " + startDate);
      System.out.println("Fin: " + endDate);
      System.out.println("PriceList: " + priceList);
      List<ProductPrice> productPrices = this.getSession().createCriteria(ProductPrice.class).add(Restrictions.eq("priceList.id", priceList.getId())).add(Restrictions.between("lastUpdatedPrice", startDate, endDate)).addOrder(Order.asc("lastUpdatedPrice")).createCriteria("product").add(Restrictions.eq("discontinued", false)).list();
      List<Product> products = new ArrayList();
      Iterator var7 = productPrices.iterator();

      while(var7.hasNext()) {
         ProductPrice pp = (ProductPrice)var7.next();
         products.add(pp.getProduct());
      }

      return products;
   }

   public List<Product> getAllActiveProducts() {
      List<Product> products = this.getSession().createCriteria(Product.class).add(Restrictions.eq("discontinued", false)).addOrder(Order.asc("description")).list();
      return products;
   }

   public int getTotalProductsQty() {
      int value = 0;

      try {
         Criteria sizeResult = this.getSession().createCriteria(Product.class).setProjection(Projections.rowCount());
         Long size = (Long)sizeResult.uniqueResult();
         value = size.intValue();
      } catch (Exception var4) {
      }

      return value;
   }

   public int getActiveProductsQty() {
      int value = 0;

      try {
         Criteria sizeResult = this.getSession().createCriteria(Product.class).add(Restrictions.eq("discontinued", false)).setProjection(Projections.rowCount());
         Long size = (Long)sizeResult.uniqueResult();
         value = size.intValue();
      } catch (Exception var4) {
      }

      return value;
   }

   public List<Product> getProductsForListOfReposition() {
      Session session = this.getSession();
      String queryStr = "from Product prod where is_stock_control_enabled = true and (prod.stock <= prod.stockMin) and is_discontinued = false";
      Query q = session.createQuery(queryStr);
      q.setMaxResults(10000);
      List<Product> products = q.list();
      Collections.sort(products);
      return products;
   }

   public List<Product> getProductsWithStockControlEnabled() {
      Session session = this.getSession();
      String queryStr = "from Product prod where is_stock_control_enabled = true and is_discontinued = false";
      Query q = session.createQuery(queryStr);
      q.setMaxResults(50000);
      List<Product> products = q.list();
      Collections.sort(products);
      return products;
   }

   public List<Product> getProductsByCategory(ProductCategory productCategory) {
      List<Product> products = null;

      try {
         products = this.getSession().createCriteria(Product.class).add(Restrictions.eq("category.id", productCategory.getId())).list();
      } catch (Exception var4) {
      }

      return products;
   }
}
