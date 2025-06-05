package com.facilvirtual.fvstoresdesk.ui.screens.sales;

import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.NotaDeCredito;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVApplicationWindow;
import com.facilvirtual.fvstoresdesk.ui.screens.cash.CashRegister;

public class NotasDeCreditoManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("NotasDeCreditoManager");
   private static NotasDeCreditoManager INSTANCE = null;
   private Composite headerContainer;
   private Composite mainContainer;
   private Table table;
   private DateTime startDatepicker;
   private DateTime endDatepicker;
   private List<NotaDeCredito> notasDeCredito = new ArrayList();
   private Employee cashier;
   private CashRegister cashRegister;

   public NotasDeCreditoManager() {
      super((Shell)null);
      this.setBlockOnOpen(true);
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new NotasDeCreditoManager();
      }

   }

   public static NotasDeCreditoManager getInstance() {
      createInstance();
      return INSTANCE;
   }
   @Override
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
      this.searchNotasDeCredito();
      return layoutContainer;
   }

   private void addNewNotaDeCredito() {
      if (this.getCashier() == null || !this.getCashier().isAdmin() && !this.getCashier().isAllowCreateOrder()) {
         this.alert(this.getMsgPrivilegeRequired());
      } else {
         this.processAddNewNotaDeCredito();
      }

   }

   private void processAddNewNotaDeCredito() {
      AddNewNotaDeCredito dialog = new AddNewNotaDeCredito(this.getShell());
      dialog.setCashier(this.getCashier());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.searchNotasDeCredito();
         this.getCashRegister().updatedWorkstationConfig();
      }

   }

   private void searchNotasDeCredito() {
      this.table.removeAll();
      this.initNotasDeCredito();
      this.initTable();
   }

   private void createHeaderContents() {
      Button btnNewSale = new Button(this.headerContainer, 0);
      //btnNewSale.addSelectionListener(new 1(this));
      btnNewSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnNewSale.setText("Nueva nota de crédito");
      btnNewSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_add.gif"));
      Label lblNewLabel = new Label(this.headerContainer, 0);
      GridData gd_lblNewLabel = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 20;
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
      //btnSearch.addSelectionListener(new 2(this));
      btnSearch.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnSearch.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      Label lblSep1 = new Label(this.headerContainer, 0);
      GridData gd_lblSep1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblSep1.widthHint = 15;
      lblSep1.setLayoutData(gd_lblSep1);
      Button btnActualizar = new Button(this.headerContainer, 0);
      btnActualizar.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnActualizar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_update.gif"));
      Label lblNewLabel_1 = new Label(this.headerContainer, 0);
      GridData gd_lblNewLabel_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel_1.widthHint = 15;
      lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
      Button btnImprimir = new Button(this.headerContainer, 0);
      btnImprimir.setText("Imprimir");
      btnImprimir.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_print.gif"));
      //btnImprimir.addSelectionListener(new 3(this));
      Label label = new Label(this.headerContainer, 0);
      GridData gd_label = new GridData(16384, 16777216, false, false, 1, 1);
      gd_label.widthHint = 15;
      label.setLayoutData(gd_label);
      Button btnComprobanteElectrnico = new Button(this.headerContainer, 0);
     // btnComprobanteElectrnico.addSelectionListener(new 4(this));
      btnComprobanteElectrnico.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnComprobanteElectrnico.setText("Comprobante Electrónico");
      btnComprobanteElectrnico.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_comprobante_e.gif"));
      //btnActualizar.addSelectionListener(new 5(this));
      //TODO: arreglar
   }

   private void printNotaDeCredito() {
      int idx = this.table.getSelectionIndex();
      if (idx >= 0) {
         try {
            Long notaId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            NotaDeCredito nota = this.getOrderService().getNotaDeCredito(notaId);
            this.getOrderService().printNotaDeCredito(nota, this.getAppConfig(), this.getWorkstationConfig(), this.getShell());
         } catch (Exception var4) {
         }
      } else {
         this.alert("Selecciona una nota de crédito");
      }

   }

   private void createBodyContents() {
      this.table = new Table(this.mainContainer, 67584);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnFecha = new TableColumn(this.table, 0);
      tblclmnFecha.setWidth(73);
      tblclmnFecha.setText("Fecha");
      TableColumn tblclmnTicket = new TableColumn(this.table, 0);
      tblclmnTicket.setWidth(81);
      tblclmnTicket.setText("Nro. transacción");
      TableColumn tblclmnNroCaja = new TableColumn(this.table, 0);
      tblclmnNroCaja.setWidth(61);
      tblclmnNroCaja.setText("Nro. caja");
      TableColumn tblclmnCajero = new TableColumn(this.table, 0);
      tblclmnCajero.setWidth(91);
      tblclmnCajero.setText("Cajero");
      TableColumn tblclmnTipoDeComprobante = new TableColumn(this.table, 0);
      tblclmnTipoDeComprobante.setWidth(89);
      tblclmnTipoDeComprobante.setText("Tipo de comprobante");
      TableColumn tblclmnTipoComprobante = new TableColumn(this.table, 0);
      tblclmnTipoComprobante.setWidth(100);
      tblclmnTipoComprobante.setText("Nro. comprobante");
      TableColumn tblclmnNroComprobante = new TableColumn(this.table, 0);
      tblclmnNroComprobante.setWidth(99);
      tblclmnNroComprobante.setText("CAE");
      TableColumn tblclmnAfipCaeFchVto = new TableColumn(this.table, 0);
      tblclmnAfipCaeFchVto.setWidth(111);
      tblclmnAfipCaeFchVto.setText("Comprobante Afip");
      TableColumn tblclmnTotal = new TableColumn(this.table, 0);
      tblclmnTotal.setWidth(60);
      tblclmnTotal.setText("Total");
      TableColumn tblclmnTickets4 = new TableColumn(this.table, 0);
      tblclmnTickets4.setWidth(178);
      tblclmnTickets4.setText("Producto/Servicio");
      TableColumn tblclmnEstado = new TableColumn(this.table, 0);
      tblclmnEstado.setWidth(85);
      tblclmnEstado.setText("Estado");
   }

   private void initNotasDeCredito() {
      Date startDate = this.buildDateFromInput(this.startDatepicker);
      Date endDate = DateUtils.addDays(this.buildDateFromInput(this.endDatepicker), 1);
      endDate = DateUtils.addMilliseconds(endDate, -1);
      this.setNotasDeCredito(this.getOrderService().getAllNotasDeCreditoForDateRange(startDate, endDate));
   }

   private void initTable() {
      //int colIdx = false;

      int colIdx;
      for(Iterator var3 = this.getNotasDeCredito().iterator(); var3.hasNext(); ++colIdx) {
         NotaDeCredito nota = (NotaDeCredito)var3.next();
         colIdx = 0;
         TableItem item = new TableItem(this.table, 0);
         item.setText(colIdx, nota.getCbteFchToDisplay());
         ++colIdx;
         item.setText(colIdx, String.valueOf(nota.getId()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(nota.getCashNumber()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(nota.getCashier().getUsername()));
         ++colIdx;
         if (nota.getCbteTipo() != null) {
            item.setText(colIdx, String.valueOf(nota.getCbteTipo().getName()));
         }

         ++colIdx;
         item.setText(colIdx, String.valueOf(nota.getCbteNro()));
         ++colIdx;
         if (nota.hasAfipCae()) {
            item.setText(colIdx, String.valueOf(nota.getAfipCae()));
         }

         ++colIdx;
         if (nota.hasAfipCae()) {
            item.setText(colIdx, nota.getAfipCbteTipoToDisplay());
         }

         ++colIdx;
         item.setText(colIdx, String.valueOf(nota.getTotalToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(nota.getProductDescription()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(nota.getStatusToDisplay()));
      }

   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Notas de Crédito");
   }
   @Override
   protected Point getInitialSize() {
      return new Point(1000, 600);
   }

   public List<NotaDeCredito> getNotasDeCredito() {
      return this.notasDeCredito;
   }

   public void setNotasDeCredito(List<NotaDeCredito> notasDeCredito) {
      this.notasDeCredito = notasDeCredito;
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

   private void openNotaDeCreditoAfip() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona una nota de crédito");
      } else {
         try {
            Long notaId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            NotaDeCredito nota = this.getOrderService().getNotaDeCredito(notaId);
            if (!this.getAppConfig().isAfipEnabledFacturaElectronica()) {
               this.alert("La Factura Electrónica no está habilitada");
            } else if (!this.getWorkstationConfig().isValidCodFactElect()) {
               this.alert("El Código de Factura Electrónica no es válido");
            } else if (!this.getAppConfig().isMonotributo() && !this.getAppConfig().isResponsableInscripto()) {
               this.alert("Verifique la condición de IVA en el menú Archivo->Configuración->General");
            } else if (!nota.hasAfipCae()) {
               AddNewNotaDeCreditoAfip dialog = new AddNewNotaDeCreditoAfip(this.getShell());
               dialog.setNotaDeCredito(nota);
               dialog.open();
               this.searchNotasDeCredito();
            } else {
               this.alert("La nota de crédito ya tiene un CAE asignado");
            }
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

   }
}
