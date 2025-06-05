package com.facilvirtual.fvstoresdesk.ui.screens.budgets;

import java.text.DecimalFormat;
import java.util.List;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.Budget;
import com.facilvirtual.fvstoresdesk.model.BudgetLine;
import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.model.Vat;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class AddProductToBudget extends AbstractFVDialog {
   private static final Logger  logger = LoggerFactory.getLogger(AddProductToBudget.class);
   private String action = "";
   private Budget budget;
   private Text txtDescription;
   private Text txtQty;
   private Text txtNetPrice;
   private Text txtPrice;
   private Combo comboVat;
   private Product product;

   public AddProductToBudget(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout(new GridLayout(2, false));
      Label lblCdigo = new Label(container, 0);
      lblCdigo.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCdigo.setText("Artículo:");
      this.txtDescription = new Text(container, 2056);
      this.txtDescription.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.txtDescription.setText(this.getProduct().getBarCode() + " - " + this.getProduct().getDescription());
      Label lblCantidad = new Label(container, 0);
      lblCantidad.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblCantidad.setText("Cantidad:");
      this.txtQty = new Text(container, 2048);
      this.txtQty.setText("1");
      GridData gd_txtQty = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtQty.widthHint = 50;
      this.txtQty.setLayoutData(gd_txtQty);
      Label lblPrecioSinIva = new Label(container, 0);
      lblPrecioSinIva.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPrecioSinIva.setText("Precio sin IVA: $");
      this.txtNetPrice = new Text(container, 2048);
      GridData gd_txtNetPrice = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtNetPrice.widthHint = 50;
      this.txtNetPrice.setLayoutData(gd_txtNetPrice);
     this.txtNetPrice.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
           updatePrice(); // Reemplazá esto por el método real que se esté llamando
         }
      });
      Label lblIva = new Label(container, 0);
      lblIva.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblIva.setText("IVA:");
      this.comboVat = new Combo(container, 8);
      GridData gd_comboVat = new GridData(16384, 16777216, true, false, 1, 1);
      gd_comboVat.widthHint = 50;
      this.comboVat.setLayoutData(gd_comboVat);
      this.comboVat.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            updatePrice(); // Reemplazá esto por el método real que se está llamando
         }
      });
      List<Vat> vats = this.getOrderService().getAllVats();
      int selectedIdx = 0;
      //TODO: CAmbie las lineas comentadas por este nuevo metodo para ver q tal funciona y no tira warning porq esta mejor usado el for.
      for (Vat v : vats) {
         this.comboVat.add(v.getName());
         Vat selectedVat = this.getProduct() != null ? this.getProduct().getVat() : null;
     
         if (selectedVat != null && selectedVat.getName().equalsIgnoreCase(v.getName())) {
             this.comboVat.select(selectedIdx);
         } else if (selectedVat == null && "0%".equalsIgnoreCase(v.getName())) {
             this.comboVat.select(selectedIdx);
         }
     
         selectedIdx++;
     }
      // for(Iterator var13 = vats.iterator(); var13.hasNext(); ++selectedIdx) {
      //    Vat v = (Vat)var13.next();
      //    this.comboVat.add(v.getName());
      //    if (this.getProduct().getVat() != null && this.getProduct().getVat().getName().equalsIgnoreCase(v.getName())) {
      //       this.comboVat.select(selectedIdx);
      //    } else if (this.getProduct().getVat() == null && "0%".equalsIgnoreCase(v.getName())) {
      //       this.comboVat.select(selectedIdx);
      //    }
      // }

      Label lblPrecioConIva = new Label(container, 0);
      lblPrecioConIva.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPrecioConIva.setText("Precio con IVA: $");
      this.txtPrice = new Text(container, 2048);
      GridData gd_txtPrice = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtPrice.widthHint = 50;
      this.txtPrice.setLayoutData(gd_txtPrice);
      this.txtPrice.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
           updatePrice(); // Reemplazá esto por el método real que se esté llamando
         }
      });
      this.txtQty.setText("1");
      PriceList defaultPriceList = this.getDefaultPriceList();
      ProductPrice productPrice = this.getProductService().getProductPriceForProduct(this.getProduct(), defaultPriceList);
      this.txtPrice.setText(productPrice.getSellingPriceToDisplay());
      this.updateNetPrice();
      return container;
   }

   private void updatePrice() {
      try {
         String netPriceStr = this.txtNetPrice.getText().replaceAll(",", ".");
         double netPriceValue = !"".equals(netPriceStr) ? Double.parseDouble(netPriceStr) : 0.0;
         Vat vat = this.getOrderService().getVatByName(this.comboVat.getText());
         double vatValue = vat.getValue();
         double priceValue = netPriceValue + netPriceValue * vatValue / 100.0;
         DecimalFormat df = new DecimalFormat("0.00");
         String s = df.format(priceValue);
         this.txtPrice.setText(s);
      } catch (Exception var11) {
         this.txtPrice.setText("#Error");
         logger.error("Se produjo un error al actualizar el precio");
         logger.error(var11.getMessage());
         //logger.error(var11);
      }

   }

   private void updateNetPrice() {
      try {
         String priceStr = this.txtPrice.getText().replaceAll(",", ".");
         double priceValue = !"".equals(priceStr) ? Double.parseDouble(priceStr) : 0.0;
         Vat vat = this.getOrderService().getVatByName(this.comboVat.getText());
         double vatValue = vat.getValue();
         double netPriceValue = priceValue / (1.0 + vatValue / 100.0);
         DecimalFormat df = new DecimalFormat("0.00");
         String s = df.format(netPriceValue);
         this.txtNetPrice.setText(s);
      } catch (Exception var11) {
         this.txtNetPrice.setText("#Error");
         logger.error("Se produjo un error al actualizar el precio");
         logger.error(var11.getMessage());
         //logger.error(var11);
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");

         try {
            BudgetLine budgetLine = this.getBudget().getBudgetLineByBarCode(this.product.getBarCode());
            Vat vat;
            double vatValue;
            if (budgetLine == null) {
               budgetLine = new BudgetLine();
               budgetLine.setBudget(this.getBudget());
               budgetLine.setLineNumber(this.getBudget().getItemsQty() + 1);
               budgetLine.setQty(Double.parseDouble(this.txtQty.getText()));
               budgetLine.setPrice(Double.parseDouble(this.txtPrice.getText().trim().replaceAll(",", ".")));
               vat = this.getOrderService().getVatByName(this.comboVat.getText());
               vatValue = vat.getValue();
               budgetLine.setVatValue(vatValue);
               budgetLine.setProduct(this.getProduct());
               this.getBudget().addBudgetLine(budgetLine);
            } else {
               budgetLine.incQty(Double.parseDouble(this.txtQty.getText()));
               budgetLine.setPrice(Double.parseDouble(this.txtPrice.getText().trim().replaceAll(",", ".")));
               vat = this.getOrderService().getVatByName(this.comboVat.getText());
               vatValue = vat.getValue();
               budgetLine.setVatValue(vatValue);
            }

            this.getBudget().updateTotal();
         } catch (Exception var5) {
            logger.error("Error al agregar artículo a presupuesto");
            logger.error(var5.getMessage());
            //logger.error(var5);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      double value;
      if (valid) {
         try {
            value = Double.valueOf(this.txtQty.getText().trim().replaceAll(",", "."));
         } catch (Exception var5) {
            valid = false;
            this.alert("La cantidad ingresada no es válida");
         }
      }

      if (valid) {
         value = Double.valueOf(this.txtQty.getText().trim().replaceAll(",", "."));
         if (value <= 0.0) {
            valid = false;
            this.alert("La cantidad debe ser mayor a 0");
         }
      }

      if (valid) {
         try {
            value = Double.valueOf(this.txtNetPrice.getText().trim().replaceAll(",", "."));
         } catch (Exception var4) {
            valid = false;
            this.alert("El precio ingresado no es válido");
         }
      }

      if (valid) {
         value = Double.valueOf(this.txtNetPrice.getText().trim().replaceAll(",", "."));
         if (value <= 0.0) {
            this.alert("El precio debe ser mayor a 0");
            valid = false;
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Agregar artículo al presupuesto");
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
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(532, 243);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public Budget getBudget() {
      return this.budget;
   }

   public void setBudget(Budget budget) {
      this.budget = budget;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }
}
