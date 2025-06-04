package com.facilvirtual.fvstoresdesk.ui.screens.sales;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.Budget;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.print.BudgetPdfCreator;
import com.facilvirtual.fvstoresdesk.ui.AddNewBudget;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVApplicationWindow;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.confirmation.FVConfirmDialog;
import com.facilvirtual.fvstoresdesk.ui.screens.cash.CashRegister;
import com.facilvirtual.fvstoresdesk.ui.screens.purchases.PurchaseDetails;

public class BudgetsManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("BudgetsManager");
   private static BudgetsManager INSTANCE = null;
   private Composite headerContainer;
   private Composite mainContainer;
   private Table table;
   private DateTime startDatepicker;
   private DateTime endDatepicker;
   private List<Budget> budgets = new ArrayList();
   private Employee cashier;
   private CashRegister cashRegister;

   public BudgetsManager() {
      super((Shell)null);
      this.setBlockOnOpen(true);
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new BudgetsManager();
      }

   }

   public static BudgetsManager getInstance() {
      createInstance();
      return INSTANCE;
   }

   protected Control createContents(Composite parent) {
      Composite layoutContainer = new Composite(parent, 0);
      layoutContainer.setLayout(new GridLayout(1, false));
      this.headerContainer = new Composite(layoutContainer, 0);
      this.headerContainer.setLayout(new GridLayout(13, false));
      this.mainContainer = new Composite(layoutContainer, 0);
      this.mainContainer.setLayout(new FillLayout(256));
      this.mainContainer.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.createHeaderContents();
      this.createBodyContents();
      this.searchBudgets();
      return layoutContainer;
   }

   private void viewPurchaseDetails() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona una compra");
      } else {
         try {
            Long purchaseId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            Purchase purchase = this.getPurchaseService().getPurchase(purchaseId);
            PurchaseDetails dialog = new PurchaseDetails(this.getShell());
            dialog.setPurchase(purchase);
            dialog.open();
            if ("OK".equalsIgnoreCase(dialog.getAction())) {
               this.searchBudgets();
               this.getCashRegister().updatedWorkstationConfig();
            }
         } catch (Exception var5) {
         }
      }

   }

   private void cancelPurchase() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona una compra");
      } else if (FVConfirmDialog.openQuestion(this.getShell(), "Anular presupuesto", "¿Quieres anular el presupuesto?")) {
         try {
            Long purchaseId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            this.getPurchaseService().cancelPurchase(purchaseId);
            this.searchBudgets();
            this.getCashRegister().updatedWorkstationConfig();
         } catch (Exception var3) {
         }
      }

   }

   private void addNewBudget() {
      AddNewBudget dialog = new AddNewBudget(this.getShell());
      dialog.setCashier(this.getCashier());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.searchBudgets();
      }

   }

   private void searchBudgets() {
      this.table.removeAll();
      this.initBudgets();
      this.initTable();
   }

   private void createHeaderContents() {
      Button btnNewBudget = new Button(this.headerContainer, 0);
      //btnNewBudget.addSelectionListener(new 1(this));
      btnNewBudget.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnNewBudget.setText("Nuevo presupuesto");
      btnNewBudget.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_add.gif"));
      Button btnViewSale = new Button(this.headerContainer, 0);
      btnViewSale.setVisible(false);
     // btnViewSale.addSelectionListener(new 2(this));
      btnViewSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnViewSale.setText("Ver detalle");
      btnViewSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_view_detail.gif"));
      Button btnCancelSale = new Button(this.headerContainer, 0);
      btnCancelSale.setVisible(false);
      //btnCancelSale.addSelectionListener(new 3(this));
      btnCancelSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnCancelSale.setText("Anular");
      btnCancelSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_delete.gif"));
      Label lblNewLabel = new Label(this.headerContainer, 0);
      GridData gd_lblNewLabel = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 10;
      lblNewLabel.setLayoutData(gd_lblNewLabel);
      lblNewLabel.setText("");
      Label lblDesde = new Label(this.headerContainer, 0);
      lblDesde.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblDesde.setText("Desde:");
      this.startDatepicker = new DateTime(this.headerContainer, 2048);
      this.startDatepicker.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      Label lblHasta = new Label(this.headerContainer, 0);
      lblHasta.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblHasta.setText("Hasta:");
      this.endDatepicker = new DateTime(this.headerContainer, 2048);
      this.endDatepicker.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      Button btnSearch = new Button(this.headerContainer, 0);
     // btnSearch.addSelectionListener(new 4(this));
      btnSearch.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnSearch.setText("Buscar");
      btnSearch.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      Label lblSep1 = new Label(this.headerContainer, 0);
      GridData gd_lblSep1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblSep1.widthHint = 10;
      lblSep1.setLayoutData(gd_lblSep1);
      Button btnActualizar = new Button(this.headerContainer, 0);
      btnActualizar.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnActualizar.setText("Actualizar");
      btnActualizar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_update.gif"));
      //btnActualizar.addSelectionListener(new 5(this));
      Label lblSep2 = new Label(this.headerContainer, 0);
      GridData gd_lblSep2 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblSep2.widthHint = 10;
      lblSep2.setLayoutData(gd_lblSep2);
      Button btnImprimir = new Button(this.headerContainer, 0);
      btnImprimir.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnImprimir.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_print.gif"));
      btnImprimir.setText("Imprimir");
      //btnImprimir.addSelectionListener(new 6(this));
   }

   private void printBudget() {
      int idx = this.table.getSelectionIndex();
      if (idx >= 0) {
         try {
            Long budgetId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            Budget budget = this.getBudgetService().getBudget(budgetId);
            BudgetPdfCreator budgetPdfCreator = new BudgetPdfCreator(budget, this.getShell());
            budgetPdfCreator.createPdf();
         } catch (Exception var5) {
            logger.error("Error imprimiendo presupuesto");
            logger.error(var5.getMessage());
            //logger.error(var5);
         }
      } else {
         this.alert("Selecciona un presupuesto");
      }

   }

   private void createBodyContents() {
      this.table = new Table(this.mainContainer, 67584);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnFecha = new TableColumn(this.table, 0);
      tblclmnFecha.setWidth(73);
      tblclmnFecha.setText("Fecha");
      TableColumn tblclmnId = new TableColumn(this.table, 0);
      tblclmnId.setResizable(false);
      tblclmnId.setText("Id");
      TableColumn tblclmnTicket = new TableColumn(this.table, 0);
      tblclmnTicket.setWidth(114);
      tblclmnTicket.setText("Nro. presupuesto");
      TableColumn tblclmnNroCaja = new TableColumn(this.table, 0);
      tblclmnNroCaja.setWidth(61);
      tblclmnNroCaja.setText("Nro. caja");
      TableColumn tblclmnCajero = new TableColumn(this.table, 0);
      tblclmnCajero.setWidth(119);
      tblclmnCajero.setText("Cajero");
      TableColumn tblclmnCantArtculos = new TableColumn(this.table, 0);
      tblclmnCantArtculos.setWidth(218);
      tblclmnCantArtculos.setText("Cliente");
      TableColumn tblclmnTotal = new TableColumn(this.table, 0);
      tblclmnTotal.setWidth(77);
      tblclmnTotal.setText("Monto");
      TableColumn tblclmnTickets4 = new TableColumn(this.table, 0);
      tblclmnTickets4.setWidth(266);
      tblclmnTickets4.setText("Observaciones");
   }

   private void initBudgets() {
      Date startDate = this.buildDateFromInput(this.startDatepicker);
      Date endDate = DateUtils.addDays(this.buildDateFromInput(this.endDatepicker), 1);
      endDate = DateUtils.addMilliseconds(endDate, -1);
      this.setBudgets(this.getBudgetService().getAllBudgetsForDateRange(startDate, endDate));
   }

   private void initTable() {


      int colIdx;
      for(Iterator var3 = this.getBudgets().iterator(); var3.hasNext(); ++colIdx) {
         Budget budget = (Budget)var3.next();
         colIdx = 0;
         TableItem item = new TableItem(this.table, 0);
         item.setText(colIdx, budget.getBudgetDateToDisplay());
         ++colIdx;
         item.setText(colIdx, String.valueOf(budget.getId()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budget.getBudgetNumber()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budget.getCashNumber()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budget.getCashier().getUsername()));
         ++colIdx;
         if (budget.getCustomer() != null) {
            item.setText(colIdx, String.valueOf(budget.getCustomerNameToPrint()));
         }

         ++colIdx;
         item.setText(colIdx, String.valueOf(budget.getTotalToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budget.getObservations()));
      }

   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Presupuestos");
   }
   @Override
   protected Point getInitialSize() {
      return new Point(1000, 600);
   }

   public List<Budget> getBudgets() {
      return this.budgets;
   }

   public void setBudgets(List<Budget> budgets) {
      this.budgets = budgets;
   }

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   public CashRegister getCashRegister() {
      return this.cashRegister;
   }

   public void setCashRegister(CashRegister cashRegister) {
      this.cashRegister = cashRegister;
   }
}
