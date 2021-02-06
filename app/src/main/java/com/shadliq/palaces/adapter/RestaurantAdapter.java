package com.shadliq.palaces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shadliq.palaces.CustomFilter;
import com.shadliq.palaces.R;
import com.shadliq.palaces.dto.response.light.RestaurantMenuLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantResponseDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private OnNoteListener onNoteListener;
    private Context context;
    public List<RestaurantResponseDTO> responseDtoList = new ArrayList<>();
    private CustomFilter filter;
    private List<RestaurantResponseDTO> mFilteredList;
    private static int VIEW_TYPE_ITEM = 1;
    private static int VIEW_TYPE_LOADING = 2;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mManager;

    public RestaurantAdapter(OnNoteListener onNoteListener, Context context,RecyclerView recyclerView) {
        this.onNoteListener = onNoteListener;
        this.context = context;
        this.responseDtoList = responseDtoList;
        this.mRecyclerView = recyclerView;
        mManager = (GridLayoutManager) recyclerView.getLayoutManager();
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return getItemViewType(position) == VIEW_TYPE_LOADING ? mManager.getSpanCount() : 1;
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        if (responseDtoList.get(position) != null)
            return VIEW_TYPE_ITEM;
        else
            return VIEW_TYPE_LOADING;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root;

        if (viewType == VIEW_TYPE_ITEM) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout3, parent, false);
            return new PostViewHolder(root,onNoteListener);
        } else {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress, parent, false);
            return new ProgressViewHolder(root);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {

        if (holder instanceof PostViewHolder) {

            PostViewHolder postViewHolder = (PostViewHolder) holder;

            RestaurantResponseDTO restaurantResponseDto = responseDtoList.get(position);


            List<RestaurantMenuLightResponseDTO> menus = restaurantResponseDto.getMenus();
            postViewHolder.location_text.setText(restaurantResponseDto.getRegionName());
            int min = menus.get(0).getPrice();

            for (int i = 0; i < menus.size(); i++) {

                if (min > menus.get(i).getPrice()) {

                    min = menus.get(i).getPrice();


                }

                postViewHolder.restaurantMenuPrice.setText(min + " AZN");

            }

            postViewHolder.restoran_adi.setText(restaurantResponseDto.getName());


            Picasso.get()
                    .load(restaurantResponseDto.getImageUrl()).


                    into(postViewHolder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return responseDtoList == null ? 0 : responseDtoList.size();
    }

    public void addNullData() {
        responseDtoList.add(null);
        notifyDataSetChanged();
    }

    public void clear() {
        responseDtoList.clear();
    }

    public List<RestaurantResponseDTO> getList(){
        return responseDtoList;
    }


    public void add(RestaurantResponseDTO restaurantResponseDTO) {
        responseDtoList.add(restaurantResponseDTO);
        notifyItemInserted(responseDtoList.size() - 1);
    }

    public RestaurantResponseDTO getItem(int position) {
        return responseDtoList.get(position);
    }

    public void addAll(List<RestaurantResponseDTO> moveResults) {
        for (RestaurantResponseDTO result : moveResults) {
            add(result);
        }
    }


    public void addData(List<RestaurantResponseDTO> posts) {
        this.responseDtoList.addAll(posts);
//        this.notifyItemInserted(getPostSize() - 1);
        notifyDataSetChanged();
    }

    public void removeNull() {
       responseDtoList.remove(responseDtoList.size() - 1);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.responseDtoList.clear();
        notifyDataSetChanged();
    }

    class PostViewHolder extends CustomViewHolder implements View.OnClickListener {
        OnNoteListener onNoteListener;
        ImageView imageView;
        TextView restaurantMenuPrice, restoran_adi, location_text;
        ProgressBar progressBar;

        public PostViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            location_text = itemView.findViewById(R.id.location_text);
            restoran_adi = itemView.findViewById(R.id.restoran_name);
            imageView = itemView.findViewById(R.id.profile_picture);

            restaurantMenuPrice = itemView.findViewById(R.id.restoran_azn);


            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            onNoteListener.OnNoteClick(getAdapterPosition());

        }

    }


    class ProgressViewHolder extends CustomViewHolder{

        ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnNoteListener {

        void OnNoteClick(int position);


    }
}