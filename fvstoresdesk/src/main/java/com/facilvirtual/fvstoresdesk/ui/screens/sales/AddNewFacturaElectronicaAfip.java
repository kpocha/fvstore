package com.facilvirtual.fvstoresdesk.ui.screens.sales;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.util.AfipUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;

public class AddNewFacturaElectronicaAfip extends AbstractFVDialog {
   protected static final Logger logger = LoggerFactory.getLogger(AddNewFacturaElectronicaAfip.class);
   private String action = "";
   private Order order;
   private Composite container;
   private Combo comboAfipCbteTipo;
   private DateTime dateTimeCbteFch;
   private Combo comboAfipConcepto;
   private Text txtAfipDocNro;
   private Text txtImpTotal;
   private Combo comboAfipDocTipo;
   private Label lblFechaServicioDesde;
   private Label lblFechaServicioHasta;
   private Label lblFechaServicioVto;
   private DateTime dateTimeFchServDesde;
   private DateTime dateTimeFchServHasta;
   private DateTime dateTimeFchVtoPago;
   private GridData gd_lblFechaServicioDesde;
   private GridData gd_lblFechaServicioHasta;
   private GridData gd_lblFechaServicioVto;
   private GridData gd_dateTimeFchServDesde;
   private GridData gd_dateTimeFchServHasta;
   private GridData gd_dateTimeFchVtoPago;
   private Label lblFacturaElectrnicaDe;
   private Button buttonCAE;

   public AddNewFacturaElectronicaAfip(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }

