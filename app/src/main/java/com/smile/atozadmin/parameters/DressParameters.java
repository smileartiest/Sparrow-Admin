package com.smile.atozadmin.parameters;

public class DressParameters {

    String id,dname,dtype,categ,cat,size,dam,doff,dpic,stock;

    public DressParameters() {
    }

    public DressParameters(String id, String dname, String dtype, String categ,String cat , String size, String dam, String doff, String dpic, String stock) {
        this.id = id;
        this.dname = dname;
        this.dtype = dtype;
        this.categ = categ;
        this.cat = cat;
        this.size = size;
        this.dam = dam;
        this.doff = doff;
        this.dpic = dpic;
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getDam() {
        return dam;
    }

    public void setDam(String dam) {
        this.dam = dam;
    }

    public String getDoff() {
        return doff;
    }

    public void setDoff(String doff) {
        this.doff = doff;
    }

    public String getDpic() {
        return dpic;
    }

    public void setDpic(String dpic) {
        this.dpic = dpic;
    }
}
