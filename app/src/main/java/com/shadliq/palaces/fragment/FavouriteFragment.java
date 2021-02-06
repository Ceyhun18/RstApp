package com.shadliq.palaces.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shadliq.palaces.RestoranActivity;
import com.shadliq.palaces.adapter.FavouriteAdapter;
import com.shadliq.palaces.R;
import com.shadliq.palaces.dto.response.light.RestaurantResponseDTO;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;

public class FavouriteFragment extends Fragment implements FavouriteAdapter.FavouriteAdapterOnNoteListener {

    RecyclerView recyclerView;
    FavouriteAdapter favouriteAdapter;
    List<RestaurantResponseDTO> favorites;
    int favouriteID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favourite_fragment, container, false);

        recyclerView = view.findViewById(R.id.favourite_recyclerview);

        favorites = Paper.book().read("favorites");
        if (favorites != null) {
            Collections.reverse(favorites);
            favouriteAdapter = new FavouriteAdapter(getContext(), favorites, this);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(favouriteAdapter);
        }

        return view;
    }

    @Override
    public void FavouriteAdapterOnNoteClick(int position) {
        Intent intent = new Intent(getContext(), RestoranActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", favorites.get(position).getId());
        intent.putExtras(bundle);        startActivity(intent);
    }
}
