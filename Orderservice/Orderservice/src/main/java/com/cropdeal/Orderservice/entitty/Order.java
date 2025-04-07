package com.cropdeal.Orderservice.entitty;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cropId;
    private String cropName;
    private String cropType;
    private Long farmerId;
  //  private String farmerEmail;
    private Long dealerId;
   // private String dealerEmail;
    private Double price; // per unit
    private Double quantity;
    private String status; // "Pending", "Completed"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCropId() {
        return cropId;
    }

    public void setCropId(Long cropId) {
        this.cropId = cropId;
    }

    public Long getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

//    public String getFarmerEmail() {
//        return farmerEmail;
//    }
//
//    public void setFarmerEmail(String farmerEmail) {
//        this.farmerEmail = farmerEmail;
//    }
//
//    public String getDealerEmail() {
//        return dealerEmail;
//    }
//
//    public void setDealerEmail(String dealerEmail) {
//        this.dealerEmail = dealerEmail;
//    }
}
