package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDtoWithOrdersStatus {
    Orders orders;
    OrderStatus orderStatus;
}
