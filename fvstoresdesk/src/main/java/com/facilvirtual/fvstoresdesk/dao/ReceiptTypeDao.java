package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.ReceiptType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ReceiptTypeDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("ReceiptTypeDao");

   public ReceiptTypeDao() {
   }

   public void saveReceipt(ReceiptType receiptType) {
      this.getSession().saveOrUpdate(receiptType);
   }

   public List<ReceiptType> getAllReceiptTypesForPurchase() {
      List<ReceiptType> types = this.getSession().createCriteria(ReceiptType.class).add(Restrictions.eq("availableForPurchase", true)).addOrder(Order.asc("name")).list();
      return types;
   }

   public List<ReceiptType> getActiveReceiptTypesForPurchase() {
      List<ReceiptType> types = this.getSession().createCriteria(ReceiptType.class).add(Restrictions.eq("availableForPurchase", true)).add(Restrictions.eq("active", true)).addOrder(Order.asc("name")).list();
      return types;
   }

   public ReceiptType getReceiptTypeByName(String receiptTypeName) {
      ReceiptType receiptType = null;

      try {
         Criteria crit = this.getSession().createCriteria(ReceiptType.class);
         crit.add(Restrictions.eq("name", receiptTypeName).ignoreCase());
         crit.setMaxResults(1);
         receiptType = (ReceiptType)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Error al recuperar tipo de comprobante");
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return receiptType;
   }

   public List<ReceiptType> getAllReceiptTypesForOrder() {
      List<ReceiptType> types = this.getSession().createCriteria(ReceiptType.class).add(Restrictions.eq("availableForOrder", true)).addOrder(Order.asc("name")).list();
      return types;
   }

   public List<ReceiptType> getActiveReceiptTypesForOrder() {
      List<ReceiptType> types = this.getSession().createCriteria(ReceiptType.class).add(Restrictions.eq("availableForOrder", true)).add(Restrictions.eq("active", true)).addOrder(Order.asc("name")).list();
      return types;
   }
}
