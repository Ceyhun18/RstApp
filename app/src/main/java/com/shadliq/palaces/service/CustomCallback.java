package com.shadliq.palaces.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.shadliq.palaces.exception.OkHttpException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CustomCallback<T> implements Callback<T> {

    private ProgressBar progressBar;
    private Context context;
    private Activity activity;

    public CustomCallback(Context context) {
        this.context = context;
    }

    public CustomCallback(Activity activity) {
        this.activity = activity;
        this.context = activity;
       // progressBar = activity.findViewById(R.id.progress_bar);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.i("ON RESPONSE", "ON RESPONSE");
        hideProgressBar();
        Runnable runnable = () -> onRequestSuccess(response.body());
        runnable.run();
    }

    @Override
    public void onFailure(Call<T> call, final Throwable t) {
        Log.i("ON FAILURE", "FAILED");
        hideProgressBar();
        t.printStackTrace();
        if ((t instanceof OkHttpException)) {
            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "An error occured", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideProgressBar() {
        if (progressBar != null && progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public abstract void onRequestSuccess(T response);

}