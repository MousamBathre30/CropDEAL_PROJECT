package com.cropDeal.PaymentService.service;

import com.cropDeal.PaymentService.dto.CropDTO;
import com.cropDeal.PaymentService.dto.OrderDTO;

import com.cropDeal.PaymentService.dto.PaymentMessageDTO;
import com.cropDeal.PaymentService.entity.Invoice;
import com.cropDeal.PaymentService.entity.Payment;
import com.cropDeal.PaymentService.feignClient.CropClient;
import com.cropDeal.PaymentService.feignClient.OrderClient;
import com.cropDeal.PaymentService.repo.InvoiceRepository;
import com.cropDeal.PaymentService.repo.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final RabbitMQSender rabbitMQSender;
    private final OrderClient orderClient;
    private final CropClient cropClient;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          InvoiceRepository invoiceRepository,
                          RabbitMQSender rabbitMQSender,
                          OrderClient orderClient,
                          CropClient cropClient) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        this.rabbitMQSender = rabbitMQSender;
        this.orderClient = orderClient;
        this.cropClient = cropClient;
    }

    @Transactional
    public Payment processPayment(Long orderId, String accountNumber) {

        OrderDTO order = orderClient.getOrderById(orderId);

//        List<CropDTO> crops = cropClient.getCropById(orderId);
//        CropDTO crop = crops.isEmpty() ? null : crops.get(0);

        double totalAmount = order.getPrice() * order.getQuantity();

        // Save Payment
        // Create Payment object manually
        Payment payment = new Payment();
      //  payment.setPaymentId(UUID.randomUUID());
        payment.setOrderId(order.getId());
        payment.setDealerId(order.getDealerId());
        payment.setFarmerId(order.getFarmerId());
        payment.setCropName(order.getCropName());
        payment.setCropType(order.getCropType());
        payment.setAmount(totalAmount);
        payment.setAccountNumber(accountNumber);
        payment.setStatus("COMPLETED");
        payment.setTimestamp(LocalDateTime.now());

// Save Payment
        Payment savedPayment = paymentRepository.save(payment);

// Create Invoice object manually
        Invoice invoice = new Invoice();
        invoice.setOrderId(order.getId());
        invoice.setPaymentId(savedPayment.getPaymentId());
        invoice.setCropName(order.getCropName());
        invoice.setPricePerUnit(order.getPrice());
        invoice.setQuantity(order.getQuantity());
        invoice.setTotalAmount(totalAmount);
        invoice.setGeneratedAt(LocalDateTime.now());

// Save Invoice
        invoiceRepository.save(invoice);

// Prepare and send message
        PaymentMessageDTO message = new PaymentMessageDTO();
        message.setOrderId(order.getId());
        message.setStatus("COMPLETED");
        message.setMessage("Payment successful!");

        rabbitMQSender.send(message);

// Return saved payment
        return savedPayment;
    }

    }
