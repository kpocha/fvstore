package com.facilvirtual.fvstoresdesk.ui.report;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;
import com.facilvirtual.fvstoresdesk.util.FVDateUtils;
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

public class ReportSalesByDateGenerator extends AbstractFVDialog {
   private String action = "";
   private List<Order> orders = new ArrayList();
   private String fileName = "";
   private Label lblProgressBarTitle;
   private ProgressBar progressBar;
   private Date startDate;
   private Date endDate;
   private boolean conCAE;

   public ReportSalesByDateGenerator(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      this.lblProgressBarTitle = new Label(container, 0);
      this.lblProgressBarTitle.setBounds(88, 39, 194, 13);
      this.lblProgressBarTitle.setText("Generando informe...");
      this.progressBar = new ProgressBar(container, 0);
      this.progressBar.setBounds(88, 58, 170, 17);
      this.progressBar.setMinimum(0);
      int days = FVDateUtils.daysBetween(this.getStartDate(), this.getEndDate()) + 1;
      this.progressBar.setMaximum(days);
      this.initFileName();
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

   private void initFileName() {
      String[] FILTER_NAMES = new String[]{"Microsoft Excel (*.xlsx)"};
      String[] FILTER_EXTS = new String[]{"*.xlsx"};
      FileDialog dlg = new FileDialog(this.getShell(), 8192);
      dlg.setFilterNames(FILTER_NAMES);
      dlg.setFilterExtensions(FILTER_EXTS);
      dlg.setFileName("Ventas_por_fecha_" + this.createDateToFile());
      String fn = dlg.open();
      if (fn != null) {
         this.setFileName(fn);
      }

   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Generar informe");
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
         (new ReportSalesByDateGeneratorInner(this.getShell().getDisplay(), this.progressBar, this.getStartDate(), this.getEndDate(), this.getOrderService(), this.getFileName(), this.lblProgressBarTitle, this.conCAE, this.getButton(1), this)).start();
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

   public boolean isConCAE() {
      return this.conCAE;
   }

   public void setConCAE(boolean conCAE) {
      this.conCAE = conCAE;
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

   public List<Order> getOrders() {
      return this.orders;
   }

   public void setOrders(List<Order> orders) {
      this.orders = orders;
   }
}
