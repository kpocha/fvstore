package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Order;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class Discount extends AbstractFVDialog {
   private Text txtDiscountPercent;
   private String discountPercent = "0.00";
   private String discountAmount = "0.00";
   private String action = "";
   private Text txtDiscountAmount;
   private Order currentOrder;

   public Discount(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngreseElDescuento = new Label(container, 0);
      lblIngreseElDescuento.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblIngreseElDescuento = new FormData();
      fd_lblIngreseElDescuento.top = new FormAttachment(0, 33);
      fd_lblIngreseElDescuento.left = new FormAttachment(0, 116);
      lblIngreseElDescuento.setLayoutData(fd_lblIngreseElDescuento);
      lblIngreseElDescuento.setText("Ingresa el descuento");
      this.txtDiscountPercent = new Text(container, 133120);
      this.txtDiscountPercent.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_txtDiscountPercent = new FormData();
      fd_txtDiscountPercent.right = new FormAttachment(100, -188);
      fd_txtDiscountPercent.width = 65;
      this.txtDiscountPercent.setLayoutData(fd_txtDiscountPercent);
      //this.txtDiscountPercent.addTraverseListener(new 1(this));
      this.txtDiscountAmount = new Text(container, 133120);
      fd_txtDiscountPercent.bottom = new FormAttachment(this.txtDiscountAmount, -14);
      this.txtDiscountAmount.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_txtDiscountAmount = new FormData();
      fd_txtDiscountAmount.top = new FormAttachment(0, 118);
      fd_txtDiscountAmount.left = new FormAttachment(this.txtDiscountPercent, 0, 16384);
      fd_txtDiscountAmount.width = 65;
      this.txtDiscountAmount.setLayoutData(fd_txtDiscountAmount);
      //this.txtDiscountAmount.addTraverseListener(new 2(this));
      //TODO: arrelgar
      Label label = new Label(container, 0);
      label.setText("%");
      label.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_label = new FormData();
      fd_label.top = new FormAttachment(this.txtDiscountPercent, 3, 128);
      fd_label.right = new FormAttachment(this.txtDiscountPercent, -6);
      label.setLayoutData(fd_label);
      Label label_1 = new Label(container, 0);
      label_1.setText("$");
      label_1.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_label_1 = new FormData();
      fd_label_1.top = new FormAttachment(this.txtDiscountAmount, 3, 128);
      fd_label_1.right = new FormAttachment(this.txtDiscountAmount, -6);
      label_1.setLayoutData(fd_label_1);
      if (this.getCurrentOrder().getDiscountPercent() != 0.0) {
         this.txtDiscountPercent.setText(this.getCurrentOrder().getDiscountPercentToDisplay());
      }

      if (this.getCurrentOrder().getDiscountAmount() != 0.0) {
         this.txtDiscountAmount.setText(this.getCurrentOrder().getDiscountAmountToDisplay());
      }

      return container;
   }

   private void processDiscount() {
      if (this.validateFields()) {
         this.setAction("OK");
         this.setDiscountPercent(this.txtDiscountPercent.getText());
         this.setDiscountAmount(this.txtDiscountAmount.getText());
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      String dStr;
      double d;
      try {
         if (!"".equals(this.txtDiscountPercent.getText())) {
            dStr = this.txtDiscountPercent.getText().replaceAll(",", "\\.");
            d = Double.valueOf(dStr);
            if (d < 0.0 || d > 100.0) {
               this.alert("El valor del descuento debe estar entre 0-100%");
               valid = false;
            }
         }
      } catch (Exception var6) {
         this.alert("El valor ingresado (%) no es válido");
         valid = false;
      }

      try {
         if (!"".equals(this.txtDiscountAmount.getText())) {
            dStr = this.txtDiscountAmount.getText().replaceAll(",", "\\.");
            d = Double.valueOf(dStr);
            if (d < 0.0) {
               this.alert("El valor del descuento ($) debe ser mayor a 0");
               valid = false;
            }
         }
      } catch (Exception var5) {
         this.alert("El valor ingresado ($) no es válido");
         valid = false;
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Descuento");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDiscount();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(450, 300);
   }

   public String getDiscountPercent() {
      return this.discountPercent;
   }

   public void setDiscountPercent(String discountPercent) {
      this.discountPercent = discountPercent;
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public String getDiscountAmount() {
      return this.discountAmount;
   }

   public void setDiscountAmount(String discountAmount) {
      this.discountAmount = discountAmount;
   }

   public Order getCurrentOrder() {
      return this.currentOrder;
   }

   public void setCurrentOrder(Order currentOrder) {
      this.currentOrder = currentOrder;
   }
}
