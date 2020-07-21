package com.smile.atozadmin.parameters;

public class DeliveryParameters {
    String did,oid,phone,name,sts,ddate,dtime;

    public DeliveryParameters() {
    }

    public DeliveryParameters(String did, String oid, String phone, String name, String sts, String ddate, String dtime) {
        this.did = did;
        this.oid = oid;
        this.phone = phone;
        this.name = name;
        this.sts = sts;
        this.ddate = ddate;
        this.dtime = dtime;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }
}
