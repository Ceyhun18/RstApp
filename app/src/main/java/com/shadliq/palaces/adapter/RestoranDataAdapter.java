package com.shadliq.palaces.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestoranDataAdapter extends RecyclerView.Adapter<RestoranDataAdapter.RowHolder7> {
    Context context;

    public RestoranDataAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RestoranDataAdapter.RowHolder7 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RestoranDataAdapter.RowHolder7 holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class RowHolder7 extends RecyclerView.ViewHolder {
        public RowHolder7(@NonNull View itemView) {
            super(itemView);
        }
    }
}
