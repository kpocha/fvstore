package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.CashOperation;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class CashOperationsManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("CashOperationsManager");
   private static CashOperationsManager INSTANCE = null;
   private Composite headerContainer;
   private Composite bodyContainer;
   private Table table;
   private List<CashOperation> cashOperations = new ArrayList();
   private Combo comboCash;
   private DateTime startDatepicker;
   private DateTime endDatepicker;

   public CashOperationsManager() {
      super((Shell)null);
      this.setBlockOnOpen(true);
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new CashOperationsManager();
      }

   }

   public static CashOperationsManager getInstance() {
      createInstance();
      return INSTANCE;
   }

   protected Control createContents(Composite parent) {
      Composite layoutContainer = new Composite(parent, 0);
      layoutContainer.setLayout(new GridLayout(1, false));
      this.headerContainer = new Composite(layoutContainer, 0);
      this.headerContainer.setLayout(new GridLayout(9, false));
      this.bodyContainer = new Composite(layoutContainer, 0);
      this.bodyContainer.setLayout(new FillLayout(256));
      this.bodyContainer.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.createHeaderContents();
      this.createBodyContents();
      this.searchCashOperations();
      return layoutContainer;
   }

   private void searchCashOperations() {
      this.table.removeAll();
      this.initCashOperations();
      this.initTable();
   }

   private void initCashOperations() {
      Date startDate = this.buildDateFromInput(this.startDatepicker);
      Date endDate = DateUtils.addDays(this.buildDateFromInput(this.endDatepicker), 1);
      endDate = DateUtils.addMilliseconds(endDate, -1);
      int cashNumber = Integer.valueOf(this.comboCash.getText());
      this.setCashOperations(this.getCashService().getActiveCashOperationsForDateRange(cashNumber, startDate, endDate));
   }

   private void initTable() {
     
      double balanceAmount = 0.0;

      int colIdx;
      for(Iterator var5 = this.getCashOperations().iterator(); var5.hasNext(); ++colIdx) {
         CashOperation cashOperation = (CashOperation)var5.next();
         colIdx = 0;
         if (cashOperation.isOpenCashOperation()) {
            balanceAmount = 0.0;
         }

         TableItem item = new TableItem(this.table, 0);
         item.setText(colIdx, String.valueOf(cashOperation.getOperationDateToDisplay()));
         ++colIdx;
         if (cashOperation.getAuthor() != null) {
            item.setText(colIdx, String.valueOf(cashOperation.getAuthor().getUsername()));
         }

         ++colIdx;
         if (cashOperation.isSystem() && "Venta".equalsIgnoreCase(cashOperation.getDescription())) {
            item.setText(colIdx, "Venta en efectivo - Nro.Trans. " + cashOperation.getOrder().getId());
         } else if (cashOperation.isSystem() && "Compra".equalsIgnoreCase(cashOperation.getDescription())) {
            item.setText(colIdx, "Compra de mercadería - Nro. Trans. " + cashOperation.getPurchase().getId());
         } else if (cashOperation.isSystem() && "Pago a proveedor".equalsIgnoreCase(cashOperation.getDescription())) {
            item.setText(colIdx, "Pago a proveedor - " + cashOperation.getSupplierOnAccountOperation().getSupplier().getCompanyName());
         } else if (cashOperation.isSystem() && "Cobro a cliente".equalsIgnoreCase(cashOperation.getDescription())) {
            item.setText(colIdx, "Cobro a cliente - " + cashOperation.getCustomerOnAccountOperation().getCustomer().getFullName());
         } else {
            item.setText(colIdx, String.valueOf(cashOperation.getDescription()));
         }

         ++colIdx;
         if (cashOperation.isIncomeType()) {
            item.setText(colIdx, String.valueOf(cashOperation.getAmountToDisplay()));
            balanceAmount += cashOperation.getAmount();
         }

         ++colIdx;
         if (cashOperation.isOutflowType()) {
            item.setText(colIdx, String.valueOf(cashOperation.getAmountToDisplay()));
            balanceAmount -= cashOperation.getAmount();
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
      Label lblCaja = new Label(this.headerContainer, 0);
      lblCaja.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCaja.setText("Caja:");
      this.comboCash = new Combo(this.headerContainer, 8);
      GridData gd_comboCash = new GridData(4, 16777216, true, false, 1, 1);
      gd_comboCash.widthHint = 12;
      this.comboCash.setLayoutData(gd_comboCash);
      List<WorkstationConfig> workstationConfigs = this.getAppConfigService().getActiveWorkstationConfigs();
      int selectedIdx = 0;

      for(Iterator var6 = workstationConfigs.iterator(); var6.hasNext(); ++selectedIdx) {
         WorkstationConfig wsc = (WorkstationConfig)var6.next();
         this.comboCash.add(wsc.getCashNumberToDisplay());
         if (wsc.getCashNumber() == this.getWorkstationConfig().getCashNumber()) {
            this.comboCash.select(selectedIdx);
         }
      }

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
     // btnSearch.addSelectionListener(new 1(this));
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
     // btnActualizar.addSelectionListener(new 2(this));
     //TODO:arrglar
   }

   private void createBodyContents() {
      this.table = new Table(this.bodyContainer, 67584);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnNroProveedor = new TableColumn(this.table, 0);
      tblclmnNroProveedor.setWidth(90);
      tblclmnNroProveedor.setText("Fecha/Hora");
      TableColumn tblclmnCreadoPor = new TableColumn(this.table, 0);
      tblclmnCreadoPor.setWidth(100);
      tblclmnCreadoPor.setText("Cajero");
      TableColumn tblclmnTickets2 = new TableColumn(this.table, 0);
      tblclmnTickets2.setWidth(265);
      tblclmnTickets2.setText("Concepto");
      TableColumn tblclmnTickets3 = new TableColumn(this.table, 0);
      tblclmnTickets3.setWidth(100);
      tblclmnTickets3.setText("Ingresos");
      TableColumn tblclmnTickets4 = new TableColumn(this.table, 0);
      tblclmnTickets4.setWidth(100);
      tblclmnTickets4.setText("Egresos");
      TableColumn tblclmnEstado = new TableColumn(this.table, 0);
      tblclmnEstado.setWidth(100);
      tblclmnEstado.setText("Saldo");
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Movimientos de caja");
   }

   protected Point getInitialSize() {
      return new Point(800, 600);
   }

   public List<CashOperation> getCashOperations() {
      return this.cashOperations;
   }

   public void setCashOperations(List<CashOperation> cashOperations) {
      this.cashOperations = cashOperations;
   }
}
