package com.android.ioyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.ioyou.DBC.DBC_Trans;
import com.android.ioyou.DBC.DBC_debt_local;
import com.android.ioyou.DBC.DBC_login;
import com.android.ioyou.login.login;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBC_login dbc_login = new DBC_login(this);
        SQLiteDatabase sqLiteDatabase = dbc_login.getWritableDatabase();
        DBC_debt_local dbc_debt_local = new DBC_debt_local(this);
        SQLiteDatabase sqLiteDatabase1 = dbc_debt_local.getWritableDatabase();
        SharedPreferences sharedPreferences = this.getSharedPreferences("res", MODE_APPEND);
        String res = sharedPreferences.getString("result", "main res");
        //Toast.makeText(this, dbname, Toast.LENGTH_SHORT).show();
        SQLiteDatabase dbs = dbc_login.getReadableDatabase();
        DBC_Trans dbc_trans = new DBC_Trans(this);
        SQLiteDatabase dbt = dbc_trans.getWritableDatabase();


        //************EXTREME TESTING AREA************************************
        boolean dbExist = dbc_debt_local.checkDataBase();
        Log.d("local_problems","value of dbExist is "+dbExist);
        if(dbExist){


            Log.d("mytag debt", "inside on create");
            String q = "CREATE TABLE IF NOT EXISTS DEBT_LOCAL(" +
                    " EMAIL_ID TEXT PRIMARY KEY NOT NULL, " +
                    "AMOUNT REAL NOT NULL, " +
                    "SYNC INTEGER NOT NULL)";
            sqLiteDatabase1.execSQL(q);
            Log.d("mytag debt", "post-create of DEBT_LOCAL");
        }

        dbExist = dbc_trans.checkDataBase();
        Log.d("local_problems","value of dbExist is "+dbExist);
        if(dbExist){


            Log.d("mytag debt", "inside on create");
            String q = "CREATE TABLE IF NOT EXISTS TRANS_ACTION(" +
                    " TO_ID TEXT NOT NULL," +
                    " FROM_ID TEXT NOT NULL, " +
                    "AMOUNT REAL NOT NULL," +
                    " TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP PRIMARY KEY, " +
                    "SYNC INTEGER NOT NULL)";
            dbt.execSQL(q);

            Log.d("mytag debt", "post-create of DEBT_LOCAL");
        }

        //*******************************************************************/
        if (!res.equals("login success")) {
            Intent i = new Intent(MainActivity.this, login.class);
            Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
            Log.d("inside", "no element in login");
            startActivity(i);
        } else {
            BackgroundLogin login = new BackgroundLogin(this);
            if(!login.isCancelled()) {
                login.cancel(true);
                Log.d("cancel",login.isCancelled()+"");
            }
            //Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);
            //RelativeLayout layout = new RelativeLayout(this);
            //layout.requestLayout();
            /*DBController dbController = new DBController(MainActivity.this);
            sqLiteDatabase = dbController.getWritableDatabase();*/

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            RelativeLayout plus = (RelativeLayout) findViewById(R.id.plus_button);
            final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("See All"));
            tabLayout.addTab(tabLayout.newTab().setText("Logs"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PageAdapter adapter = new PageAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount());


            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    //Toast.makeText(getBaseContext(),"Tab number "+tab.getPosition()+" ",Toast.LENGTH_SHORT).show();
                    if (tab.getPosition() == 0) {
                        PageAdapter adpter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adpter);
                    }else{
                        PageAdapter adpter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adpter);
                    }
                    viewPager.setCurrentItem(tab.getPosition());

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());

                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(MainActivity.this, Form.class);
                        Toast.makeText(getBaseContext(), "You are adding new ", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Error creating activity", Toast.LENGTH_LONG).show();
                    }
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

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if(id == R.id.about_dev){
            try{
                Intent i = new Intent(MainActivity.this,about.class);
                startActivity(i);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Unable to create Activity",Toast.LENGTH_SHORT).show();
            }
        }
        /*if(id==R.id.del_all){
            DBC_debt_local db = new DBC_debt_local(getApplicationContext());
            SQLiteDatabase mydb = db.getWritableDatabase();
            db.onUpgrade(mydb,1,2);
        }*/
        if(id == R.id.sync_all){
            //Log.d("calling","IN periya if");
            BackgroundTrans transmitter = new BackgroundTrans(this);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("trans",true);
                transmitter.execute(jsonObject);

            }catch(Exception e){
                e.printStackTrace();
                Log.d("error","injson");
            }

            /*SharedPreferences sharedPreferences = getSharedPreferences("res",MODE_APPEND);
            String res = sharedPreferences.getString("result_sync","no sync");
            if(res.equals("sync success")){
                DBC_Trans dbc_trans = new DBC_Trans(this);
                SQLiteDatabase sqLiteDatabase = dbc_trans.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("sync","1");

                sqLiteDatabase.update("trans_action",cv,"",null);

            }*/
        }
        /*if(id==R.id.del_trans){
            DBC_Trans dbc_trans = new DBC_Trans(this);
            SQLiteDatabase sqLiteDatabase = dbc_trans.getWritableDatabase();
            dbc_trans.onUpgrade(sqLiteDatabase,1,2);
        }*/

        return super.onOptionsItemSelected(item);
    }


}
