package com.facilvirtual.fvstoresdesk.util;

import java.text.DecimalFormat;

public class CryptUtils {
   private CryptUtils() {
   }

   public static double encrypt1(double value) {
      if (value < 0.0) {
         value *= -1.0;
      }

      return value * 3.0;
   }

   public static double encrypt2(double value) {
      if (value < 0.0) {
         value *= -1.0;
      }

      return value * 4.0;
   }

   public static double encrypt3(double value) {
      if (value < 0.0) {
         value *= -1.0;
      }

      return value * 5.0;
   }

   public static double decrypt1(double value) {
      if (value < 0.0) {
         value *= -1.0;
      }

      return value / 3.0;
   }

   public static double decrypt2(double value) {
      if (value < 0.0) {
         value *= -1.0;
      }

      return value / 4.0;
   }

   public static double decrypt3(double value) {
      if (value < 0.0) {
         value *= -1.0;
      }

      return value / 5.0;
   }

   public static String obfuscateKey(String key) {
      String newKey = "";
      int middle = key.length() / 2;
      String part1 = key.substring(0, middle);
      String part2 = key.substring(middle, key.length());
      int j = 0;

      for(int i = 0; i < key.length(); ++i) {
         if (i % 2 == 0) {
            newKey = newKey + part1.substring(j, j + 1);
         } else {
            newKey = newKey + part2.substring(j, j + 1);
            ++j;
         }
      }

      return newKey;
   }

   public static String deobfuscateKey(String key) {
      String newKey = "";

      int i;
      for(i = 0; i < key.length(); ++i) {
         if (i % 2 == 0) {
            newKey = newKey + key.substring(i, i + 1);
         }
      }

      for(i = 0; i < key.length(); ++i) {
         if (i % 2 != 0) {
            newKey = newKey + key.substring(i, i + 1);
         }
      }

      return newKey;
   }

   public static String formatKey(String key) {
      String fCode;
      for(fCode = ""; key.length() % 4 != 0; key = "0" + key) {
      }

      for(int idx = 0; idx + 4 <= key.length(); idx += 4) {
         fCode = fCode + key.substring(idx, idx + 4);
         fCode = fCode + "-";
      }

      fCode = fCode.substring(0, fCode.length() - 1);
      return fCode;
   }

   public static String unformatKey(String key) {
      String newKey;
      for(newKey = key.replaceAll("-", ""); newKey.startsWith("0"); newKey = newKey.substring(1, newKey.length())) {
      }

      return newKey;
   }

   public static String generateCodFacturaElectronica(double serialNumber) {
      String codFacturaElectronica = "";

      try {
         DecimalFormat df = new DecimalFormat("#");
         serialNumber = serialNumber * 3.0 + 129900.0;
         String serialStr = df.format(serialNumber);
         codFacturaElectronica = serialStr.substring(serialStr.length() - 6, serialStr.length());
      } catch (Exception var5) {
         codFacturaElectronica = "129900";
      }

      return codFacturaElectronica;
   }
}
