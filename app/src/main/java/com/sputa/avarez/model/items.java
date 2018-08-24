package com.sputa.avarez.model;

/**
 * Created by diego on 03/05/2018.
 */

public class items {
    private String txt_year;
    private String txt_avarez;
    private String txt_farsudegi;
    private String txt_rate_punish;
    private String txt_punish;
    private String id;

    public items(String txt_year, String txt_avarez, String txt_farsudegi, String txt_rate_punish, String txt_punish, String id) {
        this.txt_year = txt_year;
        this.txt_avarez = txt_avarez;
        this.txt_farsudegi = txt_farsudegi;
        this.txt_rate_punish = txt_rate_punish;
        this.txt_punish = txt_punish;
        this.id = id;
    }

    public void setTxt_year(String txt_year) {
        this.txt_year = txt_year;
    }

    public void setTxt_avarez(String txt_avarez) {
        this.txt_avarez = txt_avarez;
    }

    public void setTxt_farsudegi(String txt_farsudegi) {
        this.txt_farsudegi = txt_farsudegi;
    }

    public void setTxt_rate_punish(String txt_rate_punish) {
        this.txt_rate_punish = txt_rate_punish;
    }

    public void setTxt_punish(String txt_punish) {
        this.txt_punish = txt_punish;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxt_year() {
        return txt_year;
    }

    public String getTxt_avarez() {
        return txt_avarez;
    }

    public String getTxt_farsudegi() {
        return txt_farsudegi;
    }

    public String getTxt_rate_punish() {
        return txt_rate_punish;
    }

    public String getTxt_punish() {
        return txt_punish;
    }

    public String getId() {
        return id;
    }
}
