package com.sputa.avarez.model;

public class Itm_city {

    private String img;
    private String title;
    private String c_id;

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_id() {
        return c_id;
    }

    public Itm_city(String img, String title, String c_id) {
        this.img = img;
        this.title = title;
        this.c_id = c_id;

    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }
}
