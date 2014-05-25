package com.webcontroller.dao;

import com.webcontroller.entity.Cart;
import com.webcontroller.entity.Customer;
import com.webcontroller.entity.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    /**
     * Created by Arkkienkeli on 21.04.14.
     */
    @Repository
    public class OrdersDao implements IOrdersDao {
        private static final Logger log = LoggerFactory.getLogger(OrdersDao.class);

    @Autowired
    SessionFactory sf;

    public Orders sumbit(Customer c,Customer d) {
        Session s = sf.getCurrentSession();
        Customer cust = (Customer) s.createQuery("from Customer where id = :id").setLong("id", c.getId()).uniqueResult();
        if (cust.getName().equals(d.getName()) && cust.getPassword().equals(d.getPassword())) {
            Cart cart = new Cart();
            cart.setCustomer(cust);
            cart.setId(Long.parseLong(s.createQuery("select id from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult().toString()));
            cart.setTotal(new BigDecimal(s.createQuery("select total from Cart where customer.id = :customer_id").setLong("customer_id", c.getId()).uniqueResult().toString()));
            Long idd = c.getId();
            List l = s.createSQLQuery("select cartpositions from cart_cartpositions inner join Cart on (Cart.id = cart_cartpositions.cart_id) where customer_id = :idd").setLong("idd", idd).list();
            ArrayList<BigInteger> f = new ArrayList<BigInteger>(l);
            List<Long> ff = new ArrayList<>();
            for (int index = 0, n = f.size(); index < n; index++){
                ff.add(f.get(index).longValue());
            }
            log.debug(l.toString()+"array"+f.toString()+ " "+ff.toString());
            cart.setCartPositions(ff);
            Orders order = new Orders();
            order.setTotal(cart.getTotal());
            Date dateord = new Date();
            order.setDate(dateord);
            order.setOrderPositions(cart.getCartPositions());
            order.setCustomer(cart.getCustomer());
            order.setStatus("In Process");
            s.save(order);
            s.delete(cart);
            return order;
        }
        else return null;
    }
    public Orders changeStatus(Orders o, String stat, Customer c) {
        Session s = sf.getCurrentSession();
        if (c.getName().equals("Admin") && c.getPassword().equals("123456")) {
        o.setStatus(stat);
        s.update(o);
        return o; }
        else return null;
    }
    public List<Orders> viewOrders(Customer c,Customer d) {
        Session s = sf.getCurrentSession();
        Customer cust = (Customer) s.createQuery("from Customer where id = :id").setLong("id", c.getId()).uniqueResult();
        if (cust.getName().equals(d.getName()) && cust.getPassword().equals(d.getPassword())) {
            List<Orders> orders = new ArrayList<>();
            List co = s.createQuery("select id from Orders where customer_id = :id").setLong("id", c.getId()).list();
            ArrayList<Long> z = new ArrayList<Long>(co);
            for (int index = 0, n = co.size(); index < n; index++){
                Orders ord = new Orders();
                ord.setId(z.get(index).longValue());
                ord.setCustomer(cust);
                try {
                    String datecons = (s.createQuery("select date from Orders where id = :id").setLong("id", ord.getId()).uniqueResult().toString());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = formatter.parse(datecons);
                    ord.setDate(date);}
                catch (ParseException e) {  e.printStackTrace();}
                ord.setTotal(new BigDecimal(s.createQuery("select total from Orders where id = :id").setLong("id", ord.getId()).uniqueResult().toString()));
                ord.setStatus(s.createQuery("select status from Orders where id = :id").setLong("id", ord.getId()).uniqueResult().toString());
                List ll = s.createSQLQuery("select orderpositions from orders_orderpositions inner join Orders on (Orders.id = orders_orderpositions.orders_id) where orders_orderpositions.orders_id = :id ").setLong("id", ord.getId()).list();
                ArrayList<BigInteger> p = new ArrayList<BigInteger>(ll);
                List<Long> po = new ArrayList<>();
                log.debug(ll.toString()+"array"+p.toString()+ " "+z.toString()+co.toString());
                for (int inde = 0, k = p.size(); inde < k; inde++){
                    po.add(p.get(inde).longValue());
                }
                log.debug(ll.toString()+"array"+p.toString()+ " "+po.toString()+z.toString()+co.toString());
                ord.setOrderPositions(po);
                orders.add(ord);
            }
            return orders;
        }
        else return null;
    }
}
