package com.facilvirtual.fvstoresdesk.ui.screens.inventory;

import com.facilvirtual.fvstoresdesk.model.PriceList;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;

public class ImportProductsFromExcel extends AbstractFVDialog {
   private String action = "";
   private Combo comboPriceLists;
   private PriceList priceList = null;
   private Text txtFilename;
   private String filename = "";
   private Button btnNotUpdateProducts;
   private Button btnUpdateProducts;
   private Button btnKeepDescription;
   private Button btnKeepCategory;
   private Button btnKeepPrice;
   private Button btnKeepCost;
   private Button btnKeepStock;
   private Button btnKeepStockMin;
   private Button btnKeepStockMax;
   private boolean updateProducts = false;
   private boolean keepDescription = false;
   private boolean keepCategory = false;
   private boolean keepPrice = false;
   private boolean keepCost = false;
   private boolean keepStock = false;
   private boolean keepStockMin = false;
   private boolean keepStockMax = false;

   public ImportProductsFromExcel(Shell parentShell) {
      super(parentShell);
   }

   public boolean isKeepCost() {
      return this.keepCost;
   }

   public void setKeepCost(boolean keepCost) {
      this.keepCost = keepCost;
   }

   public boolean isKeepStock() {
      return this.keepStock;
   }

   public void setKeepStock(boolean keepStock) {
      this.keepStock = keepStock;
   }

   public boolean isKeepStockMin() {
      return this.keepStockMin;
   }

   public void setKeepStockMin(boolean keepStockMin) {
      this.keepStockMin = keepStockMin;
   }

   public boolean isKeepStockMax() {
      return this.keepStockMax;
   }

   public void setKeepStockMax(boolean keepStockMax) {
      this.keepStockMax = keepStockMax;
   }

   public boolean isKeepDescription() {
      return this.keepDescription;
   }

   protected void setKeepDescription(boolean keepDescription) {
      this.keepDescription = keepDescription;
   }

   public boolean isKeepPrice() {
      return this.keepPrice;
   }

   protected void setKeepPrice(boolean keepPrice) {
      this.keepPrice = keepPrice;
   }

   public boolean isKeepCategory() {
      return this.keepCategory;
   }

   protected void setKeepCategory(boolean keepCategory) {
      this.keepCategory = keepCategory;
   }

