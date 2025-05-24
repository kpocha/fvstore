package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.NotaDeCredito;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.Vat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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

public class AddNewNotaDeCredito extends AbstractFVDialog {
   protected static Logger logger = LoggerFactory.getLogger("AddNewNotaDeCredito");
   private String action = "";
   private Employee cashier;
   private Order order;
   private Composite container;
   private DateTime dateTimeFecha;
   private Label lblComprobanteAsociado;
   private Composite composite_1;
   private Label lblPtoVta;
   private Text txtCbteAsocPtoVta;
   private Label lblComprobante;
   private Text txtCbteAsocNro;
   private Label lblConcepto;
   private Text txtProductDesc;
   private Label lblTotal;
   private Text txtProductPrice;
   private Label lblIva;
   private Label lblTotal_1;
   private Text txtProductTotal;
   private Combo comboProductVat;
   private Composite composite_2;
   private Label lblProductoservicio;
   private Label lblCant;
   private Text txtProductQty;
   private Text txtProductCode;
   private Label lblCdigo;
   private Button btnCrearComprobanteAfip;
   private Label lblTipoDeComprobante;
   private Combo comboCbteTipo;
   private Label lblCliente;
   private Composite composite;
   private Text txtCustomerName;
   private Button btnChangeCustomer;
   private Label lblTipoDeComprobante_1;
   private Combo comboCbteAsocTipo;
   private Customer customer;

   public AddNewNotaDeCredito(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }

   protected Control createDialogArea(Composite parent) {
      this.container = (Composite)super.createDialogArea(parent);
      GridLayout gl_container = new GridLayout(2, false);
      gl_container.marginLeft = 5;
      this.container.setLayout(gl_container);
      new Label(this.container, 0);
      new Label(this.container, 0);
      Label lblNewLabel = new Label(this.container, 0);
      GridData gd_lblNewLabel = new GridData(16777216, 16777216, false, false, 2, 1);
      gd_lblNewLabel.widthHint = 218;
      lblNewLabel.setLayoutData(gd_lblNewLabel);
      lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      lblNewLabel.setText("Nueva nota de crédito");
      new Label(this.container, 0);
      new Label(this.container, 0);
      this.lblTipoDeComprobante = new Label(this.container, 0);
      this.lblTipoDeComprobante.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      this.lblTipoDeComprobante.setText("Tipo de comprobante");
      this.comboCbteTipo = new Combo(this.container, 8);
      this.comboCbteTipo.setLayoutData(new GridData(16384, 16777216, true, false, 1, 1));
      if (this.getAppConfig().getCompanyVatCondition().getName().equals("Monotributo")) {
         this.comboCbteTipo.add("Nota de Crédito C");
      } else {
         if (this.getAppConfig().isAfipEnabledFacturaA()) {
            this.comboCbteTipo.add("Nota de Crédito A");
         }

         this.comboCbteTipo.add("Nota de Crédito B");
      }

      this.comboCbteTipo.select(0);
      Label lblFechaDelComprobante = new Label(this.container, 0);
      lblFechaDelComprobante.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblFechaDelComprobante.setText("Fecha");
      this.dateTimeFecha = new DateTime(this.container, 2048);
      new Label(this.container, 0);
      new Label(this.container, 0);
      this.lblCliente = new Label(this.container, 0);
      this.lblCliente.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1));
      this.lblCliente.setText("Cliente");
      new Label(this.container, 0);
      this.composite = new Composite(this.container, 0);
      this.composite.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
      this.composite.setLayout(new GridLayout(2, false));
      this.txtCustomerName = new Text(this.composite, 2056);
      GridData gd_txtCustomerName = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtCustomerName.widthHint = 198;
      this.txtCustomerName.setLayoutData(gd_txtCustomerName);
      this.btnChangeCustomer = new Button(this.composite, 0);

      this.btnChangeCustomer.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent arg0) {
            changeCustomer();
         }
      });

      this.btnChangeCustomer.setText("Cambiar cliente");
      new Label(this.container, 0);
      new Label(this.container, 0);
      this.lblComprobanteAsociado = new Label(this.container, 0);
      this.lblComprobanteAsociado.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1));
      this.lblComprobanteAsociado.setText("Comprobante Asociado");
      new Label(this.container, 0);
      this.composite_1 = new Composite(this.container, 0);
      this.composite_1.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
      this.composite_1.setLayout(new GridLayout(3, false));
      this.lblTipoDeComprobante_1 = new Label(this.composite_1, 0);
      GridData gd_lblTipoDeComprobante_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblTipoDeComprobante_1.widthHint = 140;
      this.lblTipoDeComprobante_1.setLayoutData(gd_lblTipoDeComprobante_1);
      this.lblTipoDeComprobante_1.setText("Tipo de comprobante");
      this.lblPtoVta = new Label(this.composite_1, 0);
      this.lblPtoVta.setText("Pto. Vta.");
      this.lblComprobante = new Label(this.composite_1, 0);
      this.lblComprobante.setText("Nro.");
      this.comboCbteAsocTipo = new Combo(this.composite_1, 8);
      GridData gd_comboCbteAsocTipo = new GridData(4, 16777216, true, false, 1, 1);
      gd_comboCbteAsocTipo.widthHint = 66;
      this.comboCbteAsocTipo.setLayoutData(gd_comboCbteAsocTipo);
      if (this.getAppConfig().getCompanyVatCondition().getName().equals("Monotributo")) {
         this.comboCbteAsocTipo.add("Factura C");
      } else {
         if (this.getAppConfig().isAfipEnabledFacturaA()) {
            this.comboCbteAsocTipo.add("Factura A");
         }

         this.comboCbteAsocTipo.add("Factura B");
      }

      this.comboCbteAsocTipo.select(0);
      this.txtCbteAsocPtoVta = new Text(this.composite_1, 2048);
      this.txtCbteAsocPtoVta.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      this.txtCbteAsocNro = new Text(this.composite_1, 2048);
      GridData gd_txtCbteAsocNro = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtCbteAsocNro.widthHint = 152;
      this.txtCbteAsocNro.setLayoutData(gd_txtCbteAsocNro);
      new Label(this.container, 0);
      new Label(this.container, 0);
      this.lblProductoservicio = new Label(this.container, 0);
      this.lblProductoservicio.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1));
      this.lblProductoservicio.setText("Producto/Servicio");
      new Label(this.container, 0);
      this.composite_2 = new Composite(this.container, 0);
      this.composite_2.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
      this.composite_2.setLayout(new GridLayout(6, false));
      this.lblCdigo = new Label(this.composite_2, 0);
      this.lblCdigo.setText("Código");
      this.lblConcepto = new Label(this.composite_2, 0);
      this.lblConcepto.setText("Descripción");
      this.lblCant = new Label(this.composite_2, 0);
      this.lblCant.setText("Cant.");
      this.lblTotal = new Label(this.composite_2, 0);
      this.lblTotal.setText("P. Unitario");
      this.lblIva = new Label(this.composite_2, 0);
      this.lblIva.setText("IVA");
      this.lblTotal_1 = new Label(this.composite_2, 0);
      this.lblTotal_1.setText("Total");
      this.txtProductCode = new Text(this.composite_2, 133120);
      GridData gd_txtProductCode = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtProductCode.widthHint = 50;
      this.txtProductCode.setLayoutData(gd_txtProductCode);
      this.txtProductDesc = new Text(this.composite_2, 2048);
      GridData gd_txtProductDesc = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtProductDesc.widthHint = 162;
      this.txtProductDesc.setLayoutData(gd_txtProductDesc);
      this.txtProductQty = new Text(this.composite_2, 133120);

      // this.txtProductQty.addKeyListener(new KeyAdapter() {
      //    @Override
      //    public void keyReleased(KeyEvent e) {
      //       productQty();
      //    }
      // });
