package com.shadliq.palaces.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit retrofitInstance = null;
    private static final String BASE_URL = "http://161.97.79.195:8080/";

    public RetrofitUtil() {
    }

    /*
     new version of creation
    */
    public static Retrofit getInstance(Context context) {
        if (retrofitInstance == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new CustomInterceptor(context)).build();

            retrofitInstance = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
        return retrofitInstance;
    }

    private void printRequest(final Request request) {

        try {
            final Request copy = request.newBuilder().build();
            if (request.method().equals("POST") || request.method().equals("PUT")) {
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                Log.i("Request", buffer.readUtf8());
            }
        } catch (final IOException e) {
            Log.i("Error", "");
        }
    }


}
