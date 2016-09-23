package com.android.ioyou;

public class transactionFields {
    public String fromID;
    public String toID;
    public String owes;
    public String amountTransaction;

    public transactionFields(String fromID, String toID, String owes, float amountTransaction){
        this.fromID = fromID;
        this.toID = toID;
        this.owes = owes;
        this.amountTransaction = String.valueOf(amountTransaction);
    }
}
