package com.example.orderservice.controller;

import com.example.orderservice.entity.CustomerOrder;
import com.example.orderservice.service.CustomerOrderService;
import com.example.orderservice.dto.CustomerOrderTO;
import com.example.orderservice.dto.CustomerOrderCreateTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @GetMapping
    public ResponseEntity<List<CustomerOrderTO>> getOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime dateFrom,
            @RequestParam(required = false) LocalDateTime dateTo) {
        return ResponseEntity.ok(customerOrderService.getOrders(status, dateFrom, dateTo));
    }

    @PostMapping
    public ResponseEntity<CustomerOrderTO> createOrder(@RequestBody CustomerOrderCreateTO orderCreateTO) {
        return ResponseEntity.ok(customerOrderService.createOrder(orderCreateTO));
    }

    @PutMapping
    public ResponseEntity<CustomerOrderTO> updateOrder(@RequestBody CustomerOrderTO orderTO) {
        return ResponseEntity.ok(customerOrderService.updateOrder(orderTO));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        customerOrderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
