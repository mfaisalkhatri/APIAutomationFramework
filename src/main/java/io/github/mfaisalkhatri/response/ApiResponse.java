package io.github.mfaisalkhatri.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse {
    private int                 statusCode;
    private Map<String, String> headers;
    private String              body;
    private long                responseTime;
}
