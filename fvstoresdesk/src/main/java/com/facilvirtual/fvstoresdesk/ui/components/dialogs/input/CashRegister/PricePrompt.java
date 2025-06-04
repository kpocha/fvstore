package com.facilvirtual.fvstoresdesk.ui.components.dialogs.input;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import com.facilvirtual.fvstoresdesk.model.Vat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
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
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class PricePrompt extends AbstractFVDialog {
   private Text txtPrice;
   private String action = "";
   private Order currentOrder;
   private OrderLine currentOrderLine;
   private double price = 0.0;
   private boolean updateProduct = false;
   private Button btnOnlyForSale;
   private Vat vat;
   private Combo comboVat;

   public PricePrompt(Shell parentShell) {
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
      lblIngreseElDescuento.setText("Ingresa el precio");
      this.txtPrice = new Text(container, 133120);
      fd_lblIngreseElDescuento.bottom = new FormAttachment(100, -164);
      this.txtPrice.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_txtPrice = new FormData();
      fd_txtPrice.top = new FormAttachment(lblIngreseElDescuento, 17);
      this.txtPrice.setLayoutData(fd_txtPrice);

      this.txtPrice.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent e) {
            if (e.detail == SWT.TRAVERSE_RETURN) {
               processPrice();
            }
         }
      });
      Label label = new Label(container, 0);
      fd_txtPrice.right = new FormAttachment(label, 96, 131072);
      fd_txtPrice.left = new FormAttachment(label, 6);
      label.setText("$");
      label.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_label = new FormData();
      fd_label.top = new FormAttachment(lblIngreseElDescuento, 20);
      fd_label.right = new FormAttachment(100, -277);
      label.setLayoutData(fd_label);
      this.btnOnlyForSale = new Button(container, 32);
      FormData fd_btnOnlyForSale = new FormData();
      fd_btnOnlyForSale.right = new FormAttachment(100, -145);
      fd_btnOnlyForSale.bottom = new FormAttachment(100, -29);
      this.btnOnlyForSale.setLayoutData(fd_btnOnlyForSale);
      this.btnOnlyForSale.setText("Sólo para esta venta");
      if (this.getCurrentOrderLine().getProduct() != null) {
         Label lblIVA = new Label(container, 0);
         FormData fd_lblIVA = new FormData();
         fd_lblIVA.right = new FormAttachment(label, 0, 131072);
         lblIVA.setLayoutData(fd_lblIVA);
         lblIVA.setText("IVA:");
         this.comboVat = new Combo(container, 8);
         fd_lblIVA.bottom = new FormAttachment(this.comboVat, -2, 1024);
         FormData fd_comboVat = new FormData();
         fd_comboVat.top = new FormAttachment(this.txtPrice, 18);
         fd_comboVat.left = new FormAttachment(lblIVA, 6);
         this.comboVat.setLayoutData(fd_comboVat);
         List<Vat> vats = this.getOrderService().getAllVats();
         int selectedIdx = 0;

         for(Iterator var16 = vats.iterator(); var16.hasNext(); ++selectedIdx) {
            Vat v = (Vat)var16.next();
            this.comboVat.add(v.getName());
            if (this.currentOrderLine.getVatValue() == v.getValue()) {
               this.comboVat.select(selectedIdx);
            }
         }
      }

      if (this.getCurrentOrderLine().getProduct() == null) {
         this.btnOnlyForSale.setVisible(false);
      }

      return container;
   }

   private void processPrice() {
      if (this.validateFields()) {
         this.setAction("OK");
         String dStr = this.txtPrice.getText().replaceAll(",", "\\.");
         if (!"".equals(this.txtPrice.getText().trim())) {
            this.setPrice(Double.parseDouble(dStr));
         } else {
            this.setPrice(this.currentOrderLine.getPrice());
         }

         if (this.currentOrderLine.getProduct() != null) {
            Vat vat = this.getOrderService().getVatByName(this.comboVat.getText());
            this.setVat(vat);
         }

         this.setUpdateProduct(!this.btnOnlyForSale.getSelection());
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      try {
         if (!"".equals(this.txtPrice.getText().trim())) {
            String dStr = this.txtPrice.getText().replaceAll(",", "\\.");
            double d = Double.valueOf(dStr);
            if (d < 0.0) {
               this.alert("El precio debe ser mayor a 0");
               valid = false;
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
      this.initTitle(newShell, this.getTitle());
   }
 @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processPrice();
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

   public double getPrice() {
      return this.price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public boolean isUpdateProduct() {
      return this.updateProduct;
   }

   public void setUpdateProduct(boolean updateProduct) {
      this.updateProduct = updateProduct;
   }

   public Vat getVat() {
      return this.vat;
   }

   public void setVat(Vat vat) {
      this.vat = vat;
   }
}
