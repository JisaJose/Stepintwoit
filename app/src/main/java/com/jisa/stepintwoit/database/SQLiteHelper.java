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


/**
 * Created by jisajose on 2018-01-18.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Utils.DATABASE_NAME, factory, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Utils.TABLE_NAME + "(" + Utils.NAME + " TEXT ," + Utils.DESCRIPTION + " TEXT ," + Utils.IMAGE +
                " TEXT ," + Utils.PHONE + " TEXT );";
        db.execSQL(sql);
        Log.e("TABLE", "TABLE CREATED");
    }

    public void insertUserDetails(ArrayList<Product> productArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < productArrayList.size(); i++) {
            Product product = productArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put(Utils.NAME, product.getName());
            values.put(Utils.DESCRIPTION, product.getDescription());
            values.put(Utils.IMAGE, product.getImage());
            values.put(Utils.PHONE, product.getPhone());
            db.insert(Utils.TABLE_NAME, null, values);
        }
        db.close();
        Log.e("values", "value inserted");
    }

    public boolean isMakeApiCall() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + Utils.TABLE_NAME, null);
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
        db.delete(Utils.TABLE_NAME, null, null);
        db.close();
    }

    public ArrayList<Product> getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + Utils.TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Product product = new Product();
                product.setName("d-" + cursor.getString(cursor.getColumnIndex(Utils.NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(Utils.DESCRIPTION)));
                product.setPhone(cursor.getString(cursor.getColumnIndex(Utils.PHONE)));
                product.setImage(cursor.getString(cursor.getColumnIndex(Utils.IMAGE)));
                list.add(product);
            }
            return list;
        } else {
            return null;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
