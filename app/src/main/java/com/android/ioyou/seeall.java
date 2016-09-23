package com.android.ioyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prasanna on 13-03-2016.
 */
public class seeall extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeall);

        DBController db = new DBController(this);
        if(db.checkDataBase()){
           ArrayList<HashMap<String,String>> itemsList = db.display_all();
            String[] str = new String[itemsList.size()];
            ArrayList<String> arrstr = new ArrayList<>();
            HashMap<String,String> h;
            for(int i=0;i<itemsList.size();i++){
                h = itemsList.get(i);
                str[i]=h.get("Name");
                arrstr.add(str[i]);
                //str[i][1]=h.get("amount");
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    arrstr );
            ListView myList=(ListView)findViewById(R.id.seeall);
            myList.setAdapter(arrayAdapter);

           myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   DBController db = new DBController(seeall.this);
                   ArrayList<HashMap<String,String>> itemsList = db.display_all();
                   String[][] str = new String[itemsList.size()][3];
                   //ArrayList<String> arrstr = new ArrayList<>();
                   HashMap<String,String> h;
                   for(int i=0;i<itemsList.size();i++){
                       h = itemsList.get(i);
                       str[i][0]=h.get("Name");
                       //arrstr.add(str[i]);
                       str[i][1]=h.get("amount");
                       str[i][2]=h.get("Id");
                   }
                   Toast.makeText(getApplicationContext(),str[position][1],Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(seeall.this,modify.class);
                   Bundle b = new Bundle();
                   b.putString("name",str[position][0]);
                   Integer Id = Integer.parseInt(str[position][2]);
                   b.putInt("Id",Id);
                   Float amount = Float.parseFloat(str[position][1]);
                   b.putFloat("amount",amount);
                   intent.putExtras(b);
                   startActivity(intent);
               }
           });
        }
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



        return super.onOptionsItemSelected(item);
    }
}
