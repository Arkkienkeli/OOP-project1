package com.webcontroller.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Arkkienkeli on 19.05.14.
 */
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="cartPositions")
    @ElementCollection(targetClass=Long.class)
    private List<Long> cartPositions = new ArrayList<Long>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    @Column(name="total")
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Long> getCartPositions() {
        return cartPositions;
    }

    public void setCartPositions(List<Long> cartPositions) {
        this.cartPositions = cartPositions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
