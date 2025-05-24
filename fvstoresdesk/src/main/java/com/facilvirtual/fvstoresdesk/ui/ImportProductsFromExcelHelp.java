package com.facilvirtual.fvstoresdesk.ui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

public class ImportProductsFromExcelHelp extends AbstractFVDialog {
   private String action = "";
   private String filename = "";
   private boolean updateProducts = true;
   private Table table;

   public ImportProductsFromExcelHelp(Shell parentShell) {
      super(parentShell);
   }

   public String getFilename() {
      return this.filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

   public boolean isUpdateProducts() {
      return this.updateProducts;
   }

   public void setUpdateProducts(boolean updateProducts) {
      this.updateProducts = updateProducts;
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 0);
      lblTitle.setAlignment(16777216);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.top = new FormAttachment(0, 32);
      fd_lblTitle.left = new FormAttachment(0, 68);
      fd_lblTitle.right = new FormAttachment(0, 595);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Importar artículos desde Excel");
      Label lblAyuda = new Label(container, 0);
      FormData fd_lblAyuda = new FormData();
      fd_lblAyuda.top = new FormAttachment(0, 10);
      fd_lblAyuda.left = new FormAttachment(0, 10);
      lblAyuda.setLayoutData(fd_lblAyuda);
      lblAyuda.setText("Ayuda");
      Label label = new Label(container, 0);
      FormData fd_label = new FormData();
      fd_label.top = new FormAttachment(lblAyuda, 62);
      fd_label.left = new FormAttachment(lblAyuda, 0, 16384);
      label.setLayoutData(fd_label);
      Label lblElArchivoPara = new Label(container, 0);
      FormData fd_lblElArchivoPara = new FormData();
      fd_lblElArchivoPara.right = new FormAttachment(100, -189);
      fd_lblElArchivoPara.left = new FormAttachment(0, 10);
      fd_lblElArchivoPara.top = new FormAttachment(label, 0, 128);
      lblElArchivoPara.setLayoutData(fd_lblElArchivoPara);
      lblElArchivoPara.setText("El archivo para importar los artículos debe tener el siguiente formato:");
      Label lblSeAsumeQue = new Label(container, 0);
      lblSeAsumeQue.setText("Se asume que el archivo contiene en la primera fila los encabezados \r\ny se importan los artículos desde la fila nro. 2.\r\n\r\nLas columnas marcadas con [ ] son opcionales.\r\n\r\nEl archivo debe tener extensión .xlsx");
      FormData fd_lblSeAsumeQue = new FormData();
      fd_lblSeAsumeQue.bottom = new FormAttachment(100, -23);
      fd_lblSeAsumeQue.left = new FormAttachment(lblAyuda, 0, 16384);
      fd_lblSeAsumeQue.right = new FormAttachment(0, 475);
      lblSeAsumeQue.setLayoutData(fd_lblSeAsumeQue);
      this.table = new Table(container, 67584);
      fd_lblSeAsumeQue.top = new FormAttachment(this.table, 21);
      FormData fd_table = new FormData();
      fd_table.bottom = new FormAttachment(100, -140);
      fd_table.top = new FormAttachment(lblElArchivoPara, 19);
      fd_table.left = new FormAttachment(0, 10);
      fd_table.right = new FormAttachment(0, 671);
      this.table.setLayoutData(fd_table);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnCdigo = new TableColumn(this.table, 0);
      tblclmnCdigo.setWidth(52);
      tblclmnCdigo.setText("Código");
      TableColumn tblclmnDescripcin = new TableColumn(this.table, 0);
      tblclmnDescripcin.setWidth(76);
      tblclmnDescripcin.setText("Descripción");
      TableColumn tblclmnprecioVenta = new TableColumn(this.table, 0);
      tblclmnprecioVenta.setWidth(57);
      tblclmnprecioVenta.setText("[Rubro]");
      TableColumn tblclmnrubro = new TableColumn(this.table, 0);
      tblclmnrubro.setWidth(106);
      tblclmnrubro.setText("[Precio de Venta]");
      TableColumn tblclmnprecioDeCosto = new TableColumn(this.table, 0);
      tblclmnprecioDeCosto.setWidth(107);
      tblclmnprecioDeCosto.setText("[Precio de Costo]");
      TableColumn tblclmnstock = new TableColumn(this.table, 0);
      tblclmnstock.setWidth(52);
      tblclmnstock.setText("[Stock]");
      TableColumn tblclmnstockMnimo = new TableColumn(this.table, 0);
      tblclmnstockMnimo.setWidth(99);
      tblclmnstockMnimo.setText("[Stock mínimo]");
      TableColumn tblclmnstockMximo = new TableColumn(this.table, 0);
      tblclmnstockMximo.setWidth(98);
      tblclmnstockMximo.setText("[Stock máximo]");
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Ayuda: Importar artículos desde Excel");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 1, "Cancelar", false);
      button.setText("Cerrar");
   }

   protected Point getInitialSize() {
      return new Point(687, 414);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }
}
