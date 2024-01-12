package com.example.rngrocery;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rngrocery.databinding.FragmentPurchaseBinding;
import com.example.rngrocery.databinding.FragmentSearchBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    EditText itemCode;
    TextView sName,sId,sPrice,sQty;
    DBHelper dbHelper;
    Boolean status;
    Button search, cancel;
    FragmentSearchBinding fragmentSearchBinding;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        // Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = fragmentSearchBinding.getRoot();
        init(); // Initialize components
        return view;
    }

    private void init() {

        dbHelper = new DBHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        itemCode = fragmentSearchBinding.edItemCode;
        sId = fragmentSearchBinding.id;
        sName = fragmentSearchBinding.name;
        sQty = fragmentSearchBinding.qty;
        sPrice = fragmentSearchBinding.price;
        search = fragmentSearchBinding.btnSubmit;
        cancel = fragmentSearchBinding.btnCancel;

        search.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == cancel.getId()) {
            Log.d("OnClick", "Cancel button clicked");
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == search.getId()){

            Integer sItemCode = Integer.parseInt(itemCode.getText().toString());
            int itemCodeFromSales = sItemCode;
            boolean existsInStock = dbHelper.isItemCodeExistsInStock(itemCodeFromSales);

           if (existsInStock){

               // Call the method to search for stock by item code
               Stock searchedStock = dbHelper.searchStockById( sItemCode);



               if (searchedStock != null) {
                   // Display the details of the searched stock
                   sId.setText(String.valueOf(sItemCode));
                   sName.setText(searchedStock.getStockName());
                   sPrice.setText(String.valueOf("$" + String.format(Locale.getDefault(), "%.2f", searchedStock.getPrice())));
                   sQty.setText(String.valueOf(searchedStock.getQuantity()));
               } else {
                   // Handle the case where the stock is not found
                   // You may want to show a message to the user, e.g., Toast.makeText(requireContext(), "Stock not found", Toast.LENGTH_SHORT).show();
                   // Clear the fields
                   sId.setText("");
                   sName.setText("");
                   sPrice.setText("");
                   sQty.setText("");

           }



            }else{
               new MaterialAlertDialogBuilder(requireActivity()).setTitle("Item Not Found").setMessage("The item does not exist").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
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







        return true; // All input fields are valid
    }
}