package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class EditProductPhoto extends AbstractFVDialog {
   private String action = "";
   private ProductPrice productPrice;

   public EditProductPhoto(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      GridLayout gl_container = new GridLayout(1, false);
      gl_container.marginTop = 17;
      gl_container.marginLeft = 50;
      gl_container.verticalSpacing = 10;
      container.setLayout(gl_container);
      Label lblPrecioCosto = new Label(container, 0);
      lblPrecioCosto.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPrecioCosto.setText("Archivo:");
      return container;
   }

   private void processProductPrice() {
      if (this.validateFields()) {
         this.setAction("OK");
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, this.getTitle());
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processProductPrice();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(418, 270);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public ProductPrice getProductPrice() {
      return this.productPrice;
   }

   public void setProductPrice(ProductPrice productPrice) {
      this.productPrice = productPrice;
   }
}
