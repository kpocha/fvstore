package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductPriceDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("ProductPriceDao");
   @Autowired
   private SessionFactory sessionFactory;

   public ProductPriceDao() {
   }

   public void saveProductPrice(ProductPrice productPrice) {
      this.getSession().saveOrUpdate(productPrice);
   }

   public ProductPrice getProductPriceForProduct(Product product, PriceList priceList) {
      ProductPrice productPrice = null;

      try {
         Criteria crit = this.getSession().createCriteria(ProductPrice.class);
         crit.add(Restrictions.eq("product.id", product.getId()));
         crit.add(Restrictions.eq("priceList.id", priceList.getId()));
         crit.setMaxResults(1);
         productPrice = (ProductPrice)crit.uniqueResult();
      } catch (Exception var5) {
         logger.error("No se pudo recuperar el Precio del producto");
      }

      return productPrice;
   }
}
