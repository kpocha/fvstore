package com.facilvirtual.fvstoresdesk.print;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.service.ProductService;
import com.facilvirtual.fvstoresdesk.util.FVImageUtils;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.Barcode128;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.ImagePrint;
import net.sf.paperclips.LinePrint;
import net.sf.paperclips.PaperClips;
import net.sf.paperclips.PrintJob;
import net.sf.paperclips.TextPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.springframework.context.ApplicationContext;

public class TicketBCCreator {
   protected static Logger logger = LoggerFactory.getLogger("TicketCreator");
   private Order order;
   private String fileName;
   private Shell shell;
   private int lineHeight = 100;
   private int posY = 20;
   private WorkstationConfig workstationConfig;
   private AppConfig appConfig;

   public TicketBCCreator(Order order, AppConfig appConfig, WorkstationConfig workstationConfig, Shell shell) {
      this.order = order;
      this.appConfig = appConfig;
      this.workstationConfig = workstationConfig;
      this.fileName = "comprobante_" + order.getId() + ".txt";
      this.shell = shell;
   }

   public WorkstationConfig getWorkstationConfig() {
      return this.workstationConfig;
   }

   public void setWorkstationConfig(WorkstationConfig workstationConfig) {
      this.workstationConfig = workstationConfig;
   }

   public int getLineHeight() {
      return this.lineHeight;
   }

   public void setLineHeight(int lineHeight) {
      this.lineHeight = lineHeight;
   }

   public int getPosY() {
      return this.posY;
   }

   public void setPosY(int posY) {
      this.posY = posY;
   }

   public Order getOrder() {
      return this.order;
   }

   public void setOrder(Order order) {
      this.order = order;
   }

