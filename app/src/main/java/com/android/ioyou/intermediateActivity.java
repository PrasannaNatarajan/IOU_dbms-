package com.android.ioyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.ioyou.DBC.DBC_Trans;
import com.android.ioyou.DBC.DBC_debt_local;

import java.util.HashMap;

/**
 * Created by prasanna on 11-03-2016.
 */
public class intermediateActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait);
        try{
            Bundle b = getIntent().getExtras();
            String email = b.getString("email_id");
            float amount = b.getFloat("amount");
            Log.d("debug_ins",email);
            Log.d("debug_ins",amount+"");
            DBC_debt_local db = new DBC_debt_local(intermediateActivity.this);
            SQLiteDatabase dbs = db.getReadableDatabase();
            DBC_Trans dbt = new DBC_Trans(intermediateActivity.this);
            SQLiteDatabase dbts = dbt.getWritableDatabase();
            //Toast.makeText(this, db.checkelement(dbs, email) + "", Toast.LENGTH_SHORT).show();
            /*if(db.checkelement(dbs,email)){*/
            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_APPEND);
            String from = sharedPreferences.getString("user","no user logged in");
            HashMap<String,String> val = new HashMap<String,String>();
            val.put("email_id",email);
            val.put("to",email);
            val.put("from",from);
            HashMap<String,Float>aval = new HashMap<String,Float>();
            aval.put("amount",amount);
            DBC_debt_local dbc = new DBC_debt_local(intermediateActivity.this);
            dbc.getWritableDatabase();
            dbc.insertUser(val,aval);
            dbt.insertUser(val,aval);
            dbts.close();
            dbt.close();
            dbc.close();
            db.close();
            /*}else {
                Toast.makeText(this,"chech item failed",Toast.LENGTH_SHORT).show();
                *//*int temp=0;
                DBC_debt_local dbc = new DBC_debt_local(intermediateActivity.this);
                SQLiteDatabase dbcs = db.getWritableDatabase();
                ArrayList<HashMap<String, String>> itemsList = dbc.display_all();
                String[][] str = new String[itemsList.size()][3];
                HashMap<String, String> h;
                for (int i = 0; i < itemsList.size(); i++) {
                    h = itemsList.get(i);
                    str[i][0] = h.get("email_id");
                    str[i][1] = h.get("amount");
                    str[i][2] = h.get("sync");
                    if (email.equals(str[i][0])) {
                        temp = i;

                    }
                }
                ContentValues cv = new ContentValues();
                cv.put("amount", amount + Float.parseFloat(str[temp][1]));

                dbcs.update("debt", cv, "email_id=" + email + " ", null);
                Toast.makeText(getApplicationContext(), "Name Already exist", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Adding to the same name", Toast.LENGTH_SHORT).show();
                dbcs.close();
                dbc.close();*//*
            }*/
        }
        catch(Exception e){

            e.printStackTrace();
        }
        Intent intent = new Intent(intermediateActivity.this,MainActivity.class);
        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
