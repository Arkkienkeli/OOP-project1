package com.webcontroller.dao;

import com.webcontroller.entity.Cart;
import com.webcontroller.entity.Customer;
import com.webcontroller.entity.Goods;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mockito.internal.util.collections.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Arkkienkeli on 20.05.14.
 */
@Repository
public class CartDao implements ICartDao {
    private static final Logger log = LoggerFactory.getLogger(CartDao.class);

    @Autowired
    SessionFactory sf;

    public Cart showCart(Customer c,Customer d)
    {
        Session s = sf.getCurrentSession();
        Customer cust = (Customer) s.createQuery("from Customer where id = :id").setLong("id", c.getId()).uniqueResult();
        if (cust.getName().equals(d.getName()) && cust.getPassword().equals(d.getPassword())) {
            Cart cart = new Cart();
            cart.setCustomer(cust);
            cart.setId(Long.parseLong(s.createQuery("select id from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult().toString()));
            cart.setTotal(new BigDecimal(s.createQuery("select total from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult().toString()));
            try {
            String datecons = (s.createQuery("select date from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult().toString());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = formatter.parse(datecons);
                cart.setDate(date);}
            catch (ParseException e) {  e.printStackTrace();}
            Long idd = cart.getId();
            List l = s.createSQLQuery("select cartpositions from cart_cartpositions inner join Cart on (Cart.id = cart_cartpositions.cart_id) where customer_id = :idd").setLong("idd", idd).list();
            cart.setCartPositions(l);
            return cart;
        }
        else return null;
    }

    public Cart addtoCart(Customer c, Customer d, Goods g) {
        Session s = sf.getCurrentSession();
        Customer cust = (Customer) s.createQuery("from Customer where id = :id").setLong("id", c.getId()).uniqueResult();
        if (cust.getName().equals(d.getName()) && cust.getPassword().equals(d.getPassword())) {

        List<Long> x = new ArrayList<Long>();
        Date date = new Date();
        Cart cart2 = (Cart) s.createQuery("from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult();
        try {
            if (cart2 == null) {
                if (g.getQuantity() > 0) {
                Cart cart = new Cart();
                x.add(g.getId());
                cart.setCartPositions(x);
                cart.setTotal(g.getPrice());
                cart.setCustomer(c);
                cart.setDate(date);
                g.setQuantity(g.getQuantity() - 1);
                s.save(g);
                s.save(cart);
                return cart;
                }
                else return null;
            }
            else {
                if (g.getQuantity() > 0) {
                Cart cart = (Cart) s.createQuery("from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult();
                x = cart.getCartPositions();
                x.add(g.getId());
                cart.setCartPositions(x);
                cart.setTotal(cart.getTotal().add(g.getPrice()));
                g.setQuantity(g.getQuantity()-1);
                s.save(g);
                s.save(cart);
                return cart;
                }
        else return null;
            }
        }
        catch (HibernateException ex) {
            log.error("Can't cart", ex);
            return null;
        }
    }
        else return null;
    }
        public Cart deletefromCart(Customer c,Customer d, int position) {
            Session s = sf.getCurrentSession();
            Customer cust = (Customer) s.createQuery("from Customer where id = :id").setLong("id", c.getId()).uniqueResult();
            if (cust.getName().equals(d.getName()) && cust.getPassword().equals(d.getPassword())) {
                Cart cart = new Cart();
                cart.setCustomer(cust);
                cart.setId(Long.parseLong(s.createQuery("select id from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult().toString()));
                Long idd = cust.getId();
                List l = s.createSQLQuery("select cartpositions from cart_cartpositions inner join Cart on (Cart.id = cart_cartpositions.cart_id) where customer_id = :idd").setLong("idd", idd).list();
                ArrayList<BigInteger> f = new ArrayList<BigInteger>(l);
                BigInteger good = f.get(position-1);
                BigDecimal delPrice = new BigDecimal(s.createQuery("select price from Goods where id = :good").setBigInteger("good", good).uniqueResult().toString());
                cart.setTotal(new BigDecimal(s.createQuery("select total from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult().toString()));
                cart.setTotal(cart.getTotal().subtract(delPrice));
                f.remove(position-1);
                log.debug(f.toString()+"array"+f.size());
                if (f.size() == 0) {
                    s.delete(cart);
                    return cart;
                }
                List<Long> ff = new ArrayList<>();
                for (int index = 0, n = f.size(); index < n; index++){
                    ff.add(f.get(index).longValue());
                }
                log.debug(f.toString()+"array"+f.size());
                cart.setCartPositions(ff);
                log.debug(cart.getCartPositions().toString());
                try {
                    String datecons = (s.createQuery("select date from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult().toString());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = formatter.parse(datecons);
                    cart.setDate(date);}
                catch (ParseException e) {  e.printStackTrace();}
                s.update(cart);
                Goods g = (Goods)s.createQuery("from Goods where id = :good").setBigInteger("good", good).uniqueResult();
                g.setQuantity(g.getQuantity()+1);
                s.save(g);
                return cart;
            }
            else return null;

    }


}
