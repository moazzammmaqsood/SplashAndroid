package com.nouveau.splash.Api.modal.vendor;

import java.util.Date;

public class FinanceEntity {

     int id;

     int income;

     int expense;

     Date date;

     String remarks;

     String tags;

     String status;

     Integer vendorid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVendorid() {
        return vendorid;
    }

    public void setVendorid(Integer vendorid) {
        this.vendorid = vendorid;
    }

    public FinanceEntity() {
    }

    public FinanceEntity(int id, int income, int expense, Date date, String remarks, String tags, String status, Integer vendorid) {
        this.id = id;
        this.income = income;
        this.expense = expense;
        this.date = date;
        this.remarks = remarks;
        this.tags = tags;
        this.status = status;
        this.vendorid = vendorid;
    }

    @Override
    public String toString() {
        return "FInanceEntity{" +
                "id=" + id +
                ", income=" + income +
                ", expense=" + expense +
                ", date=" + date +
                ", remarks='" + remarks + '\'' +
                ", tags='" + tags + '\'' +
                ", status='" + status + '\'' +
                ", vendorid=" + vendorid +
                '}';
    }
}
