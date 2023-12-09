package com.example.rngrocery;

public class Stock {

    private int stockId;
    private String stockName;
    private int quantity;
    private double price;
    private boolean taxable;

    // Constructor
    public Stock(String stockName, int quantity, double price, boolean taxable) {
        this.stockName = stockName;
        this.quantity = quantity;
        this.price = price;
        this.taxable = taxable;
    }

    // Getter and Setter methods

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }
}
