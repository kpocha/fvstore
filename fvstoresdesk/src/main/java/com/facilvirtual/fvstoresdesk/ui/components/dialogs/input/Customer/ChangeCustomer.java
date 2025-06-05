package com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.Customer;

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

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class ChangeCustomer extends AbstractFVDialog {
   private Text txtQuery;
   private String action = "";
   private Table table;
   private Customer customer = null;

   public ChangeCustomer(Shell parentShell) {
      super(parentShell);
   }
   @Override
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
      lblIngreseElDescuento.setText("Buscar por nombre y apellido, razón social o CUIT");
      this.txtQuery = new Text(container, 2048);
     // this.txtQuery.addKeyListener(new 1(this));
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
      column.setText("Nro. cliente");
      column.setWidth(79);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Apellido y nombre / Razón social");
      column.setWidth(188);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("CUIT");
      column.setWidth(87);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Condición IVA");
      column.setWidth(92);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Teléfono");
      column.setWidth(72);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Domicilio");
      column.setWidth(92);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Localidad");
      column.setWidth(92);
      this.table.setHeaderVisible(true);
      this.searchCustomers();
      return container;
   }

   private void searchCustomers() {
      this.table.removeAll();
      List<Customer> customers = this.getCustomerService().searchCustomers(this.txtQuery.getText().trim(), true, 1000);
      Iterator var3 = customers.iterator();

      while(var3.hasNext()) {
         Customer customer = (Customer)var3.next();
         TableItem item = new TableItem(this.table, 0);
         item.setText(0, String.valueOf(customer.getId()));
         item.setText(1, String.valueOf(customer.getFullName()));
         item.setText(2, String.valueOf(customer.getCuitToDisplay()));
         item.setText(3, String.valueOf(customer.getVatCondition().getName()));
         item.setText(4, String.valueOf(customer.getPhone()));
         item.setText(5, String.valueOf(customer.getFullAddress()));
         item.setText(6, String.valueOf(customer.getCity()));
      }

   }

   private void process() {
      if (this.validateFields()) {
         this.setAction("OK");
         int idx = this.table.getSelectionIndex();
         Long customerID = Long.parseLong(this.table.getItem(idx).getText(0).trim());
         Customer customer = this.getCustomerService().getCustomer(customerID);
         this.setCustomer(customer);
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un cliente");
         valid = false;
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Cambiar cliente");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.process();
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
      return new Point(600, 450);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
}
