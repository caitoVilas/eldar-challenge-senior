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
 * date: 08/2024
 * ProductResponse
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "##0.00")
    private BigDecimal price;
    private Integer stock;
}
