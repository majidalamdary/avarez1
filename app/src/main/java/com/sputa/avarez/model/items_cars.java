package com.sputa.avarez.model;

/**
 * Created by diego on 03/05/2018.
 */

public class items_cars {
    private String txt_name;
    private String txt_pelak;
    private String txt_avarez;
    private String radif;

    public String getTxt_pelak() {
        return txt_pelak;
    }

    public void setTxt_pelak(String txt_pelak) {
        this.txt_pelak = txt_pelak;
    }

    public void setRadif(String radif) {
        this.radif = radif;
    }

    public String getRadif() {
        return radif;
    }

    public items_cars(String txt_name,String txt_pelak, String txt_avarez, String radif) {
        this.txt_name = txt_name;
        this.txt_pelak = txt_pelak;
        this.txt_avarez = txt_avarez;
        this.radif = radif;
    }

    public void setTxt_name(String txt_name) {
        this.txt_name = txt_name;
    }

    public void setTxt_avarez(String txt_avarez) {
        this.txt_avarez = txt_avarez;
    }

    public String getTxt_name() {
        return txt_name;
    }

    public String getTxt_avarez() {
        return txt_avarez;
    }
}
