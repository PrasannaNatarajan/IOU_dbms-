package com.android.ioyou;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<transactionFields>{
    public TransactionAdapter(Context context, ArrayList<transactionFields> transactions){
        super(context, R.layout.transaction_list, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater myInflator = LayoutInflater.from(getContext());
        //View customView = myInflator.inflate(R.layout.transaction_list,parent,false);


        transactionFields Transaction = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_list, parent, false);
        }
        TextView from_id = (TextView) convertView.findViewById( R.id.from_id_transaction);
        TextView to_id = (TextView) convertView.findViewById( R.id.to_id_transacion);
        TextView owes = (TextView) convertView.findViewById( R.id.owes_transaction);
        TextView amount = (TextView) convertView.findViewById( R.id.amount_transaction);

        from_id.setText(Transaction.fromID);
        to_id.setText(Transaction.toID);
        owes.setText(Transaction.owes);
        amount.setText(Transaction.amountTransaction);

        return convertView;
    }
}
