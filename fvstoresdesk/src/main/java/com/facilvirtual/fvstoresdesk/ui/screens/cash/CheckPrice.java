package com.facilvirtual.fvstoresdesk.ui.screens.cash;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import com.facilvirtual.fvstoresdesk.model.Product;
import com.facilvirtual.fvstoresdesk.model.ProductPrice;
import com.facilvirtual.fvstoresdesk.util.FVImageUtils;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class CheckPrice extends AbstractFVDialog {
   private static final Logger logger = LoggerFactory.getLogger("CheckPrice");
   private String action = "";
   private Product product;
   private CLabel lblPhoto;
   private PriceList priceList = null;

   public CheckPrice(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      logger.info("Viendo precio de producto con código: " + this.getProduct().getBarCode());
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      Label lblDescripcin = new Label(container, 0);
      lblDescripcin.setBounds(245, 71, 175, 17);
      lblDescripcin.setText("Descripción");
      Label lblCdigoDeBarras = new Label(container, 0);
      lblCdigoDeBarras.setBounds(245, 16, 175, 17);
      lblCdigoDeBarras.setText("Código");
      Label lblPrecio = new Label(container, 0);
      lblPrecio.setBounds(245, 159, 175, 17);
      lblPrecio.setText("Precio");
      Label lblBarCode = new Label(container, 0);
      lblBarCode.setBounds(245, 37, 298, 28);
      lblBarCode.setFont(SWTResourceManager.getFont("Arial", 14, 0));
      lblBarCode.setText("");
      Label lblDescription = new Label(container, 64);
      lblDescription.setBounds(245, 92, 298, 61);
      lblDescription.setFont(SWTResourceManager.getFont("Arial", 14, 0));
      lblDescription.setText("");
      Label lblPrice = new Label(container, 0);
      lblPrice.setBounds(245, 182, 298, 42);
      lblPrice.setFont(SWTResourceManager.getFont("Arial", 22, 0));
      lblPrice.setText("");
      lblBarCode.setText(this.getProduct().getBarCode());
      lblDescription.setText(this.getProduct().getDescription());
      ProductPrice productPrice = this.getProductService().getProductPriceForProduct(this.getProduct(), this.getPriceList());
      if (productPrice != null) {
         lblPrice.setText("$ " + productPrice.getSellingPriceToDisplay() + " ");
      } else {
         lblPrice.setText("$ ");
      }

      this.lblPhoto = new CLabel(container, 2048);
      this.lblPhoto.setBounds(13, 16, 221, 221);
      this.lblPhoto.setBackground(SWTResourceManager.getColor(1));
      this.lblPhoto.setText("");
      this.initPhoto();
      return container;
   }

   private void initPhoto() {
      this.initLocalPhoto();
   }

   private void initRemotePhoto() {
      try {
         String urlStr = "";
         if (this.getWorkstationConfig().isShowProductPhotos()) {
            urlStr = this.getProductService().getProductPhotoUrl(this.getProduct().getBarCode());
         }

         ImageRegistry imageRegistry = new ImageRegistry();

         try {
            if (!"".equalsIgnoreCase(urlStr)) {
               ImageDescriptor defaultPhoto = ImageDescriptor.createFromURL(new URL(urlStr));
               imageRegistry.put("defaultPhoto", defaultPhoto);
            }
         } catch (MalformedURLException var4) {
            logger.error("Error creando la foto del producto");
            logger.error(var4.getMessage());
            ////logger.error(var4);
         }

         Image image = imageRegistry.get("defaultPhoto");
         this.lblPhoto.setImage(image);
      } catch (Exception var5) {
         logger.error("Error inicializando foto web");
         logger.error(var5.getMessage());
         ////logger.error(var5);
      }

   }

   private void initLocalPhoto() {
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
            this.lblPhoto.setMargins(FVImageUtils.calculateLeftMargin(scaledImage, 221), FVImageUtils.calculateTopMargin(scaledImage, 221), 0, 0);
            this.lblPhoto.setImage(scaledImage);
         } else {
            baseOut = "C://facilvirtual//images//";
            filenameOut = "imageNotAvailable220x220.jpg";
            fileOut = baseOut + filenameOut;
            origImage = new Image(Display.getCurrent(), fileOut);
            scaledImage = FVImageUtils.scaleTo(origImage, 220, 220);
            this.lblPhoto.setMargins(1, 1, 0, 0);
            this.lblPhoto.setImage(scaledImage);
         }
      } catch (Exception var6) {
         logger.error("Error inicializando foto local");
         logger.error(var6.getMessage());
        // //logger.error(var6);
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

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, this.getTitle());
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
      this.createButton(parent, 1, "Cerrar", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(573, 332);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public PriceList getPriceList() {
      return this.priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }
}
