package com.webcontroller.dao;
import com.webcontroller.entity.User;

/**
 * Created by Arkkienkeli on 24.03.14.
 */
public interface IUserDao {
    User findById(Long Id);
    User findByName(String name);
    User save (User u);
}
