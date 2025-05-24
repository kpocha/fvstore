package com.facilvirtual.fvstoresdesk.util;

import java.text.DecimalFormat;

public class FVMathUtils {
   private FVMathUtils() {
   }

   public static double roundValue(double valueToRound) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(valueToRound));
      return Double.valueOf(str.replaceAll(",", "."));
   }
}
