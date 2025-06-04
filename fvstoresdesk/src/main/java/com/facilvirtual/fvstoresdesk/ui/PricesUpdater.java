package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class PricesUpdater extends AbstractFVDialog {
   private String action = "";
   private Combo comboPriceLists;
   private Label lblRubro;
   private Combo comboProductCategories;
   private Label lblVariacinPrecioDe;
   private Text txtCostVariation;
   private static Logger logger = LoggerFactory.getLogger("PricesUpdater");

   public PricesUpdater(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 16777216);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.right = new FormAttachment(100, -81);
      fd_lblTitle.left = new FormAttachment(0, 68);
      fd_lblTitle.top = new FormAttachment(0, 25);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Actualización masiva de precios");
      Label lblLista = new Label(container, 0);
      lblLista.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblLista = new FormData();
      fd_lblLista.left = new FormAttachment(0, 190);
      lblLista.setLayoutData(fd_lblLista);
      lblLista.setText("Lista");
      this.comboPriceLists = new Combo(container, 8);
      fd_lblLista.top = new FormAttachment(this.comboPriceLists, 2, 128);
      fd_lblLista.right = new FormAttachment(this.comboPriceLists, -6);
      FormData fd_combo = new FormData();
      fd_combo.top = new FormAttachment(lblTitle, 42);
      fd_combo.left = new FormAttachment(0, 222);
      fd_combo.right = new FormAttachment(100, -121);
      this.comboPriceLists.setLayoutData(fd_combo);
      List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
      Iterator var11 = priceLists.iterator();

      while(var11.hasNext()) {
         PriceList priceList = (PriceList)var11.next();
         this.comboPriceLists.add(priceList.getName());
      }

      this.comboPriceLists.select(0);
      this.lblRubro = new Label(container, 0);
      this.lblRubro.setText("Rubro");
      this.lblRubro.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblRubro = new FormData();
      fd_lblRubro.left = new FormAttachment(0, 182);
      fd_lblRubro.right = new FormAttachment(lblLista, 0, 131072);
      this.lblRubro.setLayoutData(fd_lblRubro);
      this.comboProductCategories = new Combo(container, 8);
      fd_lblRubro.top = new FormAttachment(this.comboProductCategories, 2, 128);
      FormData fd_comboProductCategories = new FormData();
      fd_comboProductCategories.left = new FormAttachment(0, 222);
      fd_comboProductCategories.top = new FormAttachment(this.comboPriceLists, 34);
      fd_comboProductCategories.right = new FormAttachment(100, -119);
      this.comboProductCategories.setLayoutData(fd_comboProductCategories);
      this.lblVariacinPrecioDe = new Label(container, 0);
      this.lblVariacinPrecioDe.setText("Variación Precio de Costo (%)");
      this.lblVariacinPrecioDe.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblVariacinPrecioDe = new FormData();
      fd_lblVariacinPrecioDe.left = new FormAttachment(0, 44);
      fd_lblVariacinPrecioDe.right = new FormAttachment(lblLista, 0, 131072);
      this.lblVariacinPrecioDe.setLayoutData(fd_lblVariacinPrecioDe);
      this.txtCostVariation = new Text(container, 2048);
      fd_lblVariacinPrecioDe.top = new FormAttachment(this.txtCostVariation, 2, 128);
      FormData fd_txtCostVariation = new FormData();
      fd_txtCostVariation.top = new FormAttachment(this.comboProductCategories, 33);
      fd_txtCostVariation.left = new FormAttachment(this.lblVariacinPrecioDe, 6);
      this.txtCostVariation.setLayoutData(fd_txtCostVariation);
      List<ProductCategory> categories = this.getProductService().getActiveProductCategories();
      Collections.sort(categories);
      this.comboProductCategories.add("- Sin clasificar -");
      this.comboProductCategories.select(0);
      Iterator var16 = categories.iterator();

      while(var16.hasNext()) {
         ProductCategory c = (ProductCategory)var16.next();
         if (!"Sin clasificar".equalsIgnoreCase(c.getName())) {
            this.comboProductCategories.add(c.getName());
         }
      }

      this.comboProductCategories.select(0);
      return container;
   }

   private void updatePrices() {
      try {
         logger.info("Actualización masiva de precios");
         PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
         ProductCategory productCategory = this.getProductService().getProductCategoryByName(this.comboProductCategories.getText());
         logger.info("Lista : " + priceList.getName());
         logger.info("Rubro : " + productCategory.getName());
         logger.info("% Variación Costo: " + this.txtCostVariation.getText());
         List<Product> products = this.getProductService().getProductsByCategory(productCategory);
         Iterator var5 = products.iterator();

         while(true) {
            while(var5.hasNext()) {
               Product p = (Product)var5.next();
               ProductPrice productPrice = this.getProductService().getProductPriceForProduct(p, priceList);
               logger.info("Actualizando producto: " + p.getBarCode() + " - " + p.getDescription());
               if (productPrice != null && productPrice.getCostPrice() > 0.0) {
                  logger.info("Precio Costo Anterior: " + productPrice.getCostPriceToDisplay());
                  logger.info("Precio Venta Anterior: " + productPrice.getSellingPriceToDisplay());
                  productPrice.updateCostPriceForVariationPercent(this.getDoubleValueFromText(this.txtCostVariation));
                  productPrice.updateSellingPriceForCostChanged(new Date());
                  this.getProductService().saveProductPrice(productPrice);
                  logger.info("Precio Costo Nuevo: " + productPrice.getCostPriceToDisplay());
                  logger.info("Precio Venta Nuevo: " + productPrice.getSellingPriceToDisplay());
               } else {
                  logger.info("No se actualizó el producto porque el Precio de Costo es 0.");
               }
            }

            return;
         }
      } catch (Exception var7) {
         logger.error("Error en Actualización masiva de precios");
         logger.error(var7.getMessage());
        // //logger.error(var7);
      }
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.updatePrices();
         this.setAction("OK");
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      try {
         double var2 = Double.parseDouble(this.txtCostVariation.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
         valid = false;
         this.alert("El valor ingresado en Variación Precio de Costo (%) no es válido");
      }

      return valid;
   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Actualización masiva de precios");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(524, 385);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }
}
