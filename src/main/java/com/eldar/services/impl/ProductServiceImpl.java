package com.eldar.services.impl;

import com.eldar.api.exceptions.customs.BadRequestException;
import com.eldar.api.exceptions.customs.NotFoundException;
import com.eldar.api.models.requests.ProductRequest;
import com.eldar.api.models.responses.ProductResponse;
import com.eldar.persistence.entities.Product;
import com.eldar.persistence.repository.ProductRepository;
import com.eldar.services.contracts.ProductService;
import com.eldar.utils.constants.ProductConstants;
import com.eldar.utils.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    /**
     * Create a product
     *
     * @param request
     */
    @Override
    @Transactional
    public void createProduct(ProductRequest request) {
        log.info("--> Creating product service");
        this.validateProductRequest(request);
        productRepository.save(ProductMapper.mapToEntity(request));
    }

    /**
     * Get a product
     *
     * @param id
     * @return
     */
    @Override
    public ProductResponse getProduct(Long id) {
        log.info("--> Getting product service");
        return ProductMapper.mapToDto(this.getById(id));
    }

    /**
     * Get all products
     *
     * @return
     */
    @Override
    public List<ProductResponse> getProducts() {
        log.info("--> Getting products service");
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::mapToDto).toList();
    }

    /**
     * Delete a product
     *
     * @param id
     */
    @Override
    public void deleteProduct(Long id) {
        log.info("--> Deleting product service");
        Product product = this.getById(id);
        productRepository.delete(product);
    }

    /**
     * Validate product request
     *
     * @param request
     */
    private void validateProductRequest(ProductRequest request) {
        log.info("--> Validating product request");
        List<String> errors = new ArrayList<>();
        if (request.getName() == null || request.getName().isEmpty()) {
            errors.add(ProductConstants.PRODUCT_NO_NAME);
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            errors.add(ProductConstants.PRODUCT_NO_DESCRIPTION);
        }
        if (request.getStock() == null || request.getStock() <= 0) {
            errors.add(ProductConstants.PRODUCT_NO_STOCK);
        }
        if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add(ProductConstants.PRODUCT_PRICE_INVALID);
        }
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }

    /**
     * Get product by id
     *
     * @param id
     * @return
     */
    private Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ProductConstants.PRODUCT_NOT_FOUND));
    }
}
