package com.sputa.avarez.model;

public class item_nosazi {
    private String LastPayDate;
    private String StartYear;
    private String EndYear;
    private String Services;
    private String Debit;
    private String Amount;

    public item_nosazi(String lastPayDate, String startYear, String endYear, String services, String debit, String amount) {
        LastPayDate = lastPayDate;
        StartYear = startYear;
        EndYear = endYear;
        Services = services;
        Debit = debit;
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

    public String getServices() {
        return Services;
    }

    public String getDebit() {
        return Debit;
    }

    public String getAmount() {
        return Amount;
    }

    public void setLastPayDate(String lastPayDate) {
        LastPayDate = lastPayDate;
    }

    public void setStartYear(String startYear) {
        StartYear = startYear;
    }

    public void setEndYear(String endYear) {
        EndYear = endYear;
    }

    public void setServices(String services) {
        Services = services;
    }

    public void setDebit(String debit) {
        Debit = debit;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
