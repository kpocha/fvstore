package com.facilvirtual.fvstoresdesk.ui.utils;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.model.Vat;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

class ImportProductsFromFileProcessor extends Thread {
   private static Logger logger = LoggerFactory.getLogger("ImportProductsFromFileProcessor");
   private Display display;
   private ProgressBar progressBar;
   private Label lblProgressBarTitle;
   private Button cancelButton;
   private ImportProductsFromExcelProcessor dialog;
   private int maxProducts = 50000;

   public ImportProductsFromFileProcessor(Display display, ProgressBar progressBar, Label lblProgressBarTitle, Button cancelButton, ImportProductsFromExcelProcessor dialog) {
      this.display = display;
      this.progressBar = progressBar;
      this.lblProgressBarTitle = lblProgressBarTitle;
      this.cancelButton = cancelButton;
      this.dialog = dialog;
   }
   @Override
   public void run() {
      try {
         FileInputStream file = new FileInputStream(new File(this.dialog.getSettings().getFilename()));
         XSSFWorkbook workbook = new XSSFWorkbook(file);
         XSSFSheet sheet = workbook.getSheetAt(0);
         Iterator<Row> rowIterator = sheet.iterator();
         rowIterator.next();
         int numRow = 0;

         while(rowIterator.hasNext() && numRow < this.maxProducts) {
            try {
               Row row = (Row)rowIterator.next();
               ++numRow;
               Cell cell0 = row.getCell(0);
               Cell cell1 = row.getCell(1);
               Cell cell2 = row.getCell(2);
               Cell cell3 = row.getCell(3);
               Cell cell4 = row.getCell(4);
               Cell cell5 = row.getCell(5);
               Cell cell6 = row.getCell(6);
               Cell cell7 = row.getCell(7);
               Vat vat = this.dialog.getOrderService().getVat(new Long(1L));
               String barCode = this.getStringValueForCell(cell0);
               String description = this.getStringValueForCell(cell1);
               String categoryName = this.getStringValueForCell(cell2);
               double price = this.getNumericValueForCell(cell3);
               double costPrice = this.getNumericValueForCell(cell4);
               double stock = this.getNumericValueForCell(cell5);
               double stockMin = this.getNumericValueForCell(cell6);
               double stockMax = this.getNumericValueForCell(cell7);
               Product p = this.dialog.getProductService().getProductByBarCode(barCode);
               Date importDate = new Date();
               PriceList priceList = this.dialog.getSettings().getPriceList();
               ProductPrice productPrice = this.dialog.getProductService().getProductPriceForProduct(p, priceList);
               if (p != null) {
                  productPrice = this.dialog.getProductService().getProductPriceForProduct(p, priceList);
                  p.setVat(vat);
                  this.dialog.getProductService().saveProduct(p);
               }

               if (productPrice == null) {
                  productPrice = new ProductPrice();
                  productPrice.setPriceList(priceList);
                  productPrice.setProduct(p);
                  productPrice.setVat(vat);
               }

               ProductCategory category;
               if (p == null && !"".equals(barCode) && !"".equals(description)) {
                  p = new Product();
                  p.setVat(vat);
                  p.setDiscontinued(true);
                  p.setInWeb(false);
                  productPrice.setVat(vat);
                  p.setBarCode(barCode);
                  p.setDescription(description);
                  category = null;
                  if (!"".equals(categoryName)) {
                     category = this.dialog.getProductService().getOrCreateProductCategoryByName(categoryName);
                  } else {
                     category = this.dialog.getProductService().getOrCreateProductCategoryByName("Sin clasificar");
                  }

                  p.setCategory(category);
                  if (price > 0.0) {
                     productPrice.setSellingPrice(price);
                  }

                  if (costPrice > 0.0) {
                     productPrice.setCostPrice(costPrice);
                  }

                  if (stock > 0.0) {
                     p.setStock(stock);
                  }

                  if (stockMin > 0.0) {
                     p.setStockMin(stockMin);
                  }

                  if (stockMax > 0.0) {
                     p.setStockMax(stockMax);
                  }

                  p.setCreationDate(importDate);
                  p.setLastUpdatedDescription(importDate);
                  if (p.getSellingPrice() > 0.0) {
                     p.setLastUpdatedPrice(importDate);
                  }

                  p.setLastUpdatedCategory(importDate);
                  this.dialog.getProductService().saveProduct(p);
                  this.dialog.getProductService().saveProductPrice(productPrice);
               } else if (p != null && this.dialog.getSettings().isUpdateProducts()) {
                  if (!this.dialog.getSettings().isKeepDescription() && !description.equalsIgnoreCase(p.getDescription())) {
                     p.setDescription(description);
                     p.setLastUpdatedDescription(importDate);
                  }

                  if (!this.dialog.getSettings().isKeepCategory()) {
                     category = null;
                     if (!"".equals(categoryName)) {
                        category = this.dialog.getProductService().getOrCreateProductCategoryByName(categoryName);
                     } else {
                        category = this.dialog.getProductService().getOrCreateProductCategoryByName("Sin clasificar");
                     }

                     if (this.changedCategory(p, category)) {
                        p.setCategory(category);
                        p.setLastUpdatedCategory(importDate);
                     }
                  }

                  if (!this.dialog.getSettings().isKeepPrice() && price != productPrice.getSellingPrice()) {
                     productPrice.setSellingPrice(price);
                     productPrice.setLastUpdatedPrice(importDate);
                  }

                  if (!this.dialog.getSettings().isKeepCost() && costPrice != productPrice.getCostPrice()) {
                     productPrice.setCostPrice(costPrice);
                  }

                  if (!this.dialog.getSettings().isKeepStock() && stock != p.getStock()) {
                     p.setStock(stock);
                  }

                  if (!this.dialog.getSettings().isKeepStockMin() && stockMin != p.getStockMin()) {
                     p.setStockMin(stockMin);
                  }

                  if (!this.dialog.getSettings().isKeepStockMax() && stockMax != p.getStockMax()) {
                     p.setStockMax(stockMax);
                  }

                  this.dialog.getProductService().saveProduct(p);
                  this.dialog.getProductService().saveProductPrice(productPrice);
               }

               productPrice.updateNetPrice();
               productPrice.updateGrossMargin();
               this.dialog.getProductService().saveProduct(p);
               this.dialog.getProductService().saveProductPrice(productPrice);
              // this.display.asyncExec(new 1(this));
            } catch (Exception var34) {
               logger.error("Error al importar producto. Fila: " + numRow);
            }
         }

         file.close();
         //this.display.asyncExec(new 2(this));
      } catch (Exception var35) {
         logger.error("Error al importar productos desde archivo");
         logger.error(var35.getMessage());
        // logger.error(var35);
      }

   }

