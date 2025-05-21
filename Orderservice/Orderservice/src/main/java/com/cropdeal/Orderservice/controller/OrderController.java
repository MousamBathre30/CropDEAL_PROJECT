package com.cropdeal.Orderservice.controller;

import com.cropdeal.Orderservice.entitty.Order;
import com.cropdeal.Orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /*
      --> for the message to check the api is working or not
     */
    @GetMapping
    public String getMessage(){

        return "yes you are ready to place the order";
    }

    /*
     this api for place the order or buy the order by the buyer
     */
    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.placeOrder(order), HttpStatus.CREATED);
    }

    /*
    --> this api is used to get all order by particular dealer.
     */
    @PostMapping("/dealer/{dealerId}")
    public List<Order> getOrdersByDealerId(@PathVariable Long dealerId) {
        return orderService.getOrdersByDealerId(dealerId);
    }

    /*
      get the information about the order by orderID
      when dear want to see the information.
     */
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }



    /*
     --> see the status
     */
    @PutMapping("/{orderId}/{status}")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    /*
     --> Delete the order by order ID --> if and only if the status is panding
     */
    @DeleteMapping("/{orderId}")
    public Order cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    /*
     get all order only the (admin)
     */
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
