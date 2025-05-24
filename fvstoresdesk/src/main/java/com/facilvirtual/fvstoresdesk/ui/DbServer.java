package com.facilvirtual.fvstoresdesk.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DbServer extends AbstractFVApplicationWindow {
   public DbServer() {
      super((Shell)null);
      this.setShellStyle(128);
      this.createActions();
      this.addMenuBar();
   }

   protected Control createContents(Composite parent) {
      parent.getShell().setSize(800, 600);
      Composite container = new Composite(parent, 0);
      Button btnAltaCategorias = new Button(container, 0);
      //btnAltaCategorias.addSelectionListener(new 1(this));
      btnAltaCategorias.setText("Alta Rubros Supermercados");
      btnAltaCategorias.setBounds(254, 252, 165, 41);
      Button btnNewButton = new Button(container, 0);
      //btnNewButton.addSelectionListener(new 2(this));
      btnNewButton.setBounds(254, 299, 165, 41);
      btnNewButton.setText("Cargar artículos Supermercados");
      Button btnAltaTarjetas = new Button(container, 0);
      btnAltaTarjetas.setText("Alta Tarjetas");
      btnAltaTarjetas.setBounds(30, 64, 165, 41);
      //btnAltaTarjetas.addSelectionListener(new 3(this));
      Button btnAltaCondicionesIva = new Button(container, 0);
      btnAltaCondicionesIva.setText("Alta Condiciones IVA");
      btnAltaCondicionesIva.setBounds(30, 111, 165, 41);
      //btnAltaCondicionesIva.addSelectionListener(new 4(this));
      Button btnAltaIva = new Button(container, 0);
      btnAltaIva.setText("Alta IVA");
      btnAltaIva.setBounds(254, 205, 165, 41);
      //btnAltaIva.addSelectionListener(new 5(this));
      Button btnAltaClienteOcasional = new Button(container, 0);
      btnAltaClienteOcasional.setText("Alta Cliente Ocasional");
      btnAltaClienteOcasional.setBounds(30, 205, 165, 41);
      //btnAltaClienteOcasional.addSelectionListener(new 6(this));
      Button btnAltaCajero = new Button(container, 0);
      btnAltaCajero.setText("Alta Cajero 01");
      btnAltaCajero.setBounds(30, 252, 165, 41);
      //btnAltaCajero.addSelectionListener(new 7(this));
      Button btnLoadAppConfig = new Button(container, 0);
      btnLoadAppConfig.setText("Alta Configuración");
      btnLoadAppConfig.setBounds(30, 299, 165, 41);
      //btnLoadAppConfig.addSelectionListener(new 8(this));
      Button btnNewButton_1 = new Button(container, 0);
      btnNewButton_1.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
      btnNewButton_1.setBounds(254, 10, 165, 77);
      btnNewButton_1.setText("Instalar todo");
      Button btnAltaTiposDe = new Button(container, 0);
      //btnAltaTiposDe.addSelectionListener(new 9(this));
      btnAltaTiposDe.setBounds(30, 10, 179, 41);
      btnAltaTiposDe.setText("Alta Tipos de Comprobantes");
      //btnNewButton_1.addSelectionListener(new 10(this));
      return container;
   }//TODO:Arreglar

   private void importProductsFromExcel() {
      ImportProductsFromExcel dialog = new ImportProductsFromExcel(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         try {
            ImportProductsFromExcelProcessor processor = new ImportProductsFromExcelProcessor(this.getShell());
            processor.setBlockOnOpen(true);
            processor.setSettings(dialog);
            processor.open();
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

   }

   private void createActions() {
   }

   protected MenuManager createMenuManager() {
      MenuManager menuManager = new MenuManager("menu");
      MenuManager fileMenu = new MenuManager("&Archivo");
      menuManager.add(fileMenu);
      //Action exitAction = new 11(this, "&Salir");
      //fileMenu.add(exitAction);
      menuManager.updateAll(false);
      return menuManager;
   }

   public static void main(String[] args) {
      try {
         new ClassPathXmlApplicationContext("applicationContext.xml");
         DbServer window = new DbServer();
         window.setBlockOnOpen(true);
         window.open();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   protected void configureShell(Shell shell) {
      super.configureShell(shell);
      this.initTitle(shell, "FácilVirtual - Instalador");
     // shell.addListener(21, new 12(this));
   }

   protected Point getInitialSize() {
      return new Point(797, 529);
   }
}
