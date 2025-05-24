package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Order;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {
   protected static Logger logger = LoggerFactory.getLogger("OrderDao");
   @Autowired
   private SessionFactory sessionFactory;

   public OrderDao() {
   }

   private Session getSession() {
      return this.sessionFactory.getCurrentSession();
   }

   public void saveOrder(Order order) {
      Session session = this.sessionFactory.getCurrentSession();
      session.saveOrUpdate(order);
   }

   public List<Order> getAllOrders() {
      List<Order> orders = this.getSession().createCriteria(Order.class).list();
      return orders;
   }

   public Order getOrder(Long orderId) {
      return (Order)this.getSession().get(Order.class, orderId);
   }

   public List<Order> getAllOrdersForDateRange(Date startDate, Date endDate) {
      List<Order> orders = this.getSession().createCriteria(Order.class).add(Restrictions.between("saleDate", startDate, endDate)).addOrder(org.hibernate.criterion.Order.asc("saleDate")).list();
      return orders;
   }

   public int getTotalOrdersQty() {
      int value = 0;

      try {
         Criteria sizeResult = this.getSession().createCriteria(Order.class).setProjection(Projections.rowCount());
         Long size = (Long)sizeResult.uniqueResult();
         value = size.intValue();
      } catch (Exception var4) {
      }

      return value;
   }

   public List<Order> getCompletedOrdersForDateRange(Date startDate, Date endDate) {
      List<Order> orders = this.getSession().createCriteria(Order.class).add(Restrictions.between("saleDate", startDate, endDate)).add(Restrictions.eq("status", "COMPLETED")).addOrder(org.hibernate.criterion.Order.asc("saleDate")).list();
      return orders;
   }

   public List<Order> getFiscalOrdersForDateRange(Date startDate, Date endDate) {
      List<Order> orders = this.getSession().createCriteria(Order.class).add(Restrictions.between("saleDate", startDate, endDate)).add(Restrictions.isNotNull("afipCae")).add(Restrictions.ne("afipCae", "")).addOrder(org.hibernate.criterion.Order.asc("saleDate")).list();
      return orders;
   }

   public Order getOrderByNumber(int afipPtoVta, int nroFactura) {
      Order order = null;

      Criteria crit;
      try {
         crit = this.getSession().createCriteria(Order.class);
         crit.add(Restrictions.eq("afipPtoVta", afipPtoVta));
         crit.add(Restrictions.eq("afipCbteDesde", new Long((long)nroFactura)));
         crit.add(Restrictions.eq("afipCbteHasta", new Long((long)nroFactura)));
         crit.setMaxResults(1);
         order = (Order)crit.uniqueResult();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      if (order == null) {
         try {
            crit = this.getSession().createCriteria(Order.class);
            crit.add(Restrictions.eq("receiptNumber", String.valueOf(nroFactura)));
            crit.setMaxResults(1);
            order = (Order)crit.uniqueResult();
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

      return order;
   }
}
