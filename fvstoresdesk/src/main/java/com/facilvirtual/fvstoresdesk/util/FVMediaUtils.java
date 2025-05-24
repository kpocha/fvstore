/*    */ package com.facilvirtual.fvstoresdesk.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.sound.sampled.AudioInputStream;
/*    */ import javax.sound.sampled.AudioSystem;
/*    */ import javax.sound.sampled.Clip;
/*    */ import org.slf4j.Logger;
import org.slf4j.LoggerFactory;/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FVMediaUtils
/*    */ {
/* 14 */   protected static Logger logger = LoggerFactory.getLogger("FVMediaUtils");
/*    */ 
/*    */   
/*    */   public static synchronized void playSound(final String filename) {
/* 18 */     (new Thread(new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/*    */             try {
/* 23 */               Clip clip = AudioSystem.getClip();
/*    */ 
/*    */ 
/*    */               
/* 27 */               AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("C:\\facilvirtual\\sounds\\" + filename));
/*    */ 
/*    */               
/* 30 */               clip.open(inputStream);
/* 31 */               clip.start();
/* 32 */             } catch (Exception e) {
/*    */               
/* 34 */               FVMediaUtils.logger.error("Error al reproducir el sonido: " + filename);
/* 35 */               FVMediaUtils.logger.error("Message: " + e.getMessage());
/* 36 */               FVMediaUtils.logger.error("Exception: " + e);
/*    */             } 
/*    */           }
/* 39 */         })).start();
/*    */   }
/*    */ }


/* Location:              C:\facilvirtual\facilvirtual\!\com\facilvirtual\fvstoresdes\\util\FVMediaUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */