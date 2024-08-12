package com.eldar.api.controllers;

import com.eldar.api.models.requests.ChangePasswordRequest;
import com.eldar.api.models.requests.UserRequest;
import com.eldar.api.models.requests.UserUpdateRequest;
import com.eldar.api.models.responses.ExceptionResponse;
import com.eldar.api.models.responses.ExceptionResponses;
import com.eldar.api.models.responses.UserResponse;
import com.eldar.services.contracts.UserService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "User", description = "User operations")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a user")
    @Parameter(name = "request", description = "User request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<?> registerUser(@RequestBody UserRequest request) {
        log.info("--> POST endpoint Registering user controller");
        userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/confirm-registration")
    @Operation(summary = "Confirm registration")
    @Parameter(name = "token", description = "Token to confirm registration")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<?> confirmRegistration(@RequestParam String token) {
        log.info("--> GET endpoint Confirming registration controller");
        userService.confirmRegistration(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get a user")
    @Parameter(name = "id", description = "User id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        log.info("--> GET endpoint Getting user controller");
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<List<UserResponse>> getUsers() {
        log.info("--> GET endpoint Getting users controller");
        var users = userService.getUsers();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/change-password")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Change password")
    @Parameter(name = "request", description = "Change password request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        log.info("--> PUT endpoint Changing password controller");
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Update a user")
    @Parameters({
            @Parameter(name = "id", description = "User id"),
            @Parameter(name = "request", description = "User update request")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        log.info("--> PUT endpoint Updating user controller");
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Delete a user")
    @Parameter(name = "id", description = "User id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("--> DELETE endpoint Deleting user controller");
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
