/*    */ package com.facilvirtual.fvstoresdesk.service;
/*    */ 
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service("helloWorldService")
/*    */ public class HelloWorldService
/*    */ {
/*    */   private String name;
/*    */   
/*    */   public String getName() {
/* 11 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 15 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String sayHello() {
/* 19 */     return "Hello from HelloWorld Service! " + this.name;
/*    */   }
/*    */ }


/* Location:              C:\facilvirtual\facilvirtual\!\com\facilvirtual\fvstoresdesk\service\HelloWorldService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */