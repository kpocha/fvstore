package com.facilvirtual.fvstoresdesk.ui.screens.purchases;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVApplicationWindow;
import com.facilvirtual.fvstoresdesk.ui.screens.cash.CashRegister;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.confirmation.FVConfirmDialog;

public class PurchasesManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("PurchasesManager");
   private static PurchasesManager INSTANCE = null;
   private Composite headerContainer;
   private Composite mainContainer;
   private Table table;
   private DateTime startDatepicker;
   private DateTime endDatepicker;
   private List<Purchase> purchases = new ArrayList();
   private Employee cashier;
   private CashRegister cashRegister;

   public PurchasesManager() {
      super((Shell)null);
      this.setBlockOnOpen(true);
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new PurchasesManager();
      }

   }

   public static PurchasesManager getInstance() {
      createInstance();
      return INSTANCE;
   }
   @Override
   protected Control createContents(Composite parent) {
      Composite layoutContainer = new Composite(parent, 0);
      layoutContainer.setLayout(new GridLayout(1, false));
      this.headerContainer = new Composite(layoutContainer, 0);
      this.headerContainer.setLayout(new GridLayout(11, false));
      this.mainContainer = new Composite(layoutContainer, 0);
      this.mainContainer.setLayout(new FillLayout(256));
      this.mainContainer.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.createHeaderContents();
      this.createBodyContents();
      this.searchPurchases();
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
               this.searchPurchases();
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
      } else if (FVConfirmDialog.openQuestion(this.getShell(), "Anular compra", "¿Quieres anular la compra?")) {
         try {
            Long purchaseId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            this.getPurchaseService().cancelPurchase(purchaseId);
            this.searchPurchases();
            this.getCashRegister().updatedWorkstationConfig();
         } catch (Exception var3) {
         }
      }

   }

   private void addNewPurchase() {
      if (!this.getWorkstationConfig().isCashOpened()) {
         this.alert("La caja debe estar abierta para registrar una compra nueva");
      } else if (this.getCashier() == null || !this.getCashier().isAdmin() && !this.getCashier().isAllowCreatePurchase()) {
         this.alert(this.getMsgPrivilegeRequired());
      } else {
         this.processAddNewPurchase();
      }

   }

   private void processAddNewPurchase() {
      AddNewPurchase dialog = new AddNewPurchase(this.getShell());
      dialog.setCashier(this.getCashier());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.searchPurchases();
         this.getCashRegister().updatedWorkstationConfig();
      }

   }

   private void searchPurchases() {
      this.table.removeAll();
      this.initPurchases();
      this.initTable();
   }

   private void createHeaderContents() {
      Button btnNewSale = new Button(this.headerContainer, 0);
      btnNewSale.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            addNewPurchase();
         }
      });
      btnNewSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnNewSale.setText("Nueva compra");
      btnNewSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_add.gif"));
      Button btnViewSale = new Button(this.headerContainer, 0);
      btnViewSale.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            viewPurchaseDetails();
         }
      });
      btnViewSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnViewSale.setText("Ver detalle");
      btnViewSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_view_detail.gif"));
      Button btnCancelSale = new Button(this.headerContainer, 0);
      btnCancelSale.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            cancelPurchase();
         }
      });
      btnCancelSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnCancelSale.setText("Anular");
      btnCancelSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_delete.gif"));
      Label lblNewLabel = new Label(this.headerContainer, 0);
      GridData gd_lblNewLabel = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 50;
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
      btnSearch.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            searchPurchases();
         }
      });
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
      btnActualizar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            searchPurchases();
         }
      });
   }
//TODO:Arreglar
   private void createBodyContents() {
      this.table = new Table(this.mainContainer, 67584);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnFecha = new TableColumn(this.table, 0);
      tblclmnFecha.setWidth(73);
      tblclmnFecha.setText("Fecha");
      TableColumn tblclmnTicket = new TableColumn(this.table, 0);
      tblclmnTicket.setWidth(67);
      tblclmnTicket.setText("Nro. transacción");
      TableColumn tblclmnNroCaja = new TableColumn(this.table, 0);
      tblclmnNroCaja.setWidth(61);
      tblclmnNroCaja.setText("Nro. caja");
      TableColumn tblclmnCajero = new TableColumn(this.table, 0);
      tblclmnCajero.setWidth(91);
      tblclmnCajero.setText("Cajero");
      TableColumn tblclmnTipoComprobante = new TableColumn(this.table, 0);
      tblclmnTipoComprobante.setWidth(100);
      tblclmnTipoComprobante.setText("Tipo comprobante");
      TableColumn tblclmnNroComprobante = new TableColumn(this.table, 0);
      tblclmnNroComprobante.setWidth(100);
      tblclmnNroComprobante.setText("Nro. comprobante");
      TableColumn tblclmnCantArtculos = new TableColumn(this.table, 0);
      tblclmnCantArtculos.setWidth(126);
      tblclmnCantArtculos.setText("Proveedor");
      TableColumn tblclmnTotal = new TableColumn(this.table, 0);
      tblclmnTotal.setWidth(70);
      tblclmnTotal.setText("Monto");
      TableColumn tblclmnTickets4 = new TableColumn(this.table, 0);
      tblclmnTickets4.setWidth(178);
      tblclmnTickets4.setText("Observaciones");
      TableColumn tblclmnEstado = new TableColumn(this.table, 0);
      tblclmnEstado.setWidth(85);
      tblclmnEstado.setText("Estado");
   }

   private void initPurchases() {
      Date startDate = this.buildDateFromInput(this.startDatepicker);
      Date endDate = DateUtils.addDays(this.buildDateFromInput(this.endDatepicker), 1);
      endDate = DateUtils.addMilliseconds(endDate, -1);
      this.setPurchases(this.getPurchaseService().getAllPurchasesForDateRange(startDate, endDate));
   }

   private void initTable() {
//      int colIdx = false;

      int colIdx;
      for(Iterator var3 = this.getPurchases().iterator(); var3.hasNext(); ++colIdx) {
         Purchase purchase = (Purchase)var3.next();
         colIdx = 0;
         TableItem item = new TableItem(this.table, 0);
         item.setText(colIdx, purchase.getPurchaseDateToDisplay());
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchase.getId()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchase.getCashNumber()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchase.getCashier().getUsername()));
         ++colIdx;
         if (purchase.getReceiptType() != null) {
            item.setText(colIdx, String.valueOf(purchase.getReceiptType().getName()));
         }

         ++colIdx;
         item.setText(colIdx, String.valueOf(purchase.getReceiptNumber()));
         ++colIdx;
         if (purchase.getSupplier() != null) {
            item.setText(colIdx, String.valueOf(purchase.getSupplier().getCompanyName()));
         }

         ++colIdx;
         item.setText(colIdx, String.valueOf(purchase.getTotalToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchase.getObservations()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchase.getStatusToDisplay()));
      }

   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Compras");
   }
   @Override
   protected Point getInitialSize() {
      return new Point(1000, 600);
   }

   public List<Purchase> getPurchases() {
      return this.purchases;
   }

   public void setPurchases(List<Purchase> purchases) {
      this.purchases = purchases;
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
