package com.facilvirtual.fvstoresdesk.ui.components.dialogs.input.CashRegister;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class SearchProduct extends AbstractFVDialog {
   private String action = "";
   private Text txtQuery;
   private Table table;
   private Product product = null;
   private PriceList priceList;
   private Button btnHideDiscontinued;

   public SearchProduct(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      parent.setBackground(this.themeBack);
      Composite container = (Composite)super.createDialogArea(parent);
      container.setBackground(this.themeBack);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngreseElDescuento = new Label(container, 0);
      lblIngreseElDescuento.setBackground(this.themeBack);
      lblIngreseElDescuento.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblIngreseElDescuento = new FormData();
      fd_lblIngreseElDescuento.right = new FormAttachment(0, 210);
      fd_lblIngreseElDescuento.top = new FormAttachment(0, 10);
      fd_lblIngreseElDescuento.left = new FormAttachment(0, 10);
      lblIngreseElDescuento.setLayoutData(fd_lblIngreseElDescuento);
      lblIngreseElDescuento.setText("Buscar por código o descripción");
      this.txtQuery = new Text(container, 2048);
      this.txtQuery.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtQuery = new FormData();
      fd_txtQuery.top = new FormAttachment(lblIngreseElDescuento, 6);
      fd_txtQuery.left = new FormAttachment(0, 10);
      fd_txtQuery.right = new FormAttachment(100, -10);
      this.txtQuery.setLayoutData(fd_txtQuery);
      this.txtQuery.setText("");
      this.txtQuery.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent e) {
            if (e.detail == 4) {
               searchProducts();
            }
         }
      });
      this.table = new Table(container, 67584);
      FormData fd_table = new FormData();
      fd_table.right = new FormAttachment(this.txtQuery, 0, 131072);
      fd_table.bottom = new FormAttachment(100, -10);
      fd_table.top = new FormAttachment(this.txtQuery, 6);
      fd_table.left = new FormAttachment(0, 10);
      this.table.setLayoutData(fd_table);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn column = new TableColumn(this.table, 16384);
      column.setText("Código");
      column.setWidth(105);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Descripción");
      column.setWidth(370);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Rubro");
      column.setWidth(176);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Precio");
      column.setWidth(60);
      this.table.setHeaderVisible(true);
      column = new TableColumn(this.table, 16384);
      column.setText("Stock");
      column.setWidth(60);
      this.table.setHeaderVisible(true);
      this.btnHideDiscontinued = new Button(container, 32);
      this.btnHideDiscontinued.setSelection(true);
      FormData fd_btnHideDiscontinued = new FormData();
      fd_btnHideDiscontinued.top = new FormAttachment(lblIngreseElDescuento, 0, 128);
      fd_btnHideDiscontinued.right = new FormAttachment(this.txtQuery, 0, 131072);
      this.btnHideDiscontinued.setLayoutData(fd_btnHideDiscontinued);
      this.btnHideDiscontinued.setText("Ocultar discontinuados");
      this.btnHideDiscontinued.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            searchProducts();
         }
      });
      return container;
   }//TODO:arreglar

   private void searchProducts() {
      this.table.removeAll();
      if (this.txtQuery.getText().trim().length() > 0) {
         List<Product> products = this.getProductService().searchProducts(this.txtQuery.getText().trim(), this.btnHideDiscontinued.getSelection(), 1000);
         Iterator var3 = products.iterator();

         while(var3.hasNext()) {
            Product p = (Product)var3.next();
            this.addProductToTable(p);
         }
      }

   }

   private void addProductToTable(Product product) {
      TableItem item = new TableItem(this.table, 0);
      String sellingPriceStr = "0,00";

      try {
         if (this.getPriceList() != null) {
            ProductPrice productPrice = this.getProductService().getProductPriceForProduct(product, this.getPriceList());
            sellingPriceStr = productPrice.getSellingPriceToDisplay();
         }
      } catch (Exception var5) {
      }

      item.setText(0, product.getBarCode());
      item.setText(1, product.getDescription());
      item.setText(2, product.getCategory().getName().toUpperCase());
      item.setText(3, sellingPriceStr);
      item.setText(4, product.getStockToDisplay());
   }

   private void processQty() {
      if (this.validateFields()) {
         this.setAction("OK");
         int idx = this.table.getSelectionIndex();
         Product product = this.getProductService().getProductByBarCode(this.table.getItem(idx).getText(0).trim());
         this.setProduct(product);
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un artículo");
         valid = false;
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Buscar artículo (Lista: " + this.getPriceList().getName() + ")");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processQty();
      } else {
         this.close();
      }

   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      parent.setBackground(this.themeBack);
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(826, 485);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }
}
