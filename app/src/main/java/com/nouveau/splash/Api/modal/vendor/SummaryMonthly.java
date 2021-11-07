package com.nouveau.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class SummaryMonthly {

    @SerializedName("totalpayments")
     Long totalpayments;

    @SerializedName("totalbottles")
     int totalbottles;

    @SerializedName("totalactiveclients")
     int totalactiveclients;

    @SerializedName("countersale")
     Long countersale;

    @SerializedName("expense")
     Long expense;

    @SerializedName("totalrevenue")
     Long totalrevenue;

    @Override
    public String toString() {
        return "SummaryMonthly{" +
                "totalPayments=" + totalpayments +
                ", totalBottles=" + totalbottles +
                ", totalActiveClients=" + totalactiveclients +
                ", counterSale=" + countersale +
                ", expense=" + expense +
                ", totalrevenue=" + totalrevenue +
                '}';
    }

    public Long getTotalpayments() {
        return totalpayments;
    }

    public void setTotalpayments(Long totalpayments) {
        this.totalpayments = totalpayments;
    }

    public int getTotalbottles() {
        return totalbottles;
    }

    public void setTotalbottles(int totalbottles) {
        this.totalbottles = totalbottles;
    }

    public int getTotalactiveclients() {
        return totalactiveclients;
    }

    public void setTotalactiveclients(int totalactiveclients) {
        this.totalactiveclients = totalactiveclients;
    }

    public Long getCountersale() {
        return countersale;
    }

    public void setCountersale(Long countersale) {
        this.countersale = countersale;
    }

    public Long getExpense() {
        return expense;
    }

    public void setExpense(Long expense) {
        this.expense = expense;
    }

    public Long getTotalrevenue() {
        return totalrevenue;
    }

    public void setTotalrevenue(Long totalrevenue) {
        this.totalrevenue = totalrevenue;
    }

    public SummaryMonthly() {
    }

    public SummaryMonthly(Long totalPayments, int totalBottles, int totalActiveClients, Long counterSale, Long expense, Long totalrevenue) {
        this.totalpayments = totalPayments;
        this.totalbottles = totalBottles;
        this.totalactiveclients = totalActiveClients;
        this.countersale = counterSale;
        this.expense = expense;
        this.totalrevenue = totalrevenue;
    }
}
