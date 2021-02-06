package com.shadliq.palaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.paperdb.Paper;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shadliq.palaces.fragment.HomeFragment;
import com.shadliq.palaces.util.FragmentUtil;

public class MainActivity extends AppCompatActivity {

    private FragmentUtil fragmentUtil;
    private AppCompatActivity activity;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        fragmentUtil = new FragmentUtil(activity);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        Paper.init(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int fragmentId = bundle.getInt("fragmentId");
            String iconColor = bundle.getString("iconcolor");
            fragmentUtil.openFragmentByNavId(fragmentId);
            bottomNavigationView.setSelectedItemId(fragmentId);

        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment selectedFragment = null;
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    selectedFragment = new HomeFragment();
//                    break;
//
//                case R.id.nav_search:
//                   selectedFragment = new FavouriteFragment();
//                    break;
//            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            fragmentUtil.openFragmentByNavId(item.getItemId());

            return  true;
        }
    };
}
