package com.example.splash.callbacks;

import com.example.splash.Api.modal.vendor.ClientDetails;
import com.example.splash.Api.modal.vendor.SummaryDaily;
import com.example.splash.Api.modal.vendor.SummaryDelivery;

import java.util.List;

public interface SummaryDeliveryCallBack {

    public void SuccessfullyFetchedSummary(SummaryDaily summary);
    public void SuccessfullyFetchedSummaryDelivery(List<SummaryDelivery> summaryDelivery);
    public void Failed(int Code, String message);




}
