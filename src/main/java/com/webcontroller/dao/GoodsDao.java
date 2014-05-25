package com.webcontroller.dao;
import java.util.Collections;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.webcontroller.entity.Customer;
import com.webcontroller.entity.Goods;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GoodsDao implements IGoodsDao {

    private static final Logger log = LoggerFactory.getLogger(GoodsDao.class);

    @Autowired
    SessionFactory sf;
    public List<Goods> findAll(){
        Session s = sf.getCurrentSession();
        try {
            return (List<Goods>) s.createQuery("from Goods").list();
        }
        catch (HibernateException ex) {
            log.error("Can't find any", ex);
            return Collections.emptyList();
        }
    }

    public List<Goods> findByCategory(int catId){
        Session s = sf.getCurrentSession();
        try {
            return (List<Goods>) s.createQuery("from Goods where category.id = :categoryId").setInteger("categoryId", catId).list();
        }
        catch (HibernateException ex) {
            log.error("Can't find by category", ex);
            return Collections.emptyList();
        }
    }

    public Goods findByName(String name){
        Session s = sf.getCurrentSession();
       try {
          return (Goods) s.createQuery("from Goods where name = :name").setString("name",name).uniqueResult();
       }
       catch (HibernateException ex) {
           log.error("Can't find by name", ex);
           return new Goods();
        }
    }
    public Goods save(Goods g, Customer c) {
        Session s = sf.getCurrentSession();
        if (c.getName().equals("Admin") && c.getPassword().equals("123456")) {
        s.save(g);
        return g; }
        else {
            log.error("You are not admin");
            return null;
        }
    }
    public Goods delete(Goods g, Customer c) {
        Session s = sf.getCurrentSession();
        if (c.getName().equals("Admin") && c.getPassword().equals("123456")) {
        s.delete(g);
        return g; }
        else {
            log.error("You are not admin");
            return null;
        }
    }
}
