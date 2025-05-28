package com.facilvirtual.fvstoresdesk.ui;

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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Order;

public class SalesManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("SalesManager");
   private static SalesManager INSTANCE = null;
   private Composite layoutContainer;
   private Composite headerContainer;
   private Composite mainContainer;
   private Composite footerContainer;
   private Table table;
   private Text txtSubtotal;
   private Text txtDiscount;
   private Text txtTotal;
   private Text txtCashTotal;
   private Text txtCreditCardTotal;
   private Text txtOnAccountTotal;
   private Text txtDebitCardTotal;
   private Text txtCheckTotal;
   private Text txtTicketsTotal;
   private DateTime startDatepicker;
   private DateTime endDatepicker;
   private List<Order> orders = new ArrayList();
   private Employee cashier;
   private CashRegister cashRegister;

   public SalesManager() {
      super((Shell)null);
      this.setBlockOnOpen(true);
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new SalesManager();
      }

   }

   public static SalesManager getInstance() {
      createInstance();
      return INSTANCE;
   }

   protected Control createContents(Composite parent) {
      this.layoutContainer = new Composite(parent, 0);
      this.layoutContainer.setLayout(new GridLayout(1, false));
      this.headerContainer = new Composite(this.layoutContainer, 0);
      this.headerContainer.setLayout(new GridLayout(15, false));
      this.mainContainer = new Composite(this.layoutContainer, 0);
      this.mainContainer.setLayout(new FillLayout(256));
      this.mainContainer.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.footerContainer = new Composite(this.layoutContainer, 0);
      this.footerContainer.setLayout(new FillLayout(256));
      GridData gd_footerContainer = new GridData(4, 128, true, false, 1, 1);
      gd_footerContainer.heightHint = 110;
      this.footerContainer.setLayoutData(gd_footerContainer);
      this.createHeaderContent();
      this.createMainContent();
      this.createFooterContent();
      this.searchOrders();
      return this.layoutContainer;
   }

   private void viewOrderDetails() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona una venta");
      } else {
         try {
            Long orderId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            Order order = this.getOrderService().getOrder(orderId);
            OrderDetails dialog = new OrderDetails(this.getShell());
            dialog.setOrder(order);
            dialog.open();
            this.searchOrders();
            this.getCashRegister().updatedWorkstationConfig();
         } catch (Exception var5) {
         }
      }

   }

   private void openFacturaElectronicaDialog() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona una venta");
      } else {
         try {
            Long orderId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            Order order = this.getOrderService().getOrder(orderId);
            if (!this.getAppConfig().isAfipEnabledFacturaElectronica()) {
               this.alert("La Factura Electrónica no está habilitada");
            } else if (!this.getWorkstationConfig().isValidCodFactElect()) {
               this.alert("El Código de Factura Electrónica no es válido");
            } else if (!this.getAppConfig().isMonotributo() && !this.getAppConfig().isResponsableInscripto()) {
               this.alert("Verifique la condición de IVA en el menú Archivo->Configuración->General");
            } else if (!order.hasAfipCae()) {
               AddNewFacturaElectronicaAfip dialog = new AddNewFacturaElectronicaAfip(this.getShell());
               dialog.setOrder(order);
               dialog.open();
               this.searchOrders();
            } else {
               this.alert("La venta ya tiene un CAE asignado");
            }
         } catch (Exception var5) {
         }
      }

   }

   private void cancelOrder() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona una venta");
      } else if (FVConfirmDialog.openQuestion(this.getShell(), "Anular venta", "¿Quieres anular la venta?")) {
         try {
            Long orderId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            this.getOrderService().cancelOrder(orderId);
            this.searchOrders();
            this.getCashRegister().updatedWorkstationConfig();
         } catch (Exception var3) {
         }
      }

   }

   private void searchOrders() {
      this.table.removeAll();
      this.initOrders();
      this.initTable();
      this.initTotalAmounts();
   }

   private void initTotalAmounts() {
      this.txtSubtotal.setText("$ " + this.getOrderService().getSubtotalToDisplayForOrders(this.getOrders()));
      this.txtDiscount.setText("$ " + this.getOrderService().getDiscountSurchargeToDisplayForOrders(this.getOrders()));
      this.txtTotal.setText("$ " + this.getOrderService().getTotalToDisplayForOrders(this.getOrders()));
      this.txtCashTotal.setText("$ " + this.getOrderService().getNetCashTotalToDisplayForOrders(this.getOrders()));
      this.txtCreditCardTotal.setText("$ " + this.getOrderService().getNetCreditCardTotalToDisplayForOrders(this.getOrders()));
      this.txtOnAccountTotal.setText("$ " + this.getOrderService().getNetOnAccountTotalToDisplayForOrders(this.getOrders()));
      this.txtDebitCardTotal.setText("$ " + this.getOrderService().getNetDebitCardTotalToDisplayForOrders(this.getOrders()));
      this.txtCheckTotal.setText("$ " + this.getOrderService().getNetCheckTotalToDisplayForOrders(this.getOrders()));
      this.txtTicketsTotal.setText("$ " + this.getOrderService().getNetTicketsTotalToDisplayForOrders(this.getOrders()));
   }

   private void createHeaderContent() {
      Button btnNewSale = new Button(this.headerContainer, 0);
      btnNewSale.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            close();
         }
      });
      btnNewSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnNewSale.setText("Nueva venta");
      btnNewSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_add.gif"));

      Button btnViewSale = new Button(this.headerContainer, 0);
      btnViewSale.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            viewOrderDetails();
         }
      });
      btnViewSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnViewSale.setText("Ver detalle");
      btnViewSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_view_detail.gif"));

      Button btnCancelSale = new Button(this.headerContainer, 0);
      btnCancelSale.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            cancelOrder();
         }
      });
      btnCancelSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnCancelSale.setText("Anular");
      btnCancelSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_delete.gif"));

      Label lblNewLabel = new Label(this.headerContainer, 0);
      GridData gd_lblNewLabel = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 7;
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
            searchOrders();
         }
      });
      btnSearch.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnSearch.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      Label lblSep1 = new Label(this.headerContainer, 0);
      GridData gd_lblSep1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblSep1.widthHint = 7;
      lblSep1.setLayoutData(gd_lblSep1);
      Button btnActualizar = new Button(this.headerContainer, 0);
      btnActualizar.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnActualizar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_update.gif"));
      btnActualizar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            searchOrders();
         }
      });
      Label lblSep2 = new Label(this.headerContainer, 0);
      GridData gd_lblSep2 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblSep2.widthHint = 7;
      lblSep2.setLayoutData(gd_lblSep2);
      Button btnImprimir = new Button(this.headerContainer, 0);
      btnImprimir.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnImprimir.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_print.gif"));
      btnImprimir.setText("Imprimir");
      btnImprimir.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            printOrder();
         }
      });
      Label lblSep3 = new Label(this.headerContainer, 0);
      GridData gd_lblSep3 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblSep3.widthHint = 7;
      lblSep3.setLayoutData(gd_lblSep3);
      Button btnFacturaElectrnica = new Button(this.headerContainer, 0);
      btnFacturaElectrnica.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            openFacturaElectronicaDialog();
            searchOrders();
         }
      });
      btnFacturaElectrnica.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnFacturaElectrnica.setText("Factura Electrónica");
      btnFacturaElectrnica.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_comprobante_e.gif"));
   }

   private void printOrder() {
      int idx = this.table.getSelectionIndex();
      if (idx >= 0) {
         try {
            Long orderId = Long.parseLong(this.table.getItem(idx).getText(1).trim());
            Order order = this.getOrderService().getOrder(orderId);
            this.getOrderService().printOrder(order, this.getAppConfig(), this.getWorkstationConfig(), this.getShell());
         } catch (Exception var4) {
         }
      } else {
         this.alert("Selecciona una venta");
      }

   }

   private void createMainContent() {
      this.table = new Table(this.mainContainer, 67584);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnFecha = new TableColumn(this.table, 0);
      tblclmnFecha.setWidth(115);
      tblclmnFecha.setText("Fecha/Hora");
      TableColumn tblclmnTicket = new TableColumn(this.table, 0);
      tblclmnTicket.setWidth(67);
      tblclmnTicket.setText("Nro. transacción");
      TableColumn tblclmnNroCaja = new TableColumn(this.table, 0);
      tblclmnNroCaja.setWidth(61);
      tblclmnNroCaja.setText("Nro. caja");
      TableColumn tblclmnCajero = new TableColumn(this.table, 0);
      tblclmnCajero.setWidth(91);
      tblclmnCajero.setText("Cajero");
      TableColumn tblclmnTipoDeComprobante = new TableColumn(this.table, 0);
      tblclmnTipoDeComprobante.setWidth(89);
      tblclmnTipoDeComprobante.setText("Tipo comprobante");
      TableColumn tblclmnNroComprobante = new TableColumn(this.table, 0);
      tblclmnNroComprobante.setWidth(88);
      tblclmnNroComprobante.setText("Nro. comprobante");
      TableColumn tblclmnAfipCae = new TableColumn(this.table, 0);
      tblclmnAfipCae.setWidth(99);
      tblclmnAfipCae.setText("CAE");
      TableColumn tblclmnAfipCaeFchVto = new TableColumn(this.table, 0);
      tblclmnAfipCaeFchVto.setWidth(111);
      tblclmnAfipCaeFchVto.setText("Comprobante Afip");
      TableColumn tblclmnLista = new TableColumn(this.table, 0);
      tblclmnLista.setWidth(90);
      tblclmnLista.setText("Lista");
      TableColumn tblclmnCantArtculos = new TableColumn(this.table, 0);
      tblclmnCantArtculos.setWidth(75);
      tblclmnCantArtculos.setText("Cliente");
      TableColumn tblclmnSubtotal = new TableColumn(this.table, 0);
      tblclmnSubtotal.setWidth(59);
      tblclmnSubtotal.setText("Subtotal");
      TableColumn tblclmnDescuento = new TableColumn(this.table, 0);
      tblclmnDescuento.setWidth(49);
      tblclmnDescuento.setText("Descuento/Recargo");
      TableColumn tblclmnTotal = new TableColumn(this.table, 0);
      tblclmnTotal.setWidth(59);
      tblclmnTotal.setText("Total");
      TableColumn tblclmnEfectivo = new TableColumn(this.table, 0);
      tblclmnEfectivo.setWidth(73);
      tblclmnEfectivo.setText("Efectivo");
      TableColumn tblclmnTarjetaDeCrdito = new TableColumn(this.table, 0);
      tblclmnTarjetaDeCrdito.setWidth(77);
      tblclmnTarjetaDeCrdito.setText("Tarjeta crédito");
      TableColumn tblclmnNewColumn = new TableColumn(this.table, 0);
      tblclmnNewColumn.setWidth(83);
      tblclmnNewColumn.setText("Nombre tarjeta crédito");
      TableColumn tblclmnCtaCte = new TableColumn(this.table, 0);
      tblclmnCtaCte.setWidth(63);
      tblclmnCtaCte.setText("Cuenta corriente");
      TableColumn tblclmnTDbito = new TableColumn(this.table, 0);
      tblclmnTDbito.setWidth(80);
      tblclmnTDbito.setText("Tarjeta débito");
      TableColumn tblclmnNombreTDbito = new TableColumn(this.table, 0);
      tblclmnNombreTDbito.setWidth(80);
      tblclmnNombreTDbito.setText("Nombre tarjeta débito");
      TableColumn tblclmnCheque = new TableColumn(this.table, 0);
      tblclmnCheque.setWidth(62);
      tblclmnCheque.setText("Cheque");
      TableColumn tblclmnTickets = new TableColumn(this.table, 0);
      tblclmnTickets.setWidth(55);
      tblclmnTickets.setText("Tickets");
      TableColumn tblclmnTickets4 = new TableColumn(this.table, 0);
      tblclmnTickets4.setWidth(67);
      tblclmnTickets4.setText("Observaciones");
      TableColumn tblclmnEstado = new TableColumn(this.table, 0);
      tblclmnEstado.setWidth(85);
      tblclmnEstado.setText("Estado");
   }

   private void initOrders() {
      try {
         Date startDate = this.buildDateFromInput(this.startDatepicker);
         Date endDate = DateUtils.addDays(this.buildDateFromInput(this.endDatepicker), 1);
         endDate = DateUtils.addMilliseconds(endDate, -1);
         this.setOrders(this.getOrderService().getAllOrdersForDateRange(startDate, endDate));
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void initTable() {
      try {
         int colIdx;
         for(Iterator var3 = this.getOrders().iterator(); var3.hasNext(); ++colIdx) {
            Order order = (Order)var3.next();
            colIdx = 0;
            TableItem item = new TableItem(this.table, 0);
            item.setText(colIdx, order.getSaleDateToDisplay());
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getId()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getCashNumber()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getCashier().getUsername()));
            ++colIdx;
            if (order.getReceiptType() != null) {
               item.setText(colIdx, String.valueOf(order.getReceiptType().getName()));
            }

            ++colIdx;
            if (order.getReceiptNumber() != null) {
               item.setText(colIdx, String.valueOf(order.getReceiptNumber()));
            }

            ++colIdx;
            if (order.hasAfipCae()) {
               item.setText(colIdx, String.valueOf(order.getAfipCae()));
            }

            ++colIdx;
            if (order.hasAfipCae()) {
               item.setText(colIdx, order.getAfipCbteTipoToDisplay());
            }

            ++colIdx;
            if (order.getPriceList() != null) {
               item.setText(colIdx, String.valueOf(order.getPriceList().getName()));
            }

            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getCustomer().getFullName()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getSubtotalToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getDiscountSurchargeToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getTotalToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getNetCashAmountToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getNetCreditCardAmountToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getCreditCardNameToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getNetOnAccountAmountToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getNetDebitCardAmountToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getDebitCardNameToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getNetCheckAmountToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getNetTicketsAmountToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getObservationsToDisplay()));
            ++colIdx;
            item.setText(colIdx, String.valueOf(order.getStatusToDisplay()));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   private void createFooterContent() {
      Group grpTotales = new Group(this.footerContainer, 0);
      grpTotales.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      grpTotales.setText("Totales");
      grpTotales.setLayout(new GridLayout(1, false));
      Composite grpTotalesTable = new Composite(grpTotales, 0);
      grpTotalesTable.setLayout(new GridLayout(6, true));
      grpTotalesTable.setLayoutData(new GridData(4, 1, true, false));
      Label lblSubtotal = new Label(grpTotalesTable, 0);
      lblSubtotal.setLayoutData(new GridData(131072, 16777216, true, false, 1, 1));
      lblSubtotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblSubtotal.setText("Subtotal");
      this.txtSubtotal = new Text(grpTotalesTable, 133128);
      this.txtSubtotal.setBackground(SWTResourceManager.getColor(1));
      this.txtSubtotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtSubtotal = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSubtotal.widthHint = 100;
      this.txtSubtotal.setLayoutData(gd_txtSubtotal);
      Label lblEfectivo = new Label(grpTotalesTable, 0);
      lblEfectivo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblEfectivo.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblEfectivo.setText("Efectivo");
      this.txtCashTotal = new Text(grpTotalesTable, 133128);
      this.txtCashTotal.setBackground(SWTResourceManager.getColor(1));
      this.txtCashTotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtCashTotal = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtCashTotal.widthHint = 100;
      this.txtCashTotal.setLayoutData(gd_txtCashTotal);
      Label lblTarjetaDbito = new Label(grpTotalesTable, 0);
      lblTarjetaDbito.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblTarjetaDbito.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTarjetaDbito.setText("Tarjeta de Débito");
      this.txtDebitCardTotal = new Text(grpTotalesTable, 133128);
      this.txtDebitCardTotal.setBackground(SWTResourceManager.getColor(1));
      this.txtDebitCardTotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtDebitCardTotal = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtDebitCardTotal.widthHint = 100;
      this.txtDebitCardTotal.setLayoutData(gd_txtDebitCardTotal);
      Label lblDescuento = new Label(grpTotalesTable, 0);
      lblDescuento.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblDescuento.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblDescuento.setText("Descuentos/Recargos");
      this.txtDiscount = new Text(grpTotalesTable, 133128);
      this.txtDiscount.setBackground(SWTResourceManager.getColor(1));
      this.txtDiscount.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtDiscount = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtDiscount.widthHint = 100;
      this.txtDiscount.setLayoutData(gd_txtDiscount);
      Label lblTarjetaCrdito = new Label(grpTotalesTable, 0);
      lblTarjetaCrdito.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblTarjetaCrdito.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTarjetaCrdito.setText("Tarjeta de Crédito");
      this.txtCreditCardTotal = new Text(grpTotalesTable, 133128);
      this.txtCreditCardTotal.setBackground(SWTResourceManager.getColor(1));
      this.txtCreditCardTotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtCreditCardTotal = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtCreditCardTotal.widthHint = 100;
      this.txtCreditCardTotal.setLayoutData(gd_txtCreditCardTotal);
      Label lblCheque = new Label(grpTotalesTable, 0);
      lblCheque.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblCheque.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCheque.setText("Cheque");
      this.txtCheckTotal = new Text(grpTotalesTable, 133128);
      this.txtCheckTotal.setBackground(SWTResourceManager.getColor(1));
      this.txtCheckTotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtCheckTotal = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtCheckTotal.widthHint = 100;
      this.txtCheckTotal.setLayoutData(gd_txtCheckTotal);
      Label lblTotal = new Label(grpTotalesTable, 0);
      lblTotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblTotal.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTotal.setText("Total");
      this.txtTotal = new Text(grpTotalesTable, 133128);
      this.txtTotal.setBackground(SWTResourceManager.getColor(1));
      this.txtTotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtTotal = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtTotal.widthHint = 100;
      this.txtTotal.setLayoutData(gd_txtTotal);
      Label lblCuentaCorriente = new Label(grpTotalesTable, 0);
      lblCuentaCorriente.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblCuentaCorriente.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCuentaCorriente.setText("Cuenta Corriente");
      this.txtOnAccountTotal = new Text(grpTotalesTable, 133128);
      this.txtOnAccountTotal.setBackground(SWTResourceManager.getColor(1));
      this.txtOnAccountTotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtOnAccountTotal = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtOnAccountTotal.widthHint = 100;
      this.txtOnAccountTotal.setLayoutData(gd_txtOnAccountTotal);
      Label lblTickets = new Label(grpTotalesTable, 0);
      lblTickets.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblTickets.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTickets.setText("Tickets");
      this.txtTicketsTotal = new Text(grpTotalesTable, 133128);
      this.txtTicketsTotal.setBackground(SWTResourceManager.getColor(1));
      this.txtTicketsTotal.setFont(SWTResourceManager.getFont("Tahoma", 10, 1));
      GridData gd_txtTicketsTotal = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtTicketsTotal.widthHint = 100;
      this.txtTicketsTotal.setLayoutData(gd_txtTicketsTotal);
   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Ventas");
   }
   @Override
   protected Point getInitialSize() {
      return new Point(1000, 600);
   }

   public List<Order> getOrders() {
      return this.orders;
   }

   public void setOrders(List<Order> orders) {
      this.orders = orders;
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
