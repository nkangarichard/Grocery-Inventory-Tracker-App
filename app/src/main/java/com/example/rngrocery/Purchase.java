package com.example.rngrocery;

import java.util.Date;

public class Purchase {
    // Fields to store purchase information
    private int itemCode;
    private int qtyPurchased;
    private Date dateOfPurchase;

    // Constructor to initialize the Purchase object
    public Purchase(int itemCode, int qtyPurchased, Date dateOfPurchase) {
        this.itemCode = itemCode;
        this.qtyPurchased = qtyPurchased;
        this.dateOfPurchase = dateOfPurchase;
    }

    // Getter method for retrieving the item code
    public int getItemCode() {
        return itemCode;
    }

    // Setter method for setting the item code
    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    // Getter method for retrieving the quantity purchased
    public int getQtyPurchased() {
        return qtyPurchased;
    }

    // Setter method for setting the quantity purchased
    public void setQtyPurchased(int qtyPurchased) {
        this.qtyPurchased = qtyPurchased;
    }

    // Getter method for retrieving the date of purchase
    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    // Setter method for setting the date of purchase
    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }
}
