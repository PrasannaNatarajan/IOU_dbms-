package com.android.ioyou;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ioyou.DBC.DBC_Trans;
import com.android.ioyou.DBC.DBC_debt_local;

import java.util.HashMap;

/**
 * Created by prasanna on 14-03-2016.
 */
public class modify extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);
        Button sub = (Button)findViewById(R.id.submit);
        Bundle b = getIntent().getExtras();
        String n;
        Float a;
        n=b.getString("email_id");
        a=b.getFloat("Amount");
        TextView name,amount;
        name = (TextView)findViewById(R.id.name);
        amount = (TextView)findViewById(R.id.amount);
        name.setText(n);
        amount.setText(a.toString());
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = getIntent().getExtras();
                String n;
                Float a;
                Float id = b.getFloat("sync");
                n=b.getString("email_id");
                a=b.getFloat("Amount");
                TextView name,amount;
                EditText red_amount,add_amount;

                name = (TextView)findViewById(R.id.name);
                amount = (TextView)findViewById(R.id.amount);
                red_amount = (EditText)findViewById(R.id.red_amount);
                add_amount = (EditText)findViewById(R.id.add_amount);
                name.setText(n);
                amount.setText(a.toString());
                Float red,add;
                red =Float.parseFloat(red_amount.getText().toString());
                add = Float.parseFloat(add_amount.getText().toString());
                HashMap<String,String> h1 = new HashMap<String,String>();
                SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_APPEND);
                String fromId = sharedPreferences.getString("user","No user found");
                h1.put("from",fromId);
                h1.put("to", n);
                HashMap<String,Float> h2 = new HashMap<String, Float>();
                h2.put("amount",add-red);

                //Toast.makeText(getApplicationContext()," "+(a+add-red)+" ",Toast.LENGTH_LONG).show();
                DBC_debt_local db = new DBC_debt_local(modify.this);
                DBC_Trans dbc_trans = new DBC_Trans(modify.this);
                SQLiteDatabase sqLiteDatabase = dbc_trans.getWritableDatabase();
                SQLiteDatabase dbs = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("amount",a+add-red);
                dbs.update("debt_local", cv, "email_id like '" + n + "' ", null);
                dbc_trans.insertUser(h1,h2);
                //sqLiteDatabase.update("trans_action",cv,"to_id like '"+n+"'",null);
                Toast.makeText(getApplicationContext(),"Successfully updated", Toast.LENGTH_LONG).show();
                Intent x = new Intent(modify.this,MainActivity.class);
                startActivity(x);
            }
        });

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
