package com.eldar.api.controllers;

import com.eldar.api.models.requests.OrderRequest;
import com.eldar.api.models.responses.ExceptionResponse;
import com.eldar.api.models.responses.ExceptionResponses;
import com.eldar.api.models.responses.OrderResponse;
import com.eldar.services.contracts.OrderService;
import com.eldar.utils.enums.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

/**
 * @author caito Vilas
 * date 08/2024
 * Represents the order controller.
 */
@RestController
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Order", description = "Operations related to orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Create order")
    @Parameter(name = "request", description = "Order request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
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
    public ResponseEntity<?> create(@RequestBody OrderRequest request) {
        log.info("--> POST endpoint Creating order controller");
        orderService.createOrder(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get order")
    @Parameter(name = "id", description = "Order id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
        log.info("--> GET endpoint Getting order controller");
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PutMapping("/status/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Change order status")
    @Parameters({
            @Parameter(name = "id", description = "Order id"),
            @Parameter(name = "status", description = "Order status")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order status changed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<OrderResponse> changeStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        log.info("--> PUT endpoint Changing order status controller");
        var response = orderService.changeStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
