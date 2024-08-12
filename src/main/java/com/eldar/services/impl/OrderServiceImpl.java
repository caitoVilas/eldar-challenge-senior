package com.eldar.services.impl;

import com.eldar.api.exceptions.customs.BadRequestException;
import com.eldar.api.exceptions.customs.NotFoundException;
import com.eldar.api.models.requests.OrderItemRequest;
import com.eldar.api.models.requests.OrderRequest;
import com.eldar.api.models.responses.OrderItemResponse;
import com.eldar.api.models.responses.OrderResponse;
import com.eldar.persistence.entities.Order;
import com.eldar.persistence.entities.OrderItem;
import com.eldar.persistence.repository.OrderItemRepository;
import com.eldar.persistence.repository.OrderRepository;
import com.eldar.persistence.repository.ProductRepository;
import com.eldar.services.contracts.OrderService;
import com.eldar.utils.constants.ItemConstants;
import com.eldar.utils.constants.ProductConstants;
import com.eldar.utils.enums.OrderStatus;
import com.eldar.utils.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caito Vilas
 * date 08/2024
 * Implementation of the OrderService interface.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Value("${application.limit-Minimum-Stock}")
    private String limitMinimumStock;

    /**
     * Creates an order.
     * @param request the order request.
     */
    @Override
    @Transactional
    public void createOrder(OrderRequest request) {
        log.info("--> Creating order service");
        this.validateItems(request);
        List<OrderItem> items = new ArrayList<>();
        request.getItems().forEach(item -> {
            var product = productRepository.findById(item.getProductId()).get();
            items.add(OrderItem.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .build());
        });
        var order = Order.builder()
                .date(LocalDate.now())
                .status(OrderStatus.PENDING)
                .items(items)
                .build();
        var orderNew =orderRepository.save(order);
        // Update stock
        items.forEach(item -> {
            var product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            if (product.getStock() <= Integer.parseInt(limitMinimumStock)) {
                log.warn("Product {} has reached the minimum stock limit", product.getId());
            }
            item.setOrder(orderNew);
            orderItemRepository.save(item);
            productRepository.save(product);
        });
    }

    /**
     * Gets an order.
     * @param id the order id.
     * @return the order response.
     */
    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        log.info("--> Getting order service");
        var order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ItemConstants.ORDER_NOT_FOUND + id));
        return OrderMapper.mapToDto(order);
    }

    /**
     * Changes the status of an order.
     * @param id the order id.
     * @param status the order status.
     * @return the order response.
     */
    @Override
    @Transactional
    public OrderResponse changeStatus(Long id, OrderStatus status) {
        log.info("--> Changing status order service");
        var order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ItemConstants.ORDER_NOT_FOUND + id));
        if (order.getStatus().equals(OrderStatus.CANCELED)){
            throw new BadRequestException(List.of(ItemConstants.ORDER_CANCELLED));
        }
        order.setStatus(status);
        var newOrder = orderRepository.save(order);
        //is status is CANCELED return the products to stock
        if (status.equals(OrderStatus.CANCELED)){
            order.getItems().forEach(item -> {
                var product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            });
        }
        return OrderMapper.mapToDto(newOrder);
    }

    /**
     * Validates the items in the order request.
     * @param request the order request.
     */
    public void validateItems(OrderRequest request) {
        log.info("--> Validating items");
        List<String> errors = new ArrayList<>();
        request.getItems().forEach(item -> {
            if (productRepository.findById(item.getProductId()).isEmpty()){
                errors.add(ProductConstants.PRODUCT_NOT_FOUND);
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0){
                errors.add(ProductConstants.PRODUCT_NO_STOCK);
            }
        });
        if (!errors.isEmpty()){
            throw new BadRequestException(errors);
        }
    }
}
