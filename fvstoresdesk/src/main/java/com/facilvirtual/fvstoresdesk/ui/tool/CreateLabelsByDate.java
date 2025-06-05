package com.facilvirtual.fvstoresdesk.ui.tool;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class CreateLabelsByDate extends AbstractFVDialog {
   private String action = "";
   private Combo comboPriceLists;
   private PriceList priceList = null;
   private List<Product> products;
   private DateTime startDateSelect;
   private DateTime endDateSelect;
   private Date startDate;
   private Date endDate;

   public CreateLabelsByDate(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngresaLaFecha = new Label(container, 0);
      lblIngresaLaFecha.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
      FormData fd_lblIngresaLaFecha = new FormData();
      fd_lblIngresaLaFecha.top = new FormAttachment(0, 32);
      fd_lblIngresaLaFecha.right = new FormAttachment(100, -132);
      lblIngresaLaFecha.setLayoutData(fd_lblIngresaLaFecha);
      lblIngresaLaFecha.setText("Etiquetas de precios por fecha");
      Label lblDesde = new Label(container, 0);
      lblDesde.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblDesde = new FormData();
      fd_lblDesde.left = new FormAttachment(0, 121);
      lblDesde.setLayoutData(fd_lblDesde);
      lblDesde.setText("Desde:");
      Label lblHasta = new Label(container, 0);
      lblHasta.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblHasta.setText("Hasta:");
      FormData fd_lblHasta = new FormData();
      fd_lblHasta.bottom = new FormAttachment(100, -94);
      fd_lblHasta.top = new FormAttachment(lblDesde, 0, 128);
      lblHasta.setLayoutData(fd_lblHasta);
      this.endDateSelect = new DateTime(container, 2048);
      this.endDateSelect.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_endDate = new FormData();
      fd_endDate.bottom = new FormAttachment(100, -94);
      fd_endDate.left = new FormAttachment(lblHasta, 6);
      this.endDateSelect.setLayoutData(fd_endDate);
      this.startDateSelect = new DateTime(container, 2048);
      fd_endDate.top = new FormAttachment(0, 157);
      fd_lblHasta.left = new FormAttachment(this.startDateSelect, 18);
      this.startDateSelect.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_startDate = new FormData();
      fd_startDate.bottom = new FormAttachment(100, -94);
      fd_startDate.left = new FormAttachment(lblDesde, 6);
      this.startDateSelect.setLayoutData(fd_startDate);
      Label label = new Label(container, 0);
      fd_lblDesde.top = new FormAttachment(label, 44);
      label.setText("Lista");
      label.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_label = new FormData();
      fd_label.top = new FormAttachment(lblIngresaLaFecha, 50);
      fd_label.bottom = new FormAttachment(100, -154);
      fd_label.left = new FormAttachment(lblDesde, 0, 16384);
      label.setLayoutData(fd_label);
      this.comboPriceLists = new Combo(container, 8);
      fd_startDate.top = new FormAttachment(0, 157);
      FormData fd_comboPriceLists = new FormData();
      fd_comboPriceLists.top = new FormAttachment(lblIngresaLaFecha, 45);
      fd_comboPriceLists.bottom = new FormAttachment(this.endDateSelect, -34);
      fd_comboPriceLists.right = new FormAttachment(this.startDateSelect, 180);
      fd_comboPriceLists.left = new FormAttachment(this.startDateSelect, 0, 16384);
      this.comboPriceLists.setLayoutData(fd_comboPriceLists);
      List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
      Iterator var17 = priceLists.iterator();

      while(var17.hasNext()) {
         PriceList priceList = (PriceList)var17.next();
         this.comboPriceLists.add(priceList.getName());
      }

      this.comboPriceLists.select(0);
      return container;
   }

   public Date getStartDate() {
      return this.startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public Date getEndDate() {
      return this.endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   private void createLabels() {
      PriceList priceList = this.getAppConfigService().getPriceListByName(this.comboPriceLists.getText());
      this.setPriceList(priceList);
      if (this.validateFields()) {
         this.setAction("OK");
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      try {
         this.startDate = this.buildDateFromInput(this.startDateSelect);
         this.endDate = DateUtils.addDays(this.buildDateFromInput(this.endDateSelect), 1);
         this.endDate = DateUtils.addMilliseconds(this.endDate, -1);
         this.setProducts(this.getProductService().getProductsWithUpdatedPricesInDateRange(this.startDate, this.endDate, this.getPriceList()));
      } catch (Exception var3) {
      }

      if (this.products == null || this.products.size() == 0) {
         this.alert("No se encontraron artículos para ese período");
         valid = false;
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Generar etiquetas");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.createLabels();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(532, 355);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public List<Product> getProducts() {
      return this.products;
   }

   public void setProducts(List<Product> products) {
      this.products = products;
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }
}
