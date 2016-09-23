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
public class DBC_signup extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.android.ioyou/databases/";
    private static String DB_NAME = "debt.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DBC_signup(Context context) {

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
            String q = "CREATE TABLE USER(" +
                    " EMAIL_ID TEXT NOT NULL PRIMARY KEY," +
                    "FIRST_NAME TEXT NOT NULL, " +
                    "LAST_NAME TEXT NOT NULL," +
                    "PASSWORD TEXT NOT NULL," +
                    "PHONE_NUM INTEGER NOT NULL," +
                    "AMOUNT REAL," +
                    "SYNC INTEGER NOT NULL DEFAULT 0)";
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
        query = "DROP TABLE IF EXISTS user";
        db.execSQL(query);
        onCreate(db);
    }
    public void insertUser(HashMap<String, String> queryValues,HashMap<String,Integer> val) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Email_id", queryValues.get("email_id"));
        values.put("First_name",queryValues.get("first_name"));
        values.put("Last_name",queryValues.get("last_name"));
        values.put("Password",queryValues.get("password"));
        //values.put("Img_num",val.get("img_num"));
        values.put("ph_no",val.get("phone_num"));
        values.put("Amount",val.get("amount"));
        values.put("Sync",val.get("sync"));
        db.insert("USER", null, values);
        db.close();
    }
    public ArrayList<HashMap<String,String>> display_all(){
        ArrayList<HashMap<String,String>> word;
        word = new ArrayList<HashMap<String, String>>();
        String q = "SELECT * FROM USER";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(q,null);
        if(c.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Email_id", c.getString(0));
                map.put("First_name", c.getString(1));
                map.put("Last_name", c.getString(2));
                map.put("Password", c.getString(4));
                Float imgno = c.getFloat(3),Sync = c.getFloat(7),phno = c.getFloat(5);
                Float amount = c.getFloat(6);
                map.put("Amount",amount.toString());
                map.put("Img_Num",imgno.toString());
                map.put("Sync",Sync.toString());
                map.put("ph_no",phno.toString());
                word.add(map);

            }while(c.moveToNext());
            db.close();

        }
        return word;
    }
    /*public boolean checkelement(SQLiteDatabase db,String id){
        id = "'"+id+"'";
        String q = "select * from debt where name like "+id+" ";
        Cursor cursor = db.rawQuery(q,null);
        if(cursor.getCount()==0){
            return true;
        }
        else{
            return false;
        }
    }*/

}
