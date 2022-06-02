package com.example.slangtrans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.SQLData;
import java.util.ArrayList;

public class Mydatabase extends SQLiteOpenHelper
{

    // here we create our data base
    private Context context;
    private static final String DATABASENAME = "Slang_Translater.db"; // name of our database
    private static final int VERSION = 1; // the version

    // nameof our tables
    private static final String TABLENAME = "Slang_word";
    private static final String TABLESEC = "tablesec";
    // all columns
    private static final String COLUMNID = "_id";
    private static final String English_word = "english";
    private static final String french_word = "french";



    public Mydatabase(@Nullable Context context) {//constructor
        super(context, DATABASENAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create our table
        String query2 =
                "CREATE TABLE " + TABLESEC
                + " (" + COLUMNID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + french_word + " TEXT, "+ English_word + " TEXT);";
        db.execSQL(query2);

        String query =
                "CREATE TABLE " + TABLENAME
                        + " (" + COLUMNID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + English_word + " TEXT, "+ french_word + " TEXT);";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // verify if database exist and drop the tables then call the on create
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLESEC);
        onCreate(db);

    }


    void addslang (String english, String french) // parameters are the string which are in the edithview
    {
        // add word to our TABLENAME table
        SQLiteDatabase db = this.getWritableDatabase();//write our table
        ContentValues cv = new ContentValues(); // store all our data
        cv.put(English_word,english);
        cv.put(french_word,french);

        long res = db.insert(TABLENAME, null,cv); // insert in the right table
// verified if the data are added or not
        if (res == -1)
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(context,"Added Successfully",Toast.LENGTH_SHORT).show();
            //sort();
        }

    }

    Cursor readData(){ // contains all our data
        String query = "SELECT * FROM "+ TABLENAME; // select the data from one table
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = null;

        if (db != null){
            cur = db.rawQuery(query,null);
        }
        return cur;

    }


    void deletemain(String id){
        //delete a specific element
        SQLiteDatabase db = this.getWritableDatabase();//write/open our table
        db.execSQL("DELETE FROM " + TABLENAME +  " WHERE "+ English_word + "='" + id + "'");

    }
    void deleall(){
        //delete all the elements
        SQLiteDatabase db = this.getWritableDatabase();//write our table  open
        db.execSQL("DELETE FROM " + TABLENAME);
    }
    void sort(){ // sort in function of English_word column but doesn't work i don't understand why
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("SELECT " + English_word + " FROM "+ TABLENAME + " ORDER BY "+ English_word);
        String test = ("SELECT " + English_word + " FROM "+ TABLENAME + " ORDER BY "+ English_word);
        db.rawQuery(test,null);
    }


    //second tab same function than above

    void addslangsec (String english2, String french2)
    {
        SQLiteDatabase db = this.getWritableDatabase();//write our table
        ContentValues cv = new ContentValues(); // store all our data  


        cv.put(English_word, english2);
        cv.put(french_word, french2);

        long res = db.insert(TABLESEC, null, cv);
        if (res == -1)
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readDataSec(){ // contains all our data
        String query = "SELECT * FROM "+ TABLESEC;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = null;

        if (db != null){
            cur = db.rawQuery(query,null);
        }
        return cur;

    }

    void deletemainSec (String id){
        SQLiteDatabase db = this.getWritableDatabase();//write our table
        db.execSQL("DELETE FROM " + TABLESEC +  " WHERE "+ french_word + "='" + id + "'");

    }
    void deleallsec(){
        SQLiteDatabase db = this.getWritableDatabase();//write our table
        db.execSQL("DELETE FROM " + TABLESEC);
    }


}
