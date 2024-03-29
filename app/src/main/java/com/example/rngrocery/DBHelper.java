package com.example.rngrocery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public  DBHelper(Context context){
        super(context,DBNAME,null, VERSION);
    }


    static final String DBNAME = "RNGrocery.db";
    static final int VERSION =1;

    static final String TABLE_USER = "User";
    static final String COL_USERNAME = "username";
    static final String COL_EMAIL = "emailId";
    static final String COL_PASSWORD = "password";
    static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            COL_USERNAME + " TEXT, " +
            COL_EMAIL + " TEXT, " +
            COL_PASSWORD + " TEXT);";


    static final String TABLE_STOCK = "Stock";
    static final String COL_ITEM_CODE = "itemCode";
    static final String COL_ITEM_NAME = "itemName";
    static final String COL_QTY_STOCK = "qtyStock";
    static final String COL_PRICE = "price";
    static final String COL_TAXABLE = "taxable";
    static final String CREATE_TABLE_STOCK = "CREATE TABLE " + TABLE_STOCK + " (" +
            COL_ITEM_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ITEM_NAME + " TEXT, " +
            COL_QTY_STOCK + " INTEGER, " +
            COL_PRICE + " FLOAT, " +
            COL_TAXABLE + " BOOLEAN);";

    static final String TABLE_SALES = "Sales";
    static final String COL_ORDER_NUMBER = "orderNumber";
    static final String COL_CUSTOMER_NAME = "customerName";
    static final String COL_CUSTOMER_EMAIL = "customerEmail";
    static final String COL_QTY_SOLD = "qtySold";
    static final String COL_DATE_SALES = "dateOfSales";
    static final String CREATE_TABLE_SALES = "CREATE TABLE " + TABLE_SALES + " (" +
            COL_ORDER_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ITEM_CODE + " INTEGER, " +
            COL_CUSTOMER_NAME + " TEXT, " +
            COL_CUSTOMER_EMAIL + " TEXT, " +
            COL_QTY_SOLD + " INTEGER, " +
            COL_DATE_SALES + " DATE);";




    static final String TABLE_PURCHASE = "Purchase";
    static final String COL_INVOICE_NUMBER = "invoiceNumber";
    static final String COL_QTY_PURCHASED = "qtyPurchased";
    static final String COL_DATE_PURCHASE = "dateOfPurchase";
    static final String CREATE_TABLE_PURCHASE = "CREATE TABLE " + TABLE_PURCHASE + " (" +
            COL_INVOICE_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ITEM_CODE + " INTEGER, " +
            COL_QTY_PURCHASED + " INTEGER, " +
            COL_DATE_PURCHASE + " DATE);";



    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    private static final String DROP_TABLE_STOCK = "DROP TABLE IF EXISTS " + TABLE_STOCK;
    private static final String DROP_TABLE_SALES = "DROP TABLE IF EXISTS " + TABLE_SALES;
    private static final String DROP_TABLE_PURCHASE = "DROP TABLE IF EXISTS " + TABLE_PURCHASE;




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_STOCK);
        db.execSQL(CREATE_TABLE_SALES);
        db.execSQL(CREATE_TABLE_PURCHASE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_STOCK);
        db.execSQL(DROP_TABLE_SALES);
        db.execSQL(DROP_TABLE_PURCHASE);
        onCreate(db);

    }

    public Boolean InsertUser(User objEmp) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME ,objEmp.getUsername());
        cv.put(COL_EMAIL, objEmp.getEmailId());
        cv.put(COL_PASSWORD, objEmp.getPassword());


        long userRowId = db.insert(DBHelper.TABLE_USER, null, cv);

        return  ((userRowId==-1) ? false : true);

    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER +
                " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});

        boolean userExists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return userExists;
    }

    Boolean insertTestUser(SQLiteDatabase objEmp2) {

        if (testUserExists(objEmp2)) {
            Log.d("DBHelper", "Test user already exists, skipping insertion");
            Log.e("DBHelper", "Test user already exists, skipping insertion");
            return false;  // Return false to indicate that the test user was not inserted

        }


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, "test");
        values.put(COL_EMAIL, "test@gmail.com");
        values.put(COL_PASSWORD, "test");

        long newRowId = db.insert(TABLE_USER, null, values);

        if (newRowId != -1) {
            Log.d("DBHelper", "Test user inserted successfully");
            return true;  // Return true on success
        } else {
            Log.e("DBHelper", "Failed to insert test user");
            return false;  // Return false on failure
        }
    }

    // method to check if the test user already exists
    private boolean testUserExists(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + " = ?", new String[]{"test"});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // method to check if a username exists in the database
    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the projection (columns) you want to retrieve
        String[] projection = {DBHelper.COL_USERNAME};

        // Define the selection (WHERE clause)
        String selection = DBHelper.COL_USERNAME + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {username};

        // Query the database
        Cursor cursor = db.query(DBHelper.TABLE_USER, projection, selection, selectionArgs, null, null, null);

        // Check if the cursor has any rows
        boolean usernameExists = cursor.getCount() > 0;

        // Close the cursor and database
        cursor.close();
        db.close();

        return usernameExists;
    }

    //  method to check if a username exists in the database
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the projection (columns) you want to retrieve
        String[] projection = {DBHelper.COL_EMAIL};

        // Define the selection (WHERE clause)
        String selection = DBHelper.COL_EMAIL + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {email};

        // Query the database
        Cursor cursor = db.query(DBHelper.TABLE_USER, projection, selection, selectionArgs, null, null, null);

        // Check if the cursor has any rows
        boolean emailExists = cursor.getCount() > 0;

        // Close the cursor and database
        cursor.close();
        db.close();

        return emailExists;
    }



    public boolean insertStock(Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ITEM_NAME, stock.getStockName());
        cv.put(COL_QTY_STOCK, stock.getQuantity());
        cv.put(COL_PRICE, stock.getPrice());


        // Convert boolean to integer representation
        int taxableInt = stock.isTaxable() ? 1 : 0;
        cv.put(COL_TAXABLE, taxableInt);


        long stockRowId = db.insert(DBHelper.TABLE_STOCK, null, cv);
        return  ((stockRowId==-1) ? false : true);
    }

    public boolean stockExists(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {DBHelper. COL_ITEM_NAME};

        // Specify the WHERE clause
        String selection = DBHelper.COL_ITEM_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(itemName)};

        // Query the Stock table
        Cursor cursor = db.query(
                DBHelper.TABLE_STOCK,   // The table to query
                projection,                         // The columns to return
                selection,                          // The columns for the WHERE clause
                selectionArgs,                      // The values for the WHERE clause
                null,                               // Don't group the rows
                null,                               // Don't filter by row groups
                null                                // The sort order
        );

        // Check if there are any rows returned
        boolean exists = cursor.moveToFirst();

        // Close the cursor and database
        cursor.close();
        db.close();

        return exists;
    }











    public boolean insertSale(Sales sales) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ITEM_CODE, sales.getItemCode());
        cv.put(COL_CUSTOMER_NAME, sales.getCustomerName());
        cv.put(COL_CUSTOMER_EMAIL, sales.getCustomerEmail());
        cv.put(COL_QTY_SOLD, sales.getQtySold());
        cv.put(COL_DATE_SALES, sales.getDateOfSales().getTime());


        // Convert boolean to integer representation
        long salesRowId = db.insert(DBHelper.TABLE_SALES, null, cv);
        return  ((salesRowId==-1) ? false : true);


    }

    public boolean isItemCodeExistsInStock(int itemCode) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {DBHelper. COL_ITEM_CODE};

        // Specify the WHERE clause
        String selection = DBHelper.COL_ITEM_CODE + " = ?";
        String[] selectionArgs = {String.valueOf(itemCode)};

        // Query the Stock table
        Cursor cursor = db.query(
                DBHelper.TABLE_STOCK,   // The table to query
                projection,                         // The columns to return
                selection,                          // The columns for the WHERE clause
                selectionArgs,                      // The values for the WHERE clause
                null,                               // Don't group the rows
                null,                               // Don't filter by row groups
                null                                // The sort order
        );

        // Check if there are any rows returned
        boolean exists = cursor.moveToFirst();

        // Close the cursor and database
        cursor.close();
        db.close();

        return exists;
    }



    public int getStockQuantity(int itemCode) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {COL_QTY_STOCK};

        // Specify the WHERE clause
        String selection = COL_ITEM_CODE + " = ?";
        String[] selectionArgs = {String.valueOf(itemCode)};

        // Query the Stock table
        Cursor cursor = db.query(
                TABLE_STOCK,    // The table to query
                projection,     // The columns to return
                selection,      // The columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,           // Don't group the rows
                null,           // Don't filter by row groups
                null            // The sort order
        );

        int stockQuantity = 0;

        // Check if there are any rows returned
        // Check if there are any rows returned
        if (cursor.moveToFirst()) {
            // Extract the quantity from the cursor
            stockQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QTY_STOCK));
        }

        // Close the cursor and database
