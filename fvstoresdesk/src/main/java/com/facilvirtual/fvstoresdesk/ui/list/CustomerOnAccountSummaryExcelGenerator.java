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

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;

public class CustomerOnAccountSummaryExcelGenerator extends AbstractFVDialog {
   private String action = "";
   private List<Customer> customers = new ArrayList();
   private String fileName = "";
   private Label lblProgressBarTitle;
   private ProgressBar progressBar;

   public CustomerOnAccountSummaryExcelGenerator(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      this.lblProgressBarTitle = new Label(container, 0);
      this.lblProgressBarTitle.setBounds(88, 39, 246, 13);
      this.lblProgressBarTitle.setText("Generando resumen...");
      this.setCustomers(this.getCustomerService().getActiveCustomers());
      this.progressBar = new ProgressBar(container, 0);
      this.progressBar.setBounds(88, 58, 170, 17);
      this.progressBar.setMinimum(0);
      this.progressBar.setMaximum(this.getCustomers().size());
      this.initFileName();
      return container;
   }

   private void initFileName() {
      String[] FILTER_NAMES = new String[]{"Microsoft Excel (*.xlsx)"};
      String[] FILTER_EXTS = new String[]{"*.xlsx"};
      FileDialog dlg = new FileDialog(this.getShell(), 8192);
      dlg.setFilterNames(FILTER_NAMES);
      dlg.setFilterExtensions(FILTER_EXTS);
      dlg.setFileName("Cuentas_corrientes_clientes_" + this.createDateToFile());
      String fn = dlg.open();
      if (fn != null) {
         this.setFileName(fn);
      }

   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Generar resumen");
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
         (new SummaryGenerator(this.getShell().getDisplay(), this.progressBar, this.getCustomers(), this.getFileName(), this.lblProgressBarTitle, this.getButton(1), this)).start();
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

   public List<Customer> getCustomers() {
      return this.customers;
   }

   public void setCustomers(List<Customer> customers) {
      this.customers = customers;
   }
}
