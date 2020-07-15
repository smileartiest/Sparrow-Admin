package com.smile.atozadmin.retrofit;

public class ApiUtil {

    public static final String BASE_URL = "http://samplephpdata.000webhostapp.com/";
    public static final String UPDATETOKEN = BASE_URL + "updatetoken.php";
    public static final String SINGLEPUSH = BASE_URL + "singlepushnotification.php";
    public static final String DOUBLEPUSH = BASE_URL + "doublepushnotification.php";
    public static final String PUSHALL = BASE_URL + "";

    public static RetrofitInterface getServiceClass() {
        return RetrofitAPI.getRetrofit(BASE_URL).create(RetrofitInterface.class);
    }
}
