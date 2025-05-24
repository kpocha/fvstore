package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.PurchaseDao;
import com.facilvirtual.fvstoresdesk.dao.PurchaseLineDao;
import com.facilvirtual.fvstoresdesk.model.CashOperation;
import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.model.PurchaseLine;
import com.facilvirtual.fvstoresdesk.model.SupplierOnAccountOperation;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PurchaseService {
   protected static Logger logger = LoggerFactory.getLogger("PurchaseService");
   @Autowired
   private PurchaseDao purchaseDao;
   @Autowired
   private PurchaseLineDao purchaseLineDao;

   public PurchaseService() {
   }

   public void savePurchase(Purchase purchase) {
      this.purchaseDao.savePurchase(purchase);
   }

   public List<Purchase> getAllPurchasesForDateRange(Date startDate, Date endDate) {
      return this.purchaseDao.getAllPurchasesForDateRange(startDate, endDate);
   }

   public Purchase getPurchase(Long purchaseId) {
      return this.purchaseDao.getPurchase(purchaseId);
   }

   public List<PurchaseLine> getPurchaseLinesForPurchase(Purchase purchase) {
      return this.purchaseLineDao.getPurchaseLinesForPurchase(purchase);
   }

   public CashService getCashService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (CashService)context.getBean("cashService");
   }

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   public SupplierService getSupplierService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (SupplierService)context.getBean("supplierService");
   }

   public ProductService getProductService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (ProductService)context.getBean("productService");
   }

   public void cancelPurchase(Long purchaseId) {
      Purchase purchase = this.getPurchase(purchaseId);
      if (purchase != null && !purchase.isCancelled()) {
         logger.info("Cancelando compra - Nro. Trans.:" + purchaseId);
         purchase.setStatus("CANCELLED");
         this.savePurchase(purchase);
         CashOperation cashOperation = this.getCashService().getCashOperationByPurchaseId(purchase.getId());
         if (cashOperation != null) {
            cashOperation.setActive(false);
            this.getCashService().saveCashOperation(cashOperation);
            WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
            if (this.getCashService().mustUpdateCashAmount(workstationConfig, cashOperation.getOperationDate())) {
               this.getAppConfigService().incCashAmount(workstationConfig, purchase.getCashAmount());
            }
         }

         SupplierOnAccountOperation supplierOnAccountOp = this.getSupplierService().getSupplierOnAccountOpByPurchaseId(purchase.getId());
         if (supplierOnAccountOp != null) {
            supplierOnAccountOp.setActive(false);
            this.getSupplierService().saveSupplierOnAccountOperation(supplierOnAccountOp);
         }

         this.getProductService().restoreLastPurchaseDateForPurchase(purchase);
         if (purchase.isUpdateStock()) {
            this.getProductService().restoreStockForPurchase(purchase);
         }

         if (purchase.isUpdateStock()) {
            this.getProductService().restorePricesForPurchase(purchase);
         }
      }

   }

   public void restorePurchase(Long purchaseId) {
      Purchase purchase = this.getPurchase(purchaseId);
      if (purchase != null && !purchase.isCompleted()) {
         logger.info("Restaurando compra - Nro. Trans.:" + purchaseId);
         purchase.setStatus("COMPLETED");
         this.savePurchase(purchase);
         CashOperation cashOperation = this.getCashService().getCashOperationByPurchaseId(purchase.getId());
         if (cashOperation != null) {
            cashOperation.setActive(true);
            this.getCashService().saveCashOperation(cashOperation);
            WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
            if (this.getCashService().mustUpdateCashAmount(workstationConfig, cashOperation.getOperationDate())) {
               this.getAppConfigService().decCashAmount(workstationConfig, purchase.getCashAmount());
            }
         }

         SupplierOnAccountOperation supplierOnAccountOp = this.getSupplierService().getSupplierOnAccountOpByPurchaseId(purchase.getId());
         if (supplierOnAccountOp != null) {
            supplierOnAccountOp.setActive(true);
            this.getSupplierService().saveSupplierOnAccountOperation(supplierOnAccountOp);
         }

         this.getProductService().updateLastPurchaseDateForPurchase(purchase);
         if (purchase.isUpdateStock()) {
            this.getProductService().updateStockForPurchase(purchase);
         }

         if (purchase.isUpdateStock()) {
            this.getProductService().updatePricesForPurchase(purchase);
         }
      }

   }
}
