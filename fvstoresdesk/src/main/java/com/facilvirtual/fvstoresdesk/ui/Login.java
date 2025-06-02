package com.facilvirtual.fvstoresdesk.ui;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.Employee;
import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import com.facilvirtual.fvstoresdesk.util.FVMediaUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Login extends AbstractFVApplicationWindow {
   private static final Logger logger = LoggerFactory.getLogger(Login.class);
   private Text txtPassword;
   private Canvas canvasLoginBox;
   private Action actionReactivateLicense;
   private Action actionAbout;
   private Combo comboUsername;
   private Button btnLogin;
   private Color themeColor01;
   private Color themeBack01;
   private Action actionNetworkSettings;

   public Login() {
      super((Shell)null);
      this.setBlockOnOpen(true);
      this.createActions();
      this.addMenuBar();
   }
   @Override
   protected void configureShell(Shell shell) {
      super.configureShell(shell);
      String appName = "FácilVirtual";
      String appModel = "Kioscos";
      String appVersion = "11.2";
      AppConfig appConfig = this.getAppConfigService().getAppConfig();
      if (appConfig == null) {
         this.getAppConfigService().initializeApplication(appName, appModel, appVersion);
      } else {
         if (!appName.equals(appConfig.getAppName())) {
            appConfig.setAppName(appName);
         }

         if (!appModel.equals(appConfig.getAppModel())) {
            appConfig.setAppModel(appModel);
         }

         if (!appVersion.equals(appConfig.getAppVersion())) {
            appConfig.setAppVersion(appVersion);
         }

         this.getAppConfigService().saveAppConfig(appConfig);
      }

      WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
      if (workstationConfig == null) {
         this.getAppConfigService().loadWorkstationConfig(appConfig.getAppModel());
         workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
      }

      if (!workstationConfig.isTrialMode()) {
         this.checkActivationCode(workstationConfig);
      }

      if (!this.isUpdatedData_1_10()) {
         this.updateData_1_10();
      }

      if (!this.isUpdatedData_11_0()) {
         this.updateData_11_0();
      }

      workstationConfig.initValidCodFactElect(this.getAppConfigService().getCurrentInstallationCode());
      this.getAppConfigService().saveWorkstationConfig(workstationConfig);
      this.getAppConfigService().backupDB("data1");
      this.initWindowTitle(shell, workstationConfig);
     // shell.addListener(21, new 1(this));
      shell.setMaximized(true);
   }

   private boolean isUpdatedData_1_10() {
      return this.getAppConfigService().isUpdatedData_1_10();
   }

   private void updateData_1_10() {
      this.getAppConfigService().updateData_1_10();
   }

   private boolean isUpdatedData_11_0() {
      return this.getAppConfigService().isUpdatedData_11_0();
   }

   private void updateData_11_0() {
      this.getAppConfigService().updateData_11_0();
   }
   @Override
   protected Control createContents(Composite parent) {
      logger.info("Iniciando aplicación");

      try {
         logger.info("******* System properties *************");
         logger.info("Java class path: " + System.getProperty("java.class.path"));
         logger.info("Java home: " + System.getProperty("java.home"));
         logger.info("Java vendor: " + System.getProperty("java.vendor"));
         logger.info("Java vendor URL: " + System.getProperty("java.vendor.url"));
         logger.info("Java version: " + System.getProperty("java.version"));
         logger.info("Operating system architecture: " + System.getProperty("os.arch"));
         logger.info("Operating system name: " + System.getProperty("os.name"));
         logger.info("Operating system version: " + System.getProperty("os.version"));
         logger.info("User working directory: " + System.getProperty("user.dir"));
         logger.info("User home directory: " + System.getProperty("user.home"));
         logger.info("User account name: " + System.getProperty("user.name"));
         logger.info("file.separator: " + System.getProperty("file.separator"));
         logger.info("line.separator: " + System.getProperty("line.separator"));
         logger.info("path.separator: " + System.getProperty("path.separator"));
         logger.info("********************");
      } catch (Exception var6) {
         logger.error("No se pudieron recuperar las propiedades del entorno.");
         logger.error(var6.getMessage());
         ////logger.error(var6);
      }

      try {
         AppConfig appConfig = this.getAppConfigService().getAppConfig();
         WorkstationConfig workstationConfig = this.getAppConfigService().getCurrentWorkstationConfig();
         logger.info("******* Licencia FácilVirtual *************");
         logger.info("Aplicación: " + appConfig.getAppModel());
         logger.info("Versión: " + appConfig.getAppVersion());
         logger.info("Nro. licencia: " + this.getAppConfigService().getCurrentInstallationCode());
         logger.info("Fecha de caducidad: " + workstationConfig.getAppLicenseExpirationDateToDisplay());
         logger.info("Código Factura Electrónica: " + workstationConfig.getCodFacturaElectronicaToDisplay());
         logger.info("********************");
      } catch (Exception var5) {
         logger.error("No se pudieron recuperar los datos de la licencia.");
         logger.error(var5.getMessage());
         ////logger.error(var5);
      }

      this.themeColor01 = SWTResourceManager.getColor(1);
      this.themeBack01 = SWTResourceManager.getColor(62, 133, 37);
      parent.setBackground(this.themeBack01);
      Composite container = new Composite(parent, 0);
      container.setBackground(this.themeBack01);
      GridLayout gl_container = new GridLayout(1, false);
      container.setLayout(gl_container);
      this.canvasLoginBox = new Canvas(container, 0);
      this.canvasLoginBox.setBackground(this.themeBack01);
      GridData gd_canvas = new GridData(16777216, 16777216, true, true, 1, 1);
      gd_canvas.heightHint = 200;
      gd_canvas.widthHint = 540;
      this.canvasLoginBox.setLayoutData(gd_canvas);
      this.createLoginBoxContents();
      return container;
   }

   public void createLoginBoxContents() {
      Label lblIngreseSuContrasea = new Label(this.canvasLoginBox, 0);
      lblIngreseSuContrasea.setForeground(this.themeColor01);
      lblIngreseSuContrasea.setBackground(this.themeBack01);
      lblIngreseSuContrasea.setFont(SWTResourceManager.getFont("Tahoma", 14, 0));
      lblIngreseSuContrasea.setBounds(346, 19, 146, 27);
      lblIngreseSuContrasea.setText("Iniciar sesión");
      this.txtPassword = new Text(this.canvasLoginBox, 4196352);
      this.txtPassword.addTraverseListener(new TraverseListener() {
         @Override
         public void keyTraversed(TraverseEvent e) {
            if (e.detail == SWT.TRAVERSE_RETURN) {
               processLogin();
            }
         }
      });
      this.txtPassword.setBounds(346, 117, 184, 21);
      this.btnLogin = new Button(this.canvasLoginBox, 0);
      this.btnLogin.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseDown(MouseEvent e) {
            processLogin();
         }
      });
      // this.btnLogin.addMouseTrackListener(new MouseTrackAdapter() {
      //    @Override
      //    public void mouseEnter(MouseEvent e) {
      //       Login.access$2(Login.this).setImage(
      //          SWTResourceManager.getImage(Login.this.getImagesDir() + "login/btn_login_over.gif")
      //       );
      //    }

      //    @Override
      //    public void mouseExit(MouseEvent e) {
      //       Login.access$2(Login.this).setImage(
      //          SWTResourceManager.getImage(Login.this.getImagesDir() + "login/btn_login.gif")
      //       );
      //    }
      // });
      this.btnLogin.setImage(SWTResourceManager.getImage(this.getImagesDir() + "login/btn_login.gif"));
      this.btnLogin.setBackground(this.themeBack01);
      this.btnLogin.setFont(SWTResourceManager.getFont("Tahoma", 10, 0));
      this.btnLogin.addSelectionListener(new SelectionAdapter() {
      
         @Override
         public void widgetSelected(SelectionEvent e) {
            initUsernames();
         }
      });
      this.btnLogin.setBounds(346, 150, 118, 31);
      Label lblNewLabel = new Label(this.canvasLoginBox, 0);
      lblNewLabel.setBackground(this.themeBack01);
      lblNewLabel.setBounds(287, 52, 48, 48);
      lblNewLabel.setText("");
      Image image = new Image(Display.getCurrent(), this.getImagesDir() + "login/icon_login.gif");
      lblNewLabel.setImage(image);
      Label lblContrasea = new Label(this.canvasLoginBox, 0);
      lblContrasea.setForeground(this.themeColor01);
      lblContrasea.setBackground(this.themeBack01);
      lblContrasea.setFont(SWTResourceManager.getFont("Tahoma", 8, 0));
      lblContrasea.setBounds(346, 98, 68, 13);
      lblContrasea.setText("Contraseña");
      Label label = new Label(this.canvasLoginBox, 0);
      label.setBounds(265, 10, 2, 180);
      Image sepLoginImg = new Image(Display.getCurrent(), this.getImagesDir() + "login/sep_login.gif");
      label.setImage(sepLoginImg);
      Image logoimg = new Image(Display.getCurrent(), this.getImagesDir() + "login/logo_login.gif");
      Canvas canvas = new Canvas(this.canvasLoginBox, 0);
      canvas.setBackground(this.themeBack01);
      canvas.setBounds(10, 10, 240, 180);
      Label lblLogo = new Label(canvas, 0);
      lblLogo.setBounds(55, 10, 175, 150);
      lblLogo.setText("");
      lblLogo.setImage(logoimg);
      this.comboUsername = new Combo(this.canvasLoginBox, 8);
      this.comboUsername.setBounds(346, 71, 184, 21);
      Label lblUsuario = new Label(this.canvasLoginBox, 0);
      lblUsuario.setBackground(this.themeBack01);
      lblUsuario.setForeground(this.themeColor01);
      lblUsuario.setBounds(346, 52, 107, 13);
      lblUsuario.setText("Nombre de usuario");
      this.initUsernames();
   }

   private void initUsernames() {
      List<Employee> employees = this.getAccountService().getActiveEmployees();
      int selectedIdx = 0;

      for(Iterator var4 = employees.iterator(); var4.hasNext(); ++selectedIdx) {
         Employee employee = (Employee)var4.next();
         this.comboUsername.add(WordUtils.capitalize(employee.getUsername()));
         if ("Administrador".equalsIgnoreCase(employee.getUsername())) {
            this.comboUsername.select(selectedIdx);
         }
      }

   }

   private void createActions() {
      this.actionReactivateLicense = new Action("Reactivar") {
         @Override
         public void run() {
            reactivateLicense();
         }
      };
      this.actionAbout = new Action("Acerca de FácilVirtual"){
         @Override
         public void run() {
            about();
         }
      };
      this.actionNetworkSettings = new Action("Configuración de red"){
         @Override
         public void run() {
            editNetworkSettings();
         }
      };
   }
   private void editNetworkSettings() {
      EditNetworkSettings dialog = new EditNetworkSettings(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.open();
   }
   private void about() {
      AboutFacilVirtual dialog = new AboutFacilVirtual(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.setParentWindow(this);
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.alert("Se desactivó la licencia de uso");
      }

   }

   private void reactivateLicense() {
      ReactivateLicense dialog = new ReactivateLicense(this.getShell());
      dialog.setBlockOnOpen(true);
      dialog.setParentWindow(this);
      dialog.open();
      if ("OK".equalsIgnoreCase(dialog.getAction())) {
         this.alert("La activación se realizó con éxito");
      }

   }
   @Override
   protected MenuManager createMenuManager() {
      MenuManager menuManager = new MenuManager("menu");
      MenuManager fileMenu = new MenuManager("&Archivo");
      menuManager.add(fileMenu);
      Action exitAction = new Action("&Salir") {
         @Override
         public void run() {
            exit();
         }
      };
      fileMenu.add(this.actionNetworkSettings);
      fileMenu.add(new Separator());
      fileMenu.add(exitAction);
      MenuManager menuManager_1 = new MenuManager("Ayuda");
      menuManager.add(menuManager_1);
      menuManager_1.add(this.actionReactivateLicense);
      menuManager_1.add(new Separator());
      menuManager_1.add(this.actionAbout);
      menuManager.updateAll(false);
      return menuManager;
   }

   
   public static void main(String[] args) {
      try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
         Login window = new Login();
         window.open();
      } catch (BeansException var4) {
         
         logger.error("Error iniciando Login");
         logger.error("Message: " + var4.getMessage());
         logger.error("Exception: " + var4);
         MessageBox dialog = new MessageBox(new Shell(16384));
         dialog.setText("FácilVirtual - Aviso");
         dialog.setMessage("No se ha podido establecer la conexión con la base de datos. Si el error persiste consulta al soporte técnico.");
         dialog.open();
         EditNetworkSettings dialog2 = new EditNetworkSettings(new Shell(16384));
         dialog2.setBlockOnOpen(true);
         dialog2.open();
         logger.info("Abortando el sistema.");
         System.exit(1);
      }

   }
   @Override
   public void initWindowTitle(Shell shell, WorkstationConfig workstationConfig) {
      String sessionInfo = "Caja: " + this.getWorkstationConfig().getCashNumberToDisplay();
      if (workstationConfig.isTrialMode()) {
         Date today = new Date();
         int days = workstationConfig.getTrialExpirationDaysQty(today);
         this.initTitle(shell, "FácilVirtual - Versión de evaluación (vence en " + days + " días) - " + sessionInfo);
      } else {
         boolean expiredLicense = workstationConfig.isExpiredLicense();
         if (expiredLicense) {
            this.initTitle(shell, "FácilVirtual - Licencia caducada - " + sessionInfo);
         } else {
            this.initTitle(shell, "FácilVirtual - Iniciar sesión - " + sessionInfo);
         }
      }

   }

   private void checkActivationCode(WorkstationConfig workstationConfig) {
      String installationCode = workstationConfig.getCurrentInstallationCode();
      String activationCode = workstationConfig.getLicenseActivationCode();
      if (!workstationConfig.isValidActivationCode(installationCode, activationCode)) {
         workstationConfig.setLicenseActivationCode((String)null);
         workstationConfig.setLicenseExpirationDate((Date)null);
         workstationConfig.setInstallationDate(new Date());
         this.getAppConfigService().saveWorkstationConfig(workstationConfig);
      }

   }
   @Override
   protected Point getInitialSize() {
      return new Point(800, 600);
   }
   @Override
   protected boolean canHandleShellCloseEvent() {
      return false;
   }

   private void processLogin() {
      Employee employee = this.getAccountService().getEmployeeByUsername(this.comboUsername.getText());
      AppConfig appConfig = this.getAppConfigService().getAppConfig();
      if ("".equalsIgnoreCase(this.txtPassword.getText().trim())) {
         this.alert("Ingresa tu contraseña");
      } else {
         CashRegister cashR;
         if (this.txtPassword.getText().trim().equals(appConfig.getManagerPassword())) {
            this.getShell().dispose();
            cashR = new CashRegister(false);
            cashR.setCashier((Employee)null);
            cashR.setManagerMode(true);
            cashR.open();
         } else if (!this.txtPassword.getText().trim().equals(employee.getPassword())) {
            logger.info("Acceso denegado para usuario: " + employee.getUsername() + ", " + "con password: " + this.txtPassword.getText());
            this.alert("Acceso denegado");
            this.txtPassword.setText("");
         } else if (this.txtPassword.getText().trim().equals(employee.getPassword()) && !employee.isAllowLogin()) {
            logger.info("Usuario bloqueado, para usuario: " + employee.getUsername() + ", " + "con password: " + this.txtPassword.getText());
            this.alert("Tu usuario fue bloqueado temporalmente. Consulta al Administrador del sistema.");
            this.txtPassword.setText("");
         } else if (this.getWorkstationConfig().isTrialMode() && !this.getWorkstationConfig().isInTrialPeriod()) {
            this.alert("La versión de evaluación ha caducado. Reactiva la licencia desde el menú Ayuda.");
            this.txtPassword.setText("");
         } else if (!this.getWorkstationConfig().isTrialMode() && this.getWorkstationConfig().isExpiredLicense()) {
            this.alert("La licencia de uso ha caducado. Reactiva la licencia desde el menú Ayuda.");
            this.txtPassword.setText("");
         } else {
            FVMediaUtils.playSound("login.wav");
            this.getShell().dispose();
            cashR = new CashRegister(false);
            cashR.setCashier(employee);
            cashR.setManagerMode(false);
            cashR.setFromLogin(true);
            cashR.open();
         }
      }

   }

   private void exit() {
      if (FVConfirmDialog.openQuestion(this.getShell(), "Salir", "¿Quieres salir del programa?")) {
         logger.info("Cerrando aplicación");
         System.exit(0);
      }

   }
}