   public String getFilename() {
      return this.filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

   public boolean isUpdateProducts() {
      return this.updateProducts;
   }

   public void setUpdateProducts(boolean updateProducts) {
      this.updateProducts = updateProducts;
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 16777216);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.top = new FormAttachment(0, 25);
      fd_lblTitle.left = new FormAttachment(0, 68);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Importar artículos desde Excel");
      Label lblNombreDelArchivo = new Label(container, 0);
      lblNombreDelArchivo.setText("Archivo");
      lblNombreDelArchivo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblNombreDelArchivo = new FormData();
      lblNombreDelArchivo.setLayoutData(fd_lblNombreDelArchivo);
      this.txtFilename = new Text(container, 2048);
      fd_lblNombreDelArchivo.top = new FormAttachment(this.txtFilename, 3, 128);
      fd_lblNombreDelArchivo.right = new FormAttachment(this.txtFilename, -6);
      this.txtFilename.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtFilename = new FormData();
      fd_txtFilename.left = new FormAttachment(0, 80);
      this.txtFilename.setLayoutData(fd_txtFilename);
      Button btnNewButton = new Button(container, 0);
      fd_lblTitle.right = new FormAttachment(btnNewButton, 28, 131072);
      fd_txtFilename.right = new FormAttachment(btnNewButton, -6);
      btnNewButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetDefaultSelected(SelectionEvent e) {
            browse();
         };
      });
      btnNewButton.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      FormData fd_btnNewButton = new FormData();
      fd_btnNewButton.top = new FormAttachment(lblTitle, 71);
      fd_btnNewButton.left = new FormAttachment(0, 343);
      btnNewButton.setLayoutData(fd_btnNewButton);
      btnNewButton.setText("Seleccionar");
      this.btnUpdateProducts = new Button(container, 16);
      fd_lblNombreDelArchivo.left = new FormAttachment(this.btnUpdateProducts, 0, 16384);
      FormData fd_btnUpdateProducts = new FormData();
      fd_btnUpdateProducts.left = new FormAttachment(0, 25);
      this.btnUpdateProducts.setLayoutData(fd_btnUpdateProducts);
      this.btnUpdateProducts.setText("Actualizar artículos existentes");
      this.btnNotUpdateProducts = new Button(container, 16);
      fd_btnUpdateProducts.top = new FormAttachment(0, 182);
      FormData fd_btnNotUpdateProducts = new FormData();
      fd_btnNotUpdateProducts.left = new FormAttachment(0, 25);
      fd_btnNotUpdateProducts.bottom = new FormAttachment(this.btnUpdateProducts, -6);
      this.btnNotUpdateProducts.setLayoutData(fd_btnNotUpdateProducts);
      this.btnNotUpdateProducts.setText("No actualizar artículos existentes");
      this.btnNotUpdateProducts.setSelection(true);
      Button btnAyuda = new Button(container, 0);
      btnAyuda.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetDefaultSelected(SelectionEvent e) {
            help();
         };
      });
      FormData fd_btnAyuda = new FormData();
      fd_btnAyuda.right = new FormAttachment(0, 83);
      fd_btnAyuda.bottom = new FormAttachment(100, -10);
      fd_btnAyuda.left = new FormAttachment(0, 25);
      btnAyuda.setLayoutData(fd_btnAyuda);
      btnAyuda.setText("Ayuda");
      this.btnKeepDescription = new Button(container, 32);
      FormData fd_btnKeepDescription = new FormData();
      fd_btnKeepDescription.left = new FormAttachment(0, 45);
      this.btnKeepDescription.setLayoutData(fd_btnKeepDescription);
      this.btnKeepDescription.setText("Conservar descripción");
      this.btnKeepPrice = new Button(container, 32);
      fd_btnKeepDescription.bottom = new FormAttachment(this.btnKeepPrice, -28);
      this.btnKeepPrice.setText("Conservar precio de venta");
      FormData fd_btnKeepPrice = new FormData();
      fd_btnKeepPrice.left = new FormAttachment(this.btnKeepDescription, 0, 16384);
      this.btnKeepPrice.setLayoutData(fd_btnKeepPrice);
      this.btnKeepCategory = new Button(container, 32);
      this.btnKeepCategory.setText("Conservar rubro");
      FormData fd_btnKeepCategory = new FormData();
      fd_btnKeepCategory.bottom = new FormAttachment(this.btnKeepPrice, -6);
      fd_btnKeepCategory.left = new FormAttachment(this.btnKeepDescription, 0, 16384);
      this.btnKeepCategory.setLayoutData(fd_btnKeepCategory);
      this.btnKeepCost = new Button(container, 32);
      fd_btnKeepPrice.bottom = new FormAttachment(this.btnKeepCost, -6);
      FormData fd_btnKeepCost = new FormData();
      fd_btnKeepCost.top = new FormAttachment(0, 270);
      fd_btnKeepCost.left = new FormAttachment(this.btnKeepDescription, 0, 16384);
      this.btnKeepCost.setLayoutData(fd_btnKeepCost);
      this.btnKeepCost.setText("Conservar precio de costo");
      this.btnKeepStock = new Button(container, 32);
      FormData fd_btnKeepStock = new FormData();
      fd_btnKeepStock.top = new FormAttachment(this.btnKeepCost, 6);
      fd_btnKeepStock.left = new FormAttachment(this.btnKeepDescription, 0, 16384);
      this.btnKeepStock.setLayoutData(fd_btnKeepStock);
      this.btnKeepStock.setText("Conservar stock");
      this.btnKeepStockMin = new Button(container, 32);
      FormData fd_btnKeepStockMin = new FormData();
      fd_btnKeepStockMin.top = new FormAttachment(this.btnKeepStock, 6);
      fd_btnKeepStockMin.left = new FormAttachment(this.btnKeepDescription, 0, 16384);
      this.btnKeepStockMin.setLayoutData(fd_btnKeepStockMin);
      this.btnKeepStockMin.setText("Conservar stock mínimo");
      this.btnKeepStockMax = new Button(container, 32);
      FormData fd_btnKeepStockMax = new FormData();
      fd_btnKeepStockMax.top = new FormAttachment(this.btnKeepStockMin, 6);
      fd_btnKeepStockMax.left = new FormAttachment(this.btnKeepDescription, 0, 16384);
      this.btnKeepStockMax.setLayoutData(fd_btnKeepStockMax);
      this.btnKeepStockMax.setText("Conservar stock máximo");
      Label lblLista = new Label(container, 0);
      lblLista.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblLista = new FormData();
      fd_lblLista.left = new FormAttachment(0, 25);
      fd_lblLista.bottom = new FormAttachment(100, -308);
      lblLista.setLayoutData(fd_lblLista);
      lblLista.setText("Lista");
      this.comboPriceLists = new Combo(container, 8);
      fd_txtFilename.top = new FormAttachment(this.comboPriceLists, 15);
      FormData fd_combo = new FormData();
      fd_combo.right = new FormAttachment(this.txtFilename, -82, 131072);
      fd_combo.bottom = new FormAttachment(100, -306);
      fd_combo.left = new FormAttachment(lblLista, 29);
      this.comboPriceLists.setLayoutData(fd_combo);
      List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
      Iterator var27 = priceLists.iterator();

      while(var27.hasNext()) {
         PriceList priceList = (PriceList)var27.next();
         this.comboPriceLists.add(priceList.getName());
      }

      this.comboPriceLists.select(0);
      return container;
   }

   private void help() {
      ImportProductsFromExcelHelp dialog = new ImportProductsFromExcelHelp(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.open();
   }

   private void browse() {
      String[] FILTER_NAMES = new String[]{"Microsoft Excel (*.xlsx)"};
      String[] FILTER_EXTS = new String[]{"*.xlsx"};
      FileDialog dlg = new FileDialog(this.getShell(), 8192);
      dlg.setFilterNames(FILTER_NAMES);
      dlg.setFilterExtensions(FILTER_EXTS);
      String fn = dlg.open();
      if (fn != null) {
         this.txtFilename.setText(fn);
      }

   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
         this.setPriceList(priceList);
         this.setFilename(this.txtFilename.getText().trim());
         this.setUpdateProducts(this.btnUpdateProducts.getSelection());
         this.setKeepDescription(this.btnKeepDescription.getSelection());
         this.setKeepCategory(this.btnKeepCategory.getSelection());
         this.setKeepPrice(this.btnKeepPrice.getSelection());
         this.setKeepCost(this.btnKeepCost.getSelection());
         this.setKeepStock(this.btnKeepStock.getSelection());
         this.setKeepStockMin(this.btnKeepStockMin.getSelection());
         this.setKeepStockMax(this.btnKeepStockMax.getSelection());
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      String filename = this.txtFilename.getText().trim();
      if (filename == null || filename.equals("")) {
         valid = false;
         this.alert("Selecciona el archivo.");
      }

      if (valid) {
         try {
            FileInputStream file = new FileInputStream(new File(filename));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet var5 = workbook.getSheetAt(0);
         } catch (Exception var6) {
            valid = false;
            this.alert("No se pudo abrir el archivo.");
         }
      }

      return valid;
   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Importar artículos desde Excel");
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
      return new Point(524, 494);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }
}
