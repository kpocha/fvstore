package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.DebitCard;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DebitCardDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("DebitCardDao");
   @Autowired
   private SessionFactory sessionFactory;

   public DebitCardDao() {
   }

   public void saveDebitCard(DebitCard debitCard) {
      this.getSession().saveOrUpdate(debitCard);
   }

   public DebitCard getDebitCardByName(String debitCardName) {
      DebitCard debitCard = null;

      try {
         Criteria crit = this.getSession().createCriteria(DebitCard.class);
         crit.add(Restrictions.eq("name", debitCardName).ignoreCase());
         crit.setMaxResults(1);
         debitCard = (DebitCard)crit.uniqueResult();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return debitCard;
   }

   public List<DebitCard> getActiveDebitCards() {
      List<DebitCard> cards = this.getSession().createCriteria(DebitCard.class).addOrder(Order.asc("name").ignoreCase()).add(Restrictions.eq("active", true)).list();
      return cards;
   }

   public List<DebitCard> getAllDebitCards() {
      List<DebitCard> cards = this.getSession().createCriteria(DebitCard.class).addOrder(Order.asc("name").ignoreCase()).list();
      return cards;
   }
}
