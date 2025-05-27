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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class CashCategoryPrompt extends AbstractFVDialog {
   private Text txtCategoryNumber;
   private String action = "";
   private Order currentOrder;
   private OrderLine currentOrderLine;
   private int categoryNumber = -1;

   public CashCategoryPrompt(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngreseElDescuento = new Label(container, 0);
      lblIngreseElDescuento.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblIngreseElDescuento = new FormData();
      fd_lblIngreseElDescuento.right = new FormAttachment(0, 443);
      fd_lblIngreseElDescuento.left = new FormAttachment(0, 51);
      lblIngreseElDescuento.setLayoutData(fd_lblIngreseElDescuento);
      lblIngreseElDescuento.setText("Ingresa el número de departamento");
      this.txtCategoryNumber = new Text(container, 133120);
      this.txtCategoryNumber.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
               processDialog();
            }
         }
      });
      fd_lblIngreseElDescuento.bottom = new FormAttachment(100, -165);
      this.txtCategoryNumber.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_txtCategoryNumber = new FormData();
      fd_txtCategoryNumber.width = 65;
      this.txtCategoryNumber.setLayoutData(fd_txtCategoryNumber);
      Label lblNro = new Label(container, 0);
      fd_txtCategoryNumber.top = new FormAttachment(lblNro, -3, 128);
      fd_txtCategoryNumber.left = new FormAttachment(lblNro, 6);
      lblNro.setText("Nro.");
      lblNro.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblNro = new FormData();
      fd_lblNro.top = new FormAttachment(lblIngreseElDescuento, 18);
      fd_lblNro.right = new FormAttachment(100, -250);
      lblNro.setLayoutData(fd_lblNro);
      return container;
   }

   private void processDialog() {
      if (!"".equals(this.txtCategoryNumber.getText()) && this.validateFields()) {
         this.setAction("OK");
         this.setCategoryNumber(Integer.valueOf(this.txtCategoryNumber.getText()));
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      try {
         if (!"".equals(this.txtCategoryNumber.getText().trim())) {
            int number = Integer.valueOf(this.txtCategoryNumber.getText());
            if (number < 1 || number > 8) {
               this.alert("El valor ingresado no es válido");
               valid = false;
            }
         } else {
            this.alert("Ingresa el Nro. de departamento");
            valid = false;
         }
      } catch (Exception var3) {
         this.alert("El valor ingresado no es válido");
         valid = false;
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, this.getTitle());
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
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

   public String getAction() {
      return this.action;
   }

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

   public int getCategoryNumber() {
      return this.categoryNumber;
   }

   public void setCategoryNumber(int categoryNumber) {
      this.categoryNumber = categoryNumber;
   }
}
