package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
   name = "fvpos_receipt_type"
)
public class ReceiptType implements Serializable {
   private static final long serialVersionUID = -1199896088813561298L;
   @Id
   @Column(
      name = "receipt_type_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Column(
      name = "type_name",
      unique = true,
      nullable = false
   )
   private String name;
   @Column(
      name = "is_active"
   )
   private boolean active = true;
   @Column(
      name = "is_available_for_purchase"
   )
   private boolean availableForPurchase = true;
   @Column(
      name = "is_available_for_order"
   )
   private boolean availableForOrder = true;

   public ReceiptType() {
   }

   public ReceiptType(String name, boolean availableForPurchase, boolean availableForOrder) {
      this.name = name;
      this.availableForPurchase = availableForPurchase;
      this.availableForOrder = availableForOrder;
   }

   public ReceiptType(String name, boolean availableForPurchase, boolean availableForOrder, boolean active) {
      this.name = name;
      this.availableForPurchase = availableForPurchase;
      this.availableForOrder = availableForOrder;
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

   public boolean isAvailableForPurchase() {
      return this.availableForPurchase;
   }

   public void setAvailableForPurchase(boolean availableForPurchase) {
      this.availableForPurchase = availableForPurchase;
   }

   public boolean isAvailableForOrder() {
      return this.availableForOrder;
   }

   public void setAvailableForOrder(boolean availableForOrder) {
      this.availableForOrder = availableForOrder;
   }

   public boolean isNoFiscalTicket() {
      return this.getName().equalsIgnoreCase("Ticket No Fiscal");
   }

   public boolean isFiscalTicket() {
      return this.getName().equalsIgnoreCase("Ticket Fiscal");
   }
}
