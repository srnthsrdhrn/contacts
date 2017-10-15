package com.example.android.displaycontacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Srinath on 15-10-2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mValues;
    private final ClickListener listener;

    public RecyclerViewAdapter(List<Contact> items, ClickListener listener) {
        mValues = items;
        this.listener =listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getName());
        holder.number.setText(mValues.get(position).getPhoneNumber() + "");
        holder.photo.setImageURI(mValues.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Contact mItem;
        public TextView name, number;
        public ImageView photo;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.contact_name);
            number = (TextView) view.findViewById(R.id.contact_number);
            photo = (ImageView) view.findViewById(R.id.contact_image);
        }
    }
    public interface ClickListener{
        void onClick(int position);
    }
}

