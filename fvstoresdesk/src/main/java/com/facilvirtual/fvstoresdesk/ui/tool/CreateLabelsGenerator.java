package com.facilvirtual.fvstoresdesk.ui.tool;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
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

public class CreateLabelsGenerator extends AbstractFVDialog {
   private String action = "";
   private PriceList priceList;
   private List<Product> products = new ArrayList();
   private String fileName = "";
   private Label lblProgressBarTitle;
   private ProgressBar progressBar;
   private Date startDate;
   private Date endDate;
   private int maxProducts = 3000;
   private boolean showPricePerUnit = true;

   public CreateLabelsGenerator(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      this.lblProgressBarTitle = new Label(container, 0);
      this.lblProgressBarTitle.setBounds(88, 39, 194, 15);
      this.lblProgressBarTitle.setText("Generando etiquetas...");
      this.progressBar = new ProgressBar(container, 0);
      this.progressBar.setBounds(88, 60, 170, 13);
      this.progressBar.setMinimum(0);
      int maxim = this.getProducts().size() < this.maxProducts ? this.getProducts().size() : this.maxProducts;
      this.progressBar.setMaximum(maxim);
      this.initFileName();
      this.setShowPricePerUnit(this.getAppConfig().showPricePerUnitForLabels());
      return container;
   }

   public Date getStartDate() {
      return this.startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public Date getEndDate() {
      return this.endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }

   private void initFileName() {
      String[] FILTER_NAMES = new String[]{"Microsoft Excel (*.xlsx)"};
      String[] FILTER_EXTS = new String[]{"*.xlsx"};
      FileDialog dlg = new FileDialog(this.getShell(), 8192);
      dlg.setFilterNames(FILTER_NAMES);
      dlg.setFilterExtensions(FILTER_EXTS);
      if (this.getStartDate() != null) {
         dlg.setFileName("Etiquetas_" + this.createDateToFile());
      } else {
         dlg.setFileName("Etiquetas_por_códigos");
      }

      String fn = dlg.open();
      if (fn != null) {
         this.setFileName(fn);
      }

   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Generar etiquetas");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId != 0) {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 1, "Cancelar", false);
      this.runThread();
   }

   private void runThread() {
      if (!"".equals(this.getFileName())) {
         (new CreateLabelsGeneratorInner(this.getShell().getDisplay(), this.progressBar, this.getProducts(), this.getFileName(), this.lblProgressBarTitle, this.getButton(1), this)).start();
      } else {
         this.close();
      }

   }

   protected Point getInitialSize() {
      return new Point(350, 200);
   }

   public String getAction() {
      return this.action;
   }

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
      return formatter.format(this.endDate) + "_" + formatter.format(this.startDate);
   }

   public boolean isShowPricePerUnit() {
      return this.showPricePerUnit;
   }

   public void setShowPricePerUnit(boolean showPricePerUnit) {
      this.showPricePerUnit = showPricePerUnit;
   }
}
