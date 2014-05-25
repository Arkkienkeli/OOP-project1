package com.webcontroller.dao;

import com.webcontroller.entity.Customer;
import com.webcontroller.entity.Orders;

import java.util.List;

/**
 * Created by Arkkienkeli on 21.04.14.
 */
public interface IOrdersDao {
    public Orders sumbit(Customer c,Customer d);
    public List<Orders> viewOrders(Customer c,Customer d);
    public Orders changeStatus(Orders o, String stat, Customer c);
}
