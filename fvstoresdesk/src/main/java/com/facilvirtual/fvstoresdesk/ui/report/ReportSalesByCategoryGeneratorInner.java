package com.facilvirtual.fvstoresdesk.ui.report;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import com.facilvirtual.fvstoresdesk.model.OrderLineByCategoryComparator;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;

class ReportSalesByCategoryGeneratorInner extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private AbstractFVDialog dialog;
   private Date startDate;
   private Date endDate;
   private OrderService orderService;
   private static Logger LOGGER = LoggerFactory.getLogger("ReportSalesByCategoryGeneratorInner");

   public ReportSalesByCategoryGeneratorInner(Display display, ProgressBar progressBar, Date startDate, Date endDate, OrderService orderService, String fileName, Label lblProgressBarTitle, Button cancelButton, AbstractFVDialog dialog) {
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
         XSSFSheet sheet = workbook.createSheet("Ventas por rubros");
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
         cell.setCellValue("Ventas por rubros");
         cell.setCellStyle(cellStyleTitle);
         row = sheet.createRow(1);
         cell = row.createCell(0);
         cell.setCellValue("Fecha: " + this.createDateForTitle());
         int rownum = 3;
         int colIdx = 0;
         row = sheet.createRow(rownum);
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 7500);
         cell.setCellValue("Rubro");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 3000);
         cell.setCellValue("Importe");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 3000);
         cell.setCellValue("Costo");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 3000);
         cell.setCellValue("Ganancia");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         ++rownum;
         Date currentDay = this.getStartDate();
         Date currentDayEnd = this.buildDayEndForDate(currentDay);
         double total = 0.0;
         double totalCost = 0.0;
         double totalProfit = 0.0;
         double categoryTotal = 0.0;
         double categoryTotalCost = 0.0;
         double categoryTotalProfit = 0.0;

         ArrayList orderLines;
         Iterator var33;
         for(orderLines = new ArrayList(); currentDay.before(this.getEndDate()); currentDayEnd = this.buildDayEndForDate(currentDay)) {
            try {
               List<Order> orders = this.getOrderService().getCompletedOrdersForDateRange(currentDay, currentDayEnd);
               var33 = orders.iterator();

               while(var33.hasNext()) {
                  Order order = (Order)var33.next();
                  orderLines.addAll(this.getOrderService().getOrderLinesForOrder(order));
               }
            } catch (Exception var36) {
               LOGGER.error("Error de base de datos");
               LOGGER.error(var36.getMessage());
               //LOGGER.error(var36);
               System.exit(0);
            }

            currentDay = DateUtils.addDays(currentDay, 1);
         }

         Collections.sort(orderLines, new OrderLineByCategoryComparator());
         List<OrderLine> productOrderLines = new ArrayList();
         var33 = orderLines.iterator();

         while(var33.hasNext()) {
            OrderLine orderLine = (OrderLine)var33.next();
            if (orderLine.getProduct() != null) {
               productOrderLines.add(orderLine);
            }
         }

         Iterator<OrderLine> it = productOrderLines.iterator();
         OrderLine previousOrderLine = null;
         OrderLine currentOrderLine = null;
         if (it.hasNext()) {
            previousOrderLine = (OrderLine)it.next();
            currentOrderLine = previousOrderLine;
         }

         while(currentOrderLine != null) {
            while(currentOrderLine != null && previousOrderLine.getProduct().getCategory().getName().equalsIgnoreCase(currentOrderLine.getProduct().getCategory().getName())) {
               categoryTotal += currentOrderLine.getSubtotal();
               categoryTotalCost += currentOrderLine.getCostSubtotal();
               categoryTotalProfit += currentOrderLine.getProfit();
               total += currentOrderLine.getSubtotal();
               totalCost += currentOrderLine.getCostSubtotal();
               totalProfit += currentOrderLine.getProfit();
               if (it.hasNext()) {
                  currentOrderLine = (OrderLine)it.next();
               } else {
                  currentOrderLine = null;
               }
            }

            colIdx = 0;
            row = sheet.createRow(rownum);
            cell = row.createCell(colIdx);
            cell.setCellValue(previousOrderLine.getProduct().getCategory().getName());
            cell.setCellStyle(styleDates);
            colIdx = colIdx + 1;
            cell = row.createCell(colIdx);
            cell.setCellValue(categoryTotal);
            cell.setCellStyle(styleDates);
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(categoryTotalCost);
            cell.setCellStyle(styleDates);
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(categoryTotalProfit);
            cell.setCellStyle(styleDates);
            ++colIdx;
            if (currentOrderLine != null) {
               previousOrderLine = currentOrderLine;
               categoryTotal = 0.0;
               categoryTotalCost = 0.0;
               categoryTotalProfit = 0.0;
            }

            ++rownum;
            // Actualizar la barra de progreso
            this.display.asyncExec(() -> {
                if (!progressBar.isDisposed()) {
                    progressBar.setSelection(progressBar.getSelection() + 1);
                }
            });
         }

         row = sheet.createRow(rownum);
         cell = row.createCell(0);
         cell.setCellValue("Total");
         cell.setCellStyle(cellStyleTotal);
         cell = row.createCell(1);
         cell.setCellValue(total);
         cell.setCellStyle(cellStyleTotal);
         cell = row.createCell(2);
         cell.setCellValue(totalCost);
         cell.setCellStyle(cellStyleTotal);
         cell = row.createCell(3);
         cell.setCellValue(totalProfit);
         cell.setCellStyle(cellStyleTotal);
         FileOutputStream out = new FileOutputStream(this.getFileName());
         workbook.write(out);
         out.close();
         
         // Actualizar la UI cuando se complete la generación
         this.display.asyncExec(() -> {
             if (!progressBar.isDisposed()) {
                 progressBar.setVisible(false);
                 lblProgressBarTitle.setText("Se completó la generación del informe.");
                 lblProgressBarTitle.setBounds(60, 39, 246, 20);
                 cancelButton.setText("Aceptar");
             }
         });
      } catch (IOException var37) {
         // Mostrar mensaje de error
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
