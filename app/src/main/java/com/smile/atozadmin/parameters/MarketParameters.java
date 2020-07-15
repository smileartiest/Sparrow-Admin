package com.smile.atozadmin.parameters;

public class MarketParameters {
    String id,mpic,mname,mtype,mcatg,mqnt,mam,stock;

    public MarketParameters() {
    }

    public MarketParameters(String id, String mpic, String mname, String mtype, String mcatg, String mqnt, String mam, String stock) {
        this.id = id;
        this.mpic = mpic;
        this.mname = mname;
        this.mtype = mtype;
        this.mcatg = mcatg;
        this.mqnt = mqnt;
        this.mam = mam;
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMpic() {
        return mpic;
    }

    public void setMpic(String mpic) {
        this.mpic = mpic;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getMcatg() {
        return mcatg;
    }

    public void setMcatg(String mcatg) {
        this.mcatg = mcatg;
    }

    public String getMqnt() {
        return mqnt;
    }

    public void setMqnt(String mqnt) {
        this.mqnt = mqnt;
    }

    public String getMam() {
        return mam;
    }

    public void setMam(String mam) {
        this.mam = mam;
    }
}
