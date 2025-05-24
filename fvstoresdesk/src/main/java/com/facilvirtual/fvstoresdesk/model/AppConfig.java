package com.facilvirtual.fvstoresdesk.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(
   name = "fvpos_app_config"
)
public class AppConfig implements Serializable {
   private static final long serialVersionUID = -6827166706064200408L;
   @Id
   @Column(
      name = "app_config_id"
   )
   @GeneratedValue
   private Long id;
   @Column(
      name = "company_name"
   )
   private String companyName = "";
   @Column(
      name = "company_business_name"
   )
   private String companyBusinessName = "";
   @Column(
      name = "company_cuit"
   )
   private String companyCuit = "";
   @Column(
      name = "company_address_street"
   )
   private String companyAddressStreet = "";
   @Column(
      name = "company_address_number"
   )
   private String companyAddressNumber = "";
   @Column(
      name = "company_address_other"
   )
   private String companyAddressOther = "";
   @Column(
      name = "company_postal_code"
   )
   private String companyPostalCode = "";
   @Column(
      name = "company_city"
   )
   private String companyCity = "";
   @Column(
      name = "company_province"
   )
   private String companyProvince = "";
   @Column(
      name = "company_phone"
   )
   private String companyPhone = "";
   @Column(
      name = "company_email"
   )
   private String companyEmail = "";
   @Column(
      name = "company_gross_income_number"
   )
   private String companyGrossIncomeNumber = "";
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "company_start_activities_date"
   )
   private Date companyStartActivitiesDate;
   @Column(
      name = "company_pos_number"
   )
   private int companyPosNumber = 1;
   @Column(
      name = "company_cash_qty"
   )
   private int companyCashQty = 1;
   @ManyToOne
   @JoinColumn(
      name = "company_vat_condition_id",
      nullable = false
   )
   private VatCondition companyVatCondition;
   @Column(
      name = "company_website"
   )
   private String companyWebsite = "";
   @Column(
      name = "company_owner_first_name"
   )
   private String companyOwnerFirstName = "";
   @Column(
      name = "company_owner_last_name"
   )
   private String companyOwnerLastName = "";
   @Column(
      name = "company_owner_email"
   )
   private String companyOwnerEmail = "";
   @Column(
      name = "company_owner_phone"
   )
   private String companyOwnerPhone = "";
   @Column(
      name = "company_owner_mobile"
   )
   private String companyOwnerMobile = "";
   @Column(
      name = "company_logo"
   )
   private String companyLogo = "";
   @Column(
      name = "store_web_alias"
   )
   private String storeWebAlias = "";
   @Column(
      name = "store_web_username"
   )
   private String storeWebUsername = "";
   @Column(
      name = "store_web_password"
   )
   private String storeWebPassword = "";
   @Column(
      name = "app_name"
   )
   private String appName = "FácilVirtual";
   @Column(
      name = "app_model"
   )
   private String appModel = "Supermercados";
   @Column(
      name = "app_version"
   )
   private String appVersion = "1.0";
   @Column(
      name = "manager_password"
   )
   private String managerPassword = "_root123";
   public static String DBWEB_CONN_DRIVER = "org.postgresql.Driver";
   public static String DBWEB_CONN_URL = "";
   public static String DBWEB_USER = "";
   public static String DBWEB_PASSWORD = "";
   @Column(
      name = "is_dbweb_ssl",
      nullable = false
   )
   private boolean dbwebSsl = false;
   @Column(
      name = "is_module_shifts_active",
      nullable = false
   )
   private boolean moduleShiftsActive;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "start_shift1"
   )
   private Date startShift1;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "start_shift2"
   )
   private Date startShift2;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "start_shift3"
   )
   private Date startShift3;
   @Column(
      name = "cash_label"
   )
   private String cashLabel = "Caja";
   @Column(
      name = "cash_number_label"
   )
   private String cashNumberLabel = "Nro. Caja";
   @Column(
      name = "cashier_label"
   )
   private String cashierLabel = "Cajero";
   @Column(
      name = "pto_vta"
   )
   private int ptoVta = 1;
   @Column(
      name = "budget_start_number"
   )
   private int budgetStartNumber = 1;
   @Column(
      name = "sale_start_number"
   )
   private int saleStartNumber = 1;
   @Column(
      name = "is_afip_enabled_factura_electronica"
   )
   private boolean afipEnabledFacturaElectronica = false;
   @Column(
      name = "afip_pto_vta"
   )
   private int afipPtoVta = 0;
   @Column(
      name = "is_afip_enabled_factura_a"
   )
   private boolean afipEnabledFacturaA = false;
   @Column(
      name = "text_footer_ticket"
   )
   private String textFooterTicket = "Muchas gracias por su compra";
   @Column(
      name = "text_footer_factura_1"
   )
   private String textFooterFactura1 = "";
   @Column(
      name = "text_footer_factura_2"
   )
   private String textFooterFactura2 = "";

   public AppConfig() {
   }

   public String getTextFooterTicket() {
      return this.textFooterTicket;
   }

   public void setTextFooterTicket(String textFooterTicket) {
      this.textFooterTicket = textFooterTicket;
   }

   public String getTextFooterFactura1() {
      return this.textFooterFactura1;
   }

   public void setTextFooterFactura1(String textFooterFactura1) {
      this.textFooterFactura1 = textFooterFactura1;
   }

   public String getTextFooterFactura2() {
      return this.textFooterFactura2;
   }

   public void setTextFooterFactura2(String textFooterFactura2) {
      this.textFooterFactura2 = textFooterFactura2;
   }

   public boolean isAfipEnabledFacturaElectronica() {
      return this.afipEnabledFacturaElectronica;
   }

   public void setAfipEnabledFacturaElectronica(boolean afipEnabledFacturaElectronica) {
      this.afipEnabledFacturaElectronica = afipEnabledFacturaElectronica;
   }

   public boolean isAfipEnabledFacturaA() {
      return this.afipEnabledFacturaA;
   }

   public boolean getAfipEnabledFacturaA() {
      return this.afipEnabledFacturaA;
   }

   public void setAfipEnabledFacturaA(boolean afipEnabledFacturaA) {
      this.afipEnabledFacturaA = afipEnabledFacturaA;
   }

   public int getAfipPtoVta() {
      return this.afipPtoVta;
   }

   public void setAfipPtoVta(int afipPtoVta) {
      this.afipPtoVta = afipPtoVta;
   }

   public int getBudgetStartNumber() {
      return this.budgetStartNumber;
   }

   public void setBudgetStartNumber(int budgetStartNumber) {
      this.budgetStartNumber = budgetStartNumber;
   }

   public int getSaleStartNumber() {
      return this.saleStartNumber;
   }

   public void setSaleStartNumber(int saleStartNumber) {
      this.saleStartNumber = saleStartNumber;
   }

   public String getCashLabel() {
      return this.cashLabel;
   }

   public void setCashLabel(String cashLabel) {
      this.cashLabel = cashLabel;
   }

   public String getCashNumberLabel() {
      return this.cashNumberLabel;
   }

   public void setCashNumberLabel(String cashNumberLabel) {
      this.cashNumberLabel = cashNumberLabel;
   }

   public String getCashierLabel() {
      return this.cashierLabel;
   }

   public void setCashierLabel(String cashierLabel) {
      this.cashierLabel = cashierLabel;
   }

   public String getAppModel() {
      return this.appModel;
   }

   public void setAppModel(String appModel) {
      this.appModel = appModel;
   }

   public String getCompanyName() {
      return this.companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public String getCompanyBusinessName() {
      return this.companyBusinessName;
   }

   public void setCompanyBusinessName(String companyBusinessName) {
      this.companyBusinessName = companyBusinessName;
   }

   public String getCompanyCuit() {
      return this.companyCuit;
   }

   public void setCompanyCuit(String companyCuit) {
      this.companyCuit = companyCuit;
   }

   public String getCompanyAddressStreet() {
      return this.companyAddressStreet;
   }

   public void setCompanyAddressStreet(String companyAddressStreet) {
      this.companyAddressStreet = companyAddressStreet;
   }

   public String getCompanyAddressNumber() {
      return this.companyAddressNumber;
   }

   public void setCompanyAddressNumber(String companyAddressNumber) {
      this.companyAddressNumber = companyAddressNumber;
   }

   public String getCompanyAddressOther() {
      return this.companyAddressOther;
   }

   public void setCompanyAddressOther(String companyAddressOther) {
      this.companyAddressOther = companyAddressOther;
   }

   public String getCompanyPostalCode() {
      return this.companyPostalCode;
   }

   public void setCompanyPostalCode(String companyPostalCode) {
      this.companyPostalCode = companyPostalCode;
   }

   public String getCompanyCity() {
      return this.companyCity;
   }

   public void setCompanyCity(String companyCity) {
      this.companyCity = companyCity;
   }

   public String getCompanyProvince() {
      return this.companyProvince;
   }

   public void setCompanyProvince(String companyProvince) {
      this.companyProvince = companyProvince;
   }

   public String getCompanyPhone() {
      return this.companyPhone;
   }

   public void setCompanyPhone(String companyPhone) {
      this.companyPhone = companyPhone;
   }

   public String getCompanyEmail() {
      return this.companyEmail;
   }

   public void setCompanyEmail(String companyEmail) {
      this.companyEmail = companyEmail;
   }

   public String getCompanyWebsite() {
      return this.companyWebsite;
   }

   public void setCompanyWebsite(String companyWebsite) {
      this.companyWebsite = companyWebsite;
   }

   public String getCompanyGrossIncomeNumber() {
      return this.companyGrossIncomeNumber;
   }

   public void setCompanyGrossIncomeNumber(String companyGrossIncomeNumber) {
      this.companyGrossIncomeNumber = companyGrossIncomeNumber;
   }

   public Date getCompanyStartActivitiesDate() {
      return this.companyStartActivitiesDate;
   }

   public void setCompanyStartActivitiesDate(Date companyStartActivitiesDate) {
      this.companyStartActivitiesDate = companyStartActivitiesDate;
   }

   public VatCondition getCompanyVatCondition() {
      return this.companyVatCondition;
   }

   public void setCompanyVatCondition(VatCondition companyVatCondition) {
      this.companyVatCondition = companyVatCondition;
   }

   public String getCompanyOwnerFirstName() {
      return this.companyOwnerFirstName;
   }

   public void setCompanyOwnerFirstName(String companyOwnerFirstName) {
      this.companyOwnerFirstName = companyOwnerFirstName;
   }

   public String getCompanyOwnerLastName() {
      return this.companyOwnerLastName;
   }

   public void setCompanyOwnerLastName(String companyOwnerLastName) {
      this.companyOwnerLastName = companyOwnerLastName;
   }

   public String getCompanyOwnerEmail() {
      return this.companyOwnerEmail;
   }

   public void setCompanyOwnerEmail(String companyOwnerEmail) {
      this.companyOwnerEmail = companyOwnerEmail;
   }

   public String getCompanyOwnerPhone() {
      return this.companyOwnerPhone;
   }

   public void setCompanyOwnerPhone(String companyOwnerPhone) {
      this.companyOwnerPhone = companyOwnerPhone;
   }

   public String getCompanyOwnerMobile() {
      return this.companyOwnerMobile;
   }

   public void setCompanyOwnerMobile(String companyOwnerMobile) {
      this.companyOwnerMobile = companyOwnerMobile;
   }

   public String getStoreWebAlias() {
      return this.storeWebAlias;
   }

   public void setStoreWebAlias(String storeWebAlias) {
      this.storeWebAlias = storeWebAlias;
   }

   public String getStoreWebUsername() {
      return this.storeWebUsername;
   }

   public void setStoreWebUsername(String storeWebUsername) {
      this.storeWebUsername = storeWebUsername;
   }

   public String getStoreWebPassword() {
      return this.storeWebPassword;
   }

   public void setStoreWebPassword(String storeWebPassword) {
      this.storeWebPassword = storeWebPassword;
   }

   public String getAppName() {
      return this.appName;
   }

   public void setAppName(String appName) {
      this.appName = appName;
   }

   public int getCompanyCashQty() {
      return this.companyCashQty;
   }

   public void setCompanyCashQty(int companyCashQty) {
      this.companyCashQty = companyCashQty;
   }

   public String getAppVersion() {
      return this.appVersion;
   }

   public void setAppVersion(String appVersion) {
      this.appVersion = appVersion;
   }

   public String getManagerPassword() {
      return this.managerPassword;
   }

   public void setManagerPassword(String managerPassword) {
      this.managerPassword = managerPassword;
   }

   public boolean isDbwebSsl() {
      return this.dbwebSsl;
   }

   public void setDbwebSsl(boolean dbwebSsl) {
      this.dbwebSsl = dbwebSsl;
   }

   public int getCompanyPosNumber() {
      return this.companyPosNumber;
   }

   public void setCompanyPosNumber(int companyPosNumber) {
      this.companyPosNumber = companyPosNumber;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public boolean isModuleShiftsActive() {
      return this.moduleShiftsActive;
   }

   public void setModuleShiftsActive(boolean moduleShiftsActive) {
      this.moduleShiftsActive = moduleShiftsActive;
   }

   public Date getStartShift1() {
      return this.startShift1;
   }

   public void setStartShift1(Date startShift1) {
      this.startShift1 = startShift1;
   }

   public Date getStartShift2() {
      return this.startShift2;
   }

   public void setStartShift2(Date startShift2) {
      this.startShift2 = startShift2;
   }

   public Date getStartShift3() {
      return this.startShift3;
   }

   public void setStartShift3(Date startShift3) {
      this.startShift3 = startShift3;
   }

   public int getPlanId() {
      int planId = -1;
      if ("Punto de Venta".equalsIgnoreCase(this.getAppModel())) {
         planId = 1;
      } else if ("Kioscos".equalsIgnoreCase(this.getAppModel())) {
         planId = 2;
      } else if ("Supermercados".equalsIgnoreCase(this.getAppModel())) {
         planId = 3;
      }

      return planId;
   }

   public boolean isModuleScaleAvailable() {
      return true;
   }

   public boolean showPricePerUnitForLabels() {
      return true;
   }

   public boolean isModuleFiscalAvailable() {
      return false;
   }

   public boolean isPlanPuntoDeVenta() {
      return this.getAppModel().equalsIgnoreCase("Punto de Venta");
   }

   public boolean isPlanKioscos() {
      return this.getAppModel().equalsIgnoreCase("Kioscos");
   }

   public boolean isPlanSupermercados() {
      return this.getAppModel().equalsIgnoreCase("Supermercados");
   }

   public String getCompanyAddressLine1ToPrint(int i) {
      String addressStr = "";
      addressStr = addressStr + (this.getCompanyAddressStreet() != null && !"".equals(this.getCompanyAddressStreet()) ? this.getCompanyAddressStreet() : "");
      addressStr = addressStr + (this.getCompanyAddressNumber() != null && !"".equals(this.getCompanyAddressNumber()) ? " " + this.getCompanyAddressNumber() : "");
      addressStr = addressStr + (this.getCompanyAddressOther() != null && !"".equals(this.getCompanyAddressOther()) ? " " + this.getCompanyAddressOther() : "");
      if (addressStr.length() > i) {
         addressStr = addressStr.substring(0, i);
      }

      return addressStr;
   }

   public String getCompanyAddressLine2ToPrint(int i) {
      String addressStr = "";
      addressStr = addressStr + (this.getCompanyPostalCode() != null && !"".equals(this.getCompanyPostalCode()) ? "(" + this.getCompanyPostalCode() + ") " : "");
      addressStr = addressStr + (this.getCompanyCity() != null && !"".equals(this.getCompanyCity()) ? this.getCompanyCity() : "");
      if (!"".equals(this.getCompanyCity()) && !"".equals(this.getCompanyProvince())) {
         addressStr = addressStr + " - ";
      }

      addressStr = addressStr + (!"".equals(this.getCompanyProvince()) ? this.getCompanyProvince() : "");
      if (addressStr.length() > i) {
         addressStr = addressStr.substring(0, i);
      }

      return addressStr;
   }

   public String getCompanyPhoneForTicket() {
      String phoneStr = "";
      phoneStr = phoneStr + (this.getCompanyPhone() != null && !"".equals(this.getCompanyPhone()) ? this.getCompanyPhone() : "");
      if (phoneStr.length() > 40) {
         phoneStr = phoneStr.substring(0, 40);
      }

      return phoneStr;
   }

   public String getAfipPtoVtaForBarCode() {
      String ptoVta = "";

      for(ptoVta = ptoVta + this.getAfipPtoVta(); ptoVta.length() < 4; ptoVta = "0" + ptoVta) {
      }

      return ptoVta;
   }

   public String getCompanyNameToPrint(int i) {
      String str = this.getCompanyName();
      if (str != null && str.length() > i) {
         str = str.substring(0, i);
      }

      return str;
   }

   public String getCompanyBusinessNameToPrint(int i) {
      String str = this.getCompanyBusinessName();
      if (str != null && str.length() > i) {
         str = str.substring(0, i);
      }

      return str;
   }

   public String getCompanyStartActivitiesDateToPrint() {
      String dateStr = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
         dateStr = formatter.format(this.getCompanyStartActivitiesDate());
      } catch (Exception var3) {
      }

      return dateStr;
   }

   public String getCompanyCuitToPrint() {
      String cuitStr = "";

      try {
         String p1 = this.getCompanyCuit().substring(0, 2);
         String p2 = this.getCompanyCuit().substring(2, 10);
         String p3 = this.getCompanyCuit().substring(10, 11);
         cuitStr = p1 + "-" + p2 + "-" + p3;
      } catch (Exception var5) {
         cuitStr = this.getCompanyCuit();
      }

      return cuitStr;
   }

   public String getCompanyGrossIncomeNumberToPrint() {
      String iibbStr = this.getCompanyGrossIncomeNumber();

      try {
         if (this.getCompanyGrossIncomeNumber().length() == 11 && !this.getCompanyGrossIncomeNumber().contains("-") && !this.getCompanyGrossIncomeNumber().contains(".")) {
            String p1 = this.getCompanyGrossIncomeNumber().substring(0, 2);
            String p2 = this.getCompanyGrossIncomeNumber().substring(2, 10);
            String p3 = this.getCompanyGrossIncomeNumber().substring(10, 11);
            iibbStr = p1 + "-" + p2 + "-" + p3;
         }
      } catch (Exception var5) {
      }

      return iibbStr;
   }

   public boolean isCompanyLogoAvailable() {
      return this.companyLogo != null && !this.companyLogo.equals("");
   }

   public String getCompanyLogo() {
      return this.companyLogo;
   }

   public void setCompanyLogo(String companyLogo) {
      this.companyLogo = companyLogo;
   }

   public int getPtoVta() {
      return this.ptoVta;
   }

   public void setPtoVta(int ptoVta) {
      this.ptoVta = ptoVta;
   }

   public boolean isResponsableInscripto() {
      boolean value = false;

      try {
         value = this.getCompanyVatCondition().getName().equalsIgnoreCase("Responsable Inscripto");
      } catch (Exception var3) {
      }

      return value;
   }

   public boolean isMonotributo() {
      boolean value = false;

      try {
         value = this.getCompanyVatCondition().getName().equalsIgnoreCase("Monotributo");
      } catch (Exception var3) {
      }

      return value;
   }
}
