package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class QuickReposition extends AbstractFVDialog {
   private String action = "";
   private Label lblVariacinPrecioDe;
   private Text txtStockVariation;
   private static final Logger logger = LoggerFactory.getLogger("PricesUpdater");
   private Label lblCdigoArtculo;
   private Text txtBarCode;

   public QuickReposition(Shell parentShell) {
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
      fd_lblTitle.top = new FormAttachment(0, 30);
      fd_lblTitle.left = new FormAttachment(0, 64);
      fd_lblTitle.right = new FormAttachment(100, -85);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Reposición rápida de stock");
      this.lblVariacinPrecioDe = new Label(container, 0);
      this.lblVariacinPrecioDe.setAlignment(131072);
      this.lblVariacinPrecioDe.setText("Variación Stock");
      this.lblVariacinPrecioDe.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblVariacinPrecioDe = new FormData();
      fd_lblVariacinPrecioDe.left = new FormAttachment(0, 111);
      this.lblVariacinPrecioDe.setLayoutData(fd_lblVariacinPrecioDe);
      this.txtStockVariation = new Text(container, 2048);
      fd_lblVariacinPrecioDe.right = new FormAttachment(this.txtStockVariation, -15);
      fd_lblVariacinPrecioDe.top = new FormAttachment(this.txtStockVariation, 2, 128);
      FormData fd_txtStockVariation = new FormData();
      this.txtStockVariation.setLayoutData(fd_txtStockVariation);
      this.lblCdigoArtculo = new Label(container, 0);
      this.lblCdigoArtculo.setAlignment(131072);
      this.lblCdigoArtculo.setText("Código Artículo");
      this.lblCdigoArtculo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblCdigoArtculo = new FormData();
      fd_lblCdigoArtculo.left = new FormAttachment(0, 111);
      this.lblCdigoArtculo.setLayoutData(fd_lblCdigoArtculo);
      this.txtBarCode = new Text(container, 2048);
      fd_lblCdigoArtculo.right = new FormAttachment(this.txtBarCode, -15);
      fd_lblCdigoArtculo.top = new FormAttachment(this.txtBarCode, 2, 128);
      fd_txtStockVariation.top = new FormAttachment(this.txtBarCode, 32);
      fd_txtStockVariation.left = new FormAttachment(this.txtBarCode, 0, 16384);
      FormData fd_txtBarCode = new FormData();
      fd_txtBarCode.top = new FormAttachment(lblTitle, 62);
      fd_txtBarCode.right = new FormAttachment(100, -131);
      fd_txtBarCode.left = new FormAttachment(0, 222);
      this.txtBarCode.setLayoutData(fd_txtBarCode);
      this.txtBarCode.setFocus();
      return container;
   }

   private void updatePrices() {
      try {
         Product product = this.getProductService().getProductByBarCode(this.txtBarCode.getText().trim());
         if (product != null) {
            product.setStock(product.getStock() + this.getDoubleValueFromText(this.txtStockVariation));
            this.getProductService().saveProduct(product);
         }
      } catch (Exception var2) {
         logger.error("Error en Reposición rápida de stock");
         logger.error(var2.getMessage());
       //  //logger.error(var2);
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
      if ("".equals(this.txtBarCode.getText().trim())) {
         valid = false;
         this.alert("Ingresa el código del artículo");
      }

      if (valid) {
         Product product = this.getProductService().getProductByBarCode(this.txtBarCode.getText().trim());
         if (product == null) {
            valid = false;
            this.alert("No se encontró el artículo");
         }
      }

      if (valid) {
         try {
            double var5 = Double.valueOf(this.txtStockVariation.getText().trim().replaceAll(",", "."));
         } catch (Exception var4) {
            valid = false;
            this.alert("El valor ingresado en Variación Stock no es válido");
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Reposición rápida de stock");
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
