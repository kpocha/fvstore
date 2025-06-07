package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Budget;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("BudgetDao");

   public BudgetDao() {
   }

   public void saveBudget(Budget budget) {
      this.getSession().saveOrUpdate(budget);
   }

   public List<Budget> getAllBudgetsForDateRange(Date startDate, Date endDate) {
      List<Budget> budgets = null;

      try {
         budgets = this.getSession().createCriteria(Budget.class).add(Restrictions.between("budgetDate", startDate, endDate)).addOrder(Order.asc("budgetDate")).list();
      } catch (Exception var5) {
         logger.error(var5.getMessage());
         //logger.error(var5);
      }

      return budgets;
   }

   public Budget getBudget(Long budgetId) {
      return (Budget)this.getSession().get(Budget.class, budgetId);
   }

   public int getTotalBudgetsQty() {
      int value = 0;

      try {
         Criteria sizeResult = this.getSession().createCriteria(Budget.class).setProjection(Projections.rowCount());
         Long size = (Long)sizeResult.uniqueResult();
         value = size.intValue();
      } catch (Exception var4) {
      }

      return value;
   }
}
