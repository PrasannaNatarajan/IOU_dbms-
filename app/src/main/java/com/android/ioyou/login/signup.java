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
import android.widget.Toast;

import com.android.ioyou.BackgroundLogin;
import com.android.ioyou.DBC.DBC_signup;
import com.android.ioyou.MainActivity;
import com.android.ioyou.R;

import java.util.HashMap;

/**
 * Created by prasanna on 10-04-2016.
 */
public class signup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        final Context con = this;
        final Button signupButton = (Button) findViewById(R.id.signup_submit);
            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                EditText first_name = (EditText) findViewById(R.id.signup_fname);
                EditText last_name = (EditText) findViewById(R.id.signup_lname);
                EditText email_id = (EditText) findViewById(R.id.signup_email_ID);
                EditText phone_number = (EditText) findViewById(R.id.signup_phone_number);
                EditText pwd = (EditText) findViewById(R.id.signup_password);

                String firstName = first_name.getText().toString();
                String lastName = last_name.getText().toString();
                String emailID = email_id.getText().toString();
                String phoneNumber = phone_number.getText().toString();
                String password = pwd.getText().toString();

                HashMap<String, String> h1 = new HashMap<String, String>();
                HashMap<String, Integer> h2 = new HashMap<String, Integer>();

                //DBC_signup dbc_signup = new DBC_signup(getApplicationContext());
                //SQLiteDatabase sqLiteDatabase = dbc_signup.getWritableDatabase();

                //Toast.makeText(getApplicationContext(), emailID, Toast.LENGTH_SHORT).show();
                BackgroundLogin signup = new BackgroundLogin(con);

                String type = "signup";

                //Toast.makeText(getApplicationContext(), password, Toast.LENGTH_SHORT).show();
                signup.execute(emailID, password, type, phoneNumber, firstName, lastName);

                SharedPreferences sharedPreferences = getSharedPreferences("res", MODE_APPEND);
                String res = sharedPreferences.getString("result_signup", "Sign up_res");
                //String res = signup.get_result();

                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                Log.d("variable res", res);

                if (res.equals("New user added")) {
                    DBC_signup dbc_signup = new DBC_signup(getApplicationContext());
                    SQLiteDatabase sqLiteDatabase = dbc_signup.getWritableDatabase();
                    h1.put("Email_id", emailID);
                    h1.put("Password", password);
                    h1.put("First_Name", firstName);
                    h1.put("Last_Name", lastName);
                    h1.put("Phone_No", phoneNumber);
                    h2.put("Sync", 1);

                    dbc_signup.insertUser(h1, h2);
                    Intent i = new Intent(signup.this, MainActivity.class);
                /*i.putExtra("result",res);
                setResult(Activity.RESULT_OK,i);
                finish();*/
                    Toast.makeText(getApplicationContext(), "You are signed in", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    finish();
                }

            }


        });

    }
}

