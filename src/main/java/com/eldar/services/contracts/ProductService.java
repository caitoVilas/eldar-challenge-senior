package com.eldar.services.contracts;

import com.eldar.api.models.requests.ProductRequest;
import com.eldar.api.models.responses.ProductResponse;

import java.util.List;

/**
 * @author caito Vilas
 * date: 08/2024
 * ProductService
 */
public interface ProductService {
    void createProduct(ProductRequest request);
    ProductResponse getProduct(Long id);
    List<ProductResponse> getProducts();
    void deleteProduct(Long id);
    ProductResponse updateProduct(Long id, ProductRequest request);
}
