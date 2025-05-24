package com.facilvirtual.fvstoresdesk.test;

import com.facilvirtual.fvstoresdesk.service.ProductService;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AddProductTest extends ApplicationWindow {
   private ProductService productService;

   public AddProductTest() {
      super((Shell)null);
      this.createActions();
      this.addToolBar(8388672);
      this.addMenuBar();
      this.addStatusLine();
   }
   @Override
   protected Control createContents(Composite parent) {
      Composite container = new Composite(parent, 0);
      Label lblNewLabel = new Label(container, 0);
      lblNewLabel.setBounds(78, 38, 49, 13);
      lblNewLabel.setText("New Label");
      Label lblNewLabel_1 = new Label(container, 0);
      lblNewLabel_1.setBounds(53, 89, 49, 13);
      lblNewLabel_1.setText("New Label");
      Button btnNewButton = new Button(container, 0);
      btnNewButton.setToolTipText("Guarda un artículo en la Base de Datos");
      btnNewButton.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      //btnNewButton.addSelectionListener(new 1(this));
      btnNewButton.setBounds(124, 165, 178, 45);
      btnNewButton.setText("Guardar artículo");
      return container;
   }

   private void createActions() {
   }

   protected StatusLineManager createStatusLineManager() {
      StatusLineManager statusLineManager = new StatusLineManager();
      return statusLineManager;
   }

   public static void main(String[] args) {
      try {
         new ClassPathXmlApplicationContext("applicationContext.xml");
         AddProductTest window = new AddProductTest();
         window.setBlockOnOpen(true);
         window.open();
         Display.getCurrent().dispose();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText("New Application");
   }

   protected Point getInitialSize() {
      return new Point(450, 300);
   }

   public ProductService getProductService() {
      return this.productService;
   }

   @Autowired
   public void setProductService(ProductService productService) {
      this.productService = productService;
   }
}
