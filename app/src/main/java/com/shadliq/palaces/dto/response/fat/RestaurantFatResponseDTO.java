package com.shadliq.palaces.dto.response.fat;


import com.shadliq.palaces.dto.response.light.RestaurantContactLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantHallLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantMenuLightResponseDTO;
import com.shadliq.palaces.dto.response.light.RestaurantOfferLightResponseDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RestaurantFatResponseDTO {
    private Integer id;
    private String name;
    private String location;
    private String regionName;
    private List<String> imageUrls = new ArrayList<>();
    private List<RestaurantContactLightResponseDTO> contacts = new ArrayList<>();
    private List<Byte> events = new ArrayList<>();
    private List<RestaurantHallLightResponseDTO> halls = new ArrayList<>();
    private List<RestaurantMenuLightResponseDTO> menus = new ArrayList<>();
    private List<RestaurantOfferLightResponseDTO> offers = new ArrayList<>();
}