//        cursor.close();
//        db.close();

        return stockQuantity;
    }





    public boolean salesRecord(int itemCode, int soldQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();


        // Get the current quantity in the Stock table
        int currentQuantity = getStockQuantity(itemCode);

        // Define the new quantity after the sale
        int newQuantity = currentQuantity - soldQuantity;

        // Create content values to store the new quantity
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_QTY_STOCK, newQuantity);

        // Specify the WHERE clause
        String selection = DBHelper.COL_ITEM_CODE + " = ?";
        String[] selectionArgs = {String.valueOf(itemCode)};

        // Update the Stock table
        int rowsUpdated = db.update(
                DBHelper.TABLE_STOCK,  // The table to update
                values,                             // The new values to apply
                selection,                          // The columns for the WHERE clause
                selectionArgs                       // The values for the WHERE clause
        );

        // Close the database
//        db.close();

        return rowsUpdated > 0;



    }



    public boolean insertPurchase(Purchase purchase) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COL_ITEM_CODE, purchase.getItemCode());
        cv.put(COL_QTY_PURCHASED, purchase.getQtyPurchased());
        cv.put(COL_DATE_PURCHASE, purchase.getDateOfPurchase().getTime()); // Assuming dateOfPurchase is a Date object .getTime())


        // Convert boolean to integer representation
        long salesRowId = db.insert(DBHelper.TABLE_PURCHASE, null, cv);
        return  ((salesRowId==-1) ? false : true);


    }







    public boolean purchaseRecord(int itemCode, int purchaseQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();


//        // Get the current quantity in the Stock table
        int currentQuantity = getStockQuantity(itemCode);
//
//        // Define the new quantity after the sale
        int newQuantity = currentQuantity + purchaseQuantity;

        // Create content values to store the new quantity
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_QTY_STOCK, newQuantity);

        // Specify the WHERE clause
        String selection = DBHelper.COL_ITEM_CODE + " = ?";
        String[] selectionArgs = {String.valueOf(itemCode)};

        // Update the Stock table
        int rowsUpdated = db.update(
                DBHelper.TABLE_STOCK,  // The table to update
                values,                             // The new values to apply
                selection,                          // The columns for the WHERE clause
                selectionArgs                       // The values for the WHERE clause
        );

        // Close the database
