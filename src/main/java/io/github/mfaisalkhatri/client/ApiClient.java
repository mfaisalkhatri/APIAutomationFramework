package io.github.mfaisalkhatri.client;

import io.github.mfaisalkhatri.request.ApiRequest;
import io.github.mfaisalkhatri.response.ApiResponse;

public interface ApiClient {
    ApiResponse execute (ApiRequest request);
}