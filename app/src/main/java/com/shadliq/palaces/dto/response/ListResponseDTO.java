package com.shadliq.palaces.dto.response;

import com.shadliq.palaces.dto.response.light.RestaurantResponseDTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResponseDTO {

    private List<RestaurantResponseDTO> list = new ArrayList<>();
    private Long count;

}
