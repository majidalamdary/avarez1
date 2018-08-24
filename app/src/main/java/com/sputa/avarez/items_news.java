package com.sputa.avarez;

/**
 * Created by diego on 03/05/2018.
 */

public class items_news {
    private String txt_title;
    private String radif;

    public void setTxt_title(String txt_title) {
        this.txt_title = txt_title;
    }

    public void setRadif(String radif) {
        this.radif = radif;
    }

    public String getTxt_title() {

        return txt_title;
    }

    public String getRadif() {
        return radif;
    }

    public items_news(String txt_title, String radif) {

        this.txt_title = txt_title;
        this.radif = radif;
    }
}
