package com.facilvirtual.fvstoresdesk.ui.screens.purchases;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.model.PurchaseLine;
import com.facilvirtual.fvstoresdesk.model.Supplier;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;

public class PurchaseDetails extends AbstractFVDialog {
   private static final Logger logger = LoggerFactory.getLogger(PurchaseDetails.class);
   protected String action = "";
   protected Employee cashier;
   protected Table table;
   protected Text txtReceiptNumber;
   protected Text txtSupplierName;
   protected Text txtSupplierAddress;
   protected Text txtCashAmount;
   protected Text txtCardAmount;
   protected Text txtOnAccountAmount;
   protected Text txtSubtotal2;
   protected Text txtVat1;
   protected Text txtVat2;
   protected Text txtTotal;
   protected Text txtObservations;
   protected Text txtInnerTaxes;
   protected Text txtSubtotal1;
   protected Text txtTicketsAmount;
   protected Text txtCheckAmount;
   protected Supplier supplier;
   private Purchase purchase;
   private Text txtPurchaseDate;
   private Text txtReceiptType;
   private Button btnUpdatePrices;
   private Button btnUpdateStock;
   private Button btnCancelled;

   public PurchaseDetails(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      this.table = new Table(container, 67584);
      this.table.setBounds(10, 139, 614, 165);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tableColumn = new TableColumn(this.table, 0);
      tableColumn.setWidth(30);
      tableColumn.setText("#");
      TableColumn tblclmnCdigo = new TableColumn(this.table, 0);
      tblclmnCdigo.setWidth(73);
      tblclmnCdigo.setText("Código");
      TableColumn tblclmnCantidad = new TableColumn(this.table, 0);
      tblclmnCantidad.setWidth(62);
      tblclmnCantidad.setText("Cantidad");
      TableColumn tblclmnDescripcin = new TableColumn(this.table, 0);
      tblclmnDescripcin.setWidth(124);
      tblclmnDescripcin.setText("Descripción");
      TableColumn tblclmnPrecioSinIva = new TableColumn(this.table, 0);
      tblclmnPrecioSinIva.setWidth(82);
      tblclmnPrecioSinIva.setText("Precio sin IVA");
      TableColumn tblclmnIva = new TableColumn(this.table, 0);
      tblclmnIva.setWidth(53);
      tblclmnIva.setText("% IVA");
      TableColumn tblclmnPuConIva = new TableColumn(this.table, 0);
      tblclmnPuConIva.setWidth(77);
      tblclmnPuConIva.setText("P.U. con IVA");
      TableColumn tblclmnTotalSinImp = new TableColumn(this.table, 0);
      tblclmnTotalSinImp.setWidth(83);
      tblclmnTotalSinImp.setText("Total sin Imp.");
      Group grpDatosDelProveedor = new Group(container, 0);
      grpDatosDelProveedor.setText("Datos del proveedor");
      grpDatosDelProveedor.setBounds(10, 10, 333, 72);
      this.txtSupplierName = new Text(grpDatosDelProveedor, 2056);
      this.txtSupplierName.setBounds(10, 17, 313, 19);
      this.txtSupplierAddress = new Text(grpDatosDelProveedor, 2056);
      this.txtSupplierAddress.setBounds(10, 43, 313, 19);
      Group grpInformacinDelPago = new Group(container, 0);
      grpInformacinDelPago.setText("Información del pago");
      grpInformacinDelPago.setBounds(10, 395, 391, 59);
      Label lblEfectivo = new Label(grpInformacinDelPago, 0);
      lblEfectivo.setBounds(10, 18, 49, 13);
      lblEfectivo.setText("Efectivo");
      this.txtCashAmount = new Text(grpInformacinDelPago, 2056);
      this.txtCashAmount.setBounds(10, 34, 70, 19);
      Label lblCheque = new Label(grpInformacinDelPago, 0);
      lblCheque.setBounds(162, 18, 49, 13);
      lblCheque.setText("Cheque");
      this.txtCardAmount = new Text(grpInformacinDelPago, 2056);
      this.txtCardAmount.setBounds(86, 34, 70, 19);
      this.txtOnAccountAmount = new Text(grpInformacinDelPago, 2056);
      this.txtOnAccountAmount.setBounds(314, 34, 70, 19);
      Label lblCuentaCorriente = new Label(grpInformacinDelPago, 0);
      lblCuentaCorriente.setBounds(314, 18, 70, 13);
      lblCuentaCorriente.setText("Cta. Cte.");
      this.txtTicketsAmount = new Text(grpInformacinDelPago, 2056);
      this.txtTicketsAmount.setBounds(238, 34, 70, 19);
      this.txtTicketsAmount.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            format2Decimals(txtTicketsAmount);
         }
      });
      Label lblTarjetas = new Label(grpInformacinDelPago, 0);
      lblTarjetas.setBounds(238, 18, 56, 13);
      lblTarjetas.setText("Tickets");
      this.txtCheckAmount = new Text(grpInformacinDelPago, 2056);
      this.txtCheckAmount.setBounds(162, 34, 70, 19);
      Label lblNewLabel_2 = new Label(grpInformacinDelPago, 0);
      lblNewLabel_2.setBounds(86, 18, 49, 13);
      lblNewLabel_2.setText("Tarjeta");
      Label lblCdigoDeBarras = new Label(container, 0);
      lblCdigoDeBarras.setBounds(10, 120, 105, 13);
      lblCdigoDeBarras.setText("Artículos");
      this.txtReceiptNumber = new Text(container, 2056);
      this.txtReceiptNumber.setTextLimit(60);
      this.txtReceiptNumber.setBounds(466, 37, 158, 19);
      Label lblNroFactura = new Label(container, 0);
      lblNroFactura.setBounds(358, 40, 96, 13);
      lblNroFactura.setText("Número");
      Label lblFecha = new Label(container, 0);
      lblFecha.setBounds(358, 67, 85, 13);
      lblFecha.setText("Fecha");
      Label lblNewLabel = new Label(container, 0);
      lblNewLabel.setBounds(422, 313, 115, 13);
      lblNewLabel.setText("Subtotal");
      Label lblIva = new Label(container, 0);
      lblIva.setBounds(422, 385, 115, 13);
      lblIva.setText("IVA 21%");
      this.txtSubtotal2 = new Text(container, 2056);
      this.txtSubtotal2.setBounds(548, 358, 76, 19);
      Label lblIva_1 = new Label(container, 0);
      lblIva_1.setBounds(422, 409, 115, 13);
      lblIva_1.setText("IVA 10,5%");
      this.txtVat1 = new Text(container, 2056);
      this.txtVat1.setBounds(548, 382, 76, 19);
      Label lblNewLabel_1 = new Label(container, 0);
      lblNewLabel_1.setBounds(422, 361, 115, 13);
      lblNewLabel_1.setText("Subtotal");
      this.txtVat2 = new Text(container, 2056);
      this.txtVat2.setBounds(548, 406, 76, 19);
      Label lblDescuentorecargo = new Label(container, 0);
      lblDescuentorecargo.setBounds(422, 337, 115, 15);
      lblDescuentorecargo.setText("Impuestos internos");
      Label lblTotal = new Label(container, 0);
      lblTotal.setBounds(422, 433, 115, 13);
      lblTotal.setText("Total");
      this.txtTotal = new Text(container, 2056);
      this.txtTotal.setBounds(548, 430, 76, 19);
      Label lblObservaciones = new Label(container, 0);
      lblObservaciones.setBounds(10, 474, 76, 13);
      lblObservaciones.setText("Observaciones");
      this.txtObservations = new Text(container, 2560);
      this.txtObservations.setBounds(92, 471, 532, 42);
      this.txtInnerTaxes = new Text(container, 2056);
      this.txtInnerTaxes.addFocusListener(new FocusAdapter() {
         @Override
         public void focusLost(FocusEvent e) {
            format2Decimals(txtInnerTaxes);
         }
      });
      this.txtInnerTaxes.setBounds(548, 334, 76, 19);
      this.txtSubtotal1 = new Text(container, 2056);
      this.txtSubtotal1.setBounds(548, 310, 76, 19);
      Label lblTipoComprobante = new Label(container, 0);
      lblTipoComprobante.setBounds(358, 13, 105, 19);
      lblTipoComprobante.setText("Tipo comprobante");
      Group grpOpciones = new Group(container, 0);
      grpOpciones.setText("Opciones");
      grpOpciones.setBounds(10, 341, 391, 43);
      this.btnUpdatePrices = new Button(grpOpciones, 32);
      this.btnUpdatePrices.setBounds(10, 20, 112, 16);
      this.btnUpdatePrices.setText("Actualizar precios");
      this.btnUpdatePrices.setSelection(true);
      this.btnUpdatePrices.setEnabled(false);
      this.btnUpdateStock = new Button(grpOpciones, 32);
      this.btnUpdateStock.setBounds(146, 20, 102, 16);
      this.btnUpdateStock.setText("Actualizar stock");
      this.btnUpdateStock.setSelection(true);
      this.btnUpdateStock.setEnabled(false);
      this.txtPurchaseDate = new Text(container, 2056);
      this.txtPurchaseDate.setBounds(466, 64, 158, 19);
      this.txtReceiptType = new Text(container, 2056);
      this.txtReceiptType.setBounds(466, 10, 158, 19);
      this.btnCancelled = new Button(container, 32);
      this.btnCancelled.setBounds(10, 530, 85, 16);
      this.btnCancelled.setText("Anulada");
      this.initPurchase();
      return container;
   }

   private void initPurchase() {
      try {
         List<PurchaseLine> purchaseLines = this.getPurchaseService().getPurchaseLinesForPurchase(this.getPurchase());
         this.getPurchase().setPurchaseLines(purchaseLines);
         this.txtSupplierName.setText(this.getPurchase().getSupplier().getCompanyName());
         this.txtSupplierAddress.setText(this.getPurchase().getSupplier().getFullAddress());
         this.txtReceiptType.setText(this.getPurchase().getReceiptType().getName());
         this.txtReceiptNumber.setText(this.getPurchase().getReceiptNumber());
         this.txtPurchaseDate.setText(this.getPurchase().getPurchaseDateToDisplay());
         this.initTable();
         this.txtSubtotal1.setText(this.getPurchase().getNetSubtotalToDisplay());
         this.txtInnerTaxes.setText(this.getPurchase().getInnerTaxesToDisplay());
         this.txtSubtotal2.setText(this.getPurchase().getNetSubtotal2ToDisplay());
         this.txtVat1.setText(this.getPurchase().getStandardVatTotalToDisplay());
         this.txtVat2.setText(this.getPurchase().getReducedVatTotalToDisplay());
         this.txtTotal.setText(this.getPurchase().getTotalToDisplay());
         this.btnUpdatePrices.setSelection(this.getPurchase().isUpdatePrices());
         this.btnUpdateStock.setSelection(this.getPurchase().isUpdateStock());
         this.txtCashAmount.setText(this.getPurchase().getCashAmountToDisplay());
         this.txtCardAmount.setText(this.getPurchase().getCardAmountToDisplay());
         this.txtCheckAmount.setText(this.getPurchase().getCheckAmountToDisplay());
         this.txtTicketsAmount.setText(this.getPurchase().getTicketsAmountToDisplay());
         this.txtOnAccountAmount.setText(this.getPurchase().getOnAccountAmountToDisplay());
         this.txtObservations.setText(this.getPurchase().getObservations());
         this.btnCancelled.setSelection(this.getPurchase().isCancelled());
      } catch (Exception var2) {
         logger.error("Error inicializando detalle de compra");
         var2.printStackTrace();
      }

   }

   private void initTable() {
      this.table.removeAll();
      int colIdx;
      for(Iterator var3 = this.getPurchase().getPurchaseLines().iterator(); var3.hasNext(); ++colIdx) {
         PurchaseLine purchaseLine = (PurchaseLine)var3.next();
         colIdx = 0;
         TableItem item = new TableItem(this.table, 0);
         item.setText(colIdx, String.valueOf(purchaseLine.getLineNumber()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchaseLine.getProduct().getBarCode()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchaseLine.getQtyToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchaseLine.getProduct().getDescription()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchaseLine.getNetPriceToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchaseLine.getVatValueToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchaseLine.getPriceToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(purchaseLine.getNetSubtotalToDisplay()));
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            if (!this.getPurchase().getObservations().equalsIgnoreCase(this.txtObservations.getText().trim())) {
               this.getPurchase().setObservations(this.txtObservations.getText().trim());
               this.getPurchaseService().savePurchase(this.getPurchase());
            }

            if (this.btnCancelled.getSelection()) {
               this.getPurchaseService().cancelPurchase(this.getPurchase().getId());
            } else {
               this.getPurchaseService().restorePurchase(this.getPurchase().getId());
            }
         } catch (Exception var2) {
            logger.error("Error al guardar obs. en detalle de compra");
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Detalle de la compra");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Guardar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(640, 650);
   }

   public Purchase getPurchase() {
      return this.purchase;
   }

   public void setPurchase(Purchase purchase) {
      this.purchase = purchase;
   }

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }
}
