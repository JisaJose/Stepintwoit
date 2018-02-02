package com.jisa.stepintwoit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jisa.stepintwoit.models.Product;
import com.jisa.stepintwoit.utils.Utils;

import java.util.ArrayList;

import static com.jisa.stepintwoit.utils.Utils.TABLE_NAME_PRODUCT;


/**
 * Created by jisajose on 2018-01-18.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utils.CREATE_PRODUCT_TABLE);
        Log.e("TABLE", "TABLE CREATED");
    }

    public void insertUserDetails(ArrayList<Product> productArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < productArrayList.size(); i++) {
            Product product = productArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put(Utils.PRDUCT_ID, product.getProductId());
            values.put(Utils.NAME, product.getName());
            values.put(Utils.DESCRIPTION, product.getDescription());
            values.put(Utils.IMAGE, product.getImage());
            values.put(Utils.PHONE, product.getPhone());
            values.put(Utils.IMAGE_RATE_BUTTON, "0");
            db.insert(TABLE_NAME_PRODUCT, null, values);
        }
        db.close();
        Log.e("values", "value inserted");
    }

    public boolean isMakeApiCall() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME_PRODUCT, null);
        if (cur != null) {//there is dada
            cur.moveToFirst();
            // Always one row returned.
            if (cur.getInt(0) == 0) {               // Zero count means empty table.
                return true;
            }
        }
        return false;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_PRODUCT, null, null);
        db.close();
    }

    //passing values  from sqlite table to arraylist
    public ArrayList<Product> getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME_PRODUCT, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setProductId(cursor.getString(cursor.getColumnIndex(Utils.PRDUCT_ID)));
                product.setName("d-" + cursor.getString(cursor.getColumnIndex(Utils.NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(Utils.DESCRIPTION)));
                product.setPhone(cursor.getString(cursor.getColumnIndex(Utils.PHONE)));
                product.setImage(cursor.getString(cursor.getColumnIndex(Utils.IMAGE)));
                product.setImgFavorate(cursor.getString(cursor.getColumnIndex(Utils.IMAGE_RATE_BUTTON)));
                list.add(product);
            }
            while (cursor.moveToNext());
            return list;
        } else {
            return null;
        }
    }

    //UPDATE TABLE DETALS
    public void updateProductFavorate(String productId, boolean isfavorate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String isfa = isfavorate ? "1" : "0";
        String strSQL = "UPDATE " + Utils.TABLE_NAME_PRODUCT + " SET " + Utils.IMAGE_RATE_BUTTON + " = " + isfa + " WHERE " + Utils.PRDUCT_ID + " = '" + productId + "'";

        db.execSQL(strSQL);
    }

    // if old version then update table or create table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("TABLE ", "older version is :" + oldVersion + "new version is :" + newVersion);
        if (oldVersion < newVersion) {
            db.rawQuery("DROP TABLE " + "USER", null);
            db.execSQL(Utils.CREATE_PRODUCT_TABLE);

        }

    }
}
