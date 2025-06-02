package com.facilvirtual.fvstoresdesk.ui.tool;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;
import java.util.Iterator;
import java.util.List;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class CreateLabelsByCode extends AbstractFVDialog {
   private String action = "";
   private Combo comboPriceLists;
   private PriceList priceList = null;
   private List<Product> products;
   private Text txtBarCodes;

   public CreateLabelsByCode(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngresaLaFecha = new Label(container, 0);
      lblIngresaLaFecha.setAlignment(16777216);
      lblIngresaLaFecha.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
      FormData fd_lblIngresaLaFecha = new FormData();
      fd_lblIngresaLaFecha.top = new FormAttachment(0, 31);
      fd_lblIngresaLaFecha.right = new FormAttachment(100, -50);
      fd_lblIngresaLaFecha.left = new FormAttachment(0, 51);
      lblIngresaLaFecha.setLayoutData(fd_lblIngresaLaFecha);
      lblIngresaLaFecha.setText("Etiquetas de precios por códigos");
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
      Label label = new Label(container, 0);
      label.setText("Lista");
      label.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_label = new FormData();
      fd_label.bottom = new FormAttachment(lblCdigosDeBarra, -20);
      fd_label.left = new FormAttachment(this.txtBarCodes, 0, 16384);
      label.setLayoutData(fd_label);
      this.comboPriceLists = new Combo(container, 8);
      FormData fd_combo = new FormData();
      fd_combo.top = new FormAttachment(label, -5, 128);
      fd_combo.right = new FormAttachment(label, 197, 131072);
      fd_combo.left = new FormAttachment(label, 22);
      this.comboPriceLists.setLayoutData(fd_combo);
      List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
      Iterator var14 = priceLists.iterator();

      while(var14.hasNext()) {
         PriceList priceList = (PriceList)var14.next();
         this.comboPriceLists.add(priceList.getName());
      }

      this.comboPriceLists.select(0);
      return container;
   }

   private void createLabels() {
      if (this.validateFields()) {
         this.setAction("OK");
         PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
         this.setPriceList(priceList);
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
      this.initTitle(newShell, "Generar etiquetas");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.createLabels();
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
      return new Point(532, 353);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public List<Product> getProducts() {
      return this.products;
   }

   public void setProducts(List<Product> products) {
      this.products = products;
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }
}
