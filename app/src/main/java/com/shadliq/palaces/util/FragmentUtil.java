package com.shadliq.palaces.util;

import android.app.Activity;

import com.shadliq.palaces.R;
import com.shadliq.palaces.fragment.FavouriteFragment;
import com.shadliq.palaces.fragment.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {


    private FragmentManager manager;
    private HomeFragment homeFragment = new HomeFragment();
    private FavouriteFragment favouriteFragment = new FavouriteFragment();
    private Activity activity;

    public FragmentUtil(AppCompatActivity activity) {
        this.activity = activity;
        this.manager = activity.getSupportFragmentManager();
    }

    public boolean openFragmentByNavId(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                openFragment(R.id.fragment_container,homeFragment);
                break;

            case R.id.nav_search:
                openFragment(R.id.fragment_container, favouriteFragment);

                break;
        }
        return true;
    }

    public void openFragment(int id, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
