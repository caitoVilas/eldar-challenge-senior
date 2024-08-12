package com.eldar.api.controllers;

import com.eldar.api.models.requests.LoginRequest;
import com.eldar.api.models.responses.ExceptionResponse;
import com.eldar.api.models.responses.ExceptionResponses;
import com.eldar.api.models.responses.LoginResponse;
import com.eldar.services.contracts.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth operations")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    @Parameter(name = "request", description = "Login request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponses.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("--> POST endpoint Login controller");
        return ResponseEntity.ok(authService.login(request));
    }
}
