package com.cropdealer.CropService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropDTO {
    @NotNull(message = "Farmer ID cannot be null")
    private Long farmerId;

//    @NotNull(message = "Farmer email cannot be null")
//    private String farmerEmail;

    @NotNull(message = "Crop name cannot be null")
    private String cropName;

    @NotNull(message = "Crop type cannot be null")
    private String cropType;

    @NotNull(message = "Crop price per unit  cannot be null")
    private long price;

    @NotNull(message = "Quantity cannot be null")
    private Double quantity;

    @NotNull(message = "Location cannot be null")
    private String location;
}
