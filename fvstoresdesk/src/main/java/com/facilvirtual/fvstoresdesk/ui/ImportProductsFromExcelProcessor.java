package com.facilvirtual.fvstoresdesk.ui;

import java.io.File;
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class ImportProductsFromExcelProcessor extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("ImportProductsFromExcelProcessor");
   private String action = "";
   private Label lblProgressBarTitle;
   private ProgressBar progressBar;
   private ImportProductsFromExcel settings;
   private int maxProducts = 50000;

   public ImportProductsFromExcelProcessor(Shell parentShell) {
      super(parentShell);
   }

   public ImportProductsFromExcel getSettings() {
      return this.settings;
   }

   public void setSettings(ImportProductsFromExcel settings) {
      this.settings = settings;
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      this.lblProgressBarTitle = new Label(container, 0);
      this.lblProgressBarTitle.setBounds(88, 35, 246, 20);
      this.lblProgressBarTitle.setText("Importando artículos desde archivo...");
      this.progressBar = new ProgressBar(container, 0);
      this.progressBar.setBounds(88, 58, 200, 17);
      this.progressBar.setMinimum(0);

      try {
         FileInputStream file = new FileInputStream(new File(this.getSettings().getFilename()));
         XSSFWorkbook workbook = new XSSFWorkbook(file);
         XSSFSheet sheet = workbook.getSheetAt(0);
         int maxim = sheet.getLastRowNum() < this.maxProducts ? sheet.getLastRowNum() : this.maxProducts;
         this.progressBar.setMaximum(maxim);
      } catch (Exception var7) {
         logger.error("Error al importar productos desde Excel");
         logger.error(var7.getMessage());
        // //logger.error(var7);
      }

      return container;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Importar artículos desde Excel");
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
      if (!"".equals(this.getSettings().getFilename())) {
         (new ImportProductsFromFileProcessor(this.getShell().getDisplay(), this.progressBar, this.lblProgressBarTitle, this.getButton(1), this)).start();
      } else {
         this.close();
      }

   }

   protected Point getInitialSize() {
      return new Point(380, 200);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }
}
