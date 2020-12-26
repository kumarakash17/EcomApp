package com.user.ecomapp.models;

public class Orders {
    public Orders() {
    }

    public Orders(String address, String city, String date, String mail, String name, String phone, String time, String totalAmount) {
        this.address = address;
        this.city = city;
        this.date = date;
        this.mail = mail;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.totalAmount = totalAmount;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    private String address;
    private String city;
    private String date;
    private String mail;
    private String name;
    private String phone;
    private String time;
    private String duration;
    private String totalAmount;




}
