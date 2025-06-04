package com.facilvirtual.fvstoresdesk.ui.base;

import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.context.ApplicationContext;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import com.facilvirtual.fvstoresdesk.service.AccountService;
import com.facilvirtual.fvstoresdesk.service.AppConfigService;
import com.facilvirtual.fvstoresdesk.service.ApplicationContextProvider;
import com.facilvirtual.fvstoresdesk.service.BudgetService;
import com.facilvirtual.fvstoresdesk.service.CashService;
import com.facilvirtual.fvstoresdesk.service.CustomerService;
import com.facilvirtual.fvstoresdesk.service.OrderService;
import com.facilvirtual.fvstoresdesk.service.ProductService;
import com.facilvirtual.fvstoresdesk.service.PurchaseService;
import com.facilvirtual.fvstoresdesk.service.SupplierService;

public abstract class AbstractFVApplicationWindow extends ApplicationWindow {
   protected Color themeBack;
   protected Color themeText;
   protected Color themeHeaderBack;
   protected Color themeRowOdd;
   protected Color themeRowEven;
   protected Color themeTableBack;
   protected Color themeInputReadOnly;
   private String msgFunctionNotAvailableInTrial = "Esta función no está disponible en la versión de evaluación.";
   private String msgPrivilegeRequired = "No tienes el permiso necesario para realizar esta acción";

   protected void initTheme() {
      this.themeBack = SWTResourceManager.getColor(233, 237, 234);
      this.themeText = SWTResourceManager.getColor(63, 63, 63);
      this.themeHeaderBack = SWTResourceManager.getColor(63, 133, 37);
      this.themeRowOdd = SWTResourceManager.getColor(235, 235, 235);
      this.themeRowEven = SWTResourceManager.getColor(245, 245, 245);
      this.themeTableBack = SWTResourceManager.getColor(245, 245, 245);
      this.themeInputReadOnly = SWTResourceManager.getColor(245, 245, 245);
   }

   public AbstractFVApplicationWindow(Shell parentShell) {
      super(parentShell);
   }

   public void initTitle(Shell shell, String title) {
      shell.setText(title);
      Image small = new Image(Display.getCurrent(), this.getImagesDir() + "icon_facilvirtual.gif");
      shell.setImage(small);
   }

   public String getImagesDir() {
      return "C:/facilvirtual/images/";
   }

   public void alert(String message) {
      MessageBox dialog = new MessageBox(this.getShell());
      dialog.setText("Aviso");
      dialog.setMessage(message);
      dialog.open();
   }

   public SupplierService getSupplierService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (SupplierService)context.getBean("supplierService");
   }

   public CashService getCashService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (CashService)context.getBean("cashService");
   }

   public PurchaseService getPurchaseService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (PurchaseService)context.getBean("purchaseService");
   }

   public BudgetService getBudgetService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (BudgetService)context.getBean("budgetService");
   }

   public ProductService getProductService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (ProductService)context.getBean("productService");
   }

   public OrderService getOrderService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (OrderService)context.getBean("orderService");
   }

   public AccountService getAccountService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AccountService)context.getBean("accountService");
   }

   public CustomerService getCustomerService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (CustomerService)context.getBean("customerService");
   }

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   public void clearContainer(Composite container) {
      Control[] var5;
      int var4 = (var5 = container.getChildren()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Control control = var5[var3];
         control.dispose();
      }

   }

   protected Date buildDateFromInput(DateTime datepicker) {
      GregorianCalendar calendar = new GregorianCalendar(datepicker.getYear(), datepicker.getMonth(), datepicker.getDay());
      return calendar.getTime();
   }

   protected String getMsgFunctionNotAvailableInTrial() {
      return this.msgFunctionNotAvailableInTrial;
   }

   public void setMsgFunctionNotAvailableInTrial(String msgFunctionNotAvailableInTrial) {
      this.msgFunctionNotAvailableInTrial = msgFunctionNotAvailableInTrial;
   }

   protected AppConfig getAppConfig() {
      return this.getAppConfigService().getAppConfig();
   }

   public void initWindowTitle(Shell shell, WorkstationConfig workstationConfig) {
   }

   protected WorkstationConfig getWorkstationConfig() {
      return this.getAppConfigService().getCurrentWorkstationConfig();
   }

   public String getMsgPrivilegeRequired() {
      return this.msgPrivilegeRequired;
   }

   public void setMsgPrivilegeRequired(String msgPrivilegeRequired) {
      this.msgPrivilegeRequired = msgPrivilegeRequired;
   }

   protected Double getDoubleValueFromText(Text text) {
      Double value;
      try {
         value = Double.valueOf(text.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
         value = null;
      }

      return value;
   }

   protected Integer getIntegerValueFromText(Text text) {
      Integer value;
      try {
         value = Integer.valueOf(text.getText().trim());
      } catch (Exception var4) {
         value = null;
      }

      return value;
   }
}
