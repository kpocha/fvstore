package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
   name = "fvpos_sale_condition"
)
public class SaleCondition implements Serializable {
   private static final long serialVersionUID = 4288801109938663369L;
   @Id
   @Column(
      name = "condition_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Column(
      name = "condition_name",
      unique = true,
      nullable = false
   )
   private String name;
   @Column(
      name = "abbreviate_name",
      unique = true,
      nullable = false
   )
   private String abbreviateName;
   @Column(
      name = "is_active"
   )
   private boolean active = true;
   @Column(
      name = "is_available_for_company"
   )
   private boolean availableForCompany = true;
   @Column(
      name = "is_available_for_customer"
   )
   private boolean availableForCustomer = true;

   public SaleCondition() {
   }

   public SaleCondition(String conditionName, String abbreviateName, boolean availableForCompany, boolean availableForCustomer) {
      this.name = conditionName;
      this.abbreviateName = abbreviateName;
      this.availableForCompany = availableForCompany;
      this.availableForCustomer = availableForCustomer;
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

   public String getAbbreviateName() {
      return this.abbreviateName;
   }

   public void setAbbreviateName(String abbreviateName) {
      this.abbreviateName = abbreviateName;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public boolean isAvailableForCompany() {
      return this.availableForCompany;
   }

   public void setAvailableForCompany(boolean availableForCompany) {
      this.availableForCompany = availableForCompany;
   }

   public boolean isAvailableForCustomer() {
      return this.availableForCustomer;
   }

   public void setAvailableForCustomer(boolean availableForCustomer) {
      this.availableForCustomer = availableForCustomer;
   }
}
