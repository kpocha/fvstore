package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.stereotype.Repository;

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
         String sql = "BACKUP DATABASE TO '" + filename + "' BLOCKING";
         this.getSession().createSQLQuery(sql).executeUpdate();
      } catch (Exception var4) {
         ok = false;
      }

      return ok;
   }
}
