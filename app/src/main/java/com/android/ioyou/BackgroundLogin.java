package com.android.ioyou;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by prasanna on 12-04-2016.
 */
public class BackgroundLogin extends AsyncTask<String,String,String> {

    public Context context;
    AlertDialog alertDialog;
    public SharedPreferences sharedPreferences;
    public BackgroundLogin(Context con){
        context = con;
        sharedPreferences = context.getSharedPreferences("res",Context.MODE_APPEND);
    }


    @Override
    protected String doInBackground(String... params) {
        String type = params[2];


        String login_url = "http://ioudbms.esy.es/login.php";
        String signup_url = "http://ioudbms.esy.es/signup.php";
        String trans_url = "http://ioudbms.esy.es/trans.php";
        if (haveNetworkConnection()) {
            if (type.equals("login")) {
                try {
                    String email = params[0];
                    String password = params[1];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String postdata = URLEncoder.encode("email_id", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                            URLEncoder.encode("sync", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
                    bufferedWriter.write(postdata);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String res = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        res += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.d("final", res);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("result", res);
                    editor.commit();
                    return res;
                } catch (Exception e) {
                    //Toast.makeText(context,"url failed",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            } else if (type.equals("signup")) {
                try {
                    URL url = new URL(signup_url);
                    String email = params[0];
                    String password = params[1];
                    String phoneNumber = params[3];
                    String firstName = params[4];
                    String lastName = params[5];
                    Log.d("key", email);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String postdata = URLEncoder.encode("email_id", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                            URLEncoder.encode("first_Name", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&"
                            + URLEncoder.encode("last_Name", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&"
                            + URLEncoder.encode("phone_No", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&"
                            + URLEncoder.encode("sync", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");

                    bufferedWriter.write(postdata);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String res = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        res += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.d("final", res);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("result_signup", res);
                    editor.apply();
                    return res;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(type.equals("trans")){
                try {
                    URL url = new URL(trans_url);
                    String to_id = params[0];
                    String from_id = params[1];
                    String time = params[3];
                    String amount = params[4];

                    //Log.d("key", email);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                    String postdata = URLEncoder.encode("From_ID", "UTF-8") + "=" + URLEncoder.encode(from_id, "UTF-8") + "&"
                            + URLEncoder.encode("To_ID", "UTF-8") + "=" + URLEncoder.encode(to_id, "UTF-8") + "&" +
                            URLEncoder.encode("Amount", "UTF-8") + "=" + URLEncoder.encode(amount, "UTF-8") + "&"
                            + URLEncoder.encode("Timestamp", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&"
                            + URLEncoder.encode("Sync", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");

                    bufferedWriter.write(postdata);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String res = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        res += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.d("logging json", res);
                    /*
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("result_signup", res);
                    editor.apply();
                    */
                    return res;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else{
           return "Internet Not connected\nConnect to internet to login";
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog =  new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();

        Log.d("final result", result);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
