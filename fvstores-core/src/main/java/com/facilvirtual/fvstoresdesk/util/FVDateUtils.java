package com.facilvirtual.fvstoresdesk.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FVDateUtils {
   private FVDateUtils() {
   }

   public static int daysBetween(Date date1, Date date2) {
      //int days = false;

      int days;
      try {
         Calendar cal1 = new GregorianCalendar();
         Calendar cal2 = new GregorianCalendar();
         cal1.setTime(date1);
         cal2.setTime(date2);
         days = (int)((cal2.getTime().getTime() - cal1.getTime().getTime()) / 86400000L);
      } catch (Exception var5) {
         days = -1;
      }

      return days;
   }
}
