package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.util.FVImageUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class ProductsManager extends AbstractFVApplicationWindow {
   protected static Logger logger = LoggerFactory.getLogger("ProductsManager");
   private static ProductsManager INSTANCE = null;
   private static Table table;
   private Text txtQuery;
   private Button btnHideDiscontinued;
   private Product product = null;

   public ProductsManager() {
      super((Shell)null);
      this.setBlockOnOpen(true);
      this.addStatusLine();
   }

   private static synchronized void createInstance() {
      if (INSTANCE == null) {
         INSTANCE = new ProductsManager();
      }

   }

   public static ProductsManager getInstance() {
      createInstance();
      return INSTANCE;
   }

   protected Control createContents(Composite parent) {
      FillLayout fillLayout = new FillLayout();
      fillLayout.marginHeight = 0;
      fillLayout.marginWidth = 0;
      Composite container = new Composite(parent, 0);
      FormLayout formLayout = new FormLayout();
      formLayout.marginHeight = 0;
      formLayout.marginWidth = 0;
      formLayout.spacing = 0;
      container.setLayout(formLayout);
      Composite headerContainer = new Composite(container, 0);
      GridLayout gl_headerContainer = new GridLayout();
      headerContainer.setLayout(gl_headerContainer);
      FormData fData = new FormData();
      fData.top = new FormAttachment(0);
      fData.left = new FormAttachment(0);
      fData.right = new FormAttachment(100);
      fData.bottom = new FormAttachment(0, 50);
      headerContainer.setLayoutData(fData);
      Composite topMenuContainer = new Composite(headerContainer, 0);
      topMenuContainer.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
      topMenuContainer.setLayout(new GridLayout(8, false));
      
      // Botón Nuevo artículo
      Button btnNewButton_2 = new Button(topMenuContainer, 0);
      btnNewButton_2.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            addNewProduct();
         }
      });
      btnNewButton_2.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_add.gif"));
      btnNewButton_2.setText("Nuevo artículo");
      
      // Botón Modificar
      Button btnNewButton_1 = new Button(topMenuContainer, 0);
      btnNewButton_1.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            editProduct();
         }
      });
      btnNewButton_1.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_edit.gif"));
      btnNewButton_1.setText("Modificar");
      
      // Botón Discontinuar
      Button btnNewButton = new Button(topMenuContainer, 0);
      btnNewButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            deleteProduct();
         }
      });
      btnNewButton.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_delete.gif"));
      btnNewButton.setText("Discontinuar");
      
      Label lblNewLabel = new Label(topMenuContainer, 0);
      GridData gd_lblNewLabel = new GridData(131072, 16777216, false, false, 1, 1);
      gd_lblNewLabel.widthHint = 25;
      lblNewLabel.setLayoutData(gd_lblNewLabel);
      GridData gd1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd1.widthHint = 175;
      
      this.txtQuery = new Text(topMenuContainer, 2048);
      this.txtQuery.setLayoutData(gd1);
      this.txtQuery.setFont(SWTResourceManager.getFont("Tahoma", 12, 0));
      this.txtQuery.setText("");
      this.txtQuery.addTraverseListener(new org.eclipse.swt.events.TraverseListener() {
         @Override
         public void keyTraversed(org.eclipse.swt.events.TraverseEvent e) {
            if (e.detail == org.eclipse.swt.SWT.TRAVERSE_RETURN) {
               searchProducts();
            }
         }
      });
      
      Button btnBuscar = new Button(topMenuContainer, 0);
      btnBuscar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            searchProducts();
         }
      });
      btnBuscar.setImage(SWTResourceManager.getImage("C:\\facilvirtual\\images\\icon_search.gif"));
      btnBuscar.setText("Buscar");
      
      Label lblNewLabel_1 = new Label(topMenuContainer, 0);
      GridData gd_lblNewLabel_1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblNewLabel_1.widthHint = 5;
      lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
      
      this.btnHideDiscontinued = new Button(topMenuContainer, 32);
      this.btnHideDiscontinued.setSelection(true);
      this.btnHideDiscontinued.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            searchProducts();
         }
      });
      this.btnHideDiscontinued.setText("Ocultar artículos discontinuados");
      
      Composite tableContainer = new Composite(container, 0);
      tableContainer.setLayout(fillLayout);
      fData = new FormData();
      fData.top = new FormAttachment(headerContainer);
      fData.left = new FormAttachment(0);
      fData.right = new FormAttachment(100);
      fData.bottom = new FormAttachment(100);
      tableContainer.setLayoutData(fData);
      table = new Table(tableContainer, 67588);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);
      TableColumn column = new TableColumn(table, 16384);
      column.setText("Código");
      column.setWidth(100);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Descripción");
      column.setWidth(315);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Rubro");
      column.setWidth(100);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Proveedor");
      column.setWidth(100);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Vencimiento");
      column.setWidth(100);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Precio Venta");
      column.setWidth(80);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Última Act. Precio");
      column.setWidth(100);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Última Venta");
      column.setWidth(100);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Precio Neto");
      column.setWidth(80);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Precio Costo");
      column.setWidth(80);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("IVA");
      column.setWidth(45);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("% Utilidad");
      column.setWidth(65);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Unidad Venta");
      column.setWidth(80);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Oferta");
      column.setWidth(50);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Web");
      column.setWidth(40);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Stock actual");
      column.setWidth(75);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Stock mínimo");
      column.setWidth(75);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Stock máximo");
      column.setWidth(75);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Control de stock");
      column.setWidth(75);
      table.setHeaderVisible(true);
      column = new TableColumn(table, 16384);
      column.setText("Estado");
      column.setWidth(85);
      table.setHeaderVisible(true);
      this.txtQuery.setFocus();
      this.initStatusBar();
      this.initToolTip();
      return container;
   }

   private void initToolTip() {
      table.setToolTipText("");
      final org.eclipse.swt.widgets.Label tip = new org.eclipse.swt.widgets.Label(table.getShell(), org.eclipse.swt.SWT.NONE);
      tip.setForeground(Display.getCurrent().getSystemColor(org.eclipse.swt.SWT.COLOR_INFO_FOREGROUND));
      tip.setBackground(Display.getCurrent().getSystemColor(org.eclipse.swt.SWT.COLOR_INFO_BACKGROUND));
      tip.setVisible(false);
      
      Listener tableListener = new Listener() {
         @Override
         public void handleEvent(org.eclipse.swt.widgets.Event event) {
            switch (event.type) {
               case org.eclipse.swt.SWT.Dispose:
               case org.eclipse.swt.SWT.KeyDown:
               case org.eclipse.swt.SWT.MouseMove: {
                  if (tip != null && !tip.isDisposed()) tip.setVisible(false);
                  break;
               }
               case org.eclipse.swt.SWT.MouseHover: {
                  TableItem item = table.getItem(new Point(event.x, event.y));
                  if (item != null) {
                     String text = item.getText(1);
                     if (text != null && !text.isEmpty()) {
                        Point coords = new Point(event.x, event.y);
                        coords = table.toDisplay(coords);
                        tip.setText(text);
                        tip.setLocation(coords.x + 10, coords.y + 10);
                        tip.setVisible(true);
                     }
                  }
                  break;
               }
            }
         }
      };
      
      table.addListener(org.eclipse.swt.SWT.Dispose, tableListener);
      table.addListener(org.eclipse.swt.SWT.KeyDown, tableListener);
      table.addListener(org.eclipse.swt.SWT.MouseMove, tableListener);
      table.addListener(org.eclipse.swt.SWT.MouseHover, tableListener);
   }

   private Image getRemotePhoto() {
      Image scaledImage = null;

      try {
         String urlStr = "";
         urlStr = this.getProductService().getProductPhotoUrl(this.getProduct().getBarCode());
         System.out.println(urlStr);
         ImageRegistry imageRegistry = new ImageRegistry();

         try {
            if (!"".equalsIgnoreCase(urlStr)) {
               ImageDescriptor defaultPhoto = ImageDescriptor.createFromURL(new URL(urlStr));
               imageRegistry.put("defaultPhoto" + this.getProduct().getId(), defaultPhoto);
            }
         } catch (MalformedURLException var5) {
            logger.error("Error creando la foto del producto");
            logger.error(var5.getMessage());
          //  //logger.error(var5);
         }

         Image image = imageRegistry.get("defaultPhoto" + this.getProduct().getId());
         if (image != null && !image.isDisposed()) {
            scaledImage = FVImageUtils.scaleTo(image, 120, 120);
         }
      } catch (Exception var6) {
         logger.error("Error inicializando foto web");
         logger.error(var6.getMessage());
        // //logger.error(var6);
      }

      return scaledImage;
   }

   private Image getLocalPhoto() {
      Image scaledImage = null;

      try {
         if (this.getProduct() != null && this.getProduct().havePhoto()) {
            String baseOut = "C://facilvirtual//photos//";
            String filenameOut = this.getProduct().getPhoto();
            String fileOut = baseOut + filenameOut;
            Image origImage = new Image(Display.getCurrent(), fileOut);
            scaledImage = FVImageUtils.scaleTo(origImage, 100, 100);
         }
      } catch (Exception var6) {
         logger.error("Error inicializando foto local");
         logger.error(var6.getMessage());
         ////logger.error(var6);
      }

      return scaledImage;
   }

   private void initStatusBar() {
      int totalProducts = this.getProductService().getTotalProductsQty();
      int activeProducts = this.getProductService().getActiveProductsQty();
      if (this.getStatusLineManager() != null) {
         this.getStatusLineManager().setMessage(totalProducts + " artículos" + "   |   " + activeProducts + " artículos activos");
      }

   }
   @Override
   protected boolean canHandleShellCloseEvent() {
      return false;
   }
   @Override
   protected StatusLineManager createStatusLineManager() {
      StatusLineManager statusLineManager = new StatusLineManager();
      return statusLineManager;
   }

   private void searchProducts() {
      table.removeAll();
      List<Product> products = this.getProductService().searchProducts(this.txtQuery.getText().trim(), this.btnHideDiscontinued.getSelection(), 1000);
      this.populateTable(products);
   }

   private void populateTable(List<Product> products) {
      int idx;
      for(Iterator var4 = products.iterator(); var4.hasNext(); ++idx) {
         Product p = (Product)var4.next();
         idx = 0;
         TableItem item = new TableItem(table, 0);
         item.setText(idx, p.getBarCode());
         ++idx;
         item.setText(idx, p.getDescription());
         ++idx;
         item.setText(idx, p.getCategory().getName().toUpperCase());
         ++idx;
         if (p.getSupplier() != null) {
            item.setText(idx, p.getSupplier().getCompanyName());
         }

         ++idx;
         item.setText(idx, p.getExpirationDateToDisplay());
         ++idx;
         PriceList priceList = this.getAppConfigService().getDefaultPriceList();
         ProductPrice productPrice = this.getProductService().getProductPriceForProduct(p, priceList);
         if (productPrice != null) {
            item.setText(idx, productPrice.getSellingPriceToDisplay());
            ++idx;
            item.setText(idx, productPrice.getLastUpdatedPriceToDisplay());
            ++idx;
            item.setText(idx, p.getLastSaleDateToDisplay());
            ++idx;
            item.setText(idx, productPrice.getNetPriceToDisplay());
            ++idx;
            item.setText(idx, productPrice.getCostPriceToDisplay());
            ++idx;
            item.setText(idx, productPrice.getVatToDisplay());
            ++idx;
            item.setText(idx, productPrice.getGrossMarginToDisplay());
            ++idx;
         } else {
            ++idx;
            ++idx;
            ++idx;
            ++idx;
            ++idx;
            ++idx;
            ++idx;
            ++idx;
         }

         item.setText(idx, p.getSellingUnit());
         ++idx;
         item.setText(idx, p.isInOffer() ? "SI" : "NO");
         ++idx;
         item.setText(idx, p.isInWeb() ? "SI" : "NO");
         ++idx;
         item.setText(idx, p.getStockToDisplay());
         ++idx;
         item.setText(idx, p.getStockMinToDisplay());
         ++idx;
         item.setText(idx, p.getStockMaxToDisplay());
         ++idx;
         item.setText(idx, p.isStockControlEnabled() ? "SI" : "NO");
         ++idx;
         item.setText(idx, p.isDiscontinued() ? "Discontinuado" : "Activo");
      }

   }

   private void addNewProduct() {
      AddNewProduct addNewProductDialog = new AddNewProduct(this.getShell());
      addNewProductDialog.setBlockOnOpen(true);
      addNewProductDialog.open();
      if ("OK".equalsIgnoreCase(addNewProductDialog.getAction())) {
         this.initStatusBar();
      }

   }

   private void editProduct() {
      int idx = table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un artículo");
         return;
      }
      
      try {
         String barCode = table.getItem(idx).getText(0).trim();
         logger.info("Editando artículo con código: " + barCode);
         
         Product product = this.getProductService().getProductByBarCode(barCode);
         if (product == null) {
            logger.error("No se encontró el artículo con código: " + barCode);
            this.alert("No se encontró el artículo");
            return;
         }
         
         EditProduct dialog = new EditProduct(this.getShell());
         dialog.setBlockOnOpen(true);
         dialog.setProduct(product);
         logger.info("Abriendo diálogo de edición para artículo: " + product.getDescription());
         
         dialog.open();
         
         if ("OK".equalsIgnoreCase(dialog.getAction())) {
            Product editedProduct = dialog.getProduct();
            try {
               logger.info("Guardando cambios del artículo: " + editedProduct.getDescription());
               this.getProductService().saveProduct(editedProduct);
               this.searchProducts();
               this.initStatusBar();
            } catch (Exception e) {
               logger.error("Error al guardar el artículo editado", e);
               this.alert("Error al guardar los cambios: " + e.getMessage());
            }
         }
      } catch (Exception e) {
         logger.error("Error al abrir el diálogo de edición", e);
         this.alert("Error al abrir el editor: " + e.getMessage());
      }
   }

   private void deleteProduct() {
      int idx = table.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona un artículo");
      } else if (FVConfirmDialog.openQuestion(this.getShell(), "Discontinuar artículo", "¿Quieres discontinuar el artículo?")) {
         try {
            String barCode = table.getItem(idx).getText(0).trim();
            this.getProductService().deleteProductByBarCode(barCode);
         } catch (Exception var3) {
         }

         this.searchProducts();
         this.initStatusBar();
      }

   }

   @Override 
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Artículos");
      
      // Agregar listener para manejar el cierre de la ventana
      newShell.addListener(org.eclipse.swt.SWT.Close, new Listener() {
         @Override
         public void handleEvent(org.eclipse.swt.widgets.Event e) {
            if (!canHandleShellCloseEvent()) {
               e.doit = true;
            }
         }
      });
   }
   @Override
   protected Point getInitialSize() {
      return new Point(1020, 650);
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }
}
