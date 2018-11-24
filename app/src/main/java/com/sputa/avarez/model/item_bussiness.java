package com.sputa.avarez.model;

public class item_bussiness {

    private String last_pay_date;
    private String start_year;
    private String end_year;
    private String tax;
    private String penalty;
    private String Amount;

    public void setLast_pay_date(String last_pay_date) {
        this.last_pay_date = last_pay_date;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public void setEnd_year(String end_year) {
        this.end_year = end_year;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getLast_pay_date() {
        return last_pay_date;
    }

    public String getStart_year() {
        return start_year;
    }

    public String getEnd_year() {
        return end_year;
    }

    public String getTax() {
        return tax;
    }

    public String getPenalty() {
        return penalty;
    }

    public String getAmount() {
        return Amount;
    }

    public item_bussiness(String last_pay_date, String start_year, String end_year, String tax, String penalty, String amount) {
        this.last_pay_date = last_pay_date;
        this.start_year = start_year;
        this.end_year = end_year;
        this.tax = tax;
        this.penalty = penalty;
        Amount = amount;
    }
}
