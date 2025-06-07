package com.facilvirtual.fvstoresdesk.model;

import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.util.AfipUtils;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
import org.springframework.context.ApplicationContext;

@Entity
@Table(
   name = "fvpos_nota_de_credito"
)
public class NotaDeCredito implements Serializable {
   private static final long serialVersionUID = -7705521030116218534L;
   @Id
   @Column(
      name = "nota_id"
   )
   @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
   private Long id;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "creation_date",
      nullable = false
   )
   private Date creationDate;
   @ManyToOne
   @JoinColumn(
      name = "cbte_tipo_id"
   )
   private ReceiptType cbteTipo;
   @Temporal(TemporalType.TIMESTAMP)
   @Column(
      name = "cbte_fch",
      nullable = false
   )
   private Date cbteFch;
   @Column(
      name = "cbte_nro"
   )
   private Long cbteNro = -1L;
   @ManyToOne
   @JoinColumn(
      name = "cashier_id",
      nullable = false
   )
   private Employee cashier;
   @Column(
      name = "cash_number",
      nullable = false
   )
   private int cashNumber = 0;
   @Column(
      name = "status",
      nullable = false
   )
   private String status = "COMPLETED";
   @ManyToOne
   @JoinColumn(
      name = "cbte_asoc_tipo_id"
   )
   private ReceiptType cbteAsocTipo;
   @Column(
      name = "cbte_asoc_pto_vta"
   )
   private int cbteAsocPtoVta = 0;
   @Column(
      name = "cbte_asoc_nro"
   )
   private Long cbteAsocNro = -1L;
   @ManyToOne
   @JoinColumn(
      name = "customer_id"
   )
   private Customer customer;
   @ManyToOne
   @JoinColumn(
      name = "order_id"
   )
   private Order order;
   @Column(
      name = "product_code"
   )
   private String productCode = "";
   @Column(
      name = "product_description"
   )
   private String productDescription = "";
   @Column(
      name = "product_qty",
      nullable = false
   )
   private double productQty = 0.0;
   @Column(
      name = "product_price",
      nullable = false
   )
   private double productPrice = 0.0;
   @Column(
      name = "product_vat_value",
      nullable = false
   )
   private double productVatValue = 0.0;
   @Column(
      name = "afip_pto_vta"
   )
   private int afipPtoVta = 0;
   @Column(
      name = "afip_cbte_tipo"
   )
   private int afipCbteTipo = 0;
   @Column(
      name = "afip_concepto"
   )
   private int afipConcepto = 0;
   @Column(
      name = "afip_doc_tipo"
   )
   private int afipDocTipo = 0;
   @Column(
      name = "afip_doc_nro"
   )
   private Long afipDocNro;
   @Column(
      name = "afip_cbte_desde"
   )
   private Long afipCbteDesde;
   @Column(
      name = "afip_cbte_hasta"
   )
   private Long afipCbteHasta;
   @Column(
      name = "afip_cbte_fch"
   )
   private String afipCbteFch = "";
   @Column(
      name = "afip_fch_serv_desde"
   )
   private String afipFchServDesde;
   @Column(
      name = "afip_fch_serv_hasta"
   )
   private String afipFchServHasta;
   @Column(
      name = "afip_fch_vto_pago"
   )
   private String afipFchVtoPago;
   @Column(
      name = "afip_cae"
   )
   private String afipCae;
   @Column(
      name = "afip_cae_fch_vto"
   )
   private String afipCaeFchVto;
   @Column(
      name = "afip_bar_code"
   )
   private String afipBarCode = "";
   @Column(
      name = "afip_cbte_asoc_pto_vta"
   )
   private int afipCbteAsocPtoVta = 0;
   @Column(
      name = "afip_cbte_asoc_tipo"
   )
   private int afipCbteAsocTipo = 0;
   @Column(
      name = "afip_cbte_asoc_nro"
   )
   private Long afipCbteAsocNro = 0L;

   public NotaDeCredito() {
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

   public Date getCbteFch() {
      return this.cbteFch;
   }

   public void setCbteFch(Date cbteFch) {
      this.cbteFch = cbteFch;
   }

   public Order getOrder() {
      return this.order;
   }

   public void setOrder(Order order) {
      this.order = order;
   }

   public String getProductCode() {
      return this.productCode;
   }

   public void setProductCode(String productCode) {
      this.productCode = productCode;
   }

   public String getProductDescription() {
      return this.productDescription;
   }

   public void setProductDescription(String productDescription) {
      this.productDescription = productDescription;
   }

   public double getProductQty() {
      return this.productQty;
   }

   public void setProductQty(double productQty) {
      this.productQty = productQty;
   }

   public double getProductPrice() {
      return this.productPrice;
   }

   public void setProductPrice(double productPrice) {
      this.productPrice = productPrice;
   }

   public double getProductVatValue() {
      return this.productVatValue;
   }

   public void setProductVatValue(double productVatValue) {
      this.productVatValue = productVatValue;
   }

   public int getAfipCbteTipo() {
      return this.afipCbteTipo;
   }

   public void setAfipCbteTipo(int afipCbteTipo) {
      this.afipCbteTipo = afipCbteTipo;
   }

   public int getAfipConcepto() {
      return this.afipConcepto;
   }

   public void setAfipConcepto(int afipConcepto) {
      this.afipConcepto = afipConcepto;
   }

   public int getAfipDocTipo() {
      return this.afipDocTipo;
   }

   public void setAfipDocTipo(int afipDocTipo) {
      this.afipDocTipo = afipDocTipo;
   }

   public Long getAfipDocNro() {
      return this.afipDocNro;
   }

   public void setAfipDocNro(Long afipDocNro) {
      this.afipDocNro = afipDocNro;
   }

   public Long getAfipCbteDesde() {
      return this.afipCbteDesde;
   }

   public void setAfipCbteDesde(Long afipCbteDesde) {
      this.afipCbteDesde = afipCbteDesde;
   }

   public Long getAfipCbteHasta() {
      return this.afipCbteHasta;
   }

   public void setAfipCbteHasta(Long afipCbteHasta) {
      this.afipCbteHasta = afipCbteHasta;
   }

   public String getAfipCbteFch() {
      return this.afipCbteFch;
   }

   public void setAfipCbteFch(String afipCbteFch) {
      this.afipCbteFch = afipCbteFch;
   }

   public String getAfipFchServDesde() {
      return this.afipFchServDesde;
   }

   public void setAfipFchServDesde(String afipFchServDesde) {
      this.afipFchServDesde = afipFchServDesde;
   }

   public String getAfipFchServHasta() {
      return this.afipFchServHasta;
   }

   public void setAfipFchServHasta(String afipFchServHasta) {
      this.afipFchServHasta = afipFchServHasta;
   }

   public String getAfipFchVtoPago() {
      return this.afipFchVtoPago;
   }

   public void setAfipFchVtoPago(String afipFchVtoPago) {
      this.afipFchVtoPago = afipFchVtoPago;
   }

   public String getAfipCae() {
      return this.afipCae;
   }

   public void setAfipCae(String afipCae) {
      this.afipCae = afipCae;
   }

   public String getAfipCaeFchVto() {
      return this.afipCaeFchVto;
   }

   public void setAfipCaeFchVto(String afipCaeFchVto) {
      this.afipCaeFchVto = afipCaeFchVto;
   }

   public String getAfipBarCode() {
      return this.afipBarCode;
   }

   public void setAfipBarCode(String afipBarCode) {
      this.afipBarCode = afipBarCode;
   }

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public ReceiptType getCbteTipo() {
      return this.cbteTipo;
   }

   public void setCbteTipo(ReceiptType cbteTipo) {
      this.cbteTipo = cbteTipo;
   }

   public Long getCbteNro() {
      return this.cbteNro;
   }

   public void setCbteNro(Long cbteNro) {
      this.cbteNro = cbteNro;
   }

   public ReceiptType getCbteAsocTipo() {
      return this.cbteAsocTipo;
   }

   public void setCbteAsocTipo(ReceiptType cbteAsocTipo) {
      this.cbteAsocTipo = cbteAsocTipo;
   }

   public int getCbteAsocPtoVta() {
      return this.cbteAsocPtoVta;
   }

   public void setCbteAsocPtoVta(int cbteAsocPtoVta) {
      this.cbteAsocPtoVta = cbteAsocPtoVta;
   }

   public Long getCbteAsocNro() {
      return this.cbteAsocNro;
   }

   public void setCbteAsocNro(Long cbteAsocNro) {
      this.cbteAsocNro = cbteAsocNro;
   }

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   public int getCashNumber() {
      return this.cashNumber;
   }

   public void setCashNumber(int cashNumber) {
      this.cashNumber = cashNumber;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public int getAfipPtoVta() {
      return this.afipPtoVta;
   }

   public void setAfipPtoVta(int afipPtoVta) {
      this.afipPtoVta = afipPtoVta;
   }

   public int getAfipCbteAsocPtoVta() {
      return this.afipCbteAsocPtoVta;
   }

   public void setAfipCbteAsocPtoVta(int afipCbteAsocPtoVta) {
      this.afipCbteAsocPtoVta = afipCbteAsocPtoVta;
   }

   public int getAfipCbteAsocTipo() {
      return this.afipCbteAsocTipo;
   }

   public void setAfipCbteAsocTipo(int afipCbteAsocTipo) {
      this.afipCbteAsocTipo = afipCbteAsocTipo;
   }

   public Long getAfipCbteAsocNro() {
      return this.afipCbteAsocNro;
   }

   public void setAfipCbteAsocNro(Long afipCbteAsocNro) {
      this.afipCbteAsocNro = afipCbteAsocNro;
   }

   public String getCbteFchToDisplay() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      String dateStr = formatter.format(this.getCbteFch());
      return dateStr;
   }

   public String getTotalToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getTotal()));
      str = str.replaceAll("\\.", ",");
      return this.getTotal() != 0.0 ? str : "0,00";
   }

   private double getTotal() {
      double total = 0.0;

      try {
         int qty = (int)this.getProductQty();
         double price = this.getProductPrice();
         total = price * (double)qty;
      } catch (Exception var6) {
         total = 0.0;
      }

      return total;
   }

   public String getStatusToDisplay() {
      if ("PENDING".equalsIgnoreCase(this.getStatus())) {
         return "Pendiente";
      } else {
         return "CANCELLED".equalsIgnoreCase(this.getStatus()) ? "Anulada" : "Completada";
      }
   }

   public boolean hasAfipCae() {
      return this.getAfipCae() != null && !this.getAfipCae().equals("");
   }

   public String getTotalToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getTotal()));
      str = str.replaceAll(",", "\\.");
      return this.getTotal() != 0.0 ? str : "0";
   }

   public void initAfipBarCode() {
      if (this.hasAfipCae()) {
         String barCode = "";
         barCode = barCode + this.getAppConfigService().getAppConfig().getCompanyCuit();
         barCode = barCode + this.getAfipCbteTipoForBarCode();
         barCode = barCode + this.getAppConfigService().getAppConfig().getAfipPtoVtaForBarCode();
         barCode = barCode + this.getAfipCae();
         barCode = barCode + this.getAfipCaeFchVto();
         int controlDigit = AfipUtils.createAfipControlDigit(barCode);
         barCode = barCode + controlDigit;
         this.setAfipBarCode(barCode);
      }

   }

   public String getAfipCbteTipoForBarCode() {
      return this.getAfipCbteTipo() < 10 ? "0" + this.getAfipCbteTipo() : String.valueOf(this.getAfipCbteTipo());
   }

   private AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   public String getAfipCbteTipoToDisplay() {
      String cbte = "";

      try {
         switch (this.getAfipCbteTipo()) {
            case 3:
               cbte = "Nota de Crédito A";
               break;
            case 8:
               cbte = "Nota de Crédito B";
               break;
            case 13:
               cbte = "Nota de Crédito C";
         }
      } catch (Exception var3) {
      }

      return cbte;
   }

   public String getAfipCaeFchVtoToDisplay() {
      String dateToDisplay = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipCaeFchVto());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateToDisplay = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateToDisplay;
   }

   public String getImpTotalConcToAfip() {
      return "0";
   }

   public String getImpNetoToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImpNeto()));
      str = str.replaceAll(",", "\\.");
      return this.getImpNeto() != 0.0 ? str : "0";
   }

   public double getImpNeto() {
      double impNeto = 0.0;

      try {
         impNeto = this.getProductNetPrice() * this.getProductQty();
      } catch (Exception var4) {
      }

      return impNeto;
   }

   public double getProductNetPrice() {
      double netValue = this.getProductPrice() / (1.0 + this.getProductVatValue() / 100.0);
      return netValue;
   }

   public String getImpIVAToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImpIVA()));
      str = str.replaceAll(",", "\\.");
      return this.getImpIVA() != 0.0 ? str : "0";
   }

   public double getImpIVA() {
      return this.getTotal() - this.getImpNeto();
   }

   public String getImporteIVA21ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getImporteIVA21()));
      str = str.replaceAll(",", "\\.");
      return this.getImporteIVA21() != 0.0 ? str : "0";
   }

   public double getImporteIVA21() {
      return this.getProductVatValue() == 21.0 ? (this.getProductPrice() - this.getProductNetPrice()) * this.getProductQty() : 0.0;
   }

   public String getBaseImpIVA21ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA21()));
      str = str.replaceAll(",", "\\.");
      return this.getBaseImpIVA21() != 0.0 ? str : "0";
   }

   public double getBaseImpIVA21() {
      return this.getProductVatValue() == 21.0 ? this.getProductNetPrice() * this.getProductQty() : 0.0;
   }

   public double getImporteIVA105() {
      return this.getProductVatValue() == 10.5 ? (this.getProductPrice() - this.getProductNetPrice()) * this.getProductQty() : 0.0;
   }

   public String getBaseImpIVA105ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA105()));
      str = str.replaceAll(",", "\\.");
      return this.getBaseImpIVA105() != 0.0 ? str : "0";
   }

   public double getBaseImpIVA105() {
      return this.getProductVatValue() == 10.5 ? this.getProductNetPrice() * this.getProductQty() : 0.0;
   }

   public String getImporteIVA105ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA105()));
      str = str.replaceAll(",", "\\.");
      return this.getBaseImpIVA105() != 0.0 ? str : "0";
   }

   public double getBaseImpIVA0() {
      return this.roundValue(this.getImpNeto() - this.getBaseImpIVA105() - this.getBaseImpIVA21());
   }

   private double roundValue(double valueToRound) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(valueToRound));
      return Double.valueOf(str.replaceAll(",", "\\."));
   }

   public String getBaseImpIVA0ToAfip() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getBaseImpIVA0()));
      str = str.replaceAll(",", "\\.");
      return this.getBaseImpIVA0() != 0.0 ? str : "0";
   }

   public boolean hasAfipCbteAsoc() {
      return this.getAfipCbteAsocTipo() > 0 && this.getAfipCbteAsocPtoVta() > 0 && this.getAfipCbteAsocNro() > 0L;
   }

   public String getNumberToPrint(AppConfig appConfig) {
      String strNumber = "";

      try {
         String strPtoVta = String.valueOf(appConfig.getPtoVta());
         if (this.hasAfipCae()) {
            strPtoVta = String.valueOf(this.getAfipPtoVta());
         }

         while(strPtoVta.length() < 4) {
            strPtoVta = "0" + strPtoVta;
         }

         String str = "";
         if (this.hasAfipCae()) {
            str = String.valueOf(this.getAfipCbteDesde());
         } else if (this.getCbteNro() != null) {
            str = String.valueOf(this.getCbteNro());
         }

         while(str.length() < 8) {
            str = "0" + str;
         }

         strNumber = strPtoVta + "-" + str;
      } catch (Exception var5) {
      }

      return strNumber;
   }

   public String getCbteFchToPrint() {
      String dateStr = "";

      try {
         if (this.hasAfipCae()) {
            dateStr = this.getAfipCbteFchToPrint();
         } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            dateStr = formatter.format(this.getCbteFch());
         }
      } catch (Exception var3) {
      }

      return dateStr;
   }

   private String getAfipCbteFchToPrint() {
      String dateStr = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipCbteFch());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateStr = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateStr;
   }

   public String getAfipFchServDesdeToPrint() {
      String dateToDisplay = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipFchServDesde());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateToDisplay = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateToDisplay;
   }

   public String getAfipFchServHastaToPrint() {
      String dateToDisplay = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipFchServHasta());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateToDisplay = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateToDisplay;
   }

   public String getAfipFchVtoPagoToPrint() {
      String dateToDisplay = "";

      try {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
         Date date = formatter.parse(this.getAfipFchVtoPago());
         SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
         dateToDisplay = formatter2.format(date);
      } catch (Exception var5) {
      }

      return dateToDisplay;
   }

   public String getAfipDocNroToPrint() {
      String str = "";

      try {
         String docNroStr = String.valueOf(this.getAfipDocNro());
         if (docNroStr.length() == 11) {
            try {
               String p1 = docNroStr.substring(0, 2);
               String p2 = docNroStr.substring(2, 10);
               String p3 = docNroStr.substring(10, 11);
               str = p1 + "-" + p2 + "-" + p3;
            } catch (Exception var6) {
               str = docNroStr;
            }
         } else {
            str = docNroStr;
         }
      } catch (Exception var7) {
         str = "";
      }

      return str;
   }

   public String getCustomerNameToPrint(int i) {
      String str = "";

      try {
         str = this.getCustomer().getFullName();
         if (str.contains("- Cliente Ocasional -")) {
            str = "";
         }

         if (str.length() > i) {
            str = str.substring(0, i);
         }
      } catch (Exception var4) {
      }

      return str;
   }

   public String getProductCodeToPrint(int i) {
      String codeStr = "";
      if (this.getProductCode() != null) {
         codeStr = this.getProductCode();
      }

      if (codeStr.length() > i) {
         codeStr = codeStr.substring(0, i);
      }

      return codeStr;
   }

   public String getProductDescriptionLine1ToPrint(int i) {
      String desc = "";

      try {
         if (this.getProductDescription() != null) {
            desc = this.getProductDescription();
         }

         if (desc.length() > i) {
            desc = desc.substring(0, i);
         }
      } catch (Exception var4) {
      }

      return desc;
   }

   public String getProductDescriptionLine2ToPrint(int i) {
      String desc = "";

      try {
         if (this.getProductDescription() != null) {
            desc = this.getProductDescription();
         }

         if (desc.length() > i) {
            int j = i * 2;
            if (desc.length() > j) {
               desc = desc.substring(i, j);
            } else {
               desc = desc.substring(i);
            }
         } else {
            desc = "";
         }
      } catch (Exception var4) {
      }

      return desc;
   }

   public String getProductQtyToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###", simbolos);
      String str = String.valueOf(formatter.format(this.getProductQty()));
      return str;
   }

   public String getProductNetPriceToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getProductNetPrice()));
      return this.getProductNetPrice() != 0.0 ? str : "0,00";
   }

   public String getVatValueToDisplay() {
      if (this.getProductVatValue() == 21.0) {
         return "21%";
      } else {
         return this.getProductVatValue() == 10.5 ? "10,5%" : "0%";
      }
   }

   public String getProductNetSubtotalToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getProductNetSubtotal()));
      return this.getProductNetSubtotal() != 0.0 ? str : "0,00";
   }

   private double getProductNetSubtotal() {
      return this.roundValue(this.getProductNetPrice() * this.getProductQty());
   }

   public String getImpNetoToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getImpNeto()));
      return this.getImpNeto() != 0.0 ? str : "0,00";
   }

   public String getImporteIVA21ToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getImporteIVA21()));
      return this.getImporteIVA21() != 0.0 ? str : "0,00";
   }

   public String getImporteIVA105ToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getImporteIVA105()));
      return this.getImporteIVA105() != 0.0 ? str : "0,00";
   }

   public String getTotalToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getTotal()));
      return this.getTotal() != 0.0 ? str : "0,00";
   }

   public String getCbteAsocNroToPrint() {
      String strNumber = "";

      try {
         String strPtoVta;
         for(strPtoVta = String.valueOf(this.getCbteAsocPtoVta()); strPtoVta.length() < 4; strPtoVta = "0" + strPtoVta) {
         }

         String str = "";

         for(str = String.valueOf(this.getCbteAsocNro()); str.length() < 8; str = "0" + str) {
         }

         strNumber = strPtoVta + "-" + str;
      } catch (Exception var4) {
      }

      return strNumber;
   }

   public boolean hasCbteAsoc() {
      return this.getCbteAsocTipo() != null && this.getCbteAsocPtoVta() != 0 && this.getCbteAsocNro() != -1L;
   }

   public String getProductPriceToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getProductPrice()));
      return this.getProductPrice() != 0.0 ? str : "0,00";
   }

   public String getProductSubtotalToPrint() {
      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
      simbolos.setDecimalSeparator(',');
      simbolos.setGroupingSeparator('.');
      DecimalFormat formatter = new DecimalFormat("###,###,###.00", simbolos);
      String str = String.valueOf(formatter.format(this.getTotal()));
      return this.getTotal() != 0.0 ? str : "0,00";
   }
}
