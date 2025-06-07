package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.VatCondition;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VatConditionDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("VatConditionDao");

   public VatConditionDao() {
   }

   public void saveVatCondition(VatCondition vatCondition) {
      Session session = this.getSession();
      session.saveOrUpdate(vatCondition);
   }

   public VatCondition getVatCondition(Long vatConditionId) {
      Session session = this.getSession();
      return (VatCondition)session.get(VatCondition.class, vatConditionId);
   }

   public VatCondition getVatConditionByName(String vatConditionName) {
      VatCondition vatCondition = null;

      try {
         Criteria crit = this.getSession().createCriteria(VatCondition.class);
         crit.add(Restrictions.eq("name", vatConditionName).ignoreCase());
         crit.setMaxResults(1);
         vatCondition = (VatCondition)crit.uniqueResult();
      } catch (Exception var4) {
      }

      return vatCondition;
   }

   public List<VatCondition> getAllVatConditions() {
      List<VatCondition> conditions = this.getSession().createCriteria(VatCondition.class).addOrder(Order.asc("name")).list();
      return conditions;
   }

   public List<VatCondition> getAllVatConditionsForCompany() {
      List<VatCondition> conditions = this.getSession().createCriteria(VatCondition.class).add(Restrictions.eq("availableForCompany", true)).addOrder(Order.asc("name")).list();
      return conditions;
   }
}
