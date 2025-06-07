package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.SupplierForProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierForProductDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("SupplierForProductDao");

   public SupplierForProductDao() {
   }

   public void saveSupplierForProduct(SupplierForProduct supplierForProduct) {
      this.getSession().saveOrUpdate(supplierForProduct);
   }

   public SupplierForProduct getSupplierForProduct(Product product, int supplierNumber) {
      SupplierForProduct supplierForProduct = null;

      try {
         Criteria crit = this.getSession().createCriteria(SupplierForProduct.class);
         crit.add(Restrictions.eq("product.id", product.getId()));
         crit.add(Restrictions.eq("supplierNumber", supplierNumber));
         crit.setMaxResults(1);
         supplierForProduct = (SupplierForProduct)crit.uniqueResult();
      } catch (Exception var5) {
         logger.error("Error al recuperar proveedor para producto");
         logger.error(var5.getMessage());
         //logger.error(var5);
      }

      return supplierForProduct;
   }
}
