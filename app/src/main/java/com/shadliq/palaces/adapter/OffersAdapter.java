package com.shadliq.palaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shadliq.palaces.R;
import com.shadliq.palaces.dto.response.light.RestaurantOfferLightResponseDTO;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersHolder> {
    public OffersAdapter(Context context, ArrayList<RestaurantOfferLightResponseDTO> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    ArrayList<RestaurantOfferLightResponseDTO> list;

    @NonNull
    @Override
    public OffersAdapter.OffersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.row_layout_offers,parent,false);

        return new  OffersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.OffersHolder holder, int position) {

        RestaurantOfferLightResponseDTO restaurantOfferLightResponseDTO = list.get(position);
        holder.offerTextview.setText(restaurantOfferLightResponseDTO.getName() + "  ");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OffersHolder extends RecyclerView.ViewHolder {
        TextView offerTextview;

        public OffersHolder(@NonNull View itemView) {
            super(itemView);

            offerTextview = itemView.findViewById(R.id.offers);
        }
    }
}
