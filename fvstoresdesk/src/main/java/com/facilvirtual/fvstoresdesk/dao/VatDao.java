package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Vat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VatDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("VatDao");

   public VatDao() {
   }

   public void saveVat(Vat vat) {
      Session session = this.getSession();
      session.saveOrUpdate(vat);
   }

   public Vat getVat(Long vatId) {
      Session session = this.getSession();
      return (Vat)session.get(Vat.class, vatId);
   }

   public Vat getVatByName(String vatName) {
      Session session = this.getSession();
      Vat vat = null;

      try {
         Criteria crit = session.createCriteria(Vat.class);
         crit.add(Restrictions.eq("name", vatName).ignoreCase());
         crit.setMaxResults(1);
         vat = (Vat)crit.uniqueResult();
      } catch (Exception var5) {
         logger.error("Error recuperando IVA por nombre.");
      }

      return vat;
   }

   public List<Vat> getAllVat() {
      List<Vat> vats = this.getSession().createCriteria(Vat.class).list();
      return vats;
   }

   public List<Vat> getAllVats() {
      List<Vat> vats = this.getSession().createCriteria(Vat.class).list();
      return vats;
   }
}
