package com.cropdealer.CropService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cropsNEW")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Farmer ID cannot be null")
    @Column(nullable = false)
    private Long farmerID;

    @NotNull(message = "Crop type cannot be null")
    @Column(nullable = false)
    private String cropType;

    @NotNull(message = "Quantity cannot be null")
    @Column(nullable = false)
    private Double quantity;

    @NotNull(message = "Location cannot be null")
    @Column(nullable = false)
    private String location;
}
