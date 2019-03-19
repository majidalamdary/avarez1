package com.sputa.avarez.model;

public class item_pasmand {

    private String LastPayDate;
    private String StartYear;
    private String EndYear;
    private String GroupName;
    private String Unit;
    private String Debit;
    private String Amount;

    public item_pasmand(String lastPayDate, String startYear, String endYear, String groupName, String unit, String debit, String amount) {
        LastPayDate = lastPayDate;
        StartYear = startYear;
        EndYear = endYear;
        GroupName = groupName;
        Unit = unit;
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

    public String getGroupName() {
        return GroupName;
    }

    public String getUnit() {
        return Unit;
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

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public void setDebit(String debit) {
        Debit = debit;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
