package com.facilvirtual.fvstoresdesk.ui.screens.customers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
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
import com.facilvirtual.fvstoresdesk.model.CustomerOnAccountOperation;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVApplicationWindow;

public class CustomerOnAccountOpsManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("CustomerOnAccountOpsManager");
   private static CustomerOnAccountOpsManager INSTANCE = null;
   private Composite headerContainer;
   private Composite bodyContainer;
   private Table table;
   private List<CustomerOnAccountOperation> onAccountOperations = new ArrayList();
   private Customer customer;
   private Text txtCustomer;
   private Employee employee;
   private CashRegister cashRegister;

   public CustomerOnAccountOpsManager() {
      super((Shell)null);
      this.setBlockOnOpen(true);
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new CustomerOnAccountOpsManager();
      }

   }

   public static CustomerOnAccountOpsManager getInstance() {
      createInstance();
      return INSTANCE;
   }
   @Override
   protected Control createContents(Composite parent) {
      Composite layoutContainer = new Composite(parent, 0);
      layoutContainer.setLayout(new GridLayout(1, false));
      this.headerContainer = new Composite(layoutContainer, 0);
      this.headerContainer.setLayout(new GridLayout(7, false));
      this.bodyContainer = new Composite(layoutContainer, 0);
      this.bodyContainer.setLayout(new FillLayout(256));
      this.bodyContainer.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.createHeaderContents();
      this.createBodyContents();
      this.searchOnAccountOperations();
      return layoutContainer;
   }

   private void searchOnAccountOperations() {
      this.table.removeAll();
      if (this.getCustomer() != null) {
         this.initOnAccountOperations();
         this.initTable();
         this.txtCustomer.setText(this.customer.getFullName());
      } else {
         this.txtCustomer.setText("");
      }

   }

   private void initOnAccountOperations() {
      this.setOnAccountOperations(this.getCustomerService().getActiveCustomerOnAccountOps(this.getCustomer()));
   }

   private void initTable() {
      
      double balanceAmount = 0.0;

      int colIdx;
      for(Iterator var5 = this.getOnAccountOperations().iterator(); var5.hasNext(); ++colIdx) {
         CustomerOnAccountOperation onAccountOperation = (CustomerOnAccountOperation)var5.next();
         colIdx = 0;
         TableItem item = new TableItem(this.table, 0);
         item.setText(colIdx, String.valueOf(onAccountOperation.getOperationDateToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(onAccountOperation.getCashNumber()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(onAccountOperation.getAuthor().getUsername()));
         ++colIdx;
         if (onAccountOperation.isSystem() && "Compra".equalsIgnoreCase(onAccountOperation.getDescription())) {
            item.setText(colIdx, "Compra - Nro.Trans. " + onAccountOperation.getOrder().getId());
         } else {
            item.setText(colIdx, String.valueOf(onAccountOperation.getDescription()));
         }

         ++colIdx;
         if (onAccountOperation.isCreditType()) {
            item.setText(colIdx, String.valueOf(onAccountOperation.getAmountToDisplay()));
            balanceAmount += onAccountOperation.getAmount();
         }

         ++colIdx;
         if (onAccountOperation.isDebitType()) {
            item.setText(colIdx, String.valueOf(onAccountOperation.getAmountToDisplay()));
            balanceAmount -= onAccountOperation.getAmount();
         }

         ++colIdx;
         item.setText(colIdx, this.formatBalanceAmount(balanceAmount));
      }

   }

   public String formatBalanceAmount(double balanceAmount) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(balanceAmount));
      str = str.replaceAll("\\.", ",");
      return balanceAmount != 0.0 ? str : "0,00";
   }

   private void createHeaderContents() {
      Button btnNuevoPago = new Button(this.headerContainer, 0);
      //btnNuevoPago.addSelectionListener(new 1(this));
      btnNuevoPago.setText("Nuevo pago");
      btnNuevoPago.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_add.gif"));
      new Label(this.headerContainer, 0);
      Label lblCaja = new Label(this.headerContainer, 0);
      lblCaja.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCaja.setText("Cliente:");
      this.txtCustomer = new Text(this.headerContainer, 2056);
      GridData gd_txtCustomer = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtCustomer.heightHint = 17;
      gd_txtCustomer.widthHint = 120;
      this.txtCustomer.setLayoutData(gd_txtCustomer);
      Button btnNewButton = new Button(this.headerContainer, 0);
      //btnNewButton.addSelectionListener(new 2(this));
      btnNewButton.setText("Cambiar cliente");
      btnNewButton.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      Label lblSep1 = new Label(this.headerContainer, 0);
      GridData gd_lblSep1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblSep1.widthHint = 10;
      lblSep1.setLayoutData(gd_lblSep1);
      Button btnActualizar = new Button(this.headerContainer, 0);
      btnActualizar.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnActualizar.setText("Actualizar");
      btnActualizar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_update.gif"));
      //btnActualizar.addSelectionListener(new 3(this));
   }

   private void addNewCredit() {
      if (!this.getWorkstationConfig().isCashOpened()) {
         this.alert("La caja debe estar abierta para registrar un pago nuevo");
      } else {
         this.processAddNewCredit();
      }

   }

   private void processAddNewCredit() {
      if (this.getCustomer() != null) {
         AddNewCustomerOnAccountCredit dialog = new AddNewCustomerOnAccountCredit(this.getShell());
         dialog.setEmployee(this.getEmployee());
         dialog.setCustomer(this.getCustomer());
         dialog.setCashRegister(this.getCashRegister());
         dialog.open();
         if ("OK".equalsIgnoreCase(dialog.getAction())) {
            this.searchOnAccountOperations();
         }
      } else {
         this.alert("Selecciona un cliente");
      }

   }

   private void changeCustomer() {
      ChangeCustomer dialog = new ChangeCustomer(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.setCustomer(dialog.getCustomer());
         this.txtCustomer.setText(this.customer.getFullName());
         this.searchOnAccountOperations();
      }

   }

   private void createBodyContents() {
      this.table = new Table(this.bodyContainer, 67584);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnNroProveedor = new TableColumn(this.table, 0);
      tblclmnNroProveedor.setWidth(90);
      tblclmnNroProveedor.setText("Fecha/Hora");
      TableColumn tblclmnNroCaja = new TableColumn(this.table, 0);
      tblclmnNroCaja.setWidth(61);
      tblclmnNroCaja.setText("Nro. Caja");
      TableColumn tblclmnCreadoPor = new TableColumn(this.table, 0);
      tblclmnCreadoPor.setWidth(100);
      tblclmnCreadoPor.setText("Cajero");
      TableColumn tblclmnTickets2 = new TableColumn(this.table, 0);
      tblclmnTickets2.setWidth(200);
      tblclmnTickets2.setText("Concepto");
      TableColumn tblclmnTickets3 = new TableColumn(this.table, 0);
      tblclmnTickets3.setWidth(100);
      tblclmnTickets3.setText("Créditos");
      TableColumn tblclmnTickets4 = new TableColumn(this.table, 0);
      tblclmnTickets4.setWidth(100);
      tblclmnTickets4.setText("Débitos");
      TableColumn tblclmnEstado = new TableColumn(this.table, 0);
      tblclmnEstado.setWidth(100);
      tblclmnEstado.setText("Saldo");
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Cuentas corrientes de clientes");
   }
   @Override
   protected Point getInitialSize() {
      return new Point(800, 600);
   }

   public List<CustomerOnAccountOperation> getOnAccountOperations() {
      return this.onAccountOperations;
   }

   public void setOnAccountOperations(List<CustomerOnAccountOperation> onAccountOperations) {
      this.onAccountOperations = onAccountOperations;
   }

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public Employee getEmployee() {
      return this.employee;
   }

   public void setEmployee(Employee employee) {
      this.employee = employee;
   }

   public CashRegister getCashRegister() {
      return this.cashRegister;
   }

   public void setCashRegister(CashRegister cashRegister) {
      this.cashRegister = cashRegister;
   }
}
