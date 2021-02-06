package com.shadliq.palaces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shadliq.palaces.R;
import com.shadliq.palaces.dto.response.light.RestaurantHallLightResponseDTO;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HallAdapter extends RecyclerView.Adapter<HallAdapter.HallRow> {
    Context context;
    List<RestaurantHallLightResponseDTO> list;

    public HallAdapter(Context context, List<RestaurantHallLightResponseDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HallAdapter.HallRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hall_row,parent,false);
        return new HallRow(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HallAdapter.HallRow holder, int position) {

        RestaurantHallLightResponseDTO restaurantHallLightResponseDTO = list.get(position);
        holder.hallText.setText(restaurantHallLightResponseDTO.getHallName() + " "
                + restaurantHallLightResponseDTO.getMinGuestCount() + "-" + restaurantHallLightResponseDTO.getMaxGuestCount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HallRow extends RecyclerView.ViewHolder {
        TextView hallText;
        public HallRow(@NonNull View itemView) {
            super(itemView);
            hallText = itemView.findViewById(R.id.hall_textView);
        }
    }
}
