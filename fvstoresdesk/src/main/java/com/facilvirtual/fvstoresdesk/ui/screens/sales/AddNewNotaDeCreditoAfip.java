package com.facilvirtual.fvstoresdesk.ui.screens.sales;

import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.NotaDeCredito;
import com.facilvirtual.fvstoresdesk.util.AfipUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;

public class AddNewNotaDeCreditoAfip extends AbstractFVDialog {
   protected static Logger logger = LoggerFactory.getLogger("AddNewNotaDeCredito");
   private String action = "";
   private Employee cashier;
   private NotaDeCredito notaDeCredito;
   private Composite container;
   private Combo comboAfipCbteTipo;
   private DateTime dateTimeCbteFch;
   private Combo comboAfipConcepto;
   private Text txtAfipDocNro;
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
   private Label lblImporteTotal;
   private Text txtImpTotal;

   public AddNewNotaDeCreditoAfip(Shell parentShell) {
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
      this.lblFacturaElectrnicaDe.setText("Comprobante Electrónico");
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
         this.comboAfipCbteTipo.add("Nota de Crédito C");
      } else {
         if (this.getAppConfig().isAfipEnabledFacturaA()) {
            this.comboAfipCbteTipo.add("Nota de Crédito A");
         }

         this.comboAfipCbteTipo.add("Nota de Crédito B");
      }