//        db.close();

        return rowsUpdated > 0;



    }



    // Method to retrieve all Employee records from the database
    public Cursor readStockList() {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursorObj;
        cursorObj=db.rawQuery("select * from " + TABLE_STOCK, null);
        if(cursorObj != null) {
            cursorObj.moveToFirst();
        }
        return cursorObj;
    } // End of readEmployees method






















    public Stock searchStockById(int itemCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Stock stock = null;

        String[] projection = {COL_ITEM_CODE, COL_ITEM_NAME, COL_QTY_STOCK, COL_PRICE, COL_TAXABLE};
        String selection = COL_ITEM_CODE + " = ?";
        String[] selectionArgs = {String.valueOf(itemCode)};

        Cursor cursor = db.query(
                TABLE_STOCK,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );




        if (cursor != null && cursor.moveToFirst()) {
            int stockId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ITEM_CODE));
            String stockName = cursor.getString(cursor.getColumnIndexOrThrow(COL_ITEM_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QTY_STOCK));
            float price = cursor.getFloat(cursor.getColumnIndexOrThrow(COL_PRICE));
            boolean taxable = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TAXABLE)) == 1;

            // Create a Stock object without stockId
            stock = new Stock(stockName, quantity, price, taxable);





        }



        return stock;
    }

}




