package com.eldar.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author caito Vilas
 * date 08/2024
 * OrderItemResponse class is used to represent the response of an order item.
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class OrderItemResponse implements Serializable {
    private Long id;
    private ProductResponse product;
    private Integer quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "##0.00")
    private BigDecimal subTotal;
}