   private boolean changedCategory(Product product, ProductCategory category) {
      boolean changed = false;
      if (product.getCategory() != null && category != null && !product.getCategory().getName().equalsIgnoreCase(category.getName())) {
         changed = true;
      } else if (product.getCategory() == null && category != null) {
         changed = true;
      } else if (product.getCategory() != null && category == null) {
         changed = true;
      }

      return changed;
   }

   private String getStringValueForCell(Cell cell) {
      String value = "";

      try {
         value = cell.getStringCellValue().trim();
      } catch (Exception var5) {
         value = "";
      }

      if ("".equals(value)) {
         try {
            DecimalFormat df = new DecimalFormat("#");
            value = df.format(cell.getNumericCellValue());
         } catch (Exception var4) {
            value = "";
         }
      }

      if ("0".equals(value)) {
         value = "";
      }

      return value;
   }

   private double getNumericValueForCell(Cell cell) {
      double value = 0.0;

      try {
         value = cell.getNumericCellValue();
      } catch (Exception var6) {
         value = 0.0;
      }

      if (value == 0.0) {
         try {
            value = Double.parseDouble(cell.getStringCellValue().trim().replaceAll(",", "\\."));
         } catch (Exception var5) {
            value = 0.0;
         }
      }

      return value;
   }
}
