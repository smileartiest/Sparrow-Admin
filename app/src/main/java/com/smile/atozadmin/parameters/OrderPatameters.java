package com.smile.atozadmin.parameters;

public class OrderPatameters {

    String id,uid,name,size,qnt,am,addres,pmode,sts,odate,otime;

    public OrderPatameters() {
    }

    public OrderPatameters(String id, String uid, String name, String size, String qnt, String am, String addres, String pmode, String sts, String odate, String otime) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.size = size;
        this.qnt = qnt;
        this.am = am;
        this.addres = addres;
        this.pmode = pmode;
        this.sts = sts;
        this.odate = odate;
        this.otime = otime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getPmode() {
        return pmode;
    }

    public void setPmode(String pmode) {
        this.pmode = pmode;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public String getOtime() {
        return otime;
    }

    public void setOtime(String otime) {
        this.otime = otime;
    }
}
