package com.jisa.stepintwoit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jisa.stepintwoit.models.User;
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
        {
            String sql = "CREATE TABLE " + Utils.TABLE_NAME + "(" + Utils.USER_ID + " INTEGER ," + Utils.ID + " INTEGER ," + Utils.TITLE +
                    " TEXT ," + Utils.BODY + " TEXT );";
            db.execSQL(sql);
            Log.e("TABLE", "TABLE CREATED");
        }
    }

    public void insertUserDetails(ArrayList<User> userArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < userArrayList.size(); i++) {
            User user = userArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put(Utils.USER_ID, user.getUserId());
            values.put(Utils.ID, user.getId());
            values.put(Utils.TITLE, user.getTitle());
            values.put(Utils.BODY, user.getBody());
            db.insert(Utils.TABLE_NAME, null, values);
        }
        db.close();
        Log.e("values", "value inserted");
    }

    public boolean isMakeApiCall() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + Utils.TABLE_NAME,null);
        if (cur != null) {
            cur.moveToFirst();
            // Always one row returned.
            if (cur.getInt(0) == 0) {               // Zero count means empty table.
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
