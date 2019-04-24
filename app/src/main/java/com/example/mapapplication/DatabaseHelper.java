package com.example.mapapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper  {


    // Database
    public static final String DATABASE_NAME = "ItemsBinsDB";
    private static final int DATABASE_VERSION = 2;

    //Table Items
    public static final String TABLE_NAME = "Items";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_SYMBOL = "Symbol";

    public static final String create = "CREATE TABLE "+TABLE_NAME+ "(" +
            COLUMN_ID+ " TEXT PRIMARY KEY," +
            COLUMN_NAME+ " TEXT NOT NULL," +
            COLUMN_TYPE+" TEXT NOT NULL," +
            COLUMN_SYMBOL+" TEXT NOT NULL);";

    //Table Bins
    public static final String TABLE_TWO_NAME = "Bins";
    public static final String COLUMN_TWO_ID = "_id";
    public static final String COLUMN_LAT = "Latitude";
    public static final String COLUMN_LOG = "Longitude";
    public static final String COLUMN_DESC = "Description";
    public static final String COLUMN_HUE = "Hue";

    public static final String createBins = "CREATE TABLE "+TABLE_TWO_NAME+ "(" +
            COLUMN_TWO_ID+ " TEXT PRIMARY KEY," +
            COLUMN_LAT+ " REAL NOT NULL," +
            COLUMN_LOG+ " REAL NOT NULL," +
            COLUMN_DESC+ " TEXT NOT NULL," +
            COLUMN_HUE+ " INTEGER NOT NULL);";



    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        //creates the table using the name in Item class
        db.execSQL(create);
        db.execSQL(createBins);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //drop older table first
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWO_NAME);

        //recreate table with new info
        onCreate(db);
    }


    public void addItem(String id, String name,String type, String symbol)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_SYMBOL,symbol);


        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addBin(int id,double lat,double log, String desc, float hue)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TWO_ID, id);
        values.put(COLUMN_LAT,lat);
        values.put(COLUMN_LOG,log);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_HUE,hue);


        db.insert(TABLE_TWO_NAME, null, values);
        db.close();
    }

    public Item getItem(String id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID,COLUMN_NAME,COLUMN_TYPE,COLUMN_SYMBOL},
                COLUMN_ID + "=?",
                new String[]{id},
                null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        Item item = new Item();

        item.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
        item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        Log.d("cursorLog","item type is: " +cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
        item.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
        Log.d("cursorLog","item symbol is: " +cursor.getString(cursor.getColumnIndex(COLUMN_SYMBOL)));
        item.setSymbol(cursor.getString(cursor.getColumnIndex(COLUMN_SYMBOL)));

        cursor.close();
        return item;
    }

    //check if Item is already in table
    public boolean checkItem(String id)
    {
        Log.d("myTag", id);
        SQLiteDatabase db = this.getReadableDatabase();
        //Select condition
        String selection = COLUMN_ID + " = ?";
        //Arguments for selection
        String[] selectionArgs = {id};

        Cursor c = db.query(TABLE_NAME,null,selection,selectionArgs,null,null,null);

        boolean exists = (c.getCount() > 0);
        Log.d("myTag", String.valueOf(exists));
        c.close();
        db.close();
        return exists;
    }

    public boolean checkBin(String id)
    {
        Log.d("myTag", id);
        SQLiteDatabase db = this.getReadableDatabase();
        //Select condition
        String selection = COLUMN_TWO_ID + " = ?";
        //Arguments for selection
        String[] selectionArgs = {id};

        Cursor c = db.query(TABLE_TWO_NAME,null,selection,selectionArgs,null,null,null);

        boolean exists = (c.getCount() > 0);
        Log.d("myTag", String.valueOf(exists));
        c.close();
        db.close();
        return exists;
    }



    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }

    //get all Items
    public Cursor getListContents(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //get all Bins
    public List<Bin> getAllBins() {
        List<Bin> bins = new ArrayList<Bin>();
        String selectQuery = "SELECT  * FROM " + TABLE_TWO_NAME;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Bin b = new Bin();
                b.setId(c.getInt(c.getColumnIndex(COLUMN_TWO_ID)));
                b.setDescription(c.getString(c.getColumnIndex(COLUMN_DESC)));
                b.setHue(c.getFloat(c.getColumnIndex(COLUMN_HUE)));
                b.setLat(c.getDouble(c.getColumnIndex(COLUMN_LAT)));
                b.setLog(c.getDouble(c.getColumnIndex(COLUMN_LOG)));

                // adding to bins list
                bins.add(b);
            } while (c.moveToNext());
        }
        return bins;
    }


}
