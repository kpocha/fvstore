package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;

public class AfipProp implements Serializable {
   private static final long serialVersionUID = -813318621148147631L;
   private String afipToken = "";
   private String afipSign = "";

   public AfipProp() {
   }

   public String getAfipToken() {
      return this.afipToken;
   }

   public void setAfipToken(String afipToken) {
      this.afipToken = afipToken;
   }

   public String getAfipSign() {
      return this.afipSign;
   }

   public void setAfipSign(String afipSign) {
      this.afipSign = afipSign;
   }
}
