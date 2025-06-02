package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ProductCategoriesManager extends AbstractFVDialog {
   private String action = "";
   private Table tableCategories;

   public ProductCategoriesManager(Shell parentShell) {
      super(parentShell);
      this.setBlockOnOpen(true);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout(new GridLayout(2, false));
      this.tableCategories = new Table(container, 67584);
      this.tableCategories.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.tableCategories.setHeaderVisible(true);
      this.tableCategories.setLinesVisible(true);
      TableColumn tblclmnNombre = new TableColumn(this.tableCategories, 0);
      tblclmnNombre.setWidth(192);
      tblclmnNombre.setText("Nombre");
      TableColumn tblclmnIva = new TableColumn(this.tableCategories, 0);
      tblclmnIva.setWidth(47);
      tblclmnIva.setText("IVA");
      TableColumn tblclmnEstado = new TableColumn(this.tableCategories, 0);
      tblclmnEstado.setWidth(87);
      tblclmnEstado.setText("Estado");
      Composite composite = new Composite(container, 0);
      composite.setLayout(new GridLayout(1, false));
      Button btnAgregar = new Button(composite, 0);
      //TODO: arrelgar
      
     //btnAgregar.addSelectionListener(new 1(this));
     btnAgregar.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
         addNewProductCategory();
      }
     });
      btnAgregar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnAgregar.setText("Nuevo rubro");
      Button btnModificar = new Button(composite, 0);
      //btnModificar.addSelectionListener(new 2(this));
      btnModificar.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
         editCategory();
      }
     });
      btnModificar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnModificar.setText("Modificar");
      Button btnActivar = new Button(composite, 0);
      //btnActivar.addSelectionListener(new 3(this));
      btnActivar.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
         changeCategoryStatus(true);
      }
     });
      btnActivar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnActivar.setBounds(0, 0, 68, 23);
      btnActivar.setText("Activar");
      Button btnDesactivar = new Button(composite, 0);
      //btnDesactivar.addSelectionListener(new 4(this));
      btnDesactivar.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
         changeCategoryStatus(false);
      }
     });
      btnDesactivar.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      btnDesactivar.setText("Desactivar");
      this.initCategories();
      return container;
   }

   private void editCategory() {
      try {
         int idx = this.tableCategories.getSelectionIndex();
         if (idx >= 0) {
            String categoryName = this.tableCategories.getItem(idx).getText(0).trim();
            ProductCategory category = this.getProductService().getProductCategoryByName(categoryName);
            EditProductCategory dialog = new EditProductCategory(this.getShell());
            dialog.setCategory(category);
            dialog.open();
            if ("OK".equalsIgnoreCase(dialog.getAction())) {
               this.initCategories();
            }
         } else {
            this.alert("Selecciona un rubro");
         }
      } catch (Exception var5) {
      }

   }

   private void addNewProductCategory() {
      AddNewProductCategory dialog = new AddNewProductCategory(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.initCategories();
      }

   }

   private void initCategories() {
      this.tableCategories.removeAll();
      List<ProductCategory> categories = this.getProductService().getAllProductCategories();
      Collections.sort(categories);
      Iterator var3 = categories.iterator();

      while(var3.hasNext()) {
         ProductCategory category = (ProductCategory)var3.next();
         if (!"Sin clasificar".equalsIgnoreCase(category.getName())) {
            TableItem tableItem = new TableItem(this.tableCategories, 0);
            tableItem.setText(0, category.getName());
            tableItem.setText(1, category.getVatToDisplay());
            if (category.isActive()) {
               tableItem.setText(2, "Activo");
            } else {
               tableItem.setText(2, "Inactivo");
            }
         }
      }

   }

   private void changeCategoryStatus(boolean activeStatus) {
      try {
         int idx = this.tableCategories.getSelectionIndex();
         if (idx >= 0) {
            String categoryName = this.tableCategories.getItem(idx).getText(0).trim();
            ProductCategory category = this.getProductService().getProductCategoryByName(categoryName);
            category.setActive(activeStatus);
            this.getProductService().saveProductCategory(category);
            this.initCategories();
         } else {
            this.alert("Selecciona un rubro");
         }
      } catch (Exception var5) {
      }

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

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Rubros");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
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
      return new Point(457, 419);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }
}
