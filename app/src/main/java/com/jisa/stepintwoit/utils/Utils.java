package com.jisa.stepintwoit.utils;

/**
 * Created by jisajose on 2018-01-11.
 */

public class Utils {
    public static final String EXTRA_EMAILID = "EMAILID";
    /**
     * URL
     */
    public static final String URL_POST = "https://reqres.in/api/login";
    public static final String URL_GETUSERDETALS = "https://jsonplaceholder.typicode.com/posts";
    public static final String URL_PHONEDETALS = "http://my-json-server.typicode.com/JisaJose/Stepintwoit/productData";
    public static final String BASE_URL = "http://my-json-server.typicode.com";
    /**
     * product table
     */
    public static final String DATABASE_NAME = "SQLiteDatabase.db";
    public static final String TABLE_NAME_PRODUCT = "PRODUCT";
    public static final String PRDUCT_ID = "productId";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String IMAGE_RATE_BUTTON = "imageRateButton";
    public static final String PHONE = "phone";
    /**
     * QUERY
     */
    public static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_NAME_PRODUCT + "(" + Utils.PRDUCT_ID + " TEXT ,"
            + Utils.NAME + " TEXT ," + Utils.DESCRIPTION + " TEXT ," + Utils.IMAGE + " TEXT ," + Utils.PHONE + " TEXT ," + Utils.IMAGE_RATE_BUTTON + " TEXT );";

    /**
     * Keys
     */
    public static final String KEY_PRODUCT = "productItem";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_EMAILID = "EMAILID";
    public static final String KEY_NAME="nameProduct";
    public static final String KEY_DESCRIPTION="descriptionProduct";
    public static final String KEY_IMAGE_URL="url";
    public static final String KEY_PRODUCTLIST="KEY_PRODUCTLIST";
    /**
     * Versions
     */
    public static final int DATABASE_VERSION = 2;

}


