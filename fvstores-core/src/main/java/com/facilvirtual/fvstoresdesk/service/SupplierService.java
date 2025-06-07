package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.SupplierDao;
import com.facilvirtual.fvstoresdesk.dao.SupplierOnAccountOperationDao;
import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.model.Supplier;
import com.facilvirtual.fvstoresdesk.model.SupplierOnAccountOperation;
import java.text.DecimalFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SupplierService {
   protected static Logger logger = LoggerFactory.getLogger("SupplierService");
   @Autowired
   private SupplierDao supplierDao;
   @Autowired
   private SupplierOnAccountOperationDao supplierOnAccountOperationDao;

   public SupplierService() {
   }

   public void saveSupplierOnAccountOperation(SupplierOnAccountOperation operation) {
      this.supplierOnAccountOperationDao.saveSupplierOnAccountOperation(operation);
   }

   public List<SupplierOnAccountOperation> getActiveSupplierOnAccountOps(Supplier supplier) {
      return this.supplierOnAccountOperationDao.getActiveSupplierOnAccountOps(supplier);
   }

   public void saveNewSupplierOnAccountOperationForPurchase(Purchase purchase) {
      SupplierOnAccountOperation operation = new SupplierOnAccountOperation();
      operation.setCreationDate(purchase.getCreationDate());
      operation.setOperationDate(purchase.getPurchaseDate());
      operation.setLastUpdatedDate(purchase.getCreationDate());
      operation.setCreditType();
      operation.setDescription("Venta");
      operation.setAmount(this.roundValue(purchase.getOnAccountAmount()));
      operation.setAuthor(purchase.getCashier());
      operation.setCashNumber(purchase.getCashNumber());
      operation.setSystem(true);
      operation.setPurchase(purchase);
      operation.setSupplier(purchase.getSupplier());
      this.saveSupplierOnAccountOperation(operation);
   }

   private double roundValue(double valueToRound) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(valueToRound));
      return Double.parseDouble(str.replaceAll(",", "\\."));
   }

   public SupplierOnAccountOperation getSupplierOnAccountOpByPurchaseId(Long purchaseId) {
      return this.supplierOnAccountOperationDao.getSupplierOnAccountOpByPurchaseId(purchaseId);
   }
}
