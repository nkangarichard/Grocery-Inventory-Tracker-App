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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rngrocery.databinding.FragmentAddStockBinding;
import com.example.rngrocery.databinding.FragmentSalesBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText itemCode, csName, csEmail, quantity, date;

    DBHelper dbHelper;
    Boolean insertStatus;
    DatePickerDialog datePicker;
    Button submit, cancel;
    FragmentSalesBinding fragmentSalesBinding;
    Date testDate;







    public SalesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SalesFragment newInstance(String param1, String param2) {
        SalesFragment fragment = new SalesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentSalesBinding = FragmentSalesBinding.inflate(inflater, container, false);
        View view = fragmentSalesBinding.getRoot();
        init();
        return view;
    }

    private void init() {
        // Initialize the DBHelper object to interact with the SQLite database
        dbHelper = new DBHelper(getActivity());


        dbHelper = new DBHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        itemCode = fragmentSalesBinding.edItemCode;
        csName = fragmentSalesBinding.edCustomerName;
        csEmail = fragmentSalesBinding.edCustomerEmail;
        quantity = fragmentSalesBinding.edQuantitySold;
        date = fragmentSalesBinding.edDate;
        submit = fragmentSalesBinding.btnSubmit;
        cancel = fragmentSalesBinding.btnCancel;


        date.setInputType(InputType.TYPE_NULL);

        // Set the click listeners
        date.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Log.d("OnClick", "Button clicked: " + v.getId());
        if (v.getId() == cancel.getId()) {
            Log.d("OnClick", "Cancel button clicked");
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);


        }       else if (v.getId() == date.getId()) {
            // Show a date picker dialog
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

        }



        else if (validateInput()) {
            if (v.getId() == submit.getId()) {


                Integer sItemCode = Integer.parseInt(itemCode.getText().toString());
                String sCustomerName = csName.getText().toString();
                String sCustomerEmail = csEmail.getText().toString();
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





                int itemCodeFromSales = sItemCode;
                boolean existsInStock = dbHelper.isItemCodeExistsInStock(itemCodeFromSales);

                Sales sales = new Sales(sItemCode, sCustomerName, sCustomerEmail, sQuantity, sDate);


                int AvailableQuantity = dbHelper.getStockQuantity(sItemCode);


                if (existsInStock) {
                    if (sQuantity > AvailableQuantity + 1) {
                        new MaterialAlertDialogBuilder(requireActivity()).setTitle("Transaction Failed").setMessage("Requested quantity of " + sQuantity + " is greater than the available quantity of " + AvailableQuantity).setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // User clicked OK button
                                    }
                                })

                                .show();
                    } else {
                        boolean recordSale = dbHelper.salesRecord(sItemCode, sQuantity);

                        if (recordSale) {
                            if (validateInput()) {
                                insertStatus = dbHelper.insertSale(sales);
                                if (insertStatus) {

                                    new MaterialAlertDialogBuilder(requireActivity()).setTitle("Sale Successful").setMessage("Item successfully sold.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // User clicked OK button
                                                }
                                            })

                                            .show();

                                } else {
                                    new MaterialAlertDialogBuilder(requireActivity()).setTitle("Failed").setMessage("Purchase Failed.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // User clicked OK button
                                                }
                                            })

                                            .show();
                                }
                            } else {
                                new MaterialAlertDialogBuilder(requireActivity()).setTitle("Validation Error").setMessage("There is an issue with one of the input fields.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // User clicked OK button
                                            }
                                        })

                                        .show();
                            }
                        }


                    }

                } else {

                    new MaterialAlertDialogBuilder(requireActivity()).setTitle("Item Not Found").setMessage("The item does not exist").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // User clicked OK button
                                }
                            })

                            .show();


                }


            }



    }  else

    {

        new MaterialAlertDialogBuilder(requireActivity()).setTitle("Validation Failed").setMessage("There is a problem with one of the input fields ").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked OK button
                    }
                })

                .show();
    }


}

















    private boolean validateInput() {
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

        if (csName.getText().toString().trim().isEmpty()) {
            csName.setError("Enter the name of the customer");
            return false;
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (csEmail.getText().toString().trim().isEmpty()) {
            csEmail.setError("Email is required");
            return false;
        } else if (!csEmail.getText().toString().trim().matches(emailPattern)) {
            csEmail.setError("Invalid email format");
            return false;
        }

        String inputDateFormat = "dd/MM/yyyy";
        String dateStr = date.getText().toString().trim();

        if (dateStr.isEmpty()) {
            date.setError("Enter a valid date (format: " + inputDateFormat + ")");
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat, Locale.getDefault());
            Date parsedDate = sdf.parse(dateStr);

            if (parsedDate != null && parsedDate.after(new Date())) {
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