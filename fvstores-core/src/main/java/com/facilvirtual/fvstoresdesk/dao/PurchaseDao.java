package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Purchase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class PurchaseDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger(PurchaseDao.class);

   public PurchaseDao() {
   }

   public void savePurchase(Purchase purchase) {
      this.getSession().saveOrUpdate(purchase);
   }

   public List<Purchase> getAllPurchasesForDateRange(Date startDate, Date endDate) {
      List<Purchase> purchases = this.getSession().createCriteria(Purchase.class).add(Restrictions.between("purchaseDate", startDate, endDate)).addOrder(Order.asc("purchaseDate")).list();
      return purchases;
   }

   public Purchase getPurchase(Long purchaseId) {
      return (Purchase)this.getSession().get(Purchase.class, purchaseId);
   }
}
