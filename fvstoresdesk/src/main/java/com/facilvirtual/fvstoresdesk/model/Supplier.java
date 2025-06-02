package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(
   name = "fvpos_supplier"
)
public class Supplier implements Serializable, Comparable<Supplier> {
   private static final long serialVersionUID = -813318621148147631L;
   @Id
   @Column(
      name = "supplier_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "creation_date",
      nullable = false
   )
   private Date creationDate;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "last_updated_date",
      nullable = false
   )
   private Date lastUpdatedDate;
   @Column(
      name = "photo"
   )
   private String photo;
   @Column(
      name = "is_active",
      nullable = false
   )
   private boolean active = true;
   @Column(
      name = "company_name"
   )
   private String companyName;
   @Column(
      name = "business_name"
   )
   private String businessName;
   @Column(
      name = "phone"
   )
   private String phone;
   @Column(
      name = "mobile"
   )
   private String mobile;
   @Column(
      name = "email"
   )
   private String email;
   @Column(
      name = "address_street"
   )
   private String addressStreet;
   @Column(
      name = "address_number"
   )
   private String addressNumber;
   @Column(
      name = "address_extra"
   )
   private String addressExtra;
   @Column(
      name = "fiscal_address_street"
   )
   private String fiscalAddressStreet;
   @Column(
      name = "fiscal_address_number"
   )
   private String fiscalAddressNumber;
   @Column(
      name = "fiscal_address_extra"
   )
   private String fiscalAddressExtra;
   @Column(
      name = "postal_code"
   )
   private String postalCode = "";
   @Column(
      name = "city"
   )
   private String city;
   @Column(
      name = "contact_name"
   )
   private String contactName;
   @Column(
      name = "cuit"
   )
   private String cuit;
   @Column(
      name = "gross_income_number"
   )
   private String grossIncomeNumber;
   @Column(
      name = "website"
   )
   private String website;
   @Column(
      name = "observations"
   )
   private String observations;
   @ManyToOne
   @JoinColumn(
      name = "vat_condition_id",
      nullable = false
   )
   private VatCondition vatCondition;
   @OneToMany(
      mappedBy = "supplier"
   )
   private List<Purchase> purchases = new ArrayList();
   @Column(
      name = "is_allow_on_account_op",
      nullable = false
   )
   private boolean allowOnAccountOperations = true;
   @Column(
      name = "is_on_account_limited",
      nullable = false
   )
   private boolean onAccountLimited = false;
   @Column(
      name = "on_account_limit",
      nullable = false
   )
   private double onAccountLimit = 0.0;
   @Column(
      name = "on_account_total",
      nullable = false
   )
   private double onAccountTotal = 0.0;

   public Supplier() {
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Date getCreationDate() {
      return this.creationDate;
   }

   public void setCreationDate(Date creationDate) {
      this.creationDate = creationDate;
   }

   public Date getLastUpdatedDate() {
      return this.lastUpdatedDate;
   }

   public void setLastUpdatedDate(Date lastUpdatedDate) {
      this.lastUpdatedDate = lastUpdatedDate;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getMobile() {
      return this.mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getAddressStreet() {
      return this.addressStreet;
   }

   public void setAddressStreet(String addressStreet) {
      this.addressStreet = addressStreet;
   }

   public String getAddressNumber() {
      return this.addressNumber;
   }

   public void setAddressNumber(String addressNumber) {
      this.addressNumber = addressNumber;
   }

   public String getAddressExtra() {
      return this.addressExtra;
   }

   public void setAddressExtra(String addressExtra) {
      this.addressExtra = addressExtra;
   }

   public String getCity() {
      return this.city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getContactName() {
      return this.contactName;
   }

   public void setContactName(String contactName) {
      this.contactName = contactName;
   }

   public String getCuit() {
      return this.cuit;
   }

   public void setCuit(String cuit) {
      this.cuit = cuit;
   }

   public String getObservations() {
      return this.observations;
   }

   public void setObservations(String observations) {
      this.observations = observations;
   }

   public VatCondition getVatCondition() {
      return this.vatCondition;
   }

   public void setVatCondition(VatCondition vatCondition) {
      this.vatCondition = vatCondition;
   }

   public List<Purchase> getPurchases() {
      return this.purchases;
   }

   public void setPurchases(List<Purchase> purchases) {
      this.purchases = purchases;
   }

   public String getCompanyName() {
      return this.companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public String getBusinessName() {
      return this.businessName;
   }

   public void setBusinessName(String businessName) {
      this.businessName = businessName;
   }

   public String getGrossIncomeNumber() {
      return this.grossIncomeNumber;
   }

   public void setGrossIncomeNumber(String grossIncomeNumber) {
      this.grossIncomeNumber = grossIncomeNumber;
   }

   public String getWebsite() {
      return this.website;
   }

   public void setWebsite(String website) {
      this.website = website;
   }

   public String getCreationDateToDisplay() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      String dateStr = formatter.format(this.getCreationDate());
      return dateStr;
   }

   public String getStatusToDisplay() {
      return this.isActive() ? "Activo" : "Eliminado";
   }

   public int compareTo(Supplier o) {
      return this.getCompanyName().toUpperCase().compareTo(o.getCompanyName().toUpperCase());
   }

   public String getFullAddress() {
      return this.getAddressStreet() + " " + this.getAddressNumber() + " " + this.getAddressExtra();
   }

   public String getPhoto() {
      return this.photo;
   }

   public void setPhoto(String photo) {
      this.photo = photo;
   }

   public String getPostalCode() {
      return this.postalCode;
   }

   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

   public String getFiscalAddressStreet() {
      return this.fiscalAddressStreet;
   }

   public void setFiscalAddressStreet(String fiscalAddressStreet) {
      this.fiscalAddressStreet = fiscalAddressStreet;
   }

   public String getFiscalAddressNumber() {
      return this.fiscalAddressNumber;
   }

   public void setFiscalAddressNumber(String fiscalAddressNumber) {
      this.fiscalAddressNumber = fiscalAddressNumber;
   }

   public String getFiscalAddressExtra() {
      return this.fiscalAddressExtra;
   }

   public void setFiscalAddressExtra(String fiscalAddressExtra) {
      this.fiscalAddressExtra = fiscalAddressExtra;
   }

   public boolean isAllowOnAccountOperations() {
      return this.allowOnAccountOperations;
   }

   public void setAllowOnAccountOperations(boolean allowOnAccountOperations) {
      this.allowOnAccountOperations = allowOnAccountOperations;
   }

   public boolean isOnAccountLimited() {
      return this.onAccountLimited;
   }

   public void setOnAccountLimited(boolean onAccountLimited) {
      this.onAccountLimited = onAccountLimited;
   }

   public double getOnAccountLimit() {
      return this.onAccountLimit;
   }

   public void setOnAccountLimit(double onAccountLimit) {
      this.onAccountLimit = onAccountLimit;
   }

   public double getOnAccountTotal() {
      return this.onAccountTotal;
   }

   public void setOnAccountTotal(double onAccountTotal) {
      this.onAccountTotal = onAccountTotal;
   }
}
