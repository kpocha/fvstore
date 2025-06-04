package com.facilvirtual.fvstoresdesk.ui.screens.inventory;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.model.SupplierForProduct;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditProduct extends AddNewProduct {
   private static Logger logger = LoggerFactory.getLogger("EditProductDialog");

   public EditProduct(Shell parentShell) {
      super(parentShell);
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Modificar artículo");
   }

   protected Control createDialogArea(Composite parent) {
      try {
         logger.info("Iniciando creación del diálogo de edición");
         
         if (this.getProduct() == null) {
            logger.error("No se puede editar: producto es null");
            throw new IllegalStateException("No se puede editar: producto no inicializado");
         }
         
         Composite container = (Composite)super.createDialogArea(parent);
         
         // Inicializar campos básicos
         this.txtBarCode.setText(this.getProduct().getBarCode());
         this.txtBarCode.setEditable(false);
         this.txtDescription.setText(this.getProduct().getDescription());
         
         // Inicializar unidad de venta
         initSellingUnit();
         
         // Inicializar categoría
         initCategory();
         
         // Inicializar fechas y alertas
         initDatesAndAlerts();
         
         // Inicializar otros campos
         this.btnInWeb.setSelection(this.getProduct().isInWeb());
         this.btnDiscontinued.setVisible(true);
         this.btnDiscontinued.setSelection(this.product.isDiscontinued());
         
         // Inicializar descripción corta
         if (this.getProduct().getShortDescription() != null) {
            this.txtShortDescription.setText(this.product.getShortDescription());
         }
         
         // Inicializar cantidad
         initQuantity();
         
         // Inicializar tabs
         try {
            this.initTabPrices();
         } catch (Exception e) {
            logger.error("Error al inicializar tab de precios", e);
         }
         
         try {
            this.initTabStock();
         } catch (Exception e) {
            logger.error("Error al inicializar tab de stock", e);
         }
         
         try {
            this.initTabSuppliers();
         } catch (Exception e) {
            logger.error("Error al inicializar tab de proveedores", e);
         }
         
         this.updateLabelPreview();
         
         logger.info("Diálogo de edición creado exitosamente");
         return container;
         
      } catch (Exception e) {
         logger.error("Error al crear el diálogo de edición", e);
         throw new RuntimeException("Error al crear el diálogo de edición: " + e.getMessage(), e);
      }
   }
   
   private void initSellingUnit() {
      if (this.getProduct().getSellingUnit().equalsIgnoreCase("UN")) {
         this.comboUnits.select(0);
      } else {
         this.comboUnits.select(1);
      }
   }
   
   private void initCategory() {
      this.btnInOffer.setSelection(this.getProduct().isInOffer());
      List<ProductCategory> categories = this.getProductService().getActiveProductCategories();
      Collections.sort(categories);
      
      if (this.getProduct().getCategory().getName().equalsIgnoreCase("Sin Clasificar")) {
         this.comboProductCategories.select(0);
      } else {
         int idx = 1;
         for(ProductCategory c : categories) {
            if (this.getProduct().getCategory().getName().equalsIgnoreCase(c.getName())) {
               this.comboProductCategories.select(idx);
               break;
            }
            idx++;
         }
      }
   }
   
   private void initDatesAndAlerts() {
      this.txtExpirationDate.setText(this.getProduct().getExpirationDateToDisplay());
      this.txtAlertExpDays.setText(String.valueOf(this.getProduct().getAlertExpDays()));
      this.btnAlertExpActive.setSelection(this.getProduct().getAlertExpActive());
   }
   
   private void initQuantity() {
      this.txtQuantity.setText(this.product.getQuantityToDisplay());
      
      String quantityUnit = this.getProduct().getQuantityUnit().toUpperCase();
      switch(quantityUnit) {
         case "":
            this.comboQuantityUnit.select(0);
            break;
         case "CC":
            this.comboQuantityUnit.select(1);
            break;
         case "GRS":
            this.comboQuantityUnit.select(2);
            break;
         case "UNI":
            this.comboQuantityUnit.select(3);
            break;
         case "KGS":
            this.comboQuantityUnit.select(4);
            break;
         case "LTS":
            this.comboQuantityUnit.select(5);
            break;
         default:
            this.comboQuantityUnit.select(0);
      }
   }

   private void initTabPrices() {
      try {
         List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
         Iterator var3 = priceLists.iterator();

         while(var3.hasNext()) {
            PriceList priceList = (PriceList)var3.next();
            TableItem tableItem = new TableItem(this.tablePrices, 0);
            tableItem.setText(0, String.valueOf(priceList.getName()));
            ProductPrice productPrice = this.getProductService().getProductPriceForProduct(this.product, priceList);
            if (productPrice != null) {
               tableItem.setText(1, String.valueOf(productPrice.getCostPriceToDisplay()));
               if (productPrice.getVat() != null) {
                  tableItem.setText(2, String.valueOf(productPrice.getVat().getName()));
               }

               tableItem.setText(3, String.valueOf(productPrice.getGrossMarginToDisplay()));
               tableItem.setText(4, String.valueOf(productPrice.getSellingPriceToDisplay()));
               tableItem.setText(5, String.valueOf(productPrice.getLastUpdatedPriceToDisplay()));
            } else {
               tableItem.setText(1, String.valueOf("0,00"));
               tableItem.setText(2, String.valueOf("21%"));
               tableItem.setText(3, String.valueOf("0,00"));
               tableItem.setText(4, String.valueOf("0,00"));
               tableItem.setText(5, String.valueOf(""));
            }
         }
      } catch (Exception var6) {
      }

   }

   private void initTabSuppliers() {
      int defaultSupplierId = 1;
      SupplierForProduct supplierForProduct1 = this.getProductService().getSupplierForProduct(this.getProduct(), 1);
      if (supplierForProduct1 != null) {
         this.setSupplierForProduct1(supplierForProduct1);
         if (supplierForProduct1.isDefaultSupplier()) {
            defaultSupplierId = 1;
         }
      }

      SupplierForProduct supplierForProduct2 = this.getProductService().getSupplierForProduct(this.getProduct(), 2);
      if (supplierForProduct2 != null) {
         this.setSupplierForProduct2(supplierForProduct2);
         if (supplierForProduct2.isDefaultSupplier()) {
            defaultSupplierId = 2;
         }
      }

      SupplierForProduct supplierForProduct3 = this.getProductService().getSupplierForProduct(this.getProduct(), 3);
      if (supplierForProduct3 != null) {
         this.setSupplierForProduct3(supplierForProduct3);
         if (supplierForProduct3.isDefaultSupplier()) {
            defaultSupplierId = 3;
         }
      }

      SupplierForProduct supplierForProduct4 = this.getProductService().getSupplierForProduct(this.getProduct(), 4);
      if (supplierForProduct4 != null) {
         this.setSupplierForProduct4(supplierForProduct4);
         if (supplierForProduct4.isDefaultSupplier()) {
            defaultSupplierId = 4;
         }
      }

      if (defaultSupplierId != 1) {
         this.getSupplierForProduct1().setDefaultSupplier(false);
      }

      this.createSuppliersContents();
   }

   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 0, IDialogConstants.OK_LABEL, false);
      button.setFont(SWTResourceManager.getFont("Tahoma", 8, 1));
      button.setText("Guardar");
      this.createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
   }

   protected Point getInitialSize() {
      return new Point(707, 465);
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.editProduct();
      } else {
         this.deletePhotoTmpFile();
         this.close();
      }

   }

   private void editProduct() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            Date editionDate = new Date();
            String categoryName = "- Sin clasificar -".equalsIgnoreCase(this.comboProductCategories.getText()) ? "Sin clasificar" : this.comboProductCategories.getText();
            ProductCategory productCategory = this.getProductService().getProductCategoryByName(categoryName);
            String newDescription = this.txtDescription.getText().trim();
            if (!this.product.getDescription().equals(newDescription)) {
               this.product.setDescription(newDescription);
               this.product.setLastUpdatedDescription(editionDate);
            }

            this.product.setSellingUnit(this.comboUnits.getText());
            this.product.setInOffer(this.btnInOffer.getSelection());
            if (!this.product.getCategory().getName().equalsIgnoreCase(productCategory.getName())) {
               this.product.setCategory(productCategory);
               this.product.setLastUpdatedCategory(editionDate);
            }

            this.product.setInWeb(this.btnInWeb.getSelection());
            if (this.btnDiscontinued.getSelection()) {
               this.product.setDiscontinued(true);
            } else if (!this.getWorkstationConfig().isTrialMode() || this.getWorkstationConfig().isTrialMode() && this.getWorkstationConfig().getTrialMaxProductsQty() > this.getProductService().getActiveProductsQty()) {
               this.product.setDiscontinued(false);
            }

            if (!"".equals(this.txtExpirationDate.getText().trim())) {
               Date expirationDate = this.getDateFromText(this.txtExpirationDate);
               this.product.setExpirationDate(expirationDate);
            } else {
               this.product.setExpirationDate((Date)null);
            }

            Integer expDays = this.getIntegerValueFromText(this.txtAlertExpDays);
            if (expDays != null) {
               this.product.setAlertExpDays(expDays);
            } else {
               this.product.setAlertExpDays(15);
            }

            this.product.setAlertExpActive(this.btnAlertExpActive.getSelection());

            try {
               this.product.setStockControlEnabled(this.btnStockControlEnabled.getSelection());
               this.product.setStock(Double.valueOf(this.txtStock.getText().trim().replaceAll(",", ".")));
               this.product.setStockMin(Double.valueOf(this.txtStockMin.getText().trim().replaceAll(",", ".")));
               this.product.setStockMax(Double.valueOf(this.txtStockMax.getText().trim().replaceAll(",", ".")));
            } catch (Exception var7) {
            }

            this.savePrices();
            this.saveSuppliers();
            this.product.setShortDescription(this.txtShortDescription.getText().trim());
            if (!"".equals(this.txtQuantity.getText().trim())) {
               this.product.setQuantity(Double.valueOf(this.txtQuantity.getText().trim().replaceAll(",", ".")));
            } else {
               this.product.setQuantity(0.0);
            }

            this.product.setQuantityUnit(this.comboQuantityUnit.getText());
            if (this.isDeletePhoto()) {
               this.deleteProductPhoto();
            } else {
               this.saveProductPhoto();
            }

            this.getAppConfigService().backupDB("data2");
         } catch (Exception var8) {
            logger.error("Error al guardar en Modificar artículo");
         }

         this.close();
      }

   }

   private void saveSuppliers() {
      try {
         Date editionDate = new Date();
         SupplierForProduct supplierForProduct1 = this.getProductService().getSupplierForProduct(this.getProduct(), 1);
         if (supplierForProduct1 != null) {
            this.updateSupplierForProduct1(supplierForProduct1, editionDate);
         } else {
            this.addNewSupplierForProduct1(editionDate);
         }

         SupplierForProduct supplierForProduct2 = this.getProductService().getSupplierForProduct(this.getProduct(), 2);
         if (supplierForProduct2 != null) {
            this.updateSupplierForProduct2(supplierForProduct2, editionDate);
         } else {
            this.addNewSupplierForProduct2(editionDate);
         }

         SupplierForProduct supplierForProduct3 = this.getProductService().getSupplierForProduct(this.getProduct(), 3);
         if (supplierForProduct3 != null) {
            this.updateSupplierForProduct3(supplierForProduct3, editionDate);
         } else {
            this.addNewSupplierForProduct3(editionDate);
         }

         SupplierForProduct supplierForProduct4 = this.getProductService().getSupplierForProduct(this.getProduct(), 4);
         if (supplierForProduct4 != null) {
            this.updateSupplierForProduct4(supplierForProduct4, editionDate);
         } else {
            this.addNewSupplierForProduct4(editionDate);
         }
      } catch (Exception var6) {
      }

   }

   private boolean deletedSupplierForProduct(SupplierForProduct s1) {
      return s1.getSupplier() == null && s1.getCostPrice() == 0.0 && s1.getLastUpdatedDate() == null;
   }

   private boolean updatedSupplierForProduct(SupplierForProduct s1, SupplierForProduct s2) {
      boolean cond1 = s1.getSupplier() != null && s2.getSupplier() == null || s1.getSupplier() == null && s2.getSupplier() != null || s1.getSupplier() != null && s2.getSupplier() != null && s1.getSupplier().getId() != s2.getSupplier().getId();
      boolean cond2 = s1.getCostPrice() != s2.getCostPrice();
      return cond1 || cond2;
   }

   private void updateSupplierForProduct1(SupplierForProduct supplierForProduct1, Date editionDate) {
      if (this.deletedSupplierForProduct(this.getSupplierForProduct1())) {
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct1());
      } else if (this.updatedSupplierForProduct(this.getSupplierForProduct1(), supplierForProduct1)) {
         this.getSupplierForProduct1().setLastUpdatedDate(editionDate);
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct1());
      } else if (this.getSupplierForProduct1().isDefaultSupplier() != supplierForProduct1.isDefaultSupplier()) {
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct1());
      }

      if (this.getSupplierForProduct1().isDefaultSupplier()) {
         this.getProduct().setSupplier(this.getSupplierForProduct1().getSupplier());
         this.getProductService().saveProduct(this.getProduct());
      }

   }

   private void updateSupplierForProduct2(SupplierForProduct supplierForProduct2, Date editionDate) {
      if (this.deletedSupplierForProduct(this.getSupplierForProduct2())) {
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct2());
      } else if (this.updatedSupplierForProduct(this.getSupplierForProduct2(), supplierForProduct2)) {
         this.getSupplierForProduct2().setLastUpdatedDate(editionDate);
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct2());
      } else if (this.getSupplierForProduct2().isDefaultSupplier() != supplierForProduct2.isDefaultSupplier()) {
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct2());
      }

      if (this.getSupplierForProduct2().isDefaultSupplier()) {
         this.getProduct().setSupplier(this.getSupplierForProduct2().getSupplier());
         this.getProductService().saveProduct(this.getProduct());
      }

   }

   private void updateSupplierForProduct3(SupplierForProduct supplierForProduct3, Date editionDate) {
      if (this.deletedSupplierForProduct(this.getSupplierForProduct3())) {
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct3());
      } else if (this.updatedSupplierForProduct(this.getSupplierForProduct3(), supplierForProduct3)) {
         this.getSupplierForProduct3().setLastUpdatedDate(editionDate);
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct3());
      } else if (this.getSupplierForProduct3().isDefaultSupplier() != supplierForProduct3.isDefaultSupplier()) {
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct3());
      }

      if (this.getSupplierForProduct3().isDefaultSupplier()) {
         this.getProduct().setSupplier(this.getSupplierForProduct3().getSupplier());
         this.getProductService().saveProduct(this.getProduct());
      }

   }

   private void updateSupplierForProduct4(SupplierForProduct supplierForProduct4, Date editionDate) {
      if (this.deletedSupplierForProduct(this.getSupplierForProduct4())) {
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct4());
      } else if (this.updatedSupplierForProduct(this.getSupplierForProduct4(), supplierForProduct4)) {
         this.getSupplierForProduct4().setLastUpdatedDate(editionDate);
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct4());
      } else if (this.getSupplierForProduct4().isDefaultSupplier() != supplierForProduct4.isDefaultSupplier()) {
         this.getProductService().saveSupplierForProduct(this.getSupplierForProduct4());
      }

      if (this.getSupplierForProduct4().isDefaultSupplier()) {
         this.getProduct().setSupplier(this.getSupplierForProduct4().getSupplier());
         this.getProductService().saveProduct(this.getProduct());
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtDescription.getText().trim())) {
         valid = false;
         this.alert("Ingresa la Descripción");
      }

      if (valid && !"".equals(this.txtExpirationDate.getText().trim())) {
         Date expDate = this.getDateFromText(this.txtExpirationDate);
         if (expDate == null) {
            valid = false;
            this.alert("La fecha de vencimiento no es válida");
         }
      }

      if (valid && !"".equals(this.txtAlertExpDays.getText().trim())) {
         Integer alertExpDays = this.getIntegerValueFromText(this.txtAlertExpDays);
         if (alertExpDays == null) {
            valid = false;
            this.alert("El valor ingresado en Alerta Vencimiento no es válido");
         }
      }

      if (valid && !"".equals(this.txtQuantity.getText().trim())) {
         try {
            double var6 = Double.valueOf(this.txtQuantity.getText().trim().replaceAll(",", "."));
         } catch (Exception var4) {
            valid = false;
            this.alert("El valor ingresado en Cantidad (Etiqueta) no es válido");
         }
      }

      return valid;
   }
}
