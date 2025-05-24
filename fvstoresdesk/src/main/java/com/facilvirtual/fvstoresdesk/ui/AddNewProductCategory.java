package com.facilvirtual.fvstoresdesk.ui;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.Vat;

public class AddNewProductCategory extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("AddNewProductCategory");
   protected String action = "";
   protected ProductCategory category;
   protected Text txtCategoryName;
   protected Combo comboVat;
   protected Label lblTitle;

   public AddNewProductCategory(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      this.lblTitle = new Label(container, 16777216);
      this.lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.left = new FormAttachment(0, 73);
      fd_lblTitle.right = new FormAttachment(100, -67);
      fd_lblTitle.top = new FormAttachment(0, 28);
      this.lblTitle.setLayoutData(fd_lblTitle);
      this.lblTitle.setText("Agregar rubro");
      Label lblNombreDelArchivo = new Label(container, 0);
      lblNombreDelArchivo.setText("Nombre del rubro:");
      lblNombreDelArchivo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblNombreDelArchivo = new FormData();
      fd_lblNombreDelArchivo.right = new FormAttachment(100, -200);
      fd_lblNombreDelArchivo.left = new FormAttachment(0, 54);
      lblNombreDelArchivo.setLayoutData(fd_lblNombreDelArchivo);
      this.txtCategoryName = new Text(container, 2048);
      fd_lblNombreDelArchivo.bottom = new FormAttachment(100, -132);
      this.txtCategoryName.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtCategoryName = new FormData();
      fd_txtCategoryName.top = new FormAttachment(lblNombreDelArchivo, 6);
      fd_txtCategoryName.left = new FormAttachment(0, 54);
      fd_txtCategoryName.right = new FormAttachment(100, -62);
      this.txtCategoryName.setLayoutData(fd_txtCategoryName);
      Label lblIva = new Label(container, 0);
      lblIva.setText("Iva:");
      lblIva.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblIva = new FormData();
      fd_lblIva.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      fd_lblIva.right = new FormAttachment(100, -236);
      lblIva.setLayoutData(fd_lblIva);
      this.comboVat = new Combo(container, 8);
      fd_lblIva.bottom = new FormAttachment(this.comboVat, -6);
      FormData fd_comboVat = new FormData();
      fd_comboVat.top = new FormAttachment(0, 150);
      fd_comboVat.left = new FormAttachment(lblNombreDelArchivo, 0, 16384);
      this.comboVat.setLayoutData(fd_comboVat);
      this.comboVat.select(0);
      List<Vat> vats = this.getOrderService().getAllVats();
      int selectedIdx = 0;

      for(Iterator var14 = vats.iterator(); var14.hasNext(); ++selectedIdx) {
         Vat v = (Vat)var14.next();
         this.comboVat.add(v.getName());
         if ("21%".equalsIgnoreCase(v.getName())) {
            this.comboVat.select(selectedIdx);
         }
      }

      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            ProductCategory category = new ProductCategory();
            category.setName(this.txtCategoryName.getText().trim());
            String vatName = this.comboVat.getText();
            if (!"".equals(vatName)) {
               Vat vat = this.getOrderService().getVatByName(vatName);
               category.setVat(vat);
            }

            this.getProductService().saveProductCategory(category);
            this.setCategory(category);
         } catch (Exception var4) {
            logger.error("Error al guardar rubro");
            logger.error(var4.getMessage());
            //logger.error(var4);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtCategoryName.getText().trim())) {
         valid = false;
         this.alert("Ingresa el nombre del rubro");
      }

      if (valid) {
         ProductCategory category = this.getProductService().getProductCategoryByName(this.txtCategoryName.getText().trim());
         if (category != null) {
            valid = false;
            this.alert("Ya existe un rubro con ese nombre");
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nuevo rubro");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 0, "Aceptar", false);
      button.setText("Guardar");
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
