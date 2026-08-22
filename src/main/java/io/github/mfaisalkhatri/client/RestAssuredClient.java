package io.github.mfaisalkhatri.client;

import java.util.Map;
import java.util.stream.Collectors;

import io.github.mfaisalkhatri.config.ConfigManager;
import io.github.mfaisalkhatri.exceptions.ApiRequestException;
import io.github.mfaisalkhatri.request.ApiRequest;
import io.github.mfaisalkhatri.response.ApiResponse;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredClient implements ApiClient {

    private final ConfigManager configManager;

    public RestAssuredClient (final ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public ApiResponse execute (final ApiRequest request) {
        validateRequest (request);
        final RequestSpecification requestSpecification = createRequestSpecification (request);

        final Response response = requestSpecification.request (toRestAssuredMethod (request.getMethod ()),
            request.getEndpoint ());
        return toAPIResponse (response);
    }

    private RequestSpecification createRequestSpecification (final ApiRequest request) {
        final RequestSpecification requestSpecification = RestAssured.given ()
            .baseUri (this.configManager.getBaseUrl ())
            .config (RestAssured.config ()
                .httpClient (HttpClientConfig.httpClientConfig ()
                    .setParam ("http.connection.timeout", this.configManager.getConnectionTimeout ())));
        addHeaders (requestSpecification, request.getHeaders ());
        addQueryParams (requestSpecification, request.getQueryParams ());
        addPathParams (requestSpecification, request.getPathParams ());
        if (request.getBody () != null) {
            requestSpecification.body (request.getBody ());
        }
        return requestSpecification;
    }

    private void addHeaders (final RequestSpecification requestSpecification, final Map<String, String> headers) {
        if (headers != null && !headers.isEmpty ()) {
            requestSpecification.headers (headers);
        }
    }

    private void addQueryParams (final RequestSpecification requestSpecification,
        final Map<String, String> queryParams) {
        if (queryParams != null && !queryParams.isEmpty ()) {
            requestSpecification.queryParams (queryParams);
        }
    }

    private void addPathParams (final RequestSpecification requestSpecification, final Map<String, String> pathParams) {
        if (pathParams != null && !pathParams.isEmpty ()) {
            requestSpecification.pathParams (pathParams);
        }
    }

    private ApiResponse toAPIResponse (final Response response) {
        final Map<String, String> headers = response.headers ()
            .asList ()
            .stream ()
            .collect (Collectors.toMap (Header::getName, Header::getValue, (first, second) -> second));
        return new ApiResponse (response.getStatusCode (), headers, response.asString (), response.time ());
    }

    private Method toRestAssuredMethod (final HttpMethod method) {
        return Method.valueOf (method.name ());
    }

    private void validateRequest (final ApiRequest request) {
        if (request == null) {
            throw new ApiRequestException ("API request must not be null");
        }
        if (request.getMethod () == null) {
            throw new ApiRequestException ("HTTP Method must be specified");
        }
        if (request.getEndpoint () == null || request.getEndpoint ()
            .isBlank ()) {
            throw new ApiRequestException ("Endpoint must be specified");
        }
    }
}