package com.facilvirtual.fvstoresdesk.ui.components.dialogs.confirmation;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.facilvirtual.fvstoresdesk.ui.base.AbstractFVDialog;

public class FVConfirmDialog extends AbstractFVDialog {
   private String title;
   private String question;

   public FVConfirmDialog(Shell parentShell) {
      super(parentShell);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      Composite container = (Composite)super.createDialogArea(parent);
      container.setLayout((Layout)null);
      Label lblNewLabel = new Label(container, 16777216);
      lblNewLabel.setAlignment(16384);
      lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      lblNewLabel.setBounds(52, 13, 222, 16);
      lblNewLabel.setText(this.getQuestion());
      Label lblNewLabel_1 = new Label(container, 0);
      lblNewLabel_1.setBounds(5, 7, 41, 47);
      Image image = new Image(Display.getCurrent(), this.getImagesDir() + "icon_question.gif");
      lblNewLabel_1.setImage(image);
      lblNewLabel_1.setText("");
      return container;
   }

   private void processConfirm() {
      this.setAction("OK");
      this.close();
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, this.getTitle());
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId == 0) {
         this.processConfirm();
      } else {
         this.close();
      }

   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      Button button = this.createButton(parent, 0, "Si", true);
      button.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      this.createButton(parent, 1, "No", false);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(438, 144);
   }
   @Override
   public String getTitle() {
      return this.title;
   }
   @Override
   public void setTitle(String title) {
      this.title = title;
   }

   public String getQuestion() {
      return this.question;
   }

   public void setQuestion(String question) {
      this.question = question;
   }

   public static boolean openQuestion(Shell shell, String title, String question) {
      FVConfirmDialog confirmDialog = new FVConfirmDialog(shell);
      confirmDialog.setTitle(title);
      confirmDialog.setQuestion(question);
      confirmDialog.open();
      return "OK".equalsIgnoreCase(confirmDialog.getAction());
   }
}
