package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDtoWithOrdersStatus {
    Orders orders;
    OrderStatus orderStatus;
}
