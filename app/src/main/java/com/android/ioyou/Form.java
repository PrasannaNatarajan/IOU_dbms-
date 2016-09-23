package com.android.ioyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by prasanna on 11-03-2016.
 */
public class Form extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        final EditText emailid,amount;
        Button confirm;

        try{
            emailid = (EditText)findViewById(R.id.email_id);
            amount = (EditText) findViewById(R.id.amount_form);
            confirm = (Button)findViewById(R.id.add_button);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(Form.this, intermediateActivity.class);
                        String S_email = emailid.getText().toString();
                        float S_Amount = Float.valueOf(amount.getText().toString());
                        Bundle b = new Bundle();
                        b.putString("email_id", S_email);
                        b.putFloat("amount", S_Amount);
                        intent.putExtras(b);
                        Toast.makeText(getApplicationContext(), "You owe " + S_email + " Rs. " + S_Amount + " ", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                    catch(Exception e){
                        Toast.makeText(getBaseContext(),"Error creating Activity",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        catch(Exception e){
            //Toast.makeText(getBaseContext(),"listener",Toast.LENGTH_LONG).show();
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
