package com.android.ioyou.DBC;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prasanna on 14-04-2016.
 */
public class DBC_debt_local extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.android.ioyou/databases/";
    private static String DB_NAME = "debt.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private  static boolean flag=false;

    public DBC_debt_local(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            checkDB = null;
            //db does not exist

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        boolean dbExist = checkDataBase();
        Log.d("local_problems","value of dbExist is "+dbExist);
        if(dbExist){
            flag=true;

             Log.d("mytag debt", "Before CREATE TABLE DEBT");
            String q = "CREATE TABLE DEBT(" +
                    " EMAIL_ID TEXT PRIMARY KEY NOT NULL, " +
                    "AMOUNT REAL NOT NULL, " +
                    "SYNC INTEGER NOT NULL)";
            Log.d("mytag debt", "After CREATE TABLE DEBT");
            db.execSQL(q);
        }
        else{
           /* this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }*/
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS debt_local";
        db.execSQL(query);
        onCreate(db);
    }
    public void insertUser(HashMap<String, String> queryValues,HashMap<String,Float> val) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Email_id", queryValues.get("email_id"));
        values.put("Amount",val.get("amount"));
        values.put("Sync",0);
        db.insert("debt_local", null, values);
        db.close();
    }
    public void insertUserTrans(HashMap<String, String> queryValues,HashMap<String,Float> val) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Email_id", queryValues.get("email_id"));
        Log.d("inserting into debt table",queryValues.get("email_id"));
        values.put("Amount",val.get("amount"));
        values.put("Sync",1);
        db.insert("debt_local", null, values);
        db.close();
    }
    public ArrayList<HashMap<String,String>> display_all() {
        ArrayList<HashMap<String, String>> word;
        word = new ArrayList<HashMap<String, String>>();
        String q = "SELECT * FROM debt_local";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(q, null);
        if (c.getCount() == 0) Log.d("no elem", "no elem in debt_local");
        else {
            if (c.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Email_id", c.getString(0));

                    Float f = c.getFloat(2), amount = c.getFloat(1);
                    map.put("Amount", amount.toString());
                    map.put("Sync", f.toString());

                    word.add(map);
                   // Float amount = c.getFloat(1),f = c.getFloat(2);
                    Log.d("my elems",c.getString(0));
                    Log.d("my elems",f.toString()+" "+amount.toString());

                } while (c.moveToNext());
                db.close();

            }

        }return word;
    }
    public boolean checkelement(SQLiteDatabase db,String id) {


//EXTREME TESTING AREA*********************************************

        boolean dbExist = checkDataBase();
        Log.d("local_problems","value of dbExist is "+dbExist);
        if(dbExist){
            flag=true;

            Log.d("mytag debt", "inside on create");
            String q = "CREATE TABLE IF NOT EXISTS DEBT_LOCAL(" +
                    " EMAIL_ID TEXT PRIMARY KEY NOT NULL, " +
                    "AMOUNT REAL NOT NULL, " +
                    "SYNC INTEGER NOT NULL)";
            db.execSQL(q);
            Log.d("mytag debt", "post-create of DEBT_LOCAL");
        }

//*****************************************************************

        id = "'"+id+"'";
        String q = "select * from debt_local where email_id like "+id+" ";
        Cursor cursor = db.rawQuery(q, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }

    }

}
