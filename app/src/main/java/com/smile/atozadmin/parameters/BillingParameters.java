package com.smile.atozadmin.parameters;

public class BillingParameters {

    String id,oid,uid,date,time,amount,sub_total,delivery_charge,discount,total_amount,sts;

    public BillingParameters() {
    }

    public BillingParameters(String id, String oid, String uid, String date, String time, String amount, String sub_total, String delivery_charge, String discount, String total_amount , String sts) {
        this.id = id;
        this.oid = oid;
        this.uid = uid;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.sub_total = sub_total;
        this.delivery_charge = delivery_charge;
        this.discount = discount;
        this.total_amount = total_amount;
        this.sts = sts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
}
