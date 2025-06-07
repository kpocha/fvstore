package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.CreditCard;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CreditCardDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("CreditCardDao");
   @Autowired
   private SessionFactory sessionFactory;

   public CreditCardDao() {
   }

   public void saveCreditCard(CreditCard creditCard) {
      this.getSession().saveOrUpdate(creditCard);
   }

   public CreditCard getCreditCardByName(String creditCardName) {
      CreditCard creditCard = null;

      try {
         Criteria crit = this.getSession().createCriteria(CreditCard.class);
         crit.add(Restrictions.eq("name", creditCardName).ignoreCase());
         crit.setMaxResults(1);
         creditCard = (CreditCard)crit.uniqueResult();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return creditCard;
   }

   public List<CreditCard> getActiveCreditCards() {
      List<CreditCard> cards = this.getSession().createCriteria(CreditCard.class).addOrder(Order.asc("name").ignoreCase()).add(Restrictions.eq("active", true)).list();
      return cards;
   }

   public List<CreditCard> getAllCreditCards() {
      List<CreditCard> cards = this.getSession().createCriteria(CreditCard.class).addOrder(Order.asc("name").ignoreCase()).list();
      return cards;
   }
}
