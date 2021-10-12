package com.nouveau.splash.callbacks;

import com.nouveau.splash.Api.modal.vendor.ClientDetails;

public interface ClientCallback {

    public void ClientFetched(ClientDetails clientDetails);
    public void FailedClientFetched(int Code,String message );




}
