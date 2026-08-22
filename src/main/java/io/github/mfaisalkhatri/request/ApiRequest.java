package io.github.mfaisalkhatri.request;

import java.util.Map;

import io.github.mfaisalkhatri.client.HttpMethod;
import io.github.mfaisalkhatri.exceptions.ApiRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiRequest {
    private HttpMethod          method;
    private String              endpoint;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private Map<String, String> pathParams;
    private Object              body;

    private ApiRequest () {
    }

    public static ApiRequest builder () {
        return new ApiRequest ();
    }

    public ApiRequest post () {
        this.method = HttpMethod.POST;
        return this;
    }

    public ApiRequest get () {
        this.method = HttpMethod.GET;
        return this;
    }

    public ApiRequest put () {
        this.method = HttpMethod.PUT;
        return this;
    }

    public ApiRequest patch () {
        this.method = HttpMethod.PATCH;
        return this;
    }

    public ApiRequest delete () {
        this.method = HttpMethod.DELETE;
        return this;
    }

    public ApiRequest options () {
        this.method = HttpMethod.OPTIONS;
        return this;
    }

    public ApiRequest endpoint (final String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public ApiRequest headers (final Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public ApiRequest queryParams (final Map<String, String> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public ApiRequest pathParams (final Map<String, String> pathParams) {
        this.pathParams = pathParams;
        return this;
    }

    public ApiRequest body (final Object body) {
        this.body = body;
        return this;
    }

    public ApiRequest build () {
        if (this.method == null) {
            throw new ApiRequestException ("HTTP method must be specified");
        }
        if (this.endpoint == null || this.endpoint.isBlank ()) {
            throw new ApiRequestException ("Endpoint must be specified");

        }
        return new ApiRequest (this.method, this.endpoint, this.headers, this.queryParams, this.pathParams, this.body);
    }
}
