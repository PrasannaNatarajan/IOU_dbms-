package com.android.ioyou;

/**
 * Created by prasanna on 01-04-2016.
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TabFragment1 extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Button add,about,see,delete;

        final RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.tab_fragment_1,
                container, false);
        //RelativeLayout plus = (RelativeLayout)mRelativeLayout.findViewById(R.id.plus_button);
        add = (Button)mRelativeLayout.findViewById(R.id.add);
        about = (Button)mRelativeLayout.findViewById(R.id.about);
        see = (Button)mRelativeLayout.findViewById(R.id.see);
        delete = (Button)mRelativeLayout.findViewById(R.id.delete);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getActivity(), Form.class);
                    Toast.makeText(getActivity(), "You are adding new ", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(getActivity(),"Error creating activity",Toast.LENGTH_LONG).show();
                }
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent i = new Intent(getActivity(),about.class);
                    startActivity(i);
                }catch(Exception e){
                    Toast.makeText(getActivity(),"Unable to create Activity",Toast.LENGTH_SHORT).show();
                }

            }
        });
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),seeall.class);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBController db = new DBController(getActivity());
                SQLiteDatabase mydb = db.getWritableDatabase();
                db.onUpgrade(mydb,1,2);
            }
        });
        return mRelativeLayout;
    }

}
