package com.tarun.saini.baking_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_ID;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_INGREDIENT;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_MEASURE;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_NAME;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_QUANTITY;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.TABLE_NAME;

/**
 * Created by Tarun on 25-07-2017.
 */

public class IngredientDbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "widgetIngredient.db";

    public static final String LOGTAG = "WIDGET_INGREDIENT";
    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase db;


    public IngredientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }




    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_NAME + "("+
                  _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ID + " INTEGER,"
                + KEY_NAME + " TEXT NOT NULL,"
                + KEY_QUANTITY + " DOUBLE NOT NULL,"
                + KEY_MEASURE + " TEXT NOT NULL,"
                + KEY_INGREDIENT + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_INGREDIENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
