package com.facilvirtual.fvstoresdesk.ui.components.forms;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;

public class EditPriceList extends AbstractFVDialog {
   private String action = "";
   private Text txtListName;
   private PriceList priceList;

   public EditPriceList(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 0);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.right = new FormAttachment(0, 310);
      fd_lblTitle.top = new FormAttachment(0, 32);
      fd_lblTitle.left = new FormAttachment(0, 70);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Modificar lista de precios");
      Label lblNombreDelArchivo = new Label(container, 0);
      lblNombreDelArchivo.setText("Nombre de la lista:");
      lblNombreDelArchivo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblNombreDelArchivo = new FormData();
      fd_lblNombreDelArchivo.top = new FormAttachment(lblTitle, 38);
      fd_lblNombreDelArchivo.left = new FormAttachment(0, 54);
      fd_lblNombreDelArchivo.right = new FormAttachment(100, -200);
      lblNombreDelArchivo.setLayoutData(fd_lblNombreDelArchivo);
      this.txtListName = new Text(container, 2048);
      this.txtListName.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtListName = new FormData();
      fd_txtListName.top = new FormAttachment(lblNombreDelArchivo, 6);
      fd_txtListName.right = new FormAttachment(100, -67);
      fd_txtListName.left = new FormAttachment(0, 54);
      this.txtListName.setLayoutData(fd_txtListName);
      this.txtListName.setText(this.getPriceList().getName());
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         PriceList priceList = this.getPriceList();
         priceList.setName(this.txtListName.getText().trim());
         this.setPriceList(priceList);
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtListName.getText().trim())) {
         valid = false;
         this.alert("Ingresa el nombre de la lista");
      }

      if (valid) {
         String currentListName = this.getPriceList().getName().toLowerCase();
         PriceList priceList = this.getAppConfigService().getPriceListByName(this.txtListName.getText().trim());
         if (priceList != null && !priceList.getName().toLowerCase().equals(currentListName)) {
            valid = false;
            this.alert("Ya existe una lista de precios con ese nombre");
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Modificar lista de precios");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 0, "Aceptar", false);
      button.setText("Guardar");
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(402, 303);
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
