package com.facilvirtual.fvstoresdesk.print;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.ReceiptType;
import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.service.ProductService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.springframework.context.ApplicationContext;

public class InvoiceAPdfCreatorOLD {
   protected static Logger logger = LoggerFactory.getLogger("InvoiceAPdfCreator");
   private Order order;
   private String fileName;
   private Shell shell;

   public InvoiceAPdfCreatorOLD(Order order, Shell shell) {
      this.order = order;
      this.fileName = "comprobante_" + order.getId() + ".pdf";
      this.shell = shell;
   }

   public void createPdf() {
      logger.info("Imprimiendo venta: " + this.order.getId());

      try {
         Document document = new Document();
         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\facilvirtual\\tmp\\" + this.getFileName()));
         document.open();
         BaseFont bf = BaseFont.createFont();
         PdfContentByte canvas = writer.getDirectContent();
         canvas.saveState();
         canvas.beginText();
         canvas.setFontAndSize(bf, 12.0F);
         ReceiptType receiptType = this.getAppConfigService().getReceiptTypeByName("Factura A");
         document.close();
         this.openPdf();
      } catch (FileNotFoundException var6) {
         logger.error("Se produjo un error al imprimir comprobante: " + this.order.getId());
         this.alert("Error al crear el archivo pdf. El archivo se encuentra abierto. Cierra el archivo e inténtalo de nuevo.");
      } catch (Exception var7) {
         logger.error("Se produjo un error al imprimir comprobante: " + this.order.getId());
         logger.error(var7.getMessage());
         //logger.error(var7);
         var7.printStackTrace();
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

   public OrderService getOrderService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (OrderService)context.getBean("orderService");
   }

   private void openPdf() {
      if (Desktop.isDesktopSupported()) {
         try {
            File myFile = new File("C:\\facilvirtual\\tmp\\" + this.getFileName());
            Desktop.getDesktop().open(myFile);
         } catch (IOException var2) {
            logger.error("Se produjo un error al abrir pdf de venta: " + this.order.getId());
         }
      }

   }

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
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
