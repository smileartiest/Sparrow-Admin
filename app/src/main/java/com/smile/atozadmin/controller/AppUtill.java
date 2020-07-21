package com.smile.atozadmin.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class AppUtill {

    public static DatabaseReference OFFERURL = FirebaseDatabase.getInstance().getReference("offer");
    public static DatabaseReference CATURL = FirebaseDatabase.getInstance().getReference("categorys");
    public static DatabaseReference ORDERURl = FirebaseDatabase.getInstance().getReference("order");
    public static DatabaseReference MARKETURL = FirebaseDatabase.getInstance().getReference("market");
    public static DatabaseReference DRESSURL = FirebaseDatabase.getInstance().getReference("dress");
    public static DatabaseReference EMPURL = FirebaseDatabase.getInstance().getReference("employe");
    public static DatabaseReference ADDRESURL = FirebaseDatabase.getInstance().getReference("address");
    public static DatabaseReference REGURL = FirebaseDatabase.getInstance().getReference("usregister");
    public static DatabaseReference DRESSPICURL = FirebaseDatabase.getInstance().getReference("dresspic");
    public static DatabaseReference LOCURL = FirebaseDatabase.getInstance().getReference("locations");
    public static DatabaseReference STSURL = FirebaseDatabase.getInstance().getReference("storestatus");
    public static DatabaseReference NOTIFYURL = FirebaseDatabase.getInstance().getReference("notification");
    public static DatabaseReference DELIVERYURl = FirebaseDatabase.getInstance().getReference("delivery");
    public static DatabaseReference BILLINGURl = FirebaseDatabase.getInstance().getReference("billing");
    public static DatabaseReference ELECTRONICURL = FirebaseDatabase.getInstance().getReference("electronics");

}
