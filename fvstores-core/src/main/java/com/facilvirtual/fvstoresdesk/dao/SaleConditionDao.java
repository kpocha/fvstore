package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.SaleCondition;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SaleConditionDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("SaleConditionDao");

   public SaleConditionDao() {
   }

   public void saveSaleCondition(SaleCondition saleCondition) {
      Session session = this.getSession();
      session.saveOrUpdate(saleCondition);
   }

   public SaleCondition getSaleCondition(Long saleConditionId) {
      Session session = this.getSession();
      return (SaleCondition)session.get(SaleCondition.class, saleConditionId);
   }

   public SaleCondition getSaleConditionByName(String saleConditionName) {
      SaleCondition saleCondition = null;

      try {
         Criteria crit = this.getSession().createCriteria(SaleCondition.class);
         crit.add(Restrictions.eq("name", saleConditionName).ignoreCase());
         crit.setMaxResults(1);
         saleCondition = (SaleCondition)crit.uniqueResult();
      } catch (Exception var4) {
      }

      return saleCondition;
   }

   public List<SaleCondition> getAllSaleConditions() {
      List<SaleCondition> conditions = this.getSession().createCriteria(SaleCondition.class).addOrder(Order.asc("name")).list();
      return conditions;
   }

   public List<SaleCondition> getAllSaleConditionsForCompany() {
      List<SaleCondition> conditions = this.getSession().createCriteria(SaleCondition.class).add(Restrictions.eq("availableForCompany", true)).addOrder(Order.asc("name")).list();
      return conditions;
   }
}
