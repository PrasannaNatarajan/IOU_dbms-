package com.android.ioyou;

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
 * Created by prasanna on 11-03-2016.
 */
public class DBController extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.android.ioyou/databases/";
    private static String DB_NAME = "debt.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DBController(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        //this.myDataBase=this.getWritableDatabase();
    }

    public boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }catch(SQLiteException e){
            checkDB = null;
            //db does not exist

        }

        if(checkDB != null){
            //this.onCreate(checkDB);
            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /*private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {

        boolean dbExist = checkDataBase();
        Log.d("debt","value of dbExist is "+dbExist);
        try {
            if (dbExist) {
                Log.d("inside", "inside create table debt");
                String q = "CREATE TABLE DEBT(" +
                        " EMAIL_ID TEXT PRIMARY KEY NOT NULL, " +
                        "AMOUNT REAL NOT NULL, " +
                        "SYNC INTEGER DEFAULT 0)";
                db.execSQL(q);
            } else {
                Log.d("no db", "no db");
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.d("debt","not able to create");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS DEBT";
        db.execSQL(query);
        onCreate(db);
    }
    public void insertUser(HashMap<String, String> queryValues,HashMap<String,Float> val) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Email_id", queryValues.get("email_id"));
        values.put("Amount",val.get("amount"));
        values.put("Sync",val.get("sync"));
        db.insert("debt", null, values);
        db.close();
    }
    public ArrayList<HashMap<String,String>> display_all() {
        ArrayList<HashMap<String, String>> word;
        word = new ArrayList<HashMap<String, String>>();
        String q = "SELECT * FROM DEBT_LOCAL";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c = db.rawQuery(q, null);
            if (c.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Email_Id", c.getString(0));

                    Float f = c.getFloat(1), f1 = c.getFloat(2);

                    map.put("Sync", f1.toString());
                    map.put("amount", f.toString());
                    word.add(map);

                } while (c.moveToNext());
                db.close();

            }
            return word;
        }catch (Exception e){
            e.printStackTrace();
            Log.d("debt","UGLY ERROR MESSAGE");
        }
        return word;
    }
    public boolean checkelement(SQLiteDatabase db,String id){
        id = "'"+id+"'";
        try {
            String q = "select * from debt where EMAIL_ID like " + id + " ";
            Cursor cursor = db.rawQuery(q,null);
            if (cursor.getCount() == 0) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("dbc check","TERRIBLE ERROR MESSAGE");
        }
        return false;
    }



}
