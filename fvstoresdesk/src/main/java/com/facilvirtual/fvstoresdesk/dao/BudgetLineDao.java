package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Budget;
import com.facilvirtual.fvstoresdesk.model.BudgetLine;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetLineDao {
   protected static Logger logger = LoggerFactory.getLogger("BudgetLineDao");
   @Autowired
   private SessionFactory sessionFactory;

   public BudgetLineDao() {
   }

   private Session getSession() {
      return this.sessionFactory.getCurrentSession();
   }

   public List<BudgetLine> getBudgetLinesForBudget(Budget budget) {
      List<BudgetLine> budgetLines = this.getSession().createCriteria(BudgetLine.class).add(Restrictions.eq("budget.id", budget.getId())).list();
      return budgetLines;
   }
}