   protected Control createDialogArea(Composite parent) {
      this.container = (Composite)super.createDialogArea(parent);
      this.container.setLayout(new GridLayout(2, false));
      this.lblFacturaElectrnicaDe = new Label(this.container, 0);
      GridData gd_lblFacturaElectrnicaDe = new GridData(16777216, 16777216, false, false, 2, 1);
      gd_lblFacturaElectrnicaDe.heightHint = 35;
      this.lblFacturaElectrnicaDe.setLayoutData(gd_lblFacturaElectrnicaDe);
      this.lblFacturaElectrnicaDe.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      this.lblFacturaElectrnicaDe.setText("Factura Electrónica");
      Label lblNewLabel = new Label(this.container, 0);
      GridData gd_lblNewLabel = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 200;
      lblNewLabel.setLayoutData(gd_lblNewLabel);
      lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1));
      lblNewLabel.setText("Datos del comprobante");
      new Label(this.container, 0);
      new Label(this.container, 0);
      new Label(this.container, 0);
      Label lblTipoDeComprobante = new Label(this.container, 0);
      lblTipoDeComprobante.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTipoDeComprobante.setText("Tipo de comprobante");
      this.comboAfipCbteTipo = new Combo(this.container, 8);
      this.comboAfipCbteTipo.setLayoutData(new GridData(16384, 16777216, true, false, 1, 1));
      if (this.getAppConfig().getCompanyVatCondition().getName().equals("Monotributo")) {
         this.comboAfipCbteTipo.add("Factura C");
         this.comboAfipCbteTipo.select(0);
      } else {
         int idx = 0;
         if (this.getAppConfig().isAfipEnabledFacturaA()) {
            this.comboAfipCbteTipo.add("Factura A");
            ++idx;
         }

         this.comboAfipCbteTipo.add("Factura B");
         this.comboAfipCbteTipo.select(idx);

         try {
            if (this.getOrder().getReceiptType().getName().equalsIgnoreCase("Factura A") && this.getAppConfig().isAfipEnabledFacturaA() || this.getOrder().getReceiptType().getName().equalsIgnoreCase("Ticket Factura A") && this.getAppConfig().isAfipEnabledFacturaA()) {
               this.comboAfipCbteTipo.select(0);
            }
         } catch (Exception var16) {
         }
      }

      Label lblFechaDelComprobante = new Label(this.container, 0);
      lblFechaDelComprobante.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblFechaDelComprobante.setText("Fecha del comprobante");
      this.dateTimeCbteFch = new DateTime(this.container, 2048);
      Label lblConceptosAIncluir = new Label(this.container, 0);
      lblConceptosAIncluir.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblConceptosAIncluir.setText("Conceptos a incluir");
      this.comboAfipConcepto = new Combo(this.container, 8);
      this.comboAfipConcepto.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            boolean hide = true;
            if (comboAfipConcepto.getItem(comboAfipConcepto.getSelectionIndex()).contains("Servicios")) {
               hide = true;
            } else {
               hide = false;
            }

            lblFechaServicioDesde.setVisible(hide);
            lblFechaServicioHasta.setVisible(hide);
            lblFechaServicioVto.setVisible(hide);
            dateTimeFchServDesde.setVisible(hide);
            dateTimeFchServHasta.setVisible(hide);
            dateTimeFchVtoPago.setVisible(hide);
            gd_lblFechaServicioDesde.exclude = !hide;
            gd_lblFechaServicioHasta.exclude = !hide;
            gd_lblFechaServicioVto.exclude = !hide;
            gd_dateTimeFchServDesde.exclude = !hide;
            gd_dateTimeFchServHasta.exclude = !hide;
            gd_dateTimeFchVtoPago.exclude = !hide;
            container.pack();
         }
      });
      this.comboAfipConcepto.setLayoutData(new GridData(16384, 16777216, true, false, 1, 1));
      this.comboAfipConcepto.add("Productos");
      this.comboAfipConcepto.add("Servicios");
      this.comboAfipConcepto.add("Productos y Servicios");
      this.comboAfipConcepto.select(0);
      Label lblImporteTotal = new Label(this.container, 0);
      lblImporteTotal.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblImporteTotal.setText("Importe Total");
      this.txtImpTotal = new Text(this.container, 2056);
      this.txtImpTotal.setLayoutData(new GridData(16384, 16777216, true, false, 1, 1));
      this.txtImpTotal.setText(this.getOrder().getTotalToDisplay());
      this.lblFechaServicioDesde = new Label(this.container, 0);
      this.gd_lblFechaServicioDesde = new GridData(131072, 16777216, false, false, 1, 1);
      this.gd_lblFechaServicioDesde.exclude = true;
      this.lblFechaServicioDesde.setLayoutData(this.gd_lblFechaServicioDesde);
      this.lblFechaServicioDesde.setText("Fecha Servicio Desde:");
      this.dateTimeFchServDesde = new DateTime(this.container, 2048);
      this.gd_dateTimeFchServDesde = new GridData(16384, 16777216, false, false, 1, 1);
      this.gd_dateTimeFchServDesde.exclude = true;
      this.dateTimeFchServDesde.setLayoutData(this.gd_dateTimeFchServDesde);
      this.lblFechaServicioHasta = new Label(this.container, 0);
      this.gd_lblFechaServicioHasta = new GridData(131072, 16777216, false, false, 1, 1);
      this.gd_lblFechaServicioHasta.exclude = true;
      this.lblFechaServicioHasta.setLayoutData(this.gd_lblFechaServicioHasta);
      this.lblFechaServicioHasta.setText("Fecha Servicio Hasta:");
      this.dateTimeFchServHasta = new DateTime(this.container, 2048);
      this.gd_dateTimeFchServHasta = new GridData(16384, 16777216, false, false, 1, 1);
      this.gd_dateTimeFchServHasta.exclude = true;
      this.dateTimeFchServHasta.setLayoutData(this.gd_dateTimeFchServHasta);
      this.lblFechaServicioVto = new Label(this.container, 0);
      this.gd_lblFechaServicioVto = new GridData(131072, 16777216, false, false, 1, 1);
      this.gd_lblFechaServicioVto.exclude = true;
      this.lblFechaServicioVto.setLayoutData(this.gd_lblFechaServicioVto);
      this.lblFechaServicioVto.setText("Fecha Servicio Vto:");
      this.dateTimeFchVtoPago = new DateTime(this.container, 2048);
      this.gd_dateTimeFchVtoPago = new GridData(16384, 16777216, false, false, 1, 1);
      this.gd_dateTimeFchVtoPago.exclude = true;
      this.dateTimeFchVtoPago.setLayoutData(this.gd_dateTimeFchVtoPago);
      new Label(this.container, 0);
      new Label(this.container, 0);
      Label lblDatosDelReceptor = new Label(this.container, 0);
      lblDatosDelReceptor.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1));
      lblDatosDelReceptor.setText("Datos del receptor");
      new Label(this.container, 0);
      new Label(this.container, 0);
      new Label(this.container, 0);
      Label lblTipoYNro = new Label(this.container, 0);
      lblTipoYNro.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblTipoYNro.setText("Tipo y Nro. de documento");
      Composite composite = new Composite(this.container, 0);
      composite.setLayout(new GridLayout(2, false));
      this.comboAfipDocTipo = new Combo(composite, 8);
      this.comboAfipDocTipo.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.comboAfipDocTipo.add("");
      this.comboAfipDocTipo.add("DNI");
      this.comboAfipDocTipo.add("CUIT");
      this.comboAfipDocTipo.select(0);
      this.txtAfipDocNro = new Text(composite, 2048);
      this.txtAfipDocNro.setTextLimit(11);
      GridData gd_txtAfipDocNro = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtAfipDocNro.widthHint = 150;
      this.txtAfipDocNro.setLayoutData(gd_txtAfipDocNro);

      try {
         if (this.getOrder().getCustomer() != null && this.getOrder().getCustomer().getId() != 1L) {
            this.comboAfipDocTipo.select(1);
            this.txtAfipDocNro.setText(this.getOrder().getCustomer().getDocumentNumber().trim());
         }
      } catch (Exception var14) {
      }

      try {
         if (this.getOrder().getReceiptType().getName().equalsIgnoreCase("Factura A") && this.getAppConfig().isAfipEnabledFacturaA() || this.getOrder().getReceiptType().getName().equalsIgnoreCase("Ticket Factura A") && this.getAppConfig().isAfipEnabledFacturaA()) {
            this.comboAfipDocTipo.select(2);
            if (this.getOrder().getCustomer() != null && this.getOrder().getCustomer().getId() != 1L) {
               this.txtAfipDocNro.setText(this.getOrder().getCustomer().getCuit());
            }
         }
      } catch (Exception var15) {
      }

      return this.container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         this.order.setAfipCbteTipo(AfipUtils.getAfipCbteTipoByName(this.comboAfipCbteTipo.getText()));
         String afipCbteFch = "";

         try {
            Date parsedDate = this.buildDateFromInput(this.dateTimeCbteFch);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            afipCbteFch = formatter.format(parsedDate);
            if (!DateUtils.isSameDay(this.order.getSaleDate(), parsedDate)) {
               this.order.setSaleDate(parsedDate);
            }
         } catch (Exception var14) {
         }

         this.order.setAfipCbteFch(afipCbteFch);
         int afipConcepto = 0;
         String afipConceptoStr = this.comboAfipConcepto.getItem(this.comboAfipConcepto.getSelectionIndex());
         if (afipConceptoStr.equals("Productos")) {
            afipConcepto = 1;
         } else if (afipConceptoStr.equals("Servicios")) {
            afipConcepto = 2;
         } else if (afipConceptoStr.equals("Productos y Servicios")) {
            afipConcepto = 3;
         }

         this.order.setAfipConcepto(afipConcepto);
         String errorMsg;
         String afipFchServHasta;
         if (afipConcepto != 2 && afipConcepto != 3) {
            this.order.setAfipFchServDesde("");
            this.order.setAfipFchServHasta("");
            this.order.setAfipFchVtoPago("");
         } else {
            errorMsg = "";

            try {
               Date parsedDate = this.buildDateFromInput(this.dateTimeFchServDesde);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
               errorMsg = formatter.format(parsedDate);
            } catch (Exception var13) {
            }

            this.order.setAfipFchServDesde(errorMsg);
            afipFchServHasta = "";

            try {
               Date parsedDate = this.buildDateFromInput(this.dateTimeFchServHasta);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
               afipFchServHasta = formatter.format(parsedDate);
            } catch (Exception var12) {
            }

            this.order.setAfipFchServHasta(afipFchServHasta);
            String afipFchVtoPago = "";

            try {
               Date parsedDate = this.buildDateFromInput(this.dateTimeFchVtoPago);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
               afipFchVtoPago = formatter.format(parsedDate);
            } catch (Exception var11) {
            }

            this.order.setAfipFchVtoPago(afipFchVtoPago);
         }

         try {
            int afipDocTipo = 99;
            afipFchServHasta = this.comboAfipDocTipo.getItem(this.comboAfipDocTipo.getSelectionIndex());
            if (afipFchServHasta.equals("DNI")) {
               afipDocTipo = 96;
            } else if (afipFchServHasta.equals("CUIT")) {
               afipDocTipo = 80;
            }

            this.order.setAfipDocTipo(afipDocTipo);
         } catch (Exception var10) {
            this.order.setAfipDocTipo(99);
         }

         try {
            this.order.setAfipDocNro(new Long(this.txtAfipDocNro.getText()));
         } catch (Exception var9) {
            this.order.setAfipDocNro(new Long(0L));
         }

         this.order.setAfipPtoVta(this.getAppConfig().getAfipPtoVta());
         errorMsg = this.getOrderService().createFacturaElectronicaAfip(this.order);
         if (!errorMsg.equals("")) {
            this.alert("Error: " + errorMsg);
         } else {
            if (this.order.hasAfipCae()) {
               try {
                  if (this.order.getAfipCbteTipo() == 1) {
                     if (!this.order.getReceiptType().getName().equalsIgnoreCase("Factura A") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura A") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Fiscal") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
                        this.order.setReceiptType(this.getAppConfigService().getReceiptTypeByName("Factura A"));
                     } else if (this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Fiscal") || this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
                        this.order.setReceiptType(this.getAppConfigService().getReceiptTypeByName("Ticket Factura A"));
                     }
                  }

                  if (this.order.getAfipCbteTipo() == 6) {
                     if (!this.order.getReceiptType().getName().equalsIgnoreCase("Factura B") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura B") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Fiscal") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
                        this.order.setReceiptType(this.getAppConfigService().getReceiptTypeByName("Factura B"));
                     } else if (this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
                        this.order.setReceiptType(this.getAppConfigService().getReceiptTypeByName("Ticket Fiscal"));
                     }
                  }

                  if (this.order.getAfipCbteTipo() == 11) {
                     if (!this.order.getReceiptType().getName().equalsIgnoreCase("Factura C") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Factura C") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket Fiscal") && !this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
                        this.order.setReceiptType(this.getAppConfigService().getReceiptTypeByName("Factura C"));
                     } else if (this.order.getReceiptType().getName().equalsIgnoreCase("Ticket No Fiscal")) {
                        this.order.setReceiptType(this.getAppConfigService().getReceiptTypeByName("Ticket Fiscal"));
                     }
                  }
               } catch (Exception var15) {
               }

               this.order.initAfipBarCode();
               this.getOrderService().saveOrder(this.order);
               this.alert("Se generó la Factura Electrónica de Afip con éxito.");
               logger.info("Se creó comprobante: " + this.order.getAfipCbteTipoToDisplay());
               logger.info("CAE: " + this.order.getAfipCae());
               logger.info("Vto. CAE: " + this.order.getAfipCaeFchVtoToDisplay());
            }

            this.close();
         }
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      int afipConcepto = 0;
      String afipConceptoStr = this.comboAfipConcepto.getItem(this.comboAfipConcepto.getSelectionIndex());
      if (afipConceptoStr.equals("Productos")) {
         afipConcepto = 1;
      } else if (afipConceptoStr.equals("Servicios")) {
         afipConcepto = 2;
      } else if (afipConceptoStr.equals("Productos y Servicios")) {
         afipConcepto = 3;
      }

      this.order.setAfipConcepto(afipConcepto);
      if (afipConcepto == 2 || afipConcepto == 3) {
         Date parsedDate;
         Date cbteFch;
         try {
            parsedDate = this.buildDateFromInput(this.dateTimeFchServDesde);
            cbteFch = this.buildDateFromInput(this.dateTimeFchServHasta);
            if (cbteFch.before(parsedDate)) {
               valid = false;
               this.alert("La fecha Hasta del servicio no puede ser anterior a la fecha Desde");
            }
         } catch (Exception var7) {
         }

         try {
            parsedDate = this.buildDateFromInput(this.dateTimeFchVtoPago);
            cbteFch = this.buildDateFromInput(this.dateTimeCbteFch);
            if (parsedDate.before(cbteFch)) {
               valid = false;
               this.alert("La fecha de vencimiento del pago no puede ser anterior a la fecha del comprobante");
            }
         } catch (Exception var6) {
         }
      }

      return valid;
   }

   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Factura Electrónica - Afip");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         try {
            this.buttonCAE.setEnabled(false);
            this.buttonCAE.setText("Cargando...");
            this.processDialog();
            this.buttonCAE.setEnabled(true);
            this.buttonCAE.setText("Solicitar CAE");
         } catch (Exception var3) {
         }
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.buttonCAE = this.createButton(parent, 0, "Solicitar CAE", false);
      this.buttonCAE.setText("Solicitar CAE");
      this.buttonCAE.setFocus();
      Button button_1 = this.createButton(parent, 1, "Cancelar", false);
      button_1.setText("Cancelar");
   }

   protected Point getInitialSize() {
      return new Point(500, 480);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public Order getOrder() {
      return this.order;
   }

   public void setOrder(Order order) {
      this.order = order;
   }
}
