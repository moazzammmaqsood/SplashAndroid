package com.nouveau.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class PdfUrl {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PdfUrl() {
    }

    public PdfUrl(String url) {
        this.url = url;
    }
}
