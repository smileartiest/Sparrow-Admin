package com.smile.atozadmin.parameters;

public class OrderParameters {
    String id,uid,name,size,qnt,am,bam,addres,pmode,sts,pdate,ddate,odate,otime,did,stock;

    public OrderParameters() {
    }

    public OrderParameters(String id, String uid, String name, String size, String qnt, String am, String bam, String addres, String pmode, String sts, String pdate, String ddate, String odate, String otime, String did, String stock) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.size = size;
        this.qnt = qnt;
        this.am = am;
        this.bam = bam;
        this.addres = addres;
        this.pmode = pmode;
        this.sts = sts;
        this.pdate = pdate;
        this.ddate = ddate;
        this.odate = odate;
        this.otime = otime;
        this.did = did;
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
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

    public String getBam() {
        return bam;
    }

    public void setBam(String bam) {
        this.bam = bam;
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
}
