package com.eldar.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author caito Vilas
 * date 08/2024
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class OrderRequest implements Serializable {
    private List<OrderItemRequest> items;
}
