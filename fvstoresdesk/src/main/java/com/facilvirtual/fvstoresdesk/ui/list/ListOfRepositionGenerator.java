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

import com.facilvirtual.fvstoresdesk.model.Product;

class ListOfRepositionGenerator extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private List<Product> products = new ArrayList();
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private ListOfRepositionExcelGenerator dialog;

   public ListOfRepositionGenerator(Display display, ProgressBar progressBar, List<Product> products, String fileName, Label lblProgressBarTitle, Button cancelButton, ListOfRepositionExcelGenerator dialog) {
      this.display = display;
      this.progressBar = progressBar;
      this.products = products;
      this.fileName = fileName;
      this.lblProgressBarTitle = lblProgressBarTitle;
      this.cancelButton = cancelButton;
      this.dialog = dialog;
   }
   @Override
   public void run() {
      try {
         XSSFWorkbook workbook = new XSSFWorkbook();
         XSSFSheet sheet = workbook.createSheet("Lista de reposición");
         XSSFCellStyle cellStyle = workbook.createCellStyle();
         Color color = new Color(50, 135, 54);
         byte[] rgb = new byte[]{(byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue()};
         cellStyle.setFillForegroundColor(new XSSFColor(rgb, null));
         cellStyle.setFillPattern(FillPatternType.BRICKS);
         XSSFFont font = workbook.createFont();
         font.setBold(true);
         font.setColor((short)9);
         cellStyle.setFont(font);
         XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
         XSSFFont fontTitle = workbook.createFont();
         fontTitle.setBold(true);
         cellStyleTitle.setFont(fontTitle);
         Row row = sheet.createRow(0);
         Cell cell = row.createCell(0);
         cell.setCellValue("Lista de reposición");
         cell.setCellStyle(cellStyleTitle);
         row = sheet.createRow(1);
         cell = row.createCell(0);
         cell.setCellValue("Fecha: " + this.createDateForTitle());
         int rownum = 3;
         int colIdx = 0;
         row = sheet.createRow(rownum);
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 5000);
         cell.setCellValue("Código");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 15000);
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
         cell.setCellValue("Stock actual");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 7500);
         cell.setCellValue("Stock mínimo");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 7500);
         cell.setCellValue("Stock máximo");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 7500);
         cell.setCellValue("Proveedor");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         ++rownum;
         Iterator var12 = this.products.iterator();

         while(var12.hasNext()) {
            Product product = (Product)var12.next();
           colIdx = 0;
            row = sheet.createRow(rownum);
            cell = row.createCell(colIdx);
            cell.setCellValue(String.valueOf(product.getBarCode()));
            colIdx = colIdx + 1;
            cell = row.createCell(colIdx);
            cell.setCellValue(String.valueOf(product.getDescription().toUpperCase()));
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(String.valueOf(product.getCategory().getName()));
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(String.valueOf(product.getStockToDisplay()));
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(String.valueOf(product.getStockMinToDisplay()));
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(String.valueOf(product.getStockMaxToDisplay()));
            ++colIdx;
            cell = row.createCell(colIdx);
            if (product.getSupplier() != null) {
               cell.setCellValue(String.valueOf(product.getSupplier().getCompanyName()));
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

         FileOutputStream out = new FileOutputStream(this.getFileName());
         workbook.write(out);
         out.close();
         
         // Actualizar la UI cuando se complete la generación
         this.display.asyncExec(() -> {
             if (!progressBar.isDisposed()) {
                 progressBar.setVisible(false);
                 lblProgressBarTitle.setText("Se completó la generación de la lista de reposición.");
                 lblProgressBarTitle.setBounds(45, 39, 246, 13);
                 cancelButton.setText("Aceptar");
             }
         });
      } catch (IOException var13) {
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
