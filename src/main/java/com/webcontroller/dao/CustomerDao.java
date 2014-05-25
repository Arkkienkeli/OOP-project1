package com.webcontroller.dao;

import com.webcontroller.entity.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao implements ICustomerDao{
    private static final Logger log = LoggerFactory.getLogger(CustomerDao.class);

    @Autowired
    SessionFactory sf;

    public Customer save(Customer c) {
        Session s = sf.getCurrentSession();
        try {
            s.save(c);
            return c;
        }
        catch (HibernateException ex) {
            log.error("Can't create user, maybe try another name", ex);
            return null;
        }
    }

    public Customer delete(Customer c, Customer d) {
        Session s = sf.getCurrentSession();
        if (c.getName().equals(d.getName()) && c.getPassword().equals(d.getPassword())) {
            s.delete(d);
            return c; }
        else {
            log.error("You can't delete account");
            return null;
        }
    }
    public Customer update(Customer c, Customer u) {
        Session s = sf.getCurrentSession();
        if (c.getName().equals(u.getName()) && c.getPassword().equals(u.getPassword())) {
            s.update(u);
            return u; }
        else {
            log.error("You can't update account");
            return null;
        }
    }
}

