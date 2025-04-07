package com.cropDeal.PaymentService.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropDTO {
    private Long cropId;
    private Long farmerId;
    private String cropName;
    private String cropType;
    private long price;
    private Double quantity;
    private String location;
}
