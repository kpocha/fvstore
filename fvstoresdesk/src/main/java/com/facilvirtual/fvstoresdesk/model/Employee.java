package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(
   name = "fvpos_employee"
)
public class Employee implements Serializable, Comparable<Employee> {
   private static final long serialVersionUID = -813318621148147631L;
   @Id
   @Column(
      name = "employee_id"
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
      name = "username",
      unique = true,
      nullable = false
   )
   private String username = "";
   @Column(
      name = "password",
      nullable = false
   )
   private String password = "";
   @Column(
      name = "is_admin",
      nullable = false
   )
   private boolean admin = false;
   @Column(
      name = "first_name",
      nullable = false
   )
   private String firstName = "";
   @Column(
      name = "last_name",
      nullable = false
   )
   private String lastName = "";
   @Column(
      name = "document_type"
   )
   private String documentType = "";
   @Column(
      name = "document_number"
   )
   private String documentNumber = "";
   @Column(
      name = "phone"
   )
   private String phone = "";
   @Column(
      name = "mobile"
   )
   private String mobile = "";
   @Column(
      name = "email"
   )
   private String email = "";
   @Column(
      name = "address_street"
   )
   private String addressStreet = "";
   @Column(
      name = "address_number"
   )
   private String addressNumber = "";
   @Column(
      name = "address_extra"  
   )
   private String addressExtra = "";
   @Column(
      name = "postal_code"
   )
   private String postalCode = "";
   @Column(
      name = "city"
   )
   private String city = "";
   @Column(
      name = "cuil"
   )
   private String cuil = "";
   @Column(
      name = "website"
   )
   private String website = "";
   @Column(
      name = "observations"
   )
   private String observations = "";
   @Column(
      name = "job_position",
      nullable = false
   )
   private String jobPosition = "";
   @OneToMany(
      mappedBy = "cashier"
   )
   private List<Order> orders = new ArrayList();
   @Column(
      name = "is_shift1",
      nullable = false
   )
   private boolean shift1 = false;
   @Column(
      name = "is_shift2",
      nullable = false
   )
   private boolean shift2 = false;
   @Column(
      name = "is_shift3",
      nullable = false
   )
   private boolean shift3 = false;
   @Column(
      name = "allow_login",
      nullable = false
   )
   private boolean allowLogin = false;
   @Column(
      name = "allow_open_cash",
      nullable = false
   )
   private boolean allowOpenCash = false;
   @Column(
      name = "allow_close_cash",
      nullable = false
   )
   private boolean allowCloseCash = false;
   @Column(
      name = "allow_create_income",
      nullable = false
   )
   private boolean allowCreateIncome = false;
   @Column(
      name = "allow_create_outflow",
      nullable = false
   )
   private boolean allowCreateOutflow = false;
   @Column(
      name = "allow_create_order",
      nullable = false
   )
   private boolean allowCreateOrder = false;
   @Column(
      name = "allow_create_purchase",
      nullable = false
   )
   private boolean allowCreatePurchase = false;
   @Column(
      name = "allow_modify_price",
      nullable = false
   )
   private boolean allowModifyPrice = false;
   @Column(
      name = "allow_apply_discount",
      nullable = false
   )
   private boolean allowApplyDiscount = false;
   @Column(
      name = "allow_apply_surcharge",
      nullable = false
   )
   private boolean allowApplySurcharge = false;
   @Column(
      name = "allow_module_products",
      nullable = false
   )
   private boolean allowModuleProducts = false;
   @Column(
      name = "allow_module_orders",
      nullable = false
   )
   private boolean allowModuleOrders = false;
   @Column(
      name = "allow_module_purchases",
      nullable = false
   )
   private boolean allowModulePurchases = false;
   @Column(
      name = "allow_module_customers",
      nullable = false
   )
   private boolean allowModuleCustomers = false;
   @Column(
      name = "allow_module_suppliers",
      nullable = false
   )
   private boolean allowModuleSuppliers = false;
   @Column(
      name = "allow_module_lists",
      nullable = false
   )
   private boolean allowModuleLists = false;
   @Column(
      name = "allow_module_reports",
      nullable = false
   )
   private boolean allowModuleReports = false;
   @Column(
      name = "allow_module_tools",
      nullable = false
   )
   private boolean allowModuleTools = false;
   @Column(
      name = "allow_module_cash",
      nullable = false
   )
   private boolean allowModuleCash = false;
   @Column(
      name = "commission_per_sale",
      nullable = false
   )
   private double commissionPerSale = 0.0;

   public Employee() {
   }

   public double getCommissionPerSale() {
      return this.commissionPerSale;
   }

   public void setCommissionPerSale(double commissionPerSale) {
      this.commissionPerSale = commissionPerSale;
   }

   public String getJobPosition() {
      return this.jobPosition;
   }

   public void setJobPosition(String jobPosition) {
      this.jobPosition = jobPosition;
   }

   public boolean isShift1() {
      return this.shift1;
   }

   public void setShift1(boolean shift1) {
      this.shift1 = shift1;
   }

   public boolean isShift2() {
      return this.shift2;
   }

   public void setShift2(boolean shift2) {
      this.shift2 = shift2;
   }

   public boolean isShift3() {
      return this.shift3;
   }

   public void setShift3(boolean shift3) {
      this.shift3 = shift3;
   }

   public boolean isAllowLogin() {
      return this.allowLogin;
   }

   public void setAllowLogin(boolean allowLogin) {
      this.allowLogin = allowLogin;
   }

   public boolean isAdmin() {
      return this.admin;
   }

