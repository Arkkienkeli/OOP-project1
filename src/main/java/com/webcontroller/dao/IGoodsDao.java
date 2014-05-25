package com.webcontroller.dao;

import com.webcontroller.entity.Customer;
import com.webcontroller.entity.Goods;

import java.util.List;

/**
 * Created by Arkkienkeli on 21.04.14.
 */
public interface IGoodsDao {
    public List<Goods> findAll();
    List<Goods> findByCategory(int catId);
    Goods findByName(String name);
    Goods save(Goods g, Customer c);
    Goods delete(Goods g, Customer c);
}