      this.comboAfipCbteTipo.select(0);
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
      this.comboAfipConcepto.add("Productos");
      this.comboAfipConcepto.add("Servicios");
      this.comboAfipConcepto.add("Productos y Servicios");
      this.comboAfipConcepto.select(0);
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
      this.lblImporteTotal = new Label(this.container, 0);
      this.lblImporteTotal.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblImporteTotal.setText("Importe Total");
      this.txtImpTotal = new Text(this.container, 2056);
      this.txtImpTotal.setLayoutData(new GridData(16384, 16777216, true, false, 1, 1));
      this.txtImpTotal.setText(this.getNotaDeCredito().getTotalToDisplay());
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
         if (this.getNotaDeCredito().getCbteTipo().getName().equalsIgnoreCase("Nota de Crédito B")) {
            this.comboAfipCbteTipo.select(1);
         }
      } catch (Exception var15) {
      }

      try {
         if (this.getNotaDeCredito().getCustomer() != null && this.getNotaDeCredito().getCustomer().getId() != 1L) {
            this.comboAfipDocTipo.select(1);
            this.txtAfipDocNro.setText(this.getNotaDeCredito().getCustomer().getDocumentNumber().trim());
         }
      } catch (Exception var14) {
      }

      try {
         if (this.getNotaDeCredito().getCbteTipo().getName().equalsIgnoreCase("Nota de Crédito A") && this.getAppConfig().isAfipEnabledFacturaA()) {
            this.comboAfipDocTipo.select(2);
            if (this.getNotaDeCredito().getCustomer() != null && this.getNotaDeCredito().getCustomer().getId() != 1L) {
               this.txtAfipDocNro.setText(this.getNotaDeCredito().getCustomer().getCuit());
            }
         }
      } catch (Exception var13) {
      }

      return this.container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         this.notaDeCredito.setAfipCbteTipo(AfipUtils.getAfipCbteTipoByName(this.comboAfipCbteTipo.getText()));
         String afipCbteFch = "";

         try {
            Date parsedDate = this.buildDateFromInput(this.dateTimeCbteFch);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            afipCbteFch = formatter.format(parsedDate);
            if (!DateUtils.isSameDay(this.notaDeCredito.getCbteFch(), parsedDate)) {
               this.notaDeCredito.setCbteFch(parsedDate);
            }
         } catch (Exception var16) {
         }

         this.notaDeCredito.setAfipCbteFch(afipCbteFch);
         int afipConcepto = 0;
         String afipConceptoStr = this.comboAfipConcepto.getItem(this.comboAfipConcepto.getSelectionIndex());
         if (afipConceptoStr.equalsIgnoreCase("Productos")) {
            afipConcepto = 1;
         } else if (afipConceptoStr.equalsIgnoreCase("Servicios")) {
            afipConcepto = 2;
         } else if (afipConceptoStr.equalsIgnoreCase("Productos y Servicios")) {
            afipConcepto = 3;
         }

         this.notaDeCredito.setAfipConcepto(afipConcepto);
         String errorMsg;
         String afipFchServHasta;
         if (afipConcepto != 2 && afipConcepto != 3) {
            this.notaDeCredito.setAfipFchServDesde("");
            this.notaDeCredito.setAfipFchServHasta("");
            this.notaDeCredito.setAfipFchVtoPago("");
         } else {
            errorMsg = "";

            try {
               Date parsedDate = this.buildDateFromInput(this.dateTimeFchServDesde);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
               errorMsg = formatter.format(parsedDate);
            } catch (Exception var15) {
            }

            this.notaDeCredito.setAfipFchServDesde(errorMsg);
            afipFchServHasta = "";

            try {
               Date parsedDate = this.buildDateFromInput(this.dateTimeFchServHasta);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
               afipFchServHasta = formatter.format(parsedDate);
            } catch (Exception var14) {
            }

            this.notaDeCredito.setAfipFchServHasta(afipFchServHasta);
            String afipFchVtoPago = "";

            try {
               Date parsedDate = this.buildDateFromInput(this.dateTimeFchVtoPago);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
               afipFchVtoPago = formatter.format(parsedDate);
            } catch (Exception var13) {
            }

            this.notaDeCredito.setAfipFchVtoPago(afipFchVtoPago);
         }

         try {
            int afipDocTipo = 99;
            afipFchServHasta = this.comboAfipDocTipo.getItem(this.comboAfipDocTipo.getSelectionIndex());
            if (afipFchServHasta.equalsIgnoreCase("DNI")) {
               afipDocTipo = 96;
            } else if (afipFchServHasta.equalsIgnoreCase("CUIT")) {
               afipDocTipo = 80;
            }

            this.notaDeCredito.setAfipDocTipo(afipDocTipo);
         } catch (Exception var12) {
            this.notaDeCredito.setAfipDocTipo(99);
         }

         try {
            this.notaDeCredito.setAfipDocNro(new Long(this.txtAfipDocNro.getText()));
         } catch (Exception var11) {
            this.notaDeCredito.setAfipDocNro(new Long(0L));
         }

         this.notaDeCredito.setAfipPtoVta(this.getAppConfig().getAfipPtoVta());

         try {
            this.notaDeCredito.setAfipCbteAsocTipo(AfipUtils.getAfipCbteTipoByName(this.notaDeCredito.getCbteAsocTipo().getName()));
            this.notaDeCredito.setAfipCbteAsocPtoVta(this.notaDeCredito.getCbteAsocPtoVta());
            this.notaDeCredito.setAfipCbteAsocNro(this.notaDeCredito.getCbteAsocNro());
         } catch (Exception var10) {
            this.notaDeCredito.setAfipCbteAsocTipo(0);
            this.notaDeCredito.setAfipCbteAsocPtoVta(0);
            this.notaDeCredito.setAfipCbteAsocNro(0L);
            var10.printStackTrace();
         }

         errorMsg = this.getOrderService().createNotaDeCreditoAfip(this.notaDeCredito);
         if (!errorMsg.equals("")) {
            this.alert("Error: " + errorMsg);
         } else {
            if (this.notaDeCredito.hasAfipCae()) {
               try {
                  if (this.notaDeCredito.getAfipCbteTipo() == 3 && !this.notaDeCredito.getCbteTipo().getName().equalsIgnoreCase("Nota de Crédito A")) {
                     this.notaDeCredito.setCbteTipo(this.getAppConfigService().getReceiptTypeByName("Nota de Crédito A"));
                  }

                  if (this.notaDeCredito.getAfipCbteTipo() == 8 && !this.notaDeCredito.getCbteTipo().getName().equalsIgnoreCase("Nota de Crédito B")) {
                     this.notaDeCredito.setCbteTipo(this.getAppConfigService().getReceiptTypeByName("Nota de Crédito B"));
                  }

                  if (this.notaDeCredito.getAfipCbteTipo() == 13 && !this.notaDeCredito.getCbteTipo().getName().equalsIgnoreCase("Nota de Crédito C")) {
                     this.notaDeCredito.setCbteTipo(this.getAppConfigService().getReceiptTypeByName("Nota de Crédito C"));
                  }
               } catch (Exception var9) {
               }

               this.notaDeCredito.initAfipBarCode();
               this.getOrderService().saveNotaDeCredito(this.notaDeCredito);
               this.alert("Se generó el Comprobante Electrónico de Afip con éxito.");
               logger.info("Se creó comprobante: " + this.notaDeCredito.getAfipCbteTipoToDisplay());
               logger.info("CAE: " + this.notaDeCredito.getAfipCae());
               logger.info("Vto. CAE: " + this.notaDeCredito.getAfipCaeFchVtoToDisplay());
            }

            this.close();
         }
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Comprobante Electrónico - Afip");
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
      this.buttonCAE = this.createButton(parent, 0, "Guardar", false);
      this.buttonCAE.setText("Solicitar CAE");
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

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   public NotaDeCredito getNotaDeCredito() {
      return this.notaDeCredito;
   }

   public void setNotaDeCredito(NotaDeCredito notaDeCredito) {
      this.notaDeCredito = notaDeCredito;
   }
}
