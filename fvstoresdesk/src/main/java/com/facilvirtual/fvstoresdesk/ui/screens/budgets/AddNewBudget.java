package com.facilvirtual.fvstoresdesk.ui.screens.budgets;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.wb.swt.SWTResourceManager;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.Budget;
import com.facilvirtual.fvstoresdesk.model.BudgetLine;
import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.SaleCondition;
import com.facilvirtual.fvstoresdesk.model.VatCondition;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister.SearchProduct;
import com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.Customer.ChangeCustomer;

public class AddNewBudget extends AbstractFVDialog {
   private static final Logger logger = LoggerFactory.getLogger("AddNewBudget");
   private String action = "";
   private Employee cashier;
   private Budget currentBudget;
   private Table table;
   private Text txtBarCode;
   private Text txtBudgetNumber;
   private Text txtCustomerName;
   private Text txtCustomerAddress;
   private Text txtSubtotal;
   private Text txtVat1;
   private Text txtVat2;
   private Text txtTotal;
   private Text txtObservations;
   private DateTime dateTimeBudget;
   private Customer customer;
   private Combo comboVatCondition;
   private Combo comboSaleCondition;

   public AddNewBudget(Shell parentShell) {
      super(parentShell);
   }

   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      this.table = new Table(container, 67584);
      this.table.setBounds(10, 177, 614, 177);
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
      tblclmnPrecioSinIva.setWidth(89);
      tblclmnPrecioSinIva.setText("Precio sin IVA");
      TableColumn tblclmnIva = new TableColumn(this.table, 0);
      tblclmnIva.setWidth(53);
      tblclmnIva.setText("% IVA");
      TableColumn tblclmnPuConIva = new TableColumn(this.table, 0);
      tblclmnPuConIva.setWidth(77);
      tblclmnPuConIva.setText("P.U. con IVA");
      TableColumn tblclmnTotalSinImp = new TableColumn(this.table, 0);
      tblclmnTotalSinImp.setWidth(93);
      tblclmnTotalSinImp.setText("Total sin Imp.");
      this.txtBarCode = new Text(container, 2048);
      this.txtBarCode.setBounds(10, 150, 188, 21);
      this.txtBarCode.addTraverseListener(new TraverseListener() {
        public void keyTraversed(TraverseEvent arg0) {
            if (arg0.detail == SWT.TRAVERSE_RETURN) {
                addProductToBudget(txtBarCode.getText().trim());
            }
        }
     });
      Group grpDatosDelProveedor = new Group(container, 0);
      grpDatosDelProveedor.setText("Datos del cliente");
      grpDatosDelProveedor.setBounds(10, 10, 333, 72);

