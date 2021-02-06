package com.shadliq.palaces.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.shadliq.palaces.DetailedSearch;
import com.shadliq.palaces.R;
import com.shadliq.palaces.RestoranActivity;
import com.shadliq.palaces.adapter.RestaurantAdapter;
import com.shadliq.palaces.api.RestaurantAPI;
import com.shadliq.palaces.bean.FilterBean;
import com.shadliq.palaces.dto.response.ListResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantResponseDTO;
import com.shadliq.palaces.listener.InfiniteScrollListener;
import com.shadliq.palaces.listener.PaginationScrollListener;
import com.shadliq.palaces.service.CustomCallback;
import com.shadliq.palaces.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements RestaurantAdapter.OnNoteListener, InfiniteScrollListener.OnLoadMoreListener {

    RecyclerView popularResRecyclerview;
    RestaurantAdapter restaurantAdapter;
    Retrofit retrofit;
    RestaurantAPI restaurantAPI;
    ProgressBar progressBar;
    GridLayoutManager layoutManager;
    Toolbar toolbar;
    private int max = 6;
    private HomeFragment homeFragment;
    SearchView searchView;
    private Context context;
    private String searchKey = "";
    private PaginationScrollListener scrollListener;
    private NestedScrollView mNestedScrollView;
    List<RestaurantResponseDTO> data;
    FilterBean filterBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        context = getActivity().getApplicationContext();

        homeFragment = this;

        popularResRecyclerview = view.findViewById(R.id.restoran_recyclerview);


        progressBar = view.findViewById(R.id.progress);
        toolbar = view.findViewById(R.id.toolbarr);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        searchView = view.findViewById(R.id.searchview);
        searchView.setQueryHint("Axtar...");


        layoutManager = new GridLayoutManager(getContext(), 2);
        scrollListener = new PaginationScrollListener(layoutManager, this);
        scrollListener.setLoaded();
        mNestedScrollView = view.findViewById(R.id.nestedScrollView);
        mNestedScrollView.setOnScrollChangeListener(scrollListener);
        popularResRecyclerview.setHasFixedSize(true);
        popularResRecyclerview.setLayoutManager(layoutManager);
        restaurantAdapter = new RestaurantAdapter(this, getContext(),popularResRecyclerview);
        popularResRecyclerview.setAdapter(restaurantAdapter);


        retrofit = RetrofitUtil.getInstance(context);

        filterBean = Paper.book().read("filter");
        Paper.book().delete("filter");

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchKey = query;
                getdata(0, searchKey,filterBean);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                //searchKey = text;
                //getdata(0, searchKey,filterBean);
                return false;
            }
        });

        getdata(0, searchKey,filterBean);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.detailed_search:
                Intent intent = new Intent(getActivity(), DetailedSearch.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getdata(int pageNumber, String key, FilterBean filterBean) {
        Integer menuPrice = null;
        Integer guestCount = null;
        Byte eventType = null;

        if (filterBean != null) {
            menuPrice = filterBean.getMenuPrice();
            guestCount = filterBean.getGuestCount();
            eventType = filterBean.getEventType();
        }

        restaurantAPI = retrofit.create(RestaurantAPI.class);


        Call<ListResponseDTO> call = restaurantAPI.getData(pageNumber, max, menuPrice, eventType, guestCount, key);
        CustomCallback<ListResponseDTO> callback = new CustomCallback<ListResponseDTO>(context) {

            @Override
            public void onRequestSuccess(ListResponseDTO postResultDTO) {
                data = postResultDTO.getList();
                System.out.println(data);
                progressBar.setVisibility(View.GONE);
                mNestedScrollView.setVisibility(View.VISIBLE);

                if (!key.equals("")) {
                    restaurantAdapter.removeAll();
                }
                restaurantAdapter.addData(data);

            }
        };
        call.enqueue(callback);


    }

    @Override
    public void OnNoteClick(int position) {
        Intent intent = new Intent(getContext(), RestoranActivity.class);
        List<RestaurantResponseDTO> list = restaurantAdapter.getList();
        RestaurantResponseDTO restaurantResponseDTO = list.get(position);
        Paper.book().write("restaurantPosition", restaurantResponseDTO);
        Bundle bundle = new Bundle();
        bundle.putInt("id", list.get(position).getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /* private void performPagination() {


          Call<ListResponseDTO> call = restaurantAPI.getData(PAGE_START, TOTAL_PAGES, null, null, null, searchKey);

          CustomCallback<ListResponseDTO> callback = new CustomCallback<ListResponseDTO>(context) {

              @Override
              public void onRequestSuccess(ListResponseDTO postResultDTO) {


                  List<RestaurantResponseDTO> data = postResultDTO.getList();
                  Paper.book().write("PerformPaginationData",data);

                  restaurantAdapter.addData(data);

              }
          };
          call.enqueue(callback);

      }
  */
    @Override
    public void onLoadMore(int offset) {
        restaurantAdapter.addNullData();
        new Handler().postDelayed(() -> {
            getdata(offset, searchKey,filterBean);
            restaurantAdapter.removeNull();
            scrollListener.setLoaded();
        }, 2000);
    }
}


