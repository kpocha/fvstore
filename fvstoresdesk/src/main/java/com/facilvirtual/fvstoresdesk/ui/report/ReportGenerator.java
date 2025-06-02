package com.facilvirtual.fvstoresdesk.ui.report;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import com.facilvirtual.fvstoresdesk.model.CreditCard;
import com.facilvirtual.fvstoresdesk.model.DebitCard;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;

class ReportGenerator extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private AbstractFVDialog dialog;
   private Date startDate;
   private Date endDate;
   private OrderService orderService;

   public ReportGenerator(Display display, ProgressBar progressBar, Date startDate, Date endDate, OrderService orderService, String fileName, Label lblProgressBarTitle, Button cancelButton, AbstractFVDialog dialog) {
      this.display = display;
      this.progressBar = progressBar;
      this.fileName = fileName;
      this.lblProgressBarTitle = lblProgressBarTitle;
      this.cancelButton = cancelButton;
      this.dialog = dialog;
      this.startDate = startDate;
      this.endDate = endDate;
      this.orderService = orderService;
   }
   @Override
   public void run() {
      try {
         XSSFWorkbook workbook = new XSSFWorkbook();
         XSSFSheet sheet = workbook.createSheet("Ventas por medios de pago");
         XSSFCellStyle cellStyle = workbook.createCellStyle();
         Color color = new Color(50, 135, 54);
         byte[] rgb = new byte[]{(byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue()};
         cellStyle.setFillForegroundColor(new XSSFColor(rgb, null));
         cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         cellStyle.setBorderBottom(BorderStyle.MEDIUM);
         cellStyle.setBorderTop(BorderStyle.MEDIUM);
         cellStyle.setBorderRight(BorderStyle.MEDIUM);
         cellStyle.setBorderLeft(BorderStyle.MEDIUM);
         XSSFFont font = workbook.createFont();
         font.setBold(true);
         font.setColor((short)9);
         cellStyle.setFont(font);
         XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
         XSSFFont fontTitle = workbook.createFont();
         fontTitle.setBold(true);
         cellStyleTitle.setFont(fontTitle);
         XSSFCellStyle cellStyleTotal = workbook.createCellStyle();
         Color colorTotal = new Color(221, 221, 221);
         byte[] rgbTotal = new byte[]{(byte)colorTotal.getRed(), (byte)colorTotal.getGreen(), (byte)colorTotal.getBlue()};
         cellStyleTotal.setFillForegroundColor(new XSSFColor(rgbTotal, null));
         cellStyleTotal.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         cellStyleTotal.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
         XSSFFont fontTotal = workbook.createFont();
         fontTotal.setBold(true);
         cellStyleTotal.setFont(fontTotal);
         cellStyleTotal.setBorderBottom(BorderStyle.MEDIUM);
         cellStyleTotal.setBorderTop(BorderStyle.MEDIUM);
         cellStyleTotal.setBorderRight(BorderStyle.MEDIUM);
         cellStyleTotal.setBorderLeft(BorderStyle.MEDIUM);
         XSSFCellStyle cellStyleEven = workbook.createCellStyle();
         Color colorEven = new Color(234, 234, 234);
         byte[] rgbEven = new byte[]{(byte)colorEven.getRed(), (byte)colorEven.getGreen(), (byte)colorEven.getBlue()};
         cellStyleEven.setFillForegroundColor(new XSSFColor(rgbEven, null));
         cellStyleEven.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         cellStyleEven.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
         cellStyleEven.setBorderBottom(BorderStyle.MEDIUM);
         cellStyleEven.setBorderTop(BorderStyle.MEDIUM);
         cellStyleEven.setBorderRight(BorderStyle.MEDIUM);
         cellStyleEven.setBorderLeft(BorderStyle.MEDIUM);
         XSSFCellStyle style = workbook.createCellStyle();
         style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
         style.setBorderBottom(BorderStyle.MEDIUM);
         style.setBorderTop(BorderStyle.MEDIUM);
         style.setBorderRight(BorderStyle.MEDIUM);
         style.setBorderLeft(BorderStyle.MEDIUM);
         XSSFCellStyle styleDates = workbook.createCellStyle();
         styleDates.setBorderBottom(BorderStyle.MEDIUM);
         styleDates.setBorderTop(BorderStyle.MEDIUM);
         styleDates.setBorderRight(BorderStyle.MEDIUM);
         styleDates.setBorderLeft(BorderStyle.MEDIUM);
         Row row = sheet.createRow(0);
         Cell cell = row.createCell(0);
         cell.setCellValue("Ventas por medios de pago");
         cell.setCellStyle(cellStyleTitle);
         row = sheet.createRow(1);
         cell = row.createCell(0);
         cell.setCellValue("Fecha: " + this.createDateForTitle());
         int rownum = 3;
         int colIdx = 0;
         row = sheet.createRow(rownum);
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 3000);
         cell.setCellValue("Fecha");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 3000);
         cell.setCellValue("Efectivo");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         List<CreditCard> creditCards = this.getOrderService().getActiveCreditCards();

         for(Iterator var18 = creditCards.iterator(); var18.hasNext(); ++colIdx) {
            CreditCard creditCard = (CreditCard)var18.next();
            cell = row.createCell(colIdx);
            sheet.setColumnWidth(colIdx, 3000);
            cell.setCellValue(creditCard.getName());
            cell.setCellStyle(cellStyle);
         }

         List<DebitCard> debitCards = this.getOrderService().getActiveDebitCards();

         for(Iterator var19 = debitCards.iterator(); var19.hasNext(); ++colIdx) {
            DebitCard debitCard = (DebitCard)var19.next();
            cell = row.createCell(colIdx);
            sheet.setColumnWidth(colIdx, 3000);
            cell.setCellValue(debitCard.getName());
            cell.setCellStyle(cellStyle);
         }

         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 3000);
         cell.setCellValue("Total");
         cell.setCellStyle(cellStyleTotal);
         ++colIdx;
         ++rownum;
         Date currentDay = this.getStartDate();
         Date currentDayEnd = this.buildDayEndForDate(currentDay);
         double valueDayTotal = 0.0;

         while(currentDay.before(this.getEndDate())) {
            try {
               List<Order> orders = this.getOrderService().getCompletedOrdersForDateRange(currentDay, currentDayEnd);
               colIdx = 0;
               row = sheet.createRow(rownum);
               cell = row.createCell(colIdx);
               cell.setCellValue(this.formatDateToDisplay(currentDay));
               cell.setCellStyle(styleDates);
               colIdx = colIdx + 1;
               cell = row.createCell(colIdx);
               double value = this.getOrderService().getNetCashTotalForOrders(orders);
               if (value > 0.0) {
                  cell.setCellValue(value);
                  valueDayTotal += value;
               }

               cell.setCellStyle(style);
               ++colIdx;

               Iterator var26;
               for(var26 = creditCards.iterator(); var26.hasNext(); ++colIdx) {
                  CreditCard creditCard = (CreditCard)var26.next();
                  cell = row.createCell(colIdx);
                  value = this.getOrderService().getNetCreditCardTotalForOrders(orders, creditCard.getName());
                  if (value > 0.0) {
                     cell.setCellValue(value);
                     valueDayTotal += value;
                  }

                  cell.setCellStyle(cellStyleEven);
               }

               for(var26 = debitCards.iterator(); var26.hasNext(); ++colIdx) {
                  DebitCard debitCard = (DebitCard)var26.next();
                  cell = row.createCell(colIdx);
                  value = this.getOrderService().getNetDebitCardTotalForOrders(orders, debitCard.getName());
                  if (value > 0.0) {
                     cell.setCellValue(value);
                     valueDayTotal += value;
                  }

                  cell.setCellStyle(style);
               }

               cell = row.createCell(colIdx);
               if (valueDayTotal > 0.0) {
                  cell.setCellValue(valueDayTotal);
               }

               cell.setCellStyle(cellStyleTotal);
               ++colIdx;
               ++rownum;
               currentDay = DateUtils.addDays(currentDay, 1);
               currentDayEnd = this.buildDayEndForDate(currentDay);
               valueDayTotal = 0.0;
               //this.display.asyncExec(new 1(this));
               this.display.asyncExec(() -> {
                   if (!progressBar.isDisposed()) {
                       progressBar.setSelection(progressBar.getSelection() + 1);
                   }
               });
            } catch (Exception var29) {
            }
         }

         colIdx = 0;
         row = sheet.createRow(rownum);
         double total = 0.0;
         cell = row.createCell(colIdx);
         cell.setCellValue("Total");
         cell.setCellStyle(cellStyleTotal);
         colIdx = colIdx + 1;
         List<Order> orders = this.getOrderService().getCompletedOrdersForDateRange(this.getStartDate(), this.getEndDate());
         cell = row.createCell(colIdx);
         double value = this.getOrderService().getNetCashTotalForOrders(orders);
         if (value > 0.0) {
            cell.setCellValue(value);
            total += value;
         }

         cell.setCellStyle(cellStyleTotal);
         ++colIdx;

         Iterator var28;
         for(var28 = creditCards.iterator(); var28.hasNext(); ++colIdx) {
            CreditCard creditCard = (CreditCard)var28.next();
            cell = row.createCell(colIdx);
            value = this.getOrderService().getNetCreditCardTotalForOrders(orders, creditCard.getName());
            if (value > 0.0) {
               cell.setCellValue(value);
               total += value;
            }

            cell.setCellStyle(cellStyleTotal);
         }

         for(var28 = debitCards.iterator(); var28.hasNext(); ++colIdx) {
            DebitCard debitCard = (DebitCard)var28.next();
            cell = row.createCell(colIdx);
            value = this.getOrderService().getNetDebitCardTotalForOrders(orders, debitCard.getName());
            if (value > 0.0) {
               cell.setCellValue(value);
               total += value;
            }

            cell.setCellStyle(cellStyleTotal);
         }

         cell = row.createCell(colIdx);
         if (total > 0.0) {
            cell.setCellValue(total);
         }

         cell.setCellStyle(cellStyleTotal);
         ++colIdx;
         FileOutputStream out = new FileOutputStream(this.getFileName());
         workbook.write(out);
         out.close();
         
         this.display.asyncExec(() -> {
             if (!progressBar.isDisposed()) {
                 progressBar.setVisible(false);
                 lblProgressBarTitle.setText("Se completó la generación del informe.");
                 lblProgressBarTitle.setBounds(60, 39, 246, 20);
                 cancelButton.setText("Aceptar");
             }
         });
      } catch (IOException var30) {
         this.display.asyncExec(() -> {
             dialog.alert("No se pudo guardar el archivo porque está siendo utilizado por otro programa.");
             dialog.close();
         });
      }
   }

   private String formatDateToDisplay(Date currentDay) {
      Format formatter = new SimpleDateFormat("dd/MM/yyyy");
      return formatter.format(currentDay);
   }

   private Date buildDayEndForDate(Date currentDayStart) {
      Date endDate = DateUtils.addDays(currentDayStart, 1);
      endDate = DateUtils.addMilliseconds(endDate, -1);
      return endDate;
   }

   private String createDateForTitle() {
      Format formatter = new SimpleDateFormat("dd/MM/yyyy");
      return formatter.format(this.startDate) + " - " + formatter.format(this.endDate);
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
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

   public OrderService getOrderService() {
      return this.orderService;
   }

   public void setOrderService(OrderService orderService) {
      this.orderService = orderService;
   }
}
