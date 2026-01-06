# RN Grocery (Android)

RN Grocery is a simple Android inventory and sales tracker built with **Java**, **Fragments**, and **SQLite**. It includes authentication (Sign Up / Login), a navigation drawer UI, and core inventory flows like adding stock, listing items, recording purchases (restock), and recording sales (reduces stock).

---


## Demo
https://github.com/user-attachments/assets/c57471f6-7b0e-403f-b8c9-cb769cb3732d

---

## Table of Contents
- [Tech Stack](#tech-stack)
- [Platform](#platform)
- [Project Type](#project-type)
- [Project Status](#project-status)
- [Overview](#overview)
- [Core Features](#core-features)
- [App Flow](#app-flow)
- [Database Schema](#database-schema)
- [Validation & Rules](#validation--rules)
- [Test Account](#test-account)
- [How to Run](#how-to-run)
- [Project Structure](#project-structure)
- [Future Improvements](#future-improvements)

---

## Tech Stack
- Java
- AndroidX (AppCompat, Fragments, RecyclerView)
- Material Components (MaterialAlertDialogBuilder, MaterialToolbar)
- SQLite (SQLiteOpenHelper)
- ViewBinding
- SharedPreferences

---

## Platform
- Android

---

## Project Type
Personal / School Project

---

## Project Status
In Progress 🛠️

---

## Overview
RN Grocery is designed to help manage basic grocery store inventory operations:
- Create an account and log in
- Add and track stock items
- Increase stock through **Purchases**
- Reduce stock through **Sales**
- View all items in a RecyclerView list
- Navigate through the app with a **Navigation Drawer**

---

## Core Features

### ✅ Authentication
- **Sign Up** saves users into SQLite `User` table
- **Login** validates username + password from SQLite
- Stores logged-in username using **SharedPreferences** and displays it on the main screen
- Includes a built-in **test user** insertion

### ✅ Stock Management
- Add stock items with:
  - Item name
  - Quantity
  - Price
  - Tax status (Taxable / Non-taxable)
- Prevents duplicate stock item names using `stockExists()`

### ✅ Purchases (Restocking)
- Records incoming inventory by item code + quantity + date
- Updates `Stock.qtyStock` by adding purchased quantity
- Prevents selecting a future date

### ✅ Sales
- Records sales by item code and quantity sold
- Updates `Stock.qtyStock` by subtracting sold quantity

### ✅ Stock List
- Displays all stock items in a RecyclerView using:
  - `ListStockFragment`
  - `ListAdapter`

### ✅ Navigation Drawer UI
Fragments included in the drawer:
- Add Stock
- Sales
- Purchase
- Search Stock
- List Stock
- Log out (clears SharedPreferences and returns to Login)

---

## App Flow
1. **Sign Up** → creates user in SQLite  
2. **Login** → validates user and saves username in SharedPreferences  
3. **MainActivity** → navigation drawer loads fragments  
4. User selects a feature (Add Stock, Purchase, Sales, List Stock, etc.)

---

## Database Schema

### `User`
| Column | Type |
|-------|------|
| username | TEXT |
| emailId | TEXT |
| password | TEXT |

### `Stock`
| Column | Type |
|-------|------|
| itemCode | INTEGER (PK, AUTOINCREMENT) |
| itemName | TEXT |
| qtyStock | INTEGER |
| price | FLOAT |
| taxable | BOOLEAN (stored as 0/1) |

### `Sales`
| Column | Type |
|-------|------|
| orderNumber | INTEGER (PK, AUTOINCREMENT) |
| itemCode | INTEGER |
| customerName | TEXT |
| customerEmail | TEXT |
| qtySold | INTEGER |
| dateOfSales | DATE |

### `Purchase`
| Column | Type |
|-------|------|
| invoiceNumber | INTEGER (PK, AUTOINCREMENT) |
| itemCode | INTEGER |
| qtyPurchased | INTEGER |
| dateOfPurchase | DATE |

---

## Validation & Rules

### Add Stock
- Item name required
- Quantity must be numeric
- Price must be numeric
- Taxable status must be selected
- Item name must be unique (duplicate blocked)

### Purchase
- Item code must exist in stock
- Date must be valid and **not in the future**
- Updates stock quantity automatically

---

## Test Account
A test user is inserted (if it doesn’t already exist):

- **Username:** test  
- **Password:** test  
- **Email:** test@gmail.com  

---

## How to Run
1. Clone the repo
2. Open in **Android Studio**
3. Sync Gradle
4. Run on an emulator or Android device

---

## Project Structure (Key Files)
- `MainActivity.java` — Navigation drawer + fragment switching + logout
- `DBHelper.java` — SQLite database tables + CRUD operations
- `SignUpScreen.java` — User registration + validation
- `LoginScreen.java` — User login (not included in snippet but referenced)
- `AddStockFragment.java` — Add stock items + validation + duplicate prevention
- `PurchaseFragment.java` — Record purchase + date picker + stock update
- `ListStockFragment.java` — Loads stock list from SQLite into RecyclerView
- `ListAdapter.java` — RecyclerView adapter for stock list

---

## Future Improvements
- Fix date storage consistency (use a consistent long timestamp ↔ formatted date conversion)
- Add input validation for purchase quantity and item code before parsing
- Add full Search & Sales UI improvements (filters, sorting, better empty states)
- Add update/edit stock feature
- Add better error handling & database close management
- Add unit/UI tests

---

## License
MIT (or choose your preferred license)
