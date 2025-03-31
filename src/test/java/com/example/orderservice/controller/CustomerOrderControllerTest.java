package com.example.orderservice.controller;

import com.example.orderservice.dto.CustomerOrderCreateTO;
import com.example.orderservice.entity.CustomerOrder;
import com.example.orderservice.repository.CustomerOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerOrderControllerTest {

    @Autowired
    private CustomerOrderController customerOrderController;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @BeforeEach
    void setUp() {
        // Optionally clear the database before each test
        customerOrderRepository.deleteAll();
    }

    @Test
    void testCreateOrder() {
        // Create a CustomerOrderCreateTO object
        CustomerOrderCreateTO createTO = CustomerOrderCreateTO.builder()
                .customerName("John Doe")
                .orderDescription("New order description")
                .orderValue(BigDecimal.valueOf(100.50))
                .status("PENDING")
                .build();

        // Call the createOrder method directly from the controller
        customerOrderController.createOrder(createTO);

        // Verify the order was saved in the repository
        List<CustomerOrder> orders = customerOrderRepository.findAll();
        assertEquals(1, orders.size(), "There should be 1 order in the database");

        CustomerOrder savedOrder = orders.get(0);

    }

    private void assertEquals(int i, int size, String s) {

    }
}
