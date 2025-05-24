package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
   name = "fvpos_vat"
)
public class Vat implements Serializable {
   private static final long serialVersionUID = -813318621148147631L;
   @Id
   @Column(
      name = "vat_id"
   )
   @GeneratedValue
   private Long id;
   @Column(
      name = "vat_name",
      unique = true
   )
   private String name;
   @Column(
      name = "vat_value"
   )
   private double value;

   public Vat() {
   }

   public Vat(String vatName, double vatValue) {
      this.name = vatName;
      this.value = vatValue;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public double getValue() {
      return this.value;
   }

   public void setValue(double value) {
      this.value = value;
   }
}
