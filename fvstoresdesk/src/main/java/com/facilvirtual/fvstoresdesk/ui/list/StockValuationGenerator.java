package com.facilvirtual.fvstoresdesk.ui.list;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ProductService;

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

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.util.CellRangeAddress;

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

class StockValuationGenerator extends Thread {
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private List<Product> products = new ArrayList();
   private Label lblProgressBarTitle;
   private AppConfigService appConfigService;
   private ProductService productService;
   private Button cancelButton;
   private StockValuationExcelGenerator dialog;

   public StockValuationGenerator(Display display, ProgressBar progressBar, List<Product> products, String fileName, Label lblProgressBarTitle, AppConfigService appConfigService, ProductService productService, Button cancelButton, StockValuationExcelGenerator dialog) {
      this.display = display;
      this.progressBar = progressBar;
      this.products = products;
      this.fileName = fileName;
      this.lblProgressBarTitle = lblProgressBarTitle;
      this.appConfigService = appConfigService;
      this.productService = productService;
      this.cancelButton = cancelButton;
      this.dialog = dialog;
   }
   @Override
   public void run() {
      try {
         XSSFWorkbook workbook = new XSSFWorkbook();
         XSSFSheet sheet = workbook.createSheet("Stock valorizado");
         XSSFCellStyle cellStyle = workbook.createCellStyle();
         cellStyle.setFillForegroundColor(new XSSFColor((IndexedColorMap) new Color(50, 135, 54)));
         cellStyle.setFillPattern(FillPatternType.BRICKS);
         XSSFFont font = workbook.createFont();
         font.setBold(true);
         font.setColor((short)9);
         cellStyle.setFont(font);
         XSSFCellStyle cellStyleTitle = workbook.createCellStyle();
         XSSFFont fontTitle = workbook.createFont();
         fontTitle.setBold(true);
         cellStyleTitle.setFont(fontTitle);
         XSSFCellStyle cellStyleTotal = workbook.createCellStyle();
         cellStyleTotal.setFillForegroundColor(new XSSFColor((IndexedColorMap) new Color(221, 221, 221)));
         cellStyleTotal.setFillPattern(FillPatternType.BRICKS);
         cellStyleTotal.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
         XSSFFont fontTotal = workbook.createFont();
         fontTotal.setBold(true);
         cellStyleTotal.setFont(fontTotal);
         Row row = sheet.createRow(0);
         Cell cell = row.createCell(0);
         cell.setCellValue("Stock valorizado");
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
         sheet.setColumnWidth(colIdx, 4000);
         cell.setCellValue("Cantidad");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 4000);
         cell.setCellValue("Unidad");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 4000);
         cell.setCellValue("Costo Unitario");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 4000);
         cell.setCellValue("Costo Total");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         ++rownum;
         DecimalFormat formatter = new DecimalFormat("0.00");
         double totalCost = 0.0;
         PriceList priceList = this.appConfigService.getDefaultPriceList();
         ProductPrice productPrice = null;
         Iterator var19 = this.products.iterator();

         while(var19.hasNext()) {
            Product product = (Product)var19.next();
            productPrice = this.productService.getProductPriceForProduct(product, priceList);
            totalCost += productPrice.getCostPrice() * product.getStock();
            colIdx = 0;
            row = sheet.createRow(rownum);
            cell = row.createCell(colIdx);
            cell.setCellValue(product.getBarCode());
            colIdx = colIdx + 1;
            cell = row.createCell(colIdx);
            cell.setCellValue(product.getDescription().toUpperCase());
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(product.getCategory().getName());
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(product.getStockToDisplay());
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(product.getSellingUnit().toUpperCase());
            ++colIdx;
            cell = row.createCell(colIdx);
            cell.setCellValue(productPrice.getCostPriceToDisplay());
            ++colIdx;
            double subtotalCost = productPrice.getCostPrice() * product.getStock();
            String subtotalCostStr = String.valueOf(formatter.format(subtotalCost));
            subtotalCostStr = subtotalCostStr.replaceAll("\\.", ",");
            cell = row.createCell(colIdx);
            cell.setCellValue(subtotalCostStr);
            ++colIdx;
            ++rownum;
            //this.display.asyncExec(new 1(this));
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
         sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 5));
         String totalCostStr = String.valueOf(formatter.format(totalCost));
         totalCostStr = totalCostStr.replaceAll("\\.", ",");
         cell = row.createCell(6);
         cell.setCellValue(totalCostStr);
         cell.setCellStyle(cellStyleTotal);
         FileOutputStream out = new FileOutputStream(this.getFileName());
         workbook.write(out);
         out.close();
        // this.display.asyncExec(new 2(this));
      } catch (IOException var23) {
         //this.display.asyncExec(new 3(this));
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
