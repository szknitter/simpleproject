package com.example.orderservice.service;

import com.example.orderservice.dto.CustomerOrderCreateTO;
import com.example.orderservice.dto.CustomerOrderTO;
import com.example.orderservice.entity.CustomerOrder;
import com.example.orderservice.mapper.CustomerOrderMapper;
import com.example.orderservice.repository.CustomerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerOrderMapper customerOrderMapper;

    @Transactional(readOnly = true)
    public List<CustomerOrderTO> getOrders(String status, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<CustomerOrder> orders = customerOrderRepository.findByFilters(status, dateFrom, dateTo);
        return orders.stream().map(customerOrderMapper::toTO).collect(Collectors.toList());
    }

    @Transactional
    public CustomerOrderTO createOrder(CustomerOrderCreateTO orderCreateTO) {
        CustomerOrder order = customerOrderMapper.fromCreateTO(orderCreateTO);
        order.setOrderId(UUID.randomUUID());
        order.setOrderDate(LocalDateTime.now());
        CustomerOrder savedOrder = customerOrderRepository.save(order);
        return customerOrderMapper.toTO(savedOrder);
    }

    @Transactional
    public CustomerOrderTO updateOrder(CustomerOrderTO orderTO) {
        CustomerOrder existingOrder = customerOrderRepository.findById(orderTO.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        customerOrderMapper.updateOrderFromTO(orderTO, existingOrder);
        CustomerOrder updatedOrder = customerOrderRepository.save(existingOrder);
        return customerOrderMapper.toTO(updatedOrder);
    }

    @Transactional
    public void deleteOrder(UUID orderId) {
        customerOrderRepository.deleteById(orderId);
    }
}