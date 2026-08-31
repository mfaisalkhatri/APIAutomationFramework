package io.github.mfaisalkhatri.logging;

import io.github.mfaisalkhatri.request.ApiRequest;
import io.restassured.response.Response;
import org.slf4j.Logger;

public final class RequestResponseLogger {

    private static final Logger LOGGER = FrameworkLogger.getLogger (RequestResponseLogger.class);

    private static final String SEPARATOR = "=".repeat (60);

    private RequestResponseLogger () {

    }

    public static void logRequest (final ApiRequest request) {
        LOGGER.info (SEPARATOR);
        LOGGER.info ("API REQUEST");
        LOGGER.info (SEPARATOR);

        LOGGER.info ("Method: {}", request.getMethod ());
        LOGGER.info ("Endpoint: {}", request.getEndpoint ());
        if (request.getHeaders () != null) {
            LOGGER.info ("Headers: {}", SensitiveDataMasker.maskHeaders (request.getHeaders ()));
        }
        if (request.getQueryParams () != null) {
            LOGGER.info ("Request Query Params: {}", request.getQueryParams ());
        }
        if (request.getPathParams () != null) {
            LOGGER.info ("Request Path Params: {}", request.getPathParams ());
        }
        if (request.getBody () != null) {
            LOGGER.info ("Request Body: {}", request.getBody ());
        }
    }

    public static void logResponse (final Response response) {
        LOGGER.info (SEPARATOR);
        LOGGER.info ("API RESPONSE");
        LOGGER.info (SEPARATOR);

        if (response.cookies () != null) {
            LOGGER.info ("API Response Cookies: {}", response.cookies ());
        }
        LOGGER.info ("API Response Status: {}", response.statusCode ());
        LOGGER.info ("API Response Status Line: {}", response.statusLine ());
        LOGGER.info ("API Response Headers:");
        response.headers ()
            .asList ()
            .forEach (header -> LOGGER.info ("{}: {}", header.getName (), header.getValue ()));
        LOGGER.info ("API Response Body: {}", response.body ()
            .asPrettyString ());
        LOGGER.info ("API Response Time: {} {}", response.time (), "ms");

        LOGGER.info (SEPARATOR);
    }
}

