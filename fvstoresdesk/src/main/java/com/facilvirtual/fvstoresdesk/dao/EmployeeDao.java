package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Employee;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("EmployeeDao");

   public EmployeeDao() {
   }

   public void saveEmployee(Employee employee) {
      this.getSession().saveOrUpdate(employee);
   }

   public Employee getEmployee(Long employeeID) {
      return (Employee)this.getSession().get(Employee.class, employeeID);
   }

   public List<Employee> getAllEmployees() {
      List<Employee> employees = this.getSession().createCriteria(Employee.class).addOrder(Order.asc("username").ignoreCase()).list();
      return employees;
   }

   public List<Employee> getActiveEmployees() {
      List<Employee> employees = this.getSession().createCriteria(Employee.class).add(Restrictions.eq("active", true)).addOrder(Order.asc("username").ignoreCase()).list();
      return employees;
   }

   public Employee getEmployeeByUsername(String username) {
      Employee employee = null;

      try {
         Criteria crit = this.getSession().createCriteria(Employee.class);
         crit.add(Restrictions.eq("username", username).ignoreCase());
         crit.setMaxResults(1);
         employee = (Employee)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Error recuperando usuario por username: " + username);
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return employee;
   }

   public void deleteEmployee(String username) {
      Employee employee = this.getEmployeeByUsername(username);
      if (employee != null) {
         employee.setActive(false);
         this.saveEmployee(employee);
      }

   }
}
