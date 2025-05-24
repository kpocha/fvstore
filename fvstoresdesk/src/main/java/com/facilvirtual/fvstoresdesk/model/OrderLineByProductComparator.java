package com.facilvirtual.fvstoresdesk.model;

import java.util.Comparator;

public class OrderLineByProductComparator implements Comparator<OrderLine> {
   public OrderLineByProductComparator() {
   }

   public int compare(OrderLine o1, OrderLine o2) {
      String cat1 = "";
      if (o1.getProduct() != null) {
         cat1 = o1.getProduct().getBarCode() != null ? o1.getProduct().getBarCode() : "";
      } else {
         cat1 = o1.getCategory().getName();
      }

      String cat2 = "";
      if (o2.getProduct() != null) {
         cat2 = o2.getProduct().getBarCode() != null ? o2.getProduct().getBarCode() : "";
      } else {
         cat2 = o2.getCategory().getName();
      }

      return cat1.compareTo(cat2);
   }
}
