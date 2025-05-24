package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Order;
import com.facilvirtual.fvstoresdesk.model.OrderLine;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderLineDao {
   protected static Logger logger = LoggerFactory.getLogger("OrderLineDao");
   @Autowired
   private SessionFactory sessionFactory;

   public OrderLineDao() {
   }

   private Session getSession() {
      return this.sessionFactory.getCurrentSession();
   }

   public List<OrderLine> getOrderLinesForOrder(Order order) {
      List<OrderLine> orderLines = this.getSession().createCriteria(OrderLine.class).add(Restrictions.eq("order.id", order.getId())).list();
      return orderLines;
   }
}
