package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.Supplier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class EmployeesManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("SalesManager");
   private static EmployeesManager INSTANCE = null;
   private Composite layoutContainer;
   private Composite headerContainer;
   private Composite mainContainer;
   private Table table;
   private List<Supplier> suppliers = new ArrayList();
   private Text txtQuery;
   private Button btnHideDeletedSuppliers;

   public EmployeesManager() {
      super((Shell)null);
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new EmployeesManager();
      }

   }

   public static EmployeesManager getInstance() {
      createInstance();
      return INSTANCE;
   }

   protected Control createContents(Composite parent) {
      this.layoutContainer = new Composite(parent, 0);
      this.layoutContainer.setLayout(new GridLayout(1, false));
      this.headerContainer = new Composite(this.layoutContainer, 0);
      this.headerContainer.setLayout(new GridLayout(8, false));
      this.mainContainer = new Composite(this.layoutContainer, 0);
      this.mainContainer.setLayout(new FillLayout(256));
      this.mainContainer.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.createHeaderContent();
      this.createMainContent();
      this.createFooterContent();
      this.searchSuppliers();
      return this.layoutContainer;
   }

   private void searchSuppliers() {
      this.table.removeAll();
      boolean hideDeletedSuppliers = this.btnHideDeletedSuppliers.getSelection();
      List<Supplier> suppliers = this.getProductService().searchSuppliers(this.txtQuery.getText().trim(), hideDeletedSuppliers, 1000);
      this.setSuppliers(suppliers);
      this.initSuppliers();
   }

   private void initSuppliers() {
      //int colIdx = false;

      int colIdx;
      for(Iterator var3 = this.getSuppliers().iterator(); var3.hasNext(); ++colIdx) {
         Supplier supplier = (Supplier)var3.next();
         colIdx = 0;
         TableItem item = new TableItem(this.table, 0);
         item.setText(colIdx, String.valueOf(supplier.getId()));
         ++colIdx;
         item.setText(colIdx, supplier.getCompanyName());
         ++colIdx;
         item.setText(colIdx, supplier.getCuit());
         ++colIdx;
         item.setText(colIdx, supplier.getVatCondition().getName());
         ++colIdx;
         item.setText(colIdx, supplier.getPhone());
         ++colIdx;
         item.setText(colIdx, supplier.getEmail());
         ++colIdx;
         item.setText(colIdx, supplier.getAddressStreet() + " " + supplier.getAddressNumber());
         ++colIdx;
         item.setText(colIdx, supplier.getStatusToDisplay());
      }

   }

   private void addNewSupplier() {
      AddNewSupplier addNewSupplier = new AddNewSupplier(this.getShell());
      addNewSupplier.open();
      if ("OK".equalsIgnoreCase(addNewSupplier.getAction())) {
         this.txtQuery.setText("");
         this.searchSuppliers();
      }

   }

   private void editSupplier() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un proveedor");
      } else {
         try {
            Long supplierId = Long.parseLong(this.table.getItem(idx).getText(0).trim());
            Supplier supplier = this.getProductService().getSupplier(supplierId);
            EditSupplier dialog = new EditSupplier(this.getShell());
            dialog.setSupplier(supplier);
            dialog.open();
            this.searchSuppliers();
         } catch (Exception var5) {
         }
      }

   }

   private void deleteSupplier() {
      int idx = this.table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un proveedor");
      } else if (FVConfirmDialog.openQuestion(this.getShell(), "Eliminar proveedor", "¿Quieres eliminar el proveedor?")) {
         try {
            Long supplierId = Long.parseLong(this.table.getItem(idx).getText(0).trim());
            this.getProductService().deleteSupplier(supplierId);
            this.searchSuppliers();
         } catch (Exception var3) {
         }
      }

   }

   private void createHeaderContent() {
      Button btnNewSale = new Button(this.headerContainer, 0);
     // btnNewSale.addSelectionListener(new 1(this));
      btnNewSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnNewSale.setText("Nueva proveedor");
      btnNewSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_add.gif"));
      Button btnViewSale = new Button(this.headerContainer, 0);
      //btnViewSale.addSelectionListener(new 2(this));
      btnViewSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnViewSale.setText("Modificar");
      btnViewSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_edit.gif"));
      Button btnCancelSale = new Button(this.headerContainer, 0);
      //btnCancelSale.addSelectionListener(new 3(this));
      btnCancelSale.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      btnCancelSale.setText("Eliminar");
      btnCancelSale.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_delete.gif"));
      Label lblNewLabel = new Label(this.headerContainer, 0);
      GridData gd_lblNewLabel = new GridData(131072, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 20;
      lblNewLabel.setLayoutData(gd_lblNewLabel);
      lblNewLabel.setText("");
      this.txtQuery = new Text(this.headerContainer, 2048);
      this.txtQuery.setFont(SWTResourceManager.getFont("Tahoma", 12, 0));
      GridData gd_txtQuery = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtQuery.widthHint = 125;
      this.txtQuery.setLayoutData(gd_txtQuery);
      //this.txtQuery.addTraverseListener(new 4(this));
      Button btnBuscar = new Button(this.headerContainer, 0);
      //btnBuscar.addSelectionListener(new 5(this));
      btnBuscar.setLayoutData(new GridData(16384, 4, false, false, 1, 1));
      btnBuscar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      btnBuscar.setText("Buscar");
      Label lblNewLabel_1 = new Label(this.headerContainer, 0);
      GridData gd_lblNewLabel_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel_1.widthHint = 5;
      lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
      this.btnHideDeletedSuppliers = new Button(this.headerContainer, 32);
     // this.btnHideDeletedSuppliers.addSelectionListener(new 6(this));
      this.btnHideDeletedSuppliers.setText("Ocultar proveedores eliminados");
      //TODO: arreglar
   }

   private void createMainContent() {
      this.table = new Table(this.mainContainer, 67584);
      this.table.setHeaderVisible(true);
      this.table.setLinesVisible(true);
      TableColumn tblclmnNroProveedor = new TableColumn(this.table, 0);
      tblclmnNroProveedor.setWidth(100);
      tblclmnNroProveedor.setText("Nro. Proveedor");
      TableColumn tblclmnCantArtculos = new TableColumn(this.table, 0);
      tblclmnCantArtculos.setWidth(100);
      tblclmnCantArtculos.setText("Razón Social");
      TableColumn tblclmnTickets = new TableColumn(this.table, 0);
      tblclmnTickets.setWidth(100);
      tblclmnTickets.setText("CUIT");
      TableColumn tblclmnCondicinIva = new TableColumn(this.table, 0);
      tblclmnCondicinIva.setWidth(100);
      tblclmnCondicinIva.setText("Condición IVA");
      TableColumn tblclmnTickets2 = new TableColumn(this.table, 0);
      tblclmnTickets2.setWidth(100);
      tblclmnTickets2.setText("Teléfono");
      TableColumn tblclmnTickets3 = new TableColumn(this.table, 0);
      tblclmnTickets3.setWidth(100);
      tblclmnTickets3.setText("E-mail");
      TableColumn tblclmnTickets4 = new TableColumn(this.table, 0);
      tblclmnTickets4.setWidth(100);
      tblclmnTickets4.setText("Dirección");
      TableColumn tblclmnEstado = new TableColumn(this.table, 0);
      tblclmnEstado.setWidth(100);
      tblclmnEstado.setText("Estado");
   }

   private void createFooterContent() {
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Proveedores");
   }

   protected Point getInitialSize() {
      return new Point(800, 600);
   }

   public List<Supplier> getSuppliers() {
      return this.suppliers;
   }

   public void setSuppliers(List<Supplier> suppliers) {
      this.suppliers = suppliers;
   }
}
