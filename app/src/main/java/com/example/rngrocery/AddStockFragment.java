package com.example.rngrocery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rngrocery.databinding.FragmentAddStockBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStockFragment extends Fragment implements  View.OnClickListener, RadioGroup.OnCheckedChangeListener {






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText itemName, quantity, price;
    RadioGroup taxableStatus;
    RadioButton taxable;
    RadioButton nonTaxable;
    DBHelper dbHelper;
    Boolean insertStatus;
    boolean status;
    Button save,cancel;
    FragmentAddStockBinding addStockBinding;

    public AddStockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStockFragment newInstance(String param1, String param2) {
        AddStockFragment fragment = new AddStockFragment();
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
        addStockBinding = FragmentAddStockBinding.inflate(inflater, container, false);
        View view = addStockBinding.getRoot();
        init();







        return view;







    }

    private void init() {
        // Initialize the DBHelper object to interact with the SQLite database
        dbHelper = new DBHelper(getActivity());


        itemName = addStockBinding.edItemName;
        quantity = addStockBinding.edQuantity;
        price = addStockBinding.edPrice;
        taxableStatus = addStockBinding.rgTaxStatus;
        taxable = addStockBinding.rbTaxable;
        nonTaxable = addStockBinding.rbNonTaxable;
        save = addStockBinding.btnSave;
        cancel = addStockBinding.btnCancel;

        // Set the click listener for the submit button
        save.setOnClickListener(this);

        taxableStatus.setOnCheckedChangeListener(this);







    }

    @Override
    public void onClick(View v) {

        // Check if the clicked view is the submit button


        if (validateInput()){
            if (v.getId() == save.getId()) {


                Log.e("erororororoor", "onClick:Button tapped");






                String sItemName = itemName.getText().toString();
                Integer sQuantity = Integer.parseInt(quantity.getText().toString());
                Double sPrice = Double.parseDouble(price.getText().toString());
                Boolean taxable = status;

                Stock stock = new Stock(sItemName,sQuantity,sPrice,taxable);

                insertStatus = dbHelper.insertStock(stock);





                // Display a toast message based on the insertion result
                if (insertStatus) {
//                    Toast.makeText(getActivity(), " record added successfully", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(),status + " record added successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Record insertion failure", Toast.LENGTH_LONG).show();
                }
            }
        }





    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.e("CheckedChanged", "Checked changed. ID: " + checkedId);
        if (checkedId == taxable.getId()){
            status = true;
            Log.e("Tax Status", "Taxable");




        }
        else if(checkedId == nonTaxable.getId()){
            status = false;
            Log.e("Tax Status", "Non Taxable");
            Log.e("Tax Status", "Non Taxable" + status);
        }

    }


    // Validate user input fields
    private boolean validateInput() {
        if (itemName.getText().toString().trim().isEmpty()) {
            itemName.setError("Enter the name of the item");
            return false;
        }

        String quantityStr = quantity.getText().toString().trim();
        if (quantityStr.isEmpty()) {
            quantity.setError("Enter the quantity");
            return false;
        }

        try {
            int quantityValue = Integer.parseInt(quantityStr);
            // Quantity is a valid integer
        } catch (NumberFormatException e) {
            quantity.setError("Enter a valid quantity (numeric)");
            return false;
        }



        String priceStr = price.getText().toString().trim();
        if (priceStr.isEmpty()) {
            price.setError("Enter the price");
            return false;
        }

        try {
            double priceValue = Double.parseDouble(priceStr);
            // Price is a valid double
        } catch (NumberFormatException e) {
            price.setError("Enter a valid price (numeric)");
            return false;
        }



        return true; // All input fields are valid
    }
}