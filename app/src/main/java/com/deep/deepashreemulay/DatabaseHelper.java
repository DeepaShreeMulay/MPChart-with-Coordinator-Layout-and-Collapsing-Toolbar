package com.deep.deepashreemulay;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "classesdb";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_STUDENT = "StudentTable";
    public static final String CREATE_TABLE_STUDENT= "CREATE TABLE IF NOT EXISTS "+
            TABLE_STUDENT+ "(Student_id INTEGER PRIMARY KEY AUTOINCREMENT, Student_name TEXT , Student_address TEXT" +
            ", M1 INTEGER , M2 INTEGER , M3 INTEGER  , M4 INTEGER , M5 INTEGER )";
    public static final String DELETE_TABLE_STUDENT="DROP TABLE IF EXISTS " + TABLE_STUDENT;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_STUDENT);

    }
    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DELETE_TABLE_STUDENT);
        //Create tables again
        onCreate(db);
    }

    public void insertData(String Student_name,String Student_address, int M1, int M2, int M3, int M4, int M5 ){

        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try
        {
            values = new ContentValues();
            values.put("Student_name",Student_name);
            values.put("Student_address",Student_address);
            values.put("M1",M1);
            values.put("M2",M2);
            values.put("M3",M3);
            values.put("M4",M4);
            values.put("M5",M5);
            // Insert Row
            long i = db.insert(TABLE_STUDENT, null, values);
            Log.i("Insert", i + "");
            // Insert into database successfully.
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }

    }


}


