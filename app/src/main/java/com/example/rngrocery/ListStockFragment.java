package com.example.rngrocery;

import static com.example.rngrocery.DBHelper.COL_ITEM_NAME;
import static com.example.rngrocery.DBHelper.COL_PRICE;
import static com.example.rngrocery.DBHelper.COL_TAXABLE;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rngrocery.databinding.FragmentListStockBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListStockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListStockFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FragmentListStockBinding listBinding;

    List<Stock> sList = new ArrayList<>();

    ListAdapter sAdapter;

    DBHelper  dbHelper;;

    public ListStockFragment() {
        // Required empty public constructor

     
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListStockFragment newInstance(String param1, String param2) {
        ListStockFragment fragment = new ListStockFragment();
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
        listBinding = FragmentListStockBinding.inflate(inflater, container, false);
        View view = listBinding.getRoot();
        init();
        return view;
    }

    private void init() {

        dbHelper = new DBHelper(getActivity());






        // Read stock records from the database
        Cursor cursor1 = dbHelper.readStockList();

        // Check if the cursor is not null
        if (cursor1 != null) {
            // Check if any records are found
            if (cursor1.getCount() > 0) {
                cursor1.moveToFirst();

                // Clear the existing list before adding new items
                sList.clear();

                // Loop through the cursor and populate the Stock list
                do {
                    Stock stock = new Stock();
                    stock.setStockId(cursor1.getInt(cursor1.getColumnIndexOrThrow("itemCode")));
                    stock.setStockName(cursor1.getString(1));
                    stock.setQuantity(cursor1.getInt(2));
                    stock.setPrice(cursor1.getFloat(3));
                    stock.setTaxable(cursor1.getInt(4) == 1);

                    sList.add(stock);

                } while (cursor1.moveToNext());

                // Close the cursor
                cursor1.close();

                // Close the database connection
                dbHelper.close();

                // Bind the adapter to the RecyclerView
                bindAdapter();
            } else {
                // No records found
                Toast.makeText(getActivity(), "No stock records found", Toast.LENGTH_LONG).show();

                // Close the cursor and the database connection
                cursor1.close();
                dbHelper.close();
            }
        } else {
            // Cursor is null
            Toast.makeText(getActivity(), "Error retrieving stock records", Toast.LENGTH_LONG).show();
        }
    }




    // Bind the adapter to the RecyclerView
    private void bindAdapter() {
        // Set up the layout manager for the RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listBinding.rcView.setLayoutManager(layoutManager);

        // Create and set the adapter for the RecyclerView
        sAdapter = new ListAdapter(sList, getContext());
        listBinding.rcView.setAdapter(sAdapter);
        sAdapter.notifyDataSetChanged();
    }
}