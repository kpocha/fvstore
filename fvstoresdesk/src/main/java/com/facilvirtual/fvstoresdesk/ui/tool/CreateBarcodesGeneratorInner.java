package com.facilvirtual.fvstoresdesk.ui.tool;

import com.facilvirtual.fvstoresdesk.model.Product;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

class CreateBarcodesGeneratorInner extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private List<Product> products = new ArrayList();
   private int maxProducts = 3000;
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private CreateBarcodesGenerator dialog;
   private static Logger LOGGER = Logger.getLogger("CreateBarcodesGeneratorInner");

   public CreateBarcodesGeneratorInner(Display display, ProgressBar progressBar, List<Product> products, String fileName, Label lblProgressBarTitle, Button cancelButton, CreateBarcodesGenerator dialog) {
      this.display = display;
      this.progressBar = progressBar;
      this.products = products;
      this.fileName = fileName;
      this.lblProgressBarTitle = lblProgressBarTitle;
      this.cancelButton = cancelButton;
      this.dialog = dialog;
   }

   public void run() {
      try {
         Document document = new Document(PageSize.A4, 0.0F, 0.0F, 0.0F, 0.0F);
         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(this.fileName));
         document.open();
         PdfContentByte cb = writer.getDirectContent();
         int posX = 0;
         int posY = 800;
         int numprod = 0;

         for(Iterator<Product> it = this.products.iterator(); it.hasNext() && numprod <= this.maxProducts;) {
            try {
               Product product = it.next();
               ++numprod;
               
               if (product != null && numprod <= this.maxProducts) {
                  posX = 150;
                  this.createBarcodeForProduct(document, cb, product, posX, posY);
               }

               if (it.hasNext()) {
                  product = it.next();
                  ++numprod;
               } else {
                  product = null;
               }

               if (product != null && numprod <= this.maxProducts) {
                  posX = 300;
                  this.createBarcodeForProduct(document, cb, product, posX, posY);
               }

               if (it.hasNext()) {
                  product = it.next();
                  ++numprod;
               } else {
                  product = null;
               }

               if (product != null && numprod <= this.maxProducts) {
                  posX = 450;
                  this.createBarcodeForProduct(document, cb, product, posX, posY);
               }

               posY -= 70;
               if (numprod % 33 == 0) {
                  document.newPage();
                  posY = 800;
               }
               
               this.display.asyncExec(() -> {
                   if (!progressBar.isDisposed()) {
                       progressBar.setSelection(progressBar.getSelection() + 3);
                   }
               });
            } catch (Exception var10) {
            }
         }

         document.close();
         
         this.display.asyncExec(() -> {
             if (!progressBar.isDisposed()) {
                 progressBar.setVisible(false);
                 lblProgressBarTitle.setText("Se completó la generación de códigos de barra.");
                 lblProgressBarTitle.setBounds(48, 29, 250, 17);
                 cancelButton.setText("Aceptar");
             }
         });
      } catch (Exception var11) {
         this.display.asyncExec(() -> {
             dialog.alert("No se pudo guardar el archivo porque está siendo utilizado por otro programa.");
             dialog.close();
         });
      }
   }

   private void createBarcodeForProduct(Document document, PdfContentByte cb, Product product, int posX, int posY) {
      try {
         cb.saveState();
         BaseFont bf = BaseFont.createFont();
         cb.beginText();
         cb.setFontAndSize(bf, 8.0F);
         cb.showTextAligned(1, product.getDescriptionForLabel(), (float)posX, (float)posY, 0.0F);
         posY -= 40;
         posX -= 20;
         cb.endText();
         cb.restoreState();
         Barcode128 code128 = new Barcode128();
         code128.setCode(product.getBarCode());
         cb.saveState();
         cb.concatCTM(1.0F, 0.0F, 0.0F, 1.0F, (float)posX, (float)posY);
         code128.placeBarcode(cb, (BaseColor)null, (BaseColor)null);
         cb.restoreState();
      } catch (Exception var8) {
         LOGGER.error("Se produjo un error al generar el código de barras para el producto con ID: " + product.getId());
         LOGGER.error(var8.getMessage());
         LOGGER.error(var8);
      }
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }
}
