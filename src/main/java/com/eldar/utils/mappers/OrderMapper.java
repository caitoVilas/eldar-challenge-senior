package com.eldar.utils.mappers;

import com.eldar.api.models.responses.OrderItemResponse;
import com.eldar.api.models.responses.OrderResponse;
import com.eldar.persistence.entities.Order;

import java.math.BigDecimal;

/**
 * @author caito Vilas
 * date 08/2024
 * The OrderMapper class is used to map the Order entity to OrderResponse
 */
public class OrderMapper {

    /**
     * mapToDto method is used to map the Order entity to OrderResponse
     *
     * @param order
     * @return OrderResponse
     */
    public static OrderResponse mapToDto(Order order) {
        var response = OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .date(order.getDate())
                .items(order.getItems().stream().map(ItemMapper::mapToDto).toList())
                .build();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemResponse item : response.getItems()){
            total = total.add(item.getSubTotal());
        }
        response.setTotal(total);
        return response;
    }
}
