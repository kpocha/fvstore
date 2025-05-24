package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Product;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class PrintReceiptPrompt extends AbstractFVDialog {
   private String action = "";
   private Product product;

   public PrintReceiptPrompt(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      Label lblTitle = new Label(container, 0);
      lblTitle.setBounds(55, 54, 336, 25);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      lblTitle.setText("¿Quieres imprimir el comprobante?");
      Button btnNewButton = new Button(container, 0);
     // btnNewButton.addSelectionListener(new 1(this));
      btnNewButton.setBounds(117, 121, 80, 35);
      btnNewButton.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
      btnNewButton.setText("Si");
      Button btnNewButton_1 = new Button(container, 0);
      //btnNewButton_1.addSelectionListener(new 2(this));
      btnNewButton_1.setBounds(248, 121, 80, 35);
      btnNewButton_1.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
      btnNewButton_1.setText("No");
      return container;
      //TODO: arreglar
   }

   private void pressYes() {
      this.setAction("OK");
      this.close();
   }

   private void pressNo() {
      this.setAction("");
      this.close();
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Imprimir comprobante");
   }
   @Override
   protected void buttonPressed(int buttonId) {
   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
   }
   @Override
   protected Point getInitialSize() {
      return new Point(450, 268);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }
}
