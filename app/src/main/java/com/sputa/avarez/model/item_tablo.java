package com.sputa.avarez.model;

public class item_tablo {
    private String LastPayDate;
    private String StartYear;
    private String EndYear;
    private String Tax;
    private String Penalty;
    private String Amount;

    public void setLastPayDate(String lastPayDate) {
        LastPayDate = lastPayDate;
    }

    public void setStartYear(String startYear) {
        StartYear = startYear;
    }

    public void setEndYear(String endYear) {
        EndYear = endYear;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public void setPenalty(String penalty) {
        Penalty = penalty;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getLastPayDate() {
        return LastPayDate;
    }

    public String getStartYear() {
        return StartYear;
    }

    public String getEndYear() {
        return EndYear;
    }

    public String getTax() {
        return Tax;
    }

    public String getPenalty() {
        return Penalty;
    }

    public String getAmount() {
        return Amount;
    }

    public item_tablo(String lastPayDate, String startYear, String endYear, String tax, String penalty, String amount) {
        LastPayDate = lastPayDate;
        StartYear = startYear;
        EndYear = endYear;
        Tax = tax;
        Penalty = penalty;
        Amount = amount;
    }
}
