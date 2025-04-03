package com.cropdealer.CropService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropDTO {
    @NotNull(message = "Farmer ID cannot be null")
    private Long farmerID;

    @NotNull(message = "Crop type cannot be null")
    private String cropType;

    @NotNull(message = "Quantity cannot be null")
    private Double quantity;

    @NotNull(message = "Location cannot be null")
    private String location;
}
