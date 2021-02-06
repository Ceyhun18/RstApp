package com.shadliq.palaces;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.opengl.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.shadliq.palaces.adapter.FavouriteAdapter;
import com.shadliq.palaces.adapter.HallAdapter;
import com.shadliq.palaces.adapter.OffersAdapter;
import com.shadliq.palaces.adapter.ViewPagerAdapter;
import com.shadliq.palaces.api.RestaurantAPI;
import com.shadliq.palaces.dto.response.fat.RestaurantFatResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantContactLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantHallLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantMenuLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantOfferLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantResponseDTO;
import com.shadliq.palaces.enums.EventTypeEnum;
import com.shadliq.palaces.service.CustomCallback;
import com.shadliq.palaces.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Retrofit;


public class RestoranActivity extends AppCompatActivity implements FavouriteAdapter.FavouriteAdapterOnNoteListener {

    private TextView restaurantName;
    private TextView locationText;
    private TextView menuPrice;
    private TextView telephoneNumbers;
    private AppCompatActivity activity;
    private Context context;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ScaleGestureDetector mScaleGestureDetector;
    float mScale = 1f;
    private ScaleGestureDetector SGD;
    private ImageView imageView;
    private LikeButton favouriteIcon;
    private Matrix matrix = new Matrix();
    private Button map;
    private RestaurantResponseDTO restaurantResponseDTO;
    List<RestaurantResponseDTO> favorites;
    RestoranActivity restoranActivity;
    Retrofit retrofit;
    RestaurantAPI restaurantAPI;
    Integer itemID;
    ProgressBar progressBar;
    RestaurantMenuLightResponseDTO restaurantMenuLightResponseDTO;
    RestaurantContactLightResponseDTO restaurantContactLightResponseDTO;
    RestaurantHallLightResponseDTO restaurantHallLightResponseDTO;
    RecyclerView hallRecyclerView;
    HallAdapter hallAdapter;
    TextView eventType;

    Byte event;
    TextView restaurantAnsanmbl;
    RestaurantOfferLightResponseDTO restaurantOfferLightResponseDTO;
    String names = "";
    byte eventt;
    String eventName = "";
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<String> images;
    RecyclerView offersView;
    OffersAdapter offersAdapter;
    ImageView dotImage;
    Integer restauranId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        favouriteIcon = findViewById(R.id.favourite_button);
        context = getApplicationContext();
        activity = this;
        restoranActivity = this;
        menuPrice = findViewById(R.id.restoran_activity_azn);

        restaurantName = findViewById(R.id.restoran_activity_name);
        restaurantResponseDTO = Paper.book().read("restaurantPosition");
        System.out.println("Adi " + restaurantResponseDTO.getName());
        retrofit = RetrofitUtil.getInstance(context);
        progressBar = findViewById(R.id.progressBar);
        restaurantName = findViewById(R.id.restoran_activity_name);
        telephoneNumbers = findViewById(R.id.restoran_activity_phone);
       hallRecyclerView = findViewById(R.id.hall_recyclerView);
        eventType = findViewById(R.id.event_txt_view);
        offersView = findViewById(R.id.restoran_ansanmbl);
        dotImage = findViewById(R.id.dott);


