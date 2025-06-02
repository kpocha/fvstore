package com.facilvirtual.fvstoresdesk.ui.report;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;
import com.facilvirtual.fvstoresdesk.util.FVDateUtils;

public class ReportSalesByDate extends AbstractFVDialog {
   private String action = "";
   private List<Order> orders;
   private DateTime startDatepicker;
   private DateTime endDatepicker;
   private Date startDate;
   private Date endDate;
   Button btnConCAE;
   private boolean conCAE = true;
   private static Logger logger = LoggerFactory.getLogger("ReportSalesByDateRange");

   public ReportSalesByDate(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblIngresaLaFecha = new Label(container, 16777216);
      lblIngresaLaFecha.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
      FormData fd_lblIngresaLaFecha = new FormData();
      fd_lblIngresaLaFecha.right = new FormAttachment(0, 435);
      fd_lblIngresaLaFecha.top = new FormAttachment(0, 36);
      fd_lblIngresaLaFecha.left = new FormAttachment(0, 48);
      lblIngresaLaFecha.setLayoutData(fd_lblIngresaLaFecha);
      lblIngresaLaFecha.setText("Selecciona el período de ventas");
      Label lblDesde = new Label(container, 0);
      lblDesde.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblDesde = new FormData();
      fd_lblDesde.top = new FormAttachment(lblIngresaLaFecha, 50);
      lblDesde.setLayoutData(fd_lblDesde);
      lblDesde.setText("Desde:");
      Label lblHasta = new Label(container, 0);
      lblHasta.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      lblHasta.setText("Hasta:");
      FormData fd_lblHasta = new FormData();
      fd_lblHasta.top = new FormAttachment(lblDesde, 0, 128);
      lblHasta.setLayoutData(fd_lblHasta);
      this.endDatepicker = new DateTime(container, 2048);
      this.endDatepicker.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_endDatepicker = new FormData();
      fd_endDatepicker.left = new FormAttachment(lblHasta, 6);
      this.endDatepicker.setLayoutData(fd_endDatepicker);
      this.startDatepicker = new DateTime(container, 2048);
      fd_endDatepicker.top = new FormAttachment(this.startDatepicker, 0, 128);
      fd_lblHasta.left = new FormAttachment(this.startDatepicker, 18);
      fd_lblDesde.right = new FormAttachment(100, -396);
      this.startDatepicker.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_startDatepicker = new FormData();
      fd_startDatepicker.bottom = new FormAttachment(lblDesde, 0, 1024);
      fd_startDatepicker.left = new FormAttachment(lblDesde, 6);
      this.startDatepicker.setLayoutData(fd_startDatepicker);
      this.btnConCAE = new Button(container, 32);
      this.btnConCAE.setSelection(true);
      FormData fd_btnConCAE = new FormData();
      fd_btnConCAE.top = new FormAttachment(this.startDatepicker, 36);
      fd_btnConCAE.left = new FormAttachment(this.startDatepicker, 0, 16384);
      this.btnConCAE.setLayoutData(fd_btnConCAE);
      this.btnConCAE.setText("Sólo ventas con CAE");
      return container;
   }

   public boolean isConCAE() {
      return this.conCAE;
   }

   public boolean getConCAE() {
      return this.conCAE;
   }

   public void setConCAE(boolean conCAE) {
      this.conCAE = conCAE;
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

   private void createReport() {
      if (this.validateFields()) {
         this.setAction("OK");
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;

      try {
         this.startDate = this.buildDateFromInput(this.startDatepicker);
         this.endDate = DateUtils.addDays(this.buildDateFromInput(this.endDatepicker), 1);
         this.endDate = DateUtils.addMilliseconds(this.endDate, -1);
      } catch (Exception var5) {
      }

      int days = FVDateUtils.daysBetween(this.startDate, this.endDate) + 1;
      if (days > 31) {
         this.alert("El rango de fechas no puede ser mayor a 31 días");
         valid = false;
      }

      this.conCAE = this.btnConCAE.getSelection();
      if (valid) {
         try {
            if (this.conCAE) {
               this.setOrders(this.getOrderService().getFiscalOrdersForDateRange(this.startDate, this.endDate));
            } else {
               this.setOrders(this.getOrderService().getCompletedOrdersForDateRange(this.startDate, this.endDate));
            }
         } catch (Exception var4) {
            logger.error("Error al obtener ventas completadas por fecha");
            logger.error(var4.getMessage());
            //logger.error(var4);
         }

         if (this.orders == null || this.orders.size() == 0) {
            this.alert("No se encontraron ventas para ese período");
            valid = false;
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Informe de Ventas por fecha");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.createReport();
      } else {
         this.close();
      }

   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      this.createButton(parent, 0, "Aceptar", false);
      this.createButton(parent, 1, "Cancelar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(532, 306);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public List<Order> getOrders() {
      return this.orders;
   }

   public void setOrders(List<Order> orders) {
      this.orders = orders;
   }
}
