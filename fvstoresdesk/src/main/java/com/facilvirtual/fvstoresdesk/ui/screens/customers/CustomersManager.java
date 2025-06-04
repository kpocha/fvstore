package com.facilvirtual.fvstoresdesk.ui.screens.customers;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVApplicationWindow;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.confirmation.FVConfirmDialog;
import com.facilvirtual.fvstoresdesk.ui.screens.cash.CashRegister;

public class CustomersManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("CustomersManager");
   private static CustomersManager INSTANCE = null;
   private Table table;
   private Text txtQuery;
   private Button btnCheckButton;
   private Composite headerContainer;
   private CashRegister cashRegister;

   public CustomersManager() {
      super((Shell)null);
      this.setBlockOnOpen(true);
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new CustomersManager();
      }

   }

   public static CustomersManager getInstance() {
      createInstance();
      return INSTANCE;
   }
   @Override
   protected Control createContents(Composite parent) {
      Composite container = new Composite(parent, 0);
      container.setLayout(new FormLayout());
      this.createHeaderContents(container);
      this.createMainContents(container);
      this.searchCustomers();
      return container;
   }

   private void createHeaderContents(Composite container) {
      this.headerContainer = new Composite(container, 0);
      this.headerContainer.setLayout(new GridLayout());
      FormData fData = new FormData();
      fData.top = new FormAttachment(0);
      fData.left = new FormAttachment(0);
      fData.right = new FormAttachment(100);
      fData.bottom = new FormAttachment(0, 50);
      this.headerContainer.setLayoutData(fData);
      Composite menuContainer = new Composite(this.headerContainer, 0);
      menuContainer.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      menuContainer.setLayout(new GridLayout(8, false));
      Button btnNewButton_2 = new Button(menuContainer, 0);
      btnNewButton_2.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            addNewCustomer();
         }
      });
      btnNewButton_2.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_add.gif"));
      btnNewButton_2.setText("Nuevo cliente");
      Button btnNewButton_1 = new Button(menuContainer, 0);
      btnNewButton_1.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            editCustomer();
         }
      });
      btnNewButton_1.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_edit.gif"));
      btnNewButton_1.setText("Modificar");
      Button btnNewButton = new Button(menuContainer, 0);
      btnNewButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            deleteCustomer();
         }
      });
      btnNewButton.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_delete.gif"));
      btnNewButton.setText("Eliminar");
      Label lblNewLabel = new Label(menuContainer, 0);
      GridData gd_lblNewLabel = new GridData(131072, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 58;
      lblNewLabel.setLayoutData(gd_lblNewLabel);
      GridData gd1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd1.widthHint = 125;
      this.txtQuery = new Text(menuContainer, 2048);
      this.txtQuery.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            deleteCustomer();
         }
      });
      this.txtQuery.setLayoutData(gd1);
      this.txtQuery.setFont(SWTResourceManager.getFont("Tahoma", 12, 0));
      this.txtQuery.setText("");
      this.txtQuery.addTraverseListener(e -> searchCustomers());
      this.txtQuery.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
         searchCustomers();
      }
     });
      Button btnBuscar = new Button(menuContainer, 0);
      btnBuscar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            searchCustomers();
         }
      });
      btnBuscar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      btnBuscar.setText("Buscar");
      Label lblNewLabel_1 = new Label(menuContainer, 0);
      GridData gd_lblNewLabel_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel_1.widthHint = 5;
      lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
      this.btnCheckButton = new Button(menuContainer, 32);
      this.btnCheckButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            searchCustomers();
         }
      });
      this.btnCheckButton.setBounds(664, 41, 118, 16);
      this.btnCheckButton.setText("Ocultar clientes eliminados");
   }

   private void createMainContents(Composite container) {
      Composite mainContentContainer = new Composite(container, 0);
      mainContentContainer.setLayout(new FillLayout());
      FormData fData = new FormData();
      fData.top = new FormAttachment(this.headerContainer);
      fData.left = new FormAttachment(0);
      fData.right = new FormAttachment(100);
      fData.bottom = new FormAttachment(100);
      mainContentContainer.setLayoutData(fData);
      this.table = new Table(mainContentContainer, 67584);
      this.table.setBounds(10, 65, 772, 339);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnTicket = new TableColumn(this.table, 0);
      tblclmnTicket.setWidth(95);
      tblclmnTicket.setText("Nro. de cliente");
      TableColumn tblclmnSubtotal = new TableColumn(this.table, 0);
      tblclmnSubtotal.setWidth(380);
      tblclmnSubtotal.setText("Apellido y nombre / Razón social");
      TableColumn tblclmnAccountType = new TableColumn(this.table, 0);
      tblclmnAccountType.setWidth(109);
      tblclmnAccountType.setText("Tipo de cuenta");
      TableColumn tblclmnFecha = new TableColumn(this.table, 0);
      tblclmnFecha.setWidth(134);
      tblclmnFecha.setText("Fecha de registración");
      TableColumn tblclmnTotal = new TableColumn(this.table, 0);
      tblclmnTotal.setWidth(75);
      tblclmnTotal.setText("Estado");
   }

   private void addNewCustomer() {
      AddNewCustomer addNewCustomerDialog = new AddNewCustomer(this.getShell());
      addNewCustomerDialog.open();
      if ("OK".equalsIgnoreCase(addNewCustomerDialog.getAction())) {
         Customer newCustomer = addNewCustomerDialog.getCustomer();

         try {
            this.getCustomerService().saveCustomer(newCustomer);
            this.txtQuery.setText("");
            this.searchCustomers();
         } catch (Exception var4) {
            logger.error("Error guardando cliente" + newCustomer);
         }
      }

   }

   private void deleteCustomer() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un cliente");
      } else if (FVConfirmDialog.openQuestion(this.getShell(), "Eliminar cliente", "¿Quieres eliminar el cliente?")) {
         try {
            Long customerId = Long.parseLong(this.table.getItem(idx).getText(0).trim());
            this.getCustomerService().deleteCustomer(customerId);
            this.searchCustomers();
         } catch (Exception var3) {
         }
      }

   }

   private void editCustomer() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un cliente");
      } else {
         try {
            Long customerID = Long.parseLong(this.table.getItem(idx).getText(0).trim());
            Customer customer = this.getCustomerService().getCustomer(customerID);
            EditCustomer dialog = new EditCustomer(this.getShell());
            dialog.setCustomer(customer);
            dialog.open();
            if ("OK".equalsIgnoreCase(dialog.getAction())) {
               Customer editedCustomer = dialog.getCustomer();

               try {
                  this.getCustomerService().saveCustomer(editedCustomer);
                  this.searchCustomers();
                  this.getCashRegister().refreshCustomer();
               } catch (Exception var7) {
                  var7.printStackTrace();
               }
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

   }

   private void searchCustomers() {
      this.table.removeAll();
      boolean hideDeletedCustomers = this.btnCheckButton.getSelection();
      List<Customer> customers = this.getCustomerService().searchCustomers(this.txtQuery.getText().trim(), hideDeletedCustomers, 1000);
      this.populateTable(customers);
   }

   private void populateTable(List<Customer> customers) {
      Iterator var3 = customers.iterator();

      while(var3.hasNext()) {
         Customer c = (Customer)var3.next();
         if (!c.getFullName().equalsIgnoreCase("- Cliente Ocasional -")) {
            TableItem item = new TableItem(this.table, 0);
            item.setText(0, String.valueOf(c.getId()));
            item.setText(1, String.valueOf(c.getFullName()));
            item.setText(2, c.getAccountTypeToDisplay());
            item.setText(3, c.getCreationDateToDisplay());
            item.setText(4, c.getStatusToDisplay());
         }
      }

   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Clientes");
   }
   @Override
   protected Point getInitialSize() {
      return new Point(828, 600);
   }

   public CashRegister getCashRegister() {
      return this.cashRegister;
   }

   public void setCashRegister(CashRegister cashRegister) {
      this.cashRegister = cashRegister;
   }
}
