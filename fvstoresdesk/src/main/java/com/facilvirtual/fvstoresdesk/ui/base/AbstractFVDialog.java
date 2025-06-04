package com.facilvirtual.fvstoresdesk.ui.base;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.jface.dialogs.Dialog;
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
import com.facilvirtual.fvstoresdesk.model.PriceList;
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

public abstract class AbstractFVDialog extends Dialog {
   protected Color themeBack;
   protected Color themeBack02;
   protected Color themeText;
   protected Color themeHeaderBack;
   protected Color themeRowOdd;
   protected Color themeRowEven;
   protected Color themeTableBack;
   protected Color themeTableText;
   protected Color themeInputReadOnlyBack;
   protected Color themeInputReadOnlyText;
   private String title = "";
   private String action = "";
   private String msgPrivilegeRequired = "No tienes el permiso necesario para realizar esta acción";

   public AbstractFVDialog(Shell parentShell) {
      super(parentShell);
   }

   protected void initTheme() {
      this.themeBack = SWTResourceManager.getColor(233, 237, 234);
      this.themeBack02 = SWTResourceManager.getColor(246, 248, 247);
      this.themeText = SWTResourceManager.getColor(62, 133, 37);
      this.themeHeaderBack = SWTResourceManager.getColor(62, 133, 37);
      this.themeTableBack = SWTResourceManager.getColor(255, 255, 255);
      this.themeTableText = SWTResourceManager.getColor(21, 25, 36);
      this.themeRowOdd = SWTResourceManager.getColor(255, 255, 255);
      this.themeRowEven = SWTResourceManager.getColor(246, 248, 247);
      this.themeInputReadOnlyBack = SWTResourceManager.getColor(233, 237, 234);
      this.themeInputReadOnlyText = SWTResourceManager.getColor(21, 25, 36);
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

   public OrderService getOrderService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (OrderService)context.getBean("orderService");
   }

   public ProductService getProductService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (ProductService)context.getBean("productService");
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

   protected AppConfig getAppConfig() {
      return this.getAppConfigService().getAppConfig();
   }

   protected WorkstationConfig getWorkstationConfig() {
      return this.getAppConfigService().getCurrentWorkstationConfig();
   }

   public void clearContainer(Composite container) {
      Control[] var5;
      int var4 = (var5 = container.getChildren()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Control control = var5[var3];
         control.dispose();
      }

   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   protected void format2Decimals(Text text) {
      try {
         String textStr = text.getText().replaceAll(",", ".");
         double textValue = !"".equals(textStr) ? Double.parseDouble(textStr) : 0.0;
         DecimalFormat df = new DecimalFormat("0.00");
         String s = df.format(textValue);
         s = s.replaceAll("\\.", ",");
         text.setText(s);
      } catch (Exception var7) {
      }

   }

   protected String format2Decimals(double value) {
      String str = "";

      try {
         DecimalFormat df = new DecimalFormat("0.00");
         str = df.format(value);
         str = str.replaceAll("\\.", ",");
      } catch (Exception var5) {
      }

      return str;
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

   protected Date getDateFromText(Text text) {
      Date date;
      try {
         if (text.getText().trim().length() != 10) {
            return null;
         }

         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
         date = formatter.parse(text.getText().trim());
      } catch (Exception var4) {
         date = null;
      }

      return date;
   }

   protected Date buildDateFromInput(DateTime datepicker) {
      try {
         GregorianCalendar calendar = new GregorianCalendar(datepicker.getYear(), datepicker.getMonth(), datepicker.getDay());
         return calendar.getTime();
      } catch (Exception var3) {
         return null;
      }
   }

   protected void initDatepickerForDate(DateTime datepicker, Date aDate) {
      try {
         GregorianCalendar calendar = new GregorianCalendar();
         calendar.setTime(aDate);
         datepicker.setDay(calendar.get(7));
         datepicker.setMonth(calendar.get(2));
         datepicker.setYear(calendar.get(1));
      } catch (Exception var4) {
      }

   }

   public String getMsgPrivilegeRequired() {
      return this.msgPrivilegeRequired;
   }

   public void setMsgPrivilegeRequired(String msgPrivilegeRequired) {
      this.msgPrivilegeRequired = msgPrivilegeRequired;
   }

   public PriceList getDefaultPriceList() {
      return this.getAppConfigService().getDefaultPriceList();
   }
}
