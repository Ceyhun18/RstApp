package com.shadliq.palaces;

import android.widget.Filter;

import com.shadliq.palaces.adapter.RestaurantAdapter;
import com.shadliq.palaces.dto.response.light.RestaurantResponseDTO;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    public CustomFilter(RestaurantAdapter popularRestaurantAdapter, ArrayList<RestaurantResponseDTO> filterList) {
        this.popularRestaurantAdapter = popularRestaurantAdapter;
        this.filterList = filterList;
    }

    RestaurantAdapter popularRestaurantAdapter;
    ArrayList<RestaurantResponseDTO> filterList;
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<RestaurantResponseDTO> filteredPlayers=new ArrayList<>();
            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))

                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        popularRestaurantAdapter.responseDtoList= (ArrayList<RestaurantResponseDTO>) filterResults.values;
        //REFRESH
        popularRestaurantAdapter.notifyDataSetChanged();

    }
}
