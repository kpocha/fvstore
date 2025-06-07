package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.NotaDeCredito;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NotaDeCreditoDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("NotaDeCreditoDao");

   public NotaDeCreditoDao() {
   }

   public void saveNotaDeCredito(NotaDeCredito notaDeCredito) {
      this.getSession().saveOrUpdate(notaDeCredito);
   }

   public List<NotaDeCredito> getAllNotasDeCreditoForDateRange(Date startDate, Date endDate) {
      List<NotaDeCredito> notas = this.getSession().createCriteria(NotaDeCredito.class).add(Restrictions.between("cbteFch", startDate, endDate)).addOrder(Order.asc("cbteFch")).list();
      return notas;
   }

   public List<NotaDeCredito> getAllNotasDeCredito() {
      List<NotaDeCredito> notas = this.getSession().createCriteria(NotaDeCredito.class).list();
      return notas;
   }

   public NotaDeCredito getNotaDeCredito(Long notaId) {
      return (NotaDeCredito)this.getSession().get(NotaDeCredito.class, notaId);
   }
}
