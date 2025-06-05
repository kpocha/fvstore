package com.facilvirtual.fvstoresdesk.ui.screens.settings;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.facilvirtual.fvstoresdesk.model.DebitCard;
import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;
public class AddNewDebitCard extends AbstractFVDialog {
   private String action = "";
   private Text txtDebitCardName;
   private DebitCard debitCard;

   public AddNewDebitCard(Shell parentShell) {
      super(parentShell);
   }

   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      FormLayout fl_container = new FormLayout();
      container.setLayout(fl_container);
      Label lblTitle = new Label(container, 0);
      lblTitle.setFont(SWTResourceManager.getFont("Tahoma", 16, 0));
      FormData fd_lblTitle = new FormData();
      fd_lblTitle.right = new FormAttachment(0, 310);
      fd_lblTitle.top = new FormAttachment(0, 32);
      fd_lblTitle.left = new FormAttachment(0, 66);
      lblTitle.setLayoutData(fd_lblTitle);
      lblTitle.setText("Agregar tarjeta de débito");
      Label lblNombreDelArchivo = new Label(container, 0);
      lblNombreDelArchivo.setText("Nombre de la tarjeta:");
      lblNombreDelArchivo.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_lblNombreDelArchivo = new FormData();
      fd_lblNombreDelArchivo.top = new FormAttachment(lblTitle, 38);
      fd_lblNombreDelArchivo.left = new FormAttachment(0, 54);
      fd_lblNombreDelArchivo.right = new FormAttachment(100, -200);
      lblNombreDelArchivo.setLayoutData(fd_lblNombreDelArchivo);
      this.txtDebitCardName = new Text(container, 2048);
      this.txtDebitCardName.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      FormData fd_txtCreditCardName = new FormData();
      fd_txtCreditCardName.top = new FormAttachment(lblNombreDelArchivo, 6);
      fd_txtCreditCardName.right = new FormAttachment(100, -67);
      fd_txtCreditCardName.left = new FormAttachment(0, 54);
      this.txtDebitCardName.setLayoutData(fd_txtCreditCardName);
      return container;
   }

   private void processDialog() {
      if (this.validateFields()) {
         this.setAction("OK");
         DebitCard debitCard = new DebitCard();
         debitCard.setName(this.txtDebitCardName.getText().trim());
         debitCard.setActive(true);
         this.setDebitCard(debitCard);
         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if ("".equals(this.txtDebitCardName.getText().trim())) {
         valid = false;
         this.alert("Ingresa el nombre de la tarjeta");
      }

      if (valid) {
         DebitCard debitCard = this.getOrderService().getDebitCardByName(this.txtDebitCardName.getText().trim());
         if (debitCard != null) {
            valid = false;
            this.alert("Ya existe una tarjeta de débito con ese nombre");
         }
      }

      return valid;
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Nueva tarjeta de débito");
   }

   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processDialog();
      } else {
         this.close();
      }

   }

   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 0, "Aceptar", false);
      button.setText("Guardar");
      this.createButton(parent, 1, "Cancelar", false);
   }

   protected Point getInitialSize() {
      return new Point(402, 303);
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public DebitCard getDebitCard() {
      return this.debitCard;
   }

   public void setDebitCard(DebitCard debitCard) {
      this.debitCard = debitCard;
   }
}