//TODO:arreglar
      GridData gd_txtProductQty = new GridData(4, 16777216, true, false, 1, 1);
   
      gd_txtProductQty.widthHint = 35;
      this.txtProductQty.setLayoutData(gd_txtProductQty);
      this.txtProductQty.setText("1");
      this.txtProductPrice = new Text(this.composite_2, 133120);
     // this.txtProductPrice.addKeyListener(new 3(this));
      this.txtProductPrice.setLayoutData(new GridData(16384, 16777216, true, false, 1, 1));
      this.comboProductVat = new Combo(this.composite_2, 8);
      //this.comboProductVat.addSelectionListener(new 4(this));
      GridData gd_comboProductVat = new GridData(16384, 16777216, true, false, 1, 1);
      gd_comboProductVat.widthHint = 30;
      this.comboProductVat.setLayoutData(gd_comboProductVat);
      if (this.getAppConfig().getCompanyVatCondition().getName().equalsIgnoreCase("Monotributo")) {
         this.comboProductVat.add("0%");
         this.comboProductVat.select(0);
      } else {
         List<Vat> vats = this.getOrderService().getAllVats();
         int selectedIdx = 0;

         for(Iterator var17 = vats.iterator(); var17.hasNext(); ++selectedIdx) {
            Vat v = (Vat)var17.next();
            this.comboProductVat.add(v.getName());
            if (this.getOrderService().getDefaultVat(this.getAppConfig()).getName().equals(v.getName())) {
               this.comboProductVat.select(selectedIdx);
            }
         }
      }

      this.txtProductTotal = new Text(this.composite_2, 133128);
      this.txtProductTotal.setEditable(false);
      this.txtProductTotal.setLayoutData(new GridData(16384, 16777216, true, false, 1, 1));
      new Label(this.container, 0);
      new Label(this.container, 0);
      this.btnCrearComprobanteAfip = new Button(this.container, 32);
      this.btnCrearComprobanteAfip.setSelection(true);
      this.btnCrearComprobanteAfip.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
      this.btnCrearComprobanteAfip.setText("Crear comprobante electrónico de Afip");
      return this.container;
   }

   private void changeCustomer() {
      ChangeCustomer dialog = new ChangeCustomer(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.txtCustomerName.setText(dialog.getCustomer().getFullName());
         this.setCustomer(dialog.getCustomer());
      }

   }

   private void calculateTotal() {
      double total = 0.0;

      try {
         int qty = this.getIntegerValueFromText(this.txtProductQty);
         double price = this.getDoubleValueFromText(this.txtProductPrice);
         total = price * (double)qty;
      } catch (Exception var7) {
         total = 0.0;
      }

      String totalStr = "";

      try {
         DecimalFormat formatter = new DecimalFormat("0.00");
         totalStr = String.valueOf(formatter.format(total));
         totalStr = totalStr.replaceAll("\\.", ",");
      } catch (Exception var6) {
         totalStr = "0,00";
      }

      this.txtProductTotal.setText(totalStr);
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         logger.info("Guardando nota de crédito");

         try {
            NotaDeCredito nota = new NotaDeCredito();
            nota.setCreationDate(new Date());
            nota.setCbteTipo(this.getAppConfigService().getReceiptTypeByName(this.comboCbteTipo.getText()));
            nota.setCbteFch(this.buildDateFromInput(this.dateTimeFecha));
            nota.setCbteNro(new Long((long)(this.getOrderService().getNotasDeCreditoQty() + 1)));
            nota.setCbteAsocTipo(this.getAppConfigService().getReceiptTypeByName(this.comboCbteAsocTipo.getText()));

            try {
               nota.setCbteAsocPtoVta(Integer.valueOf(this.txtCbteAsocPtoVta.getText().trim()));
            } catch (Exception var7) {
            }

            try {
               nota.setCbteAsocNro(new Long(this.txtCbteAsocNro.getText().trim()));
            } catch (Exception var6) {
            }

            nota.setCustomer(this.getCustomer());
            nota.setProductCode(this.txtProductCode.getText().trim());
            nota.setProductDescription(this.txtProductDesc.getText().trim());

            try {
               nota.setProductQty((double)Integer.valueOf(this.txtProductQty.getText()));
            } catch (Exception var5) {
            }

            try {
               nota.setProductPrice(Double.valueOf(this.txtProductPrice.getText()));
            } catch (Exception var4) {
            }

            try {
               Vat vat = this.getOrderService().getVatByName(this.comboProductVat.getText());
               nota.setProductVatValue(vat.getValue());
            } catch (Exception var3) {
            }

            nota.setCashier(this.getCashier());
            nota.setCashNumber(this.getWorkstationConfig().getCashNumber());
            this.getOrderService().saveNotaDeCredito(nota);
            if (this.btnCrearComprobanteAfip.getSelection()) {
               this.openNotaDeCreditoAfip(nota);
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }

         this.close();
      }

   }

   private void openNotaDeCreditoAfip(NotaDeCredito notaDeCredito) {
      if (!this.getWorkstationConfig().isValidCodFactElect()) {
         this.alert("El Código de Factura Electrónica no es válido");
      } else if (!this.getAppConfig().isMonotributo() && !this.getAppConfig().isResponsableInscripto()) {
         this.alert("Verifique la condición de IVA en el menú Archivo->Configuración->General");
      } else {
         AddNewNotaDeCreditoAfip dialog = new AddNewNotaDeCreditoAfip(this.getShell());
         dialog.setNotaDeCredito(notaDeCredito);
         dialog.open();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      Integer nroFactura;
      if (!"".equals(this.txtCbteAsocPtoVta.getText().trim())) {
         nroFactura = this.getIntegerValueFromText(this.txtCbteAsocPtoVta);
         if (nroFactura == null) {
            this.alert("El nro. de Pto. Vta. ingresado no es válido");
            valid = false;
         }
      }

      if (valid && !"".equals(this.txtCbteAsocNro.getText().trim())) {
         nroFactura = this.getIntegerValueFromText(this.txtCbteAsocNro);
         if (nroFactura == null) {
            this.alert("El nro. del Comprobante Asociado no es válido");
            valid = false;
         }
      }

      if (valid && "".equals(this.txtProductDesc.getText().trim())) {
         this.alert("Ingresa la descripción");
         valid = false;
      }

      if (valid) {
         if ("".equals(this.txtProductPrice.getText().trim())) {
            this.alert("Ingresa el precio");
            valid = false;
         } else {
            Double price = this.getDoubleValueFromText(this.txtProductPrice);
            if (price == null || price <= 0.0) {
               this.alert("El precio ingresado no es válido");
               valid = false;
            }
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nueva nota de crédito");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      Button btnGuardar = this.createButton(parent, 0, "Guardar", false);
      btnGuardar.setText("Guardar");
      Button button_1 = this.createButton(parent, 1, "Cancelar", false);
      button_1.setText("Cancelar");
   }

   protected Point getInitialSize() {
      return new Point(555, 547);
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

   public Employee getCashier() {
      return this.cashier;
   }

   public void setCashier(Employee cashier) {
      this.cashier = cashier;
   }

   public Customer getCustomer() {
      return this.customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
}
