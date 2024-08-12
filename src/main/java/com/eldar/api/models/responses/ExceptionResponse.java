package com.eldar.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author caito Vilas
 * date 08/2024
 * Exception response
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ExceptionResponse implements Serializable {
    private int code;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String method;
    private String path;
}
