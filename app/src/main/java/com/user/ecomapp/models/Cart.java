package com.user.ecomapp.models;

public class Cart {
    private String date;
    private String description;
    private String duration;
    private String name;
    private String pid;
    private String price;
    private String quantity;
    private String time;
    private String usage;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    private String sid;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    private String weight;

    public Cart(String date,String sid, String description, String duration, String name, String pid, String price, String quantity, String time, String usage, String weight) {
        this.date = date;
        this.description = description;
        this.duration = duration;
        this.name = name;
        this.pid = pid;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
        this.usage = usage;
        this.weight = weight;
        this.sid=sid;
    }



    public Cart() {
    }



}
