package com.facilvirtual.fvstoresdesk.ui.components.dialogs.input;

import com.facilvirtual.fvstoresdesk.model.PriceList;
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
import org.eclipse.wb.swt.SWTResourceManager;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class PriceListPrompt extends AbstractFVDialog {
   private String action = "";
   private Combo comboPriceLists;
   private PriceList priceList = null;

   public PriceListPrompt(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngreseElDescuento = new Label(container, 0);
      lblIngreseElDescuento.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblIngreseElDescuento = new FormData();
      fd_lblIngreseElDescuento.right = new FormAttachment(100, -75);
      fd_lblIngreseElDescuento.left = new FormAttachment(0, 85);
      lblIngreseElDescuento.setLayoutData(fd_lblIngreseElDescuento);
      lblIngreseElDescuento.setText("Selecciona la lista de precios");
      this.comboPriceLists = new Combo(container, 8);
      fd_lblIngreseElDescuento.bottom = new FormAttachment(100, -162);
      FormData fd_comboPriceLists = new FormData();
      fd_comboPriceLists.top = new FormAttachment(lblIngreseElDescuento, 32);
      fd_comboPriceLists.left = new FormAttachment(0, 150);
      fd_comboPriceLists.right = new FormAttachment(100, -119);
      this.comboPriceLists.setLayoutData(fd_comboPriceLists);
      List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
      Iterator var9 = priceLists.iterator();

      while(var9.hasNext()) {
         PriceList priceList = (PriceList)var9.next();
         this.comboPriceLists.add(priceList.getName());
      }

      this.comboPriceLists.select(0);
      Label label = new Label(container, 0);
      label.setText("Lista");
      label.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_label = new FormData();
      fd_label.left = new FormAttachment(0, 109);
      fd_label.top = new FormAttachment(this.comboPriceLists, 4, 128);
      fd_label.right = new FormAttachment(this.comboPriceLists, -15);
      label.setLayoutData(fd_label);
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
         this.setPriceList(priceList);
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

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }
}
