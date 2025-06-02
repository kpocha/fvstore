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
import org.apache.poi.ss.util.CellRangeAddress;
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
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;

class ReportSalesWithDetailByDateGeneratorInner extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private AbstractFVDialog dialog;
   private Date startDate;
   private Date endDate;
   private OrderService orderService;
   private static Logger LOGGER = LoggerFactory.getLogger("Report2Generator");

   public ReportSalesWithDetailByDateGeneratorInner(Display display, ProgressBar progressBar, Date startDate, Date endDate, OrderService orderService, String fileName, Label lblProgressBarTitle, Button cancelButton, AbstractFVDialog dialog) {
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
         this.createSheet1(workbook);
         this.createSheet2(workbook);
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
      } catch (IOException var3) {
         // Mostrar mensaje de error
         this.display.asyncExec(() -> {
             dialog.alert("No se pudo guardar el archivo porque está siendo utilizado por otro programa.");
             dialog.close();
         });
      }
   }

   private void createSheet1(XSSFWorkbook workbook) {
      XSSFSheet sheet = workbook.createSheet("Ventas con detalle por fecha");
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
      cell.setCellValue("Ventas con detalle por fecha");
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
      cell.setCellValue("Hora");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3000);
      cell.setCellValue("Nro. transacción");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3000);
      cell.setCellValue("Nro. caja");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 5000);
      cell.setCellValue("Código");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 12000);
      cell.setCellValue("Descripción");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 7500);
      cell.setCellValue("Rubro");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 7500);
      cell.setCellValue("Cliente");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3000);
      cell.setCellValue("Cantidad");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 2500);
      cell.setCellValue("Unidad");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3000);
      cell.setCellValue("Unitario");
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
      cell.setCellValue("IVA");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3000);
      cell.setCellValue("Margen de ganancia");
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

      while(currentDay.before(this.getEndDate())) {
         try {
            List<Order> orders = this.getOrderService().getCompletedOrdersForDateRange(currentDay, currentDayEnd);
            Iterator var26 = orders.iterator();

            while(var26.hasNext()) {
               Order order = (Order)var26.next();
               List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(order);
               Iterator var29 = orderLines.iterator();

               while(var29.hasNext()) {
                  OrderLine orderLine = (OrderLine)var29.next();
                  if (orderLine.getProduct() != null) {
                     total += orderLine.getSubtotal();
                     totalCost += orderLine.getCostSubtotal();
                     totalProfit += orderLine.getProfit();
                     
                     // Actualizar la barra de progreso
                     this.display.asyncExec(() -> {
                         if (!progressBar.isDisposed()) {
                             progressBar.setSelection(progressBar.getSelection() + 1);
                         }
                     });
                     colIdx = 0;
                     row = sheet.createRow(rownum);
                     cell = row.createCell(colIdx);
                     cell.setCellValue(this.formatDateToDisplay(currentDay));
                     cell.setCellStyle(styleDates);
                     colIdx = colIdx + 1;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getOrder().getHourToDisplay());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue((double)order.getId());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue((double)order.getCashNumber());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     if (orderLine.getProduct() != null) {
                        cell = row.createCell(colIdx);
                        cell.setCellValue(orderLine.getProduct().getBarCode());
                        cell.setCellStyle(styleDates);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(orderLine.getProduct().getDescription());
                        cell.setCellStyle(styleDates);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(orderLine.getProduct().getCategory().getName());
                        cell.setCellStyle(styleDates);
                        ++colIdx;
                     } else {
                        cell = row.createCell(colIdx);
                        cell.setCellValue("D" + orderLine.getCategory().getNumberToDisplay());
                        cell.setCellStyle(styleDates);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(orderLine.getCategory().getName());
                        cell.setCellStyle(styleDates);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue("");
                        cell.setCellStyle(styleDates);
                        ++colIdx;
                     }

                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getOrder().getCustomer().getFullNameToReport());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getQty());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     if (orderLine.getProduct() != null) {
                        cell.setCellValue(orderLine.getProduct().getSellingUnit().toUpperCase());
                     } else {
                        cell.setCellValue("");
                     }

                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getPrice());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getSubtotal());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getCostSubtotal());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getVatValueToDisplay());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getProfitMarginToDisplay());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getProfit());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     ++rownum;
                  }
               }
            }

            currentDay = DateUtils.addDays(currentDay, 1);
            currentDayEnd = this.buildDayEndForDate(currentDay);
            
            // Actualizar la barra de progreso
            this.display.asyncExec(() -> {
                if (!progressBar.isDisposed()) {
                    progressBar.setSelection(progressBar.getSelection() + 1);
                }
            });
         } catch (Exception var30) {
            LOGGER.error("Error de base de datos");
            LOGGER.error(var30.getMessage());
            //LOGGER.error(var30);
            System.exit(0);
         }
      }

      row = sheet.createRow(rownum);
      cell = row.createCell(0);
      cell.setCellValue("Total");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(7);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(8);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(9);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(10);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 10));
      cell = row.createCell(11);
      cell.setCellValue(total);
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(12);
      cell.setCellValue(totalCost);
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(13);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(14);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(15);
      cell.setCellValue(totalProfit);
      cell.setCellStyle(cellStyleTotal);
   }

   private void createSheet2(XSSFWorkbook workbook) {
      XSSFSheet sheet = workbook.createSheet("Departamentos de la Caja");
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
      cell.setCellValue("Departamentos de la Caja");
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
      cell.setCellValue("Hora");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3000);
      cell.setCellValue("Nro. transacción");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3000);
      cell.setCellValue("Nro. caja");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 5000);
      cell.setCellValue("Código");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 12000);
      cell.setCellValue("Descripción");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 7500);
      cell.setCellValue("Cliente");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3000);
      cell.setCellValue("Importe");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      ++rownum;
      Date currentDay = this.getStartDate();
      Date currentDayEnd = this.buildDayEndForDate(currentDay);
      double total = 0.0;

      while(currentDay.before(this.getEndDate())) {
         try {
            List<Order> orders = this.getOrderService().getCompletedOrdersForDateRange(currentDay, currentDayEnd);
            Iterator var22 = orders.iterator();

            while(var22.hasNext()) {
               Order order = (Order)var22.next();
               List<OrderLine> orderLines = this.getOrderService().getOrderLinesForOrder(order);
               Iterator var25 = orderLines.iterator();

               while(var25.hasNext()) {
                  OrderLine orderLine = (OrderLine)var25.next();
                  if (orderLine.getCategory() != null) {
                     total += orderLine.getSubtotal();
                     colIdx = 0;
                     row = sheet.createRow(rownum);
                     cell = row.createCell(colIdx);
                     cell.setCellValue(this.formatDateToDisplay(currentDay));
                     cell.setCellStyle(styleDates);
                     colIdx = colIdx + 1;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getOrder().getHourToDisplay());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue((double)order.getId());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue((double)order.getCashNumber());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     if (orderLine.getCategory() != null) {
                        cell = row.createCell(colIdx);
                        cell.setCellValue("D" + orderLine.getCategory().getNumberToDisplay());
                        cell.setCellStyle(styleDates);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(orderLine.getCategory().getName());
                        cell.setCellStyle(styleDates);
                        ++colIdx;
                     }

                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getOrder().getCustomer().getFullNameToReport());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(orderLine.getSubtotal());
                     cell.setCellStyle(styleDates);
                     ++colIdx;
                     ++rownum;
                  }
               }
            }

            currentDay = DateUtils.addDays(currentDay, 1);
            currentDayEnd = this.buildDayEndForDate(currentDay);
            
            // Actualizar la barra de progreso
            this.display.asyncExec(() -> {
                if (!progressBar.isDisposed()) {
                    progressBar.setSelection(progressBar.getSelection() + 1);
                }
            });
         } catch (Exception var26) {
            LOGGER.error("Error de base de datos");
            LOGGER.error(var26.getMessage());
            //LOGGER.error(var26);
            System.exit(0);
         }
      }

      row = sheet.createRow(rownum);
      cell = row.createCell(0);
      cell.setCellValue("Total");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(5);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      cell = row.createCell(6);
      cell.setCellValue("");
      cell.setCellStyle(cellStyleTotal);
      sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 6));
      cell = row.createCell(7);
      cell.setCellValue(total);
      cell.setCellStyle(cellStyleTotal);
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
