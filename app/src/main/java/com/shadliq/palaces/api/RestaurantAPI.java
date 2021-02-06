package com.shadliq.palaces.api;

import com.shadliq.palaces.dto.response.ListResponseDTO;
import com.shadliq.palaces.dto.response.fat.RestaurantFatResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.shadliq.palaces.constant.ApiPath.API_PATH;

public interface RestaurantAPI {

    String CONTROLLER = API_PATH + "/restaurants";


    @GET(CONTROLLER)
    Call<ListResponseDTO> getData(@Query("offset") int offset, @Query("max") int max,
                                  @Query("maxMenuPrice") Integer maxMenuPrice,
                                  @Query("eventType") Byte eventType,
                                  @Query("maxGuestCount") Integer maxGuestCount,
                                  @Query("name") String name);


    @GET(CONTROLLER + "/{id}")
    Call<RestaurantFatResponseDTO> getDetails(@Path("id") Integer id);
}
