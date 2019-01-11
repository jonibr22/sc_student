package com.example.apps.studycommunity.Adapter;
import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataOpenHelper extends SQLiteAssetHelper{
    private static final String DATABASE_NAME = "SampleDB.db";
    private static final int DATABASE_VERSION = 11;

    public DataOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
}
