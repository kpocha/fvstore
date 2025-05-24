package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Supplier;
import com.facilvirtual.fvstoresdesk.model.SupplierOnAccountOperation;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierOnAccountOperationDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("SupplierOnAccountOperationDao");
   @Autowired
   private SessionFactory sessionFactory;

   public SupplierOnAccountOperationDao() {
   }

   public void saveSupplierOnAccountOperation(SupplierOnAccountOperation supplierOnAccountOperation) {
      this.getSession().saveOrUpdate(supplierOnAccountOperation);
   }

   public List<SupplierOnAccountOperation> getActiveSupplierOnAccountOps(Supplier supplier) {
      List<SupplierOnAccountOperation> operations = this.getSession().createCriteria(SupplierOnAccountOperation.class).add(Restrictions.eq("active", true)).add(Restrictions.eq("supplier.id", supplier.getId())).addOrder(Order.asc("operationDate")).list();
      return operations;
   }

   public SupplierOnAccountOperation getSupplierOnAccountOpByPurchaseId(Long purchaseId) {
      SupplierOnAccountOperation supplierOnAccountOp = null;

      try {
         Criteria crit = this.getSession().createCriteria(SupplierOnAccountOperation.class);
         crit.add(Restrictions.eq("purchase.id", purchaseId));
         crit.setMaxResults(1);
         supplierOnAccountOp = (SupplierOnAccountOperation)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Error al recuperar movimiento de cta. cte. de proveedor para compra: " + purchaseId);
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return supplierOnAccountOp;
   }
}
