package com.facilvirtual.fvstoresdesk.test;

import com.facilvirtual.fvstoresdesk.service.HelloWorldService;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldApp extends ApplicationWindow {
   public HelloWorldApp() {
      super((Shell)null);
   }

   public void run() {
      this.setBlockOnOpen(true);
      this.open();
      Display.getCurrent().dispose();
   }

   protected Control createContents(Composite parent) {
      Label label = new Label(parent, 16777216);
      label.setText("Hello, World");
      parent.setSize(400, 250);
      return label;
   }

   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
      HelloWorldService obj = (HelloWorldService)context.getBean("helloWorldService");
      obj.setName("Spring 3.2.3");
      String message = obj.sayHello();
      System.out.println(message);
      (new HelloWorldApp()).run();
   }
}