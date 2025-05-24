package com.facilvirtual.fvstoresdesk.ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AdvancedSettings extends AbstractFVApplicationWindow {
   public AdvancedSettings() {
      super((Shell)null);
      this.createActions();
      this.addToolBar(8388672);
      this.addMenuBar();
      this.addStatusLine();
   }
   @Override
   protected Control createContents(Composite parent) {
      Composite container = new Composite(parent, 0);
      Label lblVentas = new Label(container, 0);
      lblVentas.setBounds(10, 10, 143, 13);
      lblVentas.setText("Configuración Avanzada");
      return container;
   }

   private void createActions() {
   }
   @Override
   protected MenuManager createMenuManager() {
      MenuManager menuManager = new MenuManager("menu");
      return menuManager;
   }
   @Override
   protected ToolBarManager createToolBarManager(int style) {
      ToolBarManager toolBarManager = new ToolBarManager(style);
      return toolBarManager;
   }
   @Override
   protected StatusLineManager createStatusLineManager() {
      StatusLineManager statusLineManager = new StatusLineManager();
      return statusLineManager;
   }

   @SuppressWarnings("CallToPrintStackTrace")
   public static void main(String[] args) {
      try {
         AdvancedSettings window = new AdvancedSettings();
         window.setBlockOnOpen(true);
         window.open();
         Display.getCurrent().dispose();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }
   @Override
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText("New Application");
   }
   @Override
   protected Point getInitialSize() {
      return new Point(800, 470);
   }
}
