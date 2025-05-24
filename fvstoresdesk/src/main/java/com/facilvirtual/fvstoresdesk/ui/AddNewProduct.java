package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductCategory;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.model.Supplier;
import com.facilvirtual.fvstoresdesk.model.SupplierForProduct;
import com.facilvirtual.fvstoresdesk.model.Vat;
import com.facilvirtual.fvstoresdesk.util.FVFileUtils;
import com.facilvirtual.fvstoresdesk.util.FVImageUtils;
import com.facilvirtual.fvstoresdesk.util.FVMathUtils;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.util.StringUtils;

public class AddNewProduct extends AbstractFVDialog {
   private static Logger logger = LoggerFactory.getLogger("AddNewProduct");
   protected Text txtBarCode;
   protected Text txtDescription;
   protected Text txtCostPrice;
   protected Text txtGrossMargin;
   protected Text txtSellingPrice;
   protected Combo comboVat;
   protected Combo comboProductCategories;
   protected Combo comboUnits;
   protected Button btnInOffer;
   protected Button btnInWeb;
   protected Button btnDiscontinued;
   protected Product product;
   protected Composite tabSuppliersContainer;
   private Text txtSupplierNumber1;
   private Text txtSupplierName1;
   private Text txtSupplierPrice1;
   private Text txtSupplierLastUpdated1;
   private Text txtSupplierNumber2;
   private Text txtSupplierName2;
   private Text txtSupplierPrice2;
   private Text txtSupplierLastUpdated2;
   private Text txtSupplierNumber3;
   private Text txtSupplierName3;
   private Text txtSupplierPrice3;
   private Text txtSupplierLastUpdated3;
   private Text txtSupplierNumber4;
   private Text txtSupplierName4;
   private Text txtSupplierPrice4;
   private Text txtSupplierLastUpdated4;
   protected SupplierForProduct supplierForProduct1 = new SupplierForProduct();
   protected SupplierForProduct supplierForProduct2 = new SupplierForProduct();
   protected SupplierForProduct supplierForProduct3 = new SupplierForProduct();
   protected SupplierForProduct supplierForProduct4 = new SupplierForProduct();
   protected Text txtShortDescription;
   protected Text txtQuantity;
   protected Combo comboQuantityUnit;
   private Label lblPreviewDescription;
   private Label lblPreviewQuantity;
   private Label lblPreviewQuantityUnit;
   private Label lblPreviewPrice;
   private Label lblPreviewBarCode;
   private Label lblPreviewPricePerUnit;
   private Label lblPreviewLastUpdatedPrice;
   private Date lastUpdatedPrice;
   protected Button btnStockControlEnabled;
   protected Text txtStock;
   protected Text txtStockMin;
   protected Text txtStockMax;
   private Composite tab1Container;
   private Button btnQuitar;
   private Button btnQuitar_1;
   private Button btnQuitar_2;
   private Button btnQuitar_3;
   private TabItem tbtmPrecios;
   private Composite composite_1;
   protected Table tablePrices;
   private TableColumn tblclmnLista;
   private TableColumn tblclmnPrecioCosto;
   private TableColumn tblclmnIva;
   private TableColumn tblclmnUtilidad;
   private Composite composite_2;
   private Button btnModificar;
   private TableColumn tblclmnPrecioVenta;
   private TableColumn tblclmnltAct;
   private Composite composite_3;
   protected Text txtExpirationDate;
   private Label lblDdmmaaaa;
   private Composite composite_4;
   protected Text txtAlertExpDays;
   private Label lblDas;
   protected Button btnAlertExpActive;
   private TabItem tbtmFoto;
   private Composite composite_5;
   protected CLabel lblFoto;
   private Button btnUpdatePhoto;
   private Composite composite_6;
   private Button btnDeletePhoto;
   private long tmpProductId = -1L;
   private String tmpPhotoFilename = "";
   private boolean deletePhoto = false;
   private TabItem tbtmVencimiento;
   private Composite composite_7;
   private Label lblFechaVencimiento;
   private Label lblAlertarVencimiento;

