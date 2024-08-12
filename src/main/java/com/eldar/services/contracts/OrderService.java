package com.eldar.services.contracts;

import com.eldar.api.models.requests.OrderRequest;
import com.eldar.api.models.responses.OrderResponse;
import com.eldar.utils.enums.OrderStatus;

/**
 * @author caito Vilas
 * date 08/2024
 * OrderService interface
 */
public interface OrderService {
    void createOrder(OrderRequest request);
    OrderResponse getOrder(Long id);
    OrderResponse changeStatus(Long id, OrderStatus status);
}
