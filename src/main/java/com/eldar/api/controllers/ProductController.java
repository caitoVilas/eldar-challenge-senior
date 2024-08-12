package com.eldar.api.controllers;

import com.eldar.api.models.requests.ProductRequest;
import com.eldar.api.models.responses.ExceptionResponse;
import com.eldar.api.models.responses.ExceptionResponses;
import com.eldar.api.models.responses.ProductResponse;
import com.eldar.services.contracts.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author  caito Vilas
 * date: 08/2024
 * Product controller
 */
@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create-product")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Create a product")
    @Parameter(name = "request", description = "Product request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<?> create(@RequestBody ProductRequest request) {
        log.info("--> POST endpoint Creating product controller");
        productService.createProduct(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get a product")
    @Parameter(name = "id", description = "Product id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        log.info("--> GET endpoint Getting product controller");
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Products not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<List<ProductResponse>> getAll() {
        log.info("--> GET endpoint Getting products controller");
        var products = productService.getProducts();
        if (products.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Delete a product")
    @Parameter(name = "id", description = "Product id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("--> DELETE endpoint Deleting product controller");
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
