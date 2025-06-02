package com.facilvirtual.fvstoresdesk.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.CashOperation;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;

public class CashOperationsManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("CashOperationsManager");
   private static CashOperationsManager INSTANCE = null;
   private boolean fullScreenMode = false;
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
   @Override
   protected Control createContents(Composite parent) {
      if (!getWorkstationConfig().isCashOpened()) {
         return createContentsCashClosed(parent);
      }

      Composite layoutContainer = new Composite(parent, SWT.NONE);
      GridLayout layout = new GridLayout(1, false);
      layout.marginWidth = 10;
      layout.marginHeight = 10;
      layout.verticalSpacing = 10;
      layoutContainer.setLayout(layout);
      
      // Header con filtros
      this.headerContainer = new Composite(layoutContainer, SWT.NONE);
      GridLayout headerLayout = new GridLayout(9, false);
      headerLayout.marginHeight = 0;
      headerLayout.marginWidth = 0;
      headerLayout.horizontalSpacing = 5;
      this.headerContainer.setLayout(headerLayout);
      this.headerContainer.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
      
      // Contenedor de la tabla
      this.bodyContainer = new Composite(layoutContainer, SWT.NONE);
      this.bodyContainer.setLayout(new FillLayout());
      GridData bodyData = new GridData(SWT.FILL, SWT.FILL, true, true);
      bodyData.heightHint = 400; // altura mínima
      this.bodyContainer.setLayoutData(bodyData);
      
      this.createHeaderContents();
      this.createBodyContents();
      this.searchCashOperations();
      return layoutContainer;
   }

   protected Control createContentsCashClosed(Composite parent) {
      logger.info("Iniciando Caja cerrada");
      if (this.isFullScreenMode()) {
         this.getMenuBarManager().dispose();
      }

      Composite container = new Composite(parent, 0);
      container.setBackground(SWTResourceManager.getColor(240, 240, 240));
      container.setLayout(null);
      Label lblLaCajaEst = new Label(container, 0);
      lblLaCajaEst.setBounds(10, 10, 157, 13);
      lblLaCajaEst.setText("La caja se encuentra cerrada.");
      lblLaCajaEst.setBackground(container.getBackground());
      
      return container;
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
      // Caja
      Label lblCaja = new Label(this.headerContainer, SWT.NONE);
      lblCaja.setText("Caja:");
      lblCaja.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
      
      this.comboCash = new Combo(this.headerContainer, SWT.READ_ONLY);
      GridData gd_comboCash = new GridData(SWT.FILL, SWT.CENTER, false, false);
      gd_comboCash.widthHint = 80;
      this.comboCash.setLayoutData(gd_comboCash);
      this.comboCash.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
      
      // Desde
      Label lblDesde = new Label(this.headerContainer, SWT.NONE);
      lblDesde.setText("Desde:");
      lblDesde.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
      
      this.startDatepicker = new DateTime(this.headerContainer, SWT.DATE | SWT.DROP_DOWN);
      this.startDatepicker.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
      
      // Hasta
      Label lblHasta = new Label(this.headerContainer, SWT.NONE);
      lblHasta.setText("Hasta:");
      lblHasta.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
      
      this.endDatepicker = new DateTime(this.headerContainer, SWT.DATE | SWT.DROP_DOWN);
      this.endDatepicker.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
      
      // Botón Buscar
      Button btnSearch = new Button(this.headerContainer, SWT.PUSH);
      btnSearch.setText("Buscar");
      btnSearch.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
      btnSearch.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      btnSearch.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            searchCashOperations();
         }
      });
      
      // Separador
      Label lblSep1 = new Label(this.headerContainer, SWT.NONE);
      GridData gd_lblSep1 = new GridData(SWT.LEFT, SWT.CENTER, true, false);
      gd_lblSep1.widthHint = 10;
      lblSep1.setLayoutData(gd_lblSep1);
      
      // Botón Actualizar
      Button btnActualizar = new Button(this.headerContainer, SWT.PUSH);
      btnActualizar.setText("Actualizar");
      btnActualizar.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
      btnActualizar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_update.gif"));
      btnActualizar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            searchCashOperations();
         }
      });

      // Inicializar combo de cajas
      List<WorkstationConfig> workstationConfigs = this.getAppConfigService().getActiveWorkstationConfigs();
      int selectedIdx = 0;
      for(Iterator var6 = workstationConfigs.iterator(); var6.hasNext(); ++selectedIdx) {
         WorkstationConfig wsc = (WorkstationConfig)var6.next();
         this.comboCash.add(wsc.getCashNumberToDisplay());
         if (wsc.getCashNumber() == this.getWorkstationConfig().getCashNumber()) {
            this.comboCash.select(selectedIdx);
         }
      }
   }

   private void createBodyContents() {
      this.table = new Table(this.bodyContainer, SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      this.table.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));

      TableColumn tblclmnFecha = new TableColumn(this.table, SWT.CENTER);
      tblclmnFecha.setWidth(130);
      tblclmnFecha.setText("Fecha/Hora");

      TableColumn tblclmnCajero = new TableColumn(this.table, SWT.LEFT);
      tblclmnCajero.setWidth(100);
      tblclmnCajero.setText("Cajero");

      TableColumn tblclmnConcepto = new TableColumn(this.table, SWT.LEFT);
      tblclmnConcepto.setWidth(300);
      tblclmnConcepto.setText("Concepto");

      TableColumn tblclmnIngresos = new TableColumn(this.table, SWT.RIGHT);
      tblclmnIngresos.setWidth(100);
      tblclmnIngresos.setText("Ingresos");

      TableColumn tblclmnEgresos = new TableColumn(this.table, SWT.RIGHT);
      tblclmnEgresos.setWidth(100);
      tblclmnEgresos.setText("Egresos");

      TableColumn tblclmnSaldo = new TableColumn(this.table, SWT.RIGHT);
      tblclmnSaldo.setWidth(100);
      tblclmnSaldo.setText("Saldo");
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Movimientos de caja");
   }

   @Override
   protected Point getInitialSize() {
      org.eclipse.swt.graphics.Rectangle bounds = getShell().getDisplay().getPrimaryMonitor().getBounds();
      int width = (int)(bounds.width * 0.8);
      int height = (int)(bounds.height * 0.8);
      return new Point(width, height);
   }

   public List<CashOperation> getCashOperations() {
      return this.cashOperations;
   }

   public void setCashOperations(List<CashOperation> cashOperations) {
      this.cashOperations = cashOperations;
   }

   protected Date buildDateFromInput(DateTime dateTime) {
      return DateUtils.setMilliseconds(
         DateUtils.setSeconds(
            DateUtils.setMinutes(
               DateUtils.setHours(
                  DateUtils.setDays(
                     DateUtils.setMonths(
                        DateUtils.setYears(new Date(), dateTime.getYear()),
                        dateTime.getMonth()
                     ),
                     dateTime.getDay()
                  ),
                  0
               ),
               0
            ),
            0
         ),
         0
      );
   }

   protected boolean isFullScreenMode() {
      return this.fullScreenMode;
   }

   protected void setFullScreenMode(boolean fullScreenMode) {
      this.fullScreenMode = fullScreenMode;
   }
}
