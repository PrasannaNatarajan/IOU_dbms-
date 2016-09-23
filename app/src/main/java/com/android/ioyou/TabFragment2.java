package com.android.ioyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.ioyou.DBC.DBC_debt_local;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prasanna on 01-04-2016.
 */
public class TabFragment2 extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.tab_fragment_2,
                container, false);
        DBC_debt_local db = new DBC_debt_local(getActivity());
        db.getWritableDatabase();

        if(db.checkDataBase()){
            ArrayList<HashMap<String,String>> itemsList = db.display_all();
            String[] str = new String[itemsList.size()];
            ArrayList<String> arrstr = new ArrayList<>();
            HashMap<String,String> h;
            for(int i=0;i<itemsList.size();i++){
                h = itemsList.get(i);
                str[i]=h.get("Email_id");
                arrstr.add(str[i]);
                Log.d("my elems_tab",str[i]);
//EXTREME DEVELOPMENT AREA FOR TRANSACTION TABLE DISPLAY







//END OF EXTREME DEVELOPMENT AREA FOR TRANSACTION TABLE DISPLAY


                //str[i][1]=h.get("amount");
            }
//EXTREME DEVELOPMENT AREA FOR TRANSACTION TABLE DISPLAY







//END OF EXTREME DEVELOPMENT AREA FOR TRANSACTION TABLE DISPLAY



            /*DBC_Trans dbt1 = new DBC_Trans(getActivity());
            SQLiteDatabase sqLiteDatabase2 = dbt1.getReadableDatabase();
            ArrayList<HashMap<String,String>> itemsList1 = dbt1.display_all();
            String[][] str1 = new String[itemsList.size()][5];
            BackgroundLogin backgroundLogin = new BackgroundLogin(getActivity());
            HashMap<String,String> h1;
            for(int i=0;i<itemsList.size();i++){
                h1 = itemsList.get(i);
                str1[i][0]=h1.get("Sync");
                if(str1[i][0].equals("0")){
                    str1[i][1]=h1.get("to_id");
                    str1[i][2]=h1.get("from_id");
                    str1[i][3]=h1.get("time");
                    str1[i][4]=h1.get("Amount");
                    Log.d("calling",str1[i][4]);
                    backgroundLogin.execute(str1[i][1],str1[i][2],"trans",str1[i][3],str1[i][4]);
                }
            }*/
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    arrstr );
            ListView myList=(ListView)mRelativeLayout.findViewById(R.id.seeall);
            myList.setAdapter(arrayAdapter);

            myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DBC_debt_local db = new DBC_debt_local(getActivity());
                    ArrayList<HashMap<String,String>> itemsList = db.display_all();
                    String[][] str = new String[itemsList.size()][3];
                    //ArrayList<String> arrstr = new ArrayList<>();
                    HashMap<String,String> h;
                    for(int i=0;i<itemsList.size();i++){
                        h = itemsList.get(i);
                        str[i][0]=h.get("Email_id");
                        //arrstr.add(str[i]);
                        str[i][1]=h.get("Amount");
                        str[i][2]=h.get("Sync");
                    }

                    Log.d("array index",str[position][1]);
                   // Toast.makeText(getActivity(), str[position][1], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(),modify.class);
                    Bundle b = new Bundle();
                    b.putString("email_id",str[position][0]);
                    Float sync = Float.parseFloat(str[position][2]);
                    b.putFloat("sync", sync);
                    Float amount = Float.parseFloat(str[position][1]);
                    b.putFloat("Amount",amount);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }
        return mRelativeLayout;
    }
}
