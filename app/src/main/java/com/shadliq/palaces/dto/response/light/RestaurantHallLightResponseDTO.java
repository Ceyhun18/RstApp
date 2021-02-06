package com.shadliq.palaces.dto.response.light;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class RestaurantHallLightResponseDTO {

    private Integer minGuestCount;

    private Integer maxGuestCount;

    private String hallName;
}
