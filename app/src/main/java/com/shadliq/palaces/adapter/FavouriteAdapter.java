package com.shadliq.palaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shadliq.palaces.R;
import com.shadliq.palaces.dto.response.light.RestaurantMenuLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantResponseDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.RowHolder6> {
    FavouriteAdapterOnNoteListener favouriteAdapterOnNoteListener;
    Context context;
    List<RestaurantResponseDTO> favorites;
    List<RestaurantMenuLightResponseDTO> menuList;

    public FavouriteAdapter(Context context, List<RestaurantResponseDTO> favorites, FavouriteAdapterOnNoteListener favouriteAdapterOnNoteListener) {
        this.context = context;
        this.favouriteAdapterOnNoteListener = favouriteAdapterOnNoteListener;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public RowHolder6 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.row_layout6, parent, false);

        return new RowHolder6(view, favouriteAdapterOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder6 holder, int position) {
        RestaurantResponseDTO restaurantResponseDTO = favorites.get(position);
        List<RestaurantMenuLightResponseDTO> menus = restaurantResponseDTO.getMenus();
        holder.menuPrice.setText(menus.get(0).getPrice() + " AZN");
        holder.restaurantName.setText(restaurantResponseDTO.getName());
        holder.locationText.setText(restaurantResponseDTO.getRegionName());


        Picasso.get()
                .load(restaurantResponseDTO.getImageUrl())

                .into(holder.imageView);

        holder.deleteButton.setOnClickListener(view -> deleteItem(position));
    }


    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public void deleteItem(int pos) {
        favorites.remove(pos);
        Paper.book().write("favorites", favorites);
        notifyDataSetChanged();
    }


    public class RowHolder6 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView restaurantName, menuPrice,locationText;
        Button deleteButton;
        FavouriteAdapterOnNoteListener favouriteAdapterOnNoteListener;
        ImageView imageView;

        public RowHolder6(@NonNull View itemView, FavouriteAdapterOnNoteListener favouriteAdapterOnNoteListener) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.ad);
            deleteButton = itemView.findViewById(R.id.delete_button);
            menuPrice = itemView.findViewById(R.id.aznn);
            imageView = itemView.findViewById(R.id.favourite_imageView);
            locationText = itemView.findViewById(R.id.location);
            this.favouriteAdapterOnNoteListener = favouriteAdapterOnNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            favouriteAdapterOnNoteListener.FavouriteAdapterOnNoteClick(getAdapterPosition());
        }
    }

    public interface FavouriteAdapterOnNoteListener {

        void FavouriteAdapterOnNoteClick(int position);


    }
}
