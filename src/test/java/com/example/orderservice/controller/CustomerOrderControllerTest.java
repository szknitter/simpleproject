package com.example.orderservice.controller;

import com.example.orderservice.dto.CustomerOrderCreateTO;
import com.example.orderservice.dto.CustomerOrderTO;
import com.example.orderservice.entity.CustomerOrder;
import com.example.orderservice.repository.CustomerOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class CustomerOrderControllerTest {

    @Autowired
    private CustomerOrderController customerOrderController;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Test
    void testCreateOrder() {
        // Given
        customerOrderRepository.deleteAll();
        CustomerOrderCreateTO createTO = CustomerOrderCreateTO.builder()
                .customerName("John Doe")
                .orderDescription("New order description")
                .orderValue(BigDecimal.valueOf(100.5))
                .status("PENDING")
                .build();

        // When
        CustomerOrderTO createdOrder = customerOrderController.createOrder(createTO).getBody();

        // Then
        List<CustomerOrder> orders = customerOrderRepository.findAll();
        assertEquals(1, orders.size(), "There should be 1 order in the database");

        CustomerOrder savedOrder = orders.get(0);
        assertNotNull(savedOrder.getOrderId());
        assertEquals(createTO.getCustomerName(), savedOrder.getCustomerName());
        assertEquals(createTO.getOrderDescription(), savedOrder.getOrderDescription());
        assertEquals(createTO.getOrderValue().setScale(2), savedOrder.getOrderValue().setScale(2));
        assertEquals(createTO.getStatus(), savedOrder.getStatus());
    }

    @Test
    @Sql("/database/TestData.sql")  // Load test data before running this test
    void testGetOrders() {
        // When
        List<CustomerOrderTO> orders = customerOrderController.getOrders(null, null, null).getBody();

        // Then
        assertFalse(orders.isEmpty(), "Orders should be retrieved from database");
        assertEquals(2, orders.size(), "Should return 2 orders from test data");
    }

    @Test
    void testUpdateOrder() {
        // Given
        CustomerOrderCreateTO createTO = CustomerOrderCreateTO.builder()
                .customerName("Jane Doe")
                .orderDescription("Sample order")
                .orderValue(BigDecimal.valueOf(200.00))
                .status("NEW")
                .build();
        CustomerOrderTO createdOrder = customerOrderController.createOrder(createTO).getBody();

        UUID orderId = createdOrder.getOrderId();
        CustomerOrderTO updateTO = CustomerOrderTO.builder()
                .orderId(orderId)
                .customerName("Updated Name")
                .orderDescription("Updated Description")
                .orderValue(BigDecimal.valueOf(250.75))
                .status("COMPLETED")
                .build();

        // When
        customerOrderController.updateOrder(updateTO);

        // Then
        Optional<CustomerOrder> updatedOrder = customerOrderRepository.findById(orderId);
        assertTrue(updatedOrder.isPresent(), "Updated order should be found in database");
        assertEquals("Updated Name", updatedOrder.get().getCustomerName());
        assertEquals("Updated Description", updatedOrder.get().getOrderDescription());
        assertEquals(BigDecimal.valueOf(250.75), updatedOrder.get().getOrderValue());
        assertEquals("COMPLETED", updatedOrder.get().getStatus());
    }

    @Test
    void testDeleteOrder() {
        // Given
        CustomerOrderCreateTO createTO = CustomerOrderCreateTO.builder()
                .customerName("To Be Deleted")
                .orderDescription("This order will be deleted")
                .orderValue(BigDecimal.valueOf(300.00))
                .status("PENDING")
                .build();
        CustomerOrderTO createdOrder = customerOrderController.createOrder(createTO).getBody();
        UUID orderId = createdOrder.getOrderId();

        // When
        customerOrderController.deleteOrder(orderId);

        // Then
        Optional<CustomerOrder> deletedOrder = customerOrderRepository.findById(orderId);
        assertFalse(deletedOrder.isPresent(), "Order should be deleted from the database");
    }
}
