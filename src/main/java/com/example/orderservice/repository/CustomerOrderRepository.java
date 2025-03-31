package com.example.orderservice.repository;

import com.example.orderservice.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {

    @Query("SELECT o FROM CustomerOrder o WHERE (:status IS NULL OR o.status = :status) " +
            "AND (:dateFrom IS NULL OR o.orderDate >= :dateFrom) " +
            "AND (:dateTo IS NULL OR o.orderDate <= :dateTo)")
    List<CustomerOrder> findByFilters(@Param("status") String status,
                                      @Param("dateFrom") LocalDateTime dateFrom,
                                      @Param("dateTo") LocalDateTime dateTo);
}
