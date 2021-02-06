package com.shadliq.palaces.dto.response.light;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDTO {

    private Integer id;
    private String name;
    private String imageUrl;
    private String regionName;
    private List<RestaurantMenuLightResponseDTO> menus = new ArrayList<>();



}
