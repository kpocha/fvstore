package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
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

public class QtyPrompt extends AbstractFVDialog {
   private Text txtQty;
   private String action = "";
   private Order currentOrder;
   private OrderLine currentOrderLine;
   private double qty = 0.0;

   public QtyPrompt(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngreseElDescuento = new Label(container, 0);
      lblIngreseElDescuento.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblIngreseElDescuento = new FormData();
      fd_lblIngreseElDescuento.left = new FormAttachment(0, 135);
      lblIngreseElDescuento.setLayoutData(fd_lblIngreseElDescuento);
      lblIngreseElDescuento.setText("Ingresa la cantidad");
      this.txtQty = new Text(container, 133120);
      fd_lblIngreseElDescuento.bottom = new FormAttachment(this.txtQty, -15);
      this.txtQty.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_txtPrice = new FormData();
      fd_txtPrice.left = new FormAttachment(100, -271);
      fd_txtPrice.top = new FormAttachment(0, 73);
      fd_txtPrice.right = new FormAttachment(100, -171);
      this.txtQty.setLayoutData(fd_txtPrice);
      //this.txtQty.addTraverseListener(new 1(this));
      //TODO:Arreglar
      return container;
   }

   private void processQty() {
      if (this.validateFields()) {
         this.setAction("OK");
         String dStr = this.txtQty.getText().replaceAll(",", "\\.");
         this.setQty(Double.parseDouble(dStr));
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      try {
         if (!"".equals(this.txtQty.getText())) {
            String dStr = this.txtQty.getText().replaceAll(",", "\\.");
            double d = Double.valueOf(dStr);
            if (d < 0.0) {
               this.alert("La cantidad debe ser mayor a 0");
               valid = false;
            } else if (dStr.contains(",") || dStr.contains(".")) {
               if (this.getCurrentOrderLine().getProduct() == null) {
                  this.alert("Las categorías no pueden fraccionarse");
                  valid = false;
               } else if (!this.getCurrentOrderLine().getProduct().isPerWeight()) {
                  this.alert("El artículo no es fraccionable");
                  valid = false;
               }
            }
         }
      } catch (Exception var5) {
         this.alert("El valor ingresado no es válido");
         valid = false;
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Cambiar cantidad");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processQty();
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
      return new Point(450, 300);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public Order getCurrentOrder() {
      return this.currentOrder;
   }

   public void setCurrentOrder(Order currentOrder) {
      this.currentOrder = currentOrder;
   }

   public OrderLine getCurrentOrderLine() {
      return this.currentOrderLine;
   }

   public void setCurrentOrderLine(OrderLine currentOrderLine) {
      this.currentOrderLine = currentOrderLine;
   }

   public double getQty() {
      return this.qty;
   }

   public void setQty(double qty) {
      this.qty = qty;
   }
}
