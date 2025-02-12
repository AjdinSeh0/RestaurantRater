package com.example.restaurantrater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RestaurantDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_RESTAURANT =
            "CREATE TABLE Restaurant (" +
                    "RestaurantID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Name TEXT NOT NULL, " +
                    "StreetAddress TEXT, " +
                    "City TEXT, " +
                    "State TEXT, " +
                    "ZipCode TEXT" +
                    ");";

    private static final String CREATE_TABLE_DISH =
            "CREATE TABLE Dish (" +
                    "DishID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Name TEXT NOT NULL, " +
                    "Type TEXT NOT NULL, " +
                    "Rating INTEGER CHECK(Rating BETWEEN 1 AND 5), " +
                    "RestaurantID INTEGER, " +
                    "FOREIGN KEY (RestaurantID) REFERENCES Restaurant(RestaurantID) ON DELETE CASCADE" +
                    ");";

            public RestaurantDBHelper(Context context){
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_TABLE_RESTAURANT);
                db.execSQL(CREATE_TABLE_DISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RestaurantDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " +
                        newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Restaurant");
        db.execSQL("DROP TABLE IF EXISTS Dish");
        onCreate(db);
    }
}
