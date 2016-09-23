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
 * Created by prasanna on 15-04-2016.
 */
public class DBC_Trans extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.android.ioyou/databases/";
    private static String DB_NAME = "debt.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private  static boolean flag=false;

    public DBC_Trans(Context context) {

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
        Log.d("local_problems", "value of dbExist is " + dbExist);
        if(dbExist){
            flag=true;

            Log.d("mytag debt", "Before CREATE TABLE DEBT");
            String q = "CREATE TABLE IF NOT EXISTS TRANS_ACTION(" +
                    " TO_ID TEXT NOT NULL," +
                    " FROM_ID TEXT NOT NULL, " +
                    "AMOUNT REAL NOT NULL," +
                    " TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP PRIMARY KEY, " +
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
        query = "DROP TABLE IF EXISTS TRANS_ACTION";
        db.execSQL(query);
        onCreate(db);
    }
    public void insertUser(HashMap<String, String> queryValues,HashMap<String,Float> val) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("to_id", queryValues.get("to"));
        values.put("from_id", queryValues.get("from"));
        values.put("Amount",val.get("amount"));
        values.put("Sync",0);
        db.insert("TRANS_ACTION", null, values);
        db.close();
    }
    public void insertUserDownSync(HashMap<String, String> queryValues,HashMap<String,Float> val) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("to_id", queryValues.get("to"));
        values.put("from_id", queryValues.get("from"));
        values.put("Amount",val.get("amount"));
        values.put("Sync",1);
        values.put("Timestamp",queryValues.get("time"));
        db.insert("TRANS_ACTION", null, values);
        db.close();
    }
    public ArrayList<HashMap<String,String>> display_all() {
        ArrayList<HashMap<String, String>> word;
        word = new ArrayList<HashMap<String, String>>();
        String q = "SELECT * FROM TRANS_ACTION";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(q, null);
        if (c.getCount() == 0) Log.d("no elem", "no elem in debt_local");
        else {
            if (c.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("to_id", c.getString(0));
                    map.put("from_id", c.getString(1));
                    map.put("time",c.getString(3));
                    Float f = c.getFloat(4), amount = c.getFloat(2);
                    map.put("Amount", amount.toString());
                    map.put("Sync", f.toString());

                    word.add(map);
                    // Float amount = c.getFloat(1),f = c.getFloat(2);
                    Log.d("my elems",c.getString(3));
                    Log.d("my elems",f.toString()+" "+amount.toString());

                } while (c.moveToNext());
                db.close();

            }

        }return word;
    }
    public boolean checkelement(SQLiteDatabase db,String id) {

        id = "'"+id+"'";
        String q = "select * from trans_action where to_id like "+id+" ";
        Cursor cursor = db.rawQuery(q, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }

    }
}
