package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.CreditCard;
import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.DebitCard;
import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.util.FVMathUtils;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class PaymentPrompt extends AbstractFVDialog {
   protected Color themeBack;
   protected Color themeBack02;
   protected Color themeText;
   protected Color themeHeaderBack;
   protected Color themeRowOdd;
   protected Color themeRowEven;
   protected Color themeTableBack;
   protected Color themeTableText;
   protected Color themeInputReadOnlyBack;
   protected Color themeInputReadOnlyText;
   private String action = "";
   private Order currentOrder;
   private Text txtCashAmount;
   private Text txtCreditCardAmount;
   private Text txtOnAccountAmount;
   private Text txtDebitCardAmount;
   private Text txtCheckAmount;
   private Text txtTicketsAmount;
   private Text txtTotal;
   private Text txtTendered;
   private Text txtToPay;
   private Text lastFocusControl;
   private Combo comboCreditCard;
   private Combo comboDebitCard;
   private Button btnOnAccount;

   public PaymentPrompt(Shell parentShell) {
      super(parentShell);
      this.initTheme();
   }
   @Override
   protected void initTheme() {
      this.themeBack = SWTResourceManager.getColor(233, 237, 234);
      this.themeBack02 = SWTResourceManager.getColor(246, 248, 247);
      this.themeText = SWTResourceManager.getColor(62, 133, 37);
      this.themeHeaderBack = SWTResourceManager.getColor(62, 133, 37);
      this.themeTableBack = SWTResourceManager.getColor(255, 255, 255);
      this.themeTableText = SWTResourceManager.getColor(21, 25, 36);
      this.themeRowOdd = SWTResourceManager.getColor(255, 255, 255);
      this.themeRowEven = SWTResourceManager.getColor(246, 248, 247);
      this.themeInputReadOnlyBack = SWTResourceManager.getColor(233, 237, 234);
      this.themeInputReadOnlyText = SWTResourceManager.getColor(21, 25, 36);
   }
   @Override
   protected Control createDialogArea(Composite parent) {
      this.initTheme();
      parent.setBackground(this.themeBack);
      Composite container = (Composite)super.createDialogArea(parent);
      container.setBackground(this.themeBack);
      container.setLayout((Layout)null);
      Button btnCreditCard = new Button(container, 0);
     // btnCreditCard.addSelectionListener(new 1(this));
      btnCreditCard.setBounds(10, 60, 163, 35);
      btnCreditCard.setText("Tarjeta de Crédito");
      btnCreditCard.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      this.btnOnAccount = new Button(container, 0);
      //this.btnOnAccount.addSelectionListener(new 2(this));
      this.btnOnAccount.setBounds(10, 150, 163, 35);
      this.btnOnAccount.setText("Cuenta Corriente");
      this.btnOnAccount.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      Button btnDebitCard = new Button(container, 0);
      //btnDebitCard.addSelectionListener(new 3(this));
      btnDebitCard.setBounds(10, 200, 163, 35);
      btnDebitCard.setText("Tarjeta de Débito");
      btnDebitCard.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      Button btnCheck = new Button(container, 0);
      //btnCheck.addSelectionListener(new 4(this));
      btnCheck.setBounds(10, 290, 163, 35);
      btnCheck.setText("Cheque");
      btnCheck.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      Button btnTickets = new Button(container, 0);
      //btnTickets.addSelectionListener(new 5(this));
      btnTickets.setBounds(10, 340, 163, 35);
      btnTickets.setText("Tickets");
      btnTickets.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      Button btnCash = new Button(container, 0);
      //btnCash.addSelectionListener(new 6(this));
      btnCash.setBounds(10, 10, 163, 35);
      btnCash.setText("Efectivo");
      btnCash.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      this.txtCashAmount = new Text(container, 133120);
      //this.txtCashAmount.addFocusListener(new 7(this));
     // this.txtCashAmount.addKeyListener(new 8(this));
      this.txtCashAmount.setText("");
      this.txtCashAmount.setFont(SWTResourceManager.getFont("Arial", 18, 0));
      this.txtCashAmount.setBounds(179, 10, 119, 35);
      //this.txtCashAmount.addTraverseListener(new 9(this));
      this.txtCreditCardAmount = new Text(container, 133120);
      //this.txtCreditCardAmount.addFocusListener(new 10(this));
      //this.txtCreditCardAmount.addKeyListener(new 11(this));
      this.txtCreditCardAmount.setFont(SWTResourceManager.getFont("Arial", 18, 0));
      this.txtCreditCardAmount.setBounds(179, 60, 119, 35);
      //this.txtCreditCardAmount.addTraverseListener(new 12(this));
      this.txtOnAccountAmount = new Text(container, 133120);
      //this.txtOnAccountAmount.addKeyListener(new 13(this));
      //t/his.txtOnAccountAmount.addFocusListener(new 14(this));
      this.txtOnAccountAmount.setFont(SWTResourceManager.getFont("Arial", 18, 0));
      this.txtOnAccountAmount.setBounds(179, 150, 119, 35);
      //this.txtOnAccountAmount.addTraverseListener(new 15(this));
      this.txtDebitCardAmount = new Text(container, 133120);
     // this.txtDebitCardAmount.addKeyListener(new 16(this));
      //this.txtDebitCardAmount.addFocusListener(new 17(this));
      this.txtDebitCardAmount.setFont(SWTResourceManager.getFont("Arial", 18, 0));
      this.txtDebitCardAmount.setBounds(179, 200, 119, 35);
     // this.txtDebitCardAmount.addTraverseListener(new 18(this));
      this.txtCheckAmount = new Text(container, 133120);
      //this.txtCheckAmount.addKeyListener(new 19(this));
      //this.txtCheckAmount.addFocusListener(new 20(this));
      this.txtCheckAmount.setFont(SWTResourceManager.getFont("Arial", 18, 0));
      this.txtCheckAmount.setBounds(179, 290, 119, 35);
     //this.txtCheckAmount.addTraverseListener(new 21(this));
      this.txtTicketsAmount = new Text(container, 133120);
      //this.txtTicketsAmount.addKeyListener(new 22(this));
      //this.txtTicketsAmount.addFocusListener(new 23(this));
      this.txtTicketsAmount.setFont(SWTResourceManager.getFont("Arial", 18, 0));
      this.txtTicketsAmount.setBounds(179, 340, 119, 35);
      //this.txtTicketsAmount.addTraverseListener(new 24(this));
      Canvas canvas = new Canvas(container, 0);
      canvas.setBackground(SWTResourceManager.getColor(2));
      canvas.setBounds(403, 10, 230, 70);
      Canvas canvas_3 = new Canvas(canvas, 0);
      canvas_3.setBackground(this.themeHeaderBack);
      canvas_3.setBounds(0, 0, 231, 24);
      Label lblTotalAPagar = new Label(canvas_3, 0);
      lblTotalAPagar.setAlignment(16777216);
      lblTotalAPagar.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      lblTotalAPagar.setForeground(SWTResourceManager.getColor(1));
      lblTotalAPagar.setBackground(this.themeHeaderBack);
      lblTotalAPagar.setBounds(10, 2, 211, 19);
      lblTotalAPagar.setText("Total a pagar");
      this.txtTotal = new Text(canvas, 131072);
      this.txtTotal.setText("$0,00");
      this.txtTotal.setFont(SWTResourceManager.getFont("Arial", 24, 0));
      this.txtTotal.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.txtTotal.setBackground(SWTResourceManager.getColor(2));
      this.txtTotal.setBounds(10, 32, 221, 35);
      Label label = new Label(container, 258);
      label.setBounds(0, 461, 625, 2);
      Button btnAceptar = new Button(container, 0);
      //TODO: arreglar
      //btnAceptar.addSelectionListener(new 25(this));
      btnAceptar.setText("Confirmar");
      btnAceptar.setFont(SWTResourceManager.getFont("Arial", 14, 0));
      btnAceptar.setBounds(197, 475, 121, 40);
      Button btnCancelar = new Button(container, 0);
      //btnCancelar.addSelectionListener(new 26(this));
      btnCancelar.setText("Cancelar");
      btnCancelar.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      btnCancelar.setBounds(324, 475, 121, 40);
      this.comboCreditCard = new Combo(container, 8);
      this.comboCreditCard.setFont(SWTResourceManager.getFont("Arial", 14, 0));
      this.comboCreditCard.setBounds(179, 100, 207, 37);
      this.comboCreditCard.add("");
      this.comboCreditCard.select(0);
      List<CreditCard> creditCards = this.getOrderService().getActiveCreditCards();
      Iterator var16 = creditCards.iterator();

      while(var16.hasNext()) {
         CreditCard card = (CreditCard)var16.next();
         this.comboCreditCard.add(card.getName());
      }

      this.comboDebitCard = new Combo(container, 8);
      this.comboDebitCard.setFont(SWTResourceManager.getFont("Arial", 14, 0));
      this.comboDebitCard.setBounds(179, 240, 207, 33);
      this.comboDebitCard.add("");
      this.comboDebitCard.select(0);
      List<DebitCard> debitCards = this.getOrderService().getActiveDebitCards();
      Iterator var17 = debitCards.iterator();

      while(var17.hasNext()) {
         DebitCard card = (DebitCard)var17.next();
         this.comboDebitCard.add(card.getName());
      }

      Canvas canvas_1 = new Canvas(container, 0);
      canvas_1.setBackground(SWTResourceManager.getColor(2));
      canvas_1.setBounds(403, 86, 230, 70);
      Canvas canvas_2 = new Canvas(canvas_1, 0);
      canvas_2.setBackground(this.themeHeaderBack);
      canvas_2.setBounds(0, 0, 231, 24);
      Label lblPagado = new Label(canvas_2, 0);
      lblPagado.setText("Total pagado");
      lblPagado.setForeground(SWTResourceManager.getColor(1));
      lblPagado.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      lblPagado.setBackground(this.themeHeaderBack);
      lblPagado.setAlignment(16777216);
      lblPagado.setBounds(10, 2, 211, 19);
      this.txtTendered = new Text(canvas_1, 131072);
      this.txtTendered.setText("$0,00");
      this.txtTendered.setForeground(SWTResourceManager.getColor(50, 205, 50));
      this.txtTendered.setFont(SWTResourceManager.getFont("Arial", 24, 0));
      this.txtTendered.setBackground(SWTResourceManager.getColor(2));
      this.txtTendered.setBounds(10, 32, 221, 35);
      Canvas canvas_4 = new Canvas(container, 0);
      canvas_4.setBackground(SWTResourceManager.getColor(2));
      canvas_4.setBounds(403, 162, 230, 70);
      Canvas canvas_5 = new Canvas(canvas_4, 0);
      canvas_5.setBackground(this.themeHeaderBack);
      canvas_5.setBounds(0, 0, 231, 24);
      Label lblRestanPagar = new Label(canvas_5, 0);
      lblRestanPagar.setText("Saldo");
      lblRestanPagar.setForeground(SWTResourceManager.getColor(1));
      lblRestanPagar.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      lblRestanPagar.setBackground(this.themeHeaderBack);
      lblRestanPagar.setAlignment(16777216);
      lblRestanPagar.setBounds(10, 2, 211, 19);
      this.txtToPay = new Text(canvas_4, 131072);
      this.txtToPay.setText("$0,00");
      this.txtToPay.setForeground(SWTResourceManager.getColor(220, 20, 60));
      this.txtToPay.setFont(SWTResourceManager.getFont("Arial", 24, 0));
      this.txtToPay.setBackground(SWTResourceManager.getColor(2));
      this.txtToPay.setBounds(10, 32, 221, 35);
      Button button = new Button(container, 0);
      //button.addSelectionListener(new 27(this));
      button.setText("50");
      button.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      button.setBounds(10, 410, 120, 35);
      Button button_1 = new Button(container, 0);
      //button_1.addSelectionListener(new 28(this));
      button_1.setText("100");
      button_1.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      button_1.setBounds(136, 410, 120, 35);
      Button button_2 = new Button(container, 0);
      //button_2.addSelectionListener(new 29(this));
      button_2.setText("200");
      button_2.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      button_2.setBounds(262, 410, 120, 35);
      Button button_3 = new Button(container, 0);
      //button_3.addSelectionListener(new 30(this));
      button_3.setText("500");
      button_3.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      button_3.setBounds(388, 410, 120, 35);
      Button button_4 = new Button(container, 0);
      //button_4.addSelectionListener(new 31(this));
      button_4.setText("1000");
      button_4.setFont(SWTResourceManager.getFont("Arial", 12, 0));
      button_4.setBounds(514, 410, 120, 35);
      int x = this.getParentShell().getClientArea().width - 660;
      int y = 25;
      this.getShell().setBounds(x, y, 650, 615);
      this.initialize();
      return container;
      //TODO: Arreglar
   }

   private void completePaymentAmount(Text txt) {
      try {
         txt.setText("0,00");
         this.updatedTender();
         if (this.getToPay() > 0.0) {
            txt.setText(this.getToPayToDisplay());
            this.updatedTender();
         }
      } catch (Exception var3) {
      }

   }

   private void amountKeyPressed(String amount) {
      this.lastFocusControl.setText(amount);
      this.updatedTender();
   }

   private void initialize() {
      this.txtTotal.setText("$ " + this.getCurrentOrder().getTotalToDisplay());
      this.txtToPay.setText("$ " + this.getCurrentOrder().getTotalToDisplay());
      this.updatedTender();
      this.txtCashAmount.setFocus();
      this.lastFocusControl = this.txtCashAmount;
      if (!this.getCurrentOrder().getCustomer().isAllowOnAccountOperations()) {
         this.btnOnAccount.setEnabled(false);
         this.txtOnAccountAmount.setEnabled(false);
      }

   }

   private void updatedTender() {
      this.txtTendered.setText("$ " + this.getTotalTenderedToDisplay());
      this.txtToPay.setText("$ " + this.getChangeToDisplay());
      if (this.roundValue(this.getToPay()) > 0.0) {
         this.txtToPay.setForeground(SWTResourceManager.getColor(220, 20, 60));
      } else {
         this.txtToPay.setForeground(SWTResourceManager.getColor(50, 205, 50));
      }

   }

   private double roundValue(double valueToRound) {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(valueToRound));
      return Double.valueOf(str.replaceAll(",", "\\."));
   }

   private double getCashAmount() {
      double cashAmount = 0.0;

      try {
         cashAmount = Double.parseDouble(this.txtCashAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return cashAmount;
   }

   private double getCreditCardAmount() {
      double creditCardAmount = 0.0;

      try {
         creditCardAmount = Double.parseDouble(this.txtCreditCardAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return creditCardAmount;
   }

   private double getOnAccountAmount() {
      double onAccountAmount = 0.0;

      try {
         onAccountAmount = Double.parseDouble(this.txtOnAccountAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return onAccountAmount;
   }

   private double getDebitCardAmount() {
      double debitCardAmount = 0.0;

      try {
         debitCardAmount = Double.parseDouble(this.txtDebitCardAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return debitCardAmount;
   }

   private double getCheckAmount() {
      double checkAmount = 0.0;

      try {
         checkAmount = Double.parseDouble(this.txtCheckAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return checkAmount;
   }

   private double getTicketsAmount() {
      double ticketsAmount = 0.0;

      try {
         ticketsAmount = Double.parseDouble(this.txtTicketsAmount.getText().trim().replaceAll(",", "."));
      } catch (Exception var4) {
      }

      return ticketsAmount;
   }

   private double getTotalTendered() {
      return this.getCashAmount() + this.getCreditCardAmount() + this.getOnAccountAmount() + this.getDebitCardAmount() + this.getCheckAmount() + this.getTicketsAmount();
   }

   private String getTotalTenderedToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getTotalTendered()));
      str = str.replaceAll("\\.", ",");
      return this.getTotalTendered() != 0.0 ? str : "0,00";
   }

   private double getToPay() {
      return this.getCurrentOrder().getTotal() - this.getTotalTendered();
   }

   private String getToPayToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getToPay()));
      str = str.replaceAll("\\.", ",");
      return this.getToPay() != 0.0 ? str : "0,00";
   }

   private String getChangeToDisplay() {
      DecimalFormat formatter = new DecimalFormat("0.00");
      String str = String.valueOf(formatter.format(this.getToPay() * -1.0));
      str = str.replaceAll("\\.", ",");
      return this.getToPay() != 0.0 ? str : "0,00";
   }

   private void processPayment() {
      if (this.validateFields()) {
         this.setAction("OK");
         Order order = this.getCurrentOrder();
         order.setCashAmount(FVMathUtils.roundValue(this.getCashAmount()));
         order.setCreditCardAmount(FVMathUtils.roundValue(this.getCreditCardAmount()));
         order.setOnAccountAmount(FVMathUtils.roundValue(this.getOnAccountAmount()));
         order.setDebitCardAmount(FVMathUtils.roundValue(this.getDebitCardAmount()));
         order.setCheckAmount(FVMathUtils.roundValue(this.getCheckAmount()));
         order.setTicketsAmount(FVMathUtils.roundValue(this.getTicketsAmount()));
         String creditCardName = this.comboCreditCard.getText();
         order.setCreditCard(this.getOrderService().getCreditCardByName(creditCardName));
         String debitCardName = this.comboDebitCard.getText();
         order.setDebitCard(this.getOrderService().getDebitCardByName(debitCardName));
         if (order.getNetTotalTendered() < order.getTotal()) {
            if (order.getCashAmount() <= order.getToPay()) {
               order.setNetCashAmount(FVMathUtils.roundValue(this.getCashAmount()));
            } else {
               order.setNetCashAmount(order.getToPay());
            }
         }

         if (order.getNetTotalTendered() < order.getTotal()) {
            if (order.getCreditCardAmount() <= order.getToPay()) {
               order.setNetCreditCardAmount(FVMathUtils.roundValue(this.getCreditCardAmount()));
            } else {
               order.setNetCreditCardAmount(order.getToPay());
            }
         }

         if (order.getNetTotalTendered() < order.getTotal()) {
            if (order.getOnAccountAmount() <= order.getToPay()) {
               order.setNetOnAccountAmount(FVMathUtils.roundValue(this.getOnAccountAmount()));
            } else {
               order.setNetOnAccountAmount(order.getToPay());
            }
         }

         if (order.getNetTotalTendered() < order.getTotal()) {
            if (order.getDebitCardAmount() <= order.getToPay()) {
               order.setNetDebitCardAmount(FVMathUtils.roundValue(this.getDebitCardAmount()));
            } else {
               order.setNetDebitCardAmount(order.getToPay());
            }
         }

         if (order.getNetTotalTendered() < order.getTotal()) {
            if (order.getCheckAmount() <= order.getToPay()) {
               order.setNetCheckAmount(FVMathUtils.roundValue(this.getCheckAmount()));
            } else {
               order.setNetCheckAmount(order.getToPay());
            }
         }

         if (order.getNetTotalTendered() < order.getTotal()) {
            if (order.getTicketsAmount() <= order.getToPay()) {
               order.setNetTicketsAmount(FVMathUtils.roundValue(this.getTicketsAmount()));
            } else {
               order.setNetTicketsAmount(order.getToPay());
            }
         }

         if (order.getSaleDate() == null) {
            Date today = new Date();
            order.setSaleDate(today);
         }

         this.close();
      }

   }

   public boolean validateFields() {
      boolean valid = true;
      if (this.roundValue(this.getToPay()) > 0.0) {
         this.alert("Restan pagar: $" + this.getToPayToDisplay());
         valid = false;
      }

      String debitCardName;
      if (valid) {
         debitCardName = this.comboCreditCard.getText();
         if (this.getCreditCardAmount() > 0.0 && "".equals(debitCardName)) {
            this.alert("Selecciona la tarjeta de crédito");
            valid = false;
         }
      }

      if (valid) {
         debitCardName = this.comboDebitCard.getText();
         if (this.getDebitCardAmount() > 0.0 && "".equals(debitCardName)) {
            this.alert("Selecciona la tarjeta de débito");
            valid = false;
         }
      }

      if (!"".equalsIgnoreCase(this.txtOnAccountAmount.getText())) {
         Customer customer = this.getCurrentOrder().getCustomer();
         if (customer.isOnAccountLimited() && this.getNetOnAccountAmount() > customer.getMaxOnAccountAllowed()) {
            valid = false;
            this.alert("El monto de cta. cte. supera el límite. Monto máximo permitido: $" + customer.getMaxOnAccountAllowedToDisplay());
         }
      }

      return valid;
   }

   private double getNetOnAccountAmount() {
      return this.getOnAccountAmount() >= this.getCurrentOrder().getTotal() ? this.getCurrentOrder().getTotal() : this.getOnAccountAmount();
   }

   @Override protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      this.initTitle(newShell, "Información de Pago");
   }
   @Override
   protected void buttonPressed(int buttonId) {
      if (buttonId != 0) {
         this.close();
      }

   }
   @Override
   protected void createButtonsForButtonBar(Composite parent) {
      parent.setBackground(this.themeBack);
   }
   @Override
   protected Point getInitialSize() {
      return new Point(650, 615);
   }
   @Override
   public String getAction() {
      return this.action;
   }
   @Override
   public void setAction(String action) {
      this.action = action;
   }

   public Order getCurrentOrder() {
      return this.currentOrder;
   }

   public void setCurrentOrder(Order currentOrder) {
      this.currentOrder = currentOrder;
   }
}
