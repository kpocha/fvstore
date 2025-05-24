package com.facilvirtual.fvstoresdesk.model;

import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.util.CryptUtils;
import com.facilvirtual.fvstoresdesk.util.DiskUtils;
import java.io.Serializable;
import java.text.DecimalFormat;
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
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.ApplicationContext;

@Entity
@Table(
   name = "fvpos_workstation_config"
)
public class WorkstationConfig implements Serializable {
   private static final long serialVersionUID = 5666054736807210530L;
   @Id
   @Column(
      name = "workstation_config_id"
   )
   @GeneratedValue
   private Long id;
   @Column(
      name = "cash_number"
   )
   private int cashNumber = 1;
   @ManyToOne
   @JoinColumn(
      name = "cash_dept1_id"
   )
   private ProductCategory cashDept1;
   @ManyToOne
   @JoinColumn(
      name = "cash_dept2_id"
   )
   private ProductCategory cashDept2;
   @ManyToOne
   @JoinColumn(
      name = "cash_dept3_id"
   )
   private ProductCategory cashDept3;
   @ManyToOne
   @JoinColumn(
      name = "cash_dept4_id"
   )
   private ProductCategory cashDept4;
   @ManyToOne
   @JoinColumn(
      name = "cash_dept5_id"
   )
   private ProductCategory cashDept5;
   @ManyToOne
   @JoinColumn(
      name = "cash_dept6_id"
   )
   private ProductCategory cashDept6;
   @ManyToOne
   @JoinColumn(
      name = "cash_dept7_id"
   )
   private ProductCategory cashDept7;
   @ManyToOne
   @JoinColumn(
      name = "cash_dept8_id"
   )
   private ProductCategory cashDept8;
   @Column(
      name = "cash_amount"
   )
   private double cashAmount = 0.0;
   @Column(
      name = "is_cash_opened",
      nullable = false
   )
   private boolean cashOpened = false;
   @Column(
      name = "is_active",
      nullable = false
   )
   private boolean active = true;
   @Column(
      name = "is_server",
      nullable = false
   )
   private boolean server = false;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "installation_date"
   )
   private Date installationDate;
   @Column(
      name = "installation_code"
   )
   private String installationCode = "";
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "license_expiration_date"
   )
   private Date licenseExpirationDate;
   @Column(
      name = "license_activation_code"
   )
   private String licenseActivationCode;
   @Column(
      name = "trial_days_qty",
      nullable = false
   )
   private int trialDaysQty = 7;
   @Column(
      name = "trial_max_orders_qty",
      nullable = false
   )
   private int trialMaxOrdersQty = 100;
   @Column(
      name = "trial_max_products_qty",
      nullable = false
   )
   private int trialMaxProductsQty = 100;
   @Column(
      name = "is_module_fiscal_active",
      nullable = false
   )
   private boolean moduleFiscalActive = false;
   @Column(
      name = "fiscal_printer_brand"
   )
   private String fiscalPrinterBrand = "EPSON";
   @Column(
      name = "fiscal_printer_model"
   )
   private String fiscalPrinterModel = "";
   @Column(
      name = "fiscal_printer_port"
   )
   private String fiscalPrinterPort = "COM1";
   @Column(
      name = "fiscal_printer_velocity"
   )
   private int fiscalPrinterVelocity = 9600;
   @Column(
      name = "fiscal_printer_copies"
   )
   private int fiscalPrinterCopies = 1;
   @Column(
      name = "default_printer"
   )
   private int defaultPrinter = 1;
   @Column(
      name = "is_module_scale_active",
      nullable = false
   )
   private boolean moduleScaleActive = false;
   @Column(
      name = "scale_label_type",
      nullable = false
   )
   private String scaleLabelType = "WEIGHT";
   @Column(
      name = "scale_code"
   )
   private String scaleCode = "20";
   @Column(
      name = "scale_product_code_start",
      nullable = false
   )
   private int scaleProductCodeStart = 3;
   @Column(
      name = "scale_product_code_end",
      nullable = false
   )
   private int scaleProductCodeEnd = 7;
   @Column(
      name = "scale_weight_start",
      nullable = false
   )
   private int scaleWeightStart = 8;
   @Column(
      name = "scale_weight_end",
      nullable = false
   )
   private int scaleWeightEnd = 9;
   @Column(
      name = "scale_weight_decimals_start",
      nullable = false
   )
   private int scaleWeightDecimalsStart = 10;
   @Column(
      name = "scale_weight_decimals_end",
      nullable = false
   )
   private int scaleWeightDecimalsEnd = 12;
   @Column(
      name = "scale_price_start",
      nullable = false
   )
   private int scalePriceStart = 8;
   @Column(
      name = "scale_price_end",
      nullable = false
   )
   private int scalePriceEnd = 10;
   @Column(
      name = "scale_price_decimals_start",
      nullable = false
   )
   private int scalePriceDecimalsStart = 11;
   @Column(
      name = "scale_price_decimals_end",
      nullable = false
   )
   private int scalePriceDecimalsEnd = 12;
   @Column(
      name = "scale_checksum_digit",
      nullable = false
   )
   private int scaleChecksumDigit = 13;
   @Column(
      name = "is_open_cash_on_login",
      nullable = false
   )
   private boolean openCashOnLogin = false;
   @Column(
      name = "is_open_confirm_print_order",
      nullable = false
   )
   private boolean openConfirmPrintOrder = true;
   @Column(
      name = "is_open_confirm_factura_electronica",
      nullable = false
   )
   private boolean openConfirmFacturaElectronica = true;
   @Column(
      name = "is_show_product_photos",
      nullable = false
   )
   private boolean showProductPhotos = false;
   @Column(
      name = "is_print_code_in_tickets",
      nullable = false
   )
   private boolean printCodeInTickets = true;
   @Column(
      name = "cost_price_decimals_format",
      nullable = false
   )
   private int costPriceDecimalsFormat = 2;
   @Column(
      name = "selling_price_decimals_format",
      nullable = false
   )
   private int sellingPriceDecimalsFormat = 2;
   @ManyToOne
   @JoinColumn(
      name = "receipt_type_for_orders_id"
   )
   private ReceiptType defaultReceiptTypeForOrders;
   @ManyToOne
   @JoinColumn(
      name = "receipt_type_for_purchases_id"
   )
   private ReceiptType defaultReceiptTypeForPurchases;
   @Column(
      name = "cod_factura_electronica"
   )
   private int codFacturaElectronica = 0;
   @Column(
      name = "is_valid_cod_fact_elect"
   )
   private boolean validCodFactElect = false;

   public WorkstationConfig() {
   }

   public int getCodFacturaElectronica() {
      return this.codFacturaElectronica;
   }

   public void setCodFacturaElectronica(int codFacturaElectronica) {
      this.codFacturaElectronica = codFacturaElectronica;
   }

   public String getCodFacturaElectronicaToDisplay() {
      String code = this.getCodFacturaElectronica() != 0 ? String.valueOf(this.getCodFacturaElectronica()) : "";
      if (!"".equals(code)) {
         while(code.length() < 6) {
            code = "0" + code;
         }
      }

      return code;
   }

   public boolean isValidCodFactElect() {
      return this.validCodFactElect;
   }

   public boolean getValidCodFactElect() {
      return this.validCodFactElect;
   }

   public void setValidCodFactElect(boolean validCodFactElect) {
      this.validCodFactElect = validCodFactElect;
   }

   public ReceiptType getDefaultReceiptTypeForOrders() {
      return this.defaultReceiptTypeForOrders;
   }

   public void setDefaultReceiptTypeForOrders(ReceiptType defaultReceiptTypeForOrders) {
      this.defaultReceiptTypeForOrders = defaultReceiptTypeForOrders;
   }

   public ReceiptType getDefaultReceiptTypeForPurchases() {
      return this.defaultReceiptTypeForPurchases;
   }

   public void setDefaultReceiptTypeForPurchases(ReceiptType defaultReceiptTypeForPurchases) {
      this.defaultReceiptTypeForPurchases = defaultReceiptTypeForPurchases;
   }

   public boolean isCashOpened() {
      return this.cashOpened;
   }

   public void setCashOpened(boolean cashOpened) {
      this.cashOpened = cashOpened;
   }

   public int getTrialMaxProductsQty() {
      return this.trialMaxProductsQty;
   }

   public void setTrialMaxProductsQty(int trialMaxProductsQty) {
      this.trialMaxProductsQty = trialMaxProductsQty;
   }

   public int getTrialMaxOrdersQty() {
      return this.trialMaxOrdersQty;
   }

   public void setTrialMaxOrdersQty(int trialMaxOrdersQty) {
      this.trialMaxOrdersQty = trialMaxOrdersQty;
   }

   public int getTrialDaysQty() {
      return this.trialDaysQty;
   }

   public void setTrialDaysQty(int trialDaysQty) {
      this.trialDaysQty = trialDaysQty;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public String getFiscalPrinterBrand() {
      return this.fiscalPrinterBrand;
   }

   public void setFiscalPrinterBrand(String fiscalPrinterBrand) {
      this.fiscalPrinterBrand = fiscalPrinterBrand;
   }

   public String getFiscalPrinterModel() {
      return this.fiscalPrinterModel;
   }

   public void setFiscalPrinterModel(String fiscalPrinterModel) {
      this.fiscalPrinterModel = fiscalPrinterModel;
   }

   public String getFiscalPrinterPort() {
      return this.fiscalPrinterPort;
   }

   public void setFiscalPrinterPort(String fiscalPrinterPort) {
      this.fiscalPrinterPort = fiscalPrinterPort;
   }

   public int getFiscalPrinterVelocity() {
      return this.fiscalPrinterVelocity;
   }

   public void setFiscalPrinterVelocity(int fiscalPrinterVelocity) {
      this.fiscalPrinterVelocity = fiscalPrinterVelocity;
   }

   public int getFiscalPrinterCopies() {
      return this.fiscalPrinterCopies;
   }

   public void setFiscalPrinterCopies(int fiscalPrinterCopies) {
      this.fiscalPrinterCopies = fiscalPrinterCopies;
   }

   public boolean isModuleFiscalActive() {
      return this.moduleFiscalActive;
   }

   public void setModuleFiscalActive(boolean moduleFiscalActive) {
      this.moduleFiscalActive = moduleFiscalActive;
   }

   public String getInstallationCode() {
      return this.installationCode;
   }

   public void setInstallationCode(String installationCode) {
      this.installationCode = installationCode;
   }

   public Date getLicenseExpirationDate() {
      return this.licenseExpirationDate;
   }

   public void setLicenseExpirationDate(Date licenseExpirationDate) {
      this.licenseExpirationDate = licenseExpirationDate;
   }

   public String getLicenseActivationCode() {
      return this.licenseActivationCode;
   }

   public void setLicenseActivationCode(String licenseActivationCode) {
      this.licenseActivationCode = licenseActivationCode;
   }

   public int getCashNumber() {
      return this.cashNumber;
   }

   public void setCashNumber(int cashNumber) {
      this.cashNumber = cashNumber;
   }

   public boolean isServer() {
      return this.server;
   }

   public void setServer(boolean server) {
      this.server = server;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public ProductCategory getCashDept1() {
      return this.cashDept1;
   }

   public void setCashDept1(ProductCategory cashDept1) {
      this.cashDept1 = cashDept1;
   }

   public ProductCategory getCashDept2() {
      return this.cashDept2;
   }

   public void setCashDept2(ProductCategory cashDept2) {
      this.cashDept2 = cashDept2;
   }

   public ProductCategory getCashDept3() {
      return this.cashDept3;
   }

   public void setCashDept3(ProductCategory cashDept3) {
      this.cashDept3 = cashDept3;
   }

   public ProductCategory getCashDept4() {
      return this.cashDept4;
   }

   public void setCashDept4(ProductCategory cashDept4) {
      this.cashDept4 = cashDept4;
   }

   public ProductCategory getCashDept5() {
      return this.cashDept5;
   }

   public void setCashDept5(ProductCategory cashDept5) {
      this.cashDept5 = cashDept5;
   }

   public ProductCategory getCashDept6() {
      return this.cashDept6;
   }

   public void setCashDept6(ProductCategory cashDept6) {
      this.cashDept6 = cashDept6;
   }

   public boolean isModuleScaleActive() {
      return this.moduleScaleActive;
   }

   public void setModuleScaleActive(boolean moduleScaleActive) {
      this.moduleScaleActive = moduleScaleActive;
   }

   public String getScaleLabelType() {
      return this.scaleLabelType;
   }

   public void setScaleLabelType(String scaleLabelType) {
      this.scaleLabelType = scaleLabelType;
   }

   public String getScaleCode() {
      return this.scaleCode;
   }

   public void setScaleCode(String scaleCode) {
      this.scaleCode = scaleCode;
   }

   public int getScaleProductCodeStart() {
      return this.scaleProductCodeStart;
   }

   public void setScaleProductCodeStart(int scaleProductCodeStart) {
      this.scaleProductCodeStart = scaleProductCodeStart;
   }

   public int getScaleProductCodeEnd() {
      return this.scaleProductCodeEnd;
   }

   public void setScaleProductCodeEnd(int scaleProductCodeEnd) {
      this.scaleProductCodeEnd = scaleProductCodeEnd;
   }

   public int getScaleWeightStart() {
      return this.scaleWeightStart;
   }

   public void setScaleWeightStart(int scaleWeightStart) {
      this.scaleWeightStart = scaleWeightStart;
   }

   public int getScaleWeightEnd() {
      return this.scaleWeightEnd;
   }

   public void setScaleWeightEnd(int scaleWeightEnd) {
      this.scaleWeightEnd = scaleWeightEnd;
   }

   public int getScaleWeightDecimalsStart() {
      return this.scaleWeightDecimalsStart;
   }

   public void setScaleWeightDecimalsStart(int scaleWeightDecimalsStart) {
      this.scaleWeightDecimalsStart = scaleWeightDecimalsStart;
   }

   public int getScaleWeightDecimalsEnd() {
      return this.scaleWeightDecimalsEnd;
   }

   public void setScaleWeightDecimalsEnd(int scaleWeightDecimalsEnd) {
      this.scaleWeightDecimalsEnd = scaleWeightDecimalsEnd;
   }

   public int getScalePriceStart() {
      return this.scalePriceStart;
   }

   public void setScalePriceStart(int scalePriceStart) {
      this.scalePriceStart = scalePriceStart;
   }

   public int getScalePriceEnd() {
      return this.scalePriceEnd;
   }

   public void setScalePriceEnd(int scalePriceEnd) {
      this.scalePriceEnd = scalePriceEnd;
   }

   public int getScalePriceDecimalsStart() {
      return this.scalePriceDecimalsStart;
   }

   public void setScalePriceDecimalsStart(int scalePriceDecimalsStart) {
      this.scalePriceDecimalsStart = scalePriceDecimalsStart;
   }

   public int getScalePriceDecimalsEnd() {
      return this.scalePriceDecimalsEnd;
   }

   public void setScalePriceDecimalsEnd(int scalePriceDecimalsEnd) {
      this.scalePriceDecimalsEnd = scalePriceDecimalsEnd;
   }

   public int getScaleChecksumDigit() {
      return this.scaleChecksumDigit;
   }

   public void setScaleChecksumDigit(int scaleChecksumDigit) {
      this.scaleChecksumDigit = scaleChecksumDigit;
   }

   public boolean isScaleLabelWeightType() {
      return this.getScaleLabelType().equalsIgnoreCase("WEIGHT");
   }

   public boolean isOpenConfirmPrintOrder() {
      return this.openConfirmPrintOrder;
   }

   public void setOpenConfirmPrintOrder(boolean openConfirmPrintOrder) {
      this.openConfirmPrintOrder = openConfirmPrintOrder;
   }

   public boolean isOpenConfirmFacturaElectronica() {
      return this.openConfirmFacturaElectronica;
   }

   public void setOpenConfirmFacturaElectronica(boolean openConfirmFacturaElectronica) {
      this.openConfirmFacturaElectronica = openConfirmFacturaElectronica;
   }

   public int getCostPriceDecimalsFormat() {
      return this.costPriceDecimalsFormat;
   }

   public void setCostPriceDecimalsFormat(int costPriceDecimalsFormat) {
      this.costPriceDecimalsFormat = costPriceDecimalsFormat;
   }

   public int getSellingPriceDecimalsFormat() {
      return this.sellingPriceDecimalsFormat;
   }

   public void setSellingPriceDecimalsFormat(int sellingPriceDecimalsFormat) {
      this.sellingPriceDecimalsFormat = sellingPriceDecimalsFormat;
   }

   public Date getInstallationDate() {
      return this.installationDate;
   }

   public void setInstallationDate(Date installationDate) {
      this.installationDate = installationDate;
   }

   public boolean isOpenCashOnLogin() {
      return this.openCashOnLogin;
   }

   public void setOpenCashOnLogin(boolean openCashOnLogin) {
      this.openCashOnLogin = openCashOnLogin;
   }

   public boolean isShowProductPhotos() {
      return this.showProductPhotos;
   }

   public void setShowProductPhotos(boolean showProductPhotos) {
      this.showProductPhotos = showProductPhotos;
   }

   public boolean isTrialMode() {
      return this.getLicenseActivationCode() == null || this.getLicenseActivationCode().equals("");
   }

   public int getTrialExpirationDaysQty(Date today) {
      Date trialExpirationDate = DateUtils.addDays(this.getInstallationDate(), this.getTrialDaysQty());
      if (!today.before(this.getInstallationDate()) && !today.after(trialExpirationDate)) {
         int x;
         for(x = 0; today.before(trialExpirationDate); today = DateUtils.addDays(today, 1)) {
            ++x;
         }

         return x;
      } else {
         return 0;
      }
   }

   public boolean isValidActivationCode(String installationCode, String activationCode) {
      DecimalFormat df = new DecimalFormat("#");
      boolean valid = true;

      try {
         activationCode = activationCode.replaceAll("-", "");
         if (activationCode.length() % 2 != 0) {
            valid = false;
         } else {
            String cleanActivationCode = CryptUtils.deobfuscateKey(activationCode);
            int middle = cleanActivationCode.length() / 2;
            String part1 = cleanActivationCode.substring(0, middle);
            String part2 = cleanActivationCode.substring(middle, cleanActivationCode.length());
            String planIdStr = String.valueOf(this.getAppConfig().getPlanId());
            String dpart1;
            if ("1".equals(planIdStr)) {
               dpart1 = df.format(CryptUtils.decrypt1(Double.parseDouble(part1)));
            } else if ("2".equals(planIdStr)) {
               dpart1 = df.format(CryptUtils.decrypt2(Double.parseDouble(part1)));
            } else {
               dpart1 = df.format(CryptUtils.decrypt3(Double.parseDouble(part1)));
            }

            while(installationCode.startsWith("0")) {
               installationCode = installationCode.substring(1, installationCode.length());
            }

            if (installationCode.length() == 0) {
               installationCode = "0";
            }

            String installationCodeNumber;
            try {
               installationCodeNumber = df.format(Double.parseDouble(installationCode));
            } catch (Exception var17) {
               installationCodeNumber = "0";
            }

            if (!installationCodeNumber.equals("0") && !dpart1.equals(installationCode)) {
               valid = false;
            } else {
               double dpart2;
               if ("1".equals(planIdStr)) {
                  dpart2 = CryptUtils.decrypt1(Double.parseDouble(part2));
               } else if ("2".equals(planIdStr)) {
                  dpart2 = CryptUtils.decrypt2(Double.parseDouble(part2));
               } else {
                  dpart2 = CryptUtils.decrypt3(Double.parseDouble(part2));
               }

               if (dpart2 != -1.0) {
                  try {
                     String dpart2Str = df.format(dpart2);
                     Date expDate = new Date();
                     expDate.setTime(new Long(dpart2Str) * 1000L);
                  } catch (Exception var16) {
                     valid = false;
                  }
               } else {
                  valid = false;
               }
            }
         }
      } catch (Exception var18) {
         valid = false;
      }

      return valid;
   }

   public String getCurrentInstallationCode() {
      String serialNumber = "";

      try {
         int maxLength = 10;
         serialNumber = DiskUtils.getSerialNumber("C");
         if (serialNumber.length() > maxLength) {
            serialNumber = serialNumber.substring(0, maxLength);
         } else if (serialNumber.length() < maxLength) {
            while(serialNumber.length() < maxLength) {
               serialNumber = "0" + serialNumber;
            }
         }

         if (serialNumber.length() == 0) {
            serialNumber = "0";
         }
      } catch (Exception var3) {
         serialNumber = "0";
      }

      return serialNumber.toUpperCase();
   }

   public boolean isExpiredLicense() {
      boolean expired = false;
      Date today = new Date();
      Date expirationDate = this.getLicenseExpirationDate();
      if (today.before(this.getInstallationDate()) || today.after(expirationDate)) {
         expired = true;
      }

      return expired;
   }

   public Date getExpirationDateFromActivationCode(String activationCode) {
      Date expirationDate = null;
      DecimalFormat df = new DecimalFormat("#");
      activationCode = activationCode.replaceAll("-", "");
      if (activationCode.length() % 2 != 0) {
         expirationDate = null;
      } else {
         String cleanActivationCode = CryptUtils.deobfuscateKey(activationCode);
         int middle = cleanActivationCode.length() / 2;
         String part2 = cleanActivationCode.substring(middle, cleanActivationCode.length());
         String planIdStr = String.valueOf(this.getAppConfig().getPlanId());
         double dpart2;
         if ("1".equals(planIdStr)) {
            dpart2 = CryptUtils.decrypt1(Double.parseDouble(part2));
         } else if ("2".equals(planIdStr)) {
            dpart2 = CryptUtils.decrypt2(Double.parseDouble(part2));
         } else {
            dpart2 = CryptUtils.decrypt3(Double.parseDouble(part2));
         }

         if (dpart2 != -1.0) {
            try {
               String dpart2Str = df.format(dpart2);
               Date expDate = new Date();
               expDate.setTime(new Long(dpart2Str) * 1000L);
               expirationDate = expDate;
            } catch (Exception var12) {
               expirationDate = null;
            }
         }
      }

      return expirationDate;
   }

   public String getAppLicenseExpirationDateToDisplay() {
      String dateStr = "";
      if (this.getLicenseExpirationDate() != null) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
         dateStr = formatter.format(this.getLicenseExpirationDate());
      }

      return dateStr;
   }

   private AppConfig getAppConfig() {
      return this.getAppConfigService().getAppConfig();
   }

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   public double getCashAmount() {
      return this.cashAmount;
   }

   public void setCashAmount(double cashAmount) {
      this.cashAmount = cashAmount;
   }

   public int getDefaultPrinter() {
      return this.defaultPrinter;
   }

   public void setDefaultPrinter(int defaultPrinter) {
      this.defaultPrinter = defaultPrinter;
   }

   public String getCashNumberToDisplay() {
      String str = "";

      try {
         str = String.valueOf(this.getCashNumber());
         if (this.getCashNumber() < 10) {
            str = "0" + str;
         }
      } catch (Exception var3) {
         str = "-1";
      }

      return str;
   }

   public boolean isInTrialPeriod() {
      return this.getTrialExpirationDaysQty(new Date()) > 0;
   }

   public ProductCategory getCashDept7() {
      return this.cashDept7;
   }

   public void setCashDept7(ProductCategory cashDept7) {
      this.cashDept7 = cashDept7;
   }

   public ProductCategory getCashDept8() {
      return this.cashDept8;
   }

   public void setCashDept8(ProductCategory cashDept8) {
      this.cashDept8 = cashDept8;
   }

   public boolean isPrintCodeInTickets() {
      return this.printCodeInTickets;
   }

   public void setPrintCodeInTickets(boolean printCodeInTickets) {
      this.printCodeInTickets = printCodeInTickets;
   }

   public void initValidCodFactElect(String serialNumber) {
      try {
         double serialNumber2 = Double.valueOf(serialNumber);
         String code1 = CryptUtils.generateCodFacturaElectronica(serialNumber2);
         String code2 = String.valueOf(this.getCodFacturaElectronicaToDisplay());
         this.setValidCodFactElect(code1.equals(code2));
      } catch (Exception var6) {
         this.setValidCodFactElect(false);
      }

   }
}
