package com.facilvirtual.fvstoresdesk.ui.list;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import com.facilvirtual.fvstoresdesk.model.Customer;

class SummaryGenerator extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private List<Customer> customers = new ArrayList();
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private CustomerOnAccountSummaryExcelGenerator dialog;

   public SummaryGenerator(Display display, ProgressBar progressBar, List<Customer> customers, String fileName, Label lblProgressBarTitle, Button cancelButton, CustomerOnAccountSummaryExcelGenerator dialog) {
      this.display = display;
      this.progressBar = progressBar;
      this.customers = customers;
      this.fileName = fileName;
      this.lblProgressBarTitle = lblProgressBarTitle;
      this.cancelButton = cancelButton;
      this.dialog = dialog;
   }
   @Override
   public void run() {
      try {
         XSSFWorkbook workbook = new XSSFWorkbook();
         XSSFSheet sheet = workbook.createSheet("Cuentas corrientes de clientes");
         XSSFCellStyle cellStyle = workbook.createCellStyle();
         Color color = new Color(50, 135, 54);
         byte[] rgb = new byte[]{(byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue()};
         cellStyle.setFillForegroundColor(new XSSFColor(rgb, null));
         cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         XSSFFont font = workbook.createFont();
         font.setBold(true);
         font.setColor((short)9);
         cellStyle.setFont(font);
         XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
         XSSFFont fontTitle = workbook.createFont();
         fontTitle.setBold(true);
         cellStyleTitle.setFont(fontTitle);
         XSSFCellStyle cellDebtorStyle = workbook.createCellStyle();
         XSSFFont fontDebtor = workbook.createFont();
         fontDebtor.setBold(true);
         fontDebtor.setColor((short)10);
         cellDebtorStyle.setFont(fontDebtor);
         Row row = sheet.createRow(0);
         Cell cell = row.createCell(0);
         cell.setCellValue("Resumen de cuentas corrientes de clientes");
         cell.setCellStyle(cellStyleTitle);
         row = sheet.createRow(1);
         cell = row.createCell(0);
         cell.setCellValue("Fecha: " + this.createDateForTitle());
         int rownum = 3;
         int colIdx = 0;
         row = sheet.createRow(rownum);
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 15000);
         cell.setCellValue("Apellido y nombre");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 7500);
         cell.setCellValue("Saldo");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         ++rownum;
         Iterator var14 = this.customers.iterator();

         while(var14.hasNext()) {
            Customer customer = (Customer)var14.next();
            if (!customer.getFullName().equalsIgnoreCase("- Cliente Ocasional -")) {
               colIdx = 0;
               row = sheet.createRow(rownum);
               cell = row.createCell(colIdx);
               cell.setCellValue(String.valueOf(customer.getFullName()));
               if (customer.getOnAccountTotal() < 0.0) {
                  cell.setCellStyle(cellDebtorStyle);
               }

               colIdx = colIdx + 1;
               cell = row.createCell(colIdx);
               cell.setCellValue(customer.getOnAccountTotalToDisplay());
               if (customer.getOnAccountTotal() < 0.0) {
                  cell.setCellStyle(cellDebtorStyle);
               }

               ++colIdx;
               ++rownum;
               // Actualizar la barra de progreso
               this.display.asyncExec(() -> {
                   if (!progressBar.isDisposed()) {
                       progressBar.setSelection(progressBar.getSelection() + 1);
                   }
               });
            }
         }

         FileOutputStream out = new FileOutputStream(this.getFileName());
         workbook.write(out);
         out.close();
         
         // Actualizar la UI cuando se complete la generación
         this.display.asyncExec(() -> {
             if (!progressBar.isDisposed()) {
                 progressBar.setVisible(false);
                 lblProgressBarTitle.setText("Se completó la generación del resumen.");
                 lblProgressBarTitle.setBounds(45, 39, 246, 17);
                 cancelButton.setText("Aceptar");
             }
         });
      } catch (IOException var15) {
         // Mostrar mensaje de error
         this.display.asyncExec(() -> {
             dialog.alert("No se pudo guardar el archivo porque está siendo utilizado por otro programa.");
             dialog.close();
         });
      }
   }

   private String createDateForTitle() {
      Format formatter = new SimpleDateFormat("dd/MM/yyyy");
      return formatter.format(new Date());
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }
}
