package com.android.ioyou;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.android.ioyou.DBC.DBC_Trans;
import com.android.ioyou.DBC.DBC_debt_local;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prasanna on 16-04-2016.
 */
public class BackgroundTrans extends AsyncTask<JSONObject,JSONObject,JSONObject> {
    public Context context;
    static String time;
    AlertDialog alertDialog;
    public SharedPreferences sharedPreferences;
    public BackgroundTrans(Context con){
        context = con;
        sharedPreferences = context.getSharedPreferences("res",Context.MODE_APPEND);
    }
    @Override
    protected JSONObject doInBackground(JSONObject... data) {

        String trans_url = "http://ioudbms.esy.es/trans.php";

        if (haveNetworkConnection()) {
            down_sync();
            JSONObject json = data[0];
            DBC_Trans dbc_trans = new DBC_Trans(context);
            SQLiteDatabase sqLiteDatabase = dbc_trans.getReadableDatabase();
            ArrayList<HashMap<String,String>> arrayList = dbc_trans.display_all();
            HashMap<String,String>h;
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            String str1[][]=new String[arrayList.size()][5],postdata;
            for(int i=0;i<arrayList.size();i++){
                h=arrayList.get(i);
                str1[i][0]=h.get("Sync");
                str1[i][1]=h.get("to_id");
                str1[i][2]=h.get("from_id");
                str1[i][3]=h.get("time");
                str1[i][4]=h.get("Amount");
                if(h.get("Sync").equals("0.0")){
                    try {
                        //jsonObject.put("item" + i + "", h);
                        //jsonArray.put(h);
                        Log.d("logging",str1[i][3]);

                        URL url = new URL(trans_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        postdata = URLEncoder.encode("From_ID", "UTF-8") + "=" + URLEncoder.encode(str1[i][2], "UTF-8") + "&"
                                + URLEncoder.encode("To_ID", "UTF-8") + "=" + URLEncoder.encode(str1[i][1], "UTF-8") + "&" +
                                URLEncoder.encode("Amount", "UTF-8") + "=" + URLEncoder.encode(str1[i][4], "UTF-8") + "&"
                                + URLEncoder.encode("Timestamp", "UTF-8") + "=" + URLEncoder.encode(str1[i][3], "UTF-8") + "&"
                                + URLEncoder.encode("Sync", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
                        //URLEncoder.encode("json", "UTF-8") + "=" + URLEncoder.encode(jsonObject.toString(), "UTF-8");
                        bufferedWriter.write(postdata);
                        time = str1[i][3];
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        //Log.d("success","suc");
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
                        if(!res.equals("unsuccessful")){
                            ContentValues cv = new ContentValues();
                            cv.put("Sync",1);
                            DBC_Trans dbc_tran = new DBC_Trans(context);
                            SQLiteDatabase sqliteDatabase = dbc_tran.getWritableDatabase();
                            sqliteDatabase.update("trans_action",cv,"timestamp like '"+res+"'",null);
                            Log.d("update",res);
                            sqliteDatabase.close();
                        }
                        //Decode(downloadFile("http://ioudbms.esy.es/trans.php"));
                        //Log.d("logging json",jsonObject.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }



            return null;


        }else{
            alertDialog =  new AlertDialog.Builder(context).create();
            alertDialog.setMessage("Connect to internet and try again");
            alertDialog.show();
            return null;
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected void onPostExecute(JSONObject result) {


//        Log.d("final result", result.toString());
    }

    @Override
    protected void onProgressUpdate(JSONObject... values) {
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


    public void down_sync(){
        String trail_url = "http://ioudbms.esy.es/trail.php";

        if (haveNetworkConnection()) {

            DBC_Trans dbc_trans = new DBC_Trans(context);
            SQLiteDatabase sqLiteDatabase = dbc_trans.getReadableDatabase();
            ArrayList<HashMap<String,String>> arrayList = dbc_trans.display_all();
            HashMap<String,String>h;
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            String str1[][]=new String[arrayList.size()][5],postdata;
            for(int i=0;i<arrayList.size();i++){
                h=arrayList.get(i);
                str1[i][0]=h.get("Sync");
                str1[i][1]=h.get("to_id");
                str1[i][2]=h.get("from_id");
                str1[i][3]=h.get("time");
                str1[i][4]=h.get("Amount");

                try {
                    //jsonObject.put("item" + i + "", h);
                    //jsonArray.put(h);
                    Log.d("logging_down",str1[i][2]);

                    URL url = new URL(trail_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    postdata = URLEncoder.encode("From_ID", "UTF-8") + "=" + URLEncoder.encode(str1[i][2], "UTF-8") + "&"
                            + URLEncoder.encode("To_ID", "UTF-8") + "=" + URLEncoder.encode(str1[i][1], "UTF-8") + "&" +
                            URLEncoder.encode("Amount", "UTF-8") + "=" + URLEncoder.encode(str1[i][4], "UTF-8") + "&"
                            + URLEncoder.encode("Timestamp", "UTF-8") + "=" + URLEncoder.encode(str1[i][3], "UTF-8") + "&"
                            + URLEncoder.encode("Sync", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
                    //URLEncoder.encode("json", "UTF-8") + "=" + URLEncoder.encode(jsonObject.toString(), "UTF-8");
                    bufferedWriter.write(postdata);

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //Log.d("success","suc");
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
                    if(!res.equals("num row <0")) {
                        parseJSON(res);
                        about abou = new about();
                        abou.makeToast("Synced Successfully");
                        //Toast.makeText(context,,Toast.LENGTH_SHORT).show();
                    }else{
                        about abou = new about();
                        abou.makeToast("Sync UnSuccessful");
                        //Toast.makeText(context,"Sync UnSuccessful",Toast.LENGTH_SHORT).show();
                    }

                    Log.d("logging_result_json_down", res);

                    //Decode(downloadFile("http://ioudbms.esy.es/trans.php"));
                    //Log.d("logging json",jsonObject.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }






        }else{
            alertDialog =  new AlertDialog.Builder(context).create();
            alertDialog.setMessage("Connect to internet and try again");
            alertDialog.show();

        }

    }
    public void parseJSON(String inp)
    {
        int len = inp.length();
        int cursor = -1;
        int i;
        int countEntries = 0,lastBracket = 0;
        int startIndex,endIndex,cursorPosition = 0;
        String from_id, to_id, amount, time_stamp, down_sync, plain_sync;
        String closeBracket = "}",doubleQuote = "\"";

        while(lastBracket<len)
        {
            lastBracket = inp.indexOf(closeBracket,lastBracket+1);
            countEntries++;
            if(lastBracket == len-1)
                break;
        }

        for(i=0;i<countEntries;i++)
        {

            //{"From_ID":"vc909@snu.edu.in",
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            endIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = endIndex;
            from_id=inp.substring(startIndex+1,endIndex);
            //System.out.println(from_id);
            //"To_ID":"pn337@snu.edu.in",
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            endIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = endIndex;
            to_id=inp.substring(startIndex+1,endIndex);
            //System.out.println(to_id);
            //"Amount":"664.00",
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            endIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = endIndex;
            amount=inp.substring(startIndex+1,endIndex);
            //System.out.println(amount);
            //"Timestamp":"2016-04-16 19:54:19",
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            endIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = endIndex;
            time_stamp=inp.substring(startIndex+1,endIndex);
            //System.out.println(time_stamp);
            //"Sync":"1"
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            endIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = endIndex;
            plain_sync=inp.substring(startIndex+1,endIndex);
            //System.out.println(plain_sync);
            //"down_sync":"0"}
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            startIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = startIndex;
            endIndex = inp.indexOf(doubleQuote,cursorPosition+1);
            cursorPosition = endIndex;
            down_sync=inp.substring(startIndex+1,endIndex);
            //System.out.println(down_sync);

            //public String substring(int beginIndex, int endIndex)
            //beginIndex -- the begin index, inclusive.
            //endIndex -- the end index, exclusive.
            int temp=0;
            HashMap<String,String> h1 = new HashMap<String,String>();
            DBC_debt_local db = new DBC_debt_local(context);
            SQLiteDatabase dbs = db.getWritableDatabase();
            if(db.checkelement(dbs,from_id)){
                ContentValues cv = new ContentValues();
                //DBC_debt_local db = new DBC_debt_local(context);
                ArrayList<HashMap<String,String>> itemsList = db.display_all();
                String[][] str = new String[itemsList.size()][3];
                //ArrayList<String> arrstr = new ArrayList<>();
                HashMap<String,String> h;
                for(int j=0;j<itemsList.size();j++){
                    h = itemsList.get(j);
                    str[j][0]=h.get("Email_id");
                    //arrstr.add(str[i]);
                    str[j][1]=h.get("Amount");
                    str[j][2]=h.get("Sync");
                    if(str[j][0].equals(from_id)){
                        temp = j;
                    }
                }
                cv.put("amount",Float.parseFloat(str[temp][1])+Float.parseFloat(amount));
                Log.d("inside_insert",amount+str[temp][1]+str[temp][0]);
                db.getWritableDatabase().update("debt_local", cv, "email_id like '" + from_id + "' ", null);
            }else{
                HashMap<String,String> val = new HashMap<String,String>();
                val.put("email_id",from_id);

                HashMap<String,Float>aval = new HashMap<String,Float>();
                aval.put("amount",Float.parseFloat(amount));
                DBC_debt_local dbc = new DBC_debt_local(context);
                dbc.getWritableDatabase();
                dbc.insertUserTrans(val,aval);
            }


            //We get a from, to and amount
            //the from from the online transaction table is the to in the debt table
            //if does not exist, put him in
            //if exists, take existing debt, update that debt with new amount, put back in debt table
            //str = select amount from debt where to_debt like from_transaction
            //str = str - amount_transaction
            //update debt set amount = str where to_debt like from_transaction


            h1.put("from",from_id);
            h1.put("to", to_id);
            h1.put("time",time_stamp);
            HashMap<String,Float> h2 = new HashMap<String, Float>();
            h2.put("amount",Float.parseFloat(amount));

            DBC_Trans dbc_trans = new DBC_Trans(context);
            SQLiteDatabase sqLiteDatabase = dbc_trans.getWritableDatabase();

            dbc_trans.insertUserDownSync(h1,h2);
        }
        String finalOutput = countEntries + " Transactions updated";
        //Toast.makeText(context,finalOutput,Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences1 = context.getSharedPreferences("victory",Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("victory_res",finalOutput);
        editor.commit();

        Log.d("victory",finalOutput);
    }
}
