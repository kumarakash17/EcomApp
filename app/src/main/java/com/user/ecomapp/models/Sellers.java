package com.user.ecomapp.models;

public class Sellers {

    private String gst;
    private String bankAccountNumber;
    private String ifsc;
    private String emailLinkedWithBank;
    private String phoneLinkedWithBank;
    private String name;
    private String pan;
    private String password;
    private String phone;
    private String sid;
    private String address;

    public Sellers(String gst, String bankAccountNumber, String ifsc, String emailLinkedWithBank, String phoneLinkedWithBank, String name, String pan, String password, String phone, String sid, String address, String email) {

        this.gst = gst;
        this.bankAccountNumber = bankAccountNumber;
        this.ifsc = ifsc;
        this.emailLinkedWithBank = emailLinkedWithBank;
        this.phoneLinkedWithBank = phoneLinkedWithBank;
        this.name = name;
        this.pan = pan;
        this.password = password;
        this.phone = phone;
        this.sid = sid;
        this.address = address;
        this.email = email;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getEmailLinkedWithBank() {
        return emailLinkedWithBank;
    }

    public void setEmailLinkedWithBank(String emailLinkedWithBank) {
        this.emailLinkedWithBank = emailLinkedWithBank;
    }

    public String getPhoneLinkedWithBank() {
        return phoneLinkedWithBank;
    }

    public void setPhoneLinkedWithBank(String phoneLinkedWithBank) {
        this.phoneLinkedWithBank = phoneLinkedWithBank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    private String email;
    public Sellers() {
    }




}
