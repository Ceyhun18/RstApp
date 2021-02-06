package com.shadliq.palaces.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.shadliq.palaces.dto.response.ErrorResponseDTO;
import com.shadliq.palaces.exception.OkHttpException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

class CustomInterceptor implements Interceptor {

    private Gson gson = new Gson();
    private Context context;

    CustomInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i("URL", request.url().toString());
//        printRequest(request);
//        LoginResponseDTO userData = UserService.getCurrentUserData();
//        if (userData != null) {
//            String accessToken = userData.getAccessToken();
//            Log.i("TOKEN", accessToken);
//            request = request.newBuilder().addHeader("Authorization", "bearer " + accessToken).build();
//        }
        Response response = chain.proceed(request);

        /*
            note that when we call response.body().string() twice it causes an error
         */
        String body = response.body().string();
        Log.i("Body", body);
        if (!body.isEmpty()) {
            ErrorResponseDTO responseDTO = gson.fromJson(body, ErrorResponseDTO.class);
            if (responseDTO.getCode() != null) {
//                if (responseDTO.getErrorCode() == 101) {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
                throw new OkHttpException(responseDTO.getMessage().toString());
            }

        }
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), body))
                .build();
    }
}
