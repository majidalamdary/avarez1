package com.sputa.avarez.model;

public class item_pay_history {

    String date;
    String pelak;
    String ID;
    String price;
    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public item_pay_history(String date, String pelak, String ID, String price, String name) {
        this.date = date;
        this.pelak = pelak;
        this.ID = ID;
        this.price = price;
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPelak(String pelak) {
        this.pelak = pelak;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getPelak() {
        return pelak;
    }

    public String getID() {
        return ID;
    }

    public String getPrice() {
        return price;
    }
}
