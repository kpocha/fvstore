package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
   name = "fvpos_price_list"
)
public class PriceList implements Serializable {
   private static final long serialVersionUID = 4288801109938663369L;
   @Id
   @Column(
      name = "list_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Column(
      name = "list_name",
      unique = true,
      nullable = false
   )
   private String name;
   @Column(
      name = "is_active"
   )
   private boolean active = true;

   public PriceList() {
   }

   public PriceList(String listName) {
      this.name = listName;
   }

   public PriceList(String listName, boolean active) {
      this.name = listName;
      this.active = active;
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

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }
}
