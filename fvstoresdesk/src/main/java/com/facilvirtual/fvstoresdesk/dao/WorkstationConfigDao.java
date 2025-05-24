package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.WorkstationConfig;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class WorkstationConfigDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("WorkstationConfigDao");

   public WorkstationConfigDao() {
   }

   public void saveWorkstationConfig(WorkstationConfig workstationConfig) {
      this.getSession().saveOrUpdate(workstationConfig);
   }

   public WorkstationConfig getWorkstationConfigByInstallationCode(String installationCode) {
      WorkstationConfig config = null;

      try {
         Criteria crit = this.getSession().createCriteria(WorkstationConfig.class);
         crit.add(Restrictions.eq("installationCode", installationCode).ignoreCase());
         crit.setMaxResults(1);
         config = (WorkstationConfig)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Error al recuperar la configuracion de la terminal de trabajo");
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return config;
   }

   public int getWorkstationsQty() {
      int value = 0;

      try {
         Criteria sizeResult = this.getSession().createCriteria(WorkstationConfig.class).setProjection(Projections.rowCount());
         Long size = (Long)sizeResult.uniqueResult();
         value = size.intValue();
      } catch (Exception var4) {
         logger.error("Error calculando total de workstations");
      }

      return value;
   }

   public List<WorkstationConfig> getActiveWorkstationConfigs() {
      List<WorkstationConfig> stations = this.getSession().createCriteria(WorkstationConfig.class).add(Restrictions.eq("active", true)).addOrder(Order.asc("cashNumber")).list();
      return stations;
   }
}
