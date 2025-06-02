package com.facilvirtual.fvstoresdesk.ui.report;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
import org.springframework.context.ApplicationContext;

class ReportSalesByDateGeneratorInner extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private AbstractFVDialog dialog;
   private Date startDate;
   private Date endDate;
   private OrderService orderService;
   private boolean conCAE = true;
   private static Logger LOGGER = LoggerFactory.getLogger("Report2Generator");

   public ReportSalesByDateGeneratorInner(Display display, ProgressBar progressBar, Date startDate, Date endDate, OrderService orderService, String fileName, Label lblProgressBarTitle, boolean conCAE, Button cancelButton, AbstractFVDialog dialog) {
      this.display = display;
      this.progressBar = progressBar;
      this.fileName = fileName;
      this.lblProgressBarTitle = lblProgressBarTitle;
      this.cancelButton = cancelButton;
      this.dialog = dialog;
      this.startDate = startDate;
      this.endDate = endDate;
      this.orderService = orderService;
      this.conCAE = conCAE;
   }
   @Override
   public void run() {
      try {
         XSSFWorkbook workbook = new XSSFWorkbook();
         this.createSheet1(workbook);
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
      XSSFSheet sheet = workbook.createSheet("Ventas por fecha");
      XSSFCellStyle cellStyle = workbook.createCellStyle();
      Color color = new Color(50, 135, 54);
      byte[] rgb = new byte[]{(byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue()};
      cellStyle.setFillForegroundColor(new XSSFColor(rgb, null));
      cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      cellStyle.setBorderBottom(BorderStyle.THIN);
      cellStyle.setBorderTop(BorderStyle.THIN);
      cellStyle.setBorderRight(BorderStyle.THIN);
      cellStyle.setBorderLeft(BorderStyle.THIN);
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
      cellStyleTotal.setBorderBottom(BorderStyle.THIN);
      cellStyleTotal.setBorderTop(BorderStyle.THIN);
      cellStyleTotal.setBorderRight(BorderStyle.THIN);
      cellStyleTotal.setBorderLeft(BorderStyle.THIN);
      XSSFCellStyle cellStyleEven = workbook.createCellStyle();
      Color colorEven = new Color(234, 234, 234);
      byte[] rgbEven = new byte[]{(byte)colorEven.getRed(), (byte)colorEven.getGreen(), (byte)colorEven.getBlue()};
      cellStyleEven.setFillForegroundColor(new XSSFColor(rgbEven, null));
      cellStyleEven.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      cellStyleEven.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
      cellStyleEven.setBorderBottom(BorderStyle.THIN);
      cellStyleEven.setBorderTop(BorderStyle.THIN);
      cellStyleEven.setBorderRight(BorderStyle.THIN);
      cellStyleEven.setBorderLeft(BorderStyle.THIN);
      XSSFCellStyle style = workbook.createCellStyle();
      style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
      style.setBorderBottom(BorderStyle.THIN);
      style.setBorderTop(BorderStyle.THIN);
      style.setBorderRight(BorderStyle.THIN);
      style.setBorderLeft(BorderStyle.THIN);
      XSSFCellStyle styleCommon = workbook.createCellStyle();
      styleCommon.setBorderBottom(BorderStyle.THIN);
      styleCommon.setBorderTop(BorderStyle.THIN);
      styleCommon.setBorderRight(BorderStyle.THIN);
      styleCommon.setBorderLeft(BorderStyle.THIN);
      Row row = sheet.createRow(0);
      Cell cell = row.createCell(0);
      cell.setCellValue("Ventas por fecha");
      cell.setCellStyle(cellStyleTitle);
      row = sheet.createRow(1);
      cell = row.createCell(0);
      cell.setCellValue("Fecha: " + this.createDateForTitle());
      int rownum = 3;
      int colIdx = 0;
      row = sheet.createRow(rownum);
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 5200);
      cell.setCellValue("Tipo de comprobante");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3500);
      cell.setCellValue("Fecha");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 4000);
      cell.setCellValue("Número");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 4200);
      cell.setCellValue("CAE");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 5500);
      cell.setCellValue("Nombre y Apellido / Razón Social");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3500);
      cell.setCellValue("DNI / CUIT");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3300);
      cell.setCellValue("No Gravado");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3300);
      cell.setCellValue("Neto al 10,5%");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3300);
      cell.setCellValue("Neto al 21%");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3300);
      cell.setCellValue("IVA al 10,5%");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3300);
      cell.setCellValue("IVA al 21%");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      cell = row.createCell(colIdx);
      sheet.setColumnWidth(colIdx, 3300);
      cell.setCellValue("Total");
      cell.setCellStyle(cellStyle);
      ++colIdx;
      ++rownum;
      Date currentDay = this.getStartDate();
      Date currentDayEnd = this.buildDayEndForDate(currentDay);
      double total = 0.0;

      while(currentDay.before(this.getEndDate())) {
         try {
            AppConfig appConfig = this.getAppConfigService().getAppConfig();
            new ArrayList();
            List orders;
            if (this.conCAE) {
               orders = this.getOrderService().getFiscalOrdersForDateRange(currentDay, currentDayEnd);
            } else {
               orders = this.getOrderService().getCompletedOrdersForDateRange(currentDay, currentDayEnd);
            }

            Iterator var23 = orders.iterator();

            while(var23.hasNext()) {
               Order order = (Order)var23.next();

               try {
                  colIdx = 0;
                  row = sheet.createRow(rownum);
                  if (order.hasAfipCae()) {
                     cell = row.createCell(colIdx);
                     cell.setCellValue(order.getAfipCbteTipoToDisplay());
                     cell.setCellStyle(styleCommon);
                     colIdx = colIdx + 1;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(order.getAfipCbteFchToPrint());
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(order.getFiscalReceiptNumber(appConfig));
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(order.getAfipCae());
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(order.getCustomer().getFullNameToReport());
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     if ("0".equals(order.getAfipDocNroToPrint())) {
                        cell.setCellValue("-");
                     } else {
                        cell.setCellValue(order.getAfipDocNroToPrint());
                     }

                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     if (order.getAfipCbteTipo() == 11) {
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(order.getTotalToAfip()));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(0.0));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(0.0));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(0.0));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(0.0));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                     } else if (order.getAfipCbteTipo() == 6 || order.getAfipCbteTipo() == 1) {
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(0.0));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(order.getBaseImpIVA105ToAfip()));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(order.getBaseImpIVA21ToAfip()));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(order.getImporteIVA105ToAfip()));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                        cell = row.createCell(colIdx);
                        cell.setCellValue(new Double(order.getImporteIVA21ToAfip()));
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        ++colIdx;
                     }

                     cell = row.createCell(colIdx);
                     cell.setCellValue(new Double(order.getTotalToAfip()));
                     cell.setCellStyle(style);
                     cell.setCellType(CellType.STRING);
                     ++colIdx;
                     total += order.getTotal();
                     ++rownum;
                  } else {
                     cell = row.createCell(colIdx);
                     cell.setCellValue("No Fiscal");
                     cell.setCellStyle(styleCommon);
                     colIdx = colIdx + 1;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(order.getSaleDateToReport());
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(order.getNotFiscalReceiptNumber(appConfig));
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue("-");
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(order.getCustomer().getFullNameToReport());
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     if (order.getCustomer().isHomeCustomer()) {
                        if (!"".equals(order.getCustomer().getDniToPrint())) {
                           cell.setCellValue(order.getCustomer().getDniToPrint());
                        } else {
                           cell.setCellValue("-");
                        }
                     } else if (!"".equals(order.getCustomer().getCuitToPrint())) {
                        cell.setCellValue(order.getCustomer().getCuitToPrint());
                     } else {
                        cell.setCellValue("-");
                     }

                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue("-");
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue("-");
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue("-");
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue("-");
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue("-");
                     cell.setCellStyle(styleCommon);
                     ++colIdx;
                     cell = row.createCell(colIdx);
                     cell.setCellValue(new Double(order.getTotalToAfip()));
                     cell.setCellStyle(style);
                     cell.setCellType(CellType.STRING);
                     ++colIdx;
                     total += order.getTotal();
                     ++rownum;
                  }
               } catch (Exception var25) {
                  LOGGER.error(var25.getMessage());
                  LOGGER.error(var25.toString());
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
            LOGGER.error(var26.toString());
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
      sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 10));
      String totalToDisplay = "";
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(total));
      str = str.replaceAll(",", "\\.");
      if (total != 0.0) {
         totalToDisplay = str;
      } else {
         totalToDisplay = "0.00";
      }

      cell = row.createCell(11);
      cell.setCellValue(new Double(totalToDisplay));
      cell.setCellStyle(cellStyleTotal);
      cell.setCellType(CellType.STRING);
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

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }
}
