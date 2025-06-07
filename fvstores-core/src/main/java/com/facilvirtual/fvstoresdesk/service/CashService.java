package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.CashOperationDao;
import com.facilvirtual.fvstoresdesk.model.CashOperation;
import com.facilvirtual.fvstoresdesk.model.CustomerOnAccountOperation;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.model.SupplierOnAccountOperation;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CashService {
   protected static Logger logger = LoggerFactory.getLogger("CashService");
   @Autowired
   private CashOperationDao cashOperationDao;

   public CashService() {
   }

   public void saveCashOperation(CashOperation cashOperation) {
      this.cashOperationDao.saveCashOperation(cashOperation);
   }

   public List<CashOperation> getAllCashOperationsForDateRange(int cashNumber, Date startDate, Date endDate) {
      return this.cashOperationDao.getAllCashOperationsForDateRange(cashNumber, startDate, endDate);
   }

   public List<CashOperation> getActiveCashOperationsForDateRange(int cashNumber, Date startDate, Date endDate) {
      return this.cashOperationDao.getActiveCashOperationsForDateRange(cashNumber, startDate, endDate);
   }

   public void saveNewCashOperationForCustomerOperation(CustomerOnAccountOperation customerOperation) {
      CashOperation cashOperation = new CashOperation();
      cashOperation.setCreationDate(customerOperation.getCreationDate());
      cashOperation.setOperationDate(customerOperation.getOperationDate());
      cashOperation.setLastUpdatedDate(customerOperation.getCreationDate());
      cashOperation.setIncomeType();
      cashOperation.setDescription("Cobro a cliente");
      cashOperation.setAmount(customerOperation.getAmount());
      cashOperation.setAuthor(customerOperation.getAuthor());
      cashOperation.setCashNumber(customerOperation.getCashNumber());
      cashOperation.setSystem(true);
      cashOperation.setCustomerOnAccountOperation(customerOperation);
      this.saveCashOperation(cashOperation);
   }

   public void saveNewCashOperationForSupplierOperation(SupplierOnAccountOperation supplierOperation) {
      CashOperation cashOperation = new CashOperation();
      cashOperation.setCreationDate(supplierOperation.getCreationDate());
      cashOperation.setOperationDate(supplierOperation.getOperationDate());
      cashOperation.setLastUpdatedDate(supplierOperation.getCreationDate());
      cashOperation.setOutflowType();
      cashOperation.setDescription("Pago a proveedor");
      cashOperation.setAmount(supplierOperation.getAmount());
      cashOperation.setAuthor(supplierOperation.getAuthor());
      cashOperation.setCashNumber(supplierOperation.getCashNumber());
      cashOperation.setSystem(true);
      cashOperation.setSupplierOnAccountOperation(supplierOperation);
      this.saveCashOperation(cashOperation);
   }

   public void saveNewCashOperationForOrder(Order order) {
      if (order.getNetCashAmount() > 0.0) {
         CashOperation cashOperation = new CashOperation();
         cashOperation.setCreationDate(order.getCreationDate());
         cashOperation.setOperationDate(order.getSaleDate());
         cashOperation.setLastUpdatedDate(order.getCreationDate());
         cashOperation.setIncomeType();
         cashOperation.setDescription("Venta");
         cashOperation.setAmount(order.getNetCashAmount());
         cashOperation.setAuthor(order.getCashier());
         cashOperation.setCashNumber(order.getCashNumber());
         cashOperation.setSystem(true);
         cashOperation.setOrder(order);
         this.saveCashOperation(cashOperation);
      }

   }

   public void saveNewOpenCashOperation(Date creationDate, int cashNumber, Employee employee) {
      CashOperation cashOperation = new CashOperation();
      cashOperation.setCreationDate(creationDate);
      cashOperation.setOperationDate(creationDate);
      cashOperation.setLastUpdatedDate(creationDate);
      cashOperation.setBalanceType();
      cashOperation.setDescription("Apertura de caja");
      cashOperation.setAuthor(employee);
      cashOperation.setCashNumber(cashNumber);
      cashOperation.setSystem(true);
      this.saveCashOperation(cashOperation);
   }

   public void saveNewInitialCash(Date creationDate, int cashNumber, double amount, Employee employee) {
      CashOperation cashOperation = new CashOperation();
      cashOperation.setCreationDate(creationDate);
      cashOperation.setOperationDate(creationDate);
      cashOperation.setLastUpdatedDate(creationDate);
      cashOperation.setIncomeType();
      cashOperation.setDescription("Caja inicial");
      cashOperation.setAmount(amount);
      cashOperation.setAuthor(employee);
      cashOperation.setCashNumber(cashNumber);
      cashOperation.setSystem(true);
      this.saveCashOperation(cashOperation);
   }

   public void saveNewWithdrawCash(Date creationDate, int cashNumber, double amount, Employee employee) {
      CashOperation cashOperation = new CashOperation();
      cashOperation.setCreationDate(creationDate);
      cashOperation.setOperationDate(creationDate);
      cashOperation.setLastUpdatedDate(creationDate);
      cashOperation.setOutflowType();
      cashOperation.setDescription("Retiro de dinero");
      cashOperation.setAmount(amount);
      cashOperation.setAuthor(employee);
      cashOperation.setCashNumber(cashNumber);
      cashOperation.setSystem(true);
      this.saveCashOperation(cashOperation);
   }

   public void saveNewCloseCashOperation(Date creationDate, int cashNumber, Employee employee) {
      CashOperation cashOperation = new CashOperation();
      cashOperation.setCreationDate(creationDate);
      cashOperation.setOperationDate(creationDate);
      cashOperation.setLastUpdatedDate(creationDate);
      cashOperation.setBalanceType();
      cashOperation.setDescription("Cierre de caja");
      cashOperation.setAuthor(employee);
      cashOperation.setCashNumber(cashNumber);
      cashOperation.setSystem(true);
      this.saveCashOperation(cashOperation);
   }

   public CashOperation getCashOperationByOrderId(Long orderId) {
      return this.cashOperationDao.getCashOperationByOrderId(orderId);
   }

   public CashOperation getCashOperationByPurchaseId(Long purchaseId) {
      return this.cashOperationDao.getCashOperationByPurchaseId(purchaseId);
   }

   public void saveNewCashOperationForPurchase(Purchase purchase) {
      if (purchase.getCashAmount() > 0.0) {
         CashOperation cashOperation = new CashOperation();
         cashOperation.setCreationDate(purchase.getCreationDate());
         cashOperation.setOperationDate(purchase.getPurchaseDate());
         cashOperation.setLastUpdatedDate(purchase.getCreationDate());
         cashOperation.setOutflowType();
         cashOperation.setDescription("Compra");
         cashOperation.setAmount(purchase.getCashAmount());
         cashOperation.setAuthor(purchase.getCashier());
         cashOperation.setCashNumber(purchase.getCashNumber());
         cashOperation.setSystem(true);
         cashOperation.setPurchase(purchase);
         this.saveCashOperation(cashOperation);
      }

   }

   public boolean mustUpdateCashAmount(WorkstationConfig workstationConfig, Date operationDate) {
      boolean mustUpdate = false;
      CashOperation openCashOperation = this.getLastOpenCashOperation(workstationConfig.getCashNumber());
      if (openCashOperation != null && openCashOperation.getOperationDate().before(operationDate) && workstationConfig.isCashOpened()) {
         mustUpdate = true;
      }

      return mustUpdate;
   }

   private CashOperation getLastOpenCashOperation(int cashNumber) {
      return this.cashOperationDao.getLastOpenCashOperation(cashNumber);
   }
}
