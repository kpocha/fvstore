package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
   name = "fvpos_product_category"
)
public class ProductCategory implements Serializable, Comparable<ProductCategory> {
   private static final long serialVersionUID = 4288801109938663369L;
   @Id
   @Column(
      name = "category_id"
   )
   @GeneratedValue
   private Long id;
   @Column(
      name = "category_name",
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
   @ManyToOne
   @JoinColumn(
      name = "vat_id"
   )
   private Vat vat;
   @OneToMany(
      mappedBy = "category"
   )
   private List<Product> products = new ArrayList();
   @OneToMany(
      mappedBy = "parent"
   )
   private List<ProductCategory> subcategories = new ArrayList();
   @ManyToOne
   @JoinColumn(
      name = "parent_id"
   )
   private ProductCategory parent;
   @Column(
      name = "position",
      nullable = false
   )
   private int position = 0;
   @Column(
      name = "positionInCash",
      nullable = false
   )
   private int positionInCash = 0;

   public ProductCategory() {
   }

   public ProductCategory(String categoryName, Vat vat) {
      this.name = categoryName;
      this.vat = vat;
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

   public String getPhoto() {
      return this.photo;
   }

   public void setPhoto(String photo) {
      this.photo = photo;
   }

   public List<ProductCategory> getSubcategories() {
      return this.subcategories;
   }

   public void setSubcategories(List<ProductCategory> subcategories) {
      this.subcategories = subcategories;
   }

   public ProductCategory getParent() {
      return this.parent;
   }

   public void setParent(ProductCategory parent) {
      this.parent = parent;
   }

   public int getPosition() {
      return this.position;
   }

   public void setPosition(int position) {
      this.position = position;
   }

   public int getPositionInCash() {
      return this.positionInCash;
   }

   public void setPositionInCash(int positionInCash) {
      this.positionInCash = positionInCash;
   }

   public int compareTo(ProductCategory o) {
      return this.getName().toUpperCase().compareTo(o.getName().toUpperCase());
   }

   public String getCode() {
      String idStr;
      for(idStr = String.valueOf(this.getId()); idStr.length() < 3; idStr = "0" + idStr) {
      }

      return "DEPT" + idStr;
   }

   public Vat getVat() {
      return this.vat;
   }

   public void setVat(Vat vat) {
      this.vat = vat;
   }

   public String getVatToDisplay() {
      if (this.getVat() != null) {
         DecimalFormat formatter = new DecimalFormat("0.00");
         String str = String.valueOf(formatter.format(this.getVat().getValue()));
         str = str.replaceAll("\\.", ",");
         return this.getVat().getValue() != 0.0 ? str : "0,00";
      } else {
         return "0,00";
      }
   }

   public String getNumberToDisplay() {
      String idStr;
      for(idStr = String.valueOf(this.getId()); idStr.length() < 3; idStr = "0" + idStr) {
      }

      return idStr;
   }
}
