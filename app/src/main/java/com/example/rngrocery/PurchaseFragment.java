package com.example.rngrocery;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.rngrocery.databinding.FragmentPurchaseBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PurchaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    EditText itemCode, quantity, date;
    DBHelper dbHelper;
    Boolean insertStatus;
    DatePickerDialog datePicker;
    Button submit, cancel;
    FragmentPurchaseBinding fragmentPurchaseBinding;
    Date testDate;

    public PurchaseFragment() {
        // Required empty public constructor
    }

    public static PurchaseFragment newInstance(String param1, String param2) {
        PurchaseFragment fragment = new PurchaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPurchaseBinding = FragmentPurchaseBinding.inflate(inflater, container, false);
        View view = fragmentPurchaseBinding.getRoot();
        init(); // Initialize components
        return view;
    }

    private void init() {
        dbHelper = new DBHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        itemCode = fragmentPurchaseBinding.edItemCode;
        quantity = fragmentPurchaseBinding.edQuantityPurchased;
        date = fragmentPurchaseBinding.edDate;
        submit = fragmentPurchaseBinding.btnSubmit;
        cancel = fragmentPurchaseBinding.btnCancel;

        date.setInputType(InputType.TYPE_NULL);

        date.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == cancel.getId()) {
            Log.d("OnClick", "Cancel button clicked");
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == date.getId()) {
            Calendar cal = Calendar.getInstance();
            int dayOfSales = cal.get(Calendar.DAY_OF_MONTH);
            int monthOfSales = cal.get(Calendar.MONTH);
            int yearOfSales = cal.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }, yearOfSales, monthOfSales, dayOfSales);

            datePicker.show();
        } else if (v.getId() == submit.getId()) {
            Integer sItemCode = Integer.parseInt(itemCode.getText().toString());
            Integer sQuantity = Integer.parseInt(quantity.getText().toString());
            String userInput = date.getText().toString();
            String inputDateFormat = "dd/MM/yyyy";

            Date sDate = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat);
                Date parsedDate = sdf.parse(userInput);
                sDate = parsedDate;
                Date date = new Date(userInput);
                System.out.println("Date:" + date);
            } catch (ParseException e) {
                System.out.println("Error parsing the date. Please use the format: " + inputDateFormat);
            }

            boolean recordPurchase = dbHelper.purchaseRecord(sItemCode, sQuantity);
            boolean existsInStock = dbHelper.isItemCodeExistsInStock(sItemCode);
            Purchase purchase = new Purchase(sItemCode, sQuantity, sDate);

            if (existsInStock) {
                if (recordPurchase) {
                    if (validateInput()) {
                        insertStatus = dbHelper.insertPurchase(purchase);
                        if (insertStatus) {
                            new MaterialAlertDialogBuilder(requireActivity())
                                    .setTitle("Sale Successful")
                                    .setMessage("Item successfully Purchased.")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // User clicked OK button
                                        }
                                    })
                                    .show();
                        } else {
                            new MaterialAlertDialogBuilder(requireActivity())
                                    .setTitle("Error")
                                    .setMessage("An error occurred during the purchase.")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // User clicked OK button
                                        }
                                    })
                                    .show();
                        }
                    } else {
                        new MaterialAlertDialogBuilder(requireActivity())
                                .setTitle("Validation Error")
                                .setMessage("There is an issue with one of the input fields.")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // User clicked OK button
                                    }
                                })
                                .show();
                    }
                } else {
                    new MaterialAlertDialogBuilder(requireActivity())
                            .setTitle("Error")
                            .setMessage("An error occurred during the purchase.")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // User clicked OK button
                                }
                            })
                            .show();
                }
            } else {
                new MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("Error")
                        .setMessage("Item does not exist.")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked OK button
                            }
                        })
                        .show();
            }
        }
    }

    private boolean validateInput() {
        String dateStr = date.getText().toString().trim();
        String itemCodeStr = itemCode.getText().toString().trim();

        if (itemCodeStr.isEmpty()) {
            itemCode.setError("Enter the item code");
            return false;
        }

        try {
            int quantityValue = Integer.parseInt(itemCodeStr);
            // Quantity is a valid integer
        } catch (NumberFormatException e) {
            itemCode.setError("Enter a valid item code (numeric)");
            return false;
        }

        String inputDateFormat = "dd/MM/yyyy";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat);
            Date parsedDate = sdf.parse(dateStr);

            if (parsedDate.after(new Date())) {
                date.setError("Cannot select a future date");
                return false;
            }

        } catch (ParseException e) {
            date.setError("Enter a valid date (format: " + inputDateFormat + ")");
            return false;
        }

        return true; // All input fields are valid
    }
}
