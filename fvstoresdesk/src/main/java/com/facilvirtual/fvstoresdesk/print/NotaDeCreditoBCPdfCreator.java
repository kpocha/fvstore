package com.facilvirtual.fvstoresdesk.print;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.NotaDeCredito;
import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.service.ProductService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

public class NotaDeCreditoBCPdfCreator {
   protected static Logger logger = LoggerFactory.getLogger("NotaDeCreditoBCPdfCreator");
   private NotaDeCredito notaDeCredito;
   private String fileName;
   private Shell shell;

   public NotaDeCreditoBCPdfCreator(NotaDeCredito notaDeCredito, Shell shell) {
      this.notaDeCredito = notaDeCredito;
      this.fileName = "nota_de_credito_" + notaDeCredito.getId() + ".pdf";
      this.shell = shell;
   }

   public void createPdf() {
      logger.info("Imprimiendo nota de crédito: " + this.notaDeCredito.getId());

      try {
         AppConfig appConfig = this.getAppConfigService().getAppConfig();
         int page = 1;
         Document document = new Document(PageSize.A4);
         document.setMargins(this.getPoints(10), this.getPoints(10), this.getPoints(12), this.getPoints(12));
         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\facilvirtual\\tmp\\" + this.getFileName()));
         document.open();
         BaseFont bf = BaseFont.createFont();
         Font bold = new Font(FontFamily.HELVETICA, 12.0F, 1);
         BaseFont bf2 = bold.getCalculatedBaseFont(false);
         Font boldItalic = new Font(FontFamily.HELVETICA, 12.0F, 3);
         BaseFont bf3 = boldItalic.getCalculatedBaseFont(false);
         Font fItalic = new Font(FontFamily.HELVETICA, 12.0F, 2);
         BaseFont bf4 = fItalic.getCalculatedBaseFont(false);
         CMYKColor colorGrisClaro = new CMYKColor(0.0F, 0.0F, 0.0F, 0.1F);
         PdfContentByte canvas = writer.getDirectContent();
         File f = new File("C:\\facilvirtual\\images\\template_factura.jpg");
         if (f.exists() && !f.isDirectory()) {
            Image image1 = Image.getInstance("C:\\facilvirtual\\images\\template_factura.jpg");
            float docW = PageSize.A4.getWidth();
            float docH = PageSize.A4.getHeight();
            image1.scaleToFit(docW, docH);
            image1.setAbsolutePosition(0.0F, 0.0F);
         }

         canvas.setLineWidth(0.5F);
         canvas.setColorStroke(BaseColor.BLACK);
         canvas.rectangle(this.getPoints(10), this.getPoints(235), this.getPoints(190), this.getPoints(50));
         canvas.stroke();
         canvas.moveTo(this.getPoints(105), this.getPoints(235));
         canvas.lineTo(this.getPoints(105), this.getPoints(266));
         canvas.closePathStroke();
         canvas.setLineWidth(0.5F);
         canvas.setColorFill(colorGrisClaro);
         canvas.rectangle(this.getPoints(97), this.getPoints(266), this.getPoints(16), this.getPoints(5));
         canvas.fill();
         canvas.setColorFill(BaseColor.BLACK);
         canvas.setLineWidth(0.5F);
         canvas.setColorStroke(BaseColor.BLACK);
         canvas.rectangle(this.getPoints(97), this.getPoints(271), this.getPoints(16), this.getPoints(14));
         canvas.stroke();
         PdfPTable table = new PdfPTable(3);
         table.setWidthPercentage(100.0F);
         float[] columnWidths = new float[]{46.0F, 8.0F, 46.0F};
         table.setWidths(columnWidths);
         PdfPCell leftCell = new PdfPCell();
         leftCell.setFixedHeight(this.getPoints(24));
         leftCell.setVerticalAlignment(5);
         boolean foundLogo = false;

         try {
            if (appConfig.isCompanyLogoAvailable()) {
               float logoW = this.getPoints(60);
               float logoH = this.getPoints(20);
               Image image = Image.getInstance("C:\\facilvirtual\\images\\" + appConfig.getCompanyLogo());
               image.scaleToFit(logoW, logoH);
               image.setAlignment(5);
               leftCell.addElement(image);
               foundLogo = true;
            }
         } catch (Exception var28) {
         }

         leftCell.setBorder(0);
         table.addCell(leftCell);
         if (!foundLogo) {
            canvas.beginText();
            canvas.setFontAndSize(bf2, 14.0F);
            canvas.showTextAligned(1, appConfig.getCompanyBusinessNameToPrint(30), this.getPoints(54), this.getPoints(270), 0.0F);
            canvas.endText();
         }

         canvas.beginText();
         int posY = 256;
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(1, appConfig.getCompanyNameToPrint(50), this.getPoints(54), this.getPoints(posY), 0.0F);
         posY -= 5;
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(1, appConfig.getCompanyAddressLine1ToPrint(50), this.getPoints(54), this.getPoints(posY), 0.0F);
         posY -= 4;
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(1, appConfig.getCompanyAddressLine2ToPrint(50), this.getPoints(54), this.getPoints(posY), 0.0F);
         posY -= 5;
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(1, appConfig.getCompanyPhone(), this.getPoints(54), this.getPoints(posY), 0.0F);
         posY -= 5;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(1, appConfig.getCompanyVatCondition().getNameToPrint(), this.getPoints(54), this.getPoints(posY), 0.0F);
         if (this.notaDeCredito.hasAfipCae() && this.notaDeCredito.getCbteTipo().getName().equalsIgnoreCase("Nota de Crédito C")) {
            canvas.setFontAndSize(bf2, 30.0F);
            canvas.showTextAligned(0, "C", this.getPoints(101), this.getPoints(276), 0.0F);
            canvas.setFontAndSize(bf, 8.0F);
            canvas.showTextAligned(0, "CÓD. 13", this.getPoints(99.5F), this.getPoints(272), 0.0F);
         } else if (this.notaDeCredito.hasAfipCae() && this.notaDeCredito.getCbteTipo().getName().equalsIgnoreCase("Nota de Crédito B")) {
            canvas.setFontAndSize(bf2, 30.0F);
            canvas.showTextAligned(0, "B", this.getPoints(101), this.getPoints(276), 0.0F);
            canvas.setFontAndSize(bf, 8.0F);
            canvas.showTextAligned(0, "CÓD. 08", this.getPoints(99.5F), this.getPoints(272), 0.0F);
         } else if (!this.notaDeCredito.hasAfipCae()) {
            canvas.setFontAndSize(bf2, 30.0F);
            canvas.showTextAligned(0, "X", this.getPoints(101), this.getPoints(276), 0.0F);
            canvas.setFontAndSize(bf, 8.0F);
            canvas.showTextAligned(0, "", this.getPoints(99.5F), this.getPoints(272), 0.0F);
         }

         canvas.setFontAndSize(bf, 8.0F);
         canvas.showTextAligned(0, "ORIGINAL", this.getPoints(98.3F), this.getPoints(267.5F), 0.0F);
         PdfPCell centerCell = new PdfPCell();
         centerCell.setBorder(0);
         table.addCell(centerCell);
         Paragraph right = new Paragraph("");
         right.setIndentationLeft(30.0F);
         PdfPCell rightCell = new PdfPCell();
         rightCell.addElement(right);
         rightCell.setBorder(0);
         table.addCell(rightCell);
         document.add(table);
          posY = 276;
         canvas.setFontAndSize(bf2, 18.0F);
         canvas.showTextAligned(0, "NOTA DE CRÉDITO", this.getPoints(120), this.getPoints(posY), 0.0F);
         posY = posY - 8;
         canvas.setFontAndSize(bf2, 12.0F);
         canvas.showTextAligned(0, this.notaDeCredito.getNumberToPrint(this.getAppConfigService().getAppConfig()), this.getPoints(120), this.getPoints(posY), 0.0F);
         posY -= 6;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Fecha de Emisión: ", this.getPoints(120), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf2, 10.0F);
         canvas.showTextAligned(0, this.notaDeCredito.getCbteFchToPrint(), this.getPoints(149), this.getPoints(posY), 0.0F);
         posY = 247;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "CUIT: ", this.getPoints(120), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(0, appConfig.getCompanyCuitToPrint(), this.getPoints(130), this.getPoints(posY), 0.0F);
         posY = posY - 5;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Ingresos Brutos: ", this.getPoints(120), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(0, appConfig.getCompanyGrossIncomeNumberToPrint(), this.getPoints(147), this.getPoints(posY), 0.0F);
         posY -= 5;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Inicio de Actividades: ", this.getPoints(120), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(0, appConfig.getCompanyStartActivitiesDateToPrint(), this.getPoints(154), this.getPoints(posY), 0.0F);
         canvas.endText();
         if (this.notaDeCredito.getAfipConcepto() == 2 || this.notaDeCredito.getAfipConcepto() == 3) {
            posY -= 10;
            canvas.setLineWidth(0.5F);
            canvas.setColorStroke(BaseColor.BLACK);
            canvas.rectangle(this.getPoints(10), this.getPoints(posY), this.getPoints(190), this.getPoints(7));
            canvas.stroke();
            posY += 2;
            canvas.beginText();
            canvas.setFontAndSize(bf2, 10.0F);
            canvas.showTextAligned(0, "Período Facturado Desde: ", this.getPoints(12), this.getPoints(posY), 0.0F);
            canvas.setFontAndSize(bf, 10.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getAfipFchServDesdeToPrint(), this.getPoints(57), this.getPoints(posY), 0.0F);
            canvas.setFontAndSize(bf2, 10.0F);
            canvas.showTextAligned(0, "Hasta: ", this.getPoints(87), this.getPoints(posY), 0.0F);
            canvas.setFontAndSize(bf, 10.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getAfipFchServHastaToPrint(), this.getPoints(99), this.getPoints(posY), 0.0F);
            canvas.setFontAndSize(bf2, 10.0F);
            canvas.showTextAligned(0, "Fecha de Vto. para el pago: ", this.getPoints(128), this.getPoints(posY), 0.0F);
            canvas.setFontAndSize(bf, 10.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getAfipFchVtoPagoToPrint(), this.getPoints(175), this.getPoints(posY), 0.0F);
            canvas.endText();
            ++posY;
         }

         posY -= 26;
         canvas.setLineWidth(0.5F);
         canvas.setColorStroke(BaseColor.BLACK);
         canvas.rectangle(this.getPoints(10), this.getPoints(posY), this.getPoints(190), this.getPoints(22));
         canvas.stroke();
         posY += 17;
         canvas.beginText();
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Nombre: ", this.getPoints(15), this.getPoints(posY), 0.0F);
         if (this.notaDeCredito.getCustomer() != null) {
            canvas.setFontAndSize(bf, 9.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getCustomerNameToPrint(40), this.getPoints(30), this.getPoints(posY), 0.0F);
         }

         if (this.notaDeCredito.getCustomer() != null && this.notaDeCredito.getCustomer().isHomeCustomer()) {
            canvas.setFontAndSize(bf2, 9.0F);
            canvas.showTextAligned(0, "DNI: ", this.getPoints(110), this.getPoints(posY), 0.0F);
            if (this.notaDeCredito.getCustomer() != null) {
               canvas.setFontAndSize(bf, 9.0F);
               canvas.showTextAligned(0, this.notaDeCredito.getCustomer().getDniToPrint(), this.getPoints(118), this.getPoints(posY), 0.0F);
            }
         } else {
            canvas.setFontAndSize(bf2, 9.0F);
            canvas.showTextAligned(0, "CUIT: ", this.getPoints(110), this.getPoints(posY), 0.0F);
            if (this.notaDeCredito.getCustomer() != null) {
               canvas.setFontAndSize(bf, 9.0F);
               canvas.showTextAligned(0, this.notaDeCredito.getCustomer().getCuitToPrint(), this.getPoints(120), this.getPoints(posY), 0.0F);
            }
         }

         posY -= 5;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Domicilio: ", this.getPoints(15), this.getPoints(posY), 0.0F);
         if (this.notaDeCredito.getCustomer() != null) {
            canvas.setFontAndSize(bf, 9.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getCustomer().getAddressToPrint(40), this.getPoints(32), this.getPoints(posY), 0.0F);
         }

         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Localidad: ", this.getPoints(110), this.getPoints(posY), 0.0F);
         if (this.notaDeCredito.getCustomer() != null) {
            canvas.setFontAndSize(bf, 9.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getCustomer().getCityToPrint(40), this.getPoints(127), this.getPoints(posY), 0.0F);
         }

         posY -= 5;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Cond. IVA: ", this.getPoints(15), this.getPoints(posY), 0.0F);
         if (this.notaDeCredito.getCustomer() != null) {
            canvas.setFontAndSize(bf, 9.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getCustomer().getVatCondition().getNameToPrint(), this.getPoints(33), this.getPoints(posY), 0.0F);
         }

         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Provincia: ", this.getPoints(110), this.getPoints(posY), 0.0F);
         if (this.notaDeCredito.getCustomer() != null) {
            canvas.setFontAndSize(bf, 9.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getCustomer().getProvinceToPrint(40), this.getPoints(127), this.getPoints(posY), 0.0F);
         }

         posY -= 5;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Cond. Venta: ", this.getPoints(15), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(0, "Contado", this.getPoints(36), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Teléfono: ", this.getPoints(110), this.getPoints(posY), 0.0F);
         if (this.notaDeCredito.getCustomer() != null) {
            canvas.setFontAndSize(bf, 9.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getCustomer().getPhoneToPrint(40), this.getPoints(126), this.getPoints(posY), 0.0F);
         }

         posY -= 5;
         canvas.endText();
         posY -= 5;
         canvas.setLineWidth(0.5F);
         canvas.setColorStroke(BaseColor.BLACK);
         canvas.setColorFill(colorGrisClaro);
         canvas.rectangle(this.getPoints(10), this.getPoints(posY), this.getPoints(190), this.getPoints(6));
         canvas.fillStroke();
         canvas.beginText();
         canvas.setColorFill(BaseColor.BLACK);
         posY += 2;
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Código", this.getPoints(12), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Descripción ", this.getPoints(42), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Cantidad", this.getPoints(132), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "P. Unitario", this.getPoints(152), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf2, 9.0F);
         canvas.showTextAligned(0, "Importe", this.getPoints(177), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf, 9.0F);
         posY -= 7;
         canvas.showTextAligned(2, this.notaDeCredito.getProductCodeToPrint(13), this.getPoints(38), this.getPoints(posY), 0.0F);
         canvas.showTextAligned(0, this.notaDeCredito.getProductDescriptionLine1ToPrint(45), this.getPoints(42), this.getPoints(posY), 0.0F);
         canvas.showTextAligned(2, this.notaDeCredito.getProductQtyToPrint(), this.getPoints(148), this.getPoints(posY), 0.0F);
         canvas.showTextAligned(2, this.notaDeCredito.getProductPriceToPrint(), this.getPoints(173), this.getPoints(posY), 0.0F);
         canvas.showTextAligned(2, this.notaDeCredito.getProductSubtotalToPrint(), this.getPoints(198), this.getPoints(posY), 0.0F);
         String descLine2 = this.notaDeCredito.getProductDescriptionLine2ToPrint(40);
         if (descLine2.length() > 0) {
            posY -= 4;
            canvas.showTextAligned(0, descLine2, this.getPoints(42), this.getPoints(posY), 0.0F);
         }

         posY -= 15;
         if (this.notaDeCredito.hasCbteAsoc()) {
            canvas.setFontAndSize(bf2, 9.0F);
            canvas.showTextAligned(0, "Comprobante Asociado", this.getPoints(12), this.getPoints(posY), 0.0F);
            posY -= 5;
            String cbteAsocStr = this.notaDeCredito.getCbteAsocTipo().getName() + " Nro. " + this.notaDeCredito.getCbteAsocNroToPrint();
            canvas.setFontAndSize(bf, 9.0F);
            canvas.showTextAligned(0, cbteAsocStr, this.getPoints(12), this.getPoints(posY), 0.0F);
            posY -= 5;
         }

         canvas.endText();
         posY -= 5;
         if (posY < 80) {
            document.newPage();
            ++page;
            posY = 277;
         }

         if (page == 1) {
            posY = 85;
         }

         posY -= 30;
         canvas.setLineWidth(0.8F);
         canvas.rectangle(this.getPoints(10), this.getPoints(posY), this.getPoints(190), this.getPoints(30));
         canvas.stroke();
         posY += 22;
         canvas.beginText();
         canvas.setFontAndSize(bf2, 10.0F);
         canvas.showTextAligned(2, "Total: $", this.getPoints(165), this.getPoints(posY), 0.0F);
         canvas.setFontAndSize(bf2, 10.0F);
         canvas.showTextAligned(2, this.notaDeCredito.getTotalToPrint(), this.getPoints(195), this.getPoints(posY), 0.0F);
         canvas.endText();
         posY -= 32;
         canvas.setLineWidth(0.5F);
         canvas.rectangle(this.getPoints(10), this.getPoints(posY), this.getPoints(190), this.getPoints(9));
         canvas.stroke();
         canvas.beginText();
         posY += 5;
         posY -= 4;
         canvas.endText();
         Image logoFV;
         if (this.notaDeCredito.hasAfipCae()) {
            posY -= 6;
            canvas.beginText();
            canvas.setFontAndSize(bf2, 10.0F);
            canvas.showTextAligned(2, "CAE Nº:", this.getPoints(160), this.getPoints(posY), 0.0F);
            canvas.setFontAndSize(bf, 10.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getAfipCae(), this.getPoints(163), this.getPoints(posY), 0.0F);
            posY -= 5;
            canvas.setFontAndSize(bf2, 10.0F);
            canvas.showTextAligned(2, "Fecha de Vto. de CAE:", this.getPoints(160), this.getPoints(posY), 0.0F);
            canvas.setFontAndSize(bf, 10.0F);
            canvas.showTextAligned(0, this.notaDeCredito.getAfipCaeFchVtoToDisplay(), this.getPoints(163), this.getPoints(posY), 0.0F);
            canvas.endText();
            logoFV = Image.getInstance("C:\\facilvirtual\\images\\logo_afip_comprobante.jpg");
            logoFV.scaleToFit(this.getPoints(26), this.getPoints(7));
            logoFV.setAbsolutePosition(this.getPoints(12), this.getPoints(posY));
            document.add(logoFV);
            posY += 3;
            canvas.beginText();
            canvas.setFontAndSize(bf3, 9.0F);
            canvas.showTextAligned(0, "Comprobante Autorizado", this.getPoints(40), this.getPoints(posY), 0.0F);
            canvas.endText();
            posY -= 16;
            Barcode128 code128 = new Barcode128();
            code128.setCode(this.notaDeCredito.getAfipBarCode());
            Image code128Image = code128.createImageWithBarcode(canvas, (BaseColor)null, (BaseColor)null);
            code128Image.setAbsolutePosition(this.getPoints(12), this.getPoints(posY));
            document.add(code128Image);
         } else {
            posY -= 6;
            canvas.beginText();
            canvas.setFontAndSize(bf, 10.0F);
            canvas.showTextAligned(0, "DOCUMENTO SIN VALIDEZ FISCAL", this.getPoints(12), this.getPoints(posY), 0.0F);
            canvas.endText();
            posY -= 7;
         }

         posY -= 5;
         canvas.beginText();
         canvas.setFontAndSize(bf, 9.0F);
         canvas.showTextAligned(2, "Comprobante generado con ", this.getPoints(173), this.getPoints(posY), 0.0F);
         canvas.endText();
         --posY;
         logoFV = Image.getInstance("C:\\facilvirtual\\images\\logo_facilvirtual_comprobante.jpg");
         logoFV.scaleToFit(this.getPoints(26), this.getPoints(6));
         logoFV.setAbsolutePosition(this.getPoints(174), this.getPoints(posY));
         document.add(logoFV);
         document.close();
         this.openPdf();
      } catch (FileNotFoundException var29) {
         logger.error("Se produjo un error al imprimir comprobante: " + this.notaDeCredito.getId());
         logger.error(var29.getMessage());
        // logger.error(var29);
         this.alert("Error al crear el archivo pdf. El archivo se encuentra abierto. Cierra el archivo e inténtalo de nuevo.");
      } catch (Exception var30) {
         logger.error("Se produjo un error al imprimir comprobante: " + this.notaDeCredito.getId());
         logger.error(var30.getMessage());
         //logger.error(var30);
      }

   }

   private float getPoints(int mm) {
      return Utilities.millimetersToPoints(new Float((float)mm));
   }

   private float getPoints(Float mm) {
      return Utilities.millimetersToPoints(mm);
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
            logger.error("Se produjo un error al abrir pdf de venta: " + this.notaDeCredito.getId());
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
