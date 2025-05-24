package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.EmployeeDao;
import com.facilvirtual.fvstoresdesk.model.Employee;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
   protected static Logger logger = LoggerFactory.getLogger("AccountService");
   @Autowired
   private EmployeeDao employeeDao;

   public AccountService() {
   }

   public void saveEmployee(Employee employee) {
      this.employeeDao.saveEmployee(employee);
   }

   public Employee getEmployee(Long employeeId) {
      return this.employeeDao.getEmployee(employeeId);
   }

   public List<Employee> getAllEmployees() {
      return this.employeeDao.getAllEmployees();
   }

   public List<Employee> getActiveEmployees() {
      return this.employeeDao.getActiveEmployees();
   }

   public Employee getEmployeeByUsername(String username) {
      return this.employeeDao.getEmployeeByUsername(username);
   }

   public void deleteEmployee(String username) {
      this.employeeDao.deleteEmployee(username);
   }
}