   public void setAdmin(boolean admin) {
      this.admin = admin;
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

   public String getLastName() {
      return this.lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public String getFirstName() {
      return this.firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
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

   public String getObservations() {
      return this.observations;
   }

   public void setObservations(String observations) {
      this.observations = observations;
   }

   public List<Order> getOrders() {
      return this.orders;
   }

   public void setOrders(List<Order> orders) {
      this.orders = orders;
   }

   public String getCuil() {
      return this.cuil;
   }

   public void setCuil(String cuil) {
      this.cuil = cuil;
   }

   public String getWebsite() {
      return this.website;
   }

   public void setWebsite(String website) {
      this.website = website;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getCreationDateToDisplay() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      String dateStr = formatter.format(this.getCreationDate());
      return dateStr;
   }

   public String getFullName() {
      String fullName = "";
      fullName = this.getLastName() + ", " + this.getFirstName();
      return fullName;
   }

   public String getStatusToDisplay() {
      return this.isActive() ? "Activo" : "Eliminado";
   }

   public int compareTo(Employee o) {
      return this.getFullName().toUpperCase().compareTo(o.getFullName().toUpperCase());
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

   public boolean isAllowOpenCash() {
      return this.allowOpenCash;
   }

   public void setAllowOpenCash(boolean allowOpenCash) {
      this.allowOpenCash = allowOpenCash;
   }

   public boolean isAllowCloseCash() {
      return this.allowCloseCash;
   }

   public void setAllowCloseCash(boolean allowCloseCash) {
      this.allowCloseCash = allowCloseCash;
   }

   public boolean isAllowCreateIncome() {
      return this.allowCreateIncome;
   }

   public void setAllowCreateIncome(boolean allowCreateIncome) {
      this.allowCreateIncome = allowCreateIncome;
   }

   public boolean isAllowCreateOutflow() {
      return this.allowCreateOutflow;
   }

   public void setAllowCreateOutflow(boolean allowCreateOutflow) {
      this.allowCreateOutflow = allowCreateOutflow;
   }

   public boolean isAllowCreateOrder() {
      return this.allowCreateOrder;
   }

   public void setAllowCreateOrder(boolean allowCreateOrder) {
      this.allowCreateOrder = allowCreateOrder;
   }

   public boolean isAllowCreatePurchase() {
      return this.allowCreatePurchase;
   }

   public void setAllowCreatePurchase(boolean allowCreatePurchase) {
      this.allowCreatePurchase = allowCreatePurchase;
   }

   public boolean isAllowModifyPrice() {
      return this.allowModifyPrice;
   }

   public void setAllowModifyPrice(boolean allowModifyPrice) {
      this.allowModifyPrice = allowModifyPrice;
   }

   public boolean isAllowApplyDiscount() {
      return this.allowApplyDiscount;
   }

   public void setAllowApplyDiscount(boolean allowApplyDiscount) {
      this.allowApplyDiscount = allowApplyDiscount;
   }

   public boolean isAllowModuleProducts() {
      return this.allowModuleProducts;
   }

   public void setAllowModuleProducts(boolean allowModuleProducts) {
      this.allowModuleProducts = allowModuleProducts;
   }

   public boolean isAllowModuleOrders() {
      return this.allowModuleOrders;
   }

   public void setAllowModuleOrders(boolean allowModuleOrders) {
      this.allowModuleOrders = allowModuleOrders;
   }

   public boolean isAllowModulePurchases() {
      return this.allowModulePurchases;
   }

   public void setAllowModulePurchases(boolean allowModulePurchases) {
      this.allowModulePurchases = allowModulePurchases;
   }

   public boolean isAllowModuleCustomers() {
      return this.allowModuleCustomers;
   }

   public void setAllowModuleCustomers(boolean allowModuleCustomers) {
      this.allowModuleCustomers = allowModuleCustomers;
   }

   public boolean isAllowModuleSuppliers() {
      return this.allowModuleSuppliers;
   }

   public void setAllowModuleSuppliers(boolean allowModuleSuppliers) {
      this.allowModuleSuppliers = allowModuleSuppliers;
   }

   public boolean isAllowModuleLists() {
      return this.allowModuleLists;
   }

   public void setAllowModuleLists(boolean allowModuleLists) {
      this.allowModuleLists = allowModuleLists;
   }

   public boolean isAllowModuleReports() {
      return this.allowModuleReports;
   }

   public void setAllowModuleReports(boolean allowModuleReports) {
      this.allowModuleReports = allowModuleReports;
   }

   public boolean isAllowModuleTools() {
      return this.allowModuleTools;
   }

   public void setAllowModuleTools(boolean allowModuleTools) {
      this.allowModuleTools = allowModuleTools;
   }

   public boolean isAllowModuleCash() {
      return this.allowModuleCash;
   }

   public void setAllowModuleCash(boolean allowModuleCash) {
      this.allowModuleCash = allowModuleCash;
   }

   public String getDocumentType() {
      return this.documentType;
   }

   public void setDocumentType(String documentType) {
      this.documentType = documentType;
   }

   public String getDocumentNumber() {
      return this.documentNumber;
   }

   public void setDocumentNumber(String documentNumber) {
      this.documentNumber = documentNumber;
   }

   public boolean isAllowApplySurcharge() {
      return this.allowApplySurcharge;
   }

   public void setAllowApplySurcharge(boolean allowApplySurcharge) {
      this.allowApplySurcharge = allowApplySurcharge;
   }

   public String getCommissionPerSaleToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getCommissionPerSale()));
      str = str.replaceAll("\\.", ",");
      return this.getCommissionPerSale() != 0.0 ? str : "0,00";
   }
}