        map = findViewById(R.id.xeritede_bax);
        imageView = findViewById(R.id.imageView);
        sliderDotspanel = findViewById(R.id.SliderDots);
        viewPager = findViewById(R.id.viewPager);
        locationText = findViewById(R.id.restoran_activity_location);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)

            itemID = bundle.getInt("id");

           checkLike();

        getDetails(itemID);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RestoranActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }


    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }


    public void getDetails(Integer id) {


        restaurantAPI = retrofit.create(RestaurantAPI.class);
        progressBar.setVisibility(View.VISIBLE);


        Call<RestaurantFatResponseDTO> call = restaurantAPI.getDetails(id);
        CustomCallback<RestaurantFatResponseDTO> callback = new CustomCallback<RestaurantFatResponseDTO>(context) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onRequestSuccess(RestaurantFatResponseDTO postResultDTO) {
                List<RestaurantMenuLightResponseDTO> menu = postResultDTO.getMenus();
                List<RestaurantContactLightResponseDTO> phoneNumbers = postResultDTO.getContacts();
                String name = postResultDTO.getName();
                restauranId = postResultDTO.getId();
                String locationName = postResultDTO.getRegionName();
                String location = postResultDTO.getLocation();
                Paper.book().write("location",location);
                System.out.println(location);
                List<Byte> eventTypes = postResultDTO.getEvents();
                List<RestaurantHallLightResponseDTO> halls = postResultDTO.getHalls();
                List<RestaurantOfferLightResponseDTO> offers = postResultDTO.getOffers();
                List<String> imageUrls = postResultDTO.getImageUrls();
                locationText.setText(locationName);


                RecyclerView.LayoutManager layoutManager =
                        new GridLayoutManager(getApplicationContext(),2);

                offersView.setLayoutManager(layoutManager);
                offersAdapter = new OffersAdapter(getApplication(), (ArrayList<RestaurantOfferLightResponseDTO>) offers);
                offersView.setAdapter(offersAdapter);


                viewPagerAdapter = new ViewPagerAdapter(RestoranActivity.this, (ArrayList<String>) imageUrls);


                viewPager.setAdapter(viewPagerAdapter);
                setUpViewPager();

                RecyclerView.LayoutManager hallLayoutManager =
                        new GridLayoutManager(getApplicationContext(),2);
                hallRecyclerView.setLayoutManager(hallLayoutManager);
                hallAdapter = new HallAdapter(getApplicationContext(),halls);
                hallRecyclerView.setAdapter(hallAdapter);


                restaurantName.setText(name);


                for (int i = 0; i < menu.size(); i++) {

                    restaurantMenuLightResponseDTO = menu.get(i);


                }
                menuPrice.setText(restaurantMenuLightResponseDTO.getPrice() + " AZN");


                for (int i = 0; i < phoneNumbers.size(); i++) {



                    restaurantContactLightResponseDTO = phoneNumbers.get(i);


                }
               telephoneNumbers.setText(restaurantContactLightResponseDTO.getDescription() + "");


                for(int i = 0; i < eventTypes.size(); i++) {



                     eventName += EventTypeEnum.getEnum(eventTypes.get(i)).toString();
                     eventName +=  "   " ;
                    eventType.setText(eventName);

                }



                for(int i = 0; i < offers.size() ; i ++) {

                       names += offers.get(i).getName();

                    names += " - ";
                    System.out.println(names);

                }


                favouriteIcon.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        favorites = Paper.book().read("favorites");
                        if (favorites == null) {
                            favorites = new ArrayList<>();
                        }

                        if (!checkItemExist(restaurantResponseDTO, favorites)) {
                            favorites.add(restaurantResponseDTO);
                            Paper.book().write("favorites", favorites);

                        }

                        activity.getResources().getResourceName(R.id.nav_search);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("fragmentId", R.id.nav_search);
                        startActivity(intent);
                        finish();
                        likeButton.setLiked(true);
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {

                        for (int i = 0; i < favorites.size(); i++) {
                            if (favorites.get(i).getId().equals(restauranId)); {

                                favorites.remove(i);

                            }

                            Paper.book().write("favorites", favorites);

                        }

                    }
                });



                progressBar.setVisibility(View.GONE);

                System.out.println(menu);


            }
        };
        call.enqueue(callback);
    }


    @Override
    public void FavouriteAdapterOnNoteClick(int position) {


    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScale = mScale * detector.getScaleFactor();

            imageView.setScaleX(mScale);
            imageView.setScaleY(mScale);

            return true;
        }
    }

    private boolean checkItemExist(RestaurantResponseDTO restaurantResponseDTO, List<RestaurantResponseDTO> favorites) {

        for (RestaurantResponseDTO favorite : favorites) {
            if (favorite.getId().equals(restauranId)) {
                return true;
            }
        }
        return false;
    }

 public void setUpViewPager() {


    dotscount = viewPagerAdapter.getCount();
    dots = new ImageView[dotscount];

    for (int i = 0; i < dotscount; i++) {

        dots[i] = new ImageView(this);
        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(8, 0, 8, 0);

        sliderDotspanel.addView(dots[i], params);

    }

    dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            for (int i = 0; i < dotscount; i++) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            }

            dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    });

    mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


}


    public void checkLike() {
        favorites = Paper.book().read("favorites");
        if (favorites != null)
            for (RestaurantResponseDTO favorite : favorites) {
                if(favorite != null) {
                    if (favorite.getId().equals(restaurantResponseDTO.getId())) {

                        favouriteIcon.setLiked(true);

                    }
                }

            }
    }

}



