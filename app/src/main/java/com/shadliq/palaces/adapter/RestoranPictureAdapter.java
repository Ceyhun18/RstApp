package com.shadliq.palaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shadliq.palaces.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestoranPictureAdapter extends RecyclerView.Adapter<RestoranPictureAdapter.RowHolder6> {
    Context context;

    public RestoranPictureAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RestoranPictureAdapter.RowHolder6 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_layout_restoran_picture,parent,false);

        return new RowHolder6(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoranPictureAdapter.RowHolder6 holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class RowHolder6 extends RecyclerView.ViewHolder {
        public RowHolder6(@NonNull View itemView) {
            super(itemView);
        }
    }
}
