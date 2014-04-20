package com.webcontroller.dao;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Arkkienkeli on 24.03.14.
 */
import org.hibernate.Session;
import java.util.List;
import com.webcontroller.entity.User;


public class UserDao implements IUserDao {
    @Autowired
    SessionFactory sf;

    @Override
    public User save(User u) {
        Session s = sf.getCurrentSession();
        return(User) s.save(u);
    }

    @Override
    public User findById(Long id) {
        Session s = sf.getCurrentSession();
        return(User) s.get(User.class, id);
    }
    @Override
    public User findByName(String name) {
        Session s = sf.getCurrentSession();
        Criteria c = s.createCriteria(User.class);
        c.add(Restrictions.like("name", name));
        List res =  c.list();
        if (res.size() > 0 ) {
            return (User) res.get(0);
        } else {
            return  null;
        }
    }

}
