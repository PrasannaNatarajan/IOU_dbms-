package com.android.ioyou.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.ioyou.BackgroundLogin;
import com.android.ioyou.DBC.DBC_login;
import com.android.ioyou.MainActivity;
import com.android.ioyou.R;

import java.util.HashMap;

/**
 * Created by prasanna on 10-04-2016.
 */
public class login extends Activity {
    public String email,password;
    static int counter =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Context con = this;
        final Button login = (Button)findViewById(R.id.login);

        final TextView signup=(TextView)findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText psw = (EditText)findViewById(R.id.signup_password),uid = (EditText) findViewById(R.id.uid);
                email = uid.getText().toString().trim();
                password = psw.getText().toString().trim();
                HashMap<String,String> h1 = new HashMap<String, String>();
                HashMap<String,Integer> h2 = new HashMap<String, Integer>();

                BackgroundLogin login1 = new BackgroundLogin(con);
                String type = "login";
                //Toast.makeText(getApplicationContext(),email, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();
                login1.execute(email,password,type);
                SharedPreferences sharedPreferences = getSharedPreferences("res",MODE_APPEND);
                String res=sharedPreferences.getString("result","login_res");
                //String res = login1.get_result();
                //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                //Log.d("variable res", res);

                if(res.equals("login success")) {
                    DBC_login dbc_login = new DBC_login(getApplicationContext());
                    SQLiteDatabase sqLiteDatabase = dbc_login.getWritableDatabase();
                    h1.put("Email_id", email);
                    h1.put("Password", password);
                    h2.put("Sync", 1);
                    dbc_login.insertUser(h1, h2);
                    Intent i = new Intent(login.this, MainActivity.class);
                    /*i.putExtra("result",res);
                    setResult(Activity.RESULT_OK,i);
                    finish();*/
                    SharedPreferences sp = getSharedPreferences("user",MODE_APPEND);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("user", email);
                    editor.commit();
                    //Toast.makeText(getApplicationContext(),"login table inserted",Toast.LENGTH_SHORT).show();
                    finish();
                    if(!login1.isCancelled()) {
                        login1.cancel(true);
                        Log.d("cancel", login1.isCancelled() + "");
                    }
                    startActivity(i);
                }
                login.setText("Proceed");

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,signup.class);
                startActivity(i);
            }
        });
    }
}
