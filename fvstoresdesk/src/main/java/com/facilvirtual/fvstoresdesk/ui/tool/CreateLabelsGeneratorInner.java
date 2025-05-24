package com.facilvirtual.fvstoresdesk.ui.tool;

import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

class CreateLabelsGeneratorInner extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private List<Product> products = new ArrayList();
   private int maxProducts = 3000;
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private CreateLabelsGenerator dialog;

   public CreateLabelsGeneratorInner(Display display, ProgressBar progressBar, List<Product> products, String fileName, Label lblProgressBarTitle, Button cancelButton, CreateLabelsGenerator dialog) {
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
         XSSFSheet sheet = workbook.createSheet("Etiquetas");
         XSSFPrintSetup printSetup = sheet.getPrintSetup();
         printSetup.setHeaderMargin(0.0);
         sheet.setMargin((short)0, 0.0);
         sheet.setMargin((short)2, 0.0);
         sheet.setMargin((short)1, 0.0);
         sheet.setMargin((short)3, 0.0);
         int col0Width = 675;
         int col1Width = 2700;
         int col2Width = 3375;
         int col3Width = 2025;
         sheet.setColumnWidth(0, col0Width);
         sheet.setColumnWidth(1, col1Width);
         sheet.setColumnWidth(2, col2Width);
         sheet.setColumnWidth(3, col3Width);
         sheet.setColumnWidth(4, col0Width);
         sheet.setColumnWidth(5, col1Width);
         sheet.setColumnWidth(6, col2Width);
         sheet.setColumnWidth(7, col3Width);
         sheet.setColumnWidth(8, col0Width);
         sheet.setColumnWidth(9, col1Width);
         sheet.setColumnWidth(10, col2Width);
         sheet.setColumnWidth(11, col3Width);
         sheet.setColumnWidth(12, col0Width);
         int offsetRow = 0;
         int numprod = 0;
         int offsetCol = 0;

         // for(Iterator<Product> it = this.products.iterator(); it.hasNext() && numprod <= this.maxProducts; this.display.asyncExec(new 1(this))) {
         //    try {
         //       Thread.sleep(1000L);
         //       Product product = (Product)it.next();
         //       ++numprod;
         //       byte offsetCol;
         //       if (product != null && numprod <= this.maxProducts) {
         //          offsetCol = 0;
         //          this.createLabelForProduct(workbook, sheet, product, offsetRow, offsetCol);
         //          sheet.addMergedRegion(new CellRangeAddress(1 + offsetRow, 1 + offsetRow, 1, 3));
         //          sheet.addMergedRegion(new CellRangeAddress(2 + offsetRow, 2 + offsetRow, 1, 3));
         //          sheet.addMergedRegion(new CellRangeAddress(3 + offsetRow, 3 + offsetRow, 1, 3));
         //       }

         //       if (it.hasNext()) {
         //          product = (Product)it.next();
         //          ++numprod;
         //       } else {
         //          product = null;
         //       }

         //       if (product != null && numprod <= this.maxProducts) {
         //          offsetCol = 4;
         //          this.createLabelForProduct(workbook, sheet, product, offsetRow, offsetCol);
         //          sheet.addMergedRegion(new CellRangeAddress(1 + offsetRow, 1 + offsetRow, 5, 7));
         //          sheet.addMergedRegion(new CellRangeAddress(2 + offsetRow, 2 + offsetRow, 5, 7));
         //          sheet.addMergedRegion(new CellRangeAddress(3 + offsetRow, 3 + offsetRow, 5, 7));
         //       }

         //       if (it.hasNext()) {
         //          product = (Product)it.next();
         //          ++numprod;
         //       } else {
         //          product = null;
         //       }

         //       if (product != null && numprod <= this.maxProducts) {
         //          offsetCol = 8;
         //          this.createLabelForProduct(workbook, sheet, product, offsetRow, offsetCol);
         //          sheet.addMergedRegion(new CellRangeAddress(1 + offsetRow, 1 + offsetRow, 9, 11));
         //          sheet.addMergedRegion(new CellRangeAddress(2 + offsetRow, 2 + offsetRow, 9, 11));
         //          sheet.addMergedRegion(new CellRangeAddress(3 + offsetRow, 3 + offsetRow, 9, 11));
         //       }

         //       offsetRow += 5;
         //       if (offsetRow % 35 == 0) {
         //          sheet.setRowBreak(offsetRow - 1);
         //       }
         //    } catch (Exception var14) {
         //    }
         // }

         FileOutputStream out = new FileOutputStream(this.getFileName());
         workbook.write(out);
         out.close();
         //this.display.asyncExec(new 2(this));
      } catch (IOException var15) {
         //this.display.asyncExec(new 3(this));
      }

   }

   private void createLabelForProduct(XSSFWorkbook workbook, XSSFSheet sheet, Product product, int offsetRow, int offsetCol) {
      try {
         BorderStyle labelBorder = BorderStyle.THIN;
         Row titleRow = sheet.getRow(1 + offsetRow);
         if (titleRow == null) {
            titleRow = sheet.createRow(1 + offsetRow);
         }

         titleRow.setHeightInPoints(22.0F);
         XSSFFont titleFont = workbook.createFont();
         titleFont.setFontName("Tahoma");
         titleFont.setFontHeightInPoints((short)11);
         Cell titleCellCol1 = titleRow.createCell(1 + offsetCol);
         titleCellCol1.setCellValue(product.getDescriptionForLabel());
         XSSFCellStyle titleStyleCol1 = workbook.createCellStyle();
         titleStyleCol1.setAlignment(HorizontalAlignment.CENTER);
         titleStyleCol1.setFont(titleFont);
         titleStyleCol1.setBorderTop(labelBorder);
         titleStyleCol1.setBorderLeft(labelBorder);
         titleCellCol1.setCellStyle(titleStyleCol1);
         Cell titleCellCol2 = titleRow.createCell(2 + offsetCol);
         titleCellCol2.setCellValue("");
         XSSFCellStyle titleStyleCol2 = workbook.createCellStyle();
         titleStyleCol2.setBorderTop(labelBorder);
         titleCellCol2.setCellStyle(titleStyleCol2);
         Cell titleCellCol3 = titleRow.createCell(3 + offsetCol);
         titleCellCol3.setCellValue("");
         XSSFCellStyle titleStyleCol3 = workbook.createCellStyle();
         titleStyleCol3.setBorderTop(labelBorder);
         titleStyleCol3.setBorderRight(labelBorder);
         titleCellCol3.setCellStyle(titleStyleCol3);
         Row quantityRow = sheet.getRow(2 + offsetRow);
         if (quantityRow == null) {
            quantityRow = sheet.createRow(2 + offsetRow);
         }

         quantityRow.setHeightInPoints(12.0F);
         XSSFFont quantityFont = workbook.createFont();
         quantityFont.setFontName("Tahoma");
         quantityFont.setFontHeightInPoints((short)8);
         Cell quantityCellCol1 = quantityRow.createCell(1 + offsetCol);
         quantityCellCol1.setCellValue(product.getQuantityForLabel());
         XSSFCellStyle quantityStyleCol1 = workbook.createCellStyle();
         quantityStyleCol1.setAlignment(HorizontalAlignment.CENTER);
         quantityStyleCol1.setFont(quantityFont);
         quantityStyleCol1.setBorderLeft(labelBorder);
         quantityCellCol1.setCellStyle(quantityStyleCol1);
         Cell quantityCellCol2 = quantityRow.createCell(2 + offsetCol);
         quantityCellCol2.setCellValue("");
         Cell quantityCellCol3 = quantityRow.createCell(3 + offsetCol);
         quantityCellCol3.setCellValue("");
         XSSFCellStyle quantityStyleCol3 = workbook.createCellStyle();
         quantityStyleCol3.setBorderRight(labelBorder);
         quantityCellCol3.setCellStyle(quantityStyleCol3);
         Row priceRow = sheet.getRow(3 + offsetRow);
         if (priceRow == null) {
            priceRow = sheet.createRow(3 + offsetRow);
         }

         priceRow.setHeightInPoints(44.0F);
         XSSFFont priceFont = workbook.createFont();
         priceFont.setFontName("Arial");
         priceFont.setBold(true);
         priceFont.setFontHeightInPoints((short)38);
         if (product.isInOffer()) {
            priceFont.setColor(new XSSFColor((IndexedColorMap) new Color(221, 0, 0)));
         } else {
            priceFont.setColor(new XSSFColor((IndexedColorMap) new Color(153, 0, 204)));
         }

         ProductPrice productPrice = this.dialog.getProductService().getProductPriceForProduct(product, this.dialog.getPriceList());
         Cell priceCellCol1 = priceRow.createCell(1 + offsetCol);
         if (productPrice != null) {
            priceCellCol1.setCellValue("$ " + productPrice.getSellingPriceToDisplay());
         } else {
            priceCellCol1.setCellValue("$ 0,00");
         }

         XSSFCellStyle priceStyleCol1 = workbook.createCellStyle();
         priceStyleCol1.setAlignment(HorizontalAlignment.JUSTIFY);
         priceStyleCol1.setFont(priceFont);
         priceStyleCol1.setBorderLeft(labelBorder);
         priceCellCol1.setCellStyle(priceStyleCol1);
         Cell priceCellCol2 = priceRow.createCell(2 + offsetCol);
         priceCellCol2.setCellValue("");
         Cell priceCellCol3 = priceRow.createCell(3 + offsetCol);
         priceCellCol3.setCellValue("");
         XSSFCellStyle priceStyleCol3 = workbook.createCellStyle();
         priceStyleCol3.setBorderRight(labelBorder);
         priceCellCol3.setCellStyle(priceStyleCol3);
         Row barCodeRow = sheet.getRow(4 + offsetRow);
         if (barCodeRow == null) {
            barCodeRow = sheet.createRow(4 + offsetRow);
         }

         barCodeRow.setHeightInPoints(14.0F);
         XSSFFont barCodeFont = workbook.createFont();
         barCodeFont.setFontName("Tahoma");
         barCodeFont.setFontHeightInPoints((short)7);
         Cell barCodeCellCol1 = barCodeRow.createCell(1 + offsetCol);
         barCodeCellCol1.setCellValue(product.getBarCode());
         XSSFCellStyle barCodeStyleCol1 = workbook.createCellStyle();
         barCodeStyleCol1.setAlignment(HorizontalAlignment.JUSTIFY);
         barCodeStyleCol1.setVerticalAlignment(VerticalAlignment.CENTER);
         barCodeStyleCol1.setFont(barCodeFont);
         barCodeStyleCol1.setBorderLeft(labelBorder);
         barCodeStyleCol1.setBorderBottom(labelBorder);
         barCodeCellCol1.setCellStyle(barCodeStyleCol1);
         Cell pricePerUnitCell = barCodeRow.createCell(2 + offsetCol);
         if (this.dialog.isShowPricePerUnit()) {
            if (productPrice != null) {
               pricePerUnitCell.setCellValue(productPrice.getPricePerUnitToDisplay());
            } else {
               pricePerUnitCell.setCellValue("");
            }
         }

         XSSFCellStyle pricePerUnitStyle = workbook.createCellStyle();
         pricePerUnitStyle.setAlignment(HorizontalAlignment.JUSTIFY);
         pricePerUnitStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         pricePerUnitStyle.setFont(barCodeFont);
         pricePerUnitStyle.setBorderBottom(labelBorder);
         pricePerUnitCell.setCellStyle(pricePerUnitStyle);
         Cell lastUpdatedPriceCell = barCodeRow.createCell(3 + offsetCol);
         if (productPrice != null) {
            lastUpdatedPriceCell.setCellValue(productPrice.getLastUpdatedPriceForLabel());
         } else {
            lastUpdatedPriceCell.setCellValue("");
         }

         XSSFCellStyle lastUpdatedPriceStyle = workbook.createCellStyle();
         lastUpdatedPriceStyle.setAlignment(HorizontalAlignment.JUSTIFY);
         lastUpdatedPriceStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         lastUpdatedPriceStyle.setFont(barCodeFont);
         lastUpdatedPriceStyle.setBorderBottom(labelBorder);
         lastUpdatedPriceStyle.setBorderRight(labelBorder);
         lastUpdatedPriceCell.setCellStyle(lastUpdatedPriceStyle);
      } catch (Exception var38) {
         var38.printStackTrace();
      }

   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }
}
