package com.facilvirtual.fvstoresdesk.print;

import com.facilvirtual.fvstoresdesk.model.Budget;
import com.facilvirtual.fvstoresdesk.model.BudgetLine;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.service.BudgetService;
import com.facilvirtual.fvstoresdesk.service.ProductService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.springframework.context.ApplicationContext;

public class BudgetPdfCreator {
   protected static Logger logger = LoggerFactory.getLogger("BudgetPdfCreator");
   private Budget budget;
   private String fileName;
   private Shell shell;

   public BudgetPdfCreator(Budget budget, Shell shell) {
      this.budget = budget;
      this.fileName = "presupuesto_" + budget.getId() + ".pdf";
      this.shell = shell;
   }

   public void createPdf() {
      logger.info("Imprimiendo presupuesto: " + this.budget.getId());

      try {
         Document document = new Document();
         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\facilvirtual\\tmp\\" + this.getFileName()));
         document.open();
         File f = new File("C:\\facilvirtual\\images\\template_budget_custom.jpg");
         Image image1;
         if (f.exists() && !f.isDirectory()) {
            image1 = Image.getInstance("C:\\facilvirtual\\images\\template_budget_custom.jpg");
            image1.setAbsolutePosition(0.0F, 15.0F);
            document.add(image1);
         } else {
            File f2 = new File("C:\\facilvirtual\\images\\template_budget_default.jpg");
            if (f2.exists() && !f2.isDirectory()) {
               image1 = Image.getInstance("C:\\facilvirtual\\images\\template_budget_default.jpg");
               image1.setAbsolutePosition(0.0F, 15.0F);
               document.add(image1);
            }
         }

         BaseFont bf = BaseFont.createFont();
         PdfContentByte canvas = writer.getDirectContent();
         canvas.saveState();
         canvas.beginText();
         canvas.setFontAndSize(bf, 12.0F);
         float budgetNumberX = Utilities.millimetersToPoints(178.0F);
         float budgetNumberY = Utilities.millimetersToPoints(271.0F);
         float budgetDateX = Utilities.millimetersToPoints(178.0F);
         float budgetDateY = Utilities.millimetersToPoints(255.0F);
         float customerNameX = Utilities.millimetersToPoints(35.0F);
         float customerNameY = Utilities.millimetersToPoints(231.0F);
         float customerAddressX = Utilities.millimetersToPoints(35.0F);
         float customerAddressY = Utilities.millimetersToPoints(224.0F);
         float cityX = Utilities.millimetersToPoints(135.0F);
         float cityY = Utilities.millimetersToPoints(224.0F);
         float vatX = Utilities.millimetersToPoints(35.0F);
         float vatY = Utilities.millimetersToPoints(217.0F);
         float cuitX = Utilities.millimetersToPoints(135.0F);
         float cuitY = Utilities.millimetersToPoints(217.0F);
         float saleCondX = Utilities.millimetersToPoints(50.0F);
         float saleCondY = Utilities.millimetersToPoints(207.0F);
         float itemStartY = Utilities.millimetersToPoints(188.0F);
         float itemOffsetY = Utilities.millimetersToPoints(7.0F);
         float itemDescX = Utilities.millimetersToPoints(12.0F);
         float itemQtyX = Utilities.millimetersToPoints(120.0F);
         float itemTotalX = Utilities.millimetersToPoints(190.0F);
         float totalX = Utilities.millimetersToPoints(190.0F);
         float totalY = Utilities.millimetersToPoints(35.0F);
         float observationsX = Utilities.millimetersToPoints(12.0F);
         float observationsY = Utilities.millimetersToPoints(15.0F);
         canvas.showTextAligned(2, this.budget.getBudgetNumberToPrint(), budgetNumberX, budgetNumberY, 0.0F);
         canvas.showTextAligned(2, this.budget.getBudgetDateToPrint(), budgetDateX, budgetDateY, 0.0F);
         canvas.showTextAligned(0, this.budget.getCustomerNameToPrint(), customerNameX, customerNameY, 0.0F);
         canvas.showTextAligned(0, this.budget.getCustomer().getFullAddress(), customerAddressX, customerAddressY, 0.0F);
         canvas.showTextAligned(0, this.budget.getVatConditionToDisplay(), vatX, vatY, 0.0F);
         canvas.showTextAligned(0, this.budget.getCustomer().getCity(), cityX, cityY, 0.0F);
         canvas.showTextAligned(0, this.budget.getCustomer().getCuit(), cuitX, cuitY, 0.0F);
         canvas.showTextAligned(0, this.budget.getSaleConditionToPrint(), saleCondX, saleCondY, 0.0F);
         List<BudgetLine> budgetLines = this.getBudgetService().getBudgetLinesForBudget(this.budget);
         float linePos = itemStartY;

         for(Iterator var35 = budgetLines.iterator(); var35.hasNext(); linePos -= itemOffsetY) {
            BudgetLine budgetLine = (BudgetLine)var35.next();
            canvas.showTextAligned(0, budgetLine.getDescriptionToPrint(), itemDescX, linePos, 0.0F);
            canvas.showTextAligned(2, budgetLine.getQtyToDisplay(), itemQtyX, linePos, 0.0F);
            canvas.showTextAligned(2, budgetLine.getPriceToDisplay(), itemTotalX, linePos, 0.0F);
         }

         canvas.showTextAligned(2, this.budget.getTotalToDisplay(), totalX, totalY, 0.0F);
         canvas.showTextAligned(0, this.budget.getObservationsToDisplay(), observationsX, observationsY, 0.0F);
         canvas.endText();
         canvas.restoreState();
         document.close();
         this.openPdf();
      } catch (FileNotFoundException var36) {
         logger.error("Se produjo un error al imprimir presupuesto: " + this.budget.getId());
         this.alert("Error al crear el archivo pdf. El archivo se encuentra abierto. Cierra el archivo e inténtalo de nuevo.");
      } catch (Exception var37) {
         logger.error("Se produjo un error al imprimir presupuesto: " + this.budget.getId());
         logger.error(var37.getMessage());
        // logger.error(var37);
      }

   }

   public void alert(String message) {
      MessageBox dialog = new MessageBox(this.getShell());
      dialog.setText("Aviso");
      dialog.setMessage(message);
      dialog.open();
   }

   public Shell getShell() {
      return this.shell;
   }

   public void setShell(Shell shell) {
      this.shell = shell;
   }

   public BudgetService getBudgetService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (BudgetService)context.getBean("budgetService");
   }

   private void openPdf() {
      if (Desktop.isDesktopSupported()) {
         try {
            File myFile = new File("C:\\facilvirtual\\tmp\\" + this.getFileName());
            Desktop.getDesktop().open(myFile);
         } catch (IOException var2) {
            logger.error("Se produjo un error al abrir pdf de presupuesto: " + this.budget.getId());
         }
      }

   }

   public ProductService getProductService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (ProductService)context.getBean("productService");
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }
}
