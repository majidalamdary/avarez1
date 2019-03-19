package com.sputa.avarez.model;

/**
 * Created by diego on 03/05/2018.
 *////

public class items_eshterak {
    public items_eshterak(String txt_eshterak, String txt_name, String txt_price, String radif, String type,String isPaid) {
        this.txt_eshterak = txt_eshterak;
        this.txt_name = txt_name;
        this.txt_price = txt_price;
        this.radif = radif;
        this.type=type;
        this.isPaid=isPaid;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTxt_eshterak(String txt_eshterak) {
        this.txt_eshterak = txt_eshterak;
    }

    public void setTxt_name(String txt_name) {
        this.txt_name = txt_name;
    }

    public void setTxt_price(String txt_price) {
        this.txt_price = txt_price;
    }

    public void setRadif(String radif) {
        this.radif = radif;
    }

    public String getTxt_eshterak() {
        return txt_eshterak;
    }

    public String getTxt_name() {
        return txt_name;
    }

    public String getTxt_price() {
        return txt_price;
    }

    public String getRadif() {
        return radif;
    }

    private String txt_eshterak;
    private String type;
    private String isPaid;
    private String txt_name;
    private String txt_price;
    private String radif;

}