   public void print() {
      logger.info("Imprimiendo venta (Ticket): " + this.order.getId());

      try {
         GridPrint grid = new GridPrint("l:80, l:35, r:55", new DefaultGridLook(2, 2));
         FontData fontDataTitle = new FontData("Tahoma", 10, 1);
         FontData fontDataCommon = new FontData("Tahoma", 9, 0);
         FontData fontDataCommonBold = new FontData("Tahoma", 9, 1);
         FontData fontDataSmall = new FontData("Tahoma", 7, 0);
         FontData fontDataBr = new FontData("Tahoma", 3, 0);
         FontData fontDataPowered = new FontData("Tahoma", 8, 2);
         boolean foundLogo = false;

         try {
            if (this.appConfig.isCompanyLogoAvailable()) {
               int logoW = (int)this.getPoints(60);
               int logoH = (int)this.getPoints(20);
               String fileOut = "C:\\facilvirtual\\images\\" + this.appConfig.getCompanyLogo();
               Image origImage = new Image(Display.getCurrent(), fileOut);
               Image scaledImage = FVImageUtils.scaleTo(origImage, logoW, logoH);
               grid.add(new ImagePrint(scaledImage.getImageData()), -1, 16777216);
               foundLogo = true;
               grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
            }
         } catch (Exception var22) {
         }

         if (!foundLogo && !"".equals(this.getAppConfig().getCompanyBusinessName().trim())) {
            grid.add(new TextPrint(this.getAppConfig().getCompanyBusinessName().toUpperCase(), fontDataTitle), -1, 16777216);
         }

         if (!"".equals(this.getAppConfig().getCompanyAddressLine1ToPrint(40).trim())) {
            grid.add(new TextPrint(this.getAppConfig().getCompanyAddressLine1ToPrint(40), fontDataCommon), -1, 16777216);
         }

         if (!"".equals(this.getAppConfig().getCompanyAddressLine2ToPrint(40).trim())) {
            grid.add(new TextPrint(this.getAppConfig().getCompanyAddressLine2ToPrint(40), fontDataCommon), -1, 16777216);
         }

         if (!"".equals(this.getAppConfig().getCompanyPhoneForTicket().trim())) {
            grid.add(new TextPrint(this.getAppConfig().getCompanyPhoneForTicket(), fontDataCommon), -1, 16777216);
         }

         try {
            if (!this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
               grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
               if (!"".equals(this.getAppConfig().getCompanyNameToPrint(40).trim())) {
                  grid.add(new TextPrint(this.appConfig.getCompanyNameToPrint(40).toUpperCase(), fontDataCommon), -1, 16384);
               }

               grid.add(new TextPrint("CUIT: " + this.appConfig.getCompanyCuitToPrint(), fontDataCommon), -1, 16384);
               if (!"".equals(this.getAppConfig().getCompanyGrossIncomeNumberToPrint().trim())) {
                  grid.add(new TextPrint("Ingresos Brutos: " + this.appConfig.getCompanyGrossIncomeNumberToPrint(), fontDataCommon), -1, 16384);
               }

               grid.add(new TextPrint("Inicio de Actividades: " + this.appConfig.getCompanyStartActivitiesDateToPrint(), fontDataCommon), -1, 16384);
               grid.add(new TextPrint("IVA " + this.appConfig.getCompanyVatCondition().getNameToPrint().toUpperCase(), fontDataCommon), -1, 16384);
               if (this.order.getCustomer() == null || this.order.getCustomer().getId() == 1L) {
                  grid.add(new TextPrint("A CONSUMIDOR FINAL", fontDataCommon), -1, 16384);
               }
            }
         } catch (Exception var23) {
         }

         grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
         if (this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura B")) {
            grid.add(new TextPrint("Tique Factura \"B\"", fontDataCommonBold));
            grid.add(new TextPrint("Nro. " + this.order.getReceiptNumberToPrint(this.getAppConfigService().getAppConfig()), fontDataCommon), 2, 131072);
         } else if (this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura C")) {
            grid.add(new TextPrint("Tique Factura \"C\"", fontDataCommonBold));
            grid.add(new TextPrint("Nro. " + this.order.getReceiptNumberToPrint(this.getAppConfigService().getAppConfig()), fontDataCommon), 2, 131072);
         } else {
            grid.add(new TextPrint("P.V. Nro. " + this.order.getPtoVtaNroToPrint(this.getAppConfigService().getAppConfig()), fontDataCommon), -1, 16384);
            grid.add(new TextPrint("Nro. T. " + this.order.getNroTicketToPrint(this.getAppConfigService().getAppConfig()), fontDataCommon), -1, 16384);
         }

         grid.add(new TextPrint("Fecha " + this.order.getCreationDateForTicket(), fontDataCommon));
         grid.add(new TextPrint("Hora " + this.order.getCreationHourForTicket(), fontDataCommon), 2, 131072);
         if (this.order.getCustomer() != null && this.order.getCustomer().getId() != 1L) {
            grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
            grid.add(new TextPrint(this.order.getCustomer().getFullName().toUpperCase(), fontDataCommon), -1, 16384);

            try {
               if (!this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
                  if (this.order.getCustomer().isHomeCustomer()) {
                     grid.add(new TextPrint("DNI: " + this.order.getCustomer().getDniToPrint(), fontDataCommon), -1, 16384);
                  } else {
                     grid.add(new TextPrint("CUIT: " + this.order.getCustomer().getCuitToPrint(), fontDataCommon), -1, 16384);
                  }

                  grid.add(new TextPrint("Cond. IVA: " + this.order.getCustomer().getVatCondition().getNameToPrint().toUpperCase(), fontDataCommon), -1, 16384);
               }

               if (!"".equals(this.order.getCustomer().getAddressToPrint(40))) {
                  grid.add(new TextPrint(this.order.getCustomer().getAddressToPrint(40).toUpperCase(), fontDataCommon), -1, 16384);
               }

               if (!"".equals(this.order.getCustomer().getCityToPrint(40))) {
                  grid.add(new TextPrint(this.order.getCustomer().getCityToPrint(40).toUpperCase(), fontDataCommon), -1, 16384);
               }
            } catch (Exception var21) {
            }
         }

         grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
         grid.add(new TextPrint("Cant./Precio Unit.", fontDataCommon), -1, 16384);
         grid.add(new TextPrint("Descripción", fontDataCommon));
         grid.add(new TextPrint("Importe", fontDataCommon), 2, 131072);
         grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
         List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(this.getOrder());
         Iterator var29 = orderLines.iterator();

         while(var29.hasNext()) {
            OrderLine orderLine = (OrderLine)var29.next();
            if (orderLine.getQty() == 1.0) {
               if (this.getWorkstationConfig().isPrintCodeInTickets()) {
                  grid.add(new TextPrint(orderLine.getDescriptionWithCodeToPrint(13), fontDataCommon), 2, 16384);
               } else {
                  grid.add(new TextPrint(orderLine.getDescriptionToPrint(20), fontDataCommon), 2, 16384);
               }

               grid.add(new TextPrint(orderLine.getSubtotalToPrint(), fontDataCommon));
            } else {
               grid.add(new TextPrint(orderLine.getQtyDescriptionForTicket(), fontDataCommon), -1, 16384);
               if (this.getWorkstationConfig().isPrintCodeInTickets()) {
                  grid.add(new TextPrint(orderLine.getDescriptionWithCodeToPrint(13), fontDataCommon), 2, 16384);
               } else {
                  grid.add(new TextPrint(orderLine.getDescriptionToPrint(20), fontDataCommon), 2, 16384);
               }

               grid.add(new TextPrint(orderLine.getSubtotalToPrint(), fontDataCommon));
            }
         }

         grid.add(new TextPrint(" ", fontDataCommon), -1, 16777216);

         try {
            if (this.getOrder().getDiscountSurcharge() != 0.0) {
               grid.add(new TextPrint("SUBTOTAL", fontDataCommon), 2, 16384);
               grid.add(new TextPrint(this.getOrder().getSubtotalToPrint(), fontDataCommon));
               grid.add(new TextPrint("Dto./Recargo", fontDataCommon), 2, 16384);
               grid.add(new TextPrint(this.getOrder().getDiscountSurchargeToDisplay(), fontDataCommon));
            }
         } catch (Exception var20) {
         }

         grid.add(new TextPrint("TOTAL  $", fontDataTitle));
         grid.add(new TextPrint(this.order.getTotalToPrint(), fontDataTitle), 2, 131072);
         grid.add(new TextPrint(" ", fontDataTitle), -1, 16777216);
         if (!"".equals(this.getAppConfig().getTextFooterTicket().trim())) {
            grid.add(new LinePrint(256), -1);
            grid.add(new TextPrint(this.getAppConfig().getTextFooterTicket(), fontDataCommon), -1, 16777216);
         }

         grid.add(new LinePrint(256), -1);
         grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
         if (this.order.hasAfipCae() && this.order.getReceiptType() != null && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
            try {
               Image image = new Image(Display.getCurrent(), "C:\\facilvirtual\\images\\logo_afip_ticket.jpg");
               grid.add(new ImagePrint(image.getImageData()), 1, 131072);
               grid.add(new TextPrint("                             Comprobante Autorizado", fontDataSmall), 2, 16384);
            } catch (Exception var19) {
            }

            try {
               Barcode128 code128 = new Barcode128();
               code128.setCode(this.order.getAfipBarCode());
               java.awt.Image image = code128.createAwtImage(Color.BLACK, Color.WHITE);
               ImageData image1 = FVImageUtils.convertAWTImageToSWT(image);
               int w = (int)this.getPoints(57);
               int h = (int)this.getPoints(19);
               Image origImage = new Image(Display.getCurrent(), image1);
               Image scaledImage = FVImageUtils.scaleTo(origImage, w, h);
               grid.add(new ImagePrint(scaledImage.getImageData()), -1, 16777216);
               grid.add(new TextPrint(this.order.getAfipBarCode(), fontDataSmall), -1, 16777216);
               grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
            } catch (Exception var18) {
            }

            try {
               grid.add(new TextPrint("CAE: " + this.order.getAfipCae(), fontDataCommon), -1, 16384);
               grid.add(new TextPrint("Vto. CAE: " + this.order.getAfipCaeFchVtoToDisplay(), fontDataCommon), -1, 16384);
               grid.add(new TextPrint(" ", fontDataBr), -1, 16777216);
            } catch (Exception var17) {
            }
         }

         grid.add(new TextPrint("Comprobante generado con Fácil Virtual", fontDataPowered), -1, 131072);
         PrintJob job = (new PrintJob("Ticket Nro " + this.order.getReceiptNumberToDisplay(), grid)).setMargins(14);
         PaperClips.print(job, new PrinterData());
      } catch (Exception var24) {
         logger.error("Se produjo un error al imprimir venta (ticket no fiscal)" + this.order.getId());
         logger.error("Message:" + var24.getMessage());
         logger.error("Exception:" + var24);
         var24.printStackTrace();
      }

   }

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   private float getPoints(int mm) {
      return Utilities.millimetersToPoints(new Float((float)mm));
   }

   private void newLine() {
      this.posY += this.getLineHeight();
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

   public AppConfig getAppConfig() {
      return this.appConfig;
   }

   public void setAppConfig(AppConfig appConfig) {
      this.appConfig = appConfig;
   }

   public OrderService getOrderService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (OrderService)context.getBean("orderService");
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
