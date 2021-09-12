package com.example.splash.callbacks;

public interface ViewClientCallback {
    void succesfullyDisabledUser();
    void succesfullyEnabledUser();
    void unsuccessful(int responsecode,String message);

}

