package com.eldar.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author caito Vilas
 * date: 08/2024
 * ProductRequest
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ProductRequest implements Serializable {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
