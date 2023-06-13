package com.nouveau.splash.callbacks;

public interface ViewClientCallback {
    void succesfullyDisabledUser();
    void succesfullyEnabledUser();
    void unsuccessful(int responsecode,String message);

    void readyToViewPdf(String url);

}

