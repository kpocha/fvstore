package com.facilvirtual.fvstoresdesk.ui.list;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.service.ProductService;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.apache.poi.ss.usermodel.Cell;
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

class ListOfPricesGenerator extends Thread {
   protected static Logger logger = LoggerFactory.getLogger("ListOfPricesGenerator");
   private Display display;
   private ProgressBar progressBar;
   private String fileName = "";
   private List<Product> products = new ArrayList();
   private PriceList priceList;
   private ProductService productService;
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private ListOfPricesExcelGenerator dialog;

   public ListOfPricesGenerator(Display display, ProgressBar progressBar, List<Product> products, PriceList priceList, ProductService productService, String fileName, Label lblProgressBarTitle, Button cancelButton, ListOfPricesExcelGenerator dialog) {
      this.display = display;
      this.progressBar = progressBar;
      this.products = products;
      this.priceList = priceList;
      this.productService = productService;
      this.fileName = fileName;
      this.lblProgressBarTitle = lblProgressBarTitle;
      this.cancelButton = cancelButton;
      this.dialog = dialog;
   }
   @Override
   public void run() {
      try {
         XSSFWorkbook workbook = new XSSFWorkbook();
         XSSFSheet sheet = workbook.createSheet("Lista de precios (" + this.priceList.getName() + ")");
         XSSFCellStyle cellStyle = workbook.createCellStyle();
         cellStyle.setFillForegroundColor(new XSSFColor((IndexedColorMap) new Color(50, 135, 54)));
         cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
         cell.setCellValue("Lista de precios (" + this.priceList.getName() + ")");
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
         cell.setCellValue("Precio Venta");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 4500);
         cell.setCellValue("Act. Precio Venta");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 4000);
         cell.setCellValue("Unidad Venta");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 2500);
         cell.setCellValue("Oferta");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         cell = row.createCell(colIdx);
         sheet.setColumnWidth(colIdx, 2500);
         cell.setCellValue("Web");
         cell.setCellStyle(cellStyle);
         ++colIdx;
         ++rownum;
         Iterator var13 = this.products.iterator();

         while(var13.hasNext()) {
            Product product = (Product)var13.next();

            try {
               ProductPrice productPrice = this.productService.getProductPriceForProduct(product, this.priceList);
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
               if (productPrice != null) {
                  cell = row.createCell(colIdx);
                  cell.setCellValue(productPrice.getSellingPrice());
               } else {
                  cell = row.createCell(colIdx);
                  cell.setCellValue(0.0);
               }

               ++colIdx;
               if (productPrice != null) {
                  cell = row.createCell(colIdx);
                  cell.setCellValue(productPrice.getLastUpdatedPriceForLabel());
               } else {
                  cell = row.createCell(colIdx);
                  cell.setCellValue("");
               }

               ++colIdx;
               cell = row.createCell(colIdx);
               cell.setCellValue(product.getSellingUnit().toUpperCase());
               ++colIdx;
               String inOfferStr = product.isInOffer() ? "S" : "N";
               cell = row.createCell(colIdx);
               cell.setCellValue(inOfferStr);
               ++colIdx;
               String inWebStr = product.isInWeb() ? "S" : "N";
               cell = row.createCell(colIdx);
               cell.setCellValue(inWebStr);
               ++colIdx;
            } catch (Exception var16) {
               logger.error("Error al procesar el producto: " + product.toString());
            }

            ++rownum;
            //this.display.asyncExec(new 1(this));
         }

         FileOutputStream out = new FileOutputStream(this.getFileName());
         workbook.write(out);
         out.close();
         //this.display.asyncExec(new 2(this));
      } catch (IOException var17) {
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