     Button btnChangeCustomer = new Button(grpDatosDelProveedor, 0);
      btnChangeCustomer.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            changeCustomer();
         }
      });
      
      btnChangeCustomer.setBounds(222, 15, 104, 23);
      btnChangeCustomer.setText("Cambiar cliente");
      this.txtCustomerName = new Text(grpDatosDelProveedor, 2056);
      this.txtCustomerName.setBounds(10, 17, 203, 19);
      this.txtCustomerAddress = new Text(grpDatosDelProveedor, 2056);
      this.txtCustomerAddress.setBounds(10, 43, 203, 19);
      Button btnQuitar = new Button(container, 0);
      btnQuitar.addSelectionListener(new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
           removeProduct();
        }
     });
      btnQuitar.setBounds(10, 358, 68, 23);
      btnQuitar.setText("Quitar");
      Label lblCdigoDeBarras = new Label(container, 0);
      lblCdigoDeBarras.setBounds(10, 131, 105, 16);
      lblCdigoDeBarras.setText("Código de barras");
      Button btnSearchProduct = new Button(container, 0);

      btnSearchProduct.addSelectionListener(new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
           searchProduct();
        }
     });
      
      btnSearchProduct.setBounds(238, 148, 105, 23);
      btnSearchProduct.setText("Buscar artículo");
      this.txtBudgetNumber = new Text(container, 2056);
      this.txtBudgetNumber.setEditable(true);
      this.txtBudgetNumber.setTextLimit(60);
      this.txtBudgetNumber.setBounds(466, 35, 158, 21);
      Label lblNroFactura = new Label(container, 0);
      lblNroFactura.setBounds(364, 38, 96, 13);
      lblNroFactura.setText("Número");
      Label lblFecha = new Label(container, 0);
      lblFecha.setBounds(364, 65, 85, 13);
      lblFecha.setText("Fecha");
      this.dateTimeBudget = new DateTime(container, 2048);
      this.dateTimeBudget.setBounds(466, 62, 158, 21);
      Label lblIva = new Label(container, 0);
      lblIva.setBounds(422, 385, 115, 13);
      lblIva.setText("IVA 21%");
      this.txtSubtotal = new Text(container, 2056);
      this.txtSubtotal.setBounds(548, 358, 76, 19);
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
      Label lblTotal = new Label(container, 0);
      lblTotal.setBounds(422, 433, 115, 13);
      lblTotal.setText("Total");
      this.txtTotal = new Text(container, 2056);
      this.txtTotal.setBounds(548, 430, 76, 19);
      Label lblObservaciones = new Label(container, 0);
      lblObservaciones.setBounds(10, 392, 76, 13);
      lblObservaciones.setText("Observaciones");
      this.txtObservations = new Text(container, 2560);
      this.txtObservations.setBounds(10, 409, 391, 40);
      Label lblNewLabel_3 = new Label(container, 0);
      lblNewLabel_3.setFont(SWTResourceManager.getFont("Arial", 12, 1));
      lblNewLabel_3.setBounds(362, 8, 145, 21);
      lblNewLabel_3.setText("PRESUPUESTO");
      Label lblCondIva = new Label(container, 0);
      lblCondIva.setBounds(10, 104, 55, 13);
      lblCondIva.setText("Cond. IVA");
      this.comboVatCondition = new Combo(container, 8);
      this.comboVatCondition.setBounds(70, 97, 158, 21);
      Label lblCondVenta = new Label(container, 0);
      lblCondVenta.setBounds(245, 104, 70, 13);
      lblCondVenta.setText("Cond. venta");
      this.comboSaleCondition = new Combo(container, 8);
      this.comboSaleCondition.setBounds(315, 97, 151, 21);
      Label label = new Label(container, 258);
      label.setBounds(10, 124, 614, 2);
      Label label_1 = new Label(container, 258);
      label_1.setBounds(10, 91, 614, 2);
      this.initNewBudget();
      this.initVatConditions();
      this.initSaleConditions();
      return container;
   }

   private void initVatConditions() {
      List<VatCondition> vatConditions = this.getOrderService().getAllVatConditions();
      int selectedIdx = 0;

      for(Iterator var4 = vatConditions.iterator(); var4.hasNext(); ++selectedIdx) {
         VatCondition vc = (VatCondition)var4.next();
         this.comboVatCondition.add(vc.getName());
         if ("Consumidor Final".equalsIgnoreCase(vc.getName())) {
            this.comboVatCondition.select(selectedIdx);
         }
      }

   }

   private void initSaleConditions() {
      List<SaleCondition> saleConditions = this.getOrderService().getAllSaleConditions();
      int selectedIdx = 0;

      for(Iterator var4 = saleConditions.iterator(); var4.hasNext(); ++selectedIdx) {
         SaleCondition saleCond = (SaleCondition)var4.next();
         this.comboSaleCondition.add(saleCond.getName());
         if ("Contado".equalsIgnoreCase(saleCond.getName())) {
            this.comboSaleCondition.select(selectedIdx);
         }
      }

   }

   private void searchProduct() {
      SearchProduct dialog = new SearchProduct(this.getShell());
      dialog.setPriceList(this.getDefaultPriceList());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.addProductToBudget(dialog.getProduct().getBarCode());
         this.updateTotals();
      }

   }

   private void removeProduct() {
      int idx = this.table.getSelectionIndex();
      if (idx >= 0) {
         this.getCurrentBudget().removeBudgetLine(idx);
         this.updateTable();
         this.updateTotals();
      } else {
         this.alert("Selecciona un artículo");
      }

   }

   private void initNewBudget() {
      Budget budget = new Budget();
      budget.setPosNumber(this.getAppConfig().getCompanyPosNumber());
      budget.setCashier(this.getCashier());
      budget.setCashNumber(this.getWorkstationConfig().getCashNumber());
      this.setCurrentBudget(budget);
      this.txtBudgetNumber.setText(String.valueOf(this.getAppConfig().getBudgetStartNumber()));
      this.cleanBarCode();
   }

   private void addProductToBudget(String barCode) {
      Product product = this.getProductService().getProductByBarCode(barCode);
      if (product != null) {
         if (product.isDiscontinued() && (!this.getWorkstationConfig().isTrialMode() || this.getWorkstationConfig().isTrialMode() && this.getWorkstationConfig().getTrialMaxProductsQty() > this.getProductService().getActiveProductsQty())) {
            product.setDiscontinued(false);
            product.setInWeb(true);
            this.getProductService().saveProduct(product);
         }

         AddProductToBudget dialog = new AddProductToBudget(this.getShell());
         dialog.setProduct(product);
         dialog.setBudget(this.getCurrentBudget());
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
     // int colIdx = false;

      int colIdx;
      for(Iterator var3 = this.getCurrentBudget().getBudgetLines().iterator(); var3.hasNext(); ++colIdx) {
         BudgetLine budgetLine = (BudgetLine)var3.next();
         colIdx = 0;
         TableItem item = new TableItem(this.table, 0);
         item.setText(colIdx, String.valueOf(budgetLine.getLineNumber()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budgetLine.getProduct().getBarCode()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budgetLine.getQtyToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budgetLine.getProduct().getDescription()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budgetLine.getNetPriceToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budgetLine.getVatValueToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budgetLine.getPriceToDisplay()));
         ++colIdx;
         item.setText(colIdx, String.valueOf(budgetLine.getNetSubtotalToDisplay()));
      }

   }

   private void updateTotals() {
      this.txtSubtotal.setText(this.format2Decimals(this.getCurrentBudget().getNetSubtotal()));
      this.txtVat1.setText(this.getCurrentBudget().getStandardVatTotalToDisplay());
      this.txtVat2.setText(this.getCurrentBudget().getReducedVatTotalToDisplay());
      this.txtTotal.setText(this.getCurrentBudget().getTotalToDisplay());
   }

   private void cleanBarCode() {
      this.txtBarCode.setText("");
      this.txtBarCode.setFocus();
   }

   private void changeCustomer() {
      ChangeCustomer dialog = new ChangeCustomer(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.txtCustomerName.setText(dialog.getCustomer().getFullName());
         this.txtCustomerAddress.setText(dialog.getCustomer().getFullAddress());
         this.setCustomer(dialog.getCustomer());
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            if (this.getCurrentBudget().getTotal() > 0.0) {
               Date creationDate = new Date();
               this.getCurrentBudget().setCustomer(this.getCustomer());
               this.getCurrentBudget().setBudgetNumber(this.getIntegerValueFromText(this.txtBudgetNumber));
               this.getCurrentBudget().setBudgetDate(this.buildDateFromInput(this.dateTimeBudget));
               this.getCurrentBudget().setObservations(this.txtObservations.getText().trim());
               this.getCurrentBudget().setInnerTaxes(0.0);
               VatCondition vatCondition = this.getOrderService().getVatConditionByName(this.comboVatCondition.getText());
               this.getCurrentBudget().setVatCondition(vatCondition);
               SaleCondition saleCondition = this.getOrderService().getSaleConditionByName(this.comboSaleCondition.getText());
               this.getCurrentBudget().setSaleCondition(saleCondition);
               this.getCurrentBudget().setCreationDate(creationDate);
               this.getCurrentBudget().setStatus("COMPLETED");
               this.getBudgetService().saveBudget(this.getCurrentBudget());
               AppConfig appConfig = this.getAppConfig();
               appConfig.setBudgetStartNumber(appConfig.getBudgetStartNumber() + 1);
               this.getAppConfigService().saveAppConfig(appConfig);
            }
         } catch (Exception var5) {
            logger.error("Error al guardar nuevo presupuesto");
            logger.error(var5.getMessage());
            //logger.error(var5);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if (this.getCustomer() == null) {
         valid = false;
         this.alert("Selecciona un cliente");
      }

      if (valid) {
         Integer budgetNumber = this.getIntegerValueFromText(this.txtBudgetNumber);
         if (budgetNumber == null || budgetNumber <= 0) {
            valid = false;
            this.alert("El nro. de presupuesto no es válido");
         }
      }

      if (valid && this.getCurrentBudget().getBudgetLines().size() == 0) {
         valid = false;
         this.alert("Agrega artículos al presupuesto");
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nuevo presupuesto");
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
      return new Point(640, 548);
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

   public Budget getCurrentBudget() {
      return this.currentBudget;
   }

   public void setCurrentBudget(Budget currentBudget) {
      this.currentBudget = currentBudget;
   }

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
}
