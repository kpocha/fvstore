package com.facilvirtual.fvstoresdesk.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.facilvirtual.fvstoresdesk.model.AppConfig;

@Repository
public class AppConfigDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("AppConfigDao");

   public AppConfigDao() {
   }

   public void saveAppConfig(AppConfig appConfig) {
      this.getSession().saveOrUpdate(appConfig);
   }

   public AppConfig getAppConfig(Long appConfigID) {
      return (AppConfig)this.getSession().get(AppConfig.class, appConfigID);
   }

   public AppConfig loadAppConfig(Long appConfigID) {
      return (AppConfig)this.getSession().load(AppConfig.class, appConfigID);
   }

   public boolean createBackup(String filename) {
      boolean ok = true;

      try {
         // Usando pg_dump para crear el backup
         ProcessBuilder pb = new ProcessBuilder(
            "pg_dump",
            "-h", "localhost",
            "-p", "5432",
            "-U", "postgrest",
            "-F", "c",
            "-b",
            "-v",
            "-f", filename,
            "fvstore"
         );
         
         // Configurar la contraseña de PostgreSQL
         pb.environment().put("PGPASSWORD", "danilo");
         
         Process process = pb.start();
         int exitCode = process.waitFor();
         
         if (exitCode != 0) {
            logger.error("Error al crear el backup. Código de salida: " + exitCode);
            ok = false;
         }
      } catch (Exception e) {
         logger.error("Error al crear el backup: " + e.getMessage(), e);
         ok = false;
      }

      return ok;
   }
}
