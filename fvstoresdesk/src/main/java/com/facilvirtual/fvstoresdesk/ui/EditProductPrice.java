package com.facilvirtual.fvstoresdesk.ui;

import java.awt.event.FocusEvent;

import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.model.Vat;
import com.facilvirtual.fvstoresdesk.util.FVMathUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EditProductPrice extends AbstractFVDialog {
   private String action = "";
   private ProductPrice productPrice;
   private Text txtCostPrice;
   private Text txtGrossMargin;
   private Text txtSellingPrice;
   private Combo comboVat;

   public EditProductPrice(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      GridLayout gl_container = new GridLayout(2, false);
      gl_container.marginTop = 17;
      gl_container.marginLeft = 50;
      gl_container.verticalSpacing = 10;
      container.setLayout(gl_container);
      Label lblPrecioCosto = new Label(container, 0);
      lblPrecioCosto.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPrecioCosto.setText("Precio Costo: $");
      this.txtCostPrice = new Text(container, 2048);
      //this.txtCostPrice.addFocusListener(new 1(this));
      GridData gd_txtCostPrice = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtCostPrice.widthHint = 75;
      this.txtCostPrice.setLayoutData(gd_txtCostPrice);
      this.txtCostPrice.setTextLimit(15);
      this.txtCostPrice.setText(this.getProductPrice().getCostPriceToDisplay());
      //this.txtCostPrice.addKeyListener(new 2(this));
      Label lblIva = new Label(container, 0);
      lblIva.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblIva.setText("IVA:");
      this.comboVat = new Combo(container, 8);
      GridData gd_comboVat = new GridData(16384, 16777216, true, false, 1, 1);
      gd_comboVat.widthHint = 61;
      this.comboVat.setLayoutData(gd_comboVat);
      //this.comboVat.addSelectionListener(new 3(this));
      List<Vat> vats = this.getOrderService().getAllVats();
      int selectedIdx = 0;
//TODO: arreglar
      for(Iterator var11 = vats.iterator(); var11.hasNext(); ++selectedIdx) {
         Vat v = (Vat)var11.next();
         this.comboVat.add(v.getName());
         if (this.productPrice.getVat().getName().equalsIgnoreCase(v.getName())) {
            this.comboVat.select(selectedIdx);
         }
      }

      Label lblUtilidad = new Label(container, 0);
      lblUtilidad.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblUtilidad.setText("% Utilidad:");
      this.txtGrossMargin = new Text(container, 2048);
      this.txtGrossMargin.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent e) {
            calculateGrossMargin();

         }
      });
      this.txtGrossMargin.setTextLimit(15);
      this.txtGrossMargin.setText(this.getProductPrice().getGrossMarginToDisplay());
      GridData gd_txtGrossMargin = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtGrossMargin.widthHint = 75;
      this.txtGrossMargin.setLayoutData(gd_txtGrossMargin);
      //todo: esto 
      //this.txtGrossMargin.addKeyListener(new 5(this));
      Label lblPrecioVenta = new Label(container, 0);
      lblPrecioVenta.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblPrecioVenta.setText("Precio Venta: $");
      this.txtSellingPrice = new Text(container, 2048);
      this.txtSellingPrice.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent e) {
            calculateGrossMargin();
            updateLabelPreview();
         }
      });
      GridData gd_txtSellingPrice = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtSellingPrice.widthHint = 75;
      this.txtSellingPrice.setLayoutData(gd_txtSellingPrice);
      this.txtSellingPrice.setTextLimit(15);
      this.txtSellingPrice.setText(this.getProductPrice().getSellingPriceToDisplay());
      //TODO: arreglar esto
      //this.txtSellingPrice.addKeyListener(new 7(this));
      return container;
   }

   protected void calculateSellingPrice() {
      try {
         String costPriceStr = this.txtCostPrice.getText().replaceAll(",", ".");
         double costPriceValue = !"".equals(costPriceStr) ? Double.parseDouble(costPriceStr) : 0.0;
         Vat vat = this.getOrderService().getVatByName(this.comboVat.getText());
         double vatValue = vat.getValue();
         String grossMarginStr = this.txtGrossMargin.getText().replaceAll(",", ".");
         double grossMarginValue = !"".equals(grossMarginStr) ? Double.parseDouble(grossMarginStr) : 0.0;
         double newSellingPrice = costPriceValue + costPriceValue * grossMarginValue / 100.0;
         newSellingPrice += newSellingPrice * vatValue / 100.0;
         DecimalFormat df = new DecimalFormat("0.00");
         String s = df.format(newSellingPrice);
         this.txtSellingPrice.setText(s);
         this.updatedSellingPrice();
         this.updateLabelPreview();
      } catch (Exception var14) {
         this.txtSellingPrice.setText("#Error");
      }

   }

   protected void calculateGrossMargin() {
      try {
         String sellingPriceStr = this.txtSellingPrice.getText().replaceAll(",", ".");
         double sellingPriceValue = !"".equals(sellingPriceStr) ? Double.parseDouble(sellingPriceStr) : 0.0;
         Vat vat = this.getOrderService().getVatByName(this.comboVat.getText());
         double vatValue = vat.getValue();
         sellingPriceValue /= 1.0 + vatValue / 100.0;
         String costPriceStr = this.txtCostPrice.getText().replaceAll(",", ".");
         double costPriceValue = !"".equals(costPriceStr) ? Double.parseDouble(costPriceStr) : 0.0;
         double diff = FVMathUtils.roundValue(sellingPriceValue) - FVMathUtils.roundValue(costPriceValue);
         double margin = 0.0;
         if (costPriceValue != 0.0) {
            margin = diff / costPriceValue * 100.0;
         }

         DecimalFormat df = new DecimalFormat("0.00");
         String s = df.format(margin);
         this.txtGrossMargin.setText(s);
      } catch (Exception var16) {
         this.txtGrossMargin.setText("#Error");
      }

   }

   protected void updatedSellingPrice() {
   }

   protected void updateLabelPreview() {
   }

   private void processProductPrice() {
      if (this.validateFields()) {
         this.setAction("OK");
         Vat vat = this.getOrderService().getVatByName(this.comboVat.getText());
         this.productPrice.setCostPrice(this.getDoubleValueFromText(this.txtCostPrice));
         this.productPrice.setVat(vat);
         this.productPrice.setGrossMargin(this.getDoubleValueFromText(this.txtGrossMargin));
         this.productPrice.setSellingPrice(this.getDoubleValueFromText(this.txtSellingPrice));
         this.productPrice.setLastUpdatedPrice(new Date());
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      double var2;
      if (valid) {
         try {
            var2 = Double.valueOf(this.txtCostPrice.getText().trim().replaceAll(",", "."));
         } catch (Exception var6) {
            valid = false;
            this.alert("El valor ingresado en Precio Costo no es válido");
         }
      }

      if (valid) {
         try {
            var2 = Double.valueOf(this.txtGrossMargin.getText().trim().replaceAll(",", "."));
         } catch (Exception var5) {
            valid = false;
            this.alert("El valor ingresado en % Utilidad no es válido");
         }
      }

      if (valid) {
         try {
            var2 = Double.valueOf(this.txtSellingPrice.getText().trim().replaceAll(",", "."));
         } catch (Exception var4) {
            valid = false;
            this.alert("El valor ingresado en Precio Venta no es válido");
         }
      }

      return valid;
   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, this.getTitle());
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processProductPrice();
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
      return new Point(304, 286);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public ProductPrice getProductPrice() {
      return this.productPrice;
   }

   public void setProductPrice(ProductPrice productPrice) {
      this.productPrice = productPrice;
   }
}
