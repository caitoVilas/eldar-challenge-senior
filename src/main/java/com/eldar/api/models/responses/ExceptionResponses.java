package com.eldar.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caito Vilas
 * date 08/2024
 * Class to handle exceptions
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ExceptionResponses implements Serializable {
    private int code;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private List<String> messages;
    private String method;
    private String path;
}
