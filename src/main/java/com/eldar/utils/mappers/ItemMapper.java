package com.eldar.utils.mappers;

import com.eldar.api.models.responses.OrderItemResponse;
import com.eldar.persistence.entities.OrderItem;

import java.math.BigDecimal;

/**
 * @author caito Vilas
 * date 08/2024
 * The ItemMapper class is used to map the OrderItem entity to ItemResponse
 */
public class ItemMapper {

    /**
     * mapToDto method is used to map the OrderItem entity to ItemResponse
     *
     * @param orderItem
     * @return ItemResponse
     */
    public static OrderItemResponse mapToDto(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .product(ProductMapper.mapToDto(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .subTotal(BigDecimal.valueOf(orderItem.getQuantity()).multiply(orderItem.getProduct().getPrice()))
                .build();
    }
}
