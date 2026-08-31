package io.github.mfaisalkhatri.response;

import java.util.Map;

import io.github.mfaisalkhatri.exceptions.ApiResponseException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@AllArgsConstructor
@Getter
public class ApiResponse {
    private int                 statusCode;
    private Map<String, String> headers;
    private String              body;
    private long                responseTime;

    public <T> T getBodyAs (final Class<T> responseType) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper ();
            return objectMapper.readValue (this.body, responseType);
        } catch (final JacksonException e) {
            throw new ApiResponseException (
                "Failed to Deserialize the response body to " + responseType.getSimpleName (), e);
        }

    }
}
