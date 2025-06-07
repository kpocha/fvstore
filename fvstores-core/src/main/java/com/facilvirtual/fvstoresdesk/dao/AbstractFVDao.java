package com.facilvirtual.fvstoresdesk.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractFVDao {
   @Autowired
   private SessionFactory sessionFactory;

   public AbstractFVDao() {
   }

   protected Session getSession() {
      return this.sessionFactory.getCurrentSession();
   }
}
