package com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.util.FVMathUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
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
public class Surcharge extends AbstractFVDialog {
   private String action = "";
   private Text txtSurchargePercent;
   private Text txtSurchargeAmount;
   private Order currentOrder;

   public Surcharge(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngreseElRecargo = new Label(container, 0);
      lblIngreseElRecargo.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblIngreseElRecargo = new FormData();
      fd_lblIngreseElRecargo.right = new FormAttachment(0, 304);
      fd_lblIngreseElRecargo.top = new FormAttachment(0, 33);
      fd_lblIngreseElRecargo.left = new FormAttachment(0, 124);
      lblIngreseElRecargo.setLayoutData(fd_lblIngreseElRecargo);
      lblIngreseElRecargo.setText("Ingresa el recargo");
      this.txtSurchargePercent = new Text(container, 133120);
      this.txtSurchargePercent.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_txtDiscountPercent = new FormData();
      fd_txtDiscountPercent.right = new FormAttachment(100, -188);
      fd_txtDiscountPercent.width = 65;
      this.txtSurchargePercent.setLayoutData(fd_txtDiscountPercent);
      this.txtSurchargePercent.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent e) {
            if (e.detail == SWT.TRAVERSE_RETURN) { // 4 == SWT.TRAVERSE_RETURN
               initSurcharge();
            }
         }
      });
      this.txtSurchargeAmount = new Text(container, 133120);
      fd_txtDiscountPercent.bottom = new FormAttachment(this.txtSurchargeAmount, -14);
      this.txtSurchargeAmount.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_txtDiscountAmount = new FormData();
      fd_txtDiscountAmount.top = new FormAttachment(0, 118);
      fd_txtDiscountAmount.left = new FormAttachment(this.txtSurchargePercent, 0, 16384);
      fd_txtDiscountAmount.width = 65;
      this.txtSurchargeAmount.setLayoutData(fd_txtDiscountAmount);
      this.txtSurchargeAmount.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent e) {
            if (e.detail == SWT.TRAVERSE_RETURN) {
               initSurcharge();
            }
         }
      });
      Label label = new Label(container, 0);
      label.setText("%");
      label.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_label = new FormData();
      fd_label.top = new FormAttachment(this.txtSurchargePercent, 3, 128);
      fd_label.right = new FormAttachment(this.txtSurchargePercent, -6);
      label.setLayoutData(fd_label);
      Label label_1 = new Label(container, 0);
      label_1.setText("$");
      label_1.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_label_1 = new FormData();
      fd_label_1.top = new FormAttachment(this.txtSurchargeAmount, 3, 128);
      fd_label_1.right = new FormAttachment(this.txtSurchargeAmount, -6);
      label_1.setLayoutData(fd_label_1);
      this.initSurcharge();
      return container;
   }

   private void initSurcharge() {
      if (this.getCurrentOrder().getSurchargePercent() != 0.0) {
         this.txtSurchargePercent.setText(this.getCurrentOrder().getSurchargePercentToDisplay());
      }

      if (this.getCurrentOrder().getSurchargeAmount() != 0.0) {
         this.txtSurchargeAmount.setText(this.getCurrentOrder().getSurchargeAmountToDisplay());
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         Double discountPercent = this.getDoubleValueFromText(this.txtSurchargePercent);
         if (discountPercent != null) {
            this.getCurrentOrder().updateSurchargePercent(FVMathUtils.roundValue(discountPercent));
         } else {
            this.getCurrentOrder().updateSurchargePercent(0.0);
         }

         Double discountAmount = this.getDoubleValueFromText(this.txtSurchargeAmount);
         if (discountAmount != null) {
            this.getCurrentOrder().updateSurchargeAmount(FVMathUtils.roundValue(discountAmount));
         } else {
            this.getCurrentOrder().updateSurchargeAmount(0.0);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      String dStr;
      double d;
      try {
         if (!"".equals(this.txtSurchargePercent.getText().trim())) {
            dStr = this.txtSurchargePercent.getText().trim().replaceAll(",", ".");
            d = Double.valueOf(dStr);
            if (d < 0.0) {
               this.alert("El valor del recargo debe ser mayor a 0");
               valid = false;
            }
         }
      } catch (Exception var6) {
         this.alert("El valor ingresado (%) no es válido");
         valid = false;
      }

      try {
         if (!"".equals(this.txtSurchargeAmount.getText().trim())) {
            dStr = this.txtSurchargeAmount.getText().trim().replaceAll(",", ".");
            d = Double.valueOf(dStr);
            if (d < 0.0) {
               this.alert("El valor del recargo ($) debe ser mayor a 0");
               valid = false;
            }
         }
      } catch (Exception var5) {
         this.alert("El valor ingresado ($) no es válido");
         valid = false;
      }

      return valid;
   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Recargo");
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
}
