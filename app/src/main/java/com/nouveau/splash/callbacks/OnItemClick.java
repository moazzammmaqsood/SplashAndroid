package com.nouveau.splash.callbacks;

import com.nouveau.splash.Api.modal.vendor.ClientDetails;

public interface OnItemClick {
    public void ClientFetched(ClientDetails clientDetails);
    public void FailedClientFetched(int Code,String message );
    void DeleteOrder(int orderid);
}
