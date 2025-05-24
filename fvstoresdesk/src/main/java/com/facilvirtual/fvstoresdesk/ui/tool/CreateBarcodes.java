package com.facilvirtual.fvstoresdesk.ui.tool;

import java.util.List;

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

import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;

public class CreateBarcodes extends AbstractFVDialog {
   private String action = "";
   private List<Product> products;
   private Text txtBarCodes;

   public CreateBarcodes(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngresaLaFecha = new Label(container, 0);
      lblIngresaLaFecha.setAlignment(16777216);
      lblIngresaLaFecha.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
      FormData fd_lblIngresaLaFecha = new FormData();
      fd_lblIngresaLaFecha.right = new FormAttachment(0, 473);
      fd_lblIngresaLaFecha.top = new FormAttachment(0, 36);
      fd_lblIngresaLaFecha.left = new FormAttachment(0, 48);
      lblIngresaLaFecha.setLayoutData(fd_lblIngresaLaFecha);
      lblIngresaLaFecha.setText("Ingresa los códigos de los artículos");
      this.txtBarCodes = new Text(container, 2626);
      FormData fd_txtBarCodes = new FormData();
      fd_txtBarCodes.right = new FormAttachment(100, -85);
      fd_txtBarCodes.bottom = new FormAttachment(100, -37);
      fd_txtBarCodes.left = new FormAttachment(0, 89);
      this.txtBarCodes.setLayoutData(fd_txtBarCodes);
      Label lblCdigosDeBarra = new Label(container, 0);
      fd_txtBarCodes.top = new FormAttachment(lblCdigosDeBarra, 6);
      FormData fd_lblCdigosDeBarra = new FormData();
      fd_lblCdigosDeBarra.bottom = new FormAttachment(100, -125);
      fd_lblCdigosDeBarra.left = new FormAttachment(0, 89);
      lblCdigosDeBarra.setLayoutData(fd_lblCdigosDeBarra);
      lblCdigosDeBarra.setText("Ingresar un código por fila");
      return container;
   }

   private void createBarcodes() {
      if (this.validateFields()) {
         this.setAction("OK");
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      try {
         String[] barCodesArray = this.txtBarCodes.getText().split("\\n");
         this.setProducts(this.getProductService().getProductsByBarCodes(barCodesArray));
      } catch (Exception var3) {
      }

      if (this.products == null || this.products.size() == 0) {
         this.alert("No se encontraron artículos");
         valid = false;
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Generar códigos de barra");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.createBarcodes();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(532, 306);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public List<Product> getProducts() {
      return this.products;
   }

   public void setProducts(List<Product> products) {
      this.products = products;
   }
}
