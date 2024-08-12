package com.eldar.api.models.responses;

import com.eldar.utils.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author caito Vilas
 * date 08/2024
 * OrderResponse class is used to represent the response of an order.
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class OrderResponse implements Serializable {
    private Long id;
    private List<OrderItemResponse> items;
    private OrderStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "##0.00")
    private BigDecimal total;
}
