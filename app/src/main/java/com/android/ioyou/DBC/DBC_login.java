package com.android.ioyou.DBC;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prasanna on 10-04-2016.
 */
public class DBC_login  extends SQLiteOpenHelper{
    private static String DB_PATH = "/data/data/com.android.ioyou/databases/";
    private static String DB_NAME = "debt.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private  static boolean flag=false;

    public DBC_login(Context context) {

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
        if(dbExist){
            flag=true;
           // Log.d("inside","inside on create");
            String q = "CREATE TABLE LOGIN(" +
                    " EMAIL_ID TEXT PRIMARY KEY NOT NULL, " +
                    "PASSWORD TEXT NOT NULL, " +
                    "SYNC INTEGER NOT NULL)";
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
        query = "DROP TABLE IF EXISTS login";
        db.execSQL(query);
        onCreate(db);
    }
    public void insertUser(HashMap<String, String> queryValues,HashMap<String,Integer> val) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Email_id", queryValues.get("email_id"));
        values.put("Password",queryValues.get("password"));
        values.put("Sync",val.get("sync"));
        db.insert("login", null, values);
        db.close();
    }
    public ArrayList<HashMap<String,String>> display_all(){
        ArrayList<HashMap<String,String>> word;
        word = new ArrayList<HashMap<String, String>>();
        String q = "SELECT * FROM LOGIN";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(q,null);
        if(c.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Email_id", c.getString(0));
                map.put("Password", c.getString(1));
                Float f = c.getFloat(2);

                map.put("Sync",f.toString());
                word.add(map);

            }while(c.moveToNext());
            db.close();

        }
        return word;
    }
    public boolean checkelement(SQLiteDatabase db) {
        if (flag) {
            String q = "select * from login";
            Cursor cursor = db.rawQuery(q, null);
            if (cursor.getCount() == 0) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }


}
