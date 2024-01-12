package com.example.rngrocery;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rngrocery.databinding.RecordLayoutBinding;

import java.util.Locale;

/**
 * ViewHolder class for the RecyclerView to hold and manage views for each item in the list.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    // Binding object for the record layout
    RecordLayoutBinding recyclerRowBinding;

    /**
     * Constructor for the ViewHolder class.
     *
     * @param recyclerRowBinding The binding object for the record layout.
     */
    public ViewHolder(RecordLayoutBinding recyclerRowBinding) {
        super(recyclerRowBinding.getRoot());
        this.recyclerRowBinding = recyclerRowBinding;
    }

    /**
     * Method to bind data to the views within the ViewHolder.
     *
     * @param stock The Stock object containing data to be displayed.
     */
    public void bindView(Stock stock) {
        // Set data to the respective TextViews in the record layout
        recyclerRowBinding.txtId.setText(String.valueOf(stock.getStockId()));
        recyclerRowBinding.txtName.setText(stock.getStockName());
        recyclerRowBinding.txtPrice.setText(String.valueOf("$" + String.format(Locale.getDefault(), "%.2f", stock.getPrice())));
        recyclerRowBinding.txtQuantity.setText(String.valueOf(stock.getQuantity())); // Note: Should this be stock.getQuantity()?
        recyclerRowBinding.txtTaxable.setText(String.valueOf(stock.isTaxable()));
    }
}
