package com.webcontroller.dao;

import com.webcontroller.entity.Customer;

/**
 * Created by Arkkienkeli on 21.05.14.
 */
public interface ICustomerDao {
    Customer delete(Customer c, Customer d);
    Customer save(Customer c);
    Customer update(Customer c, Customer u);
}
