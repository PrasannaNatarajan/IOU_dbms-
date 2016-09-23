package com.android.ioyou;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by prasanna on 18-03-2016.
 */
public class MainMenu extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add,about,see,delete;
        add = (Button)findViewById(R.id.add);
        about = (Button)findViewById(R.id.about);
        see = (Button)findViewById(R.id.see);
        delete = (Button)findViewById(R.id.delete);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainMenu.this, Form.class);
                    Toast.makeText(getBaseContext(), "You are adding new ", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(getBaseContext(),"Error creating activity",Toast.LENGTH_LONG).show();
                }
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent i = new Intent(MainMenu.this,about.class);
                    startActivity(i);
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Unable to create Activity",Toast.LENGTH_SHORT).show();
                }

            }
        });
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this,seeall.class);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBController db = new DBController(getApplicationContext());
                SQLiteDatabase mydb = db.getWritableDatabase();
                db.onUpgrade(mydb,1,2);
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
