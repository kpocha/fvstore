/*     */ package com.facilvirtual.fvstoresdesk.ui.report;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.List;

/*     */ import org.apache.commons.lang3.time.DateUtils;
/*     */ import org.eclipse.swt.graphics.Point;
/*     */ import org.eclipse.swt.layout.FormAttachment;
/*     */ import org.eclipse.swt.layout.FormData;
/*     */ import org.eclipse.swt.layout.FormLayout;
            import org.eclipse.swt.widgets.Composite;/*     */
            import org.eclipse.swt.widgets.Control;
/*     */ import org.eclipse.swt.widgets.DateTime;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Layout;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.eclipse.wb.swt.SWTResourceManager;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;

/*     */ import com.facilvirtual.fvstoresdesk.model.Order;
/*     */ import com.facilvirtual.fvstoresdesk.ui.AbstractFVDialog;
/*     */ import com.facilvirtual.fvstoresdesk.util.FVDateUtils;
/*     */ 
/*     */ 
/*     */ public class ReportSalesByDateRange
/*     */   extends AbstractFVDialog
/*     */ {
/*  26 */   private String action = "";
/*     */   private List<Order> orders;
/*     */   private DateTime startDatepicker;
/*     */   private DateTime endDatepicker;
/*     */   private Date startDate;
/*     */   private Date endDate;
/*  32 */   private static final Logger logger = LoggerFactory.getLogger("ReportSalesByDateRange");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReportSalesByDateRange(Shell parentShell) {
/*  39 */     super(parentShell);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Override
/*     */   protected Control createDialogArea(Composite parent) {
/*  49 */     Composite container = (Composite)super.createDialogArea(parent);
/*     */     
/*  51 */     FormLayout fl_container = new FormLayout();
/*  52 */     container.setLayout((Layout)fl_container);
/*     */     
/*  54 */     Label lblIngresaLaFecha = new Label(container, 16777216);
/*  55 */     lblIngresaLaFecha.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
/*  56 */     FormData fd_lblIngresaLaFecha = new FormData();
/*  57 */     fd_lblIngresaLaFecha.right = new FormAttachment(0, 435);
/*  58 */     fd_lblIngresaLaFecha.top = new FormAttachment(0, 36);
/*  59 */     fd_lblIngresaLaFecha.left = new FormAttachment(0, 48);
/*  60 */     lblIngresaLaFecha.setLayoutData(fd_lblIngresaLaFecha);
/*  61 */     lblIngresaLaFecha.setText("Selecciona el período de ventas");
/*     */     
/*  63 */     Label lblDesde = new Label(container, 0);
/*  64 */     lblDesde.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
/*  65 */     FormData fd_lblDesde = new FormData();
/*  66 */     fd_lblDesde.top = new FormAttachment((Control)lblIngresaLaFecha, 50);
/*  67 */     lblDesde.setLayoutData(fd_lblDesde);
/*  68 */     lblDesde.setText("Desde:");
/*     */     
/*  70 */     Label lblHasta = new Label(container, 0);
/*  71 */     lblHasta.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
/*  72 */     lblHasta.setText("Hasta:");
/*  73 */     FormData fd_lblHasta = new FormData();
/*  74 */     fd_lblHasta.top = new FormAttachment((Control)lblDesde, 0, 128);
/*  75 */     lblHasta.setLayoutData(fd_lblHasta);
/*     */     
/*  77 */     this.endDatepicker = new DateTime(container, 2048);
/*  78 */     this.endDatepicker.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
/*  79 */     FormData fd_endDatepicker = new FormData();
/*  80 */     fd_endDatepicker.left = new FormAttachment((Control)lblHasta, 6);
/*  81 */     this.endDatepicker.setLayoutData(fd_endDatepicker);
/*     */     
/*  83 */     this.startDatepicker = new DateTime(container, 2048);
/*  84 */     fd_endDatepicker.top = new FormAttachment((Control)this.startDatepicker, 0, 128);
/*  85 */     fd_lblHasta.left = new FormAttachment((Control)this.startDatepicker, 18);
/*  86 */     fd_lblDesde.right = new FormAttachment(100, -396);
/*  87 */     this.startDatepicker.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
/*  88 */     FormData fd_startDatepicker = new FormData();
/*  89 */     fd_startDatepicker.bottom = new FormAttachment((Control)lblDesde, 0, 1024);
/*  90 */     fd_startDatepicker.left = new FormAttachment((Control)lblDesde, 6);
/*  91 */     this.startDatepicker.setLayoutData(fd_startDatepicker);
/*     */     
/*  93 */     return (Control)container;
/*     */   }
/*     */   
/*     */   public Date getStartDate() {
/*  97 */     return this.startDate;
/*     */   }
/*     */   
/*     */   public void setStartDate(Date startDate) {
/* 101 */     this.startDate = startDate;
/*     */   }
/*     */   
/*     */   public Date getEndDate() {
/* 105 */     return this.endDate;
/*     */   }
/*     */   
/*     */   public void setEndDate(Date endDate) {
/* 109 */     this.endDate = endDate;
/*     */   }
/*     */ 
/*     */   
/*     */   private void createLabels() {
/* 114 */     if (validateFields()) {
/* 115 */       setAction("OK");
/* 116 */       close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateFields() {
/* 123 */     boolean valid = true;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 128 */       this.startDate = buildDateFromInput(this.startDatepicker);
/*     */ 
/*     */       
/* 131 */       this.endDate = DateUtils.addDays(buildDateFromInput(this.endDatepicker), 1);
/* 132 */       this.endDate = DateUtils.addMilliseconds(this.endDate, -1);
/*     */     }
/* 134 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     int days = FVDateUtils.daysBetween(this.startDate, this.endDate) + 1;
/*     */     
/* 141 */     if (days > 31) {
/* 142 */       alert("El rango de fechas no puede ser mayor a 31 días");
/* 143 */       valid = false;
/*     */     } 
/*     */ 
/*     */     
/* 147 */     if (valid) {
/*     */ 
/*     */       
/*     */       try {
/* 151 */         setOrders(getOrderService().getCompletedOrdersForDateRange(this.startDate, this.endDate));
/*     */       }
/* 154 */       catch (Exception e) {
/*     */         
/* 156 */         logger.error("Error al obtener ventas completadas por fecha");
/* 157 */         logger.error(e.getMessage());
/* 158 */        // logger.error(e);
/*     */       } 
/*     */ 
/*     */       
/* 162 */       if (this.orders == null || this.orders.size() == 0) {
/* 163 */         alert("No se encontraron ventas para ese período");
/* 164 */         valid = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 169 */     return valid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Override protected void configureShell(Shell newShell) {
/* 179 */     super.configureShell(newShell);
/* 180 */     initTitle(newShell, "Informe de Ventas por fecha");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Override
/*     */   protected void buttonPressed(int buttonId) {
/* 187 */     if (buttonId == 0) {
/* 188 */       createLabels();
/*     */     } else {
/* 190 */       close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Override
/*     */   protected void createButtonsForButtonBar(Composite parent) {
/* 200 */     createButton(parent, 0, "Aceptar", false);
/* 201 */     createButton(parent, 1, "Cancelar", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Override
/*     */   protected Point getInitialSize() {
/* 209 */     return new Point(532, 306);
/*     */   }
/*     */ 
/*     */   @Override
/*     */   public String getAction() {
/* 214 */     return this.action;
/*     */   }
/*     */   @Override
/*     */   public void setAction(String action) {
/* 218 */     this.action = action;
/*     */   }
/*     */   
/*     */   public List<Order> getOrders() {
/* 222 */     return this.orders;
/*     */   }
/*     */   
/*     */   public void setOrders(List<Order> orders) {
/* 226 */     this.orders = orders;
/*     */   }
/*     */ }
