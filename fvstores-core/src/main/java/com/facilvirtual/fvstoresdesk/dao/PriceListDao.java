package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.PriceList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PriceListDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("PriceListDao");
   @Autowired
   private SessionFactory sessionFactory;

   public PriceListDao() {
   }

   public void savePriceList(PriceList priceList) {
      this.getSession().saveOrUpdate(priceList);
   }

   public PriceList getPriceList(Long priceListId) {
      return (PriceList)this.getSession().get(PriceList.class, priceListId);
   }

   public PriceList getPriceListByName(String priceListName) {
      PriceList priceList = null;

      try {
         Criteria crit = this.getSession().createCriteria(PriceList.class);
         crit.add(Restrictions.eq("name", priceListName).ignoreCase());
         crit.setMaxResults(1);
         priceList = (PriceList)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Se produjo un error al recuperar Lista de precios por nombre");
         logger.error(var4.getMessage());
         //logger.error(var4);
         var4.printStackTrace();
      }

      return priceList;
   }

   public List<PriceList> getActivePriceLists() {
      List<PriceList> priceLists = this.getSession().createCriteria(PriceList.class).addOrder(Order.asc("id")).add(Restrictions.eq("active", true)).list();
      return priceLists;
   }

   public List<PriceList> getAllPriceLists() {
      List<PriceList> priceLists = this.getSession().createCriteria(PriceList.class).addOrder(Order.asc("id")).list();
      return priceLists;
   }
}
