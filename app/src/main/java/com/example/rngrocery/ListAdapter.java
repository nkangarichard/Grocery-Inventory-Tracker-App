package com.example.rngrocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rngrocery.databinding.RecordLayoutBinding;

import java.util.List;

/**
 * Adapter class for the RecyclerView to bind data from the data source to the ViewHolder.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // List of Stock objects to be displayed
    private List<Stock> stockList;

    // Binding object for the record layout
    RecordLayoutBinding recordLayoutBinding;

    /**
     * Constructor for the ListAdapter class.
     *
     * @param stList   List of Stock objects to be displayed.
     * @param context  Context of the calling activity or fragment.
     */
    public ListAdapter(List<Stock> stList, Context context) {
        super();
        this.stockList = stList;
    }

    /**
     * Inflates the record layout when creating a new ViewHolder.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The type of view.
     * @return A new ViewHolder with the inflated record layout.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        recordLayoutBinding = RecordLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(recordLayoutBinding);
    }

    /**
     * Binds data to the ViewHolder at the specified position.
     *
     * @param holder   The ViewHolder to bind data to.
     * @param position The position of the item in the data source.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(stockList.get(position));
    }

    /**
     * Returns the total number of items in the data source.
     *
     * @return The total number of items in the data source.
     */
    @Override
    public int getItemCount() {
        return stockList.size();
    }
}
