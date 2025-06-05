package com.facilvirtual.fvstoresdesk.ui.screens.sales;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import com.facilvirtual.fvstoresdesk.model.ReceiptType;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.Customer.ChangeCustomer;
public class OrderDetails extends AbstractFVDialog {
   protected static Logger logger = LoggerFactory.getLogger("OrderDetails");
   private String action = "";
   private Order order;
   private Table table;
   private Text txtObservations;
   private Text txtOrderId;
   private Button btnCancelled;
   private Composite composite;
   private Label label;
   private Label lblTipoDeComprobante;
   private Combo comboReceiptTypes;
   private Label lblNewLabel;
   private Label lblCliente;
   private Text txtCustomerName;
   private Button btnCambiarCliente;

   public OrderDetails(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout(new GridLayout(1, false));
      this.composite = new Composite(container, 0);
      this.composite.setLayout(new GridLayout(6, false));
      Label lblNroTransaccin = new Label(this.composite, 0);
      GridData gd_lblNroTransaccin = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNroTransaccin.widthHint = 120;
      lblNroTransaccin.setLayoutData(gd_lblNroTransaccin);
      lblNroTransaccin.setText("Nro. Transacción:");
      this.label = new Label(this.composite, 0);
      GridData gd_label = new GridData(16384, 16777216, false, false, 1, 1);
      gd_label.widthHint = 20;
      this.label.setLayoutData(gd_label);
      this.lblTipoDeComprobante = new Label(this.composite, 0);
      GridData gd_lblTipoDeComprobante = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblTipoDeComprobante.widthHint = 150;
      this.lblTipoDeComprobante.setLayoutData(gd_lblTipoDeComprobante);
      this.lblTipoDeComprobante.setText("Tipo de comprobante");
      this.lblNewLabel = new Label(this.composite, 0);
      GridData gd_lblNewLabel = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 20;
      this.lblNewLabel.setLayoutData(gd_lblNewLabel);
      this.lblCliente = new Label(this.composite, 0);
      this.lblCliente.setText("Cliente");
      new Label(this.composite, 0);
      this.txtOrderId = new Text(this.composite, 133128);
      this.txtOrderId.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      this.txtOrderId.setBackground(SWTResourceManager.getColor(1));
      new Label(this.composite, 0);
      this.comboReceiptTypes = new Combo(this.composite, 8);
      this.comboReceiptTypes.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      new Label(this.composite, 0);
      this.txtCustomerName = new Text(this.composite, 2056);
      GridData gd_txtCustomerName = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtCustomerName.widthHint = 150;
      this.txtCustomerName.setLayoutData(gd_txtCustomerName);
      this.txtCustomerName.setText(this.getOrder().getCustomer().getFullName());
      this.btnCambiarCliente = new Button(this.composite, 0);
      this.btnCambiarCliente.setText("Cambiar cliente");

      //this.btnCambiarCliente.addSelectionListener(new 1(this));
      this.btnCambiarCliente.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            changeCustomer();
         }
      });

      List<ReceiptType> receiptTypes = this.getAppConfigService().getActiveReceiptTypesForOrder(this.getOrder());
      int selectedIdx = 0;

      for(Iterator var12 = receiptTypes.iterator(); var12.hasNext(); ++selectedIdx) {
         ReceiptType rt = (ReceiptType)var12.next();
         this.comboReceiptTypes.add(rt.getName());
         if (this.getOrder() != null && this.getOrder().getReceiptType().getName().equalsIgnoreCase(rt.getName())) {
            this.comboReceiptTypes.select(selectedIdx);
         }
      }

      Label lblArtculos = new Label(container, 0);
      lblArtculos.setText("Artículos:");
      this.table = new Table(container, 67584);
      this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      if (this.getOrder().isInvoiceA()) {
         this.createTableColsForInvoiceA(this.table);
      } else {
         this.createTableColsForOtherInvoice(this.table);
      }

      Label lblObservaciones = new Label(container, 0);
      lblObservaciones.setText("Observaciones:");
      this.txtObservations = new Text(container, 2624);
      this.txtObservations.setTextLimit(255);
      GridData gd_txtObservations = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtObservations.heightHint = 50;
      this.txtObservations.setLayoutData(gd_txtObservations);
      this.btnCancelled = new Button(container, 32);
      this.btnCancelled.setText("Anulada");
      this.initOrder();
      return container;
   }

   private void changeCustomer() {
      ChangeCustomer dialog = new ChangeCustomer(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.getOrder().setCustomer(dialog.getCustomer());
         this.getOrderService().saveOrder(this.getOrder());
         this.txtCustomerName.setText(this.getOrder().getCustomer().getFullName());
      }

   }

   private void createTableColsForInvoiceA(Table table) {
      TableColumn tableColumn = new TableColumn(table, 0);
      tableColumn.setWidth(30);
      tableColumn.setText("#");
      TableColumn tblclmnCdigo = new TableColumn(table, 0);
      tblclmnCdigo.setWidth(88);
      tblclmnCdigo.setText("Código");
      TableColumn tblclmnDescripcin = new TableColumn(table, 0);
      tblclmnDescripcin.setWidth(148);
      tblclmnDescripcin.setText("Descripción");
      TableColumn tblclmnCantidad = new TableColumn(table, 0);
      tblclmnCantidad.setWidth(60);
      tblclmnCantidad.setText("Cantidad");
      TableColumn tblclmnUnitario = new TableColumn(table, 0);
      tblclmnUnitario.setWidth(60);
      tblclmnUnitario.setText("Unitario");
      TableColumn tblclmnIva = new TableColumn(table, 0);
      tblclmnIva.setWidth(60);
      tblclmnIva.setText("IVA");
      TableColumn tblclmnImporteIva = new TableColumn(table, 0);
      tblclmnImporteIva.setWidth(80);
      tblclmnImporteIva.setText("Importe IVA");
      TableColumn tblclmnImporte = new TableColumn(table, 0);
      tblclmnImporte.setWidth(70);
      tblclmnImporte.setText("Importe");
   }

   private void createTableColsForOtherInvoice(Composite container) {
      TableColumn tableColumn = new TableColumn(this.table, 0);
      tableColumn.setWidth(30);
      tableColumn.setText("#");
      TableColumn tblclmnCdigo = new TableColumn(this.table, 0);
      tblclmnCdigo.setWidth(100);
      tblclmnCdigo.setText("Código");
      TableColumn tblclmnDescripcin = new TableColumn(this.table, 0);
      tblclmnDescripcin.setWidth(250);
      tblclmnDescripcin.setText("Descripción");
      TableColumn tblclmnCantidad = new TableColumn(this.table, 0);
      tblclmnCantidad.setWidth(70);
      tblclmnCantidad.setText("Cantidad");
      TableColumn tblclmnUnitario = new TableColumn(this.table, 0);
      tblclmnUnitario.setWidth(70);
      tblclmnUnitario.setText("Unitario");
      TableColumn tblclmnImporte;
      if (this.getAppConfig().isResponsableInscripto()) {
         tblclmnImporte = new TableColumn(this.table, 0);
         tblclmnImporte.setWidth(55);
         tblclmnImporte.setText("IVA");
      }

      tblclmnImporte = new TableColumn(this.table, 0);
      tblclmnImporte.setWidth(70);
      tblclmnImporte.setText("Importe");
   }

   private void initOrder() {
      try {
         this.txtOrderId.setText(String.valueOf(this.getOrder().getId()));
         if (this.getOrder().isInvoiceA()) {
            this.initTableForInvoiceA();
         } else {
            this.initTableForOtherInvoice();
         }

         this.txtObservations.setText(String.valueOf(this.getOrder().getObservations()));
         this.txtObservations.setFocus();
         this.btnCancelled.setSelection(this.getOrder().isCancelled());
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void initTableForInvoiceA() {
      this.table.removeAll();
      List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this.getOrder());
      Iterator var3 = orderLines.iterator();

      while(var3.hasNext()) {
         OrderLine orderLine = (OrderLine)var3.next();
         this.addOrderLineToTableForInvoiceA(orderLine);
      }

   }

   private void addOrderLineToTableForInvoiceA(OrderLine orderLine) {
      TableItem item = new TableItem(this.table, 0);
      item.setText(0, String.valueOf(orderLine.getLineNumber()));
      if (orderLine.getProduct() != null) {
         item.setText(1, orderLine.getProduct().getBarCode());
         item.setText(2, orderLine.getProduct().getDescription());
      } else {
         item.setText(1, "D" + orderLine.getCategory().getNumberToDisplay());
         item.setText(2, orderLine.getCategory().getName());
      }

      item.setText(3, orderLine.getQtyToDisplay());
      item.setText(4, orderLine.getNetPriceToDisplay());
      item.setText(5, orderLine.getVatValueToDisplay());
      item.setText(6, orderLine.getVatAmountToDisplay());
      item.setText(7, orderLine.getNetSubtotalToDisplay());
   }

   private void initTableForOtherInvoice() {
      this.table.removeAll();
      List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this.getOrder());
      Iterator var3 = orderLines.iterator();

      while(var3.hasNext()) {
         OrderLine orderLine = (OrderLine)var3.next();
         this.addOrderLineToTableForOtherInvoice(orderLine);
      }

   }

   private void addOrderLineToTableForOtherInvoice(OrderLine orderLine) {
      TableItem item = new TableItem(this.table, 0);
      item.setText(0, String.valueOf(orderLine.getLineNumber()));
      if (orderLine.getProduct() != null) {
         item.setText(1, orderLine.getProduct().getBarCode());
         item.setText(2, orderLine.getProduct().getDescription());
      } else {
         item.setText(1, "C" + orderLine.getCategory().getId());
         item.setText(2, orderLine.getCategory().getName());
      }

      item.setText(3, orderLine.getQtyToDisplay());
      item.setText(4, orderLine.getPriceToDisplay());
      int idx = 5;
      if (this.getAppConfig().isResponsableInscripto()) {
         item.setText(idx, orderLine.getVatValueToDisplay());
         ++idx;
      }

      item.setText(idx, orderLine.getSubtotalToDisplay());
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            String receiptTypeName = this.comboReceiptTypes.getItem(this.comboReceiptTypes.getSelectionIndex());
            ReceiptType receiptType = this.getAppConfigService().getReceiptTypeByName(receiptTypeName);
            this.getOrder().setReceiptType(receiptType);
            this.getOrder().setObservations(this.txtObservations.getText().trim());
            this.getOrderService().saveOrder(this.getOrder());
            if (this.btnCancelled.getSelection()) {
               this.getOrderService().cancelOrder(this.getOrder().getId());
            } else {
               this.getOrderService().restoreOrder(this.getOrder().getId());
            }
         } catch (Exception var3) {
            logger.error("Error al editar venta: " + this.getOrder().getId());
            logger.error(var3.getMessage());
            ////logger.error(var3);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Detalle de la venta");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }
@Override
   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Guardar cambios", false);
      this.createButton(parent, 1, "Cerrar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(642, 432);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public Order getOrder() {
      return this.order;
   }

   public void setOrder(Order order) {
      this.order = order;
   }
}
