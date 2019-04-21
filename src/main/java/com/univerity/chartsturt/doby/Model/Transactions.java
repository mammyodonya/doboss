package com.univerity.chartsturt.doby.Model;

public class Transactions {
    String phone,name,date,status;

    public Transactions(String phone, String name, String date, String status) {
        this.phone = phone;
        this.name = name;
        this.date = date;
        this.status = status;
    }

    public Transactions() {

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
