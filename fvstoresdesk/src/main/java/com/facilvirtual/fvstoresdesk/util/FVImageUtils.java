package com.facilvirtual.fvstoresdesk.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FVImageUtils {
   static final Logger logger = LoggerFactory.getLogger(FVImageUtils.class);

   public FVImageUtils() {
   }

   public static Image resizeOLD(Image image, int width, int height) {
      Image scaled = new Image(Display.getDefault(), width, height);
      GC gc = new GC(scaled);
      gc.setAntialias(1);
      gc.setInterpolation(2);
      gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
      gc.dispose();
      image.dispose();
      return scaled;
   }

   public static Image scaleTo(Image image, int maxWidth, int maxHeight) {
      Image scaledImage = null;

      try {
         int imgWidth = image.getBounds().width;
         int imgHeight = image.getBounds().height;
        // int newWidth = false;
         //int newHeight = false;
         int newWidth;
         int newHeight;
         if (imgWidth < maxWidth && imgHeight < maxHeight) {
            newWidth = imgWidth;
            newHeight = imgHeight;
         } else {
            float rW = (float)maxWidth / (float)imgWidth;
            float rH = (float)maxHeight / (float)imgHeight;
            float R = rW < rH ? rW : rH;
            newWidth = (int)((float)imgWidth * R);
            newHeight = (int)((float)imgHeight * R);
         }

         scaledImage = new Image(Display.getDefault(), image.getImageData().scaledTo(newWidth, newHeight));
      } catch (Exception var11) {
      }

      return scaledImage;
   }

   public static int calculateLeftMargin(Image image, int width) {
      int leftMargin = 0;

      try {
         int imgWidth = image.getBounds().width;
         if (imgWidth < width) {
            leftMargin = (int)((float)width - (float)imgWidth) / 2;
         }
      } catch (Exception var4) {
      }

      return leftMargin;
   }

   public static int calculateTopMargin(Image image, int height) {
      int topMargin = 0;

      try {
         int imgHeight = image.getBounds().height;
         if (imgHeight < height) {
            topMargin = (int)((float)height - (float)imgHeight) / 2;
         }
      } catch (Exception var4) {
      }

      return topMargin;
   }

   public static ImageData convertAWTImageToSWT(java.awt.Image image) {
      if (image == null) {
         throw new IllegalArgumentException("Null 'image' argument.");
      } else {
         int w = image.getWidth((ImageObserver)null);
         int h = image.getHeight((ImageObserver)null);
         if (w != -1 && h != -1) {
            BufferedImage bi = new BufferedImage(w, h, 1);
            Graphics g = bi.getGraphics();
            g.drawImage(image, 0, 0, (ImageObserver)null);
            g.dispose();
            return convertToSWT(bi);
         } else {
            return null;
         }
      }
   }

   public static ImageData convertToSWT(BufferedImage bufferedImage) {
      int i;
      if (bufferedImage.getColorModel() instanceof DirectColorModel) {
         DirectColorModel colorModel = (DirectColorModel)bufferedImage.getColorModel();
         PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(), colorModel.getBlueMask());
         ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
         WritableRaster raster = bufferedImage.getRaster();
         int[] pixelArray = new int[3];

         for(int y = 0; y < data.height; ++y) {
            for(i = 0; i < data.width; ++i) {
               raster.getPixel(i, y, pixelArray);
               int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
               data.setPixel(i, y, pixel);
            }
         }

         return data;
      } else if (!(bufferedImage.getColorModel() instanceof IndexColorModel)) {
         return null;
      } else {
         IndexColorModel colorModel = (IndexColorModel)bufferedImage.getColorModel();
         int size = colorModel.getMapSize();
         byte[] reds = new byte[size];
         byte[] greens = new byte[size];
         byte[] blues = new byte[size];
         colorModel.getReds(reds);
         colorModel.getGreens(greens);
         colorModel.getBlues(blues);
         RGB[] rgbs = new RGB[size];

         for(i = 0; i < rgbs.length; ++i) {
            rgbs[i] = new RGB(reds[i] & 255, greens[i] & 255, blues[i] & 255);
         }

         PaletteData palette = new PaletteData(rgbs);
         ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
         data.transparentPixel = colorModel.getTransparentPixel();
         WritableRaster raster = bufferedImage.getRaster();
         int[] pixelArray = new int[1];

         for(int y = 0; y < data.height; ++y) {
            for(int x = 0; x < data.width; ++x) {
               raster.getPixel(x, y, pixelArray);
               data.setPixel(x, y, pixelArray[0]);
            }
         }

         return data;
      }
   }
}
