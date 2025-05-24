package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Supplier;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("SupplierDao");

   public SupplierDao() {
   }

   public void saveSupplier(Supplier supplier) {
      this.getSession().saveOrUpdate(supplier);
   }

   public List<Supplier> getAllSuppliers() {
      List<Supplier> suppliers = this.getSession().createCriteria(Supplier.class).addOrder(Order.asc("companyName").ignoreCase()).list();
      return suppliers;
   }

   public Supplier getSupplierByCompanyName(String companyName) {
      Supplier supplier = null;

      try {
         Criteria crit = this.getSession().createCriteria(Supplier.class);
         crit.add(Restrictions.eq("companyName", companyName).ignoreCase());
         crit.setMaxResults(1);
         supplier = (Supplier)crit.uniqueResult();
      } catch (Exception var4) {
         //logger.error(var4);
      }

      return supplier;
   }

   public List<Supplier> searchSuppliers(String query, boolean hideDeletedSuppliers, int maxResults) {
      String queryStr = "from Supplier supplier where (upper(supplier.companyName) like '%" + query.toUpperCase() + "%' or " + "upper(supplier.cuit) like '%" + query.toUpperCase() + "%')";
      if (hideDeletedSuppliers) {
         queryStr = queryStr + " and (supplier.active) = true";
      }

      queryStr = queryStr + " order by supplier.companyName";
      Query q = this.getSession().createQuery(queryStr);
      q.setMaxResults(maxResults);
      List<Supplier> suppliers = q.list();
      return suppliers;
   }

   public void deleteSupplier(Long supplierId) {
      Supplier supplier = this.getSupplier(supplierId);
      if (supplier != null) {
         supplier.setActive(false);
         this.saveSupplier(supplier);
      }

   }

   public Supplier getSupplier(Long supplierId) {
      return (Supplier)this.getSession().get(Supplier.class, supplierId);
   }
}
