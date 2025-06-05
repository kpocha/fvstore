package com.facilvirtual.fvstoresdesk.ui.screens.purchases;

import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.Purchase;
import com.facilvirtual.fvstoresdesk.model.PurchaseLine;
import com.facilvirtual.fvstoresdesk.model.ReceiptType;
import com.facilvirtual.fvstoresdesk.model.Supplier;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.Supplier.ChangeSupplier;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister.SearchProduct;

public class AddNewPurchase extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("AddNewPurchase");
   private String action = "";
   private Employee cashier;
   private Purchase currentPurchase;
   private Table table;
   private Text txtBarCode;
   private Text txtReceiptNumber;
   private Text txtSupplierName;
   private Text txtSupplierAddress;
   private Text txtCashAmount;
   private Text txtCardAmount;
   private Text txtOnAccountAmount;
   private Text txtSubtotal2;
   private Text txtVat1;
   private Text txtVat2;
   private Text txtTotal;
   private Text txtObservations;
   private Text txtInnerTaxes;
   private Text txtSubtotal1;
   private Text txtTicketsAmount;
   private Text txtCheckAmount;
   private Combo comboReceiptTypes;
   private DateTime dateTimePurchase;
   private Supplier supplier;
   private Button btnUpdatePrices;
   private Button btnUpdateStock;

   public AddNewPurchase(Shell parentShell) {
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
      this.txtBarCode = new Text(container, 2048);
      this.txtBarCode.setBounds(10, 112, 188, 21);
     // this.txtBarCode.addTraverseListener(new 1(this));
      Group grpDatosDelProveedor = new Group(container, 0);
      grpDatosDelProveedor.setText("Datos del proveedor");
      grpDatosDelProveedor.setBounds(10, 10, 343, 72);
      Button btnCambiarProveedor = new Button(grpDatosDelProveedor, 0);
     // btnCambiarProveedor.addSelectionListener(new 2(this));
      btnCambiarProveedor.setBounds(222, 15, 111, 23);
      btnCambiarProveedor.setText("Cambiar proveedor");
      this.txtSupplierName = new Text(grpDatosDelProveedor, 2056);
      this.txtSupplierName.setBounds(10, 17, 203, 19);
      this.txtSupplierAddress = new Text(grpDatosDelProveedor, 2056);
      this.txtSupplierAddress.setBounds(10, 43, 203, 19);
      Group grpInformacinDelPago = new Group(container, 0);
      grpInformacinDelPago.setText("Información del pago");
      grpInformacinDelPago.setBounds(10, 395, 391, 59);
      Label lblEfectivo = new Label(grpInformacinDelPago, 0);
      lblEfectivo.setBounds(10, 18, 49, 13);
      lblEfectivo.setText("Efectivo");
      this.txtCashAmount = new Text(grpInformacinDelPago, 2048);
      this.txtCashAmount.setBounds(10, 34, 70, 19);
      // this.txtCashAmount.addKeyListener(new 3(this));
      // this.txtCashAmount.addFocusListener(new 4(this));
      Label lblCheque = new Label(grpInformacinDelPago, 0);
      lblCheque.setBounds(162, 18, 49, 13);
      lblCheque.setText("Cheque");
      this.txtCardAmount = new Text(grpInformacinDelPago, 2048);
      this.txtCardAmount.setBounds(86, 34, 70, 19);
      // this.txtCardAmount.addKeyListener(new 5(this));
      // this.txtCardAmount.addFocusListener(new 6(this));
      this.txtOnAccountAmount = new Text(grpInformacinDelPago, 2056);
      this.txtOnAccountAmount.setBounds(314, 34, 70, 19);
      Label lblCuentaCorriente = new Label(grpInformacinDelPago, 0);
      lblCuentaCorriente.setBounds(314, 18, 70, 13);
      lblCuentaCorriente.setText("Cta. Cte.");
      this.txtTicketsAmount = new Text(grpInformacinDelPago, 2048);
      this.txtTicketsAmount.setBounds(238, 34, 70, 19);
      // this.txtTicketsAmount.addKeyListener(new 7(this));
      // this.txtTicketsAmount.addFocusListener(new 8(this));
      Label lblTarjetas = new Label(grpInformacinDelPago, 0);
      lblTarjetas.setBounds(238, 18, 56, 13);
      lblTarjetas.setText("Tickets");
      this.txtCheckAmount = new Text(grpInformacinDelPago, 2048);
      this.txtCheckAmount.setBounds(162, 34, 70, 19);
      // this.txtCheckAmount.addKeyListener(new 9(this));
      // this.txtCheckAmount.addFocusListener(new 10(this));
      Label lblNewLabel_2 = new Label(grpInformacinDelPago, 0);
      lblNewLabel_2.setBounds(86, 18, 49, 13);
      lblNewLabel_2.setText("Tarjeta");
      Button btnQuitar = new Button(container, 0);
      //btnQuitar.addSelectionListener(new 11(this));
      btnQuitar.setBounds(10, 310, 68, 23);
      btnQuitar.setText("Quitar");
      Label lblCdigoDeBarras = new Label(container, 0);
      lblCdigoDeBarras.setBounds(10, 92, 105, 15);
      lblCdigoDeBarras.setText("Código de barras");
      Button btnSearchProduct = new Button(container, 0);
     // btnSearchProduct.addSelectionListener(new 12(this));
      btnSearchProduct.setBounds(232, 109, 111, 23);
      btnSearchProduct.setText("Buscar artículo");
      this.txtReceiptNumber = new Text(container, 2048);
      this.txtReceiptNumber.setTextLimit(60);
      this.txtReceiptNumber.setBounds(466, 37, 158, 21);
      Label lblNroFactura = new Label(container, 0);
      lblNroFactura.setBounds(358, 40, 100, 13);
      lblNroFactura.setText("Número");
      Label lblFecha = new Label(container, 0);
      lblFecha.setBounds(358, 67, 100, 13);
      lblFecha.setText("Fecha");
      this.dateTimePurchase = new DateTime(container, 2048);
      this.dateTimePurchase.setBounds(466, 64, 158, 21);
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
      this.txtInnerTaxes = new Text(container, 2048);
      //this.txtInnerTaxes.addFocusListener(new 13(this));
      this.txtInnerTaxes.setBounds(548, 334, 76, 19);
     // this.txtInnerTaxes.addKeyListener(new 14(this));
      this.txtSubtotal1 = new Text(container, 2056);
      this.txtSubtotal1.setBounds(548, 310, 76, 19);
      Label lblTipoComprobante = new Label(container, 0);
      lblTipoComprobante.setBounds(358, 13, 105, 19);
      lblTipoComprobante.setText("Tipo comprobante");
      this.comboReceiptTypes = new Combo(container, 8);
      this.comboReceiptTypes.setBounds(466, 10, 158, 21);
      List<ReceiptType> receiptTypes = this.getAppConfigService().getActiveReceiptTypesForPurchase();
      int selectedIdx = 0;

      for(Iterator var35 = receiptTypes.iterator(); var35.hasNext(); ++selectedIdx) {
         ReceiptType rt = (ReceiptType)var35.next();
         this.comboReceiptTypes.add(rt.getName());
         if ("Factura A".equalsIgnoreCase(rt.getName())) {
            this.comboReceiptTypes.select(selectedIdx);
         }
      }

      Group grpOpciones = new Group(container, 0);
      grpOpciones.setText("Opciones");
      grpOpciones.setBounds(10, 341, 391, 43);
      this.btnUpdatePrices = new Button(grpOpciones, 32);
      this.btnUpdatePrices.setBounds(10, 20, 112, 16);
      this.btnUpdatePrices.setText("Actualizar precios");
      this.btnUpdatePrices.setSelection(true);
      this.btnUpdateStock = new Button(grpOpciones, 32);
      this.btnUpdateStock.setBounds(146, 20, 102, 16);
      this.btnUpdateStock.setText("Actualizar stock");
      this.btnUpdateStock.setSelection(true);
      this.initNewPurchase();
      return container;
   }

   private void searchProduct() {
      SearchProduct dialog = new SearchProduct(this.getShell());
      dialog.setPriceList(this.getDefaultPriceList());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.addProductToPurchase(dialog.getProduct().getBarCode());
         this.updateTotals();
         this.updateOnAccountAmount();
      }

   }

   private void removeProduct() {
      int idx = this.table.getSelectionIndex();
      if (idx >= 0) {
         this.getCurrentPurchase().removePurchaseLine(idx);
         this.updateTable();
         this.updateTotals();
         this.updateOnAccountAmount();
      } else {
         this.alert("Selecciona un artículo");
      }

   }

   private void initNewPurchase() {
      Purchase purchase = new Purchase();
      purchase.setPosNumber(this.getAppConfig().getCompanyPosNumber());
      purchase.setCashier(this.getCashier());
      purchase.setCashNumber(this.getWorkstationConfig().getCashNumber());
      this.setCurrentPurchase(purchase);
      this.cleanBarCode();
   }

   private void addProductToPurchase(String barCode) {
      Product product = this.getProductService().getProductByBarCode(barCode);
      if (product != null) {
         if (product.isDiscontinued() && (!this.getWorkstationConfig().isTrialMode() || this.getWorkstationConfig().isTrialMode() && this.getWorkstationConfig().getTrialMaxProductsQty() > this.getProductService().getActiveProductsQty())) {
            product.setDiscontinued(false);
            product.setInWeb(true);
            this.getProductService().saveProduct(product);
         }

         AddProductToPurchase dialog = new AddProductToPurchase(this.getShell());
         dialog.setProduct(product);
         dialog.setPurchase(this.getCurrentPurchase());
         dialog.open();
         if ("OK".equalsIgnoreCase(dialog.getAction())) {
            this.updateTable();
            this.updateTotals();
            this.cleanBarCode();
         }
      } else {
         this.alert("No se encontró el artículo");
         this.cleanBarCode();
      }

   }

   private void updateTable() {
      this.table.removeAll();
      

      int colIdx;
      for(Iterator var3 = this.getCurrentPurchase().getPurchaseLines().iterator(); var3.hasNext(); ++colIdx) {
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

   private double getCashAmount() {
      double cashAmount = 0.0;

      try {
         cashAmount = Double.parseDouble(this.txtCashAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return cashAmount;
   }

   private double getCardAmount() {
      double cardAmount = 0.0;

      try {
         cardAmount = Double.parseDouble(this.txtCardAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return cardAmount;
   }

   private double getCheckAmount() {
      double checkAmount = 0.0;

      try {
         checkAmount = Double.parseDouble(this.txtCheckAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return checkAmount;
   }

   private double getTicketsAmount() {
      double ticketsAmount = 0.0;

      try {
         ticketsAmount = Double.parseDouble(this.txtTicketsAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return ticketsAmount;
   }

   private double getTotalTendered() {
      return this.getCashAmount() + this.getCardAmount() + this.getCheckAmount() + this.getTicketsAmount();
   }

   private void updateOnAccountAmount() {
      double onAccountAmount = this.getCurrentPurchase().getTotal() - this.getTotalTendered();
      if (onAccountAmount > 0.0) {
         this.txtOnAccountAmount.setText(this.format2Decimals(onAccountAmount));
      } else {
         this.txtOnAccountAmount.setText("0,00");
      }

   }

   private void updateTotals() {
      this.txtSubtotal1.setText(this.getCurrentPurchase().getNetSubtotalToDisplay());
      Double innerTaxes = this.getDoubleValueFromText(this.txtInnerTaxes);
      double subtotal2 = this.getCurrentPurchase().getNetSubtotal();
      if (innerTaxes != null) {
         subtotal2 += innerTaxes;
         this.getCurrentPurchase().setInnerTaxes(innerTaxes);
         this.getCurrentPurchase().setTotal(this.getCurrentPurchase().calculateTotal());
      }

      this.txtSubtotal2.setText(this.format2Decimals(subtotal2));
      this.txtVat1.setText(this.getCurrentPurchase().getStandardVatTotalToDisplay());
      this.txtVat2.setText(this.getCurrentPurchase().getReducedVatTotalToDisplay());
      this.txtTotal.setText(this.getCurrentPurchase().getTotalToDisplay());
   }

   private void cleanBarCode() {
      this.txtBarCode.setText("");
      this.txtBarCode.setFocus();
   }

   private void changeSupplier() {
      ChangeSupplier dialog = new ChangeSupplier(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.txtSupplierName.setText(dialog.getSupplier().getCompanyName());
         this.txtSupplierAddress.setText(dialog.getSupplier().getFullAddress());
         this.setSupplier(dialog.getSupplier());
      }

   }

   private ReceiptType getReceiptType() {
      ReceiptType receiptType = null;
      receiptType = this.getAppConfigService().getReceiptTypeByName(this.comboReceiptTypes.getText());
      return receiptType;
   }

   private void processDialog() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            if (this.getCurrentPurchase().getTotal() > 0.0) {
               Date creationDate = new Date();
               Date purchaseDate = this.buildDateFromInput(this.dateTimePurchase);
               this.getCurrentPurchase().setStatus("COMPLETED");
               this.getCurrentPurchase().setCreationDate(creationDate);
               if (!DateUtils.isSameDay(creationDate, purchaseDate)) {
                  this.getCurrentPurchase().setPurchaseDate(purchaseDate);
                  this.getCurrentPurchase().setCustomPurchaseDate(true);
               } else {
                  this.getCurrentPurchase().setPurchaseDate(creationDate);
                  this.getCurrentPurchase().setCustomPurchaseDate(false);
               }

               this.getCurrentPurchase().setSupplier(this.getSupplier());
               this.getCurrentPurchase().setReceiptType(this.getReceiptType());
               this.getCurrentPurchase().setReceiptNumber(this.txtReceiptNumber.getText().trim());
               this.getCurrentPurchase().setObservations(this.txtObservations.getText().trim());
               this.getCurrentPurchase().setUpdatePrices(this.btnUpdatePrices.getSelection());
               this.getCurrentPurchase().setUpdateStock(this.btnUpdateStock.getSelection());
               Double cashAmount = this.getDoubleValueFromText(this.txtCashAmount);
               if (cashAmount != null) {
                  this.getCurrentPurchase().setCashAmount(cashAmount);
               }

               Double cardAmount = this.getDoubleValueFromText(this.txtCardAmount);
               if (cardAmount != null) {
                  this.getCurrentPurchase().setCardAmount(cardAmount);
               }

               Double checkAmount = this.getDoubleValueFromText(this.txtCheckAmount);
               if (checkAmount != null) {
                  this.getCurrentPurchase().setCheckAmount(checkAmount);
               }

               Double ticketsAmount = this.getDoubleValueFromText(this.txtTicketsAmount);
               if (ticketsAmount != null) {
                  this.getCurrentPurchase().setTicketsAmount(ticketsAmount);
               }

               Double onAccountAmount = this.getDoubleValueFromText(this.txtOnAccountAmount);
               if (onAccountAmount != null) {
                  this.getCurrentPurchase().setOnAccountAmount(onAccountAmount);
               }

               this.getPurchaseService().savePurchase(this.getCurrentPurchase());
               if (this.getCurrentPurchase().getCashAmount() > 0.0) {
                  this.getCashService().saveNewCashOperationForPurchase(this.getCurrentPurchase());
                  if (this.getCashService().mustUpdateCashAmount(this.getWorkstationConfig(), this.getCurrentPurchase().getPurchaseDate())) {
                     this.getAppConfigService().decCashAmount(this.getWorkstationConfig(), this.getCurrentPurchase().getCashAmount());
                  }
               }

               if (this.getCurrentPurchase().getOnAccountAmount() > 0.0) {
                  this.getSupplierService().saveNewSupplierOnAccountOperationForPurchase(this.getCurrentPurchase());
               }

               this.getProductService().updateLastPurchaseDateForPurchase(this.getCurrentPurchase());
               if (this.getCurrentPurchase().isUpdateStock()) {
                  this.getProductService().updateStockForPurchase(this.getCurrentPurchase());
               }

               if (this.getCurrentPurchase().isUpdatePrices()) {
                  this.getProductService().updatePricesForPurchase(this.getCurrentPurchase());
               }
            }
         } catch (Exception var8) {
            logger.error("Error al guardar nueva compra");
            logger.error(var8.getMessage());
            //logger.error(var8);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if (this.getSupplier() == null) {
         valid = false;
         this.alert("Selecciona un proveedor");
      }

      if (valid && "".equalsIgnoreCase(this.txtReceiptNumber.getText().trim())) {
         valid = false;
         this.alert("Ingresa el número de comprobante");
      }

      if (valid && this.getCurrentPurchase().getPurchaseLines().size() == 0) {
         valid = false;
         this.alert("Agrega artículos a la compra");
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nueva compra");
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
      return new Point(640, 600);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   public Purchase getCurrentPurchase() {
      return this.currentPurchase;
   }

   public void setCurrentPurchase(Purchase currentPurchase) {
      this.currentPurchase = currentPurchase;
   }

   public Supplier getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
   }
}