   public AddNewProduct(Shell parentShell) {
      super(parentShell);
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nuevo artículo");
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      this.initDescriptionDisplay(container);
      TabFolder tabFolderContainer = new TabFolder(container, 0);
      GridData gd_tabFolderContainer = new GridData(16384, 16777216, false, false, 1, 1);
      gd_tabFolderContainer.widthHint = 670;
      gd_tabFolderContainer.verticalIndent = 5;
      tabFolderContainer.setLayoutData(gd_tabFolderContainer);
      TabItem tabItem = new TabItem(tabFolderContainer, 0);
      tabItem.setText("General");
      this.tab1Container = new Composite(tabFolderContainer, 0);
      this.createTab1Contents(this.tab1Container);
      tabItem.setControl(this.tab1Container);
      new Label(this.tab1Container, 0);
      this.btnDiscontinued = new Button(this.tab1Container, 32);
      this.btnDiscontinued.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      this.btnDiscontinued.setText("Artículo discontinuado");
      this.btnDiscontinued.setVisible(false);
      this.tbtmFoto = new TabItem(tabFolderContainer, 0);
      this.tbtmFoto.setText("Foto");
      this.composite_5 = new Composite(tabFolderContainer, 0);
      this.tbtmFoto.setControl(this.composite_5);
      GridLayout gl_composite_5 = new GridLayout(1, false);
      gl_composite_5.marginWidth = 7;
      gl_composite_5.marginHeight = 0;
      gl_composite_5.marginTop = 7;
      gl_composite_5.verticalSpacing = 10;
      gl_composite_5.horizontalSpacing = 10;
      this.composite_5.setLayout(gl_composite_5);
      this.lblFoto = new CLabel(this.composite_5, 2048);
      GridData gd_lblFoto = new GridData(16384, 16777216, false, false, 1, 1);
      gd_lblFoto.heightHint = 221;
      gd_lblFoto.widthHint = 221;
      this.lblFoto.setLayoutData(gd_lblFoto);
      this.lblFoto.setBackground(SWTResourceManager.getColor(1));
      this.lblFoto.setText("");
      this.composite_6 = new Composite(this.composite_5, 0);
      GridLayout gl_composite_6 = new GridLayout(2, false);
      gl_composite_6.marginBottom = 8;
      gl_composite_6.marginHeight = 0;
      this.composite_6.setLayout(gl_composite_6);
      this.btnUpdatePhoto = new Button(this.composite_6, 0);
      GridData gd_btnUpdatePhoto = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnUpdatePhoto.widthHint = 90;
      this.btnUpdatePhoto.setLayoutData(gd_btnUpdatePhoto);
      this.btnUpdatePhoto.setText("Modificar");
      this.btnUpdatePhoto.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            updatePhoto();
         }
      });
      this.btnDeletePhoto = new Button(this.composite_6, 0);
      GridData gd_btnDeletePhoto = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnDeletePhoto.widthHint = 90;
      gd_btnDeletePhoto.horizontalIndent = 5;
      this.btnDeletePhoto.setLayoutData(gd_btnDeletePhoto);
      this.btnDeletePhoto.setText("Eliminar");
      this.btnDeletePhoto.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            deletePhoto();
         }
      });
      this.tbtmPrecios = new TabItem(tabFolderContainer, 0);
      this.tbtmPrecios.setText("Precios");
      this.composite_1 = new Composite(tabFolderContainer, 0);
      this.tbtmPrecios.setControl(this.composite_1);
      this.composite_1.setLayout(new GridLayout(1, false));
      this.tablePrices = new Table(this.composite_1, 67584);
      this.tablePrices.setLinesVisible(true);
      this.tablePrices.setHeaderVisible(true);
      this.tablePrices.setLayoutData(new GridData(4, 4, true, true, 1, 1));
      this.tblclmnLista = new TableColumn(this.tablePrices, 0);
      this.tblclmnLista.setWidth(178);
      this.tblclmnLista.setText("Lista");
      this.tblclmnPrecioCosto = new TableColumn(this.tablePrices, 0);
      this.tblclmnPrecioCosto.setWidth(100);
      this.tblclmnPrecioCosto.setText("Precio Costo");
      this.tblclmnIva = new TableColumn(this.tablePrices, 0);
      this.tblclmnIva.setWidth(56);
      this.tblclmnIva.setText("IVA");
      this.tblclmnUtilidad = new TableColumn(this.tablePrices, 0);
      this.tblclmnUtilidad.setWidth(72);
      this.tblclmnUtilidad.setText("% Utilidad");
      this.tblclmnPrecioVenta = new TableColumn(this.tablePrices, 0);
      this.tblclmnPrecioVenta.setWidth(100);
      this.tblclmnPrecioVenta.setText("Precio Venta");
      this.tblclmnltAct = new TableColumn(this.tablePrices, 0);
      this.tblclmnltAct.setWidth(139);
      this.tblclmnltAct.setText("Últ. actualización");
      this.composite_2 = new Composite(this.composite_1, 0);
      this.composite_2.setLayout(new GridLayout(1, false));
      this.btnModificar = new Button(this.composite_2, 0);

      this.btnModificar.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            saveNewProduct();
         }
      });
      

      GridData gd_btnModificar = new GridData(16384, 16777216, false, false, 1, 1);
      gd_btnModificar.widthHint = 90;
      this.btnModificar.setLayoutData(gd_btnModificar);
      this.btnModificar.setText("Modificar");
      TabItem tbtmProveedores = new TabItem(tabFolderContainer, 0);
      tbtmProveedores.setText("Proveedores");
      this.tabSuppliersContainer = new Composite(tabFolderContainer, 0);
      tbtmProveedores.setControl(this.tabSuppliersContainer);
      this.tabSuppliersContainer.setLayout(new GridLayout(7, false));
      this.supplierForProduct1.setDefaultSupplier(true);
      this.createSuppliersContents();
      TabItem tabItemStock = new TabItem(tabFolderContainer, 0);
      tabItemStock.setText("Stock");
      Composite tabStockContainer = new Composite(tabFolderContainer, 0);
      tabItemStock.setControl(tabStockContainer);
      GridLayout gl_tabStockContainer = new GridLayout(2, false);
      gl_tabStockContainer.marginLeft = 5;
      gl_tabStockContainer.marginTop = 5;
      tabStockContainer.setLayout(gl_tabStockContainer);
      new Label(tabStockContainer, 0);
      this.btnStockControlEnabled = new Button(tabStockContainer, 32);
      this.btnStockControlEnabled.setText("Usar control de stock");
      Label lblStockActual = new Label(tabStockContainer, 0);
      lblStockActual.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblStockActual.setText("Stock actual:");
      this.txtStock = new Text(tabStockContainer, 2048);
      GridData gd_txtStock = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtStock.widthHint = 100;
      this.txtStock.setLayoutData(gd_txtStock);
      Label lblStockMnimo = new Label(tabStockContainer, 0);
      lblStockMnimo.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblStockMnimo.setText("Stock mínimo:");
      this.txtStockMin = new Text(tabStockContainer, 2048);
      GridData gd_txtStockMin = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtStockMin.widthHint = 100;
      this.txtStockMin.setLayoutData(gd_txtStockMin);
      Label lblStockMximo = new Label(tabStockContainer, 0);
      lblStockMximo.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblStockMximo.setText("Stock máximo:");
      this.txtStockMax = new Text(tabStockContainer, 2048);
      GridData gd_txtStockMax = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtStockMax.widthHint = 100;
      this.txtStockMax.setLayoutData(gd_txtStockMax);
      this.initTabPrices();
      this.initTabStock();
      this.tbtmVencimiento = new TabItem(tabFolderContainer, 0);
      this.tbtmVencimiento.setText("Vencimiento");
      this.composite_7 = new Composite(tabFolderContainer, 0);
      this.tbtmVencimiento.setControl(this.composite_7);
      GridLayout gl_composite_7 = new GridLayout(2, false);
      gl_composite_7.marginLeft = 5;
      gl_composite_7.marginTop = 5;
      this.composite_7.setLayout(gl_composite_7);
      new Label(this.composite_7, 0);
      this.btnAlertExpActive = new Button(this.composite_7, 32);
      this.btnAlertExpActive.setText("Usar alerta de vencimiento");
      this.lblFechaVencimiento = new Label(this.composite_7, 0);
      this.lblFechaVencimiento.setText("Fecha Vencimiento:");
      this.composite_3 = new Composite(this.composite_7, 0);
      GridLayout gl_composite_3 = new GridLayout(2, false);
      gl_composite_3.marginHeight = 3;
      gl_composite_3.marginLeft = -5;
      this.composite_3.setLayout(gl_composite_3);
      this.txtExpirationDate = new Text(this.composite_3, 2048);
      GridData gd_txtExpirationDate = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtExpirationDate.widthHint = 120;
      this.txtExpirationDate.setLayoutData(gd_txtExpirationDate);
      this.lblDdmmaaaa = new Label(this.composite_3, 0);
      this.lblDdmmaaaa.setForeground(SWTResourceManager.getColor(16));
      this.lblDdmmaaaa.setText("dd/mm/aaaa");
      new Label(container, 0);
      this.lblAlertarVencimiento = new Label(this.composite_7, 0);
      this.lblAlertarVencimiento.setText("Alerta Vencimiento:");
      this.composite_4 = new Composite(this.composite_7, 0);
      GridLayout gl_composite_4 = new GridLayout(2, false);
      gl_composite_4.marginHeight = 3;
      gl_composite_4.marginLeft = -5;
      this.composite_4.setLayout(gl_composite_4);
      this.txtAlertExpDays = new Text(this.composite_4, 2048);
      this.txtAlertExpDays.setText("15");
      GridData gd_txtAlertExpDays = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtAlertExpDays.widthHint = 30;
      this.txtAlertExpDays.setLayoutData(gd_txtAlertExpDays);
      this.lblDas = new Label(this.composite_4, 0);
      this.lblDas.setText("días antes");
      this.lblDas.setForeground(SWTResourceManager.getColor(16));
      TabItem tabItem2 = new TabItem(tabFolderContainer, 0);
      tabItem2.setText("Etiqueta");
      Composite tab2Container = new Composite(tabFolderContainer, 0);
      this.createTab2Contents(tab2Container);
      tabItem2.setControl(tab2Container);
      this.txtBarCode.setFocus();
      this.initTmpProductId();
      this.initTabPhoto();
      return container;
   }

   protected void initTabPhoto() {
      try {
         String baseOut;
         String filenameOut;
         String fileOut;
         Image origImage;
         Image scaledImage;
         if (this.getProduct() != null && this.getProduct().havePhoto()) {
            baseOut = "C://facilvirtual//photos//";
            filenameOut = this.getProduct().getPhoto();
            fileOut = baseOut + filenameOut;
            origImage = new Image(Display.getCurrent(), fileOut);
            scaledImage = FVImageUtils.scaleTo(origImage, 217, 217);
            this.lblFoto.setMargins(FVImageUtils.calculateLeftMargin(scaledImage, 221), FVImageUtils.calculateTopMargin(scaledImage, 221), 0, 0);
            this.lblFoto.setImage(scaledImage);
         } else {
            baseOut = "C://facilvirtual//images//";
            filenameOut = "imageNotAvailable220x220.jpg";
            fileOut = baseOut + filenameOut;
            origImage = new Image(Display.getCurrent(), fileOut);
            scaledImage = FVImageUtils.scaleTo(origImage, 220, 220);
            this.lblFoto.setMargins(1, 1, 0, 0);
            this.lblFoto.setImage(scaledImage);
         }
      } catch (Exception var6) {
         logger.error("Error inicializando foto");
         logger.error(var6.getMessage());
         //logger.error(var6);
      }

   }

   private void initTmpProductId() {
      this.setTmpProductId((new Date()).getTime());
   }

   private void initTabPrices() {
      if (this.getProduct() == null) {
         try {
            List<PriceList> priceLists = this.getAppConfigService().getActivePriceLists();
            Iterator var3 = priceLists.iterator();

            while(var3.hasNext()) {
               PriceList priceList = (PriceList)var3.next();
               TableItem tableItem = new TableItem(this.tablePrices, 0);
               tableItem.setText(0, String.valueOf(priceList.getName()));
               tableItem.setText(1, String.valueOf("0,00"));
               tableItem.setText(2, String.valueOf("21%"));
               tableItem.setText(3, String.valueOf("0,00"));
               tableItem.setText(4, String.valueOf("0,00"));
               tableItem.setText(5, String.valueOf("0,00"));
               tableItem.setText(6, String.valueOf(""));
            }
         } catch (Exception var5) {
         }
      }

   }

   private void editProductPrice() {
      int idx = this.tablePrices.getSelectionIndex();
      if (idx < 0) {
         this.alert("Selecciona una lista");
      } else {
         try {
            String priceListName = this.tablePrices.getItem(idx).getText(0).trim();
            double costPrice = Double.parseDouble(this.tablePrices.getItem(idx).getText(1).replaceAll(",", "."));
            Vat vat = this.getOrderService().getVatByName(this.tablePrices.getItem(idx).getText(2));
            double grossMargin = Double.parseDouble(this.tablePrices.getItem(idx).getText(3).replaceAll(",", "."));
            double sellingPrice = Double.parseDouble(this.tablePrices.getItem(idx).getText(4).replaceAll(",", "."));
            PriceList priceList = this.getAppConfigService().getPriceListByName(priceListName);
            ProductPrice productPrice = new ProductPrice();
            productPrice.setPriceList(priceList);
            productPrice.setCostPrice(costPrice);
            productPrice.setVat(vat);
            productPrice.setGrossMargin(grossMargin);
            productPrice.setSellingPrice(sellingPrice);
            EditProductPrice dialog = new EditProductPrice(this.getShell());
            dialog.setTitle("Modificar precio (Lista: " + priceList.getName() + ")");
            dialog.setBlockOnOpen(true);
            dialog.setProductPrice(productPrice);
            dialog.open();
            if ("OK".equalsIgnoreCase(dialog.getAction())) {
               try {
                  ProductPrice editedProductPrice = dialog.getProductPrice();
                  this.tablePrices.getItem(idx).setText(1, editedProductPrice.getCostPriceToDisplay());
                  this.tablePrices.getItem(idx).setText(2, editedProductPrice.getVat().getName());
                  this.tablePrices.getItem(idx).setText(3, editedProductPrice.getGrossMarginToDisplay());
                  this.tablePrices.getItem(idx).setText(4, editedProductPrice.getSellingPriceToDisplay());
                  this.tablePrices.getItem(idx).setText(5, editedProductPrice.getLastUpdatedPriceToDisplay());
                  this.updateLabelPreview();
               } catch (Exception var14) {
               }
            }
         } catch (Exception var15) {
            logger.error("Error");
            logger.error(var15.getMessage());
            logger.error(var15.toString());
         }
      }

   }

   protected void createSuppliersContents() {
      this.clearContainer(this.tabSuppliersContainer);
      Label label = new Label(this.tabSuppliersContainer, 0);
      label.setText("#");
      Label lblProveedor = new Label(this.tabSuppliersContainer, 0);
      lblProveedor.setText("Nombre");
      Label lblPrecio = new Label(this.tabSuppliersContainer, 0);
      lblPrecio.setText("Costo");
      Label lblFechaAct = new Label(this.tabSuppliersContainer, 0);
      lblFechaAct.setText("Última actualización");
      new Label(this.tabSuppliersContainer, 0);
      new Label(this.tabSuppliersContainer, 0);
      new Label(this.tabSuppliersContainer, 0);
      this.txtSupplierNumber1 = new Text(this.tabSuppliersContainer, 2056);
      this.txtSupplierNumber1.setText("1");
      GridData gd_txtSupplierNumber1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierNumber1.widthHint = 10;
      this.txtSupplierNumber1.setLayoutData(gd_txtSupplierNumber1);
      this.txtSupplierName1 = new Text(this.tabSuppliersContainer, 2056);
      GridData gd_txtSupplierName1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierName1.widthHint = 130;
      this.txtSupplierName1.setLayoutData(gd_txtSupplierName1);
      if (this.supplierForProduct1.getSupplier() != null) {
         this.txtSupplierName1.setText(this.supplierForProduct1.getSupplier().getCompanyName());
      }

      this.txtSupplierPrice1 = new Text(this.tabSuppliersContainer, 2048);
      GridData gd_txtSupplierPrice1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierPrice1.widthHint = 50;
      this.txtSupplierPrice1.setLayoutData(gd_txtSupplierPrice1);
      this.txtSupplierPrice1.setText(this.supplierForProduct1.getCostPriceToDisplay());

      this.txtSupplierPrice1.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            try {
               supplierForProduct1.setCostPrice(
                  Double.valueOf(txtSupplierPrice1.getText().trim().replaceAll(",", "."))
               );
            } catch (Exception ex) {
               supplierForProduct1.setCostPrice(0.0);
            }
         }
      });      
      this.txtSupplierLastUpdated1 = new Text(this.tabSuppliersContainer, 2056);

      GridData gd_txtSupplierLastUpdated1 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierLastUpdated1.widthHint = 90;
      this.txtSupplierLastUpdated1.setLayoutData(gd_txtSupplierLastUpdated1);
      this.txtSupplierLastUpdated1.setText(this.supplierForProduct1.getLastUpdatedDateToDisplay());
      Button btnSetDefaultSupplier1 = new Button(this.tabSuppliersContainer, 0);

      btnSetDefaultSupplier1.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            setSupplierAsDefault(1);
         }
      });

      btnSetDefaultSupplier1.setText("Establecer como predeterminado");
      if (this.supplierForProduct1.isDefaultSupplier()) {
         btnSetDefaultSupplier1.dispose();
         Label lblSupplierDefault = new Label(this.tabSuppliersContainer, 0);
         lblSupplierDefault.setText("predeterminado");
      }

      Button btnChangeSupplier = new Button(this.tabSuppliersContainer, 0);

     // btnChangeSupplier.addSelectionListener(new 6(this));

      btnChangeSupplier.setText("Cambiar");
      this.btnQuitar = new Button(this.tabSuppliersContainer, 0);

     // this.btnQuitar.addSelectionListener(new 7(this));

      this.btnQuitar.setText("Quitar");
      this.txtSupplierNumber2 = new Text(this.tabSuppliersContainer, 2056);
      this.txtSupplierNumber2.setText("2");
      GridData gd_txtSupplierNumber2 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierNumber2.widthHint = 10;
      this.txtSupplierNumber2.setLayoutData(gd_txtSupplierNumber2);
      this.txtSupplierName2 = new Text(this.tabSuppliersContainer, 2056);
      GridData gd_txtSupplierName2 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierName2.widthHint = 130;
      this.txtSupplierName2.setLayoutData(gd_txtSupplierName2);
      if (this.supplierForProduct2.getSupplier() != null) {
         this.txtSupplierName2.setText(this.supplierForProduct2.getSupplier().getCompanyName());
      }

      this.txtSupplierPrice2 = new Text(this.tabSuppliersContainer, 2048);
      GridData gd_txtSupplierPrice2 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierPrice2.widthHint = 50;
      this.txtSupplierPrice2.setLayoutData(gd_txtSupplierPrice2);
      this.txtSupplierPrice2.setText(this.supplierForProduct2.getCostPriceToDisplay());
    //  this.txtSupplierPrice2.addKeyListener(new 8(this));
      this.txtSupplierLastUpdated2 = new Text(this.tabSuppliersContainer, 2056);
      GridData gd_txtSupplierLastUpdated2 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierLastUpdated2.widthHint = 90;
      this.txtSupplierLastUpdated2.setLayoutData(gd_txtSupplierLastUpdated2);
      this.txtSupplierLastUpdated2.setText(this.supplierForProduct2.getLastUpdatedDateToDisplay());
      Button btnSetDefaultSupplier2 = new Button(this.tabSuppliersContainer, 0);
      //btnSetDefaultSupplier2.addSelectionListener(new 9(this));
      btnSetDefaultSupplier2.setText("Establecer como predeterminado");
      if (this.supplierForProduct2.isDefaultSupplier()) {
         btnSetDefaultSupplier2.dispose();
         Label lblSupplierDefault = new Label(this.tabSuppliersContainer, 0);
         lblSupplierDefault.setText("predeterminado");
      }

      Button btnChangeSupplier2 = new Button(this.tabSuppliersContainer, 0);
      //btnChangeSupplier2.addSelectionListener(new 10(this));
      btnChangeSupplier2.setText("Cambiar");
      this.btnQuitar_1 = new Button(this.tabSuppliersContainer, 0);
     // this.btnQuitar_1.addSelectionListener(new 11(this));
      this.btnQuitar_1.setText("Quitar");
      this.txtSupplierNumber3 = new Text(this.tabSuppliersContainer, 2056);
      this.txtSupplierNumber3.setText("3");
      GridData gd_txtSupplierNumber3 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierNumber3.widthHint = 10;
      this.txtSupplierNumber3.setLayoutData(gd_txtSupplierNumber3);
      this.txtSupplierName3 = new Text(this.tabSuppliersContainer, 2056);
      GridData gd_txtSupplierName3 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierName3.widthHint = 130;
      this.txtSupplierName3.setLayoutData(gd_txtSupplierName3);
      if (this.supplierForProduct3.getSupplier() != null) {
         this.txtSupplierName3.setText(this.supplierForProduct3.getSupplier().getCompanyName());
      }

      this.txtSupplierPrice3 = new Text(this.tabSuppliersContainer, 2048);
      GridData gd_txtSupplierPrice3 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierPrice3.widthHint = 50;
      this.txtSupplierPrice3.setLayoutData(gd_txtSupplierPrice3);
      this.txtSupplierPrice3.setText(this.supplierForProduct3.getCostPriceToDisplay());
     // this.txtSupplierPrice3.addKeyListener(new 12(this));
      this.txtSupplierLastUpdated3 = new Text(this.tabSuppliersContainer, 2056);
      GridData gd_txtSupplierLastUpdated3 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierLastUpdated3.widthHint = 90;
      this.txtSupplierLastUpdated3.setLayoutData(gd_txtSupplierLastUpdated3);
      this.txtSupplierLastUpdated3.setText(this.supplierForProduct3.getLastUpdatedDateToDisplay());
      Button btnSetDefaultSupplier3 = new Button(this.tabSuppliersContainer, 0);
      //btnSetDefaultSupplier3.addSelectionListener(new 13(this));
      btnSetDefaultSupplier3.setText("Establecer como predeterminado");
      if (this.supplierForProduct3.isDefaultSupplier()) {
         btnSetDefaultSupplier3.dispose();
         Label lblSupplierDefault = new Label(this.tabSuppliersContainer, 0);
         lblSupplierDefault.setText("predeterminado");
      }

      Button btnChangeSupplier3 = new Button(this.tabSuppliersContainer, 0);
      //btnChangeSupplier3.addSelectionListener(new 14(this));
      btnChangeSupplier3.setText("Cambiar");
      this.btnQuitar_2 = new Button(this.tabSuppliersContainer, 0);
      //this.btnQuitar_2.addSelectionListener(new 15(this));
      this.btnQuitar_2.setText("Quitar");
      this.txtSupplierNumber4 = new Text(this.tabSuppliersContainer, 2056);
      this.txtSupplierNumber4.setText("4");
      GridData gd_txtSupplierNumber4 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierNumber4.widthHint = 10;
      this.txtSupplierNumber4.setLayoutData(gd_txtSupplierNumber4);
      this.txtSupplierName4 = new Text(this.tabSuppliersContainer, 2056);
      GridData gd_txtSupplierName4 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierName4.widthHint = 130;
      this.txtSupplierName4.setLayoutData(gd_txtSupplierName4);
      if (this.supplierForProduct4.getSupplier() != null) {
         this.txtSupplierName4.setText(this.supplierForProduct4.getSupplier().getCompanyName());
      }

      this.txtSupplierPrice4 = new Text(this.tabSuppliersContainer, 2048);
      GridData gd_txtSupplierPrice4 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierPrice4.widthHint = 50;
      this.txtSupplierPrice4.setLayoutData(gd_txtSupplierPrice4);
      this.txtSupplierPrice4.setText(this.supplierForProduct4.getCostPriceToDisplay());
      //this.txtSupplierPrice4.addKeyListener(new 16(this));
      this.txtSupplierLastUpdated4 = new Text(this.tabSuppliersContainer, 2056);
      GridData gd_txtSupplierLastUpdated4 = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtSupplierLastUpdated4.widthHint = 90;
      this.txtSupplierLastUpdated4.setLayoutData(gd_txtSupplierLastUpdated4);
      this.txtSupplierLastUpdated4.setText(this.supplierForProduct4.getLastUpdatedDateToDisplay());
      Button btnSetDefaultSupplier4 = new Button(this.tabSuppliersContainer, 0);
      //btnSetDefaultSupplier4.addSelectionListener(new 17(this));
      btnSetDefaultSupplier4.setText("Establecer como predeterminado");
      if (this.supplierForProduct4.isDefaultSupplier()) {
         btnSetDefaultSupplier4.dispose();
         Label lblSupplierDefault = new Label(this.tabSuppliersContainer, 0);
         lblSupplierDefault.setText("predeterminado");
      }

      Button btnChangeSupplier4 = new Button(this.tabSuppliersContainer, 0);
      //btnChangeSupplier4.addSelectionListener(new 18(this));
      btnChangeSupplier4.setText("Cambiar");
      this.btnQuitar_3 = new Button(this.tabSuppliersContainer, 0);
      //this.btnQuitar_3.addSelectionListener(new 19(this));
      this.btnQuitar_3.setText("Quitar");
      this.tabSuppliersContainer.layout();
   }

   private void removeSupplierForProduct(int supplierNumber) {
      switch (supplierNumber) {
         case 1:
            this.supplierForProduct1.setSupplier((Supplier)null);
            this.supplierForProduct1.setCostPrice(0.0);
            this.supplierForProduct1.setLastUpdatedDate((Date)null);
            break;
         case 2:
            this.supplierForProduct2.setSupplier((Supplier)null);
            this.supplierForProduct2.setCostPrice(0.0);
            this.supplierForProduct2.setLastUpdatedDate((Date)null);
            break;
         case 3:
            this.supplierForProduct3.setSupplier((Supplier)null);
            this.supplierForProduct3.setCostPrice(0.0);
            this.supplierForProduct3.setLastUpdatedDate((Date)null);
            break;
         case 4:
            this.supplierForProduct4.setSupplier((Supplier)null);
            this.supplierForProduct4.setCostPrice(0.0);
            this.supplierForProduct4.setLastUpdatedDate((Date)null);
      }

      this.createSuppliersContents();
   }

   private void changeSupplierForProduct(int supplierNumber) {
      ChangeSupplier dialog = new ChangeSupplier(this.getShell());
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         switch (supplierNumber) {
            case 1:
               if (!this.existSupplierForProduct(dialog.getSupplier(), this.supplierForProduct2, this.supplierForProduct3, this.supplierForProduct4)) {
                  this.supplierForProduct1.setSupplier(dialog.getSupplier());
               } else {
                  this.alert("Ya existe ese proveedor para este artículo");
               }
               break;
            case 2:
               if (!this.existSupplierForProduct(dialog.getSupplier(), this.supplierForProduct1, this.supplierForProduct3, this.supplierForProduct4)) {
                  this.supplierForProduct2.setSupplier(dialog.getSupplier());
               } else {
                  this.alert("Ya existe ese proveedor para este artículo");
               }
               break;
            case 3:
               if (!this.existSupplierForProduct(dialog.getSupplier(), this.supplierForProduct1, this.supplierForProduct2, this.supplierForProduct4)) {
                  this.supplierForProduct3.setSupplier(dialog.getSupplier());
               } else {
                  this.alert("Ya existe ese proveedor para este artículo");
               }
               break;
            case 4:
               if (!this.existSupplierForProduct(dialog.getSupplier(), this.supplierForProduct1, this.supplierForProduct2, this.supplierForProduct3)) {
                  this.supplierForProduct4.setSupplier(dialog.getSupplier());
               } else {
                  this.alert("Ya existe ese proveedor para este artículo");
               }
         }

         this.createSuppliersContents();
      }

   }

   protected boolean existSupplierForProduct(Supplier supplier, SupplierForProduct sfp1, SupplierForProduct sfp2, SupplierForProduct sfp3) {
      boolean exist = false;
      if (sfp1.getSupplier() != null && sfp1.getSupplier().getCompanyName().equalsIgnoreCase(supplier.getCompanyName())) {
         exist = true;
      } else if (sfp2.getSupplier() != null && sfp2.getSupplier().getCompanyName().equalsIgnoreCase(supplier.getCompanyName())) {
         exist = true;
      } else if (sfp3.getSupplier() != null && sfp3.getSupplier().getCompanyName().equalsIgnoreCase(supplier.getCompanyName())) {
         exist = true;
      }

      return exist;
   }

   protected void setSupplierAsDefault(int supplierNumber) {
      this.supplierForProduct1.setDefaultSupplier(false);
      this.supplierForProduct2.setDefaultSupplier(false);
      this.supplierForProduct3.setDefaultSupplier(false);
      this.supplierForProduct4.setDefaultSupplier(false);
      switch (supplierNumber) {
         case 1:
            this.supplierForProduct1.setDefaultSupplier(true);
            break;
         case 2:
            this.supplierForProduct2.setDefaultSupplier(true);
            break;
         case 3:
            this.supplierForProduct3.setDefaultSupplier(true);
            break;
         case 4:
            this.supplierForProduct4.setDefaultSupplier(true);
      }

      this.createSuppliersContents();
   }

   protected void initTabStock() {
      if (this.getProduct() == null) {
         this.btnStockControlEnabled.setSelection(false);
         this.txtStock.setText("0");
         this.txtStockMin.setText("0");
         this.txtStockMax.setText("0");
      } else {
         this.btnStockControlEnabled.setSelection(this.getProduct().isStockControlEnabled());
         this.txtStock.setText(this.getProduct().getStockToDisplay());
         this.txtStockMin.setText(this.getProduct().getStockMinToDisplay());
         this.txtStockMax.setText(this.getProduct().getStockMaxToDisplay());
      }

      this.updateStockTab();
   }

   private void initDescriptionDisplay(Composite container) {
      Composite topContainer = new Composite(container, 0);
      topContainer.setLayout(new GridLayout(2, false));
      GridData gd_topContainer = new GridData(4, 16777216, true, false, 1, 1);
      gd_topContainer.heightHint = 60;
      gd_topContainer.widthHint = 681;
      topContainer.setLayoutData(gd_topContainer);
      topContainer.setBounds(0, 0, 300, 75);
      Label lblCdigoDeBarras = new Label(topContainer, 0);
      lblCdigoDeBarras.setText("Código: *");
      this.txtBarCode = new Text(topContainer, 2048);
      GridData gd_txtBarCode = new GridData(16384, 16777216, false, false, 1, 1);
      gd_txtBarCode.widthHint = 100;
      this.txtBarCode.setLayoutData(gd_txtBarCode);
      this.txtBarCode.setTextLimit(30);
      this.txtBarCode.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      Label lblDescripcin = new Label(topContainer, 0);
      lblDescripcin.setText("Descripción: *");
      this.txtDescription = new Text(topContainer, 2048);
      GridData gd_txtDescription = new GridData(4, 16777216, true, false, 1, 1);
      gd_txtDescription.widthHint = 509;
      this.txtDescription.setLayoutData(gd_txtDescription);
    //  this.txtDescription.addKeyListener(new 20(this));
      this.txtDescription.setTextLimit(150);
      this.txtDescription.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      //this.txtBarCode.addKeyListener(new 21(this));
   }

   private void createTab1Contents(Composite container) {
      container.setLayout(new GridLayout());
      GridLayout gl_tab1Container = (GridLayout)container.getLayout();
      gl_tab1Container.verticalSpacing = 7;
      gl_tab1Container.marginHeight = 10;
      gl_tab1Container.marginLeft = 5;
      gl_tab1Container.numColumns = 2;
      Label lblUnidadVenta = new Label(container, 0);
      lblUnidadVenta.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblUnidadVenta.setText("Unidad Venta:");
      this.comboUnits = new Combo(container, 8);
      this.comboUnits.setTextLimit(15);
      this.comboUnits.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      GridData gd_comboUnits = new GridData(16384, 16777216, true, false, 1, 1);
      gd_comboUnits.widthHint = 50;
      this.comboUnits.setLayoutData(gd_comboUnits);
      this.comboUnits.add("UN");
      this.comboUnits.add("KG");
      this.comboUnits.select(0);
    //  this.comboUnits.addSelectionListener(new 22(this));
      new Label(container, 0);
      this.btnInOffer = new Button(container, 32);
    //  this.btnInOffer.addSelectionListener(new 23(this));
      this.btnInOffer.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      this.btnInOffer.setText("En Oferta");
      Label lblRubro = new Label(container, 0);
      lblRubro.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblRubro.setText("Rubro:");
      this.comboProductCategories = new Combo(container, 8);
      this.comboProductCategories.setTextLimit(60);
      this.comboProductCategories.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      GridData gd_comboProductCategories = new GridData(16384, 16777216, true, false, 1, 1);
      gd_comboProductCategories.widthHint = 250;
      this.comboProductCategories.setLayoutData(gd_comboProductCategories);
      List<ProductCategory> categories = this.getProductService().getActiveProductCategories();
      Collections.sort(categories);
      this.comboProductCategories.add("- Sin clasificar -");
      this.comboProductCategories.select(0);
      Iterator var9 = categories.iterator();

      while(var9.hasNext()) {
         ProductCategory c = (ProductCategory)var9.next();
         if (!"Sin clasificar".equalsIgnoreCase(c.getName())) {
            this.comboProductCategories.add(c.getName());
         }
      }

      new Label(this.tab1Container, 0);
      this.btnInWeb = new Button(container, 32);
      this.btnInWeb.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      this.btnInWeb.setText("Publicar en Web");
      this.btnInWeb.setSelection(true);
      this.updatedSellingPrice();
   }

   private void updateStockTab() {
      Product helper = new Product();
      helper.setStockControlEnabled(this.btnStockControlEnabled.getSelection());

      try {
         helper.setStockControlEnabled(this.btnStockControlEnabled.getSelection());
         helper.setStock(Double.valueOf(this.txtStock.getText().trim().replaceAll(",", ".")));
         helper.setStockMin(Double.valueOf(this.txtStockMin.getText().trim().replaceAll(",", ".")));
         helper.setStockMax(Double.valueOf(this.txtStockMax.getText().trim().replaceAll(",", ".")));
      } catch (Exception var3) {
      }

      helper.setSellingUnit(this.comboUnits.getText());
      this.btnStockControlEnabled.setSelection(helper.isStockControlEnabled());
      this.txtStock.setText(helper.getStockToDisplay());
      this.txtStockMin.setText(helper.getStockMinToDisplay());
      this.txtStockMax.setText(helper.getStockMaxToDisplay());
   }

   private void createTab2Contents(Composite container) {
      container.setLayout(new GridLayout());
      Composite cTop = new Composite(container, 0);
      cTop.setLayout(new GridLayout());
      GridLayout gridLayoutTop = (GridLayout)cTop.getLayout();
      gridLayoutTop.numColumns = 2;
      Label lblShortDescription = new Label(cTop, 0);
      lblShortDescription.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
      lblShortDescription.setText("Descripción breve:");
      this.txtShortDescription = new Text(cTop, 2048);
     // this.txtShortDescription.addKeyListener(new 24(this));
      this.txtShortDescription.setTextLimit(24);
      this.txtShortDescription.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      GridData gd_txtShortDescription = new GridData(16384, 16777216, true, false, 1, 1);
      gd_txtShortDescription.widthHint = 231;
      this.txtShortDescription.setLayoutData(gd_txtShortDescription);
      Composite cBottom = new Composite(container, 0);
      cBottom.setLayout(new GridLayout());
      GridLayout gridLayoutBottom = (GridLayout)cBottom.getLayout();
      gridLayoutBottom.numColumns = 3;
      Label lblCantidad = new Label(cBottom, 0);
      lblCantidad.setAlignment(131072);
      GridData gd_lblCantidad = new GridData(131072, 16777216, false, false, 1, 1);
      gd_lblCantidad.widthHint = 89;
      lblCantidad.setLayoutData(gd_lblCantidad);
      lblCantidad.setText("Cantidad:");
      this.txtQuantity = new Text(cBottom, 2048);
      //this.txtQuantity.addKeyListener(new 25(this));
      this.txtQuantity.setTextLimit(15);
      GridData gd_txtQuantity = new GridData(4, 128, true, false, 1, 1);
      gd_txtQuantity.widthHint = 66;
      this.txtQuantity.setLayoutData(gd_txtQuantity);
      this.comboQuantityUnit = new Combo(cBottom, 8);
      this.comboQuantityUnit.add("");
      this.comboQuantityUnit.add("CC");
      this.comboQuantityUnit.add("GRS");
      this.comboQuantityUnit.add("UNI");
      this.comboQuantityUnit.add("KGS");
      this.comboQuantityUnit.add("LTS");
     // this.comboQuantityUnit.addSelectionListener(new 26(this));
      this.comboQuantityUnit.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
      Composite composite = new Composite(container, 0);
      Group grpVistaPrevia = new Group(composite, 0);
      grpVistaPrevia.setText("Vista Previa");
      grpVistaPrevia.setBounds(10, 0, 333, 165);
      Canvas canvas = new Canvas(grpVistaPrevia, 2048);
      canvas.setBackground(SWTResourceManager.getColor(1));
      canvas.setBounds(55, 31, 211, 107);
      this.lblPreviewDescription = new Label(canvas, 0);
      this.lblPreviewDescription.setAlignment(16777216);
      this.lblPreviewDescription.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      this.lblPreviewDescription.setBackground(SWTResourceManager.getColor(1));
      this.lblPreviewDescription.setBounds(7, 9, 195, 16);
      this.lblPreviewDescription.setText("TITULO");
      this.lblPreviewQuantity = new Label(canvas, 131072);
      this.lblPreviewQuantity.setBackground(SWTResourceManager.getColor(1));
      this.lblPreviewQuantity.setBounds(10, 25, 92, 13);
      this.lblPreviewQuantity.setText("");
      this.lblPreviewQuantityUnit = new Label(canvas, 0);
      this.lblPreviewQuantityUnit.setBackground(SWTResourceManager.getColor(1));
      this.lblPreviewQuantityUnit.setText("");
      this.lblPreviewQuantityUnit.setBounds(109, 25, 92, 13);
      this.lblPreviewPrice = new Label(canvas, 0);
      this.lblPreviewPrice.setBackground(SWTResourceManager.getColor(1));
      this.lblPreviewPrice.setAlignment(131072);
      this.lblPreviewPrice.setFont(SWTResourceManager.getFont("Tahoma", 30, 1));
      this.lblPreviewPrice.setBounds(10, 36, 191, 51);
      this.lblPreviewPrice.setText("$ 8.00");
      this.lblPreviewBarCode = new Label(canvas, 0);
      this.lblPreviewBarCode.setFont(SWTResourceManager.getFont("Tahoma", 7, 0));
      this.lblPreviewBarCode.setBackground(SWTResourceManager.getColor(1));
      this.lblPreviewBarCode.setBounds(5, 90, 72, 13);
      this.lblPreviewBarCode.setText("7791787000989");
      this.lblPreviewPricePerUnit = new Label(canvas, 0);
      this.lblPreviewPricePerUnit.setAlignment(16777216);
      this.lblPreviewPricePerUnit.setText("$22.86/KG");
      this.lblPreviewPricePerUnit.setFont(SWTResourceManager.getFont("Tahoma", 7, 0));
      this.lblPreviewPricePerUnit.setBackground(SWTResourceManager.getColor(1));
      this.lblPreviewPricePerUnit.setBounds(77, 90, 72, 13);
      this.lblPreviewPricePerUnit.setText("");
      this.lblPreviewLastUpdatedPrice = new Label(canvas, 0);
      this.lblPreviewLastUpdatedPrice.setAlignment(131072);
      this.lblPreviewLastUpdatedPrice.setText("10/10/2013");
      this.lblPreviewLastUpdatedPrice.setFont(SWTResourceManager.getFont("Tahoma", 7, 0));
      this.lblPreviewLastUpdatedPrice.setBackground(SWTResourceManager.getColor(1));
      this.lblPreviewLastUpdatedPrice.setBounds(146, 90, 51, 13);
      this.updateLabelPreview();
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

   protected void calculateSellingPrice() {
      try {
         String costPriceStr = this.txtCostPrice.getText().replaceAll(",", ".");
         double costPriceValue = !"".equals(costPriceStr) ? Double.parseDouble(costPriceStr) : 0.0;
         Vat vat = this.getOrderService().getVatByName(this.comboVat.getText());
         double vatValue = vat.getValue();
         String grossMarginStr = this.txtGrossMargin.getText().replaceAll(",", ".");
         double grossMarginValue = !"".equals(grossMarginStr) ? Double.parseDouble(grossMarginStr) : 0.0;
         double newSellingPrice = costPriceValue + costPriceValue * grossMarginValue / 100.0;
         newSellingPrice += newSellingPrice * vatValue / 100.0;
         DecimalFormat df = new DecimalFormat("0.00");
         String s = df.format(newSellingPrice);
         this.txtSellingPrice.setText(s);
         this.updatedSellingPrice();
         this.updateLabelPreview();
      } catch (Exception var14) {
         this.txtSellingPrice.setText("#Error");
      }

   }

   protected void updatedSellingPrice() {
      if (this.product != null) {
         try {
            double newSellingPrice = Double.valueOf(this.txtSellingPrice.getText().trim().replaceAll(",", "."));
            if (this.product.getSellingPrice() != newSellingPrice) {
               this.lastUpdatedPrice = new Date();
               if (this.btnDiscontinued.getSelection()) {
                  this.btnInWeb.setSelection(true);
                  this.btnDiscontinued.setSelection(false);
               }
            } else {
               this.lastUpdatedPrice = this.product.getLastUpdatedPrice();
               this.btnDiscontinued.setSelection(this.product.isDiscontinued());
               this.btnInWeb.setSelection(this.product.isInWeb());
            }
         } catch (Exception var4) {
         }
      } else {
         try {
            this.lastUpdatedPrice = new Date();
            this.btnInWeb.setSelection(true);
         } catch (Exception var3) {
         }
      }

   }

   protected void calculateGrossMargin() {
      try {
         String sellingPriceStr = this.txtSellingPrice.getText().replaceAll(",", ".");
         double sellingPriceValue = !"".equals(sellingPriceStr) ? Double.parseDouble(sellingPriceStr) : 0.0;
         Vat vat = this.getOrderService().getVatByName(this.comboVat.getText());
         double vatValue = vat.getValue();
         sellingPriceValue /= 1.0 + vatValue / 100.0;
         String costPriceStr = this.txtCostPrice.getText().replaceAll(",", ".");
         double costPriceValue = !"".equals(costPriceStr) ? Double.parseDouble(costPriceStr) : 0.0;
         double diff = FVMathUtils.roundValue(sellingPriceValue) - FVMathUtils.roundValue(costPriceValue);
         double margin = 0.0;
         if (costPriceValue != 0.0) {
            margin = diff / costPriceValue * 100.0;
         }

         DecimalFormat df = new DecimalFormat("0.00");
         String s = df.format(margin);
         this.txtGrossMargin.setText(s);
      } catch (Exception var16) {
         this.txtGrossMargin.setText("#Error");
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 0, IDialogConstants.OK_LABEL, false);
      button.setFont(SWTResourceManager.getFont("Tahoma", 8, 1));
      button.setText("Guardar");
      this.createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
   }

   protected Point getInitialSize() {
      return new Point(707, 471);
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.saveNewProduct();
      } else {
         this.deletePhotoTmpFile();
         this.close();
      }

   }

   protected void deletePhotoTmpFile() {
      try {
         if (!"".equals(this.getTmpPhotoFilename())) {
            String baseOut = "C://facilvirtual//tmp//";
            String fileOut = baseOut + this.getTmpPhotoFilename();
            logger.info("Eliminando archivo temporal: " + this.getTmpPhotoFilename());
            FVFileUtils.deleteFile(fileOut);
         }
      } catch (Exception var3) {
         logger.error("Se produjo un error eliminando archivo temporal: " + this.getTmpPhotoFilename());
         logger.error(var3.getMessage());
         //logger.error(var3);
         var3.printStackTrace();
      }

   }

   private void saveNewProduct() {
      if (this.validateFields()) {
         try {
            this.setAction("OK");
            Vat vat = this.getOrderService().getVat(new Long(1L));
            ProductCategory productCategory = this.getProductService().getProductCategoryByName(this.comboProductCategories.getText());
            Date creationDate = new Date();
            Product product = new Product();
            product.initCreationDate(creationDate);
            product.setBarCode(this.txtBarCode.getText().trim());
            product.setDescription(this.txtDescription.getText().trim());
            product.setVat(vat);
            product.setSellingUnit(this.comboUnits.getText());
            product.setInOffer(this.btnInOffer.getSelection());
            product.setCategory(productCategory);
            product.setInWeb(this.btnInWeb.getSelection());
            if (!"".equals(this.txtExpirationDate.getText().trim())) {
               Date expirationDate = this.getDateFromText(this.txtExpirationDate);
               product.setExpirationDate(expirationDate);
            }

            Integer expDays = this.getIntegerValueFromText(this.txtAlertExpDays);
            if (expDays != null) {
               product.setAlertExpDays(expDays);
            } else {
               product.setAlertExpDays(15);
            }

            product.setAlertExpActive(this.btnAlertExpActive.getSelection());

            try {
               product.setStockControlEnabled(this.btnStockControlEnabled.getSelection());
               product.setStock(Double.valueOf(this.txtStock.getText().trim().replaceAll(",", ".")));
               product.setStockMin(Double.valueOf(this.txtStockMin.getText().trim().replaceAll(",", ".")));
               product.setStockMax(Double.valueOf(this.txtStockMax.getText().trim().replaceAll(",", ".")));
            } catch (Exception var7) {
            }

            product.setShortDescription(this.txtShortDescription.getText().trim());
            if (!"".equals(this.txtQuantity.getText().trim())) {
               product.setQuantity(Double.valueOf(this.txtQuantity.getText().trim().replaceAll(",", ".")));
            } else {
               product.setQuantity(0.0);
            }

            product.setQuantityUnit(this.comboQuantityUnit.getText());
            if (!this.getWorkstationConfig().isTrialMode() || this.getWorkstationConfig().isTrialMode() && this.getWorkstationConfig().getTrialMaxProductsQty() > this.getProductService().getActiveProductsQty()) {
               product.setDiscontinued(false);
            }

            this.getProductService().saveProduct(product);
            this.setProduct(product);
            this.savePrices();
            this.saveSuppliersTab(creationDate);
            if (this.isDeletePhoto()) {
               this.deleteProductPhoto();
            } else {
               this.saveProductPhoto();
            }
         } catch (Exception var8) {
            logger.error("Error al guardar Nuevo artículo");
            logger.error(var8.getMessage());
            //logger.error(var8);
         }

         this.close();
      }

   }

   protected void deleteProductPhoto() {
      try {
         if (this.getProduct() != null && this.getProduct().havePhoto()) {
            String baseOut = "C://facilvirtual//photos//";
            String filenameOut = this.getProduct().getPhoto();
            String fileOut = baseOut + filenameOut;
            logger.info("Borrando foto de producto: " + filenameOut);
            FVFileUtils.deleteFile(fileOut);
            this.getProduct().setPhoto("");
            this.getProductService().saveProduct(this.getProduct());
         }

         this.deletePhotoTmpFile();
      } catch (Exception var4) {
         logger.error("Se produjo un error eliminando foto de producto");
         logger.error(var4.getMessage());
         //logger.error(var4);
         var4.printStackTrace();
      }

   }

   protected void saveProductPhoto() {
      try {
         if (!"".equals(this.getTmpPhotoFilename()) && this.getProduct() != null) {
            String baseIn = "C://facilvirtual//tmp//";
            String fileIn = baseIn + this.getTmpPhotoFilename();
            String baseOut = "C://facilvirtual//photos//";
            String filenameOut = "photo_" + this.getProduct().getId() + "." + FVFileUtils.getFileType(this.getTmpPhotoFilename());
            String fileOut = baseOut + filenameOut;
            logger.info("Copiando foto: " + this.getTmpPhotoFilename() + " -> " + filenameOut);
            FVFileUtils.copyFile(fileIn, fileOut);
            this.getProduct().setPhoto(filenameOut);
            this.getProductService().saveProduct(this.getProduct());
            this.deletePhotoTmpFile();
         }
      } catch (Exception var6) {
         logger.error("Se produjo un error eliminando archivo temporal: " + this.getTmpPhotoFilename());
         logger.error(var6.getMessage());
         //logger.error(var6);
      }

   }

   protected void savePrices() {
      try {
         for(int idx = 0; idx < this.tablePrices.getItemCount(); ++idx) {
            String priceListName = this.tablePrices.getItem(idx).getText(0).trim();
            double costPrice = Double.parseDouble(this.tablePrices.getItem(idx).getText(1).replaceAll(",", "."));
            Vat vat = this.getOrderService().getVatByName(this.tablePrices.getItem(idx).getText(2));
            double grossMargin = Double.parseDouble(this.tablePrices.getItem(idx).getText(3).replaceAll(",", "."));
            double sellingPrice = Double.parseDouble(this.tablePrices.getItem(idx).getText(4).replaceAll(",", "."));
            String lastUpdatedDateStr = this.tablePrices.getItem(idx).getText(5);
            PriceList priceList = this.getAppConfigService().getPriceListByName(priceListName);
            ProductPrice productPrice = null;
            if (this.getProduct() != null) {
               productPrice = this.getProductService().getProductPriceForProduct(this.getProduct(), priceList);
            }

            if (productPrice == null) {
               productPrice = new ProductPrice();
            }

            productPrice.setProduct(this.getProduct());
            productPrice.setPriceList(priceList);
            productPrice.setCostPrice(costPrice);
            productPrice.setVat(vat);
            productPrice.setGrossMargin(grossMargin);
            productPrice.setSellingPrice(sellingPrice);
            productPrice.updateNetPrice();
            if (!"".equals(lastUpdatedDateStr)) {
               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
               Date lastUpdatedDate = formatter.parse(lastUpdatedDateStr);
               productPrice.setLastUpdatedPrice(lastUpdatedDate);
            }

            this.getProductService().saveProductPrice(productPrice);
         }
      } catch (Exception var15) {
         logger.error("Error guardando precios en alta/modif. de artículo");
         logger.error(var15.getMessage());
         //logger.error(var15);
      }

   }

   private void saveSuppliersTab(Date creationDate) {
      try {
         this.addNewSupplierForProduct1(creationDate);
         this.addNewSupplierForProduct2(creationDate);
         this.addNewSupplierForProduct3(creationDate);
         this.addNewSupplierForProduct4(creationDate);
      } catch (Exception var3) {
         logger.error("Error guardando proveedores en alta de artículo");
         logger.error(var3.getMessage());
         //logger.error(var3);
         var3.printStackTrace();
      }

   }

   protected void addNewSupplierForProduct1(Date creationDate) {
      if (this.supplierForProduct1.getSupplier() != null || this.supplierForProduct1.getCostPrice() > 0.0 || this.supplierForProduct1.isDefaultSupplier()) {
         this.supplierForProduct1.setSupplierNumber(1);
         this.supplierForProduct1.setProduct(this.product);
         if (this.supplierForProduct1.getSupplier() != null || this.supplierForProduct1.getCostPrice() > 0.0) {
            this.supplierForProduct1.setLastUpdatedDate(creationDate);
         }

         if (this.supplierForProduct1.isDefaultSupplier()) {
            this.getProduct().setSupplier(this.supplierForProduct1.getSupplier());
            this.getProductService().saveProduct(this.getProduct());
         }

         this.getProductService().saveSupplierForProduct(this.supplierForProduct1);
      }

   }

   protected void addNewSupplierForProduct2(Date creationDate) {
      if (this.supplierForProduct2.getSupplier() != null || this.supplierForProduct2.getCostPrice() > 0.0 || this.supplierForProduct2.isDefaultSupplier()) {
         this.supplierForProduct2.setSupplierNumber(2);
         this.supplierForProduct2.setProduct(this.product);
         if (this.supplierForProduct2.getSupplier() != null || this.supplierForProduct2.getCostPrice() > 0.0) {
            this.supplierForProduct2.setLastUpdatedDate(creationDate);
         }

         if (this.supplierForProduct2.isDefaultSupplier()) {
            this.getProduct().setSupplier(this.supplierForProduct2.getSupplier());
            this.getProductService().saveProduct(this.getProduct());
         }

         this.getProductService().saveSupplierForProduct(this.supplierForProduct2);
      }

   }

   protected void addNewSupplierForProduct3(Date creationDate) {
      if (this.supplierForProduct3.getSupplier() != null || this.supplierForProduct3.getCostPrice() > 0.0 || this.supplierForProduct3.isDefaultSupplier()) {
         this.supplierForProduct3.setSupplierNumber(3);
         this.supplierForProduct3.setProduct(this.product);
         if (this.supplierForProduct3.getSupplier() != null || this.supplierForProduct3.getCostPrice() > 0.0) {
            this.supplierForProduct3.setLastUpdatedDate(creationDate);
         }

         if (this.supplierForProduct3.isDefaultSupplier()) {
            this.getProduct().setSupplier(this.supplierForProduct3.getSupplier());
            this.getProductService().saveProduct(this.getProduct());
         }

         this.getProductService().saveSupplierForProduct(this.supplierForProduct3);
      }

   }

   protected void addNewSupplierForProduct4(Date creationDate) {
      if (this.supplierForProduct4.getSupplier() != null || this.supplierForProduct4.getCostPrice() > 0.0 || this.supplierForProduct4.isDefaultSupplier()) {
         this.supplierForProduct4.setSupplierNumber(4);
         this.supplierForProduct4.setProduct(this.product);
         if (this.supplierForProduct4.getSupplier() != null || this.supplierForProduct4.getCostPrice() > 0.0) {
            this.supplierForProduct4.setLastUpdatedDate(creationDate);
         }

         if (this.supplierForProduct4.isDefaultSupplier()) {
            this.getProduct().setSupplier(this.supplierForProduct4.getSupplier());
            this.getProductService().saveProduct(this.getProduct());
         }

         this.getProductService().saveSupplierForProduct(this.supplierForProduct4);
      }

   }

   private boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtBarCode.getText().trim())) {
         valid = false;
         this.alert("Ingresa el código del artículo");
      }

      if (valid && StringUtils.containsWhitespace(this.txtBarCode.getText().trim())) {
         valid = false;
         this.alert("El código del artículo no puede contener espacios");
      }

      if (valid) {
         try {
            Product product = this.getProductService().getProductByBarCode(this.txtBarCode.getText().trim());
            if (product != null) {
               valid = false;
               this.alert("Ya existe un artículo con ese código");
            }
         } catch (Exception var5) {
            valid = false;
         }
      }

      if (valid && "".equals(this.txtDescription.getText().trim())) {
         valid = false;
         this.alert("Ingresa la descripción");
      }

      if (valid && !"".equals(this.txtExpirationDate.getText().trim())) {
         Date expDate = this.getDateFromText(this.txtExpirationDate);
         if (expDate == null) {
            valid = false;
            this.alert("La fecha de vencimiento no es válida");
         }
      }

      if (valid && !"".equals(this.txtAlertExpDays.getText().trim())) {
         Integer alertExpDays = this.getIntegerValueFromText(this.txtAlertExpDays);
         if (alertExpDays == null) {
            valid = false;
            this.alert("El valor ingresado en Alerta Vencimiento no es válido");
         }
      }

      if (valid && !"".equals(this.txtQuantity.getText().trim())) {
         try {
            double var8 = Double.valueOf(this.txtQuantity.getText().trim().replaceAll(",", "."));
         } catch (Exception var4) {
            valid = false;
            this.alert("El valor ingresado en Cantidad (Etiqueta) no es válido");
         }
      }

      return valid;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   protected void updateLabelPreview() {
      try {
         String shortDescription = this.txtShortDescription.getText().trim().toUpperCase();
         if ("".equals(shortDescription)) {
            this.lblPreviewDescription.setText(this.txtDescription.getText().trim().toUpperCase());
         } else {
            this.lblPreviewDescription.setText(shortDescription);
         }

         this.lblPreviewQuantity.setText(this.txtQuantity.getText().trim());
         this.lblPreviewQuantityUnit.setText(this.comboQuantityUnit.getText());
         ProductPrice productPrice = this.getProductPriceForLabel();
         if (productPrice != null) {
            this.lblPreviewPrice.setText("$ " + productPrice.getSellingPriceToDisplay().replaceAll(",", "."));
            if (this.getAppConfig().showPricePerUnitForLabels()) {
               try {
                  Product helper = new Product();
                  helper.setSellingPrice(productPrice.getSellingPrice());
                  helper.setQuantity(Double.valueOf(this.txtQuantity.getText().trim().replaceAll(",", ".")));
                  helper.setQuantityUnit(this.comboQuantityUnit.getText());
                  String qtyStr = helper.getPricePerUnitToDisplay();
                  if (!"".equals(qtyStr)) {
                     this.lblPreviewPricePerUnit.setText("$" + qtyStr);
                  } else {
                     this.lblPreviewPricePerUnit.setText("");
                  }
               } catch (Exception var5) {
                  this.lblPreviewPricePerUnit.setText("");
               }
            }

            this.lblPreviewLastUpdatedPrice.setText(productPrice.getLastUpdatedPriceToDisplay());
         }

         if (this.btnInOffer.getSelection()) {
            this.lblPreviewPrice.setForeground(SWTResourceManager.getColor(221, 0, 0));
         } else {
            this.lblPreviewPrice.setForeground(SWTResourceManager.getColor(153, 0, 204));
         }

         if (this.product != null) {
            this.lblPreviewBarCode.setText(this.product.getBarCode());
         } else {
            this.lblPreviewBarCode.setText(this.txtBarCode.getText().trim());
         }
      } catch (Exception var6) {
         logger.error("Error al actualizar la vista previa de la etiqueta");
         logger.error(var6.getMessage());
         //logger.error(var6);
      }

   }

   private ProductPrice getProductPriceForLabel() {
      try {
         int idx = 0;
         String priceListName = this.tablePrices.getItem(idx).getText(0).trim();
         double costPrice = Double.parseDouble(this.tablePrices.getItem(idx).getText(1).replaceAll(",", "."));
         Vat vat = this.getOrderService().getVatByName(this.tablePrices.getItem(idx).getText(2));
         double grossMargin = Double.parseDouble(this.tablePrices.getItem(idx).getText(3).replaceAll(",", "."));
         double sellingPrice = Double.parseDouble(this.tablePrices.getItem(idx).getText(4).replaceAll(",", "."));
         String lastUpdatedDateStr = this.tablePrices.getItem(idx).getText(5);
         PriceList priceList = this.getAppConfigService().getPriceListByName(priceListName);
         ProductPrice productPrice = null;
         if (this.getProduct() != null) {
            productPrice = this.getProductService().getProductPriceForProduct(this.getProduct(), priceList);
         }

         if (productPrice == null) {
            productPrice = new ProductPrice();
         }

         productPrice.setPriceList(priceList);
         productPrice.setCostPrice(costPrice);
         productPrice.setVat(vat);
         productPrice.setGrossMargin(grossMargin);
         productPrice.setSellingPrice(sellingPrice);
         productPrice.setProduct(this.getProduct());
         productPrice.updateNetPrice();
         if (!"".equals(lastUpdatedDateStr)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
            Date lastUpdatedDate = formatter.parse(lastUpdatedDateStr);
            productPrice.setLastUpdatedPrice(lastUpdatedDate);
         }

         return productPrice;
      } catch (Exception var15) {
         return null;
      }
   }

   protected SupplierForProduct getSupplierForProduct1() {
      return this.supplierForProduct1;
   }

   protected void setSupplierForProduct1(SupplierForProduct supplierForProduct1) {
      this.supplierForProduct1 = supplierForProduct1;
   }

   protected SupplierForProduct getSupplierForProduct2() {
      return this.supplierForProduct2;
   }

   protected void setSupplierForProduct2(SupplierForProduct supplierForProduct2) {
      this.supplierForProduct2 = supplierForProduct2;
   }

   protected SupplierForProduct getSupplierForProduct3() {
      return this.supplierForProduct3;
   }

   protected void setSupplierForProduct3(SupplierForProduct supplierForProduct3) {
      this.supplierForProduct3 = supplierForProduct3;
   }

   protected SupplierForProduct getSupplierForProduct4() {
      return this.supplierForProduct4;
   }

   protected void setSupplierForProduct4(SupplierForProduct supplierForProduct4) {
      this.supplierForProduct4 = supplierForProduct4;
   }

   protected void updatePhoto() {
      try {
         FileDialog dialog = new FileDialog(this.getShell(), 4096);
         dialog.setFilterNames(new String[]{"Archivos JPG (*.jpg)", "Archivos PNG (*.png)", "Archivos GIF (*.gif)"});
         dialog.setFilterExtensions(new String[]{"*.jpg", "*.png", "*.gif"});
         dialog.setFilterPath("C:\\");
         dialog.setFileName("");
         String localFilePath = dialog.open();
         if (localFilePath != null) {
            String baseOut = "C://facilvirtual//tmp//";
            String filenameOut = "photo_" + this.getTmpProductId() + "." + FVFileUtils.getFileType(localFilePath);
            this.setTmpPhotoFilename(filenameOut);
            String fileOut = baseOut + filenameOut;
            FVFileUtils.copyFile(localFilePath, fileOut);
            Image origImage = new Image(Display.getCurrent(), fileOut);
            Image scaledImage = FVImageUtils.scaleTo(origImage, 217, 217);
            this.lblFoto.setMargins(FVImageUtils.calculateLeftMargin(scaledImage, 221), FVImageUtils.calculateTopMargin(scaledImage, 221), 0, 0);
            this.lblFoto.setImage(scaledImage);
            this.setDeletePhoto(false);
         }
      } catch (Exception var8) {
      }

   }

   protected void deletePhoto() {
      String baseOut = "C://facilvirtual//images//";
      String filenameOut = "imageNotAvailable220x220.jpg";
      String fileOut = baseOut + filenameOut;
      Image origImage = new Image(Display.getCurrent(), fileOut);
      Image scaledImage = FVImageUtils.scaleTo(origImage, 220, 220);
      this.lblFoto.setMargins(1, 1, 0, 0);
      this.lblFoto.setImage(scaledImage);
      this.setDeletePhoto(true);
   }

   public long getTmpProductId() {
      return this.tmpProductId;
   }

   public void setTmpProductId(long tmpProductId) {
      this.tmpProductId = tmpProductId;
   }

   public String getTmpPhotoFilename() {
      return this.tmpPhotoFilename;
   }

   public void setTmpPhotoFilename(String tmpPhotoFilename) {
      this.tmpPhotoFilename = tmpPhotoFilename;
   }

   public boolean isDeletePhoto() {
      return this.deletePhoto;
   }

   public void setDeletePhoto(boolean deletePhoto) {
      this.deletePhoto = deletePhoto;
   }
}
