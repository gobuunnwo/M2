package com.example.m2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes_db";
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE =
            "CREATE TABLE "+TABLE_NAME+"("
            +COLUMN_ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +COLUMN_NOTE+"TEXT,"
            +COLUMN_TIMESTAMP+"DATABASE DEFAULT CURRENT_TIMESTAMP"
            +")";

    public DatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertNote(String note){
        //get Writable database
        SQLiteDatabase db = this.getReadableDatabase();

        //"id" and "timestamp" will be inserted automatically
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE,note);

        //insert row
        long id = db.insert(TABLE_NAME,null,values);


        //close the database
        db.close();

        //return newly inserted row id
        return id;

    }

    public List<Note> getALLNotes() {
        List<Note> notes = new ArrayList<>();

        //Select ALL Query
        String selectQuery = "SELECT * FROM "+TABLE_NAME + " ORDER BY " +
                COLUMN_TIMESTAMP + "DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);


        //looping through all rows and adding to the list
        if(cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        //close db connection
        db.close();

        //return notes list
        return notes;
    }
}
