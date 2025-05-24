package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.Vat;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EditProductCategory extends AddNewProductCategory {
   private static Logger logger = LoggerFactory.getLogger("EditProductCategory");

   public EditProductCategory(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      this.initCategory();
      this.lblTitle.setText("Modificar rubro");
      return container;
   }

   private void initCategory() {
      try {
         this.txtCategoryName.setText(this.getCategory().getName());
         this.txtCategoryName.setEditable(false);
         this.comboVat.select(0);
         List<Vat> vats = this.getOrderService().getAllVats();
         int vatIdx = 0;

         for(Iterator var4 = vats.iterator(); var4.hasNext(); ++vatIdx) {
            Vat vat = (Vat)var4.next();
            if (this.getCategory().getVat() != null && this.getCategory().getVat().getId().equals(vat.getId())) {
               this.comboVat.select(vatIdx);
            }
         }
      } catch (Exception var5) {
         logger.error("Error al inicializar rubro para editar");
         logger.error(var5.getMessage());
         ////logger.error(var5);
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            String vatName = this.comboVat.getText();
            if (!"".equals(vatName)) {
               Vat vat = this.getOrderService().getVatByName(vatName);
               this.category.setVat(vat);
            } else {
               this.category.setVat((Vat)null);
            }

            this.getProductService().saveProductCategory(this.category);
         } catch (Exception var3) {
            logger.error("Error al guardar rubro");
            logger.error(var3.getMessage());
           // //logger.error(var3);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Modificar rubro");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Guardar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(402, 303);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public ProductCategory getCategory() {
      return this.category;
   }

   public void setCategory(ProductCategory category) {
      this.category = category;
   }
}
