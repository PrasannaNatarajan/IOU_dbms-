package com.android.ioyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.ioyou.DBC.DBC_Trans;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prasanna on 20-04-2016.
 */
public class listTrans extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.listrans,
                container, false);

            DBC_Trans db = new DBC_Trans(getActivity());
            Float amt;
            String owes;
            ArrayList<HashMap<String,String>> itemsList = db.display_all();

            ArrayList<transactionFields> transList = new ArrayList<transactionFields>();
            String[][] str = new String[itemsList.size()][4];
            //ArrayList<String> arrstr = new ArrayList<>();
            HashMap<String,String> h;
            for(int i=0;i<itemsList.size();i++){
                h = itemsList.get(i);
                str[i][0]=h.get("from_id");
                str[i][1]=h.get("to_id");
                str[i][2]=h.get("Amount");
                str[i][3]=h.get("time");

                amt = Float.parseFloat(str[i][2]);
                float f = amt.floatValue();
                if(amt.floatValue() < 0.0)
                {
                    owes = "owed by";
                    f=f*-1;
                }
                else {
                    owes = "owes";
                }
                transactionFields tf = new transactionFields(str[i][0],str[i][1],owes,f);

                transList.add(tf);
            }
        ListAdapter listAdapter = new TransactionAdapter(getActivity(),transList);

            //TransactionAdapter ta = new TransactionAdapter(getActivity(),transList);

        ListView myList=(ListView)mRelativeLayout.findViewById(R.id.seetrans);
        myList.setAdapter(listAdapter);


        return mRelativeLayout;
    }
}
