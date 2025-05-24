package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(
   name = "fvpos_customer"
)
public class Customer implements Serializable, Comparable<Customer> {
   private static final long serialVersionUID = -813318621148147631L;
   @Id
   @Column(
      name = "customer_id"
   )
   @GeneratedValue
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
      name = "account_type",
      nullable = false
   )
   private String accountType;
   @Column(
      name = "company_name"
   )
   private String companyName = "";
   @Column(
      name = "business_name"
   )
   private String businessName = "";
   @Column(
      name = "first_name"
   )
   private String firstName = "";
   @Column(
      name = "last_name"
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
      name = "fiscal_address_street"
   )
   private String fiscalAddressStreet = "";
   @Column(
      name = "fiscal_address_number"
   )
   private String fiscalAddressNumber = "";
   @Column(
      name = "fiscal_address_extra"
   )
   private String fiscalAddressExtra = "";
   @Column(
      name = "postal_code"
   )
   private String postalCode = "";
   @Column(
      name = "city"
   )
   private String city = "";
   @Column(
      name = "province"
   )
   private String province = "";
   @Column(
      name = "contact_name"
   )
   private String contactName = "";
   @Column(
      name = "cuit"
   )
   private String cuit = "";
   @Column(
      name = "cuil"
   )
   private String cuil = "";
   @Column(
      name = "gross_income_number"
   )
   private String grossIncomeNumber = "";
   @Column(
      name = "website"
   )
   private String website = "";
   @Column(
      name = "observations"
   )
   private String observations = "";
   @ManyToOne
   @JoinColumn(
      name = "vat_condition_id",
      nullable = false
   )
   private VatCondition vatCondition;
   @OneToMany(
      mappedBy = "customer"
   )
   private List<Order> orders = new ArrayList();
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

   public Customer() {
   }

   public boolean isAllowOnAccountOperations() {
      return this.allowOnAccountOperations;
   }

   public void setAllowOnAccountOperations(boolean allowOnAccountOperations) {
      this.allowOnAccountOperations = allowOnAccountOperations;
   }

   public double getOnAccountLimit() {
      return this.onAccountLimit;
   }

   public void setOnAccountLimit(double onAccountLimit) {
      this.onAccountLimit = onAccountLimit;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getDocumentNumber() {
      return this.documentNumber;
   }

   public void setDocumentNumber(String documentNumber) {
      this.documentNumber = documentNumber;
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

   public List<Order> getOrders() {
      return this.orders;
   }

   public void setOrders(List<Order> orders) {
      this.orders = orders;
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

   public String getCuil() {
      return this.cuil;
   }

   public void setCuil(String cuil) {
      this.cuil = cuil;
   }

   public String getGrossIncomeNumber() {
      return this.grossIncomeNumber;
   }

   public void setGrossIncomeNumber(String grossIncomeNumber) {
      this.grossIncomeNumber = grossIncomeNumber;
   }

   public String getAccountType() {
      return this.accountType;
   }

   public void setAccountType(String accountType) {
      this.accountType = accountType;
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

   public String getFullName() {
      String fullName = "";

      try {
         if (this.isHomeCustomer()) {
            if ((new Long(1L)).equals(this.getId())) {
               fullName = "- Cliente Ocasional -";
            } else if (this.getLastName() != null && !"".equals(this.getLastName())) {
               fullName = this.getLastName() + ", " + this.getFirstName();
            } else {
               fullName = this.getFirstName();
            }
         } else {
            fullName = this.getCompanyName();
         }
      } catch (Exception var3) {
      }

      return fullName.trim();
   }

   public String getFullNameToCompare() {
      String fullName = "";
      if (this.isHomeCustomer()) {
         if ((new Long(1L)).equals(this.getId())) {
            fullName = "- Cliente Ocasional -";
         } else if (this.getLastName() != null && !"".equals(this.getLastName())) {
            fullName = this.getLastName() + ", " + this.getFirstName();
         } else {
            fullName = this.getFirstName();
         }
      } else {
         fullName = this.getCompanyName();
      }

      return fullName;
   }

   public boolean isHomeCustomer() {
      return "HOME".equalsIgnoreCase(this.getAccountType());
   }

   public boolean isBusinessCustomer() {
      return "BUSINESS".equalsIgnoreCase(this.getAccountType());
   }

   public String getAccountTypeToDisplay() {
      return this.isHomeCustomer() ? "Individuo" : "Empresa";
   }

   public String getStatusToDisplay() {
      return this.isActive() ? "Activo" : "Eliminado";
   }

   public int compareTo(Customer o) {
      return this.getFullNameToCompare().toUpperCase().compareTo(o.getFullNameToCompare().toUpperCase());
   }

   public String getCustomerNumberToDisplay() {
      return String.format("%05d", this.getId());
   }

   public String getCuitToDisplay() {
      return this.getCuit() != null ? this.getCuit() : "";
   }

   public String getCuitForOrder() {
      return this.getCuit() != null ? this.getCuit() : "";
   }

   public String getAddressForOrder(int i) {
      String addressStr = "";
      addressStr = addressStr + (this.getAddressStreet() != null ? this.getAddressStreet() : "");
      addressStr = addressStr + (this.getAddressNumber() != null ? " " + this.getAddressNumber() : "");
      addressStr = addressStr + (this.getAddressExtra() != null ? " " + this.getAddressExtra() : "");
      return StringUtils.abbreviate(addressStr, i);
   }

   public String getAddressToPrint(int i) {
      String str = "";

      try {
         str = str + (this.getAddressStreet() != null ? this.getAddressStreet() : "");
         str = str + (this.getAddressNumber() != null ? " " + this.getAddressNumber() : "");
         if (str.length() > i) {
            str = str.substring(0, i);
         }
      } catch (Exception var4) {
      }

      return str.trim();
   }

   public String getCityForOrder() {
      String str = "";
      str = str + (this.getCity() != null ? this.getCity() : "");
      return StringUtils.abbreviate(str, 40);
   }

   public String getCityToPrint(int i) {
      String str = "";

      try {
         str = str + this.getCity();
         if (str.length() > i) {
            str = str.substring(0, i);
         }
      } catch (Exception var4) {
      }

      return str.trim();
   }

   public String getPhoneForOrder() {
      String str = "";
      str = str + (this.getPhone() != null ? this.getPhone() : "");
      if (str.length() > 0) {
         str = str + (this.getMobile() != null ? " / " + this.getMobile() : "");
      } else {
         str = str + (this.getMobile() != null ? this.getMobile() : "");
      }

      return StringUtils.abbreviate(str, 40);
   }

   public String getPhoneToPrint(int i) {
      String str = "";

      try {
         str = this.getPhone();
         if (str.length() > i) {
            str = str.substring(0, i);
         }
      } catch (Exception var4) {
      }

      return str;
   }

   public String getProvinceToPrint(int i) {
      String str = "";

      try {
         str = this.getProvince();
         if (str.length() > i) {
            str = str.substring(0, i);
         }
      } catch (Exception var4) {
      }

      return str;
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

   public boolean isOnAccountLimited() {
      return this.onAccountLimited;
   }

   public void setOnAccountLimited(boolean onAccountLimited) {
      this.onAccountLimited = onAccountLimited;
   }

   public double getOnAccountTotal() {
      return this.onAccountTotal;
   }

   public void setOnAccountTotal(double onAccountTotal) {
      this.onAccountTotal = onAccountTotal;
   }

   public String getDocumentType() {
      return this.documentType;
   }

   public void setDocumentType(String documentType) {
      this.documentType = documentType;
   }

   public String getOnAccountLimitToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getOnAccountLimit()));
      str = str.replaceAll("\\.", ",");
      return this.getOnAccountLimit() != 0.0 ? str : "";
   }

   public String getOnAccountTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getOnAccountTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getOnAccountTotal() != 0.0 ? str : "0,00";
   }

   public double getMaxOnAccountAllowed() {
      return this.getOnAccountLimit() + this.getOnAccountTotal();
   }

   public String getMaxOnAccountAllowedToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getMaxOnAccountAllowed()));
      str = str.replaceAll("\\.", ",");
      return this.getMaxOnAccountAllowed() != 0.0 ? str : "0,00";
   }

   public String getFullNameToReport() {
      String fullName = "";
      if (this.isHomeCustomer()) {
         if ((new Long(1L)).equals(this.getId())) {
            fullName = "-";
         } else {
            fullName = this.getLastName() + ", " + this.getFirstName();
         }
      } else {
         fullName = this.getCompanyName();
      }

      return fullName;
   }

   public String getCuitToPrint() {
      String cuitStr = "";

      try {
         String p1 = this.getCuit().substring(0, 2);
         String p2 = this.getCuit().substring(2, 10);
         String p3 = this.getCuit().substring(10, 11);
         cuitStr = p1 + "-" + p2 + "-" + p3;
      } catch (Exception var5) {
         cuitStr = this.getCuit();
      }

      return cuitStr;
   }

   public String getDniToPrint() {
      String str = this.getDocumentNumber();

      try {
         if (!str.contains(".")) {
            double docNumber = Double.parseDouble(str);
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator(',');
            simbolos.setGroupingSeparator('.');
            DecimalFormat formatter = new DecimalFormat("###,###,###", simbolos);
            str = String.valueOf(formatter.format(docNumber));
            if (docNumber != 0.0) {
               return str;
            }

            return "";
         }
      } catch (Exception var6) {
      }

      return str;
   }

   public String getProvince() {
      return this.province;
   }

   public void setProvince(String province) {
      this.province = province;
   }
}
