package com.example.rngrocery;

import java.util.Date;

public class Purchase {
    private int itemCode;
    private int qtyPurchased;
    private Date dateOfPurchase;

    public Purchase(int itemCode, int qtyPurchased, Date dateOfPurchase) {
        this.itemCode = itemCode;
        this.qtyPurchased = qtyPurchased;
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getQtyPurchased() {
        return qtyPurchased;
    }

    public void setQtyPurchased(int qtyPurchased) {
        this.qtyPurchased = qtyPurchased;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }
}
