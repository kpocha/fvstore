package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.model.PurchaseLine;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseLineDao {
   protected static Logger logger = LoggerFactory.getLogger("PurchaseLineDao");
   @Autowired
   private SessionFactory sessionFactory;

   public PurchaseLineDao() {
   }

   private Session getSession() {
      return this.sessionFactory.getCurrentSession();
   }

   public List<PurchaseLine> getPurchaseLinesForPurchase(Purchase purchase) {
      List<PurchaseLine> purchaseLines = this.getSession().createCriteria(PurchaseLine.class).add(Restrictions.eq("purchase.id", purchase.getId())).list();
      return purchaseLines;
   }
}
