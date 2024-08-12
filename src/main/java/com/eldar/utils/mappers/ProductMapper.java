package com.eldar.utils.mappers;

import com.eldar.api.models.requests.ProductRequest;
import com.eldar.api.models.responses.ProductResponse;
import com.eldar.persistence.entities.Product;

/**
 * @author caito Vilas
 * date: 08/2024
 * ProductMapper
 */
public class ProductMapper {

    /**
     * Maps a ProductRequest to a Product entity
     * @param request
     * @return Product
     */
    public static Product mapToEntity(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }

    /**
     * Maps a Product entity to a ProductResponse
     * @param entity
     * @return ProductResponse
     */
    public static ProductResponse mapToDto(Product entity){
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .build();
    }
}
