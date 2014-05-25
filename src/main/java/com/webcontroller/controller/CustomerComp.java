package com.webcontroller.controller;

/**
 * Created by Arkkienkeli on 22.05.14.
 */
public class CustomerComp {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String password;

    private String nameLogin;
    private String pswdLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameLogin() {
        return nameLogin;
    }

    public void setNameLogin(String nameLogin) {
        this.nameLogin = nameLogin;
    }

    public String getPswdLogin() {
        return pswdLogin;
    }

    public void setPswdLogin(String pswdLogin) {
        this.pswdLogin = pswdLogin;
    }
}
