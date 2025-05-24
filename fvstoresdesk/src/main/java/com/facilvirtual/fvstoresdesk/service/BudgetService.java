package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.BudgetDao;
import com.facilvirtual.fvstoresdesk.dao.BudgetLineDao;
import com.facilvirtual.fvstoresdesk.model.Budget;
import com.facilvirtual.fvstoresdesk.model.BudgetLine;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BudgetService {
   protected static Logger logger = LoggerFactory.getLogger("BudgetService");
   @Autowired
   private BudgetDao budgetDao;
   @Autowired
   private BudgetLineDao budgetLineDao;

   public BudgetService() {
   }

   public void saveBudget(Budget budget) {
      this.budgetDao.saveBudget(budget);
   }

   public List<Budget> getAllBudgetsForDateRange(Date startDate, Date endDate) {
      return this.budgetDao.getAllBudgetsForDateRange(startDate, endDate);
   }

   public Budget getBudget(Long budgetId) {
      return this.budgetDao.getBudget(budgetId);
   }

   public List<BudgetLine> getBudgetLinesForBudget(Budget budget) {
      return this.budgetLineDao.getBudgetLinesForBudget(budget);
   }
}
