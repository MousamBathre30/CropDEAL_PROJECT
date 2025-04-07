package com.cropDeal.PaymentService.dto;

public class PaymentRequestDTO {
    private Long orderId;
    private String accountNumber;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
// Getters and Setters (or use Lombok @Data if you like)
}
