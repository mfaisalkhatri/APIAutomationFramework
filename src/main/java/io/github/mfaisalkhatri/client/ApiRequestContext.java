package io.github.mfaisalkhatri.client;

import io.github.mfaisalkhatri.config.ConfigManager;
import io.github.mfaisalkhatri.request.ApiRequest;
import io.github.mfaisalkhatri.response.ApiResponse;

public class ApiRequestContext {
    private final ApiClient apiClient;

    public ApiRequestContext () {
        final ConfigManager configManager = new ConfigManager ();
        this.apiClient = new RestAssuredClient (configManager);
    }

    public ApiResponse execute (final ApiRequest request) {
        return this.apiClient.execute (request);
    }
}
