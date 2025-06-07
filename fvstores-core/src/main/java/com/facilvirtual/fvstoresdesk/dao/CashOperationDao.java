package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.CashOperation;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CashOperationDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("CashOperationDao");
   @Autowired
   private SessionFactory sessionFactory;

   public CashOperationDao() {
   }

   public void saveCashOperation(CashOperation cashOperation) {
      this.getSession().saveOrUpdate(cashOperation);
   }

   public List<CashOperation> getAllCashOperations() {
      List<CashOperation> operations = this.getSession().createCriteria(CashOperation.class).addOrder(Order.asc("id")).list();
      return operations;
   }

   public List<CashOperation> getAllCashOperationsForDateRange(int cashNumber, Date startDate, Date endDate) {
      List<CashOperation> operations = this.getSession().createCriteria(CashOperation.class).add(Restrictions.between("operationDate", startDate, endDate)).add(Restrictions.eq("cashNumber", cashNumber)).addOrder(Order.asc("operationDate")).list();
      return operations;
   }

   public List<CashOperation> getActiveCashOperationsForDateRange(int cashNumber, Date startDate, Date endDate) {
      List<CashOperation> operations = this.getSession().createCriteria(CashOperation.class).add(Restrictions.between("operationDate", startDate, endDate)).add(Restrictions.eq("active", true)).add(Restrictions.eq("cashNumber", cashNumber)).addOrder(Order.asc("operationDate")).list();
      return operations;
   }

   public CashOperation getCashOperationByOrderId(Long orderId) {
      CashOperation operation = null;

      try {
         Criteria crit = this.getSession().createCriteria(CashOperation.class);
         crit.add(Restrictions.eq("order.id", orderId));
         crit.setMaxResults(1);
         operation = (CashOperation)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Error al recuperar movimiento de caja para venta: " + orderId);
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return operation;
   }

   public CashOperation getCashOperationByPurchaseId(Long purchaseId) {
      CashOperation operation = null;

      try {
         Criteria crit = this.getSession().createCriteria(CashOperation.class);
         crit.add(Restrictions.eq("purchase.id", purchaseId));
         crit.setMaxResults(1);
         operation = (CashOperation)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Error al recuperar movimiento de caja para compra: " + purchaseId);
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return operation;
   }

   public CashOperation getLastOpenCashOperation(int cashNumber) {
      CashOperation cashOperation = null;
      List<CashOperation> operations = this.getSession().createCriteria(CashOperation.class).add(Restrictions.eq("active", true)).add(Restrictions.eq("cashNumber", cashNumber)).add(Restrictions.eq("description", "Apertura de caja").ignoreCase()).add(Restrictions.eq("system", true)).addOrder(Order.desc("operationDate")).list();
      if (operations.size() > 0) {
         cashOperation = (CashOperation)operations.get(0);
      }

      return cashOperation;
   }
}
