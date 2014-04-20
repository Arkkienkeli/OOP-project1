package com.webcontroller.entity;


import org.hibernate.annotations.Generated;

import javax.persistence.Id;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    //@OneToOne
    //Address address;

    //@OneToMany
    //List<Address> addresslist;
}
