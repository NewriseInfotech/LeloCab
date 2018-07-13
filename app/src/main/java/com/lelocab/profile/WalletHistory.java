package com.lelocab.profile;

/**
 * Created by ashish on 25-05-2017.
 */

public class WalletHistory {
    private String Amount;

    private String CreatedOn;

    private String Id;

    private String TransactionType;

    private String TransactionTypeString;

    private String WalletId;

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getTransactionTypeString() {
        return TransactionTypeString;
    }

    public void setTransactionTypeString(String transactionTypeString) {
        TransactionTypeString = transactionTypeString;
    }

    public String getWalletId() {
        return WalletId;
    }

    public void setWalletId(String walletId) {
        WalletId = walletId;
    }
}
