package com.smile.atozadmin.parameters;

public class OfferParameters {
    String id,picurl;

    public OfferParameters() {
    }

    public OfferParameters(String id, String picurl) {
        this.id = id;
        this.picurl = picurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}
