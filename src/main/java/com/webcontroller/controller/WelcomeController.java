package com.webcontroller.controller;

import com.google.gson.Gson;
import com.webcontroller.dao.*;
import com.webcontroller.entity.Cart;
import com.webcontroller.entity.Customer;
import com.webcontroller.entity.Goods;
import com.webcontroller.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class WelcomeController {
    int counter = 0;

    @Autowired
    IGoodsDao g;
    @Autowired
    ICustomerDao c;
    @Autowired
    ICartDao z;
    @Autowired
    IOrdersDao o;

    @RequestMapping("goods/{name}")
    @Transactional
    @ResponseBody
    public Goods goodsName(@PathVariable String name) {
        return g.findByName(name);
    }
    @RequestMapping("goods/")
    @Transactional
    @ResponseBody
    public List<Goods> goodsAll() {
        return g.findAll();
    }
    @RequestMapping("goods/cat/{catId}")
    @Transactional
    @ResponseBody
    public List<Goods> goodsCat(@PathVariable int catId) {
        return g.findByCategory(catId);
    }

    @RequestMapping("goods/save")
    @Transactional
    @ResponseBody
    public Goods goodsSave(@RequestBody Admin admin) {
        Goods goods = new Goods();
        Customer customer = new Customer();
        goods.setId(admin.getId());
        goods.setName(admin.getName());
        goods.setDescription(admin.getDescription());
        goods.setQuantity(admin.getQuantity());
        goods.setPrice(admin.getPrice());
        goods.setCategory(admin.getCategory());
        customer.setName(admin.getNameAdmin());
        customer.setPassword(admin.getPassword());
        return g.save(goods, customer);
    }

    @RequestMapping("goods/delete")
    @Transactional
    @ResponseBody
    public Goods goodsDelete(@RequestBody Admin admin) {
        Goods goods = new Goods();
        Customer customer = new Customer();
        goods.setId(admin.getId());
        goods.setName(admin.getName());
        goods.setDescription(admin.getDescription());
        goods.setQuantity(admin.getQuantity());
        goods.setPrice(admin.getPrice());
        goods.setCategory(admin.getCategory());
        customer.setName(admin.getNameAdmin());
        customer.setPassword(admin.getPassword());
        return g.delete(goods,customer);
    }

    @RequestMapping("addUser")
    @Transactional
    @ResponseBody
    public Customer CustomerAdd(@RequestBody Customer customer) {
        return c.save(customer);
    }
    @RequestMapping("deleteUser")
    @Transactional
    @ResponseBody
    public Customer CustomerDelete(@RequestBody CustomerComp custcomp) {
        Customer customer = new Customer();
        Customer customerDelete = new Customer();
        customer.setName(custcomp.getNameLogin());
        customer.setPassword(custcomp.getPswdLogin());
        customerDelete.setId(custcomp.getId());
        customerDelete.setName(custcomp.getName());
        customerDelete.setAddress(custcomp.getAddress());
        customerDelete.setEmail(custcomp.getEmail());
        customerDelete.setPhone(custcomp.getPhone());
        customerDelete.setPassword(custcomp.getPassword());
        return c.delete(customer,customerDelete);
    }
    @RequestMapping("updateUser")
    @Transactional
    @ResponseBody
    public Customer CustomerUpdate(@RequestBody CustomerComp custcomp) {
        Customer customer = new Customer();
        Customer customerUpdate = new Customer();
        customer.setName(custcomp.getNameLogin());
        customer.setPassword(custcomp.getPswdLogin());
        customerUpdate.setId(custcomp.getId());
        customerUpdate.setName(custcomp.getName());
        customerUpdate.setAddress(custcomp.getAddress());
        customerUpdate.setEmail(custcomp.getEmail());
        customerUpdate.setPhone(custcomp.getPhone());
        customerUpdate.setPassword(custcomp.getPassword());
        return c.update(customer, customerUpdate);
    }
    @RequestMapping("goods/{name}/addToCart")
    @Transactional
    @ResponseBody
    public Cart addToCart(@PathVariable String name, @RequestBody CustomerComp custcomp) {
        Goods good = g.findByName(name);
        Customer customer = new Customer();
        Customer customerLogin = new Customer();
        customer.setId(custcomp.getId());
        customerLogin.setName(custcomp.getNameLogin());
        customerLogin.setPassword(custcomp.getPswdLogin());
        return z.addtoCart(customer, customerLogin, good);
    }
    @RequestMapping("customer/{id}/myCart")
    @Transactional
    @ResponseBody
    public Cart showCart(@PathVariable Long id, @RequestBody CustomerComp custcomp) {
        Customer customer = new Customer();
        Customer customerLogin = new Customer();
        customer.setId(custcomp.getId());
        customerLogin.setName(custcomp.getNameLogin());
        customerLogin.setPassword(custcomp.getPswdLogin());
        return z.showCart(customer,customerLogin);
    }
    @RequestMapping("customer/{id}/myCart/{pos}/delete")
    @Transactional
    @ResponseBody
    public Cart deleteCart(@PathVariable Long id, @PathVariable int pos, @RequestBody CustomerComp custcomp) {
        Customer customer = new Customer();
        Customer customerLogin = new Customer();
        customer.setId(custcomp.getId());
        customerLogin.setName(custcomp.getNameLogin());
        customerLogin.setPassword(custcomp.getPswdLogin());
        return z.deletefromCart(customer,customerLogin, pos);
    }
    @RequestMapping("customer/{id}/myCart/sumbitOrder")
    @Transactional
    @ResponseBody
    public Orders sumbitOrder(@PathVariable Long id, @RequestBody CustomerComp custcomp) {
        Customer customer = new Customer();
        Customer customerLogin = new Customer();
        customer.setId(custcomp.getId());
        customerLogin.setName(custcomp.getNameLogin());
        customerLogin.setPassword(custcomp.getPswdLogin());
        return o.sumbit(customer, customerLogin);
    }
    @RequestMapping("admin/orders/{id}")
    @Transactional
    @ResponseBody
    public Orders changeOrder(@PathVariable Long id, @RequestBody Admin admin) {
        Customer customerLogin = new Customer();
        Orders order = new Orders();
        order.setId(id);
        customerLogin.setName(admin.getNameAdmin());
        customerLogin.setPassword(admin.getPassword());
        String st = admin.getStatusOrder();
        return o.changeStatus(order, st, customerLogin);
    }
    @RequestMapping("customer/{id}/myOrders")
    @Transactional
    @ResponseBody
    public List <Orders> viewOrder(@PathVariable Long id, @RequestBody CustomerComp custcomp) {
        Customer customer = new Customer();
        Customer customerLogin = new Customer();
        customer.setId(custcomp.getId());
        customerLogin.setName(custcomp.getNameLogin());
        customerLogin.setPassword(custcomp.getPswdLogin());
        return o.viewOrders(customer, customerLogin);
    }

}
