package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCategoryDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("ProductCategoryDao");

   public ProductCategoryDao() {
   }

   public void saveProductCategory(ProductCategory productCategory) {
      Session session = this.getSession();
      session.saveOrUpdate(productCategory);
   }

   public ProductCategory getProductCategoryByName(String categoryName) {
      ProductCategory category = null;

      try {
         if ("- Sin clasificar -".equalsIgnoreCase(categoryName)) {
            categoryName = "Sin clasificar";
         }

         List<ProductCategory> categories = this.getSession().createCriteria(ProductCategory.class).add(Restrictions.eq("name", categoryName).ignoreCase()).addOrder(Order.asc("id")).list();
         if (categories.size() > 0) {
            category = (ProductCategory)categories.get(0);
         }
      } catch (Exception var4) {
         logger.error("Error al recuperar categoría de producto");
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return category;
   }

   public ProductCategory getProductCategory(Long id) {
      Session session = this.getSession();
      return (ProductCategory)session.get(ProductCategory.class, id);
   }

   public List<ProductCategory> getAllProductCategories() {
      List<ProductCategory> categories = this.getSession().createCriteria(ProductCategory.class).list();
      return categories;
   }

   public List<ProductCategory> getActiveProductCategories() {
      List<ProductCategory> categories = this.getSession().createCriteria(ProductCategory.class).add(Restrictions.eq("active", true)).addOrder(Order.asc("name").ignoreCase()).list();
      return categories;
   }
}
