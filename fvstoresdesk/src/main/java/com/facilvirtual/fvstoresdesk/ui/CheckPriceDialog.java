package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
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
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class CheckPriceDialog extends AbstractFVDialog {
   private Text txtBarCode;
   private String action = "";
   private Product product;
   private PriceList priceList = null;
   private Combo comboPriceLists;

   public CheckPriceDialog(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 0);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.right = new FormAttachment(100, -167);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Ver precio");
      this.txtBarCode = new Text(container, 133120);
      this.txtBarCode.setFont(SWTResourceManager.getFont("Segoe UI", 9, 0));
      FormData fd_txtBarCode = new FormData();
      fd_txtBarCode.left = new FormAttachment(0, 168);
      fd_txtBarCode.right = new FormAttachment(100, -90);
      fd_txtBarCode.width = 65;
      this.txtBarCode.setLayoutData(fd_txtBarCode);
      Label label = new Label(container, 0);
      label.setText("Lista");
      label.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_label = new FormData();
      fd_label.left = new FormAttachment(0, 136);
      label.setLayoutData(fd_label);
      this.comboPriceLists = new Combo(container, 8);
      fd_lblTitle.bottom = new FormAttachment(this.comboPriceLists, -27);
      fd_label.top = new FormAttachment(this.comboPriceLists, 2, 128);
      fd_label.right = new FormAttachment(this.comboPriceLists, -6);
      fd_txtBarCode.top = new FormAttachment(this.comboPriceLists, 20);
      FormData fd_comboPriceLists = new FormData();
      fd_comboPriceLists.bottom = new FormAttachment(100, -115);
      fd_comboPriceLists.left = new FormAttachment(0, 168);
      fd_comboPriceLists.right = new FormAttachment(100, -90);
      this.comboPriceLists.setLayoutData(fd_comboPriceLists);
      this.comboPriceLists.select(0);
      Label lblCdigoArtculo = new Label(container, 0);
      lblCdigoArtculo.setText("Código Artículo");
      lblCdigoArtculo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblCdigoArtculo = new FormData();
      fd_lblCdigoArtculo.left = new FormAttachment(0, 76);
      fd_lblCdigoArtculo.top = new FormAttachment(this.txtBarCode, 2, 128);
      fd_lblCdigoArtculo.right = new FormAttachment(this.txtBarCode, -6);
      lblCdigoArtculo.setLayoutData(fd_lblCdigoArtculo);
      //this.txtBarCode.addTraverseListener(new 1(this));
      List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
      Iterator var14 = priceLists.iterator();

      while(var14.hasNext()) {
         PriceList priceList = (PriceList)var14.next();
         this.comboPriceLists.add(priceList.getName());
      }

      this.comboPriceLists.select(0);
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         Product product = this.getProductService().getProductByBarCode(this.txtBarCode.getText().trim());
         this.setProduct(product);
         PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
         this.setPriceList(priceList);
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

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Ver precio");
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

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }
}
