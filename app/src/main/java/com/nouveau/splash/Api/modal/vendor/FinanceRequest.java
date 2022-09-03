package com.nouveau.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class FinanceRequest
{
    @SerializedName("amount")
    int amount;
    @SerializedName("remark")
    String remark;
    @SerializedName("date")
    String date;
    @SerializedName("type")
    String type;

    public FinanceRequest() {
    }

    public FinanceRequest(int amount, String remark, String date, String type) {
        this.amount = amount;
        this.remark = remark;
        this.date = date;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
