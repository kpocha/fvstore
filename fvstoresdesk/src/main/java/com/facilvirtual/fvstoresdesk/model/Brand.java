package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
   name = "fvpos_brand"
)
public class Brand implements Serializable {
   private static final long serialVersionUID = -1199896088813561298L;
   @Id
   @Column(
      name = "brand_id"
   )
   @GeneratedValue
   private Long id;
   @Column(
      name = "brand_name",
      unique = true,
      nullable = false
   )
   private String name;
   @Column(
      name = "photo"
   )
   private String photo;
   @Column(
      name = "is_active"
   )
   private boolean active = true;
   @OneToMany(
      mappedBy = "brand"
   )
   private List<Product> products = new ArrayList();

   public Brand() {
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

   public List<Product> getProducts() {
      return this.products;
   }

   public void setProducts(List<Product> products) {
      this.products = products;
   }

   public String getPhoto() {
      return this.photo;
   }

   public void setPhoto(String photo) {
      this.photo = photo;
   }
}
