package com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.Supplier;

import com.facilvirtual.fvstoresdesk.model.Supplier;
import java.util.Iterator;
import java.util.List;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class ChangeSupplier extends AbstractFVDialog {
   private Text txtQuery;
   private String action = "";
   private Table table;
   private Supplier supplier = null;

   public ChangeSupplier(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngreseElDescuento = new Label(container, 0);
      lblIngreseElDescuento.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblIngreseElDescuento = new FormData();
      fd_lblIngreseElDescuento.top = new FormAttachment(0, 10);
      fd_lblIngreseElDescuento.left = new FormAttachment(0, 10);
      lblIngreseElDescuento.setLayoutData(fd_lblIngreseElDescuento);
      lblIngreseElDescuento.setText("Buscar por razón social o CUIT");
      this.txtQuery = new Text(container, 2048);
      //this.txtQuery.addKeyListener(new 1(this));
      //TODO:arreglar
      this.txtQuery.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtQuery = new FormData();
      fd_txtQuery.left = new FormAttachment(0, 10);
      fd_txtQuery.right = new FormAttachment(100, -10);
      fd_txtQuery.top = new FormAttachment(lblIngreseElDescuento, 6);
      this.txtQuery.setLayoutData(fd_txtQuery);
      this.table = new Table(container, 67584);
      FormData fd_table = new FormData();
      fd_table.bottom = new FormAttachment(100, -10);
      fd_table.top = new FormAttachment(this.txtQuery, 6);
      fd_table.left = new FormAttachment(0, 10);
      fd_table.right = new FormAttachment(0, 584);
      this.table.setLayoutData(fd_table);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn column = new TableColumn(this.table, 16384);
      column.setText("Nro. proveedor");
      column.setWidth(100);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Razón social");
      column.setWidth(190);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("CUIT");
      column.setWidth(100);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Condición IVA");
      column.setWidth(100);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Teléfono");
      column.setWidth(100);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Domicilio");
      column.setWidth(100);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Localidad");
      column.setWidth(100);
      this.table.setHeaderVisible(true);
      this.searchSuppliers();
      return container;
   }

   private void searchSuppliers() {
      this.table.removeAll();
      List<Supplier> suppliers = this.getProductService().searchSuppliers(this.txtQuery.getText().trim(), true, 1000);
      Iterator var3 = suppliers.iterator();

      while(var3.hasNext()) {
         Supplier supplier = (Supplier)var3.next();
         TableItem item = new TableItem(this.table, 0);
         item.setText(0, String.valueOf(supplier.getId()));
         item.setText(1, String.valueOf(supplier.getCompanyName()));
         item.setText(2, String.valueOf(supplier.getCuit()));
         item.setText(3, String.valueOf(supplier.getVatCondition().getName()));
         item.setText(4, String.valueOf(supplier.getPhone()));
         item.setText(5, String.valueOf(supplier.getFullAddress()));
         item.setText(6, String.valueOf(supplier.getCity()));
      }

   }

   private void process() {
      if (this.validateFields()) {
         this.setAction("OK");
         int idx = this.table.getSelectionIndex();
         Long supplierID = Long.parseLong(this.table.getItem(idx).getText(0).trim());
         Supplier supplier = this.getProductService().getSupplier(supplierID);
         this.setSupplier(supplier);
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un proveedor");
         valid = false;
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Cambiar proveedor");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.process();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(600, 450);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
   }
}
