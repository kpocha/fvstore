package com.facilvirtual.fvstoresdesk.ui.list;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;

public class ListOfPricesExcelGenerator extends AbstractFVDialog {
   private String action = "";
   private List<Product> products = new ArrayList();
   private String fileName = "";
   private Label lblProgressBarTitle;
   private ProgressBar progressBar;
   private PriceList priceList;

   public ListOfPricesExcelGenerator(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      System.out.println(this.priceList.getName());
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      this.lblProgressBarTitle = new Label(container, 0);
      this.lblProgressBarTitle.setBounds(88, 38, 246, 20);
      this.lblProgressBarTitle.setText("Generando lista de precios...");
      this.setProducts(this.getProductService().getAllActiveProducts());
      this.progressBar = new ProgressBar(container, 0);
      this.progressBar.setBounds(88, 58, 170, 17);
      this.progressBar.setMinimum(0);
      this.progressBar.setMaximum(this.getProducts().size());
      this.initFileName();
      return container;
   }

   private void initFileName() {
      String[] FILTER_NAMES = new String[]{"Microsoft Excel (*.xlsx)"};
      String[] FILTER_EXTS = new String[]{"*.xlsx"};
      FileDialog dlg = new FileDialog(this.getShell(), 8192);
      dlg.setFilterNames(FILTER_NAMES);
      dlg.setFilterExtensions(FILTER_EXTS);
      dlg.setFileName("Lista_de_precios_" + this.createDateToFile());
      String fn = dlg.open();
      if (fn != null) {
         this.setFileName(fn);
      }

   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Generar lista de precios");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId != 0) {
         this.close();
      }

   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 1, "Cancelar", false);
      this.runThread();
   }

   private void runThread() {
      if (!"".equals(this.getFileName())) {
         (new ListOfPricesGenerator(this.getShell().getDisplay(), this.progressBar, this.getProducts(), this.getPriceList(), this.getProductService(), this.getFileName(), this.lblProgressBarTitle, this.getButton(1), this)).start();
      } else {
         this.close();
      }

   }
   @Override
   protected Point getInitialSize() {
      return new Point(350, 200);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public List<Product> getProducts() {
      return this.products;
   }

   public void setProducts(List<Product> products) {
      this.products = products;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   private String createDateToFile() {
      Format formatter = new SimpleDateFormat("yyyy-MM-dd");
      return formatter.format(new Date());
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }
}
