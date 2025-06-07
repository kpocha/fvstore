package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(
   name = "fvpos_product_photo"
)
public class ProductPhoto implements Serializable {
   private static final long serialVersionUID = 8326994427956715466L;
   @Id
   @Column(
      name = "photo_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Column(
      name = "filename",
      unique = true,
      nullable = false
   )
   private String filename;
   @Column(
      name = "is_active"
   )
   private boolean active = true;
   @Column(
      name = "position"
   )
   private int position = 0;
   @ManyToOne
   @JoinColumn(
      name = "product_id"
   )
   private Product product;

   public ProductPhoto() {
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFilename() {
      return this.filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public int getPosition() {
      return this.position;
   }

   public void setPosition(int position) {
      this.position = position;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }
}
