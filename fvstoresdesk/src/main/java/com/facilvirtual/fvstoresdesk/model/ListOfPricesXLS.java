package com.facilvirtual.fvstoresdesk.model;

import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.service.ProductService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;

public class ListOfPricesXLS {
   private String fileName;

   public ListOfPricesXLS(String fileName) {
      this.fileName = fileName;
   }

   public void saveFile() throws IOException {
      String dateToFile = this.createDateToFile();
      ByteArrayOutputStream out = new ByteArrayOutputStream();

      try {
         XSSFWorkbook workbook = new XSSFWorkbook();
         XSSFSheet sheet = workbook.createSheet("Productos");
         int rownum = 0;
         List<Product> products = this.getProductService().getAllProductsWithPrice();
         Row row = sheet.createRow(rownum);
         Cell cell = row.createCell(0);
         cell.setCellValue("Código de barras");
         cell = row.createCell(1);
         cell.setCellValue("Descripción");
         cell = row.createCell(2);
         cell.setCellValue("Precio de Venta");
         cell = row.createCell(3);
         ++rownum;

         for(Iterator var10 = products.iterator(); var10.hasNext(); ++rownum) {
            Product product = (Product)var10.next();
            row = sheet.createRow(rownum);
            cell = row.createCell(0);
            cell.setCellValue(product.getBarCode());
            cell = row.createCell(1);
            cell.setCellValue(product.getDescription().toUpperCase());
            cell = row.createCell(2);
            cell.setCellValue(product.getSellingPrice());
            cell = row.createCell(3);
         }

         workbook.write(out);
         InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
         OutputStream outputStream = null;

         try {
            outputStream = new FileOutputStream(new File(this.getFileName()));
            //int read ;
            byte[] bytes = new byte[1024];

            int read;
            while((read = inputStream.read(bytes)) != -1) {
               outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");
         } catch (IOException var38) {
            var38.printStackTrace();
         } finally {
            if (inputStream != null) {
               try {
                  inputStream.close();
               } catch (IOException var37) {
                  var37.printStackTrace();
               }
            }

            if (outputStream != null) {
               try {
                  outputStream.close();
               } catch (IOException var36) {
                  var36.printStackTrace();
               }
            }

         }
      } catch (Exception var40) {
         var40.printStackTrace();
      } finally {
         if (out != null) {
            out.close();
         }

      }

   }

   public ProductService getProductService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (ProductService)context.getBean("productService");
   }

   private String createDateToFile() {
      Format formatter = new SimpleDateFormat("yyyyMMdd_HHmm");
      return formatter.format(new Date());
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }
}
