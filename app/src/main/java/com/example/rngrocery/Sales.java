package com.example.rngrocery;

import java.util.Date;

public class Sales {

    private int itemCode;
    private String customerName;
    private String customerEmail;
    private int qtySold;
    private Date dateOfSales;

    // Constructors

    public Sales() {
        // Default constructor
    }

    public Sales(int itemCode, String customerName, String customerEmail, int qtySold, Date dateOfSales) {
        this.itemCode = itemCode;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.qtySold = qtySold;
        this.dateOfSales = dateOfSales;
    }

    // Getter and Setter methods

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getQtySold() {
        return qtySold;
    }

    public void setQtySold(int qtySold) {
        this.qtySold = qtySold;
    }

    public Date getDateOfSales() {
        return dateOfSales;
    }

    public void setDateOfSales(Date dateOfSales) {
        this.dateOfSales = dateOfSales;
    }


}

