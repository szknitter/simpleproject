package com.example.orderservice.mapper;

import com.example.orderservice.dto.CustomerOrderCreateTO;
import com.example.orderservice.dto.CustomerOrderTO;
import com.example.orderservice.entity.CustomerOrder;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {

    CustomerOrderTO toTO(CustomerOrder order);

    CustomerOrder fromCreateTO(CustomerOrderCreateTO orderCreateTO);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    void updateOrderFromTO(CustomerOrderTO orderTO, @MappingTarget CustomerOrder order);
}
