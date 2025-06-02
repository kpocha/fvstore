package com.facilvirtual.fvstoresdesk.ui.tool;

import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class CreateBarcodesGenerator extends AbstractFVDialog {
   private String action = "";
   private List<Product> products = new ArrayList();
   private String fileName = "";
   private Label lblProgressBarTitle;
   private ProgressBar progressBar;
   private int maxProducts = 3000;
   private boolean showPricePerUnit = true;

   public CreateBarcodesGenerator(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      this.lblProgressBarTitle = new Label(container, 0);
      this.lblProgressBarTitle.setBounds(88, 19, 194, 17);
      this.lblProgressBarTitle.setText("Generando códigos de barra...");
      this.progressBar = new ProgressBar(container, 0);
      this.progressBar.setBounds(88, 58, 170, 17);
      this.progressBar.setMinimum(0);
      int maxim = this.getProducts().size() < this.maxProducts ? this.getProducts().size() : this.maxProducts;
      this.progressBar.setMaximum(maxim);
      this.initFileName();
      this.setShowPricePerUnit(this.getAppConfig().showPricePerUnitForLabels());
      return container;
   }

   private void initFileName() {
      String[] FILTER_NAMES = new String[]{"Archivos Adobe PDF (*.pdf)"};
      String[] FILTER_EXTS = new String[]{"*.pdf"};
      FileDialog dlg = new FileDialog(this.getShell(), 8192);
      dlg.setFilterNames(FILTER_NAMES);
      dlg.setFilterExtensions(FILTER_EXTS);
      dlg.setFileName("Códigos_de_barra");
      String fn = dlg.open();
      if (fn != null) {
         this.setFileName(fn);
      }

   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Generar códigos de barra");
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
         (new CreateBarcodesGeneratorInner(this.getShell().getDisplay(), this.progressBar, this.getProducts(), this.getFileName(), this.lblProgressBarTitle, this.getButton(1), this)).start();
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

   public boolean isShowPricePerUnit() {
      return this.showPricePerUnit;
   }

   public void setShowPricePerUnit(boolean showPricePerUnit) {
      this.showPricePerUnit = showPricePerUnit;
   }
}
