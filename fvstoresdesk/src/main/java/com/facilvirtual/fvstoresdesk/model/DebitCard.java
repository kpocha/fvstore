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
   name = "fvpos_debit_card"
)
public class DebitCard implements Serializable {
   private static final long serialVersionUID = 4288801109938663369L;
   @Id
   @Column(
      name = "card_id"
   )
   @GeneratedValue
   private Long id;
   @Column(
      name = "card_name",
      unique = true,
      nullable = false
   )
   private String name;
   @Column(
      name = "is_active"
   )
   private boolean active = true;
   @OneToMany(
      mappedBy = "debitCard"
   )
   private List<Order> orders = new ArrayList();

   public DebitCard() {
   }

   public DebitCard(String cardName) {
      this.name = cardName;
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

   public List<Order> getOrders() {
      return this.orders;
   }

   public void setOrders(List<Order> orders) {
      this.orders = orders;
   }
}
