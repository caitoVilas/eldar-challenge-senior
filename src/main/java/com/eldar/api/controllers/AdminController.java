package com.eldar.api.controllers;

import com.eldar.api.models.responses.ExceptionResponse;
import com.eldar.api.models.responses.ExceptionResponses;
import com.eldar.api.models.responses.UserResponse;
import com.eldar.services.contracts.AdminService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin operations")
public class AdminController {
    private final AdminService adminService;

    @PutMapping("/assign-admin/{userId}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Assign admin role to a user", description = "Assign admin role to a user")
    @Parameter(name = "userId", description = "User id to assign admin role", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin role assigned",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<UserResponse> assignAdmin(@PathVariable Long userId) {
        log.info("--> PUT endpoint Assigning admin controller");
        return ResponseEntity.ok(adminService.AssingAdmin(userId));
    }

    @PutMapping("/deallocate-admin/{userId}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Deallocate admin role to a user", description = "Deallocate admin role to a user")
    @Parameter(name = "userId", description = "User id to deallocate admin role", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin role deallocated",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<UserResponse> deallocateAdmin(@PathVariable Long userId) {
        log.info("--> PUT endpoint Deallocating admin controller");
        return ResponseEntity.ok(adminService.deallocateAdmin(userId));
    }
}
