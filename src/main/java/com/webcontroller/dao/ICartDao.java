package com.webcontroller.dao;

import com.webcontroller.entity.Cart;
import com.webcontroller.entity.Customer;
import com.webcontroller.entity.Goods;

/**
 * Created by Arkkienkeli on 20.05.14.
 */
public interface ICartDao {
    public Cart addtoCart(Customer c, Customer d, Goods g);
    public Cart showCart(Customer c,Customer d);
    public Cart deletefromCart(Customer c,Customer d, int position);
}
